package com.koron.inwlms.service.baseData;

import com.koron.inwlms.bean.DTO.baseInf.MeterDataDTO;
import com.koron.inwlms.bean.DTO.baseInf.MeterDataExcelBean;
import com.koron.inwlms.bean.VO.baseInf.PointAccountVO;
import com.koron.inwlms.bean.VO.baseInf.MeterDataHisVO;
import com.koron.inwlms.bean.VO.baseInf.MeterDataVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import org.koron.ebs.mybatis.SessionFactory;

import java.util.List;

public interface MeterDataService {

    /**
     * 删除抄表数据
     * @param factory
     * @param refID
     * @return
     */
    public Integer deleteMeterData(SessionFactory factory, Integer refID);

    /**
     * 删除某一批次抄表数据
     * @param factory
     * @param BatchNo
     * @return
     */
    public Integer deleteMeterDataByBatch(SessionFactory factory, String BatchNo);

    /**
     * 查询户表类型统计
     */
    public List<PointAccountVO> queryFlows(SessionFactory factory);

    /**
     * 批量插入抄表数据
     * @param factory
     * @param excelBeans
     * @return
     */
    public Integer addBatchMeterData(SessionFactory factory,List<MeterDataExcelBean> excelBeans);

    /**
     * 查询抄表列表数据
     * @param factory
     * @param meterDataDTO
     * @return
     */
    PageListVO<List<MeterDataVO>> queryReadMeterDataList(SessionFactory factory, MeterDataDTO meterDataDTO);

    /**
     * 查询抄表数据详情
     * @param factory
     * @param id
     * @return
     */
    MeterDataVO queryReadMeterDataDet(SessionFactory factory, Integer id);

    /**
     * 修改抄表详情数据
     * @param factory
     * @param meterDataDTO
     * @return
     */
    boolean updateReadMeterDataDet(SessionFactory factory, MeterDataDTO meterDataDTO);

    /**
     * 查询抄表导入历史列表数据
     * @param factory
     * @param meterDataDTO
     * @return
     */
    PageListVO<List<MeterDataHisVO>> queryReadMeterDataHistoryList(SessionFactory factory, MeterDataDTO meterDataDTO);


    /**
     * 查询某一批次抄表数据
     * @param factory
     * @param meterDataDTO
     * @return
     */
    PageListVO<List<MeterDataVO>> queryMeterDataByBatchNo(SessionFactory factory, MeterDataDTO meterDataDTO);

}
