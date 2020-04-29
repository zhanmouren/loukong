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
	
	/**
	 * 用水可疑关键字，
	 */
	public final static String USE_PRI1 = "公司,有限,农贸,商业,工商,制造,养殖,厂";
	
	/**
	 * 用水可疑关键字,特种类型
	 */
	public final static String USE_PRI2 = "公洗浴,洗车,饮料,酒吧,游泳,酒店,宾馆,歌舞,娱乐,茶艺,高尔夫,美容美发,桑拿,浴足,健身,水疗,休闲会所";

	/**
	 * 分区等级1
	 */
	public final static Integer RANK_F = 1;
	
	/**
	 * 分区等级2
	 */
	public final static Integer RANK_S = 2;
	
	/**
	 * 分区等级3
	 */
	public final static Integer RANK_T = 3;
	
	/**
	 * 报警指标-渗漏状态持续时间
	 */
	public final static String WARNINGINDEX_LEAKAGETIME = "leakageStateTime";
	/**
	 * 报警指标-数据连续缺失持续时间
	 */
	public final static String WARNINGINDEX_DATALOSS = "dataLossTime";
	/**
	 * 报警指标-与历史均值对比
	 */
	public final static String WARNINGINDEX_OLDCONTRAST = "oldContrast";
	/**
	 * 对象类型数据字典编码
	 */
	public final static String DATADICTIONARY_OBJECTTYPE = "L101170001";
	/**
	 * 任务状态未处理-数据字典
	 */
	public final static String DATADICTIONARY_TASKSTATUSUN = "L101110001";
	/**
	 * 任务状态处理中-数据字典
	 */
	public final static String DATADICTIONARY_TASKSTATUSON = "L101110002";
	/**
	 * 任务状态处理结束-数据字典
	 */
	public final static String DATADICTIONARY_TASKSTATUSOVER = "L101110003";
	/**
	 * 超限报警数据字典编码
	 */
	public final static String DATADICTIONARY_OVERRUN = "L101130002";
	/**
	 * 固定限值数据字典编码
	 */
	public final static String DATADICTIONARY_FIXLIMIT = "L101180001";
	/**
	 * 离线报警数据字典编码
	 */
	public final static String DATADICTIONARY_OFFLINE = "L101130004";
	/**
	 * 噪声报警数据字典编码
	 */
	public final static String DATADICTIONARY_NOISE = "L101130005";
	/**
	 * AI报警数据字典编码
	 */
	public final static String DATADICTIONARY_AI = "L101130003";
	/**
	 * 趋势变化报警数据字典编码
	 */
	public final static String DATADICTIONARY_TRENDCHANGE = "L101130001";
	/**
	 * 压力/流量监测点 数据字典编码
	 */
	public final static String DATADICTIONARY_PFPIONT = "L101170001";
	/**
	 * 噪声监测点 数据字典编码
	 */
	public final static String DATADICTIONARY_NOISEPIONT = "L101170002";
	/**
	 * DMA/PMA分区 数据字典编码
	 */
	public final static String DATADICTIONARY_DPZONE = "L101170003";
	/**
	 * 一级分区 数据字典编码
	 */
	public final static String DATADICTIONARY_FIRSTZONE = "L101170004";
	/**
	 * 二级分区 数据字典编码
	 */
	public final static String DATADICTIONARY_SECZONE = "L101170005";
	/**
	 * 正向瞬时流速-数据字典
	 */
	public final static String DATADICTIONARY_FORWARDSPEED = "L101190001";
	/**
	 * 反向瞬时流速-数据字典
	 */
	public final static String DATADICTIONARY_REVERSESPEED = "L101190002";
	/**
	 * 压力1-数据字典
	 */
	public final static String DATADICTIONARY_PRESSONE = "L101190003";
	/**
	 * 压力2-数据字典
	 */
	public final static String DATADICTIONARY_PRESSTWO = "L101190004";
	/**
	 * 最不利点压力-数据字典
	 */
	public final static String DATADICTIONARY_PRESSBAD = "L101190005";
	/**
	 * 状态-数据字典
	 */
	public final static String DATADICTIONARY_STATUS = "L101190007";
	/**
	 * 日总流量-数据字典
	 */
	public final static String DATADICTIONARY_DAYFLOW = "L101190008";
	/**
	 * 夜间最小流量-数据字典
	 */
	public final static String DATADICTIONARY_MINNIGFLOW = "L101190009";
	
	/**
	 * 正向瞬时流速-指标编码
	 */
	public final static String INDICATOR_FORWARDSPEED = "1";
	/**
	 * 反向瞬时流速-指标编码
	 */
	public final static String INDICATOR_REVERSESPEED = "2";
	/**
	 * 压力1-指标编码
	 */
	public final static String INDICATOR_PRESSONE = "3";
	/**
	 * 压力2-指标编码
	 */
	public final static String INDICATOR_PRESSTWO = "4";
	/**
	 * 最不利点压力-指标编码
	 */
	public final static String INDICATOR_PRESSBAD = "5";
	/**
	 * 状态-指标编码
	 */
	public final static String INDICATOR_STATUS = "6";
	/**
	 * 日总流量-指标编码
	 */
	public final static String INDICATOR_DAYFLOW = "7";
	/**
	 * 夜间最小流量-指标编码
	 */
	public final static String INDICATOR_MINNIGFLOW = "8";
	
	/**
	 * 分区报警
	 */
	public final static String OBJECTTYPE_ZONE = "0";
	/**
	 * 监测点报警
	 */
	public final static String OBJECTTYPE_POINT = "1";
	
	/**
	 * 事项子类型值域
	 */
	public final static String EVENTSUBTYPE = "10122";
	/**
	 * 最小夜间流量指标编码
	 */
	public final static String MINNIGHTFLOW = "";
	/**
	 * 基础指标编码
	 */
	public final static String BASE_INDIC = "WNMNOCM,FLMNOCM,SLMNOCM,DMMNOCM,VZMNOCM,WNYNOCM,FLYNOCM,SLYNOCM,DMYNOCM,VZYNOCM,WNMFTPL,FLMFTPL,SLMFTPL,DMMFTPL,VZMFTPL,WNYFTPL,FLYFTPL,SLYFTPL,DMYFTPL,VZYFTPL,WNMSA,FLMSA,SLMSA,DMMSA,VZMSA,WNMSA,FLMSA,SLYSA,DMYSA,VZYSA,WNMMRR,FLMMRR,SLMMRR,DMMMRR,WNYMRR,FLYMRR,SLYMRR,DMYMRR,WNMAMA,FLMAMA,SLMAMA,DMMAMA,WNMDCPL,FLMDCPL,SLMDCPL,WNYDCPL,FLYDCPL,SLYDCPL,WNMDCCA,FLMDCCA,SLMDCCA,WNYDCCA,FLYDCCA,SLYDCCA";

	/**
	 * 监测点指标编码
	 */
	public final static String MONI_INDIC = "FLDFDF,SLDFDF,DMDFDF,VZDFDF,MODFDF,FLDRF,SLDRF,DMDRF,VZDRF,MODRF,FLDFV,SLDFV,DMDFV,VZDFV,MODFV,FLDRV,SLDRV,DMDRV,VZDRV,MODRV,WNDFLOW,FLDFLOW,SLDFLOW,DMDFLOW,VZDFLOW,MODFLOW,WNDR,FLDR,SLDR,DMDR,VZDR,MODR,WNMR,FLMR,SLMR,DMMR,VZMR,MOMR,WNYR,FLYR,SLYR,DMYR,VZYR,MOYR,MODLF,MODNT";

	/**
	 * 分区监测指标编码
	 */
	public final static String ZONE_MONI_INDIC = "FLDPBP,SLDPBP,DMDPBP,FLMPBP,SLMPBP,DMMPBP,FLYPBP,SLYPBP,DMYPBP,FLDPAP,SLDPAP,DMDPAP,FLMPAP,SLMPAP,DMMPAP,FLYPAP,SLYPAP,DMYPAP,FLDDOR,SLDDOR,DMDDOR,FLMDOR,SLMDOR,DMMDOR,FLYDOR,SLYDOR,DMYDOR,WNDASP,FLDASP,SLDASP,DMDASP,WNMASP,FLMASP,SLMASP,DMMASP,WNYASP,FLYASP,SLYASP,DMYASP,WNDTTISRI,FLDTTISRI,SLDTTISRI,DMDTTISRI,WNDTTOSROVOF,FLDTTOSROVOF,SLDTTOSROVOF,DMDTTOSROVOF,WNDNFR,FLDNFR,SLDNFR,DMDNFR,VZDNFR";

	/**
	 * 水平衡指标编码
	 */
	public final static String BALANCE_INDIC = "WNMFWSSITDF,FLMFWSSITDF,DMMFWSSITDF,VZMFWSSITDF,WNYFWSSITDF,FLYFWSSITDF,DMYFWSSITDF,VZMFWSSITDF,WNMBMC,FLMBMC,DMMBMC,VZMBMC,WNYBMC,FLYBMC,DMYBMC,VZYBMC,WNMUMC,FLMUMC,DMMUMC,VZMUMC,WNYUMC,FLYUMC,DMYUMC,VZYUMC,WNMBUC,FLMBUC,DMMBUC,VZMBUC,WNYBUC,FLYBUC,DMYBUC,VZYBUC,WNMUUC,FLMUUC,DMMUUC,VZMUUC,WNYUUC,FLYUUC,DMYUUC,VZYUUC,WNMBC,FLMBC,DMMBC,VZMBC,WNYBC,FLYBC,DMYBC,VZYBC,WNMNRW,FLMNRW,DMMNRW,VZMNRW,WNYNRW,FLYNRW,DMYNRW,VZYNRW,WNMMC,FLMMC,DMMMC,VZMMC,WNYMC,FLYMC,DMYMC,VZYMC,WNMWL,FLMWL,DMMWL,VZMWL,WNYWL,FLYWL,DMYWL,VZYWL,WNMUFWC,FLMUFWC,DMMUFWC,VZMUFWC,WNYUFWC,FLYUFWC,DMYUFWC,VZYUFWC,WNMSRL,SLMSRL,DMMSRL,WNYSRL,SLYSRL,DMYSRL,WNMLAMB,FLMLAMB,DMMLAMB,WNYLAMB,FLYLAMB,DMYLAMB";

	/**
	 * 分区漏损指标编码
	 */
	public final static String ZONE_LOSS_INDIC = "WNDMNF,WNMMNF,WNYMNF,FLDMNF,FLMMNF,FLYMNF,SLDMNF,SLMMNF,SLYMNF,DMDMNF,DMMMNF,DMYMNF,VZDMNF,VZMMNF,VZYMNF,WNDMNFT,FLDMNFT,SLDMNFT,DMDMNFT,VZDMNFT,WNDMNFLF,WNMMNFLF,WNYMNFLF,FLDMNFLF,FLMMNFLF,FLYMNFLF,SLDMNFLF,SLMMNFLF,SLYMNFLF,DMDMNFLF,DMMMNFLF,DMYMNFLF,VZDMNFLF,VZMMNFLF,VZYMNFLF,WNDAZNP,FLDAZNP,SLDAZNP,DMDAZNP,WNMAZNP,FLMAZNP,SLMAZNP,DMMAZNP,WNDLNU,FLDLNU,SLDLNU,DMDLNU,WNMLNU,FLMLNU,SLMLNU,DMMLNU,WNYLNU,FLYLNU,SLYLNU,DMYLNU,WNDENMNF,FLDENMNF,SLDENMNF,DMDENMNF,WNMENMNF,FLMENMNF,SLMENMNF,DMMENMNF,WNDENMNF,FLDENMNF,SLDENMNF,DMYENMNF,WNDDNF,FLDDNF,SLDDNF,DMDDNF,WNDLA,FLDLA,SLDLA,DMDLA,VZDLA,WNMLA,FLMLA,SLMLA,DMMLA,VZMLA,WNYLA,FLYLA,SLYLA,DMYLA,VZYLA,WNDLRL,FLDLRL,SLDLRL,DMDLRL,VZDLRL,WNMLRL,FLMLRL,SLMLRL,DMMLRL,VZMLRL,WNYLRL,FLYLRL,SLYLRL,DMYLRL,VZYLRL,WNDLACA,FLDLACA,SLDLACA,DMDLACA,VZDLACA,WNMLACA,FLMLACA,SLMLACA,DMMLACA,VZMLACA,WNYLACA,FLYLACA,SLYLACA,DMYLACA,VZYLACA,WNDLAPL,FLDLAPL,SLDLAPL,DMDLAPL,VZDLAPL,WNMLAPL,FLMLAPL,SLMLAPL,DMMLAPL,VZMLAPL,WNYLAPL,FLYLAPL,SLYLAPL,DMYLAPL,VZYLAPL,WNDMNFTDF,FLDMNFTDF,SLDMNFTDF,DMDMNFTDF,VZDMNFTDF,WNMMNFTDF,FLMMNFTDF,SLMMNFTDF,DMMMNFTDF,VZMMNFTDF,WNYMNFTDF,FLYMNFTDF,SLYMNFTDF,DMYMNFTDF,VZYMNFTDF,WNDWLR,FLDWLR,SLDWLR,DMDWLR,VZDWLR,WNMWLR,FLMWLR,SLMWLR,DMMWLR,VZMWLR,WNYWLR,FLYWLR,SLYWLR,DMYWLR,VZYWLR,WNDLCA,FLDLCA,SLDLCA,DMDLCA,VZDLCA,WNMLCA,FLMLCA,SLMLCA,DMMLCA,VZMLCA,WNYLCA,FLYLCA,SLYLCA,DMYLCA,VZYLCA,WNDLPL,FLDLPL,SLDLPL,DMDLPL,VZDLPL,WNMLPL,FLMLPL,SLMLPL,DMMLPL,VZMLPL,WNYLPL,FLYLPL,SLYLPL,DMYLPL,VZYLPL,WNDWLPP,FLDWLPP,SLDWLPP,DMDWLPP,WNMWLPP,FLMWLPP,SLMWLPP,DMMWLPP,WNYWLPP,FLYWLPP,SLYWLPP,DMYWLPP,WNDWLAP,FLDWLAP,SLDWLAP,DMDWLAP,WNMWLAP,FLMWLAP,SLMWLAP,DMMWLAP,WNYWLAP,FLYWLAP,SLYWLAP,DMYWLAP,WNDNRR,FLDNRR,SLDNRR,DMDNRR,VZDNRR,WNMNRR,FLMNRR,SLMNRR,DMMNRR,VZMNRR,WNYNRR,FLYNRR,SLYNRR,DMYNRR,VZYNRR,WNDNRWPL,FLDNRWPL,SLDNRWPL,DMDNRWPL,VZDNRWPL,WNMNRWPL,FLMNRWPL,SLMNRWPL,DMMNRWPL,VZMNRWPL,WNYNRWPL,FLYNRWPL,SLYNRWPL,DMYNRWPL,VZYNRWPL,WNDNRWCA,FLDNRWCA,SLDNRWCA,DMDNRWCA,VZDNRWCA,WNMNRWCA,FLMNRWCA,SLMNRWCA,DMMNRWCA,VZMNRWCA,WNYNRWCA,FLYNRWCA,SLYNRWCA,DMYNRWCA,VZYNRWCA,WNDUCRFW,FLDUCRFW,SLDUCRFW,DMDUCRFW,VZDUCRFW,WNMUCRFW,FLMUCRFW,SLMUCRFW,DMMUCRFW,VZMUCRFW,WNYUCRFW,FLYUCRFW,SLYUCRFW,DMYUCRFW,VZYUCRFW,WNDUFWCPL,FLDUFWCPL,SLDUFWCPL,DMDUFWCPL,VZDUFWCPL,WNMUFWCPL,FLMUFWCPL,SLMUFWCPL,DMMUFWCPL,VZMUFWCPL,WNYUFWCPL,FLYUFWCPL,SLYUFWCPL,DMYUFWCPL,VZYUFWCPL,WNDUFWCCA,FLDUFWCCA,SLDUFWCCA,DMDUFWCCA,VZDUFWCCA,WNMUFWCCA,FLMUFWCCA,SLMUFWCCA,DMMUFWCCA,VZMUFWCCA,WNYUFWCCA,FLYUFWCCA,SLYUFWCCA,DMYUFWCCA,VZYUFWCCA";

	/**
	 * 表观漏损指标编码
	 */
	public final static String APPARENT_INDIC = "WNMAL,FLMAL,SLMAL,DMMAL,WNYAL,FLYAL,SLYAL,DMYAL,WNMALCA,FLMALCA,SLMALCA,DMMALCA,WNYALCA,FLYALCA,SLYALCA,DMYALCA,WNMALR,FLMALR,SLMALR,DMMALR,WNYALR,FLYALR,SLYALR,DMYALR,WNMALI,FLMALI,SLMALI,DMMALI,WNYALI,FLYALI,SLYALI,DMYALI,WNMNMRR,FLMNMRR,SLMNMRR,DMMNMRR,WNYNMRR,FLYNMRR,SLYNMRR,DMYNMRR,WNMTROMR,FLMTROMR,SLMTROMR,DMMTROMR,WNYTROMR,FLYTROMR,SLYTROMR,DMYTROMR,WNMIMRTR,FLMIMRTR,SLMIMRTR,DMMIMRTR,WNYIMRTR,FLYIMRTR,SLYIMRTR,DMYIMRTR,WNMOSMR,FLMOSMR,SLMOSMR,DMMOSMR,WNYOSMR,FLYOSMR,SLYOSMR,DMYOSMR,WNMIBIOMR,FLMIBIOMR,SLMIBIOMR,DMMIBIOMR,WNYIBIOMR,FLYIBIOMR,SLYIBIOMR,DMYIBIOMR,WNMNOLDM,FLMNOLDM,SLMNOLDM,DMMNOLDM,WNYNOLDM,FLYNOLDM,SLYNOLDM,DMYNOLDM,WNMNOSCM,FLMNOSCM,SLMNOSCM,DMMNOSCM,WNYNOSCM,FLYNOSCM,SLYNOSCM,DMYNOSCM,WNMNOZRM,FLMNOZRM,SLMNOZRM,DMMNOZRM,WNYNOZRM,FLYNOZRM,SLYNOZRM,DMYNOZRM";

	/**
	 * 爆管/渗漏指标编码
	 */
	public final static String LEAK_INDIC = "WNDBLFW,FLDBLFW,SLDBLFW,DMDBLFW,WNMBLFW,FLMBLFW,SLMBLFW,DMMBLFW,WNYBLFW,FLYBLFW,SLYBLFW,DMYBLFW,WNDNBFW,FLDNBFW,SLDNBFW,DMDNBFW,WNMNBFW,FLMNBFW,SLMNBFW,DMMNBFW,WNYNBFW,FLYNBFW,SLYNBFW,DMYNBFW,WNDNPLFW,FLDNPLFW,SLDNPLFW,DMDNPLFW,WNMNPLFW,FLMNPLFW,SLMNPLFW,DMMNPLFW,WNYNPLFW,FLYNPLFW,SLYNPLFW,DMYNPLFW,WNDBRFW,FLDBRFW,SLDBRFW,DMDBRFW,WNMBRFW,FLMBRFW,SLMBRFW,DMMBRFW,WNYBRFW,FLYBRFW,SLYBRFW,DMYBRFW,WNDLRFW,FLDLRFW,SLDLRFW,DMDLRFW,WNMLRFW,FLMLRFW,SLMLRFW,DMMLRFW,WNYLRFW,FLYLRFW,SLYLRFW,DMYLRFW";

}
