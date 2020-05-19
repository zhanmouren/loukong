package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 查询虚拟分区（相减）列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryVSZoneListDTO extends BaseDTO{

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
	
	private Integer virtualZoneType;
	
	/**
	 * 虚拟分区编码
	 */
	private String  virtualZoneNo;

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



	public Integer getVirtualZoneType() {
		return virtualZoneType;
	}

	public void setVirtualZoneType(Integer virtualZoneType) {
		this.virtualZoneType = virtualZoneType;
	}

	public String getVirtualZoneNo() {
		return virtualZoneNo;
	}

	public void setVirtualZoneNo(String virtualZoneNo) {
		this.virtualZoneNo = virtualZoneNo;
	}

}
