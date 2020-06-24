package com.koron.inwlms.bean.DTO.baseInf;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * @auother:zhongweibin
 * @date:2020-06-07
 * @description:
 */
public class DataQualityDTO extends BaseDTO {

    /**
     * 月份
     */
    private String mon;

    /**
     * 监测点名称
     * @return
     */
    private String pointName;

    /**
     * 状态
     * @return
     */
    private String status;

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }
}
