package com.koron.inwlms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.swan.excel.ExportExcel;

import com.koron.inwlms.bean.VO.common.PageBean;


/**
 * 导出功能工具类
 * @author 26470
 *
 */
public class ExportDataUtil {
	/**
	 * 导出列表数据公共方法
	 * @param pageInfo  列表条件查询结果
	 * @param titleInfos  导出数据列信息   [{titleName:"供水系统类型",titleValue:"system",changeValue:"1000"}]
	 * titleName  导出列标题   titleValue  导出列对应字段   changeValue  导出时字段数值转换系数
	 * @return
	 */
	public final static HttpEntity<?> getExcelDataFileInfo(PageBean<?> pageInfo,List<Map<String,String>> titleInfos){
		Map<String, Object> data = new HashMap<>();
		data.put("dataInfos", pageInfo.getDataList());
		data.put("titleInfos", titleInfos);
		HttpEntity<?> entity = ExportExcel.export("file", "static/excelTemplate/fileTemplate.xlsx", "/excelTemplate/downLoadFile.btl", data);
		return entity;
	}
	
	/**
	 * 导出列表数据公共方法
	 * @param list  列表条件查询结果
	 * @param titleInfos  导出数据列信息   [{titleName:"供水系统类型",titleValue:"system",changeValue:"1000"}]
	 * titleName  导出列标题   titleValue  导出列对应字段   changeValue  导出时字段数值转换系数
	 * @return
	 */
	public final static HttpEntity<?> getExcelDataFileInfoByList(List<?> list,List<Map<String,String>> titleInfos){
		Map<String, Object> data = new HashMap<>();
		data.put("dataInfos", list);
		data.put("titleInfos", titleInfos);
		HttpEntity<?> entity = ExportExcel.export("file", "static/excelTemplate/fileTemplate.xlsx", "/excelTemplate/downLoadFile.btl", data);
		return entity;
	}
	
	
}
