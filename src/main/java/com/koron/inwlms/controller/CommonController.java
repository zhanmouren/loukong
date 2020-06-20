package com.koron.inwlms.controller;

import com.koron.common.StaffAttribute;
import com.koron.inwlms.bean.DTO.common.FileConfigInfo;
import com.koron.inwlms.bean.DTO.common.MapServiceParam;
import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneInfoDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.common.MapServiceData;
import com.koron.inwlms.bean.VO.common.UploadFileVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicNewVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.common.impl.CommonServiceImpl;
import com.koron.inwlms.service.common.impl.FileServiceImpl;
import com.koron.inwlms.service.common.impl.GisZoneServiceImpl;
import com.koron.inwlms.service.common.impl.MapServiceConfigServiceImpl;
import com.koron.inwlms.util.FileUtil;
import com.koron.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.swan.bean.MessageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 通用controller层
 *
 * @author csh
 */
@Controller
@Api(value = "通用模块", description = "通用模块")
@RequestMapping(value = "/{tenantID}/commonController")
@EnableAutoConfiguration
public class CommonController {

	@Autowired
    private FileConfigInfo fileConfigInfo;
    /**
   	 * 上传文件
   	 * 
   	 * @param type
   	 *            上传文件的模块类型
   	 * @param file
   	 */
   	@RequestMapping(value = "/uploadFile.htm", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
   	@ResponseBody
   	public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("tId") Integer tId,@RequestParam("fileModule") String fileModule, HttpServletRequest request,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
   		MessageBean<UploadFileVO> msg = new MessageBean<>();
//   		if(tId == null || "".equals(tId)) {
//   			msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
//			msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR); 
//			return msg.toJson();
//   		}
   		// 获取上传文件名,包含后缀
   		String originalFilename = file.getOriginalFilename();
   		// 获取后缀
   		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
   		
   		//校验文件类型
//   		Map<String, String> map = com.koron.inms.constant.Constant.SUB_DIC_MAP.get(com.koron.inms.constant.Constant.ABNORMAL_FILE_TYPE);
//   		for (Map.Entry<String, String> m : map.entrySet()) {
//   			if(fileType.equals(m.getValue())) {
//   				msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
//   				msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR); 
//   				return msg.toJson();
//   			}
//   	    }
   		// 获取当前月份，时间格式:201910
   		SimpleDateFormat dataFormate = new SimpleDateFormat("yyyyMM");
   		String time = dataFormate.format(new Date());
   		// 保存目录
   		String path = fileConfigInfo.getPath() + File.separator + fileModule + File.separator + time ;
   		// 生成保存文件
   		File dirPath = new File(path);
   		if (!dirPath.exists()) {
   			dirPath.mkdirs();
   		}
   		
   		//用时间戳作为文件名称存储
   		String storeName = System.currentTimeMillis()+fileType;
   		File uploadFile = new File(path+ File.separator + storeName);
   		double size = file.getSize() / 1014;

   		// 将上传文件保存到路径
   		try {
   			file.transferTo(uploadFile);
   		} catch (IOException e) {
   			e.printStackTrace();
   		}

   		String createAccount = user.getLoginName();
   		UploadFileDTO uploadFileDTO = new UploadFileDTO();
   		uploadFileDTO.settId(tId);
   		uploadFileDTO.setFileName(originalFilename);
   		uploadFileDTO.setFilePath(path);
   		uploadFileDTO.setFileSize(size);
   		uploadFileDTO.setModuleType(fileModule);
   		uploadFileDTO.setStoreName(storeName);
   		uploadFileDTO.setStorageTime(new java.util.Date());
   		uploadFileDTO.setCreateBy(createAccount);
   		uploadFileDTO.setFileType(fileType);
   		// 上传文件记录入库
   		int result = ADOConnection.runTask(user.getEnv(),new FileServiceImpl(), "insertFileData", Integer.class, uploadFileDTO);
   		if (result == 1) {
   			UploadFileVO uploadFileVO = new UploadFileVO();
   			uploadFileVO.setFileId(uploadFileDTO.getId());
   			uploadFileVO.setFileName(originalFilename);
   			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
   			msg.setDescription(Constant.MESSAGE_STRING_SUCCESS);
   			msg.setData(uploadFileVO);
   		} else {
   			msg.setCode(Constant.MESSAGE_INT_ADDERROR);
   			msg.setDescription(Constant.MESSAGE_STRING_ADDERROR);
   		}
   		return msg.toJson();
   	}


    /**
     * 下载附件
     *
     * @param fileId
     * @param response
     * @param request
     */
    @RequestMapping(value = "/downloadFileByFileId.htm", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public void downloadFileByFileId(Integer fileId, HttpServletResponse response, HttpServletRequest request,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
        UploadFileDTO data = ADOConnection.runTask(user.getEnv(),new FileServiceImpl(), "getAttachmentInfoById", UploadFileDTO.class, fileId);
        //调用文件工具类下载文件
        if(data != null) FileUtil.downloadFile(data.getFileName(),data.getFilePath()+"/"+data.getStoreName(), response, request);
    }

    @RequestMapping(value = "/getAttachmentBaseInfoById.htm", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据附件id，获取附件的基本信息", notes = "根据附件id，获取附件的基本信息", httpMethod = "GET",response = MessageBean.class,consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAttachmentBaseInfoById(String id,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
    	MessageBean<UploadFileDTO> result = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, UploadFileDTO.class);
    	try{
    		UploadFileDTO data = ADOConnection.runTask(user.getEnv(),new FileServiceImpl(), "getAttachmentInfoById", UploadFileDTO.class, id);
	        result.setData(data);
    	}catch(Exception e){
    		result.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		result.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
        return result.toJson();
    }

    @RequestMapping(value = "/deleteAttachmentInfoById.htm", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据附件id，删除附件的基本信息和附件", notes = "根据附件id，删除附件的基本信息和附件", httpMethod = "POST", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteAttachmentInfoById(@RequestBody UploadFileDTO bean,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
    	MessageBean<UploadFileDTO> result = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, UploadFileDTO.class);
        try{
	    	//删除附件
	        String path = "";
	        if (StringUtils.isNotBlank(bean.getFilePath())) {
	            path = bean.getFilePath();
	        } else {
	            path = ADOConnection.runTask(user.getEnv(),new FileServiceImpl(), "queryFilePath", String.class, bean.getId());
	        }
	        File file = new File(path);
	        if (file.exists()) {
	            file.delete();
	        }
	        //删除附件基本信息
	        ADOConnection.runTask(user.getEnv(),new FileServiceImpl(), "deleteFileById", Integer.class, bean.getId());
        }catch(Exception e){
        	result.setCode(Constant.MESSAGE_INT_DELERROR);
        	result.setDescription(Constant.MESSAGE_STRING_DELERROR);
        }
        return result.toJson();
    }

    
    @RequestMapping(value = "/deleteFile.htm",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除附件",notes = "删除附件",httpMethod = "GET",response = MessageBean.class,consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteFile(Integer fileId,@StaffAttribute(Constant.LOGIN_USER) UserVO user){
    	MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
    	try{
	    	int result = ADOConnection.runTask(user.getEnv(),new FileServiceImpl(),"deleteFileById",Integer.class,fileId);
			if(result == Constant.EXECUTE_SQL_ERROR) {
	    		msg.setCode(Constant.MESSAGE_INT_DELERROR);
	    		msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
	    	}
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_DELERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
    	}
    	return msg.toJson();
    }
    
    public CommonController(FileConfigInfo fileConfigInfo) {
    	this.fileConfigInfo = fileConfigInfo;
    }
    
    /**
     * （临时接口）
	 * 查询指定分区等级的分区编号
	 * @param zoneRank 1-一级分区，2-二级分区
	 * @return 
	 */
	@RequestMapping(value = "/queryZoneNosByRank.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询指定分区等级的分区编号", notes = "查询指定分区等级的分区编号", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneNosByRank(Integer zoneRank,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<List> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, List.class);
		List<String> lists = ADOConnection.runTask(user.getEnv(),new GisZoneServiceImpl(),"queryZoneNosByRank", List.class,zoneRank,"");
		msg.setData(lists);
		return msg.toJson();
	}
	
	/**
	 * （临时接口）
	 * 根据指定的分区编号查询所有子分区编号
	 * @param zoneNo 分区编号
	 * @return 
	 */
	@RequestMapping(value = "/querySubZoneNos.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据指定的分区编号查询所有子分区编号", notes = "根据指定的分区编号查询所有子分区编号", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String querySubZoneNos(String zoneNo,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<List> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, List.class);
		List<String> lists = ADOConnection.runTask(user.getEnv(),new GisZoneServiceImpl(), "querySubZoneNos", List.class,zoneNo);
		msg.setData(lists);
		return msg.toJson();
	}
	
	/**
	 * （临时接口）
	 * 根据指定的分区编号查询所有子分区编号
	 * @param zoneNo 分区编号
	 * @return 
	 */
	@RequestMapping(value = "/queryFuzzyZoneInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "模糊查询分区信息", notes = "模糊查询分区信息", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryFuzzyZoneInfo(@RequestBody QueryZoneInfoDTO queryZoneInfoDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<List> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(queryZoneInfoDTO.getZoneType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区类型为空");
			return msg.toJson();
		}
		if(queryZoneInfoDTO.getZoneType() != null && (queryZoneInfoDTO.getZoneType() < 1 || queryZoneInfoDTO.getZoneType() > 4)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区类型数值错误");
			return msg.toJson();
		}
		try{
			List<ZoneInfo> data = ADOConnection.runTask(user.getEnv(),new GisZoneServiceImpl(), "queryFuzzyZoneInfo", List.class,queryZoneInfoDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	 /*
     * date:2020-05-06
     * funtion:查询所有数据字典接口
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryAllDataDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询所有数据字典接口", notes = "查询所有数据字典接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryAllDataDic(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	 
		 //执行查询数据字典
		 try {
			 List<DataDicNewVO> dicList=ADOConnection.runTask(user.getEnv(),new CommonServiceImpl(), "queryAllDataDic", List.class, dataDicDTO);
			 if(dicList.size()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关数据字典键值的信息"); 
			     msg.setData(dicList);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("没有查询到该数据字典键值的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询数据字典键值失败");
	     }
		 return msg.toJson();
		 
	}
	
	@RequestMapping(value = "/queryMapService.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询地图服务配置", notes = "查询地图服务配置", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMapService(@StaffAttribute(Constant.LOGIN_USER) UserVO user,@PathVariable("tenantID") String tenantID,@RequestBody MapServiceParam mapServiceParam) {
		MessageBean<MapServiceData> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, MapServiceData.class);	
		try {
			MapServiceData mapServiceData = ADOConnection.runTask(user.getEnv(),new MapServiceConfigServiceImpl(), "queryMapServiceConfig", MapServiceData.class,tenantID,mapServiceParam.getModule());
			msg.setData(mapServiceData);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询失败");
		}
		
		
		return msg.toJson();
	}
	
	
//	@RequestMapping(value = "/addZoneTreeInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
//    @ApiOperation(value = "添加分区树", notes = "添加分区树", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
//    @ResponseBody
//	public String addZoneTreeInfo() {
//		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	 
//		 try {
//			 ADOConnection.runTask("mz",new CommonServiceImpl(), "addZoneTreeInfo",Integer.class);
//		 }catch(Exception e){
//	     	//添加失败
//	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
//	        msg.setDescription("添加失败");
//	     }
//		 return msg.toJson();
//		 
//	}
}
