package com.koron.permission.bean.DTO;

import java.util.List;
/**
 * date: 2020/06/02
 * @author 小詹
 * description:角色-用户DTO
 *
 */
public class TblRoleUserDTO extends TblTenantDTO{
	
	//用户code
	private String userCode;
	//角色code
	private String roleCode;
	
	//用户CodeList
	private List<String> userCodeList;
	
	public List<String> getUserCodeList() {
		return userCodeList;
	}
	public void setUserCodeList(List<String> userCodeList) {
		this.userCodeList = userCodeList;
	}
	public String getUserCode() {
		return userCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
