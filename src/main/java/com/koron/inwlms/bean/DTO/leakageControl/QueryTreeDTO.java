package com.koron.inwlms.bean.DTO.leakageControl;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

public class QueryTreeDTO extends BaseDTO {
	/**
	 * 2分区,3监测点
	 */
	private int type;
	/**
	 * 编码
	 */
	private String foreignKey;
	/**
	 * 对象类型
	 */
	private String zoneIndex;
	/**
	 * 监测点树所选择的类型
	 */
	private Integer pointType;
	
	private String name;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPointType() {
		return pointType;
	}

	public void setPointType(Integer pointType) {
		this.pointType = pointType;
	}

	public String getZoneIndex() {
		return zoneIndex;
	}

	public void setZoneIndex(String zoneIndex) {
		this.zoneIndex = zoneIndex;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
	
	

}
