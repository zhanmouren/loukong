package org.swan.form;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SwanFormMapper {
	/**
	 * 控件需要的js路径映射
	 */
	public static final HashMap<String, List<String>> keyToUrl = new HashMap<>();
	private static final void add(String key,String value) {
		if(keyToUrl.get(key) == null) {
			keyToUrl.put(key, new ArrayList<String>());
		}
		keyToUrl.get(key).add(value);
	}
	static {
		// 输入框
		add("inputmask", "./addone/inputmask/jquery.inputmask.js");
		//一种布局
		add("iconinput", "./addone/baseform/baseform.css");
		add("fixed-input", "./addone/baseform/fixed-input.css");
		add("padding-layout", "./addone/baseform/layout-style.css");
		add("button", "./addone/baseform/btn.css");
		add("nav", "./addone/baseform/nav.css");
		add("nav", "./addone/baseform/nav.js");
		add("pop", "./addone/baseform/pop.css");
		add("toggle-button", "./addone/baseform/toggle-button.css");
		add("table", "./addone/baseform/table.css");
	}
}
