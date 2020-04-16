package com.koron.inwlms.bean.VO.leakageControl;

import java.util.Date;

/**
 * 
 * @author 刘刚
 *
 */
public class EventInfo {
	
	private Integer id;
	/**
	 * 事项编码
	 */
	private String code;
	/**
	 * 事项名称
	 */
	private String name;
	/**
	 * 事项类型
	 */
	private String type;
	/**
	 * 事项子类型
	 */
	private String subtype;
	/**
	 * 对象编码
	 */
	private String objectCode;
	/**
	 * 第一分区
	 */
	private String firstPartition;
	/**
	 * 第二分区
	 */
	private String secondPartition;
	/**
	 * DMA分区
	 */
	private String dmaCode;
	/**
	 * 发生时间
	 */
	private Date happenTime;
	/**
	 * 事项详情
	 */
	private String content;
	
	private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getFirstPartition() {
		return firstPartition;
	}

	public void setFirstPartition(String firstPartition) {
		this.firstPartition = firstPartition;
	}

	public String getSecondPartition() {
		return secondPartition;
	}

	public void setSecondPartition(String secondPartition) {
		this.secondPartition = secondPartition;
	}

	public String getDmaCode() {
		return dmaCode;
	}

	public void setDmaCode(String dmaCode) {
		this.dmaCode = dmaCode;
	}

	public Date getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
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
	
	

}
