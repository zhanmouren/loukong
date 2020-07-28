package com.koron.inwlms.mapper.report;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.report.ZoneMnfDTO;
import com.koron.inwlms.bean.VO.leakageControl.GisExistZoneVO;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * 分析报表
 * @author koron 刘刚
 *
 */
@Repository
public interface AnalysisReportMapper {
	
	List<GisExistZoneVO> queryZoneData(ZoneMnfDTO zoneMnfDTO);
	
	Integer queryZoneDataNum(ZoneMnfDTO zoneMnfDTO);
	
	@Select("select id from lc_app_dim_month where \"yearMonth\" = #{yearMonth} ")
	Integer queryMonthId(@Param("yearMonth") Integer yearMonth);

}
