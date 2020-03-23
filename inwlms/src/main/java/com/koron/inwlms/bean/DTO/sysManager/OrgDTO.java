package com.koron.inwlms.bean.DTO.sysManager;


/**
 * 1 组织bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/

public class OrgDTO {
	//组织Id
	private Integer orgId;
	//组织Code
	private String orgCode;
	//组织名称
	private String orgName;
	//组织地址
	private String orgAddr;
	//组织类型
	private String orgType;
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgAddr() {
		return orgAddr;
	}
	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
   
}
