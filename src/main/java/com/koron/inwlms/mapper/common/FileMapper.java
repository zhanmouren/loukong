package com.koron.inwlms.mapper.common;

import org.apache.ibatis.annotations.Param;

import com.koron.inwlms.bean.DTO.common.FilerelationDTO;
import com.koron.inwlms.bean.DTO.common.UploadFileDTO;

public interface FileMapper {

	public int insertFileData(UploadFileDTO uploadFileDTO);
	
	public int insertFileDataReturnId(UploadFileDTO uploadFileDTO);
	
	public Integer insertFilerelationData(FilerelationDTO filerelationDTO);
	
	public String queryFilePath(@Param("fileId") Integer fileId);
	
	public int deleteFileById(@Param("fileId") Integer fileId);
	
	public UploadFileDTO getAttachmentInfoById(@Param("fileId") Integer fileId);
	
	public UploadFileDTO getAttachmentInfoByTid(@Param("tId") Integer tId);
}
