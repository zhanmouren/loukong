package com.koron.indicator.service;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.indicator.bean.AddDayIndicatorDTO;
import com.koron.indicator.bean.AddMonthIndicatorDTO;
import com.koron.indicator.bean.AddYearIndicatorDTO;
import com.koron.indicator.bean.CalZoneInfos;
import com.koron.indicator.bean.MnfAndMnfTimeInfo;
import com.koron.indicator.bean.VZoneInfo;
import com.koron.indicator.mapper.AddIndicatorMapper;
import com.koron.indicator.mapper.ZoneLossIndicatorMapper;
import com.koron.inwlms.mapper.zoneLoss.LeakageParamSetMapper;
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
	 * 计算日最小夜间流量，和所在时刻
	 * @param factory
	 */
	@TaskAnnotation("calDMnf")
	public void calDMnf(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> mnfLists = new ArrayList<>();
		List<AddDayIndicatorDTO> mnfTimeLists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		//2.查询分区监测点的当日24小时各时刻移动时平均流量的最小值
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//TODO 1.查询一级分区跟监测点的关系，调用gis接口获取分区对应的监测点编号
			String code = "C001";  //监测点编号
			
			//2.查询分区监测点的当日24小时各时刻移动时平均流量的最小值
			MnfAndMnfTimeInfo mnfAndMnfTimeInfo = zlimapper.getDMoniMinFlow(code,"FLHFLOW",preDay,new Date());
			Integer value =mnfAndMnfTimeInfo.getValue();  //流量保留两位精度
			Integer hour = Integer.parseInt(mnfAndMnfTimeInfo.getTime().split(" ")[1].split(":")[0]);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDMNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			mnfLists.add(addDayIndicatorDTO);
			addDayIndicatorDTO.setAnalysisValue(hour);
			addDayIndicatorDTO.setValue(hour);
			addDayIndicatorDTO.setCode("FLDMNFT");
			mnfTimeLists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//TODO 1.查询一级分区跟监测点的关系，调用gis接口获取分区对应的监测点编号
			String code = "C002";  //监测点编号
			
			//2.查询分区监测点的当日24小时各时刻移动时平均流量的最小值
			MnfAndMnfTimeInfo mnfAndMnfTimeInfo = zlimapper.getDMoniMinFlow(code,"SLHFLOW",preDay,new Date());
			Integer value = mnfAndMnfTimeInfo.getValue();  //流量保留两位精度
			Integer hour = Integer.parseInt(mnfAndMnfTimeInfo.getTime().split(" ")[1].split(":")[0]);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDMNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			mnfLists.add(addDayIndicatorDTO);
			addDayIndicatorDTO.setAnalysisValue(hour);
			addDayIndicatorDTO.setValue(hour);
			addDayIndicatorDTO.setCode("SLDMNFT");
			mnfTimeLists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//TODO 1.查询一级分区跟监测点的关系，调用gis接口获取分区对应的监测点编号
			String code = "C003";  //监测点编号
			
			//2.查询分区监测点的当日24小时各时刻移动时平均流量的最小值
			MnfAndMnfTimeInfo mnfAndMnfTimeInfo = zlimapper.getDMoniMinFlow(code,"DMHFLOW",preDay,new Date());
			Integer value = mnfAndMnfTimeInfo.getValue();  //流量保留两位精度
			Integer hour = Integer.parseInt(mnfAndMnfTimeInfo.getTime().split(" ")[1].split(":")[0]);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDMNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("3");
			addDayIndicatorDTO.setMethod("0");
			mnfLists.add(addDayIndicatorDTO);
			addDayIndicatorDTO.setAnalysisValue(hour);
			addDayIndicatorDTO.setValue(hour);
			addDayIndicatorDTO.setCode("DMDMNFT");
			mnfTimeLists.add(addDayIndicatorDTO);
		}
		
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(mnfLists);
		addIndicMapper.addZoneLossDIndicDataBatch(mnfTimeLists);
		mnfLists.clear();  
		mnfTimeLists.clear();
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			Integer value = 0;
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
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDMNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			mnfLists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(mnfLists);
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		Integer value = 0;
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			value += zlimapper.getDMnfByNo(zoneNo, preDay);
		}
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDMNF");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
		
		//TODO 全网和虚拟分区的最小夜间流量所在的时刻计算
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
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"FLDMNF",preMonth);
			Integer value = (int)Math.round(mMnfValue);
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
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"SLDMNF",preMonth);
			Integer value = (int)Math.round(mMnfValue);
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
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"DMDMNF",preMonth);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"VZDMNF",preMonth);
			Integer value = (int)Math.round(mMnfValue);
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
		Integer mMnfValue = zlimapper.getMWnIndicatorSum("WNDMNF",preMonth);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMMNF");
		addMonthIndicatorDTO.setAnalysisValue(mMnfValue);
		addMonthIndicatorDTO.setValue(mMnfValue);
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
			double mMnfValue = zlimapper.getYIndicatorAvg(zoneNo,"FLMMNF",year);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("FLYMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getYIndicatorAvg(zoneNo,"SLMMNF",year);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("SLYMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getYIndicatorAvg(zoneNo,"DMMMNF",year);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("DMYMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getYIndicatorAvg(zoneNo,"VZMMNF",year);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("VZYMNF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入年指标数据,存在更新，不存在新增
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		Integer yMnfValue = zlimapper.getYWnIndicatorSum("WNMMNF",year);
		AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
		addMonthIndicatorDTO.setYear(year);
		addMonthIndicatorDTO.setCode("WNYMNF");
		addMonthIndicatorDTO.setAnalysisValue(yMnfValue);
		addMonthIndicatorDTO.setValue(yMnfValue);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算日最小夜间流量时刻
	 * @param factory
	 */
//	@TaskAnnotation("calDMnfTime")
//	public void calDMnfTime(SessionFactory factory,CalZoneInfos calZoneInfos){
//		
//	}
	
	/**
	 * 计算日扣除大用户的最小夜间流量
	 * @param factory
	 */
	@TaskAnnotation("calDMnfNoBigUser")
	public void calDMnfNoBigUser(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取所有分区的最小夜间流量
			Integer dMnfValue = zlimapper.getDMnfByNo(zoneNo, preDay);
			//TODO 2.获取所有分区下的大用户的最小夜间流量
			double bigUserDMnfValue = 0.0;
			Integer value = (int) (dMnfValue - Math.round(bigUserDMnfValue*100));  //流量保留两位精度
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDMNFLF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取所有分区的最小夜间流量
			Integer dMnfValue = zlimapper.getDMnfByNo(zoneNo, preDay);
			//TODO 2.获取所有分区下的大用户的最小夜间流量
			double bigUserDMnfValue = 0.0;
			Integer value = (int) (dMnfValue - Math.round(bigUserDMnfValue*100));  //流量保留两位精度
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDMNFLF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取所有分区的最小夜间流量
			Integer dMnfValue = zlimapper.getDMnfByNo(zoneNo, preDay);
			//TODO 2.获取所有分区下的大用户的最小夜间流量
			double bigUserDMnfValue = 0.0;
			Integer value = (int) (dMnfValue - Math.round(bigUserDMnfValue*100));  //流量保留两位精度
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDMNFLF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("3");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		lists.clear();  
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			Integer value = 0;
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			if(getvType == 1) {
				//合并虚拟分区
				for (int i = 0; i<vZoneInfos.size(); i++) {
					if(i == 0) {
						//计算主/辅分区的除大用户的最小夜间流量
						value += zlimapper.getDMnflfByNo(vZoneInfos.get(i).getMasCode(), preDay);
						value += zlimapper.getDMnflfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}else {
						//计算辅分区的除大用户的最小夜间流量
						value += zlimapper.getDMnflfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}
				}
			}else {
				//相减虚拟分区
				for (int i = 0; i<vZoneInfos.size(); i++) {
					if(i == 0) {
						//计算主/辅分区的除大用户的最小夜间流量
						value += zlimapper.getDMnflfByNo(vZoneInfos.get(i).getMasCode(), preDay);
						value -= zlimapper.getDMnflfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}else {
						//计算辅分区的除大用户的最小夜间流量
						value -= zlimapper.getDMnflfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}
				}
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDMNFLF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		Integer value = 0;
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			value += zlimapper.getDMnflfByNo(zoneNo, preDay);
		}
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDMNFLF");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月扣除大用户的最小夜间流量
	 * @param factory
	 */
	@TaskAnnotation("calMMnfNoBigUser")
	public void calMMnfNoBigUser(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//2.查询当前月日除大用户最小夜间流量平均值
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"FLDMNFLF",preMonth);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMMNFLF");
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
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"SLDMNFLF",preMonth);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMMNFLF");
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
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"DMDMNFLF",preMonth);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMMNFLF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"VZDMNLF",preMonth);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMMNFLF");
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
		double mMnfValue = zlimapper.getMWnIndicatorAvg("WNDMNFLF",preMonth);
		Integer value = (int)Math.round(mMnfValue);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMMNFLF");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年扣除大用户的最小夜间流量
	 * @param factory
	 */
	@TaskAnnotation("calYMnfNoBigUser")
	public void calYMnfNoBigUser(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getYIndicatorAvg(zoneNo,"FLMMNFLF",year);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("FLYMNFLF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getYIndicatorAvg(zoneNo,"SLMMNFLF",year);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("SLYMNFLF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getYIndicatorAvg(zoneNo,"DMMMNFLF",year);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("DMYMNFLF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getYIndicatorAvg(zoneNo,"VZMMNFLF",year);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("VZYMNFLF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入年指标数据,存在更新，不存在新增
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		double mMnfValue = zlimapper.getYWnIndicatorAvg("WNMMNFLF",year);
		Integer value = (int)Math.round(mMnfValue);
		AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
		addMonthIndicatorDTO.setYear(year);
		addMonthIndicatorDTO.setCode("WNYMNFLF");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算日区域平均夜间供水压力
	 * @param factory
	 */
	@TaskAnnotation("calDAznp")
	public void calDAznp(SessionFactory factory,CalZoneInfos calZoneInfos){
		
		//查询该时刻的平均压力
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//查询最小夜间流量时刻
			Integer mnfTime = zlimapper.getDMnftByNo(zoneNo,preDay);
			preDay.setHours(mnfTime);
			
			//TODO 查询一级分区跟监测点的关系，调用gis接口获取分区对应的监测点编号
			String stationCode = "C002";  //监测点编号
			List<Integer> hMoniPress = zlimapper.getHMoniPress(stationCode, "FLHASP", preDay,preDay);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDAZNP");
			addDayIndicatorDTO.setAnalysisValue(hMoniPress.size()>0?hMoniPress.get(0):0);
			addDayIndicatorDTO.setValue(hMoniPress.size()>0?hMoniPress.get(0):0);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//查询最小夜间流量时刻
			Integer mnfTime = zlimapper.getDMnftByNo(zoneNo,preDay);
			preDay.setHours(mnfTime);
			
			//TODO 查询一级分区跟监测点的关系，调用gis接口获取分区对应的监测点编号
			String stationCode = "C002";  //监测点编号
			List<Integer> hMoniPress = zlimapper.getHMoniPress(stationCode, "SLHASP",preDay, preDay);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDAZNP");
			addDayIndicatorDTO.setAnalysisValue(hMoniPress.size()>0?hMoniPress.get(0):0);
			addDayIndicatorDTO.setValue(hMoniPress.size()>0?hMoniPress.get(0):0);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//查询最小夜间流量时刻
			Integer mnfTime = zlimapper.getDMnftByNo(zoneNo,preDay);
			preDay.setHours(mnfTime);
			
			//TODO 查询一级分区跟监测点的关系，调用gis接口获取分区对应的监测点编号
			String stationCode = "C002";  //监测点编号
			List<Integer> hMoniPress = zlimapper.getHMoniPress(stationCode, "DMHASP",preDay, preDay);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDAZNP");
			addDayIndicatorDTO.setAnalysisValue(hMoniPress.size()>0?hMoniPress.get(0):0);
			addDayIndicatorDTO.setValue(hMoniPress.size()>0?hMoniPress.get(0):0);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("3");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		lists.clear();  
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			double value = 0.0;
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			int valueNum = 0;
			if(getvType == 1) {
				//合并虚拟分区
				for (int i = 0; i<vZoneInfos.size(); i++) {
					if(i == 0) {
						//计算主/辅分区的平均夜间供水压力
						value += zlimapper.getDAznpByNo(vZoneInfos.get(i).getMasCode(), preDay);
						value += zlimapper.getDAznpByNo(vZoneInfos.get(i).getSecCode(), preDay);
						valueNum += 2;
					}else {
						//计算辅分区的最小夜间流量
						value += zlimapper.getDAznpByNo(vZoneInfos.get(i).getSecCode(), preDay);
						valueNum ++;
					}
				}
			}else {
				//相减虚拟分区
				for (int i = 0; i<vZoneInfos.size(); i++) {
					if(i == 0) {
						//计算主/辅分区的平均夜间供水压力
						value += zlimapper.getDAznpByNo(vZoneInfos.get(i).getMasCode(), preDay);
						value -= zlimapper.getDAznpByNo(vZoneInfos.get(i).getSecCode(), preDay);
						valueNum += 2;
					}else {
						//计算辅分区的最小夜间流量
						value -= zlimapper.getDAznpByNo(vZoneInfos.get(i).getSecCode(), preDay);
						valueNum ++;
					}
				}
			}
			if(valueNum == 0) continue;
			int rValue = (int) Math.round(value/valueNum);  //流量保留两位精度
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
		Integer value = 0;
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			value += zlimapper.getDMnfByNo(zoneNo, preDay);
		}
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDMNF");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月区域平均夜间供水压力
	 * @param factory
	 */
	@TaskAnnotation("calMAznp")
	public void calMAznp(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//2.查询当前月日平均夜间供水压力
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"FLDAZNP",preMonth);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMAZNP");
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
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"SLDAZNP",preMonth);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMAZNP");
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
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"DMDAZNP",preMonth);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMAZNP");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//2.查询当前月日最小夜间流量平均值
			double mMnfValue = zlimapper.getMIndicatorAvg(zoneNo,"VZDAZNP",preMonth);
			Integer value = (int)Math.round(mMnfValue);
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMAZNP");
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
		double mMnfValue = zlimapper.getMWnIndicatorAvg("WNDAZNP",preMonth);
		Integer value = (int)Math.round(mMnfValue);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMAZNP");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算日用户夜间用水量
	 * @param factory
	 */
	@TaskAnnotation("calDLegitimateNightUse")
	public void calDLegitimateNightUse(SessionFactory factory,CalZoneInfos calZoneInfos){
		//TODO 1.调用gis接口，获取分区下所有用户的信息
		//2.查询所有用户类型的用户夜间标准用水量
		//3.计算用户夜间用水量：SUM（A类型用户数*A类用户夜间标准用水量+B类型用户数*B类用户夜间标准用水量……）
	}
	
	/**
	 * 计算月用户夜间用水量
	 * @param factory
	 */
	@TaskAnnotation("calMLegitimateNightUse")
	public void calMLegitimateNightUse(SessionFactory factory){
		//TODO
	}
	
	/**
	 * 计算年用户夜间用水量
	 * @param factory
	 */
	@TaskAnnotation("calYLegitimateNightUse")
	public void calYLegitimateNightUse(SessionFactory factory){
		//TODO
	}
	
//	/**
//	 * 计算日净最小夜间流量估算值
//	 * @param factory
//	 */
//	@TaskAnnotation("calDEstimatedNetMnf")
//	public void calDEstimatedNetMnf(SessionFactory factory){
//		
//	}
	
//	/**
//	 * 计算月净最小夜间流量估算值
//	 * @param factory
//	 */
//	@TaskAnnotation("calMEstimatedNetMnf")
//	public void calMEstimatedNetMnf(SessionFactory factory){
//		
//	}
	
//	/**
//	 * 计算年净最小夜间流量估算值
//	 * @param factory
//	 */
//	@TaskAnnotation("calYLegitimateNightUse")
//	public void calYEstimatedNetMnf(SessionFactory factory){
//		
//	}
	
	/**
	 * 计算日小时日转换因子
	 * @param factory
	 */
	@TaskAnnotation("calDHourDayFactor")
	public void calDHourDayFactor(SessionFactory factory,CalZoneInfos calZoneInfos){
		//1.获取漏损指数
		double le = Double.parseDouble(factory.getMapper(LeakageParamSetMapper.class).queryLeakageExponent());
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//2.获取平均夜间供水压力
			Integer aznp = zlimapper.getDAznpByNo(zoneNo, preDay);
			
			//TODO 3.获取分区与监测点的关系,调用gis接口
			String stationCode = "C001";
			
			//4.获取当天24小时的平均压力
			List<Integer> hMoniPress = zlimapper.getHMoniPress(stationCode, "FLHASP",preDay, new Date());
			
			//5.计算日转换因子
			double zp = 0.0;
			for (Integer hPress : hMoniPress) {
				zp += Math.pow(hPress/aznp, le);
			}
			Integer value = (int)Math.round(zp/(hMoniPress.size()/24)*100);
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDDNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.获取平均夜间供水压力
			Integer aznp = zlimapper.getDAznpByNo(zoneNo, preDay);
			
			//TODO 3.获取分区与监测点的关系,调用gis接口
			String stationCode = "C001";
			
			//4.获取当天24小时的平均压力
			List<Integer> hMoniPress = zlimapper.getHMoniPress(stationCode, "SLHASP",preDay, new Date());
			
			//5.计算日转换因子
			double zp = 0.0;
			for (Integer hPress : hMoniPress) {
				zp += Math.pow(hPress/aznp, le);
			}
			Integer value = (int)Math.round(zp/(hMoniPress.size()/24)*100);
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDDNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.获取平均夜间供水压力
			Integer aznp = zlimapper.getDAznpByNo(zoneNo, preDay);
			
			//TODO 3.获取分区与监测点的关系,调用gis接口
			String stationCode = "C001";
			
			//4.获取当天24小时的平均压力
			List<Integer> hMoniPress = zlimapper.getHMoniPress(stationCode, "DMHASP",preDay, new Date());
			
			//5.计算日转换因子
			double zp = 0.0;
			for (Integer hPress : hMoniPress) {
				zp += Math.pow(hPress/aznp, le);
			}
			Integer value = (int)Math.round(zp/(hMoniPress.size()/24)*100);
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDDNF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("3");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		Integer value = 0;
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			value += zlimapper.getDDnfByNo(zoneNo, preDay);
		}
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDDNF");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
		
	}
	
	/**
	 * 计算日漏失量
	 * @param factory
	 */
	@TaskAnnotation("calDLeakage")
	public void calDLeakage(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//2.获取净MNF（扣除大用户MNF）
			Integer dMnflf = zlimapper.getDMnflfByNo(zoneNo, preDay);
			
			//3.获取时间因子
			Integer dDnf = zlimapper.getDDnfByNo(zoneNo, preDay);
		
			int dLa= Math.round((dFlow/100-(dMnflf/100)*(dDnf/100))*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDLA");
			addDayIndicatorDTO.setAnalysisValue(dLa);
			addDayIndicatorDTO.setValue(dLa);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//2.获取净MNF（扣除大用户MNF）
			Integer dMnflf = zlimapper.getDMnflfByNo(zoneNo, preDay);
			
			//3.获取时间因子
			Integer dDnf = zlimapper.getDDnfByNo(zoneNo, preDay);
		
			int dLa= Math.round((dFlow/100-(dMnflf/100)*(dDnf/100))*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDLA");
			addDayIndicatorDTO.setAnalysisValue(dLa);
			addDayIndicatorDTO.setValue(dLa);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//2.获取净MNF（扣除大用户MNF）
			Integer dMnflf = zlimapper.getDMnflfByNo(zoneNo, preDay);
			
			//3.获取时间因子
			Integer dDnf = zlimapper.getDDnfByNo(zoneNo, preDay);
		
			int dLa= Math.round((dFlow/100-(dMnflf/100)*(dDnf/100))*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDLA");
			addDayIndicatorDTO.setAnalysisValue(dLa);
			addDayIndicatorDTO.setValue(dLa);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("3");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		lists.clear();  
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			Integer value = 0;
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			if(getvType == 1) {
				//合并虚拟分区
				for (int i = 0; i<vZoneInfos.size(); i++) {
					if(i == 0) {
						//计算主/辅分区的平均夜间供水压力
						value += zlimapper.getDLaByNo(vZoneInfos.get(i).getMasCode(), preDay);
						value += zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}else {
						//计算辅分区的最小夜间流量
						value += zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}
				}
			}else {
				//相减虚拟分区
				for (int i = 0; i<vZoneInfos.size(); i++) {
					if(i == 0) {
						//计算主/辅分区的平均夜间供水压力
						value += zlimapper.getDLaByNo(vZoneInfos.get(i).getMasCode(), preDay);
						value -= zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}else {
						//计算辅分区的最小夜间流量
						value -= zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					}
				}
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDLA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		Integer value = 0;
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			value += zlimapper.getDLaByNo(zoneNo, preDay);
		}
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDLA");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月漏失量
	 * @param factory
	 */
	@TaskAnnotation("calMLeakage")
	public void calMLeakage(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//2.查询当前月日漏失量
			Integer mLa = zlimapper.getMIndicatorSum(zoneNo,"FLDLA",preMonth);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMLA");
			addMonthIndicatorDTO.setAnalysisValue(mLa);
			addMonthIndicatorDTO.setValue(mLa);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月日漏失量
			Integer mLa = zlimapper.getMIndicatorSum(zoneNo,"SLDLA",preMonth);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMLA");
			addMonthIndicatorDTO.setAnalysisValue(mLa);
			addMonthIndicatorDTO.setValue(mLa);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月日漏失量
			Integer mLa = zlimapper.getMIndicatorSum(zoneNo,"DMDLA",preMonth);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMLA");
			addMonthIndicatorDTO.setAnalysisValue(mLa);
			addMonthIndicatorDTO.setValue(mLa);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//2.查询当前月日漏失量
			Integer mLa = zlimapper.getMIndicatorSum(zoneNo,"VZDLA",preMonth);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMLA");
			addMonthIndicatorDTO.setAnalysisValue(mLa);
			addMonthIndicatorDTO.setValue(mLa);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		Integer value = zlimapper.getMWnIndicatorSum("WNDLA",preMonth);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMLA");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年漏失量
	 * @param factory
	 */
	@TaskAnnotation("calYLeakage")
	public void calYLeakage(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//2.查询当前月漏失量
			Integer value = zlimapper.getYIndicatorSum(zoneNo,"FLMLA",year);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("FLYLA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月漏失量
			Integer value = zlimapper.getYIndicatorSum(zoneNo,"SLMLA",year);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("SLYLA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//2.查询当前月漏失量
			Integer value = zlimapper.getYIndicatorSum(zoneNo,"DMMLA",year);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("DMYLA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//2.查询当前月漏失量
			Integer value = zlimapper.getYIndicatorSum(zoneNo,"VZMLA",year);
			//3.指标入库
			AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
			addMonthIndicatorDTO.setYear(year);
			addMonthIndicatorDTO.setCode("VZYLA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入年指标数据,存在更新，不存在新增
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		//1.查询全网漏失量的月度加和
		Integer value = zlimapper.getYWnIndicatorSum("WNMLA",year);
		AddYearIndicatorDTO addMonthIndicatorDTO = new AddYearIndicatorDTO();
		addMonthIndicatorDTO.setYear(year);
		addMonthIndicatorDTO.setCode("WNYLA");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算日漏失率
	 * @param factory
	 */
	@TaskAnnotation("calDLeakageRate")
	public void calDLeakageRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日漏失量
			Integer dLa = zlimapper.getDLaByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求日漏失率
			Integer dLrl = Math.round((dLa/dFlow)*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDLRL");
			addDayIndicatorDTO.setAnalysisValue(dLrl);
			addDayIndicatorDTO.setValue(dLrl);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏失量
			Integer dLa = zlimapper.getDLaByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求日漏失率
			Integer dLrl = Math.round((dLa/dFlow)*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDLRL");
			addDayIndicatorDTO.setAnalysisValue(dLrl);
			addDayIndicatorDTO.setValue(dLrl);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏失量
			Integer dLa = zlimapper.getDLaByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求日漏失率
			Integer dLrl = Math.round((dLa/dFlow)*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDLRL");
			addDayIndicatorDTO.setAnalysisValue(dLrl);
			addDayIndicatorDTO.setValue(dLrl);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("3");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasLa = 0;  //主分区日漏失量
			Integer dMasFlow = 0; //主分区日供水量
			Integer dSecLa = 0; //辅分区日漏失量
			Integer dSecFlow = 0; //辅分区日供水量
			Integer dLrl = 0; //漏失率
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日漏失量
					dMasLa += zlimapper.getDLaByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区日供水量
					dMasFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//1.获取辅分区日漏失量
					dSecLa += zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}else {
					//1.获取辅分区日漏失量
					dSecLa += zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				dLrl = Math.round((dMasLa+dSecLa)/(dMasFlow+dSecFlow)*100);
			}else {
				//相减虚拟分区
				dLrl = Math.round((dMasLa-dSecLa)/(dMasFlow-dSecFlow)*100);
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDLRL");
			addDayIndicatorDTO.setAnalysisValue(dLrl);
			addDayIndicatorDTO.setValue(dLrl);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		//1.查询全网日漏失率
		//1.获取日漏失量
		Integer dLa = zlimapper.getDWnLaByNo(preDay);
		
		//2.获取日供水量
		Integer dFlow = zlimapper.getDWnFwssitdfByNo(preDay);
		
		//3.求日漏失率
		Integer dLrl = Math.round((dLa/dFlow)*100);
		
		//3.指标入库
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDLRL");
		addDayIndicatorDTO.setAnalysisValue(dLrl);
		addDayIndicatorDTO.setValue(dLrl);
		addDayIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月漏失率
	 * @param factory
	 */
	@TaskAnnotation("calMLeakageRate")
	public void calMLeakageRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取日供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求月漏失率
			Integer mLrl = Math.round((mLa/mFlow)*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMLRL");
			addMonthIndicatorDTO.setAnalysisValue(mLrl);
			addMonthIndicatorDTO.setValue(mLrl);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取日供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求月漏失率
			Integer mLrl = Math.round((mLa/mFlow)*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMLRL");
			addMonthIndicatorDTO.setAnalysisValue(mLrl);
			addMonthIndicatorDTO.setValue(mLrl);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取日供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求月漏失率
			Integer mLrl = Math.round((mLa/mFlow)*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMLRL");
			addMonthIndicatorDTO.setAnalysisValue(mLrl);
			addMonthIndicatorDTO.setValue(mLrl);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取日供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求月漏失率
			Integer mLrl = Math.round((mLa/mFlow)*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMLRL");
			addMonthIndicatorDTO.setAnalysisValue(mLrl);
			addMonthIndicatorDTO.setValue(mLrl);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		
		//全网
		//1.获取月漏失量
		Integer mLa = zlimapper.getMWnLaByNo(preMonth);
		
		//2.获取日供水量
		Integer mFlow = zlimapper.getMWnFwssitdfByNo(preMonth);
		
		//3.求月漏失率
		Integer mLrl = Math.round((mLa/mFlow)*100);
		
		//3.指标入库
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMLRL");
		addMonthIndicatorDTO.setAnalysisValue(mLrl);
		addMonthIndicatorDTO.setValue(mLrl);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年漏失率
	 * @param factory
	 */
	@TaskAnnotation("calYLeakageRate")
	public void calYLeakageRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个年的时间,格式：yyyy
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取日供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求年漏失率
			Integer value = Math.round((yLa/yFlow)*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYLRL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取日供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求年漏失率
			Integer value = Math.round((yLa/yFlow)*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYLRL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取日供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求年漏失率
			Integer value = Math.round((yLa/yFlow)*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYLRL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取日供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求年漏失率
			Integer value = Math.round((yLa/yFlow)*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYLRL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		//1.获取月漏失量
		Integer yLa = zlimapper.getYWnLaByNo(year);
		
		//2.获取日供水量
		Integer yFlow = zlimapper.getYWnFwssitdfByNo(year);
		
		//3.求月漏失率
		Integer value = Math.round((yLa/yFlow)*100);
		
		//3.指标入库
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("VZYLRL");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日单位户数漏失量
	 * @param factory
	 */
	@TaskAnnotation("calDLeakagePerCustomer")
	public void calDLeakagePerCustomer(SessionFactory factory,CalZoneInfos calZoneInfos){
		//1.查询日漏失量
		//2.查询客户数，月度值
		//3.计算单位户数漏失量
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//获取上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日漏失量
			Integer dLa = zlimapper.getDLaByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(dLa/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDLACA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏失量
			Integer dLa = zlimapper.getDLaByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(dLa/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDLACA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏失量
			Integer dLa = zlimapper.getDLaByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(dLa/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDLACA");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasLa = 0;  //主分区日漏失量
			Integer dMasUserSum = 0; //主分区日供水量
			Integer dSecLa = 0; //辅分区日漏失量
			Integer dSecUserSum = 0; //辅分区日供水量
			Integer value = 0; //漏失率
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日漏失量
					dMasLa += zlimapper.getDLaByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区月用户数
					dMasUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getMasCode(), preMonth);
					//1.获取辅分区日漏失量
					dSecLa += zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月用户数
					dSecUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}else {
					//1.获取辅分区日漏失量
					dSecLa += zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				value = Math.round((dMasLa+dSecLa)/(dMasUserSum+dSecUserSum));
			}else {
				//相减虚拟分区
				value = Math.round((dMasLa-dSecLa)/(dMasUserSum-dSecUserSum));
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDLACA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		//1.查询所有一级分区的最小夜间流量，求和
		Integer dLa = zlimapper.getDWnLaByNo(preDay);
		Integer userSum = zlimapper.getMWnNocmByNo(preMonth);
		Integer value = Math.round(dLa/userSum);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDLACA");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月单位户数漏失量
	 * @param factory
	 */
	@TaskAnnotation("calMLeakagePerCustomerAccount")
	public void calMLeakagePerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(mLa/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMLACA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(mLa/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMLACA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(mLa/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMLACA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(mLa/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMLACA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		
		//全网
		Integer mLa = zlimapper.getMWnLaByNo(preMonth);
		Integer userSum = zlimapper.getMWnNocmByNo(preMonth);
		Integer value = Math.round(mLa/userSum);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMLACA");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
				
	}
	
	/**
	 * 计算年单位户数漏失量
	 * @param factory
	 */
	@TaskAnnotation("calYLeakagePerCustomerAccount")
	public void calYLeakagePerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取年份
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求单位户数漏失量
			Integer value = Math.round(yLa/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYLACA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求单位户数漏失量
			Integer value = Math.round(yLa/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYLACA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求单位户数漏失量
			Integer value = Math.round(yLa/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYLACA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求单位户数漏失量
			Integer value = Math.round(yLa/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYLACA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		Integer mLa = zlimapper.getYWnLaByNo(year);
		Integer userSum = zlimapper.getYWnNocmByNo(year);
		Integer value = Math.round(mLa/userSum);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYLACA");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日单位管长漏失量
	 * @param factory
	 */
	@TaskAnnotation("calDLeakagePerPipeLength")
	public void calDLeakagePerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		//1.查询日漏失量
		//2.查询客户数，月度值
		//3.计算单位户数漏失量
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日漏失量
			Integer dLa = zlimapper.getDLaByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer pipiLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(dLa/pipiLength);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDLAPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏失量
			Integer dLa = zlimapper.getDLaByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer pipiLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(dLa/pipiLength);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDLAPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏失量
			Integer dLa = zlimapper.getDLaByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer pipiLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(dLa/pipiLength);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDLAPL");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasLa = 0;  //主分区日漏失量
			Integer dMasPipeLength = 0; //主分区月管长
			Integer dSecLa = 0; //辅分区日漏失量
			Integer dSecPipeLength = 0; //辅分区月管长
			Integer value = 0; //漏失率
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日漏失量
					dMasLa += zlimapper.getDLaByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区月管长
					dMasPipeLength += zlimapper.getMFtplByNo(vZoneInfos.get(i).getMasCode(), preMonth);
					//1.获取辅分区日漏失量
					dSecLa += zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月管长
					dSecPipeLength += zlimapper.getMFtplByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}else {
					//1.获取辅分区日漏失量
					dSecLa += zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecPipeLength += zlimapper.getMFtplByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				value = Math.round((dMasLa+dSecLa)/(dMasPipeLength+dSecPipeLength));
			}else {
				//相减虚拟分区
				value = Math.round((dMasLa-dSecLa)/(dMasPipeLength-dSecPipeLength));
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDLAPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dLa = zlimapper.getDWnLaByNo(preDay);
		Integer pipeLength = zlimapper.getMWnFtplByNo(preMonth);
		Integer value = Math.round(dLa/pipeLength);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDLAPL");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);		
	}
	
	/**
	 * 计算月单位管长漏失量
	 * @param factory
	 */
	@TaskAnnotation("calMLeakagePerPipeLength")
	public void calMLeakagePerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长漏失量
			Integer value = Math.round(mLa/pipeLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMLAPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长漏失量
			Integer value = Math.round(mLa/pipeLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMLAPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长漏失量
			Integer value = Math.round(mLa/pipeLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMLAPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月漏失量
			Integer mLa = zlimapper.getMLaByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长漏失量
			Integer value = Math.round(mLa/pipeLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMLAPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		
		//全网
		Integer mLa = zlimapper.getMWnLaByNo(preMonth);
		Integer pipeLength = zlimapper.getMWnFtplByNo(preMonth);
		Integer value = Math.round(mLa/pipeLength);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMLAPL");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年单位管长漏失量
	 * @param factory
	 */
	@TaskAnnotation("calYLeakagePerPipeLength")
	public void calYLeakagePerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取年份
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取年管长
			Integer pipeLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求单位管长漏失量
			Integer value = Math.round(yLa/pipeLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYLAPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取年管长
			Integer pipeLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求单位管长漏失量
			Integer value = Math.round(yLa/pipeLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYLAPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取年管长
			Integer pipeLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求单位管长漏失量
			Integer value = Math.round(yLa/pipeLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYLAPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年漏失量
			Integer yLa = zlimapper.getYLaByNo(zoneNo, year);
			
			//2.获取年管长
			Integer pipeLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求单位管长漏失量
			Integer value = Math.round(yLa/pipeLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYLAPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		Integer mLa = zlimapper.getYWnLaByNo(year);
		Integer pipeLength = zlimapper.getYWnFtplByNo(year);
		Integer value = Math.round(mLa/pipeLength);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYLAPL");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日夜间最小流量/供水量
	 * @param factory
	 */
	@TaskAnnotation("calDMnfTdf")
	public void calDMnfTdf(SessionFactory factory,CalZoneInfos calZoneInfos){
		//1.获取最小夜间流量
		//2.获取供水量
		//3.计算日夜间最小流量
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日最小夜间流量
			Integer dMnf = zlimapper.getDMnfByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求单位户数漏失量
			Integer value = Math.round(dMnf*24/dFlow);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDMNFTDF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日最小夜间流量
			Integer dMnf = zlimapper.getDMnfByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求单位户数漏失量
			Integer value = Math.round(dMnf*24/dFlow);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDMNFTDF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日最小夜间流量
			Integer dMnf = zlimapper.getDMnfByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求单位户数漏失量
			Integer value = Math.round(dMnf*24/dFlow);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDMNFTDF");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasMnf = 0;  //主分区日MNF
			Integer dMasFlow = 0; //主分区日供水量
			Integer dSecMnf = 0; //辅分区日MNF
			Integer dSecFlow = 0; //辅分区月管长
			Integer value = 0; //漏失率
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日MNF
					dMasMnf += zlimapper.getDMnfByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区日供水量
					dMasFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//1.获取辅分区日MNF
					dSecMnf += zlimapper.getDMnfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}else {
					//1.获取辅分区日漏失量
					dSecMnf += zlimapper.getDLaByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				value = Math.round((dMasMnf+dSecMnf)/(dMasFlow+dSecFlow));
			}else {
				//相减虚拟分区
				value = Math.round((dMasMnf-dSecMnf)/(dMasFlow-dSecFlow));
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDMNFTDF");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dMnf = zlimapper.getDWnMnfByNo(preDay);
		Integer dFlow = zlimapper.getDWnFwssitdfByNo(preDay);
		Integer value = Math.round(dMnf*24/dFlow);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDMNFTDF");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月夜间最小流量/供水量
	 * @param factory
	 */
	@TaskAnnotation("calMMnfTdf")
	public void calMMnfTdf(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//上个月有效天数
		Integer daySum = TimeUtil.getDaysOfPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日最小夜间流量
			Integer mMnf = zlimapper.getMMnfByNo(zoneNo, preMonth);
			
			//2.获取日供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(mMnf*24/(mFlow/daySum));
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMMNFTDF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日最小夜间流量
			Integer mMnf = zlimapper.getMMnfByNo(zoneNo, preMonth);
			
			//2.获取日供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(mMnf*24/(mFlow/daySum));
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMMNFTDF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日最小夜间流量
			Integer mMnf = zlimapper.getMMnfByNo(zoneNo, preMonth);
			
			//2.获取日供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(mMnf*24/(mFlow/daySum));
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMMNFTDF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取日最小夜间流量
			Integer mMnf = zlimapper.getMMnfByNo(zoneNo, preMonth);
			
			//2.获取日供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求单位户数漏失量
			Integer value = Math.round(mMnf*24/(mFlow/daySum));
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMMNFTDF");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		//全网
		Integer mMnf = zlimapper.getMWnMnfByNo(preMonth);
		Integer mFlow = zlimapper.getMWnFwssitdfByNo(preMonth);
		Integer value = Math.round(mMnf*24/(mFlow/daySum));
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMMNFTDF");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年夜间最小流量/供水量
	 * @param factory
	 */
	@TaskAnnotation("calYMnfTdf")
	public void calYMnfTdf(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//上一年/或当前年
		Integer year = TimeUtil.getPreOrCurrentYear();
		//上一年/或当前年的有效天数
		Integer daySum = TimeUtil.getDaysOfYear(year);
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日最小夜间流量
			Integer yMnf = zlimapper.getYMnfByNo(zoneNo, year);
			
			//2.获取日供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求单位户数漏失量
			Integer value = Math.round(yMnf*24/(yFlow/daySum));
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYMNFTDF");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日最小夜间流量
			Integer yMnf = zlimapper.getYMnfByNo(zoneNo, year);
			
			//2.获取日供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求单位户数漏失量
			Integer value = Math.round(yMnf*24/(yFlow/daySum));
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYMNFTDF");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日最小夜间流量
			Integer yMnf = zlimapper.getYMnfByNo(zoneNo, year);
			
			//2.获取日供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求单位户数漏失量
			Integer value = Math.round(yMnf*24/(yFlow/daySum));
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYMNFTDF");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取日最小夜间流量
			Integer yMnf = zlimapper.getYMnfByNo(zoneNo, year);
			
			//2.获取日供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求单位户数漏失量
			Integer value = Math.round(yMnf*24/(yFlow/daySum));
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYMNFTDF");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		//全网
		Integer mMnf = zlimapper.getYWnMnfByNo(year);
		Integer mFlow = zlimapper.getYWnFwssitdfByNo(year);
		Integer value = Math.round(mMnf*24/(mFlow/daySum));
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYMNFTDF");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日漏损率
	 * @param factory
	 */
	@TaskAnnotation("calDLossRate")
	public void calDLossRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		//1.获取漏损量
		//2.获取供水量
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日漏损量
			Integer dWl = zlimapper.getDWlByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求漏损率
			Integer value = Math.round(dWl/dFlow*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDWL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏损量
			Integer dWl = zlimapper.getDWlByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求漏损率
			Integer value = Math.round(dWl/dFlow*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDWL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏损量
			Integer dWl = zlimapper.getDWlByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求漏损率
			Integer value = Math.round(dWl/dFlow*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDWL");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasWl = 0;  //主分区日MNF
			Integer dMasFlow = 0; //主分区日供水量
			Integer dSecWl = 0; //辅分区日MNF
			Integer dSecFlow = 0; //辅分区月管长
			Integer value = 0; //漏失率
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日漏损量
					dMasWl += zlimapper.getDWlByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区日供水量
					dMasFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//1.获取辅分区日漏损量
					dSecWl += zlimapper.getDWlByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}else {
					//1.获取辅分区日漏损量
					dSecWl += zlimapper.getDWlByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				value = Math.round((dMasWl+dSecWl)/(dMasFlow+dSecFlow)*100);
			}else {
				//相减虚拟分区
				value = Math.round((dMasWl-dSecWl)/(dMasFlow-dSecFlow)*100);
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDWL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dWl = zlimapper.getDWnWlByNo(preDay);
		Integer dFlow = zlimapper.getDWnFwssitdfByNo(preDay);
		Integer value = Math.round(dWl/dFlow*100);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDWL");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月漏损率
	 * @param factory
	 */
	@TaskAnnotation("calMLossRate")
	public void calMLossRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mWl/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMWL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mWl/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMWL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mWl/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMWL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mWl/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMWL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		//全网
		Integer mWl = zlimapper.getMWnWlByNo(preMonth);
		Integer mFlow = zlimapper.getMWnFwssitdfByNo(preMonth);
		Integer value = Math.round(mWl/mFlow*100);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMWL");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年漏损率
	 * @param factory
	 */
	@TaskAnnotation("calYLossRate")
	public void calYLossRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取月供水量
			Integer yFlow = zlimapper.getMFwssitdfByNo(zoneNo, year);
			
			//3.求漏损率
			Integer value = Math.round(yWl/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYWL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取月供水量
			Integer yFlow = zlimapper.getMFwssitdfByNo(zoneNo, year);
			
			//3.求漏损率
			Integer value = Math.round(yWl/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYWL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取月供水量
			Integer yFlow = zlimapper.getMFwssitdfByNo(zoneNo, year);
			
			//3.求漏损率
			Integer value = Math.round(yWl/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYWL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取月供水量
			Integer yFlow = zlimapper.getMFwssitdfByNo(zoneNo, year);
			
			//3.求漏损率
			Integer value = Math.round(yWl/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYWL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		//全网
		Integer yWl = zlimapper.getYWnWlByNo(year);
		Integer yFlow = zlimapper.getYWnFwssitdfByNo(year);
		Integer value = Math.round(yWl/yFlow*100);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYWL");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日单位户数漏损量
	 * @param factory
	 */
	@TaskAnnotation("calDLossPerCustomerAccount")
	public void calDLossPerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		//1.查询日漏损量
		//2.查询客户数，月度值
		//3.计算单位户数漏损量
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//获取上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日漏损量
			Integer dWl = zlimapper.getDWlByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(dWl/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDLCA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏损量
			Integer dWl = zlimapper.getDWlByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(dWl/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDLCA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏损量
			Integer dWl = zlimapper.getDWlByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(dWl/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDLCA");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasWl = 0;  //主分区日漏损量
			Integer dMasUserSum = 0; //主分区日供水量
			Integer dSecWl = 0; //辅分区日漏损量
			Integer dSecUserSum = 0; //辅分区日供水量
			Integer value = 0; //单位户数漏损量
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日漏损量
					dMasWl += zlimapper.getDWlByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区月用户数
					dMasUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getMasCode(), preMonth);
					//1.获取辅分区日漏损量
					dSecWl += zlimapper.getDWlByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月用户数
					dSecUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}else {
					//1.获取辅分区日漏损量
					dSecWl += zlimapper.getDWlByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月用户数
					dSecUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				value = Math.round((dMasWl+dSecWl)/(dMasUserSum+dSecUserSum));
			}else {
				//相减虚拟分区
				value = Math.round((dMasWl-dSecWl)/(dMasUserSum-dSecUserSum));
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDLCA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dWl = zlimapper.getDWnWlByNo(preDay);
		Integer userSum = zlimapper.getMWnNocmByNo(preMonth);
		Integer value = Math.round(dWl/userSum);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDLCA");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);		
	}
	
	/**
	 * 计算月单位户数漏损量
	 * @param factory
	 */
	@TaskAnnotation("calMLossPerCustomerAccount")
	public void calMLossPerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(mWl/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMLCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(mWl/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMLCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(mWl/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMLCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(mWl/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMLCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		
		//全网
		Integer mWl = zlimapper.getMWnWlByNo(preMonth);
		Integer userSum = zlimapper.getMWnNocmByNo(preMonth);
		Integer value = Math.round(mWl/userSum);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMLCA");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年单位户数漏损量
	 * @param factory
	 */
	@TaskAnnotation("calYLossPerCustomerAccount")
	public void calYLossPerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取年份
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求单位户数漏损量
			Integer value = Math.round(yWl/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYLCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求单位户数漏损量
			Integer value = Math.round(yWl/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYLCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求单位户数漏损量
			Integer value = Math.round(yWl/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYLCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求单位户数漏损量
			Integer value = Math.round(yWl/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYLCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		Integer mWl = zlimapper.getYWnWlByNo(year);
		Integer userSum = zlimapper.getYWnNocmByNo(year);
		Integer value = Math.round(mWl/userSum);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYLCA");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日单位管长漏损量
	 * @param factory
	 */
	@TaskAnnotation("calDLossPerPipeLength")
	public void calDLossPerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//获取上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日漏损量
			Integer dWl = zlimapper.getDWlByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(dWl/pipeLength);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDLPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏损量
			Integer dWl = zlimapper.getDWlByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(dWl/pipeLength);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDLPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日漏损量
			Integer dWl = zlimapper.getDWlByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(dWl/pipeLength);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDLPL");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasWl = 0;  //主分区日漏损量
			Integer dMasLength = 0; //主分区日供水量
			Integer dSecWl = 0; //辅分区日漏损量
			Integer dSecLength  = 0; //辅分区日供水量
			Integer value = 0; //单位户数漏损量
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日漏损量
					dMasWl += zlimapper.getDWlByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区月管长
					dMasLength  += zlimapper.getMFtplByNo(vZoneInfos.get(i).getMasCode(), preMonth);
					//1.获取辅分区日漏损量
					dSecWl += zlimapper.getDWlByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月管长
					dSecLength  += zlimapper.getMFtplByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}else {
					//1.获取辅分区日漏损量
					dSecWl += zlimapper.getDWlByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月管长
					dSecLength  += zlimapper.getMFtplByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				value = Math.round((dMasWl+dSecWl)/(dMasLength+dSecLength));
			}else {
				//相减虚拟分区
				value = Math.round((dMasWl-dSecWl)/(dMasLength-dSecLength));
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDLPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dWl = zlimapper.getDWnWlByNo(preDay);
		Integer pipeLength = zlimapper.getMWnFtplByNo(preMonth);
		Integer value = Math.round(dWl/pipeLength);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDLPL");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月单位管长漏损量
	 * @param factory
	 */
	@TaskAnnotation("calMLossPerPipeLength")
	public void calMLossPerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(mWl/pipeLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMLPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(mWl/pipeLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMLPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(mWl/pipeLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMLPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月漏损量
			Integer mWl = zlimapper.getMWlByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer pipeLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位户数漏损量
			Integer value = Math.round(mWl/pipeLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMLPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		
		//全网
		Integer mWl = zlimapper.getMWnWlByNo(preMonth);
		Integer pipeLength = zlimapper.getMWnFtplByNo(preMonth);
		Integer value = Math.round(mWl/pipeLength);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMLPL");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年单位管长漏损量
	 * @param factory
	 */
	@TaskAnnotation("calYLossPerPipeLength")
	public void calYLossPerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取年份
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取年管长
			Integer pipeLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求单位户数漏损量
			Integer value = Math.round(yWl/pipeLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYLPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取年管长
			Integer pipeLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求单位户数漏损量
			Integer value = Math.round(yWl/pipeLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYLPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取年管长
			Integer pipeLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求单位户数漏损量
			Integer value = Math.round(yWl/pipeLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYLPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年漏损量
			Integer yWl = zlimapper.getYWlByNo(zoneNo, year);
			
			//2.获取年管长
			Integer pipeLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求单位户数漏损量
			Integer value = Math.round(yWl/pipeLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYLPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		Integer yWl = zlimapper.getYWnWlByNo(year);
		Integer pipeLength = zlimapper.getYWnFtplByNo(year);
		Integer value = Math.round(yWl/pipeLength);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYLPL");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日单位管长单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calDLossPerPipeLengthPerUnitPress")
	public void calDLossPerPipeLengthPerUnitPress(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日单位管长漏损量
			Integer dLpl = zlimapper.getDLplByNo(zoneNo, preDay);
			
			//2.获取日平均供水压力
			Integer avgPress = zlimapper.getDAspByNo(zoneNo, preDay);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(dLpl/avgPress);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDWLPP");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日单位管长漏损量
			Integer dLpl = zlimapper.getDLplByNo(zoneNo, preDay);
			
			//2.获取日平均供水压力
			Integer avgPress = zlimapper.getDAspByNo(zoneNo, preDay);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(dLpl/avgPress);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDWLPP");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日单位管长漏损量
			Integer dLpl = zlimapper.getDLplByNo(zoneNo, preDay);
			
			//2.获取日平均供水压力
			Integer avgPress = zlimapper.getDAspByNo(zoneNo, preDay);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(dLpl/avgPress);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDWLPP");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("3");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
//		for (String zoneNo : calZoneInfos.getvZoneLists()) {
//			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
//			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
//			int getvType = vZoneInfos.get(0).getvType();
//			Integer dMasLpl = 0;  //主分区日单位管长漏损量
//			Integer dMasAvgPress = 0; //主分区日平均压力
//			Integer dSecLpl = 0; //辅分区日单位管长漏损量
//			Integer dSecAvgPress  = 0; //辅分区日平均压力
//			Integer value = 0; //单位管长单位压力漏损量
//			for (int i = 0; i<vZoneInfos.size(); i++) {
//				if(i == 0) {
//					//1.获取主分区日单位管长漏损量
//					dMasLpl += zlimapper.getDLplByNo(vZoneInfos.get(i).getMasCode(), preDay);
//					//2.获取主分区日平均压力
//					dMasAvgPress  += zlimapper.getDAspByNo(vZoneInfos.get(i).getMasCode(), preDay);
//					//1.获取辅分区日单位管长漏损量
//					dSecLpl += zlimapper.getDLplByNo(vZoneInfos.get(i).getSecCode(), preDay);
//					//2.获取辅分区日平均压力
//					dSecAvgPress += zlimapper.getDAspByNo(vZoneInfos.get(i).getSecCode(), preDay);
//					
//				}else {
//					//1.获取辅分区日单位管长漏损量
//					dSecLpl += zlimapper.getDLplByNo(vZoneInfos.get(i).getSecCode(), preDay);
//					//2.获取辅分区日平均压力
//					dSecAvgPress  += zlimapper.getDAspByNo(vZoneInfos.get(i).getSecCode(), preDay);
//					
//				}
//			}
//			
//			if(getvType == 1) {
//				//合并虚拟分区
//				value = Math.round((dMasLpl+dSecLpl)/(dMasAvgPress+dSecAvgPress));
//			}else {
//				//相减虚拟分区
//				value = Math.round((dMasLpl-dSecLpl)/(dMasAvgPress-dSecAvgPress));
//			}
//			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
//			addDayIndicatorDTO.setAnalysisDate(preDay);
//			addDayIndicatorDTO.setCode("VZDWLPP");
//			addDayIndicatorDTO.setAnalysisValue(value);
//			addDayIndicatorDTO.setValue(value);
//			addDayIndicatorDTO.setZoneNo(zoneNo);
//			addDayIndicatorDTO.setMethod("0");
//			lists.add(addDayIndicatorDTO);
//		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dLpl = zlimapper.getDWnLplByNo(preDay);
		Integer avgPress = zlimapper.getDWnAspByNo(preDay);
		Integer value = Math.round(dLpl/avgPress);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDWLPP");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月单位管长单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calMLossPerPipeLengthPerUnitPress")
	public void calMLossPerPipeLengthPerUnitPress(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月单位管长漏损量
			Integer mLpl = zlimapper.getMLplByNo(zoneNo, preMonth);
			
			//2.获取月平均压力
			Integer avgPress = zlimapper.getMAspByNo(zoneNo, preMonth);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLpl/avgPress);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMWLPP");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月单位管长漏损量
			Integer mLpl = zlimapper.getMLplByNo(zoneNo, preMonth);
			
			//2.获取月平均压力
			Integer avgPress = zlimapper.getMAspByNo(zoneNo, preMonth);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLpl/avgPress);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMWLPP");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月单位管长漏损量
			Integer mLpl = zlimapper.getMLplByNo(zoneNo, preMonth);
			
			//2.获取月平均压力
			Integer avgPress = zlimapper.getMAspByNo(zoneNo, preMonth);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLpl/avgPress);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMWLPP");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
//		for (String zoneNo : calZoneInfos.getvZoneLists()) {
//			//1.获取月单位管长漏损量
//			Integer mLpl = zlimapper.getMLplByNo(zoneNo, preMonth);
//			
//			//2.获取月平均压力
//			Integer avgPress = zlimapper.getMAspByNo(zoneNo, preMonth);
//			
//			//3.求单位管长单位压力漏损量
//			Integer value = Math.round(mLpl/avgPress);
//			
//			//3.指标入库
//			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
//			addMonthIndicatorDTO.setYearMonth(preMonth);
//			addMonthIndicatorDTO.setCode("VZMWLPP");
//			addMonthIndicatorDTO.setAnalysisValue(value);
//			addMonthIndicatorDTO.setValue(value);
//			addMonthIndicatorDTO.setZoneNo(zoneNo);
//			addMonthIndicatorDTO.setMethod("0");
//			lists.add(addMonthIndicatorDTO);
//		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		
		//全网
		Integer mLpl = zlimapper.getMWnLplByNo(preMonth);
		Integer avgPress = zlimapper.getMWnAspByNo(preMonth);
		Integer value = Math.round(mLpl/avgPress);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMWLPP");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年单位管长单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calYLossPerPipeLengthPerUnitPress")
	public void calYLossPerPipeLengthPerUnitPress(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年单位管长漏损量
			Integer mLpl = zlimapper.getYLplByNo(zoneNo, year);
			
			//2.获取年平均压力
			Integer avgPress = zlimapper.getYAspByNo(zoneNo, year);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLpl/avgPress);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYWLPP");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年单位管长漏损量
			Integer mLpl = zlimapper.getYLplByNo(zoneNo, year);
			
			//2.获取年平均压力
			Integer avgPress = zlimapper.getYAspByNo(zoneNo, year);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLpl/avgPress);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYWLPP");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年单位管长漏损量
			Integer mLpl = zlimapper.getYLplByNo(zoneNo, year);
			
			//2.获取年平均压力
			Integer avgPress = zlimapper.getYAspByNo(zoneNo, year);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLpl/avgPress);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYWLPP");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
//		for (String zoneNo : calZoneInfos.getvZoneLists()) {
//			//1.获取年单位管长漏损量
//			Integer mLpl = zlimapper.getYLplByNo(zoneNo, year);
//			
//			//2.获取年平均压力
//			Integer avgPress = zlimapper.getYAspByNo(zoneNo, year);
//			
//			//3.求单位管长单位压力漏损量
//			Integer value = Math.round(mLpl/avgPress);
//			
//			//3.指标入库
//			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
//			addYearIndicatorDTO.setYear(year);
//			addYearIndicatorDTO.setCode("VZYWLPP");
//			addYearIndicatorDTO.setAnalysisValue(value);
//			addYearIndicatorDTO.setValue(value);
//			addYearIndicatorDTO.setZoneNo(zoneNo);
//			addYearIndicatorDTO.setMethod("0");
//			lists.add(addYearIndicatorDTO);
//		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		Integer yLpl = zlimapper.getYWnLplByNo(year);
		Integer avgPress = zlimapper.getYWnAspByNo(year);
		Integer value = Math.round(yLpl/avgPress);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(year);
		addMonthIndicatorDTO.setCode("WNYWLPP");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算日单位户数单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calDLossPerCustomerAccountPerUnitPress")
	public void calDLossPerCustomerAccountPerUnitPress(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日单位户数漏损量
			Integer dLpl = zlimapper.getDLcaByNo(zoneNo, preDay);
			
			//2.获取日平均供水压力
			Integer avgPress = zlimapper.getDAspByNo(zoneNo, preDay);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(dLpl/avgPress);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDWLAP");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日单位户数漏损量
			Integer dLpl = zlimapper.getDLcaByNo(zoneNo, preDay);
			
			//2.获取日平均供水压力
			Integer avgPress = zlimapper.getDAspByNo(zoneNo, preDay);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(dLpl/avgPress);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDWLAP");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日单位户数漏损量
			Integer dLpl = zlimapper.getDLcaByNo(zoneNo, preDay);
			
			//2.获取日平均供水压力
			Integer avgPress = zlimapper.getDAspByNo(zoneNo, preDay);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(dLpl/avgPress);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDWLAP");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("3");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
//		for (String zoneNo : calZoneInfos.getvZoneLists()) {
//			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
//			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
//			int getvType = vZoneInfos.get(0).getvType();
//			Integer dMasLca = 0;  //主分区日单位户数漏损量
//			Integer dMasAvgPress = 0; //主分区日平均压力
//			Integer dSecLca = 0; //辅分区日单位户数漏损量
//			Integer dSecAvgPress  = 0; //辅分区日平均压力
//			Integer value = 0; //单位户数单位压力漏损量
//			for (int i = 0; i<vZoneInfos.size(); i++) {
//				if(i == 0) {
//					//1.获取主分区日单位户数漏损量
//					dMasLca += zlimapper.getDLcaByNo(vZoneInfos.get(i).getMasCode(), preDay);
//					//2.获取主分区日平均压力
//					dMasAvgPress  += zlimapper.getDAspByNo(vZoneInfos.get(i).getMasCode(), preDay);
//					//1.获取辅分区日单位户数漏损量
//					dSecLca += zlimapper.getDLcaByNo(vZoneInfos.get(i).getSecCode(), preDay);
//					//2.获取辅分区日平均压力
//					dSecAvgPress += zlimapper.getDAspByNo(vZoneInfos.get(i).getSecCode(), preDay);
//					
//				}else {
//					//1.获取辅分区日单位户数漏损量
//					dSecLca += zlimapper.getDLcaByNo(vZoneInfos.get(i).getSecCode(), preDay);
//					//2.获取辅分区日平均压力
//					dSecAvgPress  += zlimapper.getDAspByNo(vZoneInfos.get(i).getSecCode(), preDay);
//					
//				}
//			}
//			
//			if(getvType == 1) {
//				//合并虚拟分区
//				value = Math.round((dMasLca+dSecLca)/(dMasAvgPress+dSecAvgPress));
//			}else {
//				//相减虚拟分区
//				value = Math.round((dMasLca-dSecLca)/(dMasAvgPress-dSecAvgPress));
//			}
//			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
//			addDayIndicatorDTO.setAnalysisDate(preDay);
//			addDayIndicatorDTO.setCode("VZDWLAP");
//			addDayIndicatorDTO.setAnalysisValue(value);
//			addDayIndicatorDTO.setValue(value);
//			addDayIndicatorDTO.setZoneNo(zoneNo);
//			addDayIndicatorDTO.setMethod("0");
//			lists.add(addDayIndicatorDTO);
//		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dLca = zlimapper.getDWnLcaByNo(preDay);
		Integer avgPress = zlimapper.getDWnAspByNo(preDay);
		Integer value = Math.round(dLca/avgPress);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDWLAP");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月单位户数单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calMLossPerCustomerAccountPerUnitPress")
	public void calMLossPerCustomerAccountPerUnitPress(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月单位户数漏损量
			Integer mLca = zlimapper.getMLcaByNo(zoneNo, preMonth);
			
			//2.获取月平均压力
			Integer avgPress = zlimapper.getMAspByNo(zoneNo, preMonth);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLca/avgPress);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMWLAP");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月单位户数漏损量
			Integer mLca = zlimapper.getMLcaByNo(zoneNo, preMonth);
			
			//2.获取月平均压力
			Integer avgPress = zlimapper.getMAspByNo(zoneNo, preMonth);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLca/avgPress);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMWLAP");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月单位户数漏损量
			Integer mLca = zlimapper.getMLcaByNo(zoneNo, preMonth);
			
			//2.获取月平均压力
			Integer avgPress = zlimapper.getMAspByNo(zoneNo, preMonth);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLca/avgPress);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMWLAP");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
//		for (String zoneNo : calZoneInfos.getvZoneLists()) {
//			//1.获取月单位户数漏损量
//			Integer mLca = zlimapper.getMLcaByNo(zoneNo, preMonth);
//			
//			//2.获取月平均压力
//			Integer avgPress = zlimapper.getMAspByNo(zoneNo, preMonth);
//			
//			//3.求单位管长单位压力漏损量
//			Integer value = Math.round(mLca/avgPress);
//			
//			//3.指标入库
//			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
//			addMonthIndicatorDTO.setYearMonth(preMonth);
//			addMonthIndicatorDTO.setCode("VZMWLAP");
//			addMonthIndicatorDTO.setAnalysisValue(value);
//			addMonthIndicatorDTO.setValue(value);
//			addMonthIndicatorDTO.setZoneNo(zoneNo);
//			addMonthIndicatorDTO.setMethod("0");
//			lists.add(addMonthIndicatorDTO);
//		}
		
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		
		//全网
		Integer mLca = zlimapper.getMWnLcaByNo(preMonth);
		Integer avgPress = zlimapper.getMWnAspByNo(preMonth);
		Integer value = Math.round(mLca/avgPress);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMWLAP");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年单位户数单位压力漏损量
	 * @param factory
	 */
	@TaskAnnotation("calYLossPerCustomerAccountPerUnitPress")
	public void calYLossPerCustomerAccountPerUnitPress(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//1.获取上个月的时间,格式：yyyyMM
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年单位户数漏损量
			Integer mLca = zlimapper.getYLcaByNo(zoneNo, year);
			
			//2.获取年平均压力
			Integer avgPress = zlimapper.getYAspByNo(zoneNo, year);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLca/avgPress);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYWLAP");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
			
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年单位户数漏损量
			Integer mLca = zlimapper.getYLcaByNo(zoneNo, year);
			
			//2.获取年平均压力
			Integer avgPress = zlimapper.getYAspByNo(zoneNo, year);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLca/avgPress);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYWLAP");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年单位户数漏损量
			Integer mLca = zlimapper.getYLcaByNo(zoneNo, year);
			
			//2.获取年平均压力
			Integer avgPress = zlimapper.getYAspByNo(zoneNo, year);
			
			//3.求单位管长单位压力漏损量
			Integer value = Math.round(mLca/avgPress);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYWLAP");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
//		for (String zoneNo : calZoneInfos.getvZoneLists()) {
//			//1.获取年单位户数漏损量
//			Integer mLca = zlimapper.getYLcaByNo(zoneNo, year);
//			
//			//2.获取年平均压力
//			Integer avgPress = zlimapper.getYAspByNo(zoneNo, year);
//			
//			//3.求单位管长单位压力漏损量
//			Integer value = Math.round(mLca/avgPress);
//			
//			//3.指标入库
//			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
//			addYearIndicatorDTO.setYear(year);
//			addYearIndicatorDTO.setCode("VZYWLAP");
//			addYearIndicatorDTO.setAnalysisValue(value);
//			addYearIndicatorDTO.setValue(value);
//			addYearIndicatorDTO.setZoneNo(zoneNo);
//			addYearIndicatorDTO.setMethod("0");
//			lists.add(addYearIndicatorDTO);
//		}
		
		//批量插入年指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		
		//全网
		Integer yLca = zlimapper.getYWnLcaByNo(year);
		Integer avgPress = zlimapper.getYWnAspByNo(year);
		Integer value = Math.round(yLca/avgPress);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(year);
		addMonthIndicatorDTO.setCode("WNYWLAP");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算日产销差率
	 * @param factory
	 */
	@TaskAnnotation("calDNrwRate")
	public void calDNrwRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日产销差水量
			Integer dNrw = zlimapper.getDNrwByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求漏损率
			Integer value = Math.round(dNrw/dFlow*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDNRW");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日产销差水量
			Integer dNrw = zlimapper.getDNrwByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求漏损率
			Integer value = Math.round(dNrw/dFlow*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDNRW");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日产销差水量
			Integer dNrw = zlimapper.getDNrwByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求漏损率
			Integer value = Math.round(dNrw/dFlow*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDNRW");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasNrw = 0;  //主分区日产销差水量
			Integer dMasFlow = 0; //主分区日供水量
			Integer dSecNrw = 0; //辅分区日产销差水量
			Integer dSecFlow = 0; //辅分区日供水量
			Integer value = 0; //漏失率
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日产销差水量
					dMasNrw += zlimapper.getDNrwByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区日供水量
					dMasFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//1.获取辅分区日产销差水量
					dSecNrw += zlimapper.getDNrwByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}else {
					//1.获取辅分区日产销差水量
					dSecNrw += zlimapper.getDNrwByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				value = Math.round((dMasNrw+dSecNrw)/(dMasFlow+dSecFlow)*100);
			}else {
				//相减虚拟分区
				value = Math.round((dMasNrw-dSecNrw)/(dMasFlow-dSecFlow)*100);
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDNRW");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dNrw = zlimapper.getDWnNrwByNo(preDay);
		Integer dFlow = zlimapper.getDWnFwssitdfByNo(preDay);
		Integer value = Math.round(dNrw/dFlow*100);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDNRW");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月产销差率
	 * @param factory
	 */
	@TaskAnnotation("calMNrwRate")
	public void calMNrwRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mNrw/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMNRW");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mNrw/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMNRW");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mNrw/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMNRW");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mNrw/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMNRW");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		//全网
		Integer mNrw = zlimapper.getMWnNrwByNo(preMonth);
		Integer mFlow = zlimapper.getMWnFwssitdfByNo(preMonth);
		Integer value = Math.round(mNrw/mFlow*100);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMNRW");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年产销差率
	 * @param factory
	 */
	@TaskAnnotation("calYNrwRate")
	public void calYNrwRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYNRW");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYNRW");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYNRW");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYNRW");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//批量插入年指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		//全网
		Integer yNrw = zlimapper.getYWnNrwByNo(year);
		Integer yFlow = zlimapper.getYWnFwssitdfByNo(year);
		Integer value = Math.round(yNrw/yFlow*100);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYNRW");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日单位管长产销差
	 * @param factory
	 */
	@TaskAnnotation("calDNrwPerPipeLength")
	public void calDNrwPerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日产销差水量
			Integer dNrw = zlimapper.getDNrwByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer mFtpl = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(dNrw/mFtpl);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDNRWPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日产销差水量
			Integer dNrw = zlimapper.getDNrwByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer mFtpl = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(dNrw/mFtpl);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDNRWPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日产销差水量
			Integer dNrw = zlimapper.getDNrwByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer mFtpl = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(dNrw/mFtpl);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDNRWPL");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasNrw = 0;  //主分区日产销差水量
			Integer dMasLength = 0; //主分区月管长
			Integer dSecNrw = 0; //辅分区日产销差水量
			Integer dSecLength = 0; //辅分区月管长
			Integer value = 0; 
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日产销差水量
					dMasNrw += zlimapper.getDNrwByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区月管长
					dMasLength += zlimapper.getMFtplByNo(vZoneInfos.get(i).getMasCode(), preMonth);
					//1.获取辅分区日产销差水量
					dSecNrw += zlimapper.getDNrwByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月管长
					dSecLength += zlimapper.getMFtplByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}else {
					//1.获取辅分区日产销差水量
					dSecNrw += zlimapper.getDNrwByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月管长
					dSecLength += zlimapper.getMFtplByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				value = Math.round((dMasNrw+dSecNrw)/(dMasLength+dSecLength)*100);
			}else {
				//相减虚拟分区
				value = Math.round((dMasNrw-dSecNrw)/(dMasLength-dSecLength)*100);
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDNRWPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dNrw = zlimapper.getDWnNrwByNo(preDay);
		Integer length = zlimapper.getMWnFtplByNo(preMonth);
		Integer value = Math.round(dNrw/length);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDNRWPL");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月单位管长产销差
	 * @param factory
	 */
	@TaskAnnotation("calMNrwPerPipeLength")
	public void calMNrwPerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer mLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(mNrw/mLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMNRWPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer mLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(mNrw/mLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMNRWPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer mLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(mNrw/mLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMNRWPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer mLength = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(mNrw/mLength);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMNRWPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		//全网
		Integer mNrw = zlimapper.getMWnNrwByNo(preMonth);
		Integer mLength = zlimapper.getMWnFtplByNo(preMonth);
		Integer value = Math.round(mNrw/mLength);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMNRWPL");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年单位管长产销差
	 * @param factory
	 */
	@TaskAnnotation("calYNrwPerPipeLength")
	public void calYNrwPerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年管长
			Integer yLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/yLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYNRWPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年管长
			Integer yLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/yLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYNRWPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年管长
			Integer yLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/yLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYNRWPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年管长
			Integer yLength = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/yLength);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYNRWPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//批量插入年指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		//全网
		Integer yNrw = zlimapper.getYWnNrwByNo(year);
		Integer yLength = zlimapper.getYWnFtplByNo(year);
		Integer value = Math.round(yNrw/yLength);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYNRWPL");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日单位户数产销差
	 * @param factory
	 */
	@TaskAnnotation("calDNrwPerCustomerAccount")
	public void calDNrwPerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日产销差水量
			Integer dNrw = zlimapper.getDNrwByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(dNrw/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDNRWCA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日产销差水量
			Integer dNrw = zlimapper.getDNrwByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(dNrw/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDNRWCA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日产销差水量
			Integer dNrw = zlimapper.getDNrwByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(dNrw/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDNRWCA");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasNrw = 0;  //主分区日产销差水量
			Integer dMasUserSum = 0; //主分区月管长
			Integer dSecNrw = 0; //辅分区日产销差水量
			Integer dSecUserSum = 0; //辅分区月管长
			Integer value = 0; 
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日产销差水量
					dMasNrw += zlimapper.getDNrwByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区月用户数
					dMasUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getMasCode(), preMonth);
					//1.获取辅分区日产销差水量
					dSecNrw += zlimapper.getDNrwByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月用户数
					dSecUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}else {
					//1.获取辅分区日产销差水量
					dSecNrw += zlimapper.getDNrwByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月用户数
					dSecUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				value = Math.round((dMasNrw+dSecNrw)/(dMasUserSum+dSecUserSum)*100);
			}else {
				//相减虚拟分区
				value = Math.round((dMasNrw-dSecNrw)/(dMasUserSum-dSecUserSum)*100);
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDNRWCA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dNrw = zlimapper.getDWnNrwByNo(preDay);
		Integer userSum = zlimapper.getMWnNocmByNo(preMonth);
		Integer value = Math.round(dNrw/userSum);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDNRWCA");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月单位户数产销差
	 * @param factory
	 */
	@TaskAnnotation("calMNrwPerCustomerAccount")
	public void calMNrwPerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(mNrw/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMNRWCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(mNrw/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMNRWCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(mNrw/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMNRWCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月产销差水量
			Integer mNrw = zlimapper.getMNrwByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求单位管长产销差
			Integer value = Math.round(mNrw/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMNRWCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		//全网
		Integer mNrw = zlimapper.getMWnNrwByNo(preMonth);
		Integer mLength = zlimapper.getMWnNocmByNo(preMonth);
		Integer value = Math.round(mNrw/mLength);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMNRWCA");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年单位户数产销差
	 * @param factory
	 */
	@TaskAnnotation("calYNrwPerCustomerAccount")
	public void calYNrwPerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYNRWCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYNRWCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYNRWCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年产销差水量
			Integer yNrw = zlimapper.getYNrwByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求产销差率
			Integer value = Math.round(yNrw/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYNRWCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//批量插入年指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		//全网
		Integer yNrw = zlimapper.getYWnNrwByNo(year);
		Integer userSum = zlimapper.getYWnNocmByNo(year);
		Integer value = Math.round(yNrw/userSum);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYNRWCA");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日未计量用水量占比
	 * @param factory
	 */
	@TaskAnnotation("calDUfwcRate")
	public void calDUfwcRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日未计量水量
			Integer dUfwc = zlimapper.getDUfwcByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求未计量水量占比
			Integer value = Math.round(dUfwc/dFlow*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDUCRFW");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日未计量水量
			Integer dUfwc = zlimapper.getDUfwcByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求未计量水量占比
			Integer value = Math.round(dUfwc/dFlow*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDUCRFW");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日未计量水量
			Integer dUfwc = zlimapper.getDUfwcByNo(zoneNo, preDay);
			
			//2.获取日供水量
			Integer dFlow = zlimapper.getDFwssitdfByNo(zoneNo, preDay);
			
			//3.求未计量水量占比
			Integer value = Math.round(dUfwc/dFlow*100);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDUCRFW");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasUfwc = 0;  //主分区日未计量水量
			Integer dMasFlow = 0; //主分区日供水量
			Integer dSecUfwc = 0; //辅分区日未计量水量
			Integer dSecFlow = 0; //辅分区日供水量
			Integer value = 0; //漏失率
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日未计量水量
					dMasUfwc += zlimapper.getDUfwcByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区日供水量
					dMasFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//1.获取辅分区日未计量水量
					dSecUfwc += zlimapper.getDUfwcByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}else {
					//1.获取辅分区日未计量水量
					dSecUfwc += zlimapper.getDUfwcByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区日供水量
					dSecFlow += zlimapper.getDFwssitdfByNo(vZoneInfos.get(i).getSecCode(), preDay);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				value = Math.round((dMasUfwc+dSecUfwc)/(dMasFlow+dSecFlow)*100);
			}else {
				//相减虚拟分区
				value = Math.round((dMasUfwc-dSecUfwc)/(dMasFlow-dSecFlow)*100);
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDUCRFW");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dUfwc = zlimapper.getDWnUfwcByNo(preDay);
		Integer dFlow = zlimapper.getDWnFwssitdfByNo(preDay);
		Integer value = Math.round(dUfwc/dFlow*100);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDUCRFW");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月未计量用水量占比
	 * @param factory
	 */
	@TaskAnnotation("calMUfwcRate")
	public void calMUfwcRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mUfwc/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMUCRFW");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mUfwc/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMUCRFW");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mUfwc/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMUCRFW");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月供水量
			Integer mFlow = zlimapper.getMFwssitdfByNo(zoneNo, preMonth);
			
			//3.求漏损率
			Integer value = Math.round(mUfwc/mFlow*100);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMUCRFW");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		//全网
		Integer mUfwc = zlimapper.getMWnUfwcByNo(preMonth);
		Integer mFlow = zlimapper.getMWnFwssitdfByNo(preMonth);
		Integer value = Math.round(mUfwc/mFlow*100);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMUCRFW");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年未计量用水量占比
	 * @param factory
	 */
	@TaskAnnotation("calYUfwcRate")
	public void calYUfwcRate(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求年未计量占比
			Integer value = Math.round(yUfwc/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYUCRFW");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求年未计量占比
			Integer value = Math.round(yUfwc/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYUCRFW");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求年未计量占比
			Integer value = Math.round(yUfwc/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYUCRFW");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年供水量
			Integer yFlow = zlimapper.getYFwssitdfByNo(zoneNo, year);
			
			//3.求年未计量占比
			Integer value = Math.round(yUfwc/yFlow*100);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYUCRFW");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//批量插入年指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		//全网
		Integer yUfwc = zlimapper.getYWnUfwcByNo(year);
		Integer yFlow = zlimapper.getYWnFwssitdfByNo(year);
		Integer value = Math.round(yUfwc/yFlow*100);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYUCRFW");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日单位管长未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calDUfwcPerPipeLength")
	public void calDUfwcPerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日未计量水量
			Integer dUfwc = zlimapper.getDUfwcByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer length = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求日单位管长未计量水量
			Integer value = Math.round(dUfwc/length);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDUFWCPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日未计量水量
			Integer dUfwc = zlimapper.getDUfwcByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer length = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求日单位管长未计量水量
			Integer value = Math.round(dUfwc/length);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDUFWCPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日未计量水量
			Integer dUfwc = zlimapper.getDUfwcByNo(zoneNo, preDay);
			
			//2.获取月管长
			Integer length = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求日单位管长未计量水量
			Integer value = Math.round(dUfwc/length);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDUFWCPL");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasUfwc = 0;  //主分区日未计量水量
			Integer dMasLength = 0; //主分区月管长
			Integer dSecUfwc = 0; //辅分区日未计量水量
			Integer dSecLength = 0; //辅分区月管长
			Integer value = 0; //漏失率
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日未计量水量
					dMasUfwc += zlimapper.getDUfwcByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区月管长
					dMasLength += zlimapper.getMFtplByNo(vZoneInfos.get(i).getMasCode(), preMonth);
					//1.获取辅分区日未计量水量
					dSecUfwc += zlimapper.getDUfwcByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月管长
					dSecLength += zlimapper.getMFtplByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}else {
					//1.获取辅分区日未计量水量
					dSecUfwc += zlimapper.getDUfwcByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月管长
					dSecLength += zlimapper.getMFtplByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				//3.求日漏失率
				value = Math.round((dMasUfwc+dSecUfwc)/(dMasLength+dSecLength));
			}else {
				//相减虚拟分区
				value = Math.round((dMasUfwc-dSecUfwc)/(dMasLength-dSecLength));
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDUFWCPL");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dUfwc = zlimapper.getDWnUfwcByNo(preDay);
		Integer length = zlimapper.getMWnFtplByNo(preMonth);
		Integer value = Math.round(dUfwc/length);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDUFWCPL");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月单位管长未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calMUfwcPerPipeLength")
	public void calMUfwcPerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer mFlow = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求月单位管长未计量水量
			Integer value = Math.round(mUfwc/mFlow);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMUFWCPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer mFlow = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求月单位管长未计量水量
			Integer value = Math.round(mUfwc/mFlow);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMUFWCPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer mFlow = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求月单位管长未计量水量
			Integer value = Math.round(mUfwc/mFlow);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMUFWCPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月管长
			Integer mFlow = zlimapper.getMFtplByNo(zoneNo, preMonth);
			
			//3.求月单位管长未计量水量
			Integer value = Math.round(mUfwc/mFlow);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMUFWCPL");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		//全网
		Integer mUfwc = zlimapper.getMWnUfwcByNo(preMonth);
		Integer length = zlimapper.getMWnFtplByNo(preMonth);
		Integer value = Math.round(mUfwc/length*100);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMUFWCPL");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年单位管长未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calYUfwcPerPipeLength")
	public void calYUfwcPerPipeLength(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//上一年或当前年
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年管长
			Integer length = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求年单位管长未计量水量
			Integer value = Math.round(yUfwc/length);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYUFWCPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年管长
			Integer length = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求年单位管长未计量水量
			Integer value = Math.round(yUfwc/length);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYUFWCPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年管长
			Integer length = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求年单位管长未计量水量
			Integer value = Math.round(yUfwc/length);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYUFWCPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年管长
			Integer length = zlimapper.getYFtplByNo(zoneNo, year);
			
			//3.求年单位管长未计量水量
			Integer value = Math.round(yUfwc/length);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYUFWCPL");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//批量插入年指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		//全网
		Integer yUfwc = zlimapper.getYWnUfwcByNo(year);
		Integer length = zlimapper.getYWnFtplByNo(year);
		Integer value = Math.round(yUfwc/length);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYUFWCPL");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
	
	/**
	 * 计算日单位户数未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calDUfwcPerCustomerAccount")
	public void calDUfwcPerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddDayIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Date preDay = TimeUtil.getPreDay();
		//上个月
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取日未计量水量
			Integer dUfwc = zlimapper.getDUfwcByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求日单位户数未计量水量
			Integer value = Math.round(dUfwc/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("FLDUFWCCA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("1");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日未计量水量
			Integer dUfwc = zlimapper.getDUfwcByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求日单位户数未计量水量
			Integer value = Math.round(dUfwc/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("SLDUFWCCA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setZoneRank("2");
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取日未计量水量
			Integer dUfwc = zlimapper.getDUfwcByNo(zoneNo, preDay);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求日单位户数未计量水量
			Integer value = Math.round(dUfwc/userSum);
			
			//3.指标入库
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("DMDUFWCCA");
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
			List<VZoneInfo> vZoneInfos = zlimapper.queryVZoneInfoByNo(zoneNo);
			if(vZoneInfos == null || vZoneInfos.size() ==0) continue;
			int getvType = vZoneInfos.get(0).getvType();
			Integer dMasUfwc = 0;  //主分区日未计量水量
			Integer dMasUserSum = 0; //主分区月管长
			Integer dSecUfwc = 0; //辅分区日未计量水量
			Integer dSecUserSum = 0; //辅分区月管长
			Integer value = 0; //漏失率
			for (int i = 0; i<vZoneInfos.size(); i++) {
				if(i == 0) {
					//1.获取主分区日未计量水量
					dMasUfwc += zlimapper.getDUfwcByNo(vZoneInfos.get(i).getMasCode(), preDay);
					//2.获取主分区月用户数
					dMasUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getMasCode(), preMonth);
					//1.获取辅分区日未计量水量
					dSecUfwc += zlimapper.getDUfwcByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月用户数
					dSecUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}else {
					//1.获取辅分区日未计量水量
					dSecUfwc += zlimapper.getDUfwcByNo(vZoneInfos.get(i).getSecCode(), preDay);
					//2.获取辅分区月用户数
					dSecUserSum += zlimapper.getMNocmByNo(vZoneInfos.get(i).getSecCode(), preMonth);
					
				}
			}
			
			if(getvType == 1) {
				//合并虚拟分区
				value = Math.round((dMasUfwc+dSecUfwc)/(dMasUserSum+dSecUserSum));
			}else {
				//相减虚拟分区
				value = Math.round((dMasUfwc-dSecUfwc)/(dMasUserSum-dSecUserSum));
			}
			AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
			addDayIndicatorDTO.setAnalysisDate(preDay);
			addDayIndicatorDTO.setCode("VZDUFWCCA");
			addDayIndicatorDTO.setAnalysisValue(value);
			addDayIndicatorDTO.setValue(value);
			addDayIndicatorDTO.setZoneNo(zoneNo);
			addDayIndicatorDTO.setMethod("0");
			lists.add(addDayIndicatorDTO);
		}
		//批量插入日指标数据
		addIndicMapper.addZoneLossDIndicDataBatch(lists);
		//全网
		Integer dUfwc = zlimapper.getDWnUfwcByNo(preDay);
		Integer userSum = zlimapper.getMWnNocmByNo(preMonth);
		Integer value = Math.round(dUfwc/userSum);
		AddDayIndicatorDTO addDayIndicatorDTO = new AddDayIndicatorDTO();
		addDayIndicatorDTO.setAnalysisDate(preDay);
		addDayIndicatorDTO.setCode("WNDUFWCCA");
		addDayIndicatorDTO.setAnalysisValue(value);
		addDayIndicatorDTO.setValue(value);
		addIndicMapper.addCompanyDIndicData(addDayIndicatorDTO);
	}
	
	/**
	 * 计算月单位户数未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calMUfwcPerCustomerAccount")
	public void calMUfwcPerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddMonthIndicatorDTO> lists = new ArrayList<>();
		//获取昨天的时间，精确到天
		Integer preMonth = TimeUtil.getPreMonth();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求月单位户数未计量水量
			Integer value = Math.round(mUfwc/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("FLMUFWCCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("1");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求月单位户数未计量水量
			Integer value = Math.round(mUfwc/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("SLMUFWCCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("2");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求月单位户数未计量水量
			Integer value = Math.round(mUfwc/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("DMMUFWCCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setZoneRank("3");
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取月未计量水量
			Integer mUfwc = zlimapper.getMUfwcByNo(zoneNo, preMonth);
			
			//2.获取月用户数
			Integer userSum = zlimapper.getMNocmByNo(zoneNo, preMonth);
			
			//3.求月单位户数未计量水量
			Integer value = Math.round(mUfwc/userSum);
			
			//3.指标入库
			AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
			addMonthIndicatorDTO.setYearMonth(preMonth);
			addMonthIndicatorDTO.setCode("VZMUFWCCA");
			addMonthIndicatorDTO.setAnalysisValue(value);
			addMonthIndicatorDTO.setValue(value);
			addMonthIndicatorDTO.setZoneNo(zoneNo);
			addMonthIndicatorDTO.setMethod("0");
			lists.add(addMonthIndicatorDTO);
		}
		//批量插入月指标数据
		addIndicMapper.addZoneLossMIndicDataBatch(lists);
		//全网
		Integer mUfwc = zlimapper.getMWnUfwcByNo(preMonth);
		Integer userSum = zlimapper.getMWnNocmByNo(preMonth);
		Integer value = Math.round(mUfwc/userSum);
		AddMonthIndicatorDTO addMonthIndicatorDTO = new AddMonthIndicatorDTO();
		addMonthIndicatorDTO.setYearMonth(preMonth);
		addMonthIndicatorDTO.setCode("WNMUFWCCA");
		addMonthIndicatorDTO.setAnalysisValue(value);
		addMonthIndicatorDTO.setValue(value);
		addMonthIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyMIndicData(addMonthIndicatorDTO);
	}
	
	/**
	 * 计算年单位户数未计量水量
	 * @param factory
	 */
	@TaskAnnotation("calYUfwcPerCustomerAccount")
	public void calYUfwcPerCustomerAccount(SessionFactory factory,CalZoneInfos calZoneInfos){
		ZoneLossIndicatorMapper zlimapper = factory.getMapper(ZoneLossIndicatorMapper.class);
		AddIndicatorMapper addIndicMapper = factory.getMapper(AddIndicatorMapper.class);
		List<AddYearIndicatorDTO> lists = new ArrayList<>();
		//上一年或当前年
		Integer year = TimeUtil.getPreOrCurrentYear();
		//一级分区
		for (String zoneNo : calZoneInfos.getFirstZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求年单位户数未计量水量
			Integer value = Math.round(yUfwc/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("FLYUFWCCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("1");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//二级分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求年单位户数未计量水量
			Integer value = Math.round(yUfwc/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("SLYUFWCCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("2");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//DMA分区
		for (String zoneNo : calZoneInfos.getSecondZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求年单位户数未计量水量
			Integer value = Math.round(yUfwc/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("DMYUFWCCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setZoneRank("3");
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		
		//虚拟分区
		//1.判断虚拟分区类型，合并或相减，并查询虚拟分区的成员
		for (String zoneNo : calZoneInfos.getvZoneLists()) {
			//1.获取年产未计量水量
			Integer yUfwc = zlimapper.getYUfwcByNo(zoneNo, year);
			
			//2.获取年用户数
			Integer userSum = zlimapper.getYNocmByNo(zoneNo, year);
			
			//3.求年单位户数未计量水量
			Integer value = Math.round(yUfwc/userSum);
			
			//3.指标入库
			AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
			addYearIndicatorDTO.setYear(year);
			addYearIndicatorDTO.setCode("VZYUFWCCA");
			addYearIndicatorDTO.setAnalysisValue(value);
			addYearIndicatorDTO.setValue(value);
			addYearIndicatorDTO.setZoneNo(zoneNo);
			addYearIndicatorDTO.setMethod("0");
			lists.add(addYearIndicatorDTO);
		}
		//批量插入年指标数据
		addIndicMapper.addZoneLossYIndicData(lists);
		//全网
		Integer yUfwc = zlimapper.getYWnUfwcByNo(year);
		Integer userSum = zlimapper.getYWnNocmByNo(year);
		Integer value = Math.round(yUfwc/userSum);
		AddYearIndicatorDTO addYearIndicatorDTO = new AddYearIndicatorDTO();
		addYearIndicatorDTO.setYear(year);
		addYearIndicatorDTO.setCode("WNYUFWCCA");
		addYearIndicatorDTO.setAnalysisValue(value);
		addYearIndicatorDTO.setValue(value);
		addYearIndicatorDTO.setMethod("0");
		addIndicMapper.addCompanyYIndicData(addYearIndicatorDTO);
	}
}
