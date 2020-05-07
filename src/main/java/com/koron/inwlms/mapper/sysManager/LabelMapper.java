package com.koron.inwlms.mapper.sysManager;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.sysManager.LabelDTO;
import com.koron.inwlms.bean.DTO.sysManager.LabelExcelBean;
import com.koron.inwlms.bean.DTO.sysManager.QueryLabelDTO;
import com.koron.inwlms.bean.VO.sysManager.LabelVO;

/**
 * @author lzy
 * @Date 2020/04/17
 */

@Repository
public interface LabelMapper {
	
	//查询标签
	public List<LabelVO> queryLabel(QueryLabelDTO queryLabelDTO);
	//添加标签
	public Integer addLabel(LabelDTO labelDTO);	
	//删除标签
    public Integer deleteLabel(LabelDTO labelDTO);
    //删除标签(批量)
  	public Integer deleteBatchLabel(List<String> labelCodeList);
  	//根据code查找标签
  	public LabelVO queryLabelByCode(String code);
  	//修改标签
  	public Integer updateLabel(LabelDTO labelDTO);	
  	

  	//根据code查重
    public int countLabelByCode(String code);

  	//excel批量新增
    public int uploadBatchLabel(List<LabelExcelBean> labelExcelBeanList);
}
