package com.koron.common.web.mapper;

public class LongTreeBean extends TreeBean.Long{
	
	public static final LongTreeBean ROOT = (LongTreeBean)new LongTreeBean().setSeq(1l).setMask(1).setChildMask(0).setParentMask(0);
	/**
	 * 分级ID
	 */
	private java.lang.Integer id;
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 外键
	 */
	private String foreignkey;

	/**
	 * 设置分级ID
	 */
	public LongTreeBean setId(java.lang.Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * 获取分级ID
	 */
	public java.lang.Integer getId() {
		return id;
	}

	/**
	 * 设置类型
	 */
	public LongTreeBean setType(int type) {
		this.type = type;
		return this;
	}

	/**
	 * 获取类型
	 */
	public int getType() {
		return type;
	}

	/**
	 * 设置外键
	 */
	public LongTreeBean setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
		return this;
	}

	/**
	 * 获取外键
	 */
	public String getForeignkey() {
		return foreignkey;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LongTreeBean [id=" + id + ", type=" + type + ", foreignkey=" + foreignkey + "]";
	}
}