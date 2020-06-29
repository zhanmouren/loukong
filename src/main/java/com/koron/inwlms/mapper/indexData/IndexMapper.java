package com.koron.inwlms.mapper.indexData;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.indexData.WarningInfoDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.indexData.InfoCompleteRateVO;
import com.koron.inwlms.bean.VO.indexData.TaskMsgVO;
import com.koron.inwlms.bean.VO.indexData.TreeZoneVO;
import com.koron.inwlms.bean.VO.sysManager.TreeMenuVO;

/*
 * date:2020-04-23
 * author:xiaozhan
 */  
@Repository
public interface IndexMapper {
	/**
	 * 查询基础指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryBaseIndicData(IndicatorNewDTO indicatorDTO);
	
	/**
	 * 查询水平衡基础指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryWBBaseIndicData(IndicatorNewDTO indicatorDTO);
	
	
	/**
	 * 查询全网下的一级分区
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryZoneFL();
	
	/**
	 * 查询全网下的二级分区
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryZoneSL();
	
	/**
	 * 查询分区漏损指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryZoneLossIndicData(IndicatorNewDTO indicatorDTO);
	
	/**
	 * 查询分区监测指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryZoneMoniIndicData(IndicatorNewDTO indicatorDTO);
	
	/**
	 * 查询分区渗漏指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryLeakIndicData(IndicatorNewDTO indicatorDTO);
	
	/**
	 * 查询全网指标数据
	 * @param indicatorDTO
	 * @return
	 */
	List<IndicatorVO> queryCompanyIndicData(IndicatorNewDTO indicatorDTO);
	
	/**
	 * 查询任务列表
	 */
	List<TaskMsgVO>  queryTaskList(WarningInfoDTO warningInfoDTO);
	
	/**
	 * 查询任务列表数量
	 */
	Integer  queryTaskListNum(WarningInfoDTO warningInfoDTO);
	
	/**
	 *  查询完成数量,未完成数量,进行中
	 */
	Integer queryComRateNum(WarningInfoDTO warningInfoDTO);
	
	/**
	 *  查询预计完成时间大于实际完成时间的条数
	 */
	Integer  queryActualTaskListNum(WarningInfoDTO warningInfoDTO);
	
	/**
	 *  根据分区日期查询有多少个监测点报警的信息
	 */
	Integer queryCheckWarningNum(WarningInfoDTO warningInfoDTO);
	/**
	 *查询噪声报警的个数,超限报警个数 ,超限报警个数 
	*/
	Integer queryTypeWarningNum(WarningInfoDTO warningInfoDTO);
	
	/**
	 * 查询检测点报警的详细信息
	 */
	List<TaskMsgVO> queryCheckWarningMsg(WarningInfoDTO warningInfoDTO);
	
	/**
	  * 计算检测点任务完成个数
	 */
	 Integer queryProActualTaskListNum(WarningInfoDTO warningInfoDTO);
	 
	 /**
	  * 计算监测点的任务总个数
	 */
	 Integer queryProTaskListNum(WarningInfoDTO warningInfoDTO);
	 
	 
	 /**
	  * 获取节点之下所有节点和节点名称(树形目录)
	  *
	  * @param bean 节点
	  * @return 节点集合
	  */
	 @Select("select tbltree.*,gis_exist_zone.p_code as code,gis_exist_zone.name,gis_exist_zone.rank,gis_exist_zone.smid from tbltree left join gis_exist_zone on  gis_exist_zone.p_code=tbltree.foreignkey where (seq & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq} and tbltree.type = #{type} order by tbltree.seq")
	 public List<TreeZoneVO> queryAllZone(@Param("seq") long seq, @Param("type") int type, @Param("mask") int mask, @Param("parentMask") int parentMask);


}
