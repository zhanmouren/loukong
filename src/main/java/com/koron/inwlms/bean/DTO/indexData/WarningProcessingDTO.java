package com.koron.inwlms.bean.DTO.indexData;

import java.security.Timestamp;

/**
 * 预警信息处理表DTO
 * @author xiaozhan
 *
 */
public class WarningProcessingDTO {
	private Integer id;
	//报警处理Code（任务编码)
	private String warningProcessingCode;
	//预警信息编码
	private String warningInfoCode;
	//处理人
	private String executorCode;
	//处理状态
	private String state;
	//预计完成时间
	private String expectFinishTime;
	//推荐策略
	private String recommendStrategy;
	//实际策略
	private String actualStrategy;
	//负责人
	private String leadingCadre;
	//预计成本
	private double projectCost;
	//预期节约水量
	private double waterSave;
	//实际策略类型
	private String actualStrategyType;
	//完成成本
	private String actualCost;
	//实际完成时间
	private String actualFinishTime;
	//完成详情
	private String content;
	
	  //创建时间
	   private Timestamp createTime;
	   //修改时间
	   private Timestamp updateTime;
	   //创建人
	   private String createBy;
	   //修改人
	   private String updateBy;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWarningProcessingCode() {
		return warningProcessingCode;
	}
	public String getWarningInfoCode() {
		return warningInfoCode;
	}
	public String getExecutorCode() {
		return executorCode;
	}
	public String getState() {
		return state;
	}
	public String getExpectFinishTime() {
		return expectFinishTime;
	}
	public String getRecommendStrategy() {
		return recommendStrategy;
	}
	public String getActualStrategy() {
		return actualStrategy;
	}
	public String getLeadingCadre() {
		return leadingCadre;
	}
	public double getProjectCost() {
		return projectCost;
	}
	public double getWaterSave() {
		return waterSave;
	}
	public String getActualStrategyType() {
		return actualStrategyType;
	}
	public String getActualCost() {
		return actualCost;
	}
	public String getActualFinishTime() {
		return actualFinishTime;
	}
	public String getContent() {
		return content;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setWarningProcessingCode(String warningProcessingCode) {
		this.warningProcessingCode = warningProcessingCode;
	}
	public void setWarningInfoCode(String warningInfoCode) {
		this.warningInfoCode = warningInfoCode;
	}
	public void setExecutorCode(String executorCode) {
		this.executorCode = executorCode;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setExpectFinishTime(String expectFinishTime) {
		this.expectFinishTime = expectFinishTime;
	}
	public void setRecommendStrategy(String recommendStrategy) {
		this.recommendStrategy = recommendStrategy;
	}
	public void setActualStrategy(String actualStrategy) {
		this.actualStrategy = actualStrategy;
	}
	public void setLeadingCadre(String leadingCadre) {
		this.leadingCadre = leadingCadre;
	}
	public void setProjectCost(double projectCost) {
		this.projectCost = projectCost;
	}
	public void setWaterSave(double waterSave) {
		this.waterSave = waterSave;
	}
	public void setActualStrategyType(String actualStrategyType) {
		this.actualStrategyType = actualStrategyType;
	}
	public void setActualCost(String actualCost) {
		this.actualCost = actualCost;
	}
	public void setActualFinishTime(String actualFinishTime) {
		this.actualFinishTime = actualFinishTime;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
}
