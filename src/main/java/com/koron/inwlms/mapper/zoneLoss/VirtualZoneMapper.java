package com.koron.inwlms.mapper.zoneLoss;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVSZoneListDTO;
import com.koron.inwlms.bean.VO.zoneLoss.VirtualZoneVO;

/** 
 * @author lzy
 * @Date 2020/06/22
 */

@Repository
public interface VirtualZoneMapper {

	public List<VirtualZoneVO> queryVirtualZoneList(QueryVSZoneListDTO queryVSZoneListDTO);
	VirtualZoneVO querySingleVirtualZone(QueryVSZoneListDTO queryVSZoneListDTO);
	
	//获取该分区的直接下级分区
	@Select("select p_code as \"zoneNo\" \r\n" + 
			"		from gis_exist_zone as a\r\n" + 
			"		left join tbltree as b on a.\"p_code\" = b.\"foreignkey\"\r\n" + 
			"		where (b.\"seq\" & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq} and (b.\"seq\" & ((1::int8 << (62 - #{parentMask}-#{mask} - #{childMask}))-1)) = 0 and type = #{type} \r\n" + 
			"")
	List<String> queryChildren(LongTreeBean bean);
	
	//获取一级分区下的所有DMA
	@Select("select p_code as \"zoneNo\" \r\n" + 
			"		from gis_exist_zone as a\r\n" + 
			"		left join tbltree as b on a.\"p_code\" = b.\"foreignkey\"\r\n" + 
			"		where (b.\"seq\" & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq}  and type = #{type} and a.rank = 'W101640003'\r\n" + 
			"")
	List<String> queryAllDMA(LongTreeBean bean);
	
	
}
