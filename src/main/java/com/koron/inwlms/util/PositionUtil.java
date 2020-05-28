package com.koron.inwlms.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 深圳科荣软件有限公司
 *
 * @ClassName PositionUtil
 * @Description 处理边界工具
 * @Author hongxl
 * @Date 2019/10/14 17:33
 */
public class PositionUtil {

    /**
     * 处理shape字段为数组
     * @param position
     * @return
     */
    public static String getDmaPositionShape(String position){
        //处理shape字段，过滤感染字符及无用字符
        String boder = "";
        if(position.contains("),(")){
            if(position.contains("MULTI")){
//                boder = position.replace("MULTIPOLYGON ", "").replace("ZM ((( ", "").
//                        replace(")))","");
                boder = position.replace("MULTIPOLYGON(((", "").
                        replace(")))","");
                String[] boderTemp = boder.split("[)][)]");
                return String.valueOf(getDmaPositionMulti(boderTemp)).replace("\"","");
            }else {
                boder = position.replace("POLYGON((", "")
                        .replace("))","");
                String[] boderTemp = boder.split("[)]");
                return String.valueOf(getDmaPositionMulti(boderTemp)).replace("\"","");
            }
        }else {
            if(position.contains("MULTI")){
//                boder = position.replace("MULTIPOLYGON ", "").replace("ZM ((( ", "").
//                        replace(")))","");
                boder = position.replace("MULTIPOLYGON(((", "").
                        replace(")))","");
                String[] boderTemp = boder.split("[)][)]");
                return String.valueOf(getDmaPositionMulti(boderTemp)).replace("\"","");
            }else {
                boder = position.replace("POLYGON((", "")
                        .replace(")", "").replace("(( ", "");
                return String.valueOf(getDmaPositionSingle(boder)).replace("\"", "");
            }
        }
    }

    /**
     * 多条线路
     * @param boderTemp
     * @return
     */
    private static List<List<String>>  getDmaPositionMulti(String[] boderTemp){
        List<List<String>> result = new ArrayList<>();
        for(String temp : boderTemp){
            //temp = temp.replace(",(( ","").replace(",( ","");
            temp = temp.replace(",((","").replace(",(","");
            String[] boderArr = temp.split(",");
            List<String> positions = new ArrayList<>();
            for (String tempPosition : boderArr) {
                String[] position = {};
                try {
                    if (StringUtils.isNotBlank(tempPosition.substring(0, 1))) {
                        position = tempPosition.substring(0, tempPosition.length()).split(" ");
                        //position = tempPosition.substring(0,23).split(" ");
                        //position = tempPosition.substring(0,31).split(" ");
                    } else {
                        position = tempPosition.substring(1, tempPosition.length()).split(" ");
                        //position = tempPosition.substring(1, 32).split(" ");
                    }
                    positions.add(Arrays.toString(position));
                }catch(Exception e){
                    System.out.println(tempPosition);
                    continue;
                }
            }
            result.add(positions);
        }
        return result;
    }

    /**
     * 单条线路
     * @param boder
     * @return
     */
    private static List<List<String>> getDmaPositionSingle(String boder){
        List<List<String>> result = new ArrayList<>();
        String[] boderArr = boder.split(",");
        List<String> positions = new ArrayList<>();
        for (String temp : boderArr) {
            String[] position = {};
            if (StringUtils.isNotBlank(temp.substring(0, 1))) {
                position = temp.substring(0,31).split(" ");
            } else {
                position = temp.substring(1,32).split(" ");
            }
            positions.add(Arrays.toString(position));
        }
        result.add(positions);
        return result;
    }

    /**
     * 处理shape字段为数组
     * @param position
     * @return
     */
    public static List<String> getPositionArray(String position){
        //处理shape字段，过滤感染字符及无用字符
        String boder = "";
        if(position.contains("),(")){
            if(position.contains("MULTI")){
                boder = position.replace("MULTIPOLYGON ", "").replace("ZM ((( ", "").
                        replace(")))","");
                String[] boderTemp = boder.split("[)][)]");
                return getDmaPositionMultiArray(boderTemp);
            }else {
                boder = position.replace("POLYGON ", "").replace("ZM (( ", "")
                        .replace("))","");
                String[] boderTemp = boder.split("[)]");
                return getDmaPositionMultiArray(boderTemp);
            }
        }else {
            boder = position.replace("POLYGON ", "").replace("ZM (( ", "")
                    .replace(")", "") .replace("(( ", "");
            return getDmaPositionSingleArray(boder);
        }
    }

    /**
     * 多条线路
     * @param boderTemp
     * @return
     */
    private static List<String> getDmaPositionMultiArray(String[] boderTemp){
        List<String> result = new ArrayList<>();
        for(String temp : boderTemp){
            temp = temp.replace(",(( ","").replace(",( ","");
            String[] boderArr = temp.split(",");
            List<String> positions = new ArrayList<>();
            for (String tempPosition : boderArr) {
                String[] position = {};
                if (StringUtils.isNotBlank(tempPosition.substring(0, 1))) {
                    position = tempPosition.substring(0,31).split(" ");
                } else {
                    position = tempPosition.substring(1,32).split(" ");
                }
                positions.add(Arrays.toString(position));
            }
            result.add(String.valueOf(positions));
        }
        return result;
    }

    /**
     * 单条线路
     * @param boder
     * @return
     */
    private static List<String> getDmaPositionSingleArray(String boder){
        String[] boderArr = boder.split(",");
        List<String> positions = new ArrayList<>();
        for (String temp : boderArr) {
            String[] position = {};
            if (StringUtils.isNotBlank(temp.substring(0, 1))) {
                position = temp.substring(0,31).split(" ");
            } else {
                position = temp.substring(1,32).split(" ");
            }
            positions.add(Arrays.toString(position));
        }
        return positions;
    }
}
