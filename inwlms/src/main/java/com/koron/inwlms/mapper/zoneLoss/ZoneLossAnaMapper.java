package com.koron.inwlms.mapper.zoneLoss;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBTReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryFZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBTReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneWBLossDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WBIndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportFileDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportIndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBTReportIndicatorDTO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.zoneLoss.FZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.IndicatorInfo;
import com.koron.inwlms.bean.VO.zoneLoss.WBIndicatorVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReporFileListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportFile;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportIndicator;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportIndicator;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneWBLossVO;

/**
 * 分区漏损-水平衡分析mapper
 * @author csh
 * @Date 2020/04/07
 */
@Repository
public interface ZoneLossAnaMapper {

	/**
	 * 查询基础指标数据
	 * @param wBIndicatorDTO
	 * @return
	 */
	List<WBIndicatorVO> queryBaseIndicData(WBIndicatorDTO wBIndicatorDTO);
	
	/**
	 * 查询水平衡基础指标数据
	 * @param wBIndicatorDTO
	 * @return
	 */
	List<WBIndicatorVO> queryWBBaseIndicData(WBIndicatorDTO wBIndicatorDTO);
	
	/**
	 * 查询分区漏损指标数据
	 * @param wBIndicatorDTO
	 * @return
	 */
	List<WBIndicatorVO> queryZoneLossIndicData(WBIndicatorDTO wBIndicatorDTO);
	
	/**
	 * 查询指标分类信息
	 * @param code
	 * @return
	 */
	IndicatorInfo queryIndicLevel2Info(String code);

	
}
