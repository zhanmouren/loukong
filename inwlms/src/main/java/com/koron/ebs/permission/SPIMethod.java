package com.koron.ebs.permission;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SPIMethod {
	/**<pre>
	 * 操作名称.
	 * 用.进行操作分隔
	 * </pre>
	 * @return
	 */
	String value();
}
