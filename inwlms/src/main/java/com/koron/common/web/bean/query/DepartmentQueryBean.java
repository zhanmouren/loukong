package com.koron.common.web.bean.query;

import com.koron.common.bean.query.BaseQueryBean;

public class DepartmentQueryBean extends BaseQueryBean{
	/**
	 * 组织名称
	 */
	private String name;
	
	/**
	 * 组织状态1部门,2水司4水厂8自来水16污水
	 */
	private Integer flag;
	
	private Integer departmentid;

	private Long maxSeq;
	
	private Long minSeq;
	
	public String getName() {
		return name;
	}

	public DepartmentQueryBean setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getFlag() {
		return flag;
	}

	public DepartmentQueryBean setFlag(Integer flag) {
		this.flag = flag;
		return this;
	}

	public Long getMinSeq() {
		return minSeq;
	}

	public void setMinSeq(Long minSeq) {
		this.minSeq = minSeq;
	}

	public Long getMaxSeq() {
		return maxSeq;
	}

	public void setMaxSeq(Long maxSeq) {
		this.maxSeq = maxSeq;
	}

	public Integer getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(Integer departmentid) {
		this.departmentid = departmentid;
	}
	
	
}
