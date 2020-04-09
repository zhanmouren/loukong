package com.koron.inwlms.service.zoneLoss.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBTReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBTReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneWBLossDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WBIndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportFileDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportIndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBTReportIndicatorDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageWNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageWNWBTReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WBIndicatorVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReporFileListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneWBLossVO;
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
	public List<ZoneWBLossVO> queryZoneWBLossData(SessionFactory factory, QueryZoneWBLossDTO queryZoneWBLossDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		Integer timeType = queryZoneWBLossDTO.getTimeType();
		String zoneNo = queryZoneWBLossDTO.getZoneNo();
		Integer zoneRank = queryZoneWBLossDTO.getZoneRank();
		List<ZoneInfo> lists = new ArrayList<>();
		/*
		 * 判断分区编号和分区等级是否为空，都为空则查询全网的水平衡数据
		 * 若有一个不为空，则查询分区的数据
		 */
		if(StringUtil.isNotEmpty(zoneNo) || zoneRank != null) {
			GisZoneServiceImpl gisZoneServiceImpl = new GisZoneServiceImpl();
			lists = gisZoneServiceImpl.querySubZoneNos(factory, zoneNo);
		}
		List<ZoneWBLossVO> result = null;
		if (Constant.TIME_TYPE_M.equals(timeType)) {
			// 月指标查询
			result = mapper.queryZoneWBMLossData(queryZoneWBLossDTO, lists);
		} else if (Constant.TIME_TYPE_Y.equals(timeType)) {
			// 年指标查询
			result = mapper.queryZoneWBYLossData(queryZoneWBLossDTO, lists);
		}
		for (ZoneWBLossVO zoneWBLossVO : result) {
			for (ZoneInfo zoneInfo : lists) {
				if(zoneWBLossVO.getZoneNo() != null && zoneWBLossVO.getZoneNo().equals(zoneInfo.getZoneNo())) {
					zoneWBLossVO.setZoneName(zoneInfo.getZoneName());
					break;
				}
			}
		}
		return result;
	}

	@TaskAnnotation("queryWNWBReportList")
	@Override
	public PageWNWBReportListVO queryWNWBReportList(SessionFactory factory,
			QueryWNWBReportListDTO queryWNWBReportListDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		// 查询列表数据
		List<WNWBReportListVO> dataLists = mapper.queryWNWBReportList(queryWNWBReportListDTO);
		// 查询总条数
		int rowNumber = mapper.countWNWBReportList(queryWNWBReportListDTO);
		// 返回数据结果
		PageWNWBReportListVO result = new PageWNWBReportListVO();
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
	public PageWNWBTReportListVO queryWNWBTReportList(SessionFactory factory,
			QueryWNWBTReportListDTO queryWNWBTReportListDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		// 查询列表数据
		List<WNWBTReportListVO> dataLists = mapper.queryWNWBTReportList(queryWNWBTReportListDTO);
		// 查询总条数
		int rowNumber = mapper.countWNWBTReportList(queryWNWBTReportListDTO);
		// 返回数据结果
		PageWNWBTReportListVO result = new PageWNWBTReportListVO();
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
	public List<WBIndicatorVO> queryWBIndicatorData(SessionFactory factory, WBIndicatorDTO wBIndicatorDTO) {
		WaterBalanceAnaMapper mapper = factory.getMapper(WaterBalanceAnaMapper.class);
		Integer timeType = wBIndicatorDTO.getTimeType();
		List<WBIndicatorVO> lists = new ArrayList<>();
		if(Constant.TIME_TYPE_M.equals(timeType)) {
			//月指标数据
			lists = mapper.queryWBMIndicatorData(wBIndicatorDTO);
		}else {
			//年指标数据
			lists = mapper.queryWBYIndicatorData(wBIndicatorDTO);
		}
		return lists;
	}
}
