package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 表观漏损总览数据VO
 * @author csh
 * @Date 2020/03/23
 *
 */
public class ALListVO {
	
	/**
	 * 分区编号
	 */
	private String zoneNo;
	/**
	 * 表观漏损量
	 */
	private Double AL;

	/**
	 * 单位户数表观漏损量	
	 */
	private Double perCustomerAccAL;
	
	/**
	 * 表观漏损占比
	 */
	private Double percentAL;
	
	/**
	 * 表观漏损指数
	 */
	private Double ALI;
	
	/**
	 * 未抄表占比
	 */
	private Double percentNonMeterRead;
	
	/**
	 * 水表基础信息不完善占比
	 */
	private Double nonBasicInfoMeterRate;

	public Double getAL() {
		return AL;
	}

	public Double getPerCustomerAccAL() {
		return perCustomerAccAL;
	}

	public Double getPercentAL() {
		return percentAL;
	}

	public Double getALI() {
		return ALI;
	}

	public Double getPercentNonMeterRead() {
		return percentNonMeterRead;
	}

	public Double getNonBasicInfoMeterRate() {
		return nonBasicInfoMeterRate;
	}

	public void setAL(Double aL) {
		AL = aL;
	}

	public void setPerCustomerAccAL(Double perCustomerAccAL) {
		this.perCustomerAccAL = perCustomerAccAL;
	}

	public void setPercentAL(Double percentAL) {
		this.percentAL = percentAL;
	}

	public void setALI(Double aLI) {
		ALI = aLI;
	}

	public void setPercentNonMeterRead(Double percentNonMeterRead) {
		this.percentNonMeterRead = percentNonMeterRead;
	}


	public void setNonBasicInfoMeterRate(Double nonBasicInfoMeterRate) {
		this.nonBasicInfoMeterRate = nonBasicInfoMeterRate;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}
	
}
