package com.koron.inwlms.bean.DTO.apparentLoss;

/**
 * 全网水平衡报表附件DTO
 * @author csh
 * @Date 2020.03.09
 */
public class WNWBReportAttachmentDTO {

	public Integer reportId;
	
	public Integer fileId;
	
	public WNWBReportIndicatorDTO indicators;

	public Integer getReportId() {
		return reportId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public WNWBReportIndicatorDTO getIndicators() {
		return indicators;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public void setIndicators(WNWBReportIndicatorDTO indicators) {
		this.indicators = indicators;
	}
	
}
