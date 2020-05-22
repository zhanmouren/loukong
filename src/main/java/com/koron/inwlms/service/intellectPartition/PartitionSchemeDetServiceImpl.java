package com.koron.inwlms.service.intellectPartition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import com.koron.inwlms.bean.VO.intellectPartition.ModelReturn;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;
import com.koron.inwlms.bean.VO.leakageControl.AlertSchemeListVO;
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
	public ModelReturn getModelReturnData(SessionFactory factory,AutomaticPartitionDTO automaticPartitionDTO,TotalSchemeDet totalSchemeDet) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		
		//TODO 调用gis接口，获取所选分区管线数据
		String gisPath = "";
		String gisJsonData = "{}";
		JsonObject gisResultData = InterfaceUtil.interfaceOfPostUtil(gisPath, gisJsonData);
		String code1 = gisResultData.get("code").getAsString();
		JsonArray gisdata = gisResultData.getAsJsonArray("data");
		Gson gson = new Gson();
		//List<AlertSchemeListVO> pipeinfo1 = gson.fromJson(data, new TypeToken<List<AlertSchemeListVO>>(){}.getType());
		List<GisZonePipeData> pipeinfo = gson.fromJson(gisdata, new TypeToken<List<GisZonePipeData>>(){}.getType());
		String code = mapper.addTotalSchemeDet(totalSchemeDet);
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
			gisZoneData.setPip_info(pipeinfo);
			//TODO 分区与管线关系
			if(automaticPartitionDTO.getZoneType() == 0) {
				
			}
			gisZoneData.setTotal_plan_code(code);
			String mlPath = "";
			JsonObject mlResultData = InterfaceUtil.interfaceOfPostUtil(mlPath, gson.toJson(pipeinfo));
		}
		
		
		
		
		
		
		//Gson gson = new Gson();
		//List<GisZonePipeData> pipeinfo = readText();
		
//		//分区评估
//		GisZoneData gisZoneData = new GisZoneData();
//		gisZoneData.setPip_info(pipeinfo);
//		gisZoneData.setR_id(27);
//		gisZoneData.setRegion_no(234);
//		String data101 = gson.toJson(gisZoneData);
//		String mlPath = "http://10.13.1.11:7500/estimate/estimateParamVerify";
//		JsonObject mlResultData = InterfaceUtil.interfaceOfPostUtil(mlPath, data101);
//		String mlPath1 = "http://10.13.1.11:7500/estimate/receiveEstimateModel";
//		JsonObject mlResultData1 = InterfaceUtil.interfaceOfPostUtil(mlPath1, data101);
				
		//TODO 调用模型算法接口推送数据，等待模型返回已接收信号时
		GisZoneData gisZoneData = new GisZoneData();
		gisZoneData.setPip_info(pipeinfo);
		gisZoneData.setNum_up(automaticPartitionDTO.getMaxZone());
		gisZoneData.setNum_down(automaticPartitionDTO.getMinZone());
//		gisZoneData.setNum_up(31);
//		gisZoneData.setNum_down(30);
		gisZoneData.setTotal_plan_code(code);
		//gisZoneData.setTotal_plan_code(17);
		String data101 = gson.toJson(gisZoneData);
		String mlPath = "http://10.13.1.11:7500/partition/partitionParamVerify";
		JsonObject mlResultData = InterfaceUtil.interfaceOfPostUtil(mlPath, data101);
		//TODO 解析返回数据
		String codejy = mlResultData.get("flag").getAsString();
		if(codejy.equals("1")) {
			String rpPath = "http://10.13.1.11:7500/partition/receivePartitionModel";
			JsonObject rpResultData = InterfaceUtil.interfaceOfPostUtil(rpPath, gson.toJson(gisZoneData));
			String codeml = rpResultData.get("flag").getAsString();
			if(codeml.equals("1")) {
				JsonObject mldata = rpResultData.get("data").getAsJsonObject();
				ModelReturn modelreturn = gson.fromJson(mldata, new TypeToken<ModelReturn>(){}.getType());
				return modelreturn;
			}
		}
		
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
	
	@TaskAnnotation("test")
	@Override
	public String test(SessionFactory factory,AutomaticPartitionDTO automaticPartitionDTO,TotalSchemeDet totalSchemeDet) {
		Gson gson = new Gson();
		List<GisZonePipeData> pipeinfo = readText();
		
		
//		//分区评估
//		GisZoneData gisZoneData = new GisZoneData();
//		gisZoneData.setPip_info(pipeinfo);
//		gisZoneData.setR_id(10001);
//		gisZoneData.setRegion_no(234);
//		String data101 = gson.toJson(gisZoneData);
//		String mlPath = "http://10.13.1.11:7500/estimate/estimateParamVerify";
//		JsonObject mlResultData = InterfaceUtil.interfaceOfPostUtil(mlPath, data101);
//		String mlPath1 = "http://10.13.1.11:7500/estimate/receiveEstimateModel";
//		JsonObject mlResultData1 = InterfaceUtil.interfaceOfPostUtil(mlPath1, data101);
				
		//TODO 调用模型算法接口推送数据，等待模型返回已接收信号时
		GisZoneData gisZoneData = new GisZoneData();
		gisZoneData.setPip_info(pipeinfo);
		gisZoneData.setNum_up(automaticPartitionDTO.getMaxZone());
		gisZoneData.setNum_down(automaticPartitionDTO.getMinZone());
		gisZoneData.setNum_up(20);
		gisZoneData.setNum_down(5);
		gisZoneData.setTotal_plan_code("888");
		String data101 = gson.toJson(gisZoneData);
		String mlPath = "http://10.13.1.11:7500/partition/partitionParamVerify";
		JsonObject mlResultData = InterfaceUtil.interfaceOfPostUtil(mlPath, data101);
		//TODO 解析返回数据
		String codejy = mlResultData.get("flag").getAsString();
		if(codejy.equals("1")) {
			String rpPath = "http://10.13.1.11:7500/partition/receivePartitionModel";
			JsonObject rpResultData = InterfaceUtil.interfaceOfPostUtil(rpPath, gson.toJson(gisZoneData));
			String codeml = rpResultData.get("flag").getAsString();
			if(codeml.equals("1")) {
				JsonObject mldata = rpResultData.get("data").getAsJsonObject();
				ModelReturn modelreturn = gson.fromJson(mldata, new TypeToken<ModelReturn>(){}.getType());
				return null;
			}
		}
		return null;
	}
	
	public List<GisZonePipeData> readText() {
		File file = new File("D:/智能分区-10000.txt");
		StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        String data = result.toString();
        Gson gsonJson = new Gson();
        List<GisZonePipeData> modelreturn = gsonJson.fromJson(data, new TypeToken<List<GisZonePipeData>>(){}.getType());
        return modelreturn;
	}
	
	
}
