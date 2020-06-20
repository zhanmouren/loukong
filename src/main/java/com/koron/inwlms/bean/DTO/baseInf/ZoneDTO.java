package com.koron.inwlms.bean.DTO.baseInf;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * @auother:zhongweibin
 * @date:2020-05-21
 * @description:
 */
public class ZoneDTO extends BaseDTO {


    /**
     * 一级分区编号
     */
    private String first;

    /**
     * 二级分区
     */
    private String second;

    /**
     * DMA
     */
    private String DMA;

    /**
     * 开始时间
     */
    private String begD;

    /**
     * 结束时间
     */
    private String endD;

    /**
     * 分区级别
     */
    private String rank;

    /**
     * 用户
     */
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getDMA() {
        return DMA;
    }

    public void setDMA(String DMA) {
        this.DMA = DMA;
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
