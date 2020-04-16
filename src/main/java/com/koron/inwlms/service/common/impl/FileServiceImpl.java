package com.koron.inwlms.service.common.impl;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.mapper.common.FileMapper;
import com.koron.inwlms.service.common.FileService;


@Service
public class FileServiceImpl implements FileService {

	@TaskAnnotation("insertFileData")
	public int insertFileData(SessionFactory factory,UploadFileDTO uploadFileDTO) {
		FileMapper mapper = factory.getMapper(FileMapper.class);
		int result = mapper.insertFileData(uploadFileDTO);
		return result;
	}

	@TaskAnnotation("queryFilePath")
	public String queryFilePath(SessionFactory factory, String fileId) {
		FileMapper mapper = factory.getMapper(FileMapper.class);
		String path = mapper.queryFilePath(fileId);
		return path;
	}

	@TaskAnnotation("deleteFileById")
	public int deleteFileById(SessionFactory factory, String fileId) {
		FileMapper mapper = factory.getMapper(FileMapper.class);
		int result = mapper.deleteFileById(fileId);
		return result;
	}
	@TaskAnnotation("getAttachmentInfoById")
	@Override
	public UploadFileDTO getAttachmentInfoById(SessionFactory factory, String fileId) {
		FileMapper mapper = factory.getMapper(FileMapper.class);
		return mapper.getAttachmentInfoById(fileId);
	}
	@TaskAnnotation("getAttachmentInfoByTid")
	@Override
	public UploadFileDTO getAttachmentInfoByTid(SessionFactory factory, String tid) {
		FileMapper mapper = factory.getMapper(FileMapper.class);
		return mapper.getAttachmentInfoByTid(tid);
	}

}
