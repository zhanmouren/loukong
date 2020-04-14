package com.koron.inwlms.service.zoneLoss.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryDmaZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryFZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QuerySZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneHstDataDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneHstDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneHstIndicData;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.zoneLoss.DmaZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.FZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.IndicatorInfo;
import com.koron.inwlms.bean.VO.zoneLoss.SZoneLossListVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.zoneLoss.ZoneLossAnaMapper;
import com.koron.inwlms.service.common.impl.GisZoneServiceImpl;
import com.koron.inwlms.service.zoneLoss.ZoneLossAnaService;
import com.koron.inwlms.util.PageUtil;
import com.koron.inwlms.util.TimeUtil;
import com.koron.util.Constant;

/**
 * 分区漏损分析接口实现层
 * @author csh
 * @Date 2020/04/09
 *
 */
@Service
public class ZoneLossAnaServiceImpl implements ZoneLossAnaService {

	@TaskAnnotation("queryFZoneLossList")
	@Override
	public PageListVO<List<FZoneLossListVO>> queryFZoneLossList(SessionFactory factory,
			QueryFZoneLossListDTO queryFZoneLossListDTO) {
		//查询一级分区信息
		GisZoneServiceImpl gzsImpl = new GisZoneServiceImpl();
		List<String> zoneNos = new ArrayList<>();
		List<ZoneInfo> zoneInfos = gzsImpl.queryZoneNosByRank(factory, Constant.RANK_F);
		for(ZoneInfo zoneInfo : zoneInfos) {
			zoneNos.add(zoneInfo.getZoneNo());
		}
		
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		IndicatorDTO indicatorDTO = new IndicatorDTO();
		indicatorDTO.setStartTime(queryFZoneLossListDTO.getStartTime());
		indicatorDTO.setEndTime(queryFZoneLossListDTO.getEndTime());
		indicatorDTO.setZoneCodes(zoneNos);
		indicatorDTO.setTimeType(queryFZoneLossListDTO.getTimeType());
		List<String> baseCodes = new ArrayList<>(); //基础指标编码集合
		List<String> wbCodes = new ArrayList<>(); //水平衡基础指标编码集合
		List<String> zoneCodes = new ArrayList<>();  //分区漏损指标编码集合
		if(Constant.TIME_TYPE_M.equals(queryFZoneLossListDTO.getTimeType())) {
			baseCodes.add("FLMNOCM"); //一级分区月用户数指标编码
			baseCodes.add("FLMFTPL"); //一级分区月管长数指标编码
			wbCodes.add("FLMFWSSITDF"); //一级分区月供水量指标编码
			wbCodes.add("FLMBC"); //一级分区月计费用水量指标编码
			wbCodes.add("FLMWL"); //一级分区月漏损量指标编码
			zoneCodes.add("FLMLCA"); //一级分区月单位户数漏损量
			zoneCodes.add("FLMLPL"); //一级分区月单位管长漏损量
			zoneCodes.add("FLMWLR"); //一级分区月漏损率
			zoneCodes.add("FLMMNF"); //一级分区月MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("FLMMNFTDF"); //一级分区月MNF/TDF
			
		}else {
			baseCodes.add("FLYNOCM");//一级分区年用户数指标编码
			baseCodes.add("FLYFTPL");//一级分区年管长数指标编码
			wbCodes.add("FLYFWSSITDF"); //一级分区年供水量指标编码
			wbCodes.add("FLYBC"); //一级分区年计费用水量指标编码
			wbCodes.add("FLYWL"); //一级分区年漏损量指标编码
			zoneCodes.add("FLYLCA"); //一级分区年单位户数漏损量
			zoneCodes.add("FLYLPL"); //一级分区年单位管长漏损量
			zoneCodes.add("FLYWLR"); //一级分区年漏损率
			zoneCodes.add("FLYMNF"); //一级分区年MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("FLYMNFTDF"); //一级分区年MNF/TDF
		}
		//查询基础指标数据
		indicatorDTO.setCodes(baseCodes);
		List<IndicatorVO> baseIndics = mapper.queryBaseIndicData(indicatorDTO);
		
		//查询水平衡指标数据
		indicatorDTO.setCodes(wbCodes);
		List<IndicatorVO> wbBaseIndics = mapper.queryWBBaseIndicData(indicatorDTO);
		
		//查询分区指标数据
		indicatorDTO.setCodes(zoneCodes);
		List<IndicatorVO> zoneIndics = mapper.queryZoneLossIndicData(indicatorDTO);
		
		List<Integer> timeList = TimeUtil.getMonthsList(indicatorDTO.getStartTime(),indicatorDTO.getEndTime());
		int timeNum = timeList.size();
		
		List<FZoneLossListVO> dataList = new ArrayList<>();
		for (int i = 0;i<zoneInfos.size();i++) {
			if(i < (queryFZoneLossListDTO.getPage()-1)*queryFZoneLossListDTO.getPageCount()) continue;
			if(dataList.size() >= queryFZoneLossListDTO.getPageCount()) break;
			FZoneLossListVO fZoneLossListVO = new FZoneLossListVO();
			fZoneLossListVO.setZoneNo(zoneInfos.get(i).getZoneNo());
			fZoneLossListVO.setZoneName(zoneInfos.get(i).getZoneName());
			fZoneLossListVO.setZoneRank(Constant.RANK_F);
			fZoneLossListVO.setpZoneNo(zoneInfos.get(i).getpZoneNo());
			fZoneLossListVO.setpZoneName(zoneInfos.get(i).getpZoneName());
			fZoneLossListVO.setAddress(zoneInfos.get(i).getAddress());
			//TODO 分区警报查询
			fZoneLossListVO.setAlarmStatus(0);
			
			int meterNum = 0;
			double pipeLength = 0;
			double flow = 0;
			double useFlow = 0;
			double lossFlow = 0;
			double perUserLossFlow = 0; //一级分区月单位户数漏损量
			double perLengthLossFlow = 0; //一级分区月单位管长漏损量
			double lossRate = 0; //一级分区月漏损率
			double mnf = 0; //一级分区月MNF
			double mnfTime = 0; //一级分区月MNF TIME
			double mnfTdf = 0; //一级分区月MNF/TDF
			if(Constant.TIME_TYPE_M.equals(queryFZoneLossListDTO.getTimeType())) {
				for(IndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLMNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLMFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLMFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLMBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLMWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLMLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLMLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLMWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLMMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLMMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}else {
				for(IndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLYNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLYFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLYFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLYBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLYWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLYLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLYLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLYWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLYMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLYMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}
			fZoneLossListVO.setMeterNum(meterNum/timeNum);
			fZoneLossListVO.setPipeLength(pipeLength/timeNum);
			fZoneLossListVO.setFlow(flow);
			fZoneLossListVO.setUseFlow(useFlow);
			fZoneLossListVO.setLossFlow(lossFlow);
			fZoneLossListVO.setPerUserLossFlow(perUserLossFlow/timeNum);
			fZoneLossListVO.setPerLengthLossFlow(perLengthLossFlow/timeNum);
			fZoneLossListVO.setLossRate(lossRate/timeNum);
			fZoneLossListVO.setMnf(mnf);
			fZoneLossListVO.setMnfTdf(mnfTdf/timeNum);
			dataList.add(fZoneLossListVO);
			
		}
		PageListVO<List<FZoneLossListVO>> result = new PageListVO<>();
		result.setDataList(dataList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryFZoneLossListDTO.getPage(), queryFZoneLossListDTO.getPageCount(), zoneInfos.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("querySZoneLossList")
	@Override
	public PageListVO<List<SZoneLossListVO>> querySZoneLossList(SessionFactory factory, QuerySZoneLossListDTO querySZoneLossListDTO) {
		//查询二级分区信息
		GisZoneServiceImpl gzsImpl = new GisZoneServiceImpl();
		List<String> zoneNos = new ArrayList<>();
		List<ZoneInfo> zoneInfos = gzsImpl.queryZoneNosByRank(factory, Constant.RANK_S);
		for(ZoneInfo zoneInfo : zoneInfos) {
			zoneNos.add(zoneInfo.getZoneNo());
		}
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		IndicatorDTO indicatorDTO = new IndicatorDTO();
		indicatorDTO.setStartTime(querySZoneLossListDTO.getStartTime());
		indicatorDTO.setEndTime(querySZoneLossListDTO.getEndTime());
		indicatorDTO.setCodes(zoneNos);
		indicatorDTO.setTimeType(querySZoneLossListDTO.getTimeType());
		List<String> baseCodes = new ArrayList<>(); //基础指标编码集合
		List<String> wbCodes = new ArrayList<>(); //水平衡基础指标编码集合
		List<String> zoneCodes = new ArrayList<>();  //分区漏损指标编码集合
		if(Constant.TIME_TYPE_M.equals(querySZoneLossListDTO.getTimeType())) {
			baseCodes.add("SLMNOCM"); //二级分区月用户数指标编码
			baseCodes.add("SLMFTPL"); //二级分区月管长数指标编码
			wbCodes.add("SLMFWSSITDF"); //二级分区月供水量指标编码
			wbCodes.add("SLMBC"); //二级分区月计费用水量指标编码
			wbCodes.add("SLMWL"); //二级分区月漏损量指标编码
			zoneCodes.add("SLMLCA"); //二级分区月单位户数漏损量
			zoneCodes.add("SLMLPL"); //二级分区月单位管长漏损量
			zoneCodes.add("SLMWLR"); //二级分区月漏损率
			zoneCodes.add("SLMMNF"); //二级分区月MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("SLMMNFTDF"); //二级分区月MNF/TDF
		}else {
			baseCodes.add("SLYNOCM");//二级分区年用户数指标编码
			baseCodes.add("SLYFTPL");//二级分区年管长数指标编码
			wbCodes.add("SLYFWSSITDF"); //二级分区年供水量指标编码
			wbCodes.add("SLYBC"); //二级分区年计费用水量指标编码
			wbCodes.add("SLYWL"); //二级分区年漏损量指标编码
			zoneCodes.add("SLYLCA"); //二级分区年单位户数漏损量
			zoneCodes.add("SLYLPL"); //二级分区年单位管长漏损量
			zoneCodes.add("SLYWLR"); //二级分区年漏损率
			zoneCodes.add("SLYMNF"); //二级分区年MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("SLYMNFTDF"); //二级分区年MNF/TDF
		}
		//查询基础指标数据
		indicatorDTO.setCodes(baseCodes);
		List<IndicatorVO> baseIndics = mapper.queryBaseIndicData(indicatorDTO);
		
		//查询水平衡指标数据
		indicatorDTO.setCodes(wbCodes);
		List<IndicatorVO> wbBaseIndics = mapper.queryWBBaseIndicData(indicatorDTO);
		
		//查询分区指标数据
		indicatorDTO.setCodes(zoneCodes);
		List<IndicatorVO> zoneIndics = mapper.queryZoneLossIndicData(indicatorDTO);
		
		List<Integer> timeList = TimeUtil.getMonthsList(indicatorDTO.getStartTime(),indicatorDTO.getEndTime());
		int timeNum = timeList.size();
		
		List<SZoneLossListVO> dataList = new ArrayList<>();
		for (int i = 0; i<zoneInfos.size(); i++) {
			if(i < (querySZoneLossListDTO.getPage()-1)*querySZoneLossListDTO.getPageCount()) continue;
			if(dataList.size() >= querySZoneLossListDTO.getPageCount()) break;
			SZoneLossListVO sZoneLossListVO = new SZoneLossListVO();
			sZoneLossListVO.setZoneNo(zoneInfos.get(i).getZoneNo());
			sZoneLossListVO.setZoneName(zoneInfos.get(i).getZoneName());
			sZoneLossListVO.setZoneRank(Constant.RANK_S);
			sZoneLossListVO.setpZoneNo(zoneInfos.get(i).getpZoneNo());
			sZoneLossListVO.setpZoneName(zoneInfos.get(i).getpZoneName());
			sZoneLossListVO.setAddress(zoneInfos.get(i).getAddress());
			//TODO 分区警报查询
			sZoneLossListVO.setAlarmStatus(0);
			
			int meterNum = 0;
			double pipeLength = 0;
			double flow = 0;
			double useFlow = 0;
			double lossFlow = 0;
			double perUserLossFlow = 0; //二级分区月单位户数漏损量
			double perLengthLossFlow = 0; //二级分区月单位管长漏损量
			double lossRate = 0; //二级分区月漏损率
			double mnf = 0; //二级分区月MNF
			double mnfTime = 0; //二级分区月MNF TIME
			double mnfTdf = 0; //二级分区月MNF/TDF
			if(Constant.TIME_TYPE_M.equals(querySZoneLossListDTO.getTimeType())) {
				for(IndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "SLMNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "SLMFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLMFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLMBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLMWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLMLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLMLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLMWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLMMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLMMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}else {
				for(IndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "SLYNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "SLYFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLYFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLYBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLYWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLYLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLYLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLYWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLYMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "SLYMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}
			sZoneLossListVO.setMeterNum(meterNum/timeNum);
			sZoneLossListVO.setPipeLength(pipeLength/timeNum);
			sZoneLossListVO.setFlow(flow);
			sZoneLossListVO.setUseFlow(useFlow);
			sZoneLossListVO.setLossFlow(lossFlow);
			sZoneLossListVO.setPerUserLossFlow(perUserLossFlow/timeNum);
			sZoneLossListVO.setPerLengthLossFlow(perLengthLossFlow/timeNum);
			sZoneLossListVO.setLossRate(lossRate/timeNum);
			sZoneLossListVO.setMnf(mnf);
			sZoneLossListVO.setMnfTdf(mnfTdf/timeNum);
			dataList.add(sZoneLossListVO);
			
		}
		PageListVO<List<SZoneLossListVO>> result = new PageListVO<>();
		result.setDataList(dataList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(querySZoneLossListDTO.getPage(), querySZoneLossListDTO.getPageCount(), zoneInfos.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("queryDmaZoneLossList")
	@Override
	public PageListVO<List<DmaZoneLossListVO>> queryDmaZoneLossList(SessionFactory factory,
			QueryDmaZoneLossListDTO queryDmaZoneLossListDTO) {
		//查询dma分区信息
		GisZoneServiceImpl gzsImpl = new GisZoneServiceImpl();
		List<String> zoneNos = new ArrayList<>();
		List<ZoneInfo> zoneInfos = gzsImpl.queryZoneNosByRank(factory, Constant.RANK_T);
		for(ZoneInfo zoneInfo : zoneInfos) {
			zoneNos.add(zoneInfo.getZoneNo());
		}
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		IndicatorDTO indicatorDTO = new IndicatorDTO();
		indicatorDTO.setStartTime(queryDmaZoneLossListDTO.getStartTime());
		indicatorDTO.setEndTime(queryDmaZoneLossListDTO.getEndTime());
		indicatorDTO.setCodes(zoneNos);
		indicatorDTO.setTimeType(queryDmaZoneLossListDTO.getTimeType());
		List<String> baseCodes = new ArrayList<>(); //基础指标编码集合
		List<String> wbCodes = new ArrayList<>(); //水平衡基础指标编码集合
		List<String> zoneCodes = new ArrayList<>();  //分区漏损指标编码集合
		if(Constant.TIME_TYPE_M.equals(queryDmaZoneLossListDTO.getTimeType())) {
			baseCodes.add("DMMNOCM"); //MDA分区月用户数指标编码
			baseCodes.add("DMMFTPL"); //MDA分区月管长数指标编码
			wbCodes.add("DMMFWSSITDF"); //MDA分区月供水量指标编码
			wbCodes.add("DMMBC"); //MDA分区月计费用水量指标编码
			wbCodes.add("DMMWL"); //MDA分区月漏损量指标编码
			zoneCodes.add("DMMLCA"); //MDA分区月单位户数漏损量
			zoneCodes.add("DMMLPL"); //MDA分区月单位管长漏损量
			zoneCodes.add("DMMWLR"); //MDA分区月漏损率
			zoneCodes.add("DMMMNF"); //MDA分区月MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("DMMMNFTDF"); //MDA分区月MNF/TDF
		}else {
			baseCodes.add("DMYNOCM");//MDA分区年用户数指标编码
			baseCodes.add("DMYFTPL");//MDA分区年管长数指标编码
			wbCodes.add("DMYFWSSITDF"); //MDA分区年供水量指标编码
			wbCodes.add("DMYBC"); //MDA分区年计费用水量指标编码
			wbCodes.add("DMYWL"); //MDA分区年漏损量指标编码
			zoneCodes.add("DMYLCA"); //MDA分区月单位户数漏损量
			zoneCodes.add("DMYLPL"); //MDA分区月单位管长漏损量
			zoneCodes.add("DMYWLR"); //MDA分区月漏损率
			zoneCodes.add("DMYMNF"); //MDA分区月MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("DMYMNFTDF"); //MDA分区月MNF/TDF
		}
		//查询基础指标数据
		indicatorDTO.setCodes(baseCodes);
		List<IndicatorVO> baseIndics = mapper.queryBaseIndicData(indicatorDTO);
		
		//查询水平衡指标数据
		indicatorDTO.setCodes(wbCodes);
		List<IndicatorVO> wbBaseIndics = mapper.queryWBBaseIndicData(indicatorDTO);
		
		//查询分区指标数据
		indicatorDTO.setCodes(zoneCodes);
		List<IndicatorVO> zoneIndics = mapper.queryZoneLossIndicData(indicatorDTO);
		
		
		List<Integer> timeList = TimeUtil.getMonthsList(indicatorDTO.getStartTime(),indicatorDTO.getEndTime());
		int timeNum = timeList.size();
		
		List<DmaZoneLossListVO> dataList = new ArrayList<>();
		for (int i = 0;i<zoneInfos.size();i++) {
			if(i < (queryDmaZoneLossListDTO.getPage()-1)*queryDmaZoneLossListDTO.getPageCount()) continue;
			if(dataList.size() >= queryDmaZoneLossListDTO.getPageCount()) break;
			DmaZoneLossListVO dmaZoneLossListVO = new DmaZoneLossListVO();
			dmaZoneLossListVO.setZoneNo(zoneInfos.get(i).getZoneNo());
			dmaZoneLossListVO.setZoneName(zoneInfos.get(i).getZoneName());
			dmaZoneLossListVO.setZoneRank(Constant.RANK_S);
			dmaZoneLossListVO.setpZoneNo(zoneInfos.get(i).getpZoneNo());
			dmaZoneLossListVO.setpZoneName(zoneInfos.get(i).getpZoneName());
			dmaZoneLossListVO.setAddress(zoneInfos.get(i).getAddress());
			//TODO 分区警报查询
			dmaZoneLossListVO.setAlarmStatus(0);
			
			int meterNum = 0;
			double pipeLength = 0;
			double flow = 0;
			double useFlow = 0;
			double lossFlow = 0;
			double perUserLossFlow = 0; //DMA分区月单位户数漏损量
			double perLengthLossFlow = 0; //DMA分区月单位管长漏损量
			double lossRate = 0; //DMA分区月漏损率
			double mnf = 0; //DMA分区月MNF
			double mnfTime = 0; //DMA分区月MNF TIME
			double mnfTdf = 0; //DMA分区月MNF/TDF
			if(Constant.TIME_TYPE_M.equals(queryDmaZoneLossListDTO.getTimeType())) {
				for(IndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "DMMNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "DMMFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMMFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMMBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMMWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMMLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMMLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMMWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMMMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMMMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}else {
				for(IndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "DMYNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "DMYFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMYFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMYBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMYWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMYLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMYLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMYWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMYMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "DMYMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}
			dmaZoneLossListVO.setMeterNum(meterNum/timeNum);
			dmaZoneLossListVO.setPipeLength(pipeLength/timeNum);
			dmaZoneLossListVO.setFlow(flow);
			dmaZoneLossListVO.setUseFlow(useFlow);
			dmaZoneLossListVO.setLossFlow(lossFlow);
			dmaZoneLossListVO.setPerUserLossFlow(perUserLossFlow/timeNum);
			dmaZoneLossListVO.setPerLengthLossFlow(perLengthLossFlow/timeNum);
			dmaZoneLossListVO.setLossRate(lossRate/timeNum);
			dmaZoneLossListVO.setMnf(mnf);
			dmaZoneLossListVO.setMnfTdf(mnfTdf/timeNum);
			dataList.add(dmaZoneLossListVO);
		}
		PageListVO<List<DmaZoneLossListVO>> result = new PageListVO<>();
		result.setDataList(dataList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryDmaZoneLossListDTO.getPage(), queryDmaZoneLossListDTO.getPageCount(), zoneInfos.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("queryZoneHstData")
	@Override
	public ZoneHstDataVO queryZoneHstData(SessionFactory factory, QueryZoneHstDataDTO queryZoneHstDataDTO) {
		IndicatorMapper indicMapper = factory.getMapper(IndicatorMapper.class);
		ZoneLossAnaMapper zoneLossMapper = factory.getMapper(ZoneLossAnaMapper.class);
		List<Integer> timeList = TimeUtil.getMonthsList(queryZoneHstDataDTO.getStartTime(),queryZoneHstDataDTO.getEndTime());
		String code = queryZoneHstDataDTO.getCodes();
		IndicatorDTO indicatorDTO = new IndicatorDTO();
		indicatorDTO.setStartTime(queryZoneHstDataDTO.getStartTime());
		indicatorDTO.setEndTime(queryZoneHstDataDTO.getEndTime());
		indicatorDTO.setTimeType(queryZoneHstDataDTO.getTimeType());
		List<String> zoneNos = new ArrayList<>();
		zoneNos.add(queryZoneHstDataDTO.getZoneNo());
		indicatorDTO.setZoneCodes(zoneNos);
		List<String> codes = new ArrayList<>();
		codes.add(code);
		indicatorDTO.setCodes(codes);
		IndicatorInfo indicatorInfo = zoneLossMapper.queryIndicLevel2Info(code);
		if(indicatorInfo == null) return null;
		List<IndicatorVO> indicLists = new ArrayList<>();
		if(indicatorInfo.getLevel2() == 0) {
			//基础指标
			indicLists = indicMapper.queryBaseIndicData(indicatorDTO);
			
		}else if(indicatorInfo.getLevel2() == 3) {
			//水平衡指标
			indicLists = indicMapper.queryWBBaseIndicData(indicatorDTO);
		}else if(indicatorInfo.getLevel2() == 4) {
			//分区漏损
			indicLists = indicMapper.queryZoneLossIndicData(indicatorDTO);
		}
		ZoneHstDataVO zoneHstDataVO = new ZoneHstDataVO();
		zoneHstDataVO.setZoneNo(queryZoneHstDataDTO.getZoneNo());
		List<ZoneHstIndicData> lists = new ArrayList<>();
		ZoneHstIndicData zoneHstIndicData = new ZoneHstIndicData();
		zoneHstIndicData.setCode(code);
		zoneHstIndicData.setName(indicatorInfo.getName());
		zoneHstIndicData.setDate(timeList);
		List<Double> value = new ArrayList<>();
		for(Integer  time : timeList) {
			boolean valueFlag = false;
			for (IndicatorVO wbIndicatorVO : indicLists) {
				if(time.equals(wbIndicatorVO.getTimeId())) {
					valueFlag = true; 
					value.add(wbIndicatorVO.getValue());
				}
			}	
			if(!valueFlag) value.add(null);
		}
		zoneHstIndicData.setValue(value);
		lists.add(zoneHstIndicData);
		zoneHstDataVO.setIndicatorData(lists);
		return zoneHstDataVO;
	}
}
