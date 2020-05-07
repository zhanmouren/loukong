package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 查询标签入参
 * @author lzy
 * @Date 2020.04.17
 */
public class QueryLabelDTO extends BaseDTO {
	
	//标签名（简体中文、繁体中文或英文）
	private String name;
	//编码
	private String code;
	//标签类型
	private String type;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

	
}
