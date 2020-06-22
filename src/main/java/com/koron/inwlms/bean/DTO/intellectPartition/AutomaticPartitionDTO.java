package com.koron.inwlms.bean.DTO.intellectPartition;

import java.util.List;

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
	private List<String> ambientLayerList;
	/**
	 * 流量计图层信息
	 */
	private List<String> flowLayerList;
	/**
	 * 虚拟分区分级
	 */
	private String othertype;
	
	
	public List<String> getAmbientLayerList() {
		return ambientLayerList;
	}
	public void setAmbientLayerList(List<String> ambientLayerList) {
		this.ambientLayerList = ambientLayerList;
	}
	public List<String> getFlowLayerList() {
		return flowLayerList;
	}
	public void setFlowLayerList(List<String> flowLayerList) {
		this.flowLayerList = flowLayerList;
	}
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
	
	
	

}
