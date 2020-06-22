package com.koron.permission.bean.DTO;

/**
 * date: 2020/05/28
 * @author 小詹
 * description:角色-操作DTO
 *
 */
public class TblRoleAndOPDTO {
	//应用
	private String app;
	//角色编码
	private String roleCode;
	//操作表类型(1菜单2按钮)
	private String flag;
	
	//树的类型
	private Integer type;
	//外键
	private String foreignKey;
	//用户
	private String userCode;
	
	
	public Integer getType() {
		return type;
	}
	public String getForeignKey() {
		return foreignKey;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getApp() {
		return app;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	

}
