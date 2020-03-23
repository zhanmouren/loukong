package com.koron.inwlms.bean.DTO.sysManager;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 1.查询职员信息bean
 * 2.根据Id删除职员Bean
* @Author xiaozhan
* @Date 2020.03.17
*/

public class QueryUserDTO extends BaseDTO{
	//职员Id
	private Integer userId;
	//职员名
	private String name;
	//部门名称
	private String depName;
	
	  //是否停用(0 启用，-1 停用)
	  private Integer whetUse;
	  //状态（0 在职，-1 离职）
	  private Integer status;
	public Integer getWhetUse() {
		return whetUse;
	}
	public void setWhetUse(Integer whetUse) {
		this.whetUse = whetUse;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
}
