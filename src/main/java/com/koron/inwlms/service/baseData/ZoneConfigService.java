package com.koron.inwlms.service.baseData;

import com.koron.inwlms.bean.DTO.baseInf.ZoneDTO;
import com.koron.inwlms.bean.DTO.baseInf.ZoneMeterDTO;
import com.koron.inwlms.bean.DTO.baseInf.ZonePointDTO;
import com.koron.inwlms.bean.VO.baseInf.*;
import com.koron.inwlms.bean.VO.common.PageListVO;
import org.koron.ebs.mybatis.SessionFactory;

import java.util.List;

public interface ZoneConfigService {


    /**
     * 查询分区列表数据
     */
    public PageListVO<List<ZoneVO>> queryZoneList(SessionFactory factory, ZoneDTO zoneDTO);

    /**
     * 查询分区监测点列表数据
     * @param factory
     * @param zonePointDTO
     * @return
     */
    public PageListVO<List<ZonePointVO>> queryZonePointList(SessionFactory factory, ZonePointDTO zonePointDTO);

    /**
     * 查询分区监测点导入历史列表数据
     * @param factory
     * @param zonePointDTO
     * @return
     */
    public List<ZonePointHisVO> queryZonePointHistory(SessionFactory factory, ZonePointDTO zonePointDTO);

    /**
     * 查询分区监测点详情数据
     * @param factory
     * @param refID
     * @return
     */
    public ZonePointVO queryZonePointDet(SessionFactory factory, Integer refID);

    /**
     * 更新分区监测点数据
     * @param factory
     * @param zonePointDTO
     * @return
     */
    public Integer updateZonePointDet(SessionFactory factory, ZonePointDTO zonePointDTO);

    /**
     * 删除某一批次分区监测点数据
     * @param factory
     * @param BatchNo
     * @return
     */
    public Integer deleteZonePointByBatch(SessionFactory factory, String BatchNo);

    /**
     * 查询分区户表导入历史列表数据
     * @param factory
     * @param zoneMeterDTO
     * @return
     */
    public List<ZoneMeterHisVO> queryZoneMeterHistory(SessionFactory factory, ZoneMeterDTO zoneMeterDTO);

    /**
     * 查询分区户表列表数据
     * @param factory
     * @param zoneMeterDTO
     * @return
     */
    public PageListVO<List<ZoneMeterVO>> queryZoneMeterList(SessionFactory factory, ZoneMeterDTO zoneMeterDTO);

    /**
     * 查询分区户表详情数据
     * @param factory
     * @param r_code
     * @return
     */
    public ZoneMeterVO queryZoneMeterDet(SessionFactory factory, String r_code);

    /**
     * 更新分区户表数据
     * @param factory
     * @param zoneMeterDTO
     * @return
     */
    public Integer updateZoneMeterDet(SessionFactory factory, ZoneMeterDTO zoneMeterDTO);

    /**
     * 删除分区与户表关系数据
     * @param factory
     * @param refID
     * @return
     */
    public Integer deleteZoneMeterRel(SessionFactory factory, Integer refID);

    /**
     * 删除某一批次分区户表数据
     * @param factory
     * @param BatchNo
     * @return
     */
    public boolean deleteZoneMeterByBatchNo(SessionFactory factory, String BatchNo);

}
