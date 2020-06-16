package com.koron.permission.service;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.permission.bean.DTO.TblOpDTO;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.TblOperationVO;
import com.koron.permission.bean.VO.TblRoleRangeValueVO;
import com.koron.permission.bean.VO.TblRoleVO;
import com.koron.permission.bean.VO.UserOperationVO;

public interface PermissionOuterService {
	 //查询用户查询权限列表
	public List<UserOperationVO> getUserOPList(SessionFactory factory,TblTenantDTO tenantCode);
	  
    //根据用户信息，获取用户所有角色。
  	public List<TblRoleVO> getUserRoleList(SessionFactory factory,TblTenantDTO tenantCode);
  	
  	//根据用户获取范围值
  	public List<TblRoleRangeValueVO> getUserRangeValueList(SessionFactory factory,TblTenantDTO tblTenantDTO);
  	
  	//根据用户判断是否有操作权限
  	public  List<TblOperationVO> getOPList(SessionFactory factory,TblOpDTO tblOpDTO);
}
