package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 大口径水表数据VO
 * @author lu
 * @Date 2020/03/27
 */
public class MeterFlowRateData {

	/**
	 * 低流量占比
	 */
	private Double lowFlowMeterRate;
	
	/**
	 * 零流量占比
	 */
	private Double zeroFlowMeterRate;
	
	/**
	 * 正常流量占比
	 */
	private Double normalFlowMeterRate;
	
	/**
	 * 过载流量占比
	 */
	private Double highFlowMeterRate;

	public Double getLowFlowMeterRate() {
		return lowFlowMeterRate;
	}

	public Double getZeroFlowMeterRate() {
		return zeroFlowMeterRate;
	}

	public Double getNormalFlowMeterRate() {
		return normalFlowMeterRate;
	}

	public Double getHighFlowMeterRate() {
		return highFlowMeterRate;
	}

	public void setLowFlowMeterRate(Double lowFlowMeterRate) {
		this.lowFlowMeterRate = lowFlowMeterRate;
	}

	public void setZeroFlowMeterRate(Double zeroFlowMeterRate) {
		this.zeroFlowMeterRate = zeroFlowMeterRate;
	}

	public void setNormalFlowMeterRate(Double normalFlowMeterRate) {
		this.normalFlowMeterRate = normalFlowMeterRate;
	}

	public void setHighFlowMeterRate(Double highFlowMeterRate) {
		this.highFlowMeterRate = highFlowMeterRate;
	}
	
}
