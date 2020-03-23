package com.koron.ebs.permission;

import java.util.List;
/**
 * 用户
 * @author 方志文
 *
 */
public interface User extends EntityID{
	/**
	 * 获取用户的所有角色
	 * @return
	 */
	public List<Role> getRole();
}
