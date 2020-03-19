package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 查询虚拟分区（相减）列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryVSZoneListDTO extends BaseDTO{

	public Double minNrw;
	
	public Double maxNrw;
	
	public Double minUfwc;
	
	public Double maxUfwc;
	
	/**
	 * 时间类型（0：分 ，1：时，2：日，3：月，4：年）
	 */
	public Integer timeType;
	
	/**
	 * 开始时间
	 */
	public Integer startTime;
	
	/**
	 * 结束时间
	 */
	public Integer endTime;
	
	public Integer vZoneType;
	
	/**
	 * 虚拟分区编码
	 */
	public String  vZoneNo;

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

	public Integer getvZoneType() {
		return vZoneType;
	}

	public String getvZoneNo() {
		return vZoneNo;
	}

	public void setvZoneType(Integer vZoneType) {
		this.vZoneType = vZoneType;
	}

	public void setvZoneNo(String vZoneNo) {
		this.vZoneNo = vZoneNo;
	}

}
