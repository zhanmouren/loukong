package com.koron.inwlms.bean.VO.sysManager;

import java.sql.Timestamp;

/**
 * 组织的详细信息
 *
* @Author xiaozhan
* @Date 2020.04.02
*/
public class OrgVO {
	//组织Id
   private Integer orgId;
   //组织Code
   private String orgCode;
   //组织名称
   private String orgName;
   //组织地址
   private String orgAddress;
   //组织类型
   private String orgType;
   //创建人
   private String createBy;
   //创建时间
   private Timestamp createTime;
   //修改人
   private String updateBy;
   //修改时间
   private Timestamp updateTime;
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
public String getOrgAddress() {
	return orgAddress;
}
public void setOrgAddress(String orgAddress) {
	this.orgAddress = orgAddress;
}
public String getOrgType() {
	return orgType;
}
public void setOrgType(String orgType) {
	this.orgType = orgType;
}
public String getCreateBy() {
	return createBy;
}
public void setCreateBy(String createBy) {
	this.createBy = createBy;
}
public Timestamp getCreateTime() {
	return createTime;
}
public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
}
public String getUpdateBy() {
	return updateBy;
}
public void setUpdateBy(String updateBy) {
	this.updateBy = updateBy;
}
public Timestamp getUpdateTime() {
	return updateTime;
}
public void setUpdateTime(Timestamp updateTime) {
	this.updateTime = updateTime;
}
}
