package com.koron.common.bean;

import com.google.gson.annotations.SerializedName;


public class LevelBean implements IdentityBean{
	/**
	 * 部门分级信息
	 */
	public static final Integer LEVELTYPE_DEPARTMENT = 0x1;
	
	/**
	 * 区块分级信息
	 */
	public static final Integer LEVELTYPE_BLOCKDIV = 0x2;
	
	/**
	 * 分级ID
	 */
	private Integer id;
	/**
	 * 分级编码(每一级编码占10位，最多可分为5级)
	 */
	private transient Long seq;
	/**
	 * 转成字符串
	 */
	@SerializedName("seq")
	private String seqStr;

	/**
	 * 父级信息编码
	 */
	private Long parentSeq; 
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private Integer levelType;
	/**
	 * 当前的级别
	 */
	private Integer currentLevel;
	/**
	 * 排序
	 */
	private Integer order = 0;
	/**
	 * 描述信息
	 */
	private String description;
	/**
	*设置分级ID
	*/
	public LevelBean setId(Integer id){
		this.id = id;
		return this;
	}
	/**
	*获取分级ID
	*/
	public Integer getId() {
		return id;
	}
	/**
	*设置分级编码(每一级编码占10位，最多可分为5级)
	*/
	public void setSeq(Long seq){
		this.seq = seq;
		seqStr = String.valueOf(seq);
	}
	/**
	*获取分级编码(每一级编码占10位，最多可分为5级)
	*/
	public Long getSeq(){
		return seq;
	}
	public Long getParentSeq() {
		return parentSeq;
	}
	public void setParentSeq(Long parentSeq) {
		this.parentSeq = parentSeq;
	}
	/**
	*设置名称
	*/
	public LevelBean setName(String name){
		this.name = name;
		return this;
	}
	/**
	*获取名称
	*/
	public String getName(){
		return name;
	}
	/**
	*设置类型
	*/
	public void setLevelType(Integer levelType){
		this.levelType = levelType;
	}
	/**
	*获取类型
	*/
	public Integer getLevelType(){
		return levelType;
	}

	public Integer getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(Integer currentLevel) {
		this.currentLevel = currentLevel;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSeqStr() {
		return seqStr;
	}
	public void setSeqStr(String seqStr) {
		this.seqStr = seqStr;
	}
	
	/**
	 * 根据父级数字编码获得本级信息属于哪一级(每级占10位)
	 * 1.当seq = 0 ，结果1；
	 * 2.当seq = 0004 0000 0000 0000，结果2
	 * 3.当seq = 0004 0100 0000 0000， 结果3
	 * 4.当seq = 0004 0100 4000 0000，结果4
	 * 4.当seq = 0004 0100 4010 0000，结果5
	 * 4.当seq = 0004 0100 4010 0400，结果6
	 * 5.当seq = 0004 0100 4010 0401，结果7，这时候是异常情况
	 * @param seq 父级编码
	 * @return
	 */
	public static int getCurrentLevelByParentseq(long seq) {
		long compare = 0xFFFFFFFFFFFFFFFL;
		for (int i = 0; i < 6; i++) {
			if (((compare >>> (i * 10)) & seq) == 0) {
				return i + 1;
			}
		}
		return 7;
	}

	/**
	 * 根据本级信息编码获取父级编码
	 * @param seq 本级编码
	 * @return
	 */
	public static long getParentSeq(Long seq) {
		int currentLevel = getCurrentLevelByParentseq(seq)-1;
		if(currentLevel == 1)//如果刚好占满64位，就需要执行这一步判断
			return 0;
		return seq & (0xFFFFFFFFFFFFFFFL ^ (0x1L << getShift(currentLevel-1))-1);
	}
	
	/**
	 * 查询某级信息的下级信息编码共占的位数
	 * 1.1级 下级信息编码占50位
	 * 2.2级 下级信息编码占40位
	 * 3.3级 下级信息编码占30位
	 * 4.4级 下级信息编码占20位
	 * 5.5级 下级信息编码占10位
	 * 6.6级 下级信息编码占0位
	 * @param level 要查询的地区所属的级数
	 * @return
	 */
	public static int getShift(int level) {
		return (6-level)*10;
	}
	
}