package com.koron.ebs.permission;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface SPIInject {
	String value() default "";
}
