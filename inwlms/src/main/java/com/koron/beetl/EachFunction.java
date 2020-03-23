package com.koron.beetl;

import java.util.Collection;
import java.util.Map.Entry;

import org.beetl.core.Configuration;
import org.beetl.core.Context;
import org.beetl.core.Function;
/**
 * 高级循环功能,支持对集合的循环
 * @author swan
 *
 */
public class EachFunction implements Function {
	/**
	 * <pre>
	 * args参数依次为： 
	 * 0:循环使用的数据 
	 * 1:循环所做的事情 
	 * 2:循环每项的参数，缺省为item
	 * 3:分隔符(循环时会加上此分隔符)
	 * 4:前缀(当返回的字符串不为空时，会自动加上前缀)
	 * 5:后缀(当返回的字符串不为空时，会自动加上后缀)
	 * 6:引入的变量 
	 * 7:引入变量对应的字符串
	 * 6-7可以循环
	 * </pre>
	 */
	@Override
	public Object call(Object[] args, Context ctx) {
		if (args == null)
			return "";
		if (args.length < 2) {
			return "";
		}
		if (args[0] == null || args[1] == null) {
			return "";
		}
		String item = "item";
		if (args.length > 2 && args[2] != null) {
			item = args[2].toString();
		}
		String separator = "";
		if (args.length > 3 && args[3] != null) {
			separator = args[3].toString();
		}
		String prefix = "";
		if (args.length > 4 && args[4] != null) {
			prefix = args[4].toString();
		}
		String suffix = "";
		if (args.length > 5 && args[5] != null) {
			suffix = args[5].toString();
		}
		
		BeetlTool tool = BeetlTool.getIntance();
		Configuration cfg = ctx.gt.getConf();
		tool.getCfg().getFnMap().putAll(cfg.getFnMap());
		tool.getCfg().getFormatMap().putAll(cfg.getFormatMap());
		for (Entry<String, Object> entry : ctx.globalVar.entrySet()) {
			tool.put(entry.getKey(), entry.getValue());
		}
		tool.getCfg().setPlaceholderStart("`");
		tool.getCfg().setPlaceholderEnd("`");
		//导入参数
		int i = 7;
		while (args.length >= i) {
			if (args[i] != null && !args[i].equals("")) {
				tool.put(args[i].toString(), args[i - 1]);
			}
			i += 2;
		}
		
		StringBuilder sb = new StringBuilder();
		if (Collection.class.isAssignableFrom(args[0].getClass())) {
			int offset = 0;
			for (Object object : (Collection<?>) args[0]) {
				tool.put(item, object);
				if (offset != 0)
					sb.append(separator);
				sb.append(tool.render(args[1].toString()));
				offset++;
			}
		} else if (args[0].getClass().isArray()) {
			int offset = 0;
			for (Object object : toArray(args[0])) {
				tool.put(item, object);
				if (offset != 0)
					sb.append(separator);
				sb.append(tool.render(args[1].toString()));
				offset++;
			}
		} else {
			return "";
		}
		if (!sb.toString().trim().equals(""))
		{
			sb.insert(0, prefix);
			sb.append(suffix);
		}
		return sb.toString();
	}

	private Object[] toArray(Object o) {
		if (o != null && o.getClass().isArray()) {
			if (o instanceof int[]) {
				Integer[] array = new Integer[((int[]) o).length];
				for (int i = 0; i < array.length; i++) {
					array[i] = ((int[]) o)[i];
				}
				return array;
			} else if (o instanceof byte[]) {
				Byte[] array = new Byte[((byte[]) o).length];
				for (int i = 0; i < array.length; i++) {
					array[i] = ((byte[]) o)[i];
				}
				return array;
			} else if (o instanceof long[]) {
				Long[] array = new Long[((long[]) o).length];
				for (int i = 0; i < array.length; i++) {
					array[i] = ((long[]) o)[i];
				}
				return array;
			} else if (o instanceof short[]) {
				Short[] array = new Short[((short[]) o).length];
				for (int i = 0; i < array.length; i++) {
					array[i] = ((short[]) o)[i];
				}
				return array;
			} else if (o instanceof boolean[]) {
				Boolean[] array = new Boolean[((boolean[]) o).length];
				for (int i = 0; i < array.length; i++) {
					array[i] = ((boolean[]) o)[i];
				}
				return array;
			} else if (o instanceof double[]) {
				Double[] array = new Double[((double[]) o).length];
				for (int i = 0; i < array.length; i++) {
					array[i] = ((double[]) o)[i];
				}
				return array;
			} else if (o instanceof float[]) {
				Float[] array = new Float[((float[]) o).length];
				for (int i = 0; i < array.length; i++) {
					array[i] = ((float[]) o)[i];
				}
				return array;
			} else if (o instanceof char[]) {
				Character[] array = new Character[((char[]) o).length];
				for (int i = 0; i < array.length; i++) {
					array[i] = ((char[]) o)[i];
				}
				return array;
			} else if (o instanceof Object[]) {
				return (Object[]) o;
			}
		}
		return new Object[0];
	}

	public static void main(String[] args) {
		BeetlTool tool = new BeetlTool();
		tool.putFunction("each", new EachFunction());
		tool.put("data", new String[] {"2","3"});//{ "IBM", "DELL", "联想", "HP" });
		tool.put("pf","[");
		tool.put("sf","]");
		System.out.println(tool.render("${each(data,'`item`')}"));
		System.out.println(tool.render("${each(data,'公司名 `item`',null,',')}"));
		System.out.println(tool.render("${each(data,'`item`',null,'-')}"));
		System.out.println(tool.render("${each(data,'`pf`field`sf`=`item`',null,' and ',' where (',')')}"));
		System.out.println(tool.render("${date(),'yyyy-MM-dd'}"));
	}
}