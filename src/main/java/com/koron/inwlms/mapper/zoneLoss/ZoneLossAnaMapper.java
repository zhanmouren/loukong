package com.koron.inwlms.mapper.zoneLoss;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.zoneLoss.QueryLeakListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBReportListDTO;
import com.koron.inwlms.bean.VO.zoneLoss.BurstLeakAnalysisVO;
import com.koron.inwlms.bean.VO.zoneLoss.IndicatorInfo;
import com.koron.inwlms.bean.VO.zoneLoss.LeakDetailsVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;

/**
 * 分区漏损-水平衡分析mapper
 * @author csh
 * @Date 2020/04/07
 */
@Repository
public interface ZoneLossAnaMapper {

	/**
	 * 查询指标分类信息
	 * @param code
	 * @return
	 */
	IndicatorInfo queryIndicLevel2Info(String code);
	
	/**
	 * 查询分区所有漏点信息
	 * @return
	 */
	List<BurstLeakAnalysisVO> queryBurstLeakAnalysis();

	/**
	 * 根据id查询漏点详细信息
	 * @param id
	 * @return
	 */
	LeakDetailsVO queryBurstLeakById(@Param("id") String id);
	
	/**
	 * 查询漏点信息列表
	 * @param queryLeakListDTO
	 * @return
	 */
	List<LeakDetailsVO> queryBurstLeakList(QueryLeakListDTO queryLeakListDTO);

	/**
	 * 查询漏点条数
	 * @param queryLeakListDTO
	 * @return
	 */
	int countBurstLeak(QueryLeakListDTO queryLeakListDTO);
	
	/**
	 * 通过分区编码获取第一级到当前级的编码集合
	 * @param zoneNo
	 * @return
	 */
	@Select("select foreignkey from tbltree where ((select seq from tbltree where foreignkey = #{zoneNo}) & ~((1::int8 << (62 - parentmask-mask))-1)) = seq and type = 2 order by seq")
	List<String> queryParentsCodeByNo(@Param("zoneNo") String zoneNo);
	
	/**
	 * 查询分区警报信息
	 * @param zoneNo
	 * @return
	 */
	@Select("select count(*) from app_warninginf where \"areaCode\" = #{zoneNo}")
	Integer queryZoneWarningInfo(@Param("zoneNo") String zoneNo);
}
