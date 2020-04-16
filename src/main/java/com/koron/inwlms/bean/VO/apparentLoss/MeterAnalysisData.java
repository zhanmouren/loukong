package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表运行分析占比数据
 * @author csh
 * @Data 2020/03/30
 */
public class MeterAnalysisData {

	/**
	 * 低流量占比
	 */
	private double lowFlowMeterRate;
	
	/**
	 * 零流量占比
	 */
	private double zeroFlowMeterRate;
	
	/**
	 * 正常流量占比
	 */
	private double normalFlowMeterRate;
	
	/**
	 * 高流量占比
	 */
	private double highFlowMeterRate;

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
