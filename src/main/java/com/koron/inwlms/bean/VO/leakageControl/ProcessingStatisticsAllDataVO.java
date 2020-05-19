package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

public class ProcessingStatisticsAllDataVO {

	private Double mnfBefor;
	
	private Double mnfAfther;
	
	private Double lossFlowBefor;
	
	private Double lossFlowAfther;
	
	private ProcessingStatisticsVO psv;
	
	private PolicyTypeNum ptn;
	
	private List<ZoneSaveWaterData> saveWaterList;
	
	private List<ZoneSaveWaterData> saveCostList;

	
	
	public PolicyTypeNum getPtn() {
		return ptn;
	}

	public void setPtn(PolicyTypeNum ptn) {
		this.ptn = ptn;
	}

	public List<ZoneSaveWaterData> getSaveWaterList() {
		return saveWaterList;
	}

	public void setSaveWaterList(List<ZoneSaveWaterData> saveWaterList) {
		this.saveWaterList = saveWaterList;
	}

	public List<ZoneSaveWaterData> getSaveCostList() {
		return saveCostList;
	}

	public void setSaveCostList(List<ZoneSaveWaterData> saveCostList) {
		this.saveCostList = saveCostList;
	}

	public Double getMnfBefor() {
		return mnfBefor;
	}

	public void setMnfBefor(Double mnfBefor) {
		this.mnfBefor = mnfBefor;
	}

	public Double getMnfAfther() {
		return mnfAfther;
	}

	public void setMnfAfther(Double mnfAfther) {
		this.mnfAfther = mnfAfther;
	}

	public Double getLossFlowBefor() {
		return lossFlowBefor;
	}

	public void setLossFlowBefor(Double lossFlowBefor) {
		this.lossFlowBefor = lossFlowBefor;
	}

	public Double getLossFlowAfther() {
		return lossFlowAfther;
	}

	public void setLossFlowAfther(Double lossFlowAfther) {
		this.lossFlowAfther = lossFlowAfther;
	}

	public ProcessingStatisticsVO getPsv() {
		return psv;
	}

	public void setPsv(ProcessingStatisticsVO psv) {
		this.psv = psv;
	}
	
	
	
}
