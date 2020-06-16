package com.koron.inwlms.bean.DTO.leakageControl;

public class RecommendStrategy {
	/**
	 * 铸铁管总长
	 */
	private Double lenghC;
	/**
	 * 非铸铁管总长
	 */
	private Double lenghNC;
	/**
	 * 用户数
	 */
	private Integer pNum;
	
	/**
	 * 按管长加权的平均管龄
	 */
	private Double age;
	/**
	 * 最小夜间流量发生时对应压力平均值
	 */
	private Double press;
	/**
	 * 表观漏损量
	 */
	private Double lossFlow; 
	
	private Double a1;
	
	private Double a2;
	
	private Double a3;
	
	private Double b;
	
	private Double c;
	/**
	 * 平均水价
	 */
	private Double avgFlowP;
	/**
	 * 要求服务压力
	 */
	private Double serviceP;
	/**
	 * 单位供水成本
	 */
	private Double unitFlowP;
	
	private Double leakage;
	/**
	 * 表观漏损指数
	 */
	private Double ali;
	/**
	 * 其他设备投资
	 */
	private Double k3;
	/**
	 * 工程费用系数
	 */
	private Double k4;
	
	private Double maxMNF;
	
	private Double midMNF;
	
	private Double minMNF;
	
	private Double maxFlow;
	
	private Double midFlow;
	
	private Double minFlow;
	
	
	public Double getMaxMNF() {
		return maxMNF;
	}

	public void setMaxMNF(Double maxMNF) {
		this.maxMNF = maxMNF;
	}

	public Double getMidMNF() {
		return midMNF;
	}

	public void setMidMNF(Double midMNF) {
		this.midMNF = midMNF;
	}

	public Double getMinMNF() {
		return minMNF;
	}

	public void setMinMNF(Double minMNF) {
		this.minMNF = minMNF;
	}

	public Double getMaxFlow() {
		return maxFlow;
	}

	public void setMaxFlow(Double maxFlow) {
		this.maxFlow = maxFlow;
	}

	public Double getMidFlow() {
		return midFlow;
	}

	public void setMidFlow(Double midFlow) {
		this.midFlow = midFlow;
	}

	public Double getMinFlow() {
		return minFlow;
	}

	public void setMinFlow(Double minFlow) {
		this.minFlow = minFlow;
	}

	public Double getAli() {
		return ali;
	}

	public void setAli(Double ali) {
		this.ali = ali;
	}

	public Double getK3() {
		return k3;
	}

	public void setK3(Double k3) {
		this.k3 = k3;
	}

	public Double getK4() {
		return k4;
	}

	public void setK4(Double k4) {
		this.k4 = k4;
	}

	public Double getLeakage() {
		return leakage;
	}

	public void setLeakage(Double leakage) {
		this.leakage = leakage;
	}

	public Double getUnitFlowP() {
		return unitFlowP;
	}

	public void setUnitFlowP(Double unitFlowP) {
		this.unitFlowP = unitFlowP;
	}

	public Double getServiceP() {
		return serviceP;
	}

	public void setServiceP(Double serviceP) {
		this.serviceP = serviceP;
	}

	public Double getAvgFlowP() {
		return avgFlowP;
	}

	public void setAvgFlowP(Double avgFlowP) {
		this.avgFlowP = avgFlowP;
	}

	public Double getLossFlow() {
		return lossFlow;
	}

	public void setLossFlow(Double lossFlow) {
		this.lossFlow = lossFlow;
	}

	public Double getLenghC() {
		return lenghC;
	}

	public void setLenghC(Double lenghC) {
		this.lenghC = lenghC;
	}

	public Double getLenghNC() {
		return lenghNC;
	}

	public void setLenghNC(Double lenghNC) {
		this.lenghNC = lenghNC;
	}

	public Integer getpNum() {
		return pNum;
	}

	public void setpNum(Integer pNum) {
		this.pNum = pNum;
	}

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	public Double getPress() {
		return press;
	}

	public void setPress(Double press) {
		this.press = press;
	}

	public Double getA1() {
		return a1;
	}

	public void setA1(Double a1) {
		this.a1 = a1;
	}

	public Double getA2() {
		return a2;
	}

	public void setA2(Double a2) {
		this.a2 = a2;
	}

	public Double getA3() {
		return a3;
	}

	public void setA3(Double a3) {
		this.a3 = a3;
	}

	public Double getB() {
		return b;
	}

	public void setB(Double b) {
		this.b = b;
	}

	public Double getC() {
		return c;
	}

	public void setC(Double c) {
		this.c = c;
	}
	
	

}
