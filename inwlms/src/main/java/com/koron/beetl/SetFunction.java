package com.koron.beetl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.apache.commons.beanutils.PropertyUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * List compare
 * 
 * @author swan
 *
 */
public class SetFunction implements Function {
	@Override
	public Object call(Object[] arg, Context arg1) {
		if (arg == null || arg.length < 2)
			return new TreeSet<Object>();
		if (!(arg[0] instanceof Collection)) {
			return new TreeSet<Object>();
		}

		Collection data = (Collection) arg[0];
		String[] str = new String[arg.length - 1];
		System.arraycopy(arg, 1, str, 0, str.length);
		Comparator<Object> c = new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				if (o2 == null)
					return 1;
				try {
					for (String key : str) {
						Object value1 = PropertyUtils.getProperty(o1, key);
						Object value2 = PropertyUtils.getProperty(o2, key);
						if (value1.equals(value2))
							continue;
						else
							return String.valueOf(value1).compareTo(String.valueOf(value2.toString()));
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
					ex.printStackTrace();
				}
				return 0;
			}
		};
		Set s = new TreeSet<Object>(c);
		for (Object object : data) {
			s.add(object);
		}
		return s;
	}
}
