package com.koron.inwlms.bean.VO.leakageControl;

public class TreeVO {
	
	private java.lang.Integer smid;
	//分区名
	private String name;
	//分区code
	private String code;
	
	private String rank;
	
	private Integer parentmask;

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

	public Integer getParentmask() {
		return parentmask;
	}

	public void setParentmask(Integer parentmask) {
		this.parentmask = parentmask;
	}
	
	

}
