package com.koron.inwlms.bean.VO.common;

import java.math.BigInteger;
import java.util.Date;

public class GdhRaw {
	
	private BigInteger id;
	
	private String code;
	
	private String station;
	
	private BigInteger time;
	
	private BigInteger end;
	
	private String value;
	
	private Integer datetime;
	
	private Date integratedtime;
	
	private BigInteger taskid;
	
	private Integer interval;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public BigInteger getTime() {
		return time;
	}

	public void setTime(BigInteger time) {
		this.time = time;
	}

	public BigInteger getEnd() {
		return end;
	}

	public void setEnd(BigInteger end) {
		this.end = end;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getDatetime() {
		return datetime;
	}

	public void setDatetime(Integer datetime) {
		this.datetime = datetime;
	}

	public Date getIntegratedtime() {
		return integratedtime;
	}

	public void setIntegratedtime(Date integratedtime) {
		this.integratedtime = integratedtime;
	}

	public BigInteger getTaskid() {
		return taskid;
	}

	public void setTaskid(BigInteger taskid) {
		this.taskid = taskid;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	
	

}
