package com.koron.inwlms.bean.DTO.baseInf;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * @auother:zhongweibin
 * @date:2020-05-11
 * @description:
 */
public class MonitorDataDTO extends BaseDTO {

    /**
     * 批次
     */
    private String batchNo;

    /**
     * 监测数据类型
     */
    private String type;

    /**
     * 报警状态
     */
    private String status;

    /**
     * DMA类型
     */
    private String dmaType;

    /**
     * 监测用途
     */
    private String use;

    /**
     * 监测点ID
     */
    private String pointID;

    /**
     * 一级分区
     */
    private String first;

    /**
     *  二级分区
     */
    private String second;

    /**
     *  DMA分区
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDmaType() {
        return dmaType;
    }

    public void setDmaType(String dmaType) {
        this.dmaType = dmaType;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getPointID() {
        return pointID;
    }

    public void setPointID(String pointID) {
        this.pointID = pointID;
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
}
