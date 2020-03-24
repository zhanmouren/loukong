package com.koron.inwlms.service;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.common.PageBean;

/**
 * 表观漏损接口
 * @author csh
 * @Date 2020/03/23
 *
 */
public interface ApparentLossService {

	/**
	 * 表观总览接口
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	ALOverviewDataVO queryALOverviewData(SessionFactory factory,QueryALDTO queryALDTO);

	/**
	 * 分页查询表观漏损列表
	 * @param factory
	 * @param queryALListDTO
	 * @return
	 */
	PageBean queryALList(SessionFactory factory,QueryALListDTO queryALListDTO);
}
