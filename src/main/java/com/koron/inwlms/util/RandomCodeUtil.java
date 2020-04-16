package com.koron.inwlms.util;

import java.util.Date;
import java.util.UUID;

//生成随机的Code
public class RandomCodeUtil {
	
	  /**
                 * 获取8位不重复随机码（取当前时间戳转化为十六进制）
     * @author xiaozhan
     * @param time
     * @return
     */
       public static String toHex(long time){    
             return Integer.toHexString((int)time);
      }
       
       public static String getCode(int type){  
    	   String code="";
    	  //1.组织
    	  if(type==1) {
    		code="org"+ toHex(new Date().getTime());
    	  }
    	  //2.部门
    	  if(type==2) {
    		 code="dept"+ toHex(new Date().getTime()); 
    	  }
    	  return code;
       }
       //获取32位的UUID
       public static String getUUID32() {
    	   return UUID.randomUUID().toString().replace("-", "").toLowerCase();
       }
       
}
