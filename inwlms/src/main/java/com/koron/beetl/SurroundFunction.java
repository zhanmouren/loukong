package com.koron.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.koron.ebs.util.Utils;
/**
 * 字符串加上前后缀.
 * 当字符串为空时不加下前缀后缀.
 * 最多四个参数，第一个为值，第二个为前缀，第三个为后缀，第四个为判断前是否要把内容前后的空格去掉,缺省true
 * @author swan
 */
public class SurroundFunction implements Function{
	@Override
	public Object call(Object[] arg, Context arg1) {
		if(arg == null || arg.length == 0 || arg[0] == null) return "";
		String str = arg[0].toString();
		String prefix = "";
		if(arg.length > 1)
			prefix = Utils.isNull(arg[1], "").toString();
		String surfix = "";
		if(arg.length > 2)
		surfix = Utils.isNull(arg[2], "").toString();
		boolean willTrim = true;
		if(arg.length > 3)
			Boolean.parseBoolean(Utils.isNull(arg[2], "true").toString());
		str = willTrim?str.trim():str;
		return str.equals("")?"":prefix+str+surfix;
	}
}
