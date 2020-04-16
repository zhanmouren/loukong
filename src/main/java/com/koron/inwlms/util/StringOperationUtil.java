package com.koron.inwlms.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 深圳科荣软件有限公司
 *
 * @ClassName StringOperationUtil
 * @Description
 * @Author hongxl
 * @Date 2019/10/27 12:39
 */
public class StringOperationUtil {

    /**
     * String转大写
     * @param caseTemp
     * @return
     */
    public static String getUpCase(String caseTemp){
        return StringUtils.isBlank(caseTemp)?null:caseTemp.toUpperCase();
    }

    /**
     * String过滤阿拉伯数字
     * @param value
     * @return
     */
    public static String removeDigital(String value){
        if(StringUtils.isBlank(value)){return null;}
        Pattern p = Pattern.compile("[\\d]");
        Matcher matcher = p.matcher(value);
        return matcher.replaceAll("");
    }
}
