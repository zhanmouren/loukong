package com.koron.inwlms.bean.VO.leakageControl;

public class ProcessingStatisticsAllDataVO {

	private Double mnfBefor;
	
	private Double mnfAfther;
	
	private Double lossFlowBefor;
	
	private Double lossFlowAfther;
	
	private ProcessingStatisticsVO psv;

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
