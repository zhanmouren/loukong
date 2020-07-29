package com.koron.inwlms.service.report;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.report.ZoneMnfDTO;
import com.koron.inwlms.bean.VO.report.statisticalReport.FlowMeterAnalysisVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.MeterAbnormalAnalysisVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.NetFaultVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.ZoneMnfStatisticalVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.ZoneMnfVO;

public interface AnalysisReportService {

	ZoneMnfVO queryZoneMnf(SessionFactory factory,ZoneMnfDTO zoneMnfDTO);

	ZoneMnfStatisticalVO queryZoneMnfStatistical(SessionFactory factory, ZoneMnfDTO zoneMnfDTO);

	FlowMeterAnalysisVO queryFlowMeterAnalysis(SessionFactory factory, ZoneMnfDTO zoneMnfDTO);

	MeterAbnormalAnalysisVO queryMeterAbnormalAnalysis(SessionFactory factory, ZoneMnfDTO zoneMnfDTO);

	NetFaultVO queryNteFault(SessionFactory factory, ZoneMnfDTO zoneMnfDTO);
	
	

}
