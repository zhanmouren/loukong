package com.koron.common.web.bean;

import com.koron.common.bean.IdentityBean;

public class DepartmentBean implements IdentityBean{
	/**
	 * 部门flag  枚举key 
	 */
	public static String FLAG_ENUM_KEY="department.flag";
	/**
	 * 集团
	 */
	public static Integer LEVEL_GROUP=1;
	/**
	 * 水司
	 */
	public static Integer LEVEL_COMPANY=16;
	/**
	 * 分区
	 */
	public static Integer LEVEL_AREA=16;
	/**
	 * 部门
	 */
	public static Integer LEVEL_DEPARTMENT = 1024;
	/**
	 * 虚拟
	 */
	public static Integer LEVEL_VIRTUAL = 2018;
	
	/**
	 * 自来水
	 */
	public static Integer FLAG_ZILAISHUI=16;
	/**
	 * 污水
	 */
	public static Integer FLAG_WUSHUI=32;
	/**
	 * 原水
	 */
	public static Integer FLAG_YUANSHUI=64;
	/**
	 * 工程
	 */
	public static Integer FLAG_GONGCHENG=128;
	
	/**
	 * 删除
	 */
	public static Integer STATE_DELETE=1;
	
	private Integer id;
	/**
	 * 组织名称
	 */
	private String name;
	/**
	 * 短名
	 */
	private String shortname;
	/**
	 * 组织编码
	 */
	private String code;
	/**
	 * 组织描述
	 */
	private String description;
	/**
	 * 排序值
	 */
	private Integer sn;
	/**
	 * 组织电话
	 */
	private String tel;
	/**
	 * 组织状态
	 */
	private Integer state;
	/**
	 * 组织状态1部门,2水司4水厂8自来水16污水
	 */
	private Integer flag;
	/**
	 * 数据仓库维键
	 */
	private String datacenterkey;
	/**
	 * 父级部门ID
	 */
	private int parentId;
	/**
	*设置
	*/
	public DepartmentBean setId(Integer id){
		this.id = id;
	return this;
	}
	/**
	*获取
	*/
	public Integer getId(){
		return id;
	}
	/**
	*设置组织名称
	*/
	public DepartmentBean setName(String name){
		this.name = name;
	return this;
	}
	/**
	*获取组织名称
	*/
	public String getName(){
		return name;
	}
	/**
	*设置组织编码
	*/
	public DepartmentBean setCode(String code){
		this.code = code;
	return this;
	}
	/**
	*获取组织编码
	*/
	public String getCode(){
		return code;
	}
	/**
	*设置组织描述
	*/
	public DepartmentBean setDescription(String description){
		this.description = description;
	return this;
	}
	/**
	*获取组织描述
	*/
	public String getDescription(){
		return description;
	}
	/**
	*设置排序值
	*/
	public DepartmentBean setSn(Integer sn){
		this.sn = sn;
	return this;
	}
	/**
	*获取排序值
	*/
	public Integer getSn(){
		return sn;
	}
	/**
	*设置组织电话
	*/
	public DepartmentBean setTel(String tel){
		this.tel = tel;
	return this;
	}
	/**
	*获取组织电话
	*/
	public String getTel(){
		return tel;
	}
	/**
	*设置组织状态
	*/
	public DepartmentBean setState(Integer state){
		this.state = state;
	return this;
	}
	/**
	*获取组织状态
	*/
	public Integer getState(){
		return state;
	}
	/**
	*设置组织状态1部门,2水司4水厂8自来水16污水
	*/
	public DepartmentBean setFlag(Integer flag){
		this.flag = flag;
	return this;
	}
	/**
	*获取组织状态1部门,2水司4水厂8自来水16污水
	*/
	public Integer getFlag(){
		return flag;
	}
	/**
	*设置数据仓库维键
	*/
	public DepartmentBean setDatacenterkey(String datacenterkey){
		this.datacenterkey = datacenterkey;
	return this;
	}
	/**
	*获取数据仓库维键
	*/
	public String getDatacenterkey(){
		return datacenterkey;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
}