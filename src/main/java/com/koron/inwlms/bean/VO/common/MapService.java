package com.koron.inwlms.bean.VO.common;

import java.util.List;
import java.util.Map;

public class MapService {
	
	private Map<String,GisMapData> mapServices;
	
	private OtherLayer OtherLayer;
	
	


	public OtherLayer getOtherLayer() {
		return OtherLayer;
	}

	public void setOtherLayer(OtherLayer otherLayer) {
		OtherLayer = otherLayer;
	}

	public Map<String, GisMapData> getMapServices() {
		return mapServices;
	}

	public void setMapServices(Map<String, GisMapData> mapServices) {
		this.mapServices = mapServices;
	}

	
	
	
}
