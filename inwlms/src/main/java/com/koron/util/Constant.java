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
	 * 操作失败
	 */
	public static final int MESSAGE_INT_Failed = -1;
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

	/**
	 * 操作成功
	 */
	public static final String MESSAGE_STRING_SUCCESS = "message_success";

	/**
	 * 操作失败
	 */
	public static final String MESSAGE_STRING_ERROR = "message_error";

	/**
	 * 未登录
	 */
	public static final String MESSAGE_STRING_NOLOGIN = "message_no_login";
	/**
	 * 无操作权限
	 */
	public static final String MESSAGE_STRING_NOMODULE = "message_no_module";
	/**
	 * 传参错误
	 */
	public static final String MESSAGE_STRING_NOPARAM = "message_param_error";
	/**
	 * 传参为空(没有获取到参数)
	 */
	public static final String MESSAGE_STRING_NULL = "message_null_param";
	/**
	 * 查询失败
	 */
	public static final String MESSAGE_STRING_SELECTERROR = "message_select_error";
	/**
	 * 添加操作失败
	 */
	public static final String MESSAGE_STRING_ADDERROR = "message_add_error";
	/**
	 * 添加操作成功
	 */
	public static final String MESSAGE_STRING_ADDSUCCESS = "message_add_success";
	/**
	 * 修改操作失败
	 */
	public static final String MESSAGE_STRING_EDITERROR = "message_edit_error";
	/**
	 * 修改操作成功
	 */
	public static final String MESSAGE_STRING_EDITSUCCESS = "message_edit_success";
	/**
	 * 删除操作失败
	 */
	public static final String MESSAGE_STRING_DELERROR = "message_del_error";
	/**
	 * 删除操作成功
	 */
	public static final String MESSAGE_STRING_DELSUCCESS = "message_del_success";
	/**
	 * 登录失败
	 */
	public static final String MESSAGE_STRING_LOGINERROR = "message_login_error";
	/**
	 * 登入成功
	 */
	public static final String MESSAGE_STRING_LOGINSUCCESS = "message_login_success";
	/**
	 * 登入成功，请完善个人信息
	 */
	public static final String MESSAGE_STRING_LOGINFINSHINFO = "message_finish_userinfo";
	/**
	 * 退出失败
	 */
	public static final String MESSAGE_STRING_LOGINOUTERROR = "message_loginout_error";
	/**
	 * 上传成功
	 */
	public static final String MESSAGE_STRING_UPLOADSUCCESS = "message_upload_success";

	/**
	 * 上传失败
	 */
	public static final String MESSAGE_STRING_UPLOADERROR = "message_upload_error";

	/**
	 * 文件已存在
	 */
	public static final String MESSAGE_STRING_FILE_EXIST = "message_file_exist";
	/**
	 * 数据已存在
	 */
	public static final String MESSAGE_STRING_DATA_EXIST = "message_data_exist";
	/**
	 * supCode 不存在
	 */
	public static final String MESSAGE_STRING_SUPCODE_NOTEXIST = "message_supcode_notexist";
	/**
	 * sourceSr 不存在
	 */
	public static final String MESSAGE_STRING_SOURCESR_NOTEXIST = "message_sourcesr_notexist";

	/**
	 * installationId 不存在
	 */
	public static final String MESSAGE_STRING_INSID_NOTEXIST = "message_insid_notexist";
	/**
	 * SSS No.不存在
	 */
	public static final String MESSAGE_STRING_SSSNO_NOTEXIST = "message_sssno_notexist";
	/**
	 * flometer 不存在
	 */
	public static final String MESSAGE_STRING_FLOMETER_NOTEXIST = "message_flometer_notexist";
	/**
	 * group 不存在
	 */
	public static final String MESSAGE_STRING_GROUP_NOTEXIST = "message_group_notexist";
	/**
	 * DMA不存在
	 */
	public static final String MESSAGE_STRING_DMA_NOTEXIST = "message_dma_notexist";
	/**
	 * data logger ref 不存在
	 */
	public static final String MESSAGE_STRING_DATALOGGERREF_NOTEXIST = "message_dataloggerref_notexist";
	/**
	 * 同步岗位错误
	 */
	public static final String MESSAGE_STRING_SYNCPOSTERROR = "message_syncpost_error";
	/**
	 * 执行sql操作成功
	 */
	public static final String MESSAGE_STRING_EXECUTESUCCESS = "message_execute_success";
	/**
	 * 执行sql操作失败
	 */
	public static final String MESSAGE_STRING_EXECUTEERROR = "message_execute_error";
	/**
	 * AD验证失败
	 */
	public static final String MESSAGE_STRING_CHECKADERROR = "message_checkad_error";
	/**
	 * 没有职位信息
	 */
	public static final String MESSAGE_STRING_NOPOST = "message_nopost_error";
	/**
	 * 密码错误
	 */
	public static final String MESSAGE_STRING_PASSWORDERROR = "message_password_error";
	/**
	 * 新密码不一致
	 */
	public static final String MESSAGE_STRING_PASSWORDNOSAME = "message_password_onsame";
	/**
	 * 执行sql成功返回码
	 */
	public static final int EXECUTE_SQL_SUCCESS = 1;

	/**
	 * 执行sql失败返回码
	 */
	public static final int EXECUTE_SQL_ERROR = 0;

	/**
	 * 登录用户key
	 */
	public static final String LOGIN_USER = "LOGIN_USER";

	/**
	 * 系统语言版本
	 */
	public static final String LANGUAGE_EN = "en";  //英文

	public static final String LANGUAGE_HANT = "Hant"; //繁体

	public static final String LANGUAGE_HANS = "Hans"; //中文

	/**
	 * logger refence数据已存在
	 */
	public static final String MESSAGE_STRING_LOGGERREF_EXIST = "message_loggerref_exist";

	/**
	 * logger id数据已存在
	 */
	public static final String MESSAGE_STRING_LOGGERID_EXIST = "message_loggerid_exist";

	/**
	 * facility数据不存在
	 */
	public static final String MESSAGE_STRING_FACILITY_NOTEXIST = "message_facility_notexist";

}
