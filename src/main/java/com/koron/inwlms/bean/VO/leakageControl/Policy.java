package com.koron.inwlms.bean.VO.leakageControl;

import java.util.Date;

/**
 * 
 * @author 刘刚
 *
 */
public class Policy {

	private Integer id;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 策略方案名称
	 */
	private String name;
	/**
	 * 方案状态
	 */
	private String state;
	/**
	 * 方案描述
	 */
	private String content;
	/**
	 * 策略类型
	 */
	private String type;
	/**
	 * 分区类型
	 */
	private String areaType;
	/**
	 * 平均单个漏点检漏修漏费用
	 */
	private Double repairCost;
	/**
	 * 平均单个漏点漏量
	 */
	private Double missFlow;
	/**
	 * 投资回收期
	 */
	private Integer time;
	/**
	 * 任务预计完成时间
	 */
	private Integer day;
	/**
	 * 平均每户换表投资
	 */
	private Double changeTabInvest;
	/**
	 * 换表户数比例
	 */
	private Double tabUserRatio;
	/**
	 * 平均每户改网投资
	 */
	private Double changeNetInvest;
	/**
	 * 改网户数比例
	 */
	private Double netUserRatio;

	private String createBy;

	private Date createTime;

	private String updateBy;
	
	private Date updateTime;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public Double getRepairCost() {
		return repairCost;
	}

	public void setRepairCost(Double repairCost) {
		this.repairCost = repairCost;
	}

	public Double getMissFlow() {
		return missFlow;
	}

	public void setMissFlow(Double missFlow) {
		this.missFlow = missFlow;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Double getChangeTabInvest() {
		return changeTabInvest;
	}

	public void setChangeTabInvest(Double changeTabInvest) {
		this.changeTabInvest = changeTabInvest;
	}

	public Double getTabUserRatio() {
		return tabUserRatio;
	}

	public void setTabUserRatio(Double tabUserRatio) {
		this.tabUserRatio = tabUserRatio;
	}

	public Double getChangeNetInvest() {
		return changeNetInvest;
	}

	public void setChangeNetInvest(Double changeNetInvest) {
		this.changeNetInvest = changeNetInvest;
	}

	public Double getNetUserRatio() {
		return netUserRatio;
	}

	public void setNetUserRatio(Double netUserRatio) {
		this.netUserRatio = netUserRatio;
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
	
	
	
	
}
