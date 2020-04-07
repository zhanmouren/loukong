package com.koron.inwlms.service.leakageControl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.ProcessingStatisticsDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.mapper.leakageControl.AlarmProcessMapper;
import com.koron.inwlms.util.TimeUtil;

public class StatisticalAnalysisServiceImpl implements StatisticalAnalysisService{
	
	@TaskAnnotation("queryProcessingStatistics")
	@Override
	public ProcessingStatisticsVO queryProcessingStatistics(SessionFactory factory,ProcessingStatisticsDTO processingStatisticsDTO) throws ParseException {
		ProcessingStatisticsVO processingStatisticsVO = new ProcessingStatisticsVO();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1获取漏损变化
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
		  while(start <= end) {
			  String startTime = TimeUtil.getMonthFirstDay(i);
			  String endTime = TimeUtil.getMonthFirstDay(i+1);
			  AlarmProcessDTO alarmProcessDTO = new AlarmProcessDTO();
			  alarmProcessDTO.setStartTime(startTime);
			  alarmProcessDTO.setEndTime(endTime);
			  //获取一个月的预警任务处理信息
			  AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
			  List<AlarmProcessVO> alarmProcessList = mapper.queryAlarmProcess(alarmProcessDTO);
			  //处理状态占比
			  
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
					  
				  }else {
					  //未处理
					  untreatedNum = untreatedNum + 1;
				  }
			  }
			  
		  }
		//TODO  2.1漏损处理状态统计总占比信息
			processingStatisticsVO.setLoadingNum((double) (loadNum/(loadNum+finishNum+untreatedNum)));
			processingStatisticsVO.setFinishNum((double) (finishNum/(loadNum+finishNum+untreatedNum)));
			processingStatisticsVO.setUntreatedNum((double) (untreatedNum/(loadNum+finishNum+untreatedNum)));
		//TODO 2.3所选时间内每月漏损处理策略统计
			
		//TODO 2.4漏损水量与供水量堆叠面积图
		
		//TODO 3获取漏损处理优先级分析
		//TODO 3.1分区节约水量和占比
		//TODO 3.2分区节约水量改造成本
		
		
		return null;
	}

}
