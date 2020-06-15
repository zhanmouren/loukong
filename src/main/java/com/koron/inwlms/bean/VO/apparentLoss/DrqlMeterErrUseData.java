package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告-问题列表用水异常数据
 * @author csh
 * @Date 2020/04/03
 */
public class DrqlMeterErrUseData {

	/**
	 * 用户编码
	 */
	private String accNo;
	
	/**
	 * 均值-3倍标准差
	 */
	private Double minV;
	
	/**
	 * 均值+3倍标准差
	 */
	private Double maxV;
	

	public Double getMinV() {
		return minV;
	}

	public Double getMaxV() {
		return maxV;
	}


	public void setMinV(Double minV) {
		this.minV = minV;
	}

	public void setMaxV(Double maxV) {
		this.maxV = maxV;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	
}
