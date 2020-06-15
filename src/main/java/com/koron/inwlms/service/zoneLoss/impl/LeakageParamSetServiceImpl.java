package com.koron.inwlms.service.zoneLoss.impl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.zoneLoss.LegitimateNightUseDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.LegitimateNightUseEditDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.zoneLoss.LegitimateNightUseVO;
import com.koron.inwlms.bean.VO.zoneLoss.VSZoneListVO;
import com.koron.inwlms.mapper.zoneLoss.LeakageParamSetMapper;
import com.koron.inwlms.service.zoneLoss.LeakageParamSetService;
import com.koron.inwlms.util.PageUtil;

/**
 * 漏损参数设置接口
 * @author csh
 * @Date 2020/04/16
 *
 */
@Service
public class LeakageParamSetServiceImpl implements LeakageParamSetService {

	@TaskAnnotation("queryLeakageExponent")
	@Override
	public String queryLeakageExponent(SessionFactory factory) {
		LeakageParamSetMapper mapper = factory.getMapper(LeakageParamSetMapper.class);
		String value = mapper.queryLeakageExponent();
		return value;
	}

	@TaskAnnotation("updateLeakageExponent")
	@Override
	public void updateLeakageExponent(SessionFactory factory,String value) {
		LeakageParamSetMapper mapper = factory.getMapper(LeakageParamSetMapper.class);
		mapper.updateLeakageExponent(value);
	}

	@TaskAnnotation("queryLegitimateNightUseList")
	@Override
	public PageListVO queryLegitimateNightUseList(SessionFactory factory, LegitimateNightUseDTO lnuDTO) {
		LeakageParamSetMapper mapper = factory.getMapper(LeakageParamSetMapper.class);
		int count = mapper.countLegitimateNightUseList(lnuDTO.getName());
		//判断条数是否符合分页
		if(count<(lnuDTO.getPage()-1)*lnuDTO.getPageCount()) return null;
		List<LegitimateNightUseVO> lists = mapper.queryLegitimateNightUseList(lnuDTO);
		PageListVO<List<LegitimateNightUseVO>> result = new PageListVO<>();
		result.setDataList(lists);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(lnuDTO.getPage(), lnuDTO.getPageCount(), count);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("updateLegitimateNightUse")
	@Override
	public void updateLegitimateNightUse(SessionFactory factory, LegitimateNightUseEditDTO lnueDTO) {
		LeakageParamSetMapper mapper = factory.getMapper(LeakageParamSetMapper.class);
		mapper.updateLegitimateNightUse(lnueDTO);
	}

}
