package com.koron.inwlms.bean.DTO.sysManager;

import com.koron.inwlms.util.ImportExcelUtil;
/**
 * @Author: lzy
 * @CreateTime: 2020-04-21
 * @Description: Label excel导入列
 */
public class LabelExcelBean {
	@ImportExcelUtil.ExcelColumn("简体中文标签名")
    @ImportExcelUtil.RequiredColumn
    private String cn;
	
	@ImportExcelUtil.ExcelColumn("繁体中文标签名")
    @ImportExcelUtil.RequiredColumn
    private String cn_HK;
	
	@ImportExcelUtil.ExcelColumn("英文标签名")
    @ImportExcelUtil.RequiredColumn
    private String en;
	
	@ImportExcelUtil.ExcelColumn("编码")
    @ImportExcelUtil.RequiredColumn
    private String code;
	
	@ImportExcelUtil.ExcelColumn("标签类型")
    @ImportExcelUtil.RequiredColumn
    private String type;
	
	@ImportExcelUtil.ExcelColumn("备注")
    @ImportExcelUtil.RequiredColumn
    private String remark;
	

	private String createBy;
	private String updateBy;
	
	public String getCn() {
		return cn;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getCn_HK() {
		return cn_HK;
	}

	public void setCn_HK(String cn_HK) {
		this.cn_HK = cn_HK;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public void setCn(String cn) {
		this.cn = cn;
	}

	@Override
    public String toString() {
        return "ExcelBean{" +
                "cn='" + cn + '\'' +
                ", cn_HK='" + cn_HK + '\'' +
                ", en='" + en + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
