package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表运行分析VO
 * @author csh
 * @Date 2020/03/27
 *
 */
public class MeterRunAnalysisVO {

	/**
	 * 口径编号
	 */
	private Integer meterDn;
	
	/**
	 * 水表总数
	 */
	private int meterNum = 0;
	
	/**
	 * 低流量水表数量
	 */
	private int lowFlowMeterNum = 0;
	
	/**
	 * 零流量水表数量
	 */
	private int zeroFlowMeterNum  = 0; 
	
	/**
	 * 正常流量水表数量
	 */
	private int normalFlowMeterNum  = 0;
	
	/**
	 * 过载流量水表数量
	 */
	private int highFlowMeterNum  = 0;
	
	/**
	 * 水表类型
	 */
	private String meterType;

	public Integer getMeterDn() {
		return meterDn;
	}

	public int getMeterNum() {
		return meterNum;
	}

	public int getLowFlowMeterNum() {
		return lowFlowMeterNum;
	}

	public int getZeroFlowMeterNum() {
		return zeroFlowMeterNum;
	}

	public int getNormalFlowMeterNum() {
		return normalFlowMeterNum;
	}

	public int getHighFlowMeterNum() {
		return highFlowMeterNum;
	}

	public void setMeterDn(Integer meterDn) {
		this.meterDn = meterDn;
	}

	public void setMeterNum(int meterNum) {
		this.meterNum = meterNum;
	}

	public void setLowFlowMeterNum(int lowFlowMeterNum) {
		this.lowFlowMeterNum = lowFlowMeterNum;
	}

	public void setZeroFlowMeterNum(int zeroFlowMeterNum) {
		this.zeroFlowMeterNum = zeroFlowMeterNum;
	}

	public void setNormalFlowMeterNum(int normalFlowMeterNum) {
		this.normalFlowMeterNum = normalFlowMeterNum;
	}

	public void setHighFlowMeterNum(int highFlowMeterNum) {
		this.highFlowMeterNum = highFlowMeterNum;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

}
