package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 查询漏点列表DTO
 * @author csh
 *
 */
public class QueryLeakListDTO extends BaseDTO {

	private String startSize;
	
	private String endSize;
	
	private String repStartTime;
	
	private String repEndTime;
	
	private String repaieStartTime;
	
	private String repaieEndTime;
	
	private String description;

	public String getStartSize() {
		return startSize;
	}

	public void setStartSize(String startSize) {
		this.startSize = startSize;
	}

	public String getEndSize() {
		return endSize;
	}

	public void setEndSize(String endSize) {
		this.endSize = endSize;
	}

	public String getRepStartTime() {
		return repStartTime;
	}

	public void setRepStartTime(String repStartTime) {
		this.repStartTime = repStartTime;
	}

	public String getRepEndTime() {
		return repEndTime;
	}

	public void setRepEndTime(String repEndTime) {
		this.repEndTime = repEndTime;
	}

	public String getRepaieStartTime() {
		return repaieStartTime;
	}

	public void setRepaieStartTime(String repaieStartTime) {
		this.repaieStartTime = repaieStartTime;
	}

	public String getRepaieEndTime() {
		return repaieEndTime;
	}

	public void setRepaieEndTime(String repaieEndTime) {
		this.repaieEndTime = repaieEndTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
