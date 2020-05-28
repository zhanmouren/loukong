package com.koron.inwlms.bean.DTO.baseInf;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * @auother:zhongweibin
 * @date:2020-05-08
 * @description:
 */
public class ZoneMeterDTO  extends BaseDTO {

    /**
     * 编号
     */
    private Integer refID;

    /**
     * 分区编号
     */
    private String zoneNo;

    /**
     * 户表编号
     */
    private String meterNo;

    /**
     * 关系类型
     */
    private String type;

    /**
     * 导入开始时间
     * @return
     */
    private String begD;

    /**
     * 导入结束时间
     * @return
     */
    private String endD;

    /**
     * 更新人
     * @return
     */
    private String updateBy;

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getRefID() {
        return refID;
    }

    public void setRefID(Integer refID) {
        this.refID = refID;
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

    public String getZoneNo() {
        return zoneNo;
    }

    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
