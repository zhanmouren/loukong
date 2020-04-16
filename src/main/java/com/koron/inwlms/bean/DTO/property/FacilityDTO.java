package com.koron.inwlms.bean.DTO.property;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * @auother:zhongweibin
 * @date:2020-04-10
 * @description:
 */
public class FacilityDTO extends BaseDTO {

    /**
     * 附属设施类型
     */
    private int type;

    /**
     * 运行状态
     */
    private String status;

    /**
     * 一级分区
     */
    private String first;

    /**
     * 二级分区
     */
    private String sec;

    /**
     * DMA编号
     */
    private String DMA;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getDMA() {
        return DMA;
    }

    public void setDMA(String DMA) {
        this.DMA = DMA;
    }
}
