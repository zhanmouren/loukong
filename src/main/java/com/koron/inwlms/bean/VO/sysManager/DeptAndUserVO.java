package com.koron.inwlms.bean.VO.sysManager;

public class DeptAndUserVO {
	//userCode;
	private String userCode;
	 //部门Id
	private Integer depId;
	//部门名称
	private String depName;
	//部门编码
	private String depCode;
	//部门状态
	private Integer depstatus;
	//用户Id
	  private Integer userId;
	  //职员名称
	  private String name;
	  //职员登录名称
	  private String loginName;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getDepCode() {
		return depCode;
	}
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}
	public Integer getDepstatus() {
		return depstatus;
	}
	public void setDepstatus(Integer depstatus) {
		this.depstatus = depstatus;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
