package com.koron.permission.service;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.sysManager.MenuTreeDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMenusVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.permission.bean.DTO.TblAppCatalogueDTO;
import com.koron.permission.bean.DTO.TblAppDTO;
import com.koron.permission.bean.DTO.TblAppOPDTO;
import com.koron.permission.bean.DTO.TblOpCodeListDTO;
import com.koron.permission.bean.DTO.TblOperationDTO;
import com.koron.permission.bean.DTO.TblOrgRoleDTO;
import com.koron.permission.bean.DTO.TblRoleAndOPDTO;
import com.koron.permission.bean.DTO.TblRoleDTO;
import com.koron.permission.bean.DTO.TblRoleOpDTO;
import com.koron.permission.bean.DTO.TblRoleRangeValueDTO;
import com.koron.permission.bean.DTO.TblRoleUserDTO;
import com.koron.permission.bean.DTO.TblRoleZoneDTO;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.TblAppCatalogueVO;
import com.koron.permission.bean.VO.TblAppVO;
import com.koron.permission.bean.VO.TblMenusVO;
import com.koron.permission.bean.VO.TblRoleMenusVO;
import com.koron.permission.bean.VO.TblRoleVO;
import com.koron.permission.bean.VO.TblRoleZoneVO;




public interface PermissionService {
	
	
	//添加应用信息
	public Integer addApp(SessionFactory factory,TblAppDTO tblAppDTO);
	
	//修改应用信息
	public Integer updateApp(SessionFactory factory,TblAppDTO tblAppDTO);
	
	//删除应用信息
	public Integer deleteApp(SessionFactory factory,TblAppDTO tblAppDTO);
	
	//查询应用信息
	public List<TblAppVO> queryApp(SessionFactory factory,TblAppDTO tblAppDTO);
	
	//生成操作子节点
    public Integer addOperate(SessionFactory factory,TblOperationDTO tblOperationDTO);
    
    //批量删除操作节点
    public Integer deleteOperate(SessionFactory factory,TblOpCodeListDTO tblOpCodeListDTO); 
    
    //修改操作节点信息
    public Integer updateOperate(SessionFactory factory,TblOperationDTO tblOperationDTO);
    
    //配置(一)应用-操作(多)之间的联系
    public Integer addAppOP(SessionFactory factory,TblAppOPDTO tblAppOPDTO);
    
    //删除(一)应用-操作(多)之间的联系
    public Integer deleteAppOp(SessionFactory factory,TblAppOPDTO tblAppOPDTO);
    
    //添加角色
    public Integer addRole(SessionFactory factory,TblRoleDTO tblRoleDTO);  
    
    //修改角色属性
    public Integer updateRole(SessionFactory factory,TblRoleDTO tblRoleDTO);  
    
    //删除角色通过code
    public Integer deleteRole(SessionFactory factory,TblRoleDTO tblRoleDTO);  
    
    //查询所有的角色
    public List<TblRoleVO> queryAllRole(SessionFactory factory,TblTenantDTO tblTenantDTO);
    
    //添加用户-角色关系
    public Integer addRoleUser(SessionFactory factory,TblRoleUserDTO tblRoleUserDTO);
    
    //添加角色-操作关系
    public Integer addRoleOP(SessionFactory factory,TblRoleOpDTO tblRoleOpDTO);
    
    //删除角色-操作关系
    public Integer deleteRoleOP(SessionFactory factory,TblRoleOpDTO tblRoleOpDTO);
    
    //修改角色-操作关系
    public Integer updateRoleOP(SessionFactory factory,TblRoleOpDTO tblRoleOpDTO);
    
    //添加角色数据范围操作
    public Integer addRoleRangeValue(SessionFactory factory,TblRoleRangeValueDTO tblRoleRangeValueDTO);
    
    //添加域
    public Integer addAppCatalogue(SessionFactory factory,TblAppCatalogueDTO tblAppCatalogueDTO);
    
    //修改域
    public Integer updateAppCatalogue(SessionFactory factory,TblAppCatalogueDTO tblAppCatalogueDTO);
    
    //删除域
    public Integer deleteAppCatalogue(SessionFactory factory,TblAppCatalogueDTO tblAppCatalogueDTO);
    
    //修改角色数据范围操作
    public Integer updateRoleRangeValue(SessionFactory factory,TblRoleRangeValueDTO tblRoleRangeValueDTO);
    
    //添加组织角色关系
    public Integer addRoleOrg(SessionFactory factory,TblOrgRoleDTO tblOrgRoleDTO);
    
    //删除(一)组织角色(多)关系
    public Integer deleteManyRoleOrg(SessionFactory factory,TblOrgRoleDTO tblOrgRoleDTO);
    
    //删除角色数据范围操作
    public Integer deleteRoleRangeValue(SessionFactory factory,TblRoleRangeValueDTO tblRoleRangeValueDTO);
    
    //查询域
    public List<TblAppCatalogueVO> queryAppCatalogue(SessionFactory factory,TblAppCatalogueDTO tblAppCatalogueDTO);
    
    /*******系统内接口*******/
    //根据角色查询用户
    public PageListVO<List<UserVO>> queryUserByRole(SessionFactory factory,RoleDTO roleDTO); 
    
    //加载菜单操作权限
    public List<TblRoleMenusVO> queryRoleMenuByRoleCode(SessionFactory factory,TblRoleAndOPDTO tblRoleAndOPDTO);
    
    //根据登录获取有关联(有权限)的操作，如果传父操作， 则只返回此操作下有权限的操作1.0。
    public List<TblRoleMenusVO> getUserMenuOPList(SessionFactory factory,TblRoleAndOPDTO tblRoleAndOPDTO);
    
    //删除角色-用户关系
    public Integer  deleteUserByRole(SessionFactory factory,TblRoleUserDTO tblRoleUserDTO);
    
    //加载分区树(打勾权限)
    public List<TblRoleZoneVO> queryRoleZone(SessionFactory factory,TblRoleZoneDTO tblRoleZoneDTO);
    
    
}
