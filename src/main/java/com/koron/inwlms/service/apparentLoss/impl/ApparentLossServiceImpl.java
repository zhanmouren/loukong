package com.koron.inwlms.service.apparentLoss.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.ADOConnection;
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
import com.koron.inwlms.bean.VO.apparentLoss.CurrentMeterData;
import com.koron.inwlms.bean.VO.apparentLoss.DrBigDnDealData;
import com.koron.inwlms.bean.VO.apparentLoss.DrMeterStatisData;
import com.koron.inwlms.bean.VO.apparentLoss.DrSmallDnAnaData;
import com.koron.inwlms.bean.VO.apparentLoss.DrSmallDnMeterData;
import com.koron.inwlms.bean.VO.apparentLoss.DrCurrentMeterDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrDealAdviseVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrFlowMeterData;
import com.koron.inwlms.bean.VO.apparentLoss.DrMeterAnaDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrMeterManageVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrTotalAnalysisDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlMeterErrUseData;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlVO;
import com.koron.inwlms.bean.VO.apparentLoss.FSMeterData;
import com.koron.inwlms.bean.VO.apparentLoss.FsMeterReadData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterAnalysisData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterAnalysisMapVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterDNParam;
import com.koron.inwlms.bean.VO.apparentLoss.MeterFlowRateData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterFlowVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfoLossData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterMFlowData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterManageData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterManageRankData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterOverUseTimeInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterQH;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRTimeUnset;
import com.koron.inwlms.bean.VO.apparentLoss.MeterReadData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterUserTimeVO;
import com.koron.inwlms.bean.VO.apparentLoss.TrendAnalysisData;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneDatas;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneRankData;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.common.SysConfigVO;
import com.koron.inwlms.mapper.apparentLoss.ApparentLossMapper;
import com.koron.inwlms.service.apparentLoss.ApparentLossService;
import com.koron.inwlms.service.common.impl.CommonServiceImpl;
import com.koron.inwlms.service.common.impl.GisZoneServiceImpl;
import com.koron.inwlms.service.common.impl.MeterServiceImpl;
import com.koron.inwlms.util.PageUtil;
import com.koron.inwlms.util.TimeUtil;
import com.koron.util.Constant;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 表观漏损接口实现类
 * 
 * @author csh
 * @Date 2020/03/23
 *
 */
@Service
public class ApparentLossServiceImpl implements ApparentLossService {


	/**
	 * 表观总览接口
	 * 
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
		
		if(StringUtil.isEmpty(queryALDTO.getZoneNo()) && queryALDTO.getZoneRank() == null) {
			//全网
			if (Constant.TIME_TYPE_M.equals(timeType)) {
				// 月指标查询
				result = mapper.queryWNMALOverviewData(queryALDTO);
			} else if (Constant.TIME_TYPE_Y.equals(timeType)) {
				// 年指标查询
				result = mapper.queryWNYALOverviewData(queryALDTO);
			}
		}else {
			//不是全网
			if (Constant.TIME_TYPE_M.equals(timeType)) {
				// 月指标查询
				result = mapper.queryMALOverviewData(queryALDTO);
			} else if (Constant.TIME_TYPE_Y.equals(timeType)) {
				// 年指标查询
				result = mapper.queryYALOverviewData(queryALDTO);
			}
		}
		
		return result;
	}

	/**
	 * 分页查询表观漏损列表
	 * 
	 * @param factory
	 * @param queryALListDTO
	 * @return
	 */
	@TaskAnnotation("queryALList")
	@Override
	public PageListVO<List<ALListVO>> queryALList(SessionFactory factory, QueryALListDTO queryALListDTO) {
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		Integer timeType = queryALListDTO.getTimeType();
		String zoneNo = queryALListDTO.getZoneNo();
		Integer zoneRank = queryALListDTO.getZoneRank();
		List<ZoneInfo> lists = new ArrayList<>();
		
		if(StringUtil.isEmpty(zoneNo)) {
			if(zoneRank != null && zoneRank == Constant.RANK_T) {
				ZoneInfo ZoneInfo = new ZoneInfo();
				ZoneInfo.setZoneNo(zoneNo);
				lists.add(ZoneInfo);
			}else {
				lists = new GisZoneServiceImpl().querySubZoneNos(factory, zoneNo);
			}
		}else {
			//目前只有一级，所以只展示该分区的数据(根据需求需要展示该分区下的所有子分区数据)
			ZoneInfo ZoneInfo = new ZoneInfo();
			ZoneInfo.setZoneNo(zoneNo);
			lists.add(ZoneInfo);
		}
		List<ALListVO> alLists = null;
		int rowNumber = 0;
		if (Constant.TIME_TYPE_M.equals(timeType)) {
			// 月指标查询
			// 查询总条数
			rowNumber = lists.size();
			//及判断分页
			if(rowNumber<(queryALListDTO.getPage()-1)*queryALListDTO.getPageCount()) return null;
			
			alLists = mapper.queryMALList(queryALListDTO, lists);
			
		} else if (Constant.TIME_TYPE_Y.equals(timeType)) {
			// 年指标查询
			// 查询总条数
			rowNumber = lists.size();
			//及判断分页
			if(rowNumber<(queryALListDTO.getPage()-1)*queryALListDTO.getPageCount()) return null;
			
			alLists = mapper.queryYALList(queryALListDTO, lists);
			
		}
		// 返回数据结果
		PageListVO<List<ALListVO>> result = new PageListVO<>();
		result.setDataList(alLists);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryALListDTO.getPage(), queryALListDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	/**
	 * 查询表观漏损图表数据
	 * 
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

		// 计算指标曲线
		List<Integer> timeList = new ArrayList<>();
		try {
			timeList = TimeUtil.getTimeList(queryALDTO.getStartTime(), queryALDTO.getEndTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		lists = getIndicatorCurve(mapper, queryALDTO, timeList);

		// 表观图表返回值
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

		// 计算分区指标排名
		QueryALListDTO queryALListDTO = new QueryALListDTO();
		queryALListDTO.setZoneNo(queryALDTO.getZoneNo());
		queryALListDTO.setZoneRank(queryALDTO.getZoneRank());
		queryALListDTO.setEndTime(queryALDTO.getEndTime());
		queryALListDTO.setStartTime(queryALDTO.getStartTime());
		queryALListDTO.setTimeType(queryALDTO.getTimeType());
		queryALListDTO.setPage(1);
		queryALListDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
		PageListVO<List<ALListVO>> queryALList = queryALList(factory, queryALListDTO);
		List<ALListVO> dataList = queryALList.getDataList();
		if (dataList == null)
			return aLMapDataVO;

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
		// 对分区数据进行排序
		sortZoneData(zrALzoneName, zrALzoneDatas);
		sortZoneData(zrPCAALzoneName, zrPCAALzoneDatas);
		sortZoneData(zrPALzoneName, zrPALzoneDatas);
		sortZoneData(zrALIzoneName, zrALIzoneDatas);
		sortZoneData(mmrPNMRzoneName, mmrPNMRzoneDatas);
		sortZoneData(mmrMRRzoneName, mmrMRRzoneDatas);
		sortZoneData(mmrNMRTRzoneName, mmrNMRTRzoneDatas);
		sortZoneData(mmrOMRzoneName, mmrOMRzoneDatas);

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
	 * 
	 * @param dataList
	 * @param zoneNames
	 * @param zoneDatas
	 */
	public void sortZoneData(List<String> zoneNames, List<Double> zoneDatas) {
		List<Double> preZoneDatas = new ArrayList<>();
		for (Double data : zoneDatas) {
			preZoneDatas.add(data);
		}
		List<String> nowZoneNames = new ArrayList<>();
		Collections.sort(zoneDatas);
		for (int i = 0; zoneDatas.size() > i; i++) {
			for (int j = 0; preZoneDatas.size() > j; j++) {
				if (zoneDatas.get(i) == preZoneDatas.get(j)) {
					nowZoneNames.add(zoneNames.get(j));
				}
			}
		}
		zoneNames = nowZoneNames;
	}

	/**
	 * 查询水表运行分析数据
	 * 
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	@TaskAnnotation("queryMeterRunAnalysisList")
	@Override
	public List<MeterRunAnalysisVO> queryMeterRunAnalysisList(SessionFactory factory, QueryALDTO queryALDTO) {
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		Integer timeType = queryALDTO.getTimeType();
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		//调用gis接口获取分区的水表信息
		List<MeterInfo> lists = queryMeterInfoByZoneNo(factory, "");
		// 根据时间类型转换开始时间和结束时间
		if (Constant.TIME_TYPE_Y.equals(timeType)) {
			// 将年的时间范围转化为年月
			if (startTime.toString().length() != 4 && endTime.toString().length() != 4) {
				try {
					throw new Exception("时间格式不正常");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			queryALDTO.setStartTime(Integer.parseInt(startTime.toString() + "01"));
			queryALDTO.setEndTime(TimeUtil.getMonthByYear(endTime));
		}
		List<Integer> dnLists = new ArrayList<>();
		List<MeterRunAnalysisVO> resLists = new ArrayList<>();
		for (MeterInfo meterInfo : lists) {
			Integer meterDn = meterInfo.getMeterDn();
			if (!dnLists.contains(meterDn)) {
				MeterRunAnalysisVO meterRunAnalysisVO = new MeterRunAnalysisVO();
				meterRunAnalysisVO.setMeterDn(meterInfo.getMeterDn());
				meterRunAnalysisVO.setMeterType(meterInfo.getMeterType());
				resLists.add(meterRunAnalysisVO);
				dnLists.add(meterDn);
			}
		}
		// 获取配置信息表数据
		Map<String, Double> qhMaxMinMap = getQhMaxMinMap(factory);
		// 获取口径的最大/最小参数流量
		// 计算所有水表的QH值,
		// 计算流量异常情况，零流量/低流量/正常/过载
		List<MeterQH> queryMeterQH = mapper.queryMeterQH(queryALDTO, lists);
		try {
			
		for (MeterQH meterQH : queryMeterQH) {
			if(meterQH.getQh() == null || meterQH.getMeterDn() == null) continue;
			double qh = Double.parseDouble(meterQH.getQh());
			for (MeterRunAnalysisVO mraVO : resLists) {
				if (mraVO.getMeterDn() == meterQH.getMeterDn()) {
					// 判断流量QH是否小0.001，是则为零流量水表
					if (qh < 0.001) {
						mraVO.setZeroFlowMeterNum(mraVO.getZeroFlowMeterNum() + 1);
					} else {
						// 计算低流量/正常/过载水表
						MeterDNParam meterDNParam = getMeterDNParam(qhMaxMinMap, meterQH.getMeterDn());
						if(meterDNParam.getMaxQ() == null || meterDNParam.getMinQ() == null) break;
						if (qh < Double.parseDouble(meterDNParam.getMinQ())) {
							mraVO.setLowFlowMeterNum(mraVO.getLowFlowMeterNum() + 1);
						} else if (qh < Double.parseDouble(meterDNParam.getMaxQ())) {
							mraVO.setNormalFlowMeterNum(mraVO.getNormalFlowMeterNum() + 1);
						} else {
							mraVO.setHighFlowMeterNum(mraVO.getHighFlowMeterNum() + 1);
						}
					}
					mraVO.setMeterNum(mraVO.getMeterNum() + 1);
					break;
				}
			}
		}
		} catch (Exception e) {
			System.out.println(33);
		}
		return resLists;
	}


	/**
	 * 查询水表分析图表数据
	 */
	@TaskAnnotation("queryMeterRunAnalysisMapData")
	@Override
	public MeterAnalysisMapVO queryMeterRunAnalysisMapData(SessionFactory factory, QueryALDTO queryALDTO) {
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		List<MeterRunAnalysisVO> queryMeterRunAnalysisList = queryMeterRunAnalysisList(factory, queryALDTO);
		MeterAnalysisMapVO meterAnalysisMapVO = new MeterAnalysisMapVO();
		MeterFlowRateData bigDnData = new MeterFlowRateData();
		MeterFlowRateData smallDnData = new MeterFlowRateData();
		MeterFlowRateData bigDnNoFSData = new MeterFlowRateData();

		int bigDnZeroFSum = 0; // 大口径零流量水表数量
		int bigDnLowFSum = 0; // 大口径低流量水表数量
		int bigDnNorFSum = 0; // 大口径正常流量水表数量
		int bigDnHighFSum = 0; // 大口径过载流量水表数量
		int smallDnZeroFSum = 0; // 小口径零流量水表数量
		int smallDnLowFSum = 0; // 小口径低流量水表数量
		int smallDnNorFSum = 0; // 小口径正常流量水表数量
		int smallDnHighFSum = 0; // 小口径过载流量水表数量
		int bigDnNoFSDnZeroFSum = 0; // 大口径（除消防表）零流量水表数量
		int bigDnNoFSLowFSum = 0; // 大口径（除消防表）低流量水表数量
		int bigDnNoFSNorFSum = 0; // 大口径（除消防表）正常流量水表数量
		int bigDnNoFSHighFSum = 0; // 大口径（除消防表）过载流量水表数量
		// 口径大于等于50的是大口径水表，否则是小口径
		for (MeterRunAnalysisVO meterRunAnalysisVO : queryMeterRunAnalysisList) {
			Integer meterDn = meterRunAnalysisVO.getMeterDn();
			if (meterDn >= Constant.METER_DN_SIZE) {
				// 大口径，判断是否是消防表
				if (Constant.FS_METER.equals(meterRunAnalysisVO.getMeterType())) {
					bigDnNoFSDnZeroFSum += meterRunAnalysisVO.getZeroFlowMeterNum();
					bigDnNoFSLowFSum += meterRunAnalysisVO.getLowFlowMeterNum();
					bigDnNoFSNorFSum += meterRunAnalysisVO.getNormalFlowMeterNum();
					bigDnNoFSHighFSum += meterRunAnalysisVO.getHighFlowMeterNum();
				}
				bigDnZeroFSum += meterRunAnalysisVO.getZeroFlowMeterNum();
				bigDnLowFSum += meterRunAnalysisVO.getLowFlowMeterNum();
				bigDnNorFSum += meterRunAnalysisVO.getNormalFlowMeterNum();
				bigDnHighFSum += meterRunAnalysisVO.getHighFlowMeterNum();
			} else {
				// 小口径
				smallDnZeroFSum += meterRunAnalysisVO.getZeroFlowMeterNum();
				smallDnLowFSum += meterRunAnalysisVO.getLowFlowMeterNum();
				smallDnNorFSum += meterRunAnalysisVO.getNormalFlowMeterNum();
				smallDnHighFSum += meterRunAnalysisVO.getHighFlowMeterNum();
			}
		}
		double bigDnSum = (bigDnZeroFSum + bigDnLowFSum + bigDnNorFSum + bigDnHighFSum) * 1.0;
		double smallDnSum = (smallDnZeroFSum + smallDnLowFSum + smallDnNorFSum + smallDnHighFSum) * 1.0;
		double bigDnNoFSDnSum = (bigDnNoFSDnZeroFSum + bigDnNoFSLowFSum + bigDnNoFSNorFSum + bigDnNoFSHighFSum) * 1.0;
		DecimalFormat df = new DecimalFormat("#.0000");
		bigDnData
				.setZeroFlowMeterRate(bigDnZeroFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnZeroFSum / bigDnSum)));
		bigDnData.setLowFlowMeterRate(bigDnLowFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnLowFSum / bigDnSum)));
		bigDnData
				.setNormalFlowMeterRate(bigDnNorFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNorFSum / bigDnSum)));
		bigDnData
				.setHighFlowMeterRate(bigDnHighFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnHighFSum / bigDnSum)));
		smallDnData.setZeroFlowMeterRate(
				smallDnZeroFSum == 0 ? 0 : Double.parseDouble(df.format(smallDnZeroFSum / smallDnSum)));
		smallDnData.setLowFlowMeterRate(
				smallDnLowFSum == 0 ? 0 : Double.parseDouble(df.format(smallDnLowFSum / smallDnSum)));
		smallDnData.setNormalFlowMeterRate(
				smallDnNorFSum == 0 ? 0 : Double.parseDouble(df.format(smallDnNorFSum / smallDnSum)));
		smallDnData.setHighFlowMeterRate(
				smallDnHighFSum == 0 ? 0 : Double.parseDouble(df.format(smallDnHighFSum / smallDnSum)));
		bigDnNoFSData.setZeroFlowMeterRate(
				bigDnNoFSDnZeroFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNoFSDnZeroFSum / bigDnNoFSDnSum)));
		bigDnNoFSData.setLowFlowMeterRate(
				bigDnNoFSLowFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNoFSLowFSum / bigDnNoFSDnSum)));
		bigDnNoFSData.setNormalFlowMeterRate(
				bigDnNoFSNorFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNoFSNorFSum / bigDnNoFSDnSum)));
		bigDnNoFSData.setHighFlowMeterRate(
				bigDnNoFSHighFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNoFSHighFSum / bigDnNoFSDnSum)));

		//根据分区编号获取水表信息
		List<MeterInfo> lists = getMeterInfo(factory,queryALDTO);
		List<String> date = new ArrayList<>();
		List<Double> value = new ArrayList<>();
		List<MeterFlowVO> queryFSMeterMFlow = mapper.queryFSMeterMFlow(queryALDTO, lists);
		for (MeterFlowVO meterFlowVO : queryFSMeterMFlow) {
			date.add(meterFlowVO.getDate());
			value.add(meterFlowVO.getValue());
		}
		FSMeterData fSMeterData = new FSMeterData();
		fSMeterData.setDate(date);
		fSMeterData.setValue(value);
		meterAnalysisMapVO.setBigDnData(bigDnData);
		meterAnalysisMapVO.setSmallDnData(smallDnData);
		meterAnalysisMapVO.setBigDnNoFSData(bigDnNoFSData);
		meterAnalysisMapVO.setfSMeterData(fSMeterData);
		return meterAnalysisMapVO;
	}

	/**
	 * 总体分析接口
	 */
	@TaskAnnotation("queryDrTotalAnalysisData")
	@Override
	public DrTotalAnalysisDataVO queryDrTotalAnalysisData(SessionFactory factory, QueryALDTO queryALDTO) {		DrTotalAnalysisDataVO drTotalAnalysisDataVO = new DrTotalAnalysisDataVO();
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		GisZoneServiceImpl gisZoneServiceImpl = new GisZoneServiceImpl();
		String anaRange = "";
		String zoneNo = "";
		List<MeterInfo> lists = new ArrayList<MeterInfo>();
		drTotalAnalysisDataVO.setGroupName("常平水司");
		if(StringUtil.isEmpty(queryALDTO.getZoneNo()) && queryALDTO.getZoneRank() == null) {
			anaRange = "全网";
		}else {
			zoneNo = queryALDTO.getZoneNo();
			anaRange = gisZoneServiceImpl.queryZoneNameByNo(factory, zoneNo);
		}
		lists = queryMeterInfoByZoneNo(factory,zoneNo);

		drTotalAnalysisDataVO.setAnalysisRange(anaRange);
		drTotalAnalysisDataVO.setAnalysisTime(
				TimeUtil.formatTime(queryALDTO.getStartTime()) + "-" + TimeUtil.formatTime(queryALDTO.getEndTime()));
		drTotalAnalysisDataVO.setMeterNum(lists.size());
		// 查询水表的平均月抄表水量
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		if (Constant.TIME_TYPE_Y.equals(queryALDTO.getTimeType())) {
			// 将年的时间范围转化为年月
			if (startTime.toString().length() != 4 && endTime.toString().length() != 4) {
				try {
					throw new Exception("时间格式不正常");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			queryALDTO.setStartTime(Integer.parseInt(startTime.toString() + "01"));
//			queryALDTO.setEndTime(TimeUtil.getMonthByYear(endTime));
		}
//		Double mMeterReadFlow = mapper.queryMMeterReadFlow(queryALDTO, lists);
		Double mMeterReadFlow = mapper.queryWNMMeterReadFlow(queryALDTO, lists);
		drTotalAnalysisDataVO.setmMeterReadFlow(mMeterReadFlow);
		// 查询表观漏损指标
		ALOverviewDataVO queryALOverviewData = queryALOverviewData(factory, queryALDTO);
		if(queryALOverviewData != null) {
			drTotalAnalysisDataVO.setALI(queryALOverviewData.getALI());
			drTotalAnalysisDataVO.setPercentAL(queryALOverviewData.getPercentAL());
			drTotalAnalysisDataVO.setOverdueMetersRate(queryALOverviewData.getOverdueMetersRate());
			drTotalAnalysisDataVO.setNonBasicInfoMeterRate(queryALOverviewData.getNonBasicInfoMeterRate());
			// 评分计算
			getReportScore(queryALOverviewData, drTotalAnalysisDataVO);
		}
		
		// 查询子分区AIL排名
		// 计算分区指标排名
		QueryALListDTO queryALListDTO = new QueryALListDTO();
		queryALListDTO.setZoneNo(queryALDTO.getZoneNo());
		queryALListDTO.setEndTime(queryALDTO.getEndTime());
		queryALListDTO.setZoneRank(queryALDTO.getZoneRank());
		queryALListDTO.setStartTime(queryALDTO.getStartTime());
		queryALListDTO.setTimeType(queryALDTO.getTimeType());
		queryALListDTO.setPage(1);
		queryALListDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
		PageListVO<List<ALListVO>> queryALList = queryALList(factory, queryALListDTO);
		List<ALListVO> dataList = queryALList.getDataList();
		if (dataList != null) {
			ZoneDatas zrALIList = new ZoneDatas();
			List<String> zrALIzoneName = new ArrayList<>();
			List<Double> zrALIzoneDatas = new ArrayList<>();
			for (ALListVO alListVO : dataList) {
				zrALIzoneName.add(alListVO.getZoneNo());
				zrALIzoneDatas.add(alListVO.getALI());
			}
			// 对分区数据进行排序
			sortZoneData(zrALIzoneName, zrALIzoneDatas);
			zrALIList.setZoneDataList(zrALIzoneDatas);
			zrALIList.setZoneNameList(zrALIzoneName);
			drTotalAnalysisDataVO.setSubZoneRank(zrALIList);
		}

		// 计算指标曲线
		List<Integer> timeList = new ArrayList<>();
		try {
			timeList = TimeUtil.getTimeList(startTime, endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<ALOverviewDataVO> idLists = getIndicatorCurve(mapper, queryALDTO, timeList);
		TrendAnalysisData taData = new TrendAnalysisData();
		ALData aLData = new ALData();
		MeterManageData meterManageData = new MeterManageData();
		aLData.setDate(timeList);
		meterManageData.setDate(timeList);
		List<Double> percentALList = new ArrayList<>();
		List<Double> aLIList = new ArrayList<>();
		List<Double> nonBasicInfoMeterRateList = new ArrayList<>();
		List<Double> overdueMetersRateList = new ArrayList<>();
		for (ALOverviewDataVO list : idLists) {
			if (list == null)
				continue;
			percentALList.add(list.getPercentAL());
			aLIList.add(list.getALI());
			nonBasicInfoMeterRateList.add(list.getNonBasicInfoMeterRate());
			overdueMetersRateList.add(list.getOverdueMetersRate());
		}
		taData.setDate(timeList);
		taData.setALIList(aLIList);
		taData.setPercentALList(percentALList);
		taData.setNonBasicInfoMeterRateList(nonBasicInfoMeterRateList);
		taData.setOverdueMetersRateList(overdueMetersRateList);
		drTotalAnalysisDataVO.setTrendAnalysisData(taData);
		// 计算水表运行分析
		List<MeterRunAnalysisVO> queryMeterRunAnalysisList = queryMeterRunAnalysisList(factory, queryALDTO);
		int meterNum = 0;
		int lowFlowMeterNum = 0;
		int zeroFlowMeterNum = 0;
		int normalFlowMeterNum = 0;
		int highFlowMeterNum = 0;
		for (MeterRunAnalysisVO meterRunAnalysisVO : queryMeterRunAnalysisList) {
			meterNum += meterRunAnalysisVO.getMeterNum();
			lowFlowMeterNum += meterRunAnalysisVO.getLowFlowMeterNum();
			zeroFlowMeterNum += meterRunAnalysisVO.getZeroFlowMeterNum();
			normalFlowMeterNum += meterRunAnalysisVO.getNormalFlowMeterNum();
			highFlowMeterNum += meterRunAnalysisVO.getHighFlowMeterNum();
		}
		MeterAnalysisData meterAnalysisData = new MeterAnalysisData();
		DecimalFormat df = new DecimalFormat("#.0000");
		if (meterNum != 0) {
			meterAnalysisData.setLowFlowMeterRate(Double.parseDouble(df.format(lowFlowMeterNum / (meterNum * 1.0))));
			meterAnalysisData.setZeroFlowMeterRate(Double.parseDouble(df.format(zeroFlowMeterNum / (meterNum * 1.0))));
			meterAnalysisData
					.setNormalFlowMeterRate(Double.parseDouble(df.format(normalFlowMeterNum / (meterNum * 1.0))));
			meterAnalysisData.setHighFlowMeterRate(Double.parseDouble(df.format(highFlowMeterNum / (meterNum * 1.0))));
		}
		drTotalAnalysisDataVO.setMeterAnalysisData(meterAnalysisData);
		return drTotalAnalysisDataVO;
	}

	/**
	 * 计算表观漏损指标曲线
	 * 
	 * @param mapper
	 * @param queryALDTO
	 * @return
	 */
	public List<ALOverviewDataVO> getIndicatorCurve(ApparentLossMapper mapper, QueryALDTO queryALDTO,
			List<Integer> timeList) {
		List<ALOverviewDataVO> lists = new ArrayList<>();
		// 计算指标曲线
		if (Constant.TIME_TYPE_M.equals(queryALDTO.getTimeType())) {
			// 月指标查询
			for (Integer month : timeList) {
				queryALDTO.setStartTime(month);
				queryALDTO.setEndTime(month);
				ALOverviewDataVO result =  new ALOverviewDataVO();
				if(StringUtil.isEmpty(queryALDTO.getZoneNo()) && queryALDTO.getZoneRank() == null) {
					// 月指标查询
					result = mapper.queryWNMALOverviewData(queryALDTO);
				}else {
					// 月指标查询
					result = mapper.queryMALOverviewData(queryALDTO);
				}
				if(result != null) {
					result.setTime(month);
					lists.add(result);
				} else {
					ALOverviewDataVO result1 = new ALOverviewDataVO();
					result1.setTime(month);
					lists.add(result1);
				}
			}
			// 解析月指标数据
		} else if (Constant.TIME_TYPE_Y.equals(queryALDTO.getTimeType())) {
			// 年指标查询
			for (Integer year : timeList) {
				queryALDTO.setStartTime(year);
				queryALDTO.setEndTime(year);
				ALOverviewDataVO result =  new ALOverviewDataVO();
				if(StringUtil.isEmpty(queryALDTO.getZoneNo()) && queryALDTO.getZoneRank() == null) {
					// 月指标查询
					result = mapper.queryWNYALOverviewData(queryALDTO);
				}else {
					// 月指标查询
					result = mapper.queryYALOverviewData(queryALDTO);
				}
				if(result != null) {
					result.setTime(year);
					lists.add(result);
				}else {
					ALOverviewDataVO result1 = new ALOverviewDataVO();
					result1.setTime(year);
					lists.add(result1);
				}
			}
		}
		return lists;
	}

	/**
	 * 计算诊断报告的评分
	 */
	public void getReportScore(ALOverviewDataVO alodVO, DrTotalAnalysisDataVO dtadVO) {
		Double ali = alodVO.getALI();
		Double percentAL = alodVO.getPercentAL();
		Double pnmr = alodVO.getPercentNonMeterRead();
		Double nbir = alodVO.getNonBasicInfoMeterRate();
		Double nmrtr = alodVO.getNonMeterReadTimeRate();
		Double omr = alodVO.getOverdueMetersRate();

		int aliSc = 0;
		int percentALSc = 0;
		int pnmrSc = 0;
		int nbirSc = 0;
		int nmrtrSc = 0;
		int omrSc = 0;

		if (ali != null) {
			if (ali >= 0.8) {
				aliSc = 100;
			} else if (ali < 1.2) {
				aliSc = 60;
			}
		}
		if (percentAL != null) {
			if (percentAL <= 0.04) {
				percentALSc = 100;
			} else if (percentAL > 0.08) {
				percentALSc = 60;
			}
		}
		if (pnmr != null) {
			if (pnmr <= 0.01) {
				pnmrSc = 100;
			} else if (pnmr > 0.02) {
				pnmrSc = 60;
			}
		}
		if (nbir != null) {
			if (nbir <= 0.001) {
				nbirSc = 100;
			} else if (nbir > 0.01) {
				nbirSc = 60;
			}
		}
		if (nmrtr != null) {
			if (nmrtr <= 0.001) {
				nmrtrSc = 100;
			} else if (nmrtr > 0.01) {
				nmrtrSc = 60;
			}
		}
		if (omr != null) {
			if (omr <= 0.1) {
				omrSc = 100;
			} else if (omr > 0.16) {
				omrSc = 60;
			}
		}
		Double score = (aliSc * 0.5 + percentALSc * 0.5) * 0.6
				+ (pnmrSc * 0.25 + nbirSc * 0.25 + nmrtrSc * 0.25 + omrSc * 0.25) * 0.4;
		String rank = "";
		if (score >= 80) {
			rank = Constant.SCORE_Y;
		} else if (score < 60) {
			rank = Constant.SCORE_C;
		} else {
			rank = Constant.SCORE_L;
		}
		dtadVO.setScore(score);
		dtadVO.setScoreLevel(rank);
	}

	/**
	 * 查询诊断报告现状水表数据
	 */
	@TaskAnnotation("queryDrCurrentMeterData")
	@Override
	public DrCurrentMeterDataVO queryDrCurrentMeterData(SessionFactory factory, QueryALDTO queryALDTO) {
		List<MeterInfo> lists = queryMeterInfoByZoneNo(factory, "");
		DrCurrentMeterDataVO drCurrentMeterDataVO = new DrCurrentMeterDataVO();
		List<CurrentMeterData> cmdLists = new ArrayList<>();
		if (lists.size() == 0)
			return null;
		int meterNum = lists.size();
		drCurrentMeterDataVO.setMeterNum(meterNum);
		int fsMeterNum = 0;
		int dn15Num = 0;
		int dn20Num = 0;
		int dn25Num = 0;
		int dn40Num = 0;
		int dn50Num = 0;
		int dn80Num = 0;
		int dn100Num = 0;
		int dn150Num = 0;
		int dn200Num = 0;
		int dn300Num = 0;
		int dn400Num = 0;
		int dn600Num = 0;
		int smallDnNum = 0; // 小口径
		int bigDnNum = 0; // 大口径
		for (MeterInfo meterInfo : lists) {
			if (Constant.FS_METER.equals(meterInfo.getMeterType()))
				fsMeterNum++;
			switch (meterInfo.getMeterDn()) {
			case Constant.DN_15:
				dn15Num++;
				smallDnNum++;
				break;
			case Constant.DN_20:
				dn20Num++;
				smallDnNum++;
				break;
			case Constant.DN_25:
				dn25Num++;
				smallDnNum++;
				break;
			case Constant.DN_40:
				dn40Num++;
				smallDnNum++;
				break;
			case Constant.DN_50:
				dn50Num++;
				bigDnNum++;
				break;
			case Constant.DN_80:
				dn80Num++;
				bigDnNum++;
				break;
			case Constant.DN_100:
				dn100Num++;
				bigDnNum++;
				break;
			case Constant.DN_150:
				dn150Num++;
				bigDnNum++;
				break;
			case Constant.DN_200:
				dn200Num++;
				bigDnNum++;
				break;
			case Constant.DN_300:
				dn300Num++;
				bigDnNum++;
				break;
			case Constant.DN_400:
				dn400Num++;
				bigDnNum++;
				break;
			case Constant.DN_600:
				dn600Num++;
				bigDnNum++;
				break;
			default:
				break;
			}
		}
		drCurrentMeterDataVO.setFsMeterNum(fsMeterNum);
		drCurrentMeterDataVO.setDnRange("DN15-DN600");
		DecimalFormat df = new DecimalFormat("#.0000");
		double smallDnRate = Double.parseDouble(df.format(smallDnNum / (meterNum * 1.0)));
		double bigDnRate = Double.parseDouble(df.format(bigDnNum / (meterNum * 1.0)));

		if (dn15Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_15);
			cmd.setMeterNum(dn15Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn15Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(smallDnNum);
			cmd.setMeterTotalRate(smallDnRate);
			cmdLists.add(cmd);
		}
		if (dn20Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_20);
			cmd.setMeterNum(dn20Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn20Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(smallDnNum);
			cmd.setMeterTotalRate(smallDnRate);
			cmdLists.add(cmd);
		}
		if (dn25Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_25);
			cmd.setMeterNum(dn25Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn25Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(smallDnNum);
			cmd.setMeterTotalRate(smallDnRate);
			cmdLists.add(cmd);
		}
		if (dn40Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_40);
			cmd.setMeterNum(dn40Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn40Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(smallDnNum);
			cmd.setMeterTotalRate(smallDnRate);
			cmdLists.add(cmd);
		}
		if (dn50Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_50);
			cmd.setMeterNum(dn50Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn50Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(bigDnNum);
			cmd.setMeterTotalRate(bigDnRate);
			cmdLists.add(cmd);
		}
		if (dn80Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_80);
			cmd.setMeterNum(dn80Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn80Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(bigDnNum);
			cmd.setMeterTotalRate(bigDnRate);
			cmdLists.add(cmd);
		}
		if (dn100Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_100);
			cmd.setMeterNum(dn100Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn100Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(bigDnNum);
			cmd.setMeterTotalRate(bigDnRate);
			cmdLists.add(cmd);
		}
		if (dn150Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_150);
			cmd.setMeterNum(dn150Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn150Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(bigDnNum);
			cmd.setMeterTotalRate(bigDnRate);
			cmdLists.add(cmd);
		}
		if (dn200Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_200);
			cmd.setMeterNum(dn200Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn200Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(bigDnNum);
			cmd.setMeterTotalRate(bigDnRate);
			cmdLists.add(cmd);
		}
		if (dn300Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_300);
			cmd.setMeterNum(dn300Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn300Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(bigDnNum);
			cmd.setMeterTotalRate(bigDnRate);
			cmdLists.add(cmd);
		}
		if (dn400Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_400);
			cmd.setMeterNum(dn400Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn400Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(bigDnNum);
			cmd.setMeterTotalRate(bigDnRate);
			cmdLists.add(cmd);
		}
		if (dn600Num != 0) {
			CurrentMeterData cmd = new CurrentMeterData();
			cmd.setDnNo(Constant.DN_STR + Constant.DN_600);
			cmd.setMeterNum(dn600Num);
			cmd.setMeterRate(Double.parseDouble(df.format(dn600Num / (meterNum * 1.0))));
			cmd.setMeterTotalNum(bigDnNum);
			cmd.setMeterTotalRate(bigDnRate);
			cmdLists.add(cmd);
		}
		drCurrentMeterDataVO.setMeterData(cmdLists);
		return drCurrentMeterDataVO;
	}

	/**
	 * 诊断报告表计管理问题
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	@TaskAnnotation("queryDrMeterManageData")
	@Override
	public DrMeterManageVO queryDrMeterManageData(SessionFactory factory, QueryALDTO queryALDTO) {
		DrMeterManageVO drMeterManageVO = new DrMeterManageVO();
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		MeterServiceImpl MeterServiceImpl = new MeterServiceImpl();
		List<MeterInfo> lists = queryMeterInfoByZoneNo(factory, "");
//		List<String> list = new ArrayList<>();
//		for (MeterInfo meterInfo : lists) {
//			list.add(meterInfo.getMeterNo());
//		}
		//1、抄表数据完整性
		int fNum = 0;  //大于5个月没数据的水表个数
		int tfNum = 0; //2-4个月没数据的水表个数
		//获取查询时间的月份差数
		List<Integer> monthsList =  new ArrayList<>();
		try {
			monthsList = TimeUtil.getTimeList(startTime,endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int monthNum = monthsList.size();
//		List<MeterMFlowData> queryMeterMFlow = mapper.queryMeterMFlow(null, startTime,endTime);
		int lossKaiDateNum = 0; //缺少安装日期的水表数量
		int lossDnNum = 0; //缺少口径的水表数量
		int lossMTypeNum = 0;  //缺少水表类型的水表数量
		int lossUTypeNum = 0;  //缺少用水类型的水表数量
		
		//4、水表超期服役
		double sDnMeterRate = 0.0;  //小口径超期服役的水表占比
		double fMeterRate = 0.0; //超期服役六年内的水表占比
		double ftMeterRate = 0.0; //超期服役六年内的水表占比
		double tMeterRate = 0.0; //超期服役六年内的水表占比
		
		List<String> useList = new ArrayList<>();
		int useBadNum = 0;
		int sDnMeterNum = 0; //小口径超期服役的水表数目
		int fMeterNum = 0; //超期服役六年内的水表数目
		int ftMeterNum = 0; //超期服役6-10年内的水表数目
		int tMeterNum = 0; //超期服役10年以上的水表数目	
		
		String[] priKeySplit1 = Constant.USE_PRI1.split(",");
		String[] priKeySplit2 = Constant.USE_PRI2.split(",");
		for (MeterInfo meterInfo : lists) {
			int codeNum = 0;
			List<String> mList = new ArrayList<>();
//			for (MeterMFlowData meterMFlowData : queryMeterMFlow) {
//				if(meterMFlowData.getCode().equals(meterInfo.getAccNo())) {
//					codeNum++;
//					mList.add(meterMFlowData.getMonth());
//				}
//			}
			if(codeNum == 0 && monthNum >= 5) {
				fNum++;
			}else if(codeNum == 0 && monthNum < 5) {
				tfNum++;
			}else {
				getMeterNoMDataNum(fNum,tfNum,mList,startTime,endTime);
			}
			
			//基本档案完整性判断
			if(meterInfo.getKaiDate() == null) {
				lossKaiDateNum++;
			}else if(meterInfo.getMeterDn() == null) {
				lossDnNum++;
			}else if(meterInfo.getMeterType() == null) {
				lossMTypeNum++;
			}else if(meterInfo.getUseType() == null) {
				lossUTypeNum++;
			}
			
			if(meterInfo.getMeterDn() < Constant.DN_50) {
				sDnMeterNum++;
				if(meterInfo.getUseYear() <= 6) {
					fMeterNum++;
				}else if(meterInfo.getUseYear() > 10) {
					tMeterNum++;
				}else {
					ftMeterNum++;
				}
			} 
			
			//用户用水可疑信息
			String accName = meterInfo.getAccName();
			String useType = meterInfo.getUseType();
			for (String string : priKeySplit1) {
				if(accName != null && accName.contains(string) && Constant.USER_TYPE_PP.equals(useType)) {
					useBadNum++;
					useList.add(meterInfo.getAccNo());
				}
			}
			
			for (String string : priKeySplit2) {
				if(accName != null && accName.contains(string) && !Constant.USER_TYPE_SP.equals(useType)) {
					useBadNum++;
					useList.add(meterInfo.getAccNo());
				}
			}
		}
		MeterReadData meterReadData = new MeterReadData();
		meterReadData.setfMNonMeterReadNum(fNum);
		meterReadData.settFMNonMeterReadNum(tfNum);
		meterReadData.setTotalNonMeterReadNum(fNum+tfNum);
		drMeterManageVO.setMeterReadData(meterReadData);
		
		//2、基本档案完整性
		MeterInfoLossData meterInfoLossData = new MeterInfoLossData();
		meterInfoLossData.setLossKaiDateNum(lossKaiDateNum);
		meterInfoLossData.setLossDnNum(lossDnNum);
		meterInfoLossData.setLossMTypeNum(lossMTypeNum);
		meterInfoLossData.setLossUTypeNum(lossUTypeNum);
		drMeterManageVO.setMeterInfoLossData(meterInfoLossData);
		
		//3、抄表时间不固定
		MeterRTimeUnset queryRTimeUnset = mapper.queryRTimeUnset(queryALDTO);
		queryRTimeUnset.settTDNonReadMeterNum(queryRTimeUnset.getTotalNonReadMeterNum()-queryRTimeUnset.gettDNonReadMeterNum());
		drMeterManageVO.setMeterRTimeUnset(queryRTimeUnset);
		
		//水表超期服役
		int meterNum = lists.size();
		DecimalFormat df = new DecimalFormat("#.0000");
		if(meterNum != 0) {
			sDnMeterRate = Double.parseDouble(df.format(sDnMeterNum / (meterNum * 1.0)));
		}
		if(sDnMeterNum != 0) {
			fMeterRate = Double.parseDouble(df.format(fMeterNum / (sDnMeterNum * 1.0)));
			ftMeterRate = Double.parseDouble(df.format(ftMeterNum / (sDnMeterNum * 1.0)));
			tMeterRate = Double.parseDouble(df.format(tMeterNum / (sDnMeterNum * 1.0)));
		}
		MeterOverUseTimeInfo meterOverUseTimeInfo = new MeterOverUseTimeInfo();
		meterOverUseTimeInfo.setfMeterRate(fMeterRate);
		meterOverUseTimeInfo.setFtMeterRate(ftMeterRate);
		meterOverUseTimeInfo.setsDnMeterRate(sDnMeterRate);
		meterOverUseTimeInfo.settMeterRate(tMeterRate);
		drMeterManageVO.setMeterOverUseTimeInfo(meterOverUseTimeInfo);
		
		//5、用户用水可疑信息
//		int currentMonth = Integer.parseInt(TimeUtil.getcurrentDay("yyyyMM",new Date()));
		Double useFlow = mapper.queryTotalMFlow(useList, startTime,endTime);
		drMeterManageVO.setUseBadNum(useBadNum);
		drMeterManageVO.setUserBadFlow(useFlow);
		return drMeterManageVO;
	}
	
	/**
	 * 获取连续无数据的次数，2-4，大于5
	 * @param fNum 大于五次无数据的数量
	 * @param tfNum 2-4次无数据的数量
	 * @param mList 月份的集合
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 */
	public void getMeterNoMDataNum(int fNum,int tfNum,List<String> mList,int startTime,int endTime) {
		int forNum = 0;
		int noDataNum = 0;
		int tfMNum = 0;
		int fMNum = 0;
		while(endTime == startTime) {
			forNum++;
			if(!mList.contains(endTime+"")) {
				noDataNum++;
				if(noDataNum == 2 && noDataNum < 5) {
					tfMNum = 1;
					fMNum = 0;
				}else if(noDataNum > 5) {
					fNum = 1;
					tfMNum = 0;
				}
			}else {
				noDataNum = 0;
			}
			endTime = TimeUtil.getPreMonth(endTime);
			if(forNum >100) break;
		}
		
		if(tfMNum == 1) tfNum ++;
		if(fMNum == 1) fNum ++;
		
	}
	
	/**
	 * 水表分析数据方法
	 */
	@TaskAnnotation("queryMeterAnaData")
	@Override
	public DrMeterAnaDataVO queryMeterAnaData(SessionFactory factory, QueryALDTO queryALDTO) {
		DrMeterAnaDataVO drMeterAnaDataVO = new DrMeterAnaDataVO();
		List<MeterInfo> lists = queryMeterInfoByZoneNo(factory, "");
//		List<String> sDnMeterList = new ArrayList<>();
//		for (MeterInfo meterInfo : lists) {
//			if(meterInfo.getMeterDn()<Constant.DN_50) {
//				sDnMeterList.add(meterInfo.getAccNo());
//			}
//		}
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
	
		// 1、不同口径水表计量统计
		List<MeterRunAnalysisVO> mralists = queryMeterRunAnalysisList(factory, queryALDTO);
		drMeterAnaDataVO.setMraLists(mralists);

		// 2、大口径，小口径，大口径（除消防表）的运行情况统计
		MeterAnalysisMapVO meterAnalysisMapVO = queryMeterRunAnalysisMapData(factory, queryALDTO);
		drMeterAnaDataVO.setMeterAnalysisMapVO(meterAnalysisMapVO);

		// 3、消防水表读数数据
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		FsMeterReadData fsMeterReadData = queryFsMeterReadData(mapper, lists, queryALDTO.getStartTime(),
				queryALDTO.getEndTime());
		drMeterAnaDataVO.setFsMeterReadData(fsMeterReadData);

		// 4、小口径零用量分析和小口径低流量月水量统计情况
		List<MeterMFlowData> queryMeterMFlow = mapper.queryMeterMZeroFlow(startTime,endTime);
		int fNum = 0;  //大于5个月没数据的水表个数
		int tfNum = 0; //2-4个月没数据的水表个数
		if(queryMeterMFlow != null && queryMeterMFlow.size() != 0) {
			List<String> mList = new ArrayList<>();
			for (MeterMFlowData meterMFlowData : queryMeterMFlow) {
				mList.add(meterMFlowData.getMonth());
			}
			getMeterNoMDataNum(fNum,tfNum,mList,startTime,endTime);	
		}
		DrSmallDnMeterData drSmallDnMeterData = new DrSmallDnMeterData();
		drSmallDnMeterData.setFmZeroMeterNum(fNum);
		drSmallDnMeterData.setTfmZeroMeterNum(tfNum);
		drSmallDnMeterData.setTotalZeroMeterNum(fNum+tfNum);
		drMeterAnaDataVO.setDrSmallDnMeterData(drSmallDnMeterData);
		
		//小口径低流量月水量统计情况
		List<MeterMFlowData> queryMeterMAvgFlow = mapper.queryMeterMAvgFlow(startTime, endTime);
		DrSmallDnAnaData drSmallDn15AnaData = new DrSmallDnAnaData();
		DrSmallDnAnaData drSmallDn20AnaData = new DrSmallDnAnaData();
		DrSmallDnAnaData drSmallDn25AnaData = new DrSmallDnAnaData();
		DrSmallDnAnaData drSmallDn40AnaData = new DrSmallDnAnaData();
		DrSmallDnAnaData drSmallDnAllAnaData = new DrSmallDnAnaData();
		int allFNum = 0;
		int allFtNum = 0;
		int allTtNum = 0;
		int allTNum = 0;
		for (MeterMFlowData meterMFlowData : queryMeterMAvgFlow) {
			Double flux = meterMFlowData.getFlux();
			switch (meterMFlowData.getMeterDn()) {
			case Constant.DN_15:
				if(flux <= 5) {
					allFNum++;
					drSmallDn15AnaData.setfFlowNum(drSmallDn15AnaData.getfFlowNum()+1);
				}else if(flux > 5 && flux <= 10) {
					allFtNum++;
					drSmallDn15AnaData.setfFlowNum(drSmallDn15AnaData.getFtFlowNum()+1);
				}else if(flux > 10 && flux <= 20) {
					allTtNum++;
					drSmallDn15AnaData.setfFlowNum(drSmallDn15AnaData.getTtFlowNum()+1);
				}else {
					allTNum++;
					drSmallDn15AnaData.setfFlowNum(drSmallDn15AnaData.gettFlowNum()+1);
				}
				break;
			case Constant.DN_20:
				if(flux <= 5) {
					allFNum++;
					drSmallDn20AnaData.setfFlowNum(drSmallDn20AnaData.getfFlowNum()+1);
				}else if(flux > 5 && flux <= 10) {
					allFtNum++;
					drSmallDn20AnaData.setfFlowNum(drSmallDn20AnaData.getFtFlowNum()+1);
				}else if(flux > 10 && flux <= 20) {
					allTtNum++;
					drSmallDn20AnaData.setfFlowNum(drSmallDn20AnaData.getTtFlowNum()+1);
				}else {
					allTNum++;
					drSmallDn20AnaData.setfFlowNum(drSmallDn20AnaData.gettFlowNum()+1);
				}
				break;
			case Constant.DN_25:
				if(flux <= 5) {
					allFNum++;
					drSmallDn25AnaData.setfFlowNum(drSmallDn25AnaData.getfFlowNum()+1);
				}else if(flux > 5 && flux <= 10) {
					allFtNum++;
					drSmallDn25AnaData.setfFlowNum(drSmallDn25AnaData.getFtFlowNum()+1);
				}else if(flux > 10 && flux <= 20) {
					allTtNum++;
					drSmallDn25AnaData.setfFlowNum(drSmallDn25AnaData.getTtFlowNum()+1);
				}else {
					allTNum++;
					drSmallDn25AnaData.setfFlowNum(drSmallDn25AnaData.gettFlowNum()+1);
				}
				break;
			case Constant.DN_40:
				if(flux <= 5) {
					allFNum++;
					drSmallDn40AnaData.setfFlowNum(drSmallDn40AnaData.getfFlowNum()+1);
				}else if(flux > 5 && flux <= 10) {
					allFtNum++;
					drSmallDn40AnaData.setfFlowNum(drSmallDn40AnaData.getFtFlowNum()+1);
				}else if(flux > 10 && flux <= 20) {
					allTtNum++;
					drSmallDn40AnaData.setfFlowNum(drSmallDn40AnaData.getTtFlowNum()+1);
				}else {
					allTNum++;
					drSmallDn40AnaData.setfFlowNum(drSmallDn40AnaData.gettFlowNum()+1);
				}
				break;

			default:
				break;
			}
		}
		drSmallDnAllAnaData.setfFlowNum(allFNum);
		drSmallDnAllAnaData.setFtFlowNum(allFtNum);
		drSmallDnAllAnaData.settFlowNum(allTNum);
		drSmallDnAllAnaData.setTtFlowNum(allTtNum);
		
		List<DrSmallDnAnaData> dsdaList = new ArrayList<>();
		dsdaList.add(drSmallDn15AnaData);
		dsdaList.add(drSmallDn20AnaData);
		dsdaList.add(drSmallDn25AnaData);
		dsdaList.add(drSmallDn40AnaData);
		dsdaList.add(drSmallDnAllAnaData);
		drMeterAnaDataVO.setDsdaLists(dsdaList);
		return drMeterAnaDataVO;
	}

	/**
	 * 获取消防水表读数数据
	 */
	public FsMeterReadData queryFsMeterReadData(ApparentLossMapper mapper, List<MeterInfo> lists, Integer startTime,
			Integer endTime) {
		List<String> list = new ArrayList<>();
		FsMeterReadData fsMeterReadData = new FsMeterReadData();
		for (MeterInfo meterInfo : lists) {
			if (Constant.FS_METER.equals(meterInfo.getMeterType()))
				list.add(meterInfo.getMeterNo());
		}
		List<String> queryFsMeterReadData = mapper.queryFsMeterReadData(list, startTime, endTime);
		int fsMeterNum = list.size();
		int zeroReadNum = queryFsMeterReadData.size();
		int normalReadNum = fsMeterNum - zeroReadNum;
		fsMeterReadData.setFsMeterNum(fsMeterNum);
		fsMeterReadData.setZeroReadNum(zeroReadNum);
		fsMeterReadData.setNormalReadNum(normalReadNum);
		return fsMeterReadData;
	}

	/**
	 * 诊断报告处理建议
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	@TaskAnnotation("queryDrDealAdvise")
	@Override
	public DrDealAdviseVO queryDrDealAdvise(SessionFactory factory, QueryALDTO queryALDTO) {
		List<MeterInfo> lists = queryMeterInfoByZoneNo(factory, "");
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		int meterNum = lists.size();
		if (meterNum == 0)
			return null;
		DrDealAdviseVO drDealAdviseVO = new DrDealAdviseVO();
		// 1、计算大口径水表处理数据
		// 大口径水表总数
		int bigDnMeterNum = 0;
		int smallDnMeterNum = 0;
		smallDnMeterNum = meterNum - bigDnMeterNum;
		DecimalFormat df = new DecimalFormat("#.0000");
		// 大口径水表占比
		double bigDnMeterRate = Double.parseDouble(df.format(bigDnMeterNum / (lists.size() * 1.0)));
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		int currentMonth = Integer.parseInt(TimeUtil.getcurrentDay("yyyyMM", new Date()));
		Double bigDnMFlow = mapper.queryBigDnTotalMFlow(currentMonth,currentMonth);
		Double smallDnMFlow = mapper.querySmallDnTotalMFlow(currentMonth,currentMonth);
		// 大口径最新月水量占比
		double allMFlow = 0.0;
		double sDnMFlowRate = 0.0;
		double bDnMFlowRate = 0.0;
		if (smallDnMFlow != null || bigDnMFlow != null) {
			allMFlow = smallDnMFlow + bigDnMFlow;
			sDnMFlowRate = Double.parseDouble(df.format(smallDnMFlow / allMFlow));
			bDnMFlowRate = Double.parseDouble(df.format(bigDnMFlow / allMFlow));
		}
		DrBigDnDealData drBigDnDealData = new DrBigDnDealData();
		drBigDnDealData.setBigDnMeterNum(bigDnMeterNum);
		drBigDnDealData.setBigDnMeterRate(bigDnMeterRate);
		drBigDnDealData.setMeterReadRate(bDnMFlowRate);
		drDealAdviseVO.setBigDnDealData(drBigDnDealData);

		// 2、大口径水表统计数据
		List<DrMeterStatisData> dmsLists = new ArrayList<>();
		DrMeterStatisData smallDnData = new DrMeterStatisData();
		smallDnData.setMeterDn("DN15~40");
		smallDnData.setmFlow(smallDnMFlow);
		smallDnData.setmFlowRate(sDnMFlowRate);
		smallDnData.setReadMeterNum(smallDnMeterNum);
		smallDnData.setReadMeterRate(Double.parseDouble(df.format(smallDnMeterNum / (meterNum * 1.0))));
		DrMeterStatisData bigDnData = new DrMeterStatisData();
		bigDnData.setMeterDn(">=DN50");
		bigDnData.setmFlow(bigDnMFlow);
		bigDnData.setmFlowRate(bDnMFlowRate);
		bigDnData.setReadMeterNum(bigDnMeterNum);
		bigDnData.setReadMeterRate(Double.parseDouble(df.format(bigDnMeterNum / (meterNum * 1.0))));
		dmsLists.add(smallDnData);
		dmsLists.add(bigDnData);
		drDealAdviseVO.setDmsList(dmsLists);
		
		// 3、诊断报告过载大口径水表统计数据
		// 4、诊断报告过载小口径水表统计数据
		List<DrFlowMeterData> bdfmList = new ArrayList<>();
		List<DrFlowMeterData> sdfmList = new ArrayList<>();
		List<MeterQH> queryMeterQH = mapper.queryMeterQH(queryALDTO, lists);
		Map<String, Double> qhMaxMinMap = getQhMaxMinMap(factory);
		try {
			
		for (MeterQH meterQH : queryMeterQH) {
			if(meterQH.getQh() == null || meterQH.getMeterDn() == null) continue;
			double qh = Double.parseDouble(meterQH.getQh());
			// 判断流量QH是否大于0.001，并且大于等于最大流量
			MeterDNParam meterDNParam = getMeterDNParam(qhMaxMinMap, meterQH.getMeterDn());
			if(meterDNParam.getMaxQ() == null) continue;
			if (qh >= Double.parseDouble(meterDNParam.getMaxQ()) ) {
				//计算最高月流量
				List<String> maxFlowCodeList = new ArrayList<>();
				maxFlowCodeList.add(meterQH.getMeterNo());
				List<Double> maxFList = mapper.queryMeterMMaxFlow(maxFlowCodeList, startTime, endTime);
				//判断是大口径，小口径
				DrFlowMeterData drFlowMeterData = new DrFlowMeterData();
				drFlowMeterData.setAddress(meterQH.getAddress());
				drFlowMeterData.setMeterNo(meterQH.getMeterNo());
				drFlowMeterData.setMeterDn(meterQH.getMeterDn());
				drFlowMeterData.sethMFlow(maxFList.size()>0?maxFList.get(0):0.0);
				int changeDn = getChangeDn(qh,qhMaxMinMap); //获取更换的口径
				drFlowMeterData.setChangeMeterDn(changeDn);
				if(meterQH.getMeterDn() < Constant.METER_DN_SIZE) {
					//小口径
					sdfmList.add(drFlowMeterData);
				}else {
					//大口径
					bdfmList.add(drFlowMeterData);
				}
			}
		}
		} catch (Exception e) {
			System.out.println(333);
		}
		drDealAdviseVO.setBdfmList(bdfmList);
		drDealAdviseVO.setSdfmList(sdfmList);

		// 5、其他
		List<MeterMFlowData> queryMeterMAvgFlow = mapper.queryMeterMAvgFlow(startTime, endTime);
		int fNum = 0;
		int ftNum = 0;
		int ttNum = 0;
		int tNum = 0;
		int allNum = 0;
		double lowFlowMeterRate = 0.0;
		double fFlowMeterRate = 0.0;
		double ftFlowMeterRate = 0.0;
		double ttFlowMeterRate = 0.0;
		double tFlowMeterRate = 0.0;
		for (MeterMFlowData meterMFlowData : queryMeterMAvgFlow) {
			Double flux = meterMFlowData.getFlux();
			if(flux <= 5) {
				fNum++;
			}else if(flux > 5 && flux <= 10) {
				ftNum++;
			}else if(flux > 10 && flux <= 20) {
				ttNum++;
			}else {
				tNum++;
			}
			allNum++;
		}
		if(allNum != 0) {
			fFlowMeterRate = Double.parseDouble(df.format(fNum / (allNum * 1.0)));
			ftFlowMeterRate = Double.parseDouble(df.format(ftNum / (allNum * 1.0)));
			ttFlowMeterRate = Double.parseDouble(df.format(ttNum / (allNum * 1.0)));
			tFlowMeterRate = Double.parseDouble(df.format(tNum / (allNum * 1.0)));
			lowFlowMeterRate = fFlowMeterRate + ftFlowMeterRate;
		}
		drDealAdviseVO.setfFlowMeterRate(fFlowMeterRate);
		drDealAdviseVO.setFtFlowMeterRate(ftFlowMeterRate);
		drDealAdviseVO.setTtFlowMeterRate(ttFlowMeterRate);
		drDealAdviseVO.settFlowMeterRate(tFlowMeterRate);
		drDealAdviseVO.setLowFlowMeterRate(lowFlowMeterRate);
		return drDealAdviseVO;
	}

	/**
	 * 获取更换的口径
	 * @param sysConfigs
	 */
	private Integer getChangeDn(Double qh, Map<String, Double> qhMaxMinMap) {
		for(Map.Entry<String, Double> entry : qhMaxMinMap.entrySet()){
		    String mapKey = entry.getKey();
		    Double mapValue = entry.getValue();
		    if(mapKey != null && mapKey.contains(Constant.MIN_DN_PARAM) && qh > mapValue) {
		    	String[] split = mapKey.split(Constant.MIN_DN_PARAM);
		    	String maxP = Constant.MAX_DN_PARAM + split[1];
		    	Double maxV = qhMaxMinMap.get(maxP);
		    	if(qh <= maxV) return Integer.parseInt(split[1]);
		    }
		}
		return 0;
		
	}

	/**
	 * 查询水表信息
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	public List<MeterInfo> getMeterInfo(SessionFactory factory, QueryALDTO queryALDTO) {
		List<MeterInfo> lists = new ArrayList<MeterInfo>();
		GisZoneServiceImpl gisZoneServiceImpl = new GisZoneServiceImpl();
		lists = gisZoneServiceImpl.queryMeterByZoneNo(factory, queryALDTO.getZoneNo());
		return lists;
	}
	
	/**
	 * 获取QH最小最大值map
	 * @return
	 */
	public Map<String,Double> getQhMaxMinMap(SessionFactory factory){
		Map<String,Double> map = new HashMap<String, Double>();
		CommonServiceImpl commonServiceImpl = new CommonServiceImpl();
		List<SysConfigVO> sysConfigs = commonServiceImpl.querySysConfig(factory);
		for (SysConfigVO sysConfigVO : sysConfigs) {
			String key = sysConfigVO.getKey();
			String value = sysConfigVO.getValue();
			if(key.contains(Constant.MAX_DN_PARAM) || key.contains(Constant.MIN_DN_PARAM)) {
				map.put(key, Double.parseDouble(value));
			}
		}
		return map;
	}
	
	/**
	 * 根据口径获取口径流量参数
	 * 
	 * @param meterDN
	 * @return
	 */
	public MeterDNParam getMeterDNParam(Map<String, Double> qhMaxMinMap, Integer meterDN) {
		MeterDNParam meterDNParam = new MeterDNParam();
		try {
			
		String maxP = Constant.MAX_DN_PARAM + meterDN.toString();
		String minP = Constant.MIN_DN_PARAM + meterDN.toString();
		Double maxValue = qhMaxMinMap.get(maxP);
		Double minValue = qhMaxMinMap.get(minP);
		meterDNParam.setMaxQ(maxValue+"");
		meterDNParam.setMinQ(minValue+"");
		} catch (Exception e) {
			System.out.println(99);
		}
		return meterDNParam;
	}

	@TaskAnnotation("queryDrQuestionList")
	@Override
	public DrqlVO queryDrQuestionList(SessionFactory factory, QueryALDTO queryALDTO) {
//		//1、大口径零流量
//		//2、大口径低流量、过载
//		//3、小口径零流量
//		//4、小口径低流量、过载
//		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
//		Integer timeType = queryALDTO.getTimeType();
//		Integer startTime = queryALDTO.getStartTime();
//		Integer endTime = queryALDTO.getEndTime();
//		DrqlVO drqlVO = new DrqlVO();
//		List<Map<Object,Object>> drqlBDnZeroFlowDataList = new ArrayList<>();
//		List<Map<Object,Object>> drqlBDnLHFlowDataList = new ArrayList<>();
//		List<Map<Object,Object>> drqlBDnErrFlowDataList = new ArrayList<>();
//		List<Map<Object,Object>> drqlSusUseDataList = new ArrayList<>();
//		List<Map<Object,Object>> drqlSDnZeroFlowDataList = new ArrayList<>();
//		List<Map<Object,Object>> drqlSDnLHFlowDataList = new ArrayList<>();
//		
//		//调用gis接口获取分区的水表信息
////		List<MeterInfo> lists = getMeterInfo(factory, queryALDTO);
//		List<MeterInfo> lists = queryMeterInfoByZoneNo(factory,"");
//		// 根据时间类型转换开始时间和结束时间
//		if (Constant.TIME_TYPE_Y.equals(timeType)) {
//			// 将年的时间范围转化为年月
//			if (startTime.toString().length() != 4 && endTime.toString().length() != 4) {
//				try {
//					throw new Exception("时间格式不正常");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			queryALDTO.setStartTime(Integer.parseInt(startTime.toString() + "01"));
//			queryALDTO.setEndTime(TimeUtil.getMonthByYear(endTime));
//		}
//		//获取时间范围的时间集合
//		List<Integer> monthsList = new ArrayList<>();
//		try {
//			monthsList = TimeUtil.getTimeList(startTime,endTime);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		List<MeterInfo> bDnLists = new ArrayList<>();
//		for (MeterInfo meterInfo : lists) {
//			if(meterInfo.getMeterDn() >= Constant.METER_DN_SIZE) {
//				bDnLists.add(meterInfo);
//			}
//		}
//		// 获取配置信息表数据
//		Map<String, Double> qhMaxMinMap = getQhMaxMinMap(factory);
//		// 获取口径的最大/最小参数流量
//		// 计算所有水表的QH值,
//		// 计算流量异常情况，零流量/低流量/正常/过载
//		List<MeterQH> queryMeterQH = mapper.queryMeterQH(queryALDTO, null);
//		List<MeterMFlowData> queryMeterMFlow = mapper.queryMeterMFlow(null, startTime, endTime);
//		for (MeterQH meterQH : queryMeterQH) {
//			double qh = Double.parseDouble(meterQH.getQh());
//			Map<Object,Object> maps = new HashMap<>();
//			maps.put("meterNo", meterQH.getMeterNo());
//			maps.put("accName", meterQH.getAccName());
//			maps.put("accNo", meterQH.getAccNo());
//			maps.put("useType", meterQH.getUseType());
//			maps.put("address", meterQH.getAddress());
//			maps.put("meterDn", meterQH.getMeterDn());
//			
//			//计算水表月份的流量
//			for (MeterMFlowData meterMFlowData : queryMeterMFlow) {
//				if(meterQH.getAccNo().equals(meterMFlowData.getCode())) {
//					List<Map<Integer,Double>> datas = getMFlowList(meterQH.getAccNo(),queryMeterMFlow,monthsList);
//					for (Map<Integer, Double> map : datas) {
//						maps.putAll(map);
//					}
//					break;
//				}
//			}
//			
//			// 判断流量QH是否小0.001，是则为零流量水表
//			if (qh < 0.001) {
//				//零流量
//				if(meterQH.getMeterDn() < Constant.METER_DN_SIZE) {
//					//小口径零流量
//					drqlSDnZeroFlowDataList.add(maps);
//				}else {
//					//大口径零流量
//					drqlBDnZeroFlowDataList.add(maps);
//				}
//			} else {
//				// 计算低流量/过载水表
//				MeterDNParam meterDNParam = getMeterDNParam(qhMaxMinMap, meterQH.getMeterDn());
//				maps.put("fsMeterStatus", (meterQH.getMeterType()==Constant.FS_METER?1:0));
//				if (meterDNParam.getMinQ() != null && qh < Double.parseDouble(meterDNParam.getMinQ())) {
//					//低流量
//					maps.put("anaResult", "低流量");
//				} else if (meterDNParam.getMaxQ() != null && qh >= Double.parseDouble(meterDNParam.getMaxQ())) {
//					//过载
//					maps.put("anaResult", "过载");
//				} 
//				//零流量
//				if(meterQH.getMeterDn() < Constant.METER_DN_SIZE) {
//					//小口径零流量
//					drqlSDnLHFlowDataList.add(maps);
//				}else {
//					//大口径零流量
//					drqlBDnLHFlowDataList.add(maps);
//				}
//			}		
//		}
//		//5、大口径用水异常
//		List<DrqlMeterErrUseData> dmeuList = mapper.queryMeterErrUseData(bDnLists, startTime, endTime);
//		for (MeterInfo meterInfo : bDnLists) {
//			for (DrqlMeterErrUseData dmeud : dmeuList) {
//				if(meterInfo.getAccNo().equals(dmeud.getAccNo())) {
//					for (MeterMFlowData meterMFlowData : queryMeterMFlow) {
//						if(dmeud.getMinV() != null && dmeud.getMaxV() != null && (meterMFlowData.getFlux() < dmeud.getMinV() || meterMFlowData.getFlux()> dmeud.getMaxV())) {
//							//异常用水,用水量Xi在X¯±3σ范围外(即Xi<X¯-3σ或Xi>X¯+3σ)，认为异常
//							Map<Object,Object> maps = new HashMap<>();
//							maps.put("meterNo", meterInfo.getMeterNo());
//							maps.put("accName", meterInfo.getAccName());
//							maps.put("accNo", meterInfo.getAccNo());
//							maps.put("useType", meterInfo.getUseType());
//							maps.put("address", meterInfo.getAddress());
//							maps.put("meterDn", meterInfo.getMeterDn());
//							maps.put("mReadDate", meterInfo.getmReadDate());
//							List<Map<Integer,Double>> datas = getMFlowList(meterInfo.getAccNo(),queryMeterMFlow,monthsList);
//							for (Map<Integer, Double> map : datas) {
//								maps.putAll(map);
//							}
//							for (MeterQH meterQH1 : queryMeterQH) {
//								if(meterQH1.getAccNo().equals(meterInfo.getAccNo())) {
//									int changeDn = getChangeDn(Double.parseDouble(meterQH1.getQh()),qhMaxMinMap); //获取更换的口径
//									maps.put("changeDn", changeDn);
//								}
//							}
//							drqlBDnErrFlowDataList.add(maps);
//							break;
//						}
//					}
//					break;
//				}
//			}
//		}	
//		
//		//6、大用户用水可疑用户
//		String[] priKeySplit1 = Constant.USE_PRI1.split(",");
//		String[] priKeySplit2 = Constant.USE_PRI2.split(",");
//		for (MeterInfo meterInfo : lists) {
//			String accName = meterInfo.getAccName();
//			String useType = meterInfo.getUseType();
//			boolean susUseFlag = false;
//			for (String string : priKeySplit1) {
//				if(accName != null && accName.contains(string) && Constant.USER_TYPE_PP.equals(useType)) {
//					susUseFlag = true;
//					break;
//				}
//			}
//			for (String string : priKeySplit2) {
//				if(accName != null && accName.contains(string) && !Constant.USER_TYPE_SP.equals(useType)) {
//					susUseFlag = true;
//					break;
//				}
//			}
//			if(susUseFlag) {
//				Map<Object,Object> maps = new HashMap<>();
//				maps.put("meterNo", meterInfo.getMeterNo());
//				maps.put("accName", meterInfo.getAccName());
//				maps.put("accNo", meterInfo.getAccNo());
//				maps.put("useType", meterInfo.getUseType());
//				maps.put("address", meterInfo.getAddress());
//				maps.put("meterDn", meterInfo.getMeterDn());
//				maps.put("mReadDate", meterInfo.getmReadDate());
//				List<MeterInfo> accNos = new ArrayList<>();
//				MeterInfo mInfo = new MeterInfo();
//				mInfo.setAccNo(meterInfo.getAccNo());
//				accNos.add(mInfo);
//				List<Map<Integer,Double>> datas = getMFlowList(meterInfo.getAccNo(),queryMeterMFlow,monthsList);
//				for (Map<Integer, Double> map : datas) {
//					maps.putAll(map);
//				}
//				drqlSusUseDataList.add(maps);
//				susUseFlag = false;
//			}
//		}
//		drqlVO.setDrqlBDnErrFlowData(drqlBDnErrFlowDataList);
//		drqlVO.setDrqlBDnLHFlowData(drqlBDnLHFlowDataList);
//		drqlVO.setDrqlBDnZeroFlowData(drqlBDnZeroFlowDataList);
//		drqlVO.setDrqlsDnLHFlowData(drqlSDnLHFlowDataList);
//		drqlVO.setDrqlsDnZeroFlowData(drqlSDnZeroFlowDataList);
//		drqlVO.setDrqlSusUseData(drqlSusUseDataList);
//		return drqlVO;
		return null;
	}
	
	/**
	 * 获取月流量集合
	 */
	public List<Map<Integer,Double>> getMFlowList(String meterNo,List<MeterMFlowData> queryMeterMFlow,List<Integer> monthsList) {
		List<MeterMFlowData> noList = new ArrayList<>();
		List<Map<Integer,Double>> datas = new ArrayList<>();
		for (MeterMFlowData meterMFlowData : queryMeterMFlow) {
			if(meterNo.equals(meterMFlowData.getCode())) {
				noList.add(meterMFlowData);
			}
		}
		boolean notNullFlag = false;
		for(int i = 0; i< monthsList.size(); i++) {
			Map<Integer,Double> map = new HashMap<>();
			for (MeterMFlowData meterMFlowData : noList) {
				if(monthsList.get(i) == Integer.parseInt(meterMFlowData.getMonth())) {
					map.put(monthsList.get(i), meterMFlowData.getFlux());
					notNullFlag = true;
					break;
				}
			}
			if(!notNullFlag) {
				map.put(monthsList.get(i), null);
				notNullFlag = false;
			} 
			datas.add(map);
		}
		return datas;
	}
	
	/**
	 * 查询水表信息
	 * @param factory
	 * @param zoneNo
	 * @return
	 */
	List<MeterInfo> queryMeterInfoByZoneNo(SessionFactory factory, String zoneNo) {
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		List<MeterInfo> result = mapper.queryMeterInfoByZoneNo(zoneNo);
		return result;
	}
}
