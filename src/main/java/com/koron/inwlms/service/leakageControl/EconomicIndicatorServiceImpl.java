package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.inwlms.bean.DTO.leakageControl.PartitionInvestDTO;
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
	
	@TaskAnnotation("updatePartitionInvest")
	@Override
	public Integer updatePartitionInvest(SessionFactory factory, List<PartitionInvestDTO> partitionInvestDTOList) {
		EconomicIndicatorMapper mapper = factory.getMapper(EconomicIndicatorMapper.class);
		//先删除数据库数据
		mapper.deletePartitionInvest(partitionInvestDTOList.get(0).getType());
		//添加修改后的数据
		Integer addNum = mapper.addPartitionInvest(partitionInvestDTOList);
		
		return addNum;
	}
	
}
