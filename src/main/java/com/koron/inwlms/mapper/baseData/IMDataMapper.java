package com.koron.inwlms.mapper.baseData;

import com.koron.inwlms.bean.DTO.baseInf.MeterDataDTO;
import com.koron.inwlms.bean.DTO.baseInf.MeterDataExcelBean;
import com.koron.inwlms.bean.DTO.baseInf.MonitorDataDTO;
import com.koron.inwlms.bean.DTO.baseInf.MonitorDataExcelBean;
import com.koron.inwlms.bean.VO.baseInf.MeterDataHisVO;
import com.koron.inwlms.bean.VO.baseInf.MeterDataVO;
import com.koron.inwlms.bean.VO.baseInf.MonitorDataHisVO;
import com.koron.inwlms.bean.VO.baseInf.MonitorDataVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMDataMapper {

    /**
     * 批量删除抄表数据
     * @param refID
     * @return
     */
    Integer deleteMeterData(@Param("refID")Integer refID);

    /**
     * 批量删除抄表数据
     * @param BatchNo
     * @return
     */
    Integer deleteMeterDataByBatch(@Param("BatchNo")String BatchNo);

    /**
     * 批量添加监测数据
     * @param excelBeans
     * @return
     */
    Integer addBatchMointorData(@Param("excelBeans") List<MonitorDataExcelBean> excelBeans);

    /**
     * 查询监测压力/流量数据列表数据
     * @param monitorDataDTO
     * @return
     */
    List<MonitorDataVO> queryPressureFlowList(MonitorDataDTO monitorDataDTO);

    /**
     * 查询监测压力/流量数据详情数据
     * @param id
     * @return
     */
    MonitorDataVO queryPressureFlowDet(@Param("id") Integer id);

    /**
     * 查询监测数据导入历史列表数据
     * @param monitorDataDTO
     * @return
     */
    List<MonitorDataHisVO> queryMonitorDataHistoryList(MonitorDataDTO monitorDataDTO);


    /**
     * 查询某一批次监测数据
     * @param monitorDataDTO
     * @return
     */
    List<MonitorDataVO> queryMonitorDataByBatchNo(MonitorDataDTO monitorDataDTO);

    /**
     * 查询监测噪声数据列表数据
     * @param monitorDataDTO
     * @return
     */
    List<MonitorDataVO> queryNoiseList(MonitorDataDTO monitorDataDTO);

    /**
     * 查询监测噪声数据详情数据
     * @param id
     * @return
     */
    MonitorDataVO queryNoiseDet(@Param("id") Integer id);


    /**
     * 更新监测数据详情
     * @param monitorDataDTO
     * @return
     */
    boolean updateMonitorDet(MonitorDataDTO monitorDataDTO);

    /**
     * 批量添加抄表数据
     * @param excelBeans
     * @return
     */
    Integer addBatchMeterData(@Param("excelBeans") List<MeterDataExcelBean> excelBeans);

    /**
     * 查询抄表数据列表数据
     * @param meterDataDTO
     * @return
     */
    List<MeterDataVO> queryReadMeterDataList(MeterDataDTO meterDataDTO);

    /**
     * 查询抄表详情数据
     * @param id
     * @return
     */
    MeterDataVO queryReadMeterDataDet(@Param("id") Integer id);

    /**
     * 更新抄表详情数据
     * @param meterDataDTO
     * @return
     */
    boolean updateReadMeterDataDet(MeterDataDTO meterDataDTO);

    /**
     * 查询抄表导入历史列表数据
     * @param meterDataDTO
     * @return
     */
    List<MeterDataHisVO> queryReadMeterDataHistoryList(MeterDataDTO meterDataDTO);

    /**
     * 查询某一批次抄表数据
     * @param BatchNo
     * @return
     */
    List<MeterDataVO> queryMeterDataByBatchNo(MeterDataDTO meterDataDTO);

}
