package com.koron.permission.bean.DTO;


/**
 * date: 2020/06/02
 * @author xiaozhan
 * description:操作-用户DTO
 *
 */
public class TblOpDTO extends TblTenantDTO{
    //用户编码
	private String userCode;
	//操作编码
	private String opCode;
	//应用
	private String appCode;
	
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	
}
