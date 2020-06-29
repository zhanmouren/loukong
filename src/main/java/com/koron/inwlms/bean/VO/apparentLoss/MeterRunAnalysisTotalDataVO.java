package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 水表运行分析总接口VO
 * @author csh
 *
 */
public class MeterRunAnalysisTotalDataVO {

	
	private List<MeterRunAnalysisVO> lists;
	
	private MeterAnalysisMapVO maps;

	public List<MeterRunAnalysisVO> getLists() {
		return lists;
	}

	public void setLists(List<MeterRunAnalysisVO> lists) {
		this.lists = lists;
	}

	public MeterAnalysisMapVO getMaps() {
		return maps;
	}

	public void setMaps(MeterAnalysisMapVO maps) {
		this.maps = maps;
	}
	
	
}
