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
     * 分区编号
     */
    private String zoneNo;

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
    private String zoneRank;

    /**
     * 用户
     */
    private String user;

    /**
     * 分区编号集
     * @return
     */
    private String[] zoons;

    /**
     * 分区等级
     * @return
     */
    private String rank;

    /**
     * 分区名称
     */
    private String name;

    /**
     * 父级分区
     */
    private String parent;

    /**
     * 类型
     */
    private String type;

    /**
     * 矢量坐标
     * @return
     */
    private Object geometry;

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getGeometry() {
        return geometry;
    }

    public void setGeometry(Object geometry) {
        this.geometry = geometry;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getZoneNo() {
        return zoneNo;
    }

    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    public String[] getZoons() {
        return zoons;
    }

    public void setZoons(String[] zoons) {
        this.zoons = zoons;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getZoneRank() {
        return zoneRank;
    }

    public void setZoneRank(String zoneRank) {
        this.zoneRank = zoneRank;
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
