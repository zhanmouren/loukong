package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

/**
 * 集成日志bean
 * @author lzy
 * @Date 2020.04.01
 */
public class IntegrationLogDTO {
	//集成日志id
	private Integer id;
	//集成日志任务名
	private String jobName;
	//任务状态
	private String status;
	//开始时间
	private String startDate;
	//结束时间
	private String endDate;
	//创建人
	private String createBy;
	//创建时间
	private Timestamp createTime;
	//修改人
	private String updateBy;
	//修改时间
	private Timestamp updateTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	
}

