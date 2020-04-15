package com.koron.inwlms.mapper.sysManager;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.EnvSource;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Repository;

import com.koron.common.bean.LongTreeBean;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuTreeDTO;
import com.koron.inwlms.bean.DTO.sysManager.ModuleMenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.OrgAndDeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleMenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.SpecialDayDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.DeptVO;
import com.koron.inwlms.bean.VO.sysManager.ModuleMenuVO;
import com.koron.inwlms.bean.VO.sysManager.OrgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMenusVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleVO;
import com.koron.inwlms.bean.VO.sysManager.TreeMenuVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;

/*
 * date:2020-03-18
 * author:xiaozhan
 */  	
@Repository
public interface UserMapper {
   //添加职员
	 Integer addUser(UserDTO userDTO);
	
	//查询职员
	public List<UserVO> queryUser(QueryUserDTO userDTO);
	
	//查询职员条数
	public int getUserCount(QueryUserDTO userDTO);
	
	//修改职员
    public Integer updateUser(UserDTO userDTO);
    
    //批量重置职员密码
    public Integer updateUserPassword(UserDTO userDTO);
    
    //删除职员
    public Integer deleteUser(UserDTO userDTO);
    
    //新建新角色
    public Integer addNewRole(RoleDTO roleDTO);
    //查询角色名称是否存在
    public List<RoleVO> queryRoleByName(RoleDTO roleDTO);
  	 //修改角色属性
    public Integer updateRoleAttr(RoleDTO roleDTO);
    
    //删除角色
    public Integer  delRole(List<String> roleCodeList);
    
    //查询该角色是否存在职员
    public List<RoleMsgVO>  queryRoleUser(RoleDTO roleDTO);
    
   //根据角色Code加载角色人员接口
  	public List<UserVO> queryUserByRoleCode(RoleDTO roleDTO);
  	
  	//根据角色code查询人员总条数
  	public int getRoleUserCount(RoleDTO roleDTO);
  	
  	//加载所有角色
	public List<RoleVO> queryAllRole();
	
	//插入角色与职员(批量)的关系
	public Integer addRoleUser(List<RoleAndUserDTO> roleAndUserDTOList);
	
	//删除角色中职员(批量)
	public Integer  deleteRoleUser(@Param("roleCode") String roleCode,@Param("list") List<String> userCodeList);
	
	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员)
	public List<UserVO> queryExceptRoleUser(RoleAndUserDTO roleUserDTO);
	
	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员) 查询总条数
	public int getExceptRoleUserCount(RoleAndUserDTO roleUserDTO);
	
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25	
	public List<UserVO> queryExceptDeptUser(DeptAndUserDTO deptUserDTO);
	
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25	
	public int getExceptDeptUserCount(DeptAndUserDTO deptUserDTO);
	
	//添加用户(批量)和部门关系的操作
	public Integer addDeptUser(List<DeptAndUserDTO> deptUserDTOList);
	
	//删除部门中职员(批量)
	public Integer deleteDeptUser(@Param("depCode") String depCode,@Param("list") List<String> userCodeList);
	
	
	/**下面是系统配置***/
	   //添加数据字典主表
		public Integer addDataDic(List<DataDicDTO> dataDicDTOList);
		
		//添加数据字典主表信息
		public Integer addMainDataDic(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询明细信息键值)
		public List<DataDicVO> queryDataDic(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询是否已经存在值域)
		public List<DataDicVO> queryParentDic(DataDicDTO dataDicDTO);
		
		//根据parent查询数据字典主的详细信息
		public List<DataDicVO> queryDic(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询主信息键值)
		public List<DataDicVO> queryMainDataDic(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询主信息键值)获取总条数
		public int getDataDicCount();
		
		//修改数据字典主表(批量修改主表信息)
		public Integer updateDicById(DataDicDTO dataDicDTO);
		
		//删除数据字典主表(删除主表信息批量)
		public Integer deleteDicById(List<DataDicDTO> dataDicDTOList);
		
		//删除数据字典主表信息(一条)key,value为空的
		public Integer deleteOneDic(DataDicDTO dataDicDTO);
		
		//插入一条数据字典明细信息
		public Integer addOneDataDet(DataDicDTO dataDicDTO);
		
		//修改数据字典明细的操作(单条)
	    public Integer updateDicDetById(DataDicDTO dataDicDTO);
	    
	  //修改数据字典主表(批量修改明细信息)
	  	public Integer deleteDetDicById(List<DataDicDTO> dataDicDTOList);	
	//特征日
	  	//新建特征日
	  	public Integer addSpecialDate(SpecialDayDTO specialDayDTO);
	  	//判断是否存在相同的特征日
	  	public  List<SpecialDayDTO> queryExistSp(SpecialDayDTO specialDayDTO);
	  	
	  	//查询某年某月特征日 2020/03/27
		public  List<SpecialDayDTO> querySpecialDate(SpecialDayDTO specialDayDTO);
		
		//根据某日日期查询特征日信息
		public  List<SpecialDayDTO> querySpecialDateByDay(SpecialDayDTO specialDayDTO);
		
		//根据日期删除特征日
		public  Integer deleteSpecialDate(SpecialDayDTO specialDayDTO);
		
		//根据日期修改特征日
		public Integer updateSpecialDate(SpecialDayDTO specialDayDTO);
		
	 /** -----------树形组件------------------**/  
		  //插入到部门表中中
		public Integer addDeptNew(OrgAndDeptDTO orgDeptDTO);
		//插入组织部门表中
		public Integer addOrgDept(OrgAndDeptDTO orgDeptDTO);
		
		//插入到部门表中
		public Integer deptAddTreeDept(OrgAndDeptDTO orgDeptDTO);
		
		//删除树结构部门的时候，判断该节点下的是否存在职员,存在的情况下不能删除
		public List<UserVO> judgeExistUser(DeptAndUserDTO deptAndUserDTO);
		
		//物理删除部门，部门表 根据外键Code
		public Integer deleteTreeDept(DeptAndUserDTO deptAndUserDTO);
		
		//根据Id更新部门名称
		public Integer updateTreeDept(DeptDTO deptDTO);
		
		//根据Code查询组织
		public List <OrgVO> queryOrgByCode(@Param("orgCode") String orgCode);
		
		//根据部门Code查询部门人员
		public List<UserVO> queryDeptUser(DeptDTO deptDTO);
		
		//根据部门Code查询部门人员 查询总人数
		public int getDeptUserCount(DeptDTO deptDTO);
		
		//添加菜单
		public Integer addMenu(MenuDTO menuDTO);
		
		//加载角色菜单权限(查看权限)
		public List<RoleMenusVO> queryRoleMenuByRoleCode(RoleDTO roleDTO);
	    
	    //根据角色Code修改菜单权限
		public Integer updateRoleMenuByRoleCode(RoleMenuDTO roleMenuDTO);
	    
	    //批量删除SM_roleMenus的操作(根据roleCode 和 moduleCode)
		public Integer deleteManyOP(List<RoleMenuDTO> roleMenuList);
	    
	    //批量插入SM_roleMenus数据
		public Integer addManyRoleMenu(List<RoleMenuDTO> roleMenuList);
	    
	    //模糊查询部门接口
		public List<DeptVO> queryDept(DeptDTO deptDTO);
	    //查询部门接口 总数量
		public int getDeptCount(DeptDTO deptDTO);
	    
	   //通过模块菜单Code和角色加载该角色所有菜单以及可操作的权限
		public List<RoleMenusVO>  queryRoleMenuByRoleMenu(@Param("roleMenuDTO")  RoleMenuDTO roleMenuDTO,@Param("list") List<String> moduleList);
		
		//根据moduleName 查询moduleCode 2020/04/14
		public List<ModuleMenuVO> queryMenuOP(ModuleMenuDTO moduleMenuDTO);
		
		//通过模块菜单Code和角色code查询该模块菜单操作权限
		public List<RoleMenusVO> queryOPByCode(RoleMenuDTO roleMenuDTO);
		
		//批量重置密码
		public Integer updateUserPassword(List<UserDTO> userList);
		
}
