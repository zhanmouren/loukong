package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告水表分析模块，小口径低用水量水表数量统计
 * @author csh
 * @Date 2020/03/31
 */
public class DrSmallDnAnaData {

	/**
	 * 口径
	 */
	private Integer meterDn;
	
	/**
	 * 小于5m³的水表数量
	 */
	private Integer fFlowNum = 0;
	
	/**
	 * 5-10m³的水表数量
	 */
	private Integer ftFlowNum = 0;
	
	/**
	 * 10-20m³的水表数量
	 */
	private Integer ttFlowNum = 0;
	
	/**
	 * 大于20m³的水表数量
	 */
	private Integer tFlowNum = 0;

	public Integer getMeterDn() {
		return meterDn;
	}

	public Integer getfFlowNum() {
		return fFlowNum;
	}

	public Integer getFtFlowNum() {
		return ftFlowNum;
	}

	public Integer getTtFlowNum() {
		return ttFlowNum;
	}

	public Integer gettFlowNum() {
		return tFlowNum;
	}

	public void setMeterDn(Integer meterDn) {
		this.meterDn = meterDn;
	}

	public void setfFlowNum(Integer fFlowNum) {
		this.fFlowNum = fFlowNum;
	}

	public void setFtFlowNum(Integer ftFlowNum) {
		this.ftFlowNum = ftFlowNum;
	}

	public void setTtFlowNum(Integer ttFlowNum) {
		this.ttFlowNum = ttFlowNum;
	}

	public void settFlowNum(Integer tFlowNum) {
		this.tFlowNum = tFlowNum;
	}
	
}
