package com.koron.inwlms.bean.VO.leakageControl;

import java.util.Date;

/**
 * 预警信息
 * @author 刘刚
 *
 */
public class AlarmMessageVO {

	private Integer id;
	
	private Integer r_ref;
	
	private String alarmNum;
	
	private Integer warningId;
	
	private Integer objectId;
	
	private Integer areaId;
	
	private String alarmType;
	
	private String content;
	
	private Date alarmTime;
	
	private Date createTime;
	
	private String updateBy;
	
	private String createBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getR_ref() {
		return r_ref;
	}

	public void setR_ref(Integer r_ref) {
		this.r_ref = r_ref;
	}

	public String getAlarmNum() {
		return alarmNum;
	}

	public void setAlarmNum(String alarmNum) {
		this.alarmNum = alarmNum;
	}

	public Integer getWarningId() {
		return warningId;
	}

	public void setWarningId(Integer warningId) {
		this.warningId = warningId;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
}
