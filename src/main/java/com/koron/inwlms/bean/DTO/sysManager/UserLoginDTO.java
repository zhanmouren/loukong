package com.koron.inwlms.bean.DTO.sysManager;


/**
 * 用户登录DTO
 * @createTime 2020.04.07
 * @author lzy
 *
 */
public class UserLoginDTO {

	//登录账号
	private String loginName;
	//登录密码
	private String password;
	//登录日志用户code
	private String loginUserCode;
	//登录用户code
	private String code;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLoginUserCode() {
		return loginUserCode;
	}
	public void setLoginUserCode(String loginUserCode) {
		this.loginUserCode = loginUserCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
