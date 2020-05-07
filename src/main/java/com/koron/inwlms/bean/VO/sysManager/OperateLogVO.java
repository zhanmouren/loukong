package com.koron.inwlms.bean.VO.sysManager;

import java.sql.Timestamp;

/**
 * 操作日志出参
 * @author lzy
 * @Date 2020.04.01
 */
public class OperateLogVO {

	//操作对象（模块）
	private String moduleName;
	//操作类型
	private String operateType;
	//操作时间
	private Timestamp operateTime;
	//操作人
	private String name;
	//操作结果
	private String result;
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public Timestamp getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
