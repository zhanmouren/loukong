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
	private String pointCode;
	/**
	 * 报警编码
	 */
	private String code;
	/**
	 * 预警方案ID
	 */
	private String schemeCode;
	/**
	 * 对象编码
	 */
	private String objectCode;
	/**
	 * 分区编码
	 */
	private String areaCode;
	/**
	 * 报警类型
	 */
	private String alarmType;
	/**
	 * 报警详情
	 */
	private String content;
	/**
	 * 报警时间
	 */
	private Date alarmTime;
	
	private Date createTime;
	
	private String updateBy;
	
	private String createBy;
	/**
	 * 对象类型
	 */
	private String ObjectType;
	/**
	 * 预警方案名称
	 */
	private String alarmSchemeName;
	/**
	 * 预警处理任务编码
	 */
	private String taskCode;
	/**
	 * 预警处理任务状态
	 */
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


	public String getPointCode() {
		return pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
	
}
