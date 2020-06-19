package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-21
 * @description:
 */
public class ZoneVO {

    /**
     * 总行数
     */
    private Integer rows;

    /**
     * 分区编号
     */
    private String zoneNo;

    /**
     * 分区名称
     */
    private String zoneName;

    /**
     * 位置
     */
    private String position;

    /**
     * 一级分区
     */
    private String firstZoon;

    /**
     * 分区类型
     */
    private String type;

    /**
     * 分区级别
     */
    private String rank;

    /**
     * 父级分区编号
     */
    private String parentCode;

    /**
     * 父级分区名称
     */
    private String parentName;

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

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getFirstZoon() {
        return firstZoon;
    }

    public void setFirstZoon(String firstZoon) {
        this.firstZoon = firstZoon;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRow(Integer rows) {
        this.rows = rows;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
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
}
