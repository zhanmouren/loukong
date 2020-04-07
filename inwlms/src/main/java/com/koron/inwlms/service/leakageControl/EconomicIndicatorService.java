package com.koron.inwlms.service.leakageControl;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;

public interface EconomicIndicatorService {

	PartitionInvestVO queryPartitionInvest(SessionFactory factory);

	
	
}
