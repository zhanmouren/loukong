package com.koron.inwlms.service.zoneLoss;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.zoneLoss.QueryVSZoneListDTO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.bean.VO.zoneLoss.VirtualZoneVO;

/**
 * 
 * @createTime 2020/06/22
 * @author lzy
 */
public interface VirtualZoneService {

	//查询虚拟分区
	public List<VirtualZoneVO> queryVirtualZone(SessionFactory factory, QueryVSZoneListDTO queryVSZoneListDTO);
}
