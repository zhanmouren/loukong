package com.koron.inwlms.bean.DTO.common;

import java.util.Date;

/**
 * 上传文件DTO
 * @author csh
 *
 */
public class UploadFileDTO {

	/**
	 * 主键id
	 */
	private String id;
	
	/**
	 * 关联外键id
	 */
	private Integer tId;
	
	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 文件路径
	 */
    private String filePath;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小，单位kb
     */
    private Double fileSize;

    /**
     * 存储名称（时间戳+文件类型）
     */
    private String storeName;
    
    /**
     * 模块类型
     */
    private String moduleType;
    
    /**
     * 状态（0-不存在或已删除，1-存在）
     */
    private String status;

    /**
     * 存储时间
     */
    private Date storageTime;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

	public String getId() {
		return id;
	}

	public Integer gettId() {
		return tId;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileType() {
		return fileType;
	}


	public String getStoreName() {
		return storeName;
	}

	public String getModuleType() {
		return moduleType;
	}

	public Date getStorageTime() {
		return storageTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public String getStatus() {
		return status;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
}
