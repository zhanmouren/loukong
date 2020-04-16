package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

/**
 * 组织和部门的dTO
* @Author xiaozhan
* @Date 2020.03.17
*/
public class OrgAndDeptDTO {
	//组织Id
	 private  Integer orgId;
	 //组织Code
	 private String  orgCode;
	 //组织名称
	 private String   orgName;
	 //组织地址
	 private String   orgAddress;
	 //组织类型
	 private String  orgType;
	   //部门Id
		private Integer depId;
		//部门名称
		private String depName;
		//部门编码
		private String depCode;
		//部门状态
		private Integer depStatus;
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
		
		public Integer getDepStatus() {
			return depStatus;
		}
		public void setDepStatus(Integer depStatus) {
			this.depStatus = depStatus;
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
