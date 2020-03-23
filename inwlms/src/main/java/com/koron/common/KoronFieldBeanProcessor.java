package com.koron.common;

import java.util.HashMap;
import java.util.Map;

import org.koron.ebs.util.field.*;

public class KoronFieldBeanProcessor extends FieldBeanProcessor {
	/**
	 * 处理器的参数
	 */
	private HashMap<String,Object> map = new HashMap<>();
	public Object get(Object key) {
		return map.get(key);
	}

	public Object put(String key, Object value) {
		return map.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		map.putAll(m);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	@Override
	protected HtmlTag image(FieldBean bean, Object value) {
//		DefineFieldBean defineBean = (DefineFieldBean) bean;
//		String defaultValue = Utils.isNull(value, String.valueOf(value), "");
//		String src=FileDeal.GETBYIDURL+defaultValue;
//		if(defaultValue.equals("")){
//			src="/images/example.png";
//		}
//		HtmlTag img = new HtmlTag("img").attr("src", src);
//		if ((defineBean.getFlag() & GCEProcessHandler.FLAG_CONDITION) == 0) {
//			return img;
//		} else {
//			// 编辑框
//			img.attr("onclick", "addPic(this,document.getElementById('"+bean.getName()+"'));");
			HtmlTag input = new HtmlTag("input").attr("id", bean.getName());
			return input;
//			return new HtmlTag("").append(img).append(input);
//		}
	}

	/**
	 * 显示的模板
	 */
	public static final String PATTERN = "_PATTERN";

	/* (non-Javadoc)
	 * @see org.koron.ebs.util.field.FieldBeanProcessor#getHandler()
	 */
	@Override
	protected FieldBeanProcessHandler getHandler() {
		return new CombinedProcessHandler(this);
	}
	
}
