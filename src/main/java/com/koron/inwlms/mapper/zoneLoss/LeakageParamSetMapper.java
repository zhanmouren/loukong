package com.koron.inwlms.mapper.zoneLoss;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.zoneLoss.LegitimateNightUseDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.LegitimateNightUseEditDTO;
import com.koron.inwlms.bean.VO.zoneLoss.IndicatorInfo;
import com.koron.inwlms.bean.VO.zoneLoss.LegitimateNightUseVO;

/**
 * 漏损参数设置mapper
 * @author csh
 * @Date 2020/04/16
 */
@Repository
public interface LeakageParamSetMapper {

	/**
	 * 查询漏损指数
	 * @return
	 */
	String queryLeakageExponent();
	
	/**
	 * 更新漏损指数
	 * @param value
	 */
	void updateLeakageExponent(@Param("value") String value);
	
	/**
	 * 查询合理夜间流量列表
	 * @param code
	 * @return
	 */
	List<LegitimateNightUseVO> queryLegitimateNightUseList(LegitimateNightUseDTO lnuDTO);

	/**
	 * 查询合理夜间流量列表条数
	 * @param code
	 * @return
	 */
	int countLegitimateNightUseList(@Param("code") String code);

	/**
	 * 更新合理夜间流量数据
	 * @param lnueDTO
	 */
	void updateLegitimateNightUse(LegitimateNightUseEditDTO lnueDTO);
    
}
