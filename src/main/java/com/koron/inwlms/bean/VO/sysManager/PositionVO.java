package com.koron.inwlms.bean.VO.sysManager;

import java.sql.Timestamp;

public class PositionVO {
	
	//职位id
	private int id;
	//职位code
	private String code;
	//职位状态
	private short status;
	//职位权重
	private int weight;
	//职位名称
	private String name;
	//职位等级
	private int level;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	

}
