package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-06-20
 * @description:
 */
public class ZoneUserVO {

    /**
     * 行数
     */
    private Integer rows;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户编码
     */
    private String user;

    /**
     * 分区编号
     */
    private String zoonNo;

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getZoonNo() {
        return zoonNo;
    }

    public void setZoonNo(String zoonNo) {
        this.zoonNo = zoonNo;
    }
}
