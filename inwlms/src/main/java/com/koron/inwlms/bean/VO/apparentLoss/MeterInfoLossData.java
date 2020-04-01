package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表信息不完善VO
 * @author csh
 * @Date 2020/03/31
 */
public class MeterInfoLossData {

	/**
	 * 缺少安装日期的水表数量
	 */
	private Integer lossInDateNum;
	
	/**
	 * 缺少口径的水表数量
	 */
	private Integer lossDnNum;
	
	/**
	 * 缺少水表类型的水表数量
	 */
	private Integer lossMTypeNum;
	
	/**
	 * 缺少用水类型的水表数量
	 */
	private Integer lossUTypeNum;

	public Integer getLossInDateNum() {
		return lossInDateNum;
	}

	public Integer getLossDnNum() {
		return lossDnNum;
	}

	public Integer getLossMTypeNum() {
		return lossMTypeNum;
	}

	public Integer getLossUTypeNum() {
		return lossUTypeNum;
	}

	public void setLossInDateNum(Integer lossInDateNum) {
		this.lossInDateNum = lossInDateNum;
	}

	public void setLossDnNum(Integer lossDnNum) {
		this.lossDnNum = lossDnNum;
	}

	public void setLossMTypeNum(Integer lossMTypeNum) {
		this.lossMTypeNum = lossMTypeNum;
	}

	public void setLossUTypeNum(Integer lossUTypeNum) {
		this.lossUTypeNum = lossUTypeNum;
	}
	
}
