package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-07
 * @description:
 */
public class PointVO {

    /**
     *  监测点编号
     */
     private String pointID;

    /**
     * 监测点名称
     */
    private String name;

    /**
     * 监测点类别
     */
    private String type;

    /**
     * 用途
     */
    private String use;

    /**
     * 厂家
     */
    private String factory;

    /**
     * 主要口径（mm）
     */
    private Integer mainDiameter;

    /**
     * 地面标高（m）
     */
    private double elevation;

    /**
     * 行政区
     */
    private String district;

    /**
     * 所在道路
     */
    private String road;

    /**
     * 运行状态
     */
    private String status;

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

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public Integer getMainDiameter() {
        return mainDiameter;
    }

    public void setMainDiameter(Integer mainDiameter) {
        this.mainDiameter = mainDiameter;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
