package com.koron.inwlms.service.report.waterReport;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.report.waterBalanceReport.WB1BalanceDTO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.WB1BalanceVO;

public interface WaterReportService {
	 //(WB_01)水司及一级分区产销差率同比报表      以月为时间间隔,汇总分析所选运作区或全网在指定时间范围内用水量、产销差和未计量食水用水量。
	 public List<WB1BalanceVO>  queryPartitionData(SessionFactory factory, IndicatorNewDTO indicatorNewDTO);
}
