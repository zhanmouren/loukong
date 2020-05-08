package com.koron.inwlms.bean.DTO.leakageControl;

import java.util.Date;

/**
 * 
 * @author 刘刚
 *
 */
public class PartitionInvestDTO {

	private String type;
	
	/**
	 * 口径
	 */
	private Integer caliber;
	
	/**
	 * 投资费用
	 */
	private Double money;
	
	/**
	 * 其他设备投资费用
	 */
	private Double otherInvest;
	
	/**
	 * 工程费用系数
	 */
	private Double projectCost;
	
	/**
	 * 总投资
	 */
	private Double totalCost;
	
    private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;

	
	
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

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCaliber() {
		return caliber;
	}

	public void setCaliber(Integer caliber) {
		this.caliber = caliber;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getOtherInvest() {
		return otherInvest;
	}

	public void setOtherInvest(Double otherInvest) {
		this.otherInvest = otherInvest;
	}

	public Double getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}
	
	
}
