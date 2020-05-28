package com.koron.inwlms.service.baseData;

import com.koron.inwlms.bean.VO.baseInf.DataVO;
import org.koron.ebs.mybatis.SessionFactory;

import java.util.List;

public interface PipeService {
    public  List<DataVO> queryALList(SessionFactory factory);


}
