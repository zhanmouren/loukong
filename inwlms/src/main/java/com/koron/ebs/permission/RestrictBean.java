package com.koron.ebs.permission;

/**
 * <pre>
 * 用来存储操作的限制条件.
 * limits为条件限制，如：age > 20  或 age > ${user.sge}
 * scope为范围限制，如：职员查看,scope为 age,birth,salary。
 * 保留字为：= > >= < <= != like between and    
 * @author 方志文
 * </pre>
 */
final class RestrictBean {
	/**
	 * 限制条件
	 */
	private String[] limits;
	/**
	 * 限制范围,null时为不进行过滤
	 * 
	 * @see #except
	 */
	private String[] scope;
	/**
	 * 范围意义. true为时表示scope表示可以看范围,false时为不可看范围
	 */
	private boolean include;

	/**
	 * 构造函数
	 * 
	 * @param limits 限制条件
	 * @param scope 范围
	 */
	public RestrictBean(String[] limits, String[] scope, boolean include) {
		super();
		this.limits = limits;
		this.scope = scope;
		this.include = include;
	}

	/**
	 * @return 获取限制条件
	 */
	public String[] getLimits() {
		return limits;
	}

	/**
	 * @param limits 设置限制条件
	 */
	public void setLimits(String[] limits) {
		this.limits = limits;
	}

	/**
	 * @return 获取限制范围
	 */
	public String[] getScope() {
		return scope;
	}

	/**
	 * @param scope 设置限制范围.
	 */
	public void setScope(String[] scope) {
		this.scope = scope;
	}

	/**
	 * @return 获取范围意义. 
	 * true为时表示scope表示可以看范围
	 * false时为不可看范围，缺省为false
	 */
	public boolean isInclude() {
		return include;
	}

	/**
	 * @param include 设置范围意义. 
	 * true为时表示scope表示可以看范围
	 * false时为不可看范围，缺省为false
	 */
	public void setInclude(boolean include) {
		this.include = include;
	}

}