package com.koron.inwlms.bean.VO.common;

import java.util.List;

public class WidgetsParam {
	
	private String show;
	
	private Integer size;
	
	private List<TreeData> treeData;
	
	

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public List<TreeData> getTreeData() {
		return treeData;
	}

	public void setTreeData(List<TreeData> treeData) {
		this.treeData = treeData;
	}
	

}
