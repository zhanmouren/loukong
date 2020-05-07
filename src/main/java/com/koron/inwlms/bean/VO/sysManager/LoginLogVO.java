package com.koron.inwlms.bean.VO.sysManager;

/**
 * 登录日志出参
 * @Author lzy
 * @Date 2020.03.30
 */

public class LoginLogVO {
	//登录日志id
	private Integer id;
	//用户名
	private String name;
	//职位
	private Integer position;
	//登录结果
	private String result;
	//登录时间
	private String loginTime;
	//登录错误日志
	private String errorLog;
	//操作类型
	private String type;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
