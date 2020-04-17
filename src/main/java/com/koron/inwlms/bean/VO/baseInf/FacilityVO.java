package com.koron.inwlms.bean.VO.baseInf;

/**
 * @auother:zhongweibin
 * @date:2020-04-14
 * @description:
 */
public class FacilityVO {

    //*************公共字段**************//
    /**
     *  资产状态
     */
    private String ASSET_STATUS;

    /**
     *  图幅号
     */
    private String GRID;

    /**
     *  点号
     */
    private String P_ID;

    /**
     * 节点编号
     */
    private String P_CODE;

    /**
     *  名称
     */
    private String name;

    /**
     *  材质
     */
    private String MATERIAL;

    /**
     *  口径（mm）
     */
    private Integer DIAMETER;

    /**
     *  规格
     */
    private String STANDARD;

    /**
     * 给排水类别
     */
    private String W_CATEGORY;

    /**
     * 运行状态
     */
    private String WORK_STATS;

    /**
     * 地面标高（m）
     */
    private Double ELEVATION;

    /**
     * 埋深（m）
     */
    private Double BUR_DEPTH;

    /**
     * 管顶标高（m）
     */
    private Double TOP_H;

    /**
     *  管底标高（m）
     */
    private Double BOTTOM_H;

    /**
     * 管线中心标高（m）
     */
    private Double CENTER_H;


    /**
     * 适用年限
     */
    private Double LIFESPAN;

    /**
     *  管理级别
     */
    private String GRADE;

    /**
     * 设计流量
     */
    private double DESIGN_F;

    /**
     * 设计扬程（m）
     */
    private double DESIGN_H;

    /**
     * 类型
     */
    private String TYPE;

    /**
     * 联系方式
     */
    private String TELEPHONE;

    /**
     * 行政区
     */
    private String DISTRICT;

    /**
     * 供水所
     */
    private String MAGUNIT;

    /**
     * 所在道路
     */
    private String ADDRESS;

    /**
     * 具体位置
     */
    private String LOCATION;

    /**
     *  位置类型
     */
    private String LOCATIONTYPE;

    /**
     * 维修部门
     */
    private String MAINT_DIVISION;

    /**
     * 管辖部门
     */
    private String ADMIN_DIVISION;

    /**
     * 工程名称
     */
    private String PROJECTNAME;

    /**
     * 工程编号
     */
    private String PRJID;

    /**
     * 归档号
     */
    private String ARCHIVE_ID;

    /**
     * 出厂日期
     */
    private String MANUFACTURE_DATE;

    /**
     * 设计单位
     */
    private String PLANUNIT;

    /**
     * 生产厂家
     */
    private String MANUFACTURER;

    /**
     * 建设单位
     */
    private String CONSTRUCTIONCOMPANY;

    /**
     * 施工单位
     */
    private String INSTALLCOMPANY;

    /**
     * 权属单位
     */
    private String OWNDER;

    /**
     * 实测X坐标
     */
    private Double X;

    /**
     * 实测Y坐标
     */
    private Double Y;

    /**
     *  数据来源
     */
    private String DATASOURCE;

    /**
     *  竣工日期
     */
    private String COMPLETDATE;

    /**
     *  备注
     */
    private String REMARK;

    //****************end***************//


    //***************管件***************//
    /**
     * 终点类别
     */
    private String FITTINGTYPE;

    /**
     * 管件功能
     */
    private String FUNCTION;

    /**
     * 管件类别
     */
    private String FITT_TYPE;

    /**
     * 连接方式
     */
    private String JOINTYPE;

    //****************end***************//

    //***************控制阀*************//
    /**
     *  阀门口径
     */
    private double CALIBER;

    /**
     *  主管口径
     */
    private double MAINPIPEDIAMETER;

    /**
     *  开关状态
     */
    private String ONOFF_STATS;

    /**
     *  排水方式
     */
    private String OUTLET;

    /**
     * 操作方式
     */
    private String OPERATION_MODE;

    /**
     * 阀门转向
     */
    private String TURN_DIRECTION;

    /**
     * 阀门转数
     */
    private double TURN_TO_CLOSE;

    /**
     * 阀门开度
     */
    private String TURNANGLE;

    /**
     * 密封类型
     */
    private String SEALYPE;

    /**
     * 是否净水隔离阀
     */
    private String IS_CCF;

    /**
     * 是否有检查井
     */
    private String IS_WELL;

    /**
     *  井室编号
     */
    private String VALVEWELLID;

    /**
     *  普查时间
     */
    private String SURVEYDATE;

    /**
     *  工作压力
     */
    private Double PRESSURE;

    /**
     *  是否旁通
     */
    private String VALVE_FLAG;

    /**
     *  阀门型号
     */
    private String MODEL;

    /**
     *  阀门安装类型
     */
    private String INSTAL_TYPE;

    /**
     *  阀门长度
     */
    private double STRUCTURE_LENGTH;

    /**
     *
     */

    //****************end***************//

    //*************调节阀************//

    //***************end************//

    //*************给水井室*********//

    /**
     *  内径（mm）
     */
    private double RADIUS;

    /**
     *  干湿井类型
     */
    private String WELL_DWTYPE;

    /**
     * 井型
     */
    private String WELL_TYPE;

    /**
     * 井规格
     */
    private String WELL_SIZE;

    /**
     *  井室形状
     */
    private String WELL_SHAPE;

    /**
     * 井内状况
     */
    private String WELL_STATUS;

    /**
     * 井室深度（m）
     */
    private double CEN_DEEP;

    /**
     *  井盖编号
     */
    private String COVER_ID;

    /**
     *  井口坐标X
     */
    private double COVER_X;

    /**
     * 井口坐标Y
     */
    private double COVER_Y;

    /**
     *  井盖材质
     */
    private String COVER_MATERIAL;

    /**
     *  井盖类型
     */
    private String COVER_TYPE;

    /**
     *  井盖尺寸
     */
    private String COVER_SIZE;

    //****************end***********//

    //**************排水井室（同给水井室）*********//

    //*****************end*******************//

    //**********水源井(同给水井室）************//

    //*****************end**************//

    //************消防栓************//
    /**
     * 进水口径（mm）
     */
    private int IN_DS;

    /**
     *  出水口径（mm）
     */
    private int OUT_DS;

    /**
     * 自由水压
     */
    private int W_PRESSURE;

    /**
     * 消防栓安装类型
     */
     private String H_TYPE;//原为TYPE

    /**
     * 消防栓用途
     */
    private String USETYPE;

    /**
     * 是否排水点
     */
    private String IS_DRAINAGE;

    /**
     * 是否管末
     */
    private String IS_PIPEND;

    /**
     *  是否测压点
     */
    private String IS_PRESS;

    /**
     *  是否有表
     */
    private String IS_METER;

    //************end***********//

    //**********泵站*********//
    /**
     * 水厂名称
     */
    private String FACTORY_NAME;

    /**
     * 设计供水能力
     */
    private String DESIGNCAPACITY;

    /**
     * 实际供水能力
     */
    private String RWSCCAPACITY;

    /**
     * 进水管径（mm）
     */
    private int D_S;

    /**
     * 出水管径（mm）
     */
    private int O_S;

    /**
     * 泵站扬程
     */
    private double LIFT;

    /**
     * 加压方式
     */
    private String PRESSURETYP;


    //**********end*********//

    //**********水厂********//
    /**
     * 进水管道数
     */
    private int PIPE_NUM;

    /**
     * 泵组数
     */
    private int PUMP_NUM;

    /**
     *  装机容积
     */
    private double MACHINE_CUB;

    /**
     * 传输流量
     */
    private double TRANS_FLUX;

    /**
     *  传输压力
     */
    private double TRANS_PRESS;

    /**
     * 传压方式
     */
    private String CPR_TYPE;

    //***********end*******//

    //***********给水构筑物**********//
    /**
     * 设施名称
     */
    private String FACILITYNAME;

    /**
     * 构筑物位置类型
     */
    private String CLASSTYPE;

    /**
     * 构筑物形状
     */
    private String JSSHAPE;

    /**
     * 构筑物容积
     */
    private double PDIM;

    /**
     * 尺寸
     */
    private String DIMENSION;

    /**
     * 进水管顶标高（m）
     */
    private double IN_ELEVATION;

    /**
     * 出水管顶标高（m）
     */
    private double OUT_ELEVATION;

    //***************end************//

    //**************取水口***********//
    /**
     *  水质状况
     */
    private String WQ_STATS;

    /**
     * 口径（mm）
     */
    private double BORE ; //原字段：D_S


    //****************end***********//

    //************厂站面************//

    //**************end************//

    //*************水泵************//
    /**
     * 进水口径（mm）
     */
    private int PUMP_PRO;

    /**
     * 出水口径（mm）
     */
    private int PUMP_BACK;

    /**
     * 水泵型号
     */
    private String PUM_MODEL;

    /**
     * 电机型号
     */
    private String POW_MODEL;

    /**
     * 水泵功率
     */
    private double POWER;

    /**
     * 电机功率
     */
    private String ELE_POWER;

    //**************end************//

    //**********二次供水设施***********//
    /**
     * 管理编号
     */
    private String M_CODE;

    /**
     * 泵房面积
     */
    private double PUMAREA;

    /**
     * 水箱容积
     */
    private double POOLCUBAGE;

    /**
     * 泵组台数
     */
    private int PUMNUM;

    /**
     * 进水管径（mm）
     */
    private int CALIBER2; //原字段：CALIBER

    /**
     * 出水口径（mm）
     */
    private int MAINPIPEDIAMETER2;//原字段：MAINPIPEDIAMETER

    /**
     * 电机功率
     */
    private double TELEPOWER;

    /**
     * 控制方式
     */
    private String CONTYPE;

    /**
     * 巡检方式
     */
    private String CHECKTYPE;

    /**
     * 加压方式
     */
    private String FORCINGTYPE;

    /**
     * 加压工艺
     */
    private String FORCINGCARFT;

    /**
     * 供水户数
     */
    private int WATERUSER;

    /**
     *  设备厂商
     */
    private String COMP_NAME;


    //**************end*************//

    //*************其他辅助设施************//


    //*****************end***************//


    public String getASSET_STATUS() {
        return ASSET_STATUS;
    }

    public void setASSET_STATUS(String ASSET_STATUS) {
        this.ASSET_STATUS = ASSET_STATUS;
    }

    public String getGRID() {
        return GRID;
    }

    public void setGRID(String GRID) {
        this.GRID = GRID;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public String getP_CODE() {
        return P_CODE;
    }

    public void setP_CODE(String p_CODE) {
        P_CODE = p_CODE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMATERIAL() {
        return MATERIAL;
    }

    public void setMATERIAL(String MATERIAL) {
        this.MATERIAL = MATERIAL;
    }

    public Integer getDIAMETER() {
        return DIAMETER;
    }

    public void setDIAMETER(Integer DIAMETER) {
        this.DIAMETER = DIAMETER;
    }

    public String getSTANDARD() {
        return STANDARD;
    }

    public void setSTANDARD(String STANDARD) {
        this.STANDARD = STANDARD;
    }

    public String getW_CATEGORY() {
        return W_CATEGORY;
    }

    public void setW_CATEGORY(String w_CATEGORY) {
        W_CATEGORY = w_CATEGORY;
    }

    public String getWORK_STATS() {
        return WORK_STATS;
    }

    public void setWORK_STATS(String WORK_STATS) {
        this.WORK_STATS = WORK_STATS;
    }

    public Double getELEVATION() {
        return ELEVATION;
    }

    public void setELEVATION(Double ELEVATION) {
        this.ELEVATION = ELEVATION;
    }

    public Double getBUR_DEPTH() {
        return BUR_DEPTH;
    }

    public void setBUR_DEPTH(Double BUR_DEPTH) {
        this.BUR_DEPTH = BUR_DEPTH;
    }

    public Double getTOP_H() {
        return TOP_H;
    }

    public void setTOP_H(Double TOP_H) {
        this.TOP_H = TOP_H;
    }

    public Double getBOTTOM_H() {
        return BOTTOM_H;
    }

    public void setBOTTOM_H(Double BOTTOM_H) {
        this.BOTTOM_H = BOTTOM_H;
    }

    public Double getCENTER_H() {
        return CENTER_H;
    }

    public void setCENTER_H(Double CENTER_H) {
        this.CENTER_H = CENTER_H;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getINSTAL_TYPE() {
        return INSTAL_TYPE;
    }

    public void setINSTAL_TYPE(String INSTAL_TYPE) {
        this.INSTAL_TYPE = INSTAL_TYPE;
    }

    public double getSTRUCTURE_LENGTH() {
        return STRUCTURE_LENGTH;
    }

    public void setSTRUCTURE_LENGTH(double STRUCTURE_LENGTH) {
        this.STRUCTURE_LENGTH = STRUCTURE_LENGTH;
    }

    public double getRADIUS() {
        return RADIUS;
    }

    public void setRADIUS(double RADIUS) {
        this.RADIUS = RADIUS;
    }

    public String getWELL_DWTYPE() {
        return WELL_DWTYPE;
    }

    public void setWELL_DWTYPE(String WELL_DWTYPE) {
        this.WELL_DWTYPE = WELL_DWTYPE;
    }

    public String getWELL_TYPE() {
        return WELL_TYPE;
    }

    public void setWELL_TYPE(String WELL_TYPE) {
        this.WELL_TYPE = WELL_TYPE;
    }

    public String getWELL_SIZE() {
        return WELL_SIZE;
    }

    public void setWELL_SIZE(String WELL_SIZE) {
        this.WELL_SIZE = WELL_SIZE;
    }

    public String getWELL_SHAPE() {
        return WELL_SHAPE;
    }

    public void setWELL_SHAPE(String WELL_SHAPE) {
        this.WELL_SHAPE = WELL_SHAPE;
    }

    public String getWELL_STATUS() {
        return WELL_STATUS;
    }

    public void setWELL_STATUS(String WELL_STATUS) {
        this.WELL_STATUS = WELL_STATUS;
    }

    public double getCEN_DEEP() {
        return CEN_DEEP;
    }

    public void setCEN_DEEP(double CEN_DEEP) {
        this.CEN_DEEP = CEN_DEEP;
    }

    public String getCOVER_ID() {
        return COVER_ID;
    }

    public void setCOVER_ID(String COVER_ID) {
        this.COVER_ID = COVER_ID;
    }

    public double getCOVER_X() {
        return COVER_X;
    }

    public void setCOVER_X(double COVER_X) {
        this.COVER_X = COVER_X;
    }

    public double getCOVER_Y() {
        return COVER_Y;
    }

    public void setCOVER_Y(double COVER_Y) {
        this.COVER_Y = COVER_Y;
    }

    public String getCOVER_MATERIAL() {
        return COVER_MATERIAL;
    }

    public void setCOVER_MATERIAL(String COVER_MATERIAL) {
        this.COVER_MATERIAL = COVER_MATERIAL;
    }

    public String getCOVER_TYPE() {
        return COVER_TYPE;
    }

    public void setCOVER_TYPE(String COVER_TYPE) {
        this.COVER_TYPE = COVER_TYPE;
    }

    public String getCOVER_SIZE() {
        return COVER_SIZE;
    }

    public void setCOVER_SIZE(String COVER_SIZE) {
        this.COVER_SIZE = COVER_SIZE;
    }

    public int getIN_DS() {
        return IN_DS;
    }

    public void setIN_DS(int IN_DS) {
        this.IN_DS = IN_DS;
    }

    public int getOUT_DS() {
        return OUT_DS;
    }

    public void setOUT_DS(int OUT_DS) {
        this.OUT_DS = OUT_DS;
    }

    public int getW_PRESSURE() {
        return W_PRESSURE;
    }

    public void setW_PRESSURE(int w_PRESSURE) {
        W_PRESSURE = w_PRESSURE;
    }

    public String getH_TYPE() {
        return H_TYPE;
    }

    public void setH_TYPE(String h_TYPE) {
        H_TYPE = h_TYPE;
    }

    public String getUSETYPE() {
        return USETYPE;
    }

    public void setUSETYPE(String USETYPE) {
        this.USETYPE = USETYPE;
    }

    public String getIS_DRAINAGE() {
        return IS_DRAINAGE;
    }

    public void setIS_DRAINAGE(String IS_DRAINAGE) {
        this.IS_DRAINAGE = IS_DRAINAGE;
    }

    public String getIS_PIPEND() {
        return IS_PIPEND;
    }

    public void setIS_PIPEND(String IS_PIPEND) {
        this.IS_PIPEND = IS_PIPEND;
    }

    public String getIS_PRESS() {
        return IS_PRESS;
    }

    public void setIS_PRESS(String IS_PRESS) {
        this.IS_PRESS = IS_PRESS;
    }

    public String getIS_METER() {
        return IS_METER;
    }

    public void setIS_METER(String IS_METER) {
        this.IS_METER = IS_METER;
    }

    public String getFACTORY_NAME() {
        return FACTORY_NAME;
    }

    public void setFACTORY_NAME(String FACTORY_NAME) {
        this.FACTORY_NAME = FACTORY_NAME;
    }

    public String getDESIGNCAPACITY() {
        return DESIGNCAPACITY;
    }

    public void setDESIGNCAPACITY(String DESIGNCAPACITY) {
        this.DESIGNCAPACITY = DESIGNCAPACITY;
    }

    public String getRWSCCAPACITY() {
        return RWSCCAPACITY;
    }

    public void setRWSCCAPACITY(String RWSCCAPACITY) {
        this.RWSCCAPACITY = RWSCCAPACITY;
    }

    public int getD_S() {
        return D_S;
    }

    public void setD_S(int d_S) {
        D_S = d_S;
    }

    public int getO_S() {
        return O_S;
    }

    public void setO_S(int o_S) {
        O_S = o_S;
    }

    public double getLIFT() {
        return LIFT;
    }

    public void setLIFT(double LIFT) {
        this.LIFT = LIFT;
    }

    public String getPRESSURETYP() {
        return PRESSURETYP;
    }

    public void setPRESSURETYP(String PRESSURETYP) {
        this.PRESSURETYP = PRESSURETYP;
    }

    public int getPIPE_NUM() {
        return PIPE_NUM;
    }

    public void setPIPE_NUM(int PIPE_NUM) {
        this.PIPE_NUM = PIPE_NUM;
    }

    public int getPUMP_NUM() {
        return PUMP_NUM;
    }

    public void setPUMP_NUM(int PUMP_NUM) {
        this.PUMP_NUM = PUMP_NUM;
    }

    public double getMACHINE_CUB() {
        return MACHINE_CUB;
    }

    public void setMACHINE_CUB(double MACHINE_CUB) {
        this.MACHINE_CUB = MACHINE_CUB;
    }

    public double getTRANS_FLUX() {
        return TRANS_FLUX;
    }

    public void setTRANS_FLUX(double TRANS_FLUX) {
        this.TRANS_FLUX = TRANS_FLUX;
    }

    public double getTRANS_PRESS() {
        return TRANS_PRESS;
    }

    public void setTRANS_PRESS(double TRANS_PRESS) {
        this.TRANS_PRESS = TRANS_PRESS;
    }

    public String getCPR_TYPE() {
        return CPR_TYPE;
    }

    public void setCPR_TYPE(String CPR_TYPE) {
        this.CPR_TYPE = CPR_TYPE;
    }

    public String getFACILITYNAME() {
        return FACILITYNAME;
    }

    public void setFACILITYNAME(String FACILITYNAME) {
        this.FACILITYNAME = FACILITYNAME;
    }

    public String getCLASSTYPE() {
        return CLASSTYPE;
    }

    public void setCLASSTYPE(String CLASSTYPE) {
        this.CLASSTYPE = CLASSTYPE;
    }

    public String getJSSHAPE() {
        return JSSHAPE;
    }

    public void setJSSHAPE(String JSSHAPE) {
        this.JSSHAPE = JSSHAPE;
    }

    public double getPDIM() {
        return PDIM;
    }

    public void setPDIM(double PDIM) {
        this.PDIM = PDIM;
    }

    public String getDIMENSION() {
        return DIMENSION;
    }

    public void setDIMENSION(String DIMENSION) {
        this.DIMENSION = DIMENSION;
    }

    public double getIN_ELEVATION() {
        return IN_ELEVATION;
    }

    public void setIN_ELEVATION(double IN_ELEVATION) {
        this.IN_ELEVATION = IN_ELEVATION;
    }

    public double getOUT_ELEVATION() {
        return OUT_ELEVATION;
    }

    public void setOUT_ELEVATION(double OUT_ELEVATION) {
        this.OUT_ELEVATION = OUT_ELEVATION;
    }

    public String getWQ_STATS() {
        return WQ_STATS;
    }

    public void setWQ_STATS(String WQ_STATS) {
        this.WQ_STATS = WQ_STATS;
    }

    public double getBORE() {
        return BORE;
    }

    public void setBORE(double BORE) {
        this.BORE = BORE;
    }

    public int getPUMP_PRO() {
        return PUMP_PRO;
    }

    public void setPUMP_PRO(int PUMP_PRO) {
        this.PUMP_PRO = PUMP_PRO;
    }

    public int getPUMP_BACK() {
        return PUMP_BACK;
    }

    public void setPUMP_BACK(int PUMP_BACK) {
        this.PUMP_BACK = PUMP_BACK;
    }

    public String getPUM_MODEL() {
        return PUM_MODEL;
    }

    public void setPUM_MODEL(String PUM_MODEL) {
        this.PUM_MODEL = PUM_MODEL;
    }

    public String getPOW_MODEL() {
        return POW_MODEL;
    }

    public void setPOW_MODEL(String POW_MODEL) {
        this.POW_MODEL = POW_MODEL;
    }

    public double getPOWER() {
        return POWER;
    }

    public void setPOWER(double POWER) {
        this.POWER = POWER;
    }

    public String getELE_POWER() {
        return ELE_POWER;
    }

    public void setELE_POWER(String ELE_POWER) {
        this.ELE_POWER = ELE_POWER;
    }

    public String getM_CODE() {
        return M_CODE;
    }

    public void setM_CODE(String m_CODE) {
        M_CODE = m_CODE;
    }

    public double getPUMAREA() {
        return PUMAREA;
    }

    public void setPUMAREA(double PUMAREA) {
        this.PUMAREA = PUMAREA;
    }

    public double getPOOLCUBAGE() {
        return POOLCUBAGE;
    }

    public void setPOOLCUBAGE(double POOLCUBAGE) {
        this.POOLCUBAGE = POOLCUBAGE;
    }

    public int getPUMNUM() {
        return PUMNUM;
    }

    public void setPUMNUM(int PUMNUM) {
        this.PUMNUM = PUMNUM;
    }

    public int getCALIBER2() {
        return CALIBER2;
    }

    public void setCALIBER2(int CALIBER2) {
        this.CALIBER2 = CALIBER2;
    }

    public int getMAINPIPEDIAMETER2() {
        return MAINPIPEDIAMETER2;
    }

    public void setMAINPIPEDIAMETER2(int MAINPIPEDIAMETER2) {
        this.MAINPIPEDIAMETER2 = MAINPIPEDIAMETER2;
    }

    public double getTELEPOWER() {
        return TELEPOWER;
    }

    public void setTELEPOWER(double TELEPOWER) {
        this.TELEPOWER = TELEPOWER;
    }

    public String getCONTYPE() {
        return CONTYPE;
    }

    public void setCONTYPE(String CONTYPE) {
        this.CONTYPE = CONTYPE;
    }

    public String getCHECKTYPE() {
        return CHECKTYPE;
    }

    public void setCHECKTYPE(String CHECKTYPE) {
        this.CHECKTYPE = CHECKTYPE;
    }

    public String getFORCINGTYPE() {
        return FORCINGTYPE;
    }

    public void setFORCINGTYPE(String FORCINGTYPE) {
        this.FORCINGTYPE = FORCINGTYPE;
    }

    public String getFORCINGCARFT() {
        return FORCINGCARFT;
    }

    public void setFORCINGCARFT(String FORCINGCARFT) {
        this.FORCINGCARFT = FORCINGCARFT;
    }

    public int getWATERUSER() {
        return WATERUSER;
    }

    public void setWATERUSER(int WATERUSER) {
        this.WATERUSER = WATERUSER;
    }

    public String getCOMP_NAME() {
        return COMP_NAME;
    }

    public void setCOMP_NAME(String COMP_NAME) {
        this.COMP_NAME = COMP_NAME;
    }

    public Double getLIFESPAN() {
        return LIFESPAN;
    }

    public void setLIFESPAN(Double LIFESPAN) {
        this.LIFESPAN = LIFESPAN;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public double getDESIGN_F() {
        return DESIGN_F;
    }

    public void setDESIGN_F(double DESIGN_F) {
        this.DESIGN_F = DESIGN_F;
    }

    public double getDESIGN_H() {
        return DESIGN_H;
    }

    public void setDESIGN_H(double DESIGN_H) {
        this.DESIGN_H = DESIGN_H;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getTELEPHONE() {
        return TELEPHONE;
    }

    public void setTELEPHONE(String TELEPHONE) {
        this.TELEPHONE = TELEPHONE;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public void setDISTRICT(String DISTRICT) {
        this.DISTRICT = DISTRICT;
    }

    public String getMAGUNIT() {
        return MAGUNIT;
    }

    public void setMAGUNIT(String MAGUNIT) {
        this.MAGUNIT = MAGUNIT;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getLOCATIONTYPE() {
        return LOCATIONTYPE;
    }

    public void setLOCATIONTYPE(String LOCATIONTYPE) {
        this.LOCATIONTYPE = LOCATIONTYPE;
    }

    public String getMAINT_DIVISION() {
        return MAINT_DIVISION;
    }

    public void setMAINT_DIVISION(String MAINT_DIVISION) {
        this.MAINT_DIVISION = MAINT_DIVISION;
    }

    public String getADMIN_DIVISION() {
        return ADMIN_DIVISION;
    }

    public void setADMIN_DIVISION(String ADMIN_DIVISION) {
        this.ADMIN_DIVISION = ADMIN_DIVISION;
    }

    public String getPROJECTNAME() {
        return PROJECTNAME;
    }

    public void setPROJECTNAME(String PROJECTNAME) {
        this.PROJECTNAME = PROJECTNAME;
    }

    public String getPRJID() {
        return PRJID;
    }

    public void setPRJID(String PRJID) {
        this.PRJID = PRJID;
    }

    public String getARCHIVE_ID() {
        return ARCHIVE_ID;
    }

    public void setARCHIVE_ID(String ARCHIVE_ID) {
        this.ARCHIVE_ID = ARCHIVE_ID;
    }

    public String getMANUFACTURE_DATE() {
        return MANUFACTURE_DATE;
    }

    public void setMANUFACTURE_DATE(String MANUFACTURE_DATE) {
        this.MANUFACTURE_DATE = MANUFACTURE_DATE;
    }

    public String getPLANUNIT() {
        return PLANUNIT;
    }

    public void setPLANUNIT(String PLANUNIT) {
        this.PLANUNIT = PLANUNIT;
    }

    public String getMANUFACTURER() {
        return MANUFACTURER;
    }

    public void setMANUFACTURER(String MANUFACTURER) {
        this.MANUFACTURER = MANUFACTURER;
    }

    public String getCONSTRUCTIONCOMPANY() {
        return CONSTRUCTIONCOMPANY;
    }

    public void setCONSTRUCTIONCOMPANY(String CONSTRUCTIONCOMPANY) {
        this.CONSTRUCTIONCOMPANY = CONSTRUCTIONCOMPANY;
    }

    public String getINSTALLCOMPANY() {
        return INSTALLCOMPANY;
    }

    public void setINSTALLCOMPANY(String INSTALLCOMPANY) {
        this.INSTALLCOMPANY = INSTALLCOMPANY;
    }

    public String getOWNDER() {
        return OWNDER;
    }

    public void setOWNDER(String OWNDER) {
        this.OWNDER = OWNDER;
    }

    public Double getX() {
        return X;
    }

    public void setX(Double x) {
        X = x;
    }

    public Double getY() {
        return Y;
    }

    public void setY(Double y) {
        Y = y;
    }

    public String getDATASOURCE() {
        return DATASOURCE;
    }

    public void setDATASOURCE(String DATASOURCE) {
        this.DATASOURCE = DATASOURCE;
    }

    public String getCOMPLETDATE() {
        return COMPLETDATE;
    }

    public void setCOMPLETDATE(String COMPLETDATE) {
        this.COMPLETDATE = COMPLETDATE;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getFITTINGTYPE() {
        return FITTINGTYPE;
    }

    public void setFITTINGTYPE(String FITTINGTYPE) {
        this.FITTINGTYPE = FITTINGTYPE;
    }

    public String getFUNCTION() {
        return FUNCTION;
    }

    public void setFUNCTION(String FUNCTION) {
        this.FUNCTION = FUNCTION;
    }

    public String getFITT_TYPE() {
        return FITT_TYPE;
    }

    public void setFITT_TYPE(String FITT_TYPE) {
        this.FITT_TYPE = FITT_TYPE;
    }

    public String getJOINTYPE() {
        return JOINTYPE;
    }

    public void setJOINTYPE(String JOINTYPE) {
        this.JOINTYPE = JOINTYPE;
    }

    public double getCALIBER() {
        return CALIBER;
    }

    public void setCALIBER(double CALIBER) {
        this.CALIBER = CALIBER;
    }

    public double getMAINPIPEDIAMETER() {
        return MAINPIPEDIAMETER;
    }

    public void setMAINPIPEDIAMETER(double MAINPIPEDIAMETER) {
        this.MAINPIPEDIAMETER = MAINPIPEDIAMETER;
    }

    public String getONOFF_STATS() {
        return ONOFF_STATS;
    }

    public void setONOFF_STATS(String ONOFF_STATS) {
        this.ONOFF_STATS = ONOFF_STATS;
    }

    public String getOUTLET() {
        return OUTLET;
    }

    public void setOUTLET(String OUTLET) {
        this.OUTLET = OUTLET;
    }

    public String getOPERATION_MODE() {
        return OPERATION_MODE;
    }

    public void setOPERATION_MODE(String OPERATION_MODE) {
        this.OPERATION_MODE = OPERATION_MODE;
    }

    public String getTURN_DIRECTION() {
        return TURN_DIRECTION;
    }

    public void setTURN_DIRECTION(String TURN_DIRECTION) {
        this.TURN_DIRECTION = TURN_DIRECTION;
    }

    public double getTURN_TO_CLOSE() {
        return TURN_TO_CLOSE;
    }

    public void setTURN_TO_CLOSE(double TURN_TO_CLOSE) {
        this.TURN_TO_CLOSE = TURN_TO_CLOSE;
    }

    public String getTURNANGLE() {
        return TURNANGLE;
    }

    public void setTURNANGLE(String TURNANGLE) {
        this.TURNANGLE = TURNANGLE;
    }

    public String getSEALYPE() {
        return SEALYPE;
    }

    public void setSEALYPE(String SEALYPE) {
        this.SEALYPE = SEALYPE;
    }

    public String getIS_CCF() {
        return IS_CCF;
    }

    public void setIS_CCF(String IS_CCF) {
        this.IS_CCF = IS_CCF;
    }

    public String getIS_WELL() {
        return IS_WELL;
    }

    public void setIS_WELL(String IS_WELL) {
        this.IS_WELL = IS_WELL;
    }

    public String getVALVEWELLID() {
        return VALVEWELLID;
    }

    public void setVALVEWELLID(String VALVEWELLID) {
        this.VALVEWELLID = VALVEWELLID;
    }

    public String getSURVEYDATE() {
        return SURVEYDATE;
    }

    public void setSURVEYDATE(String SURVEYDATE) {
        this.SURVEYDATE = SURVEYDATE;
    }

    public Double getPRESSURE() {
        return PRESSURE;
    }

    public void setPRESSURE(Double PRESSURE) {
        this.PRESSURE = PRESSURE;
    }

    public String getVALVE_FLAG() {
        return VALVE_FLAG;
    }

    public void setVALVE_FLAG(String VALVE_FLAG) {
        this.VALVE_FLAG = VALVE_FLAG;
    }
}
