package com.koron.permission.authority.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.swan.bean.MessageBean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koron.common.bean.StaffBean;
import com.koron.common.permission.SPIAccountAnno;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.util.InterfaceUtil;
import com.koron.permission.authority.OPSPIMethod;
import com.koron.permission.bean.DTO.TblOpDTO;
import com.koron.permission.bean.VO.ResponseVO;
import com.koron.permission.bean.VO.TblOperationVO;
import com.koron.permission.service.PermissionOuterService;

@Configuration
@Aspect
public class OPPermissionAOP {
	
	  
	  @Autowired
	  private PermissionOuterService permissionOuterService;
	   
	    /**
		 * 参数校验异常码
		 */
		public static final int MESSAGE_INT_PARAMS = 20108;
		/**
		 * 参数校验异常提示
		 */
		public static final String MESSAGE_STRING_PARAMS = "参数为空";
		
		/**
		 * 参数租户为空
		 */
		public static final String MESSAGE_STRING_PARAMS_Tenant = "参数租户为空";
		/**
		 * 参数租户为空
		 */
		public static final String MESSAGE_STRING_PARAMS_ANNOTATION = "权限注解参数为空";
		/**
		 * 参数应用为空
		 */
		public static final String MESSAGE_STRING_PARAMS_APP = "参数应用为空";
		/**
		 * 未登录码
		 */
		public static final int MESSAGE_INT_NOLOGIN = 10000;
		
		/**
		 * 未登录提示
		 */
		public static final String MESSAGE_STRING_NOLOGIN = "未登录";
		
		/**
		 * 无操作权限码
		 */
		public static final int MESSAGE_INT_NOMODULE = 10001;
		
		/**
		 * 无操作权限提示
		 */
		public static final String MESSAGE_STRING_NOMODULE = "无操作权限";
		
	    @Pointcut("@annotation(com.koron.permission.authority.OPSPIMethod)")
	    public void cut() {

	    }

	    @Around("cut()")
	    public Object around(ProceedingJoinPoint pjp) throws Throwable {
	        MethodSignature sig = (MethodSignature) pjp.getSignature();
	        Method method = sig.getMethod();
	        OPSPIMethod methodAnnotation = method.getAnnotation(OPSPIMethod.class);
	        Gson gson = new Gson();
	        if (methodAnnotation != null) {
	        	//获取注解上的名称
	            String operationString = methodAnnotation.value();
	            if("".equals(operationString)) {
	            	return  MessageBean.create(MESSAGE_INT_PARAMS,MESSAGE_STRING_PARAMS_ANNOTATION, Integer.class).toJson();
	            }

	            Object[] args = pjp.getArgs();
	            // 获取方法的所有参数，找出所有 SPIData SPIAccount注解的参数
	            UserVO account = null;         
	            int i = 0;
	            for (Parameter param : method.getParameters()) {
	                SPIAccountAnno obj = param.getAnnotation(SPIAccountAnno.class);
	                if (obj != null) {// 如果账号存在，则给参数赋值
	                    account = (UserVO) args[i];
	                }
	                i++;
	            }
	            if (account == null) {     
	            	return  MessageBean.create(MESSAGE_INT_NOLOGIN, MESSAGE_STRING_NOLOGIN, Integer.class).toJson();
	            }            	             
	            TblOpDTO tblOpDTO=new TblOpDTO();
	            tblOpDTO.setOpCode(operationString);
	            tblOpDTO.setUserCode(account.getCode());	                     	            
	            List<TblOperationVO> opList=ADOConnection.runTask(account.getEnv(),permissionOuterService, "getOPList", List.class,tblOpDTO);
	            if(opList!=null && opList.size()>0) {	            	
	           
	            }
                else {
                	//没有权限
               	   return MessageBean.create(MESSAGE_INT_NOMODULE,MESSAGE_STRING_NOMODULE, Integer.class).toJson();
	            }
	        }
	        Object object = pjp.proceed();// 执行该方法
	        return object;
	    }
	    
	

	   
}
