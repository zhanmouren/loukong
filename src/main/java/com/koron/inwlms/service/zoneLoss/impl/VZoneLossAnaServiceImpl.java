package com.koron.inwlms.service.zoneLoss.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddVCZoneDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVCZoneListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVSZoneListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVZoneInfoDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.zoneLoss.VCZoneListVO;
import com.koron.inwlms.bean.VO.zoneLoss.VSZoneListVO;
import com.koron.inwlms.bean.VO.zoneLoss.VZoneInfoVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.zoneLoss.VZoneLossAnaMapper;
import com.koron.inwlms.mapper.zoneLoss.ZoneLossAnaMapper;
import com.koron.inwlms.service.common.impl.GisZoneServiceImpl;
import com.koron.inwlms.service.zoneLoss.VZoneLossAnaService;
import com.koron.inwlms.util.PageUtil;
import com.koron.inwlms.util.TimeUtil;
import com.koron.util.Constant;

/**
 * 虚拟分区分析接口实现层
 * @author csh
 * @Date 2020/04/13
 *
 */
@Service
public class VZoneLossAnaServiceImpl implements VZoneLossAnaService {

	@TaskAnnotation("queryVSZoneList")
	@Override
	public PageListVO<List<VSZoneListVO>> queryVSZoneList(SessionFactory factory,
			QueryVSZoneListDTO queryVSZoneListDTO) {
		//查询虚拟分区（相减）信息
		GisZoneServiceImpl gzsImpl = new GisZoneServiceImpl();
		List<String> vZoneCodes = new ArrayList<>();
		QueryVZoneInfoDTO queryVZoneInfoDTO = new QueryVZoneInfoDTO();
		queryVZoneInfoDTO.setZoneNo(queryVSZoneListDTO.getvZoneNo());
		queryVZoneInfoDTO.setZoneType(2);
		List<VZoneInfoVO> vZoneInfos = gzsImpl.queryVZoneInfo(factory,queryVZoneInfoDTO);
		//判空，及判断分页
		if(vZoneInfos == null || vZoneInfos.size()<(queryVSZoneListDTO.getPage()-1)*queryVSZoneListDTO.getPageCount()) return null;
				
		for(VZoneInfoVO zoneInfo : vZoneInfos) {
			vZoneCodes.add(zoneInfo.getZoneNo());
		}
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		IndicatorDTO indicatorDTO = new IndicatorDTO();
		indicatorDTO.setStartTime(queryVSZoneListDTO.getStartTime());
		indicatorDTO.setEndTime(queryVSZoneListDTO.getEndTime());
		indicatorDTO.setZoneCodes(vZoneCodes);
		indicatorDTO.setTimeType(queryVSZoneListDTO.getTimeType());
		List<String> baseCodes = new ArrayList<>(); //基础指标编码集合
		List<String> wbCodes = new ArrayList<>(); //水平衡基础指标编码集合
		List<String> zoneCodes = new ArrayList<>();  //分区漏损指标编码集合
		if(Constant.TIME_TYPE_M.equals(queryVSZoneListDTO.getTimeType())) {
			baseCodes.add("VZMNOCM"); //虚拟分区月用户数指标编码
			baseCodes.add("VZMFTPL"); //虚拟分区月管长数指标编码
			wbCodes.add("VZMFWSSITDF"); //虚拟分区月供水量指标编码
			wbCodes.add("VZMBC"); //虚拟分区月计费用水量指标编码
			wbCodes.add("VZMWL"); //虚拟分区月漏损量指标编码
			zoneCodes.add("VZMLCA"); //虚拟分区月单位户数漏损量
			zoneCodes.add("VZMLPL"); //虚拟分区月单位管长漏损量
			zoneCodes.add("VZMWLR"); //虚拟分区月漏损率
			zoneCodes.add("VZMMNF"); //虚拟分区月MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("VZMMNFTDF"); //虚拟分区月MNF/TDF
			
		}else {
			baseCodes.add("VZYNOCM");//虚拟分区年用户数指标编码
			baseCodes.add("VZYFTPL");//虚拟分区年管长数指标编码
			wbCodes.add("VZYFWSSITDF"); //虚拟分区年供水量指标编码
			wbCodes.add("VZYBC"); //虚拟分区年计费用水量指标编码
			wbCodes.add("VZYWL"); //虚拟分区年漏损量指标编码
			zoneCodes.add("VZYLCA"); //虚拟分区年单位户数漏损量
			zoneCodes.add("VZYLPL"); //虚拟分区年单位管长漏损量
			zoneCodes.add("VZYWLR"); //虚拟分区年漏损率
			zoneCodes.add("VZYMNF"); //虚拟分区年MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("VZYMNFTDF"); //虚拟分区年MNF/TDF
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
		
		List<VSZoneListVO> dataList = new ArrayList<>();
		for (int i = 0;i<vZoneInfos.size();i++) {
			if(i < (queryVSZoneListDTO.getPage()-1)*queryVSZoneListDTO.getPageCount()) continue;
			if(dataList.size() >= queryVSZoneListDTO.getPageCount()) break;
			VSZoneListVO vSZoneListVO = new VSZoneListVO();
			vSZoneListVO.setZoneNo(vZoneInfos.get(i).getZoneNo());
			vSZoneListVO.setZoneName(vZoneInfos.get(i).getZoneName());
//			vSZoneListVO.setZoneRank(Constant.RANK_F);
			vSZoneListVO.setpZoneNo(vZoneInfos.get(i).getpZoneNo());
			vSZoneListVO.setpZoneName(vZoneInfos.get(i).getpZoneName());
			vSZoneListVO.setAddress(vZoneInfos.get(i).getAddress());
			//TODO 分区警报查询
			vSZoneListVO.setAlarmStatus(0);
			
			int meterNum = 0;
			double pipeLength = 0;
			double flow = 0;
			double useFlow = 0;
			double lossFlow = 0;
			double perUserLossFlow = 0; //虚拟分区月单位户数漏损量
			double perLengthLossFlow = 0; //虚拟分区月单位管长漏损量
			double lossRate = 0; //虚拟分区月漏损率
			double mnf = 0; //虚拟分区月MNF
			double mnfTime = 0; //虚拟分区月MNF TIME
			double mnfTdf = 0; //虚拟分区月MNF/TDF
			if(Constant.TIME_TYPE_M.equals(queryVSZoneListDTO.getTimeType())) {
				for(IndicatorVO baseIndic : baseIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "VZMNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "VZMFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZMFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZMBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZMWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}else {
				for(IndicatorVO baseIndic : baseIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "VZYNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "VZYFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZYFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZYBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZYWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}
			DecimalFormat df = new DecimalFormat("#.0000");
			vSZoneListVO.setMeterNum(meterNum/timeNum);
			vSZoneListVO.setPipeLength(pipeLength/timeNum);
			vSZoneListVO.setFlow(flow);
			vSZoneListVO.setUseFlow(useFlow);
			vSZoneListVO.setLossFlow(lossFlow);
			vSZoneListVO.setPerUserLossFlow(Double.parseDouble(df.format(perUserLossFlow/timeNum)));
			vSZoneListVO.setPerLengthLossFlow(Double.parseDouble(df.format(perLengthLossFlow/timeNum)));
			vSZoneListVO.setLossRate(Double.parseDouble(df.format(lossRate/timeNum)));
			vSZoneListVO.setMnf(mnf);
			vSZoneListVO.setMnfTdf(Double.parseDouble(df.format(mnfTdf/timeNum)));
			dataList.add(vSZoneListVO);
			
		}
		PageListVO<List<VSZoneListVO>> result = new PageListVO<>();
		result.setDataList(dataList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryVSZoneListDTO.getPage(), queryVSZoneListDTO.getPageCount(), vZoneInfos.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("queryVCZoneList")
	@Override
	public PageListVO<List<VCZoneListVO>> queryVCZoneList(SessionFactory factory,
			QueryVCZoneListDTO queryVCZoneListDTO) {
		//查询虚拟分区（合并）
		GisZoneServiceImpl gzsImpl = new GisZoneServiceImpl();
		List<String> vZoneCodes = new ArrayList<>();
		QueryVZoneInfoDTO queryVZoneInfoDTO = new QueryVZoneInfoDTO();
		queryVZoneInfoDTO.setZoneNo(queryVCZoneListDTO.getvZoneNo());
		queryVZoneInfoDTO.setZoneType(1);
		List<VZoneInfoVO> vZoneInfos = gzsImpl.queryVZoneInfo(factory,queryVZoneInfoDTO);
		//判空，及判断分页
		if(vZoneInfos == null || vZoneInfos.size()<(queryVCZoneListDTO.getPage()-1)*queryVCZoneListDTO.getPageCount()) return null;
				
		for(VZoneInfoVO zoneInfo : vZoneInfos) {
			vZoneCodes.add(zoneInfo.getZoneNo());
		}
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		IndicatorDTO indicatorDTO = new IndicatorDTO();
		indicatorDTO.setStartTime(queryVCZoneListDTO.getStartTime());
		indicatorDTO.setEndTime(queryVCZoneListDTO.getEndTime());
		indicatorDTO.setZoneCodes(vZoneCodes);
		indicatorDTO.setTimeType(queryVCZoneListDTO.getTimeType());
		List<String> baseCodes = new ArrayList<>(); //基础指标编码集合
		List<String> wbCodes = new ArrayList<>(); //水平衡基础指标编码集合
		List<String> zoneCodes = new ArrayList<>();  //分区漏损指标编码集合
		if(Constant.TIME_TYPE_M.equals(queryVCZoneListDTO.getTimeType())) {
			baseCodes.add("VZMNOCM"); //虚拟分区月用户数指标编码
			baseCodes.add("VZMFTPL"); //虚拟分区月管长数指标编码
			wbCodes.add("VZMFWSSITDF"); //虚拟分区月供水量指标编码
			wbCodes.add("VZMBC"); //虚拟分区月计费用水量指标编码
			wbCodes.add("VZMWL"); //虚拟分区月漏损量指标编码
			zoneCodes.add("VZMLCA"); //虚拟分区月单位户数漏损量
			zoneCodes.add("VZMLPL"); //虚拟分区月单位管长漏损量
			zoneCodes.add("VZMWLR"); //虚拟分区月漏损率
			zoneCodes.add("VZMMNF"); //虚拟分区月MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("VZMMNFTDF"); //虚拟分区月MNF/TDF
			
		}else {
			baseCodes.add("VZYNOCM");//虚拟分区年用户数指标编码
			baseCodes.add("VZYFTPL");//虚拟分区年管长数指标编码
			wbCodes.add("VZYFWSSITDF"); //虚拟分区年供水量指标编码
			wbCodes.add("VZYBC"); //虚拟分区年计费用水量指标编码
			wbCodes.add("VZYWL"); //虚拟分区年漏损量指标编码
			zoneCodes.add("VZYLCA"); //虚拟分区年单位户数漏损量
			zoneCodes.add("VZYLPL"); //虚拟分区年单位管长漏损量
			zoneCodes.add("VZYWLR"); //虚拟分区年漏损率
			zoneCodes.add("VZYMNF"); //虚拟分区年MNF
			//TODO 月指标没有MNF TIME
			zoneCodes.add("VZYMNFTDF"); //虚拟分区年MNF/TDF
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
		
		List<VCZoneListVO> dataList = new ArrayList<>();
		for (int i = 0;i<vZoneInfos.size();i++) {
			if(i < (queryVCZoneListDTO.getPage()-1)*queryVCZoneListDTO.getPageCount()) continue;
			if(dataList.size() >= queryVCZoneListDTO.getPageCount()) break;
			VCZoneListVO vCZoneListVO = new VCZoneListVO();
			vCZoneListVO.setZoneNo(vZoneInfos.get(i).getZoneNo());
			vCZoneListVO.setZoneName(vZoneInfos.get(i).getZoneName());
//			vCZoneListVO.setZoneRank(Constant.RANK_F);
			vCZoneListVO.setpZoneNo(vZoneInfos.get(i).getpZoneNo());
			vCZoneListVO.setpZoneName(vZoneInfos.get(i).getpZoneName());
			vCZoneListVO.setAddress(vZoneInfos.get(i).getAddress());
			//TODO 分区警报查询
			vCZoneListVO.setAlarmStatus(0);
			
			int meterNum = 0;
			double pipeLength = 0;
			double flow = 0;
			double useFlow = 0;
			double lossFlow = 0;
			double perUserLossFlow = 0; //虚拟分区月单位户数漏损量
			double perLengthLossFlow = 0; //虚拟分区月单位管长漏损量
			double lossRate = 0; //虚拟分区月漏损率
			double mnf = 0; //虚拟分区月MNF
			double mnfTime = 0; //虚拟分区月MNF TIME
			double mnfTdf = 0; //虚拟分区月MNF/TDF
			if(Constant.TIME_TYPE_M.equals(queryVCZoneListDTO.getTimeType())) {
				for(IndicatorVO baseIndic : baseIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "VZMNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "VZMFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZMFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZMBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZMWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZMMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}else {
				for(IndicatorVO baseIndic : baseIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "VZYNOCM".equals(baseIndic.getCode())) {
						meterNum += baseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(baseIndic.getZoneNo()) && "VZYFTPL".equals(baseIndic.getCode())) {
						pipeLength += baseIndic.getValue();
					}
				}
				
				for(IndicatorVO wbBaseIndic : wbBaseIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZYFWSSITDF".equals(wbBaseIndic.getCode())) {
						flow += wbBaseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZYBC".equals(wbBaseIndic.getCode())) {
						useFlow += wbBaseIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(wbBaseIndic.getZoneNo()) && "VZYWL".equals(wbBaseIndic.getCode())) {
						lossFlow += wbBaseIndic.getValue();
					}
				}
				
				for(IndicatorVO zoneIndic: zoneIndics) {
					if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYLCA".equals(zoneIndic.getCode())) {
						perUserLossFlow += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYLPL".equals(zoneIndic.getCode())) {
						perLengthLossFlow += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYWLR".equals(zoneIndic.getCode())) {
						lossRate += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYMNF".equals(zoneIndic.getCode())) {
						mnf += zoneIndic.getValue();
					}else if(vZoneInfos.get(i).getZoneNo().equals(zoneIndic.getZoneNo()) && "VZYMNFTDF".equals(zoneIndic.getCode())) {
						mnfTdf += zoneIndic.getValue();
					}
				}
			}
			DecimalFormat df = new DecimalFormat("#.0000");
			vCZoneListVO.setMeterNum(meterNum/timeNum);
			vCZoneListVO.setPipeLength(pipeLength/timeNum);
			vCZoneListVO.setFlow(flow);
			vCZoneListVO.setUseFlow(useFlow);
			vCZoneListVO.setLossFlow(lossFlow);
			vCZoneListVO.setPerUserLossFlow(Double.parseDouble(df.format(perUserLossFlow/timeNum)));
			vCZoneListVO.setPerLengthLossFlow(Double.parseDouble(df.format(perLengthLossFlow/timeNum)));
			vCZoneListVO.setLossRate(Double.parseDouble(df.format(lossRate/timeNum)));
			vCZoneListVO.setMnf(mnf);
			vCZoneListVO.setMnfTdf(Double.parseDouble(df.format(mnfTdf/timeNum)));
			dataList.add(vCZoneListVO);
			
		}
		PageListVO<List<VCZoneListVO>> result = new PageListVO<>();
		result.setDataList(dataList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryVCZoneListDTO.getPage(), queryVCZoneListDTO.getPageCount(), vZoneInfos.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	/**
	 * 调用gis接口，完成删除虚拟分区操作
	 */
	@TaskAnnotation("deleteVCZone")
	@Override
	public void deleteVCZone(SessionFactory factory, String vZoneNo) {
		VZoneLossAnaMapper mapper = factory.getMapper(VZoneLossAnaMapper.class);
		mapper.deleteVCZone(vZoneNo);
	}

	/**
	 * 调用gis接口，完成添加虚拟分区操作
	 */
	@TaskAnnotation("addVCZone")
	@Override
	public void addVCZone(SessionFactory factory, AddVCZoneDTO addVCZoneDTO) {
		VZoneLossAnaMapper mapper = factory.getMapper(VZoneLossAnaMapper.class);
		Integer code = mapper.querySeqVzoneCode();
		String zoneNo = "VZ"+code;
		addVCZoneDTO.setvZoneNo(zoneNo);
		addVCZoneDTO.setvZoneName("虚拟分区"+code);
		addVCZoneDTO.setZoneType(1);
		String zoneNos = addVCZoneDTO.getsZoneNos();
		String[] split = zoneNos.split(",");
		for (int i = 0; i<split.length; i++) {
			if(i%2==1) {
				addVCZoneDTO.setSecCode(split[i]);
				mapper.addVCZone(addVCZoneDTO);
			}else {
				addVCZoneDTO.setMasCode(split[i]);
				if(i == split.length-1) {
					mapper.addVCZone(addVCZoneDTO);
				}
			}
		}
	}
		

}
