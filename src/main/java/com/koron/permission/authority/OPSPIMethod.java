package com.koron.permission.authority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OPSPIMethod {
	/**<pre>
	 * 操作名称.
	 * 用.进行操作分隔
	 * </pre>
	 * @return
	 */
	String value() default "";
}
