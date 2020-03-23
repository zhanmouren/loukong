package com.koron.beetl;

import java.util.Map.Entry;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.koron.ebs.util.field.EnumElement;

import com.koron.util.Tools;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swan
 */
public class EnumFunction implements Function {
	
	@Override
	public Object call(Object[] arg, Context ctx) {
		 EnumElement<Object> obj = Tools.getEnumByKey(String.valueOf(arg[0]));
		 if(obj == null) return null;
		 if (arg.length > 1) {
			 StringBuffer sb = new StringBuffer();
			 if(obj.isBit())
			 {
				 Long value = Long.parseLong(String.valueOf(arg[1]));
				 //0字符串1整数2长型
				 for (Entry<Object,String> entry : obj.getItem().entrySet()) {
					 Long l = Long.parseLong(String.valueOf(entry.getKey()));
					 if((value & l) != 0)
						 sb.append(entry.getValue()).append(',');
				 }
			 }else
			 {
				 for (Entry<Object,String> entry : obj.getItem().entrySet()) {
					 if(String.valueOf(arg[1]).equals(String.valueOf(entry.getKey())))
					 {
						 sb.append(entry.getValue());
						 break;
					 }
				 }
			 }
			 if(sb.length() > 1 && sb.charAt(sb.length()-1) == ',')
				 sb.deleteCharAt(sb.length()-1);
			 return sb.toString();
		 } else {
			 return obj.getItem();
		 }
	}
}