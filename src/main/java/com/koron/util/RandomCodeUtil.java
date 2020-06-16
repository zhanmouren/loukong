package com.koron.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class RandomCodeUtil {
	//获取32位的UUID
    public static String getUUID32() {
 	   return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
