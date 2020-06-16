package com.koron.permission.bean.VO;

/**
 * date: 2020/06/02
 * @author xiaozhan
 * description:角色-数据范围VO
 *
 */
public class TblRoleRangeValueVO {
	//id
	private Integer id;
	//角色编码
	private String roleCode;
	//目录
	private String catalogue;
	//值
	private String value;
	//角色名称
	private String roleName;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getId() {
		return id;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public String getCatalogue() {
		return catalogue;
	}
	public String getValue() {
		return value;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public void setCatalogue(String catalogue) {
		this.catalogue = catalogue;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
