package com.koron.inwlms.service.indexData;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.indexData.WarningInfoDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.indexData.AreaInfoListVO;
import com.koron.inwlms.bean.VO.indexData.InfoPageListVO;
import com.koron.inwlms.bean.VO.indexData.MultParamterIndicatorVO;
import com.koron.inwlms.bean.VO.indexData.TaskMsgVO;
import com.koron.inwlms.bean.VO.indexData.TreeZoneVO;

public interface IndexService {

	//显示分区或全网综合信息功能
	List<IndicatorVO>  queryCompreInfo(SessionFactory factory,IndicatorNewDTO indicatorDTO);
	
	//根据时间段查询指标
	MultParamterIndicatorVO queryComYearInfo(SessionFactory factory,IndicatorNewDTO indicatorDTO);
	
    //查询漏损任务
	InfoPageListVO<List<TaskMsgVO>> queryWarningInfo(SessionFactory factory,WarningInfoDTO warningInfoDTO);
	
	//查询检测点报警信息
	InfoPageListVO<List<TaskMsgVO>> queryCheckWarningInfo(SessionFactory factory,WarningInfoDTO warningInfoDTO);
	
	//查询分区排名
	AreaInfoListVO<List<IndicatorVO>>  queryAreaRankInfo(SessionFactory factory,IndicatorNewDTO indicatorDTO);
	
	//查询所有分区（树状）
	List<TreeZoneVO> queryAllZone(SessionFactory factory,int type,String foreignKey);
	
	//查询子分区排名
	AreaInfoListVO<List<IndicatorVO>> queryChildAreaRankInfo(SessionFactory factory, IndicatorNewDTO indicatorDTO);
}
