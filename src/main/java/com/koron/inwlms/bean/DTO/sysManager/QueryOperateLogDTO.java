package com.koron.inwlms.bean.DTO.sysManager;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 查询操作日志入参
 * @author lzy
 * @Date 2020.04.01
 */
public class QueryOperateLogDTO extends BaseDTO{

	//操作人员
	private String name;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//操作类型
	private String operateType;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
}
