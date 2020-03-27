package com.koron.inwlms.service.apparentLoss.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
import com.koron.inwlms.bean.VO.apparentLoss.FSMeterData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterAnalysisMapVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterDNParam;
import com.koron.inwlms.bean.VO.apparentLoss.MeterFlowRateData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterFlowVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterManageData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterManageRankData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterQH;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.apparentLoss.PageALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneDatas;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneRankData;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.common.SysConfigVO;
import com.koron.inwlms.mapper.master.apparentLoss.ApparentLossMapper;
import com.koron.inwlms.service.apparentLoss.ApparentLossService;
import com.koron.inwlms.service.common.impl.CommonServiceImpl;
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
		String dmaNo = queryALDTO.getDmaNo();
		String sZone = queryALDTO.getSecondeLevelZone();
		String fZone = queryALDTO.getFirstLevelZone();
		// 对分区级别做过滤，按最小的分区查询指标
		if (StringUtil.isNotEmpty(dmaNo)) {
			queryALDTO.setSecondeLevelZone(null);
			queryALDTO.setFirstLevelZone(null);
		} else if (StringUtil.isNotEmpty(sZone)) {
			queryALDTO.setDmaNo(null);
			queryALDTO.setFirstLevelZone(null);
		} else if (StringUtil.isNotEmpty(fZone)) {
			queryALDTO.setDmaNo(null);
			queryALDTO.setSecondeLevelZone(null);
		}
		if (Constant.TIME_TYPE_M.equals(timeType)) {
			// 月指标查询
			result = mapper.queryMALOverviewData(queryALDTO);
		} else if (Constant.TIME_TYPE_Y.equals(timeType)) {
			// 年指标查询
			result = mapper.queryYALOverviewData(queryALDTO);
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
	public PageALListVO queryALList(SessionFactory factory, QueryALListDTO queryALListDTO) {
		ApparentLossMapper mapper = factory.getMapper(ApparentLossMapper.class);
		Integer timeType = queryALListDTO.getTimeType();
		String dmaNo = queryALListDTO.getDmaNo();
		String sZone = queryALListDTO.getSecondeLevelZone();
		String fZone = queryALListDTO.getFirstLevelZone();
		List<String> lists = new ArrayList<>();
		// 对分区级别做过滤，按最小的分区查询指标
		if (StringUtil.isEmpty(dmaNo) && StringUtil.isEmpty(sZone) && StringUtil.isEmpty(fZone)) {
			lists = new GisZoneServiceImpl().queryZoneNosByRank(factory, 1);
		} else if (StringUtil.isNotEmpty(dmaNo)) {

		} else if (StringUtil.isNotEmpty(sZone)) {
			lists = new GisZoneServiceImpl().querySubZoneNos(factory, sZone);
		} else if (StringUtil.isNotEmpty(fZone)) {
			lists = new GisZoneServiceImpl().querySubZoneNos(factory, fZone);
		}
		List<ALListVO> alLists = null;
		int rowNumber = 0;

		if (Constant.TIME_TYPE_M.equals(timeType)) {
			// 月指标查询
			alLists = mapper.queryMALList(queryALListDTO, lists);
			// 查询总条数
			rowNumber = mapper.countMALList(queryALListDTO, lists);
		} else if (Constant.TIME_TYPE_Y.equals(timeType)) {
			// 年指标查询
			alLists = mapper.queryYALList(queryALListDTO, lists);
			// 查询总条数
			rowNumber = mapper.countYALList(queryALListDTO, lists);
		}
		// 返回数据结果
		PageALListVO result = new PageALListVO();
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
		String dmaNo = queryALDTO.getDmaNo();
		String sZone = queryALDTO.getSecondeLevelZone();
		String fZone = queryALDTO.getFirstLevelZone();
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		// 对分区级别做过滤，按最小的分区查询指标
		if (StringUtil.isNotEmpty(dmaNo)) {
			queryALDTO.setSecondeLevelZone(null);
			queryALDTO.setFirstLevelZone(null);
		} else if (StringUtil.isNotEmpty(sZone)) {
			queryALDTO.setDmaNo(null);
			queryALDTO.setFirstLevelZone(null);
		} else if (StringUtil.isNotEmpty(fZone)) {
			queryALDTO.setDmaNo(null);
			queryALDTO.setSecondeLevelZone(null);
		}

		// 计算指标曲线
		List<Integer> timeList = TimeUtil.getMonthsList(startTime, endTime);
		if (Constant.TIME_TYPE_M.equals(timeType)) {
			// 月指标查询
			for (Integer month : timeList) {
				queryALDTO.setStartTime(month);
				queryALDTO.setEndTime(month);
				ALOverviewDataVO result = mapper.queryMALOverviewData(queryALDTO);
				lists.add(result);
			}
			// 解析月指标数据
		} else if (Constant.TIME_TYPE_Y.equals(timeType)) {
			// 年指标查询
			for (Integer month : timeList) {
				queryALDTO.setStartTime(month);
				queryALDTO.setEndTime(month);
				ALOverviewDataVO result = mapper.queryYALOverviewData(queryALDTO);
				lists.add(result);
			}
		}

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
		queryALListDTO.setDmaNo(queryALDTO.getDmaNo());
		queryALListDTO.setEndTime(queryALDTO.getEndTime());
		queryALListDTO.setFirstLevelZone(queryALDTO.getFirstLevelZone());
		queryALListDTO.setSecondeLevelZone(queryALDTO.getSecondeLevelZone());
		queryALListDTO.setStartTime(queryALDTO.getStartTime());
		queryALListDTO.setTimeType(queryALDTO.getTimeType());
		queryALListDTO.setPage(1);
		queryALListDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
		PageALListVO queryALList = queryALList(factory, queryALListDTO);
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
		String dmaNo = queryALDTO.getDmaNo();
		String sZone = queryALDTO.getSecondeLevelZone();
		String fZone = queryALDTO.getFirstLevelZone();
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		List<MeterInfo> lists = new ArrayList<>();
		// 对分区级别做过滤，按最小的分区查询指标，调用gis接口获取分区的水表信息
		if (StringUtil.isEmpty(dmaNo) && StringUtil.isEmpty(sZone) && StringUtil.isEmpty(fZone)) {
			lists = new GisZoneServiceImpl().queryMeterByZoneNo(factory, "");
		} else if (StringUtil.isNotEmpty(dmaNo)) {

		} else if (StringUtil.isNotEmpty(sZone)) {
			lists = new GisZoneServiceImpl().queryMeterByZoneNo(factory, sZone);
		} else if (StringUtil.isNotEmpty(fZone)) {
			lists = new GisZoneServiceImpl().queryMeterByZoneNo(factory, fZone);
		}

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
		//获取配置信息表数据
		List<SysConfigVO> sysConfigs = ADOConnection.runTask(new CommonServiceImpl(),"querySysConfig",List.class);
		// 获取口径的最大/最小参数流量
		// 计算所有水表的QH值,
		// 计算流量异常情况，零流量/低流量/正常/过载
		List<MeterQH> queryMeterQH = mapper.queryMeterQH(queryALDTO, lists);
		for (MeterQH meterQH : queryMeterQH) {
			double qh = Double.parseDouble(meterQH.getQh());
			for (MeterInfo meterInfo : lists) {
				if (meterInfo.getMeterNo().equals(meterQH.getMeterNo())) {
					for (MeterRunAnalysisVO mraVO : resLists) {
						if (mraVO.getMeterDn() == meterInfo.getMeterDn()) {
							// 判断流量QH是否小0.001，是则为零流量水表
							if (qh < 0.001) {
								mraVO.setZeroFlowMeterNum(mraVO.getZeroFlowMeterNum() + 1);
							} else {
								//计算低流量/正常/过载水表
								MeterDNParam meterDNParam = getMeterDNParam(sysConfigs,meterInfo.getMeterDn());
								if (qh < Double.parseDouble(meterDNParam.getMinQ())) {
									mraVO.setLowFlowMeterNum(mraVO.getLowFlowMeterNum() + 1);
								} else if (qh < Double.parseDouble(meterDNParam.getMaxQ())) {
									mraVO.setNormalFlowMeterNum(mraVO.getNormalFlowMeterNum() + 1);
								} else {
									mraVO.setHighFlowMeterNum(mraVO.getHighFlowMeterNum() + 1);
								}
							}
							mraVO.setMeterNum(mraVO.getMeterNum()+1);
							break;
						}
					}
					break;
				}
			}
		}
		return resLists;
	}
	

	/**
	 * 根据口径获取口径流量参数
	 * 
	 * @param meterDN
	 * @return
	 */
	public MeterDNParam getMeterDNParam(List<SysConfigVO> sysConfigs,Integer meterDN) {
		MeterDNParam meterDNParam = new MeterDNParam();
		String maxP = Constant.MAX_DN_PARAM + meterDN.toString();
		String minP = Constant.MIN_DN_PARAM + meterDN.toString();
		for (SysConfigVO sysConfigVO : sysConfigs) {
			if (sysConfigVO.getKey().equals(maxP)) {
				meterDNParam.setMaxQ(sysConfigVO.getValue());
			} else if (sysConfigVO.getKey().equals(minP)) {
				meterDNParam.setMinQ(sysConfigVO.getValue());
			}
		}
		return meterDNParam;
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
		
		int bigDnZeroFSum = 0; //大口径零流量水表数量
		int bigDnLowFSum = 0; //大口径低流量水表数量
		int bigDnNorFSum = 0; //大口径正常流量水表数量
		int bigDnHighFSum = 0; //大口径过载流量水表数量
		int smallDnZeroFSum = 0; //小口径零流量水表数量
		int smallDnLowFSum = 0; //小口径低流量水表数量
		int smallDnNorFSum = 0; //小口径正常流量水表数量
		int smallDnHighFSum = 0; //小口径过载流量水表数量
		int bigDnNoFSDnZeroFSum = 0; //大口径（除消防表）零流量水表数量
		int bigDnNoFSLowFSum = 0; //大口径（除消防表）低流量水表数量
		int bigDnNoFSNorFSum = 0; //大口径（除消防表）正常流量水表数量
		int bigDnNoFSHighFSum = 0; //大口径（除消防表）过载流量水表数量
		//口径大于等于50的是大口径水表，否则是小口径
		for (MeterRunAnalysisVO meterRunAnalysisVO : queryMeterRunAnalysisList) {
			Integer meterDn = meterRunAnalysisVO.getMeterDn();
			if(meterDn >= Constant.METER_DN_SIZE) {
				//大口径，判断是否是消防表
				if(Constant.FS_METER.equals(meterRunAnalysisVO.getMeterType())) {
					bigDnNoFSDnZeroFSum += meterRunAnalysisVO.getZeroFlowMeterNum();
					bigDnNoFSLowFSum += meterRunAnalysisVO.getLowFlowMeterNum();
					bigDnNoFSNorFSum += meterRunAnalysisVO.getNormalFlowMeterNum();
					bigDnNoFSHighFSum += meterRunAnalysisVO.getHighFlowMeterNum();
				}
				bigDnZeroFSum += meterRunAnalysisVO.getZeroFlowMeterNum();
				bigDnLowFSum += meterRunAnalysisVO.getLowFlowMeterNum();
				bigDnNorFSum += meterRunAnalysisVO.getNormalFlowMeterNum();
				bigDnHighFSum += meterRunAnalysisVO.getHighFlowMeterNum();
			}else {
				//小口径
				smallDnZeroFSum += meterRunAnalysisVO.getZeroFlowMeterNum();
				smallDnLowFSum += meterRunAnalysisVO.getLowFlowMeterNum();
				smallDnNorFSum += meterRunAnalysisVO.getNormalFlowMeterNum();
				smallDnHighFSum += meterRunAnalysisVO.getHighFlowMeterNum();
			}
		}
		double bigDnSum = (bigDnZeroFSum + bigDnLowFSum + bigDnNorFSum + bigDnHighFSum)*1.0;
		double smallDnSum = (smallDnZeroFSum + smallDnLowFSum + smallDnNorFSum + smallDnHighFSum)*1.0;
		double bigDnNoFSDnSum = (bigDnNoFSDnZeroFSum + bigDnNoFSLowFSum + bigDnNoFSNorFSum + bigDnNoFSHighFSum)*1.0;
		DecimalFormat df = new DecimalFormat("#.0000");
		bigDnData.setZeroFlowMeterRate(bigDnZeroFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnZeroFSum/bigDnSum)));
		bigDnData.setLowFlowMeterRate(bigDnLowFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnLowFSum/bigDnSum)));
		bigDnData.setNormalFlowMeterRate(bigDnNorFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNorFSum/bigDnSum)));
		bigDnData.setHighFlowMeterRate(bigDnHighFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnHighFSum/bigDnSum)));
		smallDnData.setZeroFlowMeterRate(smallDnZeroFSum == 0 ? 0 : Double.parseDouble(df.format(smallDnZeroFSum/smallDnSum)));
		smallDnData.setLowFlowMeterRate(smallDnLowFSum == 0 ? 0 : Double.parseDouble(df.format(smallDnLowFSum/smallDnSum)));
		smallDnData.setNormalFlowMeterRate(smallDnNorFSum == 0 ? 0 : Double.parseDouble(df.format(smallDnNorFSum/smallDnSum)));
		smallDnData.setHighFlowMeterRate(smallDnHighFSum == 0 ? 0 : Double.parseDouble(df.format(smallDnHighFSum/smallDnSum)));
		bigDnNoFSData.setZeroFlowMeterRate(bigDnNoFSDnZeroFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNoFSDnZeroFSum/bigDnNoFSDnSum)));
		bigDnNoFSData.setLowFlowMeterRate(bigDnNoFSLowFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNoFSLowFSum/bigDnNoFSDnSum)));
		bigDnNoFSData.setNormalFlowMeterRate(bigDnNoFSNorFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNoFSNorFSum/bigDnNoFSDnSum)));
		bigDnNoFSData.setHighFlowMeterRate(bigDnNoFSHighFSum == 0 ? 0 : Double.parseDouble(df.format(bigDnNoFSHighFSum/bigDnNoFSDnSum)));
		
		String dmaNo = queryALDTO.getDmaNo();
		String sZone = queryALDTO.getSecondeLevelZone();
		String fZone = queryALDTO.getFirstLevelZone();
		List<MeterInfo> lists = new ArrayList<>();
		// 对分区级别做过滤，按最小的分区查询指标，调用gis接口获取分区的水表信息
		if (StringUtil.isEmpty(dmaNo) && StringUtil.isEmpty(sZone) && StringUtil.isEmpty(fZone)) {
			lists = new GisZoneServiceImpl().queryMeterByZoneNo(factory, "");
		} else if (StringUtil.isNotEmpty(dmaNo)) {

		} else if (StringUtil.isNotEmpty(sZone)) {
			lists = new GisZoneServiceImpl().queryMeterByZoneNo(factory, sZone);
		} else if (StringUtil.isNotEmpty(fZone)) {
			lists = new GisZoneServiceImpl().queryMeterByZoneNo(factory, fZone);
		}
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
}
