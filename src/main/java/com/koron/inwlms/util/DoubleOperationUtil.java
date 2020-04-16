package com.koron.inwlms.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * 深圳科荣软件有限公司
 *
 * @ClassName DoubleOperationUtil
 * @Description Double类型运算
 * @Author hongxl
 * @Date 2019/9/11 16:55
 */
public class DoubleOperationUtil {

    private static final long serialVersionUID = -3345205828566485102L;
    // 默认除法运算精度
    private static final Integer DEF_DIV_SCALE = 5;

    public static String getBigDecimal(Integer value){
        if(value==null){
            return null;
        }
        NumberFormat df = NumberFormat.getInstance();
        // 取消科学计数法,取消千分位
        df.setGroupingUsed(false);
        return df.format(value);

    }

    public static String getBigDecimal(Double value){
        if(value==null){
            return null;
        }
        NumberFormat df = NumberFormat.getInstance();
        // 取消科学计数法,取消千分位
        df.setGroupingUsed(false);
        return df.format(round(value.toString()));
    }

    public static String getBigDecimal(String value){
        if ("-".equals(value) || StringUtils.isBlank(value)){
            return null;
        }
        NumberFormat df = NumberFormat.getInstance();
        // 取消科学计数法,取消千分位
        df.setGroupingUsed(false);
        return  df.format(round(value));
    }

    public static List<String> getBigDecimal(List<String> value){
        for(String item : value){
            if ("-".equals(item) || StringUtils.isBlank(item)){
                item= null;
            }else {
                NumberFormat df = NumberFormat.getInstance();
                // 取消科学计数法,取消千分位
                df.setGroupingUsed(false);
                item = df.format(round(item));
            }
        }
        return value;
    }

    /**
     * double保留2位精度
     * @param value
     * @return
     */
    public static Double scale(Double value){
        return scale(value,DEF_DIV_SCALE);
    }

    /**
     *标准差
     * @param valueList
     * @return
     */
    public static Double StandardDiviationString(List<String> valueList){
        Integer size = valueList.size();
        Double sum=0.0;
        for(String temp:valueList){
            if(StringUtils.isBlank(temp)){
                continue;
            }
            sum=add(sum,Double.valueOf(temp));
        }
        Double avg = divide(sum,Double.valueOf(size));//求平均值
        Double dVar = 0.0;
        for(String temp:valueList){//求方差
            if(StringUtils.isBlank(temp)){
                continue;
            }
            dVar=add(dVar,mul(sub(Double.valueOf(temp),avg),sub(Double.valueOf(temp),avg)));
        }
        return Math.sqrt(divide(dVar,Double.valueOf(size)));
    }

    /**
     *double保留scale位精度
     * @param value
     * @param scale 精度
     * @return
     */
    public static Double scale(Double value,Integer scale){
        return Double.valueOf(Math.round(value*Math.pow(10,scale))/100);
    }

    /**
     * 提供精确的加法运算。
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static Double add(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.add(b2).doubleValue();
    }

    public static String addString(String value1, String value2){
        value1 = ("-".equals(value1) || StringUtils.isBlank(value1))?"0":value1;
        value2 = ("-".equals(value2) || StringUtils.isBlank(value2))?"0":value2;
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return Double.toString(b1.add(b2).doubleValue());
    }

    /**
     * Double类型保留N位小数
     * @param d
     * @return
     */
    public static Double keepTwoDecimal(double d,int num){
        BigDecimal b = new BigDecimal(d);
        return b.setScale(num, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(Double value1, Double value2) {
        value1=value1==null?0.0:value1;
        value2=value2==null?0.0:value2;
        BigDecimal b1 = new BigDecimal(value1.toString());
        BigDecimal b2 = new BigDecimal(value2.toString());
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static Double mul(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static Double mulString(String value1, String value2) {
        if(StringUtils.isBlank(value1) || StringUtils.isBlank(value2)){
            return null;
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return round(String.valueOf(b1.multiply(b2).doubleValue()),DEF_DIV_SCALE);
    }


    /**
     * String类型数据相减
     * @param value1
     * @param value2
     * @return
     */
    public static String subString(Double value1,Double value2){
        value1=value1==null?0.0:value1;
        value2=value2==null?0.0:value2;
        return String.valueOf(sub(value1,value2));
    }

    /**
     * String类型数据相减
     * @param value1
     * @param value2
     * @return
     */
    public static String subString(String value1,String value2){
        if(StringUtils.isBlank(value1) || "-".equals(value1) || StringUtils.isBlank(value2) || "-".equals(value2)){
            return null;
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return String.valueOf(b1.subtract(b2).doubleValue());
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 两个参数的商
     */
    public static Double divide(Double dividend, Double divisor) {
        return divide(dividend, divisor, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     * @param dividend
     * @param divisor
     * @return
     */
    public static String divideString(String dividend,String divisor){
        if("-".equals(dividend)||"-".equals(divisor)||StringUtils.isBlank(dividend) || StringUtils.isBlank(divisor)){
            return null;
        }
        return divide(Double.valueOf(dividend), Double.valueOf(divisor), DEF_DIV_SCALE).toString();
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static Double divide(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend==null?0.0:dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor==null?0.0:divisor));
        if(b2.compareTo(BigDecimal.ZERO)==0){
            return b1.doubleValue();
        }
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     * @param value1
     * @param value2
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static String divideString(String value1,String value2, Integer scale){
        if(StringUtils.isBlank(value1) || StringUtils.isBlank(value2) || "-".equals(value1) || "-".equals(value2)){
            //除数和被除数有为零时，返回-
            return "-";
        }
        return Double.toString(divide(Double.valueOf(value1),Double.valueOf(value2),scale));
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static String divideToString(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend==null?0.0:dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor==null?0.0:divisor));
        if(b2.compareTo(BigDecimal.ZERO)==0){
            return "-";
        }
        return String.valueOf(b1.divide(b2, scale, BigDecimal.ROUND_HALF_DOWN).doubleValue());
    }

    /**
     * 提供指定数值的（精确）小数位四舍五入处理。
     *
     * @param value 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double value,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 提供指定数值的（精确）小数位四舍五入处理。
     *
     * @param value 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static Double round(String value,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if(StringUtils.isBlank(value)){
            throw new IllegalArgumentException("The value must be non-empty");
        }
        BigDecimal b = new BigDecimal(value);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 提供指定数值的（精确）小数位四舍五入处理。
     *
     * @param value 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static Double round(String value){
        return round(value,DEF_DIV_SCALE);
    }

    /**
     * 提供指定数值的（精确）小数位四舍五入处理。
     *
     * @param value 需要四舍五入的数字
     * @param i 除数
     * @return 四舍五入后的结果
     */
    public static Double roundDivide(String value,String i){
        if("-".equals(value) || StringUtils.isBlank(value) || StringUtils.isBlank(i)){
            return null;
        }
        return round(divideString(value,i),DEF_DIV_SCALE);
    }

    /**
     * 比较大小
     * @param value1
     * @param value2
     * @return
     */
    public static int compareTo(Double value1,Double value2){
        BigDecimal data1 = new BigDecimal(value1);
        BigDecimal data2 = new BigDecimal(value2);
        return data1.compareTo(data2);
    }
}
