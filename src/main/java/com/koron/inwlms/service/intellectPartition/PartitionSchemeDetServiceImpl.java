 package com.koron.inwlms.service.intellectPartition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.AutomaticPartitionDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.GisAllPipeDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.GisZoneData;
import com.koron.inwlms.bean.DTO.intellectPartition.GisZonePipeData;
import com.koron.inwlms.bean.DTO.intellectPartition.KafkaReturnData;
import com.koron.inwlms.bean.DTO.intellectPartition.PipePreZoneRelationDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.PreZoneRelationDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.ZoneSchemeData;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.intellectPartition.GisZonePipeDateVO;
import com.koron.inwlms.bean.VO.intellectPartition.ModelReturn;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.ZonePipeData;
import com.koron.inwlms.bean.VO.intellectPartition.ZonePipeDataReturn;
import com.koron.inwlms.bean.VO.intellectPartition.ZoneRange;
import com.koron.inwlms.bean.VO.leakageControl.AlertSchemeListVO;
import com.koron.inwlms.bean.VO.leakageControl.PartitionInvestVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.mapper.intellectPartition.PartitionSchemeMapper;
import com.koron.inwlms.mapper.leakageControl.EconomicIndicatorMapper;
import com.koron.inwlms.util.InterfaceUtil;
import com.koron.inwlms.util.TimeUtil;
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
	public List<SchemeDet> querySchemeDet(SessionFactory factory,TotalSchemeDetDTO totalSchemeDetDTO) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		List<SchemeDet> list = mapper.querySchemeDet(totalSchemeDetDTO);
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
	
	@TaskAnnotation("deleteSchemeDetByCode")
	@Override
	public Integer deleteSchemeDetByCode(SessionFactory factory,List<Integer> ids) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		Integer num = mapper.deleteSchemeDetByCode(ids);
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
	public ModelReturn getModelReturnData(SessionFactory factory,AutomaticPartitionDTO automaticPartitionDTO,TotalSchemeDet totalSchemeDet,String tenantID) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		Gson gson = new Gson();
		List<String> railwayList = new ArrayList<>();
		List<String> riverList = new ArrayList<>();
		List<String> xzqList = new ArrayList<>(); 
		List<String> scadaList = new ArrayList<>(); 
		List<String> ambientLayerList = automaticPartitionDTO.getAmbientLayerList();
		List<String> flowLayerList = automaticPartitionDTO.getFlowLayerList();
		
		//调用gis接口，获取所选分区管线数据(不包括图层信息)
		String gisPath = "http://10.13.1.11:8888/"+tenantID+"/getDmaAllPipe.htm";
		GisAllPipeDTO gisAllPipeDTO = new GisAllPipeDTO();
		List<String> cutRegion = new ArrayList<>();
		gisAllPipeDTO.setBaseRegion(automaticPartitionDTO.getZoneCode());
		//查看是否是虚拟分区
		if(automaticPartitionDTO.getCutRegion() != null && automaticPartitionDTO.getCutRegion().size() != 0) {
			cutRegion = automaticPartitionDTO.getCutRegion();
		}
		gisAllPipeDTO.setCutRegion(cutRegion);
		String gisJsonData = gson.toJson(gisAllPipeDTO);
		JsonObject gisResultData = InterfaceUtil.interfaceOfPostUtil(gisPath, gisJsonData);
		String code1 = gisResultData.get("code").getAsString();
		if(!code1.equals("0")) {
			return null;
		}
		JsonArray gisdata = gisResultData.getAsJsonArray("data");
		List<GisZonePipeDateVO>  gisZonePipeDateVO= gson.fromJson(gisdata, new TypeToken<List<GisZonePipeDateVO>>(){}.getType());

		if(ambientLayerList != null && ambientLayerList.size() != 0) {
			for(String flag : ambientLayerList) {
				if(flag.equals("BASE_RAILWAY")) {
					//调用gis接口，查询铁路图层信息
					String gisPath1 = "http://10.13.1.11:8888/"+tenantID+"/railwayIntersect.htm";
					JsonObject gisResultData1 = InterfaceUtil.interfaceOfPostUtil(gisPath1, gisJsonData);
					String code2 = gisResultData1.get("code").getAsString();
					if(!code2.equals("0")) {
						return null;
					}
					JsonArray gisdata1 = gisResultData.getAsJsonArray("data");
					railwayList = gson.fromJson(gisdata1, new TypeToken<List<String>>(){}.getType());
				}else if(flag.equals("BASE_RIVER")) {
					//调用gis接口，查询河流图层信息
					String gisPath2 = "http://10.13.1.11:8888/"+tenantID+"/riverIntersect.htm";
					JsonObject gisResultData2 = InterfaceUtil.interfaceOfPostUtil(gisPath2, gisJsonData);
					String code3 = gisResultData2.get("code").getAsString();
					if(!code3.equals("0")) {
						return null;
					}
					JsonArray gisdata2 = gisResultData.getAsJsonArray("data");
					riverList = gson.fromJson(gisdata2, new TypeToken<List<String>>(){}.getType());
				}else if(flag.equals("BASE_XZQ")) {
					//查询gis接口，查询行政区域图层信息
					String gisPath3 = "http://10.13.1.11:8888/"+tenantID+"/xzqIntersect.htm";
					JsonObject gisResultData3 = InterfaceUtil.interfaceOfPostUtil(gisPath3, gisJsonData);
					String code4 = gisResultData3.get("code").getAsString();
					if(!code4.equals("0")) {
						return null;
					}
					JsonArray gisdata3 = gisResultData.getAsJsonArray("data");
					xzqList = gson.fromJson(gisdata3, new TypeToken<List<String>>(){}.getType());
				}
			}
		}
		
		if(flowLayerList != null && flowLayerList.size() != 0) {
			//查询gis接口，查询计量点相交管线
			String gisPath4 = "http://10.13.1.11:8888/"+tenantID+"/scadaIntersect.htm";
			JsonObject gisResultData4 = InterfaceUtil.interfaceOfPostUtil(gisPath4, gisJsonData);
			String code5 = gisResultData4.get("code").getAsString();
			if(!code5.equals("0")) {
				return null;
			}
			JsonArray gisdata4 = gisResultData.getAsJsonArray("data");
			scadaList = gson.fromJson(gisdata4, new TypeToken<List<String>>(){}.getType());
		}
		
		List<GisZonePipeData> pipeinfo = new ArrayList<>();
		
		for(GisZonePipeDateVO gisZonePipeDataVO : gisZonePipeDateVO) {
			//获取数据
			GisZonePipeData gisZonePipeData = new GisZonePipeData();
			if(gisZonePipeDataVO.getPip_p() == null || gisZonePipeDataVO.getPip_p_pre() == null || gisZonePipeDataVO.getPip_obj_code() == null || gisZonePipeDataVO.getPip_len() == null) {
				System.out.print(1);
			}else if(gisZonePipeDataVO.getPoint_a() == null || gisZonePipeDataVO.getPoint_b() == null || gisZonePipeDataVO.getPip_d() == null) {
				System.out.print(1);
			}
			gisZonePipeData.setPip_p(gisZonePipeDataVO.getPip_p());
			gisZonePipeData.setPip_p_pre(gisZonePipeDataVO.getPip_p_pre());
			gisZonePipeData.setPip_obj_code(gisZonePipeDataVO.getPip_obj_code());
			gisZonePipeData.setPip_len(gisZonePipeDataVO.getPip_len());
			gisZonePipeData.setPoint_a(gisZonePipeDataVO.getPoint_a());
			gisZonePipeData.setPoint_b(gisZonePipeDataVO.getPoint_b());
			gisZonePipeData.setPip_d(gisZonePipeDataVO.getPip_d());
			gisZonePipeData.setPoint_c(gisZonePipeDataVO.getPoint_c());
			gisZonePipeData.setPoint_z(gisZonePipeDataVO.getPoint_z());
			gisZonePipeData.setAdministration(0);
			gisZonePipeData.setRailway(0);
			gisZonePipeData.setPip_river(0);
			gisZonePipeData.setPip_gauge(0);
			gisZonePipeData.setLayerOne(0);
			gisZonePipeData.setLayerTwo(0);
			gisZonePipeData.setLayerThree(0);
			gisZonePipeData.setLayerFour(0);
			
			//测试数据
			gisZonePipeData.setPoint_c(0.0);
			gisZonePipeData.setPoint_z(0.0);
			
			if(gisZonePipeDataVO.getPip_value().equals("W101510001")) {
				gisZonePipeData.setPip_value(1); 
			}else {
				gisZonePipeData.setPip_value(0);
			}
			
			//铁路
			if(railwayList != null && railwayList.size() != 0) {
				for(String code : railwayList) {
					if(gisZonePipeDataVO.getPip_obj_code().equals(code)) {
						gisZonePipeData.setRailway(1);
					}
				}
			}
			
			//河流
			if(riverList != null && riverList.size() != 0) {
				for(String code : riverList) {
					if(gisZonePipeData.getPip_obj_code().equals(code)) {
						gisZonePipeData.setPip_river(1);
					}
				}
			}
			
			//行政
			if(xzqList != null && xzqList.size() != 0) {
				for(String code : xzqList) {
					if(gisZonePipeData.getPip_obj_code().equals(code)) {
						gisZonePipeData.setAdministration(1);
					}
				}
			}
			
			
			//流量计
			if(scadaList != null && scadaList.size() != 0) {
				for(String code : scadaList) {
					if(gisZonePipeData.getPip_obj_code().equals(code)) {
						gisZonePipeData.setPip_gauge(1);
					}
				}
			}
			
			pipeinfo.add(gisZonePipeData);
		}
		
		String code = mapper.addTotalSchemeDet(totalSchemeDet);
		//查询管径数据
		 //TODO 判断分区类型（PMA或者DMA）
		String type = "dma";
		EconomicIndicatorMapper emapper = factory.getMapper(EconomicIndicatorMapper.class);
		List<PartitionInvestVO> partInvestList = emapper.queryPartitionInvestCaliber(type);
		if(partInvestList != null && partInvestList.size() != 0) {
			for(GisZonePipeData gisZonePipeData : pipeinfo) {
				String caliber = gisZonePipeData.getPip_d() +"";
				for(PartitionInvestVO partitionInvestVO : partInvestList) {
					if(partitionInvestVO.getCaliber().equals(caliber)) {
						gisZonePipeData.setPip_price(partitionInvestVO.getMoney());
					}
				}
				//测试
				gisZonePipeData.setPip_price(100.1);
			}
		}else {
			//测试
			for(GisZonePipeData gisZonePipeData : pipeinfo) {
				//测试
				gisZonePipeData.setPip_price(100.1);
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
		gisZoneData.setTotal_plan_code(code);
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
	
	@TaskAnnotation("getZoneNum")
	@Override
	public ZoneRange getZoneNum(SessionFactory factory,AutomaticPartitionDTO automaticPartitionDTO) {
		IndicatorMapper mapper = factory.getMapper(IndicatorMapper.class);
		ZoneRange zoneRange = new ZoneRange();
		Integer max = 0;
		Integer min = 0;
		if(automaticPartitionDTO.getZoneType() == 1) {
			if(automaticPartitionDTO.getZoneGrade() == 1) {
				max = 20;
				min = 2;
			}else if(automaticPartitionDTO.getZoneGrade() == 2) {
				max = 20;
				min = 2;
			}else {
				IndicatorDTO indicatorDTO = new IndicatorDTO();
				List<String> codes = new ArrayList<>();
				List<String> zonecodes = new ArrayList<>();
				String code = "DMYNOCM";
				codes.add(code);
				indicatorDTO.setCodes(codes);
				zonecodes.add(automaticPartitionDTO.getZoneCode());
				indicatorDTO.setZoneCodes(zonecodes);
				indicatorDTO.setTimeType(4);
				Integer year = TimeUtil.getYears(new Date());
				indicatorDTO.setStartTime(year);
				indicatorDTO.setEndTime(year);
				List<IndicatorVO> list = mapper.queryBaseIndicData(indicatorDTO);
				Double num = list.get(0).getValue();
				Integer maxnum = (int)Math.ceil(num);
				maxnum = maxnum/100;
				Integer minnum = (int) Math.floor(num);
				minnum = minnum/1000;
				if(minnum < 2) {
					minnum = 2;
				}
				max = maxnum;
				min = minnum;
			}
		}else {
			if(automaticPartitionDTO.getOthertype().equals("")) {
				max = 20;
				min = 2;
			}else if(automaticPartitionDTO.getOthertype().equals("")) {
				max = 20;
				min = 2;
			}else {
				IndicatorDTO indicatorDTO = new IndicatorDTO();
				List<String> codes = new ArrayList<>();
				List<String> zonecodes = new ArrayList<>();
				String code = "DMYNOCM";
				codes.add(code);
				indicatorDTO.setCodes(codes);
				zonecodes.add(automaticPartitionDTO.getZoneCode());
				indicatorDTO.setZoneCodes(zonecodes);
				indicatorDTO.setTimeType(4);
				Integer year = TimeUtil.getYears(new Date());
				indicatorDTO.setStartTime(year);
				indicatorDTO.setEndTime(year);
				List<IndicatorVO> list = mapper.queryBaseIndicData(indicatorDTO);
				Double num = list.get(0).getValue();
				Integer maxnum = (int)Math.ceil(num);
				maxnum = maxnum/100;
				Integer minnum = (int) Math.floor(num);
				minnum = minnum/1000;
				if(minnum < 2) {
					minnum = 2;
				}
				max = maxnum;
				min = minnum;
			}
		}
		
		
		zoneRange.setMax(max);
		zoneRange.setMin(min);
		
		return zoneRange;
	}
	
	@TaskAnnotation("getSchemePipeData")
	@Override
	public List<ZonePipeDataReturn> getSchemePipeData(SessionFactory factory,Integer schemeId) {
		PartitionSchemeMapper mapper = factory.getMapper(PartitionSchemeMapper.class);
		List<ZonePipeDataReturn> zonePipeDataReturnList = new ArrayList<>();
		List<String> codeList = mapper.queryRCodeBySchemeId(schemeId);
		if(codeList != null && codeList.size() != 0) {
			for(String code : codeList) {
				ZonePipeDataReturn zonePipeDataReturn = new ZonePipeDataReturn();
				List<ZonePipeData> zonePipeDataList = mapper.queryZonePipeData(code);
				zonePipeDataReturn.setrCode(code);
				zonePipeDataReturn.setPipeCodeList(zonePipeDataList);
				zonePipeDataReturnList.add(zonePipeDataReturn);
			}
			return zonePipeDataReturnList;
		}else {
			return null;
		}

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
        SchemeDet.setFlowNum(kafkaReturnData.getFlow_num().intValue());
        SchemeDet.setTightness(kafkaReturnData.getTightness());
        SchemeDet.setEcomony(kafkaReturnData.getEconomy());
        SchemeDet.setRegionNum(kafkaReturnData.getPartition_no().intValue());
		Integer schemeDetId = mapper.addSchemeDet(SchemeDet);
		
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
			preZoneRelationDTO.setZoneSchemeId(schemeDetId);
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
		for(GisZonePipeData gisZonePipeData : pipeinfo) {
			gisZonePipeData.setRailway(0);
			gisZonePipeData.setLayerOne(0);
			gisZonePipeData.setLayerTwo(0);
			gisZonePipeData.setLayerThree(0);
			gisZonePipeData.setLayerFour(0);
			gisZonePipeData.setAdministration(0);
		}
		
		
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
		gisZoneData.setNum_up(67);
		gisZoneData.setNum_down(50);
		gisZoneData.setTotal_plan_code("123");
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
//				JsonObject mldata = rpResultData.get("data").getAsJsonObject();
//				ModelReturn modelreturn = gson.fromJson(mldata, new TypeToken<ModelReturn>(){}.getType());
				return null;
			}
		}
		return null;
	}
	
	public List<GisZonePipeData> readText() {
		File file = new File("D:/智能分区-1000.txt");
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
