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

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.mapper.TreeMapper;
import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.ProcessingStatisticsDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsAllDataVO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsVO;
import com.koron.inwlms.bean.VO.leakageControl.StrategyStatistics;
import com.koron.inwlms.bean.VO.leakageControl.TreeVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.leakageControl.AlarmMessageMapper;
import com.koron.inwlms.mapper.leakageControl.AlarmProcessMapper;
import com.koron.inwlms.mapper.leakageControl.WarningSchemeMapper;
import com.koron.inwlms.util.TimeUtil;
import com.koron.util.Constant;

@Service
public class StatisticalAnalysisServiceImpl implements StatisticalAnalysisService{
	
	@TaskAnnotation("queryProcessingStatistics")
	@Override
	public ProcessingStatisticsAllDataVO queryProcessingStatistics(SessionFactory factory,ProcessingStatisticsDTO processingStatisticsDTO) throws ParseException {
		IndicatorMapper indicatorMapper = factory.getMapper(IndicatorMapper.class);
		ProcessingStatisticsAllDataVO psad = new ProcessingStatisticsAllDataVO();
		ProcessingStatisticsVO processingStatisticsVO = new ProcessingStatisticsVO();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1获取漏损变化 
		//获取指标编码 
		  //TODO 查询分区级别,判断分区级别获取对应指标编码
		String areaCode = "";
		if(processingStatisticsDTO.getDmaCode() != null && !processingStatisticsDTO.getDmaCode().equals("")) {
			areaCode = processingStatisticsDTO.getDmaCode();
		}else {
			if(processingStatisticsDTO.getFirstPartion() != null && !processingStatisticsDTO.getFirstPartion().equals("")) {
				if(processingStatisticsDTO.getSeconPartion() != null && !processingStatisticsDTO.getSeconPartion().equals("")) {
					areaCode = processingStatisticsDTO.getSeconPartion();
				}else {
					areaCode = processingStatisticsDTO.getFirstPartion();
				}
			}
		}
		WarningSchemeMapper warningMapper = factory.getMapper(WarningSchemeMapper.class);
		TreeMapper treemapper = factory.getMapper(TreeMapper.class);
		//获取树的该节点信息
		LongTreeBean node = treemapper.getBeanByForeignIdType(2,areaCode);
		List<TreeVO> areaCodeList = new ArrayList<>();
		if(node != null) {
			areaCodeList = warningMapper.queryTree(node.getSeq(),node.getType(),node.getMask(),node.getParentMask());
		}
		
		String minNightFlowCode = "";
		List<String> zoneCodeList = new ArrayList<>();
		Integer timeType = Constant.TIME_TYPE_M;
		//TODO 漏损水量指标编码和供水量指标编码
		String lossFlowCode = "";
		String allFlowCode = "";
		//TODO 最小夜间流量指标编码
		String minFlowCode = "";
		zoneCodeList.add(areaCode);
		if(processingStatisticsDTO.getFirstPartion() != null && !processingStatisticsDTO.getFirstPartion().equals("")) {
			if(processingStatisticsDTO.getSeconPartion() != null && !processingStatisticsDTO.getSeconPartion().equals("")) {
				minFlowCode = "SLDMNF";
			}else {
				lossFlowCode="FLMWL";
				allFlowCode = "FLMFWSSITDF";
				minFlowCode = "FLDMNF";
			}
		}else {
			lossFlowCode = "WNDWL";
			allFlowCode = "WNMFWSSITDF";
			minFlowCode = "WNDMNF";
		}
		
		
		//获取选择的所有月份
		  Date startDate = format.parse(processingStatisticsDTO.getStartTime());
		  Date endDate = format.parse(processingStatisticsDTO.getEndTime());
		  int startY = TimeUtil.getYears(startDate);
		  int startM = TimeUtil.getMonth(startDate);
		  int endY = TimeUtil.getYears(endDate);
		  int endM = TimeUtil.getMonth(endDate);
		  
		  int start = startY*100 + startM;
		  int end = endY*100 + endM;
		  
		//TODO  1.1处理前后最小夜间流量7天平均值
		  IndicatorDTO mnfFlowDTO = new IndicatorDTO();
		  List<String> mnfCodes = new ArrayList<>();
		  mnfCodes.add(minFlowCode);
		  mnfFlowDTO.setCodes(mnfCodes);
		  mnfFlowDTO.setEndTime(start);
		  mnfFlowDTO.setStartTime(start);
		  mnfFlowDTO.setTimeType(timeType);
		  mnfFlowDTO.setZoneCodes(zoneCodeList);
		  List<IndicatorVO> mnfIndicatorList = indicatorMapper.queryZoneLossIndicData(mnfFlowDTO);
		  if(mnfIndicatorList != null && mnfIndicatorList.size() != 0) {
			  psad.setMnfBefor(mnfIndicatorList.get(0).getValue());
		  }else {
			  psad.setMnfBefor(0.0);
		  }
		  mnfFlowDTO.setEndTime(end);
		  mnfFlowDTO.setStartTime(end);
		  List<IndicatorVO> mnfIndicatorList1 = indicatorMapper.queryZoneLossIndicData(mnfFlowDTO);
		  if(mnfIndicatorList1 != null && mnfIndicatorList1.size() != 0) {
			  psad.setMnfBefor(mnfIndicatorList1.get(0).getValue());
		  }else {
			  psad.setMnfBefor(0.0);
		  }
		  
		
		//TODO  1.2处理前后漏损水量
		  IndicatorDTO lossFlowDTO = new IndicatorDTO();
		  List<String> lossCodes = new ArrayList<>();
		  lossCodes.add(lossFlowCode);
		  lossFlowDTO.setCodes(lossCodes);
		  lossFlowDTO.setEndTime(start);
		  lossFlowDTO.setStartTime(start);
		  lossFlowDTO.setTimeType(timeType);
		  lossFlowDTO.setZoneCodes(zoneCodeList);
		  List<IndicatorVO> lossIndicatorList = indicatorMapper.queryWBBaseIndicData(lossFlowDTO);
		  if(lossIndicatorList != null && lossIndicatorList.size() != 0) {
			  psad.setLossFlowBefor(lossIndicatorList.get(0).getValue());
		  }else {
			  psad.setLossFlowBefor(0.0);
		  }
		  lossFlowDTO.setEndTime(end);
		  lossFlowDTO.setStartTime(end);
		  List<IndicatorVO> lossIndicatorList1 = indicatorMapper.queryWBBaseIndicData(lossFlowDTO);
		  if(lossIndicatorList1 != null && lossIndicatorList1.size() != 0) {
			  psad.setLossFlowAfther(lossIndicatorList1.get(0).getValue());
		  }else {
			  psad.setLossFlowAfther(0.0);
		  }
		  
		
		//2获取漏损处理情况统计
		//2.2所选时间内每月漏损处理状态统计
		  
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
			  alarmProcessDTO.setType(processingStatisticsDTO.getType());
			  alarmProcessDTO.setStartTime(startTime);
			  alarmProcessDTO.setEndTime(endTime);
			  //获取一个月的预警任务处理信息
			  AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
			  AlarmMessageMapper messageMapper = factory.getMapper(AlarmMessageMapper.class);
			  List<AlarmProcessVO> alarmProcessList = mapper.queryAlarmProcess(alarmProcessDTO);		  
			  //控制策略统计
			  List<DataDicVO> dataDicList = new ArrayList<>();
			  StrategyStatistics strategyst = new StrategyStatistics();
			  if(alarmProcessList != null && alarmProcessList.size() != 0) {
				  for(AlarmProcessVO alarmProcessVO : alarmProcessList) {
					  String state = alarmProcessVO.getState();
					  //查询预警信息
					  if(alarmProcessVO.getWarningCode() != null && !alarmProcessVO.getWarningCode().equals("")) {
						  AlarmMessageVO alarmMessageVO = messageMapper.queryAlarmMessageByCode(alarmProcessVO.getWarningCode());
						  if(areaCodeList != null || areaCodeList.size() != 0) {
							  for(TreeVO treeVO : areaCodeList) {
								  if(alarmMessageVO.getAreaCode() != null && !alarmMessageVO.getAreaCode().equals("")) {
									  if(alarmMessageVO.getAreaCode().equals(treeVO.getCode())) {
										  if(state.equals(Constant.DATADICTIONARY_TASKSTATUSON)) {
											  //处理中
											  loadNum = loadNum + 1;
										  }else if(state.equals(Constant.DATADICTIONARY_TASKSTATUSOVER)) {
											  //已处理
											  finishNum = finishNum + 1;
											  //TODO  统计使用的控制策略次数
											  //查询控制策略类型
										  }else {
											  //未处理
											  untreatedNum = untreatedNum + 1;
										  } 
									  }
								  }
								  
								  strategyst = getStrategyStatistics(alarmProcessVO.getRecommendStrategy(),strategyst);
							  }  
						  }else {
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
							  strategyst = getStrategyStatistics(alarmProcessVO.getRecommendStrategy(),strategyst);
						  }
						  
					  }
					  
				  }
			  }
			  
			  //获取每月处理状态占比
			  ProcessingStatisticsVO proStat = new ProcessingStatisticsVO();
			  if(loadNum == 0 && finishNum == 0 && untreatedNum == 0) {
				  proStat.setLoadingNum(0.0);
				  proStat.setFinishNum(0.0);
				  proStat.setUntreatedNum(0.0);
			  }else {
				  proStat.setLoadingNum((double) (loadNum/(loadNum+finishNum+untreatedNum)));
				  proStat.setFinishNum((double) (finishNum/(loadNum+finishNum+untreatedNum)));
				  proStat.setUntreatedNum((double) (untreatedNum/(loadNum+finishNum+untreatedNum)));
			  }
			  
			  
			  
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
			  if(lossFlowList == null || lossFlowList.size() == 0) {
				  if(allFlowList == null || allFlowList.size() == 0) {
					  proStat.setLossFlowNum(0.0);
					  proStat.setAllFlowNum(0.0);
				  }else {
					  proStat.setLossFlowNum(Math.abs(lossFlowList.get(0).getValue())/(Math.abs(lossFlowList.get(0).getValue()) + Math.abs(allFlowList.get(0).getValue())));
					  proStat.setAllFlowNum(Math.abs(allFlowList.get(0).getValue())/(Math.abs(lossFlowList.get(0).getValue()) + Math.abs(allFlowList.get(0).getValue())));  
				  }
			  }else {
				  proStat.setLossFlowNum(Math.abs(lossFlowList.get(0).getValue())/(Math.abs(lossFlowList.get(0).getValue()) + Math.abs(allFlowList.get(0).getValue())));
				  proStat.setAllFlowNum(Math.abs(allFlowList.get(0).getValue())/(Math.abs(lossFlowList.get(0).getValue()) + Math.abs(allFlowList.get(0).getValue())));
			  }
			  
			  
			  proStat.setMonth(start);
			  proStat.setStrategy(strategyst);
			  proStatList.add(proStat);
			  //循环增加，开始时间改变
			  i = i + 1;
			  startM = startM + 1;
			  if(startM < 13) {
				  start = startY*100 + startM;
			  }else {
				  start = (startY + 1)*100 + startM - 12;
			  }
		  }
		//2.1漏损处理状态统计总占比信息
		  if(loadNum == 0 && finishNum == 0 && untreatedNum == 0) {
			  processingStatisticsVO.setLoadingNum(0.0);
			  processingStatisticsVO.setFinishNum(0.0);
			  processingStatisticsVO.setUntreatedNum(0.0);
		  }else {
			  processingStatisticsVO.setLoadingNum((double) (loadNum/(loadNum+finishNum+untreatedNum)));
				processingStatisticsVO.setFinishNum((double) (finishNum/(loadNum+finishNum+untreatedNum)));
				processingStatisticsVO.setUntreatedNum((double) (untreatedNum/(loadNum+finishNum+untreatedNum)));
		  }
			
			
			processingStatisticsVO.setProStatList(proStatList);
		//TODO 2.3所选时间内每月漏损处理策略统计
			
		
		//TODO 3获取漏损处理优先级分析
		//TODO 3.1分区节约水量和占比
		//TODO 3.2分区节约水量改造成本
		
			psad.setPsv(processingStatisticsVO);
		return psad;
	}
	
	public StrategyStatistics getStrategyStatistics(String Strategy,StrategyStatistics Strategyst) {
		if(Strategy.equals(Constant.DATADICTIONARY_PCSTRA)) {
			if(Strategyst.getP() == null) {
				Strategyst.setP(0);
			}
			Integer num = Strategyst.getP() + 1;
			Strategyst.setP(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PNLDETECTION)) {
			if(Strategyst.getL() == null) {
				Strategyst.setL(0);
			}
			Integer num = Strategyst.getL() + 1;
			Strategyst.setL(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_FLOWCHANGE)) {
			if(Strategyst.getF() == null) {
				Strategyst.setF(0);
			}
			Integer num = Strategyst.getF() + 1;
			Strategyst.setF(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PNCHANGE)) {
			if(Strategyst.getR() == null) {
				Strategyst.setR(0);
			}
			Integer num = Strategyst.getR() + 1;
			Strategyst.setR(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDPN)) {
			if(Strategyst.getPr() == null) {
				Strategyst.setPr(0);
			}
			Integer num = Strategyst.getPr() + 1;
			Strategyst.setPr(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDPNLD)) {
			if(Strategyst.getPl() == null) {
				Strategyst.setPl(0);
			}
			Integer num = Strategyst.getPl() + 1;
			Strategyst.setPl(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDFLOWC)) {
			if(Strategyst.getPf() == null) {
				Strategyst.setPf(0);
			}
			Integer num = Strategyst.getPf() + 1;
			Strategyst.setPf(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_FLOWCANDPNLD)) {
			if(Strategyst.getLf() == null) {
				Strategyst.setLf(0);
			}
			Integer num = Strategyst.getLf() + 1;
			Strategyst.setLf(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PNANDFLOWC)) {
			if(Strategyst.getRf() == null) {
				Strategyst.setRf(0);
			}
			Integer num = Strategyst.getRf() + 1;
			Strategyst.setRf(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDPNANDFC)) {
			if(Strategyst.getPrf() == null) {
				Strategyst.setPrf(0);
			}
			Integer num = Strategyst.getPrf() + 1;
			Strategyst.setPrf(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDPNLDANDFC)) {
			if(Strategyst.getPlf() == null) {
				Strategyst.setPlf(0);
			}
			Integer num = Strategyst.getPlf() + 1;
			Strategyst.setPlf(num);
		}
		return Strategyst;
	}

}
