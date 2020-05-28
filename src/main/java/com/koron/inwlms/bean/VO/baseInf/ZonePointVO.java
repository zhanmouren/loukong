package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-09
 * @description:
 */
public class ZonePointVO {

    /**
     * 行数
     */
    private Integer rows;

    /**
     * id
     */
    private Integer id;

    /**
     * 编号
     */
    private Integer refID;

    /**
     * 分区编号
     */
    private String zoneNo;

    /**
     * 分区名称
     */
    private String zoneName;

    /**
     * 监测点编号
     */
    private String pointNo;

    /**
     * 监测点名称
     */
    private String pointName;

    /**
     * 分区级别
     */
    private Integer level;

    /**
     * 关系类型
     */
    private String type;

    /**
     * 批次
     */
    private String BatchNo;

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

    /**
     *  分区级别
     */
    private String rank;

    /**
     *父级分区编号
     */
    private String parentNo;

    /**
     * 父级供水区编号
     */
    private String parentSupportNo;

    /**
     * 压力偏离量(m)
     */
    private Double pressureDeviation;

    /**
     * 时间因子 (hr/d)
     */
    private String timeFactor;

    /**
     * 区域服务水压
     */
    private Double waterPressure;

    /**
     * 当前状态
     */
    private String status;

    /**
     * 已废弃
     */
    private String discard;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    public String getParentSupportNo() {
        return parentSupportNo;
    }

    public void setParentSupportNo(String parentSupportNo) {
        this.parentSupportNo = parentSupportNo;
    }

    public Double getPressureDeviation() {
        return pressureDeviation;
    }

    public void setPressureDeviation(Double pressureDeviation) {
        this.pressureDeviation = pressureDeviation;
    }

    public String getTimeFactor() {
        return timeFactor;
    }

    public void setTimeFactor(String timeFactor) {
        this.timeFactor = timeFactor;
    }

    public Double getWaterPressure() {
        return waterPressure;
    }

    public void setWaterPressure(Double waterPressure) {
        this.waterPressure = waterPressure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiscard() {
        return discard;
    }

    public void setDiscard(String discard) {
        this.discard = discard;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRefID() {
        return refID;
    }

    public void setRefID(Integer refID) {
        this.refID = refID;
    }

    public String getZoneNo() {
        return zoneNo;
    }

    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getPointNo() {
        return pointNo;
    }

    public void setPointNo(String pointNo) {
        this.pointNo = pointNo;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
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
