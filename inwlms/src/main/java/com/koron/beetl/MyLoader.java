package com.koron.beetl;

import org.beetl.core.Resource;
import org.beetl.core.resource.ClasspathResourceLoader;
/**
 * 随时重载btl模板文件
 * @author 方志文
 *
 */
public class MyLoader  extends ClasspathResourceLoader{

	/* (non-Javadoc)
	 * @see org.beetl.core.resource.ClasspathResourceLoader#isModified(org.beetl.core.Resource)
	 */
	@Override
	public boolean isModified(Resource key) {
		return true;
	}
}
