package com.koron.authority.annotation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.stereotype.Component;
import org.swan.bean.MessageBean;

import com.koron.authority.ValidatePermission;
import com.koron.inwlms.bean.DTO.sysManager.ModuleMenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleMenuDTO;
import com.koron.inwlms.bean.VO.sysManager.ModuleMenuVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMenusVO;
import com.koron.inwlms.service.sysManager.impl.UserServiceImpl;
import com.koron.util.Constant;
/***
 *       菜单权限校验
 * @author xiaozhan
 * @date  2020-04-09
 */
@Aspect
@Component
public class SysMoudleAspect {

	    @Pointcut("@annotation(com.koron.authority.ValidatePermission)")
	    public void logPoinCut() {
	    }
	    
	    
	  // @Before("logPoinCut()")
	    @Around("logPoinCut()")
	    public Object dobefore(JoinPoint joinPoint) throws Throwable {
	      System.out.println("处理前。。。。。"); 	     
	      //查询该用户该模块权限
	      UserServiceImpl userService=new UserServiceImpl();
	      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	      Method method = signature.getMethod();	
	      ValidatePermission op=method.getAnnotation(ValidatePermission.class);
	        String className = joinPoint.getTarget().getClass().getName();	
			String methodName = method.getName();			
	        String moduleName =new String();
	        String userOp=new String();
	        if (op != null) { 
	        	moduleName = op.value();
	        	String[] strArr = moduleName.split("-");
	        	moduleName=strArr[0];
	        	userOp=strArr[1];
			}
	        //根据moduleName 查询moduleCode
	        ModuleMenuDTO moduleMenuDTO=new ModuleMenuDTO();
	        moduleMenuDTO.setModuleName(moduleName);
	        List<ModuleMenuVO> moduleMenuList=ADOConnection.runTask(new UserServiceImpl(), "queryMenuOP", List.class,moduleMenuDTO);
	        if(moduleMenuList.size()>0) {
	        	String moduleCode=moduleMenuList.get(0).getModuleCode();
	        	//获取登录的用户名 测试使用 
	        	String userCode="c545d2c156834f1b9eea9136620726b3";	
	        	RoleMenuDTO roleMenuDTO=new RoleMenuDTO();
	        	roleMenuDTO.setModuleCode(moduleCode);
	        	roleMenuDTO.setUserCode(userCode);
				//返回该用户该模块权限
	        	List<RoleMenusVO>  roleMenusList=ADOConnection.runTask(new UserServiceImpl(), "queryOPByCode", List.class,roleMenuDTO);	
	        	//String转为List									
				for(int  i=0;i<roleMenusList.size();i++) {					
					 String str = roleMenusList.get(i).getOp().replace(" ", "");						 
				     List<String> lis = Arrays.asList(str.split(","));						    
				     roleMenusList.get(i).setOpList(lis);
				}
				int opInt=1;
				//判断有没有权限
				if("query".equals(userOp)) {
					opInt=1;
				}else if("add".equals(userOp)) {
					opInt=2;
				}else if("update".equals(userOp)) {
					opInt=3;
				}else{
					opInt=4;
				}
				boolean roleBool=false;
				//遍历
				for(int j=0;j<roleMenusList.get(0).getOpList().size();j++) {
					if(opInt==Integer.parseInt(roleMenusList.get(0).getOpList().get(j))) {
						roleBool=true;
						break;
					}
				}
				if(roleBool==false) {
					//没有权限
		        	return  MessageBean.create(Constant.MESSAGE_INT_NOMODULE, "没有操作权限", Integer.class).toJson();
				}
				
	        }else {
	        	//查询失败，没有权限
	        	return  MessageBean.create(Constant.MESSAGE_INT_NOMODULE, "没有操作权限", Integer.class).toJson();
	        }
	    	return ((ProceedingJoinPoint) joinPoint).proceed();		
			     			
			
	    }
}
