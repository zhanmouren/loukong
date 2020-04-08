package com.koron.inwlms.bean.VO.sysManager;

import com.koron.common.web.mapper.TreeBean;

public class TreeMenuVO extends TreeBean.Long{
	    //模块Id
		private java.lang.Integer menuId;
		//模块编号
		private String menuCode;
		//模块编号
		private String moduleNo;
		//模块名称
		private String moduleName;
		//链接地址
		private String linkAddress;
		
		
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
		public TreeMenuVO setId(java.lang.Integer id) {
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
		public TreeMenuVO setType(int type) {
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
		public TreeMenuVO setForeignkey(String foreignkey) {
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
			return "TreeMenuVO [id=" + id + ", type=" + type + ", foreignkey=" + foreignkey + "]";
		}
}
