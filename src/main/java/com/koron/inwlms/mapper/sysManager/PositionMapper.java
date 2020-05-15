package com.koron.inwlms.mapper.sysManager;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.sysManager.PositionDTO;
import com.koron.inwlms.bean.VO.sysManager.PositionVO;


@Repository
public interface PositionMapper {

	//查询职位
	public List<PositionVO> queryPosition(PositionDTO positionDTO);
	//查询职位条数
	public int getPositionCount(PositionDTO positionDTO);
	//查询职位
	public List<PositionVO> queryPositionDetail(PositionDTO positionDTO);
	
}
