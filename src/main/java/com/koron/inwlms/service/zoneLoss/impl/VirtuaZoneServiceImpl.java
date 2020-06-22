package com.koron.inwlms.service.zoneLoss.impl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.mapper.TreeMapper;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVSZoneListDTO;
import com.koron.inwlms.bean.VO.indexData.TreeZoneVO;
import com.koron.inwlms.bean.VO.zoneLoss.VSZoneListVO;
import com.koron.inwlms.bean.VO.zoneLoss.VirtualZoneVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneDetailInfoVO;
import com.koron.inwlms.mapper.zoneLoss.VirtualZoneMapper;
import com.koron.inwlms.service.zoneLoss.VirtualZoneService;

/**
 * @createTime 2020/06/22
 * @author lzy
 */
@Service
public class VirtuaZoneServiceImpl implements VirtualZoneService{
 
	@TaskAnnotation("queryVirtualZone")
	@Override
	public List<VirtualZoneVO> queryVirtualZone(SessionFactory factory, QueryVSZoneListDTO queryVSZoneListDTO) {
		VirtualZoneMapper virtualZoneMapper = factory.getMapper(VirtualZoneMapper.class);
		TreeMapper treeMapper = factory.getMapper(TreeMapper.class);	
		List<VirtualZoneVO> virtualZoneList = virtualZoneMapper.queryVirtualZoneList(queryVSZoneListDTO);
		String virtualZoneType = queryVSZoneListDTO.getVirtualZoneType();
		int type  = 2;
		if(virtualZoneList == null) {
			return null;
		}
		//“Non二级 in 一级” 或 “NonDMA in 二级” 或 “Non子DMA in DMA”
		if(virtualZoneType.equals("L101980001") || virtualZoneType.equals("L101980003") || virtualZoneType.equals("L101980004")) {
			for(int i=0;i < virtualZoneList.size();i++) {
				String foreignKey = virtualZoneList.get(i).getBaseRegion();
				LongTreeBean node=treeMapper.getBeanByForeignIdType(type,foreignKey);
				if(node == null) {
					return null;
				}
				else{
					List<String> zoneList=virtualZoneMapper.queryChildren(node);
					for(int j=0;j < zoneList.size();j++) {
						if(zoneList.get(j).equals(foreignKey)) {
							zoneList.remove(j);
						}
					}
					virtualZoneList.get(i).setCutRegion(zoneList);
				}
			}
		}
		
		//NonDMA in 一级
		if(virtualZoneType.equals("L101980002")) {
			for(int i=0;i < virtualZoneList.size();i++) {
				String foreignKey = virtualZoneList.get(i).getBaseRegion();
				LongTreeBean node=treeMapper.getBeanByForeignIdType(type,foreignKey);
				if(node == null) {
					return null;
				}
				else{
					List<String> zoneList=virtualZoneMapper.queryAllDMA(node);
					for(int j=0;j < zoneList.size();j++) {
						if(zoneList.get(j).equals(foreignKey)) {
							zoneList.remove(j);
						}
					}
					virtualZoneList.get(i).setCutRegion(zoneList);
				}
			}
		}
//			for(int i=0;i < virtualZoneList.size();i++) {
//				String foreignKey = virtualZoneList.get(i).getBaseRegion();
//				LongTreeBean node=treeMapper.getBeanByForeignIdType(type,foreignKey);
//				if(node == null) {
//					return null;
//				}
//				else{
//					List<ZoneDetailInfoVO> zoneList=virtualZoneMapper.queryChildren(node);
//					virtualZoneList.get(i).setCutRegion(zoneList);
//				}
//			}
//		}
		
		
		return virtualZoneList;
	}

}
