package com.koron.authority;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented  
public @interface ValidatePermission {
	String value() default "";
}
