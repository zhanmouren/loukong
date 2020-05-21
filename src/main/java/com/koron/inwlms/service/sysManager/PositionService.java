package com.koron.inwlms.service.sysManager;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.swan.bean.MessageBean;

import com.koron.inwlms.bean.DTO.sysManager.PositionDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.PagePositionListVO;
import com.koron.inwlms.bean.VO.sysManager.PositionVO;

public interface PositionService {

	//查询职位
	PageListVO<List<PositionVO>> queryPosition(SessionFactory factory,PositionDTO positionDTO);
	//查询职位详情
	List<PositionVO> queryPositionDetail(SessionFactory factory,PositionDTO positionDTO);
	//删除职位
	Integer deletePosition(SessionFactory factory,PositionDTO positionDTO);
	//添加职位
	MessageBean<String> addPosition(SessionFactory factory,PositionDTO positionDTO);
	//修改职位
	MessageBean<String> updatePosition(SessionFactory factory,PositionDTO positionDTO);
	//下载职位列表
	PagePositionListVO downloadAllList(SessionFactory factory, PositionDTO positionDTO);
}
