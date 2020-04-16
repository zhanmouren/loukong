package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告水表分析模块，小口径零用水量水表数量统计
 * @author csh
 * @Date 2020/03/31
 */
public class DrSmallDnMeterData {

	/**
	 * 连续5个月水量零用水量数量
	 */
	private Integer fmZeroMeterNum;
	
	/**
	 * 连续2-4个月水量零用水量数量
	 */
	private Integer tfmZeroMeterNum;
	
	/**
	 * 总计数量
	 */
	private Integer totalZeroMeterNum;

	public Integer getFmZeroMeterNum() {
		return fmZeroMeterNum;
	}

	public Integer getTfmZeroMeterNum() {
		return tfmZeroMeterNum;
	}

	public Integer getTotalZeroMeterNum() {
		return totalZeroMeterNum;
	}

	public void setFmZeroMeterNum(Integer fmZeroMeterNum) {
		this.fmZeroMeterNum = fmZeroMeterNum;
	}

	public void setTfmZeroMeterNum(Integer tfmZeroMeterNum) {
		this.tfmZeroMeterNum = tfmZeroMeterNum;
	}

	public void setTotalZeroMeterNum(Integer totalZeroMeterNum) {
		this.totalZeroMeterNum = totalZeroMeterNum;
	}
	
}
