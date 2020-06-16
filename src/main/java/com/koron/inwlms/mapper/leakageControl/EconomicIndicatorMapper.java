package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.leakageControl.PartitionInvestDTO;
import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;

@Repository
public interface EconomicIndicatorMapper {
	
	@Select("select * from app_partitioninvest where type = #{type}")
	List<PartitionInvestVO> queryPartitionInvest(@Param("type") String type);
	
	@Select("select * from app_partitioninvest where type = #{type} and caliber = #{caliber}")
	PartitionInvestVO queryPartitionInvestByCal(@Param("type") String type,@Param("caliber") String caliber);
	
	@Select("delete from app_partitioninvest where type = #{type}")
	Integer deletePartitionInvest(@Param("type") String type);
	
	Integer addPartitionInvest(List<PartitionInvestDTO> partitionInvestDTO);
	
	List<PartitionInvestVO> queryPartitionInvestCaliber(String type);

}
