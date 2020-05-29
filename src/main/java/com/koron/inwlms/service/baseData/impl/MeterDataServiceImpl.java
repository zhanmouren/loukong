package com.koron.inwlms.service.baseData.impl;

import com.koron.inwlms.bean.DTO.baseInf.MeterDataDTO;
import com.koron.inwlms.bean.DTO.baseInf.MeterDataExcelBean;
import com.koron.inwlms.bean.VO.baseInf.MeterDataHisVO;
import com.koron.inwlms.bean.VO.baseInf.MeterDataVO;
import com.koron.inwlms.mapper.baseData.IMDataMapper;
import com.koron.inwlms.service.baseData.MeterDataService;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auother:zhongweibin
 * @date:2020-05-12
 * @description:
 */
@Service
public class MeterDataServiceImpl implements MeterDataService {

    /**
     * 批量插入监测数据
     * @param factory
     * @param excelBeans
     * @return
     */
    @TaskAnnotation("addBatchMeterData")
    @Override
    public Integer addBatchMeterData(SessionFactory factory,List<MeterDataExcelBean> excelBeans){
        IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
        Integer result = mapper.addBatchMeterData(excelBeans);
        return result;
    }

    /**
     * 查询抄表列表数据
     * @param factory
     * @param meterDataDTO
     * @return
     */
    public List<MeterDataVO> queryReadMeterDataList(SessionFactory factory, MeterDataDTO meterDataDTO) {
        IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
        List<MeterDataVO> result = mapper.queryReadMeterDataList(meterDataDTO);
        return result;
    }

    /**
     * 查询抄表数据详情
     * @param factory
     * @param id
     * @return
     */
    public MeterDataVO queryReadMeterDataDet(SessionFactory factory, Integer id) {
        IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
        MeterDataVO result = mapper.queryReadMeterDataDet(id);
        return result;
    }

    /**
     * 修改抄表详情数据
     * @param factory
     * @param meterDataDTO
     * @return
     */
    public boolean updateReadMeterDataDet(SessionFactory factory, MeterDataDTO meterDataDTO){
        IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
        boolean result = mapper.updateReadMeterDataDet(meterDataDTO);
        return result;
    }

    /**
     * 查询抄表导入历史列表数据
     * @param factory
     * @param meterDataDTO
     * @return
     */
    public List<MeterDataHisVO> queryReadMeterDataHistoryList(SessionFactory factory, MeterDataDTO meterDataDTO) {
        IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
        List<MeterDataHisVO> result = mapper.queryReadMeterDataHistoryList(meterDataDTO);
        return result;
    }


    /**
     * 查询某一批次抄表数据
     * @param factory
     * @param BatchNo
     * @return
     */
    public List<MeterDataVO> queryMeterDataByBatchNo(SessionFactory factory, String BatchNo) {
        IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
        List<MeterDataVO> result = mapper.queryMeterDataByBatchNo(BatchNo);
        return result;
    }

}
