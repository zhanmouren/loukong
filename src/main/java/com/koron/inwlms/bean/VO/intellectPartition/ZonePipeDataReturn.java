package com.koron.inwlms.bean.VO.intellectPartition;

import java.util.List;

public class ZonePipeDataReturn {
	
	private String rCode;
	
	private List<ZonePipeData> pipeCodeList;

	public String getrCode() {
		return rCode;
	}

	public void setrCode(String rCode) {
		this.rCode = rCode;
	}

	public List<ZonePipeData> getPipeCodeList() {
		return pipeCodeList;
	}

	public void setPipeCodeList(List<ZonePipeData> pipeCodeList) {
		this.pipeCodeList = pipeCodeList;
	}
	
	

}
