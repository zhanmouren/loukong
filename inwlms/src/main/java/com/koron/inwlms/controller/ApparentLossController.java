package com.koron.inwlms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import com.koron.inwlms.bean.DTO.TestBean;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 表观漏损Controller层
 * @author csh
 * @Date 2020.03.09
 */
@Controller
@Api(value = "ApparentLossController", description = "表观漏损Controller")
@RequestMapping(value = "/ApparentLossController")
public class ApparentLossController {

	@RequestMapping(value = "/queryALOverviewData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询表观漏损数据总览接口", notes = "查询表观漏损数据总览接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryALOverviewData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryALList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询表观漏损数据列表", notes = "查询表观漏损数据列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryALList(@RequestBody QueryALListDTO queryALListDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryALMapData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询表观漏损图表数据", notes = "查询表观漏损图表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryALMapData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/downloadALList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载表观漏损列表数据", notes = "下载表观漏损列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadALList(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryMeterRunAnalysisList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询水表运行分析列表数据", notes = "查询水表运行分析列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryMeterRunAnalysisList(@RequestBody QueryALListDTO queryALListDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryMeterRunAnalysisMapData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询水表运行分析列表数据", notes = "查询水表运行分析列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryMeterRunAnalysisMapData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/downloadMeterRunAnalysisList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载水表运行分析列表数据", notes = "下载水表运行分析列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadMeterRunAnalysisList(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryDrTotalAnalysisData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告总体分析数据", notes = "查询诊断报告总体分析数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrTotalAnalysisData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryDrCurrentMeterData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告现状水表数据", notes = "查询诊断报告现状水表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrCurrentMeterData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryDrMeterManageData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告表计管理数据", notes = "查询诊断报告表计管理数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrMeterManageData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryDrMeterRunAnalysisData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告水表运行分析数据", notes = "查询诊断报告水表运行分析数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrMeterRunAnalysisData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryDrDealAdvise.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告处理建议数据", notes = "查询诊断报告处理建议数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrDealAdvise(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
}
