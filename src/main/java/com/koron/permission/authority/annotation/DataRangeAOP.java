package com.koron.permission.authority.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
import com.koron.inwlms.util.InterfaceUtil;
import com.koron.permission.authority.DataInject;
import com.koron.permission.authority.DataRangeMethod;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.ResponseVO;
import com.koron.permission.bean.VO.TblRoleRangeValueListVO;
import com.koron.permission.bean.VO.TblRoleRangeValueVO;
import com.koron.util.Constant;


@Configuration
@Aspect
public class DataRangeAOP {
	
	@Value("${app.datapath}")
	private String path;	
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
	public static final String MESSAGE_STRING_PARAMS_TENANT = "参数租户为空";
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
	
	
    @Pointcut("@annotation(com.koron.permission.authority.DataRangeMethod)")
    public void cut() {

    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        Method method = sig.getMethod();
        DataRangeMethod methodAnnotation = method.getAnnotation(DataRangeMethod.class);
        Gson gson = new Gson();
        if (methodAnnotation != null) {
            String operationString = methodAnnotation.value();
            Object[] args = pjp.getArgs();         
            String tenantCode ="";
            String app="";
            if(args!=null) {
	            for(int i=0;i<args.length;i++) {
	            	String string=gson.toJson(args[i]);	 	            	
	            	if(string.contains("_tenantCode")) {
	            		JsonObject  jo = new JsonParser().parse(string).getAsJsonObject();
	            		//获取值
	                    tenantCode = jo.get("_tenantCode").getAsString();	                    
	            	}
	            	if(string.contains("_app")) {
	            		JsonObject  jo = new JsonParser().parse(string).getAsJsonObject();
	            		//获取值
	            		app = jo.get("_app").getAsString();	                    
	            	}	            	
	            	
	            }
            }else {
            	return  MessageBean.create(MESSAGE_INT_PARAMS, MESSAGE_STRING_PARAMS, Integer.class).toJson();
            }
            if("".equals(tenantCode)){
            	return  MessageBean.create(MESSAGE_INT_PARAMS, MESSAGE_STRING_PARAMS_TENANT, Integer.class).toJson();
            } 
            if("".equals(app)){
            	return  MessageBean.create(MESSAGE_INT_PARAMS, MESSAGE_STRING_PARAMS_APP, Integer.class).toJson();
            } 
            
            // 获取方法的所有参数，找出所有 SPIInject SPIAccount注解的参数
            StaffBean account = null;    
            int i = 0;
            for (Parameter param : method.getParameters()) {
                SPIAccountAnno obj = param.getAnnotation(SPIAccountAnno.class);
                if (obj != null && StaffBean.class.isAssignableFrom(param.getType())) {// 如果账号存在，则给参数赋值
                    account = (StaffBean) args[i];
                    if(account==null) {
                    	return  MessageBean.create(MESSAGE_INT_NOLOGIN, MESSAGE_STRING_NOLOGIN, Integer.class).toJson();
                    }
                }
                DataInject data = param.getAnnotation(DataInject.class);
                if (data != null) {
                	//获取范围值，并放入args[i]对应的参数中               	               	
                	List<TblRoleRangeValueVO>  rangeValueList=new ArrayList<>();
                	//调用第三方权限接口
    	            InterfaceUtil interfaceUtil=new InterfaceUtil();  
    	            TblTenantDTO tblTenant=new TblTenantDTO();
    	         //   tblTenant.set_userCode(account.getCode());
    	            tblTenant.set_userCode("user001");
    	            tblTenant.set_tenantCode(tenantCode);
    	            tblTenant.set_app(app);
    	            String jsonData= gson.toJson(tblTenant);
    	            JsonObject json=InterfaceUtil.interfaceOfPostUtil(path, jsonData);  
    	            if(json!=null) {	            	
    	            	String jsonString=json.toString();
    	            	JsonElement  je = new JsonParser().parse(jsonString);
    	            	JsonArray jsonArray=(JsonArray) je.getAsJsonObject().get("data");
    	            	if(jsonArray.size()>0) {   	            		
    	                    for (JsonElement bean : jsonArray) {
    	                    	TblRoleRangeValueVO tblRoleRangeValue=gson.fromJson(bean, TblRoleRangeValueVO.class);
    	                    	rangeValueList.add(tblRoleRangeValue);
    	                    }
    	                   //处理数据
    	    	        	TblRoleRangeValueListVO tblRoleRangeValueListVO=new TblRoleRangeValueListVO();
    	    	        	List<TblRoleRangeValueListVO> tblRoleRangeValueList=new ArrayList<>();
    	    	        	tblRoleRangeValueListVO.setRoleRangeValueList(rangeValueList);
    	    	        	tblRoleRangeValueList.add(tblRoleRangeValueListVO);
    	    	        	for(TblRoleRangeValueListVO bean:tblRoleRangeValueList) {          	
    	                    	BeanUtils.copyProperties(args[i], bean);
    	                    } 
    	            	}
    	            	
    	            }
                	               	   	 
                }
                i++;
            }
           
        }
        Object object = pjp.proceed();// 执行该方法
        return object;
    }
    
    
    public static ResponseVO create(int code, String description) {
    	ResponseVO bean=new ResponseVO();
        bean.setCode(code);
        bean.setDescription(description);
        return bean;
    }
}
