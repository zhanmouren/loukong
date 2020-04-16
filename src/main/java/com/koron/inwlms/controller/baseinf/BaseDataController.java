package com.koron.inwlms.controller.baseinf;


import com.alibaba.fastjson.JSONObject;
import com.koron.inwlms.bean.DTO.property.FacilityDTO;
import com.koron.inwlms.bean.DTO.property.PipeDTO;
import com.koron.inwlms.bean.DTO.property.PointDTO;
import com.koron.inwlms.service.baseData.*;
import com.koron.inwlms.util.InterfaceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.swan.bean.MessageBean;

/**
 * @auother:zhong
 * @date:
 * @description:
 */
@RestController
@Api(value = "baseData", description = "基础数据Controller")
@RequestMapping(value = "/baseData")
public class BaseDataController {

    @Autowired
    private DataQualityService dqs;

    @Autowired
    private FacilityService fs;

    @Autowired
    private PipeNetworkService pns;

    @Autowired
    private PipeService ps;

    @Autowired
    private PointService pointS;

    @Autowired
    private ReadMeterDataService rds;

    @Autowired
    private ZoneConfigService zcs;

    @RequestMapping(value = "/queryPipeList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询管线信息接口", notes = "查询管线信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPipeList(@RequestBody PipeDTO pipeDTO) {

        //TODO:参数pipeDTO校验

        JSONObject json = JSONObject.fromObject(pipeDTO);
        //TODO:调用GIS接口获取管线数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }


    @RequestMapping(value = "/queryPipeDet/{pipeID}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询管线详情接口", notes = "查询管线详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPipeDet(@PathVariable("pipeID") String pipeID) {

        //TODO:参数pipeID校验

        //TODO:调用GIS接口获取管线数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }

    @RequestMapping("/importPipe.htm")
    @ApiOperation(value = "导入管线接口", notes = "导入管线接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importPipe(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent ") MultipartFile file) {

        //TODO:Excel数据读取校验完整性，一致性，准确性


        //TODO:调用GIS接口导入管线数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }

    @RequestMapping("/deletePipes/{BatchNo}")
    @ApiOperation(value = "删除某一批次管线数据", notes = "删除某一批次管线数据", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deletePipes(@PathVariable("BatchNo") String BatchNo) {

        //TODO:调用GIS接口删除某一批次数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }

    @RequestMapping(value = "/queryPointList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测点信息接口", notes = "查询监测点信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPointList(@RequestBody PointDTO pointDTO) {


        JSONObject json = JSONObject.fromObject(pointDTO);
        //TODO:调用GIS接口获取管线数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }

    @RequestMapping(value = "/queryPointDet/{P_CODE}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测点详情接口", notes = "查询监测点详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPointDet(@PathVariable("P_CODE") String P_CODE) {

        //TODO:参数P_CODE校验

        //TODO:调用GIS接口获取管线数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }

    @RequestMapping("/importPoint.htm")
    @ApiOperation(value = "导入监测点接口", notes = "导入监测点接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String importPoint(@RequestParam("BatchNo") String BatchNo,@RequestParam("FileContent ") MultipartFile file) {

        //TODO:Excel数据读取校验完整性，一致性，准确性


        //TODO:调用GIS接口导入管线数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }

    @RequestMapping("/deletePoints/{BatchNo}")
    @ApiOperation(value = "删除某一批次监测点数据", notes = "删除某一批次监测点数据", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deletePoints(@PathVariable("BatchNo") String BatchNo) {

        //TODO:调用GIS接口删除某一批次数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }

    @RequestMapping(value = "/queryFacilityList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询附属设施信息接口", notes = "查询附属设施信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryPointList(@RequestBody FacilityDTO facilityDTO) {

        JSONObject json = JSONObject.fromObject(facilityDTO);
        //TODO:调用GIS接口获取附属设施数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }

    @RequestMapping(value = "/queryFacilityDet/{P_CODE}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询附属设施详情接口", notes = "查询附属设施详情接口", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryFacilityDet(@PathVariable("P_CODE") String P_CODE) {

        //TODO:参数P_CODE校验

        //TODO:调用GIS接口获取附属设施数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }


    @RequestMapping("/deleteFacility/{BatchNo}")
    @ApiOperation(value = "删除某一批次附属设施数据", notes = "删除某一批次附属设施数据", httpMethod = "POST", response = MessageBean.class, consumes = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteFacility(@PathVariable("BatchNo") String BatchNo) {

        //TODO:调用GIS接口删除某一批次数据
        JSONObject msg = InterfaceUtil.interfaceUtil(path);

        return msg.toJSONString();
    }



}
