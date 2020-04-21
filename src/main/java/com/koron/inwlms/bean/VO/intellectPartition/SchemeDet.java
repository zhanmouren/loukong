package com.koron.inwlms.bean.VO.intellectPartition;

import java.math.BigInteger;
import java.util.Date;

/**
 * 分区方案表
 * @author 刘刚
 *
 */
public class SchemeDet {

	private BigInteger id;
	/**
	 * 分区方案汇总编码
	 */
	private String totalSchemeCode;
	/**
	 * 分区个数
	 */
	private Integer regionNum;
	/**
	 * 经济性
	 */
	private Double ecomony;
	/**
	 * 结构度
	 */
	private Double tightness;
	/**
	 * 新增用户设备数量
	 */
	private Integer flowNum;
	/**
	 * 状态(0临时，1已保存)
	 */
	private Integer state;
	
	private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getTotalSchemeCode() {
		return totalSchemeCode;
	}

	public void setTotalSchemeCode(String totalSchemeCode) {
		this.totalSchemeCode = totalSchemeCode;
	}

	public Integer getRegionNum() {
		return regionNum;
	}

	public void setRegionNum(Integer regionNum) {
		this.regionNum = regionNum;
	}

	public Double getEcomony() {
		return ecomony;
	}

	public void setEcomony(Double ecomony) {
		this.ecomony = ecomony;
	}

	public Double getTightness() {
		return tightness;
	}

	public void setTightness(Double tightness) {
		this.tightness = tightness;
	}

	public Integer getFlowNum() {
		return flowNum;
	}

	public void setFlowNum(Integer flowNum) {
		this.flowNum = flowNum;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
