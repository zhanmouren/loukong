package com.koron.inwlms.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.koron.inwlms.bean.VO.common.GdhRaw;
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
}
