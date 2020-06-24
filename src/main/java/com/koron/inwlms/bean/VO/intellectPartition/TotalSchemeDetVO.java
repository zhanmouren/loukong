package com.koron.inwlms.bean.VO.intellectPartition;

import java.util.Date;
import java.util.List;

import com.koron.inwlms.bean.DTO.intellectPartition.LayerData;

public class TotalSchemeDetVO {
	private Integer id;
	/**
	 * 方案编码
	 */
	private String code;
	/**
	 * 分区类型
	 */
	private Integer zoneType;
	/**
	 * 最大分区数量
	 */
	private Integer maxZone;
	/**
	 * 最小分区数量
	 */
	private Integer minZone;
	/**
	 * 地理环境图层
	 */
	private List<LayerData> ambientLayer;
	/**
	 * 流量计图层
	 */
	private List<LayerData> flowLayer;
	 /**
	  * 分区分级
	  */
	private Integer zoneGrade;
	/**
	 * 分区编码
	 */
	private String zoneCode;
	/**
	 * 方案状态，0暂存，1保存
	 */
	private Integer state;

	private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getZoneType() {
		return zoneType;
	}

	public void setZoneType(Integer zoneType) {
		this.zoneType = zoneType;
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

	public List<LayerData> getAmbientLayer() {
		return ambientLayer;
	}

	public void setAmbientLayer(List<LayerData> ambientLayer) {
		this.ambientLayer = ambientLayer;
	}

	public List<LayerData> getFlowLayer() {
		return flowLayer;
	}

	public void setFlowLayer(List<LayerData> flowLayer) {
		this.flowLayer = flowLayer;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
