package com.koron.inwlms.service.sysManager.impl;


import java.util.List;


import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;
import org.swan.bean.MessageBean;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.sysManager.LabelDTO;
import com.koron.inwlms.bean.DTO.sysManager.LabelExcelBean;
import com.koron.inwlms.bean.DTO.sysManager.QueryLabelDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.sysManager.LabelVO;
import com.koron.inwlms.bean.VO.sysManager.LoginLogVO;
import com.koron.inwlms.bean.VO.sysManager.PageLabelListVO;
import com.koron.inwlms.bean.VO.sysManager.UserListVO;
import com.koron.inwlms.mapper.common.FileMapper;
import com.koron.inwlms.mapper.sysManager.LabelMapper;
import com.koron.inwlms.service.sysManager.LabelService;
import com.koron.inwlms.util.PageUtil;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;

/**
 * @author lzy
 * @Date 2020/04/17
 *
 */

@Service
public class LabelServiceImpl implements LabelService{


	//查询标签 2020/04/17
	@TaskAnnotation("queryLabel")
	@Override
	public PageListVO<List<LabelVO>> queryLabel(SessionFactory factory, QueryLabelDTO queryLabelDTO) {
		LabelMapper labelMapper = factory.getMapper(LabelMapper.class);
		List<LabelVO> labelList=labelMapper.queryLabel(queryLabelDTO);
		int rowNumber = labelMapper.getLabelCount(queryLabelDTO);
		//返回数据结果
		PageListVO<List<LabelVO>> result = new PageListVO<>();
		result.setDataList(labelList);
		
		PageVO pageVO = PageUtil.getPageBean(queryLabelDTO.getPage(), queryLabelDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}
	
	//添加标签 2020/04/17
	@TaskAnnotation("addLabel")
	@Override
	public MessageBean<String> addLabel(SessionFactory factory,LabelDTO  labelDTO) {
		MessageBean<String> msg =MessageBean.create(Constant.MESSAGE_INT_SUCCESS,Constant.MESSAGE_STRING_SUCCESS, String.class);
		LabelMapper labelMapper = factory.getMapper(LabelMapper.class);
		try {
			LabelVO label = labelMapper.queryLabelByCode(labelDTO.getCode());
			if (label != null) {
				msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				msg.setDescription("添加标签失败(已存在相同code)");
			} else {
//				Gson jsonValue = new Gson();
//				// 查询条件字符串转对象，查询数据结果
//				UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);
//				labelDTO.setCreateBy(userListVO.getLoginName());
//				labelDTO.setUpdateBy(userListVO.getLoginName());
				labelMapper.addLabel(labelDTO);
			}
		} catch (Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			msg.setDescription(Constant.MESSAGE_STRING_ADDERROR);
		}
		
		return msg;
	}
	
	//删除标签 2020/04/17
	@TaskAnnotation("deleteLabel")
	@Override
	public Integer deleteLabel(SessionFactory factory, LabelDTO labelDTO) {		
		LabelMapper labelMapper = factory.getMapper(LabelMapper.class);
		Integer delResult=labelMapper.deleteLabel(labelDTO);
		return delResult;
	}
	
	//批量删除标签 2020/04/17
	@TaskAnnotation("deleteBatchLabel")
	@Override
	public Integer deleteBatchLabel(SessionFactory factory, LabelDTO labelDTO) {
		LabelMapper labelMapper = factory.getMapper(LabelMapper.class);
		//执行批量删除角色职员的操作
		Integer delResult=labelMapper.deleteBatchLabel(labelDTO.getLabelCodeList());
		return delResult;
	}
	
	
	//修改标签 2020/04/20
	@TaskAnnotation("updateLabel")
	@Override
	public MessageBean<String> updateLabel(SessionFactory factory,LabelDTO  labelDTO) {
		MessageBean<String> msg =MessageBean.create(Constant.MESSAGE_INT_SUCCESS,Constant.MESSAGE_STRING_SUCCESS, String.class);
		LabelMapper labelMapper = factory.getMapper(LabelMapper.class);
		try {
			LabelVO label = labelMapper.queryLabelByCode(labelDTO.getCode());
			if (label !=null && !label.getId().equals(labelDTO.getId())) {
				msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				msg.setDescription("标签code已存在");
			}
			else {
//				Gson jsonValue = new Gson();
//				UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);
//				labelDTO.setUpdateBy(userListVO.getLoginName());
				labelMapper.updateLabel(labelDTO);
			}
		} catch (Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			msg.setDescription(Constant.MESSAGE_STRING_ADDERROR);
		}
		
		return msg;
	}
	
	//根据id，获取附件基本信息 2020/04/21	 
	@TaskAnnotation("getAttachmentInfoById")
	@Override
	public UploadFileDTO getAttachmentInfoById(SessionFactory factory, Integer fileId) {
		FileMapper mapper = factory.getMapper(FileMapper.class);
		return mapper.getAttachmentInfoById(fileId);
	}
	
	//分页查询标签列表 2020/04/21 
	@TaskAnnotation("queryAllList")
	@Override
	public PageLabelListVO queryAllList(SessionFactory factory, QueryLabelDTO queryLabelDTO) {
		LabelMapper labelMapper = factory.getMapper(LabelMapper.class);
		List<LabelVO> labelList=labelMapper.queryLabel(queryLabelDTO);
		// 返回数据结果
		PageLabelListVO result = new PageLabelListVO();
		result.setDataList(labelList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryLabelDTO.getPage(), queryLabelDTO.getPageCount(), labelList.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}
	
	//批量导入标签 2020/04/21 
	@TaskAnnotation("uploadBatchLabel")
	@Override
	public MessageBean<String> uploadBatchLabel(SessionFactory factory, List<LabelExcelBean> labelExcelBeanList) {
        MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
        LabelMapper mapper = factory.getMapper(LabelMapper.class);
        boolean flag = true;
        for (LabelExcelBean labelExcelBean : labelExcelBeanList) {
            int i = mapper.countLabelByCode(labelExcelBean.getCode());
            if (i >= 1) {
                flag = false;
                break;
            }
//            Gson jsonValue = new Gson();
//			UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);
//            labelExcelBean.setCreateBy(userListVO.getLoginName());
//            labelExcelBean.setUpdateBy(userListVO.getLoginName());
        }
        if (flag) {
            try {
                mapper.uploadBatchLabel(labelExcelBeanList);

            } catch (Exception e) {
                msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
                msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
            }
        } else {
            msg.setCode(Constant.MESSAGE_INT_UPLOADERROR);
            msg.setDescription(Constant.MESSAGE_STRING_UPLOADERROR);
        }
        return msg;
    }
	
	
}
