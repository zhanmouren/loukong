package com.koron.inwlms.mapper.common;

import org.apache.ibatis.annotations.Param;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;

public interface FileMapper {

	public int insertFileData(UploadFileDTO uploadFileDTO);
	
	public String queryFilePath(@Param("fileId") String fileId);
	
	public int deleteFileById(@Param("fileId") String fileId);
	
	public UploadFileDTO getAttachmentInfoById(@Param("fileId") String fileId);
	
	public UploadFileDTO getAttachmentInfoByTid(@Param("tId") String tId);
}
