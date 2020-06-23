package com.koron.inwlms.bean.VO.baseInf;

import com.koron.inwlms.util.ImportExcelUtil;

/**
 * @auother:zhongweibin
 * @date:2020-05-12
 * @description:
 */
public class MonitorDataVO {

    /**
     * id
     */
    private Integer id;

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
    private Double noise;

    /**
     * 阈值（dB)
     */
    @ImportExcelUtil.ExcelColumn("阈值（dB）")
    @ImportExcelUtil.RequiredColumn
    private Double threshold;

    /**
     * 流量
     */
    @ImportExcelUtil.ExcelColumn("流量")
    @ImportExcelUtil.RequiredColumn
    private Double flow;

    /**
     * 正向流量
     */
    @ImportExcelUtil.ExcelColumn("正向流量")
    @ImportExcelUtil.RequiredColumn
    private Double forwardFlowRate;

    /**
     * 反向流量
     */
    @ImportExcelUtil.ExcelColumn("反向流量")
    @ImportExcelUtil.RequiredColumn
    private Double reverseFlowRate;

    /**
     * 瞬时流量
     */
    @ImportExcelUtil.ExcelColumn("瞬时流量")
    @ImportExcelUtil.RequiredColumn
    private Double flowRate;

    /**
     * 阀前压力
     */
    @ImportExcelUtil.ExcelColumn("阀前压力")
    @ImportExcelUtil.RequiredColumn
    private Double upstreamPressure;

    /**
     * 阀后压力
     */
    @ImportExcelUtil.ExcelColumn("阀后压力")
    @ImportExcelUtil.RequiredColumn
    private Double downstreamPressure;

    /**
     * 最不利点压力
     */
    @ImportExcelUtil.ExcelColumn("最不利点压力")
    @ImportExcelUtil.RequiredColumn
    private Double unfavorPressure;

    /**
     * 一级分区
     */
    private String firstZoon;

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

    /**
     * 监测用途
     * @return
     */
    private String useType;

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getFirstZoon() {
        return firstZoon;
    }

    public void setFirstZoon(String firstZoon) {
        this.firstZoon = firstZoon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Double getNoise() {
        return noise;
    }

    public void setNoise(Double noise) {
        this.noise = noise;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
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

    public Double getFlow() {
        return flow;
    }

    public void setFlow(Double flow) {
        this.flow = flow;
    }

    public Double getForwardFlowRate() {
        return forwardFlowRate;
    }

    public void setForwardFlowRate(Double forwardFlowRate) {
        this.forwardFlowRate = forwardFlowRate;
    }

    public Double getReverseFlowRate() {
        return reverseFlowRate;
    }

    public void setReverseFlowRate(Double reverseFlowRate) {
        this.reverseFlowRate = reverseFlowRate;
    }

    public Double getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(Double flowRate) {
        this.flowRate = flowRate;
    }

    public Double getUpstreamPressure() {
        return upstreamPressure;
    }

    public void setUpstreamPressure(Double upstreamPressure) {
        this.upstreamPressure = upstreamPressure;
    }

    public Double getDownstreamPressure() {
        return downstreamPressure;
    }

    public void setDownstreamPressure(Double downstreamPressure) {
        this.downstreamPressure = downstreamPressure;
    }

    public Double getUnfavorPressure() {
        return unfavorPressure;
    }

    public void setUnfavorPressure(Double unfavorPressure) {
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
