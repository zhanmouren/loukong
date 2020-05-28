package com.koron.inwlms.mapper.baseData;

import com.koron.inwlms.bean.VO.baseInf.MonRepVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataQualityMapper {

    /**
     * 数据月报
     * @param reportDate
     * @return
     */
    List<MonRepVO> queryMonRep(@Param("reportDate")String reportDate);
}
