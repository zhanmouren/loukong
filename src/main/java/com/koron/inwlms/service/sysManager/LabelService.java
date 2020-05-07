package com.koron.inwlms.service.sysManager;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.web.multipart.MultipartFile;
import org.swan.bean.MessageBean;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.sysManager.LabelDTO;
import com.koron.inwlms.bean.DTO.sysManager.LabelExcelBean;
import com.koron.inwlms.bean.DTO.sysManager.QueryLabelDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.LabelVO;
import com.koron.inwlms.bean.VO.sysManager.PageLabelListVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;

/**
 * @author lzy
 * @Date 2020/04/17
 */
public interface LabelService {

	//查询标签
	PageListVO<List<LabelVO>> queryLabel(SessionFactory factory, QueryLabelDTO queryLabelDTO);
	//添加标签
	MessageBean<String> addLabel(SessionFactory factory,LabelDTO labelDTO);
	//删除标签
    Integer deleteLabel(SessionFactory factory,LabelDTO labelDTO);
    //批量删除标签
  	Integer deleteBatchLabel(SessionFactory factory, LabelDTO labelDTO);
  	//修改标签
  	MessageBean<String> updateLabel(SessionFactory factory,LabelDTO labelDTO);
  	//分页查询标签列表
  	PageLabelListVO queryAllList(SessionFactory factory,QueryLabelDTO queryLabelDTO);
  	//根据id，获取附件基本信息
	UploadFileDTO getAttachmentInfoById(SessionFactory factory,Integer fileId);

	
	//excel批量导入标签
	MessageBean<String> uploadBatchLabel(SessionFactory factory, List<LabelExcelBean> LabelExcelBeanList);
	
  	
}
