package com.koron.inwlms.bean.VO.sysManager;

import java.sql.Timestamp;

/**
 * 集成日志出参
 * @author lzy
 * @Date 2020.04.01
 */
public class IntegrationLogVO {
	
	//集成日志任务名
	private String jobName;
	//开始时间
	private Timestamp startDate;
	//结束时间
	private Timestamp endDate;
	//任务状态
	private String status;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
