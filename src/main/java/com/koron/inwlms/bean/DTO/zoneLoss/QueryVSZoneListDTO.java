package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 查询虚拟分区（相减）列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryVSZoneListDTO extends BaseDTO{

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
	
	private String virtualZoneType;
	
	/**
	 * 虚拟分区编码
	 */
	private String  virtualZoneNo;


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

	public String getVirtualZoneType() {
		return virtualZoneType;
	}

	public void setVirtualZoneType(String virtualZoneType) {
		this.virtualZoneType = virtualZoneType;
	}

	public String getVirtualZoneNo() {
		return virtualZoneNo;
	}

	public void setVirtualZoneNo(String virtualZoneNo) {
		this.virtualZoneNo = virtualZoneNo;
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

}
