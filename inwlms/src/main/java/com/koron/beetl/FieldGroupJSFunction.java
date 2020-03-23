package com.koron.beetl;

import java.util.List;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.util.field.FieldBean;
import org.koron.ebs.util.field.FieldBeanProcessor;

import com.google.gson.Gson;
import com.koron.common.bean.DefineFieldBean;
import com.koron.common.bean.LayerBean;
import com.koron.common.mapper.UserDefineMapper;

public class FieldGroupJSFunction implements Function {
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
			String[] str = String.valueOf(arg[0]).split("\\.");
			if(str.length == 0 ) return "";
			SessionFactory factory = new SessionFactory();
			UserDefineMapper mapper = factory.getMapper(UserDefineMapper.class);
			int layerId = -1;
			for (int i = 0; i < str.length; i++) {
				LayerBean tmp = mapper.getLayerBeanByNamePid(str[i], layerId);
				layerId = tmp.getId();
			}
			List<DefineFieldBean> beans= mapper.getFieldBeans(layerId);		
			factory.close();
			StringBuilder result= new StringBuilder();
			Gson gson=new Gson();
			if (beans != null){
				for (DefineFieldBean bean : beans) {
//					语言包转换
//					bean.setCaption(LanguageDeal.getLanguage(arg1, bean.getCaption()));
//					bean.setDesc(LanguageDeal.getLanguage(arg1, bean.getDesc()));
					result.append(bean.getName()+":new FieldBean("+gson.toJson(bean)+"),");
				}
				if(result.length()>0){
					return	result.substring(0, result.length()-1);
				}
			}
		}
		return "";
	}
}
