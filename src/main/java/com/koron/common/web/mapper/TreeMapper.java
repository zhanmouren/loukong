package com.koron.common.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.koron.inwlms.bean.VO.sysManager.TreeDeptVO;
import com.koron.inwlms.bean.VO.sysManager.TreeMenuVO;

public interface TreeMapper {
    /**
     * 根据序号获取对应的层级数据
     *
     * @param seq  序号
     * @param type 类型
     * @return 对应的节点
     */
    @Select("select * from \"SM_treeDet\" where type = #{type} and seq = #{seq}")
    public LongTreeBean getBySeq(@Param("seq") long seq, @Param("type") int type);
    /**
     * 根据外键获取对应的层级数据
     *
     * @param seq  序号
     * @param type 类型
     * @return 对应的节点
     */
    @Select("select * from \"SM_treeDet\" where type = #{type} and foreignkey = #{foreignkey}")
    public LongTreeBean getByFor(@Param("foreignkey") String foreignkey, @Param("type") int type);

    /**
     * 根据ID获取对应的层级数据
     *
     * @param id ID
     * @return 对应的节点
     */
    @Select("select * from \"SM_treeDet\" where id = #{id}")
    public LongTreeBean getById(int id);

    /**
     * 根据ID获取对应的层级数据
     *
     * @param id ID
     * @return 对应的节点
     */
    @Select("select * from \"SM_treeDet\" where foreignkey = #{foreignkey}")
    public LongTreeBean getByForeignkey(int foreignkey);

    /**
     * 获取节点的直接下级节点
     *
     * @param bean 节点
     * @return 节点集合
     */
    @Select("select * from \"SM_treeDet\" where (seq & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq} "
            + "and (seq & ((1::int8 << (62 - #{parentMask}-#{mask} - #{childMask}))-1)) = 0 and type = #{type}")
    List<LongTreeBean> getChildren(LongTreeBean bean);
    
    /**
     * 获取节点的直接下级节点(目录菜单)
     *
     * @param bean 节点
     * @return 节点集合
     */
    @Select("select \"SM_treeDet\".*,\"SM_moduleMenu\".code \"menuCode\",\"SM_moduleMenu\".id \"menuId\",\"SM_moduleMenu\".\"moduleNo\",\"SM_moduleMenu\".\"moduleName\",\"SM_moduleMenu\".\"linkAddress\" from \"SM_treeDet\" left join \"SM_moduleMenu\" on  \"SM_moduleMenu\".code=\"SM_treeDet\".foreignkey where (seq & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq} "
            + "and (seq & ((1::int8 << (62 - #{parentMask}-#{mask} - #{childMask}))-1)) = 0 and \"SM_treeDet\".type = #{type}")
    List<TreeMenuVO> getMenuChildren(LongTreeBean bean);

    /**
     * 根据用户Code获取节点的直接下级节点(目录菜单)
     *
     * @param bean 节点
     * @return 节点集合
     */
    @Select("select \"SM_treeDet\".*,\"SM_moduleMenu\".code \"menuCode\",\"SM_moduleMenu\".id \"menuId\",\"SM_moduleMenu\".\"moduleNo\",\"SM_moduleMenu\".\"moduleName\",\"SM_moduleMenu\".\"linkAddress\"\r\n" + 
    		"		 ,case when string_agg(DISTINCT (to_char(\"rolemenu\".op,'9')),',') is null then '' else string_agg(DISTINCT (to_char(\"rolemenu\".op,'9')),',') end as op\r\n" + 
    		"		 from \"SM_treeDet\"\r\n" + 
    		"		 left join \"SM_moduleMenu\" on \"SM_moduleMenu\".code=\"SM_treeDet\".foreignkey\r\n" + 
    		"		 left join \"SM_roleMenus\" \"rolemenu\" on \"rolemenu\".\"moduleCode\"=\"SM_moduleMenu\".code\r\n" + 
    		"		 left join \"SM_userRole\"  \"role\" on  \"role\".code=\"rolemenu\".\"roleCode\"\r\n" + 
    		"		 LEFT JOIN \"SM_userRoleRelation\" \"rela\" on  \"rela\".\"roleCode\"=\"role\".code\r\n" + 
    		"		 left join \"SM_user\" \"user\" on \"user\".code=\"rela\".\"userCode\"\r\n" + 
    		"		 where  (seq & ~((1::int8 << (62 - #{bean.parentMask}-#{bean.mask}))-1)) = #{bean.seq}  and  (seq & ((1::int8 << (62 - #{bean.parentMask}-#{bean.mask} - #{bean.childMask}))-1)) = 0 and \"SM_treeDet\".type = 1  and \"user\".code=#{userCode}\r\n" + 
    		"		 GROUP BY  \"SM_treeDet\".id,\"SM_treeDet\".childmask,\"SM_treeDet\".foreignkey,\"SM_treeDet\".mask,\"SM_treeDet\".parentmask,\r\n" + 
    		"		 \"SM_treeDet\".seq,\"SM_treeDet\".\"type\",\"SM_moduleMenu\".code ,\"SM_moduleMenu\".id ,\"SM_moduleMenu\".\"moduleNo\",\"SM_moduleMenu\".\"moduleName\",\"SM_moduleMenu\".\"linkAddress\"\r\n" 
    			)
    List<TreeMenuVO> queryChildOneMenu(@Param("bean") LongTreeBean bean,@Param("userCode") String userCode);

    /**
     * 获取节点之下所有节点
     *
     * @param bean 节点
     * @return 节点集合
     */
    @Select("select * from \"SM_treeDet\" where (seq & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq} and type = #{type} order by seq")
    public List<LongTreeBean> getDescendant(@Param("seq") long seq, @Param("type") int type, @Param("mask") int mask, @Param("parentMask") int parentMask);

    /**
     * 获取节点之下所有节点(菜单权限)
     *
     * @param bean 节点
     * @return 节点集合
     */
    @Select("select \"SM_treeDet\".*,\"SM_moduleMenu\".code \"menuCode\",\"SM_moduleMenu\".id \"menuId\",\"SM_moduleMenu\".\"moduleNo\",\"SM_moduleMenu\".\"moduleName\",\"SM_moduleMenu\".\"linkAddress\"\r\n" + 
    		"		  ,case when string_agg(DISTINCT(to_char(\"rolemenu\".op,'9')),',') is null then '' else string_agg(DISTINCT(to_char(\"rolemenu\".op,'9')),',') end as op\r\n" + 
    		"		 from \"SM_treeDet\"\r\n" + 
    		"		 left join \"SM_moduleMenu\" on \"SM_moduleMenu\".code=\"SM_treeDet\".foreignkey\r\n" + 
    		"		 left join \"SM_roleMenus\" \"rolemenu\" on \"rolemenu\".\"moduleCode\"=\"SM_moduleMenu\".code\r\n" + 
    		"		 left join \"SM_userRole\"  \"role\" on  \"role\".code=\"rolemenu\".\"roleCode\"\r\n" + 
    		"		 LEFT JOIN \"SM_userRoleRelation\" \"rela\" on  \"rela\".\"roleCode\"=\"role\".code\r\n" + 
    		"		 left join \"SM_user\" \"user\" on \"user\".code=\"rela\".\"userCode\"\r\n" + 
    		"		 where (seq & ~((1::int8 << (62 - #{bean.parentMask}-#{bean.mask}))-1)) = #{bean.seq} and type = #{bean.type}  and \"user\".code=#{userCode}  \r\n" + 
    		"		 GROUP BY  \"SM_treeDet\".id,\"SM_treeDet\".childmask,\"SM_treeDet\".foreignkey,\"SM_treeDet\".mask,\"SM_treeDet\".parentmask,\r\n" + 
    		"		 \"SM_treeDet\".seq,\"SM_treeDet\".\"type\",\"SM_moduleMenu\".code ,\"SM_moduleMenu\".id ,\"SM_moduleMenu\".\"moduleNo\",\"SM_moduleMenu\".\"moduleName\",\"SM_moduleMenu\".\"linkAddress\"\r\n" + 
    		"		  order by \"SM_treeDet\".seq")
    public List<TreeMenuVO> queryChildAllMenu(@Param("bean") LongTreeBean bean,@Param("userCode") String userCode);
    /**
     * 获取节点之下所有节点和节点名称(组织部门)
     *
     * @param bean 节点
     * @return 节点集合
     */
    @Select("select \"SM_treeDet\".*, \"SM_department\".id \"depId\", \"SM_department\".name \"depName\",\"SM_department\".code \"depCode\",\"SM_department\".status \"depstatus\" from \"SM_treeDet\"  left join \"SM_department\"  on \"SM_treeDet\".foreignkey=\"SM_department\".code where (\"SM_treeDet\".seq & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq} and \"SM_treeDet\".type = #{type} order by \"SM_treeDet\".seq")
    public List<TreeDeptVO> getDescendantName(@Param("seq") long seq, @Param("type") int type, @Param("mask") int mask, @Param("parentMask") int parentMask);

    /**
     * 获取节点之下所有节点和节点名称(树形目录)
     *
     * @param bean 节点
     * @return 节点集合
     */
    @Select("select \"SM_treeDet\".*,\"SM_moduleMenu\".code \"menuCode\",\"SM_moduleMenu\".id \"menuId\",\"SM_moduleMenu\".\"moduleNo\",\"SM_moduleMenu\".\"moduleName\",\"SM_moduleMenu\".\"linkAddress\" from \"SM_treeDet\" left join \"SM_moduleMenu\" on  \"SM_moduleMenu\".code=\"SM_treeDet\".foreignkey where (seq & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq} and \"SM_treeDet\".type = #{type} order by \"SM_treeDet\".seq")
    public List<TreeMenuVO> descendantMenu(@Param("seq") long seq, @Param("type") int type, @Param("mask") int mask, @Param("parentMask") int parentMask);

    /**
     * 获取节点的兄弟节点
     *
     * @param bean 节点
     * @return 节点集合
     */
	@Select("select * from \"SM_treeDet\" " + "where (seq & ~((1::int8 << (62 - #{bean.parentMask}))-1)) = (#{bean.seq} & ~((1::int8 << (62 - #{bean.parentMask}))-1))"
			+ " and (seq & ((1::int8 << (62 - #{bean.parentMask}+#{bean.mask}))-1)) = 0")
    public List<LongTreeBean> getSibling(@Param("bean") LongTreeBean bean);

    /**
     * 获取节点的路径，从最上一级到当前级
     *
     * @param bean 节点
     * @return 节点集合
     */
	@Select("select * from \"SM_treeDet\" where (#{seq} & ~((1::int8 << (62 - parentmask-mask))-1)) = seq and type = #{type} order by seq")
    public List<LongTreeBean> getPath(@Param("type") int type, @Param("seq") long seq);

    /**
     * 添加一个节点
     *
     * @param bean
     * @return
     */
	 @Insert("insert into \"SM_treeDet\"(seq,parentmask,mask,childmask,type,foreignkey) "
    + "values (#{node.seq},#{parent.parentMask}+#{parent.mask},#{parent.childMask}," + "0,#{node.type},#{node.foreignkey})")
    public Integer add(@Param("parent") LongTreeBean parent, @Param("node") LongTreeBean node);

    /**
     * 更新节点的父节点
     *
     * @param bean
     * @return
     */
	@Update("update \"SM_treeDet\" set parentmask = parentmask + #{sign} * #{offset} , seq = ((seq & #{mask}) ${shift} #{offset}) | #{parentSeq} where foreignkey = #{root} and seq > #{min} and seq < #{max}")
    public Integer moveNode(@Param("parentSeq") long parentSeq, @Param("mask") long mask, @Param("shift") String shift, @Param("sign") int sign,
                            @Param("offset") int offset, @Param("root") String root, @Param("min") long min, @Param("max") long max);

    /**
     * 删除一个节点
     *
     * @param bean
     * @return
     */
    @Delete("delete from \"SM_treeDet\" where seq = #{seq} and type = #{type}")
    public Integer delete(@Param("type") int type, @Param("seq") long seq);

    /**
     * 获取符合要求的节点的数量. 包含下限不包含上限
     *
     * @param lower 下限
     * @param upper 上限
     * @param mask  掩码位数(即最后mask位全为0)
     * @param type  类型
     * @return
     */
	@Select("select count(0) from \"SM_treeDet\" where type = #{type} and seq >= #{lower} and seq < #{upper} and seq & ((1::int8 << #{mask}) -1) = 0")
    public int getCount(@Param("lower") long lower, @Param("upper") long upper, @Param("mask") int mask, @Param("type") int type);

    /**
     * 扩展节点的子掩码
     *
     * @param extendMask 扩展的位数
     * @param type       类型
     * @param seq        seq
     * @return
     */
    @Update("update \"SM_treeDet\" set childmask = childmask + #{extendMask} where type = #{type} and seq = #{seq}")
    public int updateChildMask(@Param("extendMask") int extendMask, @Param("type") int type, @Param("seq") long seq);

    @Update("update \"SM_treeDet\" set childmask = #{childmask} where foreignkey = #{foreignkey}")
    public int setChildMask(@Param("childmask") int childmask, @Param("foreignkey") String foreignkey);

    @Select("update \"SM_treeDet\" set mask = #{bean.mask},parentmask = #{bean.parentMask},seq = #{bean.seq} where id = #{bean.id}")
    public Integer updateMask(@Param("bean") LongTreeBean bean);

    /**
     * 获取已使用位数
     *
     * @param lower 父节点SEQ
     * @param upper 下一个节点SEQ
     * @param type  类型
     * @return
     */
    @Select("select max(parentmask + mask + childmask) from \"SM_treeDet\" where type = #{type} and seq between #{lower} and #{upper}")
    public Integer getMaxMask(@Param("lower") long lower, @Param("upper") long upper, @Param("type") int type);

    /**
     * @return
     */
	@Select("select COALESCE(max(key),0) from (select row_number() over() as key,a.seq >> (62-parentmask-mask)  & (1::int8 << mask )-1 as value from \"SM_treeDet\" a where a.type = #{type} and seq > #{lower} and seq < #{upper} and (seq & ((1::int8 << #{mask}) -1)) = 0) as t where t.key = t.value")
    public int getAvailable(@Param("lower") long lower, @Param("upper") long upper, @Param("mask") int mask, @Param("type") int type);

    /**
     * 获取一定范围的数据. 不包含上下限
     *
     * @param lower 下限
     * @param upper 上限
     * @param type
     * @return
     */
    @Select("select id,seq,parentmask,mask,childmask,type,foreignkey from \"SM_treeDet\" where type = #{type} and seq > #{lower} and seq < #{upper} order by seq asc")
    public List<LongTreeBean> getRange(@Param("lower") long lower, @Param("upper") long upper, @Param("type") int type);

    @Select("select * from \"SM_treeDet\" where type = #{type} and foreignkey = #{foreign}")
    public LongTreeBean getBeanByForeignIdType(@Param("type") int type, @Param("foreign") String foreignKey);

	@Select("select * from \"SM_treeDet\" where (#{seq} & ~((1::int8 << (62 - parentmask - mask))-1)) = seq and type = #{type} order by seq desc limit 1,1")
    public LongTreeBean getParant(LongTreeBean bean);

	@Select("select \"SM_treeDet\".seq,\"SM_treeDet\".parentmask from \"SM_treeDet\" " + "inner join (" + "	select \"SM_treeDet\".* from \"SM_treeDet\" "
			+ "where type = #{type} and seq =#{seq}) a on (\"SM_treeDet\".seq & ~((1::int8 << (62 - a.parentMask-a.mask))-1)) = a.seq")
    public List<LongTreeBean> getDescendantByParentId(@Param("seq") Long seq, @Param("type") String type);
}