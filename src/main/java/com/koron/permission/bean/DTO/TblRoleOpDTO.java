package com.koron.permission.bean.DTO;

import java.util.List;
/**
 * date: 2020/05/28
 * @author 小詹
 * description:角色-操作DTO
 *
 */
public class TblRoleOpDTO{
	//主键
    private Integer id;
    //角色编码
    private String roleCode;
    //操作编码
    private String opCode;
    //操作编码List
    private List<String> opCodeList;
	public Integer getId() {
		return id;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public String getOpCode() {
		return opCode;
	}
	public List<String> getOpCodeList() {
		return opCodeList;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public void setOpCodeList(List<String> opCodeList) {
		this.opCodeList = opCodeList;
	}
    
    
}
