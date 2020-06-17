package com.koron.permission.controller;

import java.util.List;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swan.bean.MessageBean;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.StaffAttribute;
import com.koron.common.bean.StaffBean;
import com.koron.common.permission.SPIAccountAnno;
import com.koron.common.web.service.TreeService;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.permission.authority.DataInject;
import com.koron.permission.authority.DataRangeMethod;
import com.koron.permission.authority.OPSPIMethod;
import com.koron.permission.bean.DTO.TblAppCatalogueDTO;
import com.koron.permission.bean.DTO.TblAppDTO;
import com.koron.permission.bean.DTO.TblAppOPDTO;
import com.koron.permission.bean.DTO.TblOpCodeListDTO;
import com.koron.permission.bean.DTO.TblOpDTO;
import com.koron.permission.bean.DTO.TblOperationDTO;
import com.koron.permission.bean.DTO.TblOrgRoleDTO;
import com.koron.permission.bean.DTO.TblRoleDTO;
import com.koron.permission.bean.DTO.TblRoleOpDTO;
import com.koron.permission.bean.DTO.TblRoleRangeValueDTO;
import com.koron.permission.bean.DTO.TblRoleUserDTO;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.TblRoleRangeValueListVO;
import com.koron.permission.service.PermissionService;
import com.koron.util.Constant;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 权限控制Controller层
 * @author xiaozhan
 * @Date 2020.05.28
 */

@RestController
@Api(value = "permissionController", description = "权限控制Controller")
@RequestMapping(value = "/permissionController")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 登录用户在SESSION里的储存KEY
	 */
	public static final String USER = "_user";
	
	/**
	 * 参数校验异常码
	 */
	public static final int MESSAGE_INT_PARAMS = 20108;
	/**
	 * 操作成功码
	 */
	public static final int MESSAGE_INT_SUCCESS = 0;
	/**
	 * 操作成功提示
	 */
	public static final String MESSAGE_STRING_SUCCESS = "操作成功";
	
	/**
	 * 操作失败码
	 */
	public static final int MESSAGE_INT_ERROR = 1;
	/**
	 * 添加失败码
	 */
	public static final int MESSAGE_INT_ADDERROR = 20102;
	
	/**
	 * 修改失败码
	 */
	public static final int MESSAGE_INT_EDITERROR = 20103;
	
	/**
	 * 删除失败码
	 */
	public static final int MESSAGE_INT_DELERROR = 20104;
	
	/**
	 * 登录失败码
	 */
	public static final int MESSAGE_INT_LOGINERROR = 20105;
	/**
	 * 查询失败码
	 */
	public static final int MESSAGE_INT_SELECTERROR = 20107;
		
	/*
	 * @author xiaozhan
	 * @Date 2020.06.01
	 * description:添加应用信息
	 */
	@OPSPIMethod("op001")
	@RequestMapping(value = "/addApp.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加应用接口", notes = "添加应用接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addApp(@RequestBody TblAppDTO tblAppDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(tblAppDTO.getAppName()==null || "".equals(tblAppDTO.getAppName())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用名称不能为空", Integer.class).toJson();
		}
		if(tblAppDTO.getAppStatus()==null || "".equals(tblAppDTO.getAppStatus())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用状态不能为空", Integer.class).toJson();
		}
		if(tblAppDTO.getAppWeight()==null || "".equals(tblAppDTO.getAppWeight())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用权重不能为空", Integer.class).toJson();
		}
		if(tblAppDTO.getAppCode()==null || "".equals(tblAppDTO.getAppCode())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用code不能为空", Integer.class).toJson();
		}
		tblAppDTO.setCreator(user.getLoginName());
		tblAppDTO.setModifier(user.getLoginName());
		MessageBean<?> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	
		  try{
			  Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "addApp", Integer.class,tblAppDTO);	
			  if(addRes==null) {
		        msg.setCode(MESSAGE_INT_ADDERROR);
				msg.setDescription("添加应用失败");    
			  }else if(addRes==-1) {
			    msg.setCode(MESSAGE_INT_ADDERROR);
			    msg.setDescription("添加应用失败");  
			  }else if(addRes==-2){
				msg.setCode(MESSAGE_INT_ADDERROR);
				msg.setDescription("应用编码重复");   
			  }else {
			   msg.setCode(MESSAGE_INT_SUCCESS);
			   msg.setDescription("添加应用成功");
			  }
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("添加失败");
	        }		
	     return msg.toJson();
	}
	
	/*
	 * @author xiaozhan
	 * @Date 2020.06.01
	 * description:修改应用信息
	 */
	@OPSPIMethod("op001")
	@RequestMapping(value = "/updateApp.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改应用接口", notes = "修改应用接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateApp(@RequestBody TblAppDTO tblAppDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(tblAppDTO.getAppCode()==null || "".equals(tblAppDTO.getAppCode())) {			
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用编码不能为空", Integer.class).toJson();
		}
		if(tblAppDTO.getAppName()==null || "".equals(tblAppDTO.getAppName())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用名称不能为空", Integer.class).toJson();
		}
		if(tblAppDTO.getAppStatus()==null || "".equals(tblAppDTO.getAppStatus())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用状态不能为空", Integer.class).toJson();
		}
		if(tblAppDTO.getAppWeight()==null || "".equals(tblAppDTO.getAppWeight())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用权重不能为空", Integer.class).toJson();
		}
		tblAppDTO.setCreator(user.getLoginName());
		tblAppDTO.setModifier(user.getLoginName());
		MessageBean<?> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	
		  try{
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),permissionService, "updateApp", Integer.class,tblAppDTO);
			  if(updateRes==null) {
			     msg.setCode(MESSAGE_INT_ADDERROR);
				 msg.setDescription("修改应用失败");    
			 }else if(updateRes==-1) {
			     msg.setCode(MESSAGE_INT_ADDERROR);
				 msg.setDescription("修改应用失败");  
			 }else {
				 msg.setCode(MESSAGE_INT_SUCCESS);
				 msg.setDescription("修改应用成功");
			  }
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("修改失败");
	        }		
	     return msg.toJson();
	}
	
	/*
	 * @author xiaozhan
	 * @Date 2020.06.01
	 * description:删除应用信息
	 */
	@OPSPIMethod("op001")
	@RequestMapping(value = "/deleteApp.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除应用接口", notes = "删除应用接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteApp(@RequestBody TblAppDTO tblAppDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(tblAppDTO.getAppCode()==null || "".equals(tblAppDTO.getAppCode())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用编码不能为空", Integer.class).toJson();
		}
		MessageBean<?> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	
		  try{
			  Integer delRes=ADOConnection.runTask(user.getEnv(),permissionService, "deleteApp", Integer.class,tblAppDTO);
			  if(delRes==null) {
			     msg.setCode(MESSAGE_INT_ADDERROR);
				 msg.setDescription("删除应用失败");    
			 }else if(delRes==-1) {
			     msg.setCode(MESSAGE_INT_ADDERROR);
				 msg.setDescription("删除应用失败");  
			 }else {
				 msg.setCode(MESSAGE_INT_SUCCESS);
				 msg.setDescription("删除应用成功");
			  }
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("删除失败");
	        }		
	     return msg.toJson();
	}
	
	
	/*
	 * @author xiaozhan
	 * @Date 2020.06.01
	 * description:查询应用信息
	 */
	@OPSPIMethod("op001")
	@RequestMapping(value = "/queryApp.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询应用信息接口", notes = "查询应用信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryApp(@RequestBody TblAppDTO tblAppDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		MessageBean<List> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, List.class);	
		  try{
			  List<?> appList=ADOConnection.runTask(user.getEnv(),permissionService, "queryApp", List.class,tblAppDTO);	
			  if(appList.size()>=0) {
				  msg.setCode(MESSAGE_INT_SUCCESS);
				  msg.setDescription("查询应用信息接口成功");
				  msg.setData(appList);
			  }else {
				  msg.setCode(MESSAGE_INT_SELECTERROR);
				  msg.setDescription("查询应用信息接口失败");
			  }
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }		
	     return msg.toJson();
	}
	
	/*
     * date:2020-06-01
     * function:生成父节点 (type=10)
     * author:xiaozhan
     */	
	@OPSPIMethod("op001")
	@RequestMapping(value = "/addPerParent.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "生成父节点接口", notes = "生成父节点接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addPerParent(@RequestBody  LongTreeBean child,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		Integer type=new Integer(child.getType());
		if(type==null || "".equals(type)) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "树的类型不能为空", Integer.class).toJson();
		}	
		if(child.getForeignkey()==null || "".equals(child.getForeignkey())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "操作节点的编码(外键)不能为空", Integer.class).toJson();
		}
		 MessageBean<LongTreeBean> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, LongTreeBean.class);	       
		  try{
			  LongTreeBean longTreeBean=ADOConnection.runTask(user.getEnv(),new TreeService(), "addNode", LongTreeBean.class, null,child);
			  if(longTreeBean!=null) {
			        msg.setCode(MESSAGE_INT_SUCCESS);
			        msg.setDescription("生成父节点成功");
			        msg.setData(longTreeBean);
			  }else {
				  msg.setCode(MESSAGE_INT_ADDERROR);
			      msg.setDescription("生成父节点失败");
			  }
	        }catch(Exception e){
	        	//生成失败
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("生成父节点失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-06-01
     * function:通过此接口生成操作节点。
     * author:xiaozhan
     */
	@OPSPIMethod("op001")
	@RequestMapping(value = "/addOperate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "生成操作节点接口", notes = "生成操作节点接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addOperate(@RequestBody TblOperationDTO tblOperationDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		 if(tblOperationDTO.getForeignkey()==null || "".equals(tblOperationDTO.getForeignkey())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "父节点外键不能为空", Integer.class).toJson(); 
		 }	
		 if(tblOperationDTO.getOpName()==null || "".equals(tblOperationDTO.getOpName())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作名称不能为空", Integer.class).toJson(); 
		 }		
		 if(tblOperationDTO.getOpStatus()==null || "".equals(tblOperationDTO.getOpStatus())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作状态不能为空", Integer.class).toJson(); 
		 }
		 if(tblOperationDTO.getOpWeight()==null || "".equals(tblOperationDTO.getOpWeight())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作权重不能为空", Integer.class).toJson(); 
		 }
		 if(tblOperationDTO.getOpFlag()==null || "".equals(tblOperationDTO.getOpFlag())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作标识不能为空", Integer.class).toJson(); 
		 }
		 MessageBean<List> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "addOperate", Integer.class,tblOperationDTO);	
			  if(addRes==1) {			 
				    msg.setCode(MESSAGE_INT_SUCCESS); 
					msg.setDescription("添加操作节点成功"); 				
			   }else {
			        msg.setCode(MESSAGE_INT_ADDERROR);
			        msg.setDescription("添加操作节点失败"); 
			   }		  
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("添加失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-06-01
     * function:通过此接口批量删除操作节点。
     * author:xiaozhan
     */
	@OPSPIMethod("op001")
	@RequestMapping(value = "/deleteOperate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "批量删除操作节点接口", notes = "批量删除操作节点接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteOperate(@RequestBody TblOpCodeListDTO tblOpCodeListDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(tblOpCodeListDTO.getOpCodeList()==null) {
	 		 return  MessageBean.create(MESSAGE_INT_PARAMS, "当前节点编码不能为空", Integer.class).toJson(); 
		 }
	 	 if(tblOpCodeListDTO.getOpCodeList().size()<1) {
	 		 return  MessageBean.create(MESSAGE_INT_PARAMS, "当前节点编码不能为空", Integer.class).toJson(); 
		 }
		 MessageBean<?> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{				
			  Integer delRes=ADOConnection.runTask(user.getEnv(),permissionService, "deleteOperate", Integer.class,tblOpCodeListDTO);	
			  if(delRes!=-1) {			 
				    msg.setCode(MESSAGE_INT_SUCCESS); 
					msg.setDescription("删除操作节点成功"); 				
			   }else {
			        msg.setCode(MESSAGE_INT_DELERROR);
			        msg.setDescription("删除操作节点失败"); 
			   }		  
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("删除失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-06-01
     * function:通过此接口修改操作节点状态。
     * author:xiaozhan
     */
	@OPSPIMethod("op001")
	@RequestMapping(value = "/updateOperate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改操作节点信息接口", notes = "修改操作节点信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateOperate(@RequestBody TblOperationDTO tblOperationDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(tblOperationDTO.getOpCode()==null || "".equals(tblOperationDTO.getOpCode())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson(); 
		 }
		 if(tblOperationDTO.getOpName()==null || "".equals(tblOperationDTO.getOpName())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作名称不能为空", Integer.class).toJson(); 
		 }	
		 if(tblOperationDTO.getOpName()==null || "".equals(tblOperationDTO.getOpName())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作名称不能为空", Integer.class).toJson(); 
		 }	
		 if(tblOperationDTO.getOpStatus()==null || "".equals(tblOperationDTO.getOpStatus())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作状态不能为空", Integer.class).toJson(); 
		 }
		 if(tblOperationDTO.getOpWeight()==null || "".equals(tblOperationDTO.getOpWeight())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作权重不能为空", Integer.class).toJson(); 
		 }
		 if(tblOperationDTO.getOpFlag()==null || "".equals(tblOperationDTO.getOpFlag())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作标识不能为空", Integer.class).toJson(); 
		 }
		 MessageBean<?> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{				
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),permissionService, "updateOperate", Integer.class,tblOperationDTO);	
			  if(updateRes!=-1) {			 
				    msg.setCode(MESSAGE_INT_SUCCESS); 
					msg.setDescription("修改操作节点成功"); 				
			   }else {
			        msg.setCode(MESSAGE_INT_EDITERROR);
			        msg.setDescription("修改操作节点失败"); 
			   }		  
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("修改失败");
	        }		
	     return msg.toJson();
	}
	
	

	 /*
    * date:2020-06-02
    * function:通过此接口配置(一)应用-操作（多）之间的联系。
    * author:xiaozhan
    */
	@OPSPIMethod("op001")
	@RequestMapping(value = "/addAppOP.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
   @ApiOperation(value = "生成应用-操作接口", notes = "生成操作节点接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
   @ResponseBody
	public String addAppOP(@RequestBody TblAppOPDTO tblAppOPDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		 if(tblAppOPDTO.getAppCode()==null || "".equals(tblAppOPDTO.getAppCode())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "应用编码不能为空", Integer.class).toJson(); 
		 }
		 if(tblAppOPDTO.getOpCodeList()==null) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson(); 
		 }
		 if(tblAppOPDTO.getOpCodeList().size()<1) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson(); 
		 }
		 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{				
			  Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "addAppOP", Integer.class,tblAppOPDTO);	
			  if(addRes==-1) {		
				  msg.setCode(MESSAGE_INT_ADDERROR);
			      msg.setDescription("添加应用-操作接口失败"); 			    				
			   }else if(addRes==-2){
				  msg.setCode(MESSAGE_INT_ADDERROR);
				  msg.setDescription("参数该应用不存在"); 
			   }else if(addRes==-3){
				  msg.setCode(MESSAGE_INT_ADDERROR);
			      msg.setDescription("参数该操作不存在"); 
			   }else if(addRes==-4){
				  msg.setCode(MESSAGE_INT_ADDERROR);
				  msg.setDescription("应用-操作关系已经存在"); 
			   }else {
				   msg.setCode(MESSAGE_INT_SUCCESS); 
					msg.setDescription("添加应用-操作接口成功"); 
			   }		  
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("添加失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-06-02
     * function:通过此接口删除(一)应用-操作(多)节点。
     * author:xiaozhan
     */
	@OPSPIMethod("op001")
	@RequestMapping(value = "/deleteAppOp.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除(一)应用-操作(多)节点接口", notes = "删除(一)应用-操作(多)节点接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteAppOp(@RequestBody TblAppOPDTO tblAppOPDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		 if(tblAppOPDTO.getAppCode()==null || "".equals(tblAppOPDTO.getAppCode())) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "应用编码不能为空", Integer.class).toJson(); 
		 }
		 if(tblAppOPDTO.getOpCodeList()==null) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson(); 
		 }
		 if(tblAppOPDTO.getOpCodeList().size()<1) {
			 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson(); 
		 }
		 MessageBean<?> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{				
			  Integer delRes=ADOConnection.runTask(user.getEnv(),permissionService, "deleteAppOp", Integer.class,tblAppOPDTO);	
			  if(delRes!=-1) {			 
				    msg.setCode(MESSAGE_INT_SUCCESS); 
					msg.setDescription("删除(一)应用-操作(多)操作成功"); 				
			   }else {
			        msg.setCode(MESSAGE_INT_DELERROR);
			        msg.setDescription("删除(一)应用-操作(多)操作失败"); 
			   }		  
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("删除失败");
	        }
		
	     return msg.toJson();
	}
	
	   /*
	    * date:2020-06-02
	    * function:通过此接口添加角色
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/addRole.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "添加角色接口", notes = "添加角色接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String addRole(@RequestBody TblRoleDTO tblRoleDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		     if(tblRoleDTO.getRoleName()==null || "".equals(tblRoleDTO.getRoleName())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "角色名称不能为空", Integer.class).toJson();  
			 }
			 if(tblRoleDTO.getRoleStatus()==null || "".equals(tblRoleDTO.getRoleName())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "角色状态不能为空", Integer.class).toJson();  
			 }
			 if(tblRoleDTO.getRoleWeight()==null || "".equals(tblRoleDTO.getRoleWeight())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "角色权重不能为空", Integer.class).toJson();  
			 }
			 if(tblRoleDTO.getApp()==null || "".equals(tblRoleDTO.getApp())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "应用编码不能为空", Integer.class).toJson();  
			 }
			 tblRoleDTO.setCreator(user.getLoginName());
			 tblRoleDTO.setModifier(user.getLoginName());
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				  Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "addRole", Integer.class,tblRoleDTO);	
				  if(addRes==-1){
					  msg.setCode(MESSAGE_INT_ADDERROR);
					  msg.setDescription("添加角色失败"); 
				   } 
				   else if(addRes==-2){
					  msg.setCode(MESSAGE_INT_ADDERROR);
					  msg.setDescription("应用不存在"); 
				   }else {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("添加角色成功"); 
				   }		  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("添加失败");
		        }
			
		     return msg.toJson();
		}
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口修改角色属性
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/updateRole.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "修改角色属性接口", notes = "修改角色属性接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String updateRole(@RequestBody TblRoleDTO tblRoleDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		     if(tblRoleDTO.getRoleCode()==null || "".equals(tblRoleDTO.getRoleCode())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();  
			 }
			 if(tblRoleDTO.getRoleName()==null || "".equals(tblRoleDTO.getRoleName())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "角色名称不能为空", Integer.class).toJson();  
			 }
			 if(tblRoleDTO.getRoleStatus()==null || "".equals(tblRoleDTO.getRoleName())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "角色状态不能为空", Integer.class).toJson();  
			 }
			 if(tblRoleDTO.getRoleWeight()==null || "".equals(tblRoleDTO.getRoleWeight())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "角色权重不能为空", Integer.class).toJson();  
			 }
			 if(tblRoleDTO.getApp()==null || "".equals(tblRoleDTO.getApp())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "应用编码不能为空", Integer.class).toJson();  
			 }
			 tblRoleDTO.setCreator(user.getLoginName());
			 tblRoleDTO.setModifier(user.getLoginName());
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				  Integer updateRes=ADOConnection.runTask(user.getEnv(),permissionService, "updateRole", Integer.class,tblRoleDTO);	
				  if(updateRes==-1){
					  msg.setCode(MESSAGE_INT_EDITERROR);
					  msg.setDescription("修改角色失败"); 
				   } 
				   else {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("修改角色成功"); 
				   }		  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("修改失败");
		        }
			
		     return msg.toJson();
		}
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口删除角色
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/deleteRole.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "删除角色接口", notes = "删除角色接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String deleteRole(@RequestBody TblRoleDTO tblRoleDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		    if(tblRoleDTO.getRoleCode()==null || "".equals(tblRoleDTO.getRoleCode())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();  
		    }
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				  Integer deleteRes=ADOConnection.runTask(user.getEnv(),permissionService, "deleteRole", Integer.class,tblRoleDTO);	
				  if(deleteRes==-1){
					  msg.setCode(MESSAGE_INT_DELERROR);
					  msg.setDescription("删除角色失败"); 
				   } 
				  else {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("删除角色成功"); 
				   }		  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("删除失败");
		        }
			
		     return msg.toJson();
		}
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口查询所有角色
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/queryAllRole.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "查询所有角色接口", notes = "查询所有角色接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String queryAllRole(@RequestBody TblTenantDTO tblTenantDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {				   
		   if(tblTenantDTO.get_tenantCode()==null || "".equals(tblTenantDTO.get_tenantCode())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "租户code不能为空", Integer.class).toJson();
		   } 
		   if(tblTenantDTO.get_app()==null || "".equals(tblTenantDTO.get_app())) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "应用信息不能为空", Integer.class).toJson();
		   } 
		   MessageBean<List> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, List.class);	       
			  try{				
				  List<?>  roleList=ADOConnection.runTask(user.getEnv(),permissionService, "queryAllRole", List.class,tblTenantDTO);	
				  if(roleList.size()>=0) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("查询角色成功"); 
					  msg.setData(roleList);
				  }else {
					  msg.setCode(MESSAGE_INT_SELECTERROR); 
					  msg.setDescription("查询角色失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("查询失败");
		        }
			
		     return msg.toJson();
		}
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口添加角色-用户关系操作
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/addRoleUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "添加角色-用户接口", notes = "添加角色-用户接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String addRoleUser(@RequestBody TblRoleUserDTO tblRoleUserDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblRoleUserDTO.getRoleCode()==null || "".equals(tblRoleUserDTO.getRoleCode())) {
		    	return  MessageBean.create(MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		     }
		     if(tblRoleUserDTO.getUserCodeList()==null) {
			    return  MessageBean.create(MESSAGE_INT_PARAMS, "用户编码不能为空", Integer.class).toJson();
			 }
		     if(tblRoleUserDTO.getUserCodeList().size()<1) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "用户编码不能为空", Integer.class).toJson();
			 }
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "addRoleUser", Integer.class,tblRoleUserDTO);	
				  if(addRes!=-1) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("添加角色-用户关系成功"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_ADDERROR); 
					  msg.setDescription("添加角色-用户关系失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("添加失败");
		        }
			
		     return msg.toJson();
		}	 
	   
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口添加角色-操作关系操作
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/addRoleOP.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "添加角色-用户接口", notes = "添加角色-用户接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String addRoleOP(@RequestBody TblRoleOpDTO tblRoleOpDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblRoleOpDTO.getRoleCode()==null || "".equals(tblRoleOpDTO.getRoleCode())) {
		    	return  MessageBean.create(MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		     }
		     if(tblRoleOpDTO.getOpCodeList()==null) {
			    return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson();
			 }
		     if(tblRoleOpDTO.getOpCodeList().size()<1) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson();
			 }
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "addRoleOP", Integer.class,tblRoleOpDTO);	
				  if(addRes!=-1) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("添加角色-操作关系成功"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_ADDERROR); 
					  msg.setDescription("添加角色-操作关系失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("添加失败");
		        }
			
		     return msg.toJson();
		}	
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口删除角色-操作关系操作
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/deleteRoleOP.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "删除角色-操作接口", notes = "删除角色-操作接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String deleteRoleOP(@RequestBody TblRoleOpDTO tblRoleOpDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblRoleOpDTO.getRoleCode()==null || "".equals(tblRoleOpDTO.getRoleCode())) {
		    	return  MessageBean.create(MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		     }
		     if(tblRoleOpDTO.getOpCodeList()==null) {
			    return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson();
			 }
		     if(tblRoleOpDTO.getOpCodeList().size()<1) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson();
			 }
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer delRes=ADOConnection.runTask(user.getEnv(),permissionService, "deleteRoleOP", Integer.class,tblRoleOpDTO);	
				  if(delRes!=-1) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("删除角色-操作关系成功"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_DELERROR); 
					  msg.setDescription("删除角色-操作关系失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("删除失败");
		        }
			
		     return msg.toJson();
		}	
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口修改角色-操作关系操作
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/updateRoleOP.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "修改角色-操作接口", notes = "修改角色-操作接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String updateRoleOP(@RequestBody TblRoleOpDTO tblRoleOpDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblRoleOpDTO.getRoleCode()==null || "".equals(tblRoleOpDTO.getRoleCode())) {
		    	return  MessageBean.create(MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		     }
		     if(tblRoleOpDTO.getOpCodeList()==null) {
			    return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson();
			 }
		     if(tblRoleOpDTO.getOpCodeList().size()<1) {
				 return  MessageBean.create(MESSAGE_INT_PARAMS, "操作编码不能为空", Integer.class).toJson();
			 }
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer updateRes=ADOConnection.runTask(user.getEnv(),permissionService, "updateRoleOP", Integer.class,tblRoleOpDTO);	
				  if(updateRes!=-1) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("修改角色-操作关系成功"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_EDITERROR); 
					  msg.setDescription("修改角色-操作关系失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("修改失败");
		        }
			
		     return msg.toJson();
		}	
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口添加角色数据范围操作
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/addRoleRangeValue.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "添加角色数据范围操作接口", notes = "添加角色数据范围操作接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String addRoleRangeValue(@RequestBody TblRoleRangeValueDTO tblRoleRangeValueDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblRoleRangeValueDTO.getRoleCode()==null || "".equals(tblRoleRangeValueDTO.getRoleCode())) {
		    	return  MessageBean.create(MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		     }
		     if(tblRoleRangeValueDTO.getCatalogue()==null || "".equals(tblRoleRangeValueDTO.getCatalogue())) {
			    return  MessageBean.create(MESSAGE_INT_PARAMS, "目录编码不能为空", Integer.class).toJson();
			 }
		     if(tblRoleRangeValueDTO.getValue()==null || "".equals(tblRoleRangeValueDTO.getValue())) {
		        return  MessageBean.create(MESSAGE_INT_PARAMS, "值不能为空", Integer.class).toJson(); 
		     }
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "addRoleRangeValue", Integer.class,tblRoleRangeValueDTO);	
				  if(addRes!=-1) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("添加角色数据范围操作成功"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_ADDERROR); 
					  msg.setDescription("添加角色数据范围操作失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("添加失败");
		        }
			
		     return msg.toJson();
		}	
	   
	   /*
	    * date:2020-06-03
	    * function:通过此接口添加域
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/addAppCatalogue.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "添加域接口", notes = "添加域接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String addAppCatalogue(@RequestBody TblAppCatalogueDTO tblAppCatalogueDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblAppCatalogueDTO.getAppCode()==null || "".equals(tblAppCatalogueDTO.getAppCode())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "应用编码不能为空", Integer.class).toJson();
		     }
		     if(tblAppCatalogueDTO.getName()==null || "".equals(tblAppCatalogueDTO.getName())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "域名称不能为空", Integer.class).toJson();
		     }
		     if(tblAppCatalogueDTO.getStatus()==null || "".equals(tblAppCatalogueDTO.getStatus())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "域状态不能为空", Integer.class).toJson();
		     }
		     if(tblAppCatalogueDTO.getWeight()==null || "".equals(tblAppCatalogueDTO.getWeight())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "域权重不能为空", Integer.class).toJson();
		     }
		     tblAppCatalogueDTO.setCreator(user.getLoginName());
		     tblAppCatalogueDTO.setModifier(user.getLoginName());
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "addAppCatalogue", Integer.class,tblAppCatalogueDTO);	
				  if(addRes!=-1) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("添加域成功"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_ADDERROR); 
					  msg.setDescription("添加域失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("添加失败");
		        }
			
		     return msg.toJson();
		}	
	   
	   /*
	    * date:2020-06-03
	    * function:通过此接口修改域
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/updateAppCatalogue.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "修改域接口", notes = "添加域接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String updateAppCatalogue(@RequestBody TblAppCatalogueDTO tblAppCatalogueDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblAppCatalogueDTO.getCode()==null || "".equals(tblAppCatalogueDTO.getCode())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "域编码不能为空", Integer.class).toJson();
		     }
		     if(tblAppCatalogueDTO.getName()==null || "".equals(tblAppCatalogueDTO.getName())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "域名称不能为空", Integer.class).toJson();
		     }
		     if(tblAppCatalogueDTO.getStatus()==null || "".equals(tblAppCatalogueDTO.getStatus())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "域状态不能为空", Integer.class).toJson();
		     }
		     if(tblAppCatalogueDTO.getWeight()==null || "".equals(tblAppCatalogueDTO.getWeight())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "域权重不能为空", Integer.class).toJson();
		     }
		     tblAppCatalogueDTO.setCreator(user.getLoginName());
		     tblAppCatalogueDTO.setModifier(user.getLoginName());		     
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "updateAppCatalogue", Integer.class,tblAppCatalogueDTO);	
				  if(addRes!=-1) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("修改域成功"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_EDITERROR); 
					  msg.setDescription("修改域失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("修改失败");
		        }
			
		     return msg.toJson();
		}	
	   
	   /*
	    * date:2020-06-03
	    * function:通过此接口修改域
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/deleteAppCatalogue.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "删除域接口", notes = "删除域接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String deleteAppCatalogue(@RequestBody TblAppCatalogueDTO tblAppCatalogueDTO,  @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblAppCatalogueDTO.getCode()==null || "".equals(tblAppCatalogueDTO.getCode())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "域编码不能为空", Integer.class).toJson();
		     }		     
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer delRes=ADOConnection.runTask(user.getEnv(),permissionService, "deleteAppCatalogue", Integer.class,tblAppCatalogueDTO);	
				  if(delRes==-1) {
					  msg.setCode(MESSAGE_INT_DELERROR); 
					  msg.setDescription("删除域失败"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("删除域成功");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("删除失败");
		        }
			
		     return msg.toJson();
		}	
	   
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口根据角色和目录修改数据范围操作
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/updateRoleRangeValue.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "修改角色数据范围操作接口", notes = "修改角色数据范围操作接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String updateRoleRangeValue(@RequestBody TblRoleRangeValueDTO tblRoleRangeValueDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblRoleRangeValueDTO.getRoleCode()==null || "".equals(tblRoleRangeValueDTO.getRoleCode())) {
		    	return  MessageBean.create(MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		     }
		     if(tblRoleRangeValueDTO.getCatalogue()==null || "".equals(tblRoleRangeValueDTO.getCatalogue())) {
			    return  MessageBean.create(MESSAGE_INT_PARAMS, "目录编码不能为空", Integer.class).toJson();
			 }
		     if(tblRoleRangeValueDTO.getValue()==null || "".equals(tblRoleRangeValueDTO.getValue())) {
		        return  MessageBean.create(MESSAGE_INT_PARAMS, "值不能为空", Integer.class).toJson(); 
		     }
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "updateRoleRangeValue", Integer.class,tblRoleRangeValueDTO);	
				  if(addRes!=-1) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("修改角色数据范围操作成功"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_ADDERROR); 
					  msg.setDescription("修改角色数据范围操作失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("修改失败");
		        }
			
		     return msg.toJson();
		}	
	
	   
	   /*
	    * date:2020-06-03
	    * function:通过此接口添加组织部门关系
	    * author:xiaozhan
	    */
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/addRoleOrg.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "添加组织部门关系接口", notes = "添加域接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String addRoleOrg(@RequestBody TblOrgRoleDTO tblOrgRoleDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {			   
		      if(tblOrgRoleDTO.getOrgCode()==null || "".equals(tblOrgRoleDTO.getOrgCode())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "组织编码不能为空", Integer.class).toJson();
		     }	
		     if(tblOrgRoleDTO.getRoleCode()==null || "".equals(tblOrgRoleDTO.getRoleCode())) {
		    	 return  MessageBean.create(MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		     }	
			 MessageBean<Integer> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer addRes=ADOConnection.runTask(user.getEnv(),permissionService, "addRoleOrg", Integer.class,tblOrgRoleDTO);	
				  if(addRes!=-1) {
					  msg.setCode(MESSAGE_INT_SUCCESS); 
					  msg.setDescription("添加组织部门关系成功"); 					  
				  }else {
					  msg.setCode(MESSAGE_INT_ADDERROR); 
					  msg.setDescription("添加组织部门关系失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(MESSAGE_INT_ERROR);
		            msg.setDescription("添加失败");
		        }
			
		     return msg.toJson();
		}	
	   
	   /*
	    * date:2020-06-11
	    * function:通过此接口删除(一)组织角色(多)关系
	    * author:xiaozhan
	    */
	   
	   @RequestMapping(value = "/deleteManyRoleOrg.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "删除组织角色关系接口", notes = "删除组织角色关系接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String deleteManyRoleOrg(@RequestBody TblOrgRoleDTO tblOrgRoleDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {			   
		      if(tblOrgRoleDTO.getOrgCode()==null || "".equals(tblOrgRoleDTO.getOrgCode())) {
		    	 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "组织编码不能为空", Integer.class).toJson();
		     }	
		     if(tblOrgRoleDTO.getRoleCodeList()==null) {
		    	 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson(); 
		     }
		     if(tblOrgRoleDTO.getRoleCodeList().size()<1) {
		    	 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();  
		     }
			 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer delRes=ADOConnection.runTask(user.getEnv(),permissionService, "deleteManyRoleOrg", Integer.class,tblOrgRoleDTO);	
				  if(delRes==null) {
					  msg.setCode(Constant.MESSAGE_INT_ADDERROR); 
					  msg.setDescription("删除组织角色关系失败"); 					  
				  }else if(delRes==-1){
					  msg.setCode(Constant.MESSAGE_INT_ADDERROR); 
					  msg.setDescription("删除组织角色关系失败"); 	 
				  }else {
					  msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					  msg.setDescription("删除组织角色关系成功");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(Constant.MESSAGE_INT_ERROR);
		            msg.setDescription("删除失败");
		        }
			
		     return msg.toJson();
		}
	   
	   
	   /*
	    * date:2020-06-02
	    * function:通过此接口删除角色数据范围操作
	    * author:xiaozhan
	    */
	   
	   @RequestMapping(value = "/deleteRoleRangeValue.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "删除角色数据范围操作接口", notes = "删除角色数据范围操作接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String deleteRoleRangeValue(@RequestBody TblRoleRangeValueDTO tblRoleRangeValueDTO, @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(tblRoleRangeValueDTO.getRoleCode()==null || "".equals(tblRoleRangeValueDTO.getRoleCode())) {
		    	return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		     }
		     if(tblRoleRangeValueDTO.getCatalogue()==null || "".equals(tblRoleRangeValueDTO.getCatalogue())) {
			    return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "目录编码不能为空", Integer.class).toJson();
			 }
			 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
			  try{				
				 Integer delRes=ADOConnection.runTask(user.getEnv(),permissionService, "deleteRoleRangeValue", Integer.class,tblRoleRangeValueDTO);	
				 if(delRes==null) {
					  msg.setCode(Constant.MESSAGE_INT_ADDERROR); 
					  msg.setDescription("删除角色数据范围操作失败"); 					  
				  }else if(delRes==-1){
					  msg.setCode(Constant.MESSAGE_INT_ADDERROR); 
					  msg.setDescription("删除角色数据范围操作失败"); 	 
				  }else {
					  msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					  msg.setDescription("删除角色数据范围操作成功");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(Constant.MESSAGE_INT_ERROR);
		            msg.setDescription("删除失败");
		        }
			
		     return msg.toJson();
		}	
	   
	   
	   /*
	    * date:2020-06-03
	    * function:通过此接口查询域
	    * author:xiaozhan
	    */
	   @DataRangeMethod
	   @OPSPIMethod("op001")
	   @RequestMapping(value = "/queryAppCatalogue.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "查询域接口", notes = "查询域接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String queryAppCatalogue(@RequestBody TblAppCatalogueDTO tblAppCatalogueDTO,  @SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user,@DataInject TblRoleRangeValueListVO tblRoleRangeValueListVO) {	
			 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
			  try{				
				List<?> appCatalogueList=ADOConnection.runTask(user.getEnv(),permissionService, "queryAppCatalogue", List.class,tblAppCatalogueDTO);	
				  if(appCatalogueList!=null && appCatalogueList.size()>=0) {
					  msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					  msg.setDescription("查询域成功"); 
					  msg.setData(appCatalogueList);
				  }else {
					  msg.setCode(Constant.MESSAGE_INT_SELECTERROR); 
					  msg.setDescription("查询域失败");  
				  }	  
		        }catch(Exception e){
		        	msg.setCode(Constant.MESSAGE_INT_ERROR);
		            msg.setDescription("查询失败");
		        }
			
		     return msg.toJson();
		}	
	   /*******系统内接口*******/
	   
	   /*
	    * date:2020-06-17
	    * function:通过此接口根据角色查询人员
	    * author:xiaozhan
	    */
	   @RequestMapping(value = "/queryUserByRole.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	   @ApiOperation(value = "根据角色查询人员接口", notes = "根据角色查询人员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	   @ResponseBody
		public String queryUserByRole(@RequestBody RoleDTO roleDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		     if(roleDTO.getRoleCode()==null || "".equals(roleDTO.getRoleCode())) {
				 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
			 }
			 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	          
			  try{				
				  PageListVO userVO=ADOConnection.runTask(user.getEnv(),permissionService, "queryUserByRole", PageListVO.class,roleDTO);	
				  if(userVO!=null && userVO.getRowNumber()>0) {				 
						 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
						 msg.setDescription("根据角色code查询到相关职员信息列表"); 
						 msg.setData(userVO);
				 }else {
					     msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					     msg.setDescription("该角色未查询到相关职员信息列表"); 
				 } 
		        }catch(Exception e){
		        	msg.setCode(Constant.MESSAGE_INT_ERROR);
		            msg.setDescription("查询失败");
		        }
			
		     return msg.toJson();
		}	
	   

}
