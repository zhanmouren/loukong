package com.koron.indicator.mapper;

import java.util.List;

import com.koron.indicator.bean.AddDayIndicatorDTO;
import com.koron.indicator.bean.AddMonthIndicatorDTO;
import com.koron.indicator.bean.AddYearIndicatorDTO;

/**
 * 指标Mapper
 * @author csh
 * @Date 2020/04/14
 */
public interface AddIndicatorMapper {
	
	/**
	 * 分区漏损日指标入库
	 * @param lists
	 */
	void addZoneLossDIndicDataBatch(List<AddDayIndicatorDTO> lists);
	
	/**
	 * 分区漏损月指标入库
	 * @param lists
	 */
	void addZoneLossMIndicDataBatch(List<AddMonthIndicatorDTO> lists);
	
	/**
	 * 分区漏损年指标入库
	 * @param addYearIndicatorDTO
	 */
	void addZoneLossYIndicData(List<AddYearIndicatorDTO> lists);
	
	/**
	 * 全网日指标入库
	 * @param addDayIndicatorDTO
	 */
	void addCompanyDIndicData(AddDayIndicatorDTO addDayIndicatorDTO);
	
	/**
	 * 全网月指标入库
	 * @param addMonthIndicatorDTO
	 */
	void addCompanyMIndicData(AddMonthIndicatorDTO addMonthIndicatorDTO);
	
	/**
	 * 全网年指标入库
	 * @param addYearIndicatorDTO
	 */
	void addCompanyYIndicData(AddYearIndicatorDTO addYearIndicatorDTO);
}
