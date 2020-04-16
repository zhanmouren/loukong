package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告现状水表数据VO
 * @author csh
 * @Data 2020/03/30
 */
public class CurrentMeterData {

	/**
	 * 水表口径
	 */
	private String dnNo;
	
	/**
	 * 水表数量
	 */
	private Integer meterNum;
	
	/**
	 * 水表占比
	 */
	private Double meterRate;
	
	/**
	 * 水表总数
	 */
	private Integer meterTotalNum;
	
	/**
	 * 水表总数占比
	 */
	private Double meterTotalRate;


	public Integer getMeterNum() {
		return meterNum;
	}

	public Double getMeterRate() {
		return meterRate;
	}

	public Integer getMeterTotalNum() {
		return meterTotalNum;
	}

	public Double getMeterTotalRate() {
		return meterTotalRate;
	}


	public void setMeterNum(Integer meterNum) {
		this.meterNum = meterNum;
	}

	public void setMeterRate(Double meterRate) {
		this.meterRate = meterRate;
	}

	public void setMeterTotalNum(Integer meterTotalNum) {
		this.meterTotalNum = meterTotalNum;
	}

	public void setMeterTotalRate(Double meterTotalRate) {
		this.meterTotalRate = meterTotalRate;
	}

	public String getDnNo() {
		return dnNo;
	}

	public void setDnNo(String dnNo) {
		this.dnNo = dnNo;
	}
	
}
