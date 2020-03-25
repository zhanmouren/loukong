package com.koron.inwlms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.PageALListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.mapper.master.ApparentLossMapper;
import com.koron.inwlms.service.ApparentLossService;
import com.koron.inwlms.service.common.impl.GisZoneServiceImpl;
import com.koron.inwlms.util.PageUtil;
import com.koron.util.Constant;

/**
 * 表观漏损接口实现类
 * @author csh
 * @Date 2020/03/23
 *
 */
@Service
public class ApparentLossServiceImpl implements ApparentLossService {
	
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
}
