package com.koron.inwlms.service.zoneLoss;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBTReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBTReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneWBLossDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WBIndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportFileDTO;
import com.koron.inwlms.bean.VO.zoneLoss.PageWNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageWNWBTReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WBIndicatorVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReporFileListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportDetailVO;
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

	/**
	 * 删除报表
	 * @param factory
	 * @param id
	 */
	void deleteWNWBReport(SessionFactory factory, Integer id);
	
	/**
	 * 查询报表模板列表
	 * @param factory
	 * @param queryWNWBTReportListDTO
	 * @return
	 */
	PageWNWBTReportListVO queryWNWBTReportList(SessionFactory factory,QueryWNWBTReportListDTO queryWNWBTReportListDTO);

	/**
	 * 删除报表模板
	 * @param factory
	 * @param tId
	 */
	void deleteWNWBTReport(SessionFactory factory, Integer tId);
	
	/**
	 * 新增报表
	 * @param factory
	 * @param addWNWBReportDTO
	 * @return
	 */
	Integer addWNWBReport(SessionFactory factory, AddWNWBReportDTO addWNWBReportDTO);

	/**
	 * 更新报表
	 * @param factory
	 * @param addWNWBReportDTO
	 * @return
	 */
	Integer updateWNWBReport(SessionFactory factory, AddWNWBReportDTO addWNWBReportDTO);

	/**
	 * 查询报表详情
	 * @param factory
	 * @param id
	 * @return
	 */
	WNWBReportDetailVO queryWNWBReportDetail(SessionFactory factory, Integer id);
	
	/**
	 * 查询报表附件列表
	 * @param factory
	 * @param wNWBReportFileDTO
	 * @return
	 */
	List<WNWBReporFileListVO> queryWNWBReporFileList(SessionFactory factory, WNWBReportFileDTO wNWBReportFileDTO);

	/**
	 * 删除报表附件
	 * @param factory
	 * @param id
	 */
	void deleteReportFileById(SessionFactory factory, Integer id);

	/**
	 * 新增报表模板
	 * @param factory
	 * @param addWNWBTReportDTO
	 * @return
	 */
	Integer addWNWBTReport(SessionFactory factory, AddWNWBTReportDTO addWNWBTReportDTO);

	/**
	 * 更新报表模板
	 * @param factory
	 * @param editWNWBTReportDTO
	 * @return
	 */
	Integer updateWNWBTReport(SessionFactory factory, AddWNWBTReportDTO editWNWBTReportDTO);

	/**
	 * 查询报表模板详情
	 * @param factory
	 * @param id
	 * @return
	 */
	WNWBTReportDetailVO queryWNWBTReportDetail(SessionFactory factory, Integer id);
	
	/**
	 * 查询水平衡指标数据
	 * @param factory
	 * @param wBIndicatorDTO
	 * @return
	 */
	List<WBIndicatorVO> queryWBIndicatorData(SessionFactory factory,WBIndicatorDTO wBIndicatorDTO);
}
