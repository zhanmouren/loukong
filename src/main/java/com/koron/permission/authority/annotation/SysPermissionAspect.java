package com.koron.permission.authority.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.swan.bean.MessageBean;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.koron.inwlms.service.sysManager.impl.UserServiceImpl;
import com.koron.permission.authority.PermissionAOP;
import com.koron.permission.bean.VO.UserListVO;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;

/***
 *  判断权限是否拥有操作
 * @author xiaozhan
 * @date  2020-05-29
 */
@Aspect
@Component
public class SysPermissionAspect {

	   @Pointcut("@annotation(com.koron.permission.authority.PermissionAOP)")
	   public void cut() {
	   }
	   
	   @Around("cut()")
	   public Object dobefore(ProceedingJoinPoint joinPoint) throws Throwable {
		   System.out.print("处理中");
		   //判断用户是否登录
		   Gson jsonValue = new Gson();
		   if(SessionUtil.getAttribute(Constant.LOGIN_USER)==null) {
		    //	return  MessageBean.create(Constant.MESSAGE_INT_NOLOGIN, "未登录", Integer.class).toJson();
		   }
		   UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);	
		   //查询用户有没有操作权限
		      UserServiceImpl userService=new UserServiceImpl();
		      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		      Method method = signature.getMethod();	
		      PermissionAOP op=method.getAnnotation(PermissionAOP.class);
		        String className = joinPoint.getTarget().getClass().getName();	
				String methodName = method.getName();			
		        String moduleName =new String();
		        String userOp=new String();
		        //获取入参
		        Object[] args = joinPoint.getArgs();
		        for (Parameter param : method.getParameters()) {
		        //	PermissionAOP op=method.getAnnotation(PermissionAOP.class);
		        }
		       
		        if (op != null) { 
//		        	moduleName = op.value();
//		        	String[] strArr = moduleName.split("-");
//		        	moduleName=strArr[0];
//		        	userOp=strArr[1];
				}
		        else {
					return  MessageBean.create(Constant.MESSAGE_INT_ERROR, "注解方式不对", Integer.class).toJson();
				}		       	      							 
		    	return ((ProceedingJoinPoint) joinPoint).proceed();					     							
		    }
	   
	   
}
