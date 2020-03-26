package com.koron.inwlms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALData;
import com.koron.inwlms.bean.VO.apparentLoss.ALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALMapDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterManageData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterManageRankData;
import com.koron.inwlms.bean.VO.apparentLoss.PageALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneData;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneDatas;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneRankData;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.mapper.master.ApparentLossMapper;
import com.koron.inwlms.service.ApparentLossService;
import com.koron.inwlms.service.common.impl.GisZoneServiceImpl;
import com.koron.inwlms.util.PageUtil;
import com.koron.inwlms.util.TimeUtil;
import com.koron.util.Constant;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 表观漏损接口实现类
 * @author csh
 * @Date 2020/03/23
 *
 */
@Service
public class ApparentLossServiceImpl implements ApparentLossService {
	
	/**
	 * 表观总览接口
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	@TaskAnnotation("queryALOverviewData")
	@Override
	public ALOverviewDataVO queryALOverviewData(SessionFactory factory, QueryALDTO queryALDTO) {
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		Integer timeType = queryALDTO.getTimeType();
		ALOverviewDataVO result = null;
		String dmaNo = queryALDTO.getDmaNo();
		String sZone = queryALDTO.getSecondeLevelZone();
		String fZone = queryALDTO.getFirstLevelZone();
		//对分区级别做过滤，按最小的分区查询指标
		if(StringUtil.isNotEmpty(dmaNo)) {
			queryALDTO.setSecondeLevelZone(null);
			queryALDTO.setFirstLevelZone(null);
		}else if(StringUtil.isNotEmpty(sZone)) {
			queryALDTO.setDmaNo(null);
			queryALDTO.setFirstLevelZone(null);
		}else if(StringUtil.isNotEmpty(fZone)) {
			queryALDTO.setDmaNo(null);
			queryALDTO.setSecondeLevelZone(null);
		}
		if(Constant.TIME_TYPE_M.equals(timeType)) {
			//月指标查询
			result = mapper.queryMALOverviewData(queryALDTO);
		}else if(Constant.TIME_TYPE_Y.equals(timeType)) {
			//年指标查询
			result = mapper.queryYALOverviewData(queryALDTO);
		}
		return result;
	}
	
	/**
	 * 分页查询表观漏损列表
	 * @param factory
	 * @param queryALListDTO
	 * @return
	 */
	@TaskAnnotation("queryALList")
	@Override
	public PageALListVO queryALList(SessionFactory factory, QueryALListDTO queryALListDTO) {
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		Integer timeType = queryALListDTO.getTimeType();
		String dmaNo = queryALListDTO.getDmaNo();
		String sZone = queryALListDTO.getSecondeLevelZone();
		String fZone = queryALListDTO.getFirstLevelZone();
		List<String> lists = new ArrayList<>();
		//对分区级别做过滤，按最小的分区查询指标
		if(StringUtil.isEmpty(dmaNo) && StringUtil.isEmpty(sZone) && StringUtil.isEmpty(fZone)) {
			lists = new GisZoneServiceImpl().queryZoneNosByRank(factory, 1);
		}else if(StringUtil.isNotEmpty(dmaNo)) {
			
		}else if(StringUtil.isNotEmpty(sZone)) {
			lists = new GisZoneServiceImpl().querySubZoneNos(factory, sZone);
		}else if(StringUtil.isNotEmpty(fZone)) {
			lists = new GisZoneServiceImpl().querySubZoneNos(factory, fZone);
		}
		List<ALListVO> alLists = null;
		int rowNumber = 0;
		
		if(Constant.TIME_TYPE_M.equals(timeType)) {
			//月指标查询
			alLists = mapper.queryMALList(queryALListDTO,lists);
			//查询总条数
			rowNumber = mapper.countMALList(queryALListDTO,lists);
		}else if(Constant.TIME_TYPE_Y.equals(timeType)) {
			//年指标查询
			alLists = mapper.queryYALList(queryALListDTO,lists);
			//查询总条数
			rowNumber = mapper.countYALList(queryALListDTO,lists);
		}
		//返回数据结果
		PageALListVO result = new PageALListVO();
		result.setDataList(alLists);
		//插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryALListDTO.getPage(),queryALListDTO.getPageCount(),rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	/**
	 * 查询表观漏损图表数据
	 * @param factory
	 * @param queryALListDTO
	 * @return
	 */
	@TaskAnnotation("queryALMapData")
	@Override
	public ALMapDataVO queryALMapData(SessionFactory factory, QueryALDTO queryALDTO) {
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		Integer timeType = queryALDTO.getTimeType();
		List<ALOverviewDataVO> lists = new ArrayList<>();
		String dmaNo = queryALDTO.getDmaNo();
		String sZone = queryALDTO.getSecondeLevelZone();
		String fZone = queryALDTO.getFirstLevelZone();
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		//对分区级别做过滤，按最小的分区查询指标
		if(StringUtil.isNotEmpty(dmaNo)) {
			queryALDTO.setSecondeLevelZone(null);
			queryALDTO.setFirstLevelZone(null);
		}else if(StringUtil.isNotEmpty(sZone)) {
			queryALDTO.setDmaNo(null);
			queryALDTO.setFirstLevelZone(null);
		}else if(StringUtil.isNotEmpty(fZone)) {
			queryALDTO.setDmaNo(null);
			queryALDTO.setSecondeLevelZone(null);
		}
		
		//计算指标曲线
		List<Integer> timeList = TimeUtil.getMonthsList(startTime,endTime);
		if(Constant.TIME_TYPE_M.equals(timeType)) {
			//月指标查询
			for (Integer month : timeList) {
				queryALDTO.setStartTime(month);
				queryALDTO.setEndTime(month);
				ALOverviewDataVO result = mapper.queryMALOverviewData(queryALDTO);
				lists.add(result);
			}
			//解析月指标数据
		}else if(Constant.TIME_TYPE_Y.equals(timeType)) {
			//年指标查询
			for (Integer month : timeList) {
				queryALDTO.setStartTime(month);
				queryALDTO.setEndTime(month);
				ALOverviewDataVO result = mapper.queryYALOverviewData(queryALDTO);
				lists.add(result);
			}
		}
		
		//表观图表返回值
		ALMapDataVO aLMapDataVO = new ALMapDataVO();
		ALData aLData = new ALData();
		MeterManageData meterManageData = new MeterManageData();
		aLData.setDate(timeList);
		meterManageData.setDate(timeList);
		List<Double> aLList = new ArrayList<>();
		List<Double> perCustomerAccALList = new ArrayList<>();
		List<Double> percentALList = new ArrayList<>();
		List<Double> aLIList = new ArrayList<>();
		List<Double> nonBasicInfoMeterRateList = new ArrayList<>();
		List<Double> percentNonMeterReadList = new ArrayList<>();
		List<Double> meterReadRateList = new ArrayList<>();
		List<Double> nonMeterReadTimeRateList = new ArrayList<>();
		List<Double> overdueMetersRateList = new ArrayList<>();
		for (ALOverviewDataVO list : lists) {
			aLList.add(list.getAL());
			perCustomerAccALList.add(list.getPerCustomerAccAL());
			percentALList.add(list.getPercentAL());
			aLIList.add(list.getALI());
			nonBasicInfoMeterRateList.add(list.getNonBasicInfoMeterRate());
			percentNonMeterReadList.add(list.getPercentNonMeterRead());
			meterReadRateList.add(list.getMeterReadRate());
			nonMeterReadTimeRateList.add(list.getNonMeterReadTimeRate());
			overdueMetersRateList.add(list.getOverdueMetersRate());
		}
		aLData.setALIList(aLIList);
		aLData.setALList(percentALList);
		aLData.setPercentALList(percentALList);
		aLData.setPerCustomerAccALList(perCustomerAccALList);
		meterManageData.setMeterReadRateList(meterReadRateList);
		meterManageData.setNonBasicInfoMeterRateList(nonBasicInfoMeterRateList);
		meterManageData.setNonMeterReadTimeRateList(nonMeterReadTimeRateList);
		meterManageData.setOverdueMetersRateList(overdueMetersRateList);
		meterManageData.setPercentNonMeterReadList(percentNonMeterReadList);
		
		aLMapDataVO.setAlData(aLData);
		aLMapDataVO.setMeterManageData(meterManageData);
		
		//计算分区指标排名
		QueryALListDTO queryALListDTO = new QueryALListDTO();
		queryALListDTO.setDmaNo(queryALDTO.getDmaNo());
		queryALListDTO.setEndTime(queryALDTO.getEndTime());
		queryALListDTO.setFirstLevelZone(queryALDTO.getFirstLevelZone());
		queryALListDTO.setSecondeLevelZone(queryALDTO.getSecondeLevelZone());
		queryALListDTO.setStartTime(queryALDTO.getStartTime());
		queryALListDTO.setTimeType(queryALDTO.getTimeType());
		queryALListDTO.setPage(1);
		queryALListDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
		PageALListVO queryALList = queryALList(factory,queryALListDTO);
		List<ALListVO> dataList = queryALList.getDataList();
		if(dataList == null) return aLMapDataVO;
		
		ZoneDatas zrALList = new ZoneDatas();
		ZoneDatas zrPerCustomerAccALList = new ZoneDatas();
		ZoneDatas zrPercentALList = new ZoneDatas();
		ZoneDatas zrALIList = new ZoneDatas();
		ZoneDatas mmrPercentNonMeterReadList = new ZoneDatas();
		ZoneDatas mmrMeterReadRateList = new ZoneDatas();
		ZoneDatas mmrNonMeterReadTimeRateList = new ZoneDatas();
		ZoneDatas mmrOverdueMetersRateList = new ZoneDatas();
		List<String> zrALzoneName = new ArrayList<>();
		List<Double> zrALzoneDatas = new ArrayList<>();
		List<String> zrPCAALzoneName = new ArrayList<>();
		List<Double> zrPCAALzoneDatas = new ArrayList<>();
		List<String> zrPALzoneName = new ArrayList<>();
		List<Double> zrPALzoneDatas = new ArrayList<>();
		List<String> zrALIzoneName = new ArrayList<>();
		List<Double> zrALIzoneDatas = new ArrayList<>();
		List<String> mmrPNMRzoneName = new ArrayList<>();
		List<Double> mmrPNMRzoneDatas = new ArrayList<>();
		List<String> mmrMRRzoneName = new ArrayList<>();
		List<Double> mmrMRRzoneDatas = new ArrayList<>();
		List<String> mmrNMRTRzoneName = new ArrayList<>();
		List<Double> mmrNMRTRzoneDatas = new ArrayList<>();
		List<String> mmrOMRzoneName = new ArrayList<>();
		List<Double> mmrOMRzoneDatas = new ArrayList<>();
		for (ALListVO alListVO : dataList) {
			zrALzoneName.add(alListVO.getZoneNo());
			zrPCAALzoneName.add(alListVO.getZoneNo());
			zrPALzoneName.add(alListVO.getZoneNo());
			zrALIzoneName.add(alListVO.getZoneNo());
			mmrPNMRzoneName.add(alListVO.getZoneNo());
			mmrMRRzoneName.add(alListVO.getZoneNo());
			mmrNMRTRzoneName.add(alListVO.getZoneNo());
			mmrOMRzoneName.add(alListVO.getZoneNo());
			zrALzoneDatas.add(alListVO.getAL());
			zrPCAALzoneDatas.add(alListVO.getPerCustomerAccAL());
			zrPALzoneDatas.add(alListVO.getPercentAL());
			zrALIzoneDatas.add(alListVO.getALI());
			mmrPNMRzoneDatas.add(alListVO.getPercentNonMeterRead());
			mmrMRRzoneDatas.add(alListVO.getMeterReadRate());
			mmrNMRTRzoneDatas.add(alListVO.getNonMeterReadTimeRate());
			mmrOMRzoneDatas.add(alListVO.getOverdueMetersRate());
		}
		//对分区数据进行排序
		sortZoneData(zrALzoneName,zrALzoneDatas);
		sortZoneData(zrPCAALzoneName,zrPCAALzoneDatas);
		sortZoneData(zrPALzoneName,zrPALzoneDatas);
		sortZoneData(zrALIzoneName,zrALIzoneDatas);
		sortZoneData(mmrPNMRzoneName,mmrPNMRzoneDatas);
		sortZoneData(mmrMRRzoneName,mmrMRRzoneDatas);
		sortZoneData(mmrNMRTRzoneName,mmrNMRTRzoneDatas);
		sortZoneData(mmrOMRzoneName,mmrOMRzoneDatas);
		
		zrALList.setZoneNameList(zrALzoneName);
		zrALList.setZoneDataList(zrALzoneDatas);
		zrPerCustomerAccALList.setZoneNameList(zrPCAALzoneName);
		zrPerCustomerAccALList.setZoneDataList(zrPCAALzoneDatas);
		zrPercentALList.setZoneNameList(zrPALzoneName);
		zrPercentALList.setZoneDataList(zrPALzoneDatas);
		zrALIList.setZoneNameList(zrALIzoneName);
		zrALIList.setZoneDataList(zrALIzoneDatas);
		mmrPercentNonMeterReadList.setZoneNameList(mmrPNMRzoneName);
		mmrPercentNonMeterReadList.setZoneDataList(mmrPNMRzoneDatas);
		mmrMeterReadRateList.setZoneNameList(mmrMRRzoneName);
		mmrMeterReadRateList.setZoneDataList(mmrMRRzoneDatas);
		mmrNonMeterReadTimeRateList.setZoneNameList(mmrNMRTRzoneName);
		mmrNonMeterReadTimeRateList.setZoneDataList(mmrNMRTRzoneDatas);
		mmrOverdueMetersRateList.setZoneNameList(mmrOMRzoneName);
		mmrOverdueMetersRateList.setZoneDataList(mmrOMRzoneDatas);
		ZoneRankData zoneRankData = new ZoneRankData();
		zoneRankData.setZrALIList(zrALIList);
		zoneRankData.setZrALList(zrALList);
		zoneRankData.setZrPercentALList(zrPercentALList);
		zoneRankData.setZrPerCustomerAccALList(zrPerCustomerAccALList);
		MeterManageRankData meterManageRankData = new MeterManageRankData();
		meterManageRankData.setMmrMeterReadRateList(mmrMeterReadRateList);
		meterManageRankData.setMmrNonMeterReadTimeRateList(mmrNonMeterReadTimeRateList);
		meterManageRankData.setMmrOverdueMetersRateList(mmrOverdueMetersRateList);
		meterManageRankData.setMmrPercentNonMeterReadList(mmrPercentNonMeterReadList);
		aLMapDataVO.setZoneRankData(zoneRankData);
		aLMapDataVO.setMeterManageRankData(meterManageRankData);
		
		return aLMapDataVO;
	}
	
	/**
	 * 根据分区数据排序
	 * @param dataList
	 * @param zoneNames
	 * @param zoneDatas
	 */
	public void sortZoneData(List<String> zoneNames,List<Double> zoneDatas) {
		List<Double> preZoneDatas = new ArrayList<>();
		for (Double data : zoneDatas) {
			preZoneDatas.add(data);
		}
		List<String> nowZoneNames = new ArrayList<>();
		Collections.sort(zoneDatas);
		for(int i = 0; zoneDatas.size() > i; i++) {
			for(int j = 0; preZoneDatas.size() > j; j++) {
				if(zoneDatas.get(i) == preZoneDatas.get(j)) {
					nowZoneNames.add(zoneNames.get(j));
				}
			}
		}
		zoneNames = nowZoneNames;
	}
	
}
