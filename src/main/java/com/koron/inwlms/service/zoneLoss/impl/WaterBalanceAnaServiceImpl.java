package com.koron.inwlms.service.zoneLoss.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBTReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryFZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBTReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneInfoDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneWBLossDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportFileDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportIndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBTReportIndicatorDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReporFileListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneDetailInfoVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneWBLossVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.zoneLoss.WaterBalanceAnaMapper;
import com.koron.inwlms.service.common.impl.GisZoneServiceImpl;
import com.koron.inwlms.service.zoneLoss.WaterBalanceAnaService;
import com.koron.inwlms.util.PageUtil;
import com.koron.util.Constant;


/**
 * 水平衡分析接口实现层
 * @author csh
 * @Date 2020/04/07
 *
 */
@Service
public class WaterBalanceAnaServiceImpl implements WaterBalanceAnaService {

	/**
	 * 查询分区水平衡数据
	 * @param factory
	 * @param queryZoneWBLossDTO
	 * @return
	 */
	@TaskAnnotation("queryZoneWBLossData")
	@Override
	public ZoneWBLossVO queryZoneWBLossData(SessionFactory factory, QueryZoneWBLossDTO queryZoneWBLossDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		ZoneWBLossVO zoneWBLossVO = new ZoneWBLossVO();
		Integer timeType = queryZoneWBLossDTO.getTimeType();
		String zoneNo = queryZoneWBLossDTO.getZoneNo();
		Integer zoneRank = queryZoneWBLossDTO.getZoneRank();
		double avgLossRate = 0;  //全网平均漏损率
		//1、分区编号为空，查询全网的平均漏损率
		//2、根据条件查询单个分区编号或全网的水平衡数据信息
		if(StringUtil.isEmpty(zoneNo)) {
			if (Constant.TIME_TYPE_M.equals(timeType)) {
				// 月指标查询
				List<String> codes = new ArrayList<>();
				codes.add("WNMFWSSITDF");
				codes.add("WNMBC");
				codes.add("WNMWL");
				codes.add("WNMWLR");
				zoneWBLossVO = mapper.queryCompanyWBMLossData(queryZoneWBLossDTO, codes);
			} else if (Constant.TIME_TYPE_Y.equals(timeType)) {
				// 年指标查询
				List<String> codes = new ArrayList<>();
				codes.add("WNYFWSSITDF");
				codes.add("WNYBC");
				codes.add("WNYWL");
				codes.add("WNYWLR");
				zoneWBLossVO = mapper.queryCompanyWBYLossData(queryZoneWBLossDTO, codes);
			}
			avgLossRate = zoneWBLossVO.getLossRate();
			zoneWBLossVO.setColor(1);  //灰色
			zoneWBLossVO.setZoneName("全网");
			
		}else {
			//查询全网的平均漏损率
			IndicatorMapper indicMapper = factory.getMapper(IndicatorMapper.class);
			IndicatorDTO indicatorDTO = new IndicatorDTO();
			List<String> codes = new ArrayList<>();
			codes.add("WNMWLR");  //全网漏损率指标
			indicatorDTO.setCodes(codes );
			indicatorDTO.setEndTime(queryZoneWBLossDTO.getEndTime());
			indicatorDTO.setStartTime(queryZoneWBLossDTO.getStartTime());
			indicatorDTO.setTimeType(queryZoneWBLossDTO.getTimeType());
			List<IndicatorVO> queryCompanyIndicData = indicMapper.queryCompanyIndicData(indicatorDTO );
			if(queryCompanyIndicData == null || queryCompanyIndicData.size() == 0) return null;
			DecimalFormat df = new DecimalFormat("#.0000");
			double valueSum = 0;
			for (IndicatorVO indicatorVO : queryCompanyIndicData) {
				valueSum += indicatorVO.getValue();
			}
			avgLossRate =  Double.parseDouble(df.format(valueSum/queryCompanyIndicData.size()));
			
			//查询条件中的单个zoneNo的水平衡数据
			List<ZoneWBLossVO> zoneWBLossVOs = new ArrayList<>();
			List<String> indicCodes = getZoneWBIndicCodes(timeType,zoneRank);
			List<ZoneInfo> zoneInfos = new ArrayList<>();
			ZoneInfo zoneInfo = new ZoneInfo();
			zoneInfo.setZoneNo(zoneNo);
			zoneInfos.add(zoneInfo);
			//查询zoneNo的漏损指标
			if (Constant.TIME_TYPE_M.equals(timeType)) {
				// 月指标查询
				zoneWBLossVOs = mapper.queryZoneWBMLossData(queryZoneWBLossDTO, zoneInfos, indicCodes);
			} else if (Constant.TIME_TYPE_Y.equals(timeType)) {
				// 年指标查询
				zoneWBLossVOs = mapper.queryZoneWBYLossData(queryZoneWBLossDTO, zoneInfos, indicCodes);
			}
			if(zoneWBLossVOs == null || zoneWBLossVOs.size() ==0) return null;
			zoneWBLossVO = zoneWBLossVOs.get(0);
			avgLossRate = zoneWBLossVO.getLossRate();
			Double lossRate = zoneWBLossVO.getLossRate();
			if(lossRate >= avgLossRate*1.5) {
				zoneWBLossVO.setColor(3);
			}else if(lossRate >= avgLossRate && avgLossRate < avgLossRate*1.5) {
				zoneWBLossVO.setColor(2);
			}else {
				zoneWBLossVO.setColor(1);
			}
			GisZoneServiceImpl gisZoneServiceImpl = new GisZoneServiceImpl();
			QueryZoneInfoDTO queryZoneInfoDTO = new QueryZoneInfoDTO();
			queryZoneInfoDTO.setZoneNo(zoneNo);
			queryZoneInfoDTO.setZoneType(zoneRank);
			ZoneDetailInfoVO queryZoneDetailInfo = gisZoneServiceImpl.queryZoneDetailInfo(factory, queryZoneInfoDTO);
			zoneWBLossVO.setZoneName(queryZoneDetailInfo.getZoneName());
		}
		//3、查询子分区水平衡数据信息
		List<ZoneWBLossVO> zoneWBMLossData = getZoneWBMLossData(factory,queryZoneWBLossDTO,avgLossRate,zoneNo);
		zoneWBLossVO.setChildrens(zoneWBMLossData);
		return zoneWBLossVO;
	}

	/**
	 * 获取全网水平衡指标编码
	 * @param timeType
	 * @param zoneRank
	 * @return
	 */
	public List<String> getZoneWBIndicCodes(Integer timeType,Integer zoneRank){
		List<String> codes = new ArrayList<>();
		if (Constant.TIME_TYPE_M.equals(timeType)) {
			if(Constant.RANK_F.equals(zoneRank)) {
				//一级
				codes.add("FLMFWSSITDF");
				codes.add("FLMBC");
				codes.add("FLMWL");
				codes.add("FLMWLR");
			}else if(Constant.RANK_S.equals(zoneRank)) {
				//二级
				codes.add("SLMFWSSITDF");
				codes.add("SLMBC");
				codes.add("SLMWL");
				codes.add("SLMWLR");
			}else if(Constant.RANK_T.equals(zoneRank)) {
				//DMA
				codes.add("DMMFWSSITDF");
				codes.add("DMMBC");
				codes.add("DMMWL");
				codes.add("DMMWLR");
			}
		}else if((Constant.TIME_TYPE_Y.equals(timeType))) {
			if(Constant.RANK_F.equals(zoneRank)) {
				//一级
				codes.add("FLYFWSSITDF");
				codes.add("FLYBC");
				codes.add("FLYWL");
				codes.add("FLYWLR");
			}else if(Constant.RANK_S.equals(zoneRank)) {
				//二级
				codes.add("SLYFWSSITDF");
				codes.add("SLYBC");
				codes.add("SLYWL");
				codes.add("SLYWLR");
			}else if(Constant.RANK_T.equals(zoneRank)) {
				//dma
				codes.add("DMYFWSSITDF");
				codes.add("DMYBC");
				codes.add("DMYWL");
				codes.add("DMYWLR");
			}
		}
		return codes;
	}
	
	/**
	 * 获取分区漏损数据
	 * @param mapper 
	 * @param queryZoneWBLossDTO 查询条件DTO
	 * @param avgLossRate 全网平均漏损率
	 * @param zoneNo 父分区编号
	 */
	public List<ZoneWBLossVO> getZoneWBMLossData(SessionFactory factory,
			QueryZoneWBLossDTO queryZoneWBLossDTO,double avgLossRate,String zoneNo){
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		//查询分区的子分区
		List<ZoneInfo> lists = new ArrayList<>();
		GisZoneServiceImpl gisZoneServiceImpl = new GisZoneServiceImpl();
		lists = gisZoneServiceImpl.querySubZoneNos(factory, zoneNo);
		//没有子分区，返回
		if(lists == null || lists.size() == 0) return null;
		//获取全网水平衡指标编码
		Integer zoneRank = gisZoneServiceImpl.getZoneRankByNo(factory, zoneNo)+1;
		queryZoneWBLossDTO.setZoneRank(zoneRank);
		List<String> codes = getZoneWBIndicCodes(queryZoneWBLossDTO.getTimeType(),zoneRank);
		List<ZoneWBLossVO> result = new ArrayList<>();
		if (Constant.TIME_TYPE_M.equals(queryZoneWBLossDTO.getTimeType())) {
			// 月指标查询
			result = mapper.queryZoneWBMLossData(queryZoneWBLossDTO, lists,codes);
		} else if (Constant.TIME_TYPE_Y.equals(queryZoneWBLossDTO.getTimeType())) {
			// 年指标查询
			result = mapper.queryZoneWBYLossData(queryZoneWBLossDTO, lists,codes);
		}
		for (ZoneWBLossVO zwbVO : result) {
			Double lossRate = zwbVO.getLossRate();
			if(lossRate >= avgLossRate*1.5) {
				zwbVO.setColor(3);
			}else if(lossRate >= avgLossRate && avgLossRate < avgLossRate*1.5) {
				zwbVO.setColor(2);
			}else {
				zwbVO.setColor(1);
			}
			zwbVO.setpZoneNo(zoneNo);
			for (ZoneInfo zoneInfo : lists) {
				if(zwbVO.getZoneNo() != null && zwbVO.getZoneNo().equals(zoneInfo.getZoneNo())) {
					zwbVO.setZoneName(zoneInfo.getZoneName());
					break;
				}
			}
			List<ZoneWBLossVO> childrens = getZoneWBMLossData(factory,queryZoneWBLossDTO,avgLossRate,zwbVO.getZoneNo());
			zwbVO.setChildrens(childrens);
		}
		return result;
	}
	
	
	@TaskAnnotation("queryWNWBReportList")
	@Override
	public PageListVO<List<WNWBReportListVO>> queryWNWBReportList(SessionFactory factory,
			QueryWNWBReportListDTO queryWNWBReportListDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		// 查询总条数
		int rowNumber = mapper.countWNWBReportList(queryWNWBReportListDTO);
		//判断分页
		if(rowNumber<(queryWNWBReportListDTO.getPage()-1)*queryWNWBReportListDTO.getPageCount()) return null;
				
		// 查询列表数据
		List<WNWBReportListVO> dataLists = mapper.queryWNWBReportList(queryWNWBReportListDTO);
				
		
		// 返回数据结果
		PageListVO<List<WNWBReportListVO>> result = new PageListVO<>();
		result.setDataList(dataLists);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryWNWBReportListDTO.getPage(), queryWNWBReportListDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("deleteWNWBReport")
	@Override
	public void deleteWNWBReport(SessionFactory factory, Integer id) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		mapper.deleteWNWBReport(id);
	}

	@TaskAnnotation("queryWNWBTReportList")
	@Override
	public PageListVO<List<WNWBTReportListVO>> queryWNWBTReportList(SessionFactory factory,
			QueryWNWBTReportListDTO queryWNWBTReportListDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		
		// 查询总条数
		int rowNumber = mapper.countWNWBTReportList(queryWNWBTReportListDTO);
		//判断分页
		if(rowNumber<(queryWNWBTReportListDTO.getPage()-1)*queryWNWBTReportListDTO.getPageCount()) return null;
				
		// 查询列表数据
		List<WNWBTReportListVO> dataLists = mapper.queryWNWBTReportList(queryWNWBTReportListDTO);
				
		// 返回数据结果
		PageListVO<List<WNWBTReportListVO>> result = new PageListVO<>();
		result.setDataList(dataLists);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryWNWBTReportListDTO.getPage(), queryWNWBTReportListDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("deleteWNWBTReport")
	@Override
	public void deleteWNWBTReport(SessionFactory factory, Integer tId) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		mapper.deleteWNWBTReport(tId);
	}

	@TaskAnnotation("addWNWBReport")
	@Override
	public Integer addWNWBReport(SessionFactory factory, AddWNWBReportDTO addWNWBReportDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		mapper.addWNWBReport(addWNWBReportDTO);
		addReportOtherInfo(mapper,addWNWBReportDTO);
		return addWNWBReportDTO.getId();
	}
	
	/**
	 * 保存报表的其他信息（指标信息，附件信息）
	 * @param mapper
	 * @param addWNWBReportDTO
	 */
	private void addReportOtherInfo(WaterBalanceAnaMapper mapper,AddWNWBReportDTO addWNWBReportDTO){
		 List<WNWBReportIndicatorDTO> indicators = addWNWBReportDTO.getIndicators();
		 //保存报表指标信息
		 if(indicators != null && indicators.size() > 0){
			 for(int i=0;i<indicators.size();i++){
				 indicators.get(i).setReportId(addWNWBReportDTO.getId());
				 indicators.get(i).setCreateBy(addWNWBReportDTO.getCreateBy());
				 indicators.get(i).setUpdateBy(addWNWBReportDTO.getUpdateBy());
				 mapper.addReportIndicator(indicators.get(i));  
			 } 
		 }
		 List<WNWBReportFileDTO> attachments = addWNWBReportDTO.getFiles();
		 //保存附件信息
		 if(attachments != null && attachments.size() > 0){
			 for(int i = 0;i < attachments.size(); i++){
				 attachments.get(i).setReportId(addWNWBReportDTO.getId());
				 attachments.get(i).setCreateBy(addWNWBReportDTO.getCreateBy());
				 attachments.get(i).setUpdateBy(addWNWBReportDTO.getUpdateBy());
				 mapper.addReportFile(attachments.get(i));  
			 } 
		 }	 
	}

	@TaskAnnotation("updateWNWBReport")
	@Override
	public Integer updateWNWBReport(SessionFactory factory, AddWNWBReportDTO eidtWNWBReportDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		mapper.updateWNWBReport(eidtWNWBReportDTO);
		mapper.deleteReportIndicatorById(eidtWNWBReportDTO.getId());
		mapper.deleteReportFileById(eidtWNWBReportDTO.getId());
		addReportOtherInfo(mapper,eidtWNWBReportDTO);
		return eidtWNWBReportDTO.getId();
	}

	@TaskAnnotation("queryWNWBReportDetail")
	@Override
	public WNWBReportDetailVO queryWNWBReportDetail(SessionFactory factory, Integer id) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		WNWBReportDetailVO wNWBReportDetailVO = mapper.queryWNWBReportById(id);
		if(wNWBReportDetailVO != null) {
			wNWBReportDetailVO.setFiles(mapper.queryWNWBReportFileById(id));
			wNWBReportDetailVO.setIndicators(mapper.queryWNWBReportIndicatorById(id));
		}
		return wNWBReportDetailVO;
	}
	
	@TaskAnnotation("queryWNWBReporFileList")
	@Override
	public List<WNWBReporFileListVO> queryWNWBReporFileList(SessionFactory factory,
			WNWBReportFileDTO wNWBReportFileDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		List<WNWBReporFileListVO> result = mapper.queryWNWBReporFileList(wNWBReportFileDTO);
		return result;
	}

	@TaskAnnotation("deleteReportFileById")
	@Override
	public void deleteReportFileById(SessionFactory factory, Integer id) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		mapper.deleteReportFileById(id);
	}

	@TaskAnnotation("addWNWBTReport")
	@Override
	public Integer addWNWBTReport(SessionFactory factory, AddWNWBTReportDTO addWNWBTReportDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		mapper.addWNWBTReport(addWNWBTReportDTO);
		List<WNWBTReportIndicatorDTO> indicators = addWNWBTReportDTO.getIndicators();
		 //保存报表模板指标信息
		 if(indicators != null && indicators.size() > 0){
			 for(int i=0;i<indicators.size();i++){
				 indicators.get(i).setTemplateId(addWNWBTReportDTO.getId());
				 indicators.get(i).setCreateBy(addWNWBTReportDTO.getCreateBy());
				 indicators.get(i).setUpdateBy(addWNWBTReportDTO.getUpdateBy());
				 mapper.addTReportIndicator(indicators.get(i));  
			 } 
		 }
		return addWNWBTReportDTO.getId();
	}

	@TaskAnnotation("updateWNWBTReport")
	@Override
	public Integer updateWNWBTReport(SessionFactory factory, AddWNWBTReportDTO editWNWBTReportDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		mapper.updateWNWBTReport(editWNWBTReportDTO);
		mapper.deleteTReportIndicatorById(editWNWBTReportDTO.getId());
		mapper.addWNWBTReport(editWNWBTReportDTO);
		List<WNWBTReportIndicatorDTO> indicators = editWNWBTReportDTO.getIndicators();
		 //保存报表模板指标信息
		 if(indicators != null && indicators.size() > 0){
			 for(int i=0;i<indicators.size();i++){
				 indicators.get(i).setTemplateId(editWNWBTReportDTO.getId());
				 indicators.get(i).setCreateBy(editWNWBTReportDTO.getCreateBy());
				 indicators.get(i).setUpdateBy(editWNWBTReportDTO.getUpdateBy());
				 mapper.addTReportIndicator(indicators.get(i));  
			 } 
		 }
		return editWNWBTReportDTO.getId();
	}

	@TaskAnnotation("queryWNWBTReportDetail")
	@Override
	public WNWBTReportDetailVO queryWNWBTReportDetail(SessionFactory factory, Integer id) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		WNWBTReportDetailVO wNWBTReportDetailVO = mapper.queryWNWBTReportById(id);
		if(wNWBTReportDetailVO != null) {
			wNWBTReportDetailVO.setIndicators(mapper.queryWNWBTReportIndicatorById(id));
		}
		return wNWBTReportDetailVO;
	}

	@TaskAnnotation("queryWBIndicatorData")
	@Override
	public List<IndicatorVO> queryWBIndicatorData(SessionFactory factory, IndicatorDTO indicatorDTO) {
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		List<IndicatorVO> lists = mapper.queryWBBaseIndicData(indicatorDTO);
		return lists;
	}

	@TaskAnnotation("queryWNWBIndicatorData")
	@Override
	public List<IndicatorVO> queryWNWBIndicatorData(SessionFactory factory, IndicatorDTO indicatorDTO) {
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		List<IndicatorVO> result = new ArrayList<>();
		List<IndicatorVO> lists = mapper.queryCompanyIndicData(indicatorDTO);
		for (String code : indicatorDTO.getCodes()) {
			IndicatorVO indicVO = new IndicatorVO();
			Double value = 0.0;
			for (IndicatorVO indicatorVO : lists) {
				if(code.equals(indicatorVO.getCode())) {
					value += indicatorVO.getValue();
				}
			}
			indicVO.setCode(code);
			indicVO.setValue(value);
			result.add(indicVO);
		}
		return result;
	}
	
}
