package com.koron.inwlms.service.sysManager.impl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.swan.bean.MessageBean;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.koron.inwlms.bean.DTO.sysManager.PositionDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryLabelDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.sysManager.LabelVO;
import com.koron.inwlms.bean.VO.sysManager.PageLabelListVO;
import com.koron.inwlms.bean.VO.sysManager.PagePositionListVO;
import com.koron.inwlms.bean.VO.sysManager.PositionVO;
import com.koron.inwlms.mapper.sysManager.PositionMapper;
import com.koron.inwlms.service.sysManager.PositionService;
import com.koron.inwlms.util.PageUtil;
import com.koron.inwlms.util.RandomCodeUtil;
import com.koron.util.Constant;

@Service
public class PositionServiceImpl implements PositionService{

	private static final Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);
	
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

	//查询职位详情 2020/05/15
	@TaskAnnotation("queryPositionDetail")
	@Override
	public List<PositionVO> queryPositionDetail(SessionFactory factory, PositionDTO positionDTO) {
		PositionMapper positionMapper = factory.getMapper(PositionMapper.class);
		List<PositionVO> positionList = positionMapper.queryPositionDetail(positionDTO);
		return positionList;
	}

	//删除职位 2020/05/18
	@TaskAnnotation("deletePosition")
	@Override
	public Integer deletePosition(SessionFactory factory, PositionDTO positionDTO) {
		PositionMapper positionMapper = factory.getMapper(PositionMapper.class);
		Integer delResult = positionMapper.deletePosition(positionDTO);
		return delResult;
	}

	//添加职位 2020/05/18
	@TaskAnnotation("addPosition")
	@Override
	public MessageBean<String> addPosition(SessionFactory factory, PositionDTO positionDTO) {
		MessageBean<String> msg =MessageBean.create(Constant.MESSAGE_INT_SUCCESS,Constant.MESSAGE_STRING_SUCCESS, String.class);
		PositionMapper positionMapper = factory.getMapper(PositionMapper.class);
		String code=new RandomCodeUtil().getUUID32();
		positionDTO.setCode(code);
		try {
			PositionVO position = positionMapper.queryPositionByCode(positionDTO.getCode());
			positionMapper.addPosition(positionDTO);
		} catch (Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			msg.setDescription(Constant.MESSAGE_STRING_ADDERROR);
		}
		
		return msg;
	}
	
	//修改职位 2020/05/18
	@TaskAnnotation("updatePosition")
	@Override
	public MessageBean<String> updatePosition(SessionFactory factory, PositionDTO positionDTO) {
		MessageBean<String> msg =MessageBean.create(Constant.MESSAGE_INT_SUCCESS,Constant.MESSAGE_STRING_SUCCESS, String.class);
		PositionMapper positionMapper = factory.getMapper(PositionMapper.class);
		try {
			PositionVO position = positionMapper.queryPositionByCode(positionDTO.getCode());
			if (position == null) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("不存在该职位");
			}else{
				positionMapper.updatePosition(positionDTO);
			}
		} catch (Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("修改失败");
		}
		return msg;
	}
	
	//下载职位列表
	@TaskAnnotation("downloadAllList")
	@Override
	public PagePositionListVO downloadAllList(SessionFactory factory, PositionDTO positionDTO) {
		PositionMapper positionMapper = factory.getMapper(PositionMapper.class);
		List<PositionVO> positionList = positionMapper.queryPosition(positionDTO);
		
		PagePositionListVO result = new PagePositionListVO();
		result.setDataList(positionList);
		
		PageVO pageVO = PageUtil.getPageBean(positionDTO.getPage(), positionDTO.getPageCount(), positionList.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}
	
}
