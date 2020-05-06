package com.koron.inwlms.service.baseData;

import com.koron.inwlms.bean.VO.baseInf.PipeVO;
import org.koron.ebs.mybatis.SessionFactory;

import java.util.List;

public interface PipeService {

    public  List<PipeVO> queryALList(SessionFactory factory);
}
