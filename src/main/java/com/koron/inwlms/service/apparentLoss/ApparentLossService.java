package com.koron.inwlms.service.apparentLoss;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.web.bind.annotation.RequestBody;

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALMapDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrCurrentMeterDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrDealAdviseVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrMeterAnaDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrMeterManageVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrTotalAnalysisDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrTotalVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlBDnErrFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlBDnLHFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlBDnZeroFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlSDnLHFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlSDnZeroFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlSusUseDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterAnalysisMapVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;

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
	PageListVO<List<ALListVO>> queryALList(SessionFactory factory,QueryALListDTO queryALListDTO);

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
	List<MeterRunAnalysisVO> queryMeterRunAnalysisList(SessionFactory factory,QueryALDTO queryALDTO,List<MeterInfo> lists);

	/**
	 * 查询水表分析图表数据
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	MeterAnalysisMapVO queryMeterRunAnalysisMapData(SessionFactory factory, QueryALDTO queryALDTO);
	
	/**
	 * 查询诊断报告总体数据
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
//	DrTotalAnalysisDataVO queryDrTotalAnalysisData(SessionFactory factory, QueryALDTO queryALDTO);

	/**
	 * 查询诊断报告现状水表数据
	 * @return
	 */
//	DrCurrentMeterDataVO queryDrCurrentMeterData(SessionFactory factory, QueryALDTO queryALDTO);

	/**
	 * 诊断报告表计管理问题
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
//	DrMeterManageVO queryDrMeterManageData(SessionFactory factory, QueryALDTO queryALDTO);

	/**
	 * 诊断报告水表分析接口
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
//	DrMeterAnaDataVO queryMeterAnaData(SessionFactory factory, QueryALDTO queryALDTO);
	
	/**
	 * 诊断报告处理建议接口
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
//	DrDealAdviseVO queryDrDealAdvise(SessionFactory factory, QueryALDTO queryALDTO);

	/**
	 * 查询诊断报告-问题清单数据
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	DrqlVO queryDrQuestionList(SessionFactory factory, QueryALDTO queryALDTO);
	
	/**
	 * 诊断报告数据总接口
	 * @param factory
	 * @param queryALDTO
	 * @return
	 */
	DrTotalVO queryDrTotalData(SessionFactory factory, QueryALDTO queryALDTO,UserVO userVO,String tenantID);
	
	/**
	 * 查询大口径零流量水表数据
	 * @param qaDTO
	 * @return
	 */
	PageListVO<List<DrqlBDnZeroFlowDataListVO>> queryDrqlBDnZeroFlowDataList(SessionFactory factory, QueryALListDTO qaDTO);

	/**
	 * 查询大口径低流量过载水表数据
	 * @param qaDTO
	 * @return
	 */
	PageListVO<List<DrqlBDnLHFlowDataListVO>> queryDrqlBDnLHFlowDataList(SessionFactory factory, QueryALListDTO qaDTO);

	/**
	 * 查询大口径用水异常
	 * @param qaDTO
	 * @return
	 */
	PageListVO<List<DrqlBDnErrFlowDataListVO>> queryDrqlBDnErrFlowDataList(SessionFactory factory,  QueryALListDTO qaDTO);

	/**
	 * 查询用水性质可疑
	 * @param qaDTO
	 * @return
	 */
	PageListVO<List<DrqlSusUseDataListVO>> queryDrqlSusUseDataList(SessionFactory factory,  QueryALListDTO qaDTO);
	
	/**
	 * 查询小口径零流量水表数据
	 * @param qaDTO
	 * @return
	 */
	PageListVO<List<DrqlSDnZeroFlowDataListVO>> queryDrqlSDnZeroFlowDataList(SessionFactory factory, QueryALListDTO qaDTO);

	/**
	 * 查询小口径低流量过载水表数据
	 * @param qaDTO
	 * @return
	 */
	PageListVO<List<DrqlSDnLHFlowDataListVO>> queryDrqlSDnLHFlowDataList(SessionFactory factory, QueryALListDTO qaDTO);
}
