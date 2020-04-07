package com.koron.inwlms.service.leakageControl;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;

public class EconomicIndicatorServiceImpl implements EconomicIndicatorService{

	@TaskAnnotation("queryPartitionInvest")
	@Override
	public PartitionInvestVO queryPartitionInvest(SessionFactory factory) {
		
		
		return null;
	}
	
}
