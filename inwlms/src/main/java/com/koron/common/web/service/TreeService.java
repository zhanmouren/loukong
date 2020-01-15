package com.koron.common.web.service;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.mapper.TreeMapper;

/**<pre>
 * 树形结构：
 * 增加节点使用 {@link #add(TreeMapper, LongTreeBean, LongTreeBean)} 方法。
 * 		添加根节点的时候，父级参数用NULL.只能有一个根节点。
 * </pre>
 * @author swan
 *
 */
public class TreeService {
	/**
	 * 根据序号获取对应的层级数据
	 */
	@TaskAnnotation("getBySeq")
	public LongTreeBean getBySeq(SessionFactory factory,Long seq,int type){
		TreeMapper mapper = factory.getMapper(TreeMapper.class);
		LongTreeBean node = mapper.getBySeq(seq, type);
		return node;
	}
	/**
	 * <pre>
	 * 在某节点下增加一个节点.
	 * 如果扩展成功子节点里的seq会设置成对应的值并返回.
	 * 父节点如果为null，这加的节点为顶级节点
	 * 如果失败，则返回null.
	 * </pre>
	 * 
	 * @param mapper 数据库操作对象
	 * @param parent 父节点
	 * @param child 子节点
	 */
	@TaskAnnotation("addNode")
	public static LongTreeBean add(SessionFactory factory, LongTreeBean parent, LongTreeBean child) {
		TreeMapper mapper = factory.getMapper(TreeMapper.class);
		if (parent == null) {
			LongTreeBean bean = new LongTreeBean();
			bean.setParentMask(0).setMask(0).setChildMask(1);
			child.setSeq(1l << 61);
			mapper.add(bean, child);
			return child;
		}
		
		// 判断parent直接下级节点数和 mask相比较，如果节点满了则进行扩展
		int childrenNumber = getChildrenNumber(mapper, parent);
		if (childrenNumber >= (1l << parent.getChildMask()) - 2)// 如果子节点已满，则需要进行扩容操作
			if (!extend(mapper, parent)) {// 如果扩展失败则返回NULL
				return null;
			}
		long seq = occupySeq(mapper, parent, child);
		child.setSeq(seq);
		return child;
	}
	/**
	 * 获取某个节点
	 * @param factory
	 * @param type 类型
	 * @param foreignKey 外键
	 * @return
	 */
	@TaskAnnotation("getNode")
	public static LongTreeBean getNode(SessionFactory factory,int type,String foreignKey) {
		TreeMapper mapper = factory.getMapper(TreeMapper.class);
		return mapper.getBeanByForeignIdType(type, foreignKey);
	}
	/**
	 * 获取节点下所有子节点
	 * @param factory
	 * @param type 类型
	 * @param seq 顺序值
	 * @param mask 掩码位数
	 * @param parentMask 父级掩码位数
	 * 
	 * @return
	 */
	@TaskAnnotation("descendant")
	public static List<LongTreeBean> getDescendant(SessionFactory factory,int type,long seq,int mask,int parentMask){
		TreeMapper mapper = factory.getMapper(TreeMapper.class);
		return mapper.getDescendant(seq,type,mask,parentMask);
	}
	/**
	 * 获取节点路径.
	 * 从最上层节点到当前节点
	 * @param factory
	 * @param type 类型
	 * @param seq 顺序值
	 * @return
	 */
	@TaskAnnotation("getPath")
	public static List<LongTreeBean> getPath(SessionFactory factory,int type,long seq){
		TreeMapper mapper = factory.getMapper(TreeMapper.class);
		return mapper.getPath(type,seq);
	}
	/**
	 * <pre>
	 * 删除节点.
	 * 返回0为删除成功,1为存在子节点删除失败,2其余原因删除失败.
	 * </pre>
	 * 
	 * @param node 被删除的节点
	 * @return
	 */
	@TaskAnnotation("deleteNode")
	public static final int delete(SessionFactory factory,int type,long seq) {
		return forceDelete(factory,type,seq, false);
	}
	/**
	 * <pre>	
	 * 删除节点.
	 * 如果force为真，且其下有子节点，则子节点一起删除
	 * </pre>
	 * 
	 * @param node 被删除的节点
	 * @param force 是否强制删除节点
	 * @return
	 */
	@TaskAnnotation("forceDeleteNode")
	public static final int forceDelete(SessionFactory factory, int type,long seq, boolean force) {
		TreeMapper mapper = factory.getMapper(TreeMapper.class);
		LongTreeBean node = mapper.getBySeq(seq, type);
		List<LongTreeBean> list = getDescendant(factory,type,seq,node.getMask(),node.getParentMask());
		if(!force && list.size() > 1)
			return -1;
		for (LongTreeBean longTreeBean : list) {
			mapper.delete(longTreeBean.getType(),longTreeBean.getSeq());			
		}
		return list.size();
	}

	/**
	 * 分枝迁移
	 * 
	 * @param parent
	 * @param child
	 * @return
	 */
	public int move(LongTreeBean parent, LongTreeBean child) {
		// TODO
		return -1;
	}

	/**
	 * 对节点的下级进行扩容
	 * 
	 * @param mapper ADO操作对象
	 * @param parent 父节点
	 * @return
	 */
	public synchronized static boolean extend(TreeMapper mapper, LongTreeBean parent) {
		int extendMask = parent.getChildMask();
		extendMask = extendMask == 0 ? 2 : 1;// 如果原来掩码是0,则直接扩展到2,存储扩展
		long lower = parent.getSeq();
		long upper = getUpperSeq(parent);//parent.getSeq() + (1l << (62 - parent.getParentMask() - parent.getMask()));
		Integer mask = mapper.getMaxMask(lower, upper, parent.getType());
		if (mask == null)
			mask = 0;
		if (mask + extendMask >= 62)// 扩展的位不足，返回false
			return false;
		//父级子掩码扩展
		mapper.updateChildMask(extendMask, parent.getType(), parent.getSeq());
		parent.setChildMask(parent.getChildMask()+extendMask);
		// 下级的 Mask要改
		List<LongTreeBean> children = mapper.getRange(parent.getSeq()+1, getUpperSeq(parent),parent.getType());//获取所有的子节点
		for (LongTreeBean longTreeBean : children) {
			long seq = longTreeBean.getSeq();
			int offset = parent.getMask() + parent.getParentMask();
			offset = 62 - offset;
			seq = seq & ((1l << offset) -1); //取得子节点的值
			seq = seq >> extendMask; //子节点向右移
			seq = parent.getSeq() | seq; //加上父节点值
			longTreeBean.setSeq(seq);
			if(longTreeBean.getParentMask() == parent.getParentMask() + parent.getMask())
			{
				longTreeBean.setMask(longTreeBean.getMask() + extendMask);
			}else
				longTreeBean.setParentMask(longTreeBean.getParentMask() + extendMask);
			mapper.updateMask(longTreeBean);
		}
		return true;
	}

	/**
	 * 对父节点进行压缩
	 * 
	 * @param parent
	 * @return
	 */
	private synchronized static boolean shrink(LongTreeBean parent) {
		return true;
	}

	/**
	 * 获取当前节点直接下级节点数.
	 * 
	 * @param mapper 数据库操作对象
	 * @param parent 父节点
	 * @return
	 */
	private static int getChildrenNumber(TreeMapper mapper, LongTreeBean parent) {
		long lower = parent.getSeq();
		long upper = getUpperSeq(parent);//parent.getSeq() | (1l << (62 - parent.getParentMask() - parent.getMask()-parent.getChildMask()));
		int mask = 62 - parent.getParentMask() - parent.getMask() - parent.getChildMask();
		return mapper.getCount(lower+1, upper, mask, parent.getType());
	}

	/**
	 * 返回节点下可用的下级节点SEQ
	 * 
	 * @param parent
	 * @return
	 */
	private static synchronized long getAvailableSeq(TreeMapper mapper, LongTreeBean parent) {
		long lower = parent.getSeq();
		long upper = getUpperSeq(parent);
		//直接子节点的掩码
		int mask = 62 - parent.getParentMask() - parent.getMask() - parent.getChildMask();
		System.out.println(mask);
		long seq = mapper.getAvailable(lower, upper, mask, parent.getType())+1;
		System.out.println(seq);
		seq = parent.getSeq() | (seq << mask);
		System.out.println(seq);
		return seq;
	}

	private static synchronized long occupySeq(TreeMapper mapper, LongTreeBean parent, LongTreeBean child) {
		long seq = getAvailableSeq(mapper, parent);
		child.setSeq(seq);
		child.setChildMask(0).setMask(parent.getChildMask()).setParentMask(parent.getParentMask()+parent.getMask());
		mapper.add(parent, child);
		return seq;
	}
	/**
	 * 获取下个兄弟节点的SEQ
	 * @param bean 当前节点
	 * @return 下一节点的SEQ
	 */
	public static long getUpperSeq(LongTreeBean bean)
	{
		return bean.getSeq() + (1l << (62 - bean.getParentMask() - bean.getMask()));
	}
	/**
	 * 判断实际节点是否是几个节点的子节点
	 * @param actual 实际节点
	 * @param nodes 进行判断的几个节点
	 * @return
	 */
	public static boolean test(LongTreeBean actual,LongTreeBean[] nodes)
	{
		for (LongTreeBean longTreeBean : nodes) {
			if(actual.getSeq() > longTreeBean.getSeq() && actual.getSeq() < getUpperSeq(longTreeBean)-1)
			return true;
		}
		return false;
	}
}
