package com.koron.inwlms.bean.VO.leakageControl;

/**
 * 
 * @author 刘刚
 *
 */
public class AlertSchemeListVO {
	
	private Integer id;
	
	private String name;
	
	private String alarmType;
	
	private String objectType;
	
	private String alarmIndex;
	
	private String noticeType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getAlarmIndex() {
		return alarmIndex;
	}

	public void setAlarmIndex(String alarmIndex) {
		this.alarmIndex = alarmIndex;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	
	

}
