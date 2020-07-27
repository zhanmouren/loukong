package com.koron.inwlms.bean.DTO.report.waterBalanceReport;

import java.util.List;

/**
 *   水司及一级分区产销差率同比报表  DTO
 * @author xiaozhan
 *
 */
public class WB1BalanceDTO {

	//开始时间
	private Integer startTime;
	//结束时间
	private Integer endTime; 
	/**
	 * 时间类型 2-日，3-月，4-年
	 */
	private Integer timeType;
	/*指标编码*/
	private List<String> codes;
	/*地区编码*/
	private List<String> zoneCodes;
	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	public Integer getEndTime() {
		return endTime;
	}
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
	public Integer getTimeType() {
		return timeType;
	}
	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}
	public List<String> getCodes() {
		return codes;
	}
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
	public List<String> getZoneCodes() {
		return zoneCodes;
	}
	public void setZoneCodes(List<String> zoneCodes) {
		this.zoneCodes = zoneCodes;
	}
	
}
