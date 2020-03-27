package com.koron.inwlms.mapper.master.apparentLoss;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterFlowVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterQH;

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
     * 查询月表观漏损列表
     * @return
     */
	List<ALListVO> queryMALList(@Param("qaDTO") QueryALListDTO qaDTO,@Param("lists") List<String> lists);
	
	/**
	 * 查询月表观漏损总条数
	 * @param qaDTO
	 * @param lists
	 * @return
	 */
	int countMALList(@Param("qaDTO") QueryALListDTO qaDTO,@Param("lists") List<String> lists);
	
	/**
     * 查询年表观漏损列表
     * @return
     */
	List<ALListVO> queryYALList(@Param("qaDTO") QueryALListDTO qaDTO,@Param("lists") List<String> lists);
	
	/**
	 * 查询年表观漏损总条数
	 * @param qaDTO
	 * @param lists
	 * @return
	 */
	int countYALList(@Param("qaDTO") QueryALListDTO qaDTO,@Param("lists") List<String> lists);
	
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
	List<MeterFlowVO> queryFSMeterMFlow(@Param("qaDTO") QueryALDTO queryALDTO,@Param("lists") List<MeterInfo> lists);
}
