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
import com.koron.permission.authority.DataInject;
import com.koron.permission.authority.DataRangeMethod;
import com.koron.permission.bean.DTO.TblOpDTO;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.ResponseVO;
import com.koron.permission.bean.VO.TblRoleRangeValueListVO;
import com.koron.permission.bean.VO.TblRoleRangeValueVO;
import com.koron.permission.service.PermissionOuterService;
import com.koron.permission.service.impl.PermissionOuterServiceImpl;
import com.koron.util.Constant;


@Configuration
@Aspect
public class DataRangeAOP {
		
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
            // 获取方法的所有参数，找出所有 SPIInject SPIAccount注解的参数
            UserVO account = null;    
            int i = 0;
            int y = 0;
            for (Parameter param : method.getParameters()) {
                SPIAccountAnno obj = param.getAnnotation(SPIAccountAnno.class);
                if (obj != null) {// 如果账号存在，则给参数赋值
                    account = (UserVO) args[i]; 
                    break;
                }              
                i++;
            }
            if(account==null) {
            	return  MessageBean.create(MESSAGE_INT_NOLOGIN, MESSAGE_STRING_NOLOGIN, Integer.class).toJson();
            }
            for(Parameter param : method.getParameters()) {
            	DataInject data = param.getAnnotation(DataInject.class);
                if (data != null) {
                	//获取范围值，并放入args[i]对应的参数中   
                	TblTenantDTO tblTenantDTO=new TblTenantDTO();
                	tblTenantDTO.set_userCode(account.getCode());
                    List<TblRoleRangeValueVO> rangeValueList=ADOConnection.runTask(account.getEnv(),permissionOuterService, "getUserRangeValueList", List.class,tblTenantDTO);
                    //处理数据
    	        	TblRoleRangeValueListVO tblRoleRangeValueListVO=new TblRoleRangeValueListVO();
    	        	List<TblRoleRangeValueListVO> tblRoleRangeValueList=new ArrayList<>();
    	        	tblRoleRangeValueListVO.setRoleRangeValueList(rangeValueList);
    	        	tblRoleRangeValueList.add(tblRoleRangeValueListVO);
    	        	for(TblRoleRangeValueListVO bean:tblRoleRangeValueList) {          	
                    	BeanUtils.copyProperties(args[y], bean);
                    }
    	        	break;
               	               	   	 
                }
                y++;
            }
           
        }
        Object object = pjp.proceed();// 执行该方法
        return object;
    }
}
