package com.koron.inwlms.bean.DTO.intellectPartition;
/**
 * 
 * @author 刘刚
 *
 */
public class TotalSchemeDetDTO {
	/**
	 * 方案汇总编码
	 */
	private String code;
	
	private Integer id;
	/**
	 * 分区编码
	 */
	private String zoneCode;
	
	private String StartTime;
	
	private String endTime;
	/**
	 * 分区分级
	 */
	private Integer zoneGrade;
	/**
	 * 分区类型（实际分区1或者虚拟分区0）
	 */
	private Integer zoneType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getZoneGrade() {
		return zoneGrade;
	}

	public void setZoneGrade(Integer zoneGrade) {
		this.zoneGrade = zoneGrade;
	}

	public Integer getZoneType() {
		return zoneType;
	}

	public void setZoneType(Integer zoneType) {
		this.zoneType = zoneType;
	}
	
	

}
