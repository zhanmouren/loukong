package com.koron.inwlms.mapper.baseData;

import com.koron.inwlms.bean.DTO.baseInf.DataQualityDTO;
import com.koron.inwlms.bean.VO.baseInf.DataImpactVO;
import com.koron.inwlms.bean.VO.baseInf.DataQualityVO;
import com.koron.inwlms.bean.VO.baseInf.MonRepVO;

import java.util.List;

public interface DataQualityMapper {

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
     * 添加分区监测点导入历史
     */
    Integer addZoneConfDataQuality(DataQualityVO dq);
}
