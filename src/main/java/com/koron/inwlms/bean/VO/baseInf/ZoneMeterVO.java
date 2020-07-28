package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-10
 * @description:
 */
public class ZoneMeterVO {

    /**
     * 行数
     */
    private Integer rows;

    /**
     * 关系编号
     */
    private String refID;

    /**
     * 分区编号
     */
    private String zoneNo;

    /**
     * 分区名称
     */
    private String zoneName;

    /**
     * 册本号
     */
    private String bookNo;

    /**
     * 水表编号
     */
    private String meterNo;

    /**
     * 用户号
     */
    private String userNo;

    /**
     * 类型
     */
    private String type;

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

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
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

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
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
}
