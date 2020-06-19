package com.koron.inwlms.service.baseData;

import com.koron.inwlms.bean.DTO.baseInf.DataQualityDTO;
import com.koron.inwlms.bean.VO.baseInf.DataImpactVO;
import com.koron.inwlms.bean.VO.baseInf.MonRepVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import org.koron.ebs.mybatis.SessionFactory;

import java.util.List;

public interface DataQualityService {

    public PageListVO<List<MonRepVO>> queryMonRep(SessionFactory factory, DataQualityDTO dqd);

    public List<DataImpactVO> queryDataImpact(SessionFactory factory);

}
