package com.koron.inwlms.bean.DTO.leakageControl;

/**
 * 
 * @author 刘刚
 *
 */
public class ZoneDayData {
	
	private Double allFlow;
	
	private Double minNigFlow;
	
	private String zoneCode;
	
	private String zoneIndex;
	
	private String zoneType;
	
	
	

	public String getZoneType() {
		return zoneType;
	}

	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

	public String getZoneIndex() {
		return zoneIndex;
	}

	public void setZoneIndex(String zoneIndex) {
		this.zoneIndex = zoneIndex;
	}

	public Double getAllFlow() {
		return allFlow;
	}

	public void setAllFlow(Double allFlow) {
		this.allFlow = allFlow;
	}

	public Double getMinNigFlow() {
		return minNigFlow;
	}

	public void setMinNigFlow(Double minNigFlow) {
		this.minNigFlow = minNigFlow;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	
	

}
