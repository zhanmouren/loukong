package com.koron.inwlms.bean.DTO.zoneLoss;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**|
 * 查询全网水平衡模板报表列表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class QueryWNWBTReportListDTO extends BaseDTO{

	/**
	 * 模板报表名称
	 */
	public String templateName;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	

}
