package com.koron.inwlms.service.zoneLoss;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneWBLossDTO;
import com.koron.inwlms.bean.VO.zoneLoss.PageWNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneWBLossVO;

/**
 * 水平衡分析接口
 * @author csh
 * @Date 2020/04/07
 *
 */
public interface WaterBalanceAnaService {

	/**
	 * 查询分区水平衡数据
	 * @param factory
	 * @param queryZoneWBLossDTO
	 * @return
	 */
	List<ZoneWBLossVO> queryZoneWBLossData(SessionFactory factory,QueryZoneWBLossDTO queryZoneWBLossDTO);
	
	/**
	 * 查询全网水平衡报表列表
	 * @param factory
	 * @param queryWNWBReportListDTO
	 * @return
	 */
	PageWNWBReportListVO queryWNWBReportList(SessionFactory factory,QueryWNWBReportListDTO queryWNWBReportListDTO);
}
