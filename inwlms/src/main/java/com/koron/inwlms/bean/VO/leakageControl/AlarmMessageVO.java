package com.koron.inwlms.bean.VO.leakageControl;

import java.util.Date;

/**
 * 预警信息
 * @author 刘刚
 *
 */
public class AlarmMessageVO {

	private Integer id;
	
	/**
	 * 主报警ID
	 */
	private Integer r_ref;
	/**
	 * 报警编码
	 */
	private String alarmNum;
	/**
	 * 预警方案ID
	 */
	private Integer warningId;
	
	private Integer objectId;
	
	private Integer areaId;
	
	private String alarmType;
	
	private String content;
	/**
	 * 报警时间
	 */
	private Date alarmTime;
	
	private Date createTime;
	
	private String updateBy;
	
	private String createBy;
	
	private String ObjectType;
	
	private String alarmSchemeName;
	
	private String taskCode;
	
	private String taskState;
	
	

	public String getObjectType() {
		return ObjectType;
	}

	public void setObjectType(String objectType) {
		ObjectType = objectType;
	}

	public String getAlarmSchemeName() {
		return alarmSchemeName;
	}

	public void setAlarmSchemeName(String alarmSchemeName) {
		this.alarmSchemeName = alarmSchemeName;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

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
