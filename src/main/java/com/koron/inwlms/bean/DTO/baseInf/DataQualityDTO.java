package com.koron.inwlms.bean.DTO.baseInf;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * @auother:zhongweibin
 * @date:2020-06-07
 * @description:
 */
public class DataQualityDTO extends BaseDTO {


    /**
     * id
     */
    private Integer[] refID;

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
    private Integer status;

    /**
     * 仅显示异常
     * @return
     */
    private String flag;

    /**
     * 本月行码
     */
    private Integer value;

    /**
     * 本月调整后行码
     */
    private Integer verifyValue;

    /**
     * 本月水量
     * @return
     */
    private Integer curValue;

    /**
     * 修改人
     * @return
     */
    private String updateBy;

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getVerifyValue() {
        return verifyValue;
    }

    public void setVerifyValue(Integer verifyValue) {
        this.verifyValue = verifyValue;
    }

    public Integer[] getRefID() {
        return refID;
    }

    public void setRefID(Integer[] refID) {
        this.refID = refID;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getCurValue() {
        return curValue;
    }

    public void setCurValue(Integer curValue) {
        this.curValue = curValue;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }
}
