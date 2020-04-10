package com.koron.inwlms.service.zoneLoss.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryDmaZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryFZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QuerySZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneHstDataDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneInfoDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WBIndicatorDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneHstDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneHstIndicData;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.zoneLoss.DmaZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.FZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.IndicatorInfo;
import com.koron.inwlms.bean.VO.zoneLoss.PageDmaZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageFZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageSZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.SZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WBIndicatorVO;
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
	public PageFZoneLossListVO queryFZoneLossList(SessionFactory factory,
			QueryFZoneLossListDTO queryFZoneLossListDTO) {
		//查询一级分区信息
		GisZoneServiceImpl gzsImpl = new GisZoneServiceImpl();
		List<ZoneInfo> zoneInfos = new ArrayList<>();
		List<ZoneInfo> zoneInfoTemp = gzsImpl.queryZoneNosByRank(factory, Constant.RANK_F);
		if(StringUtil.isEmpty(queryFZoneLossListDTO.getZoneNo())) {
			zoneInfos = zoneInfoTemp;
		}else {
			for(ZoneInfo zoneInfo : zoneInfoTemp) {
				if(zoneInfo.getZoneNo().equals(queryFZoneLossListDTO.getZoneNo())) {
					zoneInfos.add(zoneInfo);
					break;
				}
			}
		}
		ZoneLossAnaMapper mapper = factory.getMapper(ZoneLossAnaMapper.class);
		WBIndicatorDTO wBIndicatorDTO = new WBIndicatorDTO();
		wBIndicatorDTO.setStartTime(queryFZoneLossListDTO.getStartTime());
		wBIndicatorDTO.setEndTime(queryFZoneLossListDTO.getEndTime());
		wBIndicatorDTO.setZoneInfos(zoneInfos);
		wBIndicatorDTO.setTimeType(queryFZoneLossListDTO.getTimeType());
		List<String> baseCodes = new ArrayList<>(); //基础指标编码集合
		List<String> wbCodes = new ArrayList<>(); //水平衡基础指标编码集合
//		List<String> zoneCodes = new ArrayList<>();  //分区漏损指标编码集合
		if(Constant.TIME_TYPE_M.equals(queryFZoneLossListDTO.getTimeType())) {
			baseCodes.add("FLMNOCM"); //一级分区月用户数指标编码
			baseCodes.add("FLMFTPL"); //一级分区月管长数指标编码
			wbCodes.add("FLMFWSSITDF"); //一级分区月供水量指标编码
			wbCodes.add("FLMBC"); //一级分区月计费用水量指标编码
			wbCodes.add("FLMWL"); //一级分区月漏损量指标编码
		}else {
			baseCodes.add("FLYNOCM");//一级分区年用户数指标编码
			baseCodes.add("FLYFTPL");//一级分区年管长数指标编码
			wbCodes.add("FLYFWSSITDF"); //一级分区年供水量指标编码
			wbCodes.add("FLYBC"); //一级分区年计费用水量指标编码
			wbCodes.add("FLYWL"); //一级分区年漏损量指标编码
		}
		//查询基础指标数据
		wBIndicatorDTO.setCodes(baseCodes);
		List<WBIndicatorVO> baseIndics = mapper.queryBaseIndicData(wBIndicatorDTO);
		
		//查询水平衡指标数据
		wBIndicatorDTO.setCodes(wbCodes);
		List<WBIndicatorVO> wbBaseIndics = mapper.queryWBBaseIndicData(wBIndicatorDTO);
		
		//查询分区指标数据
//		wBIndicatorDTO.setCodes(zoneCodes);
//		mapper.queryZoneLossIndicData(wBIndicatorDTO);
		
		List<Integer> timeList = TimeUtil.getMonthsList(wBIndicatorDTO.getStartTime(),wBIndicatorDTO.getEndTime());
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
			if(Constant.TIME_TYPE_M.equals(queryFZoneLossListDTO.getTimeType())) {
				for(WBIndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLMNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLMFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(WBIndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLMFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLMBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLMWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
			}else {
				for(WBIndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLYNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLYFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(WBIndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLYFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLYBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLYWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
			}
			fZoneLossListVO.setMeterNum(meterNum/timeNum);
			fZoneLossListVO.setPipeLength(pipeLength/timeNum);
			fZoneLossListVO.setFlow(flow);
			fZoneLossListVO.setUseFlow(useFlow);
			fZoneLossListVO.setLossFlow(lossFlow);
			dataList.add(fZoneLossListVO);
			
		}
		PageFZoneLossListVO result = new PageFZoneLossListVO();
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
	public PageSZoneLossListVO querySZoneLossList(SessionFactory factory, QuerySZoneLossListDTO querySZoneLossListDTO) {
		//查询二级分区信息
		GisZoneServiceImpl gzsImpl = new GisZoneServiceImpl();
		List<ZoneInfo> zoneInfos = new ArrayList<>();
		List<ZoneInfo> zoneInfoTemp = gzsImpl.queryZoneNosByRank(factory, Constant.RANK_S);
		if(StringUtil.isEmpty(querySZoneLossListDTO.getZoneNo())) {
			zoneInfos = zoneInfoTemp;
		}else {
			for(ZoneInfo zoneInfo : zoneInfoTemp) {
				if(zoneInfo.getZoneNo().equals(querySZoneLossListDTO.getZoneNo())) {
					zoneInfos.add(zoneInfo);
					break;
				}
			}
		}
		ZoneLossAnaMapper mapper = factory.getMapper(ZoneLossAnaMapper.class);
		WBIndicatorDTO wBIndicatorDTO = new WBIndicatorDTO();
		wBIndicatorDTO.setStartTime(querySZoneLossListDTO.getStartTime());
		wBIndicatorDTO.setEndTime(querySZoneLossListDTO.getEndTime());
		wBIndicatorDTO.setZoneInfos(zoneInfos);
		wBIndicatorDTO.setTimeType(querySZoneLossListDTO.getTimeType());
		List<String> baseCodes = new ArrayList<>(); //基础指标编码集合
		List<String> wbCodes = new ArrayList<>(); //水平衡基础指标编码集合
//		List<String> zoneCodes = new ArrayList<>();  //分区漏损指标编码集合
		if(Constant.TIME_TYPE_M.equals(querySZoneLossListDTO.getTimeType())) {
			baseCodes.add("SLMNOCM"); //一级分区月用户数指标编码
			baseCodes.add("SLMFTPL"); //一级分区月管长数指标编码
			wbCodes.add("SLMFWSSITDF"); //一级分区月供水量指标编码
			wbCodes.add("SLMBC"); //一级分区月计费用水量指标编码
			wbCodes.add("SLMWL"); //一级分区月漏损量指标编码
		}else {
			baseCodes.add("SLYNOCM");//一级分区年用户数指标编码
			baseCodes.add("SLYFTPL");//一级分区年管长数指标编码
			wbCodes.add("SLYFWSSITDF"); //一级分区年供水量指标编码
			wbCodes.add("SLYBC"); //一级分区年计费用水量指标编码
			wbCodes.add("SLYWL"); //一级分区年漏损量指标编码
		}
		//查询基础指标数据
		wBIndicatorDTO.setCodes(baseCodes);
		List<WBIndicatorVO> baseIndics = mapper.queryBaseIndicData(wBIndicatorDTO);
		
		//查询水平衡指标数据
		wBIndicatorDTO.setCodes(wbCodes);
		List<WBIndicatorVO> wbBaseIndics = mapper.queryWBBaseIndicData(wBIndicatorDTO);
		
		//查询分区指标数据
//		wBIndicatorDTO.setCodes(zoneCodes);
//		mapper.queryZoneLossIndicData(wBIndicatorDTO);
		
		List<Integer> timeList = TimeUtil.getMonthsList(wBIndicatorDTO.getStartTime(),wBIndicatorDTO.getEndTime());
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
			if(Constant.TIME_TYPE_M.equals(querySZoneLossListDTO.getTimeType())) {
				for(WBIndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "SLMNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "SLMFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(WBIndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLMFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLMBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLMWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
			}else {
				for(WBIndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "SLYNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "SLYFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(WBIndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLYFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLYBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "SLYWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
			}
			sZoneLossListVO.setMeterNum(meterNum/timeNum);
			sZoneLossListVO.setPipeLength(pipeLength/timeNum);
			sZoneLossListVO.setFlow(flow);
			sZoneLossListVO.setUseFlow(useFlow);
			sZoneLossListVO.setLossFlow(lossFlow);
			dataList.add(sZoneLossListVO);
			
		}
		PageSZoneLossListVO result = new PageSZoneLossListVO();
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
	public PageDmaZoneLossListVO queryDmaZoneLossList(SessionFactory factory,
			QueryDmaZoneLossListDTO queryDmaZoneLossListDTO) {
		//查询dma分区信息
		GisZoneServiceImpl gzsImpl = new GisZoneServiceImpl();
		List<ZoneInfo> zoneInfos = new ArrayList<>();
		List<ZoneInfo> zoneInfoTemp = gzsImpl.queryZoneNosByRank(factory, Constant.RANK_T);
		if(StringUtil.isEmpty(queryDmaZoneLossListDTO.getDmaNo())) {
			zoneInfos = zoneInfoTemp;
		}else {
			for(ZoneInfo zoneInfo : zoneInfoTemp) {
				if(zoneInfo.getZoneNo().equals(queryDmaZoneLossListDTO.getDmaNo())) {
					zoneInfos.add(zoneInfo);
					break;
				}
			}
		}
		ZoneLossAnaMapper mapper = factory.getMapper(ZoneLossAnaMapper.class);
		WBIndicatorDTO wBIndicatorDTO = new WBIndicatorDTO();
		wBIndicatorDTO.setStartTime(queryDmaZoneLossListDTO.getStartTime());
		wBIndicatorDTO.setEndTime(queryDmaZoneLossListDTO.getEndTime());
		wBIndicatorDTO.setZoneInfos(zoneInfos);
		wBIndicatorDTO.setTimeType(queryDmaZoneLossListDTO.getTimeType());
		List<String> baseCodes = new ArrayList<>(); //基础指标编码集合
		List<String> wbCodes = new ArrayList<>(); //水平衡基础指标编码集合
//		List<String> zoneCodes = new ArrayList<>();  //分区漏损指标编码集合
		if(Constant.TIME_TYPE_M.equals(queryDmaZoneLossListDTO.getTimeType())) {
			baseCodes.add("DMMNOCM"); //一级分区月用户数指标编码
			baseCodes.add("DMMFTPL"); //一级分区月管长数指标编码
			wbCodes.add("DMMFWSSITDF"); //一级分区月供水量指标编码
			wbCodes.add("DMMBC"); //一级分区月计费用水量指标编码
			wbCodes.add("DMMWL"); //一级分区月漏损量指标编码
		}else {
			baseCodes.add("DMYNOCM");//一级分区年用户数指标编码
			baseCodes.add("DMYFTPL");//一级分区年管长数指标编码
			wbCodes.add("DMYFWSSITDF"); //一级分区年供水量指标编码
			wbCodes.add("DMYBC"); //一级分区年计费用水量指标编码
			wbCodes.add("DMYWL"); //一级分区年漏损量指标编码
		}
		//查询基础指标数据
		wBIndicatorDTO.setCodes(baseCodes);
		List<WBIndicatorVO> baseIndics = mapper.queryBaseIndicData(wBIndicatorDTO);
		
		//查询水平衡指标数据
		wBIndicatorDTO.setCodes(wbCodes);
		List<WBIndicatorVO> wbBaseIndics = mapper.queryWBBaseIndicData(wBIndicatorDTO);
		
		//查询分区指标数据
//		wBIndicatorDTO.setCodes(zoneCodes);
//		mapper.queryZoneLossIndicData(wBIndicatorDTO);
		
		List<Integer> timeList = TimeUtil.getMonthsList(wBIndicatorDTO.getStartTime(),wBIndicatorDTO.getEndTime());
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
			if(Constant.TIME_TYPE_M.equals(queryDmaZoneLossListDTO.getTimeType())) {
				for(WBIndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "DMMNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "DMMFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(WBIndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMMFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMMBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMMWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
			}else {
				for(WBIndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "DMYNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "DMYFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(WBIndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMYFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMYBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "DMYWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
			}
			dmaZoneLossListVO.setMeterNum(meterNum/timeNum);
			dmaZoneLossListVO.setPipeLength(pipeLength/timeNum);
			dmaZoneLossListVO.setFlow(flow);
			dmaZoneLossListVO.setUseFlow(useFlow);
			dmaZoneLossListVO.setLossFlow(lossFlow);
			dataList.add(dmaZoneLossListVO);
		}
		PageDmaZoneLossListVO result = new PageDmaZoneLossListVO();
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
		ZoneLossAnaMapper mapper = factory.getMapper(ZoneLossAnaMapper.class);
		List<Integer> timeList = TimeUtil.getMonthsList(queryZoneHstDataDTO.getStartTime(),queryZoneHstDataDTO.getEndTime());
		String code = queryZoneHstDataDTO.getCodes();
		WBIndicatorDTO wBIndicatorDTO = new WBIndicatorDTO();
		wBIndicatorDTO.setStartTime(queryZoneHstDataDTO.getStartTime());
		wBIndicatorDTO.setEndTime(queryZoneHstDataDTO.getEndTime());
		wBIndicatorDTO.setTimeType(queryZoneHstDataDTO.getTimeType());
		List<ZoneInfo> zoneInfos = new ArrayList<>();
		ZoneInfo zoneInfo = new ZoneInfo();
		zoneInfo.setZoneNo(queryZoneHstDataDTO.getZoneNo());
		zoneInfos.add(zoneInfo);
		wBIndicatorDTO.setZoneInfos(zoneInfos);
		List<String> codes = new ArrayList<>();
		codes.add(code);
		wBIndicatorDTO.setCodes(codes);
		IndicatorInfo indicatorInfo = mapper.queryIndicLevel2Info(code);
		if(indicatorInfo == null) return null;
		List<WBIndicatorVO> indicLists = new ArrayList<>();
		if(indicatorInfo.getLevel2() == 0) {
			//基础指标
			indicLists = mapper.queryBaseIndicData(wBIndicatorDTO);
			
		}else if(indicatorInfo.getLevel2() == 3) {
			//水平衡指标
			indicLists = mapper.queryWBBaseIndicData(wBIndicatorDTO);
		}else if(indicatorInfo.getLevel2() == 4) {
			//分区漏损
//			indicLists = mapper.queryZoneLossIndicData(wBIndicatorDTO);
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
			for (WBIndicatorVO wbIndicatorVO : indicLists) {
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
