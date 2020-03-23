package com.koron.ebs.permission.mybatis;
public class SPIrelationBean implements Cloneable{
	
	
	private Integer id;
	/**
	 * 源ID
	 */
	private String source;
	/**
	 * 关系类型
	 */
	private Integer type;
	/**
	 * 关联ID
	 */
	private String target;
	
	private String dept;
	/**
	*设置
	*/
	public SPIrelationBean setId(Integer id){
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
	*设置源ID
	*/
	public SPIrelationBean setSource(String source){
		this.source = source;
	return this;
	}
	/**
	*获取源ID
	*/
	public String getSource(){
		return source;
	}
	/**
	*设置关系类型
	*/
	public SPIrelationBean setType(Integer type){
		this.type = type;
	return this;
	}
	/**
	*获取关系类型
	*/
	public Integer getType(){
		return type;
	}
	/**
	*设置关联ID
	*/
	public SPIrelationBean setTarget(String target){
		this.target = target;
	return this;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SPIrelationBean other = (SPIrelationBean) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	/**
	*获取关联ID
	*/
	public String getTarget(){
		return target;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	
	@Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}