package com.koron.inwlms.mapper.baseData;

import com.koron.inwlms.bean.DTO.baseInf.DataQualityDTO;
import com.koron.inwlms.bean.VO.baseInf.DataImpactVO;
import com.koron.inwlms.bean.VO.baseInf.DataQualityVO;
import com.koron.inwlms.bean.VO.baseInf.MonRepVO;
import com.koron.inwlms.bean.VO.baseInf.MonitorQuantityVO;

import java.util.List;

public interface DataQualityMapper {

    /**
     * 更新监测水量数据
     */
    Integer updateMonitoringQuantity(DataQualityDTO dqd);

    /**
     * 数据月报
     * @param dqd
     * @return
     */
    List<MonRepVO> queryMonRep(DataQualityDTO dqd);

    /**
     * 数据影响分析
     * @return
     */
    List<DataImpactVO> queryDataImpact();

    /**
     * 添加分区配置导入历史
     */
    Integer addZoneConfDataQuality(DataQualityVO dq);

    /**
     * 监测水量校对
     */
    List<MonitorQuantityVO> queryMonitoringQuantity(DataQualityDTO dqd);
}
