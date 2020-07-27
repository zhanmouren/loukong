package com.koron.inwlms.mapper.report.waterReport;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.report.waterBalanceReport.WB1BalanceDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.WB1BalanceVO;

@Repository
public interface WaterReportMapper {
	//(WB_01)水司及一级分区产销差率同比报表      以月为时间间隔,汇总分析所选运作区或全网在指定时间范围内用水量、产销差和未计量食水用水量。
	 public List<WB1BalanceVO>  queryPartitionData(WB1BalanceDTO wB1BalanceDTO);
	 
	    /**
		 * 查询基础指标数据
		 * @param indicatorDTO
		 * @return
		 */
		List<IndicatorVO> queryBaseIndicData(IndicatorNewDTO indicatorDTO);
		
		/**
		 * 查询水平衡基础指标数据
		 * @param indicatorDTO
		 * @return
		 */
		List<IndicatorVO> queryWBBaseIndicData(IndicatorNewDTO indicatorDTO);
		
		
		/**
		 * 查询全网下的一级分区
		 * @param indicatorDTO
		 * @return
		 */
		List<IndicatorVO> queryZoneFL();
		
		/**
		 * 查询全网下的二级分区
		 * @param indicatorDTO
		 * @return
		 */
		List<IndicatorVO> queryZoneSL();
		
		/**
		 * 查询分区漏损指标数据
		 * @param indicatorDTO
		 * @return
		 */
		List<IndicatorVO> queryZoneLossIndicData(IndicatorNewDTO indicatorDTO);
		
		/**
		 * 查询分区监测指标数据
		 * @param indicatorDTO
		 * @return
		 */
		List<IndicatorVO> queryZoneMoniIndicData(IndicatorNewDTO indicatorDTO);
		
		/**
		 * 查询分区渗漏指标数据
		 * @param indicatorDTO
		 * @return
		 */
		List<IndicatorVO> queryLeakIndicData(IndicatorNewDTO indicatorDTO);
		
		/**
		 * 查询全网指标数据
		 * @param indicatorDTO
		 * @return
		 */
		List<IndicatorVO> queryCompanyIndicData(IndicatorNewDTO indicatorDTO);
}
