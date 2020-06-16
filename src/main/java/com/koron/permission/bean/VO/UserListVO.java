package com.koron.permission.bean.VO;


/**
 * 用户登录列表实体VO
 * @createTime 2020/04/08
 * @author lzy
 *
 */
public class UserListVO {

	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 用户账号
	 */
	private String loginName;
	
	/**
	 * 用户名
	 */
	private String name;
	
	/**
	 * 登录密码
	 */
	private String password;
	
	/**
	 * 用户code
	 */
	private String code;
	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}

