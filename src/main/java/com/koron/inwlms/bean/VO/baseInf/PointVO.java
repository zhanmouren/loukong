package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-07
 * @description:
 */
public class PointVO {

    /**
     *  地址
     */
     private String address;

    /**
     * 管辖部门
     */
    private String admin_division;

    /**
     * 符号角度
     */
    private Integer angle;

    /**
     * 归档号
     */
    private String archive_id;

    /**
     * 资产状态
     */
    private String asset_status;

    /**
     * 审核时间
     */
    private Integer checkdate;

    /**
     * 用途类型
     */
     private String type;

    private Double[] smgeometry;

    private String featid;

    private Integer smid;

    private Integer smuserid;

    private String objtype;

    private String p_code;

    private String name;

    private String w_category;

    private String is_remote;

    private String district;

    private String magunit;

    private String planunit;

    private String is_check;

    private Double x;

    private Double y;

    private String completdate;

    private String inputdate;

    private String subtype;

    private String geoType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double[] getSmgeometry() {
        return smgeometry;
    }

    public void setSmgeometry(Double[] smgeometry) {
        this.smgeometry = smgeometry;
    }

    public String getFeatid() {
        return featid;
    }

    public void setFeatid(String featid) {
        this.featid = featid;
    }

    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }

    public Integer getSmuserid() {
        return smuserid;
    }

    public void setSmuserid(Integer smuserid) {
        this.smuserid = smuserid;
    }

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getW_category() {
        return w_category;
    }

    public void setW_category(String w_category) {
        this.w_category = w_category;
    }

    public String getIs_remote() {
        return is_remote;
    }

    public void setIs_remote(String is_remote) {
        this.is_remote = is_remote;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMagunit() {
        return magunit;
    }

    public void setMagunit(String magunit) {
        this.magunit = magunit;
    }

    public String getPlanunit() {
        return planunit;
    }

    public void setPlanunit(String planunit) {
        this.planunit = planunit;
    }

    public String getIs_check() {
        return is_check;
    }

    public void setIs_check(String is_check) {
        this.is_check = is_check;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getCompletdate() {
        return completdate;
    }

    public void setCompletdate(String completdate) {
        this.completdate = completdate;
    }

    public String getInputdate() {
        return inputdate;
    }

    public void setInputdate(String inputdate) {
        this.inputdate = inputdate;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getGeoType() {
        return geoType;
    }

    public void setGeoType(String geoType) {
        this.geoType = geoType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdmin_division() {
        return admin_division;
    }

    public void setAdmin_division(String admin_division) {
        this.admin_division = admin_division;
    }

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }

    public String getArchive_id() {
        return archive_id;
    }

    public void setArchive_id(String archive_id) {
        this.archive_id = archive_id;
    }

    public String getAsset_status() {
        return asset_status;
    }

    public void setAsset_status(String asset_status) {
        this.asset_status = asset_status;
    }

    public Integer getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Integer checkdate) {
        this.checkdate = checkdate;
    }
}
