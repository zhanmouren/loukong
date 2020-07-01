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
import com.koron.inwlms.bean.VO.leakageControl.PolicyTypeNum;
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
		WarningSchemeMapper warningMapper = factory.getMapper(WarningSchemeMapper.class);
		TreeMapper treemapper = factory.getMapper(TreeMapper.class);
		ProcessingStatisticsAllDataVO psad = new ProcessingStatisticsAllDataVO();
		ProcessingStatisticsVO processingStatisticsVO = new ProcessingStatisticsVO();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//1获取漏损变化 
		//获取指标编码 
		  //TODO 查询分区级别,判断分区级别获取对应指标编码
		String areaCode = "";
		List<TreeVO> areaCodeList = new ArrayList<>();
		if(processingStatisticsDTO.getDmaCode() != null && !processingStatisticsDTO.getDmaCode().equals("")) {
			areaCode = processingStatisticsDTO.getDmaCode();
			//获取树的该节点信息
			LongTreeBean node = treemapper.getBeanByForeignIdType(2,areaCode);
			if(node != null) {
				areaCodeList = warningMapper.queryTree(node.getSeq(),node.getType(),node.getMask(),node.getParentMask());
			}
		}else {
			if(processingStatisticsDTO.getFirstPartion() != null && !processingStatisticsDTO.getFirstPartion().equals("")) {
				if(processingStatisticsDTO.getSeconPartion() != null && !processingStatisticsDTO.getSeconPartion().equals("")) {
					areaCode = processingStatisticsDTO.getSeconPartion();
				}else {
					areaCode = processingStatisticsDTO.getFirstPartion();
				}
				//获取树的该节点信息
				LongTreeBean node = treemapper.getBeanByForeignIdType(2,areaCode);
				if(node != null) {
					areaCodeList = warningMapper.queryTree(node.getSeq(),node.getType(),node.getMask(),node.getParentMask());
				}
			}
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
		  List<IndicatorVO> mnfIndicatorList = new ArrayList<>();
		  if(minFlowCode.equals("WNDMNF")) {
			  mnfIndicatorList = indicatorMapper.queryCompanyIndicData(mnfFlowDTO);
		  }else {
			  mnfIndicatorList = indicatorMapper.queryZoneLossIndicData(mnfFlowDTO);
		  }
		  if(mnfIndicatorList != null && mnfIndicatorList.size() != 0) {
			  psad.setMnfBefor(mnfIndicatorList.get(0).getValue());
		  }else {
			  psad.setMnfBefor(0.0);
		  }
		  mnfFlowDTO.setEndTime(end);
		  mnfFlowDTO.setStartTime(end);
		  List<IndicatorVO> mnfIndicatorList1 = new ArrayList<>();
		  if(minFlowCode.equals("WNDMNF")) {
			  mnfIndicatorList1 = indicatorMapper.queryCompanyIndicData(mnfFlowDTO);
		  }else {
			  mnfIndicatorList1 = indicatorMapper.queryZoneLossIndicData(mnfFlowDTO);
		  }
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
		  List<IndicatorVO> lossIndicatorList = new ArrayList<>();
		  if(lossFlowCode.equals("WNDWL")) {
			  lossIndicatorList = indicatorMapper.queryCompanyIndicData(mnfFlowDTO);
		  }else {
			  lossIndicatorList = indicatorMapper.queryWBBaseIndicData(lossFlowDTO);
		  }
		  if(lossIndicatorList != null && lossIndicatorList.size() != 0) {
			  psad.setLossFlowBefor(lossIndicatorList.get(0).getValue());
		  }else {
			  psad.setLossFlowBefor(0.0);
		  }
		  lossFlowDTO.setEndTime(end);
		  lossFlowDTO.setStartTime(end);
		  List<IndicatorVO> lossIndicatorList1 = new ArrayList<>();
		  if(lossFlowCode.equals("WNDWL")) {
			  lossIndicatorList1 = indicatorMapper.queryCompanyIndicData(mnfFlowDTO);
		  }else {
			  lossIndicatorList1 = indicatorMapper.queryWBBaseIndicData(lossFlowDTO);
		  }
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
		  PolicyTypeNum ptn = new PolicyTypeNum();
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
								  
								  ptn = getStrategyStatistics(alarmProcessVO.getRecommendStrategy(),ptn);
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
							  ptn = getStrategyStatistics(alarmProcessVO.getRecommendStrategy(),ptn);
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
			  List<IndicatorVO> lossFlowList = new ArrayList<>();
			  if(lossFlowCode.equals("WNDWL")) {
				  lossFlowList = indicatorMapper.queryCompanyIndicData(lossFlowIndicatorDTO);
			  }else {
				  lossFlowList = indicatorMapper.queryWBBaseIndicData(lossFlowIndicatorDTO);
			  }
			  
			  IndicatorDTO allFlowIndicatorDTO = new IndicatorDTO();
			  List<String> allFlowCodeList = new ArrayList<>();
			  allFlowCodeList.add(allFlowCode);
			  allFlowIndicatorDTO.setCodes(allFlowCodeList);
			  allFlowIndicatorDTO.setEndTime(start);
			  allFlowIndicatorDTO.setStartTime(start);
			  allFlowIndicatorDTO.setTimeType(timeType);
			  List<IndicatorVO> allFlowList = new ArrayList<>();
			  if(allFlowCode.equals("WNMFWSSITDF")) {
				  allFlowList = indicatorMapper.queryCompanyIndicData(allFlowIndicatorDTO);
			  }else {
				  allFlowList = indicatorMapper.queryWBBaseIndicData(allFlowIndicatorDTO);
			  }
			  if(lossFlowList == null || lossFlowList.size() == 0) {
				  if(allFlowList == null || allFlowList.size() == 0) {
					  proStat.setLossFlowNum(0.0);
					  proStat.setAllFlowNum(0.0);
				  }else {
					  proStat.setLossFlowNum(Math.abs(0)/(0 + Math.abs(allFlowList.get(0).getValue())));
					  proStat.setAllFlowNum(Math.abs(allFlowList.get(0).getValue())/(0 + Math.abs(allFlowList.get(0).getValue())));  
				  }
			  }else {
				  if(allFlowList == null || allFlowList.size() == 0) {
					  proStat.setLossFlowNum(Math.abs(lossFlowList.get(0).getValue())/(Math.abs(lossFlowList.get(0).getValue()) + 0));
					  proStat.setAllFlowNum(Math.abs(0)/(Math.abs(lossFlowList.get(0).getValue()) + 0));
				  }else {
					  proStat.setLossFlowNum(Math.abs(lossFlowList.get(0).getValue())/(Math.abs(lossFlowList.get(0).getValue()) + Math.abs(allFlowList.get(0).getValue())));
					  proStat.setAllFlowNum(Math.abs(allFlowList.get(0).getValue())/(Math.abs(lossFlowList.get(0).getValue()) + Math.abs(allFlowList.get(0).getValue()))); 
				  }
				  
			  }
			  
			  
			  proStat.setMonth(start);
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
			psad.setPtn(ptn);
			psad.setPsv(processingStatisticsVO);
		return psad;
	}
	
	public PolicyTypeNum getStrategyStatistics(String Strategy,PolicyTypeNum ptn) {
		if(Strategy.equals(Constant.DATADICTIONARY_PCSTRA)) {
			if(ptn.getP1() == null) {
				ptn.setP1(0);
			}
			Integer num = ptn.getP1() + 1;
			ptn.setP1(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PNLDETECTION)) {
			if(ptn.getP2() == null) {
				ptn.setP2(0);
			}
			Integer num = ptn.getP2() + 1;
			ptn.setP2(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_FLOWCHANGE)) {
			if(ptn.getP3() == null) {
				ptn.setP3(0);
			}
			Integer num = ptn.getP3() + 1;
			ptn.setP3(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PNCHANGE)) {
			if(ptn.getP4() == null) {
				ptn.setP4(0);
			}
			Integer num = ptn.getP4() + 1;
			ptn.setP4(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDPN)) {
			if(ptn.getP5() == null) {
				ptn.setP5(0);
			}
			Integer num = ptn.getP5() + 1;
			ptn.setP5(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDPNLD)) {
			if(ptn.getP6() == null) {
				ptn.setP6(0);
			}
			Integer num = ptn.getP6() + 1;
			ptn.setP6(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDFLOWC)) {
			if(ptn.getP7() == null) {
				ptn.setP7(0);
			}
			Integer num = ptn.getP7() + 1;
			ptn.setP7(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_FLOWCANDPNLD)) {
			if(ptn.getP8() == null) {
				ptn.setP8(0);
			}
			Integer num = ptn.getP8() + 1;
			ptn.setP8(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PNANDFLOWC)) {
			if(ptn.getP9() == null) {
				ptn.setP9(0);
			}
			Integer num = ptn.getP9() + 1;
			ptn.setP9(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDPNANDFC)) {
			if(ptn.getP10() == null) {
				ptn.setP10(0);
			}
			Integer num = ptn.getP10() + 1;
			ptn.setP10(num);
		}else if(Strategy.equals(Constant.DATADICTIONARY_PCANDPNLDANDFC)) {
			if(ptn.getP11() == null) {
				ptn.setP11(0);
			}
			Integer num = ptn.getP11() + 1;
			ptn.setP11(num);
		}
		return ptn;
	}

}
