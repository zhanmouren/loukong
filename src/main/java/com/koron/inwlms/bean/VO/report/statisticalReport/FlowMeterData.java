package com.koron.inwlms.bean.VO.report.statisticalReport;

public class FlowMeterData {
	
	private String ctmNum;
	/**
	 * 用水类别
	 */
	private String ysName;
	/**
	 * 水表编号
	 */
	private String tblNum;
	/**
	 * 直径
	 */
	private String boreValue;
	
	

	public String getBoreValue() {
		return boreValue;
	}

	public void setBoreValue(String boreValue) {
		this.boreValue = boreValue;
	}

	public String getTblNum() {
		return tblNum;
	}

	public void setTblNum(String tblNum) {
		this.tblNum = tblNum;
	}

	public String getCtmNum() {
		return ctmNum;
	}

	public void setCtmNum(String ctmNum) {
		this.ctmNum = ctmNum;
	}

	public String getYsName() {
		return ysName;
	}

	public void setYsName(String ysName) {
		this.ysName = ysName;
	}

	
	

}
