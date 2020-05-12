package com.koron.inwlms.mapper.sysManager;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.sysManager.LabelDTO;
import com.koron.inwlms.bean.DTO.sysManager.LabelExcelBean;
import com.koron.inwlms.bean.DTO.sysManager.QueryLabelDTO;
import com.koron.inwlms.bean.VO.sysManager.LabelNameVO;
import com.koron.inwlms.bean.VO.sysManager.LabelVO;

/**
 * @author lzy
 * @Date 2020/04/17
 */

@Repository
public interface LabelMapper {
	
	//查询标签
	public List<LabelVO> queryLabel(QueryLabelDTO queryLabelDTO);
	//查询标签条数
	public int getLabelCount(QueryLabelDTO queryLabelDTO);
		
	//添加标签
	public Integer addLabel(LabelDTO labelDTO);	
	//删除标签
    public Integer deleteLabel(LabelDTO labelDTO);
    //删除标签(批量)
  	public Integer deleteBatchLabel(List<String> labelCodeList);
  	//根据code查找标签
  	public LabelVO queryLabelByCode(String code);
    //根据id查找标签
    public LabelVO queryLabelById(Integer id);
  	//修改标签
  	public Integer updateLabel(LabelDTO labelDTO);	
  	
  	//根据code查重
    public int countLabelByCode(String code);
    
  	//excel批量新增
    public int uploadBatchLabel(List<LabelExcelBean> labelExcelBeanList);
    
    //查询标签简体中文名
  	public List<LabelNameVO> queryLabelCnList(QueryLabelDTO queryLabelDTO);
  	 //查询标签英文名
  	public List<LabelNameVO> queryLabelEnList(QueryLabelDTO queryLabelDTO);
  	 //查询标签繁体中文名
  	public List<LabelNameVO> queryLabelCnHKList(QueryLabelDTO queryLabelDTO);
}
