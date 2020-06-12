package com.koron.inwlms.bean.DTO.sysManager;

/**
 * @auother:zhongweibin
 * @date:2020-06-10
 * @description:
 */
public class DBInfoDTO {

    /**
     * 标识
     */
    private String key;

    /**
     * 租户
     */
    private String terant;

    /**
     * 数据库模板
     */
    private String dbtemplate;

    /**
     * 连接字符串
     */
    private String dbInfo;

    /**
     * 数据库类型
     */
    private String type;

    /**
     * 最大连接数
     */
    private Integer maxConnection;

    /**
     * 数据库URL
     */
    private String url;

    /**
     * 用户
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTerant() {
        return terant;
    }

    public void setTerant(String terant) {
        this.terant = terant;
    }

    public String getDbtemplate() {
        return dbtemplate;
    }

    public void setDbtemplate(String dbtemplate) {
        this.dbtemplate = dbtemplate;
    }

    public String getDbInfo() {
        return dbInfo;
    }

    public void setDbInfo(String dbInfo) {
        this.dbInfo = dbInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(Integer maxConnection) {
        this.maxConnection = maxConnection;
    }
}
