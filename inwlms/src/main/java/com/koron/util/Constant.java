package com.koron.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.ADOConnection;
import org.koron.ebs.util.field.EnumElement;
import org.springframework.stereotype.Component;

import com.koron.inwlms.bean.VO.common.SysConfigVO;
import com.koron.inwlms.service.common.impl.CommonServiceImpl;

@Component
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
	 * 操作成功码
	 */
	public static final int MESSAGE_INT_SUCCESS = 0;
	
	/**
	 * 操作成功提示
	 */
	public static final String MESSAGE_STRING_SUCCESS = "操作成功";
	
	/**
	 * 操作失败码
	 */
	public static final int MESSAGE_INT_ERROR = 1;
	
	/**
	 * 操作失败提示
	 */
	public static final String MESSAGE_STRING_ERROR = "操作失败";
	
	/**
	 * 未登录码
	 */
	public static final int MESSAGE_INT_NOLOGIN = 10000;
	
	/**
	 * 未登录提示
	 */
	public static final String MESSAGE_STRING_NOLOGIN = "未登录";
	
	/**
	 * 无操作权限码
	 */
	public static final int MESSAGE_INT_NOMODULE = 10001;
	
	/**
	 * 无操作权限提示
	 */
	public static final String MESSAGE_STRING_NOMODULE = "无操作权限";
	
	/**
	 * 密码错误码
	 */
	public static final int MESSAGE_INT_PWDERROR = 10002;
	
	/**
	 * 密码错误提示
	 */
	public static final String MESSAGE_STRING_PWDERROR = "密码错误";
	
	/**
	 * 分配100个错误码给文件转换
	 */
	public static final int MESSAGE_INT_FILE_TRANSFER_ERROR = 20001;
	
	/**
	 * 传参为空码
	 */
	public static final int MESSAGE_INT_NULL = 20101;
	
	/**
	 * 参数为空提示
	 */
	public static final String MESSAGE_STRING_NULL = "参数为空";
	
	/**
	 * 添加失败码
	 */
	public static final int MESSAGE_INT_ADDERROR = 20102;
	
	/**
	 * 添加失败提示
	 */
	public static final String MESSAGE_STRING_ADDERROR = "添加失败";
	
	/**
	 * 修改失败码
	 */
	public static final int MESSAGE_INT_EDITERROR = 20103;
	
	/**
	 * 修改失败提示
	 */
	public static final String MESSAGE_STRING_EDITERROR = "修改失败";
	
	/**
	 * 删除失败码
	 */
	public static final int MESSAGE_INT_DELERROR = 20104;
	
	/**
	 * 修改操作失败提示
	 */
	public static final String MESSAGE_STRING_DELERROR = "删除失败";
	
	/**
	 * 登录失败码
	 */
	public static final int MESSAGE_INT_LOGINERROR = 20105;
	
	/**
	 * 登录失败提示
	 */
	public static final String MESSAGE_STRING_LOGINERROR = "登录失败";
	
	/**
	 * 上传失败码
	 */
	public static final int MESSAGE_INT_UPLOADERROR = 20106;
	
	/**
	 * 上传失败提示
	 */
	public static final String MESSAGE_STRING_UPLOADERROR = "上传失败";
	
	/**
	 * 查询失败码
	 */
	public static final int MESSAGE_INT_SELECTERROR = 20107;
	
	/**
	 * 查询失败提示
	 */
	public static final String MESSAGE_STRING_SELECTERROR = "查询失败";
	
	/**
	 * 参数校验异常码
	 */
	public static final int MESSAGE_INT_PARAMS = 20108;
	
	/**
	 * 参数校验异常提示
	 */
	public static final String MESSAGE_STRING_PARAMS = "参数校验异常";
	
	/**
	 * 自定义表单增加辅助单位功能(在前台要自动加上辅助功能的脚本)
	 */
	public static final int HAS_ASSIST = 1000;
	// ////////////////////////接口错误说明/////////////


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
	
	/**
	 * 自定义层别缓存
	 */
	public static final HashMap<String, Integer> layerCache = new HashMap<>();
	/**
	 * 枚举缓存
	 */
	public final static HashMap<String, EnumElement<Object>> enumCache = new HashMap<>();
	
	
	/**
	 * 在windown服务器的文件存储路径
	 */
	public final static String WIN_FILE_PATH = "C:/inwlms_file";
	
	/**
	 * 在linux服务器的文件存储路径
	 */
	public final static String LINUX_FILE_PATH = "/usr/inms_file";
	
	/**
	 * 导出列表的最大条数
	 */
	public final static Integer DOWN_MAX_LIMIT = 1000;
	
	/**
	 * 时间类型分钟
	 */
	public final static Integer TIME_TYPE_MIN = 0;
	
	/**
	 * 时间类型小时
	 */
	public final static Integer TIME_TYPE_H = 1;
	
	/**
	 * 时间类型天
	 */
	public final static Integer TIME_TYPE_D = 2;
	
	/**
	 * 时间类型月
	 */
	public final static Integer TIME_TYPE_M = 3;
	
	/**
	 * 时间类型年
	 */
	public final static Integer TIME_TYPE_Y = 4;
	
	/**
	 * 最大口径参数前缀
	 */
	public final static String MAX_DN_PARAM = "QMADN";
	
	/**
	 * 最小口径参数前缀
	 */
	public final static String MIN_DN_PARAM = "QMIDN";
	
	/**
	 * 水表口径（大口径/小口径）判断值
	 */
	public final static int METER_DN_SIZE = 50;
	
	/**
	 * 正常表
	 */
	public final static String NOR_METER =  "W10184001";
	
	/**
	 * 监控表
	 */
	public final static String MONI_METER =  "W10184002";
	
	/**
	 * 消防表
	 */
	public final static String FS_METER =  "W10184003";
	
	/**
	 * 公司内部表
	 */
	public final static String COMN_METER =  "W10184004";
	
	/**
	 * 口径15
	 */
	public final static int DN_15 =  15;
	
	/**
	 * 口径20
	 */
	public final static int DN_20 =  20;
	
	/**
	 * 口径25
	 */
	public final static int DN_25 =  25;
	
	/**
	 * 口径40
	 */
	public final static int DN_40 =  40;
	
	/**
	 * 口径50
	 */
	public final static int DN_50 =  50;
	
	/**
	 * 口径80
	 */
	public final static int DN_80 =  80;
	
	/**
	 * 口径100
	 */
	public final static int DN_100 =  100;
	
	/**
	 * 口径150
	 */
	public final static int DN_150 =  150;
	
	/**
	 * 口径200
	 */
	public final static int DN_200 =  200;
	
	/**
	 * 口径300
	 */
	public final static int DN_300 =  300;
	
	/**
	 * 口径400
	 */
	public final static int DN_400 =  400;
	
	/**
	 * 口径600
	 */
	public final static int DN_600 =  600;
	
	/**
	 * 口径前缀
	 */
	public final static String DN_STR =  "DN";
	
	/**
	 * 评分等级:优
	 */
	public final static String SCORE_Y =  "优";
	
	/**
	 * 评分等级:良
	 */
	public final static String SCORE_L =  "良";
	
	/**
	 * 评分等级:差
	 */
	public final static String SCORE_C =  "差";
	
	/**
	 * 用水类别：居民
	 */
	public final static String USER_TYPE_PP =  "W101770001";
	
	/**
	 * 用水类别：工商业
	 */
	public final static String USER_TYPE_BS =  "W101770002";
	
	/**
	 * 用水类别：区域
	 */
	public final static String USER_TYPE_AR =  "W101770003";
	
	/**
	 * 用水类别：特种
	 */
	public final static String USER_TYPE_SP =  "W101770004";
	
	/**
	 * 用水类别：其他
	 */
	public final static String USER_TYPE_OT =  "W101770005";
	
}
