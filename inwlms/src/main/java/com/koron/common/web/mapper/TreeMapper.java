package com.koron.common.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

public interface TreeMapper {
	/**
	 * 根据序号获取对应的层级数据
	 * 
	 * @param seq 序号
	 * @param type 类型
	 * @return 对应的节点
	 */
	@Select("select * from tbltree where type = #{type} and seq = #{seq}")
	public LongTreeBean getBySeq(@Param("seq") long seq, @Param("type") int type);

	/**
	 * 根据ID获取对应的层级数据
	 * 
	 * @param id ID
	 * @return 对应的节点
	 */
	@Select("select * from tbltree where id = #{id}")
	public LongTreeBean getById(int id);

	/**
	 * 根据ID获取对应的层级数据
	 * 
	 * @param id ID
	 * @return 对应的节点
	 */
	@Select("select * from tbltree where foreignkey = #{foreignkey}")
	public LongTreeBean getByForeignkey(int foreignkey);

	/**
	 * 获取节点的直接下级节点
	 * 
	 * @param bean 节点
	 * @return 节点集合
	 */
	@Select("select * from tbltree where bitand(seq,bitnot((bitlmv(1,(62 - #{parentMask}-#{mask}))-1))) = #{seq} "
			+ "and bitand(seq,(bitlmv(1,(62 - #{parentMask}-#{mask} - #{childMask}))-1)) = 0 and type = #{type}")
	public List<LongTreeBean> getChildren(LongTreeBean bean);

	/**
	 * 获取节点之下所有节点
	 * 
	 * @param bean 节点
	 * @return 节点集合
	 */
	@Select("select * from tbltree where bitand(seq,bitnot(bitlmv(1,(62 - #{parentMask}-#{mask}))-1)) = #{seq} and type = #{type} order by seq")
	public List<LongTreeBean> getDescendant(@Param("seq") long seq, @Param("type") int type, @Param("mask") int mask, @Param("parentMask") int parentMask);

	/**
	 * 获取节点的兄弟节点
	 * 
	 * @param bean 节点
	 * @return 节点集合
	 */
	@Select("select * from tbltree where bitand(seq,bitnot(bitlmv(1,(62 - parentmask))-1)) = bitand(#{bean.seq},bitnot(bitlmv(1,(62 - #{bean.parentMask}))-1))"
			+ " and type=#{bean.type} order by seq")
	public List<LongTreeBean> getSibling(@Param("bean") LongTreeBean bean);

	/**
	 * 获取节点的路径，从最上一级到当前级
	 * 
	 * @param bean 节点
	 * @return 节点集合
	 */
	@Select("select * from tbltree where bitand(#{seq},bitnot(bitlmv(1,(62 - parentmask-mask))-1)) = seq and type = #{type} order by seq")
	public List<LongTreeBean> getPath(@Param("type") int type, @Param("seq") long seq);

	/**
	 * 添加一个节点
	 * 
	 * @param bean
	 * @return
	 */
	@Insert("insert into tbltree(id,seq,parentmask,mask,childmask,type,foreignkey) "
			+ "values (#{id},#{node.seq},#{parent.parentMask}+#{parent.mask},#{parent.childMask}," + "0,#{node.type},#{node.foreignkey})")
	@SelectKey(keyProperty="id", before = true, resultType = Integer.class, statement = { "SELECT SEQ_TBLAPP.Nextval as ID from DUAL" })
	public Integer add(@Param("parent") LongTreeBean parent, @Param("node") LongTreeBean node);

	/**
	 * 更新节点的父节点
	 * 
	 * @param bean
	 * @return
	 */
	@Update("update tbltree set parentmask = parentmask + #{sign} * #{offset} , seq = ((seq & #{mask}) ${shift} #{offset}) | #{parentSeq} where foreignkey = #{root} and seq > #{min} and seq < #{max}")
	public Integer moveNode(@Param("parentSeq") long parentSeq, @Param("mask") long mask, @Param("shift") String shift, @Param("sign") int sign,
                            @Param("offset") int offset, @Param("root") String root, @Param("min") long min, @Param("max") long max);

	/**
	 * 删除一个节点
	 * 
	 * @param bean
	 * @return
	 */
	@Delete("delete from tbltree where seq = #{seq} and type = #{type}")
	public Integer delete(@Param("type") int type, @Param("seq") long seq);

	/**
	 * 获取符合要求的节点的数量. 包含下限不包含上限
	 * 
	 * @param lower 下限
	 * @param upper 上限
	 * @param mask 掩码位数(即最后mask位全为0)
	 * @param type 类型
	 * @return
	 */
	@Select("select count(0) from tbltree where type = #{type} and seq >= #{lower} and seq < #{upper} and bitand(seq,bitlmv(1,#{mask})-1) = 0")
	public int getCount(@Param("lower") long lower, @Param("upper") long upper, @Param("mask") int mask, @Param("type") int type);

	/**
	 * 扩展节点的子掩码
	 * 
	 * @param extendMask 扩展的位数
	 * @param type 类型
	 * @param seq seq
	 * @return
	 */
	@Update("update tbltree set childmask = childmask + #{extendMask} where type = #{type} and seq = #{seq}")
	public int updateChildMask(@Param("extendMask") int extendMask, @Param("type") int type, @Param("seq") long seq);

	@Update("update tbltree set childmask = #{childmask} where foreignkey = #{foreignkey}")
	public int setChildMask(@Param("childmask") int childmask, @Param("foreignkey") String foreignkey);

	@Select("update tbltree set mask = #{bean.mask},parentmask = #{bean.parentMask},seq = #{bean.seq} where id = #{bean.id}")
	public Integer updateMask(@Param("bean") LongTreeBean bean);

	/**
	 * 获取已使用位数
	 * 
	 * @param lower 父节点SEQ
	 * @param upper 下一个节点SEQ
	 * @param type 类型
	 * @return
	 */
	@Select("select max(parentmask + mask + childmask) from tbltree where type = #{type} and seq between #{lower} and #{upper}")
	public Integer getMaxMask(@Param("lower") long lower, @Param("upper") long upper, @Param("type") int type);

	/**
	 * 
	 * @return
	 */
	@Select("select nvl(max(key),0) from (select rownum as key,value from"
			+ "(select rownum as key,bitand(bitrmv(a.seq , (62-parentmask-mask)),bitlmv(1,mask)-1) as value from tbltree a where a.type = #{type} and seq > #{lower} and seq < #{upper} and bitand(seq,bitlmv(1,#{mask}) -1) = 0 order by seq asc) b) t where t.key = t.value")
	public int getAvailable(@Param("lower") long lower, @Param("upper") long upper, @Param("mask") int mask, @Param("type") int type);
	/**
	 * 获取一定范围的数据. 不包含上下限
	 * 
	 * @param lower 下限
	 * @param upper 上限
	 * @param type
	 * @return
	 */
	@Select("select id,seq,parentmask,mask,childmask,type,foreignkey from tbltree where type = #{type} and seq > #{lower} and seq < #{upper} order by seq asc")
	public List<LongTreeBean> getRange(@Param("lower") long lower, @Param("upper") long upper, @Param("type") int type);

	@Select("select * from tbltree where type = #{type} and foreignkey = #{foreign}")
	public LongTreeBean getBeanByForeignIdType(@Param("type") int type, @Param("foreign") String foreignKey);

	@Select("select * from"+
			"(select rownum num,t.* from"+
			"(select * from tbltree where bitand(#{seq},bitnot(bitlmv(1,62 - parentmask - mask)-1)) = seq and type = #{type} order by seq desc) t) where num=1" )
	public LongTreeBean getParant(LongTreeBean bean);

	@Select("select tbltree.seq,tbltree.parentmask from tbltree " + "inner join (" + "	select tbltree.* from tbltree "
			+ "where type = #{type} and seq =#{seq}) a on bitand(tbltree.seq,bitnot(bitlmv(1,62 - a.parentMask-a.mask)-1)) = a.seq")
	public List<LongTreeBean> getDescendantByParentId(@Param("seq") Long seq, @Param("type") String type);
}