package com.koron.inwlms.service.intellectPartition;

import java.text.ParseException;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.intellectPartition.AutomaticPartitionDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.KafkaReturnData;
import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.VO.intellectPartition.ModelReturn;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDetListVO;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDetReturn;
import com.koron.inwlms.bean.VO.intellectPartition.ZonePipeData;
import com.koron.inwlms.bean.VO.intellectPartition.ZonePipeDataReturn;
import com.koron.inwlms.bean.VO.intellectPartition.ZoneRange;

@Service
public interface PartitionSchemeDetService {

	Integer deleteSchemeDet(SessionFactory factory, List<String> codes);

	SchemeDetListVO querySchemeDet(SessionFactory factory, TotalSchemeDetDTO totalSchemeDetDTO);

	Integer deleteTotalSchemeDet(SessionFactory factory, List<String> codes);

	String addTotalSchemeDet(SessionFactory factory, TotalSchemeDet totalSchemeDet);

	TotalSchemeDetReturn queryTotalSchemeDet(SessionFactory factory, TotalSchemeDetDTO totalSchemeDetDTO) throws ParseException;

	Integer changeSchemeDet(SessionFactory factory,List<String> codes,List<Integer> ids);

	ModelReturn getModelReturnData(SessionFactory factory, AutomaticPartitionDTO automaticPartitionDTO,
			TotalSchemeDet totalSchemeDet,String tenantID);

	Integer addKafkaData(SessionFactory factory, KafkaReturnData kafkaReturnData);

	String test(SessionFactory factory, AutomaticPartitionDTO automaticPartitionDTO, TotalSchemeDet totalSchemeDet);

	Integer deleteSchemeDetByCode(SessionFactory factory, List<Integer> ids);

	ZoneRange getZoneNum(SessionFactory factory, AutomaticPartitionDTO automaticPartitionDTO);

	List<ZonePipeDataReturn> getSchemePipeData(SessionFactory factory, Integer schemeId);

	ModelReturn getModelSubnetData(SessionFactory factory, AutomaticPartitionDTO automaticPartitionDTO,
			TotalSchemeDet totalSchemeDet, String tenantID);
	
	

}
