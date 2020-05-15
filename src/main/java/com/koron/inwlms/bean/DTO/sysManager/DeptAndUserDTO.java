package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
import java.util.List;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 *  部门和用户DTO
* @Author xiaozhan
* @Date 2020.03.25
*/
public class DeptAndUserDTO extends BaseDTO{
        private Integer id;
	   //人员名称
		private String name;
		//登录名称
		private String loginName;
		//是否停用
		private Integer whetUse;
		//是否是主部门(0代表是主部门，1代表不是主部门)
		private Integer mainDeptFlag;
	     //存储userCode的List
	    private List<String> userCodeList;
	    //单个的userCode
	    private String userCode;
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
		
		public Integer getMainDeptFlag() {
			return mainDeptFlag;
		}
		public void setMainDeptFlag(Integer mainDeptFlag) {
			this.mainDeptFlag = mainDeptFlag;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLoginName() {
			return loginName;
		}
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}
		public Integer getWhetUse() {
			return whetUse;
		}
		public void setWhetUse(Integer whetUse) {
			this.whetUse = whetUse;
		}
		public String getUserCode() {
			return userCode;
		}
		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}
		public List<String> getUserCodeList() {
			return userCodeList;
		}
		public void setUserCodeList(List<String> userCodeList) {
			this.userCodeList = userCodeList;
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
