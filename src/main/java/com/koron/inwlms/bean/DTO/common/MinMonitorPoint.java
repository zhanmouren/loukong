package com.koron.inwlms.bean.DTO.common;

import java.math.BigInteger;
import java.sql.Date;

/**
 * 
 * @author 刘刚
 *
 */
public class MinMonitorPoint {
	
	private BigInteger id;
	/**
	 * 测站编码
	 */
	private String stationCode;
	/**
	 * 统计分析时间
	 */
	private Date analysisDate;
	/**
	 * 指标维表编码
	 */
	private String code;
	/**
	 * 统计值
	 */
	private BigInteger analysisValue;
	/**
	 * 确认值
	 */
	private BigInteger value;
	/**
	 * 统计方式 0统计、1估算
	 */
	private Integer method;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public Date getAnalysisDate() {
		return analysisDate;
	}

	public void setAnalysisDate(Date analysisDate) {
		this.analysisDate = analysisDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigInteger getAnalysisValue() {
		return analysisValue;
	}

	public void setAnalysisValue(BigInteger analysisValue) {
		this.analysisValue = analysisValue;
	}

	public BigInteger getValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}
	
	
	

}
