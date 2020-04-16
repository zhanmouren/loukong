package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 大口径水表处理数据
 * @author csh
 * @Date 2020/03/31
 */
public class DrBigDnDealData {

	/**
	 * 大口径水表数量
	 */
	private Integer bigDnMeterNum;
	
	/**
	 * 大口径水表占比
	 */
	private Double bigDnMeterRate;
	
	/**
	 * 大口径抄表水量占比，所选时间的最新一个月
	 */
	private Double meterReadRate;

	public Integer getBigDnMeterNum() {
		return bigDnMeterNum;
	}

	public Double getBigDnMeterRate() {
		return bigDnMeterRate;
	}

	public Double getMeterReadRate() {
		return meterReadRate;
	}

	public void setBigDnMeterNum(Integer bigDnMeterNum) {
		this.bigDnMeterNum = bigDnMeterNum;
	}

	public void setBigDnMeterRate(Double bigDnMeterRate) {
		this.bigDnMeterRate = bigDnMeterRate;
	}

	public void setMeterReadRate(Double meterReadRate) {
		this.meterReadRate = meterReadRate;
	}
	
}
