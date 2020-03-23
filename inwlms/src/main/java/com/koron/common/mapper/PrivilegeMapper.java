package com.koron.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.koron.common.bean.OperationBean;
import com.koron.common.bean.RoleOperationBean;
import com.koron.common.bean.RoleOperationModifyBean;
import com.koron.common.bean.query.OperationQueryBean;
import com.koron.common.bean.query.RoleOperationQueryBean;

public interface PrivilegeMapper {
	/**
	 * 搜索系统操作
	 */
	public List<OperationBean> listOperation(OperationQueryBean bean);
	/**
	 * 查询角色相对应的操作数量
	 */
	public Integer listRoleOperationCount(RoleOperationQueryBean bean);
	
	
	/**
	 * 删除角色相对应的操作
	 */
	public Integer delRoleOperation(RoleOperationModifyBean bean);
	/**
	 * 添加角色相对应的操作
	 */
	public Integer addRoleOperation(RoleOperationModifyBean bean);
	/**
	 * 角色操作映射功能
	 */
	public List<RoleOperationBean> listRoleOperation(RoleOperationQueryBean bean);
	
	/**
	 * 修改用户密码
	 */
	public Integer updateUserPassword(@Param("id") int id, @Param("password") String password);
	/**
	 * 用老密码修改新密码
	 * @param id
	 * @param password
	 * @param oldPwd
	 * @return
	 */
	public Integer updateUserPassword2(@Param("id") int id, @Param("password") String password,@Param("oldPwd") String oldPwd);

}