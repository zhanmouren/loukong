package com.koron.inwlms.bean.DTO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-12
 * @description:
 */
public class MeterDataDTO {

    private String[]  dma;

    /**
     * 批次
     */
    private String BatchNo;

    /**
     * 抄表方式
     */
    private String readType;

    /**
     * 抄表流水号
     */
    private String serialNo;

    /**
     * 用户类别
     */
    private String usrType;

    /**
     * 上次抄表时间
     */
    private String lastTime;

    /**
     *上次读数
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
     * 水表编号
     */
    private String waterMeterNo;

    /**
     * 用户号
     */
    private String userNo;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 抄表时间（开始）
     */
    private String begD;

    /**
     * 抄表时间（结束）
     */
    private String endD;

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getUsrType() {
        return usrType;
    }

    public void setUsrType(String usrType) {
        this.usrType = usrType;
    }

    public String getWaterMeterNo() {
        return waterMeterNo;
    }

    public void setWaterMeterNo(String waterMeterNo) {
        this.waterMeterNo = waterMeterNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBegD() {
        return begD;
    }

    public void setBegD(String begD) {
        this.begD = begD;
    }

    public String getEndD() {
        return endD;
    }

    public void setEndD(String endD) {
        this.endD = endD;
    }
}
