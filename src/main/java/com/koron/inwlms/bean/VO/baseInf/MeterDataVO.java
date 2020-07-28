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
     * id
     */
    private Integer id;

    /**
     * 水表编号
     */
    private String meterNo;

    /**
     * 册本号
     */
    private String bookNo;

    /**
     * 水表口径
     */
    private String pipeD;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户类别
     */
    private String  userType;

    /**
     * 上次读数
     */
    private Double priorPeriodNum;

    /**
     * 上期抄表日期
     */
    private String priorPeriodDate;

    /**
     * 抄表日期
     */
    private String meterReadingDate;

    /**
     * 抄表读数
     */
    private String currendPeriodNum;

    /**
     * 实际用水量
     */
    private Double realityWater;

    /**
     * 抄表方式
     */
    private String meterReadingWay;

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

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getPipeD() {
        return pipeD;
    }

    public void setPipeD(String pipeD) {
        this.pipeD = pipeD;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Double getPriorPeriodNum() {
        return priorPeriodNum;
    }

    public void setPriorPeriodNum(Double priorPeriodNum) {
        this.priorPeriodNum = priorPeriodNum;
    }

    public String getPriorPeriodDate() {
        return priorPeriodDate;
    }

    public void setPriorPeriodDate(String priorPeriodDate) {
        this.priorPeriodDate = priorPeriodDate;
    }

    public String getMeterReadingDate() {
        return meterReadingDate;
    }

    public void setMeterReadingDate(String meterReadingDate) {
        this.meterReadingDate = meterReadingDate;
    }

    public String getCurrendPeriodNum() {
        return currendPeriodNum;
    }

    public void setCurrendPeriodNum(String currendPeriodNum) {
        this.currendPeriodNum = currendPeriodNum;
    }

    public Double getRealityWater() {
        return realityWater;
    }

    public void setRealityWater(Double realityWater) {
        this.realityWater = realityWater;
    }

    public String getMeterReadingWay() {
        return meterReadingWay;
    }

    public void setMeterReadingWay(String meterReadingWay) {
        this.meterReadingWay = meterReadingWay;
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
