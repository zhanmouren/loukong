package com.koron.beetl;

import java.util.ArrayList;
import java.util.List;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.koron.ebs.mybatis.SessionFactory;

import com.koron.common.bean.DefineFieldBean;
import com.koron.common.bean.LayerBean;
import com.koron.common.mapper.UserDefineMapper;
import com.koron.util.Constant;

public class FieldGroupFunction implements Function {
	@Override
	public Object call(Object[] arg, Context ctx) {
		if (arg == null || arg.length == 0)
			return null;
		List<DefineFieldBean> beans = null;
		if (arg[0] instanceof String) {
			beans = getFieldBean(String.valueOf(arg[0]));
		} else if (arg[0] instanceof List) {
			beans = (List) arg[0];
			List<DefineFieldBean> beans2 = new ArrayList<DefineFieldBean>();
			for (DefineFieldBean bean : beans) {
				beans2.add(bean.clone());
			}
			beans = beans2;
		}
		StringBuilder html = new StringBuilder();
		if (beans != null) {
			for (DefineFieldBean bean : beans) {
				html.append(FieldFunction.beanToHtml(arg, ctx, bean)).append("\n");
			}
			return html.toString();
		}
		return "";
	}

	public static final List<DefineFieldBean> getFieldBean(String layer) {
		Integer layerId = -1;
		layerId = Constant.layerCache.get(layer);// 查找缓存
		try (SessionFactory factory = new SessionFactory();) {
			UserDefineMapper mapper = factory.getMapper(UserDefineMapper.class);
			if (layerId == null) {
				layerId = -1;
				String[] str = String.valueOf(layer).split("\\.");
				if (str.length == 0)
					return null;
				for (int i = 0; i < str.length; i++) {
					LayerBean tmp = mapper.getLayerBeanByNamePid(str[i], layerId);
					if (tmp == null)
						return null;
					layerId = tmp.getId();
				}
				Constant.layerCache.put(layer, layerId);
			}

			List<DefineFieldBean> beans = Constant.layerFieldCache.get(layerId);
			if (beans == null) {
				beans = mapper.getFieldBeans(layerId);
				Constant.layerFieldCache.put(layerId, beans);
			}
			return beans;
		}
	}
}
