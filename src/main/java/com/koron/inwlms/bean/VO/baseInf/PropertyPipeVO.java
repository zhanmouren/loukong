package com.koron.inwlms.bean.VO.baseInf;

import com.koron.inwlms.bean.VO.common.BaseQueryBean;

/**
 * 管网资产-管线
 */
public class PropertyPipeVO {

    /**
     *  管段ID
     */
    private String pipeID;

    /**
     *  类型
     */
    private String type;

    /**
     *  对象类别
     */
    private String objType;

    /**
     *  管径
     */
    private int caliber;

    /**
     *  管材
     */
    private String tubing;

    /**
     *  埋设形式
     */
    private String form;

    /**
     *  长度（m）
     */
    private Double length;

    /**
     *  起点地面标高
     */
    private Double startHeight;

    /**
     *  终点地面标高
     */
    private Double endHeight;

    /**
     *  管段埋设深（m）
     */
    private Double depth;

    /**
     *  行政区
     */
    private String district;

    /**
     *  所在道路
     */
    private String road;

    /**
     *  竣工日期
     */
    private String completeDate;

    public String getPipeID() {
        return pipeID;
    }

    public void setPipeID(String pipeID) {
        this.pipeID = pipeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public int getCaliber() {
        return caliber;
    }

    public void setCaliber(int caliber) {
        this.caliber = caliber;
    }

    public String getTubing() {
        return tubing;
    }

    public void setTubing(String tubing) {
        this.tubing = tubing;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getStartHeight() {
        return startHeight;
    }

    public void setStartHeight(Double startHeight) {
        this.startHeight = startHeight;
    }

    public Double getEndHeight() {
        return endHeight;
    }

    public void setEndHeight(Double endHeight) {
        this.endHeight = endHeight;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
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

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }
}
