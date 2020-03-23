package com.koron.ebs.permission;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * <pre>
 * 权限模块判断规则 
 * 1、优先判断Deny规则。任一角色被Deny,均会导致无权限。 
 * 2、ACCEPT规则
 * </pre>
 * 
 * @author 方志文
 */
public class Permission {
	static Logger logger = Logger.getLogger(Permission.class);
	/**
	 * 用来判断权限是否登录，及Account是否存在
	 */
	public static final String LOGIN_RANGE = "_login"; 
	
	/**
	 * 加载器
	 */
	List<ResourceLoader> loaders = new ArrayList<ResourceLoader>();
	/**
	 * 账户
	 */
	private Map<String, Account> accounts = new TreeMap<>();
	/**
	 * 群组
	 */
	private Map<String, Group> groups = new TreeMap<>();
	/**
	 * 角色
	 */
	private Map<String, Role> roles = new TreeMap<>();
	/**
	 * 操作
	 */
	private Map<String, Operation> operations = new TreeMap<>();
	/**
	 * 规则
	 */
	private Map<String, Rule> rules = new TreeMap<>();
	
	/**
	 * 用来存储返回规则
	 */
	private Map<Long, PermissionEntity> map = new TreeMap<Long, PermissionEntity>();
	
	private static Permission intance = new Permission();
	
	public static Permission getInstance()
	{
		return intance;
	}
	/**
	 * 清除缓存
	 */
	public void clearCache()
	{
		accounts.clear();
		groups.clear();
		roles.clear();
		operations.clear();
		rules.clear();
	}
	/**
	 * 获取登录账号
	 * 
	 * @param id
	 * @return
	 */
	public Account getAccount(String id) {
		Account account = accounts.get(id);
		if (account != null)
			return account;
		account = loadAccount(id);
		if (account != null) {
			accounts.put(account.getId(), account);
			logger.debug("取到账户" + id + " " + account.getName());
		}
		return account;
	}

	private Account loadAccount(String id) {
		Account acc = null;
		for (ResourceLoader loader : loaders) {
			if (loader.accept(id, Account.class, ResourceLoader.ENTITY_ACCOUNT_INT)) {
				acc = loader.get(id, Account.class, ResourceLoader.ENTITY_ACCOUNT_INT);
				if (acc != null)
					break;
			}
		}
		return acc;
	}
	/**
	 * 获取群组
	 * @param id
	 * @return
	 */
	public Group getGroup(String id) {
		Group group = groups.get(id);
		if (group != null)
			return group;
		group = loadGroup(id);
		if (group != null) {
			groups.put(group.getId(), group);
			logger.debug("取到群组" + id + " " + group.getName());
		}
		return group;
	}

	private Group loadGroup(String id) {
		Group group = null;
		for (ResourceLoader loader : loaders) {
			if (loader.accept(id, Group.class, ResourceLoader.ENTITY_GROUP_INT)) {
				group = loader.get(id, Group.class, ResourceLoader.ENTITY_GROUP_INT);
				if (group != null)
					break;
			}
		}
		return group;
	}
	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	public Role getRole(String id) {
		Role role = roles.get(id);
		if (role != null)
			return role;
		role = loadRole(id);
		if (role != null) {
			roles.put(role.getId(), role);
			logger.debug("取到角色" + id + " " + role.getName());
		}
		return role;
	}

	private Role loadRole(String id) {
		Role role = null;
		for (ResourceLoader loader : loaders) {
			if (loader.accept(id, Role.class, ResourceLoader.ENTITY_ROLE_INT)) {
				role = loader.get(id, Role.class, ResourceLoader.ENTITY_ROLE_INT);
				if (role != null)
					break;
			}
		}
		return role;
	}
	/**
	 * 获取操作
	 * @param id
	 * @return
	 */
	public Operation getOperation(String id) {
		Operation operation = operations.get(id);
		if (operation != null)
			return operation;
		operation = loadOperation(id);
		if (operation != null) {
			operations.put(operation.getId(), operation);

			logger.debug("取到操作" + id + " " + operation.getName());
		}
		return operation;
	}

	private Operation loadOperation(String id) {
		Operation operation = null;
		for (ResourceLoader loader : loaders) {
			if (loader.accept(id, Operation.class, ResourceLoader.ENTITY_OPERATION_INT)) {
				operation = loader.get(id, Operation.class, ResourceLoader.ENTITY_OPERATION_INT);
				if (operation != null)
					break;
			}
		}
		return operation;
	}
	/**
	 * 获取规则
	 * @param id
	 * @return
	 */
	public Rule getRule(String id) {
		Rule rule = rules.get(id);
		if (rule != null)
			return rule;
		rule = loadRule(id);
		if (rule != null) {
			rules.put(rule.getId(), rule);
			logger.debug("取到规则" + id + " " + rule.getName());
		}
		return rule;
	}

	private Rule loadRule(String id) {
		Rule rule = null;
		for (ResourceLoader loader : loaders) {
			if (loader.accept(id, Rule.class, ResourceLoader.ENTITY_RULE_INT)) {
				rule = loader.get(id, Rule.class, ResourceLoader.ENTITY_RULE_INT);
				if (rule != null)
					break;
			}
		}
		return rule;
	}

	/**
	 * 权限判断功能
	 * 
	 * @param user 用户
	 * @param operation 操作
	 * @param billBean 单据
	 * @return 是否有权限
	 */
	public boolean test(Account account, Operation operation, Map<String, Object> data) {
		Random r = new Random();
		Long token = null;
		do {
			token = r.nextLong();
		} while (map.containsKey(token));
		map.put(token, new PermissionEntity());
		map.get(token).setAccount(account);
		boolean testType = false;
	/*	if (test(testType, account, operation, token, data))
			return false;*/
		List<Group> groups = account.getGroup();
		if (groups != null && groups.size() > 0) {
			for (Group group : groups) {
				if (test(testType, group, operation, token, data)) {
					map.get(token).setGroup(group);
					return false;
				}
			}
		}
		testType = true;
		if (test(testType, account, operation, token, data))
			return true;
		groups = account.getGroup();
		if (groups != null && groups.size() > 0) {
			for (Group group : groups) {
				if (test(testType, group, operation, token, data)) {
					map.get(token).setGroup(group);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * 进行权限的单方面规则通过检测,根据isAccept来进行接受（拒绝）判断
	 * true 通过
	 * false 未通过
	 * </pre>
	 * @param isAccept 进行接受规则或拒绝规则判断
	 * @param user 用户
	 * @param token 用来记录最后判断的返回值
	 * @param operation 操作
	 * @param data 单据
	 * @return 是否有权限进行操作，返NULL即为不进行判断
	 */
	private boolean test(boolean isAccept, User user, Operation operation, Long token, Map<String, Object> data) {
		List<Role> roles = user.getRole();
		if (roles == null || roles.size() == 0) {
			return false;// 用户无角色，则未通过检测
		}
		for (Role role : roles) {
			map.get(token).setRole(role);
			map.get(token).setOperation(operation);
			// 找出同位操作
			Operation op = operation;
			Operation tmp = null;
			do {
				tmp = operation.getPeerOperation();
				if (tmp != null)
					op = tmp;
			} while (tmp != null);

			List<Operation> operations = role.getOperation();
			if (operations == null || operations.size() == 0) {
				
				continue;//				return false;// 如果角色没操作则未通过检测
			}
			if (operations.indexOf(op) == -1)continue;
//				return false;// 如果找不到操作则未通过检测
			// 上级有权限则进行本级判断
			Rule rule = (isAccept ? op.getAccept() : op.getDeny());
			map.get(token).setRule(rule);
			if(rule == null)continue;
//				return false;
			boolean result = rule.inspect(user, role, op, data);
			if(result) return result;
		}
		return false;
	}
	/**
	 * 注册加载器
	 * 
	 * @param loader
	 */
	public void registeLoader(ResourceLoader loader) {
		loaders.add(loader);
	}
}