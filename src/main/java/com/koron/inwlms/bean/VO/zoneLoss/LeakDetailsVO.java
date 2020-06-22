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
	private String LEAK_ID ;
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
	private String REP_DATE;
	//巡查负责人
	private String REPORTED;
	//开始维修时间
	private String EX_DATE;
	//完成时间
	private String REPAIE_DATE ;
	//维修负责人
	private String MAIN_PERSON;
	//坐标
	private String COORDINATE;
	//备注
	private String REMARK;
	//管段编号
	private String P_CODE;
	//负责人
	private String ASSIGNER;
	//处理结果
	private String DEALRESULT;
	//漏点形状
	private String LEAK_SHAPE;
	//漏点尺寸（cm）
	private Double LEAKDIA ;
	//漏水流量（m3/h）
	private Double LEAK_FLOW ;
	//漏失水量（m3）
	private String WATER_LEAK ;
	//管龄
	private Double YEAR_PIPE ;
	//管径纠正（mm）
	private String DIAMETER_CORRECT;
	//管材纠正
	private String MATERIAL_CORRECT;
	//管段埋深纠正（m）
	private String DEPTH_CORRECT ;
	//紧急程度
	private String EMERGENCY_LEVEL ;
	//停水时间
	private String CUTOFF_TIME ;
	//漏点所在区域
	private String LEAK_REGION ;
	//漏点编号(手填)
	private String LEAK_ID2;
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
	public String getLEAK_ID() {
		return LEAK_ID;
	}
	public void setLEAK_ID(String lEAK_ID) {
		LEAK_ID = lEAK_ID;
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
	public String getREP_DATE() {
		return REP_DATE;
	}
	public void setREP_DATE(String rEP_DATE) {
		REP_DATE = rEP_DATE;
	}
	public String getREPORTED() {
		return REPORTED;
	}
	public void setREPORTED(String rEPORTED) {
		REPORTED = rEPORTED;
	}
	public String getEX_DATE() {
		return EX_DATE;
	}
	public void setEX_DATE(String eX_DATE) {
		EX_DATE = eX_DATE;
	}
	public String getREPAIE_DATE() {
		return REPAIE_DATE;
	}
	public void setREPAIE_DATE(String rEPAIE_DATE) {
		REPAIE_DATE = rEPAIE_DATE;
	}
	public String getMAIN_PERSON() {
		return MAIN_PERSON;
	}
	public void setMAIN_PERSON(String mAIN_PERSON) {
		MAIN_PERSON = mAIN_PERSON;
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
	public String getP_CODE() {
		return P_CODE;
	}
	public void setP_CODE(String p_CODE) {
		P_CODE = p_CODE;
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
	public String getLEAK_SHAPE() {
		return LEAK_SHAPE;
	}
	public void setLEAK_SHAPE(String lEAK_SHAPE) {
		LEAK_SHAPE = lEAK_SHAPE;
	}
	public Double getLEAKDIA() {
		return LEAKDIA;
	}
	public void setLEAKDIA(Double lEAKDIA) {
		LEAKDIA = lEAKDIA;
	}
	public Double getLEAK_FLOW() {
		return LEAK_FLOW;
	}
	public void setLEAK_FLOW(Double lEAK_FLOW) {
		LEAK_FLOW = lEAK_FLOW;
	}
	public String getWATER_LEAK() {
		return WATER_LEAK;
	}
	public void setWATER_LEAK(String wATER_LEAK) {
		WATER_LEAK = wATER_LEAK;
	}
	public Double getYEAR_PIPE() {
		return YEAR_PIPE;
	}
	public void setYEAR_PIPE(Double yEAR_PIPE) {
		YEAR_PIPE = yEAR_PIPE;
	}
	public String getDIAMETER_CORRECT() {
		return DIAMETER_CORRECT;
	}
	public void setDIAMETER_CORRECT(String dIAMETER_CORRECT) {
		DIAMETER_CORRECT = dIAMETER_CORRECT;
	}
	public String getMATERIAL_CORRECT() {
		return MATERIAL_CORRECT;
	}
	public void setMATERIAL_CORRECT(String mATERIAL_CORRECT) {
		MATERIAL_CORRECT = mATERIAL_CORRECT;
	}
	public String getDEPTH_CORRECT() {
		return DEPTH_CORRECT;
	}
	public void setDEPTH_CORRECT(String dEPTH_CORRECT) {
		DEPTH_CORRECT = dEPTH_CORRECT;
	}
	public String getEMERGENCY_LEVEL() {
		return EMERGENCY_LEVEL;
	}
	public void setEMERGENCY_LEVEL(String eMERGENCY_LEVEL) {
		EMERGENCY_LEVEL = eMERGENCY_LEVEL;
	}
	public String getCUTOFF_TIME() {
		return CUTOFF_TIME;
	}
	public void setCUTOFF_TIME(String cUTOFF_TIME) {
		CUTOFF_TIME = cUTOFF_TIME;
	}
	public String getLEAK_REGION() {
		return LEAK_REGION;
	}
	public void setLEAK_REGION(String lEAK_REGION) {
		LEAK_REGION = lEAK_REGION;
	}
	public String getLEAK_ID2() {
		return LEAK_ID2;
	}
	public void setLEAK_ID2(String lEAK_ID2) {
		LEAK_ID2 = lEAK_ID2;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
