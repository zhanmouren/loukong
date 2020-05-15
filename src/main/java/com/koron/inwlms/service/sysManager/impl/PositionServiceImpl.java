package com.koron.inwlms.service.sysManager.impl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.sysManager.PositionDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.sysManager.PositionVO;
import com.koron.inwlms.mapper.sysManager.PositionMapper;
import com.koron.inwlms.service.sysManager.PositionService;
import com.koron.inwlms.util.PageUtil;

@Service
public class PositionServiceImpl implements PositionService{

	//查询职位 2020/05/13
	@TaskAnnotation("queryPosition")
	@Override
	public PageListVO<List<PositionVO>> queryPosition(SessionFactory factory, PositionDTO positionDTO) {
		PositionMapper positionMapper = factory.getMapper(PositionMapper.class);
		List<PositionVO> positionList = positionMapper.queryPosition(positionDTO);
		int rowNumber = positionMapper.getPositionCount(positionDTO);
		
		PageListVO<List<PositionVO>> result = new PageListVO<>();
		result.setDataList(positionList);
		
		PageVO pageVO = PageUtil.getPageBean(positionDTO.getPage(), positionDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}

	@TaskAnnotation("queryPositionDetail")
	@Override
	public List<PositionVO> queryPositionDetail(SessionFactory factory, PositionDTO positionDTO) {
		PositionMapper positionMapper = factory.getMapper(PositionMapper.class);
		List<PositionVO> positionList = positionMapper.queryPositionDetail(positionDTO);
		return positionList;
	}

}
