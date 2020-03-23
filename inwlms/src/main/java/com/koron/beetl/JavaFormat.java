package com.koron.beetl;

import org.beetl.core.Format;
/**
 * 把字符串转换成JAVA格式
 * @author swan
 *
 */
public class JavaFormat implements Format {
	@Override
	public Object format(Object arg0, String str) {
		if(arg0==null)
			return arg0;
		if (arg0 instanceof String) {
			String ret = arg0.toString().replaceAll("\\\\","\\\\\\\\");
			ret = ret.replaceAll("\\\"", "\\\\\\\"");
			ret = ret.replace("\r", "\\r");
			ret = ret.replace("\n", "\\n");
			return ret;
		}
		return arg0;
	}
}