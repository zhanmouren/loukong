package com.koron.inwlms.mapper.master.leakageControl;

import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;

@Repository
@EnvSource("_default")
public interface EconomicIndicatorMapper {
	
	PartitionInvestVO queryPartitionInvest();

}
