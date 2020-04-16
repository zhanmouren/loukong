package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 诊断报告现状水表VO
 * @author csh
 * @Data 2020/03/30
 */
public class DrCurrentMeterDataVO {

	/**
	 * 水表总数
	 */
	private Integer meterNum;
	
	/**
	 * 消防水表总数
	 */
	private Integer fsMeterNum;
	
	/**
	 * 口径范围
	 */
	private String dnRange;
	
	/**
	 * 水表信息
	 */
	private List<CurrentMeterData> meterData;

	public Integer getMeterNum() {
		return meterNum;
	}

	public Integer getFsMeterNum() {
		return fsMeterNum;
	}

	public String getDnRange() {
		return dnRange;
	}

	public void setMeterNum(Integer meterNum) {
		this.meterNum = meterNum;
	}

	public void setFsMeterNum(Integer fsMeterNum) {
		this.fsMeterNum = fsMeterNum;
	}

	public void setDnRange(String dnRange) {
		this.dnRange = dnRange;
	}

	public List<CurrentMeterData> getMeterData() {
		return meterData;
	}

	public void setMeterData(List<CurrentMeterData> meterData) {
		this.meterData = meterData;
	}

}
