package com.koron.permission.bean.DTO;

/**
 * date: 2020/05/28
 * @author 小詹
 * description:角色范围DTO
 *
 */
public class TblRoleRangeValueDTO{
	//主键
	private Integer id;
	//角色编码
	private String roleCode;
	//目录编码
	private String catalogue;
	//具体的值
	private String value;
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
