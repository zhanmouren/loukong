package com.koron.beetl;

import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;

import org.beetl.core.*;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.koron.ebs.beetl.VarListener;

public class BeetlTool {
	/**
	 * 默认从未处理器
	 */
	public final static ErrorHandler DEFAULT_ERROR_HANDLER = (a, b) -> {
	};
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Format> format = new HashMap<>();
	private HashMap<String, Function> function = new HashMap<>();
	private Configuration cfg = null;
	private ErrorHandler errorHandler = DEFAULT_ERROR_HANDLER;

	public BeetlTool() {
		try {
			cfg = Configuration.defaultConfiguration();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BeetlTool getIntance() {
		BeetlTool tool = new BeetlTool();
		tool.putFormat("http", new HttpFormat());
		tool.putFormat("java", new JavaFormat());
		tool.putFormat("json", new JSonString());
		tool.putFunction("each", new EachFunction());

		return tool;
	}

	public Object put(String key, Object value) {
		return map.put(key, value);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public Object putFormat(String key, Format value) {
		return format.put(key, value);
	}

	public Object removeFormat(String key) {
		return format.remove(key);
	}

	public void clearFormat() {
		format.clear();
	}

	public Object putFunction(String key, Function value) {
		return function.put(key, value);
	}

	public Object remove(String key) {
		return function.remove(key);
	}

	public void clearFunction() {
		function.clear();
	}

	/**
	 * @return the errorHandler
	 */
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public Configuration getCfg() {
		return cfg;
	}

	public void setCfg(Configuration cfg) {
		this.cfg = cfg;
	}

	public void setEngine(String engine) {
		cfg.setEngine(engine);
	}

	public String render(String key, ResourceLoader loader) {
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

		for (Entry<String, Function> ent : function.entrySet()) {
			gt.registerFunction(ent.getKey(), ent.getValue());
		}
		for (Entry<String, Format> ent : format.entrySet()) {
			gt.registerFormat(ent.getKey(), ent.getValue());
		}

		Template t = null;
		if (loader == null)
			t = gt.getTemplate(key);
		else
			t = gt.getTemplate(key, loader);
		if (errorHandler != DEFAULT_ERROR_HANDLER) {
			t.gt.setErrorHandler(errorHandler);
		}
		// t.getCtx().set("VAR_NOT_DEFINED", new RealListener());//TODO

		for (Entry<String, Object> item : map.entrySet()) {
			t.binding(item.getKey(), item.getValue());
		}
		return t.render();
	}

	public void renderTo(String key, ResourceLoader loader, Writer writer) {
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);

		for (Entry<String, Function> ent : function.entrySet()) {
			gt.registerFunction(ent.getKey(), ent.getValue());
		}
		for (Entry<String, Format> ent : format.entrySet()) {
			gt.registerFormat(ent.getKey(), ent.getValue());
		}

		Template t = null;
		if (loader == null)
			t = gt.getTemplate(key);
		else
			t = gt.getTemplate(key, loader);

		for (Entry<String, Object> item : map.entrySet()) {
			t.binding(item.getKey(), item.getValue());
		}
		if (errorHandler != DEFAULT_ERROR_HANDLER) {
			t.gt.setErrorHandler(errorHandler);
		}
		t.renderTo(writer);
	}

	public void renderTo(String key, ResourceLoader loader, OutputStream out) {
		renderTo(key, loader, new OutputStreamWriter(out));
	}

	public String render(String key) {
		return render(key, null);
	}

	public void renderTo(String key, Writer writer) {
		renderTo(key, null, writer);
	}

	public void renderTo(String key, OutputStream out) {
		renderTo(key, null, out);
	}

	class RealListener implements VarListener {
		@Override
		public Object parse(String arg) {
			System.out.println(arg);
			// HashMap<String,Object> ret = new HashMap<>();
			// HashMap<String,String> aa = new HashMap<>();
			// ret.put("aa", aa);
			// aa.put("bb","曹操");
			return null;
		}
	}

	// public static void main(String[] args) {
	// BeetlTool tool = new BeetlTool();
	// tool.setEngine("org.koron.ebs.beetl.MyEngine");
	// String str = tool.render("${who.aa.bb} am i");
	// System.out.println(str);
	// }
}