package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断水表超期服役信息
 * @author csh
 * @Date 2020/04/01
 *
 */
public class MeterOverUseTimeInfo {

	/**
	 * 小口径水表超期服役占比
	 */
	private Double sDnMeterRate;
	
	/**
	 * 小口径水表服役时间6年内占比
	 */
	private Double fMeterRate;
	
	/**
	 * 小口径水表服役时间6-10年内占比
	 */
	private Double ftMeterRate;
	
	/**
	 * 小口径水表服役时间10年内占比
	 */
	private Double tMeterRate;

	public Double getsDnMeterRate() {
		return sDnMeterRate;
	}

	public Double getfMeterRate() {
		return fMeterRate;
	}

	public Double getFtMeterRate() {
		return ftMeterRate;
	}

	public Double gettMeterRate() {
		return tMeterRate;
	}

	public void setsDnMeterRate(Double sDnMeterRate) {
		this.sDnMeterRate = sDnMeterRate;
	}

	public void setfMeterRate(Double fMeterRate) {
		this.fMeterRate = fMeterRate;
	}

	public void setFtMeterRate(Double ftMeterRate) {
		this.ftMeterRate = ftMeterRate;
	}

	public void settMeterRate(Double tMeterRate) {
		this.tMeterRate = tMeterRate;
	}
	
}
