package com.koron.common.bean.query;


/**
 * 搜索操作的条件
 * @author swan
 *
 */
public class OperationQueryBean extends BaseQueryBean {
	/**
	 * 关键字
	 */
	private String key;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * @return 获取关键字
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param 设置关键字
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return 获取名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param 设置名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
