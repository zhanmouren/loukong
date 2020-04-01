package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告消防水表读数bean
 * @author csh
 * @Date 2020/03/31
 */
public class FsMeterReadData {

	/**
	 * 消防水表总数
	 */
	private Integer fsMeterNum;
	
	/**
	 * 零读数消防水表数量
	 */
	private Integer zeroReadNum;
	
	/**
	 * 	有读数消防水表数量
	 */
	private Integer normalReadNum;

	public Integer getFsMeterNum() {
		return fsMeterNum;
	}

	public Integer getZeroReadNum() {
		return zeroReadNum;
	}

	public Integer getNormalReadNum() {
		return normalReadNum;
	}

	public void setFsMeterNum(Integer fsMeterNum) {
		this.fsMeterNum = fsMeterNum;
	}

	public void setZeroReadNum(Integer zeroReadNum) {
		this.zeroReadNum = zeroReadNum;
	}

	public void setNormalReadNum(Integer normalReadNum) {
		this.normalReadNum = normalReadNum;
	}
	
	
}
