package com.koron.inwlms.service.statisticalReport;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.statisticalReport.ZoneMnfVO;

public interface AnalysisReportService {

	ZoneMnfVO queryZoneMnf(SessionFactory factory);
	
	

}
