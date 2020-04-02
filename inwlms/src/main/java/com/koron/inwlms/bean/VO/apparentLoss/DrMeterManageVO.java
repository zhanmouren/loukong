package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告表计管理VO
 * @author csh
 * @Date 2020/04/01
 *
 */
public class DrMeterManageVO {

	/**
	 * 抄表数据完整性
	 */
	private MeterReadData meterReadData;
	
	/**
	 * 水表信息完整性
	 */
	private MeterInfoLossData meterInfoLossData;
	
	/**
	 * 抄表时间不固定
	 */
	private MeterRTimeUnset meterRTimeUnset;
	
	/**
	 * 小口径水表超期服役信息
	 */
	private MeterOverUseTimeInfo meterOverUseTimeInfo;
	
	/**
	 * 用水可疑的用户数
	 */
	private Integer useBadNum;
	
	/**
	 * 可疑的用户的总用水量
	 */
	private Double userBadFlow;

	public MeterReadData getMeterReadData() {
		return meterReadData;
	}

	public MeterInfoLossData getMeterInfoLossData() {
		return meterInfoLossData;
	}

	public MeterRTimeUnset getMeterRTimeUnset() {
		return meterRTimeUnset;
	}

	public MeterOverUseTimeInfo getMeterOverUseTimeInfo() {
		return meterOverUseTimeInfo;
	}

	public void setMeterReadData(MeterReadData meterReadData) {
		this.meterReadData = meterReadData;
	}

	public void setMeterInfoLossData(MeterInfoLossData meterInfoLossData) {
		this.meterInfoLossData = meterInfoLossData;
	}

	public void setMeterRTimeUnset(MeterRTimeUnset meterRTimeUnset) {
		this.meterRTimeUnset = meterRTimeUnset;
	}

	public void setMeterOverUseTimeInfo(MeterOverUseTimeInfo meterOverUseTimeInfo) {
		this.meterOverUseTimeInfo = meterOverUseTimeInfo;
	}

	public Integer getUseBadNum() {
		return useBadNum;
	}

	public Double getUserBadFlow() {
		return userBadFlow;
	}

	public void setUseBadNum(Integer useBadNum) {
		this.useBadNum = useBadNum;
	}

	public void setUserBadFlow(Double userBadFlow) {
		this.userBadFlow = userBadFlow;
	}
	
}
