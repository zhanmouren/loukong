package com.koron.inwlms.bean.VO.indexData;

import com.koron.common.web.mapper.TreeBean;

public class TreeZoneVO extends TreeBean.Long{
	
	private java.lang.Integer smid;
	//分区名
	private String name;
	//分区code
	private String code;
	
	private String rank;

	public java.lang.Integer getSmid() {
		return smid;
	}

	public void setSmid(java.lang.Integer smid) {
		this.smid = smid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	
}
