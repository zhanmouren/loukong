package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 全网水平衡报表文件列表VO
 * @author csh
 * @createTime 2020/04/08
 *
 */
public class WNWBReporFileListVO {

	private Integer id;
	
	private String fileName;
	
	private Double size;
	
	private String fileType;

	public Integer getId() {
		return id;
	}

	public Double getSize() {
		return size;
	}

	public String getFileType() {
		return fileType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
