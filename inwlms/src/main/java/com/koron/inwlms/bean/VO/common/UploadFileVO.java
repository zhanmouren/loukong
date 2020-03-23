package com.koron.inwlms.bean.VO.common;

/**
 * 上传文件成功返回实体VO
 * @author csh
 *
 */
public class UploadFileVO {

	/**
	 * 文件id
	 */
	private String fileId;
	
	/**
	 * 文件名称
	 */
	private String fileName;

	public String getFileId() {
		return fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
