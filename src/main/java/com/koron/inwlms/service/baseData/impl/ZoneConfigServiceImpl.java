package com.koron.inwlms.service.baseData.impl;

import com.koron.inwlms.bean.DTO.baseInf.*;
import com.koron.inwlms.bean.VO.baseInf.*;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.mapper.baseData.PropertyMapper;
import com.koron.inwlms.service.baseData.ZoneConfigService;
import com.koron.inwlms.util.PageUtil;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auother:zhongweibin
 * @date:2020-04-01
 * @description:
 */
@Service
public class ZoneConfigServiceImpl implements ZoneConfigService {


    /**
     * 导入分区监测点数据
     */
    @TaskAnnotation("addBatchZonePoint")
    @Override
    public Integer addBatchZonePoint(SessionFactory factory,List<ZonePointExcelBean> excelBeans){
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        Integer result = mapper.addBatchZonePoint(excelBeans);
        return result;
    }

    /**
     * 分区列表数据
     *
     * @param factory
     * @param zoneDTO
     * @return
     */
    @TaskAnnotation("queryZoneList")
    @Override
    public PageListVO<List<ZoneVO>> queryZoneList(SessionFactory factory, ZoneDTO zoneDTO) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        List<ZoneVO> result = mapper.queryZoneList(zoneDTO);

        PageListVO<List<ZoneVO>> plv = new PageListVO<>();

        ZoneVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(zoneDTO.getPage(), zoneDTO.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());


        return plv;
    }

    /**
     * 分区与监测点列表数据
     *
     * @param factory
     * @param zonePointDTO
     * @return
     */
    @TaskAnnotation("queryZonePointList")
    @Override
    public PageListVO<List<ZonePointVO>> queryZonePointList(SessionFactory factory, ZonePointDTO zonePointDTO) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        List<ZonePointVO> result = mapper.queryZonePointList(zonePointDTO);

        PageListVO<List<ZonePointVO>> plv = new PageListVO<>();
        ZonePointVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(zonePointDTO.getPage(), zonePointDTO.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());

        return plv;
    }

    /**
     * 分区与监测点导入历史列表数据
     *
     * @param factory
     * @param zonePointDTO
     * @return
     */
    @TaskAnnotation("queryZonePointHistory")
    @Override
    public PageListVO<List<ZonePointHisVO>> queryZonePointHistory(SessionFactory factory,ZonePointDTO zonePointDTO) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        List<ZonePointHisVO> result = mapper.queryZonePointHistory(zonePointDTO);
        PageListVO<List<ZonePointHisVO>> plv = new PageListVO<>();
        ZonePointHisVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(zonePointDTO.getPage(), zonePointDTO.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());

        return plv;
    }

    /**
     * 分区与监测点详情数据
     *
     * @param factory
     * @param refID
     * @return
     */
    @TaskAnnotation("queryZonePointDet")
    @Override
    public ZonePointVO queryZonePointDet(SessionFactory factory, Integer refID) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        ZonePointVO result = mapper.queryZonePointDet(refID);
        return result;
    }

    /**
     * 更新分区与监测点详情数据
     *
     * @param factory
     * @param zonePointDTO
     * @return
     */
    @TaskAnnotation("updateZonePointDet")
    @Override
    public Integer updateZonePointDet(SessionFactory factory, ZonePointDTO zonePointDTO) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        Integer result = mapper.updateZonePointDet(zonePointDTO);
        return result;
    }

    /**
     * 删除某一批次分区与监测点数据
     *
     * @param factory
     * @param BatchNo
     * @return
     */
    @TaskAnnotation("deleteZonePointByBatch")
    @Override
    public Integer deleteZonePointByBatch(SessionFactory factory, String BatchNo) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        Integer result = mapper.deleteZonePointByBatch(BatchNo);
        return result;
    }

    /**
     * 导入分区户表数据
     */
    @TaskAnnotation("addBatchZoneMeter")
    @Override
    public Integer addBatchZoneMeter(SessionFactory factory,List<ZoneMeterExcelBean> excelBeans){
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        Integer result = mapper.addBatchZoneMeter(excelBeans);
        return result;
    }

    /**
     * 查询分区户表列表数据
     * @param factory
     * @param zoneMeterDTO
     * @return
     */
    @TaskAnnotation("queryZoneMeterList")
    @Override
    public PageListVO<List<ZoneMeterVO>> queryZoneMeterList(SessionFactory factory, ZoneMeterDTO zoneMeterDTO){
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        List<ZoneMeterVO> result = mapper.queryZoneMeterList(zoneMeterDTO);
        PageListVO<List<ZoneMeterVO>> plv = new PageListVO<>();
        ZoneMeterVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(zoneMeterDTO.getPage(), zoneMeterDTO.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());
        return plv;
    }

    /**
     * 查询分区户表列表数据
     * @param factory
     * @param zoneMeterDTO
     * @return
     */
    @TaskAnnotation("queryZoneMeterHistory")
    @Override
    public PageListVO<List<ZoneMeterHisVO>> queryZoneMeterHistory(SessionFactory factory, ZoneMeterDTO zoneMeterDTO){
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        List<ZoneMeterHisVO> result = mapper.queryZoneMeterHistory(zoneMeterDTO);
        PageListVO<List<ZoneMeterHisVO>> plv = new PageListVO<>();
        ZoneMeterHisVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(zoneMeterDTO.getPage(), zoneMeterDTO.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());
        return plv;
    }

    /**
     * 分区与户表详情数据
     *
     * @param factory
     * @param refID
     * @return
     */
    @TaskAnnotation("queryZoneMeterDet")
    @Override
    public ZoneMeterVO queryZoneMeterDet(SessionFactory factory, Integer refID) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        ZoneMeterVO result = mapper.queryZoneMeterDet(refID);
        return result;
    }

    /**
     * 更新分区与户表详情数据
     *
     * @param factory
     * @param zoneMeterDTO
     * @return
     */
    @TaskAnnotation("updateZoneMeterDet")
    @Override
    public Integer updateZoneMeterDet(SessionFactory factory, ZoneMeterDTO zoneMeterDTO) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        Integer result = mapper.updateZoneMeterDet(zoneMeterDTO);
        return result;
    }

    /**
     * 删除分区与户表数据
     *
     * @param factory
     * @param refID
     * @return
     */
    @TaskAnnotation("deleteZoneMeterRel")
    @Override
    public Integer deleteZoneMeterRel(SessionFactory factory, Integer refID) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        Integer result = mapper.deleteZoneMeterRel(refID);
        return result;
    }

    /**
     * 删除某一批次分区与监测点数据
     *
     * @param factory
     * @param BatchNo
     * @return
     */
    @TaskAnnotation("deleteZoneMeterByBatchNo")
    @Override
    public boolean deleteZoneMeterByBatchNo(SessionFactory factory, String BatchNo) {
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        boolean result = mapper.deleteZoneMeterByBatchNo(BatchNo);
        return result;
    }
}
