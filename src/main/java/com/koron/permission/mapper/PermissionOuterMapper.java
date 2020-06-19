package com.koron.permission.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koron.permission.bean.DTO.TblOpDTO;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.TblOperationVO;
import com.koron.permission.bean.VO.TblRoleRangeValueVO;
import com.koron.permission.bean.VO.TblRoleVO;
import com.koron.permission.bean.VO.UserOperationVO;

@Repository
public interface PermissionOuterMapper {

	    //根据登录的用户查询该角色的权限
		public List<UserOperationVO> getUserOPList(TblTenantDTO tblTenantDTO);
		  //根据用户信息，获取用户所有角色。
	    public List<TblRoleVO> getUserRoleList(TblTenantDTO tblTenantDTO);
	    
	    //根据登录的用户获取范围值
	  	public List<TblRoleRangeValueVO> getUserRangeValueList(TblTenantDTO tblTenantDTO);
	  	
	  	//查询是否存在操作权限
	  	public List<TblOperationVO> getOPList(TblOpDTO tblOpDTO);  
}
