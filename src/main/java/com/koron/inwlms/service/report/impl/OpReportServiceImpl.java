package com.koron.inwlms.service.report.impl;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.report.DmaOperabilityReportDTO;
import com.koron.inwlms.bean.DTO.report.LoggerFlowQuestionReportDTO;
import com.koron.inwlms.bean.DTO.report.PFLoggerListReportDTO;
import com.koron.inwlms.bean.DTO.report.PFLoggerOperabilityReportDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.report.DmaOperabilityReportVO;
import com.koron.inwlms.bean.VO.report.LoggerFlowQuestionReportVO;
import com.koron.inwlms.bean.VO.report.NoiseLoggerListReportVO;
import com.koron.inwlms.bean.VO.report.PFLoggerListReportVO;
import com.koron.inwlms.bean.VO.report.PFLoggerOperabilityReportVO;
import com.koron.inwlms.service.report.OpReportService;

/**
 * 操作报表接口实现类
 * 
 * @author csh
 * @Date 2020/03/23
 *
 */
@Service
public class OpReportServiceImpl implements OpReportService {

	@Override
	public PageListVO<List<PFLoggerListReportVO>> queryPFLoggerListReport(SessionFactory factory,
			PFLoggerListReportDTO pFLoggerListReportDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XSSFWorkbook downloadPFLoggerListReport(SessionFactory factory, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageListVO<List<NoiseLoggerListReportVO>> queryNoiseLoggerListReport(SessionFactory factory,
			PFLoggerListReportDTO pFLoggerListReportDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XSSFWorkbook downloadNoiseLoggerListReport(SessionFactory factory, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageListVO<List<DmaOperabilityReportVO>> queryDmaOperabilityReport(SessionFactory factory,
			DmaOperabilityReportDTO dmaOperabilityReportDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XSSFWorkbook downloadDmaOperabilityReport(SessionFactory factory, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageListVO<List<PFLoggerOperabilityReportVO>> queryPFLoggerOperabilityReport(SessionFactory factory,
			PFLoggerOperabilityReportDTO pFLoggerOperabilityReportDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XSSFWorkbook downloadPFLoggerOperabilityReport(SessionFactory factory, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageListVO<List<LoggerFlowQuestionReportVO>> queryLoggerFlowQuestionReport(SessionFactory factory,
			LoggerFlowQuestionReportDTO loggerFlowQuestionReportDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XSSFWorkbook downloadLoggerFlowQuestionReport(SessionFactory factory,
			LoggerFlowQuestionReportDTO loggerFlowQuestionReportDTO, List<List<Map<String, Object>>> titleInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
