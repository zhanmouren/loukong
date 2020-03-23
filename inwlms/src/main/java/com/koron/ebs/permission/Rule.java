package com.koron.ebs.permission;

import java.util.Map;

import org.koron.ebs.util.Propertible;

/**
 * 判断规则
 * 
 * @author 方志文
 * 
 */
public interface Rule extends Propertible, EntityID {
	/**
	 * 检测
	 * 
	 * @param user 用户
	 * @param role 角色
	 * @param operation 操作
	 * @param billBean 单据数据
	 * @return
	 */
	public boolean inspect(User user, Role role, Operation operation, Map<String,Object> data);
}
