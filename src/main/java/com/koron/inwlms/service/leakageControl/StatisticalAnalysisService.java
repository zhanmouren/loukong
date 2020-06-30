package com.koron.inwlms.service.leakageControl;

import java.text.ParseException;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.ProcessingStatisticsDTO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsAllDataVO;
import com.koron.inwlms.bean.VO.leakageControl.ProcessingStatisticsVO;

@Service
public interface StatisticalAnalysisService {

	ProcessingStatisticsAllDataVO queryProcessingStatistics(SessionFactory factory,
			ProcessingStatisticsDTO processingStatisticsDTO) throws ParseException;

}
