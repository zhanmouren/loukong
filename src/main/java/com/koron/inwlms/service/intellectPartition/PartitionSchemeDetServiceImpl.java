package com.koron.inwlms.service.intellectPartition;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;
import com.koron.inwlms.mapper.intellectPartition.PartitionSchemeMapper;


@Service
public class PartitionSchemeDetServiceImpl implements PartitionSchemeDetService{
	
	/**
	 * 通过方案总表code删除分区方案表数据
	 */
	@TaskAnnotation("deleteSchemeDet")
	@Override
	public Integer deleteSchemeDet(SessionFactory factory,List<String> codes) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		Integer num = mapper.deleteSchemeDet(codes);
		return num;
	}
	
	/**
	 * 通过方案总表code查询分区方案信息
	 */
	@TaskAnnotation("querySchemeDet")
	@Override
	public List<SchemeDet> querySchemeDet(SessionFactory factory,String totalSchemeCode) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		List<SchemeDet> list = mapper.querySchemeDet(totalSchemeCode);
		return list;
	}

	/**
	 * 通过code删除方案总表数据
	 */
	@TaskAnnotation("deleteTotalSchemeDet")
	@Override
	public Integer deleteTotalSchemeDet(SessionFactory factory,List<String> codes) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		Integer num = mapper.deleteTotalSchemeDet(codes);
		if(num > 0) {
			mapper.deleteSchemeDet(codes);
		}
		return num;	
	}
	
	/**
	 * 添加方案总表数据
	 */
	@TaskAnnotation("addTotalSchemeDet")
	@Override
	public String addTotalSchemeDet(SessionFactory factory,TotalSchemeDet totalSchemeDet) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		String num = mapper.addTotalSchemeDet(totalSchemeDet);	
		return num;
	}
	
	/**
	 * 查询方案总表数据
	 */
	@TaskAnnotation("queryTotalSchemeDet")
	@Override
	public List<TotalSchemeDet> queryTotalSchemeDet(SessionFactory factory,TotalSchemeDetDTO totalSchemeDetDTO){
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		List<TotalSchemeDet> list = mapper.queryTotalSchemeDet(totalSchemeDetDTO);
		return list;
	}
	
	@TaskAnnotation("changeSchemeDet")
	@Override
	public Integer changeSchemeDet(SessionFactory factory,Integer state,List<String> codes) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		mapper.updateTotalSchemeDet(codes);
		Integer num = mapper.updateSchemeDet(codes);
		return num;
	}
	
	
}
