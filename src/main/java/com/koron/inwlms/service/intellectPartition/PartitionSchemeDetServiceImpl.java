package com.koron.inwlms.service.intellectPartition;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.intellectPartition.AutomaticPartitionDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.GisZoneData;
import com.koron.inwlms.bean.DTO.intellectPartition.GisZonePipeData;
import com.koron.inwlms.bean.DTO.intellectPartition.KafkaReturnData;
import com.koron.inwlms.bean.DTO.intellectPartition.PipePreZoneRelationDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.PreZoneRelationDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.ZoneSchemeData;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;
import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;
import com.koron.inwlms.mapper.intellectPartition.PartitionSchemeMapper;
import com.koron.inwlms.mapper.leakageControl.EconomicIndicatorMapper;
import com.koron.inwlms.util.InterfaceUtil;
import com.koron.util.Constant;


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
	
	/**
	 * 获取gis分区管线信息，调用模型接口传入参数
	 */
	@TaskAnnotation("getModelReturnData")
	@Override
	public String getModelReturnData(SessionFactory factory,AutomaticPartitionDTO automaticPartitionDTO,TotalSchemeDet totalSchemeDet) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		String code = mapper.addTotalSchemeDet(totalSchemeDet);
		//TODO 调用gis接口，获取所选分区管线数据
		String gisPath = "";
		String gisJsonData = "";
		JsonObject gisResultData = InterfaceUtil.interfaceOfPostUtil(gisPath, gisJsonData);
		Gson gson = new Gson(); 
		List<GisZonePipeData> pipeinfo = gson.fromJson(gisResultData, new TypeToken<List<GisZonePipeData>>(){}.getType());
		//查询管径数据
		 //TODO 判断分区类型（PMA或者DMA）
		String type = "";
		EconomicIndicatorMapper emapper = factory.getMapper(EconomicIndicatorMapper.class);
		List<PartitionInvestVO> partInvestList = emapper.queryPartitionInvestCaliber(type);
		for(GisZonePipeData gisZonePipeData : pipeinfo) {
			String caliber = gisZonePipeData.getPip_d() +"";
			for(PartitionInvestVO partitionInvestVO : partInvestList) {
				if(partitionInvestVO.getCaliber().equals(caliber)) {
					gisZonePipeData.setPip_price(partitionInvestVO.getMoney());
				}
			}
		}
		
		//TODO 判断是否有子分区
		boolean zoneFlag = false;
						
						
		if(zoneFlag) {
			//进行分区评估
			GisZoneData gisZoneData = new GisZoneData();
			gisZoneData.setPipe_info(pipeinfo);
			//TODO 分区与管线关系
			if(automaticPartitionDTO.getZoneType() == 0) {
				
			}
			gisZoneData.setTotal_plan_code(code);
			String mlPath = "";
			JsonObject mlResultData = InterfaceUtil.interfaceOfPostUtil(mlPath, gson.toJson(pipeinfo));
		}
		
				
		//TODO 调用模型算法接口推送数据，等待模型返回已接收信号时
		GisZoneData gisZoneData = new GisZoneData();
		gisZoneData.setPipe_info(pipeinfo);
		gisZoneData.setNum_up(automaticPartitionDTO.getMaxZone());
		gisZoneData.setNum_down(automaticPartitionDTO.getMinZone());
		gisZoneData.setTotal_plan_code(code);
		String mlPath = "";
		JsonObject mlResultData = InterfaceUtil.interfaceOfPostUtil(mlPath, gson.toJson(pipeinfo));
		//TODO 解析返回数据
		return null;
	}
	
	/**
	 * kafka消费智能分区的数据存库
	 */
	@TaskAnnotation("addKafkaData")
	@Override
	public Integer addKafkaData(SessionFactory factory,KafkaReturnData kafkaReturnData) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		
		
		
		
		SchemeDet SchemeDet = new SchemeDet();
        SchemeDet.setTotalSchemeCode(kafkaReturnData.getTotal_plan_code());
        SchemeDet.setFlowNum(kafkaReturnData.getFlow_num());
        SchemeDet.setTightness(kafkaReturnData.getTightness());
        SchemeDet.setEcomony(kafkaReturnData.getEconomy());
        SchemeDet.setRegionNum(kafkaReturnData.getPartition_no());
		String schemeDetCode = mapper.addSchemeDet(SchemeDet);
		
		for(ZoneSchemeData zoneSchemeData : kafkaReturnData.getPartition_detail()) {
			String code = UUID.randomUUID().toString();
			List<PipePreZoneRelationDTO> preZoneList = new ArrayList<>();
			for(String pipeCode : zoneSchemeData.getList()) {
				PipePreZoneRelationDTO preZone = new PipePreZoneRelationDTO();		
				preZone.setPipeCode(pipeCode);
				preZone.setrCode(code);	
				preZone.setIsBorder(Constant.IS_BORDER_F);
				preZoneList.add(preZone);
			}
			PreZoneRelationDTO preZoneRelationDTO = new PreZoneRelationDTO();
			preZoneRelationDTO.setrCode(code);
			preZoneRelationDTO.setZoneSchemeCode(schemeDetCode);
			mapper.addPreZone(preZoneRelationDTO);
			mapper.addPipePreZone(preZoneList);
		}
		
		return kafkaReturnData.getPartition_detail().size();
	}
	
	
}
