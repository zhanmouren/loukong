package com.koron.inwlms.mapper.zoneLoss;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.VO.zoneLoss.BurstLeakAnalysisVO;
import com.koron.inwlms.bean.VO.zoneLoss.IndicatorInfo;
import com.koron.inwlms.bean.VO.zoneLoss.LeakDetailsVO;

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
	
	/**
	 * 查询分区所有漏点信息
	 * @return
	 */
	List<BurstLeakAnalysisVO> queryBurstLeakAnalysis();

	/**
	 * 根据id查询漏点详细信息
	 * @param id
	 * @return
	 */
	LeakDetailsVO queryBurstLeakById(@Param("id") String id);
}
