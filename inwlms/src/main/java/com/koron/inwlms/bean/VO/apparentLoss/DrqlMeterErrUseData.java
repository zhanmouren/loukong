package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告-问题列表用水异常数据
 * @author csh
 * @Date 2020/04/03
 */
public class DrqlMeterErrUseData {

	/**
	 * 水表编码
	 */
	private String meterNo;
	
	/**
	 * 均值-3倍标准差
	 */
	private Double minV;
	
	/**
	 * 均值+3倍标准差
	 */
	private Double maxV;

	public String getMeterNo() {
		return meterNo;
	}

	public Double getMinV() {
		return minV;
	}

	public Double getMaxV() {
		return maxV;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public void setMinV(Double minV) {
		this.minV = minV;
	}

	public void setMaxV(Double maxV) {
		this.maxV = maxV;
	}
	
}
