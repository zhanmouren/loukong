package com.koron.inwlms.mapper.zoneLoss;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneWBLossDTO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneWBLossVO;

/**
 * 分区漏损-水平衡分析mapper
 * @author csh
 * @Date 2020/04/07
 */
@Repository
public interface WaterBalanceAnaMapper {

    /**
     * 查询分区水平衡月漏损数据
     * @return
     */
	List<ZoneWBLossVO> queryZoneWBMLossData(@Param("qzlDTO") QueryZoneWBLossDTO queryZoneWBLossDTO,@Param("lists") List<ZoneInfo> lists);
	
	 /**
     * 查询分区水平衡年漏损数据
     * @return
     */
	List<ZoneWBLossVO> queryZoneWBYLossData(@Param("qzlDTO") QueryZoneWBLossDTO queryZoneWBLossDTO,@Param("lists") List<ZoneInfo> lists);
	
	/**
	 * 查询全网水平衡报表列表
	 * @param queryWNWBReportListDTO
	 * @return
	 */
	List<WNWBReportListVO> queryWNWBReportList(QueryWNWBReportListDTO queryWNWBReportListDTO);

	/**
	 * 查询全网水平衡列表总条数
	 * @param queryWNWBReportListDTO
	 * @return
	 */
	int countWNWBReportList(QueryWNWBReportListDTO queryWNWBReportListDTO);
	
}
