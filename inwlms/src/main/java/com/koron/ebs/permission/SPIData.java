package com.koron.ebs.permission;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface SPIData {
	String value() default "data";
}
