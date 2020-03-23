package com.koron.common.bean;

public class UserBean implements IdentityBean {
	private java.lang.Integer id;
	/**
	 * 登录名
	 */
	private java.lang.String loginname;
	/**
	 * 密码（MD5）
	 */
	private java.lang.String password;
	/**
	 * 角色ID
	 */
	private String roleid;
	/**
	 * 角色名
	 */
	private String roleName;

	/**
	 * 设置
	 */
	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	/**
	 * 获取
	 */
	public java.lang.Integer getId() {
		return id;
	}

	/**
	 * 设置登录名
	 */
	public void setLoginname(java.lang.String loginname) {
		this.loginname = loginname;
	}

	/**
	 * 获取登录名
	 */
	public java.lang.String getLoginname() {
		return loginname;
	}

	/**
	 * 设置密码（MD5）
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	/**
	 * 获取密码（MD5）
	 */
	public java.lang.String getPassword() {
		return password;
	}


	/**
	 * @return 获取角色名
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param 设置角色名
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
}