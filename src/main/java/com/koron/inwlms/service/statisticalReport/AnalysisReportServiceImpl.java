package com.koron.inwlms.service.statisticalReport;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.inwlms.bean.VO.statisticalReport.ZoneMnfVO;


public class AnalysisReportServiceImpl implements AnalysisReportService {
	
	/**
	 * 分区最小夜间流量报表
	 */
	@TaskAnnotation("queryZoneMnf")
	@Override
	public ZoneMnfVO queryZoneMnf(SessionFactory factory) {
		
		
		//判断分区级别
		
		
		return null;
	}
	

}
