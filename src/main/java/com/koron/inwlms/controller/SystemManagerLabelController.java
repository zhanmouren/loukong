package com.koron.inwlms.controller;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.swan.bean.MessageBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.sysManager.LabelDTO;
import com.koron.inwlms.bean.DTO.sysManager.LabelExcelBean;
import com.koron.inwlms.bean.DTO.sysManager.QueryLabelDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.LabelVO;
import com.koron.inwlms.bean.VO.sysManager.PageLabelListVO;
import com.koron.inwlms.service.sysManager.LabelService;
import com.koron.inwlms.util.ExportDataUtil;
import com.koron.inwlms.util.FileUtil;
import com.koron.inwlms.util.ImportExcelUtil;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 标签维护Controller层
 * @author lzy
 * @Date 2020.04.17
 */

@RestController
@Api(value = "systemManagerLabel",description = "标签维护Controller")
@RequestMapping(value = "/systemManagerLabel")
public class SystemManagerLabelController {

	//TODO 权限
	@Autowired
	private LabelService labelSerivce;
	
	
	/*
	 * 查询标签接口
     * date:2020-04-17
     */  
	@RequestMapping(value = "queryLabel.htm",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "查询标签接口", notes = "查询标签接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryLabel(@RequestBody QueryLabelDTO queryLabelDTO) {
		if(queryLabelDTO.getPage() == null || queryLabelDTO.getPage() <0 || queryLabelDTO.getPage() == 0) {
			queryLabelDTO.setPage(1);
		}
		if(queryLabelDTO.getPageCount() == null || queryLabelDTO.getPageCount() <0 || queryLabelDTO.getPageCount() ==0) {
			queryLabelDTO.setPageCount(20);
		}
		if(queryLabelDTO.getStartTime() == null || queryLabelDTO.getStartTime().equals("")) {
	        return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间不能为空", Integer.class).toJson();
		}else {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try{
				formatter.parse(queryLabelDTO.getStartTime());
			}catch(Exception e){
				return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间格式不是yyyy-mm-dd", Integer.class).toJson();
			}
		}
		if(queryLabelDTO.getEndTime() == null || queryLabelDTO.getEndTime().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!结束时间不能为空", Integer.class).toJson();
		}else {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try{
				formatter.parse(queryLabelDTO.getEndTime());
			}catch(Exception e){
				return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!结束时间格式不是yyyy-mm-dd", Integer.class).toJson();
			}
		}
		int res = queryLabelDTO.getStartTime().compareTo(queryLabelDTO.getEndTime());
		if(res > 0) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间大于结束时间", Integer.class).toJson();
		}
		
		if(queryLabelDTO.getType()==null || StringUtils.isBlank(queryLabelDTO.getType())) {
			queryLabelDTO.setType(null);
		}else if(queryLabelDTO.getType().equals("全部")) {
			queryLabelDTO.setType(null);
		}
		MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		//执行查询标签
		 try {
			 PageListVO labelList=ADOConnection.runTask(labelSerivce, "queryLabel", PageListVO.class, queryLabelDTO);
			 if(labelList != null && labelList.getRowNumber() > 0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到标签的信息"); 
			     msg.setData(labelList);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("无该标签的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询标签失败");
	     }
		 return msg.toJson();
	}
	
	/*
     * 添加标签接口
     * date:2020-04-17
     */  
	@RequestMapping(value = "/addLabel.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "添加标签接口",notes = "添加标签接口", httpMethod = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addLabel(@RequestBody LabelDTO labelDTO) {
		
		if(labelDTO.getCode() == null || StringUtils.isBlank(labelDTO.getCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "标签编码code不能为空", Integer.class).toJson();
		}
		MessageBean<?>  insertRes = ADOConnection.runTask(labelSerivce, "addLabel",MessageBean.class,labelDTO);
		return insertRes.toJson();
	}

	/*
     * 删除标签接口
     * date:2020-03-23
     */  	
	@RequestMapping(value = "/deleteLabel.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除标签接口", notes = "删除标签接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String  deleteLabel(@RequestBody LabelDTO labelDTO) {
		if(labelDTO.getCode() == null || StringUtils.isBlank(labelDTO.getCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "标签编码code不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行删除标签的操作
		  try{
			  Integer delRes=ADOConnection.runTask(labelSerivce, "deleteLabel", Integer.class, labelDTO);		 
			  if(delRes!=null) {
				  if(delRes==1) {
					//删除标签成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("删除标签成功");
				  }else {
				    //删除标签失败
			        msg.setCode(Constant.MESSAGE_INT_DELERROR);
			        msg.setDescription("无该标签");
				  }
			  }
	        }catch(Exception e){
	        	//删除标签失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除标签失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * 批量删除标签接口
     * date:2020-04-17
     */  	
	@RequestMapping(value = "/deleteBatchLabel.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "批量删除标签接口", notes = "批量删除标签接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String  deleteBatchLabel(@RequestBody LabelDTO labelDTO) {
		if(labelDTO.getLabelCodeList() == null ) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "标签列表参数不能为空", Integer.class).toJson();
		}
		if(labelDTO.getLabelCodeList().size() < 1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "标签列表code不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行批量删除标签的操作
		  try{
			  Integer delRes=ADOConnection.runTask(labelSerivce, "deleteBatchLabel", Integer.class, labelDTO);		 
			  if(delRes!=null) {
				  if(delRes==1) {
					//批量删除标签成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("批量删除标签成功");
				  }else {
				    //批量删除标签失败
			        msg.setCode(Constant.MESSAGE_INT_DELERROR);
			        msg.setDescription("批量删除标签失败");
				  }
			  }
	        }catch(Exception e){
	        	//批量删除标签失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("批量删除标签失败");
	        }
		
	     return msg.toJson();
	}
	
	
	/*
     * 修改标签接口
     * date:2020-04-20
     */  	
	@RequestMapping(value = "/updateLabel.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改标签接口", notes = "修改标签接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateLabel(@RequestBody LabelDTO labelDTO) {
		if(labelDTO.getId() == null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "标签id不能为空", Integer.class).toJson();
		}
		if(labelDTO.getCode() == null || StringUtils.isBlank(labelDTO.getCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "标签编码code不能为空", Integer.class).toJson();
		}
		MessageBean<?>  updateRes = ADOConnection.runTask(labelSerivce, "updateLabel",MessageBean.class,labelDTO);
		return updateRes.toJson();
	}
	
	/*
     * 下载标签列表模板接口
     * date:2020-04-20
     */  
	@RequestMapping(value = "/downloadFileByFileId.htm", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public void downloadFileByFileId(Integer fileId, HttpServletResponse response, HttpServletRequest request) {
        UploadFileDTO data = ADOConnection.runTask(labelSerivce, "getAttachmentInfoById", UploadFileDTO.class, fileId);
        //调用文件工具类下载文件
        if(data != null) FileUtil.downloadFile(data.getFileName(),data.getFilePath()+"/"+data.getStoreName(), response, request);
    }

	

	/*
     * 下载标签列表数据接口
     * date:2020-04-20
     */  
	@RequestMapping(value = "/downloadAllList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载标签列表数据", notes = "下载标签列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadAllList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryLabelDTO queryLabelDTO = jsonValue.fromJson(objValue, QueryLabelDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (queryLabelDTO  == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			queryLabelDTO.setPage(1);
			queryLabelDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageLabelListVO pageBean = ADOConnection.runTask(labelSerivce, "queryAllList", PageLabelListVO.class,queryLabelDTO);
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
	
	
	/*
     * 批量导入标签接口
     * date:2020-04-21
     */
	@RequestMapping(value = "/uploadBatchLabel.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "批量导入标签接口", notes = "批量导入标签接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String uploadBatchLabel(MultipartFile file) {
		//1.先判断导入的excel格式信息正不正确
        //2.如果正确则excelBeans中数据不为null，然后 执行批量新增操作
        //3.如果不正确则excelBeans中数据为null，返回错误
        MessageBean<?> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
        List<LabelExcelBean> excelBeans = ImportExcelUtil.readExcel(file, LabelExcelBean.class);
        if (excelBeans == null) {
            msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
            msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
        } else {
            try {
                msg = ADOConnection.runTask(labelSerivce, "uploadBatchLabel", MessageBean.class, excelBeans);

            } catch (Exception e) {
                msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
            }
        }
        return msg.toJson();
	}
	
}
