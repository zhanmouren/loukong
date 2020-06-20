package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-06-03
 * @description:
 */
public class DataImpactVO {


    /**
     * 管线缺失行数
     */
    private Integer pipeLine;

    /**
     * 管径或管长数据项为空的行数
     */
    private Integer pipeDiameterLen;

    /**
     * 阀门的数据行数
     */
    private Integer valveLine;

    /**
     * 位置项为空的行数
     */
    private Integer valvePosLine;

    /**
     * 监测点的数据行数
     */
    private Integer pointLine;

    /**
     * 监测点的位置项为空的行数
     */
    private Integer pointPosLine;

    /**
     * 分区的数据行数
     */
    private Integer zoneLine;

    /**
     * 分区关系表的数据行数
     */
    private Integer zoneRelLine;

    public Integer getPipeLine() {
        return pipeLine;
    }

    public void setPipeLine(Integer pipeLine) {
        this.pipeLine = pipeLine;
    }

    public Integer getPipeDiameterLen() {
        return pipeDiameterLen;
    }

    public void setPipeDiameterLen(Integer pipeDiameterLen) {
        this.pipeDiameterLen = pipeDiameterLen;
    }

    public Integer getValveLine() {
        return valveLine;
    }

    public void setValveLine(Integer valveLine) {
        this.valveLine = valveLine;
    }

    public Integer getValvePosLine() {
        return valvePosLine;
    }

    public void setValvePosLine(Integer valvePosLine) {
        this.valvePosLine = valvePosLine;
    }

    public Integer getPointLine() {
        return pointLine;
    }

    public void setPointLine(Integer pointLine) {
        this.pointLine = pointLine;
    }

    public Integer getPointPosLine() {
        return pointPosLine;
    }

    public void setPointPosLine(Integer pointPosLine) {
        this.pointPosLine = pointPosLine;
    }

    public Integer getZoneLine() {
        return zoneLine;
    }

    public void setZoneLine(Integer zoneLine) {
        this.zoneLine = zoneLine;
    }

    public Integer getZoneRelLine() {
        return zoneRelLine;
    }

    public void setZoneRelLine(Integer zoneRelLine) {
        this.zoneRelLine = zoneRelLine;
    }
}
