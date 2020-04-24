package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 全网水平衡模板报表指标信息DTO
 * @author csh
 * @Date 2020.03.09
 */
public class WNWBTReportIndicatorDTO {

	/**
	 * 主键id
	 */
	private Integer id;
	
	/**
	 * 模板id
	 */
	private Integer templateId;
	
	/**
	 * 指标编码
	 */
	private String indicatorCode;
	
	/**
	 * 描述
	 */
	private String indicatorDescribe;
	
	/**
	 * 编辑状态（0-未编辑，1-已编辑）
	 */
	private Integer editStatus;
	
	/**
	 * 是否可编辑（0-不可编辑，1-可编辑）
	 */
	private Integer editAble;
	
	/**
	 * 是否设置公式（0-否，1-是）
	 */
	private Integer formulaAble;
	
	/**
	 * 公式
	 */
	private String formula;
	
	/**
	 * 创建人
	 */
	private String createBy;
	
	/**
	 * 修改人
	 */
	private String updateBy;

	public Integer getId() {
		return id;
	}

	public Integer getTemplateId() {
		return templateId;
	}


	public Integer getEditStatus() {
		return editStatus;
	}

	public Integer getEditAble() {
		return editAble;
	}

	public Integer getFormulaAble() {
		return formulaAble;
	}

	public String getFormula() {
		return formula;
	}


	public String getCreateBy() {
		return createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
	}

	public void setEditAble(Integer editAble) {
		this.editAble = editAble;
	}

	public void setFormulaAble(Integer formulaAble) {
		this.formulaAble = formulaAble;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getIndicatorCode() {
		return indicatorCode;
	}

	public String getIndicatorDescribe() {
		return indicatorDescribe;
	}

	public void setIndicatorCode(String indicatorCode) {
		this.indicatorCode = indicatorCode;
	}

	public void setIndicatorDescribe(String indicatorDescribe) {
		this.indicatorDescribe = indicatorDescribe;
	}
	
}
