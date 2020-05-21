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
	//删除职位
	public Integer deletePosition(PositionDTO positionDTO);
	//根据code查找职位
	public PositionVO queryPositionByCode(String code);
	//根据id查找职位
	public PositionVO queryPositionById(Integer id);
	//添加职位
	public Integer addPosition(PositionDTO positionDTO);
	//修改职位
	public Integer updatePosition(PositionDTO positionDTO);
}
