package com.koron.common;

import java.util.Map.Entry;

import org.koron.ebs.util.field.EnumElement;
import org.koron.ebs.util.field.FieldBean;
import org.koron.ebs.util.field.FieldBeanFilter;

import com.koron.common.KoronFieldBeanProcessor;
import com.koron.util.Tools;

public class CaptionFieldBeanFilter implements FieldBeanFilter{
	@Override
	public FieldBean filter(FieldBean bean) {
		//获取单位说明
		String appendix = null;
		if(bean.getParam("measure")!=null)
		{
			String measure = bean.getParam("measure");
			if(measure.indexOf(',')!=-1)
			{
				measure = measure.substring(0,measure.indexOf(','));
			}
			EnumElement<Object> element = Tools.getEnumByKey(measure);
			if(element.getItem()!=null && element.getItem().size()!=0)
			{
				for (Entry<Object, String> ent: element.getItem().entrySet()) {
					appendix = ent.getValue();
					break;
				}
			}
		}
		if(appendix!=null)
		{
			appendix = appendix.replaceAll("\\$\\{([a-zA-Z0-9._]*)\\}", "\\\\$0");
		}
		bean.setParam(KoronFieldBeanProcessor.PATTERN,"<td width=\"${bean.length}\" ${decode(@bean.getParam(\"colspan\"),null,\"\",\"colspan=\"+@bean.getParam(\"colspan\"))}> ${bean.caption}"+(appendix==null?"":"("+appendix+")")+"</td>");
		return bean;
	}
}
