package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-06-22
 * @description:
 */
public class PointAccountVO {

    /**
     * 关系类型
     */
    private String type;

    /**
     * 监测点编号
     */
    private String pointNo;

    /**
     * 数量
     */
    private Integer count;

    public String getPointNo() {
        return pointNo;
    }

    public void setPointNo(String pointNo) {
        this.pointNo = pointNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
