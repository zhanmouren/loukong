package com.koron.inwlms.mapper.zoneLoss;

import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.VO.zoneLoss.IndicatorInfo;

/**
 * 分区漏损-水平衡分析mapper
 * @author csh
 * @Date 2020/04/07
 */
@Repository
public interface ZoneLossAnaMapper {

	/**
	 * 查询指标分类信息
	 * @param code
	 * @return
	 */
	IndicatorInfo queryIndicLevel2Info(String code);

	
}
