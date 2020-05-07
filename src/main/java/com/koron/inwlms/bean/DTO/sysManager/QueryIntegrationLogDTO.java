package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

import com.koron.inwlms.bean.DTO.common.BaseDTO;


/**
 * 查询集成日志入参
 * @author lzy
 * @Date 2020.04.01
 */

public class QueryIntegrationLogDTO extends BaseDTO{

	//任务名称
	private String jobName;
	//开始时间
	private String startDate;
	//结束时间
	private String endDate;
	//任务状态
	private String status;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
