package com.koron.inwlms.service.common;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfoLossData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterUserTimeVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;

/**
 * 户表接口（临时）
 * @author csh
 * @Date 2020/03/31
 *
 */
public interface MeterService {

	/**
	 * 查询户表信息不完善接口（按照日期，口径，表分类，用水类别不完善的水表个数）
	 * @param factory
	 * @param zoneNo
	 * @return
	 */
	MeterInfoLossData queryMeterInfoLossData(SessionFactory factory,List<String> lists);

	/**
	 * 查询水表使用时间信息
	 * @param factory
	 * @param zoneNo
	 * @return
	 */
	List<MeterUserTimeVO> queryMeterUserTimeInfo(SessionFactory factory,List<String> lists);

}
