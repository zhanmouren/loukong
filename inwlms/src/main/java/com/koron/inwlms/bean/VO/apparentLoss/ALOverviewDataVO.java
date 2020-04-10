package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 表观漏损总览数据VO
 * @author csh
 * @Date 2020/03/23
 *
 */
public class ALOverviewDataVO {
	
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
	 * 抄表及时率
	 */
	private Double meterReadRate;
	
	/**
	 * 抄表时间不固定占比
	 */
	private Double nonMeterReadTimeRate;
	
	/**
	 * 超期服役水表占比
	 */
	private Double overdueMetersRate;
	
	/**
	 * 水表基础信息不完善占比
	 */
	private Double nonBasicInfoMeterRate;
	
	/**
	 * 时间
	 */
	private Integer time;
	
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

	public Double getMeterReadRate() {
		return meterReadRate;
	}

	public Double getNonMeterReadTimeRate() {
		return nonMeterReadTimeRate;
	}

	public Double getOverdueMetersRate() {
		return overdueMetersRate;
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

	public void setMeterReadRate(Double meterReadRate) {
		this.meterReadRate = meterReadRate;
	}

	public void setNonMeterReadTimeRate(Double nonMeterReadTimeRate) {
		this.nonMeterReadTimeRate = nonMeterReadTimeRate;
	}

	public void setOverdueMetersRate(Double overdueMetersRate) {
		this.overdueMetersRate = overdueMetersRate;
	}

	public void setNonBasicInfoMeterRate(Double nonBasicInfoMeterRate) {
		this.nonBasicInfoMeterRate = nonBasicInfoMeterRate;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
}
