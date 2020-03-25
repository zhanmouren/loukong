package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
import java.util.List;

/**
 *  部门和用户DTO
* @Author xiaozhan
* @Date 2020.03.25
*/
public class DeptAndUserDTO {
	    //部门Id
		private Integer depId;
		//部门名称
		private String depName;
		//部门编码
		private String depCode;
		//部门状态
		private Integer status;
		//存储userId的List
		private List<Integer> userList;
		//存储单个的userId
		private Integer userId;
		//创建人
		private String createBy;
		//创建时间
		private Timestamp createTime;
		//修改人
		private String updateBy;
		//修改时间
		private Timestamp updateTime;
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
		public List<Integer> getUserList() {
			return userList;
		}
		public void setUserList(List<Integer> userList) {
			this.userList = userList;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
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
