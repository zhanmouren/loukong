package com.koron.inwlms.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.koron.inwlms.bean.DTO.leakageControl.ProcessingStatisticsDTO;
import com.koron.inwlms.bean.DTO.leakageControl.TreatmentEffectDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByType;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByTypeVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeScheme;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.AlertSchemeListVO;
import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsVO;
import com.koron.inwlms.bean.VO.leakageControl.TreatmentEffectVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;
import com.koron.inwlms.service.leakageControl.AlarmMessageService;
import com.koron.inwlms.service.leakageControl.AlarmProcessService;
import com.koron.inwlms.service.leakageControl.StatisticalAnalysisService;
import com.koron.inwlms.service.leakageControl.WarningSchemeService;
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

	@RequestMapping(value = "/queryAlarmMessage.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询预警信息接口", notes = "查询预警信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmMessage(@RequestBody WarningInfDTO warningInfDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
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
				//TODO 转化枚举类型key为value
				
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(alarmMessageList);
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
	
	@RequestMapping(value = "/queryAlarmMessageByRref.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询主报警ID下的预警信息接口", notes = "查询主报警ID下的预警信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmMessageByRref(Integer code) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(code == null) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("主报警ID为空");
	        return msg.toJson();
		}
		
		try {
			List<AlarmMessageVO> alarmMessageList = ADOConnection.runTask(ams, "queryAlarmMessageByRref", List.class, code);
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
		//查询参数设置调整
		System.out.print(0);
		try {
			List<AlarmProcessVO> alarmProcessList = ADOConnection.runTask(aps,"queryAlarmProcess",List.class,alarmProcessDTO);
			if(alarmProcessList != null && alarmProcessList.size() != 0) {
				//转化枚举类型key为value
				
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
		//自动产生任务编码
		String code = "TA1001";
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
	
	@RequestMapping(value = "/deleteAlarmProcess.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除预警信息处理任务接口", notes = "删除预警信息处理任务接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteAlarmProcess(Integer id) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		try {
			Integer num = ADOConnection.runTask(aps, "deleteAlarmProcess",Integer.class,id);
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
	
	@RequestMapping(value = "/queryWarningScheme.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "预警方案查询接口", notes = "预警方案查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryWarningScheme(@RequestBody WarningSchemeDTO warningSchemeDTO) {
		
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		List<WarningSchemeVO> warningSchemeList = new ArrayList<>();
		List<AlertSchemeListVO> alertSchemeListVO = new ArrayList<>();
		//查询预警方案表信息
		try {
			warningSchemeList = ADOConnection.runTask(wss, "queryWarningScheme", List.class,warningSchemeDTO);
			if(warningSchemeList == null || warningSchemeList.size() == 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("改条件下未查询到数据");
			}
			
		}catch(Exception e) {
			//添加失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警方案失败");
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
						//通过通知方式的key查询其值
						
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
			}
			
		}
		msg.setCode(Constant.MESSAGE_INT_SUCCESS);
		msg.setData(alertSchemeListVO);
		
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/addWarningScheme.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "预警方案添加接口", notes = "预警方案添加接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addWarningScheme(@RequestBody WarningSchemeDTO warningSchemeDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		//产生一个方案编码
		
		List<AlarmRuleDTO> alarmRuleList = warningSchemeDTO.getAlarmRuleList();
		try {
			Integer num = ADOConnection.runTask(wss, "addWarningScheme", Integer.class,warningSchemeDTO);
			if(num > 0) {
				//插入报警规则表
				for(AlarmRuleDTO alarmRule : alarmRuleList) {
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
	public String deleteWarningScheme(Integer id) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		try {
			Integer num = ADOConnection.runTask(wss, "deleteWarningScheme", Integer.class,id);
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
		
		
		//通过分区编码查询出所属的所有0级分区编码，将参数分区编码设置为0级分区编码
		
		try {
			List<AlarmMessageVO> alarmMessageList = ADOConnection.runTask(ams, "queryAlarmMessage", List.class, warningInfDTO);
			if(alarmMessageList != null && alarmMessageList.size() != 0) {
				//转化枚举类型key为value
				
				//查询出所有对象类型
				
				
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
		
		
		//通过分区编码查询出所属的所有0级分区编码，将参数分区编码设置为0级分区编码
		
		try {
			List<AlarmMessageVO> alarmMessageList = ADOConnection.runTask(ams, "queryAlarmMessage", List.class, warningInfDTO);
			if(alarmMessageList != null && alarmMessageList.size() != 0) {
				//转化枚举类型key为value
				
				//查询出所有报警类型
				
				
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
		if(processingStatisticsDTO.getStartTime() == null || processingStatisticsDTO.getEndTime() == null) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("时间段为空");
			return msg.toJson();
		}
		
		try {
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
		MessageBean<PartitionInvestVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PartitionInvestVO.class);
		
		
		return null;
	}
	
}
