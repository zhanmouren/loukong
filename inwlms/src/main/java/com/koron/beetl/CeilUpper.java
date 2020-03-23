package com.koron.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;
/**
 * beetl函数，把字符串首字母大写
 * @author swan
 *
 */
public class CeilUpper implements Function {
	@Override
	public Object call(Object[] arg, Context arg1) {
		if(arg[0]==null || arg[0].equals(""))
			return arg[0];
		StringBuilder sb = new StringBuilder(arg[0].toString());
		char c = sb.charAt(0);
		c=Character.toUpperCase(c);
		sb.setCharAt(0, c);
		return sb.toString();
	}
}
