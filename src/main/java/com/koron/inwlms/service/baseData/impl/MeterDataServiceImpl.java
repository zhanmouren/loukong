package com.koron.inwlms.service.baseData.impl;

import com.koron.inwlms.bean.DTO.baseInf.MeterDataDTO;
import com.koron.inwlms.bean.DTO.baseInf.MeterDataExcelBean;
import com.koron.inwlms.bean.VO.baseInf.PointAccountVO;
import com.koron.inwlms.bean.VO.baseInf.MeterDataHisVO;
import com.koron.inwlms.bean.VO.baseInf.MeterDataVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.mapper.baseData.IMDataMapper;
import com.koron.inwlms.mapper.baseData.PropertyMapper;
import com.koron.inwlms.service.baseData.MeterDataService;
import com.koron.inwlms.util.PageUtil;
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
     * 查询户表类型统计数据
     * @param factory
     * @return
     */
    @TaskAnnotation("queryFlows")
    @Override
    public List<PointAccountVO> queryFlows(SessionFactory factory) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        List<PointAccountVO> result = mapper.queryFlows();
        return result;
    }

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
    @TaskAnnotation("queryReadMeterDataList")
    @Override
    public PageListVO<List<MeterDataVO>> queryReadMeterDataList(SessionFactory factory, MeterDataDTO meterDataDTO) {
        IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
        List<MeterDataVO> result = mapper.queryReadMeterDataList(meterDataDTO);
        PageListVO<List<MeterDataVO>> plv = new PageListVO<>();
        MeterDataVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(meterDataDTO.getPage(), meterDataDTO.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());
        return plv;
    }

    /**
     * 查询抄表数据详情
     * @param factory
     * @param id
     * @return
     */
    @TaskAnnotation("queryReadMeterDataDet")
    @Override
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
    @TaskAnnotation("updateReadMeterDataDet")
    @Override
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
    @TaskAnnotation("queryReadMeterDataHistoryList")
    @Override
    public PageListVO<List<MeterDataHisVO>> queryReadMeterDataHistoryList(SessionFactory factory, MeterDataDTO meterDataDTO) {
        IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
        List<MeterDataHisVO> result = mapper.queryReadMeterDataHistoryList(meterDataDTO);
        PageListVO<List<MeterDataHisVO>> plv = new PageListVO<>();
        MeterDataHisVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(meterDataDTO.getPage(), meterDataDTO.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());
        return plv;
    }


    /**
     * 查询某一批次抄表数据
     * @param factory
     * @param meterDataDTO
     * @return
     */
    @TaskAnnotation("queryMeterDataByBatchNo")
    @Override
    public PageListVO<List<MeterDataVO>> queryMeterDataByBatchNo(SessionFactory factory, MeterDataDTO meterDataDTO) {
        IMDataMapper mapper = factory.getMapper(IMDataMapper.class);
        List<MeterDataVO> result = mapper.queryMeterDataByBatchNo(meterDataDTO);
        PageListVO<List<MeterDataVO>> plv = new PageListVO<>();
        MeterDataVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(meterDataDTO.getPage(), meterDataDTO.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());
        return plv;
    }

}
