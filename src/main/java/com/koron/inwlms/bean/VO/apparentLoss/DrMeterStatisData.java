package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表统计数据
 * @author csh
 * @Date 2020/03/31
 */
public class DrMeterStatisData {

	/**
	 * 水表口径，范围类型的字符串，例如：DN15-40
	 */
	private String meterDn;
	
	/**
	 * 抄表数量
	 */
	private Integer readMeterNum;
	
	/**
	 * 抄表占比
	 */
	private Double readMeterRate;
	
	/**
	 * 月用水量
	 */
	private Double mFlow;
	
	/**
	 * 月用水量占比
	 */
	private Double	mFlowRate;

	public String getMeterDn() {
		return meterDn;
	}

	public Integer getReadMeterNum() {
		return readMeterNum;
	}

	public Double getReadMeterRate() {
		return readMeterRate;
	}

	public Double getmFlow() {
		return mFlow;
	}

	public Double getmFlowRate() {
		return mFlowRate;
	}

	public void setMeterDn(String meterDn) {
		this.meterDn = meterDn;
	}

	public void setReadMeterNum(Integer readMeterNum) {
		this.readMeterNum = readMeterNum;
	}

	public void setReadMeterRate(Double readMeterRate) {
		this.readMeterRate = readMeterRate;
	}

	public void setmFlow(Double mFlow) {
		this.mFlow = mFlow;
	}

	public void setmFlowRate(Double mFlowRate) {
		this.mFlowRate = mFlowRate;
	}
	
}
