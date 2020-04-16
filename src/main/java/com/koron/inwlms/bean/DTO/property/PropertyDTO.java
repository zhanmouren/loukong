package com.koron.inwlms.bean.DTO.property;


import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * @auother:zhong
 * @date:
 * @description:
 */
public class PropertyDTO extends BaseDTO {

    /**
     * 管段ID
     */
    private String P_CODE;

    /**
     * 管径
     */
    private int DIAMETER;

    /**
     * 管材
     */
    private String MATERIAL;

    /**
     * 埋设形式
     */
    private String BURIEDTYPE;

    /**
     * 长度（m）
     */
    private Double ASSETSLENGTH;

    /**
     * 管龄下限
     */
    private int ageMin;

    /**
     * 管龄上限
     */
    private int ageMax;

    /**
     * 一级分区
     */
    private String firstLevel;

    /**
     * 二级分区
     */
    private String secondLevel;

    /**
     * DMA编号
     */
    private String DMA;

    /**
     * 开始日期
     */
    private String BegD;

    /**
     * 结束日期
     */
    private String EndD;

    public String getTubing() {
        return tubing;
    }

    public void setTubing(String tubing) {
        this.tubing = tubing;
    }

    public int getCaliberMin() {
        return caliberMin;
    }

    public void setCaliberMin(int caliberMin) {
        this.caliberMin = caliberMin;
    }

    public int getCaliberMax() {
        return caliberMax;
    }

    public void setCaliberMax(int caliberMax) {
        this.caliberMax = caliberMax;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    public String getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel;
    }

    public String getDMA() {
        return DMA;
    }

    public void setDMA(String DMA) {
        this.DMA = DMA;
    }

    public String getBegD() {
        return BegD;
    }

    public void setBegD(String begD) {
        BegD = begD;
    }

    public String getEndD() {
        return EndD;
    }

    public void setEndD(String endD) {
        EndD = endD;
    }
}
