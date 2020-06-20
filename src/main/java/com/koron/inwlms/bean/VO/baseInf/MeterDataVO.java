package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-12
 * @description:
 */
public class MeterDataVO {

    /**
     * 行数
     */
    private Integer rows;

    /**
     * 水表编号
     */
    private String meter_no;

    /**
     * 水表口径
     */
    private String pipe_d;

    /**
     * 用户编号
     */
    private String user_no;

    /**
     * 用户名称
     */
    private String user_name;

    /**
     * 用户类别
     */
    private String  user_type;

    /**
     * 上次读数
     */
    private Double prior_period_num;

    /**
     * 上期抄表日期
     */
    private String prior_period_date;

    /**
     * 抄表日期
     */
    private String meter_reading_date;

    /**
     * 抄表读数
     */
    private String currend_period_num;

    /**
     * 实际用水量
     */
    private Double reality_water;

    /**
     * 抄表方式
     */
    private String meter_reading_way;

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
     * 批次
     */
    private String BatchNo;

    public String getMeter_no() {
        return meter_no;
    }

    public void setMeter_no(String meter_no) {
        this.meter_no = meter_no;
    }

    public String getPipe_d() {
        return pipe_d;
    }

    public void setPipe_d(String pipe_d) {
        this.pipe_d = pipe_d;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public Double getPrior_period_num() {
        return prior_period_num;
    }

    public void setPrior_period_num(Double prior_period_num) {
        this.prior_period_num = prior_period_num;
    }

    public String getPrior_period_date() {
        return prior_period_date;
    }

    public void setPrior_period_date(String prior_period_date) {
        this.prior_period_date = prior_period_date;
    }

    public String getMeter_reading_date() {
        return meter_reading_date;
    }

    public void setMeter_reading_date(String meter_reading_date) {
        this.meter_reading_date = meter_reading_date;
    }

    public String getCurrend_period_num() {
        return currend_period_num;
    }

    public void setCurrend_period_num(String currend_period_num) {
        this.currend_period_num = currend_period_num;
    }

    public Double getReality_water() {
        return reality_water;
    }

    public void setReality_water(Double reality_water) {
        this.reality_water = reality_water;
    }

    public String getMeter_reading_way() {
        return meter_reading_way;
    }

    public void setMeter_reading_way(String meter_reading_way) {
        this.meter_reading_way = meter_reading_way;
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

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }
}
