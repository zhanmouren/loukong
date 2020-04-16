package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 抄表数据信息
 * @author csh
 * @Date 2020/03/31
 */
public class MeterReadData {

	/**
	 * 连续超过5个月没有抄表数据的户数
	 */
	private Integer fMNonMeterReadNum;
	
	/**
	 * 连续2-4个月没有抄表数据的户数
	 */
	private Integer tFMNonMeterReadNum;
	
	/**
	 * 连续2个月以上没有抄表数据的户数
	 */
	private Integer totalNonMeterReadNum;

	public Integer getfMNonMeterReadNum() {
		return fMNonMeterReadNum;
	}

	public Integer gettFMNonMeterReadNum() {
		return tFMNonMeterReadNum;
	}

	public Integer getTotalNonMeterReadNum() {
		return totalNonMeterReadNum;
	}

	public void setfMNonMeterReadNum(Integer fMNonMeterReadNum) {
		this.fMNonMeterReadNum = fMNonMeterReadNum;
	}

	public void settFMNonMeterReadNum(Integer tFMNonMeterReadNum) {
		this.tFMNonMeterReadNum = tFMNonMeterReadNum;
	}

	public void setTotalNonMeterReadNum(Integer totalNonMeterReadNum) {
		this.totalNonMeterReadNum = totalNonMeterReadNum;
	}
	
}
