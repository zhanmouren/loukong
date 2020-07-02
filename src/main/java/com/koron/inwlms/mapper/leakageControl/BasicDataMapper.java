package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.leakageControl.BasicDataParam;
import com.koron.inwlms.bean.VO.leakageControl.GisZonePointVO;
import com.koron.inwlms.bean.VO.leakageControl.PointHourData;
import com.koron.inwlms.bean.VO.leakageControl.ZoneAndPoint;

@Repository
public interface BasicDataMapper {
	
	PointHourData queryPointHourData(BasicDataParam basicDataParam); 
	
	@Select("select * from gis_zone_point where \"zoneNo\" = #{zoneNo}")
	List<GisZonePointVO> queryZonePoint(@Param("zoneNo") String zoneNo);
	
	List<PointHourData> queryPointHourDataByTime(BasicDataParam basicDataParam);
	
	@Select("select * from gis_zone_point where \"zoneNo\" = #{code}")
	List<ZoneAndPoint> queryPointByZoneCode(@Param("code") String code);

}
