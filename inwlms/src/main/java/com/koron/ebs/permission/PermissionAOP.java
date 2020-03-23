package com.koron.ebs.permission;

import org.swan.bean.MessageBean;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.koron.common.web.service.VariableService;
import com.koron.ebs.permission.mybatis.SPILoader;
import com.koron.ebs.permission.mybatis.SPIRole;

@Component
@Aspect
public class PermissionAOP {

	private static Logger logger = Logger.getLogger(PermissionAOP.class);

	@Pointcut("@annotation(com.koron.ebs.permission.SPIMethod)")
	public void cut() {
		
	}

	@Around("cut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature sig = (MethodSignature) pjp.getSignature();
		Method method = sig.getMethod();
		SPIMethod methodAnnotation = method.getAnnotation(SPIMethod.class);
		MessageBean<String> ret = MessageBean.create(10001, "无权限", String.class);
		boolean pass = false;
		if (methodAnnotation != null) {
			if (methodAnnotation.value() != null && !methodAnnotation.value().trim().equals(""))// 如果有值则进行权限判断
			{
				String operationString = methodAnnotation.value();
				Permission permission = Permission.getInstance();
				Operation operation = permission.getOperation(operationString);
				if (operation == null && !operationString.equals(Permission.LOGIN_RANGE)) {
					logger.error(operationString + " can't be found.");
					ret.setData(operationString + " can't be found.");
					return convert(ret, method.getReturnType());
				}
				Object[] args = pjp.getArgs();
				// 获取方法的所有参数，找出所有 SPIData SPIAccount注解的参数
				Account account = null;
				Map<String, Object> map = new HashMap<>();

				HashMap<String, Object> roleParam = null;

				int i = 0;
				for (Parameter param : method.getParameters()) {
					SPIAccountAnno obj = param.getAnnotation(SPIAccountAnno.class);
					if (obj != null && Account.class.isAssignableFrom(param.getType()))// 如果账号存在，则给参数赋值
						account = (Account) args[i];
					SPIData data = param.getAnnotation(SPIData.class);
					if (data != null) {
						map.put(data.value(), args[i]);
					}
					SPIInject inject = param.getAnnotation(SPIInject.class);
					if (inject != null && args[i] instanceof HashMap) {
						roleParam = (HashMap) args[i];
					}
					i++;
				}
				if (account == null) {
					logger.error("haven't account");
					ret.setData("You haven't define account.");
					ret.setCode(10000);
					return convert(ret, method.getReturnType());
				}

				SPILoader spi = new SPILoader();
				List<EntityID> roles = spi.getRelation(account.getId(), SPILoader.RELATION_ACCOUNT_ROLE_INT);
				for (EntityID entityID : roles) {
					if (entityID instanceof SPIRole && roleParam != null) {
						HashMap<String, Object> objmap = ((SPIRole) entityID).getMap();
						for (String strkey : objmap.keySet()) {
							if (roleParam.get(strkey) != null) {
								if (roleParam.get(strkey) instanceof List) {
									List<Object> list = (List<Object>) roleParam.get(strkey);
									list.addAll((List<Object>) objmap.get(strkey));
									roleParam.put(strkey, new ArrayList(new HashSet(list)));
								}
							} else {
								roleParam.putAll(objmap);
							}
						}
					}
				}
				if (operation != null)
					pass = Permission.getInstance().test(account, operation, map);
				else
					pass = true;
				if (!pass) {
					logger.error("You haven't" + operationString + " permission.");
					ret.setData("You haven't " + operationString + " permission.");
					return convert(ret, method.getReturnType());
				}
			}
			// 如果不带operation的字符，则不进行权限判断，只是进行参数赋值
		}
		Object object = pjp.proceed();// 执行该方法
		return object;
	}

	private Object convert(MessageBean<?> msg, Class<?> clazz) {
		if (clazz.equals(MessageBean.class))
			return msg;
		if (clazz.equals(String.class))
			return msg.toJson();
		if (clazz.equals(ModelAndView.class)) {
			if (msg.getCode() == 10000) {// 未登录
				String loginMode = VariableService.getVariableWithDefault("local", "system", "login", "mode");
				if (loginMode.toLowerCase().indexOf("local") != -1) {// 本地登录登录
					return new ModelAndView(UrlBasedViewResolver.REDIRECT_URL_PREFIX + "/login.htm");
				} else
					return new ModelAndView("redirect:/cas.htm");
			} else {// 无权限

			}
		}
		return null;
	}
}
