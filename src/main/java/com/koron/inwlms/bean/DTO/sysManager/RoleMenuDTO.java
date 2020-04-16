package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
import java.util.List;

/**
 * 角色菜单DTO
* @Author xiaozhan
* @Date 2020.03.23
*/
public class RoleMenuDTO {
	//用户Code
	private String userCode;
	//角色菜单List
	private List<RoleMenuDTO> roleMenuList;
	//角色code
    private String roleCode;
    //菜单code
    private String moduleCode;
    //菜单CodeList
    private List<String> moduleCodeList;
    //单个的操作权限
    private Integer op;
    //操作列表
    private List<String> opList;
  //创建人
  	private String createBy;
  	//创建时间
  	private Timestamp createTime;
  	//修改人
  	private String updateBy;
  	//修改时间
  	private Timestamp updateTime;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public List<String> getModuleCodeList() {
		return moduleCodeList;
	}
	public void setModuleCodeList(List<String> moduleCodeList) {
		this.moduleCodeList = moduleCodeList;
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
	public Integer getOp() {
		return op;
	}
	public void setOp(Integer op) {
		this.op = op;
	}
	public List<RoleMenuDTO> getRoleMenuList() {
		return roleMenuList;
	}
	public void setRoleMenuList(List<RoleMenuDTO> roleMenuList) {
		this.roleMenuList = roleMenuList;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public List<String> getOpList() {
		return opList;
	}
	public void setOpList(List<String> opList) {
		this.opList = opList;
	}
    
}
