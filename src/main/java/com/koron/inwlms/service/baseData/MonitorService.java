package com.koron.inwlms.service.baseData;

import com.koron.inwlms.bean.DTO.baseInf.MonitorDataDTO;
import com.koron.inwlms.bean.DTO.baseInf.MonitorDataExcelBean;
import com.koron.inwlms.bean.VO.baseInf.DataQualityVO;
import com.koron.inwlms.bean.VO.baseInf.MonitorDataHisVO;
import com.koron.inwlms.bean.VO.baseInf.MonitorDataVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import org.koron.ebs.mybatis.SessionFactory;

import java.util.List;

public interface MonitorService {

    /**
     * 查询监测点最新监测数据
     */
    public List<MonitorDataVO> queryLastMonitorDataList(SessionFactory factory,MonitorDataDTO monitorDataDTO);

    /**
     * 批量插入监测数据
     * @param factory
     * @param excelBeans
     * @return
     */
    public Integer addBatchMointorData(SessionFactory factory, List<MonitorDataExcelBean> excelBeans, DataQualityVO dq);

    /**
     * 查询压力/流量监测列表数据
     * @param factory
     * @param monitorDataDTO
     * @return
     */
    public PageListVO<List<MonitorDataVO>> queryPressureFlowList(SessionFactory factory, MonitorDataDTO monitorDataDTO);

    /**
     * 查询压力/流量监测详情数据
     * @param factory
     * @param id
     * @return
     */
    public MonitorDataVO queryPressureFlowDet(SessionFactory factory, Integer id);

    /**
     * 查询监测导入历史数据
     * @param factory
     * @param monitorDataDTO
     * @return
     */
    public PageListVO<List<MonitorDataHisVO>> queryMonitorDataHistoryList(SessionFactory factory, MonitorDataDTO monitorDataDTO);

    /**
     * 查询某一批次监测数据
     * @param factory
     * @param monitorDataDTO
     * @return
     */
    public PageListVO<List<MonitorDataVO>> queryMonitorDataByBatchNo(SessionFactory factory,MonitorDataDTO monitorDataDTO);


    /**
     * 查询噪音列表数据
     * @param factory
     * @param monitorDataDTO
     * @return
     */
    public PageListVO<List<MonitorDataVO>> queryNoiseList(SessionFactory factory, MonitorDataDTO monitorDataDTO);


    /**
     * 查询噪音详情数据
     * @param factory
     * @param id
     * @return
     */
    public MonitorDataVO queryNoiseDet(SessionFactory factory, Integer id);

    /**
     * 更新监测详情数据
     * @param factory
     * @param monitorDataDTO
     * @return
     */
    public boolean updateMonitorDet(SessionFactory factory, MonitorDataDTO monitorDataDTO);



}
