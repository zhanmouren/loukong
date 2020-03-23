package com.koron.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.util.field.FieldBean;
import org.koron.ebs.util.field.FieldBeanProcessor;

import com.google.gson.Gson;
import com.koron.common.bean.DefineFieldBean;
import com.koron.common.bean.LayerBean;
import com.koron.common.mapper.UserDefineMapper;

public class FieldJSFunction implements Function {
	@Override
	public Object call(Object[] arg, Context arg1) {
		if (arg == null || arg.length == 0)
			return null;
		if (arg[0] instanceof FieldBean) {
			if (arg.length == 1)
				return FieldBeanProcessor.newInstance().html((FieldBean) arg[0],
						((FieldBean) arg[0]).getParam("defaultValue"));
			else if (arg.length == 2)
				return FieldBeanProcessor.newInstance().html((FieldBean) arg[0], arg[1]);
		} else if (arg[0] instanceof String) {
			DefineFieldBean bean = null;
			String[] str = String.valueOf(arg[0]).split("\\.");
			if(str.length == 0 ) return "";
			String fieldName = str[str.length-1];
			SessionFactory factory = new SessionFactory();
			UserDefineMapper mapper = factory.getMapper(UserDefineMapper.class);
			int layerId = -1;
			for (int i = 0; i < str.length-1; i++) {
				LayerBean tmp = mapper.getLayerBeanByNamePid(str[i], layerId);
				layerId = tmp.getId();
			}
			bean = mapper.getFieldBean(layerId, fieldName);
			factory.close();
			if (bean != null){
//				//语言包转换
//				bean.setCaption(LanguageDeal.getLanguage(arg1, bean.getCaption()));
//				bean.setDesc(LanguageDeal.getLanguage(arg1, bean.getDesc()));
				return new Gson().toJson(bean);
			}
		}
		return "";
	}
}
