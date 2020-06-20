package com.koron.inwlms.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.common.GisMapServiceData;
import com.koron.inwlms.bean.VO.common.DMA;
import com.koron.inwlms.bean.VO.common.GisMapData;
import com.koron.inwlms.bean.VO.common.MapOpTion;
import com.koron.inwlms.bean.VO.common.MapService;
import com.koron.inwlms.bean.VO.common.MapServiceData;
import com.koron.inwlms.bean.VO.common.OtherLayer;
import com.koron.inwlms.bean.VO.common.ProjInfo;
import com.koron.inwlms.bean.VO.common.TreeData;
import com.koron.inwlms.bean.VO.common.TreeDataControl;
import com.koron.inwlms.bean.VO.common.Widgets;
import com.koron.inwlms.bean.VO.common.WidgetsParam;
import com.koron.inwlms.bean.VO.intellectPartition.GisConfigDetVO;
import com.koron.inwlms.mapper.common.GisMapper;
import com.koron.inwlms.service.common.MapServiceConfigService;
import com.koron.inwlms.util.InterfaceUtil;
@Service
public class MapServiceConfigServiceImpl implements MapServiceConfigService{
	
	@TaskAnnotation("queryMapServiceConfig")
	@Override
	public MapServiceData queryMapServiceConfig(SessionFactory factory,String tenantID,String module) {
		String gisPath = "http://10.13.1.11:8888/"+tenantID+"/sysService/getAll.htm";
		String gisJsonData = "{}";
		JsonObject gisResultData = InterfaceUtil.interfaceOfPostUtil(gisPath, gisJsonData);
		JsonArray gisdata = gisResultData.getAsJsonArray("data");
		Gson gson = new Gson();
		List<GisMapServiceData> gisMapDataList = gson.fromJson(gisdata, new TypeToken<List<GisMapServiceData>>(){}.getType());
		 
		MapServiceData mapServiceData = new MapServiceData();
		MapService mapService = new MapService();
		MapOpTion mapOpTion = new MapOpTion();
		List<Widgets> widgetsList = new ArrayList<>();
		
		GisMapper gismapper = factory.getMapper(GisMapper.class);
		HashMap<String,GisMapData> mapServices = new HashMap<String,GisMapData>();
		List<TreeData> treeDataList = new ArrayList<>();
		
		ProjInfo projInfo = new ProjInfo();
		for(GisMapServiceData gisMapServiceData : gisMapDataList) {
			//获取BaseMap url
			if(gisMapServiceData.getId_service().equals("BaseMap")) {
				mapServiceData.setBaseMapUrl(gisMapServiceData.getResturl());
				String center = gisMapServiceData.getCenter();
				List<Double> centerList = gson.fromJson(center, new TypeToken<List<Double>>(){}.getType());
				mapOpTion.setCenter(centerList);
				mapOpTion.setMaxZoom(gisMapServiceData.getMaxzoom());
				mapOpTion.setZoom(gisMapServiceData.getZoom());
				String res = gisMapServiceData.getRes();
				List<Double> resList = gson.fromJson(res, new TypeToken<List<Double>>(){}.getType());
				mapOpTion.setRes(resList);
				String gisValue = gisMapServiceData.getValue();
				String key = gisValue.substring(0, gisValue.indexOf(","));
				projInfo.setKey(key);
				String value = gisValue.substring(gisValue.indexOf(",")+1);
				projInfo.setValue(value);
				String bounds = gisMapServiceData.getBounds();
				List<List<Double>> boundsList = gson.fromJson(bounds, new TypeToken<List<List<Double>>>(){}.getType());
				projInfo.setBounds(boundsList);
				mapOpTion.setProjInfo(projInfo);	
				mapServiceData.setMapOption(mapOpTion);
			}else if(gisMapServiceData.getId_service().equals("BaseMap")) {
				
			}
			
			GisMapData gisMapData = new GisMapData();
			gisMapData.setOpacity(gisMapServiceData.getOpacity());
			gisMapData.setServiceName(gisMapServiceData.getAlias());
			gisMapData.setType("tiledMapLayer");
			gisMapData.setUrl(gisMapServiceData.getResturl());
			mapServices.put(gisMapServiceData.getId_service(), gisMapData);
			
			TreeData treeData = new TreeData();
			List<TreeDataControl> controlList = new ArrayList<>();
			String id = "tree_" + gisMapServiceData.getId_service();
			treeData.setId(id);
			treeData.setTitle(gisMapServiceData.getAlias());
			TreeDataControl treeDataControl = new TreeDataControl();
			treeDataControl.setLayerID("0");
			treeDataControl.setServerID(gisMapServiceData.getId_service());
			treeDataControl.setType("tiledMap");
			controlList.add(treeDataControl);
			treeData.setControl(controlList);
			treeDataList.add(treeData);
			
			
		}
		
		OtherLayer otherLayer = new OtherLayer();
		DMA dMA = new DMA();
		dMA.setServerID("DMA");
		dMA.setServiceName("DMA地图");
		dMA.setType("localLayer");
		otherLayer.setDMA(dMA);
		
		mapService.setOtherLayer(otherLayer);
		mapService.setMapServices(mapServices);
		mapServiceData.setServices(mapService);
		
		if(module != null && !module.equals("")) {
			List<GisConfigDetVO> gisConfigList = gismapper.queryGisMapConfig(module);
			for(GisConfigDetVO gisConfigDetVO : gisConfigList) {
				Widgets widgets = new Widgets();
				String param = gisConfigDetVO.getParam();
				WidgetsParam widgetsParam = gson.fromJson(param, new TypeToken<WidgetsParam>(){}.getType());
				String name  = gisConfigDetVO.getComNo();
				if(name.equals("LayerControl")) {
					widgetsParam.setTreeData(treeDataList);
				}
				widgets.setParam(widgetsParam);
				widgets.setName(name);
				widgetsList.add(widgets);
			}
		}else {
			Widgets widgets1 = new Widgets();
			widgets1.setName("LayerControl");
			WidgetsParam widgetsParam1 = new WidgetsParam();
			widgetsParam1.setShow("hello this is legend");
			widgetsParam1.setTreeData(treeDataList);
			widgets1.setParam(widgetsParam1);
			widgetsList.add(widgets1);
			
			Widgets widgets2 = new Widgets();
			widgets2.setName("ToolBox");
			WidgetsParam widgetsParam2 = new WidgetsParam();
			widgetsParam2.setShow("toolBox this is");
			widgets2.setParam(widgetsParam2);
			widgetsList.add(widgets2);
			
			Widgets widgets3 = new Widgets();
			widgets3.setName("Operation");
			WidgetsParam widgetsParam3 = new WidgetsParam();
			widgetsParam3.setShow("Operation this is");
			widgets3.setParam(widgetsParam3);
			widgetsList.add(widgets3);
			
			Widgets widgets4 = new Widgets();
			widgets4.setName("Measure");
			WidgetsParam widgetsParam4 = new WidgetsParam();
			widgetsParam4.setShow("hello this is Measure");
			widgets4.setParam(widgetsParam4);
			widgetsList.add(widgets4);
			
			Widgets widgets5 = new Widgets();
			widgets5.setName("MapClear");
			WidgetsParam widgetsParam5 = new WidgetsParam();
			widgetsParam5.setShow("hello this is MapClear");
			widgets5.setParam(widgetsParam5);
			widgetsList.add(widgets5);
			
			Widgets widgets6 = new Widgets();
			widgets6.setName("HomeButton");
			WidgetsParam widgetsParam6 = new WidgetsParam();
			widgetsParam6.setShow("hello this is HomeButton");
			widgets6.setParam(widgetsParam6);
			widgetsList.add(widgets6);
			
			Widgets widgets7 = new Widgets();
			widgets7.setName("Legend");
			WidgetsParam widgetsParam7 = new WidgetsParam();
			widgetsParam7.setShow("hello this is Legend");
			widgets7.setParam(widgetsParam7);
			widgetsList.add(widgets7);
			
			Widgets widgets8 = new Widgets();
			widgets8.setName("Draw");
			WidgetsParam widgetsParam8 = new WidgetsParam();
			widgetsParam8.setShow("hello this is Draw");
			widgets8.setParam(widgetsParam8);
			widgetsList.add(widgets8);
			
			Widgets widgets9 = new Widgets();
			widgets9.setName("SearchLayer");
			WidgetsParam widgetsParam9 = new WidgetsParam();
			widgetsParam9.setShow("hello this is SearchLayer");
			widgets9.setParam(widgetsParam9);
			widgetsList.add(widgets9);
		}
		
		mapServiceData.setWidgets(widgetsList);
		return mapServiceData;
	}
	

}
