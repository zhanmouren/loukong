package com.koron.inwlms.mapper.zoneLoss;

import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.zoneLoss.AddVCZoneDTO;

/**
 * 分区漏损-虚拟分区分析mapper
 * @author csh
 * @Date 2020/04/07
 */
@Repository
public interface VZoneLossAnaMapper {

	/**
	 * 查询虚拟分区自增的序列号
	 * @param code
	 * @return
	 */
	Integer querySeqVzoneCode();
	
	/**
	 * 添加虚拟分区（合并）
	 * @param addVCZoneDTO
	 */
	void addVCZone(AddVCZoneDTO addVCZoneDTO);

	/**
	 * 删除虚拟分区（合并）
	 * @param addVCZoneDTO
	 */
	void deleteVCZone(String vZoneNo);

	
	
}
