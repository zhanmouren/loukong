package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 查询虚拟分区（合并）列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryVCZoneListDTO extends BaseDTO{

	private String minNrw;
	
	private String maxNrw;
	
	private String minWl;
	
	private String maxWl;
	
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
	private String minLossRate;
	
	/**
	 * 最大漏损率
	 */
	private String maxLossRate;
	
	/**
	 * 最小漏损量
	 */
	private String minLossFlow;
	
	/**
	 * 最大漏损量
	 */
	private String maxLossFlow;
	
	/**
	 * 虚拟分区编码
	 */
	private String virtualZoneNo;
	
	/**
	 * dma编码
	 */
	private String dmaNo;


	public Integer getTimeType() {
		return timeType;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public Integer getEndTime() {
		return endTime;
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


	
	public String getVirtualZoneNo() {
		return virtualZoneNo;
	}

	public void setVirtualZoneNo(String virtualZoneNo) {
		this.virtualZoneNo = virtualZoneNo;
	}


	public String getDmaNo() {
		return dmaNo;
	}


	public void setDmaNo(String dmaNo) {
		this.dmaNo = dmaNo;
	}

	public String getMinNrw() {
		return minNrw;
	}

	public void setMinNrw(String minNrw) {
		this.minNrw = minNrw;
	}

	public String getMaxNrw() {
		return maxNrw;
	}

	public void setMaxNrw(String maxNrw) {
		this.maxNrw = maxNrw;
	}

	public String getMinWl() {
		return minWl;
	}

	public void setMinWl(String minWl) {
		this.minWl = minWl;
	}

	public String getMaxWl() {
		return maxWl;
	}

	public void setMaxWl(String maxWl) {
		this.maxWl = maxWl;
	}

	public String getMinLossRate() {
		return minLossRate;
	}

	public void setMinLossRate(String minLossRate) {
		this.minLossRate = minLossRate;
	}

	public String getMaxLossRate() {
		return maxLossRate;
	}

	public void setMaxLossRate(String maxLossRate) {
		this.maxLossRate = maxLossRate;
	}

	public String getMinLossFlow() {
		return minLossFlow;
	}

	public void setMinLossFlow(String minLossFlow) {
		this.minLossFlow = minLossFlow;
	}

	public String getMaxLossFlow() {
		return maxLossFlow;
	}

	public void setMaxLossFlow(String maxLossFlow) {
		this.maxLossFlow = maxLossFlow;
	}

	
}
