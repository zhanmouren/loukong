package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-05-27
 * @description:
 */
public class PipeLineVO {

    /**
     * smid
     */
    private Integer smid;

    /**
     * smtopoerror
     */
    private Integer smtopoerror;

    /**
     * smlength
     */
    private Double smlength;

    /**
     * 图形对象ID
     */
    private String featid;

    /**
     * 资产状态
     */
    private String asset_status;

    /**
     * 管段编号
     */
    private String p_code;

    /**
     * 管线等级
     */
    private String pipe_level;

    /**
     * 管径（mm）
     */
    private Integer diameter;

    /**
     * 材质
     */
    private String material;

    /**
     * 断面形式
     */
    private String pipe_style;

    /**
     * 粗糙系数C值
     */
    private Double c_value;

    /**
     * 粗糙系数N值
     */
    private Double n_value;

    /**
     * 埋设形式
     */
    private String buriedtype;

    /**
     * 连接方式
     */
    private String jointype;

    /**
     * 是否特殊管
     */
    private String is_special;

    /**
     * 特殊管类别
     */
    private String special_type;

    /**
     * 公称压力
     */
    private  Double nominalpressure;

    /**
     * 图上长度（m）
     */
    private Double length;

    /**
     * 长度（m）
     */
    private Double assetslength;

    /**
     * 起点编码
     */
    private String start_id;

    /**
     * 终点编码
     */
    private String end_id;

    /**
     * 起点地面标高（m）
     */
    private Double star_ele;

    /**
     * 终点地面标高（m）
     */
    private Double end_ele;

    /**
     * 起点埋深（m）
     */
    private Double star_bur;

    /**
     * 终点埋深（m）
     */
    private Double end_bur;

    /**
     * 管段埋深（m）
     */
    private Double cen_bur;

    /**
     * 起点管顶标高（m）
     */
    private Double star_top_h;

    /**
     * 终点管顶标高（m）
     */
    private Double end_top_h;

    /**
     * 起点管底标高（m）
     */
    private Double star_bottom_h;

    /**
     * 终点管底标高（m）
     */
    private Double end_bottom_h;

    /**
     * 起点中心标高（m）
     */
    private Double star_center_h;

    /**
     * 终点中心标高（m）
     */
    private Double end_center_h;

    /**
     * 行政区
     */
    private String district;

    /**
     * 供水所
     */
    private String magunit;

    /**
     * 所在道路
     */
    private String address;

    /**
     * 具体位置
     */
    private String location;

    /**
     * 位置类型
     */
    private String locationtype;

    /**
     * 管段使用状况
     */
    private String usestatus;

    /**
     * 内防腐类型
     */
    private String n_external;

    /**
     * 外防腐类型
     */
    private String w_external;

    /**
     * 路面种类
     */
    private String cov_type;

    /**
     * 生产厂商
     */
    private String manufacturer;

    /**
     * 维修部门
     */
    private String maint_division;

    /**
     * 管辖部门
     */
    private String admin_division;

    /**
     * 工程名称
     */
    private String projectname;

    /**
     * 报建编号
     */
    private String recid;

    /**
     * 工程编号
     */
    private String prjid;

    /**
     * 测量编号
     */
    private String arcid;

    /**
     * 归档号
     */
    private String archive_id;

    /**
     * 设计单位
     */
    private String planunit;

    /**
     * 建设单位
     */
    private String constructioncompany;

    /**
     * 施工单位
     */
    private String installcompany;

    /**
     * 权属单位
     */
    private String ownder;

    /**
     * 数据来源
     */
    private String datasource;

    /**
     * 竣工日期
     */
    private String completdate;

    /**
     * 备注
     */
    private String remark;

    public Integer getSmid() {
        return smid;
    }

    public void setSmid(Integer smid) {
        this.smid = smid;
    }

    public Integer getSmtopoerror() {
        return smtopoerror;
    }

    public void setSmtopoerror(Integer smtopoerror) {
        this.smtopoerror = smtopoerror;
    }

    public Double getSmlength() {
        return smlength;
    }

    public void setSmlength(Double smlength) {
        this.smlength = smlength;
    }

    public String getFeatid() {
        return featid;
    }

    public void setFeatid(String featid) {
        this.featid = featid;
    }

    public String getAsset_status() {
        return asset_status;
    }

    public void setAsset_status(String asset_status) {
        this.asset_status = asset_status;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getPipe_level() {
        return pipe_level;
    }

    public void setPipe_level(String pipe_level) {
        this.pipe_level = pipe_level;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPipe_style() {
        return pipe_style;
    }

    public void setPipe_style(String pipe_style) {
        this.pipe_style = pipe_style;
    }

    public Double getC_value() {
        return c_value;
    }

    public void setC_value(Double c_value) {
        this.c_value = c_value;
    }

    public Double getN_value() {
        return n_value;
    }

    public void setN_value(Double n_value) {
        this.n_value = n_value;
    }

    public String getBuriedtype() {
        return buriedtype;
    }

    public void setBuriedtype(String buriedtype) {
        this.buriedtype = buriedtype;
    }

    public String getJointype() {
        return jointype;
    }

    public void setJointype(String jointype) {
        this.jointype = jointype;
    }

    public String getIs_special() {
        return is_special;
    }

    public void setIs_special(String is_special) {
        this.is_special = is_special;
    }

    public String getSpecial_type() {
        return special_type;
    }

    public void setSpecial_type(String special_type) {
        this.special_type = special_type;
    }

    public Double getNominalpressure() {
        return nominalpressure;
    }

    public void setNominalpressure(Double nominalpressure) {
        this.nominalpressure = nominalpressure;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getAssetslength() {
        return assetslength;
    }

    public void setAssetslength(Double assetslength) {
        this.assetslength = assetslength;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public String getEnd_id() {
        return end_id;
    }

    public void setEnd_id(String end_id) {
        this.end_id = end_id;
    }

    public Double getStar_ele() {
        return star_ele;
    }

    public void setStar_ele(Double star_ele) {
        this.star_ele = star_ele;
    }

    public Double getEnd_ele() {
        return end_ele;
    }

    public void setEnd_ele(Double end_ele) {
        this.end_ele = end_ele;
    }

    public Double getStar_bur() {
        return star_bur;
    }

    public void setStar_bur(Double star_bur) {
        this.star_bur = star_bur;
    }

    public Double getEnd_bur() {
        return end_bur;
    }

    public void setEnd_bur(Double end_bur) {
        this.end_bur = end_bur;
    }

    public Double getCen_bur() {
        return cen_bur;
    }

    public void setCen_bur(Double cen_bur) {
        this.cen_bur = cen_bur;
    }

    public Double getStar_top_h() {
        return star_top_h;
    }

    public void setStar_top_h(Double star_top_h) {
        this.star_top_h = star_top_h;
    }

    public Double getEnd_top_h() {
        return end_top_h;
    }

    public void setEnd_top_h(Double end_top_h) {
        this.end_top_h = end_top_h;
    }

    public Double getStar_bottom_h() {
        return star_bottom_h;
    }

    public void setStar_bottom_h(Double star_bottom_h) {
        this.star_bottom_h = star_bottom_h;
    }

    public Double getEnd_bottom_h() {
        return end_bottom_h;
    }

    public void setEnd_bottom_h(Double end_bottom_h) {
        this.end_bottom_h = end_bottom_h;
    }

    public Double getStar_center_h() {
        return star_center_h;
    }

    public void setStar_center_h(Double star_center_h) {
        this.star_center_h = star_center_h;
    }

    public Double getEnd_center_h() {
        return end_center_h;
    }

    public void setEnd_center_h(Double end_center_h) {
        this.end_center_h = end_center_h;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationtype() {
        return locationtype;
    }

    public void setLocationtype(String locationtype) {
        this.locationtype = locationtype;
    }

    public String getUsestatus() {
        return usestatus;
    }

    public void setUsestatus(String usestatus) {
        this.usestatus = usestatus;
    }

    public String getN_external() {
        return n_external;
    }

    public void setN_external(String n_external) {
        this.n_external = n_external;
    }

    public String getW_external() {
        return w_external;
    }

    public void setW_external(String w_external) {
        this.w_external = w_external;
    }

    public String getCov_type() {
        return cov_type;
    }

    public void setCov_type(String cov_type) {
        this.cov_type = cov_type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMaint_division() {
        return maint_division;
    }

    public void setMaint_division(String maint_division) {
        this.maint_division = maint_division;
    }

    public String getAdmin_division() {
        return admin_division;
    }

    public void setAdmin_division(String admin_division) {
        this.admin_division = admin_division;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getPrjid() {
        return prjid;
    }

    public void setPrjid(String prjid) {
        this.prjid = prjid;
    }

    public String getArcid() {
        return arcid;
    }

    public void setArcid(String arcid) {
        this.arcid = arcid;
    }

    public String getArchive_id() {
        return archive_id;
    }

    public void setArchive_id(String archive_id) {
        this.archive_id = archive_id;
    }

    public String getPlanunit() {
        return planunit;
    }

    public void setPlanunit(String planunit) {
        this.planunit = planunit;
    }

    public String getConstructioncompany() {
        return constructioncompany;
    }

    public void setConstructioncompany(String constructioncompany) {
        this.constructioncompany = constructioncompany;
    }

    public String getInstallcompany() {
        return installcompany;
    }

    public void setInstallcompany(String installcompany) {
        this.installcompany = installcompany;
    }

    public String getOwnder() {
        return ownder;
    }

    public void setOwnder(String ownder) {
        this.ownder = ownder;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getCompletdate() {
        return completdate;
    }

    public void setCompletdate(String completdate) {
        this.completdate = completdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
