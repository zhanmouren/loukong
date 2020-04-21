package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 查询虚拟分区（合并）列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryVCZoneListDTO extends BaseDTO{

	private Double minNrw;
	
	private Double maxNrw;
	
	private Double minUfwc;
	
	private Double maxUfwc;
	
	/**
	 * 时间类型（0：分 ，1：时，2：日，3：月，4：年）
	 */
	private Integer timeType;
	
	/**
	 * 开始时间
	 */
	private Integer startTime;
	
	/**
	 * 结束时间
	 */
	private Integer endTime;
	
	/**
	 * 最小漏损率
	 */
	private Double minLossRate;
	
	/**
	 * 最大漏损率
	 */
	private Double maxLossRate;
	
	/**
	 * 最小漏损量
	 */
	private Double minLossFlow;
	
	/**
	 * 最大漏损量
	 */
	private Double maxLossFlow;
	
	/**
	 * 虚拟分区编码
	 */
	private String vZoneNo;
	
	/**
	 * dma编码
	 */
	private String dmaNo;

	public Double getMinNrw() {
		return minNrw;
	}

	public Double getMaxNrw() {
		return maxNrw;
	}

	public Double getMinUfwc() {
		return minUfwc;
	}

	public Double getMaxUfwc() {
		return maxUfwc;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}


	public void setMinNrw(Double minNrw) {
		this.minNrw = minNrw;
	}

	public void setMaxNrw(Double maxNrw) {
		this.maxNrw = maxNrw;
	}

	public void setMinUfwc(Double minUfwc) {
		this.minUfwc = minUfwc;
	}

	public void setMaxUfwc(Double maxUfwc) {
		this.maxUfwc = maxUfwc;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}


	public String getvZoneNo() {
		return vZoneNo;
	}

	public void setvZoneNo(String vZoneNo) {
		this.vZoneNo = vZoneNo;
	}

	public Double getMinLossRate() {
		return minLossRate;
	}

	public Double getMaxLossRate() {
		return maxLossRate;
	}

	public Double getMinLossFlow() {
		return minLossFlow;
	}

	public Double getMaxLossFlow() {
		return maxLossFlow;
	}

	public String getDmaNo() {
		return dmaNo;
	}

	public void setMinLossRate(Double minLossRate) {
		this.minLossRate = minLossRate;
	}

	public void setMaxLossRate(Double maxLossRate) {
		this.maxLossRate = maxLossRate;
	}

	public void setMinLossFlow(Double minLossFlow) {
		this.minLossFlow = minLossFlow;
	}

	public void setMaxLossFlow(Double maxLossFlow) {
		this.maxLossFlow = maxLossFlow;
	}

	public void setDmaNo(String dmaNo) {
		this.dmaNo = dmaNo;
	}

	
}
