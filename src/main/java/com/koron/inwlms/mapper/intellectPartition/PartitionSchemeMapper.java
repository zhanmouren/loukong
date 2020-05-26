package com.koron.inwlms.mapper.intellectPartition;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.koron.inwlms.bean.DTO.intellectPartition.PipePreZoneRelationDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.PreZoneRelationDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;

/**
 * 分区方案mapper
 * @author 刘刚
 *
 */
public interface PartitionSchemeMapper {

	
	Integer deleteSchemeDet(List<String> codes);
	
	List<SchemeDet> querySchemeDet(@Param("totalSchemeCode") String totalSchemeCode);
	
	Integer deleteTotalSchemeDet(List<String> codes);
	
	String addTotalSchemeDet(TotalSchemeDet totalSchemeDet);
	
	List<TotalSchemeDet> queryTotalSchemeDet(TotalSchemeDetDTO totalSchemeDetDTO);
	
	
	Integer updateTotalSchemeDet(List<String> codes);
	
	Integer updateSchemeDet(List<String> codes);
	
	String addSchemeDet(SchemeDet schemeDet);
	
	Integer addPreZone(PreZoneRelationDTO preZoneRelationDTO);
	
	Integer addPipePreZone(List<PipePreZoneRelationDTO> pipeList);
	
	Integer deleteSchemeDetByCode(List<Integer> ids);
	
}
