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
	private Integer lowFlowMeterRate;
	
	/**
	 * 零流量占比
	 */
	private Integer zeroFlowMeterRate;
	
	/**
	 * 正常流量占比
	 */
	private Integer normalFlowMeterRate;
	
	/**
	 * 高流量占比
	 */
	private Integer highFlowMeterRate;

	public Integer getLowFlowMeterRate() {
		return lowFlowMeterRate;
	}

	public void setLowFlowMeterRate(Integer lowFlowMeterRate) {
		this.lowFlowMeterRate = lowFlowMeterRate;
	}

	public Integer getZeroFlowMeterRate() {
		return zeroFlowMeterRate;
	}

	public void setZeroFlowMeterRate(Integer zeroFlowMeterRate) {
		this.zeroFlowMeterRate = zeroFlowMeterRate;
	}

	public Integer getNormalFlowMeterRate() {
		return normalFlowMeterRate;
	}

	public void setNormalFlowMeterRate(Integer normalFlowMeterRate) {
		this.normalFlowMeterRate = normalFlowMeterRate;
	}

	public Integer getHighFlowMeterRate() {
		return highFlowMeterRate;
	}

	public void setHighFlowMeterRate(Integer highFlowMeterRate) {
		this.highFlowMeterRate = highFlowMeterRate;
	}

	
}
