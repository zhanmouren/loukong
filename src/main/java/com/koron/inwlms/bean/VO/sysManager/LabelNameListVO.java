package com.koron.inwlms.bean.VO.sysManager;


/**
 * 标签列表VO
 * @author lzy
 * @date 2020/5/11
 *
 */
public class LabelNameListVO<T> {


	//标签简体中文名列表数据
	private T cnList;
	//标签英文名列表数据
	private T enList;
	//标签繁体中文名列表数据
	private T cnHKList;

	public T getCnList() {
		return cnList;
	}

	public void setCnList(T cnList) {
		this.cnList = cnList;
	}

	public T getEnList() {
		return enList;
	}

	public void setEnList(T enList) {
		this.enList = enList;
	}

	public T getCnHKList() {
		return cnHKList;
	}

	public void setCnHKList(T cnHKList) {
		this.cnHKList = cnHKList;
	}
	
	
}
