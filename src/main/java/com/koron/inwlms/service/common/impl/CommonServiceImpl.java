package com.koron.inwlms.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.service.TreeService;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.common.SysConfigVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicNewVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.mapper.common.CommonMapper;
import com.koron.inwlms.mapper.common.GisMapper;
import com.koron.inwlms.service.common.CommonService;
import com.koron.util.Constant;

@Service
public class CommonServiceImpl implements CommonService {

	@TaskAnnotation("querySysConfig")
	@Override
	public List<SysConfigVO> querySysConfig(SessionFactory factory) {
		CommonMapper mapper = factory.getMapper(CommonMapper.class);
		List<SysConfigVO> lists = mapper.querySysConfig();
		return lists;
	}

	//查询所有数据字典说明
	@TaskAnnotation("queryAllDataDic")
	@Override
	public List<DataDicNewVO> queryAllDataDic(SessionFactory factory) {
		CommonMapper mapper = factory.getMapper(CommonMapper.class);
		List<DataDicNewVO> dicList=mapper.queryAllDataDic();
		return dicList;
	}

	@TaskAnnotation("addZoneTreeInfo")
	@Override
	public void addZoneTreeInfo(SessionFactory factory) {
		LongTreeBean parentTree =new LongTreeBean();
		parentTree.setForeignkey("C021002");
		parentTree.setType(2);
		TreeService treeService  =new TreeService();
		LongTreeBean parentBean =treeService.add(factory, new LongTreeBean(), parentTree);
		List<ZoneInfo> firstZoneList = getFirstZone();
		for (ZoneInfo zoneInfo : firstZoneList) {
			LongTreeBean chileTree =new LongTreeBean();
			chileTree.setForeignkey(zoneInfo.getZoneNo());
			chileTree.setType(2);
			//根据treeParentId获取node
			LongTreeBean parent= treeService.getNode(factory, parentTree.getType(), parentTree.getForeignkey());
			    if(parent!=null) {
			      //生成根节点
			      LongTreeBean longTreeBean =treeService.add(factory, parent, chileTree);	
			}
			if(zoneInfo.getZoneNo().equals("C02100201")) {
				List<ZoneInfo> oneSubZoneList = getOneSubZone();
				for (ZoneInfo zoneInfo2 : oneSubZoneList) {
					LongTreeBean chileTree1 =new LongTreeBean();
					chileTree1.setForeignkey(zoneInfo2.getZoneNo());
					chileTree1.setType(2);
					//根据treeParentId获取node
					LongTreeBean parent1= treeService.getNode(factory, 2, zoneInfo.getZoneNo());
					    if(parent1 != null) {
					      //生成根节点
					      LongTreeBean longTreeBean =treeService.add(factory, parent1, chileTree1);	
					}
				}
			}else if(zoneInfo.getZoneNo().equals("C02100202")) {
				List<ZoneInfo> twoSubZone = getTwoSubZone();
				for (ZoneInfo zoneInfo2 : twoSubZone) {
					LongTreeBean chileTree1 =new LongTreeBean();
					chileTree1.setForeignkey(zoneInfo2.getZoneNo());
					chileTree1.setType(2);
					//根据treeParentId获取node
					LongTreeBean parent1= treeService.getNode(factory, 2, zoneInfo.getZoneNo());
					    if(parent1 != null) {
					      //生成根节点
					      LongTreeBean longTreeBean =treeService.add(factory, parent1, chileTree1);	
					}
				}
			}else if(zoneInfo.getZoneNo().equals("C02100203")) {
				List<ZoneInfo> threeSubZone = getThreeSubZone();
				for (ZoneInfo zoneInfo2 : threeSubZone) {
					LongTreeBean chileTree1 =new LongTreeBean();
					chileTree1.setForeignkey(zoneInfo2.getZoneNo());
					chileTree1.setType(2);
					//根据treeParentId获取node
					LongTreeBean parent1= treeService.getNode(factory, 2, zoneInfo.getZoneNo());
					    if(parent1 != null) {
					      //生成根节点
					      LongTreeBean longTreeBean =treeService.add(factory, parent1, chileTree1);	
					}
				}
			}
		}
		
	}
	
	List<ZoneInfo> getFirstZone(){
		List<ZoneInfo> lists = new ArrayList<ZoneInfo>();
		ZoneInfo zoneInfo = new ZoneInfo();
		zoneInfo.setZoneNo("C02100201");
		zoneInfo.setZoneName("江北区");
		lists.add(zoneInfo);
		ZoneInfo zoneInfo1 = new ZoneInfo();
		zoneInfo1.setZoneNo("C02100202");
		zoneInfo1.setZoneName("梅县区");
		lists.add(zoneInfo1);
		ZoneInfo zoneInfo2 = new ZoneInfo();
		zoneInfo2.setZoneNo("C02100203");
		zoneInfo2.setZoneName("江南区");
		lists.add(zoneInfo2);
		return lists;
	}
	
	List<ZoneInfo> getOneSubZone(){
		List<ZoneInfo> lists = new ArrayList<ZoneInfo>();
		ZoneInfo zoneInfo = new ZoneInfo();
		zoneInfo.setZoneNo("C021002L2009");
		lists.add(zoneInfo);
		ZoneInfo zoneInfo1 = new ZoneInfo();
		zoneInfo1.setZoneNo("C021002L2008");
		lists.add(zoneInfo1);
		ZoneInfo zoneInfo2 = new ZoneInfo();
		zoneInfo2.setZoneNo("C021002L2010");
		lists.add(zoneInfo2);
		ZoneInfo zoneInfo3 = new ZoneInfo();
		zoneInfo3.setZoneNo("C021002L2011");
		lists.add(zoneInfo3);
		ZoneInfo zoneInfo4 = new ZoneInfo();
		zoneInfo4.setZoneNo("C021002L2012");
		lists.add(zoneInfo4);
		ZoneInfo zoneInfo5 = new ZoneInfo();
		zoneInfo5.setZoneNo("C021002L2013");
		lists.add(zoneInfo5);
		ZoneInfo zoneInfo6 = new ZoneInfo();
		zoneInfo6.setZoneNo("C021002L2014");
		lists.add(zoneInfo6);
		return lists;
	}
	
	List<ZoneInfo> getTwoSubZone(){
		List<ZoneInfo> lists = new ArrayList<ZoneInfo>();
		ZoneInfo zoneInfo = new ZoneInfo();
		zoneInfo.setZoneNo("C021002L2018");
		lists.add(zoneInfo);
		ZoneInfo zoneInfo1 = new ZoneInfo();
		zoneInfo1.setZoneNo("C021002L2015");
		lists.add(zoneInfo1);
		ZoneInfo zoneInfo2 = new ZoneInfo();
		zoneInfo2.setZoneNo("C021002L2016");
		lists.add(zoneInfo2);
		ZoneInfo zoneInfo3 = new ZoneInfo();
		zoneInfo3.setZoneNo("C021002L2017");
		lists.add(zoneInfo3);
		ZoneInfo zoneInfo4 = new ZoneInfo();
		zoneInfo4.setZoneNo("C021002L2019");
		lists.add(zoneInfo4);
		ZoneInfo zoneInfo5 = new ZoneInfo();
		zoneInfo5.setZoneNo("C021002L2022");
		lists.add(zoneInfo5);
		ZoneInfo zoneInfo6 = new ZoneInfo();
		zoneInfo6.setZoneNo("C021002L2020");
		lists.add(zoneInfo6);
		ZoneInfo zoneInfo7 = new ZoneInfo();
		zoneInfo7.setZoneNo("C021002L2021");
		lists.add(zoneInfo7);
		return lists;
	}
	
	List<ZoneInfo> getThreeSubZone(){
		List<ZoneInfo> lists = new ArrayList<ZoneInfo>();
		ZoneInfo zoneInfo = new ZoneInfo();
		zoneInfo.setZoneNo("C021002L2001");
		lists.add(zoneInfo);
		ZoneInfo zoneInfo1 = new ZoneInfo();
		zoneInfo1.setZoneNo("C021002L2006");
		lists.add(zoneInfo1);
		ZoneInfo zoneInfo2 = new ZoneInfo();
		zoneInfo2.setZoneNo("C021002L2002");
		lists.add(zoneInfo2);
		ZoneInfo zoneInfo3 = new ZoneInfo();
		zoneInfo3.setZoneNo("C021002L2007");
		lists.add(zoneInfo3);
		ZoneInfo zoneInfo4 = new ZoneInfo();
		zoneInfo4.setZoneNo("C021002L2003");
		lists.add(zoneInfo4);
		ZoneInfo zoneInfo5 = new ZoneInfo();
		zoneInfo5.setZoneNo("C021002L2004");
		lists.add(zoneInfo5);
		ZoneInfo zoneInfo6 = new ZoneInfo();
		zoneInfo6.setZoneNo("C021002L2005");
		lists.add(zoneInfo6);
		return lists;
	}
}
