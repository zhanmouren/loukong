package com.koron.inwlms.bean.DTO.baseInf;

import com.koron.inwlms.util.ImportExcelUtil;

/**
 * @auother:zhongweibin
 * @date:2020-05-25
 * @description:
 */
public class MeterDataExcelBean {

    /**
     * 批次
     */
    private String BatchNo;

    /**
     * 抄表流水号
     */
    @ImportExcelUtil.ExcelColumn("抄表流水号")
    @ImportExcelUtil.RequiredColumn
    private String serialNo;

    /**
     * 水表编号
     */
    @ImportExcelUtil.ExcelColumn("水表编号")
    @ImportExcelUtil.RequiredColumn
    private String waterMeterNo;

    /**
     * 水表口径
     */
    @ImportExcelUtil.ExcelColumn("水表口径")
    @ImportExcelUtil.RequiredColumn
    private String caliber;

    /**
     * 水表类别
     */
    @ImportExcelUtil.ExcelColumn("水表类别")
    @ImportExcelUtil.RequiredColumn
    private String type;

    /**
     * 开户时间
     */
    @ImportExcelUtil.ExcelColumn("开户时间")
    @ImportExcelUtil.RequiredColumn
    private String openTime;

    /**
     * 换表时间
     */
    @ImportExcelUtil.ExcelColumn("换表时间")
    @ImportExcelUtil.RequiredColumn
    private String changeTime;

    /**
     * 用户号
     */
    @ImportExcelUtil.ExcelColumn("用户号")
    @ImportExcelUtil.RequiredColumn
    private String userNo;

    /**
     * 用户名称
     */
    @ImportExcelUtil.ExcelColumn("用户名称")
    @ImportExcelUtil.RequiredColumn
    private String userName;

    /**
     * 用户类别
     */
    @ImportExcelUtil.ExcelColumn("用户类别")
    @ImportExcelUtil.RequiredColumn
    private String userType;

    /**
     * 上次抄表时间
     */
    @ImportExcelUtil.ExcelColumn("上次抄表时间")
    @ImportExcelUtil.RequiredColumn
    private String lastTime;

    /**
     * 上次读数
     */
    @ImportExcelUtil.ExcelColumn("上次读数")
    @ImportExcelUtil.RequiredColumn
    private String lastReading;

    /**
     * 抄表时间
     */
    @ImportExcelUtil.ExcelColumn("抄表时间")
    @ImportExcelUtil.RequiredColumn
    private String recordTime;

    /**
     * 抄表读数
     */
    @ImportExcelUtil.ExcelColumn("抄表读数")
    @ImportExcelUtil.RequiredColumn
    private String meterReading;

    /**
     * 用水量
     */
    @ImportExcelUtil.ExcelColumn("用水量")
    @ImportExcelUtil.RequiredColumn
    private String mwo;

    /**
     * 抄表方式
     */
    @ImportExcelUtil.ExcelColumn("抄表方式")
    @ImportExcelUtil.RequiredColumn
    private String readType;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
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

    public String getLastReading() {
        return lastReading;
    }

    public void setLastReading(String lastReading) {
        this.lastReading = lastReading;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(String meterReading) {
        this.meterReading = meterReading;
    }

    public String getMwo() {
        return mwo;
    }

    public void setMwo(String mwo) {
        this.mwo = mwo;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }
}
