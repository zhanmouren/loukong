package com.koron.inwlms.mapper.sysManager;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.EnvSource;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Repository;


import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.EnumMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.FieldMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.IntegrationConfDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.ModuleMenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.OrgAndDeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleMenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.SpecialDayDTO;
import com.koron.inwlms.bean.DTO.sysManager.TableMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.UpdateWordDTO;
import com.koron.inwlms.bean.DTO.sysManager.UploadFileNewDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserExcelDTO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.DeptAndUserIdVO;
import com.koron.inwlms.bean.VO.sysManager.DeptUserCodeVO;
import com.koron.inwlms.bean.VO.sysManager.DeptVO;
import com.koron.inwlms.bean.VO.sysManager.EnumMapperVO;
import com.koron.inwlms.bean.VO.sysManager.FieldMapperVO;
import com.koron.inwlms.bean.VO.sysManager.IntegrationConfVO;
import com.koron.inwlms.bean.VO.sysManager.ModuleMenuVO;
import com.koron.inwlms.bean.VO.sysManager.OrgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMenusVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleUserCodeVO;
import com.koron.inwlms.bean.VO.sysManager.RoleVO;
import com.koron.inwlms.bean.VO.sysManager.TableMapperVO;
import com.koron.inwlms.bean.VO.sysManager.TreeMenuVO;
import com.koron.inwlms.bean.VO.sysManager.UploadFileNewVO;
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
	
	//判断角色编码在职员表是否存在
	public List<RoleVO> queryExistRole(RoleAndUserDTO roleUserDTO);
	
	//先判断职员code和roleCode是否已经存在在用户角色关系表
	public List<RoleUserCodeVO> judgeExistCode(RoleAndUserDTO roleAndUserDTO);
	
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
	
	//查询deptCode是否存在
	public List<DeptVO> queryExistDept(DeptDTO deptDTO);
	
	//判断userCode和deptCode是否在部门人员关系表已经存在一条记录
	public List<DeptUserCodeVO>  judgeExistDeptUserCode(DeptAndUserDTO deptUserDTO);
	
	//删除部门中职员(批量)
	public Integer deleteDeptUser(@Param("depCode") String depCode,@Param("list") List<String> userCodeList);
	
	
	/**下面是系统配置***/
	 
	  
	
	   //添加数据字典主表
		public Integer addDataDic(List<DataDicDTO> dataDicDTOList);
		
		//根据Parent查询key(查询最大编号的key)
		public List<DataDicVO> queryMaxDataKey(DataDicDTO dataDicDTO);
		
		//添加数据字典主表信息
		public Integer addMainDataDic(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询明细信息键值)
		public List<DataDicVO> queryDataDic(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询是否已经存在值域)
		public List<DataDicVO> queryParentDic(DataDicDTO dataDicDTO);
		
		//根据parent查询数据字典主的详细信息
		public List<DataDicVO> queryDic(DataDicDTO dataDicDTO);
		
		//查询key是否重复
		public List<DataDicVO> queryKey(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询主信息键值)
		public List<DataDicVO> queryMainDataDic(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询主信息键值)获取总条数
		public int getDataDicCount(DataDicDTO dataDicDTO);
		
		//修改数据字典主表(批量修改主表信息)
		public Integer updateDic(DataDicDTO dataDicDTO);
		
		//删除数据字典主表(删除主表信息批量)
		public Integer deleteDicByParent(List<DataDicDTO> dataDicDTOList);
		
		//删除数据字典主表信息(一条)key,value为空的
		public Integer deleteOneDic(DataDicDTO dataDicDTO);
		
		//插入一条数据字典明细信息
		public Integer addOneDataDet(DataDicDTO dataDicDTO);
		
		//修改数据字典明细的操作(单条)
	    public Integer updateDicDetById(DataDicDTO dataDicDTO);
	    
	  //修改数据字典主表(批量修改明细信息)
	  	public Integer deleteDetDicByKey(List<DataDicDTO> dataDicDTOList);	
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
		
		//删除组织部门关系 根据外键Code和组织Code
		public Integer deleteOrgDept(OrgAndDeptDTO orgAndDeptDTO);
		
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
		
		
		 //添加集成配置主表
	    public Integer addIntegration(IntegrationConfDTO integrationConfDTO);
	    
	    //添加表格映射主表信息
	    public Integer addTableMapper(TableMapperDTO tableMapperDTO);
	    
	    //添加表格映射主表信息 2020/04/16
	    public Integer addFieldMapper(FieldMapperDTO fieldMapperDTO);
	    
	    //添加枚举值映射明细
	    public Integer addEnumMapper(EnumMapperDTO enumMapperDTO);
	    
	    //查询集成配置列表
	    public List<IntegrationConfVO> queryIntegration(IntegrationConfDTO integrationConfDTO);
	    
	    //查询集成配置列表总数
	    public Integer getIntegrationCount(IntegrationConfDTO integrationConfDTO);
	    
	   //根据code查询集成配置信息
	    public List<IntegrationConfVO> queryIntegrationByCode(IntegrationConfDTO integrationConfDTO);
	    
	    //根据配置主表Code查询表格映射明细列表
	    public List<TableMapperVO> queryTableMapper(TableMapperDTO tableMapperDTO);  
		
	    //查询表格映射列表明细条数
	    public Integer getTableMapperCount(TableMapperDTO tableMapperDTO);
	    
	    //根据配置主表Code查询枚举值映射明细列表
	    public List<EnumMapperVO> queryEnumMapper(EnumMapperDTO enumMapperDTO);
		
	    //查询枚举值映射明细列表条数
	    public Integer getEnumMapperCount(EnumMapperDTO enumMapperDTO);
	    
	    //根据表格Code查询表格字段映射明细列表
	    public List<FieldMapperVO> queryFieldMapper(FieldMapperDTO fieldMapperDTO);
		
	    //根据表格Code查询表格字段映射明细列表条数
	    public Integer getFieldMapperCount(FieldMapperDTO fieldMapperDTO);
	    
	    //修改集成配置信息接口
	    public Integer updateConf(IntegrationConfDTO integrationConfDTO);
	    
	    //根据Code修改集成配置信息接口
	    public Integer updateTableMapper(TableMapperDTO tableMapperDTO);
	        
	    //根据id修改表格映射明细信息
		public Integer updateEnumMapper(EnumMapperDTO enumMapperDTO);
		
		//根据Code修改表格字段映射明细
		public Integer updateFieldMapper(FieldMapperDTO fieldMapperDTO);
		
		//根据Code删除表格映射
		public Integer deleteTableMapper(List<String> codeList);
		
		//根据Code删除表格映射
		public Integer deleteFieldMapper(List<String> codeList);
		
		
		//查询明细字段code根据主表code
		public List<FieldMapperVO> queryFieldMapperCode(List<String> codeList);
		
		//根据id删除枚举值映射明细
		public Integer deleteEnumMapper(List<Integer> idList);
		
		//查询职员部门关系表的Id
		public List<DeptAndUserIdVO> queryUserDeptId(DeptAndUserDTO deptAndUserDTO);
		
		//修改职员部门
		public Integer updateUserDept(DeptAndUserDTO deptAndUserDTO);
		
		//批量导入用户
		public Integer addManyUser(List<UserExcelDTO> userList);
		
		//查询个人密码
		public List<UserVO> queryPassWord(UserDTO userDTO);
		
		//修改个人密码
		public Integer updateMyPassword(UserDTO userDTO);
		
		 //删除头像(修改状态) 2020/05/06
		public Integer deleteHeadPortrait(@Param("foreignkey") String foreignkey);
		
		//上传头像
		public Integer insertFileDataNew(UploadFileNewDTO uploadFileNewDTO);
		
		//查询头像
		public List<UploadFileNewVO> queryHeadPortrait(UploadFileNewDTO uploadFileNewDTO);
		
}
