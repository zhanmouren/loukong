package com.koron.inwlms.aspect;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.koron.inwlms.bean.DTO.sysManager.OperateLogDTO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.sysManager.LogService;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 切面日志管理
 * 
 * @author lzy
 *
 */
@Aspect
// 申明是个spring管理的bean
@Component
//@Order(1)
public class SystemLogAspect {

	private Logger log = Logger.getLogger(getClass());

	private Gson gson = new Gson();

	@Autowired(required = false)
	HttpServletRequest request;

	@Autowired
	private LogService logService;

//	// 申明一个切点 里面是 execution表达式
//	@Pointcut("execution(public * com.koron.web.servlet.IndexAction.*(..))")
//	private void controllerAspect() {
//	}

	@Pointcut("@annotation(com.koron.inwlms.aspect.OperateAspect)")
	private void operateAspect() {
	}

	/**
	 * 对查询方法记录日志的切点
	 */
	@Pointcut("execution(* com.koron..*.*Controller.query*(..))")
	public void query() {
	}

	/**
	 * 对新增方法记录日志的切点
	 */
	@Pointcut("execution(* com.koron..*.*Controller.add*(..))")
	public void add() {
	}

	/**
	 * 对修改方法记录日志的切点
	 */
	@Pointcut("execution(* com.koron..*.*Controller.update*(..))")
	public void update() {
	}

	/**
	 * 对删除方法记录日志的切点
	 */
	@Pointcut("execution(* com.koron..*.*Controller.delete*(..))")
	public void delete() {
	}
	
	/**
	 * 对导出方法记录日志的切点
	 */
	@Pointcut("execution(* com.koron..*.*Controller.download*(..))")
	public void download() {
	}

	// 请求method前打印内容
	@Before(value = "operateAspect()")
	public void methodBefore(JoinPoint joinPoint) {
		// 打印请求内容
		log.info("===============请求内容===============");
		log.info("请求地址:" + request.getRequestURL().toString());
		log.info("请求方式:" + request.getMethod());
		log.info("请求类方法:" + joinPoint.getSignature());
		log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
		log.info("===============请求内容===============");
	}

	// 在方法执行完结后打印返回内容
	@AfterReturning(returning = "o", pointcut = "operateAspect()")
	public void methodAfterReturing(Object o) {
		log.info("--------------返回内容----------------");
		log.info("Response内容:" + gson.toJson(o));
		log.info("--------------返回内容----------------");
	}

	/**
	 * 操作动作方法前执行此方法
	 */
	@Before(value = "operateAspect()")
	public void operateBefore() {
		// 记录操作用户，操作开始时间
		System.out.println("操作动作前已拦截。。。。。。。。");

	}

	/**
	 * 操作动作方法后执行此方法
	 */
	@After(value = "operateAspect()")
	public void operateAfter() {
		// 记录操作用户，操作时间，操作动作，操作结果
		System.out.println("操作动作后已拦截。。。。。。。。");
	}

	/**
	 *  异常通知 记录操作报错日志     
	 * 
	 * @param joinPoint
	 *              
	 * @param e
	 *                
	 */
	@AfterThrowing(pointcut = "operateAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.info("进入日志切面异常通知!!");
		log.info("异常信息:" + e.getMessage());
	}

	@AfterReturning(value = "query()", returning = "rvt")
	public void queryLog(JoinPoint joinPoint, Object rvt) throws IllegalAccessException {
		OperateLogDTO operateLogDTO = new OperateLogDTO();
		
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = joinPoint.getSignature().getName();
//		Object[] params = joinPoint.getArgs();
//		String url = request.getRequestURL().toString();
		UserVO user = new UserVO();
		//Object attribute = request.getSession().getAttribute(com.koron.util.Constant.LOGIN_USER);
		String servletPath = request.getServletPath();
		String tenantID = servletPath.split("/")[1];
		Object attribute = null;
		if(SessionUtil.redisUtil !=null){
			attribute = SessionUtil.redisUtil.getHashValue(tenantID+"_"+request.getSession().getId(), Constant.LOGIN_USER);
		}
		if (attribute != null) {
			user = (UserVO) attribute;
		}
		
		MethodSignature sign = (MethodSignature) joinPoint.getSignature();
		Method method = sign.getMethod();
		OperateAspect annotation = method.getAnnotation(OperateAspect.class);
		if(annotation == null) {
			return ; 
		}
		String operateModule = annotation.operateModule();
		
		String resultStr = rvt.toString();
		String[] split = resultStr.split(",");
		String[] split1 = split[1].split(":");
		String result = split1[1].replace("\"", "");
		
		operateLogDTO.setCreateBy(user.getLoginName());
		operateLogDTO.setUpdateBy(user.getLoginName());
		operateLogDTO.setOperateModuleNo(operateModule);
		operateLogDTO.setOperateType("L102120005");
		operateLogDTO.setOperateUserCode(user.getCode());
		operateLogDTO.setResult(result);
		ADOConnection.runTask(user.getEnv(),logService, "addOperateLog",Integer.class,operateLogDTO);

	}

	@AfterReturning(value = "add()", returning = "rvt")
	public void addLog(JoinPoint joinPoint,Object rvt) {
		
		OperateLogDTO operateLogDTO = new OperateLogDTO();
		
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = joinPoint.getSignature().getName();
//		Object[] params = joinPoint.getArgs();
//		String url = request.getRequestURL().toString();
		UserVO user = new UserVO();
		//Object attribute = request.getSession().getAttribute(com.koron.util.Constant.LOGIN_USER);
		String servletPath = request.getServletPath();
		String tenantID = servletPath.split("/")[1];
		Object attribute = null;
		if(SessionUtil.redisUtil !=null){
			attribute = SessionUtil.redisUtil.getHashValue(tenantID+"_"+request.getSession().getId(), Constant.LOGIN_USER);
		}
		if (attribute != null) {
			user = (UserVO) attribute;
		}
		
		MethodSignature sign = (MethodSignature) joinPoint.getSignature();
		Method method = sign.getMethod();
		OperateAspect annotation = method.getAnnotation(OperateAspect.class);
		if(annotation == null) {
			return ; 
		}
		String operateModule = annotation.operateModule();
		
		String resultStr = rvt.toString();
		String[] split = resultStr.split(",");
		String[] split1 = split[1].split(":");
		String result = split1[1].replace("\"", "");
		
		operateLogDTO.setCreateBy(user.getLoginName());
		operateLogDTO.setUpdateBy(user.getLoginName());
		operateLogDTO.setOperateModuleNo(operateModule);
		operateLogDTO.setOperateType("L102120002");
		operateLogDTO.setOperateUserCode(user.getCode());
		operateLogDTO.setResult(result);
		ADOConnection.runTask(user.getEnv(),logService, "addOperateLog",Integer.class,operateLogDTO);

	}

	@AfterReturning(value = "update()", returning = "rvt")
	public void updateLog(JoinPoint joinPoint,Object rvt) {
		OperateLogDTO operateLogDTO = new OperateLogDTO();
		
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = joinPoint.getSignature().getName();
//		Object[] params = joinPoint.getArgs();
//		String url = request.getRequestURL().toString();
		UserVO user = new UserVO();
		String servletPath = request.getServletPath();
		String tenantID = servletPath.split("/")[1];
		Object attribute = null;
		if(SessionUtil.redisUtil !=null){
			attribute = SessionUtil.redisUtil.getHashValue(tenantID+"_"+request.getSession().getId(), Constant.LOGIN_USER);
		}
		if (attribute != null) {
			user = (UserVO) attribute;
		}
		
		MethodSignature sign = (MethodSignature) joinPoint.getSignature();
		Method method = sign.getMethod();
		OperateAspect annotation = method.getAnnotation(OperateAspect.class);
		if(annotation == null) {
			return ; 
		}
		String operateModule = annotation.operateModule();
		
		String resultStr = rvt.toString();
		String[] split = resultStr.split(",");
		String[] split1 = split[1].split(":");
		String result = split1[1].replace("\"", "");
		
		operateLogDTO.setCreateBy(user.getLoginName());
		operateLogDTO.setUpdateBy(user.getLoginName());
		operateLogDTO.setOperateModuleNo(operateModule);
		operateLogDTO.setOperateType("L102120004");
		operateLogDTO.setOperateUserCode(user.getCode());
		operateLogDTO.setResult(result);
		ADOConnection.runTask(user.getEnv(),logService, "addOperateLog",Integer.class,operateLogDTO);
	}

	@AfterReturning(value = "delete()", returning = "rvt")
	public void deleteLog(JoinPoint joinPoint,Object rvt) {
		OperateLogDTO operateLogDTO = new OperateLogDTO();
		
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = joinPoint.getSignature().getName();
//		Object[] params = joinPoint.getArgs();
//		String url = request.getRequestURL().toString();
		UserVO user = new UserVO();
		//Object attribute = request.getSession().getAttribute(com.koron.util.Constant.LOGIN_USER);
		String servletPath = request.getServletPath();
		String tenantID = servletPath.split("/")[1];
		Object attribute = null;
		if(SessionUtil.redisUtil !=null){
			attribute = SessionUtil.redisUtil.getHashValue(tenantID+"_"+request.getSession().getId(), Constant.LOGIN_USER);
		}
		if (attribute != null) {
			user = (UserVO) attribute;
		}
		
		MethodSignature sign = (MethodSignature) joinPoint.getSignature();
		Method method = sign.getMethod();
		OperateAspect annotation = method.getAnnotation(OperateAspect.class);
		if(annotation == null) {
			return ; 
		}
		String operateModule = annotation.operateModule();
		
		String resultStr = rvt.toString();
		String[] split = resultStr.split(",");
		String[] split1 = split[1].split(":");
		String result = split1[1].replace("\"", "");
		
		operateLogDTO.setCreateBy(user.getLoginName());
		operateLogDTO.setUpdateBy(user.getLoginName());
		operateLogDTO.setOperateModuleNo(operateModule);
		operateLogDTO.setOperateType("L102120003");
		operateLogDTO.setOperateUserCode(user.getCode());
		operateLogDTO.setResult(result);
		ADOConnection.runTask(user.getEnv(),logService, "addOperateLog",Integer.class,operateLogDTO);

	}

	@AfterReturning(value = "download()", returning = "rvt")
	public void downloadLog(JoinPoint joinPoint,Object rvt) {
		
		OperateLogDTO operateLogDTO = new OperateLogDTO();
		
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = joinPoint.getSignature().getName();
//		Object[] params = joinPoint.getArgs();
//		String url = request.getRequestURL().toString();
		UserVO user = new UserVO();
		//Object attribute = request.getSession().getAttribute(com.koron.util.Constant.LOGIN_USER);
		String servletPath = request.getServletPath();
		String tenantID = servletPath.split("/")[1];
		Object attribute = null;
		if(SessionUtil.redisUtil !=null){
			attribute = SessionUtil.redisUtil.getHashValue(tenantID+"_"+request.getSession().getId(), Constant.LOGIN_USER);
		}
		if (attribute != null) {
			user = (UserVO) attribute;
		}
		
		MethodSignature sign = (MethodSignature) joinPoint.getSignature();
		Method method = sign.getMethod();
		OperateAspect annotation = method.getAnnotation(OperateAspect.class);
		String result = "";
		if(annotation == null) {
			return ; 
		}
		String operateModule = annotation.operateModule();
		if(rvt != null) {
			ResponseEntity responseEntity = (ResponseEntity) rvt;
			HttpStatus statusCode = responseEntity.getStatusCode();
			if(statusCode.value() == 200) {
				result = "下载成功";
			}else {
				result = "下载失败";
			}
		}
		
//		String resultStr = rvt.toString();
//		String[] split = resultStr.split(",");
//		String[] split1 = split[1].split(":");
//		String result = split1[1].replace("\"", "");
		
		operateLogDTO.setCreateBy(user.getLoginName());
		operateLogDTO.setUpdateBy(user.getLoginName());
		operateLogDTO.setOperateModuleNo(operateModule);
		operateLogDTO.setOperateType("L102120006");
		operateLogDTO.setOperateUserCode(user.getCode());
		operateLogDTO.setResult(result);
		ADOConnection.runTask(user.getEnv(),logService, "addOperateLog",Integer.class,operateLogDTO);

	}
}
