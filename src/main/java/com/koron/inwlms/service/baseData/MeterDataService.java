package com.koron.inwlms.service.baseData;

import com.koron.inwlms.bean.DTO.baseInf.MeterDataDTO;
import com.koron.inwlms.bean.DTO.baseInf.MeterDataExcelBean;
import com.koron.inwlms.bean.VO.baseInf.MeterDataHisVO;
import com.koron.inwlms.bean.VO.baseInf.MeterDataVO;
import org.koron.ebs.mybatis.SessionFactory;

import java.util.List;

public interface MeterDataService {

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
    List<MeterDataVO> queryReadMeterDataList(SessionFactory factory, MeterDataDTO meterDataDTO);

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
    List<MeterDataHisVO> queryReadMeterDataHistoryList(SessionFactory factory, MeterDataDTO meterDataDTO);


    /**
     * 查询某一批次抄表数据
     * @param factory
     * @param BatchNo
     * @return
     */
    List<MeterDataVO> queryMeterDataByBatchNo(SessionFactory factory, String BatchNo);

}
