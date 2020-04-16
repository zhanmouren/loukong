package com.koron.inwlms.util;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import org.swan.bean.MessageBean;

/**
 * @author qingfeng
 * @description: 统一返回工具类
 * @date 2019/12/23 18:05
 */
public class ResponseUtil {

    public static String toJson(MessageBean<?> data) {
        return new GsonBuilder()
                .serializeNulls()
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create().toJson(data);
    }

}
