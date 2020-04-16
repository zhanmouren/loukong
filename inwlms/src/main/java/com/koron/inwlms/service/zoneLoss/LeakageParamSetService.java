package com.koron.inwlms.service.zoneLoss;


import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.zoneLoss.LegitimateNightUseDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.LegitimateNightUseEditDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;

/**
 * 漏损参数设置接口
 * @author csh
 * @Date 2020/04/16
 *
 */
public interface LeakageParamSetService {

	 /**
     * 查询泄漏量指标
     * @return
     */
    String queryLeakageExponent(SessionFactory factory);
    
    /**
     * 更新泄漏量指标
     * @return
     */
    void updateLeakageExponent(SessionFactory factory,String value);
	
    /**
	 * 查询合理夜晚使用量
	 * @param factory
	 * @param code
	 * @return
	 */
	PageListVO queryLegitimateNightUseList(SessionFactory factory,LegitimateNightUseDTO lnueDTO);
	
	/**
	 * 更新合理夜晚使用量
	 * @param factory
	 * @param lnueDTO
	 */
	void updateLegitimateNightUse(SessionFactory factory, LegitimateNightUseEditDTO lnueDTO);
    
}
