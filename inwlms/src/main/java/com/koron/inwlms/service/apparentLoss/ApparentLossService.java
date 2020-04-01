package com.koron.inwlms.service.apparentLoss;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.web.bind.annotation.RequestBody;

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALMapDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterAnalysisMapVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.apparentLoss.PageALListVO;

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
	PageALListVO queryALList(SessionFactory factory,QueryALListDTO queryALListDTO);

	/**
	 * 查询表观漏损图表数据
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	ALMapDataVO queryALMapData(SessionFactory factory,QueryALDTO queryALDTO);

	/**
	 * 查询水表运行分析数据
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	List<MeterRunAnalysisVO> queryMeterRunAnalysisList(SessionFactory factory,QueryALDTO queryALDTO);

	/**
	 * 查询水表分析图表数据
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	MeterAnalysisMapVO queryMeterRunAnalysisMapData(SessionFactory factory, QueryALDTO queryALDTO);
}
