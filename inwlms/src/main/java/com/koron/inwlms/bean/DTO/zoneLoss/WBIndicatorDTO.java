package com.koron.inwlms.bean.DTO.zoneLoss;

import java.util.List;

import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;

/**
 * 水平衡指标DTO
 * @author csh
 * @Date 2020/04/08
 */
public class WBIndicatorDTO {

	private List<String> codes;
	
	private Integer timeType;
	
	private Integer startTime;
	
	private Integer endTime;
	
	private List<ZoneInfo> zoneInfos;

	public List<String> getCodes() {
		return codes;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public List<ZoneInfo> getZoneInfos() {
		return zoneInfos;
	}

	public void setZoneInfos(List<ZoneInfo> zoneInfos) {
		this.zoneInfos = zoneInfos;
	}
	
}
