package com.koron.common.web.service;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.common.web.mapper.VariableMapper;

public class VariableService {
	static {
		try(SessionFactory factory = new SessionFactory()){
			VariableMapper mapper =  factory.getMapper(VariableMapper.class);
			mapper.init();
		}
	}
	/**
	 * 获取参数.
	 * 如果参数未设置，返回空字符串
	 * @param name 参数，可按层级添加
	 * @return 属性对应的值
	 */
	public static final String getVariable(String...name) {
		return getVariableWithDefault("",name);
	}
	/**
	 * 获取参数
	 * @param defaultValue 缺省值
	 * @param name 参数
	 * @return 属性对应的值
	 */
	public static final String getVariableWithDefault(String defaultValue,String... name) {
		if(name == null || name.length == 0)
			return defaultValue;
		try(SessionFactory factory = new SessionFactory()){
			VariableMapper mapper =  factory.getMapper(VariableMapper.class);
			String value = mapper.getVariable(String.join(".",name));
			if(value == null) {
//				mapper.addVariable(String.join(".",name), defaultValue);
				return defaultValue;
			}else
				return value;
		}
	}
	/**
	 * 设置系统参数
	 * @param value 值
	 * @param name 参数，可按层级添加，不可包含.。
	 */
	public static final void setVariable(String value,String... name) {
		if(name == null || name.length == 0)
			return ;
		try(SessionFactory factory = new SessionFactory()){
			VariableMapper mapper =  factory.getMapper(VariableMapper.class);
			String value2 = mapper.getVariable(String.join(".",name));
			if(value2 != null) {
				mapper.setVariable(String.join(".",name), value);
			}else
				mapper.addVariable(String.join(".",name), value);
		}
	}
}
