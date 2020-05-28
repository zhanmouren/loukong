package com.koron.inwlms.bean.DTO.baseInf;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * @auother:zhongweibin
 * @date:2020-04-10
 * @description:
 */
public class PointDTO extends BaseDTO {

    /**
     * 监测点类别
     */
    private int tubing;

    /**
     * 监测点名称
     */
    private String name;

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

    public int getTubing() {
        return tubing;
    }

    public void setTubing(int tubing) {
        this.tubing = tubing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
