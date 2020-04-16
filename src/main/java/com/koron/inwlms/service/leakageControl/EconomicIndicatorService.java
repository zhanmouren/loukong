package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;

public interface EconomicIndicatorService {

	List<PartitionInvestVO> queryPartitionInvest(SessionFactory factory);

	
	
}
