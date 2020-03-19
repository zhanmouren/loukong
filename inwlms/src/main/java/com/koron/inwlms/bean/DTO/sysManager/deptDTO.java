package com.koron.inwlms.bean.DTO.sysManager;



/**
 * 1 添加部门bean
 * 2 修改部门bean
* @Author xiaozhan
* @Date 2020.03.17
*/

public class deptDTO {
	//部门Id
	private Integer depId;
	//部门名称
	private String depName;
	//部门编码
	private String depCode;
	//部门状态
	private Integer status;
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getDepCode() {
		return depCode;
	}
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
