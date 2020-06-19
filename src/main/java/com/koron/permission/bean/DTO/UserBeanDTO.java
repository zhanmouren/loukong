package com.koron.permission.bean.DTO;

/**
 * date: 2020/05/28
 * @author 小詹
 * description:租户DTO
 *
 */
public class UserBeanDTO {
	//用户名
	private String userName;
	//用户编码
	private String userCode;
	public String getUserName() {
		return userName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}	
}
