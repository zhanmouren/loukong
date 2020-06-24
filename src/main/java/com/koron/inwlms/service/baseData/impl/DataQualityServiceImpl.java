package com.koron.inwlms.service.baseData.impl;

import com.koron.inwlms.bean.DTO.baseInf.DataQualityDTO;
import com.koron.inwlms.bean.VO.baseInf.DataImpactVO;
import com.koron.inwlms.bean.VO.baseInf.DataQualityVO;
import com.koron.inwlms.bean.VO.baseInf.MonRepVO;
import com.koron.inwlms.bean.VO.baseInf.MonitorQuantityVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.mapper.baseData.DataQualityMapper;
import com.koron.inwlms.service.baseData.DataQualityService;
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
public class DataQualityServiceImpl implements DataQualityService {

    @TaskAnnotation("queryMonRep")
    public PageListVO<List<MonRepVO>> queryMonRep(SessionFactory factory, DataQualityDTO dqd){
        DataQualityMapper mapper = factory.getMapper(DataQualityMapper.class);
        List<MonRepVO> result = mapper.queryMonRep(dqd);
        PageListVO<List<MonRepVO>> plv = new PageListVO<>();

        MonRepVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(dqd.getPage(), dqd.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());


        return plv;
    }

    @TaskAnnotation("queryDataImpact")
    public List<DataImpactVO> queryDataImpact(SessionFactory factory){
        DataQualityMapper mapper = factory.getMapper(DataQualityMapper.class);
        List<DataImpactVO> list = mapper.queryDataImpact();
        return list;
    }

    @TaskAnnotation("addZoneConfDataQuality")
    public Integer addZoneConfDataQuality(SessionFactory factory, DataQualityVO dq){
        DataQualityMapper mapper = factory.getMapper(DataQualityMapper.class);
        Integer ret = mapper.addZoneConfDataQuality(dq);
        return ret;
    }

    @TaskAnnotation("queryMonitoringQuantity")
    public PageListVO<List<MonitorQuantityVO>> queryMonitoringQuantity(SessionFactory factory, DataQualityDTO dqd){
        DataQualityMapper mapper = factory.getMapper(DataQualityMapper.class);
        List<MonitorQuantityVO> result = mapper.queryMonitoringQuantity(dqd);
        MonitorQuantityVO r = result.get(result.size()-1);
        result.remove(result.size()-1);
        PageListVO<List<MonitorQuantityVO>> plv = new PageListVO<>();
        plv.setDataList(result);
        PageVO pageVO = PageUtil.getPageBean(dqd.getPage(), dqd.getPageCount(), r.getRows());
        plv.setTotalPage(pageVO.getTotalPage());
        plv.setRowNumber(pageVO.getRowNumber());
        plv.setPageCount(pageVO.getPageCount());
        plv.setPage(pageVO.getPage());

        return plv;
    }
}
