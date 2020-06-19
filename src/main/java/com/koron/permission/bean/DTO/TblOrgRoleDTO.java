package com.koron.permission.bean.DTO;

import java.util.List;

/**
 * date: 2020/05/28
 * @author 小詹
 * description:角色-组织DTO
 *
 */
public class TblOrgRoleDTO{
	//主键
	private Integer id;
	//角色编码
	private String roleCode;
	//组织编码
	private String orgCode;
	//多个角色编码
	private List<String> roleCodeList;
	
	public List<String> getRoleCodeList() {
		return roleCodeList;
	}
	public void setRoleCodeList(List<String> roleCodeList) {
		this.roleCodeList = roleCodeList;
	}
	public Integer getId() {
		return id;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	

}
