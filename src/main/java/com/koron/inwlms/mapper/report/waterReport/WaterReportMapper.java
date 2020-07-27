package com.koron.inwlms.mapper.report.waterReport;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.report.waterBalanceReport.WB1BalanceDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.indexData.TreeZoneVO;
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
		
		/**
		 * 查询一级分区
		 */
		 /**
		  * 获取节点之下所有节点和节点名称(树形目录)
		  *
		  * @param bean 节点
		  * @return 节点集合
		  */
		 @Select("select tbltree.*,gis_exist_zone.p_code as code,gis_exist_zone.name,gis_exist_zone.rank,gis_exist_zone.smid from tbltree left join gis_exist_zone on  gis_exist_zone.p_code=tbltree.foreignkey where (seq & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq} and (seq & ((1::int8 << (62 - #{parentMask}-#{mask} - #{childMask}))-1)) = 0 and type = #{type}")
		 public List<TreeZoneVO> queryTreeOneZone(LongTreeBean bean);
}
