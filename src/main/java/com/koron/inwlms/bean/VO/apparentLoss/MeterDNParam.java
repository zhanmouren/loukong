package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 水表口径参数
 * @author csh
 * @Date 2020/03/27
 */
public class MeterDNParam {

	/**
	 * 最大流量参数
	 */
	private String maxQ;
	
	/**
	 * 最小流量参数
	 */
	private String minQ;

	public String getMaxQ() {
		return maxQ;
	}

	public String getMinQ() {
		return minQ;
	}

	public void setMaxQ(String maxQ) {
		this.maxQ = maxQ;
	}

	public void setMinQ(String minQ) {
		this.minQ = minQ;
	}
	
	
}
