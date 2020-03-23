package com.koron.common.web.util;

import org.swan.bean.MessageBean;
import org.swan.excel.Excel;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.koron.ebs.util.field.EnumElement;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpEntity;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.koron.beetl.FieldGroupFunction;
import com.koron.common.bean.CrossBean;
import com.koron.common.bean.DefineFieldBean;
import com.koron.common.bean.IdentityBean;
import com.koron.common.bean.query.BaseQueryBean;
import com.koron.util.Constant;
import com.koron.util.Tools;

/**
 * 实现基础的CRUD功能
 * 
 * @author swan
 * 
 */
public class CRUD {
}
//	
//	/**
//	 * 列表方式展示数据列表
//	 * 
//	 * @param crud 实现CRUD接口
//	 * @param bean 查询BEAN
//	 * @param tag 执行方法的标签,参看{@link TaskAnnotation}
//	 * @return spring视图
//	 */
//	public static final ModelAndView list(ICrud crud, BaseQueryBean bean, String tag, String... param) {
//		String viewName = null;
//		String[] layers = new String[9];
//		if (param != null) {
//			for (int i = 0; i < param.length; i++) {
//				if (param[i] == null || param[i].isEmpty())
//					continue;
//				if (i == 0)
//					viewName = param[i];
//				else if (i > 0 && i < 10)
//					layers[i - 1] = param[i];
//			}
//		}
//
//		ModelAndView view = Tools.getView(viewName == null ? (crud.getActionKey() + ".btl") : viewName, crud.getClass());
//		try (SessionFactory factory = new SessionFactory()) {
//			List<?> list = factory.runTask(crud, tag, List.class, bean, view);
//			view.addObject("datalist", list);
//			view.addObject("condition", new Gson().toJson(bean));
//
//			view.addObject("layer", FieldGroupFunction.getFieldBean(crud.getLayer()));
//			for (int i = 1; i < layers.length; i++) {
//				if (layers[i] == null)
//					continue;
//				view.addObject("layer" + i, FieldGroupFunction.getFieldBean(layers[i]));
//			}
//			view.addObject("flag", new HashMap<String, String>());
//
//			return view;
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public static final ModelAndView cross(ICrud crud, BaseQueryBean bean, String tag, CrossConfigBean config) {
//		ModelAndView view = Tools.getView(crud.getActionKey() + ".btl", crud.getClass());
//		view.addObject("flag", new HashMap<String, String>());
//		try (SessionFactory factory = new SessionFactory()) {
//			HashMap<String, Object> map = cross(factory.runTask(crud, tag, List.class, bean, view), config, crud.getLayer());
//			view.addObject("datalist", map.get("datalist"));
//			view.addObject("layer", map.get("layer"));
//			view.addObject("condition", new Gson().toJson(bean));
//			return view;
//		}
//	}
//
//	/**
//	 * AJAX方式获取数据列表
//	 * 
//	 * @param crud 实现CRUD接口
//	 * @param bean 查询BEAN
//	 * @param tag 执行方法的标签,参看{@link TaskAnnotation}
//	 * @return json数据
//	 */
//	public static final String listajax(ICrud crud, BaseQueryBean bean, String tag) {
//		ModelAndView view = new ModelAndView();
//		try (SessionFactory factory = new SessionFactory()) {
//			List<?> list = factory.runTask(crud, tag, List.class, bean, view);
//			List<Object> data = new ArrayList<>();
//			String layer = crud.getLayer();
//			FieldGroupFunction function = new FieldGroupFunction();
//			for (Object o : list) {
//				data.add(function.call(new Object[] { layer, o }, null));
//			}
//			HashMap<String, Object> map = new HashMap<>();
//			map.putAll(view.getModel());
//			map.put("datalist", data);
//			map.put("data", list);
//			map.put("condition", bean);
//			map.put("code", "0");
//			return new Gson().toJson(map);
//		}
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static final String listajaxCross(ICrud crud, BaseQueryBean bean, String tag, CrossConfigBean config) {
//		try (SessionFactory factory = new SessionFactory()) {
//			List<CrossBean> list = factory.runTask(crud, tag, List.class, bean, null);
//			List<Object> data = new ArrayList<>();
//			HashMap<String, Object> crossMap = cross(list, config, crud.getLayer());
//
//			FieldGroupFunction function = new FieldGroupFunction();
//			for (Object o : (Collection) crossMap.get("datalist")) {
//				data.add(function.call(new Object[] { crossMap.get("layer"), o }, null));
//			}
//
//			HashMap<String, Object> map = new HashMap<>();
//			map.put("datalist", data);
//			map.put("data", crossMap.get("datalist"));
//			map.put("condition", bean);
//			return new Gson().toJson(map);
//		}
//	}
//
//	/**
//	 * 获取明细功能
//	 * 
//	 * @param crud 实现CRUD接口
//	 * @param pojo 查询BEAN
//	 * @param tag 执行方法的标签,参看{@link TaskAnnotation}
//	 * @param param 参数,可为null,第一个为视图名，第2-11个为层别
//	 * @return spring视图
//	 */
//	public static final ModelAndView detail(ICrud crud, IdentityBean pojo, String tag, String... param) {
//		String viewName = null;
//		String[] layers = new String[9];
//		if (param != null) {
//			for (int i = 0; i < param.length; i++) {
//				if (param[i] == null || param[i].isEmpty())
//					continue;
//				if (i == 0)
//					viewName = param[i];
//				else if (i > 0 && i < 10)
//					layers[i - 1] = param[i];
//			}
//		}
//		ModelAndView view = Tools.getView(viewName == null ? (crud.getActionKey() + "pre.btl") : viewName, crud.getClass());
//		Object ret = pojo;
//		if (pojo.getId() != -1) {
//			ret = new SessionFactory().runTask(crud, tag, pojo.getClass(), pojo, view);
//		}
//		view.addObject("bean", ret);
//		view.addObject("flag", new HashMap<String, String>());
//		for (int i = 1; i < layers.length; i++) {
//			if (layers[i] == null)
//				continue;
//			view.addObject("layer" + i, FieldGroupFunction.getFieldBean(layers[i]));
//		}
//		return view;
//	}
//
//	/**
//	 * <pre>
//	 * 存盘功能 
//	 * id为-1则insert,否则update
//	 * </pre>
//	 * 
//	 * @param crud crud接口
//	 * @param pojo 包含ID的POJO对象
//	 * @param insertTag 执行插入的标签,参看{@link TaskAnnotation}
//	 * @param updateTag 执行更新的标签,参看{@link TaskAnnotation}
//	 * @return 消息BEAN
//	 */
//	public static final MessageBean<Integer> save(ICrud crud, IdentityBean pojo, String insertTag, String updateTag) {
//		Integer ret = 0;
//		String item = "添加";
//		if (pojo.getId() == -1) {
//			ret = new SessionFactory().runTask(crud, insertTag, Integer.class, pojo);
//		} else {
//			ret = new SessionFactory().runTask(crud, updateTag, Integer.class, pojo);
//			item = "修改";
//		}
//		MessageBean<Integer> msg = new MessageBean<Integer>();
//		msg.setData(ret);
//		if (ret == null) {
//			msg.setCode(Constant.MESSAGE_DBFAIL);
//			msg.setDescription("数据" + item + "失败。");
//		} else if (ret > 0) {
//			msg.setCode(0);
//			msg.setDescription("已" + item + ret + "条数据。");
//		} else {
//			msg.setCode(Constant.MESSAGE_DBFAIL);
//			msg.setDescription("数据" + item + "失败。");
//		}
//		return msg;
//	}
//
//	/**
//	 * 直接执行某个写操作(insert delete updae)
//	 * 
//	 * @param crud crud接口
//	 * @param pojo 包含ID的POJO对象
//	 * @param tag 执行方法的标签,参看{@link TaskAnnotation}
//	 * @return 消息BEAN
//	 */
//	public static final MessageBean<Integer> execute(ICrud crud, Object[] pojo, String tag, String item) {
//		Integer ret = 0;
//		try (SessionFactory factory = new SessionFactory()) {
//			ret = factory.runTask(crud, tag, Integer.class, Arrays.asList(pojo));
//			MessageBean<Integer> msg = new MessageBean<Integer>();
//			msg.setData(ret);
//			if (ret == null) {
//				msg.setCode(Constant.MESSAGE_DBFAIL);
//				msg.setDescription("数据" + item + "失败。");
//			} else if (ret > 0) {
//				msg.setCode(0);
//				msg.setDescription("已" + item + ret + "条�                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   