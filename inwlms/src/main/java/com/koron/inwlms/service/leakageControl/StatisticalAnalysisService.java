package com.koron.inwlms.service.leakageControl;

import java.text.ParseException;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.leakageControl.ProcessingStatisticsDTO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsVO;

public interface StatisticalAnalysisService {

	ProcessingStatisticsVO queryProcessingStatistics(SessionFactory factory,
			ProcessingStatisticsDTO processingStatisticsDTO) throws ParseException;

}
