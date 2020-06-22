package com.koron.inwlms.bean.VO.common;

import java.util.List;

public class MapServiceData {
	
	private String baseMapUrl;
	
	private MapService services;
	
    private MapOpTion mapOption;
	
	private List<Widgets> widgets;

	public String getBaseMapUrl() {
		return baseMapUrl;
	}

	public void setBaseMapUrl(String baseMapUrl) {
		this.baseMapUrl = baseMapUrl;
	}

	public MapService getServices() {
		return services;
	}

	public void setServices(MapService services) {
		this.services = services;
	}
	

	public MapOpTion getMapOption() {
		return mapOption;
	}

	public void setMapOption(MapOpTion mapOption) {
		this.mapOption = mapOption;
	}

	public List<Widgets> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<Widgets> widgets) {
		this.widgets = widgets;
	}
	
	

}
