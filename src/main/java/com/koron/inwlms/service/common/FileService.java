package com.koron.inwlms.service.common;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.VO.common.UploadFileVO;


/**
 * 文件service
 * @author csh
 *
 */
public interface FileService {
	/**
	 * 新增附件信息
	 * @param factory
	 * @param uploadFileDTO
	 * @return
	 */
	public int insertFileData(SessionFactory factory,UploadFileDTO uploadFileDTO);
	/**
	 * 查询附件路径信息
	 * @param factory
	 * @param fileId
	 * @return
	 */
	public String queryFilePath(SessionFactory factory,String fileId);
	/**
	 * 删除附件信息
	 * @param factory
	 * @param fileId
	 * @return
	 */

	public int deleteFileById(SessionFactory factory,String fileId);
	/**
	 * 根据id，获取附件基本信息
	 * @param factory
	 * @param fileId
	 * @return
	 */
	public UploadFileDTO getAttachmentInfoById(SessionFactory factory,String fileId);

	/**
	 * 根据tId，获取附件基本信息
	 * @param factory
	 * @param fileId
	 * @return
	 */
	public UploadFileDTO getAttachmentInfoByTid(SessionFactory factory,String tid);

}
