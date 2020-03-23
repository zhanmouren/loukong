package com.koron.ebs.permission.mybatis;

import org.swan.bean.MessageBean;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.ebs.permission.EntityID;
import com.koron.ebs.permission.ResourceLoader;

public class SPICreator {
//	/**
//	 * 添加操作功能
//	 * @param factory 数据库连接
//	 * @param operation 操作
//	 * @param parent 上级操作,为NULL则为无上级
//	 * @param peer 同位操作，为NULL则为无同位操作
//	 * @return
//	 */
//	@TaskAnnotation("addOperation")
//	public MessageBean<String> addOperation(SessionFactory factory, EntID operation, String parent, String peer) {
//		SPIMapper spi = factory.getMapper(SPIMapper.class);
//		EntityID op = null;
//		if (parent != null) {
//			op = spi.getEntity(parent, ResourceLoader.ENTITY_OPERATION_INT);
//			if (op == null)
//				return MessageBean.create(1, "Operation :" + parent + " isn't exist.", String.class);
//		}
//		if (peer != null) {
//			op = spi.getEntity(peer, ResourceLoader.ENTITY_OPERATION_INT);
//			if (op == null)
//				return MessageBean.create(1, "Operation :" + peer + " isn't exist.", String.class);
//		}
//		op = spi.getEntity(operation.getId(), ResourceLoader.ENTITY_OPERATION_INT);
//		if (op != null)
//			return MessageBean.create(1, "Operation :" + operation.getId() + " exist.", String.class);
//		spi.addEntity(operation, ResourceLoader.ENTITY_OPERATION_INT);
//		if (parent != null)
//			spi.addRelation(operation.getId(), parent, ResourceLoader.RELATION_OPERATION_PARENT_INT);
//		if (peer != null)
//			spi.addRelation(operation.getId(), peer, ResourceLoader.RELATION_OPERATION_PEER_INT);
//		return MessageBean.create(0, "添加成功", String.class);
//	}
//	/**
//	 * 添加角色功能
//	 * @param role 角色
//	 * @return
//	 */
//	@TaskAnnotation("addRole")
//	public MessageBean<String> addRole(SessionFactory factory, EntID role) {
//		SPIMapper spi = factory.getMapper(SPIMapper.class);
//		EntityID op = null;
//		op = spi.getEntity(role.getId(), ResourceLoader.ENTITY_ROLE_INT);
//		if (op != null)
//			return MessageBean.create(1, "Role :" + role.getId() + " exist.", String.class);
//		spi.addEntity(role, ResourceLoader.ENTITY_ROLE_INT);
//		return MessageBean.create(0, "添加成功", String.class);
//	}
//	/**
//	 * 添加用户功能
//	 * @param user 角色
//	 * @return
//	 */
//	@TaskAnnotation("addUser")
//	public MessageBean<String> addUser(SessionFactory factory, EntID account) {
//		SPIMapper spi = factory.getMapper(SPIMapper.class);
//		EntityID op = null;
//		op = spi.getEntity(account.getId(), ResourceLoader.ENTITY_ACCOUNT_INT);
//		if (op != null)
//			return MessageBean.create(1, "Account :" + account.getId() + " exist.", String.class);
//		spi.addEntity(account, ResourceLoader.ENTITY_ACCOUNT_INT);
//		return MessageBean.create(0, "添加成功", String.class);
//	}
//	/**
//	 * 添加群组功能
//	 * @param group 群组
//	 * @return
//	 */
//	@TaskAnnotation("addGroup")
//	public MessageBean<String> addGroup(SessionFactory factory, EntID group) {
//		SPIMapper spi = factory.getMapper(SPIMapper.class);
//		EntityID op = null;
//		op = spi.getEntity(group.getId(), ResourceLoader.ENTITY_GROUP_INT);
//		if (op != null)
//			return MessageBean.create(1, "Group :" + group.getId() + " exist.", String.class);
//		spi.addEntity(group, ResourceLoader.ENTITY_GROUP_INT);
//		return MessageBean.create(0, "添加成功", String.class);
//	}
//	/**
//	 * 添加群组功能
//	 * @param group 群组
//	 * @return
//	 */
//	@TaskAnnotation("addRule")
//	public MessageBean<String> addRule(SessionFactory factory, EntID group) {
//		SPIMapper spi = factory.getMapper(SPIMapper.class);
//		EntityID op = null;
//		op = spi.getEntity(group.getId(), ResourceLoader.ENTITY_RULE_INT);
//		if (op != null)
//			return MessageBean.create(1, "Rule :" + group.getId() + " exist.", String.class);
//		spi.addEntity(group, ResourceLoader.ENTITY_RULE_INT);
//		return MessageBean.create(0, "添加成功", String.class);
//	}
//	@TaskAnnotation("addRelation")
//	public MessageBean<String> addRelation(SessionFactory factory, String source,String target,int type) {
//		SPIMapper spi = factory.getMapper(SPIMapper.class);
//		EntityID op = null;
//		op = spi.getEntity(source, (type >> 8) & 0xFF);
//		if (op == null)
//			return MessageBean.create(1, "Source :" + source + " isn't exist.", String.class);
//		op = spi.getEntity(target, type & 0xFF);
//		if (op == null)
//			return MessageBean.create(1, "Target :" + source + " isn't exist.", String.class);
//		spi.addRelation(source, target, type);
//		return MessageBean.create(0, "添加成功", String.class);
//	}
}
