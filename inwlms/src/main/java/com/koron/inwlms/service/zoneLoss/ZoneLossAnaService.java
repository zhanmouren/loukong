package com.koron.inwlms.service.zoneLoss;


import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.zoneLoss.QueryDmaZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryFZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QuerySZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneHstDataDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneInfoDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneHstDataVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageDmaZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageFZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageSZoneLossListVO;

/**
 * 分区漏损分析接口
 * @author csh
 * @Date 2020/04/09
 *
 */
public interface ZoneLossAnaService {

	/**
	 * 查询一级分区漏损列表
	 * @param factory
	 * @param queryFZoneLossListDTO
	 * @return
	 */
	PageFZoneLossListVO queryFZoneLossList(SessionFactory factory,QueryFZoneLossListDTO queryFZoneLossListDTO);

	/**
	 * 查询二级分区漏损列表
	 * @param factory
	 * @param querySZoneLossListDTO
	 * @return
	 */
	PageSZoneLossListVO querySZoneLossList(SessionFactory factory,QuerySZoneLossListDTO querySZoneLossListDTO);

	/**
	 * 查询dma分区漏损列表
	 * @param factory
	 * @param queryDmaZoneLossListDTO
	 * @return
	 */
	PageDmaZoneLossListVO queryDmaZoneLossList(SessionFactory factory,QueryDmaZoneLossListDTO queryDmaZoneLossListDTO);
	
	/**
	 * 查询分区的历史数据
	 * @param factory
	 * @param queryZoneInfoDTO
	 * @return
	 */
	ZoneHstDataVO queryZoneHstData(SessionFactory factory,QueryZoneHstDataDTO queryZoneHstDataDTO);
	
}
