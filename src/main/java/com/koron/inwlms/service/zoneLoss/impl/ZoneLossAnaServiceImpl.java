package com.koron.inwlms.service.zoneLoss.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryDmaZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryFZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryLeakListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QuerySZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneHstDataDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneIndicatorListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.ZoneThematicValueDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneHstDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneHstIndicData;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.zoneLoss.BurstLeakAnalysisVO;
import com.koron.inwlms.bean.VO.zoneLoss.DmaZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.FZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.IndicatorInfo;
import com.koron.inwlms.bean.VO.zoneLoss.LeakDetailsVO;
import com.koron.inwlms.bean.VO.zoneLoss.SZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneIndicatorDicVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.zoneLoss.WaterBalanceAnaMapper;
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
		List<ZoneInfo> zoneInfos = gzsImpl.queryZoneNosByRank(factory, Constant.RANK_F,queryFZoneLossListDTO.getZoneNo());
		//判空，及判断分页
		if(zoneInfos == null || zoneInfos.size()<(queryFZoneLossListDTO.getPage()-1)*queryFZoneLossListDTO.getPageCount()) return null;
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
		if(Constant.TIME_TYPE_D.equals(queryFZoneLossListDTO.getTimeType())){
			baseCodes.add("FLMNOCM");//一级分区月用户数指标编码
			baseCodes.add("FLMFTPL");//一级分区月管长数指标编码
			wbCodes.add("FLDFWSSITDF"); //一级分区日供水量指标编码
			wbCodes.add("FLDBC"); //一级分区日计费用水量指标编码
			wbCodes.add("FLDWL"); //一级分区日漏损量指标编码
			wbCodes.add("FLDNRW"); //一级分区日产销差指标编码
			zoneCodes.add("FLDMNFT"); //一级分区日MNF所在时刻
			zoneCodes.add("FLDLCA"); //一级分区日单位户数漏损量
			zoneCodes.add("FLDLPL"); //一级分区日单位管长漏损量
			zoneCodes.add("FLDWLR"); //一级分区日漏损率
			zoneCodes.add("FLDMNF"); //一级分区日MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("FLYMNFTDF"); //一级分区年MNF/TDF
		}else if(Constant.TIME_TYPE_M.equals(queryFZoneLossListDTO.getTimeType())) {
			baseCodes.add("FLMNOCM"); //一级分区月用户数指标编码
			baseCodes.add("FLMFTPL"); //一级分区月管长数指标编码
			wbCodes.add("FLMFWSSITDF"); //一级分区月供水量指标编码
			wbCodes.add("FLMBC"); //一级分区月计费用水量指标编码
			wbCodes.add("FLMWL"); //一级分区月漏损量指标编码
			wbCodes.add("FLMNRW"); //一级分区月产销差指标编码
			zoneCodes.add("FLMLCA"); //一级分区月单位户数漏损量
			zoneCodes.add("FLMLPL"); //一级分区月单位管长漏损量
			zoneCodes.add("FLMWLR"); //一级分区月漏损率
			zoneCodes.add("FLMMNF"); //一级分区月MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("FLMMNFTDF"); //一级分区月MNF/TDF
			zoneCodes.add("FLMNRR"); //一级分区月产销差指标编码
			
		}else if(Constant.TIME_TYPE_Y.equals(queryFZoneLossListDTO.getTimeType())){
			baseCodes.add("FLYNOCM");//一级分区年用户数指标编码
			baseCodes.add("FLYFTPL");//一级分区年管长数指标编码
			wbCodes.add("FLYFWSSITDF"); //一级分区年供水量指标编码
			wbCodes.add("FLYBC"); //一级分区年计费用水量指标编码
			wbCodes.add("FLYWL"); //一级分区年漏损量指标编码
			wbCodes.add("FLYNRW"); //一级分区年产销差指标编码
			zoneCodes.add("FLYLCA"); //一级分区年单位户数漏损量
			zoneCodes.add("FLYLPL"); //一级分区年单位管长漏损量
			zoneCodes.add("FLYWLR"); //一级分区年漏损率
			zoneCodes.add("FLYMNF"); //一级分区年MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("FLYMNFTDF"); //一级分区年MNF/TDF
			zoneCodes.add("FLYNRR"); //一级分区日产销差指标编码
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
		
		List<Integer> timeList;
		int timeNum = 0;
		try {
			timeList = TimeUtil.getTimeList(indicatorDTO.getStartTime(),indicatorDTO.getEndTime());
			timeNum = timeList.size();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<FZoneLossListVO> dataList = new ArrayList<>();
		
		int inputSum = 0; //记录写入返回集合的条数
		for (int i = 0;i<zoneInfos.size();i++) {
			if(inputSum < (queryFZoneLossListDTO.getPage()-1)*queryFZoneLossListDTO.getPageCount()) continue;
			if(dataList.size() >= queryFZoneLossListDTO.getPageCount()) break;
			FZoneLossListVO fZoneLossListVO = new FZoneLossListVO();
			fZoneLossListVO.setZoneNo(zoneInfos.get(i).getZoneNo());
			fZoneLossListVO.setZoneName(zoneInfos.get(i).getZoneName());
			fZoneLossListVO.setZoneRank(Constant.RANK_F);
			fZoneLossListVO.setpZoneNo(zoneInfos.get(i).getpZoneNo());
			fZoneLossListVO.setpZoneName(zoneInfos.get(i).getpZoneName());
			fZoneLossListVO.setAddress(zoneInfos.get(i).getAddress());
			fZoneLossListVO.setCreateTime(zoneInfos.get(i).getCreateTime());
			//TODO 分区警报查询
			fZoneLossListVO.setAlarmStatus(0);
			
			int meterNum = 0;
			double pipeLength = 0;
			double flow = 0;
			double useFlow = 0;
			double lossFlow = 0;
			double nrw = 0; //产销差
			double perUserLossFlow = 0; //一级分区月单位户数漏损量
			double perLengthLossFlow = 0; //一级分区月单位管长漏损量
			double lossRate = 0; //一级分区月漏损率
			double mnf = 0; //一级分区月MNF
			double mnfTime = 0; //一级分区月MNF TIME
			double mnfTdf = 0; //一级分区月MNF/TDF
			DecimalFormat df = new DecimalFormat("#.0000");
			if(Constant.TIME_TYPE_D.equals(queryFZoneLossListDTO.getTimeType())){
				for(IndicatorVO baseIndic : baseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLMNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "FLMFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLDFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLDBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLDWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLDNRW".equals(wbBaseIndic.getCode())) {
						nrw += wbBaseIndic.getValue();
					}
				}
				
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLDLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLDLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLDWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLDMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLDMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}else if(zoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "FLDMNFT".equals(zoneIndic.getCode())) {
						mnfTime += zoneIndic.getValue();
					}
				}
			}else if(Constant.TIME_TYPE_M.equals(queryFZoneLossListDTO.getTimeType())) {
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
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLMNRW".equals(wbBaseIndic.getCode())) {
						nrw += wbBaseIndic.getValue();
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
			}else if(Constant.TIME_TYPE_Y.equals(queryFZoneLossListDTO.getTimeType())){
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
					}else if(zoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "FLYNRW".equals(wbBaseIndic.getCode())) {
						nrw += wbBaseIndic.getValue();
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
			//判断产销差，漏损量范围
			if(StringUtil.isNotEmpty(queryFZoneLossListDTO.getMinNrw())) {
				if(nrw < Double.parseDouble(queryFZoneLossListDTO.getMinNrw())) continue;
			}
			if(StringUtil.isNotEmpty(queryFZoneLossListDTO.getMaxNrw())) {
				if(nrw > Double.parseDouble(queryFZoneLossListDTO.getMaxNrw())) continue;
			}
			if(StringUtil.isNotEmpty(queryFZoneLossListDTO.getMinWl())) {
				if(lossFlow < Double.parseDouble(queryFZoneLossListDTO.getMinWl())) continue;
			}
			if(StringUtil.isNotEmpty(queryFZoneLossListDTO.getMaxWl())) {
				if(lossFlow > Double.parseDouble(queryFZoneLossListDTO.getMaxWl())) continue;
			}
			
			fZoneLossListVO.setMeterNum(meterNum/timeNum);
			fZoneLossListVO.setPipeLength(Double.parseDouble(df.format(pipeLength/timeNum)));
			fZoneLossListVO.setFlow(flow);
			fZoneLossListVO.setUseFlow(useFlow);
			fZoneLossListVO.setLossFlow(lossFlow);
			fZoneLossListVO.setNrw(Double.parseDouble(df.format(nrw)));
			fZoneLossListVO.setPerUserLossFlow(Double.parseDouble(df.format(perUserLossFlow/timeNum)));
			fZoneLossListVO.setPerLengthLossFlow(Double.parseDouble(df.format(perLengthLossFlow/timeNum)));
			fZoneLossListVO.setLossRate(Double.parseDouble(df.format(lossRate/timeNum)));
			fZoneLossListVO.setMnf(Double.parseDouble(df.format(mnf)));
			fZoneLossListVO.setMnfTdf(Double.parseDouble(df.format(mnfTdf/timeNum)));
			fZoneLossListVO.setMnfTime((int)Math.round(mnfTime/timeNum));
			dataList.add(fZoneLossListVO);
			inputSum++;
			
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
		ZoneLossAnaMapper zlMapper = factory.getMapper(ZoneLossAnaMapper.class);
		GisZoneServiceImpl gzsImpl = new GisZoneServiceImpl();
		List<String> zoneNos = new ArrayList<>();
		List<ZoneInfo> zoneInfos = gzsImpl.queryZoneNosByRank(factory, Constant.RANK_S,querySZoneLossListDTO.getZoneNo());
		for (ZoneInfo zoneInfo : zoneInfos) {
			List<String> pLists = zlMapper.queryParentsCodeByNo(zoneInfo.getZoneNo());
			if(pLists != null && pLists.size() >1) {
				String pZoneNo =  pLists.get(pLists.size()-2);
				String pZoneName = gzsImpl.queryZoneNameByNo(factory, pZoneNo);
				zoneInfo.setpZoneNo(pZoneNo);
				zoneInfo.setpZoneName(pZoneName);
			}
		}
		//判空，及判断分区
		if(zoneInfos == null || zoneInfos.size()<(querySZoneLossListDTO.getPage()-1)*querySZoneLossListDTO.getPageCount()) return null;
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
		
		List<Integer> timeList;
		int timeNum = 0;
		try {
			timeList = TimeUtil.getTimeList(indicatorDTO.getStartTime(),indicatorDTO.getEndTime());
			timeNum = timeList.size();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
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
		List<ZoneInfo> zoneInfos = gzsImpl.queryZoneNosByRank(factory, Constant.RANK_T,queryDmaZoneLossListDTO.getZoneNo());
		//判空，及判断分页
		if(zoneInfos == null || zoneInfos.size()<(queryDmaZoneLossListDTO.getPage()-1)*queryDmaZoneLossListDTO.getPageCount()) return null;
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
		
		
		List<Integer> timeList;
		int timeNum = 0;
		try {
			timeList = TimeUtil.getTimeList(indicatorDTO.getStartTime(),indicatorDTO.getEndTime());
			timeNum = timeList.size();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
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
		List<Integer> timeList = new ArrayList<>();
		try {
			timeList = TimeUtil.getTimeList(queryZoneHstDataDTO.getStartTime(),queryZoneHstDataDTO.getEndTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
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

	@TaskAnnotation("queryZoneIndicatorList")
	@Override
	public PageListVO queryZoneIndicatorList(SessionFactory factory,
			QueryZoneIndicatorListDTO queryZoneIndicatorListDTO) {
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		Integer zoneType = queryZoneIndicatorListDTO.getZoneType();
		List<String> zoneNos = new ArrayList<>();
		List<String> codes = new ArrayList<>();
		//根据分区类型获取所有分区编号
		GisZoneServiceImpl gisZoneServiceImpl = new GisZoneServiceImpl();
		List<ZoneInfo> zoneInfos = gisZoneServiceImpl.queryZoneNosByRank(factory, zoneType,"");
		//判空，及判断分页
		if(zoneInfos == null || zoneInfos.size()<(queryZoneIndicatorListDTO.getPage()-1)*queryZoneIndicatorListDTO.getPageCount()) return null;
		for (ZoneInfo zoneInfo : zoneInfos) {
			zoneNos.add(zoneInfo.getZoneNo());
		}
		//获取所有指标
		List<ZoneIndicatorDicVO> zidVOList = queryZoneIndicatorDic(factory,zoneType);
		for (ZoneIndicatorDicVO zoneIndicatorDicVO : zidVOList) {
			if(queryZoneIndicatorListDTO.getTimeType().equals(zoneIndicatorDicVO.getTimeType())) {
				codes.add(zoneIndicatorDicVO.getItemCode());
			}
		}
		List<IndicatorVO> lists = new ArrayList<>();
		IndicatorDTO indicatorDTO = new IndicatorDTO();
		indicatorDTO.setCodes(codes);
		indicatorDTO.setZoneCodes(zoneNos);
		indicatorDTO.setStartTime(queryZoneIndicatorListDTO.getStartTime());
		indicatorDTO.setEndTime(queryZoneIndicatorListDTO.getEndTime());
		indicatorDTO.setTimeType(queryZoneIndicatorListDTO.getTimeType());
		//基础指标
		List<IndicatorVO> queryBaseIndicData = mapper.queryBaseIndicData(indicatorDTO);
		//水平衡指标
		List<IndicatorVO> queryWBBaseIndicData = mapper.queryWBBaseIndicData(indicatorDTO);
		//分区漏损指标
		List<IndicatorVO> queryZoneLossIndicData = mapper.queryZoneLossIndicData(indicatorDTO);
		//爆管/渗漏指标
		List<IndicatorVO> queryLeakIndicData = mapper.queryLeakIndicData(indicatorDTO);
		lists.addAll(queryBaseIndicData);
		lists.addAll(queryWBBaseIndicData);
		lists.addAll(queryZoneLossIndicData);
		lists.addAll(queryLeakIndicData);
		for (IndicatorVO indicatorVO : queryLeakIndicData) {
			indicatorVO.setCode(indicatorVO.getCode().substring(3));
		}
		List<Map<Object,Object>> mapLists = new ArrayList<>();
		List<String> zoneNoList = new ArrayList<>(); //已存储的分区编号
		DecimalFormat df = new DecimalFormat("#.0000");
		for (int i = 0; i<zoneNos.size();i++) {
			if(i < (queryZoneIndicatorListDTO.getPage()-1)*queryZoneIndicatorListDTO.getPageCount()) continue;
			if(mapLists.size() >= queryZoneIndicatorListDTO.getPageCount()) break;
			Map<Object,Object> map = new HashMap<Object, Object>();
			String zoneNo = zoneNos.get(i);
			map.put("zoneNo", zoneNo);
			if(zoneNoList.contains(zoneNo)) continue;
			zoneNoList.add(zoneNo);
			Double values = 0.0; //统计值
			int timeNum = 0;  //参与计算的月份数量
			for (String code : codes) {
				code = code.substring(3);  
				for (IndicatorVO indicatorVO1 : lists) {
					if(zoneNo.equals(indicatorVO1.getZoneNo()) && code.equals(indicatorVO1.getCode())) {
						values += indicatorVO1.getValue();
						timeNum++;
					}
				}
				
				if(code.contains("NOCM") || code.contains("FTPL") || code.contains("DCPL") || code.contains("UCRFW") || code.contains("WLR") || 
						code.contains("MNF") || code.contains("LCA") || code.contains("LPL")) {
					//DMA覆盖率（管长），未计量用水量占比，漏损率，最小夜间流量,扣除大用户的最小夜间流量,单位户数漏损量,单位管长漏损量,计算平均值
					if(timeNum == 0) {
						map.put(code, null);
					} else{
						map.put(code, Double.parseDouble(df.format(values/timeNum)));
					}
					
				}else {
					map.put(code, values);
				}
			}
			mapLists.add(map);
		}
		PageListVO<List<Map<Object,Object>>> result = new PageListVO<>();
		result.setDataList(mapLists);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryZoneIndicatorListDTO.getPage(), queryZoneIndicatorListDTO.getPageCount(), zoneInfos.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
		
	}

	@TaskAnnotation("queryZoneIndicatorDic")
	@Override
	public List<ZoneIndicatorDicVO> queryZoneIndicatorDic(SessionFactory factory, Integer zoneType) {
		List<ZoneIndicatorDicVO> lists = new ArrayList<>();
		if(Constant.RANK_F.equals(zoneType)) {
			//一级分区
			//日指标
			ZoneIndicatorDicVO dZidVO = new ZoneIndicatorDicVO();
			dZidVO.setItemCode("FLDUCRFW");
			dZidVO.setItemName("未计量水量占比");
			dZidVO.setTimeType(2);
			ZoneIndicatorDicVO dZidVO1 = new ZoneIndicatorDicVO();
			dZidVO1.setItemCode("FLDNBFW");
			dZidVO1.setItemName("爆管数");
			dZidVO1.setTimeType(2);
			ZoneIndicatorDicVO dZidVO2 = new ZoneIndicatorDicVO();
			dZidVO2.setItemCode("FLDNPLFW");
			dZidVO2.setItemName("渗漏数");
			dZidVO2.setTimeType(2);
			ZoneIndicatorDicVO dZidVO3 = new ZoneIndicatorDicVO();
			dZidVO3.setItemCode("FLDBRFW");
			dZidVO3.setItemName("爆管率");
			dZidVO3.setTimeType(2);
			ZoneIndicatorDicVO dZidVO4 = new ZoneIndicatorDicVO();
			dZidVO4.setItemCode("FLDWLR");
			dZidVO4.setItemName("漏损率");
			dZidVO4.setTimeType(2);
			ZoneIndicatorDicVO dZidVO5 = new ZoneIndicatorDicVO();
			dZidVO5.setItemCode("FLDMNF");
			dZidVO5.setItemName("最小夜间流量");
			dZidVO5.setTimeType(2);
			ZoneIndicatorDicVO dZidVO6 = new ZoneIndicatorDicVO();
			dZidVO6.setItemCode("FLDMNFLF");
			dZidVO6.setItemName("扣除大用户的最小夜间流量");
			dZidVO6.setTimeType(2);
			ZoneIndicatorDicVO dZidVO7 = new ZoneIndicatorDicVO();
			dZidVO7.setItemCode("FLDLCA");
			dZidVO7.setItemName("单位户数漏损量");
			dZidVO7.setTimeType(2);
			ZoneIndicatorDicVO dZidVO8 = new ZoneIndicatorDicVO();
			dZidVO8.setItemCode("FLDLPL");
			dZidVO8.setItemName("单位管长漏损量");
			dZidVO8.setTimeType(2);
			ZoneIndicatorDicVO dZidVO9 = new ZoneIndicatorDicVO();
			dZidVO9.setItemCode("FLMNOCM");
			dZidVO9.setItemName("用户数");
			dZidVO9.setTimeType(2);
			ZoneIndicatorDicVO dZidVO10 = new ZoneIndicatorDicVO();
			dZidVO10.setItemCode("FLMFTPL");
			dZidVO10.setItemName("管长");
			dZidVO10.setTimeType(2);
			//月指标
			
			ZoneIndicatorDicVO mZidVO = new ZoneIndicatorDicVO();
			mZidVO.setItemCode("FLMMC");
			mZidVO.setItemName("客户计量用水量");
			mZidVO.setTimeType(3);
			ZoneIndicatorDicVO mZidVO1 = new ZoneIndicatorDicVO();
			mZidVO1.setItemCode("FLMUCRFW");
			mZidVO1.setItemName("未计量水量占比");
			mZidVO1.setTimeType(3);
			ZoneIndicatorDicVO mZidVO2 = new ZoneIndicatorDicVO();
			mZidVO2.setItemCode("FLMNRW");
			mZidVO2.setItemName("产销差");
			mZidVO2.setTimeType(3);
			ZoneIndicatorDicVO mZidVO3 = new ZoneIndicatorDicVO();
			mZidVO3.setItemCode("FLMNBFW");
			mZidVO3.setItemName("爆管数");
			mZidVO3.setTimeType(3);
			ZoneIndicatorDicVO mZidVO4 = new ZoneIndicatorDicVO();
			mZidVO4.setItemCode("FLMNPLFW");
			mZidVO4.setItemName("渗漏数");
			mZidVO4.setTimeType(3);
			ZoneIndicatorDicVO mZidVO5 = new ZoneIndicatorDicVO();
			mZidVO5.setItemCode("FLMBRFW");
			mZidVO5.setItemName("爆管率");
			mZidVO5.setTimeType(3);
			ZoneIndicatorDicVO mZidVO6 = new ZoneIndicatorDicVO();
			mZidVO6.setItemCode("FLMWL");
			mZidVO6.setItemName("漏损量");
			mZidVO6.setTimeType(3);
			ZoneIndicatorDicVO mZidVO7 = new ZoneIndicatorDicVO();
			mZidVO7.setItemCode("FLMWLR");
			mZidVO7.setItemName("漏损率");
			mZidVO7.setTimeType(3);
			ZoneIndicatorDicVO mZidVO8 = new ZoneIndicatorDicVO();
			mZidVO8.setItemCode("FLMMNF");
			mZidVO8.setItemName("最小夜间流量");
			mZidVO8.setTimeType(3);
			ZoneIndicatorDicVO mZidVO9 = new ZoneIndicatorDicVO();
			mZidVO9.setItemCode("FLMMNFLF");
			mZidVO9.setItemName("扣除大用户的最小夜间流量");
			mZidVO9.setTimeType(3);
			ZoneIndicatorDicVO mZidVO10 = new ZoneIndicatorDicVO();
			mZidVO10.setItemCode("FLMLCA");
			mZidVO10.setItemName("单位户数漏损量");
			mZidVO10.setTimeType(3);
			ZoneIndicatorDicVO mZidVO11 = new ZoneIndicatorDicVO();
			mZidVO11.setItemCode("FLMLPL");
			mZidVO11.setItemName("单位管长漏损量");
			mZidVO11.setTimeType(3);
			ZoneIndicatorDicVO mZidVO12 = new ZoneIndicatorDicVO();
			mZidVO12.setItemCode("FLMDCPL");
			mZidVO12.setItemName("DMA/PMA面积覆盖率");
			mZidVO12.setTimeType(3);
			ZoneIndicatorDicVO mZidVO13 = new ZoneIndicatorDicVO();
			mZidVO13.setItemCode("FLMNOCM");
			mZidVO13.setItemName("用户数");
			mZidVO13.setTimeType(3);
			ZoneIndicatorDicVO mZidVO14 = new ZoneIndicatorDicVO();
			mZidVO14.setItemCode("FLMFTPL");
			mZidVO14.setItemName("管长");
			mZidVO14.setTimeType(3);
			
			//月指标
			ZoneIndicatorDicVO yZidVO = new ZoneIndicatorDicVO();
			yZidVO.setItemCode("FLYMC");
			yZidVO.setItemName("客户计量用水量");
			yZidVO.setTimeType(4);
			ZoneIndicatorDicVO yZidVO1 = new ZoneIndicatorDicVO();
			yZidVO1.setItemCode("FLYUCRFW");
			yZidVO1.setItemName("未计量水量占比");
			yZidVO1.setTimeType(4);
			ZoneIndicatorDicVO yZidVO2 = new ZoneIndicatorDicVO();
			yZidVO2.setItemCode("FLYNRW");
			yZidVO2.setItemName("产销差");
			yZidVO2.setTimeType(4);
			ZoneIndicatorDicVO yZidVO3 = new ZoneIndicatorDicVO();
			yZidVO3.setItemCode("FLYNBFW");
			yZidVO3.setItemName("爆管数");
			yZidVO3.setTimeType(4);
			ZoneIndicatorDicVO yZidVO4 = new ZoneIndicatorDicVO();
			yZidVO4.setItemCode("FLYNPLFW");
			yZidVO4.setItemName("渗漏数");
			yZidVO4.setTimeType(4);
			ZoneIndicatorDicVO yZidVO5 = new ZoneIndicatorDicVO();
			yZidVO5.setItemCode("FLYBRFW");
			yZidVO5.setItemName("爆管率");
			yZidVO5.setTimeType(4);
			ZoneIndicatorDicVO yZidVO6 = new ZoneIndicatorDicVO();
			yZidVO6.setItemCode("FLYWL");
			yZidVO6.setItemName("漏损量");
			yZidVO6.setTimeType(4);
			ZoneIndicatorDicVO yZidVO7 = new ZoneIndicatorDicVO();
			yZidVO7.setItemCode("FLYWLR");
			yZidVO7.setItemName("漏损率");
			yZidVO7.setTimeType(4);
			ZoneIndicatorDicVO yZidVO8 = new ZoneIndicatorDicVO();
			yZidVO8.setItemCode("FLYMNF");
			yZidVO8.setItemName("最小夜间流量");
			yZidVO8.setTimeType(4);
			ZoneIndicatorDicVO yZidVO9 = new ZoneIndicatorDicVO();
			yZidVO9.setItemCode("FLYMNFLF");
			yZidVO9.setItemName("扣除大用户的最小夜间流量");
			yZidVO9.setTimeType(4);
			ZoneIndicatorDicVO yZidVO10 = new ZoneIndicatorDicVO();
			yZidVO10.setItemCode("FLYLCA");
			yZidVO10.setItemName("单位户数漏损量");
			yZidVO10.setTimeType(4);
			ZoneIndicatorDicVO yZidVO11 = new ZoneIndicatorDicVO();
			yZidVO11.setItemCode("FLYLPL");
			yZidVO11.setItemName("单位管长漏损量");
			yZidVO11.setTimeType(4);
			ZoneIndicatorDicVO yZidVO12 = new ZoneIndicatorDicVO();
			yZidVO12.setItemCode("FLYDCPL");
			yZidVO12.setItemName("DMA/PMA面积覆盖率");
			yZidVO12.setTimeType(4);
			ZoneIndicatorDicVO yZidVO13 = new ZoneIndicatorDicVO();
			yZidVO13.setItemCode("FLYNOCM");
			yZidVO13.setItemName("用户数");
			yZidVO13.setTimeType(4);
			ZoneIndicatorDicVO yZidVO14 = new ZoneIndicatorDicVO();
			yZidVO14.setItemCode("FLYFTPL");
			yZidVO14.setItemName("管长");
			yZidVO14.setTimeType(4);
			lists.add(dZidVO);
			lists.add(dZidVO1);
			lists.add(dZidVO2);
			lists.add(dZidVO3);
			lists.add(dZidVO4);
			lists.add(dZidVO5);
			lists.add(dZidVO6);
			lists.add(dZidVO7);
			lists.add(dZidVO8);
			lists.add(dZidVO9);
			lists.add(dZidVO10);
			lists.add(mZidVO);
			lists.add(mZidVO1);
			lists.add(mZidVO2);
			lists.add(mZidVO3);
			lists.add(mZidVO4);
			lists.add(mZidVO5);
			lists.add(mZidVO6);
			lists.add(mZidVO7);
			lists.add(mZidVO8);
			lists.add(mZidVO9);
			lists.add(mZidVO10);
			lists.add(mZidVO11);
			lists.add(mZidVO12);
			lists.add(mZidVO13);
			lists.add(mZidVO14);
			lists.add(yZidVO);
			lists.add(yZidVO1);
			lists.add(yZidVO2);
			lists.add(yZidVO3);
			lists.add(yZidVO4);
			lists.add(yZidVO5);
			lists.add(yZidVO6);
			lists.add(yZidVO7);
			lists.add(yZidVO8);
			lists.add(yZidVO9);
			lists.add(yZidVO10);
			lists.add(yZidVO11);
			lists.add(yZidVO12);
			lists.add(yZidVO13);
			lists.add(yZidVO14);
		}else if(Constant.RANK_S.equals(zoneType)) {
			//二级分区
			//日指标
			ZoneIndicatorDicVO dZidVO = new ZoneIndicatorDicVO();
			dZidVO.setItemCode("SLDUCRFW");
			dZidVO.setItemName("未计量水量占比");
			dZidVO.setTimeType(2);
			ZoneIndicatorDicVO dZidVO1 = new ZoneIndicatorDicVO();
			dZidVO1.setItemCode("SLDNBFW");
			dZidVO1.setItemName("爆管数");
			dZidVO1.setTimeType(2);
			ZoneIndicatorDicVO dZidVO2 = new ZoneIndicatorDicVO();
			dZidVO2.setItemCode("SLDNPLFW");
			dZidVO2.setItemName("渗漏数");
			dZidVO2.setTimeType(2);
			ZoneIndicatorDicVO dZidVO3 = new ZoneIndicatorDicVO();
			dZidVO3.setItemCode("SLDBRFW");
			dZidVO3.setItemName("爆管率");
			dZidVO3.setTimeType(2);
			ZoneIndicatorDicVO dZidVO4 = new ZoneIndicatorDicVO();
			dZidVO4.setItemCode("SLDWLR");
			dZidVO4.setItemName("漏损率");
			dZidVO4.setTimeType(2);
			ZoneIndicatorDicVO dZidVO5 = new ZoneIndicatorDicVO();
			dZidVO5.setItemCode("SLDMNF");
			dZidVO5.setItemName("最小夜间流量");
			dZidVO5.setTimeType(2);
			ZoneIndicatorDicVO dZidVO6 = new ZoneIndicatorDicVO();
			dZidVO6.setItemCode("SLDMNFLF");
			dZidVO6.setItemName("扣除大用户的最小夜间流量");
			dZidVO6.setTimeType(2);
			ZoneIndicatorDicVO dZidVO7 = new ZoneIndicatorDicVO();
			dZidVO7.setItemCode("SLDLCA");
			dZidVO7.setItemName("单位户数漏损量");
			dZidVO7.setTimeType(2);
			ZoneIndicatorDicVO dZidVO8 = new ZoneIndicatorDicVO();
			dZidVO8.setItemCode("SLDLPL");
			dZidVO8.setItemName("单位管长漏损量");
			dZidVO8.setTimeType(2);
			
			//月指标
			ZoneIndicatorDicVO mZidVO = new ZoneIndicatorDicVO();
			mZidVO.setItemCode("SLMMC");
			mZidVO.setItemName("客户计量用水量");
			mZidVO.setTimeType(3);
			ZoneIndicatorDicVO mZidVO1 = new ZoneIndicatorDicVO();
			mZidVO1.setItemCode("SLMUCRFW");
			mZidVO1.setItemName("未计量水量占比");
			mZidVO1.setTimeType(3);
			ZoneIndicatorDicVO mZidVO2 = new ZoneIndicatorDicVO();
			mZidVO2.setItemCode("SLMNRW");
			mZidVO2.setItemName("产销差");
			mZidVO2.setTimeType(3);
			ZoneIndicatorDicVO mZidVO3 = new ZoneIndicatorDicVO();
			mZidVO3.setItemCode("SLMNBFW");
			mZidVO3.setItemName("爆管数");
			mZidVO3.setTimeType(3);
			ZoneIndicatorDicVO mZidVO4 = new ZoneIndicatorDicVO();
			mZidVO4.setItemCode("SLMNPLFW");
			mZidVO4.setItemName("渗漏数");
			mZidVO4.setTimeType(3);
			ZoneIndicatorDicVO mZidVO5 = new ZoneIndicatorDicVO();
			mZidVO5.setItemCode("SLMBRFW");
			mZidVO5.setItemName("爆管率");
			mZidVO5.setTimeType(3);
			ZoneIndicatorDicVO mZidVO6 = new ZoneIndicatorDicVO();
			mZidVO6.setItemCode("SLMWL");
			mZidVO6.setItemName("漏损量");
			mZidVO6.setTimeType(3);
			ZoneIndicatorDicVO mZidVO7 = new ZoneIndicatorDicVO();
			mZidVO7.setItemCode("SLMWLR");
			mZidVO7.setItemName("漏损率");
			mZidVO7.setTimeType(3);
			ZoneIndicatorDicVO mZidVO8 = new ZoneIndicatorDicVO();
			mZidVO8.setItemCode("SLMMNF");
			mZidVO8.setItemName("最小夜间流量");
			mZidVO8.setTimeType(3);
			ZoneIndicatorDicVO mZidVO9 = new ZoneIndicatorDicVO();
			mZidVO9.setItemCode("SLMMNFLF");
			mZidVO9.setItemName("扣除大用户的最小夜间流量");
			mZidVO9.setTimeType(3);
			ZoneIndicatorDicVO mZidVO10 = new ZoneIndicatorDicVO();
			mZidVO10.setItemCode("SLMLCA");
			mZidVO10.setItemName("单位户数漏损量");
			mZidVO10.setTimeType(3);
			ZoneIndicatorDicVO mZidVO11 = new ZoneIndicatorDicVO();
			mZidVO11.setItemCode("SLMLPL");
			mZidVO11.setItemName("单位管长漏损量");
			mZidVO11.setTimeType(3);
			ZoneIndicatorDicVO mZidVO12 = new ZoneIndicatorDicVO();
			mZidVO12.setItemCode("SLMDCPL");
			mZidVO12.setItemName("DMA/PMA面积覆盖率");
			mZidVO12.setTimeType(3);
			
			//月指标
			ZoneIndicatorDicVO yZidVO = new ZoneIndicatorDicVO();
			yZidVO.setItemCode("SLYMC");
			yZidVO.setItemName("客户计量用水量");
			yZidVO.setTimeType(4);
			ZoneIndicatorDicVO yZidVO1 = new ZoneIndicatorDicVO();
			yZidVO1.setItemCode("SLYUCRFW");
			yZidVO1.setItemName("未计量水量占比");
			yZidVO1.setTimeType(4);
			ZoneIndicatorDicVO yZidVO2 = new ZoneIndicatorDicVO();
			yZidVO2.setItemCode("SLYNRW");
			yZidVO2.setItemName("产销差");
			yZidVO2.setTimeType(4);
			ZoneIndicatorDicVO yZidVO3 = new ZoneIndicatorDicVO();
			yZidVO3.setItemCode("SLYNBFW");
			yZidVO3.setItemName("爆管数");
			yZidVO3.setTimeType(4);
			ZoneIndicatorDicVO yZidVO4 = new ZoneIndicatorDicVO();
			yZidVO4.setItemCode("SLYNPLFW");
			yZidVO4.setItemName("渗漏数");
			yZidVO4.setTimeType(4);
			ZoneIndicatorDicVO yZidVO5 = new ZoneIndicatorDicVO();
			yZidVO5.setItemCode("SLYBRFW");
			yZidVO5.setItemName("爆管率");
			yZidVO5.setTimeType(4);
			ZoneIndicatorDicVO yZidVO6 = new ZoneIndicatorDicVO();
			yZidVO6.setItemCode("SLYWL");
			yZidVO6.setItemName("漏损量");
			yZidVO6.setTimeType(4);
			ZoneIndicatorDicVO yZidVO7 = new ZoneIndicatorDicVO();
			yZidVO7.setItemCode("SLYWLR");
			yZidVO7.setItemName("漏损率");
			yZidVO7.setTimeType(4);
			ZoneIndicatorDicVO yZidVO8 = new ZoneIndicatorDicVO();
			yZidVO8.setItemCode("SLYMNF");
			yZidVO8.setItemName("最小夜间流量");
			yZidVO8.setTimeType(4);
			ZoneIndicatorDicVO yZidVO9 = new ZoneIndicatorDicVO();
			yZidVO9.setItemCode("SLYMNFLF");
			yZidVO9.setItemName("扣除大用户的最小夜间流量");
			yZidVO9.setTimeType(4);
			ZoneIndicatorDicVO yZidVO10 = new ZoneIndicatorDicVO();
			yZidVO10.setItemCode("SLYLCA");
			yZidVO10.setItemName("单位户数漏损量");
			yZidVO10.setTimeType(4);
			ZoneIndicatorDicVO yZidVO11 = new ZoneIndicatorDicVO();
			yZidVO11.setItemCode("SLYLPL");
			yZidVO11.setItemName("单位管长漏损量");
			yZidVO11.setTimeType(4);
			ZoneIndicatorDicVO yZidVO12 = new ZoneIndicatorDicVO();
			yZidVO12.setItemCode("SLYDCPL");
			yZidVO12.setItemName("DMA/PMA面积覆盖率");
			yZidVO12.setTimeType(4);
			lists.add(dZidVO);
			lists.add(dZidVO1);
			lists.add(dZidVO2);
			lists.add(dZidVO3);
			lists.add(dZidVO4);
			lists.add(dZidVO5);
			lists.add(dZidVO6);
			lists.add(dZidVO7);
			lists.add(dZidVO8);
			lists.add(mZidVO);
			lists.add(mZidVO1);
			lists.add(mZidVO2);
			lists.add(mZidVO3);
			lists.add(mZidVO4);
			lists.add(mZidVO5);
			lists.add(mZidVO6);
			lists.add(mZidVO7);
			lists.add(mZidVO8);
			lists.add(mZidVO9);
			lists.add(mZidVO10);
			lists.add(mZidVO11);
			lists.add(mZidVO12);
			lists.add(yZidVO);
			lists.add(yZidVO1);
			lists.add(yZidVO2);
			lists.add(yZidVO3);
			lists.add(yZidVO4);
			lists.add(yZidVO5);
			lists.add(yZidVO6);
			lists.add(yZidVO7);
			lists.add(yZidVO8);
			lists.add(yZidVO9);
			lists.add(yZidVO10);
			lists.add(yZidVO11);
			lists.add(yZidVO12);
		}else if(Constant.RANK_T.equals(zoneType)) {
			//DMA分区
			//日指标
			ZoneIndicatorDicVO dZidVO = new ZoneIndicatorDicVO();
			dZidVO.setItemCode("DMDUCRFW");
			dZidVO.setItemName("未计量水量占比");
			dZidVO.setTimeType(2);
			ZoneIndicatorDicVO dZidVO1 = new ZoneIndicatorDicVO();
			dZidVO1.setItemCode("DMDNBFW");
			dZidVO1.setItemName("爆管数");
			dZidVO1.setTimeType(2);
			ZoneIndicatorDicVO dZidVO2 = new ZoneIndicatorDicVO();
			dZidVO2.setItemCode("DMDNPLFW");
			dZidVO2.setItemName("渗漏数");
			dZidVO2.setTimeType(2);
			ZoneIndicatorDicVO dZidVO3 = new ZoneIndicatorDicVO();
			dZidVO3.setItemCode("DMDBRFW");
			dZidVO3.setItemName("爆管率");
			dZidVO3.setTimeType(2);
			ZoneIndicatorDicVO dZidVO4 = new ZoneIndicatorDicVO();
			dZidVO4.setItemCode("DMDWLR");
			dZidVO4.setItemName("漏损率");
			dZidVO4.setTimeType(2);
			ZoneIndicatorDicVO dZidVO5 = new ZoneIndicatorDicVO();
			dZidVO5.setItemCode("DMDMNF");
			dZidVO5.setItemName("最小夜间流量");
			dZidVO5.setTimeType(2);
			ZoneIndicatorDicVO dZidVO6 = new ZoneIndicatorDicVO();
			dZidVO6.setItemCode("DMDMNFLF");
			dZidVO6.setItemName("扣除大用户的最小夜间流量");
			dZidVO6.setTimeType(2);
			ZoneIndicatorDicVO dZidVO7 = new ZoneIndicatorDicVO();
			dZidVO7.setItemCode("DMDLCA");
			dZidVO7.setItemName("单位户数漏损量");
			dZidVO7.setTimeType(2);
			ZoneIndicatorDicVO dZidVO8 = new ZoneIndicatorDicVO();
			dZidVO8.setItemCode("DMDLPL");
			dZidVO8.setItemName("单位管长漏损量");
			dZidVO8.setTimeType(2);
			
			//月指标
			ZoneIndicatorDicVO mZidVO = new ZoneIndicatorDicVO();
			mZidVO.setItemCode("DMMMC");
			mZidVO.setItemName("客户计量用水量");
			mZidVO.setTimeType(3);
			ZoneIndicatorDicVO mZidVO1 = new ZoneIndicatorDicVO();
			mZidVO1.setItemCode("DMMUCRFW");
			mZidVO1.setItemName("未计量水量占比");
			mZidVO1.setTimeType(3);
			ZoneIndicatorDicVO mZidVO2 = new ZoneIndicatorDicVO();
			mZidVO2.setItemCode("DMMNRW");
			mZidVO2.setItemName("产销差");
			mZidVO2.setTimeType(3);
			ZoneIndicatorDicVO mZidVO3 = new ZoneIndicatorDicVO();
			mZidVO3.setItemCode("DMMNBFW");
			mZidVO3.setItemName("爆管数");
			mZidVO3.setTimeType(3);
			ZoneIndicatorDicVO mZidVO4 = new ZoneIndicatorDicVO();
			mZidVO4.setItemCode("DMMNPLFW");
			mZidVO4.setItemName("渗漏数");
			mZidVO4.setTimeType(3);
			ZoneIndicatorDicVO mZidVO5 = new ZoneIndicatorDicVO();
			mZidVO5.setItemCode("DMMBRFW");
			mZidVO5.setItemName("爆管率");
			mZidVO5.setTimeType(3);
			ZoneIndicatorDicVO mZidVO6 = new ZoneIndicatorDicVO();
			mZidVO6.setItemCode("DMMWL");
			mZidVO6.setItemName("漏损量");
			mZidVO6.setTimeType(3);
			ZoneIndicatorDicVO mZidVO7 = new ZoneIndicatorDicVO();
			mZidVO7.setItemCode("DMMWLR");
			mZidVO7.setItemName("漏损率");
			mZidVO7.setTimeType(3);
			ZoneIndicatorDicVO mZidVO8 = new ZoneIndicatorDicVO();
			mZidVO8.setItemCode("DMMMNF");
			mZidVO8.setItemName("最小夜间流量");
			mZidVO8.setTimeType(3);
			ZoneIndicatorDicVO mZidVO9 = new ZoneIndicatorDicVO();
			mZidVO9.setItemCode("DMMMNFLF");
			mZidVO9.setItemName("扣除大用户的最小夜间流量");
			mZidVO9.setTimeType(3);
			ZoneIndicatorDicVO mZidVO10 = new ZoneIndicatorDicVO();
			mZidVO10.setItemCode("DMMLCA");
			mZidVO10.setItemName("单位户数漏损量");
			mZidVO10.setTimeType(3);
			ZoneIndicatorDicVO mZidVO11 = new ZoneIndicatorDicVO();
			mZidVO11.setItemCode("DMMLPL");
			mZidVO11.setItemName("单位管长漏损量");
			mZidVO11.setTimeType(3);
			ZoneIndicatorDicVO mZidVO12 = new ZoneIndicatorDicVO();
			mZidVO12.setItemCode("DMMDCPL");
			mZidVO12.setItemName("DMA/PMA面积覆盖率");
			mZidVO12.setTimeType(3);
			
			//月指标
			ZoneIndicatorDicVO yZidVO = new ZoneIndicatorDicVO();
			yZidVO.setItemCode("DMYMC");
			yZidVO.setItemName("客户计量用水量");
			yZidVO.setTimeType(4);
			ZoneIndicatorDicVO yZidVO1 = new ZoneIndicatorDicVO();
			yZidVO1.setItemCode("DMYUCRFW");
			yZidVO1.setItemName("未计量水量占比");
			yZidVO1.setTimeType(4);
			ZoneIndicatorDicVO yZidVO2 = new ZoneIndicatorDicVO();
			yZidVO2.setItemCode("DMYNRW");
			yZidVO2.setItemName("产销差");
			yZidVO2.setTimeType(4);
			ZoneIndicatorDicVO yZidVO3 = new ZoneIndicatorDicVO();
			yZidVO3.setItemCode("DMYNBFW");
			yZidVO3.setItemName("爆管数");
			yZidVO3.setTimeType(4);
			ZoneIndicatorDicVO yZidVO4 = new ZoneIndicatorDicVO();
			yZidVO4.setItemCode("DMYNPLFW");
			yZidVO4.setItemName("渗漏数");
			yZidVO4.setTimeType(4);
			ZoneIndicatorDicVO yZidVO5 = new ZoneIndicatorDicVO();
			yZidVO5.setItemCode("DMYBRFW");
			yZidVO5.setItemName("爆管率");
			yZidVO5.setTimeType(4);
			ZoneIndicatorDicVO yZidVO6 = new ZoneIndicatorDicVO();
			yZidVO6.setItemCode("DMYWL");
			yZidVO6.setItemName("漏损量");
			yZidVO6.setTimeType(4);
			ZoneIndicatorDicVO yZidVO7 = new ZoneIndicatorDicVO();
			yZidVO7.setItemCode("DMYWLR");
			yZidVO7.setItemName("漏损率");
			yZidVO7.setTimeType(4);
			ZoneIndicatorDicVO yZidVO8 = new ZoneIndicatorDicVO();
			yZidVO8.setItemCode("DMYMNF");
			yZidVO8.setItemName("最小夜间流量");
			yZidVO8.setTimeType(4);
			ZoneIndicatorDicVO yZidVO9 = new ZoneIndicatorDicVO();
			yZidVO9.setItemCode("DMYMNFLF");
			yZidVO9.setItemName("扣除大用户的最小夜间流量");
			yZidVO9.setTimeType(4);
			ZoneIndicatorDicVO yZidVO10 = new ZoneIndicatorDicVO();
			yZidVO10.setItemCode("DMYLCA");
			yZidVO10.setItemName("单位户数漏损量");
			yZidVO10.setTimeType(4);
			ZoneIndicatorDicVO yZidVO11 = new ZoneIndicatorDicVO();
			yZidVO11.setItemCode("DMYLPL");
			yZidVO11.setItemName("单位管长漏损量");
			yZidVO11.setTimeType(4);
			ZoneIndicatorDicVO yZidVO12 = new ZoneIndicatorDicVO();
			yZidVO12.setItemCode("DMYDCPL");
			yZidVO12.setItemName("DMA/PMA面积覆盖率");
			yZidVO12.setTimeType(4);
			lists.add(dZidVO);
			lists.add(dZidVO1);
			lists.add(dZidVO2);
			lists.add(dZidVO3);
			lists.add(dZidVO4);
			lists.add(dZidVO5);
			lists.add(dZidVO6);
			lists.add(dZidVO7);
			lists.add(dZidVO8);
			lists.add(mZidVO);
			lists.add(mZidVO1);
			lists.add(mZidVO2);
			lists.add(mZidVO3);
			lists.add(mZidVO4);
			lists.add(mZidVO5);
			lists.add(mZidVO6);
			lists.add(mZidVO7);
			lists.add(mZidVO8);
			lists.add(mZidVO9);
			lists.add(mZidVO10);
			lists.add(mZidVO11);
			lists.add(mZidVO12);
			lists.add(yZidVO);
			lists.add(yZidVO1);
			lists.add(yZidVO2);
			lists.add(yZidVO3);
			lists.add(yZidVO4);
			lists.add(yZidVO5);
			lists.add(yZidVO6);
			lists.add(yZidVO7);
			lists.add(yZidVO8);
			lists.add(yZidVO9);
			lists.add(yZidVO10);
			lists.add(yZidVO11);
			lists.add(yZidVO12);
		}
		return lists;
	}


	@TaskAnnotation("queryZoneThematicValue")
	@Override
	public Map<String, Map<Object, Object>> queryZoneThematicValue(SessionFactory factory,
			ZoneThematicValueDTO zoneThematicValueDTO) {
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		List<String> codes = new ArrayList<>();
		List<String> zoneNos = new ArrayList<>();
		Map<String, Map<Object, Object>> maps = new HashMap<>();
		DecimalFormat df = new DecimalFormat("#.0000");
		//根据分区类型获取所有分区编号
		GisZoneServiceImpl gisZoneServiceImpl = new GisZoneServiceImpl();
		List<ZoneInfo> zoneInfos = gisZoneServiceImpl.queryZoneNosByRank(factory, zoneThematicValueDTO.getZoneType(),"");
		for (ZoneInfo zoneInfo : zoneInfos) {
			zoneNos.add(zoneInfo.getZoneNo());
		}
		String itemCode = zoneThematicValueDTO.getItemCode();
		
		codes.add(itemCode);
		IndicatorDTO indicatorDTO = new IndicatorDTO();
		indicatorDTO.setTimeType(zoneThematicValueDTO.getTimeType());
		indicatorDTO.setStartTime(zoneThematicValueDTO.getStartTime());
		indicatorDTO.setEndTime(zoneThematicValueDTO.getEndTime());
		indicatorDTO.setCodes(codes);
		indicatorDTO.setZoneCodes(zoneNos);
		List<String> zoneNoList = new ArrayList<>(); //已存储的分区编号
		if(Constant.BASE_INDIC.contains(itemCode)) {
			//基础指标
			List<IndicatorVO> queryBaseIndicData = mapper.queryBaseIndicData(indicatorDTO);
			for (int i = 0; i<zoneNos.size();i++) {
				String zoneNo = zoneNos.get(i);
				if(zoneNoList.contains(zoneNo)) continue;
				zoneNoList.add(zoneNo);
				Double values = 0.0; //统计值
				int timeNum = 0;  //参与计算的月份数量
				for (IndicatorVO indicatorVO1 : queryBaseIndicData) {
					if(indicatorVO1.getZoneNo().equals(zoneNo)) {
						values += indicatorVO1.getValue();
						timeNum++;
					}
				}
				Map<Object,Object> map = new HashMap<Object, Object>();
				if(itemCode.contains("MRR") || itemCode.contains("DCPL") || itemCode.contains("DCCA")) {
					//管道更新率指标，DMA覆盖率（管长），DMA覆盖率（户数），计算平均值
					if(timeNum == 0) {
						map.put(itemCode, null);
					} else{
						map.put(itemCode, Double.parseDouble(df.format(values/timeNum)));
					}
					
				}else {
					map.put(itemCode, values);
				}
				maps.put(zoneNo, map);
			}
		}else if(Constant.MONI_INDIC.contains(itemCode)) {
			//监测点指标
		}else if(Constant.ZONE_MONI_INDIC.contains(itemCode)) {
			//分区监测指标
		}else if(Constant.BALANCE_INDIC.contains(itemCode)) {
			//水平衡指标
			List<IndicatorVO> queryWBBaseIndicData = mapper.queryWBBaseIndicData(indicatorDTO);
			for (int i = 0; i<zoneNos.size();i++) {
				String zoneNo = zoneNos.get(i);
				if(zoneNoList.contains(zoneNo)) continue;
				zoneNoList.add(zoneNo);
				Double values = 0.0; //统计值
				for (IndicatorVO indicatorVO1 : queryWBBaseIndicData) {
					if(indicatorVO1.getZoneNo().equals(zoneNo)) {
						values += indicatorVO1.getValue();
					}
				}
				Map<Object,Object> map = new HashMap<Object, Object>();
				map.put(itemCode, values);
				maps.put(zoneNo, map);
			}
		}else if(Constant.ZONE_LOSS_INDIC.contains(itemCode)) {
			//分区漏损指标
			List<IndicatorVO> queryZoneLossIndicData = mapper.queryZoneLossIndicData(indicatorDTO);
			for (int i = 0; i<zoneNos.size();i++) {
				String zoneNo = zoneNos.get(i);
				if(zoneNoList.contains(zoneNo)) continue;
				zoneNoList.add(zoneNo);
				Double values = 0.0; //统计值
				int timeNum = 0;  //参与计算的月份数量
				for (IndicatorVO indicatorVO1 : queryZoneLossIndicData) {
					if(indicatorVO1.getZoneNo().equals(zoneNo)) {
						values += indicatorVO1.getValue();
						timeNum++;
					}
				}
				Map<Object,Object> map = new HashMap<Object, Object>();
				if(timeNum == 0) {
					map.put(itemCode, null);
				} else{
					map.put(itemCode, Double.parseDouble(df.format(values/timeNum)));
				}
				maps.put(zoneNo, map);
			}
		}else if(Constant.APPARENT_INDIC.contains(itemCode)) {
			//表观漏损指标
		}else if(Constant.LEAK_INDIC.contains(itemCode)) {
			//爆管/渗漏指标
			List<IndicatorVO> queryLeakIndicData = mapper.queryLeakIndicData(indicatorDTO);
			for (int i = 0; i<zoneNos.size();i++) {
				String zoneNo = zoneNos.get(i);
				if(zoneNoList.contains(zoneNo)) continue;
				zoneNoList.add(zoneNo);
				Double values = 0.0; //统计值
				int timeNum = 0;
				for (IndicatorVO indicatorVO1 : queryLeakIndicData) {
					if(indicatorVO1.getZoneNo().equals(zoneNo)) {
						values += indicatorVO1.getValue();
						timeNum++;
					}
				}
				Map<Object,Object> map = new HashMap<Object, Object>();
				if(itemCode.contains("MRR") || itemCode.contains("DCPL") || itemCode.contains("DCCA")) {
					//管道更新率指标，DMA覆盖率（管长），DMA覆盖率（户数），计算平均值
					if(timeNum == 0) {
						map.put(itemCode, null);
					} else{
						map.put(itemCode, Double.parseDouble(df.format(values/timeNum)));
					}
				}else {
					map.put(itemCode, values);
				}
				maps.put(zoneNo, map);
			}
		}
		return maps;
	}

	@TaskAnnotation("queryZoneHstIndicatorDic")
	@Override
	public List<ZoneIndicatorDicVO> queryZoneHstIndicatorDic(SessionFactory factory, Integer zoneType) {
		
		List<ZoneIndicatorDicVO> lists = new ArrayList<>();
		if(Constant.RANK_F.equals(zoneType)) {
			//一级分区
			//日指标
			ZoneIndicatorDicVO dZidVO = new ZoneIndicatorDicVO();
			dZidVO.setItemCode("FLDUFWC");
			dZidVO.setItemName("未计量水量");
			dZidVO.setTimeType(2);
			ZoneIndicatorDicVO dZidVO1 = new ZoneIndicatorDicVO();
			dZidVO1.setItemCode("FLDUFWCCA");
			dZidVO1.setItemName("单位户数未计量水量");
			dZidVO1.setTimeType(2);
			ZoneIndicatorDicVO dZidVO2 = new ZoneIndicatorDicVO();
			dZidVO2.setItemCode("FLDUFWCPL");
			dZidVO2.setItemName("单位管长未计量水量");
			dZidVO2.setTimeType(2);
			ZoneIndicatorDicVO dZidVO3 = new ZoneIndicatorDicVO();
			dZidVO3.setItemCode("FLDUCRFW");
			dZidVO3.setItemName("未计量占比");
			dZidVO3.setTimeType(2);
			ZoneIndicatorDicVO dZidVO4 = new ZoneIndicatorDicVO();
			dZidVO4.setItemCode("FLDNRW");
			dZidVO4.setItemName("产销差");
			dZidVO4.setTimeType(2);
			ZoneIndicatorDicVO dZidVO5 = new ZoneIndicatorDicVO();
			dZidVO5.setItemCode("FLDNRWCA");
			dZidVO5.setItemName("单位户数产销差");
			dZidVO5.setTimeType(2);
			ZoneIndicatorDicVO dZidVO6 = new ZoneIndicatorDicVO();
			dZidVO6.setItemCode("FLDNRWPL");
			dZidVO6.setItemName("单位管长产销差");
			dZidVO6.setTimeType(2);
			ZoneIndicatorDicVO dZidVO7 = new ZoneIndicatorDicVO();
			dZidVO7.setItemCode("FLDNRR");
			dZidVO7.setItemName("产销差率");
			dZidVO7.setTimeType(2);
			ZoneIndicatorDicVO dZidVO8 = new ZoneIndicatorDicVO();
			dZidVO8.setItemCode("FLDFWSSITDF");
			dZidVO8.setItemName("日均供水量");
			dZidVO8.setTimeType(2);
			ZoneIndicatorDicVO dZidVO9 = new ZoneIndicatorDicVO();
			dZidVO9.setItemCode("FLDBMC");
			dZidVO9.setItemName("计量水量");
			dZidVO9.setTimeType(2);
			ZoneIndicatorDicVO dZidVO10 = new ZoneIndicatorDicVO();
			dZidVO10.setItemCode("FLDBC");
			dZidVO10.setItemName("计费水量");
			dZidVO10.setTimeType(2);
			ZoneIndicatorDicVO dZidVO11 = new ZoneIndicatorDicVO();
			dZidVO11.setItemCode("FLDNBFW");
			dZidVO11.setItemName("爆管次数");
			dZidVO11.setTimeType(2);
			ZoneIndicatorDicVO dZidVO12 = new ZoneIndicatorDicVO();
			dZidVO12.setItemCode("FLDBLFW");
			dZidVO12.setItemName("渗漏次数");
			dZidVO12.setTimeType(2);
			ZoneIndicatorDicVO dZidVO13 = new ZoneIndicatorDicVO();
			dZidVO13.setItemCode("FLDBRFW");
			dZidVO13.setItemName("爆管率");
			dZidVO13.setTimeType(2);
			ZoneIndicatorDicVO dZidVO14 = new ZoneIndicatorDicVO();
			dZidVO14.setItemCode("FLDLRFW");
			dZidVO14.setItemName("渗漏率");
			dZidVO14.setTimeType(2);
			ZoneIndicatorDicVO dZidVO15 = new ZoneIndicatorDicVO();
			dZidVO15.setItemCode("FLMNOCM");
			dZidVO15.setItemName("用户表数");
			dZidVO15.setTimeType(2);
			ZoneIndicatorDicVO dZidVO16 = new ZoneIndicatorDicVO();
			dZidVO16.setItemCode("FLMFTPL");
			dZidVO16.setItemName("总管长");
			dZidVO16.setTimeType(2);
			
			//月指标
			ZoneIndicatorDicVO mZidVO = new ZoneIndicatorDicVO();
			mZidVO.setItemCode("FLMUFWC");
			mZidVO.setItemName("未计量水量");
			mZidVO.setTimeType(3);
			ZoneIndicatorDicVO mZidVO1 = new ZoneIndicatorDicVO();
			mZidVO1.setItemCode("FLMUFWCCA");
			mZidVO1.setItemName("单位户数未计量水量");
			mZidVO1.setTimeType(3);
			ZoneIndicatorDicVO mZidVO2 = new ZoneIndicatorDicVO();
			mZidVO2.setItemCode("FLMUFWCPL");
			mZidVO2.setItemName("单位管长未计量水量");
			mZidVO2.setTimeType(3);
			ZoneIndicatorDicVO mZidVO3 = new ZoneIndicatorDicVO();
			mZidVO3.setItemCode("FLMUCRFW");
			mZidVO3.setItemName("未计量占比");
			mZidVO3.setTimeType(3);
			ZoneIndicatorDicVO mZidVO4 = new ZoneIndicatorDicVO();
			mZidVO4.setItemCode("FLMNRW");
			mZidVO4.setItemName("产销差");
			mZidVO4.setTimeType(3);
			ZoneIndicatorDicVO mZidVO5 = new ZoneIndicatorDicVO();
			mZidVO5.setItemCode("FLMNRWCA");
			mZidVO5.setItemName("单位户数产销差");
			mZidVO5.setTimeType(3);
			ZoneIndicatorDicVO mZidVO6 = new ZoneIndicatorDicVO();
			mZidVO6.setItemCode("FLMNRWPL");
			mZidVO6.setItemName("单位管长产销差");
			mZidVO6.setTimeType(3);
			ZoneIndicatorDicVO mZidVO7 = new ZoneIndicatorDicVO();
			mZidVO7.setItemCode("FLMNRR");
			mZidVO7.setItemName("产销差率");
			mZidVO7.setTimeType(3);
			ZoneIndicatorDicVO mZidVO8 = new ZoneIndicatorDicVO();
			mZidVO8.setItemCode("FLMFWSSITDF");
			mZidVO8.setItemName("日均供水量");
			mZidVO8.setTimeType(3);
			ZoneIndicatorDicVO mZidVO9 = new ZoneIndicatorDicVO();
			mZidVO9.setItemCode("FLMBMC");
			mZidVO9.setItemName("计量水量");
			mZidVO9.setTimeType(3);
			ZoneIndicatorDicVO mZidVO10 = new ZoneIndicatorDicVO();
			mZidVO10.setItemCode("FLMBC");
			mZidVO10.setItemName("计费水量");
			mZidVO10.setTimeType(3);
			ZoneIndicatorDicVO mZidVO11 = new ZoneIndicatorDicVO();
			mZidVO11.setItemCode("FLMNBFW");
			mZidVO11.setItemName("爆管次数");
			mZidVO11.setTimeType(3);
			ZoneIndicatorDicVO mZidVO12 = new ZoneIndicatorDicVO();
			mZidVO12.setItemCode("FLMBLFW");
			mZidVO12.setItemName("渗漏次数");
			mZidVO12.setTimeType(3);
			ZoneIndicatorDicVO mZidVO13 = new ZoneIndicatorDicVO();
			mZidVO13.setItemCode("FLMBRFW");
			mZidVO13.setItemName("爆管率");
			mZidVO13.setTimeType(3);
			ZoneIndicatorDicVO mZidVO14 = new ZoneIndicatorDicVO();
			mZidVO14.setItemCode("FLMLRFW");
			mZidVO14.setItemName("渗漏率");
			mZidVO14.setTimeType(3);
			ZoneIndicatorDicVO mZidVO15 = new ZoneIndicatorDicVO();
			mZidVO15.setItemCode("FLMNOCM");
			mZidVO15.setItemName("用户表数");
			mZidVO15.setTimeType(3);
			ZoneIndicatorDicVO mZidVO16 = new ZoneIndicatorDicVO();
			mZidVO16.setItemCode("FLMFTPL");
			mZidVO16.setItemName("总管长");
			mZidVO16.setTimeType(3);
			
			//年指标
			ZoneIndicatorDicVO yZidVO = new ZoneIndicatorDicVO();
			yZidVO.setItemCode("FLYUFWC");
			yZidVO.setItemName("未计量水量");
			yZidVO.setTimeType(4);
			ZoneIndicatorDicVO yZidVO1 = new ZoneIndicatorDicVO();
			yZidVO1.setItemCode("FLYUFWCCA");
			yZidVO1.setItemName("单位户数未计量水量");
			yZidVO1.setTimeType(4);
			ZoneIndicatorDicVO yZidVO2 = new ZoneIndicatorDicVO();
			yZidVO2.setItemCode("FLYUFWCPL");
			yZidVO2.setItemName("单位管长未计量水量");
			yZidVO2.setTimeType(4);
			ZoneIndicatorDicVO yZidVO3 = new ZoneIndicatorDicVO();
			yZidVO3.setItemCode("FLYUCRFW");
			yZidVO3.setItemName("未计量占比");
			yZidVO3.setTimeType(4);
			ZoneIndicatorDicVO yZidVO4 = new ZoneIndicatorDicVO();
			yZidVO4.setItemCode("FLYNRW");
			yZidVO4.setItemName("产销差");
			yZidVO4.setTimeType(4);
			ZoneIndicatorDicVO yZidVO5 = new ZoneIndicatorDicVO();
			yZidVO5.setItemCode("FLYNRWCA");
			yZidVO5.setItemName("单位户数产销差");
			yZidVO5.setTimeType(4);
			ZoneIndicatorDicVO yZidVO6 = new ZoneIndicatorDicVO();
			yZidVO6.setItemCode("FLYNRWPL");
			yZidVO6.setItemName("单位管长产销差");
			yZidVO6.setTimeType(4);
			ZoneIndicatorDicVO yZidVO7 = new ZoneIndicatorDicVO();
			yZidVO7.setItemCode("FLYNRR");
			yZidVO7.setItemName("产销差率");
			yZidVO7.setTimeType(4);
			ZoneIndicatorDicVO yZidVO8 = new ZoneIndicatorDicVO();
			yZidVO8.setItemCode("FLYFWSSITDF");
			yZidVO8.setItemName("日均供水量");
			yZidVO8.setTimeType(4);
			ZoneIndicatorDicVO yZidVO9 = new ZoneIndicatorDicVO();
			yZidVO9.setItemCode("FLYBMC");
			yZidVO9.setItemName("计量水量");
			yZidVO9.setTimeType(4);
			ZoneIndicatorDicVO yZidVO10 = new ZoneIndicatorDicVO();
			yZidVO10.setItemCode("FLYBC");
			yZidVO10.setItemName("计费水量");
			yZidVO10.setTimeType(4);
			ZoneIndicatorDicVO yZidVO11 = new ZoneIndicatorDicVO();
			yZidVO11.setItemCode("FLYNBFW");
			yZidVO11.setItemName("爆管次数");
			yZidVO11.setTimeType(4);
			ZoneIndicatorDicVO yZidVO12 = new ZoneIndicatorDicVO();
			yZidVO12.setItemCode("FLYBLFW");
			yZidVO12.setItemName("渗漏次数");
			yZidVO12.setTimeType(4);
			ZoneIndicatorDicVO yZidVO13 = new ZoneIndicatorDicVO();
			yZidVO13.setItemCode("FLYBRFW");
			yZidVO13.setItemName("爆管率");
			mZidVO13.setTimeType(4);
			ZoneIndicatorDicVO yZidVO14 = new ZoneIndicatorDicVO();
			yZidVO14.setItemCode("FLYLRFW");
			yZidVO14.setItemName("渗漏率");
			yZidVO14.setTimeType(4);
			ZoneIndicatorDicVO yZidVO15 = new ZoneIndicatorDicVO();
			yZidVO15.setItemCode("FLYNOCM");
			yZidVO15.setItemName("用户表数");
			yZidVO15.setTimeType(4);
			ZoneIndicatorDicVO yZidVO16 = new ZoneIndicatorDicVO();
			yZidVO16.setItemCode("FLYFTPL");
			yZidVO16.setItemName("总管长");
			yZidVO16.setTimeType(4);
			lists.add(dZidVO);
			lists.add(dZidVO1);
			lists.add(dZidVO2);
			lists.add(dZidVO3);
			lists.add(dZidVO4);
			lists.add(dZidVO5);
			lists.add(dZidVO6);
			lists.add(dZidVO7);
			lists.add(dZidVO8);
			lists.add(dZidVO9);
			lists.add(dZidVO10);
			lists.add(dZidVO11);
			lists.add(dZidVO12);
			lists.add(dZidVO13);
			lists.add(dZidVO14);
			lists.add(dZidVO15);
			lists.add(dZidVO16);
			lists.add(mZidVO);
			lists.add(yZidVO1);
			lists.add(mZidVO2);
			lists.add(mZidVO3);
			lists.add(mZidVO4);
			lists.add(mZidVO5);
			lists.add(mZidVO6);
			lists.add(mZidVO7);
			lists.add(mZidVO8);
			lists.add(mZidVO9);
			lists.add(mZidVO10);
			lists.add(mZidVO11);
			lists.add(mZidVO12);
			lists.add(mZidVO13);
			lists.add(mZidVO14);
			lists.add(mZidVO15);
			lists.add(mZidVO16);
			lists.add(yZidVO);
			lists.add(yZidVO1);
			lists.add(yZidVO2);
			lists.add(yZidVO3);
			lists.add(yZidVO4);
			lists.add(yZidVO5);
			lists.add(yZidVO6);
			lists.add(yZidVO7);
			lists.add(yZidVO8);
			lists.add(yZidVO9);
			lists.add(yZidVO10);
			lists.add(yZidVO11);
			lists.add(yZidVO12);
			lists.add(yZidVO13);
			lists.add(yZidVO14);
			lists.add(yZidVO15);
			lists.add(yZidVO16);
		}else if(Constant.RANK_S.equals(zoneType)) {
			//二级分区
			//日指标
			ZoneIndicatorDicVO dZidVO = new ZoneIndicatorDicVO();
			dZidVO.setItemCode("SLDUFWC");
			dZidVO.setItemName("未计量水量");
			dZidVO.setTimeType(2);
			ZoneIndicatorDicVO dZidVO1 = new ZoneIndicatorDicVO();
			dZidVO1.setItemCode("SLDUFWCCA");
			dZidVO1.setItemName("单位户数未计量水量");
			dZidVO1.setTimeType(2);
			ZoneIndicatorDicVO dZidVO2 = new ZoneIndicatorDicVO();
			dZidVO2.setItemCode("SLDUFWCPL");
			dZidVO2.setItemName("单位管长未计量水量");
			dZidVO2.setTimeType(2);
			ZoneIndicatorDicVO dZidVO3 = new ZoneIndicatorDicVO();
			dZidVO3.setItemCode("SLDUCRFW");
			dZidVO3.setItemName("未计量占比");
			dZidVO3.setTimeType(2);
			ZoneIndicatorDicVO dZidVO4 = new ZoneIndicatorDicVO();
			dZidVO4.setItemCode("SLDNRW");
			dZidVO4.setItemName("产销差");
			dZidVO4.setTimeType(2);
			ZoneIndicatorDicVO dZidVO5 = new ZoneIndicatorDicVO();
			dZidVO5.setItemCode("SLDNRWCA");
			dZidVO5.setItemName("单位户数产销差");
			dZidVO5.setTimeType(2);
			ZoneIndicatorDicVO dZidVO6 = new ZoneIndicatorDicVO();
			dZidVO6.setItemCode("SLDNRWPL");
			dZidVO6.setItemName("单位管长产销差");
			dZidVO6.setTimeType(2);
			ZoneIndicatorDicVO dZidVO7 = new ZoneIndicatorDicVO();
			dZidVO7.setItemCode("SLDNRR");
			dZidVO7.setItemName("产销差率");
			dZidVO7.setTimeType(2);
			ZoneIndicatorDicVO dZidVO8 = new ZoneIndicatorDicVO();
			dZidVO8.setItemCode("SLDFWSSITDF");
			dZidVO8.setItemName("日均供水量");
			dZidVO8.setTimeType(2);
			ZoneIndicatorDicVO dZidVO9 = new ZoneIndicatorDicVO();
			dZidVO9.setItemCode("SLDBMC");
			dZidVO9.setItemName("计量水量");
			dZidVO9.setTimeType(2);
			ZoneIndicatorDicVO dZidVO10 = new ZoneIndicatorDicVO();
			dZidVO10.setItemCode("SLDBC");
			dZidVO10.setItemName("计费水量");
			dZidVO10.setTimeType(2);
			ZoneIndicatorDicVO dZidVO11 = new ZoneIndicatorDicVO();
			dZidVO11.setItemCode("SLDNBFW");
			dZidVO11.setItemName("爆管次数");
			dZidVO11.setTimeType(2);
			ZoneIndicatorDicVO dZidVO12 = new ZoneIndicatorDicVO();
			dZidVO12.setItemCode("SLDBLFW");
			dZidVO12.setItemName("渗漏次数");
			dZidVO12.setTimeType(2);
			ZoneIndicatorDicVO dZidVO13 = new ZoneIndicatorDicVO();
			dZidVO13.setItemCode("SLDBRFW");
			dZidVO13.setItemName("爆管率");
			dZidVO13.setTimeType(2);
			ZoneIndicatorDicVO dZidVO14 = new ZoneIndicatorDicVO();
			dZidVO14.setItemCode("SLDLRFW");
			dZidVO14.setItemName("渗漏率");
			dZidVO14.setTimeType(2);
			ZoneIndicatorDicVO dZidVO15 = new ZoneIndicatorDicVO();
			dZidVO15.setItemCode("SLMNOCM");
			dZidVO15.setItemName("用户表数");
			dZidVO15.setTimeType(2);
			ZoneIndicatorDicVO dZidVO16 = new ZoneIndicatorDicVO();
			dZidVO16.setItemCode("SLMFTPL");
			dZidVO16.setItemName("总管长");
			dZidVO16.setTimeType(2);
			
			//月指标
			ZoneIndicatorDicVO mZidVO = new ZoneIndicatorDicVO();
			mZidVO.setItemCode("SLMUFWC");
			mZidVO.setItemName("未计量水量");
			mZidVO.setTimeType(3);
			ZoneIndicatorDicVO mZidVO1 = new ZoneIndicatorDicVO();
			mZidVO1.setItemCode("SLMUFWCCA");
			mZidVO1.setItemName("单位户数未计量水量");
			mZidVO1.setTimeType(3);
			ZoneIndicatorDicVO mZidVO2 = new ZoneIndicatorDicVO();
			mZidVO2.setItemCode("SLMUFWCPL");
			mZidVO2.setItemName("单位管长未计量水量");
			mZidVO2.setTimeType(3);
			ZoneIndicatorDicVO mZidVO3 = new ZoneIndicatorDicVO();
			mZidVO3.setItemCode("SLMUCRFW");
			mZidVO3.setItemName("未计量占比");
			mZidVO3.setTimeType(3);
			ZoneIndicatorDicVO mZidVO4 = new ZoneIndicatorDicVO();
			mZidVO4.setItemCode("SLMNRW");
			mZidVO4.setItemName("产销差");
			mZidVO4.setTimeType(3);
			ZoneIndicatorDicVO mZidVO5 = new ZoneIndicatorDicVO();
			mZidVO5.setItemCode("SLMNRWCA");
			mZidVO5.setItemName("单位户数产销差");
			mZidVO5.setTimeType(3);
			ZoneIndicatorDicVO mZidVO6 = new ZoneIndicatorDicVO();
			mZidVO6.setItemCode("SLMNRWPL");
			mZidVO6.setItemName("单位管长产销差");
			mZidVO6.setTimeType(3);
			ZoneIndicatorDicVO mZidVO7 = new ZoneIndicatorDicVO();
			mZidVO7.setItemCode("SLMNRR");
			mZidVO7.setItemName("产销差率");
			mZidVO7.setTimeType(3);
			ZoneIndicatorDicVO mZidVO8 = new ZoneIndicatorDicVO();
			mZidVO8.setItemCode("SLMFWSSITDF");
			mZidVO8.setItemName("日均供水量");
			mZidVO8.setTimeType(3);
			ZoneIndicatorDicVO mZidVO9 = new ZoneIndicatorDicVO();
			mZidVO9.setItemCode("SLMBMC");
			mZidVO9.setItemName("计量水量");
			mZidVO9.setTimeType(3);
			ZoneIndicatorDicVO mZidVO10 = new ZoneIndicatorDicVO();
			mZidVO10.setItemCode("SLMBC");
			mZidVO10.setItemName("计费水量");
			mZidVO10.setTimeType(3);
			ZoneIndicatorDicVO mZidVO11 = new ZoneIndicatorDicVO();
			mZidVO11.setItemCode("SLMNBFW");
			mZidVO11.setItemName("爆管次数");
			mZidVO11.setTimeType(3);
			ZoneIndicatorDicVO mZidVO12 = new ZoneIndicatorDicVO();
			mZidVO12.setItemCode("SLMBLFW");
			mZidVO12.setItemName("渗漏次数");
			mZidVO12.setTimeType(3);
			ZoneIndicatorDicVO mZidVO13 = new ZoneIndicatorDicVO();
			mZidVO13.setItemCode("SLMBRFW");
			mZidVO13.setItemName("爆管率");
			mZidVO13.setTimeType(3);
			ZoneIndicatorDicVO mZidVO14 = new ZoneIndicatorDicVO();
			mZidVO14.setItemCode("SLMLRFW");
			mZidVO14.setItemName("渗漏率");
			mZidVO14.setTimeType(3);
			ZoneIndicatorDicVO mZidVO15 = new ZoneIndicatorDicVO();
			mZidVO15.setItemCode("SLMNOCM");
			mZidVO15.setItemName("用户表数");
			mZidVO15.setTimeType(3);
			ZoneIndicatorDicVO mZidVO16 = new ZoneIndicatorDicVO();
			mZidVO16.setItemCode("SLMFTPL");
			mZidVO16.setItemName("总管长");
			mZidVO16.setTimeType(3);
			
			//年指标
			ZoneIndicatorDicVO yZidVO = new ZoneIndicatorDicVO();
			yZidVO.setItemCode("SLYUFWC");
			yZidVO.setItemName("未计量水量");
			yZidVO.setTimeType(4);
			ZoneIndicatorDicVO yZidVO1 = new ZoneIndicatorDicVO();
			yZidVO1.setItemCode("SLYUFWCCA");
			yZidVO1.setItemName("单位户数未计量水量");
			yZidVO1.setTimeType(4);
			ZoneIndicatorDicVO yZidVO2 = new ZoneIndicatorDicVO();
			yZidVO2.setItemCode("SLYUFWCPL");
			yZidVO2.setItemName("单位管长未计量水量");
			yZidVO2.setTimeType(4);
			ZoneIndicatorDicVO yZidVO3 = new ZoneIndicatorDicVO();
			yZidVO3.setItemCode("SLYUCRFW");
			yZidVO3.setItemName("未计量占比");
			yZidVO3.setTimeType(4);
			ZoneIndicatorDicVO yZidVO4 = new ZoneIndicatorDicVO();
			yZidVO4.setItemCode("SLYNRW");
			yZidVO4.setItemName("产销差");
			yZidVO4.setTimeType(4);
			ZoneIndicatorDicVO yZidVO5 = new ZoneIndicatorDicVO();
			yZidVO5.setItemCode("SLYNRWCA");
			yZidVO5.setItemName("单位户数产销差");
			yZidVO5.setTimeType(4);
			ZoneIndicatorDicVO yZidVO6 = new ZoneIndicatorDicVO();
			yZidVO6.setItemCode("SLYNRWPL");
			yZidVO6.setItemName("单位管长产销差");
			yZidVO6.setTimeType(4);
			ZoneIndicatorDicVO yZidVO7 = new ZoneIndicatorDicVO();
			yZidVO7.setItemCode("SLYNRR");
			yZidVO7.setItemName("产销差率");
			yZidVO7.setTimeType(4);
			ZoneIndicatorDicVO yZidVO8 = new ZoneIndicatorDicVO();
			yZidVO8.setItemCode("SLYFWSSITDF");
			yZidVO8.setItemName("日均供水量");
			yZidVO8.setTimeType(4);
			ZoneIndicatorDicVO yZidVO9 = new ZoneIndicatorDicVO();
			yZidVO9.setItemCode("SLYBMC");
			yZidVO9.setItemName("计量水量");
			yZidVO9.setTimeType(4);
			ZoneIndicatorDicVO yZidVO10 = new ZoneIndicatorDicVO();
			yZidVO10.setItemCode("SLYBC");
			yZidVO10.setItemName("计费水量");
			yZidVO10.setTimeType(4);
			ZoneIndicatorDicVO yZidVO11 = new ZoneIndicatorDicVO();
			yZidVO11.setItemCode("SLYNBFW");
			yZidVO11.setItemName("爆管次数");
			yZidVO11.setTimeType(4);
			ZoneIndicatorDicVO yZidVO12 = new ZoneIndicatorDicVO();
			yZidVO12.setItemCode("SLYBLFW");
			yZidVO12.setItemName("渗漏次数");
			yZidVO12.setTimeType(4);
			ZoneIndicatorDicVO yZidVO13 = new ZoneIndicatorDicVO();
			yZidVO13.setItemCode("SLYBRFW");
			yZidVO13.setItemName("爆管率");
			mZidVO13.setTimeType(4);
			ZoneIndicatorDicVO yZidVO14 = new ZoneIndicatorDicVO();
			yZidVO14.setItemCode("SLYLRFW");
			yZidVO14.setItemName("渗漏率");
			yZidVO14.setTimeType(4);
			ZoneIndicatorDicVO yZidVO15 = new ZoneIndicatorDicVO();
			yZidVO15.setItemCode("SLYNOCM");
			yZidVO15.setItemName("用户表数");
			yZidVO15.setTimeType(4);
			ZoneIndicatorDicVO yZidVO16 = new ZoneIndicatorDicVO();
			yZidVO16.setItemCode("SLYFTPL");
			yZidVO16.setItemName("总管长");
			yZidVO16.setTimeType(4);
			lists.add(dZidVO);
			lists.add(dZidVO1);
			lists.add(dZidVO2);
			lists.add(dZidVO3);
			lists.add(dZidVO4);
			lists.add(dZidVO5);
			lists.add(dZidVO6);
			lists.add(dZidVO7);
			lists.add(dZidVO8);
			lists.add(dZidVO9);
			lists.add(dZidVO10);
			lists.add(dZidVO11);
			lists.add(dZidVO12);
			lists.add(dZidVO13);
			lists.add(dZidVO14);
			lists.add(dZidVO15);
			lists.add(dZidVO16);
			lists.add(mZidVO);
			lists.add(yZidVO1);
			lists.add(mZidVO2);
			lists.add(mZidVO3);
			lists.add(mZidVO4);
			lists.add(mZidVO5);
			lists.add(mZidVO6);
			lists.add(mZidVO7);
			lists.add(mZidVO8);
			lists.add(mZidVO9);
			lists.add(mZidVO10);
			lists.add(mZidVO11);
			lists.add(mZidVO12);
			lists.add(mZidVO13);
			lists.add(mZidVO14);
			lists.add(mZidVO15);
			lists.add(mZidVO16);
			lists.add(yZidVO);
			lists.add(yZidVO1);
			lists.add(yZidVO2);
			lists.add(yZidVO3);
			lists.add(yZidVO4);
			lists.add(yZidVO5);
			lists.add(yZidVO6);
			lists.add(yZidVO7);
			lists.add(yZidVO8);
			lists.add(yZidVO9);
			lists.add(yZidVO10);
			lists.add(yZidVO11);
			lists.add(yZidVO12);
			lists.add(yZidVO13);
			lists.add(yZidVO14);
			lists.add(yZidVO15);
			lists.add(yZidVO16);
		}else if(Constant.RANK_T.equals(zoneType)) {
			//DMA分区
			//日指标
			ZoneIndicatorDicVO dZidVO = new ZoneIndicatorDicVO();
			dZidVO.setItemCode("DMDUFWC");
			dZidVO.setItemName("未计量水量");
			dZidVO.setTimeType(2);
			ZoneIndicatorDicVO dZidVO1 = new ZoneIndicatorDicVO();
			dZidVO1.setItemCode("DMDUFWCCA");
			dZidVO1.setItemName("单位户数未计量水量");
			dZidVO1.setTimeType(2);
			ZoneIndicatorDicVO dZidVO2 = new ZoneIndicatorDicVO();
			dZidVO2.setItemCode("DMDUFWCPL");
			dZidVO2.setItemName("单位管长未计量水量");
			dZidVO2.setTimeType(2);
			ZoneIndicatorDicVO dZidVO3 = new ZoneIndicatorDicVO();
			dZidVO3.setItemCode("DMDUCRFW");
			dZidVO3.setItemName("未计量占比");
			dZidVO3.setTimeType(2);
			ZoneIndicatorDicVO dZidVO4 = new ZoneIndicatorDicVO();
			dZidVO4.setItemCode("DMDNRW");
			dZidVO4.setItemName("产销差");
			dZidVO4.setTimeType(2);
			ZoneIndicatorDicVO dZidVO5 = new ZoneIndicatorDicVO();
			dZidVO5.setItemCode("DMDNRWCA");
			dZidVO5.setItemName("单位户数产销差");
			dZidVO5.setTimeType(2);
			ZoneIndicatorDicVO dZidVO6 = new ZoneIndicatorDicVO();
			dZidVO6.setItemCode("DMDNRWPL");
			dZidVO6.setItemName("单位管长产销差");
			dZidVO6.setTimeType(2);
			ZoneIndicatorDicVO dZidVO7 = new ZoneIndicatorDicVO();
			dZidVO7.setItemCode("DMDNRR");
			dZidVO7.setItemName("产销差率");
			dZidVO7.setTimeType(2);
			ZoneIndicatorDicVO dZidVO8 = new ZoneIndicatorDicVO();
			dZidVO8.setItemCode("DMDFWSSITDF");
			dZidVO8.setItemName("日均供水量");
			dZidVO8.setTimeType(2);
			ZoneIndicatorDicVO dZidVO9 = new ZoneIndicatorDicVO();
			dZidVO9.setItemCode("DMDBMC");
			dZidVO9.setItemName("计量水量");
			dZidVO9.setTimeType(2);
			ZoneIndicatorDicVO dZidVO10 = new ZoneIndicatorDicVO();
			dZidVO10.setItemCode("DMDBC");
			dZidVO10.setItemName("计费水量");
			dZidVO10.setTimeType(2);
			ZoneIndicatorDicVO dZidVO11 = new ZoneIndicatorDicVO();
			dZidVO11.setItemCode("DMDNBFW");
			dZidVO11.setItemName("爆管次数");
			dZidVO11.setTimeType(2);
			ZoneIndicatorDicVO dZidVO12 = new ZoneIndicatorDicVO();
			dZidVO12.setItemCode("DMDBLFW");
			dZidVO12.setItemName("渗漏次数");
			dZidVO12.setTimeType(2);
			ZoneIndicatorDicVO dZidVO13 = new ZoneIndicatorDicVO();
			dZidVO13.setItemCode("DMDBRFW");
			dZidVO13.setItemName("爆管率");
			dZidVO13.setTimeType(2);
			ZoneIndicatorDicVO dZidVO14 = new ZoneIndicatorDicVO();
			dZidVO14.setItemCode("DMDLRFW");
			dZidVO14.setItemName("渗漏率");
			dZidVO14.setTimeType(2);
			ZoneIndicatorDicVO dZidVO15 = new ZoneIndicatorDicVO();
			dZidVO15.setItemCode("DMMNOCM");
			dZidVO15.setItemName("用户表数");
			dZidVO15.setTimeType(2);
			ZoneIndicatorDicVO dZidVO16 = new ZoneIndicatorDicVO();
			dZidVO16.setItemCode("DMMFTPL");
			dZidVO16.setItemName("总管长");
			dZidVO16.setTimeType(2);
			
			//月指标
			ZoneIndicatorDicVO mZidVO = new ZoneIndicatorDicVO();
			mZidVO.setItemCode("DMMUFWC");
			mZidVO.setItemName("未计量水量");
			mZidVO.setTimeType(3);
			ZoneIndicatorDicVO mZidVO1 = new ZoneIndicatorDicVO();
			mZidVO1.setItemCode("DMMUFWCCA");
			mZidVO1.setItemName("单位户数未计量水量");
			mZidVO1.setTimeType(3);
			ZoneIndicatorDicVO mZidVO2 = new ZoneIndicatorDicVO();
			mZidVO2.setItemCode("DMMUFWCPL");
			mZidVO2.setItemName("单位管长未计量水量");
			mZidVO2.setTimeType(3);
			ZoneIndicatorDicVO mZidVO3 = new ZoneIndicatorDicVO();
			mZidVO3.setItemCode("DMMUCRFW");
			mZidVO3.setItemName("未计量占比");
			mZidVO3.setTimeType(3);
			ZoneIndicatorDicVO mZidVO4 = new ZoneIndicatorDicVO();
			mZidVO4.setItemCode("DMMNRW");
			mZidVO4.setItemName("产销差");
			mZidVO4.setTimeType(3);
			ZoneIndicatorDicVO mZidVO5 = new ZoneIndicatorDicVO();
			mZidVO5.setItemCode("DMMNRWCA");
			mZidVO5.setItemName("单位户数产销差");
			mZidVO5.setTimeType(3);
			ZoneIndicatorDicVO mZidVO6 = new ZoneIndicatorDicVO();
			mZidVO6.setItemCode("DMMNRWPL");
			mZidVO6.setItemName("单位管长产销差");
			mZidVO6.setTimeType(3);
			ZoneIndicatorDicVO mZidVO7 = new ZoneIndicatorDicVO();
			mZidVO7.setItemCode("DMMNRR");
			mZidVO7.setItemName("产销差率");
			mZidVO7.setTimeType(3);
			ZoneIndicatorDicVO mZidVO8 = new ZoneIndicatorDicVO();
			mZidVO8.setItemCode("DMMFWSSITDF");
			mZidVO8.setItemName("日均供水量");
			mZidVO8.setTimeType(3);
			ZoneIndicatorDicVO mZidVO9 = new ZoneIndicatorDicVO();
			mZidVO9.setItemCode("DMMBMC");
			mZidVO9.setItemName("计量水量");
			mZidVO9.setTimeType(3);
			ZoneIndicatorDicVO mZidVO10 = new ZoneIndicatorDicVO();
			mZidVO10.setItemCode("DMMBC");
			mZidVO10.setItemName("计费水量");
			mZidVO10.setTimeType(3);
			ZoneIndicatorDicVO mZidVO11 = new ZoneIndicatorDicVO();
			mZidVO11.setItemCode("DMMNBFW");
			mZidVO11.setItemName("爆管次数");
			mZidVO11.setTimeType(3);
			ZoneIndicatorDicVO mZidVO12 = new ZoneIndicatorDicVO();
			mZidVO12.setItemCode("DMMBLFW");
			mZidVO12.setItemName("渗漏次数");
			mZidVO12.setTimeType(3);
			ZoneIndicatorDicVO mZidVO13 = new ZoneIndicatorDicVO();
			mZidVO13.setItemCode("DMMBRFW");
			mZidVO13.setItemName("爆管率");
			mZidVO13.setTimeType(3);
			ZoneIndicatorDicVO mZidVO14 = new ZoneIndicatorDicVO();
			mZidVO14.setItemCode("DMMLRFW");
			mZidVO14.setItemName("渗漏率");
			mZidVO14.setTimeType(3);
			ZoneIndicatorDicVO mZidVO15 = new ZoneIndicatorDicVO();
			mZidVO15.setItemCode("DMMNOCM");
			mZidVO15.setItemName("用户表数");
			mZidVO15.setTimeType(3);
			ZoneIndicatorDicVO mZidVO16 = new ZoneIndicatorDicVO();
			mZidVO16.setItemCode("DMMFTPL");
			mZidVO16.setItemName("总管长");
			mZidVO16.setTimeType(3);
			
			//年指标
			ZoneIndicatorDicVO yZidVO = new ZoneIndicatorDicVO();
			yZidVO.setItemCode("DMYUFWC");
			yZidVO.setItemName("未计量水量");
			yZidVO.setTimeType(4);
			ZoneIndicatorDicVO yZidVO1 = new ZoneIndicatorDicVO();
			yZidVO1.setItemCode("DMYUFWCCA");
			yZidVO1.setItemName("单位户数未计量水量");
			yZidVO1.setTimeType(4);
			ZoneIndicatorDicVO yZidVO2 = new ZoneIndicatorDicVO();
			yZidVO2.setItemCode("DMYUFWCPL");
			yZidVO2.setItemName("单位管长未计量水量");
			yZidVO2.setTimeType(4);
			ZoneIndicatorDicVO yZidVO3 = new ZoneIndicatorDicVO();
			yZidVO3.setItemCode("DMYUCRFW");
			yZidVO3.setItemName("未计量占比");
			yZidVO3.setTimeType(4);
			ZoneIndicatorDicVO yZidVO4 = new ZoneIndicatorDicVO();
			yZidVO4.setItemCode("DMYNRW");
			yZidVO4.setItemName("产销差");
			yZidVO4.setTimeType(4);
			ZoneIndicatorDicVO yZidVO5 = new ZoneIndicatorDicVO();
			yZidVO5.setItemCode("DMYNRWCA");
			yZidVO5.setItemName("单位户数产销差");
			yZidVO5.setTimeType(4);
			ZoneIndicatorDicVO yZidVO6 = new ZoneIndicatorDicVO();
			yZidVO6.setItemCode("DMYNRWPL");
			yZidVO6.setItemName("单位管长产销差");
			yZidVO6.setTimeType(4);
			ZoneIndicatorDicVO yZidVO7 = new ZoneIndicatorDicVO();
			yZidVO7.setItemCode("DMYNRR");
			yZidVO7.setItemName("产销差率");
			yZidVO7.setTimeType(4);
			ZoneIndicatorDicVO yZidVO8 = new ZoneIndicatorDicVO();
			yZidVO8.setItemCode("DMYFWSSITDF");
			yZidVO8.setItemName("日均供水量");
			yZidVO8.setTimeType(4);
			ZoneIndicatorDicVO yZidVO9 = new ZoneIndicatorDicVO();
			yZidVO9.setItemCode("DMYBMC");
			yZidVO9.setItemName("计量水量");
			yZidVO9.setTimeType(4);
			ZoneIndicatorDicVO yZidVO10 = new ZoneIndicatorDicVO();
			yZidVO10.setItemCode("DMYBC");
			yZidVO10.setItemName("计费水量");
			yZidVO10.setTimeType(4);
			ZoneIndicatorDicVO yZidVO11 = new ZoneIndicatorDicVO();
			yZidVO11.setItemCode("DMYNBFW");
			yZidVO11.setItemName("爆管次数");
			yZidVO11.setTimeType(4);
			ZoneIndicatorDicVO yZidVO12 = new ZoneIndicatorDicVO();
			yZidVO12.setItemCode("DMYBLFW");
			yZidVO12.setItemName("渗漏次数");
			yZidVO12.setTimeType(4);
			ZoneIndicatorDicVO yZidVO13 = new ZoneIndicatorDicVO();
			yZidVO13.setItemCode("DMYBRFW");
			yZidVO13.setItemName("爆管率");
			mZidVO13.setTimeType(4);
			ZoneIndicatorDicVO yZidVO14 = new ZoneIndicatorDicVO();
			yZidVO14.setItemCode("DMYLRFW");
			yZidVO14.setItemName("渗漏率");
			yZidVO14.setTimeType(4);
			ZoneIndicatorDicVO yZidVO15 = new ZoneIndicatorDicVO();
			yZidVO15.setItemCode("DMYNOCM");
			yZidVO15.setItemName("用户表数");
			yZidVO15.setTimeType(4);
			ZoneIndicatorDicVO yZidVO16 = new ZoneIndicatorDicVO();
			yZidVO16.setItemCode("DMYFTPL");
			yZidVO16.setItemName("总管长");
			yZidVO16.setTimeType(4);
			lists.add(dZidVO);
			lists.add(dZidVO1);
			lists.add(dZidVO2);
			lists.add(dZidVO3);
			lists.add(dZidVO4);
			lists.add(dZidVO5);
			lists.add(dZidVO6);
			lists.add(dZidVO7);
			lists.add(dZidVO8);
			lists.add(dZidVO9);
			lists.add(dZidVO10);
			lists.add(dZidVO11);
			lists.add(dZidVO12);
			lists.add(dZidVO13);
			lists.add(dZidVO14);
			lists.add(dZidVO15);
			lists.add(dZidVO16);
			lists.add(mZidVO);
			lists.add(yZidVO1);
			lists.add(mZidVO2);
			lists.add(mZidVO3);
			lists.add(mZidVO4);
			lists.add(mZidVO5);
			lists.add(mZidVO6);
			lists.add(mZidVO7);
			lists.add(mZidVO8);
			lists.add(mZidVO9);
			lists.add(mZidVO10);
			lists.add(mZidVO11);
			lists.add(mZidVO12);
			lists.add(mZidVO13);
			lists.add(mZidVO14);
			lists.add(mZidVO15);
			lists.add(mZidVO16);
			lists.add(yZidVO);
			lists.add(yZidVO1);
			lists.add(yZidVO2);
			lists.add(yZidVO3);
			lists.add(yZidVO4);
			lists.add(yZidVO5);
			lists.add(yZidVO6);
			lists.add(yZidVO7);
			lists.add(yZidVO8);
			lists.add(yZidVO9);
			lists.add(yZidVO10);
			lists.add(yZidVO11);
			lists.add(yZidVO12);
			lists.add(yZidVO13);
			lists.add(yZidVO14);
			lists.add(yZidVO15);
			lists.add(yZidVO16);
		}else if(Constant.RANK_VZ.equals(zoneType)) {
			//虚拟分区
			//日指标
			ZoneIndicatorDicVO dZidVO = new ZoneIndicatorDicVO();
			dZidVO.setItemCode("VZDUFWC");
			dZidVO.setItemName("未计量水量");
			dZidVO.setTimeType(2);
			ZoneIndicatorDicVO dZidVO1 = new ZoneIndicatorDicVO();
			dZidVO1.setItemCode("VZDUFWCCA");
			dZidVO1.setItemName("单位户数未计量水量");
			dZidVO1.setTimeType(2);
			ZoneIndicatorDicVO dZidVO2 = new ZoneIndicatorDicVO();
			dZidVO2.setItemCode("VZDUFWCPL");
			dZidVO2.setItemName("单位管长未计量水量");
			dZidVO2.setTimeType(2);
			ZoneIndicatorDicVO dZidVO3 = new ZoneIndicatorDicVO();
			dZidVO3.setItemCode("VZDUCRFW");
			dZidVO3.setItemName("未计量占比");
			dZidVO3.setTimeType(2);
			ZoneIndicatorDicVO dZidVO4 = new ZoneIndicatorDicVO();
			dZidVO4.setItemCode("VZDNRW");
			dZidVO4.setItemName("产销差");
			dZidVO4.setTimeType(2);
			ZoneIndicatorDicVO dZidVO5 = new ZoneIndicatorDicVO();
			dZidVO5.setItemCode("VZDNRWCA");
			dZidVO5.setItemName("单位户数产销差");
			dZidVO5.setTimeType(2);
			ZoneIndicatorDicVO dZidVO6 = new ZoneIndicatorDicVO();
			dZidVO6.setItemCode("VZDNRWPL");
			dZidVO6.setItemName("单位管长产销差");
			dZidVO6.setTimeType(2);
			ZoneIndicatorDicVO dZidVO7 = new ZoneIndicatorDicVO();
			dZidVO7.setItemCode("VZDNRR");
			dZidVO7.setItemName("产销差率");
			dZidVO7.setTimeType(2);
			ZoneIndicatorDicVO dZidVO8 = new ZoneIndicatorDicVO();
			dZidVO8.setItemCode("VZDFWSSITDF");
			dZidVO8.setItemName("日均供水量");
			dZidVO8.setTimeType(2);
			ZoneIndicatorDicVO dZidVO9 = new ZoneIndicatorDicVO();
			dZidVO9.setItemCode("VZDBMC");
			dZidVO9.setItemName("计量水量");
			dZidVO9.setTimeType(2);
			ZoneIndicatorDicVO dZidVO10 = new ZoneIndicatorDicVO();
			dZidVO10.setItemCode("VZDBC");
			dZidVO10.setItemName("计费水量");
			dZidVO10.setTimeType(2);
			ZoneIndicatorDicVO dZidVO11 = new ZoneIndicatorDicVO();
			dZidVO11.setItemCode("VZDNBFW");
			dZidVO11.setItemName("爆管次数");
			dZidVO11.setTimeType(2);
			ZoneIndicatorDicVO dZidVO12 = new ZoneIndicatorDicVO();
			dZidVO12.setItemCode("VZDBLFW");
			dZidVO12.setItemName("渗漏次数");
			dZidVO12.setTimeType(2);
			ZoneIndicatorDicVO dZidVO13 = new ZoneIndicatorDicVO();
			dZidVO13.setItemCode("VZDBRFW");
			dZidVO13.setItemName("爆管率");
			dZidVO13.setTimeType(2);
			ZoneIndicatorDicVO dZidVO14 = new ZoneIndicatorDicVO();
			dZidVO14.setItemCode("VZDLRFW");
			dZidVO14.setItemName("渗漏率");
			dZidVO14.setTimeType(2);
			ZoneIndicatorDicVO dZidVO15 = new ZoneIndicatorDicVO();
			dZidVO15.setItemCode("VZMNOCM");
			dZidVO15.setItemName("用户表数");
			dZidVO15.setTimeType(2);
			ZoneIndicatorDicVO dZidVO16 = new ZoneIndicatorDicVO();
			dZidVO16.setItemCode("VZMFTPL");
			dZidVO16.setItemName("总管长");
			dZidVO16.setTimeType(2);
			
			//月指标
			ZoneIndicatorDicVO mZidVO = new ZoneIndicatorDicVO();
			mZidVO.setItemCode("VZMUFWC");
			mZidVO.setItemName("未计量水量");
			mZidVO.setTimeType(3);
			ZoneIndicatorDicVO mZidVO1 = new ZoneIndicatorDicVO();
			mZidVO1.setItemCode("VZMUFWCCA");
			mZidVO1.setItemName("单位户数未计量水量");
			mZidVO1.setTimeType(3);
			ZoneIndicatorDicVO mZidVO2 = new ZoneIndicatorDicVO();
			mZidVO2.setItemCode("VZMUFWCPL");
			mZidVO2.setItemName("单位管长未计量水量");
			mZidVO2.setTimeType(3);
			ZoneIndicatorDicVO mZidVO3 = new ZoneIndicatorDicVO();
			mZidVO3.setItemCode("VZMUCRFW");
			mZidVO3.setItemName("未计量占比");
			mZidVO3.setTimeType(3);
			ZoneIndicatorDicVO mZidVO4 = new ZoneIndicatorDicVO();
			mZidVO4.setItemCode("VZMNRW");
			mZidVO4.setItemName("产销差");
			mZidVO4.setTimeType(3);
			ZoneIndicatorDicVO mZidVO5 = new ZoneIndicatorDicVO();
			mZidVO5.setItemCode("VZMNRWCA");
			mZidVO5.setItemName("单位户数产销差");
			mZidVO5.setTimeType(3);
			ZoneIndicatorDicVO mZidVO6 = new ZoneIndicatorDicVO();
			mZidVO6.setItemCode("VZMNRWPL");
			mZidVO6.setItemName("单位管长产销差");
			mZidVO6.setTimeType(3);
			ZoneIndicatorDicVO mZidVO7 = new ZoneIndicatorDicVO();
			mZidVO7.setItemCode("VZMNRR");
			mZidVO7.setItemName("产销差率");
			mZidVO7.setTimeType(3);
			ZoneIndicatorDicVO mZidVO8 = new ZoneIndicatorDicVO();
			mZidVO8.setItemCode("VZMFWSSITDF");
			mZidVO8.setItemName("日均供水量");
			mZidVO8.setTimeType(3);
			ZoneIndicatorDicVO mZidVO9 = new ZoneIndicatorDicVO();
			mZidVO9.setItemCode("VZMBMC");
			mZidVO9.setItemName("计量水量");
			mZidVO9.setTimeType(3);
			ZoneIndicatorDicVO mZidVO10 = new ZoneIndicatorDicVO();
			mZidVO10.setItemCode("VZMBC");
			mZidVO10.setItemName("计费水量");
			mZidVO10.setTimeType(3);
			ZoneIndicatorDicVO mZidVO11 = new ZoneIndicatorDicVO();
			mZidVO11.setItemCode("VZMNBFW");
			mZidVO11.setItemName("爆管次数");
			mZidVO11.setTimeType(3);
			ZoneIndicatorDicVO mZidVO12 = new ZoneIndicatorDicVO();
			mZidVO12.setItemCode("VZMBLFW");
			mZidVO12.setItemName("渗漏次数");
			mZidVO12.setTimeType(3);
			ZoneIndicatorDicVO mZidVO13 = new ZoneIndicatorDicVO();
			mZidVO13.setItemCode("VZMBRFW");
			mZidVO13.setItemName("爆管率");
			mZidVO13.setTimeType(3);
			ZoneIndicatorDicVO mZidVO14 = new ZoneIndicatorDicVO();
			mZidVO14.setItemCode("VZMLRFW");
			mZidVO14.setItemName("渗漏率");
			mZidVO14.setTimeType(3);
			ZoneIndicatorDicVO mZidVO15 = new ZoneIndicatorDicVO();
			mZidVO15.setItemCode("VZMNOCM");
			mZidVO15.setItemName("用户表数");
			mZidVO15.setTimeType(3);
			ZoneIndicatorDicVO mZidVO16 = new ZoneIndicatorDicVO();
			mZidVO16.setItemCode("VZMFTPL");
			mZidVO16.setItemName("总管长");
			mZidVO16.setTimeType(3);
			
			//年指标
			ZoneIndicatorDicVO yZidVO = new ZoneIndicatorDicVO();
			yZidVO.setItemCode("VZYUFWC");
			yZidVO.setItemName("未计量水量");
			yZidVO.setTimeType(4);
			ZoneIndicatorDicVO yZidVO1 = new ZoneIndicatorDicVO();
			yZidVO1.setItemCode("VZYUFWCCA");
			yZidVO1.setItemName("单位户数未计量水量");
			yZidVO1.setTimeType(4);
			ZoneIndicatorDicVO yZidVO2 = new ZoneIndicatorDicVO();
			yZidVO2.setItemCode("VZYUFWCPL");
			yZidVO2.setItemName("单位管长未计量水量");
			yZidVO2.setTimeType(4);
			ZoneIndicatorDicVO yZidVO3 = new ZoneIndicatorDicVO();
			yZidVO3.setItemCode("VZYUCRFW");
			yZidVO3.setItemName("未计量占比");
			yZidVO3.setTimeType(4);
			ZoneIndicatorDicVO yZidVO4 = new ZoneIndicatorDicVO();
			yZidVO4.setItemCode("VZYNRW");
			yZidVO4.setItemName("产销差");
			yZidVO4.setTimeType(4);
			ZoneIndicatorDicVO yZidVO5 = new ZoneIndicatorDicVO();
			yZidVO5.setItemCode("VZYNRWCA");
			yZidVO5.setItemName("单位户数产销差");
			yZidVO5.setTimeType(4);
			ZoneIndicatorDicVO yZidVO6 = new ZoneIndicatorDicVO();
			yZidVO6.setItemCode("VZYNRWPL");
			yZidVO6.setItemName("单位管长产销差");
			yZidVO6.setTimeType(4);
			ZoneIndicatorDicVO yZidVO7 = new ZoneIndicatorDicVO();
			yZidVO7.setItemCode("VZYNRR");
			yZidVO7.setItemName("产销差率");
			yZidVO7.setTimeType(4);
			ZoneIndicatorDicVO yZidVO8 = new ZoneIndicatorDicVO();
			yZidVO8.setItemCode("VZYFWSSITDF");
			yZidVO8.setItemName("日均供水量");
			yZidVO8.setTimeType(4);
			ZoneIndicatorDicVO yZidVO9 = new ZoneIndicatorDicVO();
			yZidVO9.setItemCode("VZYBMC");
			yZidVO9.setItemName("计量水量");
			yZidVO9.setTimeType(4);
			ZoneIndicatorDicVO yZidVO10 = new ZoneIndicatorDicVO();
			yZidVO10.setItemCode("VZYBC");
			yZidVO10.setItemName("计费水量");
			yZidVO10.setTimeType(4);
			ZoneIndicatorDicVO yZidVO11 = new ZoneIndicatorDicVO();
			yZidVO11.setItemCode("VZYNBFW");
			yZidVO11.setItemName("爆管次数");
			yZidVO11.setTimeType(4);
			ZoneIndicatorDicVO yZidVO12 = new ZoneIndicatorDicVO();
			yZidVO12.setItemCode("VZYBLFW");
			yZidVO12.setItemName("渗漏次数");
			yZidVO12.setTimeType(4);
			ZoneIndicatorDicVO yZidVO13 = new ZoneIndicatorDicVO();
			yZidVO13.setItemCode("VZYBRFW");
			yZidVO13.setItemName("爆管率");
			mZidVO13.setTimeType(4);
			ZoneIndicatorDicVO yZidVO14 = new ZoneIndicatorDicVO();
			yZidVO14.setItemCode("VZYLRFW");
			yZidVO14.setItemName("渗漏率");
			yZidVO14.setTimeType(4);
			ZoneIndicatorDicVO yZidVO15 = new ZoneIndicatorDicVO();
			yZidVO15.setItemCode("VZYNOCM");
			yZidVO15.setItemName("用户表数");
			yZidVO15.setTimeType(4);
			ZoneIndicatorDicVO yZidVO16 = new ZoneIndicatorDicVO();
			yZidVO16.setItemCode("VZYFTPL");
			yZidVO16.setItemName("总管长");
			yZidVO16.setTimeType(4);
			lists.add(dZidVO);
			lists.add(dZidVO1);
			lists.add(dZidVO2);
			lists.add(dZidVO3);
			lists.add(dZidVO4);
			lists.add(dZidVO5);
			lists.add(dZidVO6);
			lists.add(dZidVO7);
			lists.add(dZidVO8);
			lists.add(dZidVO9);
			lists.add(dZidVO10);
			lists.add(dZidVO11);
			lists.add(dZidVO12);
			lists.add(dZidVO13);
			lists.add(dZidVO14);
			lists.add(dZidVO15);
			lists.add(dZidVO16);
			lists.add(mZidVO);
			lists.add(yZidVO1);
			lists.add(mZidVO2);
			lists.add(mZidVO3);
			lists.add(mZidVO4);
			lists.add(mZidVO5);
			lists.add(mZidVO6);
			lists.add(mZidVO7);
			lists.add(mZidVO8);
			lists.add(mZidVO9);
			lists.add(mZidVO10);
			lists.add(mZidVO11);
			lists.add(mZidVO12);
			lists.add(mZidVO13);
			lists.add(mZidVO14);
			lists.add(mZidVO15);
			lists.add(mZidVO16);
			lists.add(yZidVO);
			lists.add(yZidVO1);
			lists.add(yZidVO2);
			lists.add(yZidVO3);
			lists.add(yZidVO4);
			lists.add(yZidVO5);
			lists.add(yZidVO6);
			lists.add(yZidVO7);
			lists.add(yZidVO8);
			lists.add(yZidVO9);
			lists.add(yZidVO10);
			lists.add(yZidVO11);
			lists.add(yZidVO12);
			lists.add(yZidVO13);
			lists.add(yZidVO14);
			lists.add(yZidVO15);
			lists.add(yZidVO16);
		}
		return lists;
	}

	@TaskAnnotation("queryBurstLeakAnalysis")
	@Override
	public List<BurstLeakAnalysisVO> queryBurstLeakAnalysis(SessionFactory factory) {
		ZoneLossAnaMapper mapper = factory.getMapper(ZoneLossAnaMapper.class);
		List<BurstLeakAnalysisVO> result = mapper.queryBurstLeakAnalysis();
		for (BurstLeakAnalysisVO burstLeakAnalysisVO : result) {
			String xy = burstLeakAnalysisVO.getXy();
			String[] split = xy.split(",");
			burstLeakAnalysisVO.setSmGeometry(split);
		}
		return result;
	}

	@TaskAnnotation("queryBurstLeakById")
	@Override
	public LeakDetailsVO queryBurstLeakById(SessionFactory factory, String id) {
		ZoneLossAnaMapper mapper = factory.getMapper(ZoneLossAnaMapper.class);
		LeakDetailsVO leakDetailsVO = mapper.queryBurstLeakById(id);
		return leakDetailsVO;
	}

	@TaskAnnotation("queryBurstLeakList")
	@Override
	public PageListVO<List<LeakDetailsVO>> queryBurstLeakList(SessionFactory factory, QueryLeakListDTO queryLeakListDTO) {
		
		ZoneLossAnaMapper mapper = factory.getMapper(ZoneLossAnaMapper.class);
		// 查询总条数
		int rowNumber = mapper.countBurstLeak(queryLeakListDTO);
		//判断分页
		if(rowNumber<(queryLeakListDTO.getPage()-1)*queryLeakListDTO.getPageCount()) return null;
				
		// 查询列表数据
		List<LeakDetailsVO> dataLists = mapper.queryBurstLeakList(queryLeakListDTO);
				
		
		// 返回数据结果
		PageListVO<List<LeakDetailsVO>> result = new PageListVO<>();
		result.setDataList(dataLists);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryLeakListDTO.getPage(), queryLeakListDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}
	
}
