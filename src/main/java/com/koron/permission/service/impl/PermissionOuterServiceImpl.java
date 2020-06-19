package com.koron.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.koron.permission.bean.DTO.TblOpDTO;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.TblOperationVO;
import com.koron.permission.bean.VO.TblRoleRangeValueVO;
import com.koron.permission.bean.VO.TblRoleVO;
import com.koron.permission.bean.VO.UserOperationVO;
import com.koron.permission.mapper.PermissionOuterMapper;
import com.koron.permission.service.PermissionOuterService;

@Service
public class PermissionOuterServiceImpl  implements PermissionOuterService{
		
	//根据用户名查询权限列表 2020-05-28
	@TaskAnnotation("getUserOPList")
	@Override
	public List<UserOperationVO> getUserOPList(SessionFactory factory,TblTenantDTO tblTenantDTO) {			
		PermissionOuterMapper mapper=factory.getMapper(PermissionOuterMapper.class);
		List<UserOperationVO> userOPList=mapper.getUserOPList(tblTenantDTO);
		return userOPList;
	}
	   //根据用户信息，获取用户所有角色
		@TaskAnnotation("getUserRoleList")
		@Override
		public List<TblRoleVO> getUserRoleList(SessionFactory factory,TblTenantDTO tenantDTO) {
				PermissionOuterMapper mapper=factory.getMapper(PermissionOuterMapper.class);
				List<TblRoleVO> roleList=mapper.getUserRoleList(tenantDTO);
				return roleList;
		}

		//根据用户获取范围值
		@TaskAnnotation("getUserRangeValueList")
		@Override
		public List<TblRoleRangeValueVO> getUserRangeValueList(SessionFactory factory,TblTenantDTO tblTenantDTO) {	
				PermissionOuterMapper mapper=factory.getMapper(PermissionOuterMapper.class);
				List<TblRoleRangeValueVO> rangeValueList=mapper.getUserRangeValueList(tblTenantDTO);
				return rangeValueList;
		}

		//根据用户获取是否存在操作权限
		@TaskAnnotation("getOPList")
		@Override
		public List<TblOperationVO> getOPList(SessionFactory factory, TblOpDTO tblOpDTO) {			
				PermissionOuterMapper mapper=factory.getMapper(PermissionOuterMapper.class);
				List<TblOperationVO> opList=mapper.getOPList(tblOpDTO);
				return opList;
		}
}
