package com.koron.inwlms.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koron.common.StaffAttribute;
import com.koron.inwlms.bean.DTO.baseInf.*;
import com.koron.inwlms.bean.VO.baseInf.*;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.baseData.*;
import com.koron.inwlms.util.ExportDataUtil;
import com.koron.inwlms.util.FileUtil;
import com.koron.inwlms.util.ImportExcelUtil;
import com.koron.inwlms.util.InterfaceUtil;
import com.koron.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.swan.bean.MessageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auother:zhong
 * @date:
 * @description:
 */
@Controller
@Api(value = "baseData", description = "基础数据Controller")
@RequestMapping(value = "/{tenantID}/baseData")
public class BaseDataController {

    @Autowired
    private DataQualityService dqs;

    //@Autowired
    //private FacilityService fs;

    //@Autowired
    //private PipeNetworkService pns;

    @Autowired
    private PipeService pipesvr;

    @Autowired
    private PointService pointS;

    @Autowired
    private MeterDataService mds;

    @Autowired
    private ZoneConfigService zcs;

    @Autowired
    private MonitorService ms;

    @Value("${server.gis.address}")
    private String gis;

    @RequestMapping(value = "/queryPGData.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询数据接口", notes = "查询数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPGData(@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
      MessageBean msg = new MessageBean();
      List<DataVO> pipes = ADOConnection.runTask(user.getEnv(),pipesvr, "queryALList", List.class);
      msg.setCode(0);
      msg.setData(pipes);
      return msg.toJson();
    }


    @RequestMapping(value = "/queryPipeList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询管线信息接口", notes = "查询管线信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPipeList(@RequestBody PipeDTO pipeDTO) {

        //TODO:参数pipeDTO校验

        //JSONObject json = JSONObject.fromObject(pipeDTO);
        String path = "";
        //TODO:调用GIS接口获取管线数据
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }


    @RequestMapping(value = "/queryPipeDet/{pipeID}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询管线详情接口", notes = "查询管线详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPipeDet(@PathVariable("pipeID") String pipeID) {

        //TODO:参数pipeID校验

        //TODO:调用GIS接口获取管线数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }

    @RequestMapping("/importPipe.htm")
    @ApiOperation(value = "导入管线接口", notes = "导入管线接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importPipe(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent") MultipartFile file) {
        //TODO:操作权限校验

        //TODO:Excel数据读取校验完整性，一致性，准确性

        //TODO:调用GIS接口导出管线数据
        String path= "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }

    @RequestMapping("/downloadPipeTemplate.htm")
    @ApiOperation(value = "导出管线Excel模板", notes = "导出管线Excel模板", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void downloadPipeTemplate(HttpServletResponse response, HttpServletRequest request) {

        //TODO:权限校验-校验是否有添加权限（有添加权限即有导入权限）

        //TODO:
        FileUtil.downloadFile(Constant.PIPE_IMPORT_TEMPLATE,Constant.LINUX_TEMPALTE_PATH + Constant.PIPE_IMPORT_TEMPLATE, response, request);
        return;
//        try {
//            return ExportExcel.exportFile(Constant.PIPE_IMPORT_TEMPLATE, Constant.LINUX_TEMPALTE_PATH + Constant.PIPE_IMPORT_TEMPLATE, Constant.PIPE_IMPORT_TEMPLATE_BTL, (Map) new HashMap());
//        }catch(Exception e){
//            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
//        }
    }

    @RequestMapping("/exportPipe.htm")
    @ApiOperation(value = "导出管线接口", notes = "导出管线接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> exportPipe(@RequestBody PipeDTO pipeDTO,@RequestParam String titleInfos) {

        //TODO:权限校验

        if (pipeDTO == null) {
            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
        }
        Gson jsonValue = new Gson();
        //TODO:调用GIS接口导入管线数据
        String path= "";
        String JsonData = "";
        JsonObject pipeObj = InterfaceUtil.interfaceOfPostUtil(path,JsonData);
        Gson gson = new Gson();
        List<PipeVO> pipes = gson.fromJson(pipeObj, new TypeToken<List<PipeVO>>(){}.getType());
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(pipes, jsonArray);
    }

    @RequestMapping("/deletePipes/{BatchNo}")
    @ApiOperation(value = "删除某一批次管线数据", notes = "删除某一批次管线数据", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deletePipes(@PathVariable("BatchNo") String BatchNo) {

        //TODO:调用GIS接口删除某一批次数据
        String path = "";
        String JsonData = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }

    @RequestMapping(value = "/queryPointList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测点信息接口", notes = "查询监测点信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPointList(@RequestBody PointDTO pointDTO) {


        //TODO:调用GIS接口获取管线数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);
        return msg.toString();
    }

    @RequestMapping(value = "/queryPointDet/{P_CODE}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测点详情接口", notes = "查询监测点详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPointDet(@PathVariable("P_CODE") String P_CODE) {

        //TODO:参数P_CODE校验

        //TODO:调用GIS接口获取管线数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }

    @RequestMapping("/importPoint.htm")
    @ApiOperation(value = "导入监测点接口", notes = "导入监测点接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importPoint(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent ") MultipartFile file) {

        //TODO:Excel数据读取校验完整性，一致性，准确性


        //TODO:调用GIS接口导入管线数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }

    @RequestMapping("/downloadPointTemplate.htm")
    @ApiOperation(value = "导出监测点Excel模板", notes = "导出监测点Excel模板", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void downloadPointTemplate(HttpServletResponse response, HttpServletRequest request) {

        //TODO:权限校验-校验是否有添加权限（有添加权限即有导入权限）

        //TODO:
        FileUtil.downloadFile(Constant.POINT_IMPORT_TEMPLATE,Constant.LINUX_TEMPALTE_PATH + Constant.POINT_IMPORT_TEMPLATE, response, request);
        return;
//        try {
//            return ExportExcel.exportFile(Constant.POINT_IMPORT_TEMPLATE, Constant.LINUX_TEMPALTE_PATH + Constant.POINT_IMPORT_TEMPLATE, Constant.POINT_IMPORT_TEMPLATE_BTL, (Map) new HashMap());
//        }catch(Exception e){
//            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
//        }
    }

    @RequestMapping("/exportPoint.htm")
    @ApiOperation(value = "导出监测点接口", notes = "导出监测点接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> exportPipe(@RequestBody PointDTO pointDTO,@RequestParam String titleInfos) {

        //TODO:权限校验

        if (pointDTO == null) {
            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
        }
        Gson jsonValue = new Gson();
        //TODO:调用GIS接口导入监测点数据
        String path= "";
        String JsonData = "";
        JsonObject pointObj = InterfaceUtil.interfaceOfPostUtil(path,JsonData);
        Gson gson = new Gson();
        List<PointVO> points = gson.fromJson(pointObj, new TypeToken<List<PipeVO>>(){}.getType());
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(points, jsonArray);
    }

    @RequestMapping("/deletePoints/{BatchNo}")
    @ApiOperation(value = "删除某一批次监测点数据", notes = "删除某一批次监测点数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deletePoints(@PathVariable("BatchNo") String BatchNo) {

        //TODO:调用GIS接口删除某一批次数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }

    @RequestMapping(value = "/queryFacilityList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询附属设施信息接口", notes = "查询附属设施信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPointList(@RequestBody FacilityDTO facilityDTO) {

        //TODO:调用GIS接口获取附属设施数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }

    @RequestMapping(value = "/queryFacilityDet/{P_CODE}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询附属设施详情接口", notes = "查询附属设施详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryFacilityDet(@PathVariable("P_CODE") String P_CODE) {

        //TODO:参数P_CODE校验

        //TODO:调用GIS接口获取附属设施数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }


    @RequestMapping("/deleteFacility/{BatchNo}")
    @ApiOperation(value = "删除某一批次附属设施数据", notes = "删除某一批次附属设施数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteFacility(@PathVariable("BatchNo") String BatchNo) {

        //TODO:调用GIS接口删除某一批次数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toString();
    }

    @RequestMapping("/downloadFacilityTemplate.htm")
    @ApiOperation(value = "导出附属设施Excel模板", notes = "导出附属设施Excel模板", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void downloadFacilityTemplate(HttpServletResponse response, HttpServletRequest request) {

        //TODO:权限校验-校验是否有添加权限（有添加权限即有导入权限）

        //TODO:
        FileUtil.downloadFile(Constant.FACILITY_IMPORT_TEMPLATE,Constant.LINUX_TEMPALTE_PATH + Constant.FACILITY_IMPORT_TEMPLATE, response, request);
        return;
//        try {
//            return ExportExcel.exportFile(Constant.FACILITY_IMPORT_TEMPLATE, Constant.LINUX_TEMPALTE_PATH + Constant.FACILITY_IMPORT_TEMPLATE, Constant.FACILITY_IMPORT_TEMPLATE_BTL, (Map) new HashMap());
//        }catch(Exception e){
//            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
//        }
    }

    @RequestMapping("/importFacility.htm")
    @ApiOperation(value = "导入附属设施接口", notes = "导入附属设施接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importFacility(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent ") MultipartFile file) {

        //TODO:Excel数据读取校验完整性，一致性，准确性


        //TODO:调用GIS接口导入管线数据
        String path = "";
        String jsonData = "";
        JsonObject msg = InterfaceUtil.interfaceOfPostUtil(path,jsonData);

        return msg.toString();
    }


    @RequestMapping(value = "/queryTabList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询水表列表接口", notes = "查询水表列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryTabList(@RequestBody WatermeterDTO watermeterDTO) {
        //TODO:权限校验是否有查询权限

        //TODO:调用GIS接口获取附属设施数据
        String path = "";
        String jsonData = "";
        JsonObject msg = InterfaceUtil.interfaceOfPostUtil(path,jsonData);
        //TODO:返回数据二次加工

        return msg.toString();
    }

    @RequestMapping(value = "/queryTabDet/{P_CODE}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询水表详情接口", notes = "查询水表详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryTabDet(@PathVariable("P_CODE") String P_CODE) {

        //TODO:参数P_CODE校验

        //TODO:调用GIS接口获取附属设施数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);
        //TODO:返回数据加工

        return msg.toString();
    }


    @RequestMapping("/deleteTab/{BatchNo}")
    @ApiOperation(value = "删除某一批次户表数据", notes = "删除某一批次户表数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteTab(@PathVariable("BatchNo") String BatchNo) {
        //TODO:校验是否有删除权限

        //TODO:调用GIS接口删除某一批次数据
        String path = "";
        JsonObject msg = InterfaceUtil.interfaceUtil(path);

        //TODO:返回数据

        return msg.toString();
    }

    @RequestMapping("/downloadTableTemplate.htm")
    @ApiOperation(value = "导出水表Excel模板", notes = "导出水表Excel模板", httpMethod = "POST", response = MessageBean.class)
    @ResponseBody
    public void downloadTableTemplate(HttpServletResponse response, HttpServletRequest request) {

        //TODO:权限校验-校验是否有添加权限（有添加权限即有导入权限）

        //TODO:
        FileUtil.downloadFile(Constant.WATER_IMPORT_TEMPLATE,Constant.LINUX_TEMPALTE_PATH + Constant.WATER_IMPORT_TEMPLATE, response, request);
        return;
//        try {
//            return ExportExcel.exportFile(Constant.WATER_IMPORT_TEMPLATE, Constant.LINUX_TEMPALTE_PATH + Constant.WATER_IMPORT_TEMPLATE, Constant.WATER_IMPORT_TEMPLATE_BTL, (Map) new HashMap());
//        }catch(Exception e){
//            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
//        }
    }

    @RequestMapping("/importTable.htm")
    @ApiOperation(value = "导入水表数据接口", notes = "导入水表数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importTable(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent ") MultipartFile file) {
        //TODO:校验是否有数据添加权限

        //TODO:Excel数据读取校验完整性，一致性，准确性


        //TODO:调用GIS接口导入管线数据
        String path = "";
        String jsonData = "";
        JsonObject msg = InterfaceUtil.interfaceOfPostUtil(path,jsonData);

        return msg.toString();
    }

    @RequestMapping(value = "/downloadZoneList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "导出分区接口", notes = "导出分区接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downloadZoneList(@RequestBody ZoneDTO zoneDTO,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        //TODO:校验参数有效性
        if(zoneDTO.getBegD()!=null && !"".equals(zoneDTO.getBegD()) && !_checkFormat("YYYY-MM-DD",zoneDTO.getBegD())){
            msg.setCode(Constant.BASE_PARAM_DATE_FORMAT_ERROR);
            msg.setDescription("日期格式不正确");
            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
        }

        //*****查询符合条件数据
        PageListVO<List<ZoneVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneList", PageListVO.class,zoneDTO);
        List<ZoneVO> list= zps.getDataList();
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
    }

    @RequestMapping(value = "/queryZoneList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区列表接口", notes = "查询分区列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneList(@RequestBody ZoneDTO zoneDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性
        if(zoneDTO.getBegD()!=null && !"".equals(zoneDTO.getBegD()) && !_checkFormat("YYYY-MM-DD",zoneDTO.getBegD())){
            msg.setCode(Constant.BASE_PARAM_DATE_FORMAT_ERROR);
            msg.setDescription("日期格式不正确");
            return msg.toJson();
        }

        //*****查询符合条件数据
        PageListVO<List<ZoneVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneList", PageListVO.class,zoneDTO);

        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryChargeZones.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询管辖分区接口", notes = "查询管辖分区接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryChargeZones(@RequestBody ZoneDTO zoneDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性
        if(zoneDTO.getUser()!=null && !"".equals(zoneDTO.getUser())){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toJson();
        }

        //*****查询符合条件数据
        PageListVO<List<ZoneUserVO>> zus = ADOConnection.runTask(user.getEnv(),zcs, "queryChargeZones", PageListVO.class,zoneDTO);

        msg.setCode(0);
        msg.setData(zus);
        return msg.toJson();
    }

    @RequestMapping(value = "/updateChargeZones.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "更新管辖分区接口", notes = "更新管辖分区接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateChargeZones(@RequestBody ZoneDTO zoneDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性
        if(zoneDTO.getUser()!=null && !"".equals(zoneDTO.getUser())){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toJson();
        }

        //*****插入符合条件数据
        Integer r1 = ADOConnection.runTask(user.getEnv(),zcs, "deleteChargeZones", Integer.class,zoneDTO);
        if(r1>=0) {
            if(zoneDTO.getZoons().length>0) {
                Integer r2 = ADOConnection.runTask(user.getEnv(), zcs, "addChargeZones", Integer.class, zoneDTO);
                if (r2 > 0) {
                    msg.setCode(0);
                    msg.setDescription("操作成功");
                }
            }else{
                msg.setCode(0);
                msg.setDescription("操作成功");
            }
        }else{
            msg.setCode(Constant.MESSAGE_INT_ERROR);
            msg.setDescription("操作失败");
        }
        return msg.toJson();
    }

    @RequestMapping(value = "/downloadZonePointList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "导出分区与监测点接口", notes = "导出分区与监测点接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downloadZonePointList(@RequestBody ZonePointDTO zonePointDTO,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        //TODO:校验参数有效性

        //*****查询符合条件数据
        PageListVO<List<ZonePointVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZonePointList", PageListVO.class,zonePointDTO);
        List<ZonePointVO> list= zps.getDataList();
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
    }

    @RequestMapping(value = "/queryZonePointList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与监测点列表接口", notes = "查询分区与监测点列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZonePointList(@RequestBody ZonePointDTO zonePointDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性

        //*****查询符合条件数据
        PageListVO<List<ZonePointVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZonePointList", PageListVO.class,zonePointDTO);
        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryZonePointHistory.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与监测点数据导入历史接口", notes = "查询分区与监测点数据导入历史接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZonePointHistory(@RequestBody ZonePointDTO zonePointDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性

        //TODO:权限校验是否有查询权限

        //*****查询符合条件数据
        PageListVO<List<ZonePointHisVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZonePointHistory", PageListVO.class,zonePointDTO);
        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryZonePointDet/{refID}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区监测点详情接口", notes = "查询分区监测点详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZonePointDet(@PathVariable("refID") Integer refID,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();

        //TODO:参数refID校验
        if(refID== null){
            msg.setCode(Constant.BASE_PARAM_INT_NULL_ERROR);
            msg.setDescription("参数refID不能为空");
            return msg.toJson();
        }

        //TODO:获取分区监测点详情数据
        //*****查询符合条件数据
        ZonePointVO zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZonePointDet", ZonePointVO.class,refID);
        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }


    @RequestMapping(value = "/updateZonePointDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改分区监测点详情接口", notes = "修改分区监测点详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateZonePointDet(@RequestBody ZonePointDTO zonePointDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();

        //TODO:参数zonePointDTO校验
        if(zonePointDTO.getRefID()==null){
            msg.setCode(Constant.BASE_PARAM_INT_NULL_ERROR);
            msg.setDescription("参数refID不能为空");
            return msg.toString();
        }

        //TODO:获取分区监测点详情数据
        //*****查询符合条件数据
        Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "updateZonePointDet", Integer.class,zonePointDTO);
        if(ret>=0){
            msg.setCode(Constant.MESSAGE_INT_SUCCESS);
            msg.setDescription("操作成功");
        }else{
            msg.setCode(Constant.MESSAGE_INT_ERROR);
            msg.setDescription("操作失败");
        }

        return msg.toJson();
    }

    @RequestMapping("/deleteZonePointByBatch/{BatchNo}")
    @ApiOperation(value = "删除某一批次分区与监测点数据", notes = "删除某一批次分区与监测点数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteZonePointByBatch(@PathVariable("BatchNo") String BatchNo,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验是否有删除权限

        //TODO:
        if(BatchNo==null || "".equals(BatchNo)){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toString();
        }

        //***删除某一批次数据
        Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "deleteZonePointByBatch", Integer.class,BatchNo);
        msg.setCode(0);
        msg.setDescription(Constant.MESSAGE_STRING_SUCCESS);
        return msg.toString();
    }

    @RequestMapping("/downloadZonePointTemplate.htm")
    @ApiOperation(value = "导出分区与监测点Excel模板", notes = "导出分区与监测点Excel模板", httpMethod = "GET", response = MessageBean.class)
    @ResponseBody
    public void downloadZonePointTemplate(HttpServletResponse response, HttpServletRequest request) {

        //TODO:权限校验-校验是否有添加权限（有添加权限即有导入权限）

        //TODO:
        //return ExportExcel.exportFile(Constant.POINT_IMPORT_TEMPLATE, Constant.LINUX_TEMPALTE_PATH + Constant.POINT_IMPORT_TEMPLATE, Constant.LINUX_TEMPALTE_PATH+Constant.ZONEPOINT_IMPORT_TEMPLATE_BTL, (Map) new HashMap());
        FileUtil.downloadFile(Constant.ZONEPOINT_IMPORT_TEMPLATE,Constant.LINUX_TEMPALTE_PATH + Constant.ZONEPOINT_IMPORT_TEMPLATE, response, request);
        return;

    }

    @RequestMapping("/importZonePoint.htm")
    @ApiOperation(value = "导入分区与监测点数据接口", notes = "导入分区与监测点数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importZonePoint(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent") MultipartFile file,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();

        //TODO:校验是否有数据添加权限

        //参数校验
        if(BatchNo==null || "".equals(BatchNo)){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription("批号不能为空");
            return msg.toString();
        }

        //TODO:Excel数据读取校验完整性，一致性，准确性
        List<ZonePointExcelBean> excelBeans = ImportExcelUtil.readExcel(file, ZonePointExcelBean.class);
        for(ZonePointExcelBean bean : excelBeans){
            bean.setBatchNo(BatchNo);
        }
        if (excelBeans == null || excelBeans.size()==0) {
            msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
            msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
        } else {
            try {
                Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "addBatchZonePoint", Integer.class, excelBeans);
            } catch (Exception e) {
                msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
            }
        }
        return msg.toJson();
    }


    @RequestMapping(value = "/queryZoneMeterHistory.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与户表数据导入历史接口", notes = "查询分区与户表数据导入历史接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMeterHistory(@RequestBody ZoneMeterDTO zoneMeterDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性

        //TODO:权限校验是否有查询权限

        //*****查询符合条件数据
        PageListVO<List<ZoneMeterHisVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneMeterHistory", PageListVO.class,zoneMeterDTO);
        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }

    @RequestMapping(value = "/downloadZoneMeterList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "导出分区与户表接口", notes = "导出分区与户表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downloadZoneMeterList(@RequestBody ZoneMeterDTO zoneMeterDTO,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        //TODO:校验参数有效性

        //*****查询符合条件数据
        PageListVO<List<ZoneMeterVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneMeterList", PageListVO.class,zoneMeterDTO);
        List<ZoneMeterVO> list= zps.getDataList();
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
    }

    @RequestMapping(value = "/queryZoneMeterList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与户表列表接口", notes = "查询分区与户表列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMeterList(@RequestBody ZoneMeterDTO zoneMeterDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数

        //*****查询符合条件数据
        //List<ZonePointVO> zps = ADOConnection.runTask(zcs, "queryZoneMeterList", List.class,zoneMeterDTO);
        PageListVO<List<ZoneMeterVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneMeterList", PageListVO.class,zoneMeterDTO);
        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryZoneMeterDet/{refID}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与户表详情接口", notes = "查询分区与户表详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMeterDet(@PathVariable("refID") Integer refID,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        //TODO:r_code校验
        if(refID==null){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toString();
        }
        //*****查询符合条件数据
        ZoneMeterVO zp = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneMeterDet", ZoneMeterVO.class,refID);
        //TODO:获取分区户表详情数据
        msg.setData(zp);
        return msg.toJson();
    }

    @RequestMapping(value = "/updateZoneMeterDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改分区户表详情接口", notes = "修改分区户表详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateZoneMeterDet(@RequestBody ZoneMeterDTO zoneMeterDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();

        //TODO:参数zoneMeterDTO校验
        if(zoneMeterDTO.getRefID()==null ){
            msg.setCode(Constant.BASE_PARAM_INT_NULL_ERROR);
            msg.setDescription("关系编号不能为空");
            return msg.toString();
        }

        //TODO:获取分区监测点详情数据
        //*****查询符合条件数据
        Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "updateZoneMeterDet", Integer.class,zoneMeterDTO);
        if(ret>=0){
            msg.setCode(Constant.MESSAGE_INT_SUCCESS);
            msg.setDescription("操作成功");
        }else{
            msg.setCode(Constant.MESSAGE_INT_ERROR);
            msg.setDescription("操作失败");
        }

        return msg.toJson();
    }

    @RequestMapping("/deleteZoneMeterRel/{refID}")
    @ApiOperation(value = "删除分区与户表数据", notes = "删除分区与户表数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteZoneMeterRel(@PathVariable("refID") String refID,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验是否有删除权限


        //TODO:返回数据
        Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "deleteZoneMeterRel", Integer.class,refID);
        if(ret>=0){
            msg.setCode(Constant.MESSAGE_INT_SUCCESS);
            msg.setDescription("操作成功");
        }else{
            msg.setCode(Constant.MESSAGE_INT_ERROR);
            msg.setDescription("操作失败");
        }
        return msg.toJson();
    }

    @RequestMapping("/deleteZoneMeterByBatchNo/{BatchNo}")
    @ApiOperation(value = "删除某一批次分区与户表数据", notes = "删除某一批次分区与户表数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteZoneMeterByBatchNo(@PathVariable("BatchNo") String BatchNo,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验是否有删除权限

        //TODO:参数校验
        if(BatchNo == null || "".equals(BatchNo)){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription("批次为空");
            return msg.toJson();
        }

        Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "deleteZoneMeterByBatchNo", Integer.class,BatchNo);
        if(ret>=0){
            msg.setCode(Constant.MESSAGE_INT_SUCCESS);
            msg.setDescription("操作成功");
        }else{
            msg.setCode(Constant.MESSAGE_INT_ERROR);
            msg.setDescription("操作失败");
        }
        return msg.toJson();
    }

    @RequestMapping("/downloadZoneMeterTemplate.htm")
    @ApiOperation(value = "导出分区与户表Excel模板", notes = "导出分区与户表Excel模板", httpMethod = "GET", response = MessageBean.class)
    @ResponseBody
    public void downloadZoneMeterTemplate(HttpServletResponse response, HttpServletRequest request) {

        //TODO:权限校验-校验是否有添加权限（有添加权限即有导入权限）

         FileUtil.downloadFile(Constant.ZONEMETER_IMPORT_TEMPLATE,Constant.LINUX_TEMPALTE_PATH + Constant.ZONEMETER_IMPORT_TEMPLATE, response, request);
         return;
         //
//        try {
//            return ExportExcel.exportFile(Constant.WATER_IMPORT_TEMPLATE, Constant.LINUX_TEMPALTE_PATH + Constant.WATER_IMPORT_TEMPLATE, Constant.WATER_IMPORT_TEMPLATE_BTL, (Map) new HashMap());
//        }catch(Exception e){
//            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
//        }
    }

    @RequestMapping("/importZoneMeter.htm")
    @ApiOperation(value = "导入分区与户表数据接口", notes = "导入分区与户表数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importZoneMeter(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent ") MultipartFile file,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();

        //TODO:校验是否有数据添加权限

        //参数校验
        if(BatchNo==null || "".equals(BatchNo)){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription("批号不能为空");
            return msg.toString();
        }

        //TODO:Excel数据读取校验完整性，一致性，准确性
        List<ZoneMeterExcelBean> excelBeans = ImportExcelUtil.readExcel(file, ZoneMeterExcelBean.class);
        for(ZoneMeterExcelBean bean : excelBeans){
            bean.setBatchNo(BatchNo);
        }
        if (excelBeans == null || excelBeans.size()==0) {
            msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
            msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
        } else {
            try {
                Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "addBatchZoneMeter", Integer.class, excelBeans);
            } catch (Exception e) {
                msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
            }
        }
        return msg.toJson();
    }

    @RequestMapping(value = "/downloadMonitorDataList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "导出监测数据接口", notes = "导出监测数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downloadMonitorDataList(@RequestBody MonitorDataDTO monitorDataDTO,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        //TODO:校验参数有效性

        //*****查询符合条件数据
        PageListVO<List<MonitorDataVO>> mds = null;
        //List<MonitorDataVO> mds = null;
        if("1".equals(monitorDataDTO.getType())) {
            //*****获取压力/流量数据
            mds = ADOConnection.runTask(user.getEnv(),ms, "queryPressureFlowList", PageListVO.class, monitorDataDTO);
        }else{
            //*****获取噪声数据
            mds = ADOConnection.runTask(user.getEnv(),ms, "queryNoiseList", PageListVO.class, monitorDataDTO);
        }
        List<MonitorDataVO> list= mds.getDataList();
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
    }

    @RequestMapping(value = "/queryMonitorDataList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测数据列表接口", notes = "查询监测数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMonitorDataList(@RequestBody MonitorDataDTO monitorDataDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数

        PageListVO<List<MonitorDataVO>> mds = null;
        //List<MonitorDataVO> mds = null;
        if("1".equals(monitorDataDTO.getType())) {
            //*****获取压力/流量数据
             mds = ADOConnection.runTask(user.getEnv(),ms, "queryPressureFlowList", PageListVO.class, monitorDataDTO);
        }else{
            //*****获取噪声数据
             mds = ADOConnection.runTask(user.getEnv(),ms, "queryNoiseList", PageListVO.class, monitorDataDTO);
        }

        msg.setCode(0);
        msg.setData(mds);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryMonitorDataByBatchNo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据批次查询监测数据列表接口", notes = "根据批次查询监测数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMonitorDataByBatchNo(@RequestBody MonitorDataDTO monitorDataDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        if(monitorDataDTO.getBatchNo()==null || "".equals(monitorDataDTO.getBatchNo()) ){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toString();
        }

        PageListVO<List<MonitorDataVO>>  mds = ADOConnection.runTask(user.getEnv(),ms, "queryMonitorDataByBatchNo", PageListVO.class, monitorDataDTO);

        msg.setCode(0);
        msg.setData(mds);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryPressureFlowDet/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询压力/流量详情接口", notes = "查询压力/流量详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPressureFlowDet(@PathVariable("id") Integer id,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        //TODO:r_code校验
        if(id==null ){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toString();
        }
        //*****查询符合条件数据
        MonitorDataVO md = ADOConnection.runTask(user.getEnv(),ms, "queryPressureFlowDet", MonitorDataVO.class,id);
        //TODO:获取分区户表详情数据
        msg.setData(md);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryNoiseDet/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询噪声详情接口", notes = "查询噪声详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryNoiseDet(@PathVariable("id") Integer id,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();

        if(id==null ){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toString();
        }
        //*****查询符合条件数据
        MonitorDataVO md = ADOConnection.runTask(user.getEnv(),ms, "queryNoiseDet", MonitorDataVO.class,id);
        msg.setData(md);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryMonitorDataHistoryList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测导入历史数据列表", notes = "查询监测导入历史数据列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMonitorDataHistoryList(@RequestBody MonitorDataDTO monitorDataDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数
        if(monitorDataDTO.getBegD()!=null && ! "".equals(monitorDataDTO.getBegD()) && !_checkFormat("YYYY-MM-DD",monitorDataDTO.getBegD())){
            msg.setCode(Constant.BASE_PARAM_DATE_FORMAT_ERROR);
            msg.setDescription("日期格式错误");
            return msg.toString();
        }
        if(monitorDataDTO.getEndD()!=null && ! "".equals(monitorDataDTO.getEndD()) && !_checkFormat("YYYY-MM-DD",monitorDataDTO.getEndD())){
            msg.setCode(Constant.BASE_PARAM_DATE_FORMAT_ERROR);
            msg.setDescription("日期格式错误");
            return msg.toString();
        }

        PageListVO<List<MonitorDataHisVO>>  mds= ADOConnection.runTask(user.getEnv(),ms, "queryMonitorDataHistoryList", PageListVO.class,monitorDataDTO);
        msg.setCode(0);
        msg.setData(mds);
        return msg.toJson();
    }

    @RequestMapping(value = "/updateMonitorDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改监测详情接口", notes = "修改监测详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateMonitorDet(@RequestBody MonitorDataDTO monitorDataDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();

        //TODO:校验是否有修改权限

        //TODO:参数monitorDataDTO校验

        //TODO:获取分区监测点详情数据
        //*****查询符合条件数据
        boolean ret = ADOConnection.runTask(user.getEnv(),ms, "updateMonitorDet", boolean.class,monitorDataDTO);
        msg.setCode(0);
        msg.setData(ret);
        return msg.toString();
    }

    @RequestMapping("/downloadMonitorDataTemplate.htm")
    @ApiOperation(value = "导出监测数据Excel模板", notes = "导出监测数据Excel模板", httpMethod = "POST", response = MessageBean.class)
    @ResponseBody
    public void downloadMonitorDataTemplate(HttpServletResponse response, HttpServletRequest request) {

        //TODO:权限校验-校验是否有添加权限（有添加权限即有导入权限）

        //TODO:
        FileUtil.downloadFile(Constant.MONITORDATA_IMPORT_TEMPLATE,Constant.LINUX_TEMPALTE_PATH + Constant.MONITORDATA_IMPORT_TEMPLATE, response, request);
        return;
        //
//        try {
//            return ExportExcel.exportFile(Constant.MONITORDATA_IMPORT_TEMPLATE, Constant.LINUX_TEMPALTE_PATH + Constant.MONITORDATA_IMPORT_TEMPLATE, Constant.MONITORDATA_IMPORT_TEMPLATE_BTL, (Map) new HashMap());
//        }catch(Exception e){
//            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
//        }
    }

    @RequestMapping("/importMonitorData.htm")
    @ApiOperation(value = "导入监测数据接口", notes = "导入监测数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importMonitorData(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent") MultipartFile file,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean<?> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);

        //TODO:校验是否有数据添加权限

        //TODO:Excel数据读取校验完整性，一致性，准确性

        List<MonitorDataExcelBean> excelBeans = ImportExcelUtil.readExcel(file, MonitorDataExcelBean.class);
        for(MonitorDataExcelBean bean : excelBeans){
            bean.setBatchNo(BatchNo);
        }
        if (excelBeans == null || excelBeans.size()==0) {
            msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
            msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
        } else {
            try {
                Integer ret = ADOConnection.runTask(user.getEnv(),ms, "addBatchMointorData", Integer.class, excelBeans);
            } catch (Exception e) {
                msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
            }
        }
        return msg.toJson();
    }

    @RequestMapping(value = "/downloadReadMeterDataList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "导出抄表接口", notes = "导出抄表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downloadZoneMeterList(@RequestBody MeterDataDTO meterDataDTO,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        //TODO:校验参数有效性

        //*****查询符合条件数据
        PageListVO<List<MeterDataVO>> md = ADOConnection.runTask(user.getEnv(),mds, "queryReadMeterDataList", PageListVO.class, meterDataDTO);
        List<MeterDataVO> list= md.getDataList();
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
    }

    @RequestMapping(value = "/queryReadMeterDataList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询抄表列表接口", notes = "查询抄表数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryReadMeterDataList(@RequestBody MeterDataDTO meterDataDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数格式
        PageListVO<List<MeterDataVO>> md = ADOConnection.runTask(user.getEnv(),mds, "queryReadMeterDataList", PageListVO.class, meterDataDTO);

        msg.setCode(0);
        msg.setData(md);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryReadMeterDataByBatchNo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据批次查询抄表数据列表接口", notes = "根据批次查询抄表数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryReadMeterDataByBatchNo(@RequestBody MeterDataDTO meterDataDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数
        if(meterDataDTO.getBatchNo()==null || "".equals(meterDataDTO.getBatchNo()) ){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toString();
        }

        PageListVO<List<MeterDataVO>>  md = ADOConnection.runTask(user.getEnv(),mds, "queryMeterDataByBatchNo", PageListVO.class, meterDataDTO);
        msg.setCode(0);
        msg.setData(md);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryReadMeterDataHistoryList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询抄表导入历史数据列表", notes = "查询抄表导入历史数据列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryReadMeterDataHistoryList(@RequestBody MeterDataDTO meterDataDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数

        PageListVO<List<MeterDataHisVO>>  md= ADOConnection.runTask(user.getEnv(),mds, "queryReadMeterDataHistoryList", PageListVO.class,meterDataDTO);
        msg.setCode(0);
        msg.setData(md);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryReadMeterDataDet/{refID}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询抄表详情接口", notes = "查询抄表详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryReadMeterDataDet(@PathVariable("refID") Integer refID,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        //TODO:校验是否有修改权限

        //TODO:参数meterDataDTO校验

        //*****查询符合条件数据
        MeterDataVO ret = ADOConnection.runTask(user.getEnv(),mds, "queryReadMeterDataDet", MeterDataVO.class,refID);
        msg.setCode(0);
        msg.setData(ret);
        return msg.toJson();
    }

    @RequestMapping(value = "/updateReadMeterDataDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改抄表详情接口", notes = "修改抄表详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateReadMeterDataDet(@RequestBody MeterDataDTO meterDataDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        //TODO:校验是否有修改权限

        //TODO:参数meterDataDTO校验

        //*****查询符合条件数据
        boolean ret = ADOConnection.runTask(user.getEnv(),ms, "updateReadMeterDataDet", boolean.class,meterDataDTO);
        msg.setCode(0);
        msg.setData(ret);
        return msg.toJson();
    }

    @RequestMapping("/downloadMeterDataTemplate.htm")
    @ApiOperation(value = "导出抄表数据Excel模板", notes = "导出抄表数据Excel模板", httpMethod = "POST", response = MessageBean.class)
    @ResponseBody
    public void downloadMeterDataTemplate(HttpServletResponse response, HttpServletRequest request) {

        //TODO:权限校验-校验是否有添加权限（有添加权限即有导入权限）

        //TODO:
        FileUtil.downloadFile(Constant.METERDATA_IMPORT_TEMPLATE,Constant.LINUX_TEMPALTE_PATH + Constant.METERDATA_IMPORT_TEMPLATE, response, request);
        return;
        //        try {
//            return ExportExcel.exportFile(Constant.METERDATA_IMPORT_TEMPLATE, Constant.LINUX_TEMPALTE_PATH + Constant.METERDATA_IMPORT_TEMPLATE, Constant.METERDATA_IMPORT_TEMPLATE_BTL, (Map) new HashMap());
//        }catch(Exception e){
//            return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
//        }
    }

    @RequestMapping("/importMeterData.htm")
    @ApiOperation(value = "导入抄表数据接口", notes = "导入抄表数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importMeterData(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent") MultipartFile file,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean<?> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);

        //TODO:校验是否有数据添加权限

        //TODO:Excel数据读取校验完整性，一致性，准确性

        List<MeterDataExcelBean> excelBeans = ImportExcelUtil.readExcel(file, MeterDataExcelBean.class);
        for(MeterDataExcelBean bean : excelBeans){
            bean.setBatchNo(BatchNo);
        }
        if (excelBeans == null || excelBeans.size()==0) {
            msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
            msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
        } else {
            try {
                Integer ret = ADOConnection.runTask(user.getEnv(),mds, "addBatchMeterData", Integer.class, excelBeans);
                msg.setCode(0);
                msg.setDescription("操作成功");
            } catch (Exception e) {
                e.printStackTrace();
                msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
            }
        }
        return msg.toJson();
    }

    @RequestMapping(value = "/queryMonRep.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询数据月报数据", notes = "查询数据月报数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMonRep(@RequestBody DataQualityDTO dqd,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        if(dqd.getMon() == null){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription("日期为空");
            return msg.toString();
        }
        PageListVO<List<MonRepVO>> monReps = ADOConnection.runTask(user.getEnv(),dqs, "queryMonRep", PageListVO.class,dqd);
        msg.setCode(0);
        msg.setData(monReps);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryDataImpact.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询数据影响分析数据", notes = "查询数据分析接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryDataImpact(@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        List<DataImpactVO> dis = ADOConnection.runTask(user.getEnv(),dqs, "queryDataImpact", List.class);
        msg.setCode(0);
        msg.setData(dis);
        return msg.toJson();
    }

    @RequestMapping(value = "/queryFlows.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询流量计类型数据", notes = "查询流量计类型数据", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryFlows(@PathVariable("tenantID") String tenantID,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        Gson gson = new Gson();
        Map data = new HashMap();
        List<PointAccountVO> dis = ADOConnection.runTask(user.getEnv(),mds, "queryFlows", List.class);
        if(dis.size()>0) {
            List<String> arr = new ArrayList<>();
            for (PointAccountVO item : dis) {
                arr.add(item.getPointNo());
            }
            String url = gis+"/"+tenantID+"/scada/listByPcodes.htm";
            JsonObject ret = InterfaceUtil.interfaceOfPostUtil(url,gson.toJson(arr));
            JsonArray gisdata = ret.getAsJsonArray("data");

            List<PointVO>  points= gson.fromJson(gisdata, new TypeToken<List<PointVO>>(){}.getType());
            for(PointVO item :points){
                for(PointAccountVO pa : dis){
                    if(item.getP_code().equals(pa.getPointNo())){
                        item.setType(pa.getType());
                    }
                }
            }
            /*
            data.put("group",dis);
            data.put("detail",points);
            */
            msg.setCode(0);
            msg.setData(points);
        }
        return msg.toJson();
    }

    /**
     * 校验字符串格式工具方法
     * @param format
     * @param str
     * @return
     */
    boolean _checkFormat(String format,String str){
        boolean _r = true;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            sdf.setLenient(false);
            sdf.parse(str);
        }catch(Exception e){
            _r = false;
        }
        return _r;
    }

}
