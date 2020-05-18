package com.koron.inwlms.service.intellectPartition;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.intellectPartition.AutomaticPartitionDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.KafkaReturnData;
import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;

@Service
public interface PartitionSchemeDetService {

	Integer deleteSchemeDet(SessionFactory factory, List<String> codes);

	List<SchemeDet> querySchemeDet(SessionFactory factory, String totalSchemeCode);

	Integer deleteTotalSchemeDet(SessionFactory factory, List<String> codes);

	String addTotalSchemeDet(SessionFactory factory, TotalSchemeDet totalSchemeDet);

	List<TotalSchemeDet> queryTotalSchemeDet(SessionFactory factory, TotalSchemeDetDTO totalSchemeDetDTO);

	Integer changeSchemeDet(SessionFactory factory, Integer state, List<String> codes);

	String getModelReturnData(SessionFactory factory, AutomaticPartitionDTO automaticPartitionDTO,
			TotalSchemeDet totalSchemeDet);

	Integer addKafkaData(SessionFactory factory, KafkaReturnData kafkaReturnData);
	
	

}
