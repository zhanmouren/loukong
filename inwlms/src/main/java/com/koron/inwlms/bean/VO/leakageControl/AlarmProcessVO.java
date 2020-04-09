package com.koron.inwlms.bean.VO.leakageControl;

import java.util.Date;

public class AlarmProcessVO {
	
	private Integer id;
	
	/**
	 * 报警ID
	 */
	private String warningCode;
	
	/**
	 * 处理人ID
	 */
	private String executorCode;
	
	/**
	 * 处理状态
	 */
	private String state;
	
	/**
	 * 预计完成时间
	 */
	private Date expectFinishTime;
	
	/**
	 * 推荐策略
	 */
	private String recommendStrategy;
	
	/**
	 * 负责人ID
	 */
	private String leadingCadre;
	
	/**
	 * 预计成本
	 */
	private Double projectCost;
	
	/**
	 * 预期节约水量
	 */
	private Double waterSave;
	
	/**
	 * 实际策略
	 */
	private String actualStrategy;
	
	/**
	 * 实际策略类型
	 */
	private String actualStrategyType;
	
	/**
	 * 实际成本
	 */
	private Double actualCost;
	
	/**
	 * 实际完成时间
	 */
	private Date actualFinishTime;
	
	/**
	 * 完成详情
	 */
	private String content;
	
	/**
	 * 创建人
	 */
	private String createBy;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改人
	 */
	private String updateBy;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
    /**
     * 报警信息详情
     */
	private String alarmContent;
	/**
	 * 报警类型
	 */
	private String alarmType;
	/**
	 * 对象类型
	 */
	private String objectType;
	/**
	 * 预警处理任务编码
	 */
	private String taskCode;
	
	
	
	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getExpectFinishTime() {
		return expectFinishTime;
	}

	public void setExpectFinishTime(Date expectFinishTime) {
		this.expectFinishTime = expectFinishTime;
	}

	public String getRecommendStrategy() {
		return recommendStrategy;
	}

	public void setRecommendStrategy(String recommendStrategy) {
		this.recommendStrategy = recommendStrategy;
	}

	public Double getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}

	public Double getWaterSave() {
		return waterSave;
	}

	public void setWaterSave(Double waterSave) {
		this.waterSave = waterSave;
	}

	public String getActualStrategy() {
		return actualStrategy;
	}

	public void setActualStrategy(String actualStrategy) {
		this.actualStrategy = actualStrategy;
	}

	public String getActualStrategyType() {
		return actualStrategyType;
	}

	public void setActualStrategyType(String actualStrategyType) {
		this.actualStrategyType = actualStrategyType;
	}

	public Double getActualCost() {
		return actualCost;
	}

	public void setActualCost(Double actualCost) {
		this.actualCost = actualCost;
	}

	public Date getActualFinishTime() {
		return actualFinishTime;
	}

	public void setActualFinishTime(Date actualFinishTime) {
		this.actualFinishTime = actualFinishTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getWarningCode() {
		return warningCode;
	}

	public void setWarningCode(String warningCode) {
		this.warningCode = warningCode;
	}

	public String getExecutorCode() {
		return executorCode;
	}

	public void setExecutorCode(String executorCode) {
		this.executorCode = executorCode;
	}

	public String getLeadingCadre() {
		return leadingCadre;
	}

	public void setLeadingCadre(String leadingCadre) {
		this.leadingCadre = leadingCadre;
	}
	
	
	
	
	

}
