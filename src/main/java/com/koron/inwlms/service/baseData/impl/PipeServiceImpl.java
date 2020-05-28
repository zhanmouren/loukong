package com.koron.inwlms.service.baseData.impl;

import com.koron.inwlms.bean.VO.baseInf.DataVO;
import com.koron.inwlms.mapper.baseData.PropertyMapper;
import com.koron.inwlms.service.baseData.PipeService;
import com.koron.inwlms.util.PositionUtil;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @auother:zhongweibin
 * @date:2020-04-01
 * @description:
 */
@Service
public class PipeServiceImpl implements PipeService {


    @TaskAnnotation("queryALList")
    public List<DataVO> queryALList(SessionFactory factory){
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        List<DataVO> list = mapper.queryALList();
        List<DataVO> result = new ArrayList<>();



            for(DataVO temp : list){
                DataVO tempResult = new DataVO();
                String position = "";
                position=position+PositionUtil.getDmaPositionShape(temp.getSmgeometry()).replace("[[[",",[[")
                 .replace("]]]","]]");
                position="["+position.substring(1,position.length()-1)+"]]";
                //处理shape边界
                tempResult.setFeatid(temp.getFeatid());
                tempResult.setName(temp.getName());
                tempResult.setObjtype(temp.getObjtype());
                tempResult.setP_code(temp.getP_code());
                tempResult.setRank(temp.getRank());
                tempResult.setShape_area(temp.getShape_area());
                tempResult.setShape_length(temp.getShape_length());
                tempResult.setSmid(temp.getSmid());
                tempResult.setSubtype(temp.getSubtype());
                tempResult.setSmgeometry(position);
                result.add(tempResult);
            }


        return result;
    }
}
