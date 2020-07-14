package com.koron.inwlms.service.baseData.impl;

import com.koron.inwlms.bean.DTO.baseInf.MonitorDataDTO;
import com.koron.inwlms.bean.DTO.baseInf.MonitorDataExcelBean;
import com.koron.inwlms.bean.VO.baseInf.DataQualityVO;
import com.koron.inwlms.bean.VO.baseInf.MonitorDataHisVO;
import com.koron.inwlms.bean.VO.baseInf.MonitorDataVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.mapper.baseData.IMDataMapper;
import com.koron.inwlms.mapper.baseData.PropertyMapper;
import com.koron.inwlms.service.baseData.MonitorService;
import com.koron.inwlms.util.PageUtil;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auother:zhongweibin
 * @date:2020-05-11
 * @description:
 */
@Service
public class MonitorServiceImpl implements MonitorService {

	/**
	 * 按批次删除监测数据
	 * 
	 * @param factory
	 * @param BatchNo
	 * @return
	 */
	@TaskAnnotation("deleteMonitorDataByBatchNo")
	@Override
	public Integer deleteMonitorDataByBatchNo(SessionFactory factory, String BatchNo) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		Integer ret = mapper.deleteMonitorDataByBatchNo(BatchNo);
		return ret;
	}

	/**
	 * 查询监测点最新监测数据
	 * 
	 * @param factory
	 * @param monitorDataDTO
	 * @return
	 */
	@TaskAnnotation("queryLastMonitorDataList")
	@Override
	public List<MonitorDataVO> queryLastMonitorDataList(SessionFactory factory, MonitorDataDTO monitorDataDTO) {
		PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
		List<MonitorDataVO> ret = mapper.queryLastMonitorDataList(monitorDataDTO);
		return ret;

	}

	/**
	 * 批量插入压力流量监测数据
	 * 
	 * @param factory
	 * @param excelBeans
	 * @return
	 */
	@TaskAnnotation("addBatchMointorData")
	@Override
	public Integer addBatchMointorData(SessionFactory factory, List<MonitorDataExcelBean> excelBeans,
			DataQualityVO dq) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		Integer result = mapper.addBatchMointorData(excelBeans);
		if (result > 0) {
			Integer ret = mapper.addImportDataQuality(dq);
			return ret;
		} else {
			return result;
		}
	}

	/**
	 * 批量插入噪声监测数据
	 * 
	 * @param factory
	 * @param excelBeans
	 * @return
	 */
	@TaskAnnotation("addBatchNoiseData")
	@Override
	public Integer addBatchNoiseData(SessionFactory factory, List<MonitorDataExcelBean> excelBeans, DataQualityVO dq) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		Integer result = mapper.addBatchNoiseData(excelBeans);
		if (result > 0) {
			Integer ret = mapper.addImportDataQuality(dq);
			return ret;
		} else {
			return result;
		}
	}

	/**
	 * 查询监测数据列表数据
	 * 
	 * @param factory
	 * @param monitorDataDTO
	 * @return
	 */
	@TaskAnnotation("queryPressureFlowList")
	@Override
	public PageListVO<List<MonitorDataVO>> queryPressureFlowList(SessionFactory factory,
			MonitorDataDTO monitorDataDTO) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		List<MonitorDataVO> rtResult = mapper.queryPressureFlowList(monitorDataDTO);

		int rowNumber = mapper.getPressureFlowListCount(monitorDataDTO);
		PageListVO<List<MonitorDataVO>> plv = new PageListVO<>();
		plv.setDataList(rtResult);
		PageVO pageVO = PageUtil.getPageBean(monitorDataDTO.getPage(), monitorDataDTO.getPageCount(), rowNumber);
		plv.setTotalPage(pageVO.getTotalPage());
		plv.setRowNumber(pageVO.getRowNumber());
		plv.setPageCount(pageVO.getPageCount());
		plv.setPage(pageVO.getPage());
		return plv;
	}

	/**
	 * 查询压力/流量数据数据
	 * 
	 * @param factory
	 * @param id
	 * @return
	 */
	@TaskAnnotation("queryPressureFlowDet")
	@Override
	public MonitorDataVO queryPressureFlowDet(SessionFactory factory, Integer id) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		MonitorDataVO result = mapper.queryPressureFlowDet(id);
		return result;
	}

	/**
	 * 查询监测导入历史列表数据
	 * 
	 * @param factory
	 * @param monitorDataDTO
	 * @return
	 */
	@TaskAnnotation("queryMonitorDataHistoryList")
	@Override
	public PageListVO<List<MonitorDataHisVO>> queryMonitorDataHistoryList(SessionFactory factory,
			MonitorDataDTO monitorDataDTO) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		List<MonitorDataHisVO> result = mapper.queryMonitorDataHistoryList(monitorDataDTO);
		PageListVO<List<MonitorDataHisVO>> plv = new PageListVO<>();
		MonitorDataHisVO r = result.get(result.size() - 1);
		result.remove(result.size() - 1);
		plv.setDataList(result);
		PageVO pageVO = PageUtil.getPageBean(monitorDataDTO.getPage(), monitorDataDTO.getPageCount(), r.getRows());
		plv.setTotalPage(pageVO.getTotalPage());
		plv.setRowNumber(pageVO.getRowNumber());
		plv.setPageCount(pageVO.getPageCount());
		plv.setPage(pageVO.getPage());
		return plv;
	}

	/**
	 * 查询某一批次监测数据
	 * 
	 * @param factory
	 * @param monitorDataDTO
	 * @return
	 */
	@TaskAnnotation("queryMonitorDataByBatchNo")
	@Override
	public PageListVO<List<MonitorDataVO>> queryMonitorDataByBatchNo(SessionFactory factory,
			MonitorDataDTO monitorDataDTO) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		List<MonitorDataVO> result = mapper.queryMonitorDataByBatchNo(monitorDataDTO);
		PageListVO<List<MonitorDataVO>> plv = new PageListVO<>();
		MonitorDataVO r = result.get(result.size() - 1);
		result.remove(result.size() - 1);
		plv.setDataList(result);
		PageVO pageVO = PageUtil.getPageBean(monitorDataDTO.getPage(), monitorDataDTO.getPageCount(), r.getRows());
		plv.setTotalPage(pageVO.getTotalPage());
		plv.setRowNumber(pageVO.getRowNumber());
		plv.setPageCount(pageVO.getPageCount());
		plv.setPage(pageVO.getPage());
		return plv;
	}

	/**
	 * 查询噪音列表数据
	 * 
	 * @param factory
	 * @param monitorDataDTO
	 * @return
	 */
	@TaskAnnotation("queryNoiseList")
	@Override
	public PageListVO<List<MonitorDataVO>> queryNoiseList(SessionFactory factory, MonitorDataDTO monitorDataDTO) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		List<MonitorDataVO> result = mapper.queryNoiseList(monitorDataDTO);
		PageListVO<List<MonitorDataVO>> plv = new PageListVO<>();
		MonitorDataVO r = result.get(result.size() - 1);
		result.remove(result.size() - 1);
		plv.setDataList(result);
		PageVO pageVO = PageUtil.getPageBean(monitorDataDTO.getPage(), monitorDataDTO.getPageCount(), r.getRows());
		plv.setTotalPage(pageVO.getTotalPage());
		plv.setRowNumber(pageVO.getRowNumber());
		plv.setPageCount(pageVO.getPageCount());
		plv.setPage(pageVO.getPage());
		return plv;
	}

	/**
	 * 查询噪音详情数据
	 * 
	 * @param factory
	 * @param id
	 * @return
	 */
	@TaskAnnotation("queryNoiseDet")
	@Override
	public MonitorDataVO queryNoiseDet(SessionFactory factory, Integer id) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		MonitorDataVO result = mapper.queryNoiseDet(id);
		return result;
	}

	/**
	 * 更新监测详情数据
	 * 
	 * @param factory
	 * @param monitorDataDTO
	 * @return
	 */
	@TaskAnnotation("updateMonitorDet")
	@Override
	public boolean updateMonitorDet(SessionFactory factory, MonitorDataDTO monitorDataDTO) {
		IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
		boolean result = mapper.updateMonitorDet(monitorDataDTO);
		return result;
	}
}
