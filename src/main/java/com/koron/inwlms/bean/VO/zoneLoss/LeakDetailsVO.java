package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 分区漏点信息VO
 * @author csh
 *
 */
public class LeakDetailsVO {

	//id
	private Integer id;
	//任务编号
	private String worderid;
	//检漏负责人	
	private String patrolmanid;
	//漏损现象
	private String isligorsh;
	//管段FEATID
	private String featid;
	//管径（mm）
	private Double diameter ;
	//管材
	private String material ;
	//漏水点位置
	private String location;
	//漏点编号
	private String leakid ;
	//发现方式
	private String foundtype ;
	//压力(MPa)
	private Double pressure ;
	//管段埋深（m）
	private Double depth ;
	//漏水时间（h）
	private Double leaktime ;
	//漏点类型
	private String leakcond;
	//上报时间（探漏时间）
	private String repdate;
	//巡查负责人
	private String reported;
	//开始维修时间
	private String exdate;
	//完成时间
	private String repaiedate ;
	//维修负责人
	private String mainperson;
	//坐标
	private String coordinate;
	//备注
	private String remark;
	//管段编号
	private String pcode;
	//负责人
	private String assigner;
	//处理结果
	private String dealresult;
	//漏点形状
	private String leakshape;
	//漏点尺寸（cm）
	private Double leakdia ;
	//漏水流量（m3/h）
	private Double leakflow ;
	//漏失水量（m3）
	private String waterleak ;
	//管龄
	private Double yearpipe ;
	//管径纠正（mm）
	private String diametercorrect;
	//管材纠正
	private String materialcorrect;
	//管段埋深纠正（m）
	private String depthcorrect ;
	//紧急程度
	private String emergencylevel ;
	//停水时间
	private String cutofftime ;
	//漏点所在区域
	private String leakregion ;
	//漏点编号(手填)
	private String leakid2;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWorderid() {
		return worderid;
	}
	public void setWorderid(String worderid) {
		this.worderid = worderid;
	}
	public String getPatrolmanid() {
		return patrolmanid;
	}
	public void setPatrolmanid(String patrolmanid) {
		this.patrolmanid = patrolmanid;
	}
	public String getIsligorsh() {
		return isligorsh;
	}
	public void setIsligorsh(String isligorsh) {
		this.isligorsh = isligorsh;
	}
	public String getFeatid() {
		return featid;
	}
	public void setFeatid(String featid) {
		this.featid = featid;
	}
	public Double getDiameter() {
		return diameter;
	}
	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLeakid() {
		return leakid;
	}
	public void setLeakid(String leakid) {
		this.leakid = leakid;
	}
	public String getFoundtype() {
		return foundtype;
	}
	public void setFoundtype(String foundtype) {
		this.foundtype = foundtype;
	}
	public Double getPressure() {
		return pressure;
	}
	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}
	public Double getDepth() {
		return depth;
	}
	public void setDepth(Double depth) {
		this.depth = depth;
	}
	public Double getLeaktime() {
		return leaktime;
	}
	public void setLeaktime(Double leaktime) {
		this.leaktime = leaktime;
	}
	public String getLeakcond() {
		return leakcond;
	}
	public void setLeakcond(String leakcond) {
		this.leakcond = leakcond;
	}
	public String getRepdate() {
		return repdate;
	}
	public void setRepdate(String repdate) {
		this.repdate = repdate;
	}
	public String getReported() {
		return reported;
	}
	public void setReported(String reported) {
		this.reported = reported;
	}
	public String getExdate() {
		return exdate;
	}
	public void setExdate(String exdate) {
		this.exdate = exdate;
	}
	public String getRepaiedate() {
		return repaiedate;
	}
	public void setRepaiedate(String repaiedate) {
		this.repaiedate = repaiedate;
	}
	public String getMainperson() {
		return mainperson;
	}
	public void setMainperson(String mainperson) {
		this.mainperson = mainperson;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getAssigner() {
		return assigner;
	}
	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}
	public String getDealresult() {
		return dealresult;
	}
	public void setDealresult(String dealresult) {
		this.dealresult = dealresult;
	}
	public String getLeakshape() {
		return leakshape;
	}
	public void setLeakshape(String leakshape) {
		this.leakshape = leakshape;
	}
	public Double getLeakdia() {
		return leakdia;
	}
	public void setLeakdia(Double leakdia) {
		this.leakdia = leakdia;
	}
	public Double getLeakflow() {
		return leakflow;
	}
	public void setLeakflow(Double leakflow) {
		this.leakflow = leakflow;
	}
	public String getWaterleak() {
		return waterleak;
	}
	public void setWaterleak(String waterleak) {
		this.waterleak = waterleak;
	}
	public Double getYearpipe() {
		return yearpipe;
	}
	public void setYearpipe(Double yearpipe) {
		this.yearpipe = yearpipe;
	}
	public String getDiametercorrect() {
		return diametercorrect;
	}
	public void setDiametercorrect(String diametercorrect) {
		this.diametercorrect = diametercorrect;
	}
	public String getMaterialcorrect() {
		return materialcorrect;
	}
	public void setMaterialcorrect(String materialcorrect) {
		this.materialcorrect = materialcorrect;
	}
	public String getDepthcorrect() {
		return depthcorrect;
	}
	public void setDepthcorrect(String depthcorrect) {
		this.depthcorrect = depthcorrect;
	}
	public String getEmergencylevel() {
		return emergencylevel;
	}
	public void setEmergencylevel(String emergencylevel) {
		this.emergencylevel = emergencylevel;
	}
	public String getCutofftime() {
		return cutofftime;
	}
	public void setCutofftime(String cutofftime) {
		this.cutofftime = cutofftime;
	}
	public String getLeakregion() {
		return leakregion;
	}
	public void setLeakregion(String leakregion) {
		this.leakregion = leakregion;
	}
	public String getLeakid2() {
		return leakid2;
	}
	public void setLeakid2(String leakid2) {
		this.leakid2 = leakid2;
	}
	
}
