package com.koron.inwlms.service.report.waterReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.report.waterBalanceReport.WB1BalanceDTO;
import com.koron.inwlms.bean.VO.indexData.TreeZoneVO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.WB1BalanceVO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.WB2OneZoneVO;

public interface WaterReportService {
	 //(WB_01)水司及一级分区产销差率同比报表      以月为时间间隔,汇总分析所选运作区或全网在指定时间范围内用水量、产销差和未计量食水用水量。
	 //public List<WB1BalanceVO>  queryPartitionData(SessionFactory factory, IndicatorNewDTO indicatorNewDTO);
	public Map<String,Object>  queryPartitionData(SessionFactory factory, IndicatorNewDTO indicatorNewDTO);
	 
	 //查询一级分区
	 List<TreeZoneVO> queryTreeOneZone (SessionFactory factory,int type,String foreignKey,int allFlag);
	 
	 //一级分区产销差率比较报表
	 List<WB2OneZoneVO> queryOneZoneCXC(SessionFactory factory, IndicatorNewDTO indicatorNewDTO);
	 
	 //WB_03)二级分区水平衡报表
	 Map<String,Object> queryTwoZoneWater(SessionFactory factory,IndicatorNewDTO indicatorNewDTO);
	 
}
