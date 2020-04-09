package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 水平衡指标VO
 * @author csh
 * @Date 2020/04/08
 */
public class WBIndicatorVO {

	private String code;
	
	private Double value;
	
	private Integer timeId;

	public String getCode() {
		return code;
	}

	public Double getValue() {
		return value;
	}

	public Integer getTimeId() {
		return timeId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}
	
}
