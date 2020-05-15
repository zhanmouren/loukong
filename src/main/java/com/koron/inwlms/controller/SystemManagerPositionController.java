package com.koron.inwlms.controller;

import java.util.List;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swan.bean.MessageBean;

import com.koron.inwlms.bean.DTO.sysManager.PositionDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.PositionVO;
import com.koron.inwlms.service.sysManager.PositionService;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 职位管理Controller层
 * @author lzy
 * @Date 2020/05/12
 */

@RestController
@Api(value = "systemManagerPosition",description = "职位管理Controller层")
@RequestMapping(value = "/systemManagerPosition")
public class SystemManagerPositionController {
	
	@Autowired
	private PositionService positionService;
	
	@RequestMapping(value = "/queryPosition.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "查询职位接口",notes = "查询职位接口",httpMethod  = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryPosition(@RequestBody PositionDTO positionDTO) {
		if(positionDTO.getPage() == null || positionDTO.getPage() <0 || positionDTO.getPage() == 0) {
			positionDTO.setPage(1);
		}
		if(positionDTO.getPageCount() == null || positionDTO.getPageCount() <0 || positionDTO.getPageCount() ==0) {
			positionDTO.setPageCount(20);
		}
		MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		//执行查询登录日志 
		try {
			PageListVO positionList=ADOConnection.runTask(positionService, "queryPosition", PageListVO.class, positionDTO);
			if(positionList != null && positionList.getRowNumber() > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("查询到相关职位的信息"); 
				msg.setData(positionList);
			}else {
				//没查询到数据
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("没有查询到相关职位的信息"); 
			}
		}catch(Exception e){
			//查询失败
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("查询职位失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryPositionDetail.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "查询职位详情接口",notes = "查询职位详情接口",httpMethod  = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryPositionDetail(@RequestBody PositionDTO positionDTO) {
		if(positionDTO.getCode() == null || positionDTO.getCode().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!code不能为空", Integer.class).toJson();
		}
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		//执行查询登录日志 
		try {
			List<PositionVO> positionList=ADOConnection.runTask(positionService, "queryPositionDetail", List.class, positionDTO);
			if(positionList.size() > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("查询到相关职位的信息"); 
				msg.setData(positionList);
			}else {
				//没查询到数据
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("没有查询到相关职位的信息"); 
			}
		}catch(Exception e){
			//查询失败
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("查询职位失败");
		}
		return msg.toJson();
	}
}
