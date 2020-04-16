package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表分析图表数据VO
 * @author lu
 * @Date 2020/03/27
 */
public class MeterAnalysisMapVO {

	/**
	 * 大口径数据
	 */
	private MeterFlowRateData bigDnData;
	
	/**
	 * 小口径数据
	 */
	private MeterFlowRateData smallDnData;
	
	/**
	 * 大口径（除消防水表）数据
	 */
	private MeterFlowRateData bigDnNoFSData;
	
	/**
	 * 消防水表数据
	 */
	private FSMeterData fSMeterData;

	public MeterFlowRateData getBigDnData() {
		return bigDnData;
	}

	public MeterFlowRateData getSmallDnData() {
		return smallDnData;
	}

	public MeterFlowRateData getBigDnNoFSData() {
		return bigDnNoFSData;
	}

	public void setBigDnData(MeterFlowRateData bigDnData) {
		this.bigDnData = bigDnData;
	}

	public void setSmallDnData(MeterFlowRateData smallDnData) {
		this.smallDnData = smallDnData;
	}

	public void setBigDnNoFSData(MeterFlowRateData bigDnNoFSData) {
		this.bigDnNoFSData = bigDnNoFSData;
	}

	public FSMeterData getfSMeterData() {
		return fSMeterData;
	}

	public void setfSMeterData(FSMeterData fSMeterData) {
		this.fSMeterData = fSMeterData;
	}
}
