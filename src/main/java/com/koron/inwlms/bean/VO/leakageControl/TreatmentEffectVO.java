package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

public class TreatmentEffectVO {
	
	private List<TimeAndFlowData> lossFlow;
	
	private List<TimeAndFlowData> allFlow;

	public List<TimeAndFlowData> getLossFlow() {
		return lossFlow;
	}

	public void setLossFlow(List<TimeAndFlowData> lossFlow) {
		this.lossFlow = lossFlow;
	}

	public List<TimeAndFlowData> getAllFlow() {
		return allFlow;
	}

	public void setAllFlow(List<TimeAndFlowData> allFlow) {
		this.allFlow = allFlow;
	}
	
	

}
