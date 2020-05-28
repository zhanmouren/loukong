package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-04-29
 * @description:
 */
public class MonRepVO {


    /**
     * 月份
     */
   private String reportDate;

    /**
     * 时间
     */
    private String reportTime;

    /**
     * 分类
     */
    private String type;

    /**
     * 总行数
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
    private Double available;

    /**
     * 时效性
     */
    private Double timely;

    /**
     * 一致性
     */
    private Double consistence;

    /**
     * 总体评价
     */
    private Double rating;

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }

    public Double getTimely() {
        return timely;
    }

    public void setTimely(Double timely) {
        this.timely = timely;
    }

    public Double getConsistence() {
        return consistence;
    }

    public void setConsistence(Double consistence) {
        this.consistence = consistence;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
