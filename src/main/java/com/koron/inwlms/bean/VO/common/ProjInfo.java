package com.koron.inwlms.bean.VO.common;

import java.util.List;

public class ProjInfo {
	
	private String key;
	
	private String value;
	
	private List<List<Double>> bounds;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<List<Double>> getBounds() {
		return bounds;
	}

	public void setBounds(List<List<Double>> bounds) {
		this.bounds = bounds;
	}
	
	

}
