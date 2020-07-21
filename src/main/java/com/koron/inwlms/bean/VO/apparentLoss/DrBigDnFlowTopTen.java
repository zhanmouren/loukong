package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 诊断报告大口径排名前十的水量数据
 * @author csh
 *
 */
public class DrBigDnFlowTopTen {

	private List<String> accNos;
	
	private List<Double> flows;
	
	private List<Double> flowRates;

	public List<String> getAccNos() {
		return accNos;
	}

	public void setAccNos(List<String> accNos) {
		this.accNos = accNos;
	}

	public List<Double> getFlows() {
		return flows;
	}

	public void setFlows(List<Double> flows) {
		this.flows = flows;
	}

	public List<Double> getFlowRates() {
		return flowRates;
	}

	public void setFlowRates(List<Double> flowRates) {
		this.flowRates = flowRates;
	}
	
	
}
