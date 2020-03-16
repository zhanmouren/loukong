package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 一级分区漏损列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class queryFZoneLossListDTO {

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
	
	/**
	 * 一级分区
	 */
	public String firstLevelZone;
	
	/**
	 * 分区编码
	 */
	public String zoneNo;
	
	/**
	 * 警报状态（0：不报警，1：报警）
	 */
	public Integer alarmStatus;
}
