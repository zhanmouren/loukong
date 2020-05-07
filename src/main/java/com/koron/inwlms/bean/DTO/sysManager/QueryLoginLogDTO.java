package com.koron.inwlms.bean.DTO.sysManager;

import com.koron.inwlms.bean.DTO.common.BaseDTO;


/**
 * 查询登录日志入参
 * @author lzy
 * @Date 2020.03.30
 */
public class QueryLoginLogDTO extends BaseDTO{
	//用户名称
	private String name;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//操作类型
	private String type;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
