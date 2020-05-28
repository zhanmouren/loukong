package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-13
 * @description:
 */
public class MeterDataHisVO {
    /**
     * id
     */
    private Integer id;

    /**
     * 批号
     */
    private String BatchNo;

    /**
     * 原行数
     */
    private Integer originRow;

    /**
     * 导入行数
     */
    private Integer row;

    /**
     * 完整性
     */
    private Double integrity;

    /**
     * 准确性
     */
    private Double accuracy;

    /**
     * 有效性
     */
    private Double availability;

    /**
     * 时效性
     */
    private Double timely;

    /**
     * 一致性
     */
    private Double consistency;

    /**
     * 状态
     */
    private Integer status;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public Integer getOriginRow() {
        return originRow;
    }

    public void setOriginRow(Integer originRow) {
        this.originRow = originRow;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Double getIntegrity() {
        return integrity;
    }

    public void setIntegrity(Double integrity) {
        this.integrity = integrity;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(Double availability) {
        this.availability = availability;
    }

    public Double getTimely() {
        return timely;
    }

    public void setTimely(Double timely) {
        this.timely = timely;
    }

    public Double getConsistency() {
        return consistency;
    }

    public void setConsistency(Double consistency) {
        this.consistency = consistency;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
