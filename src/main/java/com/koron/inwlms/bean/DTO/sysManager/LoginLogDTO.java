package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

/**
 * 登录日志bean
* @Author lzy
* @Date 2020.03.30
*/


public class LoginLogDTO {
	//登录日志id
	private Integer id;
	//登录用户的code
	private String loginUserCode;
	//登录时间
	private String loginTime;
	//登录IP
	private String loginIp;
	//登录结果
	private String result;
	//登录错误日志
	private String errorLog;
	//登录错误日志
	private String type;
	//创建人
	private String createBy;
	//创建时间
	private Timestamp createTime;
	//修改人
	private String updateBy;
	//修改时间
	private Timestamp updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginUserCode() {
		return loginUserCode;
	}
	public void setLoginUserCode(String loginUserCode) {
		this.loginUserCode = loginUserCode;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
