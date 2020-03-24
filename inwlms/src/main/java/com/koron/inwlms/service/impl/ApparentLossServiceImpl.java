package com.koron.inwlms.service.impl;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.common.PageBean;
import com.koron.inwlms.mapper.master.ApparentLossMapper;
import com.koron.inwlms.mapper.master.TestMapper;
import com.koron.inwlms.service.ApparentLossService;
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
		if(Constant.TIME_TYPE_M.equals(timeType)) {
			//月指标查询
			result = mapper.queryMALOverviewData(queryALDTO);
		}else if(Constant.TIME_TYPE_Y.equals(timeType)) {
			//年指标查询
			result = mapper.queryYALOverviewData(queryALDTO);
		}
		return result;
	}

	@Override
	public PageBean queryALList(SessionFactory factory, QueryALListDTO queryALListDTO) {
		// TODO Auto-generated method stub
		return null;
	}
}
