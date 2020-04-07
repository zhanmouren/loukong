package com.koron.inwlms.service.zoneLoss.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneWBLossDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.PageALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageWNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneWBLossVO;
import com.koron.inwlms.mapper.apparentLoss.ApparentLossMapper;
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

}
