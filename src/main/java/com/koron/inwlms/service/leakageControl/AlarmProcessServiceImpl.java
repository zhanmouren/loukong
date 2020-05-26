package com.koron.inwlms.service.leakageControl;

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
import com.koron.inwlms.bean.DTO.leakageControl.TreatmentEffectDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.TimeAndFlowData;
import com.koron.inwlms.bean.VO.leakageControl.TreatmentEffectVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.leakageControl.AlarmProcessMapper;
import com.koron.inwlms.util.TimeUtil;
import com.koron.util.Constant;

@Service
public class AlarmProcessServiceImpl implements AlarmProcessService {

	@TaskAnnotation("queryAlarmProcess")
	@Override
	public List<AlarmProcessVO> queryAlarmProcess(SessionFactory factory,AlarmProcessDTO alarmProcessDTO){
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		List<AlarmProcessVO> list = mapper.queryAlarmProcess(alarmProcessDTO);
		return list;
	}
	@TaskAnnotation("queryAlarmProcessByTaskCode")
	@Override
	public List<AlarmProcessVO> queryAlarmProcessByTaskCode(SessionFactory factory,String taskCode){
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		List<AlarmProcessVO> list = mapper.queryAlarmProcessByTaskCode(taskCode);
		return list;
	}
	
	@TaskAnnotation("updateAlarmProcess")
	@Override
	public Integer updateAlarmProcess(SessionFactory factory,AlarmProcessVO alarmProcessVO) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = mapper.updateAlarmProcess(alarmProcessVO);
		return num;
	}
	
	@TaskAnnotation("addAlarmProcess")
	@Override
	public Integer addAlarmProcess(SessionFactory factory,AlarmProcessVO alarmProcessVO) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = 0;
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
	 * 按模块查询上传附件信息
	 */
	@TaskAnnotation("queryAlarmProcessFile")
	@Override
	public List<UploadFileDTO> queryAlarmProcessFile(SessionFactory factory,String type) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		List<UploadFileDTO> list = mapper.queryAlarmProcessFile(type);
		return list;
	}
	
	/**
	 * 漏损预警处理效果
	 */
	@TaskAnnotation("queryTreatmentEffect")
	@Override
	public TreatmentEffectVO queryTreatmentEffect(SessionFactory factory,TreatmentEffectDTO treatmentEffectDTO) {
		TreatmentEffectVO treatmentEffectVO = new TreatmentEffectVO();
		IndicatorMapper indMapper = factory.getMapper(IndicatorMapper.class);
		Date startDate = new Date();
		Date endDate = new Date();
		Date nowDate = new Date();
		
		//查询分区漏损月指标编码
		String zonecodeM = "";
		String mnfCode = "";
		String zonecodeD = "";
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
		
		//TODO 供水量统计
		
		
		
		
		
		return null;
	}
	
	
}
