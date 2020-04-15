package com.koron.inwlms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicyDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicySchemeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicySettingDTO;
import com.koron.inwlms.bean.DTO.leakageControl.ProcessingStatisticsDTO;
import com.koron.inwlms.bean.DTO.leakageControl.TreatmentEffectDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByType;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByTypeVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageReturnVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeScheme;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.AlertSchemeListVO;
import com.koron.inwlms.bean.VO.leakageControl.DataDicRelationVO;
import com.koron.inwlms.bean.VO.leakageControl.EventInfo;
import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;
import com.koron.inwlms.bean.VO.leakageControl.Policy;
import com.koron.inwlms.bean.VO.leakageControl.PolicySchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsVO;
import com.koron.inwlms.bean.VO.leakageControl.TreatmentEffectVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeDateVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.service.leakageControl.AlarmMessageService;
import com.koron.inwlms.service.leakageControl.AlarmProcessService;
import com.koron.inwlms.service.leakageControl.EconomicIndicatorServiceImpl;
import com.koron.inwlms.service.leakageControl.EventInfoService;
import com.koron.inwlms.service.leakageControl.PolicyService;
import com.koron.inwlms.service.leakageControl.StatisticalAnalysisService;
import com.koron.inwlms.service.leakageControl.WarningSchemeService;
import com.koron.inwlms.service.sysManager.impl.UserServiceImpl;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 漏损控制Controller层
 * @author 刘刚
 * 
 */

@Controller
@Api(value = "leakageControlController", description = "漏损控制Controller")
@RequestMapping(value = "/leakageControlController")
public class LeakageControlController {
	
	@Autowired
	private AlarmMessageService ams;
	@Autowired
	private AlarmProcessService aps;
	@Autowired
	private WarningSchemeService wss;
	@Autowired
	private StatisticalAnalysisService sas;
	@Autowired
	private EventInfoService eis;
	@Autowired
	private PolicyService ps;

	@RequestMapping(value = "/queryAlarmMessage.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询预警信息接口", notes = "查询预警信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmMessage(@RequestBody WarningInfDTO warningInfDTO) {
		MessageBean<AlarmMessageReturnVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AlarmMessageReturnVO.class);
		
		if(warningInfDTO.getStartTime() == null) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!开始时间为空");
	        return msg.toJson();
		}
		if(warningInfDTO.getEndTime() == null) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!结束时间为空");
	        return msg.toJson();
		}
		
		if(warningInfDTO.getFirstPartion().equals("全部")) {
			warningInfDTO.setAreaCode(null); 
		}
		//TODO 通过分区编码查询出所属的所有0级分区编码，将参数分区编码设置为0级分区编码
		
		try {
			List<AlarmMessageVO> alarmMessageList = ADOConnection.runTask(ams, "queryAlarmMessage", List.class, warningInfDTO);
			if(alarmMessageList != null && alarmMessageList.size() != 0) {
				
				AlarmMessageReturnVO alarmMessageReturnVO = new AlarmMessageReturnVO();
				alarmMessageReturnVO.setAlarmMessageList(alarmMessageList);
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(alarmMessageReturnVO);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("预警信息无");
			}
		}catch(Exception e){
			//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警信息失败");
		}
			
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryAlarmMessageByPointCode.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询主报警ID下的预警信息接口", notes = "查询主报警ID下的预警信息接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmMessageByPointCode(String code) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(code == null || code.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("主报警ID为空");
	        return msg.toJson();
		}
		
		try {
			List<AlarmMessageVO> alarmMessageList = ADOConnection.runTask(ams, "queryAlarmMessageByPointCode", List.class, code);
			if(alarmMessageList != null && alarmMessageList.size() != 0) {
				msg.setData(alarmMessageList);
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无信息");
			}
		}catch(Exception e) {
			//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警信息失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryAlarmProcess.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询预警信息处理任务接口", notes = "查询预警信息处理任务接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmProcess(@RequestBody AlarmProcessDTO alarmProcessDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(alarmProcessDTO.getStartTime() == null || alarmProcessDTO.getStartTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!开始时间为空");
	        return msg.toJson();
		}
		if(alarmProcessDTO.getEndTime() == null || alarmProcessDTO.getEndTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!结束时间为空");
	        return msg.toJson();
		}
		
		//查询参数设置调整
		try {
			List<AlarmProcessVO> alarmProcessList = ADOConnection.runTask(aps,"queryAlarmProcess",List.class,alarmProcessDTO);
			if(alarmProcessList != null && alarmProcessList.size() != 0) {
				//TODO 转化枚举类型key为value
				
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(alarmProcessList);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("未查询到数据");
			}
			
		}catch(Exception e) {
			//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警信息处理任务失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/updateAlarmProcess.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改预警信息处理任务接口", notes = "修改预警信息处理任务接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateAlarmProcess(@RequestBody AlarmProcessVO alarmProcessVO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(alarmProcessVO.getTaskCode() == null || alarmProcessVO.getTaskCode().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!任务编码为空");
	        return msg.toJson();
		}
		
		try {
			Integer num = ADOConnection.runTask(aps,"updateAlarmProcess",Integer.class,alarmProcessVO);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("未修改数据");
			}
			
		}catch(Exception e) {
			//修改失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("修改预警信息处理任务失败");
		}
		
		return msg.toJson();
		
	}
	
	@RequestMapping(value = "/addAlarmProcess.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加预警信息处理任务接口", notes = "添加预警信息处理任务接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addAlarmProcess(@RequestBody AlarmProcessVO alarmProcessVO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		//TODO 自动产生任务编码
		String code = new Date()+"";
		alarmProcessVO.setTaskCode(code);
		//添加预警信息处理任务
		try {
			Integer num = ADOConnection.runTask(aps, "addAlarmProcess",Integer.class,alarmProcessVO);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("未添加数据");
			}
		}catch(Exception e) {
			//添加失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("添加预警信息处理任务失败");
		}
		
		return msg.toJson();
		
	}
	
	@RequestMapping(value = "/deleteAlarmProcess.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除预警信息处理任务接口", notes = "删除预警信息处理任务接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteAlarmProcess(String taskCode) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(taskCode == null || taskCode.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!任务编码为空");
	        return msg.toJson();
		}
		
		try {
			Integer num = ADOConnection.runTask(aps, "deleteAlarmProcess",Integer.class,taskCode);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("改预警信息处理任务Id不存在数据");
			}
			
		}catch(Exception e) {
			//删除失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("删除预警信息处理任务失败");
		}
		return msg.toJson();
		
	}
	
	@RequestMapping(value = "/queryWarningSchemeList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "预警方案查询接口", notes = "预警方案查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryWarningSchemeList(@RequestBody WarningSchemeDTO warningSchemeDTO) {
		
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		//TODO 判断分区、状态、对象类型等是否为全部
		
		List<WarningSchemeVO> warningSchemeList = new ArrayList<>();
		List<AlertSchemeListVO> alertSchemeListVO = new ArrayList<>();
		//查询预警方案表信息
		try {
			warningSchemeList = ADOConnection.runTask(wss, "queryWarningScheme", List.class,warningSchemeDTO);
			if(warningSchemeList == null || warningSchemeList.size() == 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("改条件下未查询到数据");
				return msg.toJson();
			}
			
		}catch(Exception e) {
			
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警方案失败");
	        return msg.toJson();
		}
		
		//通过预警方案ID查询通知方案
		for(WarningSchemeVO warningScheme : warningSchemeList) {
			AlertSchemeListVO alertSchemeVO = new AlertSchemeListVO();
			//拼接通知方式字段
			String noticeType = "";
			try {
				List<AlertNoticeScheme> alertNoticeSchemeList = ADOConnection.runTask(wss, "queryAlertNoticeSchemeByWarningId",List.class,warningScheme.getCode());
				if(alertNoticeSchemeList != null && alertNoticeSchemeList.size() != 0) {
					for(AlertNoticeScheme alertNoticeScheme : alertNoticeSchemeList) {
						//TODO 通过通知方式的key查询其值
						
						//拼装返回格式
						noticeType = noticeType + alertNoticeScheme.getType() + "/";
					}
					
					alertSchemeVO.setNoticeType(noticeType);
					alertSchemeVO.setAlarmType(warningScheme.getAlarmType());
					alertSchemeVO.setName(warningScheme.getName());
					alertSchemeVO.setAlarmIndex(warningScheme.getAlarmIndex());
					alertSchemeVO.setId(warningScheme.getId());
					alertSchemeVO.setObjectType(warningScheme.getObjectType());
					alertSchemeListVO.add(alertSchemeVO);					
				}	
			}catch(Exception e) {
				//添加失败
		     	msg.setCode(Constant.MESSAGE_INT_ERROR);
		        msg.setDescription("查询通知方案失败");
		        return msg.toJson();
			}
			
		}
		msg.setCode(Constant.MESSAGE_INT_SUCCESS);
		msg.setData(alertSchemeListVO);
		
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryWarningScheme.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "预警方案查询接口", notes = "预警方案查询接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryWarningScheme(String code) {
		MessageBean<WarningSchemeDateVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, WarningSchemeDateVO.class);
		if(code == null || code.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("预警方案编码为空");
			return msg.toJson();
		}
		WarningSchemeDateVO warningSchemeDateVO = new WarningSchemeDateVO();
		WarningSchemeDTO warningSchemeDTO = new WarningSchemeDTO();
		warningSchemeDTO.setCode(code);
		try {
			//查询预警方案信息
			List<WarningSchemeVO> warningSchemeList = ADOConnection.runTask(wss, "queryWarningScheme", List.class,warningSchemeDTO);
			if(warningSchemeList != null && warningSchemeList.size() != 0) {
				List<AlarmRuleDTO> alarmRuleDTOLst = ADOConnection.runTask(wss, "queryAlarmRuleByAlarmCode", List.class,code);
				warningSchemeDateVO.setAlarmRuleList(alarmRuleDTOLst);
				warningSchemeDateVO.setWarningScheme(warningSchemeList.get(0));
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(warningSchemeDateVO);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("未有预警方案信息");
			}
			
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警方案失败");
	        return msg.toJson();
		}
		
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/addWarningScheme.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "预警方案添加接口", notes = "预警方案添加接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addWarningScheme(@RequestBody WarningSchemeDTO warningSchemeDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		//判断参数是否为空
		if(warningSchemeDTO.getName() == null  || warningSchemeDTO.getName().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("预警方案名称为空");
			return msg.toJson();
		}
		if(warningSchemeDTO.getState() == null || warningSchemeDTO.getState().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("预警方案状态为空");
			return msg.toJson();
		}
		if(warningSchemeDTO.getAlarmRuleList() == null || warningSchemeDTO.getAlarmRuleList().size() == 0) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("预警方案规则为空");
			return msg.toJson();
		}
		if(warningSchemeDTO.getAlarmType() == null || warningSchemeDTO.getAlarmType().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("预警方案预警类型为空");
			return msg.toJson();
		}
		if(warningSchemeDTO.getObjectType() == null || warningSchemeDTO.getObjectType().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("预警方案对象类型为空");
			return msg.toJson();
		}
		
		//TODO 产生一个方案编码,入库判断是否重复
		String code = new Date()+"";
		warningSchemeDTO.setCode(code);
		
		List<AlarmRuleDTO> alarmRuleList = warningSchemeDTO.getAlarmRuleList();
		try {
			Integer num = ADOConnection.runTask(wss, "addWarningScheme", Integer.class,warningSchemeDTO);
			if(num > 0) {
				//插入报警规则表
				for(AlarmRuleDTO alarmRule : alarmRuleList) {
					alarmRule.setSchemeCode(code);
					Integer ruleNum = ADOConnection.runTask(wss, "addAlarmRule", Integer.class, alarmRule);
					if(ruleNum > 0) {
						msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					}else {
						msg.setCode(Constant.MESSAGE_INT_SUCCESS);
						msg.setDescription("无报警规则数据插入");
					}					
				}	
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("未有数据插入数据库");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("添加预警方案失败");
		}
		return msg.toJson();	
	}
	
	@RequestMapping(value = "/updateWarningScheme.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "预警方案修改接口", notes = "预警方案修改接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateWarningScheme(@RequestBody WarningSchemeDTO warningSchemeDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(warningSchemeDTO.getCode() == null || warningSchemeDTO.getCode().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("预警方案code为空");
			return msg.toJson();
		}
		
		try {
			Integer num = ADOConnection.runTask(wss, "updateWarningScheme", Integer.class,warningSchemeDTO);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("未修改数据");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("修改预警方案失败");
		}
		return msg.toJson();
		
	}
	
	@RequestMapping(value = "/deleteWarningScheme.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "预警方案删除接口", notes = "预警方案删除接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteWarningScheme(String code) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(code == null || code.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("预警方案code为空");
			return msg.toJson();
		}
		
		try {
			Integer num = ADOConnection.runTask(wss, "deleteWarningScheme", Integer.class,code);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("未删除数据");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("删除预警方案失败");
		}
		return msg.toJson();
		
	}
	
	/**
	 * 预警列表中的通知信息查询功能
	 * @param code 预警方案编码
	 * @return
	 */
	@RequestMapping(value = "/queryNoticeSchemeByWarningCode.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "通知方案查询接口", notes = "通知方案查询接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryNoticeScheme(String code) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(code == null || code.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("预警方案code为空");
			return msg.toJson();
		}
		
		try {
			List<AlertNoticeSchemeVO> alertNoticeSchemeList = ADOConnection.runTask(wss, "queryNoticeSchemeByWarningCode", List.class,code);
			if(alertNoticeSchemeList != null && alertNoticeSchemeList.size() != 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(alertNoticeSchemeList);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无数据");
			}
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("查询通知方案失败");
		}
		return msg.toJson();
	}
	
	
	/**
	 * 预警信息对象类型统计
	 * @param warningInfDTO
	 * @return
	 */
	@RequestMapping(value = "/queryAlarmMessageByObjectType.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "预警信息对象类型统计接口", notes = "预警信息对象类型统计接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmMessageByObjectType(@RequestBody WarningInfDTO warningInfDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(warningInfDTO.getFirstPartion().equals("全部")) {
			warningInfDTO.setAreaCode(null); 
		}
		
		List<AlarmMessageByType> AlarmMessageByObjectTypeList = new ArrayList<>();
		
		
		//TODO 通过分区编码查询出所属的所有0级分区编码，将参数分区编码设置为0级分区编码
		
		try {
			List<AlarmMessageVO> alarmMessageList = ADOConnection.runTask(ams, "queryAlarmMessage", List.class, warningInfDTO);
			if(alarmMessageList != null && alarmMessageList.size() != 0) {
				//TODO 转化枚举类型key为value
				
				//TODO 查询出所有对象类型
				
				
				//统计监测预警不同对象的数据
				List<AlarmMessageByTypeVO> alarmMessageByObjectTypeList = ADOConnection.runTask(ams, "queryAlarmMessageByObjectType",List.class, alarmMessageList);
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(alarmMessageByObjectTypeList);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("预警信息无");
			}
		}catch(Exception e){
			//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警信息失败");
		}
		
		return msg.toJson();
	}
	
	/**
	 * 预警信息报警类型统计
	 * @param warningInfDTO
	 * @return
	 */
	@RequestMapping(value = "/queryAlarmMessageByAlarmType.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "预警信息报警类型统计接口", notes = "预警信息报警类型统计接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmMessageByAlarmType(@RequestBody WarningInfDTO warningInfDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(warningInfDTO.getFirstPartion().equals("全部")) {
			warningInfDTO.setAreaCode(null); 
		}
		
		List<AlarmMessageByType> AlarmMessageByObjectTypeList = new ArrayList<>();
		
		
		//TODO 通过分区编码查询出所属的所有0级分区编码，将参数分区编码设置为0级分区编码
		
		try {
			List<AlarmMessageVO> alarmMessageList = ADOConnection.runTask(ams, "queryAlarmMessage", List.class, warningInfDTO);
			if(alarmMessageList != null && alarmMessageList.size() != 0) {
				//TODO 转化枚举类型key为value
				
				//TODO 查询出所有报警类型
				
				
				//统计监测预警不同对象的数据
				List<AlarmMessageByTypeVO> alarmMessageByObjectTypeList = ADOConnection.runTask(ams, "queryAlarmMessageByType",List.class, alarmMessageList);
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(alarmMessageByObjectTypeList);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("预警信息无");
			}
		}catch(Exception e){
			//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警信息失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryTreatmentEffect.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "报警任务处理效果接口", notes = "报警任务处理效果接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryTreatmentEffect(@RequestBody TreatmentEffectDTO treatmentEffectDTO) {
		MessageBean<TreatmentEffectVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, TreatmentEffectVO.class);
		
		if(treatmentEffectDTO.getProcessCode() == null || treatmentEffectDTO.getProcessCode().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("漏损预警任务编码为空");
	        return msg.toJson();
		}
		if(treatmentEffectDTO.getStartTime() == null || treatmentEffectDTO.getStartTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("开始时间为空");
	        return msg.toJson();
		}
		if(treatmentEffectDTO.getEndTime() == null || treatmentEffectDTO.getEndTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("结束时间为空");
	        return msg.toJson();
		}
		
		try {
			TreatmentEffectVO treatmentEffectVO = ADOConnection.runTask(aps, "queryTreatmentEffect", TreatmentEffectVO.class,treatmentEffectDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(treatmentEffectVO);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询历史数据失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryProcessingStatistics.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "漏控处理统计接口", notes = "漏控处理统计接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryProcessingStatistics(@RequestBody ProcessingStatisticsDTO processingStatisticsDTO) {
		MessageBean<ProcessingStatisticsVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, ProcessingStatisticsVO.class);
		
		if(processingStatisticsDTO.getStartTime() == null || processingStatisticsDTO.getEndTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("开始时间为空");
	        return msg.toJson();
		}
		if(processingStatisticsDTO.getEndTime() == null || processingStatisticsDTO.getEndTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("结束时间为空");
	        return msg.toJson();
		}
		
		try {
			//TODO 数据统计
			ProcessingStatisticsVO processingStatisticsVO = ADOConnection.runTask(sas, "queryProcessingStatistics",ProcessingStatisticsVO.class,processingStatisticsDTO);
			
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(processingStatisticsVO);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("漏控处理信息统计失败");
		}
		
		return msg.toJson();
	}
	
	
	@RequestMapping(value = "/queryPartitionInvest.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "分区投资曲线查询接口", notes = "分区投资曲线查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPartitionInvest() {
		
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		try {
			List<PartitionInvestVO> List = ADOConnection.runTask(new EconomicIndicatorServiceImpl(), "queryPartitionInvest",List.class);
			if(List != null && List.size() != 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(List);
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("分区投资曲线查询失败");
		}
		
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryqueryEventInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项列表查询接口", notes = "事项列表查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryqueryEventInfo(@RequestBody EventInfoDTO eventInfoDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(eventInfoDTO.getStartTime() == null || eventInfoDTO.getStartTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("开始时间为空");
	        return msg.toJson();
		}
		if(eventInfoDTO.getEndTime() == null || eventInfoDTO.getEndTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("结束时间为空");
	        return msg.toJson();
		}
		
		try {
			List<EventInfo> eventInfoList = ADOConnection.runTask(eis, "queryEventInfo",List.class,eventInfoDTO);
			if(eventInfoList != null && eventInfoList.size() != 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(eventInfoList);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("事项列表数据为空");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项列表查询失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/deleteEventInfo.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项信息删除接口", notes = "事项信息删除接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteEventInfo(String code) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(code != null || code.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项编码为空");
		}
		
		try {
			//删除事项表的数据
			Integer num = ADOConnection.runTask(eis, "deleteEventInfo",Integer.class,code);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("事项信息删除成功");
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无事项信息可删除");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项信息删除失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/updateEventInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项信息修改接口", notes = "事项信息修改接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateEventInfo(@RequestBody EventInfo eventInfo) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(eventInfo.getCode() != null || eventInfo.getCode().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项信息编码为空");
	        return msg.toJson(); 
		}
		
		try {
			Integer num = ADOConnection.runTask(eis, "updateEventInfo",Integer.class,eventInfo);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("事项信息修改成功");
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无事项信息可修改");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项信息修改失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/addEventInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项信息添加接口", notes = "事项信息添加接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addEventInfo(@RequestBody EventInfo eventInfo) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		//TODO 产生一个编码
		
		try {
			Integer num = ADOConnection.runTask(eis, "addEventInfo",Integer.class,eventInfo);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("事项信息添加成功");
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无事项信息可添加");
			}
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项信息创建失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryPolicy.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "控漏损策略设置查询接口", notes = "控漏损策略查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPolicy(@RequestBody PolicySchemeDTO policySchemeDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(policySchemeDTO.getCode() == null || policySchemeDTO.getCode().equals("")) {
			if(!policySchemeDTO.getState().equals("0")) {
				msg.setCode(Constant.MESSAGE_INT_ERROR);
		        msg.setDescription("漏损控制策略方案编码为空");
		        return msg.toJson();
			}
		}

		try {
			List<PolicySchemeVO> policySchemeList = ADOConnection.runTask(ps, "queryPolicyScheme",List.class,policySchemeDTO);
			if(policySchemeList != null && policySchemeList.size() != 0) {
				List<Policy> policyList = ADOConnection.runTask(ps, "queryPolicySetting",List.class,policySchemeList.get(0).getCode());
				if(policyList != null && policyList.size() != 0) {
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setData(policyList);
				}else {
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("无策略方案设置信息");
				}
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无策略方案信息");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("漏损控制策略方案查询失败");	
		}
		
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/addPolicy.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "控漏损策略设置增加接口", notes = "控漏损策略增加接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addPolicy(@RequestBody PolicyDTO policyDTO) {
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
		PolicySchemeDTO policySchemeDTO = policyDTO.getPolicySchemeDTO();
		List<PolicySettingDTO> policySettingDTOList = policyDTO.getPolicySettingDTOList();
		if(policySchemeDTO.getName() != null || policySchemeDTO.getName().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("方案名称为空");
	        return msg.toJson();
		}
		if(policySchemeDTO.getState() != null || policySchemeDTO.getState().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("方案状态为空");
	        return msg.toJson();
		}
		
		try {
			//产生编码 
			String code = UUID.randomUUID().toString();
			policySchemeDTO.setCode(code);
			Integer schemeNum = ADOConnection.runTask(ps, "addPolicyScheme",Integer.class,policySchemeDTO);
			if(schemeNum > 0) {
				for(PolicySettingDTO policySettingDTO : policySettingDTOList) {
					policySettingDTO.setPolicyCode(code);
					Integer settingNum = ADOConnection.runTask(ps, "addPolicySetting",Integer.class,policySettingDTO);
					if(settingNum > 0) {
						msg.setCode(Constant.MESSAGE_INT_SUCCESS);
						msg.setDescription("添加成功");
					}else {
						msg.setCode(Constant.MESSAGE_INT_SUCCESS);
						msg.setDescription("无策略设置信息添加");
					}
				}
				
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无策略方案信息添加");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("策略方案添加失败");
		}
		
		return msg.toJson();
	}
    
	@RequestMapping(value = "/deletePolicy.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "控漏损策略删除接口", notes = "控漏损策略删除接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deletePolicy(String code) {
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
		
		if(code != null || code.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("方案编码为空");
	        return msg.toJson();
		}
		
		try {
			//删除方案表
			Integer schemeNum = ADOConnection.runTask(ps, "deletePolicyScheme",Integer.class,code);
			if(schemeNum > 0) {
				Integer settingNum = ADOConnection.runTask(ps, "deletePolicySetting",Integer.class,code);
				if(settingNum > 0) {
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("删除成功");
				}else {
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("无策略设置信息删除");
				}
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无策略方案信息删除");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("策略方案删除失败");
		}
		
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/updatePolicy.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "控漏损策略修改接口", notes = "控漏损策略修改接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updatePolicy(@RequestBody List<PolicySettingDTO> policySettingDTOList) {
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
		
		try {
			for(PolicySettingDTO policySettingDTO : policySettingDTOList) {
				if(policySettingDTO.getPolicyCode() != null || policySettingDTO.getPolicyCode().equals("")) {
					msg.setCode(Constant.MESSAGE_INT_ERROR);
			        msg.setDescription("方案编码为空");
			        return msg.toJson();
				}
				
				Integer num = ADOConnection.runTask(ps, "updatePolicySetting",Integer.class,policySettingDTO);
				if(num > 0) {
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("修改成功");
				}else {
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("无数据修改");
				}
			}
			
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("策略方案修改失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryEventType.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项类型查询接口", notes = "事项类型查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryEventType() {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		try {
			//通过值域查询出所有事项类型
			DataDicDTO dataDicDTO = new DataDicDTO();
			dataDicDTO.setDicParent("");
			List<DataDicVO> dataDicVoList = ADOConnection.runTask(new UserServiceImpl(), "queryDataDic",List.class,dataDicDTO);
			if(dataDicVoList != null && dataDicVoList.size() != 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(dataDicVoList);
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无数据");
			}
			
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询事项类型失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryEventSubtype.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项子类型查询接口", notes = "事项子类型查询接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryEventSubtype(String code) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(code != null || code.equals(" ")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项类型编码为空");
		}
		
		try {
			//查询关联的子级key
			List<DataDicRelationVO> dataDicRelationVoList = ADOConnection.runTask(eis, "querychildKey",List.class,code);
			for(DataDicRelationVO dataDicRelationVO : dataDicRelationVoList) {
				//查询子级key的value
				DataDicDTO dataDicDTO = new DataDicDTO();
				dataDicDTO.setDicKey(dataDicRelationVO.getChildKey());
				List<DataDicVO> dataDicVoList = ADOConnection.runTask(new UserServiceImpl(), "queryDataDic",List.class,dataDicDTO);
				if(dataDicVoList != null && dataDicVoList.size() != 0) {
					msg.setData(dataDicVoList);
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				}else {
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("无数据");
				}
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询事项子类型失败");
		}
		return msg.toJson();
	}
    
	
	
}
