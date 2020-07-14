package com.koron.inwlms.bean.DTO.leakageControl;

public class WarningSchemeHisData {
	
	private Double oldMax;
	
	private Double oldMin;
	
	private Double max;
	
	private Double min;
	
	private Integer time;
	
	private Integer type;
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getOldMax() {
		return oldMax;
	}

	public void setOldMax(Double oldMax) {
		this.oldMax = oldMax;
	}

	public Double getOldMin() {
		return oldMin;
	}

	public void setOldMin(Double oldMin) {
		this.oldMin = oldMin;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
	

}
