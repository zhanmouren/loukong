package com.koron.inwlms.bean.DTO.intellectPartition;

/**
 * 
 * @author 刘刚
 *
 */
public class AutomaticPartitionDTO {
	/**
	 * 分区类型（1实际分区，0虚拟分区）
	 */
	private Integer zoneType;
	/**
	 * 分区分级
	 */
	private Integer zoneGrade;
	/**
	 * 分区编码
	 */
	private String zoneCode;
	/**
	 * 最大分区数量
	 */
	private Integer maxZone;
	/**
	 * 最小分区数量
	 */
	private Integer minZone;
	/**
	 * 地理图层信息
	 */
	private String ambientLayer;
	/**
	 * 流量计图层信息
	 */
	private String flowLayer;
	/**
	 * 虚拟分区分级
	 */
	private String othertype;
	
	
	public String getOthertype() {
		return othertype;
	}
	public void setOthertype(String othertype) {
		this.othertype = othertype;
	}
	public Integer getZoneType() {
		return zoneType;
	}
	public void setZoneType(Integer zoneType) {
		this.zoneType = zoneType;
	}
	public Integer getZoneGrade() {
		return zoneGrade;
	}
	public void setZoneGrade(Integer zoneGrade) {
		this.zoneGrade = zoneGrade;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public Integer getMaxZone() {
		return maxZone;
	}
	public void setMaxZone(Integer maxZone) {
		this.maxZone = maxZone;
	}
	public Integer getMinZone() {
		return minZone;
	}
	public void setMinZone(Integer minZone) {
		this.minZone = minZone;
	}
	public String getAmbientLayer() {
		return ambientLayer;
	}
	public void setAmbientLayer(String ambientLayer) {
		this.ambientLayer = ambientLayer;
	}
	public String getFlowLayer() {
		return flowLayer;
	}
	public void setFlowLayer(String flowLayer) {
		this.flowLayer = flowLayer;
	}
	
	
	

}
