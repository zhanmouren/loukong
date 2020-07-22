package com.koron.inwlms.service.report;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.koron.ebs.mybatis.SessionFactory;

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


/**
 * 操作报表接口
 * @author csh
 * @Date 2020/07/22
 *
 */
public interface OpReportService {


    /**
     * 查询压力流量监测设备列表
     *
     * @param factory
     * @param dmaOperabilityReportDTO
     * @return
     */
	PageListVO<List<PFLoggerListReportVO>> queryPFLoggerListReport(SessionFactory factory, PFLoggerListReportDTO pFLoggerListReportDTO);

    /**
     * 导出压力流量监测设备列表
     *
     * @param factory
     * @param map
     * @return
     */
    XSSFWorkbook downloadPFLoggerListReport(SessionFactory factory, Map<String, Object> map);

    /**
     * 查询噪声监测设备列表
     *
     * @param factory
     * @param dmaOperabilityReportDTO
     * @return
     */
    PageListVO<List<NoiseLoggerListReportVO>> queryNoiseLoggerListReport(SessionFactory factory, PFLoggerListReportDTO pFLoggerListReportDTO);

    /**
     * 导出噪声监测设备列表
     *
     * @param factory
     * @param map
     * @return
     */
    XSSFWorkbook downloadNoiseLoggerListReport(SessionFactory factory, Map<String, Object> map);
  
    /**
     * 查询DMA可操作性报表
     *
     * @param factory
     * @param dmaOperabilityReportDTO
     * @return
     */
    PageListVO<List<DmaOperabilityReportVO>> queryDmaOperabilityReport(SessionFactory factory, DmaOperabilityReportDTO dmaOperabilityReportDTO);

    /**
     * 导出DMA可操作性报表
     *
     * @param factory
     * @param map
     * @return
     */
    XSSFWorkbook downloadDmaOperabilityReport(SessionFactory factory, Map<String, Object> map);


    /**
     * 查询压力流量监测设备可操作性报表
     *
     * @param factory
     * @param meterOperabilityReportDTO
     * @return
     */
    PageListVO<List<PFLoggerOperabilityReportVO>> queryPFLoggerOperabilityReport(SessionFactory factory, PFLoggerOperabilityReportDTO pFLoggerOperabilityReportDTO);


    /**
     * 导出压力流量监测设备可操作性报表
     *
     * @param factory
     * @param map
     * @return
     */
    XSSFWorkbook downloadPFLoggerOperabilityReport(SessionFactory factory, Map<String, Object> map);

    /**
     * 查询监测水量存疑报表
     * @param dto
     * @return
     */
    PageListVO<List<LoggerFlowQuestionReportVO>> queryLoggerFlowQuestionReport(SessionFactory factory, LoggerFlowQuestionReportDTO loggerFlowQuestionReportDTO);

    /**
     * 导出监测水量存疑报表
     *
     * @param factory
     * @return
     */
    XSSFWorkbook downloadLoggerFlowQuestionReport(SessionFactory factory, LoggerFlowQuestionReportDTO loggerFlowQuestionReportDTO, List<List<Map<String, Object>>> titleInfo);

}
