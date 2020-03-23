package com.koron.inwlms.controller;

import com.koron.common.web.util.StaffAttribute;
import com.koron.inwlms.bean.DTO.common.FileConfigInfo;
import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.VO.common.UploadFileVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.common.impl.FileServiceImpl;
import com.koron.inwlms.util.FileUtil;
import com.koron.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
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

/**
 * 通用controller层
 *
 * @author csh
 */
@Controller
@Api(value = "通用模块", description = "通用模块")
@RequestMapping(value = "/commonController")
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
   		int result = ADOConnection.runTask(new FileServiceImpl(), "insertFileData", Integer.class, uploadFileDTO);
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
    public void downloadFileByFileId(String fileId, HttpServletResponse response, HttpServletRequest request) {
        UploadFileDTO data = ADOConnection.runTask(new FileServiceImpl(), "getAttachmentInfoById", UploadFileDTO.class, fileId);
        //调用文件工具类下载文件
        if(data != null) FileUtil.downloadFile(data.getFileName(),data.getFilePath()+"/"+data.getStoreName(), response, request);
    }

    @RequestMapping(value = "/getAttachmentBaseInfoById.htm", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "根据附件id，获取附件的基本信息", notes = "根据附件id，获取附件的基本信息", httpMethod = "GET",response = MessageBean.class,consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getAttachmentBaseInfoById(String id) {
    	MessageBean<UploadFileDTO> result = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, UploadFileDTO.class);
    	try{
    		UploadFileDTO data = ADOConnection.runTask(new FileServiceImpl(), "getAttachmentInfoById", UploadFileDTO.class, id);
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
    public String deleteAttachmentInfoById(@RequestBody UploadFileDTO bean) {
    	MessageBean<UploadFileDTO> result = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, UploadFileDTO.class);
        try{
	    	//删除附件
	        String path = "";
	        if (StringUtils.isNotBlank(bean.getFilePath())) {
	            path = bean.getFilePath();
	        } else {
	            path = ADOConnection.runTask(new FileServiceImpl(), "queryFilePath", String.class, bean.getId());
	        }
	        File file = new File(path);
	        if (file.exists()) {
	            file.delete();
	        }
	        //删除附件基本信息
	        ADOConnection.runTask(new FileServiceImpl(), "deleteFileById", Integer.class, bean.getId());
        }catch(Exception e){
        	result.setCode(Constant.MESSAGE_INT_DELERROR);
        	result.setDescription(Constant.MESSAGE_STRING_DELERROR);
        }
        return result.toJson();
    }

    
    @RequestMapping(value = "/deleteFile.htm",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除附件",notes = "删除附件",httpMethod = "GET",response = MessageBean.class,consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteFile(String fileId){
    	MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_DELSUCCESS, String.class);
    	try{
	    	int result = ADOConnection.runTask(new FileServiceImpl(),"deleteFileById",Integer.class,fileId);
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
    
}
