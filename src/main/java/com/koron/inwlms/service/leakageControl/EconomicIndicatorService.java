package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.leakageControl.PartitionInvestDTO;
import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;

public interface EconomicIndicatorService {

	List<PartitionInvestVO> queryPartitionInvest(SessionFactory factory,String type);

	Integer updatePartitionInvest(SessionFactory factory, List<PartitionInvestDTO> partitionInvestDTOList);

	
	
}
