package com.koron.inwlms.service.leakageControl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.ProcessingStatisticsDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.leakageControl.AlarmMessageMapper;
import com.koron.inwlms.mapper.leakageControl.AlarmProcessMapper;
import com.koron.inwlms.util.TimeUtil;
import com.koron.util.Constant;

@Service
public class StatisticalAnalysisServiceImpl implements StatisticalAnalysisService{
	
	@TaskAnnotation("queryProcessingStatistics")
	@Override
	public ProcessingStatisticsVO queryProcessingStatistics(SessionFactory factory,ProcessingStatisticsDTO processingStatisticsDTO) throws ParseException {
		IndicatorMapper indicatorMapper = factory.getMapper(IndicatorMapper.class);
		
		ProcessingStatisticsVO processingStatisticsVO = new ProcessingStatisticsVO();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1获取漏损变化 
		//获取指标编码
		  //TODO 查询分区级别,判断分区级别获取对应指标编码
		String areaCode = "";
		
		String minNightFlowCode = "";
		List<String> zoneCodeList = new ArrayList<>();
		Integer timeType = Constant.TIME_TYPE_M;
		String lossFlowCode = "";
		String allFlowCode = "";
		zoneCodeList.add(areaCode);
		
		
		//TODO  1.1处理前后最小夜间流量
		
		//TODO  1.2处理前后漏损水量
		
		//2获取漏损处理情况统计
		//2.2所选时间内每月漏损处理状态统计
		  //获取选择的所有月份
		  Date startDate = format.parse(processingStatisticsDTO.getStartTime());
		  Date endDate = format.parse(processingStatisticsDTO.getEndTime());
		  int startY = TimeUtil.getYears(startDate);
		  int startM = TimeUtil.getMonth(startDate);
		  int endY = TimeUtil.getYears(endDate);
		  int endM = TimeUtil.getMonth(endDate);
		  
		  int start = startY*10 + startM;
		  int end = endY*10 + endM;
		  int i = 0;
		  int loadNum = 0;
		  int finishNum = 0;
		  int untreatedNum = 0;
		  
		  
		  
		  
		  
		  
		  List<ProcessingStatisticsVO> proStatList = new ArrayList<>();
		  while(start <= end) {
			  Calendar calendar = Calendar.getInstance();
			  calendar.setTime(startDate);
			  calendar.set(Calendar.DAY_OF_MONTH,1);
			  calendar.add(Calendar.MONTH, i);
			  String startTime = format.format(calendar.getTime());
			  calendar.add(Calendar.MONTH, i+1);
			  String endTime = format.format(calendar.getTime());
			  
			  AlarmProcessDTO alarmProcessDTO = new AlarmProcessDTO();
			  alarmProcessDTO.setStartTime(startTime);
			  alarmProcessDTO.setEndTime(endTime);
			  //获取一个月的预警任务处理信息
			  AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
			  List<AlarmProcessVO> alarmProcessList = mapper.queryAlarmProcess(alarmProcessDTO);		  
			  //控制策略统计
			  List<DataDicVO> dataDicList = new ArrayList<>();
			  for(AlarmProcessVO alarmProcessVO : alarmProcessList) {
				  String state = alarmProcessVO.getState();
				  if(state.equals("0")) {
					  //处理中
					  loadNum = loadNum + 1;
				  }else if(state.equals("1")) {
					  //已处理
					  finishNum = finishNum + 1;
					  //TODO  统计使用的控制策略次数
					  //查询控制策略类型
					  
					  
				  }else {
					  //未处理
					  untreatedNum = untreatedNum + 1;
				  }
			  }
			  //获取每月处理状态占比
			  ProcessingStatisticsVO proStat = new ProcessingStatisticsVO();
			  proStat.setLoadingNum((double) (loadNum/(loadNum+finishNum+untreatedNum)));
			  proStat.setFinishNum((double) (finishNum/(loadNum+finishNum+untreatedNum)));
			  proStat.setUntreatedNum((double) (untreatedNum/(loadNum+finishNum+untreatedNum)));
			  
			  
			//获取每月漏损水量与供水量占比
			  IndicatorDTO lossFlowIndicatorDTO = new IndicatorDTO();
			  List<String> lossFlowCodeList = new ArrayList<>();
			  lossFlowCodeList.add(lossFlowCode);
			  lossFlowIndicatorDTO.setCodes(lossFlowCodeList);
			  lossFlowIndicatorDTO.setEndTime(start);
			  lossFlowIndicatorDTO.setStartTime(start);
			  lossFlowIndicatorDTO.setTimeType(timeType);
			  List<IndicatorVO> lossFlowList = indicatorMapper.queryWBBaseIndicData(lossFlowIndicatorDTO);
			  
			  IndicatorDTO allFlowIndicatorDTO = new IndicatorDTO();
			  List<String> allFlowCodeList = new ArrayList<>();
			  allFlowCodeList.add(allFlowCode);
			  allFlowIndicatorDTO.setCodes(allFlowCodeList);
			  allFlowIndicatorDTO.setEndTime(start);
			  allFlowIndicatorDTO.setStartTime(start);
			  allFlowIndicatorDTO.setTimeType(timeType);
			  List<IndicatorVO> allFlowList = indicatorMapper.queryWBBaseIndicData(allFlowIndicatorDTO);
			  proStat.setLossFlowNum(lossFlowList.get(0).getValue()/(lossFlowList.get(0).getValue()+allFlowList.get(0).getValue()));
			  proStat.setAllFlowNum(allFlowList.get(0).getValue()/(lossFlowList.get(0).getValue()+allFlowList.get(0).getValue()));
			  
			  proStatList.add(proStat);
			  //循环增加，开始时间改变
			  i = i + 1;
			  startM = startM + 1;
			  if(startM < 13) {
				  start = startY*10 + startM;
			  }else {
				  start = (startY + 1)*10 + startM - 12;
			  }
		  }
		//2.1漏损处理状态统计总占比信息
			processingStatisticsVO.setLoadingNum((double) (loadNum/(loadNum+finishNum+untreatedNum)));
			processingStatisticsVO.setFinishNum((double) (finishNum/(loadNum+finishNum+untreatedNum)));
			processingStatisticsVO.setUntreatedNum((double) (untreatedNum/(loadNum+finishNum+untreatedNum)));
			
			processingStatisticsVO.setProStatList(proStatList);
		//TODO 2.3所选时间内每月漏损处理策略统计
			
		
		//TODO 3获取漏损处理优先级分析
		//TODO 3.1分区节约水量和占比
		//TODO 3.2分区节约水量改造成本
		
		
		return null;
	}

}
