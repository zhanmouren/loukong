package com.koron.inwlms.mapper.report;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.report.ZoneMnfDTO;
import com.koron.inwlms.bean.VO.leakageControl.GisExistZoneVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.FlowMeterData;

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
	
	@Select("select \"meterNo\" from gis_zone_meter where \"zoneNo\" = #{zoneCode}")
	List<String> queryMeterNoByZoneCode(@Param("zoneCode") String zoneCode);
	
	@Select("select \"CTM_NUM\" as \"ctmNum\", \"YS_NAME\" as \"ysName\" from rw_fct_ctm where \"BOOK_NUM\" = #{bookNum}")
	List<FlowMeterData> queryCtmByBookNum(@Param("bookNum") String bookNum);
	
	@Select("select \"REAL_NUM\" from rw_fct_cb where \"CTM_NUM\" = #{ctmNum} and \"MONTH_ID\" = #{monthId}")
	Double queryMeterValueByMonth(@Param("ctmNum") String ctmNum,@Param("monthId") Integer monthId);
	
	@Select("select distinct \"YS_NAME\" from rw_fct_ctm limit #{pageCount} OFFSET #{pageCount}*(#{page}-1);")
	List<String> queryMeterTypeName(@Param("pageCount") Integer pageCount,@Param("page") Integer page);
	
	@Select("select count(*) from (select distinct \"YS_NAME\" from rw_fct_ctm)")
	Integer queryMeterTypeNameNum();

}
