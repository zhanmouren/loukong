package com.koron.permission.bean.VO;

import java.sql.Timestamp;

/**
 * date: 2020/05/28
 * @author 小詹
 * description:角色表VO
 *
 */
public class TblRoleVO {
	    //主键id
		private Integer roleId;
		//角色标识编码
		private String roleCode;
		//状态
		private Integer roleStatus;
		//权重
		private Integer roleWeight;
		//角色名称
		private String roleName;
		//应用编码
		private String app;			 
		
		//创建人
	    private String creator;
	    //修改人
	    private String modifier;
	    //创建时间
	    private Timestamp create_time;
	    //修改时间
	    private Timestamp modify_time;
		public Integer getRoleId() {
			return roleId;
		}
		public String getRoleCode() {
			return roleCode;
		}
		public Integer getRoleStatus() {
			return roleStatus;
		}
		public Integer getRoleWeight() {
			return roleWeight;
		}
		public String getRoleName() {
			return roleName;
		}
		public String getApp() {
			return app;
		}
		public String getCreator() {
			return creator;
		}
		public String getModifier() {
			return modifier;
		}
		public Timestamp getCreate_time() {
			return create_time;
		}
		public Timestamp getModify_time() {
			return modify_time;
		}
		public void setRoleId(Integer roleId) {
			this.roleId = roleId;
		}
		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
		}
		public void setRoleStatus(Integer roleStatus) {
			this.roleStatus = roleStatus;
		}
		public void setRoleWeight(Integer roleWeight) {
			this.roleWeight = roleWeight;
		}
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}
		public void setApp(String app) {
			this.app = app;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}
		public void setModifier(String modifier) {
			this.modifier = modifier;
		}
		public void setCreate_time(Timestamp create_time) {
			this.create_time = create_time;
		}
		public void setModify_time(Timestamp modify_time) {
			this.modify_time = modify_time;
		}
	    
        
}
