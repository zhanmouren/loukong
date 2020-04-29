package com.koron.inwlms.mapper.common;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.koron.inwlms.bean.DTO.common.Indicator;
import com.koron.inwlms.bean.VO.common.SysConfigVO;

/**
 * 
 * 通用Mapper
 * @author lu
 *
 */
public interface CommonMapper {

	List<SysConfigVO> querySysConfig();
	
	@Select("select * from \"LC_APP_Dim_Indicator\" where code = #{code}")
	Indicator queryIndicatorByCode(@Param("code") String code); 
	
}
