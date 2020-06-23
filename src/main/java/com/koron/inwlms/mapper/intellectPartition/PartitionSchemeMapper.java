package com.koron.inwlms.mapper.intellectPartition;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.intellectPartition.PipePreZoneRelationDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.PreZoneRelationDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.ZonePipeData;

/**
 * 分区方案mapper
 * @author 刘刚
 *
 */
@Repository
public interface PartitionSchemeMapper {

	
	Integer deleteSchemeDet(List<String> codes);
	
	List<SchemeDet> querySchemeDet(TotalSchemeDetDTO totalSchemeDetDTO);
	
	Integer deleteTotalSchemeDet(List<String> codes);
	
	String addTotalSchemeDet(TotalSchemeDet totalSchemeDet);
	
	List<TotalSchemeDet> queryTotalSchemeDet(TotalSchemeDetDTO totalSchemeDetDTO);
	
	
	Integer updateTotalSchemeDet(List<String> codes);
	
	Integer updateSchemeDet(List<String> codes);
	
	Integer addSchemeDet(SchemeDet schemeDet);
	
	Integer addPreZone(PreZoneRelationDTO preZoneRelationDTO);
	
	Integer addPipePreZone(List<PipePreZoneRelationDTO> pipeList);
	
	Integer deleteSchemeDetByCode(List<Integer> ids);
	
	@Select("select \"rCode\" from gis_prezone where \"zoneSchemeId\" = #{zoneSchemeId}")
	List<String> queryRCodeBySchemeId(@Param("zoneSchemeId") Integer zoneSchemeId);
	
	@Select("select * from gis_pipe_prezone where \"rCode\" = #{code}")
	List<ZonePipeData> queryZonePipeData(@Param("code") String code);
}
