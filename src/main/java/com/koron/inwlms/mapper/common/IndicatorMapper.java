package com.koron.inwlms.mapper.common;

import java.util.List;

import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;

/**
 * 
 * 指标Mapper
 * @author csh
 * @Date 2020/04/14
 */
public interface IndicatorMapper {
	/**
	 * 查询基础指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryBaseIndicData(IndicatorDTO indicatorDTO);
	
	/**
	 * 查询水平衡基础指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryWBBaseIndicData(IndicatorDTO indicatorDTO);
	
	/**
	 * 查询分区漏损指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryZoneLossIndicData(IndicatorDTO indicatorDTO);
	
	/**
	 * 查询分区监测指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryZoneMoniIndicData(IndicatorDTO indicatorDTO);
	
	/**
	 * 查询分区渗漏指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryLeakIndicData(IndicatorDTO indicatorDTO);
	
	/**
	 * 查询分区表观漏损指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryZoneApparentIndicData(IndicatorDTO indicatorDTO);
	
	/**
	 * 查询全网指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryCompanyIndicData(IndicatorDTO indicatorDTO);
	
}
