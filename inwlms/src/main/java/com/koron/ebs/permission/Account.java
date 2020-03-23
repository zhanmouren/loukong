package com.koron.ebs.permission;

import java.util.List;
/**
 * 登录的账号
 * @author 方志文
 *
 */
public interface Account extends User{
	/**
	 * 所属的群组
	 * @return 
	 */
	public List<Group> getGroup();
}