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
	private String meterDn;
	
	/**
	 * 水表总数
	 */
	private int meterNum = 0;
	
	/**
	 * 低流量水表数量
	 */
	private int lowFlowMeterNum = 0;
	
	private int fsLowFlowMeterNum = 0;
	
	/**
	 * 零流量水表数量
	 */
	private int zeroFlowMeterNum  = 0; 
	
	private int fsZeroFlowMeterNum  = 0; 
	
	/**
	 * 正常流量水表数量
	 */
	private int normalFlowMeterNum  = 0;
	
	private int fsNormalFlowMeterNum  = 0;
	
	/**
	 * 过载流量水表数量
	 */
	private int highFlowMeterNum  = 0;
	
	private int fsHighFlowMeterNum  = 0;
	/**
	 * 水表类型
	 */
	private String meterType;
	
	/**
	 * 未抄表水表数量
	 */
	private int noReadNum = 0;

	public String getMeterDn() {
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

	public void setMeterDn(String meterDn) {
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

	public int getFsLowFlowMeterNum() {
		return fsLowFlowMeterNum;
	}

	public void setFsLowFlowMeterNum(int fsLowFlowMeterNum) {
		this.fsLowFlowMeterNum = fsLowFlowMeterNum;
	}

	public int getFsZeroFlowMeterNum() {
		return fsZeroFlowMeterNum;
	}

	public void setFsZeroFlowMeterNum(int fsZeroFlowMeterNum) {
		this.fsZeroFlowMeterNum = fsZeroFlowMeterNum;
	}

	public int getFsNormalFlowMeterNum() {
		return fsNormalFlowMeterNum;
	}

	public void setFsNormalFlowMeterNum(int fsNormalFlowMeterNum) {
		this.fsNormalFlowMeterNum = fsNormalFlowMeterNum;
	}

	public int getFsHighFlowMeterNum() {
		return fsHighFlowMeterNum;
	}

	public void setFsHighFlowMeterNum(int fsHighFlowMeterNum) {
		this.fsHighFlowMeterNum = fsHighFlowMeterNum;
	}

	public int getNoReadNum() {
		return noReadNum;
	}

	public void setNoReadNum(int noReadNum) {
		this.noReadNum = noReadNum;
	}

}
