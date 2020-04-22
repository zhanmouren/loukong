package com.koron.inwlms.bean.DTO.sysManager;

/**
 * @date 2020/04/22
 * @author 小詹
 *
 */
public class TitleInfoDTO {
	//标题名称
	private String titleName;
	//标题值
	private String titleValue;
	//导出时字段数值转换系数
	private String changeValue;
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getTitleValue() {
		return titleValue;
	}
	public void setTitleValue(String titleValue) {
		this.titleValue = titleValue;
	}
	public String getChangeValue() {
		return changeValue;
	}
	public void setChangeValue(String changeValue) {
		this.changeValue = changeValue;
	}
}
