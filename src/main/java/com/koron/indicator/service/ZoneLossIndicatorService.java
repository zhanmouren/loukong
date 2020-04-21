package com.koron.indicator.service;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.indicator.bean.AddDayIndicatorDTO;
import com.koron.indicator.bean.AddMonthIndicatorDTO;
import com.koron.indicator.bean.AddYearIndicatorDTO;
import com.koron.indicator.bean.CalZoneInfos;
import com.koron.indicator.bean.VZoneInfo;
import com.koron.indicator.mapper.AddIndicatorMapper;
import com.koron.indicator.mapper.ZoneLossIndicatorMapper;
import com.koron.inwlms.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 分区漏损指标计算服务
 * @author csh
 * @Date 2020/04/20
 */
public class ZoneLossIndicatorService {

	/**
	 * 计算日最小夜间流量
	 * @param factory
	 */
	@TaskAnnotation("calDMnf")
	public void calDMnf(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		//2.查询分区监测点的当日24小时各时刻移动时平均流量的最小值
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//TODO 1.查询一级分区跟监测点的关系，调用gis接口获取分区对应的监测点编号
			String code = "C001";  //监测点编号
			
			//2.查询分区监测点的当日24小时各时刻移动时平均流量的最小值
			double dMoniMinFlow = zlimapper.getDMoniMinFlow(code,preDay,new Date());
			Long value = Math.round(dMoniMinFlow*100);  //流量保留两位精度
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDMNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//TODO 1.查询一级分区跟监测点的关系，调用gis接口获取分区对应的监测点编号
			String code = "C002";  //监测点编号
			
			//2.查询分区监测点的当日24小时各时刻移动时平均流量的最小值
			double dMoniMinFlow = zlimapper.getDMoniMinFlow(code,preDay,new Date());
			Long value = Math.round(dMoniMinFlow*100);  //流量保留两位精度
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDMNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//TODO 1.查询一级分区跟监测点的关系，调用gis接口获取分区对应的监测点编号
			String code = "C003";  //监测点编号
			
			//2.查询分区监测点的当日24小时各时刻移动时平均流量的最小值
			double dMoniMinFlow = zlimapper.getDMoniMinFlow(code,preDay,new Date());
			Long value = Math.round(dMoniMinFlow*100);  //流量保留两位精度
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDMNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("3");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			double value = 0.0;
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			if(getvType == 1) {
				//合并虚拟分区
				for (int i = 0; i<vZoneInfos.size(); i++) {
					if(i == 0) {
						//计算主/辅分区的最小夜间流量
						value += zlimapper.getDMnfByNo(vZoneInfos.get(i).getMasCode(), preDay);
						value += zlimapper.getDMnfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}else {
						//计算辅分区的最小夜间流量
						value += zlimapper.getDMnfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}
				}
			}else {
				//相减虚拟分区
				for (int i = 0; i<vZoneInfos.size(); i++) {
					if(i == 0) {
						//计算主/辅分区的最小夜间流量
						value += zlimapper.getDMnfByNo(vZoneInfos.get(i).getMasCode(), preDay);
						value -= zlimapper.getDMnfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}else {
						//计算辅分区的最小夜间流量
						value -= zlimapper.getDMnfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}
				}
			}
			Long rValue = Math.round(value*100);  //流量保留两位精度
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDMNF");
			addDayIndicatorDTO.setAnalysisValue(rValue);
			addDayIndicatorDTO.setValue(rValue);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		double value = 0.0;
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			value += zlimapper.getDMnfByNo(zoneNo, preDay);
		}
		Long rValue = Math.round(value*100);  //流量保留两位精度
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDMNF");
		addDayIndicatorDTO.setAnalysisValue(rValue);
		addDayIndicatorDTO.setValue(rValue);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月最小夜间流量
	 * @param factory
	 */
	@TaskAnnotation("calMMnf")
	public void calMMnf(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getMMnfAvg(zoneNo,"FLDMNF",preMonth);
			Long value = Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getMMnfAvg(zoneNo,"SLDMNF",preMonth);
			Long value = Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getMMnfAvg(zoneNo,"DMDMNF",preMonth);
			Long value = Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getMMnfAvg(zoneNo,"VZDMNF",preMonth);
			Long value = Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		double mMnfValue = zlimapper.getMWnMnfAvg("WNDMNF",preMonth);
		Long value = Math.round(mMnfValue);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMMNF");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年最小夜间流量
	 * @param factory
	 */
	@TaskAnnotation("calYMnf")
	public void calYMnf(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getYMnfAvg(zoneNo,"FLMMNF",year);
			Long value = Math.round(mMnfValue);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("FLYMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
//		//二级分区
//		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
//			//2.查询当前月日最小夜间流量平均值
//			double mMnfValue = zlimapper.getMMnfAvg(zoneNo,"SLDMNF",preMonth);
//			Long value = Math.round(mMnfValue);
//			//3.指标入库
//			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
//			addMonthIndicatorDTO.setYearMonth(preMonth);
//			addMonthIndicatorDTO.setCode("SLMMNF");
//			addMonthIndicatorDTO.setAnalysisValue(value);
//			addMonthIndicatorDTO.setValue(value);
//			addMonthIndicatorDTO.setZoneNo(zoneNo);
//			addMonthIndicatorDTO.setZoneRank("2");
//			addIndicMapper.addZoneLossMIndicData(addMonthIndicatorDTO);
//		}
//		//DMA分区
//		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
//			//2.查询当前月日最小夜间流量平均值
//			double mMnfValue = zlimapper.getMMnfAvg(zoneNo,"DMDMNF",preMonth);
//			Long value = Math.round(mMnfValue);
//			//3.指标入库
//			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
//			addMonthIndicatorDTO.setYearMonth(preMonth);
//			addMonthIndicatorDTO.setCode("DMMMNF");
//			addMonthIndicatorDTO.setAnalysisValue(value);
//			addMonthIndicatorDTO.setValue(value);
//			addMonthIndicatorDTO.setZoneNo(zoneNo);
//			addMonthIndicatorDTO.setZoneRank("2");
//			addIndicMapper.addZoneLossMIndicData(addMonthIndicatorDTO);
//		}
//		//虚拟分区
//		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
//		for (String zoneNo : calZoneInfos.getvZoneLists()) {
//			//2.查询当前月日最小夜间流量平均值
//			double mMnfValue = zlimapper.getMMnfAvg(zoneNo,"VZDMNF",preMonth);
//			Long value = Math.round(mMnfValue);
//			//3.指标入库
//			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
//			addMonthIndicatorDTO.setYearMonth(preMonth);
//			addMonthIndicatorDTO.setCode("VZMMNF");
//			addMonthIndicatorDTO.setAnalysisValue(value);
//			addMonthIndicatorDTO.setValue(value);
//			addMonthIndicatorDTO.setZoneNo(zoneNo);
//			addIndicMapper.addZoneLossMIndicData(addMonthIndicatorDTO);
//		}
//		//全网
//		//1.查询所有一级分区的最小夜间流量，求和
//		double mMnfValue = zlimapper.getMWnMnfAvg("WNDMNF",preMonth);
//		Long value = Math.round(mMnfValue);
//		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
//		addMonthIndicatorDTO.setYearMonth(preMonth);
//		addMonthIndicatorDTO.setCode("WNMMNF");
//		addMonthIndicatorDTO.setAnalysisValue(value);
//		addMonthIndicatorDTO.setValue(value);
//		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算日最小夜间流量时刻
	 * @param factory
	 */
	@TaskAnnotation("calDMnfTime")
	public void calDMnfTime(SessionFactory factory){
		
	}
	
	/**
	 * 计算日扣除大用户的最小夜间流量
	 * @param factory
	 */
	@TaskAnnotation("calDMnfNoBigUser")
	public void calDMnfNoBigUser(SessionFactory factory){
		
	}
	
	/**
	 * 计算月扣除大用户的最小夜间流量
	 * @param factory
	 */
	@TaskAnnotation("calMMnfNoBigUser")
	public void calMMnfNoBigUser(SessionFactory factory){
		
	}
	
	/**
	 * 计算年扣除大用户的最小夜间流量
	 * @param factory
	 */
	@TaskAnnotation("calYMnfNoBigUser")
	public void calYMnfNoBigUser(SessionFactory factory){
		
	}
	
	/**
	 * 计算日区域平均夜间供水压力
	 * @param factory
	 */
	@TaskAnnotation("calDAznp")
	public void calDAznp(SessionFactory factory){
		
	}
	
	/**
	 * 计算月区域平均夜间供水压力
	 * @param factory
	 */
	@TaskAnnotation("calMAznp")
	public void calMAznp(SessionFactory factory){
		
	}
	
	/**
	 * 计算日用户夜间用水量
	 * @param factory
	 */
	@TaskAnnotation("calDLegitimateNightUse")
	public void calDLegitimateNightUse(SessionFactory factory){
		
	}
	
	/**
	 * 计算月用户夜间用水量
	 * @param factory
	 */
	@TaskAnnotation("calMLegitimateNightUse")
	public void calMLegitimateNightUse(SessionFactory factory){
		
	}
	
	/**
	 * 计算年用户夜间用水量
	 * @param factory
	 */
	@TaskAnnotation("calYLegitimateNightUse")
	public void calYLegitimateNightUse(SessionFactory factory){
		
	}
	
	/**
	 * 计算日净最小夜间流量估算值
	 * @param factory
	 */
	@TaskAnnotation("calDEstimatedNetMnf")
	public void calDEstimatedNetMnf(SessionFactory factory){
		
	}
	
	/**
	 * 计算月净最小夜间流量估算值
	 * @param factory
	 */
	@TaskAnnotation("calMEstimatedNetMnf")
	public void calMEstimatedNetMnf(SessionFactory factory){
		
	}
	
	/**
	 * 计算年净最小夜间流量估算值
	 * @param factory
	 */
	@TaskAnnotation("calYLegitimateNightUse")
	public void calYEstimatedNetMnf(SessionFactory factory){
		
	}
	
	/**
	 * 计算日小时日转换因子
	 * @param factory
	 */
	@TaskAnnotation("calDHourDayFactor")
	public void calDHourDayFactor(SessionFactory factory){
		
	}
	
	/**
	 * 计算日漏失量
	 * @param factory
	 */
	@TaskAnnotation("calDLeakage")
	public void calDLeakage(SessionFactory factory){
		
	}
	
	/**
	 * 计算月漏失量
	 * @param factory
	 */
	@TaskAnnotation("calMLeakage")
	public void calMLeakage(SessionFactory factory){
		
	}
	
	/**
	 * 计算年漏失量
	 * @param factory
	 */
	@TaskAnnotation("calYLeakage")
	public void calYLeakage(SessionFactory factory){
		
	}
	
	/**
	 * 计算日漏失率
	 * @param factory
	 */
	@TaskAnnotation("calDLeakageRate")
	public void calDLeakageRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算月漏失率
	 * @param factory
	 */
	@TaskAnnotation("calMLeakageRate")
	public void calMLeakageRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算年漏失率
	 * @param factory
	 */
	@TaskAnnotation("calYLeakageRate")
	public void calYLeakageRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位户数漏失量
	 * @param factory
	 */
	@TaskAnnotation("calDLeakagePerCustomer")
	public void calDLeakagePerCustomer(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位户数漏失量
	 * @param factory
	 */
	@TaskAnnotation("calMLeakagePerCustomerAccount")
	public void calMLeakagePerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位户数漏失量
	 * @param factory
	 */
	@TaskAnnotation("calYLeakagePerCustomerAccount")
	public void calYLeakagePerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位管长漏失量
	 * @param factory
	 */
	@TaskAnnotation("calDLeakagePerPipeLength")
	public void calDLeakagePerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位管长漏失量
	 * @param factory
	 */
	@TaskAnnotation("calMLeakagePerPipeLength")
	public void calMLeakagePerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位管长漏失量
	 * @param factory
	 */
	@TaskAnnotation("calYLeakagePerPipeLength")
	public void calYLeakagePerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算日夜间最小流量/供水量
	 * @param factory
	 */
	@TaskAnnotation("calDMnfTdf")
	public void calDMnfTdf(SessionFactory factory){
		
	}
	
	/**
	 * 计算月夜间最小流量/供水量
	 * @param factory
	 */
	@TaskAnnotation("calMMnfTdf")
	public void calMMnfTdf(SessionFactory factory){
		
	}
	
	/**
	 * 计算年夜间最小流量/供水量
	 * @param factory
	 */
	@TaskAnnotation("calYMnfTdf")
	public void calYMnfTdf(SessionFactory factory){
		
	}
	
	/**
	 * 计算日漏损率
	 * @param factory
	 */
	@TaskAnnotation("calDLossRate")
	public void calDLossRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算月漏损率
	 * @param factory
	 */
	@TaskAnnotation("calMLossRate")
	public void calMLossRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算年漏损率
	 * @param factory
	 */
	@TaskAnnotation("calYLossRate")
	public void calYLossRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位户数漏损量
	 * @param factory
	 */
	@TaskAnnotation("calDLossPerCustomerAccount")
	public void calDLossPerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位户数漏损量
	 * @param factory
	 */
	@TaskAnnotation("calMLossPerCustomerAccount")
	public void calMLossPerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位户数漏损量
	 * @param factory
	 */
	@TaskAnnotation("calYLossPerCustomerAccount")
	public void calYLossPerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位管长漏损量
	 * @param factory
	 */
	@TaskAnnotation("calDLossPerPipeLength")
	public void calDLossPerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位管长漏损量
	 * @param factory
	 */
	@TaskAnnotation("calMLossPerPipeLength")
	public void calMLossPerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位管长漏损量
	 * @param factory
	 */
	@TaskAnnotation("calYLossPerPipeLength")
	public void calYLossPerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位管长单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calDLossPerPipeLengthPerUnitPress")
	public void calDLossPerPipeLengthPerUnitPress(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位管长单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calMLossPerPipeLengthPerUnitPress")
	public void calMLossPerPipeLengthPerUnitPress(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位管长单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calYLossPerPipeLengthPerUnitPress")
	public void calYLossPerPipeLengthPerUnitPress(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位户数单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calDLossPerCustomerAccountPerUnitPress")
	public void calDLossPerCustomerAccountPerUnitPress(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位户数单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calMLossPerCustomerAccountPerUnitPress")
	public void calMLossPerCustomerAccountPerUnitPress(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位户数单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calYLossPerCustomerAccountPerUnitPress")
	public void calYLossPerCustomerAccountPerUnitPress(SessionFactory factory){
		
	}
	
	/**
	 * 计算日产销差率
	 * @param factory
	 */
	@TaskAnnotation("calDNrwRate")
	public void calDNrwRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算月产销差率
	 * @param factory
	 */
	@TaskAnnotation("calMNrwRate")
	public void calMNrwRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算年产销差率
	 * @param factory
	 */
	@TaskAnnotation("calYNrwRate")
	public void calYNrwRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位管长产销差
	 * @param factory
	 */
	@TaskAnnotation("calDNrwPerPipeLength")
	public void calDNrwPerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位管长产销差
	 * @param factory
	 */
	@TaskAnnotation("calMNrwPerPipeLength")
	public void calMNrwPerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位管长产销差
	 * @param factory
	 */
	@TaskAnnotation("calYNrwPerPipeLength")
	public void calYNrwPerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位户数产销差
	 * @param factory
	 */
	@TaskAnnotation("calDNrwPerCustomerAccount")
	public void calDNrwPerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位户数产销差
	 * @param factory
	 */
	@TaskAnnotation("calMNrwPerCustomerAccount")
	public void calMNrwPerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位户数产销差
	 * @param factory
	 */
	@TaskAnnotation("calYNrwPerCustomerAccount")
	public void calYNrwPerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算日未计量用水量占比
	 * @param factory
	 */
	@TaskAnnotation("calDUfwcRate")
	public void calDUfwcRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算月未计量用水量占比
	 * @param factory
	 */
	@TaskAnnotation("calMUfwcRate")
	public void calMUfwcRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算年未计量用水量占比
	 * @param factory
	 */
	@TaskAnnotation("calYUfwcRate")
	public void calYUfwcRate(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位管长未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calDUfwcPerPipeLength")
	public void calDUfwcPerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位管长未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calMUfwcPerPipeLength")
	public void calMUfwcPerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位管长未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calYUfwcPerPipeLength")
	public void calYUfwcPerPipeLength(SessionFactory factory){
		
	}
	
	/**
	 * 计算日单位户数未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calDUfwcPerCustomerAccount")
	public void calDUfwcPerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算月单位户数未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calMUfwcPerCustomerAccount")
	public void calMUfwcPerCustomerAccount(SessionFactory factory){
		
	}
	
	/**
	 * 计算年单位户数未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calYUfwcPerCustomerAccount")
	public void calYUfwcPerCustomerAccount(SessionFactory factory){
		
	}
}
