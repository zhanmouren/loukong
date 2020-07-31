package com.koron.inwlms.service.report;

import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.report.ZoneMnfDTO;
import com.koron.inwlms.bean.VO.report.statisticalReport.FlowMeterAnalysisVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.HourFlowAvg;
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

	Map<String, Object> queryNteFaultAnalysis(SessionFactory factory, ZoneMnfDTO zoneMnfDTO);

	List<HourFlowAvg> queryHourFlow(SessionFactory factory, ZoneMnfDTO zoneMnfDTO);
	
	

}
