package com.koron.inwlms.service.leakageControl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.BasicDataParam;
import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;
import com.koron.inwlms.bean.DTO.leakageControl.PolicySchemeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.TreatmentEffectDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessLog;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessReturnVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.GisExistZoneVO;
import com.koron.inwlms.bean.VO.leakageControl.GisZonePointVO;
import com.koron.inwlms.bean.VO.leakageControl.PointHourData;
import com.koron.inwlms.bean.VO.leakageControl.Policy;
import com.koron.inwlms.bean.VO.leakageControl.PolicySchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.TimeAndFlowData;
import com.koron.inwlms.bean.VO.leakageControl.TreatmentEffectVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.leakageControl.AlarmProcessMapper;
import com.koron.inwlms.mapper.leakageControl.BasicDataMapper;
import com.koron.inwlms.mapper.leakageControl.PolicyMapper;
import com.koron.inwlms.util.TimeUtil;
import com.koron.util.Constant;

@Service
public class AlarmProcessServiceImpl implements AlarmProcessService {

	@TaskAnnotation("queryAlarmProcess")
	@Override
	public AlarmProcessReturnVO queryAlarmProcess(SessionFactory factory,AlarmProcessDTO alarmProcessDTO){
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		AlarmProcessReturnVO alarmProcessReturnVO = new AlarmProcessReturnVO();
		List<AlarmProcessVO> reList = new ArrayList<>();
		//查询工单信息
		List<AlarmProcessVO> list = mapper.queryAlarmProcess(alarmProcessDTO);
		//查询报警信息
		for(AlarmProcessVO alarmProcessVO : list) {
			if(alarmProcessVO.getWarningCode() != null && !alarmProcessVO.getWarningCode().equals("")) {
				List<AlarmProcessVO> alarmProcessVO1List = mapper.queryAlarmMessageByP(alarmProcessDTO);
				if(alarmProcessVO1List != null && alarmProcessVO1List.size() != 0) {
					for(AlarmProcessVO alarmProcessVO1 : alarmProcessVO1List) {
						if(alarmProcessVO1.getWarningCode().equals(alarmProcessVO.getWarningCode())) {
							if(alarmProcessVO1 != null) {
								if(alarmProcessVO1.getAlarmType() != null && !alarmProcessVO1.getAlarmType().equals("")) {
									alarmProcessVO.setAlarmType(alarmProcessVO1.getAlarmType());
								}
								if(alarmProcessVO1.getAlarmContent() != null) {
									alarmProcessVO.setAlarmContent(alarmProcessVO1.getAlarmContent());
								}
								if(alarmProcessVO1.getObjectType() != null ) {
									alarmProcessVO.setObjectType(alarmProcessVO1.getObjectType());
								}
								if(alarmProcessVO1.getObjectCode() != null) {
									alarmProcessVO.setObjectCode(alarmProcessVO1.getObjectCode());
								}
								//若有预警类型筛选条件，则展示相应数据
								if(alarmProcessDTO.getAlarmType() != null) {
									if(alarmProcessVO1.getAlarmType().equals(alarmProcessDTO.getAlarmType())) {
										reList.add(alarmProcessVO);
									}
								}
							
							}
						}
					}
				}		
			}
			if(alarmProcessDTO.getAlarmType() == null || alarmProcessDTO.getAlarmType().equals("")) {
				if(alarmProcessDTO.getDmaCode() == null || alarmProcessDTO.getDmaCode().equals("")) {
					if(alarmProcessDTO.getAreaCode() == null || alarmProcessDTO.getAreaCode().equals("")) {
						reList.add(alarmProcessVO);
					}
				}
			}
		}
		
		alarmProcessReturnVO.setAlarmProcessList(reList);
		PageInfo query = new PageInfo();
		Integer num = mapper.queryAlarmProcessTotalNumber(alarmProcessDTO);
		query.setTotalNumber(num);
		query.setPage(alarmProcessDTO.getPage());
		query.setSize(alarmProcessDTO.getPageCount());
		alarmProcessReturnVO.setQuery(query);
		
		return alarmProcessReturnVO;
	}
	@TaskAnnotation("queryAlarmProcessByTaskCode")
	@Override
	public List<AlarmProcessVO> queryAlarmProcessByTaskCode(SessionFactory factory,String taskCode){
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		List<AlarmProcessVO> list = mapper.queryAlarmProcessByTaskCode(taskCode);
		
		for(AlarmProcessVO alarmProcessVO : list) {
			if(alarmProcessVO.getWarningCode() != null) {
				AlarmProcessVO alarmProcessVO1 = mapper.queryAlarmMessageByCode(alarmProcessVO.getWarningCode());
				if(alarmProcessVO1 != null) {
					if(alarmProcessVO1.getAlarmType() != null) {
						alarmProcessVO.setAlarmType(alarmProcessVO1.getAlarmType());
					}
					if(alarmProcessVO1.getAlarmContent() != null) {
						alarmProcessVO.setAlarmContent(alarmProcessVO1.getAlarmContent());
					}
					if(alarmProcessVO1.getObjectType() != null ) {
						alarmProcessVO.setObjectType(alarmProcessVO1.getObjectType());
					}
					if(alarmProcessVO1.getObjectCode() != null) {
						alarmProcessVO.setObjectCode(alarmProcessVO1.getObjectCode());
					}
				}
			}
		}
		return list;
	}
	
	@TaskAnnotation("updateAlarmProcess")
	@Override
	public Integer updateAlarmProcess(SessionFactory factory,AlarmProcessVO alarmProcessVO,UserVO user) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = mapper.updateAlarmProcess(alarmProcessVO);
		if(alarmProcessVO.getState().equals(Constant.DATADICTIONARY_TASKSTATUSON)) {
			//添加流程日志
			AlarmProcessLog alarmProcessLog = new AlarmProcessLog();
			alarmProcessLog.setTaskCode(alarmProcessVO.getTaskCode());
			alarmProcessLog.setCreateTime(new Date());
			if(alarmProcessVO.getRemarks() != null) {
				alarmProcessLog.setContent(alarmProcessVO.getRemarks());
			}
			alarmProcessLog.setOperation("确认任务");
			alarmProcessLog.setCreateBy(user.getCode());
		}else if(alarmProcessVO.getState().equals(Constant.DATADICTIONARY_TASKSTATUSOVER)) {
			//添加流程日志
			AlarmProcessLog alarmProcessLog = new AlarmProcessLog();
			alarmProcessLog.setTaskCode(alarmProcessVO.getTaskCode());
			alarmProcessLog.setCreateTime(new Date());
			if(alarmProcessVO.getRemarks() != null) {
				alarmProcessLog.setContent(alarmProcessVO.getRemarks());
			}
			alarmProcessLog.setOperation("完成任务");
			alarmProcessLog.setCreateBy(user.getCode());
		}
		
		return num;
	}
	
	@TaskAnnotation("addAlarmProcess")
	@Override
	public String addAlarmProcess(SessionFactory factory,AlarmProcessVO alarmProcessVO, UserVO user) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		String num = "";
		//判断报警类型
		if(alarmProcessVO.getAlarmType().equals(Constant.DATADICTIONARY_TRENDCHANGE)) { 
			num = mapper.addAlarmProcessOfZQS(alarmProcessVO);
		}
		if(alarmProcessVO.getAlarmType().equals(Constant.DATADICTIONARY_OFFLINE)) {
			num = mapper.addAlarmProcessOfPLX(alarmProcessVO);
		}
		if(alarmProcessVO.getAlarmType().equals(Constant.DATADICTIONARY_OVERRUN) && alarmProcessVO.getType() == 1) {
			num = mapper.addAlarmProcessOfPCX(alarmProcessVO);
		}
		if(alarmProcessVO.getAlarmType().equals(Constant.DATADICTIONARY_NOISE)) {
			num = mapper.addAlarmProcessOfPZS(alarmProcessVO);
		}
		if(alarmProcessVO.getAlarmType().equals(Constant.DATADICTIONARY_OVERRUN) && alarmProcessVO.getType() == 0) {
			num = mapper.addAlarmProcessOfZCX(alarmProcessVO);
		}
		
		//添加流程日志
		AlarmProcessLog alarmProcessLog = new AlarmProcessLog();
		alarmProcessLog.setTaskCode(num);
		alarmProcessLog.setCreateTime(new Date());
		if(alarmProcessVO.getRemarks() != null) {
			alarmProcessLog.setContent(alarmProcessVO.getRemarks());
		}
		alarmProcessLog.setOperation("创建任务");
		alarmProcessLog.setCreateBy(user.getCode());
		
		return num;
	}
	
	
	@TaskAnnotation("deleteAlarmProcess")
	@Override
	public Integer deleteAlarmProcess(SessionFactory factory,String code) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = mapper.deleteAlarmProcess(code);
		return num;
	}
	
	/**
	 * 根据策略返回预计完成时间
	 */
	@TaskAnnotation("getEstimatedTime")
	@Override
	public String getEstimatedTime(SessionFactory factory,String code) {
		Date date = new Date();
		int num = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<String> codes = new ArrayList<>();
		//判断策略为哪几个基础策略组成
		if(code.equals(Constant.DATADICTIONARY_PCANDPNLDANDFC)) {
			String code1 = Constant.DATADICTIONARY_PCSTRA;
			String code2 = Constant.DATADICTIONARY_PNLDETECTION;
			String code3 = Constant.DATADICTIONARY_FLOWCHANGE;
			codes.add(code1);
			codes.add(code2);
			codes.add(code3);
		}else if(code.equals(Constant.DATADICTIONARY_PCANDPNANDFC)) {
			String code1 = Constant.DATADICTIONARY_PCSTRA;
			String code2 = Constant.DATADICTIONARY_PNCHANGE;
			String code3 = Constant.DATADICTIONARY_FLOWCHANGE;
			codes.add(code1);
			codes.add(code2);
			codes.add(code3);
		}else if(code.equals(Constant.DATADICTIONARY_FLOWCANDPNLD)) {
			String code2 = Constant.DATADICTIONARY_PNLDETECTION;
			String code3 = Constant.DATADICTIONARY_FLOWCHANGE;
			codes.add(code2);
			codes.add(code3);
		}else if(code.equals(Constant.DATADICTIONARY_PNANDFLOWC)) {
			String code2 = Constant.DATADICTIONARY_PNCHANGE;
			String code3 = Constant.DATADICTIONARY_FLOWCHANGE;
			codes.add(code2);
			codes.add(code3);
		}else if(code.equals(Constant.DATADICTIONARY_PCANDFLOWC)) {
			String code2 = Constant.DATADICTIONARY_PCSTRA;
			String code3 = Constant.DATADICTIONARY_FLOWCHANGE;
			codes.add(code2);
			codes.add(code3);
		}else if(code.equals(Constant.DATADICTIONARY_PCANDPN)) {
			String code2 = Constant.DATADICTIONARY_PCSTRA;
			String code3 = Constant.DATADICTIONARY_PNCHANGE;
			codes.add(code2);
			codes.add(code3);
		}else if(code.equals(Constant.DATADICTIONARY_PCANDPNLD)) {
			String code2 = Constant.DATADICTIONARY_PCSTRA;
			String code3 = Constant.DATADICTIONARY_PNLDETECTION;
			codes.add(code2);
			codes.add(code3);
		}else {
			codes.add(code);
		}
		PolicySchemeDTO policySchemeDTO = new PolicySchemeDTO();
		policySchemeDTO.setState("1");
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		List<PolicySchemeVO> policySchemeList = mapper.queryPolicyScheme(policySchemeDTO);
		List<Policy> policyList = mapper.queryPolicySetting(policySchemeList.get(0).getCode());
		for(Policy policy : policyList) {
			for(String typCode : codes) {
				if(policy.getType().equals(typCode)) {
					int day = policy.getDay();
					if(day > num) {
						num = day;
					}
				}
			}	
		}
		Date newdate = TimeUtil.addDay(date, num);
		String dateStr = format.format(newdate);
		return dateStr;
	}
	
	/**
	 * 按模块查询上传附件信息
	 */
	@TaskAnnotation("queryAlarmProcessFile")
	@Override
	public List<UploadFileDTO> queryAlarmProcessFile(SessionFactory factory,String code) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		List<UploadFileDTO> uploadList = new ArrayList<>();
		List<Integer> list = mapper.queryAlarmProcessFileRelation(code);
		if(list != null && list.size() != 0) {
			for(Integer id : list) {
				UploadFileDTO upload = mapper.queryAlarmProcessFile(id);
				uploadList.add(upload);
			}
			
		}
		return uploadList;
	}
	
	/**
	 * 漏损预警处理效果
	 * @throws ParseException 
	 */
	@TaskAnnotation("queryTreatmentEffect")
	@Override
	public TreatmentEffectVO queryTreatmentEffect(SessionFactory factory,TreatmentEffectDTO treatmentEffectDTO) throws ParseException {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		List<AlarmProcessVO> list = mapper.queryAlarmProcessByTaskCode(treatmentEffectDTO.getProcessCode());
		String zoneCode = list.get(0).getObjectCode();
		
		String zonecodeM = "";
		String mnfCode = "";
		String zonecodeD = "";
		
		//查询分区分级
		GisExistZoneVO gisExistZoneVO = mapper.queryGisZone(zoneCode);
		if(gisExistZoneVO.getRemark().equals(Constant.DMAZONELEVEL_ONE)) {
			zonecodeM = "FLMWL";
			mnfCode = "FLDMNF";
			zonecodeD = "FLDWL";
		}else if(gisExistZoneVO.getRemark().equals(Constant.DMAZONELEVEL_TWO)) {
			zonecodeM = "SLMWL";
			mnfCode = "SLDMNF";
		}else if(gisExistZoneVO.getRemark().equals(Constant.DMAZONELEVEL_THREE)) {
			zonecodeM = "DLMWL";
			mnfCode = "DMDMNF";
			zonecodeD = "DMDWL";
		}
		
		TreatmentEffectVO treatmentEffectVO = new TreatmentEffectVO();
		IndicatorMapper indMapper = factory.getMapper(IndicatorMapper.class);
		Date startDate = new Date();
		Date endDate = new Date();
		Date nowDate = new Date();
		
		//查询分区漏损月指标编码
		
		List<String> codes = new ArrayList<>();
		codes.add(zonecodeM);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			startDate = format.parse(treatmentEffectDTO.getStartTime());
			endDate = format.parse(treatmentEffectDTO.getEndTime());
		}catch(Exception e) {
			
		}
		//获取当前月份
		int nowY = TimeUtil.getYears(nowDate);
		int nowM = TimeUtil.getMonth(nowDate);
		int dateNow = nowY*100 + nowM;
		
		//月漏损量统计
		//获取工单开始的月份和结束的月份
		Date startDateL = TimeUtil.addMonth(startDate, -2);
		Date endDateL = TimeUtil.addMonth(endDate, 2);

		int startYL = TimeUtil.getYears(startDateL);
		int startML = TimeUtil.getMonth(startDateL);
		int endYL = TimeUtil.getYears(endDateL);
		int endML = TimeUtil.getMonth(endDateL);
		//获取分区漏损量的开始时间
		int dateStartL = startYL*100 + startML;
		//获取分区漏损量的结束时间
		int dateEndL = endYL*100 + endML;
		
		List<TimeAndFlowData> lossFlowList = new ArrayList<>();
		//当前月份在工单结束俩个月之后
		
		//查询分区漏损月指标表，获取月漏损数据
		IndicatorDTO indicatorDTO = new IndicatorDTO();
		indicatorDTO.setCodes(codes);
		indicatorDTO.setTimeType(3);
		indicatorDTO.setStartTime(dateStartL);
		indicatorDTO.setEndTime(dateEndL);
		List<IndicatorVO> indicatorData = indMapper.queryZoneLossIndicData(indicatorDTO);
		for(IndicatorVO indicatorVO : indicatorData) {
			TimeAndFlowData timeAndFlowData = new TimeAndFlowData();
			timeAndFlowData.setFlow(indicatorVO.getValue());
			timeAndFlowData.setTimeNum(indicatorVO.getTimeId());
			lossFlowList.add(timeAndFlowData);
		}
		//添加当前月数据
		if(dateEndL <= dateNow) {
			indicatorDTO.setStartTime(dateNow);
			indicatorDTO.setEndTime(dateNow);
			List<IndicatorVO> indicatorData1 = indMapper.queryZoneLossIndicData(indicatorDTO);
			TimeAndFlowData timeAndFlowData = new TimeAndFlowData();
			timeAndFlowData.setFlow(indicatorData1.get(0).getValue());
			timeAndFlowData.setTimeNum(indicatorData1.get(0).getTimeId());
			lossFlowList.add(timeAndFlowData);
		}
		treatmentEffectVO.setLossFlow(lossFlowList);
		
		//计算处理前后的夜间流量和漏损流量
		Date startDay7 = TimeUtil.addDay(startDate, -7);
		int startD7 = TimeUtil.getDays(startDay7);
		int startM7 = TimeUtil.getMonth(startDay7);
		int startY7 = TimeUtil.getYears(startDay7);
		int startD = TimeUtil.getDays(startDate);
		int startM = TimeUtil.getMonth(startDate);
		int startY = TimeUtil.getYears(startDate);
		
		Date endDay7 = TimeUtil.addDay(endDate, 7);
		int endD7 = TimeUtil.getDays(endDay7);
		int endM7 = TimeUtil.getMonth(endDay7);
		int endY7 = TimeUtil.getYears(endDay7);
		int endD = TimeUtil.getDays(endDate);
		int endM = TimeUtil.getMonth(endDate);
		int endY = TimeUtil.getYears(endDate);
		
		int startf7 = startY7*10000 + startM7*100 + startD7;
		int startf = startY*10000 + startM*100 + startD;
		
		int endf7 = endY7*10000 + endM7*100 + endD7;
		int endf = endY*10000 + endM*100 + endD;
		//最小夜间流量
		List<String> mnfCodes = new ArrayList<>();
		mnfCodes.add(mnfCode);
		indicatorDTO.setCodes(mnfCodes);
		indicatorDTO.setTimeType(2);
		indicatorDTO.setStartTime(startf7);
		indicatorDTO.setEndTime(startf);
		double mflowB = 0.0;
		List<IndicatorVO> indicatorData2 = indMapper.queryZoneLossIndicData(indicatorDTO);
		for(IndicatorVO indicatorVO : indicatorData2) {
			mflowB = mflowB + indicatorVO.getValue();
		}
		if(mflowB != 0.0) {
			double mnfFlow7 = mflowB/indicatorData2.size();
			treatmentEffectVO.setMnfBefore(mnfFlow7);
		}
		indicatorDTO.setStartTime(endf);
		indicatorDTO.setEndTime(endf7);
		double mflowA = 0.0;
		List<IndicatorVO> indicatorData3 = indMapper.queryZoneLossIndicData(indicatorDTO);
		for(IndicatorVO indicatorVO : indicatorData3) {
			mflowA = mflowA + indicatorVO.getValue();
		}
		if(mflowA != 0.0) {
			double mnfFlow7 = mflowA/indicatorData3.size();
			treatmentEffectVO.setMnfAfther(mnfFlow7);
		}
		
		
		
		//漏损流量
		List<String> zoneDCodes = new ArrayList<>();
		zoneDCodes.add(zonecodeD);
		indicatorDTO.setCodes(zoneDCodes);
		indicatorDTO.setStartTime(startf7);
		indicatorDTO.setEndTime(startf);
		double lossFlowB = 0.0;
		List<IndicatorVO> indicatorData4 = indMapper.queryZoneLossIndicData(indicatorDTO);
		for(IndicatorVO indicatorVO : indicatorData4) {
			lossFlowB = lossFlowB + indicatorVO.getValue();
		}
		if(lossFlowB != 0.0) {
			double lossFlow7 = lossFlowB/indicatorData4.size();
			treatmentEffectVO.setLossFlowBefore(lossFlow7);
		}
		indicatorDTO.setStartTime(endf);
		indicatorDTO.setEndTime(endf7);
		double lossFlowA = 0.0;
		List<IndicatorVO> indicatorData5 = indMapper.queryZoneLossIndicData(indicatorDTO);
		for(IndicatorVO indicatorVO : indicatorData5) {
			lossFlowA = lossFlowA + indicatorVO.getValue();
		}
		if(lossFlowA != 0.0) {
			double lossFlow7 = lossFlowA/indicatorData5.size();
			treatmentEffectVO.setLossFlowAfther(lossFlow7); 
		}
		
		//供水量统计 
		//查询分区下的监测点
		//工单发起前一日时间
		Date createDate = list.get(0).getCreateTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String createDateStr = sf.format(createDate);
		createDate = sf.parse(createDateStr);
		Date beforfDay = TimeUtil.addDay(createDate, -1);
		BasicDataMapper basDataMapper = factory.getMapper(BasicDataMapper.class);
		List<GisZonePointVO> pointList = basDataMapper.queryZonePoint(zoneCode);
		//工单前一日供水量
		List<TimeAndFlowData> beforAllFlowList = getZoneHourData(pointList,beforfDay,basDataMapper);
		treatmentEffectVO.setAllFlowBList(beforAllFlowList);
		//工单开始至结束中间日的供水量
		long avgLong = Math.round((list.get(0).getCreateTime().getTime() + list.get(0).getUpdateTime().getTime())/2);
		Date avgDate = new Date(avgLong);
		String avgDateStr = sf.format(avgDate);
		avgDate = sf.parse(avgDateStr);
		List<TimeAndFlowData> avgAllFlowList = getZoneHourData(pointList,avgDate,basDataMapper);
		treatmentEffectVO.setAllFlowRList(avgAllFlowList);
		//工单结束日的供水量
		Date endFDate = list.get(0).getUpdateTime();
		String endFDateStr = sf.format(endFDate);
		endFDate = sf.parse(endFDateStr);
		List<TimeAndFlowData> endAllFlowList = getZoneHourData(pointList,endFDate,basDataMapper);
		treatmentEffectVO.setAllFlowAList(endAllFlowList);
		List<TimeAndFlowData> nowAllFlowList = getZoneHourData(pointList,nowDate,basDataMapper);
		treatmentEffectVO.setAllFlowNList(nowAllFlowList);
		return treatmentEffectVO;
	}
	
	
	public List<TimeAndFlowData> getZoneHourData(List<GisZonePointVO> pointList,Date date,BasicDataMapper basDataMapper){
		List<TimeAndFlowData> beforAllFlowList = new ArrayList<>();
		for(int i = 0; i < 24; i++ ) {
			double flow = 0.0;
			for(GisZonePointVO gisZonePointVO : pointList) {
				BasicDataParam basicDataParam = new BasicDataParam();
				basicDataParam.setCode("MOHFLOW");
				basicDataParam.setStationCode(gisZonePointVO.getPointNo()); 
				basicDataParam.setStartTime(date);
				PointHourData pointHourData = basDataMapper.queryPointHourData(basicDataParam);
				if(pointHourData != null) {
					flow = flow + pointHourData.getValue();
				}
			}
			TimeAndFlowData timeAndFlowData = new TimeAndFlowData();
			timeAndFlowData.setTimeDate(date);
			timeAndFlowData.setFlow(flow);
			beforAllFlowList.add(timeAndFlowData);
			date = TimeUtil.addHour(date, 1);
		}
		return beforAllFlowList;
	}
	
}
