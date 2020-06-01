package com.koron.permission.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koron.permission.bean.DTO.TblRoleDTO;
import com.koron.permission.bean.VO.userOperationVO;

/*
 * date:2020/05/29
 * @author 小詹
 */
@Repository
public interface PermissionMapper {
	
	//根据登录的用户查询该角色的权限
	public List<userOperationVO> getUserOPList(TblRoleDTO tblRoleDTO);

}
