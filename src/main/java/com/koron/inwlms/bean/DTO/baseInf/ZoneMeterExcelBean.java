package com.koron.inwlms.bean.DTO.baseInf;

import com.koron.inwlms.util.ImportExcelUtil;

/**
 * @auother:zhongweibin
 * @date:2020-06-17
 * @description:
 */
public class ZoneMeterExcelBean {
    /**
     * 批次
     */
    private String BatchNo;

    /**
     * 分区编号
     */
    @ImportExcelUtil.ExcelColumn("分区编号")
    @ImportExcelUtil.RequiredColumn
    private String zoneNo;

    /**
     * 水表编号
     */
    @ImportExcelUtil.ExcelColumn("水表编号")
    @ImportExcelUtil.RequiredColumn
    private String meterNo;

    /**
     * 关系类型
     */
    @ImportExcelUtil.ExcelColumn("关系类型")
    @ImportExcelUtil.RequiredColumn
    private String type;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private String updateTime;

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getZoneNo() {
        return zoneNo;
    }

    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
