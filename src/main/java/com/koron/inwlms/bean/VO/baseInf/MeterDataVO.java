package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-12
 * @description:
 */
public class MeterDataVO {

    /**
     * 抄表流水号
     */
    private String serialNo;

    /**
     * 水表编号
     */
    private String waterMeterNo;

    /**
     * 水表口径
     */
    private Double caliber;

    /**
     * 水表类别
     */
    private String type;

    /**
     * 开户时间
     */
    private String openTime;

    /**
     * 换表时间
     */
    private String changeTime;

    /**
     * 用户号
     */
    private String userNo;

    /**
     * 用户类别
     */
    private String userType;

    /**
     * 上次抄表时间
     */
    private String lastTime;

    /**
     * 上次读数
     */
    private Double lastReading;

    /**
     * 抄表时间
     */
    private String recordTime;

    /**
     * 抄表读数
     */
    private Double meterReading;

    /**
     * 用水量
     */
    private Double mwo;

    /**
     * 抄表方式
     */
    private String readType;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getWaterMeterNo() {
        return waterMeterNo;
    }

    public void setWaterMeterNo(String waterMeterNo) {
        this.waterMeterNo = waterMeterNo;
    }

    public Double getCaliber() {
        return caliber;
    }

    public void setCaliber(Double caliber) {
        this.caliber = caliber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public Double getLastReading() {
        return lastReading;
    }

    public void setLastReading(Double lastReading) {
        this.lastReading = lastReading;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Double getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(Double meterReading) {
        this.meterReading = meterReading;
    }

    public Double getMwo() {
        return mwo;
    }

    public void setMwo(Double mwo) {
        this.mwo = mwo;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }
}
