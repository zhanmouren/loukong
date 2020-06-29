package com.koron.inwlms.mapper.apparentLoss;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlBDnErrFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlBDnLHFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlBDnZeroFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlMeterErrUseData;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlSDnLHFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlSDnZeroFlowDataListVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterFlowVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterMFlowData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterQH;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRTimeUnset;
import com.koron.inwlms.bean.VO.apparentLoss.MeterReadData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterReadNumInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MonthFlowData;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneData;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;

/**
 * 表观漏损mapper
 * @author csh
 * @Date 2020/03/23
 */
@Repository
public interface ApparentLossMapper {

    /**
     * 查询月表观漏损总览
     * @return
     */
	ALOverviewDataVO queryMALOverviewData(QueryALDTO queryALDTO);
	
	 /**
     * 查询年表观漏损总览
     * @return
     */
	ALOverviewDataVO queryYALOverviewData(QueryALDTO queryALDTO);
	
	  /**
     * 查询全网月表观漏损总览
     * @return
     */
	ALOverviewDataVO queryWNMALOverviewData(QueryALDTO queryALDTO);
	
	 /**
     * 查询全网年表观漏损总览
     * @return
     */
	ALOverviewDataVO queryWNYALOverviewData(QueryALDTO queryALDTO);
	
	/**
     * 查询月表观漏损列表
     * @return
     */
	List<ALListVO> queryMALList(@Param("qaDTO") QueryALListDTO qaDTO,@Param("lists") List<ZoneInfo> lists);
	
	/**
	 * 查询月表观漏损总条数
	 * @param qaDTO
	 * @param lists
	 * @return
	 */
	int countMALList(@Param("qaDTO") QueryALListDTO qaDTO,@Param("lists") List<ZoneInfo> lists);
	
	/**
     * 查询年表观漏损列表
     * @return
     */
	List<ALListVO> queryYALList(@Param("qaDTO") QueryALListDTO qaDTO,@Param("lists") List<ZoneInfo> lists);
	
	/**
	 * 查询年表观漏损总条数
	 * @param qaDTO
	 * @param lists
	 * @return
	 */
	int countYALList(@Param("qaDTO") QueryALListDTO qaDTO,@Param("lists") List<ZoneInfo> lists);
	
	/**
	 * 查询水表运行分析数据
	 * @param queryALDTO
	 * @param lists
	 * @return
	 */
	List<MeterQH> queryMeterQH(@Param("qaDTO") QueryALDTO queryALDTO,@Param("lists") List<MeterInfo> lists);

	/**
	 * 查询消防水表的月流量
	 * @param queryALDTO
	 * @param lists
	 * @return
	 */
	List<MeterFlowVO> queryFSMeterMFlow(@Param("qaDTO") QueryALDTO queryALDTO);

	/**
	 * 查询水表月抄表水量
	 * @return
	 */
	Double queryMMeterReadFlow(@Param("qaDTO") QueryALDTO queryALDTO,@Param("lists") List<MeterInfo> lists);

	/**
	 * 查询子分区的AIL排名
	 * @param queryALDTO
	 * @return
	 */
	List<ZoneData> querySubZoneAILRank(@Param("qaDTO") QueryALDTO queryALDTO);

	/**
	 * 查询抄表数据异常信息
	 * @param queryALDTO
	 */
//	MeterReadData queryMeterReadLoss(@Param("lists") List<MeterInfo> lists,@Param("startTime") Integer startTime,@Param("endTime") Integer endTime);

	/**
	 * 查询消防水表读表数据
	 * @param lists
	 * @return
	 */
	Integer queryFsMeterReadData(@Param("lists") List<String> lists,@Param("qaDTO") QueryALDTO queryALDTO);

	/**
	 * 查询月总水量
	 * @return
	 */
	Double queryTotalMFlow(@Param("lists") List<String> lists,@Param("qaDTO") QueryALDTO queryALDTO);
	
	/**
	 * 查询水表月水量
	 * @param lists
	 * @param time
	 * @return
	 */
	List<MeterMFlowData> queryMeterMFlow(@Param("lists") List<MeterInfo> lists,@Param("qaDTO") QueryALDTO queryALDTO);

	/**
	 * 查询水表零月水量数据
	 * @param lists
	 * @param time
	 * @return
	 */
	List<MeterMFlowData> queryMeterMZeroFlow(@Param("qaDTO") QueryALDTO queryALDTO);

	/**
	 * 查询小口径水表平均月水量
	 * @param lists
	 * @param time
	 * @return
	 */
	List<MeterMFlowData> queryMeterMAvgFlow(@Param("qaDTO") QueryALDTO queryALDTO);
	
	/**
	 * 查询水表月最高用水量
	 * @param lists
	 * @param time
	 * @return
	 */
	List<Double> queryMeterMMaxFlow(@Param("lists") List<String> lists,@Param("qaDTO") QueryALDTO queryALDTO);

	/**
	 * 查询用水异常水表数据
	 */
	List<DrqlMeterErrUseData> queryMeterErrUseData(@Param("lists") List<MeterInfo> lists,@Param("qaDTO") QueryALDTO queryALDTO);

	/**
	 * 查询水表信息
	 * @param zoneNo
	 * @return
	 */
	List<MeterInfo> queryMeterInfoByZoneNo(@Param("zoneNo") String zoneNo);
	
	/**
	 * 查询全网水表月抄表水量
	 * @return
	 */
	Double queryWNMMeterReadFlow(@Param("qaDTO") QueryALDTO queryALDTO,@Param("lists") List<MeterInfo> lists);

	/**
	 * 查询抄表不固定时间信息
	 * @param queryALDTO
	 * @return
	 */
	MeterRTimeUnset queryRTimeUnset(@Param("qaDTO") QueryALDTO queryALDTO);
	
	/**
	 * 查询大口径月总水量
	 * @return
	 */
	Double queryBigDnTotalMFlow(@Param("startTime") Integer startTime,@Param("endTime") Integer endTime);
	
	/**
	 * 查询小口径月总水量
	 * @return
	 */
	Double querySmallDnTotalMFlow(@Param("startTime") Integer startTime,@Param("endTime") Integer endTime);
	
	/**
	 * 根据用户编号查询月流量集合
	 * @param ctmNum
	 * @return
	 */
	List<MonthFlowData> queryMFlowByCtmNum(@Param("ctmNum") String ctmNum,@Param("startTime") Integer startTime,@Param("endTime") Integer endTime);

	/**
	 * 查询大口径零流量水表数据
	 * @param qaDTO
	 * @return
	 */
	List<DrqlBDnZeroFlowDataListVO> queryDrqlBDnZeroFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);

	/**
	 * 查询大口径零流量水表数据总条数
	 * @param qaDTO
	 * @return
	 */
	Integer countDrqlBDnZeroFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);

	/**
	 * 查询大口径低流量过载水表数据
	 * @param qaDTO
	 * @return
	 */
	List<DrqlBDnLHFlowDataListVO> queryDrqlBDnLHFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);

	/**
	 * 查询大口径低流量过载水表数据总条数
	 * @param qaDTO
	 * @return
	 */
	Integer countDrqlBDnLHFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);
	
	/**
	 * 查询大口径用水异常
	 * @param qaDTO
	 * @return
	 */
	List<DrqlBDnErrFlowDataListVO> queryDrqlBDnErrFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);

	/**
	 * 查询大口径用水异常总条数
	 * @param qaDTO
	 * @return
	 */
	Integer countDrqlBDnErrFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);

	
	/**
	 * 查询小口径零流量水表数据
	 * @param qaDTO
	 * @return
	 */
	List<DrqlSDnZeroFlowDataListVO> queryDrqlSDnZeroFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);
	
	/**
	 * 查询小口径零流量水表数据总条数
	 * @param qaDTO
	 * @return
	 */
	Integer countDrqlSDnZeroFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);

	/**
	 * 查询小口径低流量过载水表数据
	 * @param qaDTO
	 * @return
	 */
	List<DrqlSDnLHFlowDataListVO> queryDrqlSDnLHFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);
	
	/**
	 * 查询小口径低流量过载水表数据总条数
	 * @param qaDTO
	 * @return
	 */
	Integer countDrqlSDnLHFlowDataList(@Param("qaDTO") QueryALListDTO qaDTO);
	
	/**
	 * 查询已抄表的数量信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<MeterReadNumInfo> queryMeterReadNum(@Param("startTime") Integer startTime,@Param("endTime") Integer endTime);

	/**
	 * 记录诊断报告默认结果，每个月定时记录一次，
	 * 诊断报告默认计算最新一年的数据，默认展示，计算至少需要两个月（2月默认计算去年1月到今年1月）
	 * @param tenantid
	 * @param result
	 */
	void addDrReportResult(@Param("tenantid") String tenantid,@Param("result") String result);
	
	/**
	 * 根据租户id返回诊断报告结果
	 * @param tenantid
	 * @return
	 */
	String getDrReportResult(@Param("tenantid") String tenantid);
}
