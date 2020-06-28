package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 分区漏点信息VO
 * @author csh
 *
 */
public class LeakDetailsVO {

	//id
	private Integer ID;
	//任务编号
	private String WORDERID;
	//检漏负责人	
	private String PATROLMANID;
	//漏损现象
	private String ISLIGORSH;
	//管段FEATID
	private String FEATID;
	//管径（mm）
	private Double DIAMETER ;
	//管材
	private String MATERIAL ;
	//漏水点位置
	private String LOCATION;
	//漏点编号
	private String LEAKID ;
	//发现方式
	private String FOUNDTYPE ;
	//压力(MPa)
	private Double PRESSURE ;
	//管段埋深（m）
	private Double DEPTH ;
	//漏水时间（h）
	private Double LEAKTIME ;
	//漏点类型
	private String LEAKCOND;
	//上报时间（探漏时间）
	private String REPDATE;
	//巡查负责人
	private String REPORTED;
	//开始维修时间
	private String EXDATE;
	//完成时间
	private String REPAIEDATE ;
	//维修负责人
	private String MAINPERSON;
	//坐标
	private String COORDINATE;
	//备注
	private String REMARK;
	//管段编号
	private String PCODE;
	//负责人
	private String ASSIGNER;
	//处理结果
	private String DEALRESULT;
	//漏点形状
	private String LEAKSHAPE;
	//漏点尺寸（cm）
	private Double LEAKDIA ;
	//漏水流量（m3/h）
	private Double LEAKFLOW ;
	//漏失水量（m3）
	private String WATERLEAK ;
	//管龄
	private Double YEARPIPE ;
	//管径纠正（mm）
	private String DIAMETERCORRECT;
	//管材纠正
	private String MATERIALCORRECT;
	//管段埋深纠正（m）
	private String DEPTHCORRECT ;
	//紧急程度
	private String EMERGENCYLEVEL ;
	//停水时间
	private String CUTOFFTIME ;
	//漏点所在区域
	private String LEAKREGION ;
	//漏点编号(手填)
	private String LEAKID2;
	public Integer getId() {
		return ID;
	}
	public void setId(Integer ID) {
		this.ID = ID;
	}
	public String getWORDERID() {
		return WORDERID;
	}
	public void setWORDERID(String wORDERID) {
		WORDERID = wORDERID;
	}
	public String getPATROLMANID() {
		return PATROLMANID;
	}
	public void setPATROLMANID(String pATROLMANID) {
		PATROLMANID = pATROLMANID;
	}
	public String getISLIGORSH() {
		return ISLIGORSH;
	}
	public void setISLIGORSH(String iSLIGORSH) {
		ISLIGORSH = iSLIGORSH;
	}
	public String getFEATID() {
		return FEATID;
	}
	public void setFEATID(String fEATID) {
		FEATID = fEATID;
	}
	public Double getDIAMETER() {
		return DIAMETER;
	}
	public void setDIAMETER(Double dIAMETER) {
		DIAMETER = dIAMETER;
	}
	public String getMATERIAL() {
		return MATERIAL;
	}
	public void setMATERIAL(String mATERIAL) {
		MATERIAL = mATERIAL;
	}
	public String getLOCATION() {
		return LOCATION;
	}
	public void setLOCATION(String lOCATION) {
		LOCATION = lOCATION;
	}
	public String getLEAKID() {
		return LEAKID;
	}
	public void setLEAKID(String lEAKID) {
		LEAKID = lEAKID;
	}
	public String getFOUNDTYPE() {
		return FOUNDTYPE;
	}
	public void setFOUNDTYPE(String fOUNDTYPE) {
		FOUNDTYPE = fOUNDTYPE;
	}
	public Double getPRESSURE() {
		return PRESSURE;
	}
	public void setPRESSURE(Double pRESSURE) {
		PRESSURE = pRESSURE;
	}
	public Double getDEPTH() {
		return DEPTH;
	}
	public void setDEPTH(Double dEPTH) {
		DEPTH = dEPTH;
	}
	public Double getLEAKTIME() {
		return LEAKTIME;
	}
	public void setLEAKTIME(Double lEAKTIME) {
		LEAKTIME = lEAKTIME;
	}
	public String getLEAKCOND() {
		return LEAKCOND;
	}
	public void setLEAKCOND(String lEAKCOND) {
		LEAKCOND = lEAKCOND;
	}
	public String getREPDATE() {
		return REPDATE;
	}
	public void setREPDATE(String rEPDATE) {
		REPDATE = rEPDATE;
	}
	public String getREPORTED() {
		return REPORTED;
	}
	public void setREPORTED(String rEPORTED) {
		REPORTED = rEPORTED;
	}
	public String getEXDATE() {
		return EXDATE;
	}
	public void setEXDATE(String eXDATE) {
		EXDATE = eXDATE;
	}
	public String getREPAIEDATE() {
		return REPAIEDATE;
	}
	public void setREPAIEDATE(String rEPAIEDATE) {
		REPAIEDATE = rEPAIEDATE;
	}
	public String getMAINPERSON() {
		return MAINPERSON;
	}
	public void setMAINPERSON(String mAINPERSON) {
		MAINPERSON = mAINPERSON;
	}
	public String getCOORDINATE() {
		return COORDINATE;
	}
	public void setCOORDINATE(String cOORDINATE) {
		COORDINATE = cOORDINATE;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getPCODE() {
		return PCODE;
	}
	public void setPCODE(String pCODE) {
		PCODE = pCODE;
	}
	public String getASSIGNER() {
		return ASSIGNER;
	}
	public void setASSIGNER(String aSSIGNER) {
		ASSIGNER = aSSIGNER;
	}
	public String getDEALRESULT() {
		return DEALRESULT;
	}
	public void setDEALRESULT(String dEALRESULT) {
		DEALRESULT = dEALRESULT;
	}
	public String getLEAKSHAPE() {
		return LEAKSHAPE;
	}
	public void setLEAKSHAPE(String lEAKSHAPE) {
		LEAKSHAPE = lEAKSHAPE;
	}
	public Double getLEAKDIA() {
		return LEAKDIA;
	}
	public void setLEAKDIA(Double lEAKDIA) {
		LEAKDIA = lEAKDIA;
	}
	public Double getLEAKFLOW() {
		return LEAKFLOW;
	}
	public void setLEAKFLOW(Double lEAKFLOW) {
		LEAKFLOW = lEAKFLOW;
	}
	public String getWATERLEAK() {
		return WATERLEAK;
	}
	public void setWATERLEAK(String wATERLEAK) {
		WATERLEAK = wATERLEAK;
	}
	public Double getYEARPIPE() {
		return YEARPIPE;
	}
	public void setYEARPIPE(Double yEARPIPE) {
		YEARPIPE = yEARPIPE;
	}
	public String getDIAMETERCORRECT() {
		return DIAMETERCORRECT;
	}
	public void setDIAMETERCORRECT(String dIAMETERCORRECT) {
		DIAMETERCORRECT = dIAMETERCORRECT;
	}
	public String getMATERIALCORRECT() {
		return MATERIALCORRECT;
	}
	public void setMATERIALCORRECT(String mATERIALCORRECT) {
		MATERIALCORRECT = mATERIALCORRECT;
	}
	public String getDEPTHCORRECT() {
		return DEPTHCORRECT;
	}
	public void setDEPTHCORRECT(String dEPTHCORRECT) {
		DEPTHCORRECT = dEPTHCORRECT;
	}
	public String getEMERGENCYLEVEL() {
		return EMERGENCYLEVEL;
	}
	public void setEMERGENCYLEVEL(String eMERGENCYLEVEL) {
		EMERGENCYLEVEL = eMERGENCYLEVEL;
	}
	public String getCUTOFFTIME() {
		return CUTOFFTIME;
	}
	public void setCUTOFFTIME(String cUTOFFTIME) {
		CUTOFFTIME = cUTOFFTIME;
	}
	public String getLEAKREGION() {
		return LEAKREGION;
	}
	public void setLEAKREGION(String lEAKREGION) {
		LEAKREGION = lEAKREGION;
	}
	public String getLEAKID2() {
		return LEAKID2;
	}
	public void setLEAKID2(String lEAKID2) {
		LEAKID2 = lEAKID2;
	}
	
	
	
}
