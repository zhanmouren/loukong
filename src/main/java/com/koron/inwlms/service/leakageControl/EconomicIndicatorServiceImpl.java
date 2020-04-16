package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;
import com.koron.inwlms.mapper.leakageControl.EconomicIndicatorMapper;


public class EconomicIndicatorServiceImpl implements EconomicIndicatorService{

	@TaskAnnotation("queryPartitionInvest")
	@Override
	public List<PartitionInvestVO> queryPartitionInvest(SessionFactory factory) {
		EconomicIndicatorMapper mapper = factory.getMapper(EconomicIndicatorMapper.class);
		List<PartitionInvestVO> list = mapper.queryPartitionInvest();
		return list;
	}
	
}
