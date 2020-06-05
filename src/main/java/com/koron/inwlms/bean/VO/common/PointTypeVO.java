package com.koron.inwlms.bean.VO.common;

import java.util.List;

public class PointTypeVO {
	
	/**
	 * 通道类型
	 */
	private String stype;
	/**
	 * 
	 */
	private String indexCode;
	/**
	 * 通道类型名称
	 */
	private String sname;
	
	private List<PointDataVO> pointDataList;

	
	public List<PointDataVO> getPointDataList() {
		return pointDataList;
	}

	public void setPointDataList(List<PointDataVO> pointDataList) {
		this.pointDataList = pointDataList;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
	
	

}
