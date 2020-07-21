package com.koron.inwlms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "StatisticalReportController", description = "统计报表Controller")
@RequestMapping(value = "/{tenantID}/StatisticalReportController")
public class StatisticalReportController {
	
	/**
	 * 分区最小夜间流量报表
	 */
	@RequestMapping(value = "/queryZoneMnf.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "分区最小夜间流量报表", notes = "分区最小夜间流量报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMnf() {
		return null;
	}
	
	/**
	 * 分区最小夜间流量统计报表
	 * @return
	 */
	@RequestMapping(value = "/queryZoneMnfStatistical.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "分区最小夜间流量统计报表", notes = "分区最小夜间流量统计报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMnfStatistical() {
		return null;
	}

}
