package com.koron.inwlms.bean.DTO.leakageControl;

/**
 * 
 * @author 刘刚
 *
 */
public class AlarmRuleDTO {

	private String schemeCode;
	
	private Integer id;
	
	private Double constant;
	
	private String target;
	
	private String arithmeticRule;
	
	private Integer limitType;
	
	
 
	

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getConstant() {
		return constant;
	}

	public void setConstant(Double constant) {
		this.constant = constant;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getArithmeticRule() {
		return arithmeticRule;
	}

	public void setArithmeticRule(String arithmeticRule) {
		this.arithmeticRule = arithmeticRule;
	}

	public Integer getLimitType() {
		return limitType;
	}

	public void setLimitType(Integer limitType) {
		this.limitType = limitType;
	}
	
	
}
