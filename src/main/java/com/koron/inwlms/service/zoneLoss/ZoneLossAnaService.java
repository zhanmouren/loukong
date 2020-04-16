package com.koron.inwlms.service.zoneLoss;


import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.zoneLoss.QueryDmaZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryFZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QuerySZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneHstDataDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneIndicatorListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.ZoneThematicValueDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneHstDataVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.zoneLoss.DmaZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.FZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.SZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneIndicatorDicVO;

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
	PageListVO<List<FZoneLossListVO>> queryFZoneLossList(SessionFactory factory,QueryFZoneLossListDTO queryFZoneLossListDTO);

	/**
	 * 查询二级分区漏损列表
	 * @param factory
	 * @param querySZoneLossListDTO
	 * @return
	 */
	PageListVO<List<SZoneLossListVO>> querySZoneLossList(SessionFactory factory,QuerySZoneLossListDTO querySZoneLossListDTO);

	/**
	 * 查询dma分区漏损列表
	 * @param factory
	 * @param queryDmaZoneLossListDTO
	 * @return
	 */
	PageListVO<List<DmaZoneLossListVO>> queryDmaZoneLossList(SessionFactory factory,QueryDmaZoneLossListDTO queryDmaZoneLossListDTO);
	
	/**
	 * 查询分区的历史数据
	 * @param factory
	 * @param queryZoneInfoDTO
	 * @return
	 */
	ZoneHstDataVO queryZoneHstData(SessionFactory factory,QueryZoneHstDataDTO queryZoneHstDataDTO);
	
	/**
	 * 查询分区指标数据（分区专题图）
	 * @param factory
	 * @param queryZoneIndicatorListDTO
	 * @return
	 */
	PageListVO queryZoneIndicatorList(SessionFactory factory,QueryZoneIndicatorListDTO queryZoneIndicatorListDTO);
	
	/**
	 * 查询分区指标数据字典（漏损专题图）
	 * @param factory
	 * @param zoneType
	 * @return
	 */
	List<ZoneIndicatorDicVO> queryZoneIndicatorDic(SessionFactory factory,Integer zoneType);
	
	/**
	 * 查询分区漏损专题图指标数据
	 * @param factory
	 * @param queryZoneIndicatorListDTO
	 * @return
	 */
	List<Map<Object,Object>> queryZoneThematicValue(SessionFactory factory,ZoneThematicValueDTO zoneThematicValueDTO);
	
}
