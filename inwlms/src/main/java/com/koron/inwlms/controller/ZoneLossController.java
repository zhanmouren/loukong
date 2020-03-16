package com.koron.inwlms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 分区漏损Controller层
 * @author csh
 * @Date 2020.03.09
 */
@Controller
@Api(value = "ZoneLossController", description = "分区漏损Controller")
@RequestMapping(value = "/ZoneLossController")
public class ZoneLossController {

	@RequestMapping(value = "/queryZoneWBLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区水平衡漏损数据", notes = "查询分区水平衡漏损数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneWBLossList() {
		return null;
	}
	
	@RequestMapping(value = "/queryWNWBReportList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网水平衡报表列表", notes = "查询全网水平衡报表列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNWBReportList() {
		return null;
	}
	
	@RequestMapping(value = "/downloadWNWBReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载全网水平衡报表", notes = "下载全网水平衡报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadWNWBReport() {
		return null;
	}
	
	@RequestMapping(value = "/deleteWNWBReportList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除全网水平衡报表", notes = "删除全网水平衡报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteWNWBReportList() {
		return null;
	}
	
	@RequestMapping(value = "/downloadWNWBReportList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载全网水平衡报表列表", notes = "下载全网水平衡报表列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadWNWBReportList() {
		return null;
	}
	
	@RequestMapping(value = "/addWNWBReportList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加全网水平衡报表", notes = "添加全网水平衡报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addWNWBReportList() {
		return null;
	}
	
	@RequestMapping(value = "/updateWNWBReportList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "编辑全网水平衡报表", notes = "编辑全网水平衡报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateWNWBReportList() {
		return null;
	}
	
	@RequestMapping(value = "/queryWNWBReporFiletList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网水平衡报表附件列表", notes = "查查询全网水平衡报表附件列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNWBReporFiletList() {
		return null;
	}
	
	@RequestMapping(value = "/uploadWNWBReporFile.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "上传全网水平衡报表附件", notes = "上传全网水平衡报表附件", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String uploadWNWBReporFile() {
		return null;
	}
	
	@RequestMapping(value = "/deleteWNWBReporFile.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除全网水平衡报表附件", notes = "删除全网水平衡报表附件", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteWNWBReporFile() {
		return null;
	}
	
	@RequestMapping(value = "/queryWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网水平衡模板报表列表", notes = "查询全网水平衡模板报表列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNWBReporTemplate() {
		return null;
	}
	
	@RequestMapping(value = "/downloadWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载全网水平衡模板报表列表", notes = "下载全网水平衡模板报表列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadWNWBReporTemplate() {
		return null;
	}
	
	@RequestMapping(value = "/deleteWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除全网水平衡模板报表", notes = "删除全网水平衡模板报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteWNWBReporTemplate() {
		return null;
	}
	
	@RequestMapping(value = "/addWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加全网水平衡模板报表", notes = "添加全网水平衡模板报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addWNWBReporTemplate() {
		return null;
	}
	
	@RequestMapping(value = "/updateWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "编辑全网水平衡模板报表", notes = "编辑全网水平衡模板报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateWNWBReporTemplate() {
		return null;
	}
	
	@RequestMapping(value = "/queryFZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询一级分区漏损分析列表", notes = "查询一级分区漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryFZoneLossList() {
		return null;
	}
	
	@RequestMapping(value = "/downloadFZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载一级分区漏损分析列表", notes = "下载一级分区漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadFZoneLossList() {
		return null;
	}
	
	@RequestMapping(value = "/querySZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询二级分区漏损分析列表", notes = "查询二级分区漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String querySZoneLossList() {
		return null;
	}
	
	@RequestMapping(value = "/downloadSZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载二级分区漏损分析列表", notes = "下载二级分区漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadSZoneLossList() {
		return null;
	}
	
	@RequestMapping(value = "/queryDmaZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询DMA漏损分析列表", notes = "查询DMA漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDmaZoneLossList() {
		return null;
	}
	
	@RequestMapping(value = "/downloadDmaZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载DMA漏损分析列表", notes = "下载DMA漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadDmaZoneLossList() {
		return null;
	}
	
	@RequestMapping(value = "/queryZonelocation.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区地图定位信息", notes = "查询分区地图定位信息", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZonelocation() {
		return null;
	}
	
	@RequestMapping(value = "/queryZonedetail.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区详情", notes = "查询分区详情", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZonedetail() {
		return null;
	}
	
	@RequestMapping(value = "/queryZoneHstData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区漏损历史数据", notes = "查询分区漏损历史数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneHstData() {
		return null;
	}
	
	@RequestMapping(value = "/queryZoneInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "模糊查询分区信息", notes = "模糊查询分区信息", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneInfo() {
		return null;
	}
	
	@RequestMapping(value = "/queryVSZoneList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询虚拟分区（相减）列表", notes = "查询虚拟分区（相减）列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryVSZoneList() {
		return null;
	}
	
	@RequestMapping(value = "/downloadVSZoneHstList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载虚拟分区（相减）列表", notes = "下载虚拟分区（相减）列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadVSZoneHstList() {
		return null;
	}
	
	@RequestMapping(value = "/queryVCZoneList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询拟分区（合并）列表", notes = "查询拟分区（合并）列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryVCZoneList() {
		return null;
	}
	
	@RequestMapping(value = "/downloadVCZoneList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载拟分区（合并）列表", notes = "下载拟分区（合并）列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadVCZoneList() {
		return null;
	}
	
	@RequestMapping(value = "/deleteVCZone.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除虚拟分区（合并）", notes = "删除虚拟分区（合并）", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteVCZone() {
		return null;
	}
	
	@RequestMapping(value = "/queryWNLeakMapList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网专题图列表", notes = "查询全网专题图列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNLeakMapList() {
		return null;
	}
	
	@RequestMapping(value = "/queryZoneIndicatorList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区指标数据", notes = "查询分区指标数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneIndicatorList() {
		return null;
	}
	
	@RequestMapping(value = "/downloadZoneIndicatorList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载分区指标数据", notes = "下载分区指标数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String downloadZoneIndicatorList() {
		return null;
	}
}
