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
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportFileDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportIndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBTReportIndicatorDTO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.zoneLoss.FZoneLossListVO;
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
	
	/**
	 * 删除报表
	 * @param id
	 */
	void deleteWNWBReport(@Param("id") Integer id);
	
	/**
	 * 查询全网水平衡报表模板列表
	 * @param queryWNWBReportListDTO
	 * @return
	 */
	List<WNWBTReportListVO> queryWNWBTReportList(QueryWNWBTReportListDTO queryWNWBTReportListDTO);

	/**
	 * 查询全网水平衡列表模板总条数
	 * @param queryWNWBTReportListDTO
	 * @return
	 */
	int countWNWBTReportList(QueryWNWBTReportListDTO queryWNWBTReportListDTO);
	
	/**
	 * 删除报表模板
	 * @param id
	 */
	void deleteWNWBTReport(@Param("tId") Integer tId);
	
	/**
	 * 保存报表
	 * @param addWNWBReportDTO
	 */
	void addWNWBReport(AddWNWBReportDTO addWNWBReportDTO);
	
	/**
	 * 保存报表指标信息
	 * @param addWNWBReportDTO
	 */
	void addReportIndicator(WNWBReportIndicatorDTO wNWBReportIndicatorDTO);
	
	/**
	 * 保存指标附件
	 * @param wNWBReportFileDTO
	 */
	void addReportFile(WNWBReportFileDTO wNWBReportFileDTO);
	
	/**
	 * 更新报表
	 * @param addWNWBReportDTO
	 */
	void updateWNWBReport(AddWNWBReportDTO editWNWBReportDTO);
	
	/**
	 * 根据id删除报表指标
	 * @param id
	 */
	void deleteReportIndicatorById(@Param("id") Integer id);
	
	/**
	 * 根据id删除报表附件
	 * @param id
	 */
	void deleteReportFileById(@Param("id") Integer id);
	
	/**
	 * 查询报表附件列表信息
	 * @param wNWBReportFileDTO
	 * @return
	 */
	List<WNWBReporFileListVO> queryWNWBReporFileList(WNWBReportFileDTO wNWBReportFileDTO);

	/**
	 * 根据id查询报表信息
	 * @param id
	 * @return
	 */
	WNWBReportDetailVO queryWNWBReportById(@Param("id") Integer id);
	
	/**
	 * 根据id查询报表附件信息
	 * @param id
	 * @return
	 */
	List<WNWBReportFile> queryWNWBReportFileById(@Param("id") Integer id);
	
	/**
	 * 根据id查询报表指标信息
	 * @param id
	 * @return
	 */
	List<WNWBReportIndicator> queryWNWBReportIndicatorById(@Param("id") Integer id);

	/**
	 * 保存模板报表
	 * @param addWNWBReportDTO
	 */
	void addWNWBTReport(AddWNWBTReportDTO addWNWBTReportDTO);
	
	/**
	 * 保存报表模板指标
	 * @param wNWBTReportIndicatorDTO
	 */
	void addTReportIndicator(WNWBTReportIndicatorDTO wNWBTReportIndicatorDTO);

	/**
	 * 更新报表模板
	 * @param editWNWBTReportDTO
	 */
	void updateWNWBTReport(AddWNWBTReportDTO editWNWBTReportDTO);
	
	/**
	 * 根据id删除报表模板指标
	 * @param id
	 */
	void deleteTReportIndicatorById(@Param("id") Integer id);
	
	/**
	 * 根据id查询报表模板信息
	 * @param id
	 * @return
	 */
	WNWBTReportDetailVO queryWNWBTReportById(@Param("id") Integer id);
	
	/**
	 * 根据id查询报表模板指标信息
	 * @param id
	 * @return
	 */
	List<WNWBTReportIndicator> queryWNWBTReportIndicatorById(@Param("id") Integer id);

	/**
	 * 查询一级分区列表
	 * @param queryFZoneLossListDTO
	 * @return
	 */
	List<FZoneLossListVO> queryFZoneLossList(QueryFZoneLossListDTO queryFZoneLossListDTO);
	
}
