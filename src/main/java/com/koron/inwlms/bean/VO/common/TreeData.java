package com.koron.inwlms.bean.VO.common;

import java.util.List;

public class TreeData {
	
	private String title;
	
	private String id;
	
	private List<TreeDataControl> control;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TreeDataControl> getControl() {
		return control;
	}

	public void setControl(List<TreeDataControl> control) {
		this.control = control;
	}
	
	

}
