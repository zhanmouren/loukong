package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;

public class EventFileVO {
	
	private List<UploadFileDTO> fileList;
	
	private PageInfo query;

	public List<UploadFileDTO> getFileList() {
		return fileList;
	}

	public void setFileList(List<UploadFileDTO> fileList) {
		this.fileList = fileList;
	}

	public PageInfo getQuery() {
		return query;
	}

	public void setQuery(PageInfo query) {
		this.query = query;
	} 
	
	

}
