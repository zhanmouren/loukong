package com.koron.inwlms.bean.DTO.leakageControl;

public class QueryTreeDTO {
	
	private int type;
	
	private String foreignKey;
	
	private String zoneIndex;
	
	

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
