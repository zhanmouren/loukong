package com.koron.inwlms.service.sysManager;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.sysManager.PositionDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.PositionVO;

public interface PositionService {

	//查询职位
	PageListVO<List<PositionVO>> queryPosition(SessionFactory factory,PositionDTO positionDTO);
	//查询职位详情
	List<PositionVO> queryPositionDetail(SessionFactory factory,PositionDTO positionDTO);
	
}
