package com.koron.inwlms.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swan.bean.MessageBean;

import com.koron.common.StaffAttribute;
import com.koron.common.permission.SPIAccountAnno;
import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.report.waterBalanceReport.WB1BalanceDTO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.WB1BalanceVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.report.waterReport.WaterReportService;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 统计报表-水平衡报表controller
 * @author xiaozhan
 * @Date 2020.07.22
 */
@RestController
@Api(value = "report", description = "水平衡报表管理Controller")
@RequestMapping(value = "/{tenantID}/report")
public class WaterReportController {
	
	@Autowired
	private WaterReportService waterReportService;
	 /*
     * date:2020-07.22
     * function:报表：(WB_01)水司及一级分区产销差率同比报表      以月为时间间隔,汇总分析所选运作区或全网在指定时间范围内用水量、产销差和未计量食水用水量。
     * author:xiaozhan
     */

	@RequestMapping(value = "/queryPartitionData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "水司及一级分区产销差率同比报表", notes = "水司及一级分区产销差率同比报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryPartitionData(@RequestBody IndicatorNewDTO indicatorNewDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(indicatorNewDTO.getStartTime()==null || "".equals(indicatorNewDTO.getStartTime())) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "开始时间不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getEndTime()==null || "".equals(indicatorNewDTO.getEndTime())) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "结束时间不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getZoneCodes()==null) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "指标编码不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getZoneCodes().size()<1) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "指标编码不能为空", Integer.class).toJson();
		}
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       		
		  try{
			  List<WB1BalanceVO> WB1BalanceVOList=ADOConnection.runTask(user.getEnv(),waterReportService, "queryPartitionData", List.class, indicatorNewDTO);		 
				  if(WB1BalanceVOList!=null && WB1BalanceVOList.size()>0) {			
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询水司及一级分区产销差率同比信息成功");
				    msg.setData(WB1BalanceVOList);
				  }
				  else {				   
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("未查询到水司及一级分区产销差率同比信息");
				  }
			  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
}
