package com.koron.inwlms.service.report.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.report.ZoneMnfDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.leakageControl.GisExistZoneVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.FlowMeterAnalysis;
import com.koron.inwlms.bean.VO.report.statisticalReport.FlowMeterAnalysisVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.FlowMeterData;
import com.koron.inwlms.bean.VO.report.statisticalReport.MeterAbnormalAnalysisVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.ZoneMnf;
import com.koron.inwlms.bean.VO.report.statisticalReport.ZoneMnfStatistical;
import com.koron.inwlms.bean.VO.report.statisticalReport.ZoneMnfStatisticalVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.ZoneMnfVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.report.AnalysisReportMapper;
import com.koron.inwlms.service.report.AnalysisReportService;
import com.koron.inwlms.util.TimeUtil;
import com.koron.util.Constant;

/**
 * 
 * @author koron 刘刚
 *
 */
@Service
public class AnalysisReportServiceImpl implements AnalysisReportService {
	
	/**
	 * 分区最小夜间流量报表
	 */
	@TaskAnnotation("queryZoneMnf")
	@Override
	public ZoneMnfVO queryZoneMnf(SessionFactory factory,ZoneMnfDTO zoneMnfDTO) {
		IndicatorMapper indicMapper = factory.getMapper(IndicatorMapper.class);
		AnalysisReportMapper anaMapper = factory.getMapper(AnalysisReportMapper.class);
		ZoneMnfVO zoneMnfVO = new ZoneMnfVO();
		List<ZoneMnf> dataList = new ArrayList<ZoneMnf>();
		
		//查询分区信息
		if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_FIRSTZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_ONE);
		}else if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_SECZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_TWO);
		}else if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_DPZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_THREE);
		}
		List<GisExistZoneVO> zoneList = anaMapper.queryZoneData(zoneMnfDTO);
		Integer num = anaMapper.queryZoneDataNum(zoneMnfDTO);
		zoneMnfVO.setPage(zoneMnfDTO.getPage());
		zoneMnfVO.setPageCount(zoneMnfDTO.getPageCount());
		zoneMnfVO.setRowNumber(num);
		Integer totalPage = (int) Math.ceil(num/zoneMnfDTO.getPageCount());
		zoneMnfVO.setTotalPage(totalPage+1);
		
		if(zoneList == null || zoneList.size() == 0) {
			zoneMnfVO.setDataList(dataList);
			return zoneMnfVO;
		}
//		if(zoneMnfDTO.getZoneCode() != null && !zoneMnfDTO.getZoneCode().equals("")) {
//			
//		}else {
//			
//		}
		
		//转化时间格式
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = form.parse(zoneMnfDTO.getStartTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int time = getTimeDay(date,0);
		int time1 = getTimeDay(date,-1);
		int time2 = getTimeDay(date,-7);
		int time3 = getTimeDay(date,-30);
		List<Integer> timeList = new ArrayList<>();
		timeList.add(time);
		timeList.add(time1);
		timeList.add(time2);
		timeList.add(time3);

		
		for(GisExistZoneVO zoneInfo : zoneList) {
			ZoneMnf zoneMnf = new ZoneMnf();
			zoneMnf.setZoneCode(zoneInfo.getPcode());
			zoneMnf.setZoneName(zoneInfo.getName());
			String zoneCode = "";
			//最小夜间流量指标
			String mnfCode = "";
			//供水量指标
			String allFlowCode = "";
			//判断分区级别
			if(zoneInfo.getRank().equals(Constant.DMAZONELEVEL_ONE)) {
				mnfCode = "FLDMNF";
				allFlowCode = "FLDFWSSITDF";
				zoneCode = Constant.DATADICTIONARY_FIRSTZONE;
			}else if(zoneInfo.getRank().equals(Constant.DMAZONELEVEL_TWO)) {
				mnfCode = "SLDMNF";
				zoneCode = Constant.DATADICTIONARY_SECZONE;
			}else if(zoneInfo.getRank().equals(Constant.DMAZONELEVEL_THREE)) {
				mnfCode = "DMDMNF";
				allFlowCode = "DMDFWSSITDF";
				zoneCode = Constant.DATADICTIONARY_DPZONE;
			}
			zoneMnf.setZoneGrade(zoneCode);
			
			IndicatorDTO indicatorDTO = new IndicatorDTO();
			List<String> codes = new ArrayList<>();
			List<String> zoneCodes = new ArrayList<>();
			codes.add(mnfCode);
			codes.add(allFlowCode);
			zoneCodes.add(zoneInfo.getPcode());
			indicatorDTO.setCodes(codes);
			indicatorDTO.setZoneCodes(zoneCodes);
			indicatorDTO.setTimeType(2);
			for(Integer timef : timeList) {
				indicatorDTO.setStartTime(timef);
				indicatorDTO.setEndTime(time);
				//所选日期数据
				List<IndicatorVO> mnfList = indicMapper.queryZoneLossIndicData(indicatorDTO);
				List<IndicatorVO> allFlowList = indicMapper.queryWBBaseIndicData(indicatorDTO);
				if(mnfList != null && mnfList.size() != 0) {
					double prop = 0.0;
					double mnf = 0.0;
					for(IndicatorVO indicatorVO : mnfList) {
						mnf = mnf + indicatorVO.getValue();
					}
					double avgMnf = Math.ceil((mnf/mnfList.size())*100)/100;
					if(allFlowList != null && allFlowList.size() != 0) {
						double allFlow = 0.0;
						for(IndicatorVO indicatorVO : allFlowList) {
							allFlow = allFlow + indicatorVO.getValue();
						}
						prop = mnf/allFlow;
						prop = Math.ceil(prop*10000)/100;	
					}
					if(timef == time) {
						zoneMnf.setTimeMNF(avgMnf);
						zoneMnf.setTimeProp(prop);
					}else if(timef == time1) {
						zoneMnf.setYesterdayMNF(avgMnf);
						zoneMnf.setYesterdayProp(prop);
					}else if(timef == time2) {
						zoneMnf.setSevenDayMNF(avgMnf);
						zoneMnf.setSevenDayProp(prop);
					}else if(timef == time3) {
						zoneMnf.setMonthMNF(avgMnf);
						zoneMnf.setMonthProp(prop);
					}
				}
			}
			//计算增长率
			if(zoneMnf.getTimeMNF() != null && zoneMnf.getTimeMNF() != 0.0) {
				if(zoneMnf.getYesterdayMNF() != null) {
					double grow = (zoneMnf.getYesterdayMNF() - zoneMnf.getTimeMNF())/zoneMnf.getTimeMNF();
					grow = Math.ceil(grow*10000)/100;
					zoneMnf.setYesterdayGrow(grow);
				}
				if(zoneMnf.getSevenDayMNF() != null) {
					double grow = (zoneMnf.getSevenDayMNF() - zoneMnf.getTimeMNF())/zoneMnf.getTimeMNF();
					grow = Math.ceil(grow*10000)/100;
					zoneMnf.setSevenDayGrow(grow);
				}
				if(zoneMnf.getMonthMNF() != null) {
					double grow = (zoneMnf.getMonthMNF() - zoneMnf.getTimeMNF())/zoneMnf.getTimeMNF();
					grow = Math.ceil(grow*10000)/100;
					zoneMnf.setMonthGrow(grow);
				}
			}
			dataList.add(zoneMnf);
		}
		zoneMnfVO.setDataList(dataList);
		
		return zoneMnfVO;
	}
	
	@TaskAnnotation("queryZoneMnfStatistical")
	@Override
	public ZoneMnfStatisticalVO queryZoneMnfStatistical(SessionFactory factory,ZoneMnfDTO zoneMnfDTO) {
		AnalysisReportMapper anaMapper = factory.getMapper(AnalysisReportMapper.class);
		IndicatorMapper indicMapper = factory.getMapper(IndicatorMapper.class);
		
		ZoneMnfStatisticalVO zoneMnfStatisticalVO = new ZoneMnfStatisticalVO();
		List<ZoneMnfStatistical> dataList = new ArrayList<>();
		
		//查询分区信息
		if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_FIRSTZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_ONE);
		}else if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_SECZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_TWO);
		}else if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_DPZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_THREE);
		}
		List<GisExistZoneVO> zoneList = anaMapper.queryZoneData(zoneMnfDTO);
		Integer num = anaMapper.queryZoneDataNum(zoneMnfDTO);
		zoneMnfStatisticalVO.setPage(zoneMnfDTO.getPage());
		zoneMnfStatisticalVO.setPageCount(zoneMnfDTO.getPageCount());
		zoneMnfStatisticalVO.setRowNumber(num);
		Integer totalPage = (int) Math.ceil(num/zoneMnfDTO.getPageCount());
		zoneMnfStatisticalVO.setTotalPage(totalPage+1);
		if(zoneList == null || zoneList.size() == 0) {
			zoneMnfStatisticalVO.setDataList(dataList);
			return zoneMnfStatisticalVO;
		}
		
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = new Date();
		Date endDate = new Date();
		try {
			startDate = form.parse(zoneMnfDTO.getStartTime());
			endDate = form.parse(zoneMnfDTO.getEndTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer startTime = getTimeDay(startDate,0);
		Integer endTime = getTimeDay(endDate,0);
		
		Integer flagTime = getTimeDay(startDate,0);
		
		for(GisExistZoneVO zoneInf : zoneList) {
			//最小夜间流量指标
			String mnfCode = "";
			//供水量指标
			String allFlowCode = "";
			//分区管长
			String lenCode = "";
			//分区用户数
			String peoCode = "";
			
			ZoneMnfStatistical zoneMnfStatistical = new ZoneMnfStatistical();
			
			zoneMnfStatistical.setZoneName(zoneInf.getName());
			zoneMnfStatistical.setZoneGrade(zoneInf.getRank());
			String zoneCode = "";
			//判断分区级别
			if(zoneInf.getRank().equals(Constant.DMAZONELEVEL_ONE)) {
				mnfCode = "FLDMNF";
				allFlowCode = "FLDFWSSITDF";
				lenCode = "FLMFTPL";
				peoCode = "FLMNOCM";
				zoneCode = Constant.DATADICTIONARY_FIRSTZONE;
			}else if(zoneInf.getRank().equals(Constant.DMAZONELEVEL_TWO)) {
				mnfCode = "SLDMNF";
				lenCode = "SLMFTPL";
				peoCode = "SLMNOCM";
				zoneCode = Constant.DATADICTIONARY_SECZONE;
			}else if(zoneInf.getRank().equals(Constant.DMAZONELEVEL_THREE)) {
				mnfCode = "DMDMNF";
				allFlowCode = "DMDFWSSITDF";
				lenCode = "DMMFTPL";
				peoCode = "DMMNOCM";
				zoneCode = Constant.DATADICTIONARY_DPZONE;
			}
			zoneMnfStatistical.setZoneCode(zoneCode);
			
			Integer startTimeM = getTimeMonth(startDate,0);
			Integer timeId = anaMapper.queryMonthId(startTimeM);
			//获取分区用户数和管长
			IndicatorDTO indicatorDTO1 = new IndicatorDTO();
			List<String> codes1 = new ArrayList<>();
			List<String> zoneCodes = new ArrayList<>();
			codes1.add(lenCode);
			zoneCodes.add(zoneInf.getPcode());
			indicatorDTO1.setCodes(codes1);
			indicatorDTO1.setZoneCodes(zoneCodes);
			indicatorDTO1.setTimeType(3);
			indicatorDTO1.setStartTime(timeId);
			indicatorDTO1.setEndTime(timeId);
			List<IndicatorVO> lenList = indicMapper.queryBaseIndicData(indicatorDTO1);
			List<String> codes2 = new ArrayList<>();
			codes2.add(peoCode);
			indicatorDTO1.setCodes(codes2);
			List<IndicatorVO> peoList = indicMapper.queryBaseIndicData(indicatorDTO1);
			
			double avgMnf = 0.0;
			double prop = 0.0;
			double minMnf = 0.0;
			double maxMnf = 0.0;
			int i = 0;
			int j = 0;
			int k = 0;
			List<Double> allMnfList = new ArrayList<>();
			while(startTime <= endTime) {
				IndicatorDTO indicatorDTO = new IndicatorDTO();
				List<String> codes = new ArrayList<>();
				codes.add(mnfCode);
				codes.add(allFlowCode);
				indicatorDTO.setCodes(codes);
				indicatorDTO.setZoneCodes(zoneCodes);
				indicatorDTO.setTimeType(2);
				indicatorDTO.setStartTime(startTime);
				indicatorDTO.setEndTime(startTime);
				//所选日期数据
				List<IndicatorVO> mnfList = indicMapper.queryZoneLossIndicData(indicatorDTO);
				List<IndicatorVO> allFlowList = indicMapper.queryWBBaseIndicData(indicatorDTO);
				
				if(mnfList != null && mnfList.size() != 0) {
					if(startTime == flagTime) {
						minMnf = mnfList.get(0).getValue();
						maxMnf = mnfList.get(0).getValue();
					}else {
						if(minMnf >= mnfList.get(0).getValue()) {
							minMnf = mnfList.get(0).getValue();
						}
						if(maxMnf <= mnfList.get(0).getValue()) {
							maxMnf = mnfList.get(0).getValue();
						}
					}
					i = i + 1;
					avgMnf = avgMnf + mnfList.get(0).getValue();
					allMnfList.add(mnfList.get(0).getValue());
					
					if(allFlowList != null && allFlowList.size() != 0) {
						prop = prop + mnfList.get(0).getValue()/allFlowList.get(0).getValue();
						j = j + 1;
					}
				}
				k = k + 1;
				startTime = getTimeDay(startDate,k);
			}
			
			if(allMnfList != null && allMnfList.size() != 0) {
				avgMnf = avgMnf/allMnfList.size();
			}
			if(peoList != null && peoList.size() != 0) {
				double avgHouseMnf = avgMnf/peoList.get(0).getValue();
				zoneMnfStatistical.setAvgHouseMnf(Math.ceil(avgHouseMnf*100)/100);
			}
			if(lenList != null && lenList.size() != 0) {
				double avgLengthMnf = avgMnf/lenList.get(0).getValue();
				zoneMnfStatistical.setAvgLengthMnf(Math.ceil(avgLengthMnf*100)/100);
			}
			
			if(j > 0) {
				prop = avgMnf/j;
			}
			if(allMnfList != null && allMnfList.size() != 0) {
				Double medMnf = allMnfList.get((int) Math.ceil(i/2));
				zoneMnfStatistical.setMedMnf(Math.ceil(medMnf*100)/100);
				Double standardMnf = Math.sqrt(getVariance(avgMnf,allMnfList));
				zoneMnfStatistical.setStandardMnf(Math.ceil(standardMnf*100)/100);
			}
			
			
			zoneMnfStatistical.setMinMnf(Math.ceil(minMnf*100)/100);
			zoneMnfStatistical.setMaxMnf(Math.ceil(maxMnf*100)/100);
			zoneMnfStatistical.setAvgMnf(Math.ceil(avgMnf*100)/100);
			zoneMnfStatistical.setAvgProp(Math.ceil(prop*10000)/100);
			
			
			
			
			
			dataList.add(zoneMnfStatistical);
		}
		zoneMnfStatisticalVO.setDataList(dataList);
		
		return zoneMnfStatisticalVO;
	}
	
	@TaskAnnotation("queryFlowMeterAnalysis")
	@Override
	public FlowMeterAnalysisVO queryFlowMeterAnalysis(SessionFactory factory,ZoneMnfDTO zoneMnfDTO) {
		AnalysisReportMapper anaMapper = factory.getMapper(AnalysisReportMapper.class);
		
		FlowMeterAnalysisVO flowMeterAnalysisVO = new FlowMeterAnalysisVO();
		List<FlowMeterAnalysis> dataList = new ArrayList<>();
		
		if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_FIRSTZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_ONE);
		}else if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_SECZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_TWO);
		}else if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_DPZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_THREE);
		}
		List<GisExistZoneVO> zoneList = anaMapper.queryZoneData(zoneMnfDTO);
		//获取时间
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = form.parse(zoneMnfDTO.getStartTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer monthId = getTimeMonth(date,0);
		
		Double allFlow = 0.0;
		List<String> typeNameList = anaMapper.queryMeterTypeName(zoneMnfDTO.getPageCount(),zoneMnfDTO.getPage());
		Integer num = anaMapper.queryMeterTypeNameNum();
		flowMeterAnalysisVO.setPage(zoneMnfDTO.getPageCount());
		flowMeterAnalysisVO.setPageCount(zoneMnfDTO.getPage());
		flowMeterAnalysisVO.setRowNumber(num);
		flowMeterAnalysisVO.setTotalPage((int) Math.ceil(num/zoneMnfDTO.getPageCount())+1);
		
		for(String name : typeNameList) {
			int meter = 0;
			Double flow = 0.0;
			for(GisExistZoneVO zoneInf : zoneList) {
				List<String> meterNoList = anaMapper.queryMeterNoByZoneCode(zoneInf.getPcode());
				for(String meterNo : meterNoList) {
					List<FlowMeterData> flowMeterDataList = anaMapper.queryCtmByBookNum(meterNo);
					for(FlowMeterData flowMeterData : flowMeterDataList) {
						Double value = anaMapper.queryMeterValueByMonth(flowMeterData.getCtmNum(), monthId);
						if(flowMeterData.getYsName().equals(name)) {
							meter = meter + 1;
							flow = flow + value;
						}
					}
				}
			}
			allFlow = allFlow + flow;
			
			Integer monthTime = getTimeMonth(date,-1);
			Double monthValue = getMeterFlow(anaMapper,monthTime,zoneList,name);
			Double monthContrast = (flow - monthValue)/monthValue;
			Integer yearTime = getTimeMonth(date,-12);
			Double yearValue = getMeterFlow(anaMapper,yearTime,zoneList,name);
			Double yearContrast = (flow - yearValue)/yearValue;
			
			FlowMeterAnalysis flowMeterAnalysis = new FlowMeterAnalysis();
			flowMeterAnalysis.setType(name);
			flowMeterAnalysis.setMeterNum(meter);
			flowMeterAnalysis.setCustomerWater(Math.ceil(flow*100)/100);
			Double unitFlow = flow/meter;
			flowMeterAnalysis.setUnitHouseWater(Math.ceil(unitFlow*100)/100);
			flowMeterAnalysis.setMonthContrast(Math.ceil(monthContrast*10000)/100);
			flowMeterAnalysis.setYearContrast(Math.ceil(yearContrast*10000)/100);
			dataList.add(flowMeterAnalysis);
		}
		
		for(FlowMeterAnalysis flowMeterAnalysis : dataList) {
			Double value = flowMeterAnalysis.getCustomerWater()/allFlow;
			flowMeterAnalysis.setWaterProportion(Math.ceil(value*10000)/100);
		}
		flowMeterAnalysisVO.setDataList(dataList);
		
		return flowMeterAnalysisVO;
	}
	
	public MeterAbnormalAnalysisVO queryMeterAbnormalAnalysis(SessionFactory factory,ZoneMnfDTO zoneMnfDTO) {
		AnalysisReportMapper anaMapper = factory.getMapper(AnalysisReportMapper.class);
		
		if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_FIRSTZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_ONE);
		}else if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_SECZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_TWO);
		}else if(zoneMnfDTO.getZoneGrade().equals(Constant.DATADICTIONARY_DPZONE)) {
			zoneMnfDTO.setZoneGrade(Constant.DMAZONELEVEL_THREE);
		}
		List<GisExistZoneVO> zoneList = anaMapper.queryZoneData(zoneMnfDTO);
		if(zoneList == null || zoneList.size() == 0) {
			
			return null;
		}
		
		for(GisExistZoneVO zoneInf : zoneList) {
			
			
		}
		Map<String,Object> tableData = new HashMap<String, Object>();
		
		
		return null;
	}
	
	public Double getMeterFlow(AnalysisReportMapper anaMapper,Integer time,List<GisExistZoneVO> zoneList,String name) {
		Double flow = 0.0;
		for(GisExistZoneVO zoneInf : zoneList) {
			List<String> meterNoList = anaMapper.queryMeterNoByZoneCode(zoneInf.getPcode());
			for(String meterNo : meterNoList) {
				List<FlowMeterData> flowMeterDataList = anaMapper.queryCtmByBookNum(meterNo);
				for(FlowMeterData flowMeterData : flowMeterDataList) {
					Double value = anaMapper.queryMeterValueByMonth(flowMeterData.getCtmNum(), time);
					if(flowMeterData.getYsName().equals(name)) {
						flow = flow + value;
					}
				}
			}
		}
		return flow;
	}
	
	public Integer getTimeDay(Date date,Integer dayNum) {
		Date newDate = TimeUtil.addDay(date, dayNum);
		int year = TimeUtil.getYears(newDate);
		int month = TimeUtil.getMonth(newDate);
		int day =  TimeUtil.getDays(newDate);
		int time = year*10000 + month*100 + day;
		return time;
	}
	
	public Integer getTimeMonth(Date date,Integer dayNum) {
		Date newDate = TimeUtil.addMonth(date, dayNum);
		int year = TimeUtil.getYears(newDate);
		int month = TimeUtil.getMonth(newDate);
		int time = year*100 + month;
		return time;
	}
	
	public Double getVariance(Double avg,List<Double> list) {
		double variance = 0;
        for (int i = 0; i < list.size(); i++) {
            variance = variance + (Math.pow((list.get(i) - avg), 2));
        }
        variance = variance / list.size();
        return variance;
	}

}
