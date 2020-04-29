package com.koron.inwlms.bean.DTO.common;

import java.util.Date;

/**
 * 指标维表
 * @author 刘刚
 *
 */
public class Indicator {
	private Integer id;
	/**
	 * 指标编码
	 */
	private String code;
	/**
	 * 指标名称
	 */
	private String name;
	/**
	 * 指标字段类型
	 */
	private Integer type;
	/**
	 * 指标字段长度
	 */
	private Integer length;
	/**
	 * 指标精度
	 */
	private Integer precision;
	/**
	 * 计量单位
	 */
	private String unit;
	/**
	 * 分区类型
	 */
	private String zoneType;
	/**
	 * 分区等级
	 */
	private String zoneRank;
	/**
	 * 统计周期
	 */
	private String timeType;
	/**
	 * 指标大类
	 */
	private String level1;
	/**
	 * 指标小类
	 */
	private String level2;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 计算公式
	 */
	private String formula;
	/**
	 * 指标来源
	 */
	private String origin;
	/**
	 * 备注
	 */
	private String remark;
	
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getZoneType() {
		return zoneType;
	}

	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

	public String getZoneRank() {
		return zoneRank;
	}

	public void setZoneRank(String zoneRank) {
		this.zoneRank = zoneRank;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
