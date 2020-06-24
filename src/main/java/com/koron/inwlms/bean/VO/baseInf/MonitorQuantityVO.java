package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-06-24
 * @description:
 */
public class MonitorQuantityVO {


    /**
     * 行数
     */
    private Integer rows;

    /**
     * id
     */
    private Integer id;

    /**
     * 测站编号
     */
    private String moniCode;

    /**
     * 指标编码
     */
    private String code;

    /**
     * 月份
     */
    private Integer yearMonth;

    /**
     * 当月流量
     */
    private Integer value;

    /**
     * 上月流量
     */
    private Integer lastValue;

    /**
     * 本月水量
     */
    private Integer curValue;

    /**
     * 前三月平均水量
     */
    private Integer preAvg;

    /**
     * 本月调整后行码
     */
    private Integer adjustValue;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 校对时间
     */
    private String verifyTime;

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

    public String getMoniCode() {
        return moniCode;
    }

    public void setMoniCode(String moniCode) {
        this.moniCode = moniCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getLastValue() {
        return lastValue;
    }

    public void setLastValue(Integer lastValue) {
        this.lastValue = lastValue;
    }

    public Integer getCurValue() {
        return curValue;
    }

    public void setCurValue(Integer curValue) {
        this.curValue = curValue;
    }

    public Integer getPreAvg() {
        return preAvg;
    }

    public void setPreAvg(Integer preAvg) {
        this.preAvg = preAvg;
    }

    public Integer getAdjustValue() {
        return adjustValue;
    }

    public void setAdjustValue(Integer adjustValue) {
        this.adjustValue = adjustValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }
}
