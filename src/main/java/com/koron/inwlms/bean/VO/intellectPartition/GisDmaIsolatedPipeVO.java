package com.koron.inwlms.bean.VO.intellectPartition;

import java.util.List;

public class GisDmaIsolatedPipeVO {
	
	private List<GisZonePipeDateVO> isolatedList;
	
	private List<GisZonePipeDateVO> normalList;

	public List<GisZonePipeDateVO> getIsolatedList() {
		return isolatedList;
	}

	public void setIsolatedList(List<GisZonePipeDateVO> isolatedList) {
		this.isolatedList = isolatedList;
	}

	public List<GisZonePipeDateVO> getNormalList() {
		return normalList;
	}

	public void setNormalList(List<GisZonePipeDateVO> normalList) {
		this.normalList = normalList;
	}
	
	

}
