package com.koron.inwlms.mapper.common;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.koron.inwlms.bean.DTO.common.MinMonitorPoint;
import com.koron.inwlms.bean.DTO.common.PointParamDTO;
import com.koron.inwlms.bean.VO.common.GdhRaw;
import com.koron.inwlms.bean.VO.common.GisScadaStation;
import com.koron.inwlms.bean.VO.common.PointSensor;

/**
 * 监测点历史数据
 * @author 刘刚
 *
 */
public interface PointHistoryDataMapper {

	@Select("select * from nw_gdh_ssensor where station = #{station}")
	List<PointSensor> queryPointType(@Param("station") String station);
	
	@Select("select * from nw_gdh_raw where code = #{code} and datatime between #{start} and #{end}")
	List<GdhRaw> queryPointHistoryData(@Param("code") String code,@Param("start") Integer start,@Param("end") Integer end);
	
	@Select("select * from nw_monitorpoint_hour where \"analysisDate\" = #{datatime}")
	List<MinMonitorPoint> queryPointHourData(@Param("datatime") Date datatime);
	
	@Select("select a.code as code, round( a.value/b.precision :: NUMERIC, 2 ) as value, a.\"analysisDate\" as \"analysisDate\", a.\"stationCode\" as \"stationCode\"\r\n" + 
			"from nw_monitorpoint_hour a left join lc_app_dim_indicator b on a.code = b.code where a.code = #{code} and a.\"analysisDate\" between '${start}' and '${end}'\r\n" + 
			" and a.\"stationCode\"  = #{stationCode}")
	List<MinMonitorPoint> queryPointHourDataByDay(@Param("code") String code,@Param("start") Date start, @Param("end") Date end,@Param("stationCode") String stationCode);
	
	@Select("select id as id, name as name, p_code as pCode, \"zoneNo\" as \"zoneNo\" from gis_scada_station")
	List<GisScadaStation> queryAllPointMessage();
	
	@Select("select id as id, name as name, p_code as pCode, \"zoneNo\" as \"zoneNo\" from gis_scada_station where \"zoneNo\" = #{zoneNo}")
	List<GisScadaStation> queryPointByZoneNo(@Param("zoneNo") String zoneNo);
	
	List<GisScadaStation> queryPointMessageByName(PointParamDTO pointParamDTO);
	
}
