package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表月流量数据
 * @author csh
 *
 */
public class MeterMFlowData {

	private String code;
	
	private String month;
	
	private Double flux;
	
	private Integer meterDn;

	public String getCode() {
		return code;
	}

	public String getMonth() {
		return month;
	}

	public Double getFlux() {
		return flux;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setFlux(Double flux) {
		this.flux = flux;
	}

	public Integer getMeterDn() {
		return meterDn;
	}

	public void setMeterDn(Integer meterDn) {
		this.meterDn = meterDn;
	}
	
}
