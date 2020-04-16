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
	public Integer id;
	
	/**
	 * 模板id
	 */
	public Integer templateId;
	
	/**
	 * 指标编码
	 */
	public String code;
	
	/**
	 * 描述
	 */
	public String description;
	
	/**
	 * 编辑状态（0-未编辑，1-已编辑）
	 */
	public Integer editStatus;
	
	/**
	 * 是否可编辑（0-不可编辑，1-可编辑）
	 */
	public Integer editAble;
	
	/**
	 * 是否设置公式（0-否，1-是）
	 */
	public Integer formulaAble;
	
	/**
	 * 公式
	 */
	public String formula;
	
	/**
	 * 创建时间（时间戳）
	 */
	public Integer createTime;
	
	/**
	 * 修改时间（时间戳）
	 */
	public Integer updateTime;
	
	/**
	 * 创建人
	 */
	public String createBy;
	
	/**
	 * 修改人
	 */
	public String updateBy;

	public Integer getId() {
		return id;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
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

	public Integer getCreateTime() {
		return createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
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

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
}
