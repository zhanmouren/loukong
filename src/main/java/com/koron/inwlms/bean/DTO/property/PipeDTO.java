package com.koron.inwlms.bean.DTO.property;

import com.koron.common.web.mapper.TreeBean;
import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * @auother:zhongweibin
 * @date:2020-04-10
 * @description:
 */
public class PipeDTO extends BaseDTO {

    /**
     * 管材
     */
    private String tubing;

    /**
     * 管径-下限
     */
    private Integer caliberMin;

    /**
     * 管径-上限
     */
    private Integer caliberMax;

    /**
     * 管龄下限
     */
    private Integer ageMin;

    /**
     * 管龄上限
     */
    private Integer ageMax;

    /**
     * 一级分区
     */
    private String firstLevel;

    /**
     *二级分区
     */
    private String secondLevel;

    /**
     * DMA编号
     */
    private String DMA;

    /**
     *开始日期
     */
    private String begD;

    /**
     *结束日期
     */
    private String endD;

    public String getTubing() {
        return tubing;
    }

    public void setTubing(String tubing) {
        this.tubing = tubing;
    }

    public Integer getCaliberMin() {
        return caliberMin;
    }

    public void setCaliberMin(Integer caliberMin) {
        this.caliberMin = caliberMin;
    }

    public Integer getCaliberMax() {
        return caliberMax;
    }

    public void setCaliberMax(Integer caliberMax) {
        this.caliberMax = caliberMax;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
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
