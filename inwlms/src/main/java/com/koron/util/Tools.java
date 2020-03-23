package com.koron.util;

import org.swan.bean.MessageBean;

import java.io.File;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.koron.ebs.businessLog.LogBean;
import org.koron.ebs.common.KVBean;
import org.koron.ebs.module.ModuleService;
import org.koron.ebs.mybatis.MybatisInfo;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.koron.ebs.util.field.EnumElement;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.koron.common.bean.EnumDBBean;
import com.koron.common.bean.EnumDetailDBBean;
import com.koron.common.mapper.UserDefineMapper;
import com.koron.ebs.permission.StaffAccount;

public class Tools {
	public static final ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();

	private static ModuleService service = null;

	/**
	 * 获取模块服务组件
	 * 
	 * @return
	 */
	public static final ModuleService getModuleService() {
		if (service == null) {
			WebApplicationContext context2 = WebApplicationContextUtils.getWebApplicationContext(context);
			return (ModuleService) context2.getBean("moduleService");
		}
		return service;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static final SessionFactory getSessionFactory() {
		MessageBean<SessionFactory> msgBean = getModuleService().invoke(MybatisInfo.MODULENAME, "getSessionFactory", SessionFactory.class);
		return msgBean.getCode() == 0 ? msgBean.getData() : null;
	}

	/**
	 * 取得调用该函数的类的包下name对应的文件. 使用 {@link #getView(String, Class)}代替
	 * 
	 * @param name 名称
	 * @param layer 线程中的层数，默认为3
	 * @return
	 */
	@Deprecated
	public static final ModelAndView getView(String name, int layer) {
		StackTraceElement[] els = Thread.currentThread().getStackTrace();
		if (els == null || els.length < layer + 1)
			return new ModelAndView("/" + name);
		StackTraceElement el = els[layer];
		String pkgStr = el.getClassName();
		if (pkgStr.indexOf('.') != -1) {
			pkgStr = pkgStr.substring(0, pkgStr.lastIndexOf('.'));
			return new ModelAndView("/" + pkgStr.replace('.', '/') + '/' + name);
		} else {
			return new ModelAndView("/" + name);
		}
	}

	/**
	 * 取得调用该函数的类的包下name对应的文件. 使用 {@link #getView(String, Class)}代替
	 * 
	 * @param name 名称
	 * @param clazz 对应类
	 * @return 类对应目录下的name文件作为视图
	 */
	public static final ModelAndView getView(String name, Class<?> clazz) {
		String pkgStr = clazz.getPackage().getName();
		return new ModelAndView("/" + pkgStr.replace('.', '/') + '/' + name);
	}

	/**
	 * 取得调用该函数的类的包下name对应的文件
	 * 
	 * @param name 名称
	 * @return
	 */
	public static final ModelAndView getView(String name) {
		return getView(name, 3);
	}

	/**
	 * 获取MD5后的字符串
	 * 
	 * @param source 进行加密的字符串
	 * @return
	 */
	public static String MD5(String source) {
		System.out.println(source);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			byte[] b = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte c : b) {
				int val = ((int) c) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));
			}
			return sb.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static LogBean getLogBean(HttpServletRequest request) {
		LogBean bean = new LogBean();
		bean.setIp(request.getHeader("X-Real-IP"));
		bean.setMacaddress(request.getRemoteHost());
		if (request.getSession().getAttribute(Constant.USER) != null)
			bean.setUsername(((StaffAccount) request.getSession().getAttribute(Constant.USER)).getStaff().getName());
		return bean;
	}

	/**
	 * 获取枚举
	 * 
	 * @param key 枚举的key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final EnumElement<Object> getEnumByKey(String key, Object... parameter) {
		try (SessionFactory factory = new SessionFactory()) {
			return factory.runTask(new Tools(), "getEnum", EnumElement.class, key, parameter);
		}
	}

	/**
	 * 
	 * @param factory
	 * @param key
	 * @return
	 */
	@TaskAnnotation("getEnum")
	private EnumElement<Object> getEnumByKey(SessionFactory factory, String key, Object... parameter) {
		EnumElement<Object> ret = Constant.enumCache.get(key);
		if (parameter == null || parameter.length == 0) {
			if (ret != null)
				return ret;
		}
		Logger.getLogger(this.getClass()).debug("获取指定的枚举值：" + key);
		UserDefineMapper mapper = factory.getMapper(UserDefineMapper.class);
		EnumDBBean bean = mapper.getEnumByKey(key);
		ret = new EnumElement<>();
		ret.setBit(bean.getIsbit());
		ret.setType(bean.getType());
		if (bean.getParam() != null && !bean.getParam().isEmpty()) {
			Map<String, String> map = getDyn(bean.getParam(), parameter);
			for (Entry<String, String> item : map.entrySet()) {
				switch (bean.getType()) {
				case 0:
					ret.put(item.getKey(), item.getValue());
					break;
				case 1:
					ret.put(Integer.parseInt(item.getKey()), item.getValue());
					break;
				case 2:
					ret.put(Long.parseLong(item.getKey()), item.getValue());
				}
			}
		} else {
			List<EnumDetailDBBean> list = mapper.getEnumDetailByEnumId(bean.getId());
			for (EnumDetailDBBean enumDetailDBBean : list) {
				switch (bean.getType()) {
				case 0:
					ret.put(enumDetailDBBean.getKey(), enumDetailDBBean.getValue());
					break;
				case 1:
					ret.put(Integer.parseInt(enumDetailDBBean.getKey()), enumDetailDBBean.getValue());
					break;
				case 2:
					ret.put(Long.parseLong(enumDetailDBBean.getKey()), enumDetailDBBean.getValue());
				}
			}
		}
		if (parameter == null || parameter.length == 0)
			Constant.enumCache.put(key, ret);
		return ret;
	}

	/**
	 * 根据类名#方法的方式获取得KVBean数组，然后转换成map
	 * 
	 * @param url mybatis的接口名加方法名,方法不带参数，返回必须为List<KVBean>
	 * @param parameter 参数，调用接口用的参数
	 * 
	 * @return
	 */
	public static final LinkedHashMap<String, String> getDyn(String url, Object... parameter) {
		if (url == null || url.indexOf('#') == -1)
			return null;
		String className = url.substring(0, url.indexOf('#'));
		String methodName = url.substring(url.indexOf('#') + 1);
		try {
			SessionFactory factory = new SessionFactory();
			Class<?> clazz = Class.forName(className);
			Class<?>[] param = null;
			if (parameter != null) {
				param = new Class[parameter.length];
				for (int i = 0; i < parameter.length; i++) {
					param[i] = parameter[i].getClass();
				}
			}
			Method m = clazz.getMethod(methodName, param);
			@SuppressWarnings("unchecked")
			List<KVBean> list = (List<KVBean>) m.invoke(factory.getMapper(clazz), parameter);
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			for (KVBean kvBean : list) {
				map.put(kvBean.getKey(), kvBean.getValue());
			}
			factory.close();
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成16位key
	 * 
	 * @return
	 */
	public static String get16Key() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		char s;
		String key = "";
		Random ran = new Random();
		for (int i = 0; i < 16; i++) {
			int index = ran.nextInt(32);
			char[] chars = uuid.toCharArray();
			s = chars[index];
			key += s;
		}
		return key.toUpperCase();
	}

	/**
	 * 获取web目录的文件
	 * 
	 * @param path 文件路径
	 * @return
	 */
	public static final File getWebFile(String path) {
		String url = context.getRealPath(path);
		File f = new File(url);
		if (f.exists())
			return f;
		return null;
	}
}