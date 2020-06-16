package com.koron.permission.bean.DTO;

import java.util.List;

/**
 * date: 2020/06/02
 * @author xiaozhan
 * description:应用-操作DTO
 *
 */
public class TblAppOPDTO extends TblTenantDTO{
	 //主键id
	 private Integer id;
	 //应用编码
     private String appCode;
     //操作编码
     private String opCode;
     
     //操作编码List
     private List<String> opCodeList;
     
     
     
	public List<String> getOpCodeList() {
		return opCodeList;
	}
	public void setOpCodeList(List<String> opCodeList) {
		this.opCodeList = opCodeList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppCode() {
		return appCode;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
     
     
}
