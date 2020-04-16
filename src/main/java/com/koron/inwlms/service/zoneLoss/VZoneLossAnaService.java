package com.koron.inwlms.service.zoneLoss;


import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.zoneLoss.AddVCZoneDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVCZoneListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVSZoneListDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.zoneLoss.VCZoneListVO;
import com.koron.inwlms.bean.VO.zoneLoss.VSZoneListVO;

/**
 * 虚拟分区分析接口
 * @author csh
 * @Date 2020/04/13
 *
 */
public interface VZoneLossAnaService {

	/**
	 * 查询虚拟分区（相减）列表
	 * @param factory
	 * @param queryVSZoneListDTO
	 * @return
	 */
	PageListVO<List<VSZoneListVO>> queryVSZoneList(SessionFactory factory,QueryVSZoneListDTO queryVSZoneListDTO);

	/**
	 * 查询虚拟分区（合并）列表
	 * @param factory
	 * @param queryVSZoneListDTO
	 * @return
	 */
	PageListVO<List<VCZoneListVO>> queryVCZoneList(SessionFactory factory,QueryVCZoneListDTO queryVCZoneListDTO);

	/**
	 * 删除虚拟分区（合并）
	 * @param factory
	 * @param vZoneNo
	 */
	void deleteVCZone(SessionFactory factory,String vZoneNo);

	/**
	 * 添加虚拟分区（合并）
	 * @param factory
	 * @param addVCZoneDTO
	 */
	void addVCZone(SessionFactory factory,AddVCZoneDTO addVCZoneDTO);
}
