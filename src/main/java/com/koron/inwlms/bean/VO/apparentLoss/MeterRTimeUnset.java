package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告抄表时间不固定
 * @author csh
 * @Date 2020/04/01
 */
public class MeterRTimeUnset {

	/**
	 * 超过十天未抄表的水表数量
	 */
	private Integer tDNonReadMeterNum;
	
	/**
	 * 3-10天未抄表的水表数量
	 */
	private Integer tTDNonReadMeterNum;
	
	/**
	 * 合计超过3天未抄表的水表数量
	 */
	private Integer totalNonReadMeterNum;

	public Integer gettDNonReadMeterNum() {
		return tDNonReadMeterNum;
	}

	public Integer gettTDNonReadMeterNum() {
		return tTDNonReadMeterNum;
	}

	public Integer getTotalNonReadMeterNum() {
		return totalNonReadMeterNum;
	}

	public void settDNonReadMeterNum(Integer tDNonReadMeterNum) {
		this.tDNonReadMeterNum = tDNonReadMeterNum;
	}

	public void settTDNonReadMeterNum(Integer tTDNonReadMeterNum) {
		this.tTDNonReadMeterNum = tTDNonReadMeterNum;
	}

	public void setTotalNonReadMeterNum(Integer totalNonReadMeterNum) {
		this.totalNonReadMeterNum = totalNonReadMeterNum;
	}
	
}
