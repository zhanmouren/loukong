package com.koron.beetl;

import org.beetl.core.Format;
/**
 * 把字符串进行XML转换.
 * 对 &amp; &lt; &gt; &sqot; 进行了转换
 * @author swan
 *
 */
public class HttpFormat implements Format {
	@Override
	public Object format(Object arg0, String arg1) {
		if(arg0 == null) return null;
		if (arg0 instanceof String) {
			String tmp = (String) arg0;
			tmp = tmp.replaceAll("&", "&amp;");
			tmp = tmp.replaceAll("<", "&lt;");
			tmp = tmp.replaceAll(">", "&gt;");
			tmp = tmp.replaceAll("\"", "&quot;");
			return tmp;
		}
		return arg0;
	}
}