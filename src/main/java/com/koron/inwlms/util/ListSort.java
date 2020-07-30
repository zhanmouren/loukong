package com.koron.inwlms.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.NullComparator;

public class ListSort {
	/**
	       * bean的某一个属性
	       * @param list:列表名称
	       * @param filedName:属性名称
	       * @param ascFlag:true;升序;false:降序
	       */
	@SuppressWarnings("unchecked")
	      public static void sort(List list, String filedName, boolean ascFlag) {
	  
	         if (list.size() == 0 || filedName.equals("")) {
	              return;
	          }
	          Comparator<?> cmp = ComparableComparator.getInstance();
	          // 判断升序还是降序
	          if (ascFlag) {
	              cmp = ComparatorUtils.nullLowComparator(cmp);
	          } else {
	              cmp = ComparatorUtils.reversedComparator(cmp);
	  
	          }
	          Collections.sort(list, new BeanComparator(filedName, cmp));
	      }
	      
	      /**
	       * bean根据某几个属性排序
	       * @param beans
	       * @param sortParam
	       */
	      @SuppressWarnings({ "rawtypes", "unchecked" })
	      public static void sortExecute(List<?> beans, Map<String, String> sortParam) {
	  
	          // 判断beans和排序规则是否有内容
	          if (beans.size() == 0 || sortParam.keySet().size() == 0) {
	              return;
	          }
	 
	          // 判断beans和排序规则是否有内容
	          if (beans.isEmpty() || sortParam.isEmpty()) {
	              return;
	          }
	          ComparatorChain comparator = new ComparatorChain();
	          boolean sortMethod = false;
	          for (String itemName : sortParam.keySet()) {
	              sortMethod = false;
	              if ("desc".equals(sortParam.get(itemName))) {
	                  sortMethod = true;
	              }
	              comparator.addComparator(new BeanComparator(itemName, new NullComparator()), sortMethod);
	          }
	          Collections.sort(beans, comparator);
	      }
}
