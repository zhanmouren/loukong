package com.koron.inwlms.mapper.baseData;

import com.koron.inwlms.bean.DTO.baseInf.*;
import com.koron.inwlms.bean.VO.baseInf.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@EnvSource("SEC")
public interface PropertyMapper {

    @Select("select smid,smuserid,smarea,smperimeter,ST_AsText(smgeometry)smgeometry,featid,subtype,objtype,p_code,name,rank,shape_length,shape_area from public.\"GDH_DMA\"")
    List<DataVO> queryALList();

    /**
     * 添加分区
     * @param zoneDTO
     * @return
     */
    Integer addZone(ZoneDTO zoneDTO);

    /**
     * 获取最大分区编号
     * @param zoneDTO
     * @return
     */
    ZoneVO queryMaxZoneNo(ZoneDTO zoneDTO);

    /**
     * 获取监测点最近监测数据
     * @return
     */
    List<MonitorDataVO> queryLastMonitorDataList(MonitorDataDTO monitorDataDTO);

    /**
     * 查询户表类型汇总数
      * @return
     */
    List<PointAccountVO> queryFlows();

    /**
     * 添加分区负责人
     * @param zoneDTO
     * @return
     */
    Integer addChargeZones(ZoneDTO zoneDTO);

    /**
     * 删除负责分区
     * @param zoneDTO
     * @return
     */
    Integer deleteChargeZones(ZoneDTO zoneDTO);

    /**
     * 查询负责分区列表
     * @param zoneDTO
     * @return
     */
    List<ZoneUserVO> queryChargeZones(ZoneDTO zoneDTO);

    /**
     * 批量添加分区监测点数据
     * @param excelBeans
     * @return
     */
    Integer addBatchZonePoint(@Param("excelBeans")List<ZonePointExcelBean> excelBeans);

    /**
     * 批量添加分区户表数据
     * @param excelBeans
     * @return
     */
    Integer addBatchZoneMeter(@Param("excelBeans")List<ZoneMeterExcelBean> excelBeans);

    /**
     * 查询管线数据
     */
    List<PipeLineVO> queryPipes(PipeDTO pipeDTO);

    /**
     * 查询分区列表数据
     * @param zoneDTO
     * @return
     */
    List<ZoneVO> queryZoneList(ZoneDTO zoneDTO);

    /**
     * 查询分区与监测点列表数据
     * @param zonePointDTO
     * @return
     */
    List<ZonePointVO>  queryZonePointList(ZonePointDTO zonePointDTO);

    /**
     * 查询分区与监测点导入历史列表数据
     * @param zonePointDTO
     * @return
     */
    List<ZonePointHisVO>  queryZonePointHistory(ZonePointDTO zonePointDTO);

    /**
     * 查询分区与监测点导入详情数据
     * @param refID
     * @return
     */
    ZonePointVO queryZonePointDet(@Param("refID") Integer refID);

    /**
     * 更新分区与监测点详情数据
     * @param zonePointDTO
     * @return
     */
    Integer updateZonePointDet(ZonePointDTO zonePointDTO);

    /**
     * 删除某一批次分区与监测点数据
     * @param BatchNo
     * @return
     */
    Integer deleteZonePointByBatch(@Param("BatchNo") String BatchNo);

    /**
     * 查询分区与户表列表数据
     * @param zonePointDTO
     * @return
     */
    List<ZoneMeterVO>  queryZoneMeterList(ZoneMeterDTO zonePointDTO);

    /**
     * 查询分区与户表导入历史列表数据
     * @param zoneMeterDTO
     * @return
     */
    List<ZoneMeterHisVO>  queryZoneMeterHistory(ZoneMeterDTO zoneMeterDTO);

    /**
     * 查询分区与户表详情数据
     * @param refID
     * @return
     */
    ZoneMeterVO queryZoneMeterDet(@Param("refID") Integer refID);

    /**
     * 更新分区与户表详情数据
     * @param zoneMeterDTO
     * @return
     */
    Integer updateZoneMeterDet(ZoneMeterDTO zoneMeterDTO);

    /**
     * 删除分区与户表数据
     * @param refID
     * @return
     */
    Integer deleteZoneMeterRel(@Param("refID") Integer refID);

    /**
     * 删除某一批次分区与户表数据
     * @param BatchNo
     * @return
     */
    boolean deleteZoneMeterByBatchNo(@Param("BatchNo") String BatchNo);
}
