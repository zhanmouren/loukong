package com.koron.ebs.permission;


/**
 * 操作
 * @author 方志文
 *
 */
public interface Operation extends EntityID{
	/**
	 * 获取上级操作
	 * @return
	 */
	public Operation getParent();
	/**
	 * 获取拒约的规则
	 * @return
	 */
	public Rule getDeny();
	/**
	 * 获取允许的规则
	 * @return
	 */
	public Rule getAccept();
	/**
	 * 同位操作。
	 * 当返回非NULL则表示此操作权限同等于返回的操作,其余 {@link #isInherit()} {@link #getAccept()} {@link #getDeny()}均失效。
	 * @return 如果无同等操作，则返回NULL，
	 */
	public Operation getPeerOperation();
}
