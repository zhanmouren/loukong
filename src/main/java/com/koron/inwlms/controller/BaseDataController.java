package com.koron.inwlms.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koron.common.StaffAttribute;
import com.koron.common.permission.SPIAccountAnno;
import com.koron.inwlms.bean.DTO.baseInf.*;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.VO.baseInf.*;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.baseData.*;
import com.koron.inwlms.service.sysManager.UserService;
import com.koron.inwlms.util.ExportDataUtil;
import com.koron.inwlms.util.FileUtil;
import com.koron.inwlms.util.ImportExcelUtil;
import com.koron.inwlms.util.InterfaceUtil;
import com.koron.permission.authority.DataInject;
import com.koron.permission.authority.DataRangeMethod;
import com.koron.permission.authority.OPSPIMethod;
import com.koron.permission.bean.VO.TblRoleRangeValueListVO;
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

    @Autowired
    private UserService userService;

    @Value("${server.gis.address}")
    private String gis;

    @RequestMapping(value = "/queryPGData.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询数据接口", notes = "查询数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPGData(@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        List<String[]> pipes = ADOConnection.runTask(user.getEnv(),pipesvr, "queryALList", List.class);
        msg.setCode(0);
        msg.setData(pipes);
        return msg.toJson();
    }

    /*
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
*/

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
    public String importPipe(@RequestParam("BatchNo") String BatchNo,@RequestParam("file") MultipartFile file) {
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
    public String importPoint(@RequestParam("BatchNo") String BatchNo,@RequestParam("file ") MultipartFile file) {

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
    public String importFacility(@RequestParam("BatchNo") String BatchNo,@RequestParam("file ") MultipartFile file) {

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
    public String importTable(@RequestParam("BatchNo") String BatchNo,@RequestParam("file ") MultipartFile file) {
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
    //public HttpEntity<?> downloadZoneList(@RequestBody ZoneDTO zoneDTO,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
    public HttpEntity<?> downloadZoneList(@RequestParam String objValue,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        ZoneDTO zoneDTO = jsonValue.fromJson(objValue, ZoneDTO.class);

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

    @RequestMapping(value = "/addZone.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加分区接口", notes = "添加分区接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addZone(@RequestBody ZoneDTO zoneDTO,@PathVariable("tenantID") String tenantID,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        Gson gson = new Gson();
        Map data = new HashMap();
        data.put("name",zoneDTO.getName());
        data.put("type",zoneDTO.getType());
        data.put("rank",zoneDTO.getRank());
        data.put("geometry",zoneDTO.getGeometry());
        try {
        	//***获取当前最大分区号
            ZoneVO zv = ADOConnection.runTask(user.getEnv(),zcs, "queryMaxZoneNo", ZoneVO.class,zoneDTO);
            if(zv == null) {
            	zoneDTO.setZoneNo("U00001");
            }else {
            	Integer newZoneNo = Integer.valueOf(zv.getZoneNo().toString().substring(1)) + 1;
            	zoneDTO.setZoneNo("U"+String.format("%05d", newZoneNo));
            }
           data.put("zoneNo", zoneDTO.getZoneNo());
            String url = gis+"/"+tenantID+"/dmaPosition/add.htm";
            JsonObject ret = InterfaceUtil.interfaceOfPostUtil(url,gson.toJson(data));
            msg = gson.fromJson(ret, new TypeToken<MessageBean>(){}.getType());
            if(msg.getCode()==0){
                //*****添加分区并根据父分区添加分区树节点
            	Integer addResult = ADOConnection.runTask(user.getEnv(),zcs, "addZone", Integer.class,zoneDTO);
            	if(addResult!=null) {
    				if(addResult==1) {
    					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
    					msg.setDescription("添加分区成功");
    					msg.setData(data);
    				}else {
    					msg.setCode(Constant.MESSAGE_INT_ADDERROR);
    					msg.setDescription("添加分区失败");
    				}
            	}else {
            		
            	}
            }else {
            	msg.setCode(Constant.MESSAGE_INT_ADDERROR);
        		msg.setDescription("添加分区失败(Gis添加失败)");
            }
        }catch(Exception e) {
        	msg.setCode(Constant.MESSAGE_INT_ERROR);
        	msg.setDescription("添加分区失败");
        }
        
        return msg.toJson();
    }

    @OPSPIMethod("fqzs"+Constant.QUERY)
    @DataRangeMethod
    @RequestMapping(value = "/queryZoneList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区列表接口", notes = "查询分区列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneList(@RequestBody ZoneDTO zoneDTO, @DataInject TblRoleRangeValueListVO rangeList, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
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

    @OPSPIMethod("fqzs"+Constant.UPDATE)
    @DataRangeMethod
    @RequestMapping(value = "/updateZones.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "更新分区属性接口", notes = "更新分区属性接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateZones(@RequestBody ZoneDTO zoneDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性
        if(zoneDTO.getZoneNo()==null || "".equals(zoneDTO.getZoneNo())){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toJson();
        }

        //*****插入符合条件数据
        Integer r1 = ADOConnection.runTask(user.getEnv(),zcs, "updateZones", Integer.class,zoneDTO);
        if(r1>=0) {
                msg.setCode(0);
                msg.setDescription("操作成功");
            }else{
                msg.setCode(Constant.MESSAGE_INT_ERROR);
                msg.setDescription("操作失败");
            }
        return msg.toJson();
    }

    @OPSPIMethod("fqzs"+Constant.UPDATE)
    @DataRangeMethod
    @RequestMapping(value = "/updateZoneCharger.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "更新分区负责人接口", notes = "更新分区负责人接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateZoneCharger(@RequestBody ZoneDTO zoneDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性
        if(zoneDTO.getZoneNo()==null || "".equals(zoneDTO.getZoneNo())){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toJson();
        }

        //*****插入符合条件数据
        Integer r1 = ADOConnection.runTask(user.getEnv(), zcs, "updateZoneCharger", Integer.class, zoneDTO);
        if (r1 >= 0) {
            msg.setCode(0);
            msg.setDescription("操作成功");
        } else{
            msg.setCode(-1);
            msg.setDescription("操作失败");
        }
        return msg.toJson();
    }

    @RequestMapping(value = "/queryChargers.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询管辖人接口", notes = "查询管辖人接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryChargers(@RequestBody QueryUserDTO userDTO, @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
        //执行查询职员
        try {
            PageListVO result=ADOConnection.runTask(user.getEnv(),userService, "queryUser", PageListVO.class, userDTO);
            if(result!=null  && result.getRowNumber()>0) {
                msg.setCode(Constant.MESSAGE_INT_SUCCESS);
                msg.setDescription("查询到相关职员的信息");
                msg.setData(result);
            }else {
                //没查询到数据
                msg.setCode(Constant.MESSAGE_INT_SUCCESS);
                msg.setDescription("没有查询到相关职员的信息");
            }
        }catch(Exception e){
            //查询失败
            msg.setCode(Constant.MESSAGE_INT_ERROR);
            msg.setDescription("查询职员失败");
        }
        return msg.toJson();
    }

    @RequestMapping(value = "/queryZoneOwners.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区管辖人接口", notes = "查询分区管辖人接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneOwners(@RequestBody ZoneDTO zoneDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性
        if(zoneDTO.getZoneNo()==null || "".equals(zoneDTO.getZoneNo())){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toJson();
        }

        //*****查询符合条件数据
        PageListVO<List<ZoneUserVO>> zus = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneOwners", PageListVO.class,zoneDTO);

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
        if(zoneDTO.getUser()==null || "".equals(zoneDTO.getUser())){
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

    @OPSPIMethod("fqyjcd"+Constant.QUERY)
    @DataRangeMethod
    @RequestMapping(value = "/downloadZonePointList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "导出分区与监测点接口", notes = "导出分区与监测点接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    //public HttpEntity<?> downloadZonePointList(@RequestBody ZonePointDTO zonePointDTO,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
    public HttpEntity<?> downloadZonePointList(@RequestParam String objValue,@RequestParam String titleInfos,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        //TODO:校验参数有效性

        ZonePointDTO zonePointDTO = jsonValue.fromJson(objValue, ZonePointDTO.class);
        //*****查询符合条件数据
        PageListVO<List<ZonePointVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZonePointList", PageListVO.class,zonePointDTO);
        List<ZonePointVO> list= zps.getDataList();
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
    }

    @OPSPIMethod("fqyjcd"+Constant.QUERY)
    @DataRangeMethod
    @RequestMapping(value = "/queryZonePointList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与监测点列表接口", notes = "查询分区与监测点列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZonePointList(@RequestBody ZonePointDTO zonePointDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性

        //*****查询符合条件数据
        PageListVO<List<ZonePointVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZonePointList", PageListVO.class,zonePointDTO);
        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }

    @OPSPIMethod("fqyjcd"+Constant.QUERY)
    @DataRangeMethod
    @RequestMapping(value = "/queryZonePointHistory.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与监测点数据导入历史接口", notes = "查询分区与监测点数据导入历史接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZonePointHistory(@RequestBody ZonePointDTO zonePointDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性

        //TODO:权限校验是否有查询权限

        //*****查询符合条件数据
        PageListVO<List<ZonePointHisVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZonePointHistory", PageListVO.class,zonePointDTO);
        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }

    @OPSPIMethod("fqyjcd"+Constant.QUERY)
    @DataRangeMethod
    @RequestMapping(value = "/queryZonePointDet/{refID}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区监测点详情接口", notes = "查询分区监测点详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZonePointDet(@PathVariable("refID") Integer refID,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

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

    @OPSPIMethod("fqyjcd"+Constant.UPDATE)
    @DataRangeMethod
    @RequestMapping(value = "/updateZonePointDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改分区监测点详情接口", notes = "修改分区监测点详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateZonePointDet(@RequestBody ZonePointDTO zonePointDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

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

    @OPSPIMethod("fqyjcd"+Constant.DELETE)
    @DataRangeMethod
    @RequestMapping("/deleteZonePointByBatch/{BatchNo}")
    @ApiOperation(value = "删除某一批次分区与监测点数据", notes = "删除某一批次分区与监测点数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteZonePointByBatch(@PathVariable("BatchNo") String BatchNo,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
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

    @OPSPIMethod("fqyjcd"+Constant.ADD)
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

    @OPSPIMethod("fqyjcd"+Constant.ADD)
    @DataRangeMethod
    @RequestMapping("/importZonePoint.htm")
    @ApiOperation(value = "导入分区与监测点数据接口", notes = "导入分区与监测点数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importZonePoint(@RequestParam("BatchNo") String BatchNo,@RequestParam("file") MultipartFile file,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();

        //TODO:校验是否有数据添加权限

        //参数校验
        if(BatchNo==null || "".equals(BatchNo)){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription("批号不能为空");
            return msg.toString();
        }

        List<ZonePointExcelBean> excelBeans = ImportExcelUtil.readExcel(file, ZonePointExcelBean.class);

        if (excelBeans == null || excelBeans.size()==0) {
            msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
            msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
        } else {
            try {
                //TODO:Excel数据读取校验完整性，一致性，准确性
                DataQualityVO dq = new DataQualityVO();
                Integer zoonNum = 0;
                Integer pointNum = 0;
                Integer typeNum = 0;
                Integer validNum = 0;
                for(ZonePointExcelBean bean : excelBeans){
                    boolean isZoonNo = true;
                    boolean isPointNo = true;
                    boolean isType = true;
                    bean.setBatchNo(BatchNo);
                    if(bean.getZoneNo()==null || "".equals(bean.getZoneNo())){
                        zoonNum++;
                        isZoonNo = false;
                    }
                    if(bean.getPointNo() == null || "".equals(bean.getPointNo())){
                        pointNum++;
                        isPointNo = false;
                    }
                    if(bean.getType()== null || "".equals(bean.getType())){
                        typeNum++;
                        isType = false;
                    }
                    if(isZoonNo && isPointNo && isType){
                        validNum++;
                    }
                }
                dq.setRow(validNum);
                dq.setOriginRow(excelBeans.size());
                dq.setAvailability((double)(validNum/excelBeans.size()));//有效性
                dq.setIntegrity((double)(1-(zoonNum+pointNum+typeNum)/(validNum*3)));//完整性
                dq.setAccuracy(1.0);//准确性
                dq.setConsistency((double)(zoonNum+pointNum)/(2*100));
                dq.setTimely(1.0);
                dq.setBatchNo(BatchNo);
                dq.setCreateBy(user.getLoginName());
                dq.setType("L103500001");

                Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "addBatchZonePoint", Integer.class, excelBeans,dq);
               /*
                if(ret>0){
                    Integer r = ADOConnection.runTask(user.getEnv(),dqs, "addZoneConfDataQuality", Integer.class, dq);
                }
                */
               msg.setCode(0);
                msg.setDescription("导入成功");
            } catch (Exception e) {
                msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
            }
        }
        return msg.toJson();
    }

    @OPSPIMethod("fqyhb"+Constant.QUERY)
    @DataRangeMethod
    @RequestMapping(value = "/queryZoneMeterHistory.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与户表数据导入历史接口", notes = "查询分区与户表数据导入历史接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMeterHistory(@RequestBody ZoneMeterDTO zoneMeterDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验参数有效性

        //TODO:权限校验是否有查询权限

        //*****查询符合条件数据
        PageListVO<List<ZoneMeterHisVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneMeterHistory", PageListVO.class,zoneMeterDTO);
        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }

    @OPSPIMethod("fqyhb"+Constant.QUERY)
    @DataRangeMethod
    @RequestMapping(value = "/downloadZoneMeterList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "导出分区与户表接口", notes = "导出分区与户表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downloadZoneMeterList(@RequestParam String objValue,@RequestParam String titleInfos,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //public HttpEntity<?> downloadZoneMeterList(@RequestBody ZoneMeterDTO zoneMeterDTO,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        //TODO:校验参数有效性

        ZoneMeterDTO zoneMeterDTO = jsonValue.fromJson(objValue, ZoneMeterDTO.class);
        //*****查询符合条件数据
        PageListVO<List<ZoneMeterVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneMeterList", PageListVO.class,zoneMeterDTO);
        List<ZoneMeterVO> list= zps.getDataList();
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
    }

    @OPSPIMethod("fqyhb"+Constant.QUERY)
    @DataRangeMethod
    @RequestMapping(value = "/queryZoneMeterList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与户表列表接口", notes = "查询分区与户表列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMeterList(@RequestBody ZoneMeterDTO zoneMeterDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数

        //*****查询符合条件数据
        PageListVO<List<ZoneMeterVO>> zps = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneMeterList", PageListVO.class,zoneMeterDTO);
        msg.setCode(0);
        msg.setData(zps);
        return msg.toJson();
    }

    @OPSPIMethod("fqyhb"+Constant.QUERY)
    @DataRangeMethod
    @RequestMapping(value = "/queryZoneMeterDet.html", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区与户表详情接口", notes = "查询分区与户表详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMeterDet(@RequestBody ZoneMeterDTO zoneMeterDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        //入参校验
        if(zoneMeterDTO.getVolumeNo()==null || zoneMeterDTO.getZoneNo()==null){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toJson();
        }
        //*****查询符合条件数据
        PageListVO<List<ZoneMeterVO>> zp = ADOConnection.runTask(user.getEnv(),zcs, "queryZoneMeterDet", PageListVO.class,zoneMeterDTO);
        //TODO:获取分区户表详情数据
        msg.setData(zp);
        return msg.toJson();
    }

    @OPSPIMethod("fqyhb"+Constant.UPDATE)
    @DataRangeMethod
    @RequestMapping(value = "/updateZoneMeterDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改分区户表详情接口", notes = "修改分区户表详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateZoneMeterDet(@RequestBody ZoneMeterDTO zoneMeterDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();

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

    @OPSPIMethod("fqyjcd"+Constant.DELETE)
    @DataRangeMethod
    @RequestMapping("/deleteZonePointByRef/{refID}")
    @ApiOperation(value = "删除分区与监测点数据", notes = "删除分区与监测点数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteZonePointByRef(@PathVariable("refID") Integer refID,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验是否有删除权限


        //TODO:返回数据
        Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "deleteZonePointByRef", Integer.class,refID);
        if(ret>=0){
            msg.setCode(Constant.MESSAGE_INT_SUCCESS);
            msg.setDescription("操作成功");
        }else{
            msg.setCode(Constant.MESSAGE_INT_ERROR);
            msg.setDescription("操作失败");
        }
        return msg.toJson();
    }

    @OPSPIMethod("fqyhb"+Constant.DELETE)
    @DataRangeMethod
    @RequestMapping("/deleteZoneMeterRel/{refID}")
    @ApiOperation(value = "删除分区与户表数据", notes = "删除分区与户表数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteZoneMeterRel(@PathVariable("refID")Integer refID,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
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

    @OPSPIMethod("fqyhb"+Constant.DELETE)
    @DataRangeMethod
    @RequestMapping(value ="/deleteZoneMeters.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除分区某一册本号数据", notes = "删除分区某一册本号数据", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteZoneMeters(@RequestBody ZoneMeterDTO zoneMeterDTO,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        if(zoneMeterDTO.getZoneNo() ==null || zoneMeterDTO.getVolumeNo()==null){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toJson();
        }

        //TODO:返回数据
        Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "deleteZoneMeters", Integer.class,zoneMeterDTO);
        if(ret>=0){
            msg.setCode(Constant.MESSAGE_INT_SUCCESS);
            msg.setDescription("操作成功");
        }else{
            msg.setCode(Constant.MESSAGE_INT_ERROR);
            msg.setDescription("操作失败");
        }
        return msg.toJson();
    }

    @OPSPIMethod("fqyhb"+Constant.DELETE)
    @DataRangeMethod
    @RequestMapping("/deleteZoneMeterByBatchNo/{BatchNo}")
    @ApiOperation(value = "删除某一批次分区与户表数据", notes = "删除某一批次分区与户表数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteZoneMeterByBatchNo(@PathVariable("BatchNo") String BatchNo,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
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

    @OPSPIMethod("fqyhb"+Constant.QUERY)
    @DataRangeMethod
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

    @OPSPIMethod("fqyhb"+Constant.ADD)
    @DataRangeMethod
    @RequestMapping("/importZoneMeter.htm")
    @ApiOperation(value = "导入分区与户表数据接口", notes = "导入分区与户表数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importZoneMeter(@RequestParam("BatchNo") String BatchNo,@RequestParam("file") MultipartFile file,@DataInject TblRoleRangeValueListVO rangeList,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();

        //TODO:校验是否有数据添加权限

        //参数校验
        if(BatchNo==null || "".equals(BatchNo)){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription("批号不能为空");
            return msg.toJson();
        }

        //TODO:Excel数据读取校验完整性，一致性，准确性
        List<ZoneMeterExcelBean> excelBeans = ImportExcelUtil.readExcel(file, ZoneMeterExcelBean.class);

        if (excelBeans == null || excelBeans.size()==0) {
            msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
            msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
        } else {
            try {
                //Excel数据读取校验完整性，一致性，准确性
                DataQualityVO dq = new DataQualityVO();
                Integer zoonNum = 0;
                Integer meterNum = 0;
                Integer typeNum = 0;
                Integer validNum = 0;
                for(ZoneMeterExcelBean bean : excelBeans){
                    boolean isZoonNo = true;
                    boolean isMeterNo = true;
                    boolean isType = true;
                    bean.setBatchNo(BatchNo);
                    if(bean.getZoneNo()==null || "".equals(bean.getZoneNo())){
                        ++zoonNum;
                        isZoonNo = false;
                    }
                    if(bean.getMeterNo() == null || "".equals(bean.getMeterNo())){
                        ++meterNum;
                        isMeterNo = false;
                    }
                    if(bean.getType()== null || "".equals(bean.getType())){
                        ++typeNum;
                        isType = false;
                    }
                    if(isZoonNo && isMeterNo && isType){
                        validNum++;
                    }
                }
                dq.setRow(validNum);
                dq.setOriginRow(excelBeans.size());
                dq.setAvailability((double)(validNum/excelBeans.size()));//有效性
                dq.setIntegrity((double)(1-(zoonNum+meterNum+typeNum)/(validNum*3)));//完整性
                dq.setAccuracy(1.0);//准确性
                dq.setConsistency((double)(zoonNum+meterNum)/(2*100));
                dq.setTimely(1.0);
                dq.setBatchNo(BatchNo);
                dq.setCreateBy(user.getLoginName());
                dq.setType("L103500002");
                Integer ret = ADOConnection.runTask(user.getEnv(),zcs, "addBatchZoneMeter", Integer.class, excelBeans, dq);
                msg.setCode(0);
            } catch (Exception e) {
                msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
            }
        }
        return msg.toJson();
    }

    @OPSPIMethod("jcsj"+Constant.QUERY)
    @RequestMapping(value = "/downloadMonitorDataList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "导出监测数据接口", notes = "导出监测数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    //public HttpEntity<?> downloadMonitorDataList(@RequestBody MonitorDataDTO monitorDataDTO,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
    public HttpEntity<?> downloadMonitorDataList(@RequestParam String objValue,@RequestParam String titleInfos,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        //TODO:校验参数有效性

        MonitorDataDTO monitorDataDTO = jsonValue.fromJson(objValue, MonitorDataDTO.class);
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

    @OPSPIMethod("jcsj"+Constant.QUERY)
    @RequestMapping(value = "/queryMonitorDataList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测数据列表接口", notes = "查询监测数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMonitorDataList(@RequestBody MonitorDataDTO monitorDataDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
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

    @OPSPIMethod("jcsj"+Constant.DELETE)
    @RequestMapping(value = "/deleteMonitorDataByBatchNo/{BatchNo}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "按批次删除监测数据接口", notes = "按批次删除监测数据接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteMonitorDataByBatchNo(@PathVariable("BatchNo")String BatchNo,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数

        //List<MonitorDataVO> mds = null;
        Integer ret = ADOConnection.runTask(user.getEnv(),ms, "deleteMonitorDataByBatchNo", Integer.class, BatchNo);

        msg.setCode(0);
        msg.setData(ret);
        return msg.toJson();
    }

    @OPSPIMethod("jcsj"+Constant.QUERY)
    @RequestMapping(value = "/queryLastMonitorDataList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询最新监测数据接口", notes = "查询最新监测数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryLastMonitorDataList(@RequestBody MonitorDataDTO monitorDataDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user){
        MessageBean msg = new MessageBean();
        List<MonitorDataVO> ret= ADOConnection.runTask(user.getEnv(),ms, "queryLastMonitorDataList", List.class, monitorDataDTO);
        msg.setCode(0);
        msg.setData(ret);
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

    @OPSPIMethod("jcsj"+Constant.QUERY)
    @RequestMapping(value = "/queryPressureFlowDet/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询压力/流量详情接口", notes = "查询压力/流量详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPressureFlowDet(@PathVariable("id") Integer id,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

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

    @OPSPIMethod("jcsj"+Constant.QUERY)
    @RequestMapping(value = "/queryNoiseDet/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询噪声详情接口", notes = "查询噪声详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryNoiseDet(@PathVariable("id") Integer id,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

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

    @OPSPIMethod("jcsj"+Constant.QUERY)
    @RequestMapping(value = "/queryMonitorDataHistoryList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测导入历史数据列表", notes = "查询监测导入历史数据列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMonitorDataHistoryList(@RequestBody MonitorDataDTO monitorDataDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
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

    @OPSPIMethod("jcsj"+Constant.UPDATE)
    @RequestMapping(value = "/updateMonitorDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改监测详情接口", notes = "修改监测详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateMonitorDet(@RequestBody MonitorDataDTO monitorDataDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

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

    @OPSPIMethod("jcsj"+Constant.ADD)
    @RequestMapping("/downloadMonitorDataTemplate.htm")
    @ApiOperation(value = "导出监测数据Excel模板", notes = "导出监测数据Excel模板", httpMethod = "POST", response = MessageBean.class)
    @ResponseBody
    public void downloadMonitorDataTemplate(HttpServletResponse response, HttpServletRequest request,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

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

    @OPSPIMethod("jcsj"+Constant.ADD)
    @RequestMapping("/importMonitorData.htm")
    @ApiOperation(value = "导入监测数据接口", notes = "导入监测数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importMonitorData(@RequestParam("BatchNo") String BatchNo,@RequestParam("type") String type,@RequestParam("file") MultipartFile file,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean<?> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);

        //TODO:校验是否有数据添加权限

        //TODO:Excel数据读取校验完整性，一致性，准确性

        List<MonitorDataExcelBean> excelBeans = ImportExcelUtil.readExcel(file, MonitorDataExcelBean.class);

        if (excelBeans == null || excelBeans.size()==0) {
            msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
            msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
        } else {
            try {
                //TODO:Excel数据读取校验完整性，一致性，准确性
                DataQualityVO dq = _checkMonitorDataQuality(excelBeans,type);
                for(MonitorDataExcelBean bean : excelBeans) {
                    bean.setBatchNo(BatchNo);
                    bean.setType(type);
                }
                dq.setBatchNo(BatchNo);
                dq.setCreateBy(user.getLoginName());
                Integer ret = 0;
                if("1".equals(type)){
                	ret = ADOConnection.runTask(user.getEnv(),ms, "addBatchMointorData", Integer.class, excelBeans,dq);
                }else {
                	ret = ADOConnection.runTask(user.getEnv(),ms, "addBatchNoiseData", Integer.class, excelBeans,dq);
                }
                if(ret < 1) {
                	msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                    msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
                }
            } catch (Exception e) {
                msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
            }
        }
        return msg.toJson();
    }

    @OPSPIMethod("tm"+Constant.QUERY)
    @RequestMapping(value = "/downloadReadMeterDataList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "导出抄表接口", notes = "导出抄表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downloadReadMeterDataList(@RequestParam String objValue,@RequestParam String titleInfos,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        //TODO:权限校验是否有查询权限

        MessageBean msg = new MessageBean();
        Gson jsonValue = new Gson();
        //TODO:校验参数有效性
        MeterDataDTO meterDataDTO = jsonValue.fromJson(objValue, MeterDataDTO.class);
        //*****查询符合条件数据
        PageListVO<List<MeterDataVO>> md = ADOConnection.runTask(user.getEnv(),mds, "queryReadMeterDataList", PageListVO.class, meterDataDTO);
        List<MeterDataVO> list= md.getDataList();
        List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
        }.getType());
        //导出list
        return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
    }

    @OPSPIMethod("tm"+Constant.QUERY)
    @RequestMapping(value = "/queryReadMeterDataList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询抄表列表接口", notes = "查询抄表数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryReadMeterDataList(@RequestBody MeterDataDTO meterDataDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数格式
        PageListVO<List<MeterDataVO>> md = ADOConnection.runTask(user.getEnv(),mds, "queryReadMeterDataList", PageListVO.class, meterDataDTO);

        msg.setCode(0);
        msg.setData(md);
        return msg.toJson();
    }

    @OPSPIMethod("tm"+Constant.QUERY)
    @RequestMapping(value = "/queryReadMeterDataByBatchNo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据批次查询抄表数据列表接口", notes = "根据批次查询抄表数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryReadMeterDataByBatchNo(@RequestBody MeterDataDTO meterDataDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
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

    @OPSPIMethod("tm"+Constant.QUERY)
    @RequestMapping(value = "/queryReadMeterDataHistoryList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询抄表导入历史数据列表", notes = "查询抄表导入历史数据列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryReadMeterDataHistoryList(@RequestBody MeterDataDTO meterDataDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:权限校验是否有查询权限

        //TODO:校验参数

        PageListVO<List<MeterDataHisVO>>  md= ADOConnection.runTask(user.getEnv(),mds, "queryReadMeterDataHistoryList", PageListVO.class,meterDataDTO);
        msg.setCode(0);
        msg.setData(md);
        return msg.toJson();
    }

    @OPSPIMethod("tm"+Constant.QUERY)
    @RequestMapping(value = "/queryReadMeterDataDet/{refID}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询抄表详情接口", notes = "查询抄表详情接口", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryReadMeterDataDet(@PathVariable("refID") Integer refID,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        //TODO:校验是否有修改权限

        //TODO:参数meterDataDTO校验

        //*****查询符合条件数据
        MeterDataVO ret = ADOConnection.runTask(user.getEnv(),mds, "queryReadMeterDataDet", MeterDataVO.class,refID);
        msg.setCode(0);
        msg.setData(ret);
        return msg.toJson();
    }

    @OPSPIMethod("tm"+Constant.UPDATE)
    @RequestMapping(value = "/updateReadMeterDataDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改抄表详情接口", notes = "修改抄表详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateReadMeterDataDet(@RequestBody MeterDataDTO meterDataDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        //TODO:校验是否有修改权限

        //TODO:参数meterDataDTO校验

        //*****查询符合条件数据
        boolean ret = ADOConnection.runTask(user.getEnv(),ms, "updateReadMeterDataDet", boolean.class,meterDataDTO);
        msg.setCode(0);
        msg.setData(ret);
        return msg.toJson();
    }

    @OPSPIMethod("tm"+Constant.ADD)
    @RequestMapping("/downloadMeterDataTemplate.htm")
    @ApiOperation(value = "导出抄表数据Excel模板", notes = "导出抄表数据Excel模板", httpMethod = "POST", response = MessageBean.class)
    @ResponseBody
    public void downloadMeterDataTemplate(HttpServletResponse response, HttpServletRequest request,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

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

    @OPSPIMethod("tm"+Constant.ADD)
    @RequestMapping("/importMeterData.htm")
    @ApiOperation(value = "导入抄表数据接口", notes = "导入抄表数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importMeterData(@RequestParam("BatchNo") String BatchNo,@RequestParam("file") MultipartFile file,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
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

    @OPSPIMethod("tm"+Constant.DELETE)
    @RequestMapping("/deleteMeterData/{refID}")
    @ApiOperation(value = "删除抄表数据", notes = "删除抄表数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteMeterDataByBatch(@PathVariable("refID") Integer refID,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验是否有删除权限

        //TODO:
        if(refID==null){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toString();
        }

        //***删除数据
        Integer ret = ADOConnection.runTask(user.getEnv(),mds, "deleteMeterData", Integer.class,refID);
        msg.setCode(0);
        msg.setDescription(Constant.MESSAGE_STRING_SUCCESS);
        return msg.toJson();
    }

    @OPSPIMethod("tm"+Constant.DELETE)
    @RequestMapping("/deleteMeterDataByBatch/{BatchNo}")
    @ApiOperation(value = "删除某一批次抄表数据", notes = "删除某一批次抄表数据", httpMethod = "GET", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteMeterDataByBatch(@PathVariable("BatchNo") String BatchNo,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        MessageBean msg = new MessageBean();
        //TODO:校验是否有删除权限

        //TODO:
        if(BatchNo==null || "".equals(BatchNo)){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription(Constant.MESSAGE_STRING_NULL);
            return msg.toString();
        }

        //***删除某一批次数据
        Integer ret = ADOConnection.runTask(user.getEnv(),mds, "deleteMeterDataByBatch", Integer.class,BatchNo);
        msg.setCode(0);
        msg.setDescription(Constant.MESSAGE_STRING_SUCCESS);
        return msg.toJson();
    }

    @OPSPIMethod("sjyb"+Constant.QUERY)
    @RequestMapping(value = "/queryMonRep.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询数据月报数据", notes = "查询数据月报数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMonRep(@RequestBody DataQualityDTO dqd,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

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

    @OPSPIMethod("sjyxfx"+Constant.QUERY)
    @RequestMapping(value = "/queryDataImpact.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询数据影响分析数据", notes = "查询数据分析接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryDataImpact(@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        List<DataImpactVO> dis = ADOConnection.runTask(user.getEnv(),dqs, "queryDataImpact", List.class);
        msg.setCode(0);
        msg.setData(dis);
        return msg.toJson();
    }

    @OPSPIMethod("jcsljd"+Constant.QUERY)
    @RequestMapping(value = "/queryMonitoringQuantity.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测水量校对数据", notes = "查询监测水量校对数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMonitoringQuantity(@RequestBody DataQualityDTO dqd,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        Gson gson = new Gson();
        Map data = new HashMap();
        PageListVO<List<MonitorQuantityVO>> dis = ADOConnection.runTask(user.getEnv(),dqs, "queryMonitoringQuantity", PageListVO.class,dqd);
        msg.setCode(0);
        msg.setData(dis);
        return msg.toJson();
    }


    @OPSPIMethod("jcsljd"+Constant.UPDATE)
    @RequestMapping(value = "/updateMonitoringQuantity.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改监测水量校对数据", notes = "修改监测水量校对数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateMonitoringQuantity(@RequestBody DataQualityDTO dqd,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {

        MessageBean msg = new MessageBean();
        Gson gson = new Gson();
        Map data = new HashMap();

        //数据校验
        if(dqd.getRefID() == null || dqd.getRefID().length <=0){
            msg.setCode(Constant.MESSAGE_INT_NULL);
            msg.setDescription("参数为空");
            return msg.toJson();
        }
        dqd.setUpdateBy(user.getLoginName());
        Integer ret = ADOConnection.runTask(user.getEnv(),dqs, "updateMonitoringQuantity", Integer.class,dqd);
        if(ret>0){
            msg.setCode(Constant.MESSAGE_INT_SUCCESS);
            msg.setDescription("操作成功");
        }else{
            msg.setCode(Constant.MESSAGE_INT_ERROR);
            msg.setDescription("操作失败");
        }
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

    /**
     * 监测数据数据质量校验
     */
    DataQualityVO _checkMonitorDataQuality(List<MonitorDataExcelBean> excelBeans,String type){

        DataQualityVO dq = new DataQualityVO();

        if("1".equals(type)){
            Integer flowNum = 0;
            Integer forwardFlowRateNum = 0;
            Integer reverseFlowRateNum = 0;
            Integer flowRateNum = 0;
            Integer upstreamPressureNum = 0;
            Integer downstreamPressureNum =0;
            Integer unfavorPressureNum = 0;
            Integer pointNum = 0;
            Integer validNum = 0;

            for(MonitorDataExcelBean bean : excelBeans){
                boolean isForwardFlowRate = true;
                boolean isFlow = true;
                boolean isReverseFlowRate = true;
                boolean isFlowRate = true;
                boolean isUpstreamPressure = true;
                boolean isDownstreamPressure = true;
                boolean isUnfavorPressure = true;
                boolean isPointID = true;

                if(bean.getPointID()== null || "".equals(bean.getPointID())){
                    pointNum++;
                    isPointID = false;
                }

                if(bean.getFlow()== null || "".equals(bean.getFlow())){
                    flowNum++;
                    isFlow = false;
                }
                if(bean.getForwardFlowRate()== null || "".equals(bean.getForwardFlowRate())){
                    forwardFlowRateNum++;
                    isForwardFlowRate = false;
                }
                if(bean.getReverseFlowRate()==null || "".equals(bean.getReverseFlowRate())){
                    flowRateNum++;
                    isFlow = false;
                }
                if(bean.getFlowRate()==null || "".equals(bean.getFlowRate())){
                    flowRateNum++;
                    isFlowRate = false;
                }
                if(bean.getUpstreamPressure()==null || "".equals(bean.getUpstreamPressure())){
                    upstreamPressureNum++;
                    isUpstreamPressure = false;
                }
                if(bean.getDownstreamPressure() == null || "".equals(bean.getDownstreamPressure())){
                    downstreamPressureNum++;
                    isDownstreamPressure = false;
                }
                if(bean.getUnfavorPressure() == null || "".equals(bean.getUnfavorPressure())){
                    unfavorPressureNum++;
                    isUnfavorPressure = false;
                }

                if(isPointID && isFlow && isForwardFlowRate && isReverseFlowRate && isFlowRate && isUpstreamPressure && isDownstreamPressure && isUnfavorPressure){
                    validNum++;
                }
            }
            dq.setRow(validNum);
            dq.setOriginRow(excelBeans.size());
            dq.setAvailability((double)(validNum/excelBeans.size()));//有效性
            dq.setIntegrity((double)(1-(pointNum+flowNum+forwardFlowRateNum+reverseFlowRateNum+flowRateNum+upstreamPressureNum+downstreamPressureNum+unfavorPressureNum)/(validNum*8)));//完整性
            dq.setAccuracy(1.0);//准确性
            dq.setConsistency((double)(pointNum+flowNum+forwardFlowRateNum+reverseFlowRateNum+flowRateNum+upstreamPressureNum+downstreamPressureNum+unfavorPressureNum)/(2*100));
            dq.setTimely(1.0);
            dq.setType(type);
        }else{
            Integer noiseNum = 0;
            Integer thresholdNum = 0;
            Integer pointNum = 0;
            Integer validNum = 0;

            for(MonitorDataExcelBean bean : excelBeans){
                boolean isNoise = true;
                boolean isThresholdNum = true;
                boolean isPointID = true;

                if(bean.getPointID()== null || "".equals(bean.getPointID())){
                    pointNum++;
                    isPointID = false;
                }
                if(bean.getNoise()== null || "".equals(bean.getNoise())){
                    noiseNum++;
                    isNoise = false;
                }
                if(bean.getThreshold()== null || "".equals(bean.getThreshold())){
                    thresholdNum++;
                    isThresholdNum = false;
                }
                if(isPointID && isNoise && isThresholdNum){
                    validNum++;
                }
            }
            dq.setRow(validNum);
            dq.setOriginRow(excelBeans.size());
            dq.setAvailability((double)(validNum/excelBeans.size()));//有效性
            dq.setIntegrity((double)(1-(pointNum+noiseNum+thresholdNum)/(validNum*3)));//完整性
            dq.setAccuracy(1.0);//准确性
            dq.setConsistency((double)(pointNum+noiseNum+thresholdNum)/(2*100));
            dq.setTimely(1.0);
            dq.setType(type);
        }
        return dq;
    }

}
