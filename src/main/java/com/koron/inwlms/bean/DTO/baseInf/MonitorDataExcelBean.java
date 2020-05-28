package com.koron.inwlms.bean.DTO.baseInf;

import com.koron.inwlms.util.ImportExcelUtil;

/**
 * @auother:zhongweibin
 * @date:2020-05-12
 * @description:
 */
public class MonitorDataExcelBean {

    /**
     * 行数
     */
    private Integer rows;

    /**
     * 报警状态
     */
    @ImportExcelUtil.ExcelColumn("报警状态")
    @ImportExcelUtil.RequiredColumn
    private String status;

    /**
     * 监测点ID
     */
    @ImportExcelUtil.ExcelColumn("监测点ID")
    @ImportExcelUtil.RequiredColumn
    private String pointID;

    /**
     * 监测点名称
     */
    @ImportExcelUtil.ExcelColumn("监测点名称")
    @ImportExcelUtil.RequiredColumn
    private String name;

    /**
     * 监测数据类型
     */
    @ImportExcelUtil.ExcelColumn("监测点类型")
    @ImportExcelUtil.RequiredColumn
    private String type;

    /**
     * 最近记录数据时间
     */
    @ImportExcelUtil.ExcelColumn("最近记录时间")
    @ImportExcelUtil.RequiredColumn
    private String lastTime;

    /**
     * 噪声（dB)
     */
    @ImportExcelUtil.ExcelColumn("噪声（dB）")
    @ImportExcelUtil.RequiredColumn
    private String noise;

    /**
     * 阈值（dB)
     */
    @ImportExcelUtil.ExcelColumn("阈值（dB）")
    @ImportExcelUtil.RequiredColumn
    private String threshold;

    /**
     * 流量
     */
    @ImportExcelUtil.ExcelColumn("流量")
    @ImportExcelUtil.RequiredColumn
    private String flow;

    /**
     * 正向流量
     */
    @ImportExcelUtil.ExcelColumn("正向流量")
    @ImportExcelUtil.RequiredColumn
    private String forwardFlowRate;

    /**
     * 反向流量
     */
    @ImportExcelUtil.ExcelColumn("反向流量")
    @ImportExcelUtil.RequiredColumn
    private String reverseFlowRate;

    /**
     * 瞬时流量
     */
    @ImportExcelUtil.ExcelColumn("瞬时流量")
    @ImportExcelUtil.RequiredColumn
    private String flowRate;

    /**
     * 阀前压力
     */
    @ImportExcelUtil.ExcelColumn("阀前压力")
    @ImportExcelUtil.RequiredColumn
    private String upstreamPressure;

    /**
     * 阀后压力
     */
    @ImportExcelUtil.ExcelColumn("阀后压力")
    @ImportExcelUtil.RequiredColumn
    private String downstreamPressure;

    /**
     * 最不利点压力
     */
    @ImportExcelUtil.ExcelColumn("最不利点压力")
    @ImportExcelUtil.RequiredColumn
    private String unfavorPressure;

    /**
     * 批次
     */
    private String BatchNo;

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

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPointID() {
        return pointID;
    }

    public void setPointID(String pointID) {
        this.pointID = pointID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getForwardFlowRate() {
        return forwardFlowRate;
    }

    public void setForwardFlowRate(String forwardFlowRate) {
        this.forwardFlowRate = forwardFlowRate;
    }

    public String getReverseFlowRate() {
        return reverseFlowRate;
    }

    public void setReverseFlowRate(String reverseFlowRate) {
        this.reverseFlowRate = reverseFlowRate;
    }

    public String getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(String flowRate) {
        this.flowRate = flowRate;
    }

    public String getUpstreamPressure() {
        return upstreamPressure;
    }

    public void setUpstreamPressure(String upstreamPressure) {
        this.upstreamPressure = upstreamPressure;
    }

    public String getDownstreamPressure() {
        return downstreamPressure;
    }

    public void setDownstreamPressure(String downstreamPressure) {
        this.downstreamPressure = downstreamPressure;
    }

    public String getUnfavorPressure() {
        return unfavorPressure;
    }

    public void setUnfavorPressure(String unfavorPressure) {
        this.unfavorPressure = unfavorPressure;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
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
}
