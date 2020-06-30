package com.koron.inwlms.mapper.common;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.common.Indicator;
import com.koron.inwlms.bean.VO.common.SysConfigVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicNewVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;

/**
 * 
 * 通用Mapper
 * @author lu
 *
 */
public interface CommonMapper {

	List<SysConfigVO> querySysConfig();
	
	@Select("select * from lc_app_dim_indicator where code = #{code}")
	Indicator queryIndicatorByCode(@Param("code") String code); 
	
	List<DataDicNewVO> queryAllDataDic();
	
}
