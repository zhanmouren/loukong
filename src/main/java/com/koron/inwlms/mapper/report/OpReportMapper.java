package com.koron.inwlms.mapper.report;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.report.DmaOperabilityReportDTO;
import com.koron.inwlms.bean.DTO.report.LoggerFlowQuestionReportDTO;
import com.koron.inwlms.bean.DTO.report.PFLoggerListReportDTO;
import com.koron.inwlms.bean.DTO.report.PFLoggerOperabilityReportDTO;
import com.koron.inwlms.bean.VO.report.DmaOperabilityReportVO;
import com.koron.inwlms.bean.VO.report.LoggerFlowQuestionReportVO;
import com.koron.inwlms.bean.VO.report.NoiseLoggerListReportVO;
import com.koron.inwlms.bean.VO.report.PFLoggerDataInfo;
import com.koron.inwlms.bean.VO.report.PFLoggerListReportVO;
import com.koron.inwlms.bean.VO.report.PFLoggerOperabilityReportVO;

/**
 * 操作报表mapper
 * @author csh
 * @Date 2020/07/22
 */
@Repository
public interface OpReportMapper {

	/**
	 * 压力流量监测点列表信息
	 * @param pFLoggerListReportDTO
	 * @return
	 */
	List<PFLoggerListReportVO> queryPFLoggerListReport(PFLoggerListReportDTO pFLoggerListReportDTO);

	/**
	 * 压力流量监测点总条数
	 * @param pFLoggerListReportDTO
	 * @return
	 */
	Integer countPFLoggerListReport(PFLoggerListReportDTO pFLoggerListReportDTO);
	
	List<NoiseLoggerListReportVO> queryNoiseLoggerListReport(PFLoggerListReportDTO pFLoggerListReportDTO);
	
	List<DmaOperabilityReportVO> queryDmaOperabilityReport(DmaOperabilityReportDTO dmaOperabilityReportDTO);

	/**
	 * 可用的压力流量监测点信息
	 * @param pFLoggerOperabilityReportDTO
	 * @return
	 */
	List<PFLoggerOperabilityReportVO> queryPFLoggerOperabilityReport(PFLoggerOperabilityReportDTO pFLoggerOperabilityReportDTO);

	/**
	 * 可用的压力流量监测点总条数
	 * @param pFLoggerOperabilityReportDTO
	 * @return
	 */
	Integer countPFLoggerOperabilityReport(PFLoggerOperabilityReportDTO pFLoggerOperabilityReportDTO);
	
	/**
	 * 压力流量监测点历史数据信息
	 * @param pFLoggerOperabilityReportDTO
	 * @return
	 */
	List<PFLoggerDataInfo> queryPFLoggerData(PFLoggerOperabilityReportDTO pFLoggerOperabilityReportDTO);

	/**
	 * 压力流量监测点最后记录的数据信息
	 * @param pFLoggerOperabilityReportDTO
	 * @return
	 */
	List<PFLoggerDataInfo> queryPFLoggerLastRecordTime();

	/**
	 * 流量监测点水量存疑报表
	 * @param loggerFlowQuestionReportDTO
	 * @return
	 */
	List<LoggerFlowQuestionReportVO> queryLoggerFlowQuestionReport(@Param("lfqrDTO") LoggerFlowQuestionReportDTO lfqrDTO,@Param("days") Integer days );

	/**
	 * 流量监测点水量存疑报表总条数
	 * @param lfqrDTO
	 * @param days
	 * @return
	 */
	Integer countLoggerFlowQuestionReport(@Param("lfqrDTO") LoggerFlowQuestionReportDTO lfqrDTO);
	

}
