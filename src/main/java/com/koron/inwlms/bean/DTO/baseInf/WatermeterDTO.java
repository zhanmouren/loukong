package com.koron.inwlms.bean.DTO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-07
 * @description:
 */
public class WatermeterDTO {


    /**
     * 水表编号
     */
    private String tableNo;

    /**
     *水表类别
     */
    private String userType;

    /**
     *水表类别
     */
    private String tableType;

    /**
     *用户号
     */
    private String userNo;

    /**
     *品牌
     */
    private String brand;

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
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
}
