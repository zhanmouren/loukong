package com.koron.util;

public class Constant {
	/**
	 * 登录用户在SESSION里的储存KEY
	 */
public static final String USER = "_user";
	/**
	 * 登陆微信号在SESSION里的储存KEY
	 */
	public static final String OPENID = "_openid";
	/**
	 * 验证码在Session里的存储KEY
	 */
	public static final String VALIDATECODE = "_validate_code";
	// ////////////////////////接口错误说明/////////////
	/**
	 * 操作成功
	 */
	public static final int MESSAGE_INT_SUCCESS = 0;
	/**
	 * 未登录
	 */
	public static final int MESSAGE_INT_NOLOGIN = 10000;
	/**
	 * 无操作权限
	 */
	public static final int MESSAGE_INT_NOMODULE = 10001;
	/**
	 * 密码错误
	 */
	public static final int MESSAGE_INT_PWDERROR = 10002;
	/**
	 * 参数校验异常
	 */
	public static final int MESSAGE_INT_PARAMS = 11002;
	/**MESSAGE_VALIDATION_ERROR
	 * 操作异常（比如不符合删除条件）
	 */
	public static final int MESSAGE_INT_OPERATION=11003;
	/**
	 * 分配100个错误码给文件转换
	 */
	public static final int MESSAGE_INT_FILE_TRANSFER_ERROR = 20001;
	/**
	 * 分配100个错误吗给数据库
	 */
	public static final int MESSAGE_DBFAIL = 20101;
	/**
	 * 自定义表单增加辅助单位功能(在前台要自动加上辅助功能的脚本)
	 */
	public static final int HAS_ASSIST = 1000;
	// ////////////////////////接口错误说明/////////////

}
