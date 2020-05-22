package com.koron.inwlms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.swan.bean.MessageBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.common.StaffAttribute;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.DTO.common.FileConfigInfo;
import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventSubTypeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventTypeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;
import com.koron.inwlms.bean.DTO.leakageControl.PartitionInvestDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicyDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicySchemeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicySettingDTO;
import com.koron.inwlms.bean.DTO.leakageControl.ProcessingStatisticsDTO;
import com.koron.inwlms.bean.DTO.leakageControl.TreatmentEffectDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALListVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.UploadFileVO;
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
import com.koron.inwlms.bean.VO.leakageControl.EventSubtypeVO;
import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;
import com.koron.inwlms.bean.VO.leakageControl.Policy;
import com.koron.inwlms.bean.VO.leakageControl.PolicySchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.PolicyTypeNum;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsAllDataVO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsVO;
import com.koron.inwlms.bean.VO.leakageControl.TreatmentEffectVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeDateVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.ZoneSaveWaterData;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.common.impl.FileServiceImpl;
import com.koron.inwlms.service.leakageControl.AlarmMessageService;
import com.koron.inwlms.service.leakageControl.AlarmProcessService;
import com.koron.inwlms.service.leakageControl.EconomicIndicatorServiceImpl;
import com.koron.inwlms.service.leakageControl.EventInfoService;
import com.koron.inwlms.service.leakageControl.PolicyService;
import com.koron.inwlms.service.leakageControl.StatisticalAnalysisService;
import com.koron.inwlms.service.leakageControl.WarningSchemeService;
import com.koron.inwlms.service.sysManager.impl.UserServiceImpl;
import com.koron.inwlms.util.ExportDataUtil;
import com.koron.inwlms.util.UnitUtil;
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
	@Autowired
    private FileConfigInfo fileConfigInfo;

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
		
		//TODO 通过分区编码查询出所属的所有0级分区编码，将参数分区编码设置为0级分区编码
		
		try {
			AlarmMessageReturnVO alarmMessageReturnVO = ADOConnection.runTask(ams, "queryAlarmMessage", AlarmMessageReturnVO.class, warningInfDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(alarmMessageReturnVO);
		}catch(Exception e){
			//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警信息失败");
		}
			
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downAlarmMessage.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载预警信息列表数据", notes = "下载预警信息列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downAlarmMessage(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			WarningInfDTO warningInfDTO = jsonValue.fromJson(objValue, WarningInfDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (warningInfDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			warningInfDTO.setPage(1);
			warningInfDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			AlarmMessageReturnVO alarmMessageReturnVO = ADOConnection.runTask(ams, "queryAlarmMessage", AlarmMessageReturnVO.class, warningInfDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(alarmMessageReturnVO.getAlarmMessageList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		if(alarmProcessDTO.getType() == null) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!任务类型为空");
	        return msg.toJson();
		}
		
		//查询参数设置调整
		try {
			List<AlarmProcessVO> alarmProcessList = ADOConnection.runTask(aps,"queryAlarmProcess",List.class,alarmProcessDTO);
			if(alarmProcessList != null && alarmProcessList.size() != 0) {			
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
	
	@RequestMapping(value = "/downAlarmProcess.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载预警信息处理任务列表数据", notes = "下载预警信息处理任务列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downAlarmProcess(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			AlarmProcessDTO alarmProcessDTO = jsonValue.fromJson(objValue, AlarmProcessDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (alarmProcessDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			alarmProcessDTO.setPage(1);
			alarmProcessDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			List<AlarmProcessVO> alarmProcessList = ADOConnection.runTask(aps,"queryAlarmProcess",List.class,alarmProcessDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(alarmProcessList, jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/queryAlarmProcessByTaskCode.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "通过code查询预警信息处理任务接口", notes = "通过code查询预警信息处理任务接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmProcessByTaskCode(String taskCode) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(taskCode == null || taskCode.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR); 
	        msg.setDescription("参数错误!任务编码为空");
	        return msg.toJson();
		}
		
		
		//查询参数设置调整
		try {
			List<AlarmProcessVO> alarmProcessList = ADOConnection.runTask(aps,"queryAlarmProcessByTaskCode",List.class,taskCode);
			if(alarmProcessList != null && alarmProcessList.size() != 0) {
				
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
		
		if(alarmProcessVO.getAlarmType() == null && alarmProcessVO.getAlarmType().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!报警类型为空");
	        return msg.toJson();
		}
		
		if(alarmProcessVO.getType() == null ) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!任务类型为空");
	        return msg.toJson();
		}
		
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
	
	/**
	 * 预警处理任务创建时上传附件
	 * @param file
	 * @param tId
	 * @param fileModule
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/deleteAlarmProcess.htm", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
    @ResponseBody
    public String uploadAlarmProcessFile(@RequestParam("file") MultipartFile file, @RequestParam("tId") Integer tId,@RequestParam("fileModule") String fileModule, HttpServletRequest request,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<UploadFileVO> msg = new MessageBean<>();
		// 获取上传文件名,包含后缀
   		String originalFilename = file.getOriginalFilename();
   		// 获取后缀
   		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
   	// 获取当前月份，时间格式:201910
   		SimpleDateFormat dataFormate = new SimpleDateFormat("yyyyMM");
   		String time = dataFormate.format(new Date());
   		// 保存目录
   		String path = fileConfigInfo.getPath() + File.separator + fileModule + File.separator + time ;
   		// 生成保存文件
   		File dirPath = new File(path);
   		if (!dirPath.exists()) {
   			dirPath.mkdirs();
   		}
   		
   		//用时间戳作为文件名称存储
   		String storeName = System.currentTimeMillis()+fileType;
   		File uploadFile = new File(path+ File.separator + storeName);
   		double size = file.getSize() / 1014;

   		// 将上传文件保存到路径
   		try {
   			file.transferTo(uploadFile);
   		} catch (IOException e) {
   			e.printStackTrace();
   		}

   		String createAccount = user.getLoginName();
   		UploadFileDTO uploadFileDTO = new UploadFileDTO();
   		uploadFileDTO.settId(tId);
   		uploadFileDTO.setFileName(originalFilename);
   		uploadFileDTO.setFilePath(path);
   		uploadFileDTO.setFileSize(size);
   		uploadFileDTO.setModuleType(fileModule);
   		uploadFileDTO.setStoreName(storeName);
   		uploadFileDTO.setStorageTime(new java.util.Date());
   		uploadFileDTO.setCreateBy(createAccount);
   		uploadFileDTO.setFileType(fileType);
   		// 上传文件记录入库
   		int result = ADOConnection.runTask(new FileServiceImpl(), "insertFileData", Integer.class, uploadFileDTO);
   		if (result == 1) {
   			UploadFileVO uploadFileVO = new UploadFileVO();
   			uploadFileVO.setFileId(uploadFileDTO.getId());
   			uploadFileVO.setFileName(originalFilename);
   			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
   			msg.setDescription(Constant.MESSAGE_STRING_SUCCESS);
   			msg.setData(uploadFileVO);
   		} else {
   			msg.setCode(Constant.MESSAGE_INT_ADDERROR);
   			msg.setDescription(Constant.MESSAGE_STRING_ADDERROR);
   		}
		return null;
	}
	
	@RequestMapping(value = "/queryAlarmProcessFile.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询预警信息任务附件接口", notes = "查询预警信息任务附件接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmProcessFile(@RequestBody String type) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		try {
			List<UploadFileDTO> list = ADOConnection.runTask(aps, "queryAlarmProcessFile",List.class,type);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(list);
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("删除预警信息处理任务失败");
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
    @ApiOperation(value = "预警方案列表查询接口", notes = "预警方案列表查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryWarningSchemeList(@RequestBody WarningSchemeDTO warningSchemeDTO) {
		
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		
		//查询预警方案表信息
		try {
			List<AlertSchemeListVO> alertSchemeListVO = ADOConnection.runTask(wss, "queryWarningSchemeList", List.class,warningSchemeDTO);
			if(alertSchemeListVO == null || alertSchemeListVO.size() == 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("该条件下未查询到数据");
				return msg.toJson();
			}
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(alertSchemeListVO);
			
		}catch(Exception e) {
			
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询预警方案失败");
	        return msg.toJson();
		}
		
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downWarningScheme.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载预警方案列表数据", notes = "下载预警方案列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downWarningScheme(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			WarningSchemeDTO warningSchemeDTO = jsonValue.fromJson(objValue, WarningSchemeDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (warningSchemeDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			warningSchemeDTO.setPage(1);
			warningSchemeDTO.setPageCount(Constant.DOWN_MAX_LIMIT); 
			// 查询到导出数据结果
			List<AlertSchemeListVO> alertSchemeListVO = ADOConnection.runTask(wss, "queryWarningSchemeList", List.class,warningSchemeDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(alertSchemeListVO, jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		
		List<AlarmRuleDTO> alarmRuleList = warningSchemeDTO.getAlarmRuleList();
		try {
			ADOConnection.runTask(wss, "addWarningScheme", String.class,warningSchemeDTO,alarmRuleList);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			
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
		MessageBean<AlarmMessageByTypeVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AlarmMessageByTypeVO.class);
		
		//TODO 通过分区编码查询出所属的所有0级分区编码，将参数分区编码设置为0级分区编码
		
		try {			
			//统计监测预警不同对象的数据
			AlarmMessageByTypeVO alarmMessageByObjectType = ADOConnection.runTask(ams, "queryAlarmMessageByObjectType",AlarmMessageByTypeVO.class, warningInfDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(alarmMessageByObjectType);
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
		MessageBean<AlarmMessageByTypeVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AlarmMessageByTypeVO.class);
		
		
		
		//TODO 通过分区编码查询出所属的所有0级分区编码，将参数分区编码设置为0级分区编码
		
		try {
			AlarmMessageByTypeVO alarmMessageByObjectType = ADOConnection.runTask(ams, "queryAlarmMessageByAlarmType", AlarmMessageByTypeVO.class, warningInfDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(alarmMessageByObjectType);
			
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
		MessageBean<ProcessingStatisticsAllDataVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, ProcessingStatisticsAllDataVO.class);
		
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
			//测试数据（待数据连通后删除）
			ProcessingStatisticsAllDataVO psadv = new ProcessingStatisticsAllDataVO();
			psadv.setMnfBefor(100.01);
			psadv.setMnfAfther(81.2);
			psadv.setLossFlowBefor(111.3);
			psadv.setLossFlowAfther(80.4);
			ProcessingStatisticsVO processingStatisticsVO = new ProcessingStatisticsVO();
			processingStatisticsVO.setLoadingNum(10.0);
			processingStatisticsVO.setFinishNum(5.0);
			processingStatisticsVO.setUntreatedNum(8.0);
			List<ProcessingStatisticsVO> processingStatisticsVOList = new ArrayList<>();
			ProcessingStatisticsVO processingStatisticsVO1 = new ProcessingStatisticsVO();
			processingStatisticsVO1.setLoadingNum(10.0);
			processingStatisticsVO1.setFinishNum(5.0);
			processingStatisticsVO1.setUntreatedNum(8.0);
			processingStatisticsVO1.setLossFlowNum(340.2);
			processingStatisticsVO1.setAllFlowNum(700.4);
			processingStatisticsVO1.setMonth(1);
			processingStatisticsVOList.add(processingStatisticsVO1);
			ProcessingStatisticsVO processingStatisticsVO2 = new ProcessingStatisticsVO();
			processingStatisticsVO2.setLoadingNum(11.0);
			processingStatisticsVO2.setFinishNum(6.0);
			processingStatisticsVO2.setUntreatedNum(7.0);
			processingStatisticsVO2.setAllFlowNum(800.6);
			processingStatisticsVO2.setLossFlowNum(300.4);
			processingStatisticsVO2.setMonth(2);
			processingStatisticsVOList.add(processingStatisticsVO2);
			ProcessingStatisticsVO processingStatisticsVO3 = new ProcessingStatisticsVO();
			processingStatisticsVO3.setLoadingNum(11.0);
			processingStatisticsVO3.setFinishNum(8.0);
			processingStatisticsVO3.setUntreatedNum(4.0);
			processingStatisticsVO3.setAllFlowNum(780.6);
			processingStatisticsVO3.setLossFlowNum(300.1);
			processingStatisticsVO3.setMonth(3);
			processingStatisticsVOList.add(processingStatisticsVO3);
			ProcessingStatisticsVO processingStatisticsVO4 = new ProcessingStatisticsVO();
			processingStatisticsVO4.setLoadingNum(11.0);
			processingStatisticsVO4.setFinishNum(8.0);
			processingStatisticsVO4.setUntreatedNum(4.0);
			processingStatisticsVO4.setAllFlowNum(600.5);
			processingStatisticsVO4.setLossFlowNum(500.1);
			processingStatisticsVO4.setMonth(4);
			processingStatisticsVOList.add(processingStatisticsVO4);
			processingStatisticsVO.setProStatList(processingStatisticsVOList);
			psadv.setPsv(processingStatisticsVO);
			List<ZoneSaveWaterData> zswd = new ArrayList<>();
			ZoneSaveWaterData zoneSaveWaterData = new ZoneSaveWaterData();
			zoneSaveWaterData.setName("分区1");
			zoneSaveWaterData.setProportion(50.0);
			zoneSaveWaterData.setSaveWater(1800.0);
			zswd.add(zoneSaveWaterData);
			ZoneSaveWaterData zoneSaveWaterData1 = new ZoneSaveWaterData();
			zoneSaveWaterData1.setName("分区2");
			zoneSaveWaterData1.setProportion(20.0);
			zoneSaveWaterData1.setSaveWater(800.0);
			zswd.add(zoneSaveWaterData1);
			ZoneSaveWaterData zoneSaveWaterData2 = new ZoneSaveWaterData();
			zoneSaveWaterData2.setName("分区3");
			zoneSaveWaterData2.setProportion(20.0);
			zoneSaveWaterData2.setSaveWater(800.0);
			zswd.add(zoneSaveWaterData2);
			ZoneSaveWaterData zoneSaveWaterData3 = new ZoneSaveWaterData();
			zoneSaveWaterData3.setName("分区4");
			zoneSaveWaterData3.setProportion(10.0);
			zoneSaveWaterData3.setSaveWater(400.0);
			zswd.add(zoneSaveWaterData3);
			psadv.setSaveWaterList(zswd);
			List<ZoneSaveWaterData> zscd = new ArrayList<>();
			ZoneSaveWaterData zoneSaveWaterData4 = new ZoneSaveWaterData();
			zoneSaveWaterData4.setName("分区1");
			zoneSaveWaterData4.setSaveCost(10.0);
			zscd.add(zoneSaveWaterData4);
			ZoneSaveWaterData zoneSaveWaterData5 = new ZoneSaveWaterData();
			zoneSaveWaterData5.setName("分区2");
			zoneSaveWaterData5.setSaveCost(12.3);
			zscd.add(zoneSaveWaterData5);
			ZoneSaveWaterData zoneSaveWaterData6 = new ZoneSaveWaterData();
			zoneSaveWaterData6.setName("分区3");
			zoneSaveWaterData6.setSaveCost(14.7);
			zscd.add(zoneSaveWaterData6);
			ZoneSaveWaterData zoneSaveWaterData7 = new ZoneSaveWaterData();
			zoneSaveWaterData7.setName("分区4");
			zoneSaveWaterData7.setSaveCost(20.6);
			zscd.add(zoneSaveWaterData7);
			psadv.setSaveCostList(zscd);
			PolicyTypeNum policyTypeNum = new PolicyTypeNum();
			policyTypeNum.setP1(2);
			policyTypeNum.setP2(5);
			policyTypeNum.setP3(4);
			policyTypeNum.setP4(7);
			policyTypeNum.setP5(1);
			policyTypeNum.setP6(0);
			policyTypeNum.setP7(2);
			policyTypeNum.setP8(10);
			policyTypeNum.setP9(0);
			policyTypeNum.setP10(5);
			policyTypeNum.setP11(3);
			policyTypeNum.setP12(2);
			psadv.setPtn(policyTypeNum);
			
			
			
			//TODO 数据统计
			//ProcessingStatisticsVO processingStatisticsVO = ADOConnection.runTask(sas, "queryProcessingStatistics",ProcessingStatisticsVO.class,processingStatisticsDTO);
			
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(psadv);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("漏控处理信息统计失败");
		}
		
		return msg.toJson();
	}
	
	
	@RequestMapping(value = "/queryPartitionInvest.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "分区投资曲线查询接口", notes = "分区投资曲线查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPartitionInvest(@RequestBody PartitionInvestDTO partitionInvestDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(partitionInvestDTO.getType() == null || partitionInvestDTO.getType().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("分区类型为空");
	        return msg.toJson();
		}
		
		try {
			List<PartitionInvestVO> List = ADOConnection.runTask(new EconomicIndicatorServiceImpl(), "queryPartitionInvest",List.class, partitionInvestDTO.getType());
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
	
	@RequestMapping(value = "/updatePartitionInvest.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "分区投资曲线修改接口", notes = "分区投资曲线修改接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updatePartitionInvest(@RequestBody List<PartitionInvestDTO> partitionInvestDTOList) {
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
		if(partitionInvestDTOList == null || partitionInvestDTOList.size() == 0) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("分区投资曲线为空");
	        return msg.toJson();
		}
		
		try {
			ADOConnection.runTask(new EconomicIndicatorServiceImpl(), "updatePartitionInvest",Integer.class,partitionInvestDTOList);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setDescription("分区投资曲线修改成功");
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("分区投资曲线修改失败");
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
	
	@RequestMapping(value = "/queryqueryEventInfoByCode.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项信息查询接口", notes = "事项信息查询接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryqueryEventInfoByCode(String code) {
		MessageBean<EventInfo> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, EventInfo.class);
		
		if(code == null || code.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项编码为空");
	        return msg.toJson();
		}
		
		try {
			
			EventInfo even = ADOConnection.runTask(eis, "queryEventInfoByCode",EventInfo.class,code);
			msg.setData(even);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项信息查询失败");
		}
		
		
		return msg.toJson();
	}
	
	
	@RequestMapping(value = "/deleteEventInfo.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项信息删除接口", notes = "事项信息删除接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteEventInfo(String code) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(code == null || code.equals("")) {
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
		
		if(eventInfo.getCode() == null || eventInfo.getCode().equals("")) {
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
		String code = UUID.randomUUID().toString();
		eventInfo.setCode(code);
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
	
	@RequestMapping(value = "/queryPolicyScheme.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "控漏损策略方案查询接口", notes = "控漏损策略方案查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPolicyScheme(@RequestBody PolicySchemeDTO policySchemeDTO) {
		
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		try {
			List<PolicySchemeVO> policySchemeList = ADOConnection.runTask(ps, "queryPolicyScheme",List.class,policySchemeDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
			msg.setData(policySchemeList);
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("漏损控制策略方案查询失败");	
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryPolicy.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "控漏损策略设置查询接口", notes = "控漏损策略设置查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPolicy(@RequestBody PolicySchemeDTO policySchemeDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(policySchemeDTO.getCode() == null || policySchemeDTO.getCode().equals("")) {
			if(!policySchemeDTO.getState().equals("1")) {
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
		if(policySchemeDTO.getName() == null || policySchemeDTO.getName().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("方案名称为空");
	        return msg.toJson();
		}
		if(policySchemeDTO.getState() == null || policySchemeDTO.getState().equals("")) {
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
	
	@RequestMapping(value = "/updatePolicyScheme.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "控漏损策略方案修改接口", notes = "控漏损策略方案修改接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updatePolicyScheme(@RequestBody PolicySchemeDTO policySchemeDTO) {
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
		if(policySchemeDTO.getCode() == null || policySchemeDTO.getCode().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("方案编码为空"); 
	        return msg.toJson();
		}
		
		try {
			ADOConnection.runTask(ps, "updatePolicyScheme",Integer.class,policySchemeDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setDescription("修改成功");
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("策略方案修改失败");
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
			dataDicDTO.setDicParent("10120"); 
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
	
	@RequestMapping(value = "/queryEventSubtype.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项子类型查询接口", notes = "事项子类型查询接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryEventSubtype(@RequestBody EventTypeDTO eventTypeDTO) {
		MessageBean<EventSubtypeVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, EventSubtypeVO.class);
		if(eventTypeDTO.getCode() == null || eventTypeDTO.getCode().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
	        msg.setDescription("事项类型编码为空");
	        msg.setData(null);
	        return msg.toJson();
		}
		
		try {
			//查询关联的子级key
			List<DataDicRelationVO> dataDicRelationVoList = ADOConnection.runTask(eis, "querychildKey",List.class,eventTypeDTO);
			PageInfo pageInfo = new PageInfo();
			pageInfo.setPage(eventTypeDTO.getPage());
			pageInfo.setSize(eventTypeDTO.getPageCount());
			pageInfo.setTotalNumber(dataDicRelationVoList.get(0).getTotalNum());
			List<DataDicVO> dataEventSubtype = new ArrayList<>();
			for(DataDicRelationVO dataDicRelationVO : dataDicRelationVoList) {
				//查询子级key的value
				DataDicDTO dataDicDTO = new DataDicDTO();
				dataDicDTO.setDicKey(dataDicRelationVO.getChildKey());
				
				List<DataDicVO> dataDicVoList = ADOConnection.runTask(new UserServiceImpl(), "queryDataDic",List.class,dataDicDTO);
				if(dataDicVoList != null && dataDicVoList.size() != 0) {
					dataEventSubtype.add(dataDicVoList.get(0));	
				}
			}
			EventSubtypeVO eventSubtypeVO = new EventSubtypeVO();
			eventSubtypeVO.setEventSubtypeList(dataEventSubtype);
			eventSubtypeVO.setQuery(pageInfo);
			msg.setData(eventSubtypeVO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询事项子类型失败");
		}
		return msg.toJson();
	}
    
	
	@RequestMapping(value = "/addEventSubtype.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项子类型添加接口", notes = "事项子类型添加接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addEventSubtype(@RequestBody EventSubTypeDTO eventSubTypeDTO) {
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
		
		try {
			//添加子类型
			Integer num = ADOConnection.runTask(eis, "addEventSubType", Integer.class, eventSubTypeDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项子类型添加失败");
		}

		return msg.toJson();
	}
	
	@RequestMapping(value = "/deleteEventSubtype.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "事项子类型删除接口", notes = "事项子类型删除接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteEventSubtype(String key) {
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
		
		if(key == null || key.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项子类型编码为空");
	        return msg.toJson();
		}
		
		try {
			Integer num = ADOConnection.runTask(eis, "deleteEventSubType", Integer.class, key);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("事项子类型删除成功");
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无事项子类型数据");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("事项子类型删除失败");
		}
		
		return msg.toJson();
	}
	
	
	
}
