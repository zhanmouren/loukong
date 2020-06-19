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
    private Integer pipe_line;

    /**
     * 管径或管长数据项为空的行数
     */
    private Integer pipe_diameter_len;

    /**
     * 阀门的数据行数
     */
    private Integer valve_line;

    /**
     * 位置项为空的行数
     */
    private Integer valve_pos_line;

    /**
     * 监测点的数据行数
     */
    private Integer point_line;

    /**
     * 监测点的位置项为空的行数
     */
    private Integer point_pos_line;

    /**
     * 分区的数据行数
     */
    private Integer zone_line;

    /**
     * 分区关系表的数据行数
     */
    private Integer zone_rel_line;

    public Integer getPipe_line() {
        return pipe_line;
    }

    public void setPipe_line(Integer pipe_line) {
        this.pipe_line = pipe_line;
    }

    public Integer getPipe_diameter_len() {
        return pipe_diameter_len;
    }

    public void setPipe_diameter_len(Integer pipe_diameter_len) {
        this.pipe_diameter_len = pipe_diameter_len;
    }

    public Integer getValve_line() {
        return valve_line;
    }

    public void setValve_line(Integer valve_line) {
        this.valve_line = valve_line;
    }

    public Integer getValve_pos_line() {
        return valve_pos_line;
    }

    public void setValve_pos_line(Integer valve_pos_line) {
        this.valve_pos_line = valve_pos_line;
    }

    public Integer getPoint_line() {
        return point_line;
    }

    public void setPoint_line(Integer point_line) {
        this.point_line = point_line;
    }

    public Integer getPoint_pos_line() {
        return point_pos_line;
    }

    public void setPoint_pos_line(Integer point_pos_line) {
        this.point_pos_line = point_pos_line;
    }

    public Integer getZone_line() {
        return zone_line;
    }

    public void setZone_line(Integer zone_line) {
        this.zone_line = zone_line;
    }

    public Integer getZone_rel_line() {
        return zone_rel_line;
    }

    public void setZone_rel_line(Integer zone_rel_line) {
        this.zone_rel_line = zone_rel_line;
    }
}
