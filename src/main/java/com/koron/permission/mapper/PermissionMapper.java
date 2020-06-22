package com.koron.permission.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Repository;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.inwlms.bean.DTO.sysManager.MenuTreeDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.VO.sysManager.RoleMenusVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.permission.bean.DTO.TblAppCatalogueDTO;
import com.koron.permission.bean.DTO.TblAppDTO;
import com.koron.permission.bean.DTO.TblAppOPDTO;
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
import com.koron.permission.bean.VO.TblAppOPVO;
import com.koron.permission.bean.VO.TblAppVO;
import com.koron.permission.bean.VO.TblMenusVO;
import com.koron.permission.bean.VO.TblOpCodeVO;
import com.koron.permission.bean.VO.TblRoleMenusVO;
import com.koron.permission.bean.VO.TblRoleVO;
import com.koron.permission.bean.VO.TblRoleZoneVO;



/*
 * date:2020/05/29
 * @author 小詹
 */
@Repository
public interface PermissionMapper {
	
	//添加应用信息
	public Integer addApp(TblAppDTO tblAppDTO);
	
	//修改应用信息
	public Integer updateApp(TblAppDTO tblAppDTO);
	
	//删除应用信息(改变状态)
	public Integer deleteApp(TblAppDTO tblAppDTO);
	
	//查询应用信息
	public List<TblAppVO> queryApp(TblAppDTO tblAppDTO);
	
    //生成操作子节点
    public Integer addOperate(TblOperationDTO tblOperationDTO);
    
    //批量删除操作节点
    public Integer deleteOperate(List<String> codeList);
    
    //批量删除操作节点树形关系
    public Integer deleteOperateTree(List<String> codeList);
    
    //修改操作节点信息
    public Integer updateOperate(TblOperationDTO tblOperationDTO);
    
    //根据操作opCode查询操作表信息是否存在
    public List<TblOpCodeVO> queryOpCode(@Param("opCode") String opCode);
    
    //查询应用-操作表中是否已经存在一条记录了
    public List<TblAppOPVO> queryAppOp(TblAppOPDTO tblAppOPDTO);
    
    //添加(一)应用-操作(多)关系
    public Integer addAppOP(List<TblAppOPDTO> appOpList);
    
    //删除(一)应用-操作(多)关系
    public Integer deleteAppOp(@Param("appCode") String appCode,@Param("list") List<String> opCodeList);
    
    //添加角色
    public Integer addRole(TblRoleDTO tblRoleDTO);
    
    //修改角色属性
    public Integer updateRole(TblRoleDTO tblRoleDTO);  
    
    //删除角色-用户中关于该角色
    public Integer deleteRoleUser(@Param("roleCode") String roleCode);  
    
    //删除角色-操作中关于该角色
    public Integer deleteRoleOp(@Param("roleCode") String roleCode);
    
    //删除角色-数据范围关于该角色
    public Integer deleteRoleRange(@Param("roleCode") String roleCode);
    
    //删除角色-组织中关于该角色的
    public Integer deleteRoleOrg(@Param("roleCode") String roleCode);
    
    //删除角色
    public Integer deleteRole(@Param("roleCode") String roleCode);
    
    //查询所有角色
    public List<TblRoleVO> queryAllRole(TblTenantDTO tblTenantDTO);
    
    //添加(一)角色-用户(多)关系
	public Integer addRoleUser(List<TblRoleUserDTO> roleUserList);
	
	//添加(一)角色-操作(多)关系
	public Integer addRoleOP(List<TblRoleOpDTO> roleOpList);
	
	//删除(一)角色-操作(多)关系
    public Integer deleteRoleOP(@Param("roleCode") String roleCode,@Param("list") List<String> opCodeList);
    
    //添加角色数据范围操作
    public Integer addRoleRangeValue(List<TblRoleRangeValueDTO> list);
    
    //添加域
    public Integer addAppCatalogue(TblAppCatalogueDTO tblAppCatalogueDTO);
    
    //修改域
    public Integer updateAppCatalogue(TblAppCatalogueDTO tblAppCatalogueDTO);
    
    //删除域
    public Integer deleteAppCatalogue(TblAppCatalogueDTO tblAppCatalogueDTO);
    
    //修改角色数据范围操作(修改值而已)
    public Integer updateRoleRangeValue(TblRoleRangeValueDTO tblRoleRangeValueDTO);
    
    //添加组织角色关系
    public Integer addRoleOrg(List<TblOrgRoleDTO> tblOrgRoleList);
    
    //删除组织角色关系
    public Integer deleteManyRoleOrg(List<TblOrgRoleDTO> tblOrgRoleList);
    
    //删除角色数据范围操作
    public Integer deleteRoleRangeValue(TblRoleRangeValueDTO tblRoleRangeValueDTO);
    
    //查询域
    public List<TblAppCatalogueVO> queryAppCatalogue(TblAppCatalogueDTO tblAppCatalogueDTO);
    
    /****系统内接口***/
    //根据角色Code加载角色人员接口
  	public List<UserVO> queryUserByRoleCode(RoleDTO roleDTO);
	
	//根据角色code查询人员总条数
  	public int getRoleUserCount(RoleDTO roleDTO);
  	
    //加载角色菜单按钮操作权限
    public List<TblRoleMenusVO> queryRoleMenuByRoleCode(TblRoleAndOPDTO tblRoleAndOPDTO);
    
    //查询用户角色
    public List<TblRoleVO> getRoleByUser(@Param("userCode") String userCode);
    
    //删除角色-用户
    public Integer deleteUserByRole(@Param("roleCode") String roleCode,@Param("list") List<String> userCodeList);
    
    //加载分区树以及打勾的权限
    public List<TblRoleZoneVO> queryRoleZone(TblRoleZoneDTO tblRoleZoneDTO);
}
