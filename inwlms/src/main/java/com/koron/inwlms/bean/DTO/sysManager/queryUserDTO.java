package com.koron.inwlms.bean.DTO.sysManager;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 1.查询职员信息bean
 * 2.根据Id删除职员Bean
* @Author xiaozhan
* @Date 2020.03.17
*/

public class queryUserDTO extends BaseDTO{
	//职员Id
	private Integer userId;
	//职员名
	private String name;
	//部门名称
	private String depName;
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
