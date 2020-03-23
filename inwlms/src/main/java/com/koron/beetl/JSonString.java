package com.koron.beetl;

import org.beetl.core.Format;

public class JSonString implements Format {
	@Override
	public Object format(Object arg0, String pattern) {
		if(arg0==null)
			return "\"\"";
		if (arg0 instanceof String) {
			StringBuilder sb = new StringBuilder(arg0.toString());
			int pos = sb.length();
			while(pos-- > 0)
			{
				char c = sb.charAt(pos);
				switch (c) {
				case '\n':
					sb.replace(pos, pos+1, "\\n");
					break;
				case '\r':
					sb.replace(pos, pos+1, "\\r");
					break;
				case '\t':
					sb.replace(pos, pos+1, "\\t");
					break;
				case '\\':
					sb.replace(pos, pos+1, "\\\\");
					break;
				case '\"':
					sb.replace(pos, pos+1, "\\\"");
					break;
				default:
					break;
				}
			}
			return "\""+sb.toString()+"\"";
		}else
		return "\""+arg0+"\"";
	}
}