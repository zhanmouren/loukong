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

import com.koron.common.StaffAttribute;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVSZoneListDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.bean.VO.zoneLoss.VirtualZoneVO;
import com.koron.inwlms.service.common.CommonLoginService;
import com.koron.inwlms.service.zoneLoss.VirtualZoneService;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 虚拟分区controller层
 *
 * @author lzy
 */
@RestController
@Api(value = "通用模块", description = "通用模块")
@RequestMapping(value = "/{tenantID}/commonController")
public class VirtualCommonController {

	@Autowired
	private VirtualZoneService virtualZoneService;
	
	/*
     * date:2020-03-19
     * funtion:查询虚拟分区接口
     * author:lzy
     */
	@RequestMapping(value = "/queryVirtualZone.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询虚拟分区接口", notes = "查询虚拟分区接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryVirtualZone(@RequestBody QueryVSZoneListDTO queryVSZoneListDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(queryVSZoneListDTO.getVirtualZoneType()== null || queryVSZoneListDTO.getVirtualZoneType().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!虚拟分区类型不能为空", Integer.class).toJson();
		} 
		
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		 try {
			 List<VirtualZoneVO> result=ADOConnection.runTask(user.getEnv(),virtualZoneService, "queryVirtualZone", List.class, queryVSZoneListDTO);
			 if(result.size()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关虚拟分区"); 
			     msg.setData(result);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("没有查询到相关虚拟分区"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询虚拟分区失败");
	     }
		 return msg.toJson();
		 
	}
}
