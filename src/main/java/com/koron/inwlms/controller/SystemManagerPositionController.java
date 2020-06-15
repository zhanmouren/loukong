package com.koron.inwlms.controller;

import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swan.bean.MessageBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.common.StaffAttribute;
import com.koron.inwlms.bean.DTO.sysManager.PositionDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryLabelDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.PageLabelListVO;
import com.koron.inwlms.bean.VO.sysManager.PagePositionListVO;
import com.koron.inwlms.bean.VO.sysManager.PositionVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.sysManager.PositionService;
import com.koron.inwlms.util.ExportDataUtil;
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
@RequestMapping(value = "/{tenantID}/systemManagerPosition")
public class SystemManagerPositionController {
	
	@Autowired
	private PositionService positionService;
	
	@RequestMapping(value = "/queryPosition.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "查询职位接口",notes = "查询职位接口",httpMethod  = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryPosition(@RequestBody PositionDTO positionDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		if(positionDTO.getPage() == null || positionDTO.getPage() <0 || positionDTO.getPage() == 0) {
			positionDTO.setPage(1);
		}
		if(positionDTO.getPageCount() == null || positionDTO.getPageCount() <0 || positionDTO.getPageCount() ==0) {
			positionDTO.setPageCount(20);
		}
		MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
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
	public String queryPositionDetail(@RequestBody PositionDTO positionDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		if(positionDTO.getCode() == null || positionDTO.getCode().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!code不能为空", Integer.class).toJson();
		}
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
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
	
	@RequestMapping(value = "/deletePosition.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "删除职位接口",notes = "删除职位接口",httpMethod  = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8")
	@ResponseBody
	public String deletePosition(@RequestBody PositionDTO positionDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		if(positionDTO.getCode() == null || positionDTO.getCode().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!code不能为空", Integer.class).toJson();
		}
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		try {
			Integer delResult = ADOConnection.runTask(positionService, "deletePosition", Integer.class, positionDTO);
			if(delResult != null) {
				if(delResult == 1) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("删除职位成功"); 
				}else {
					//没查询到数据
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("没有该职位"); 
				}	
			}
			
		}catch(Exception e){
			//查询失败
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("删除职位失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/addPosition.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "添加职位接口",notes = "添加职位接口",httpMethod  = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8")
	@ResponseBody
	public String addPosition(@RequestBody PositionDTO positionDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<?>  insertRes = ADOConnection.runTask(positionService, "addPosition",MessageBean.class,positionDTO);
		return insertRes.toJson();
	}
	
	@RequestMapping(value = "/updatePosition.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "修改职位接口",notes = "修改职位接口",httpMethod  = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8")
	@ResponseBody
	public String updatePosition(@RequestBody PositionDTO positionDTO) {
		if(positionDTO.getCode() == null || positionDTO.getCode().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!code不能为空", Integer.class).toJson();
		}
		MessageBean<?>  insertRes = ADOConnection.runTask(positionService, "updatePosition",MessageBean.class,positionDTO);
		return insertRes.toJson();
	}
	
  
	@RequestMapping(value = "/downloadAllList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载职位列表数据", notes = "下载职位列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadAllList(@RequestParam String objValue,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			PositionDTO positionDTO = jsonValue.fromJson(objValue, PositionDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (positionDTO  == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			positionDTO.setPage(1);
			positionDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<PositionVO>> pageBean = ADOConnection.runTask(positionService, "queryPosition", PageListVO.class,positionDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(pageBean.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
