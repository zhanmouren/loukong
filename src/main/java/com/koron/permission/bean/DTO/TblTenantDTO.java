package com.koron.permission.bean.DTO;

import com.koron.common.bean.StaffBean;

/**
 * date: 2020/05/28
 * @author 小詹
 * description:租户DTO
 *
 */
public class TblTenantDTO{
	
	//租户Code
	private String _tenantCode;
	//应用app
	private String _app;
	//token
	private String _token;
	//用户Code
	private String _userCode;
	
	public String get_userCode() {
		return _userCode;
	}
	public void set_userCode(String _userCode) {
		this._userCode = _userCode;
	}
	public String get_tenantCode() {
		return _tenantCode;
	}
	public String get_app() {
		return _app;
	}
	public String get_token() {
		return _token;
	}
	public void set_tenantCode(String _tenantCode) {
		this._tenantCode = _tenantCode;
	}
	public void set_app(String _app) {
		this._app = _app;
	}
	public void set_token(String _token) {
		this._token = _token;
	}
	

}
