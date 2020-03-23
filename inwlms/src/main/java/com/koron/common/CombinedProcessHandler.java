package com.koron.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.apache.log4j.Logger;
import org.koron.ebs.util.field.*;

public class CombinedProcessHandler implements FieldBeanProcessHandler {
	/**
	 * 缺省的处理器映射的句柄
	 */
	public static final String DEFAULT = "_default";
	/**
	 * 用存储主题同处理器的映射句柄
	 */
	private final static HashMap<String, Class<? extends FieldBeanProcessHandler>> map = new HashMap<>();
	/**
	 * 日志句柄
	 */
	private static final Logger logger = Logger.getLogger(CombinedProcessHandler.class);
	/**
	 * 当前事务处理器
	 */
	private final FieldBeanProcessor processor;
	static {
		map.put(DEFAULT, KoronProcessHandler.class);
		
	}

	public CombinedProcessHandler(FieldBeanProcessor processor) {
		this.processor = processor;
	}

	@Override
	public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
		String theme = bean.getParam("theme");
		if (theme == null || theme.trim().isEmpty()) {
			theme = DEFAULT;
		}
		Class<? extends FieldBeanProcessHandler> clazz = map.get(theme);
		if (clazz == null)
			clazz = map.get(DEFAULT);
		try {
			Constructor<? extends FieldBeanProcessHandler> constructor = clazz.getConstructor(FieldBeanProcessor.class);
			if (constructor == null)// 无构造函数
			{
				return new HtmlTag("div");
			}
			FieldBeanProcessHandler handler = constructor.newInstance(processor);
			return handler.handle(key, value, bean, tag, data);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.debug(e.getMessage());
		}
		return new HtmlTag("div");
	}

	@Override
	public Set<String> keys() {
		String[] str = new String[] { "flag", "name", "desc", "className", "validate", "param_attr", "param_css", "param_url", "param_data", "param_enum",
				"param_option", "caption", "value", "param_wrap", "param_reparse", "param__PATTERN" };
		LinkedHashSet<String> set = new LinkedHashSet<>();
		for (String string : str) {
			set.add(string);
		}
		return set;
	}
}
