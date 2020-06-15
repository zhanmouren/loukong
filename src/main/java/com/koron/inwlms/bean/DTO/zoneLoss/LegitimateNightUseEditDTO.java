package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 合理夜晚使用量更新DTO
 * @Author: csh
 * @CreateTime: 2020/04/16
 */
public class LegitimateNightUseEditDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 水表编码
     */
    private String code;

    /**
     * 夜间流量值
     */
    private Double flow;
    
    private String name;

    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getFlow() {
		return flow;
	}

	public void setFlow(Double flow) {
		this.flow = flow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


    
}
