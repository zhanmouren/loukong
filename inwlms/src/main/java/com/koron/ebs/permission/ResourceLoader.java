package com.koron.ebs.permission;

import java.util.List;

public interface ResourceLoader {
	/**
	 * 账号
	 */
	public static final int ENTITY_ACCOUNT_INT = 1;
	/**
	 * 组
	 */
	public static final int ENTITY_GROUP_INT = 2;
	/**
	 * 角色
	 */
	public static final int ENTITY_ROLE_INT = 4;
	/**
	 * 操作
	 */
	public static final int ENTITY_OPERATION_INT = 8;
	/**
	 * 判断规则
	 */
	public static final int ENTITY_RULE_INT = 16;
	
	public static final int ENTITY_APP_INT= 1 << 5;
	/**
	 * 账户群组关系
	 */
	public static final int RELATION_ACCOUNT_GROUP_INT = (ENTITY_ACCOUNT_INT << 8) | ENTITY_GROUP_INT;
	/**
	 * 账号角色关系
	 */
	public static final int RELATION_ACCOUNT_ROLE_INT = (ENTITY_ACCOUNT_INT << 8) | ENTITY_ROLE_INT;
	/**
	 * 账号应用关系
	 */
	public static final int RELATION_ACCOUNT_APP_INT = (ENTITY_APP_INT << 8) | ENTITY_OPERATION_INT;
	/**
	 * 群组角色关系
	 */
	public static final int RELATION_GROUP_ROLE_INT = (ENTITY_GROUP_INT << 8) | ENTITY_ROLE_INT;
	/**
	 * 角色操作关系
	 */
	public static final int RELATION_ROLE_OPERATION_INT = (ENTITY_ROLE_INT << 8) | ENTITY_OPERATION_INT;
	/**
	 * 操作-操作父子关系
	 */
	public static final int RELATION_OPERATION_PARENT_INT = (ENTITY_OPERATION_INT << 8) | ENTITY_OPERATION_INT;
	/**
	 * 操作-拒绝规则
	 */
	public static final int RELATION_OPERATION_DENY_INT = (ENTITY_OPERATION_INT << 8) | ENTITY_RULE_INT;
	/**
	 * 操作-接受规则
	 */
	public static final int RELATION_OPERATION_ACCEPT_INT = (1 << 16) | (ENTITY_OPERATION_INT << 8) | ENTITY_RULE_INT;
	/**
	 * 操作-同位操作
	 */
	public static final int RELATION_OPERATION_PEER_INT = (1 << 16) | (ENTITY_OPERATION_INT << 8) | ENTITY_OPERATION_INT;

	/**<pre>
	 * 是否能加载此类型数据
	 * </pre>
	 * @param id 标识
	 * @param t 返回类型对应的类
	 * @param type 类型,对应ResourceLoader下的ENTITY开头的常量
	 * @return 是否接受该类型
	 * @see ResourceLoader#ENTITY_ACCOUNT_INT
	 * @see ResourceLoader#ENTITY_GROUP_INT
	 * @see ResourceLoader#ENTITY_ROLE_INT
	 * @see ResourceLoader#ENTITY_OPERATION_INT
	 * @see ResourceLoader#ENTITY_RULE_INT
	 */
	public <T> boolean accept(String id, Class<T> t, int type);

	/**
	 * 根据ID获取相对应的对象
	 * 
	 * @param id 标识
	 * @param type 类型,对应ResourceLoader下的ENTITY开头的常量
	 * @return
	 */
	public <T> T get(String id, Class<T> t, int type);

	/**
	 * 加载器是否能加载此类关系
	 * 
	 * @param id 标识
	 * @param type 类型,对应ResourceLoader下的RELATION开头的常量
	 * @return
	 * @see ResourceLoader#RELATION_ACCOUNT_GROUP_INT
	 * @see ResourceLoader#RELATION_ACCOUNT_ROLE_INT
	 * @see ResourceLoader#RELATION_GROUP_ROLE_INT
	 * @see ResourceLoader#RELATION_OPERATION_ACCEPT_INT
	 * @see ResourceLoader#RELATION_OPERATION_DENY_INT
	 * @see ResourceLoader#RELATION_OPERATION_PARENT_INT
	 * @see ResourceLoader#RELATION_OPERATION_PEER_INT
	 * @see ResourceLoader#RELATION_ROLE_OPERATION_INT
	 */
	public boolean acceptRelation(String id, int type);

	/**
	 * 加载此类关联的对象
	 * @param id 标识
	 * @param type 类型,对应ResourceLoader下的RELATION开头的常量
	 * @return 关联对象列表
	 * @see ResourceLoader#RELATION_ACCOUNT_GROUP_INT
	 * @see ResourceLoader#RELATION_ACCOUNT_ROLE_INT
	 * @see ResourceLoader#RELATION_GROUP_ROLE_INT
	 * @see ResourceLoader#RELATION_OPERATION_ACCEPT_INT
	 * @see ResourceLoader#RELATION_OPERATION_DENY_INT
	 * @see ResourceLoader#RELATION_OPERATION_PARENT_INT
	 * @see ResourceLoader#RELATION_OPERATION_PEER_INT
	 * @see ResourceLoader#RELATION_ROLE_OPERATION_INT
	 */
	public List<EntityID> getRelation(String id, int type);
	/**
	 * 根据加载器返回的类型
	 * @param type 类型,对应ResourceLoader下的ENTITY开头的常量
	 * @return 对应的类
	 */
	public Class<? extends EntityID> getTypeClass(int type);
}
