package com.koron.inwlms.bean.VO.common;

import java.util.List;

public class MapOpTion {
	
	private List<Double> res;
	
	private List<Double> center;
	
	private Integer maxZoom;
	
	private Integer zoom;
	
	private ProjInfo projInfo;
	
	

	public ProjInfo getProjInfo() {
		return projInfo;
	}

	public void setProjInfo(ProjInfo projInfo) {
		this.projInfo = projInfo;
	}

	public List<Double> getRes() {
		return res;
	}

	public void setRes(List<Double> res) {
		this.res = res;
	}

	public List<Double> getCenter() {
		return center;
	}

	public void setCenter(List<Double> center) {
		this.center = center;
	}

	public Integer getMaxZoom() {
		return maxZoom;
	}

	public void setMaxZoom(Integer maxZoom) {
		this.maxZoom = maxZoom;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	
	

}
