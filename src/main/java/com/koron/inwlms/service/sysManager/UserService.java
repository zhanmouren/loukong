package com.koron.inwlms.service.sysManager;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.web.multipart.MultipartFile;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.EnumMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.FieldMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.IntegrationConfDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuSeqDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuTreeDTO;
import com.koron.inwlms.bean.DTO.sysManager.ModuleMenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.OrgAndDeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.PositionDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleMenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.SpecialDayDTO;
import com.koron.inwlms.bean.DTO.sysManager.TableMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.UpdateWordDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserExcelDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.UploadFileVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.DeptVO;
import com.koron.inwlms.bean.VO.sysManager.EnumMapperVO;
import com.koron.inwlms.bean.VO.sysManager.FieldMapperVO;
import com.koron.inwlms.bean.VO.sysManager.ImportUserResVO;
import com.koron.inwlms.bean.VO.sysManager.IntegrationConfVO;
import com.koron.inwlms.bean.VO.sysManager.ModuleMenuVO;
import com.koron.inwlms.bean.VO.sysManager.PositionVO;
import com.koron.inwlms.bean.VO.sysManager.RoleAndUserVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMenusVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleVO;
import com.koron.inwlms.bean.VO.sysManager.TableMapperVO;
import com.koron.inwlms.bean.VO.sysManager.TreeMenuVO;
import com.koron.inwlms.bean.VO.sysManager.UploadFileNewVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;


public interface UserService {
	
	//管理员添加职员
	Integer addUser(SessionFactory factory, UserDTO userDTO);
	//查询职员（名称或部门）
	PageListVO<List<UserVO>> queryUser(SessionFactory factory, QueryUserDTO userDTO);
	//查询职员详情
	List<UserVO> queryUserDetail(SessionFactory factory,QueryUserDTO userDTO);
	//修改职员
    Integer  updateUser(SessionFactory factory, UserDTO userDTO);
    //批量重置职员密码
    Integer updateUserPassword(SessionFactory factory, UserDTO userDTO);
    //删除职员（不物理删除）
    Integer  deleteUser(SessionFactory factory, UserDTO userDTO);
    //新建新角色
	Integer addNewRole(SessionFactory factory, RoleDTO roleDTO);
	 //修改角色属性
	Integer updateRoleAttr(SessionFactory factory, RoleDTO roleDTO);
	 //修改角色属性(物理删除)
	RoleMsgVO deleteRoleAttr(SessionFactory factory, RoleDTO roleDTO);	
	//根据角色Code加载角色人员接口
	PageListVO<List<UserVO>> queryUserByRoleCode(SessionFactory factory, RoleDTO roleDTO);
	//查询所有角色接口
	 List<RoleVO> queryAllRole(SessionFactory factory);
	//添加用户(批量)和角色关系的操作
	Integer addRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
	//删除角色中职员(批量)接口
	Integer deleteRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员)
	PageListVO<List<UserVO>> queryExceptRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25			
	PageListVO<List<UserVO>> queryExceptDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO);
	//添加用户(批量)和部门关系的操作
	Integer addDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO);
	//删除部门中职员(批量)接口
	Integer deleteDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO);
	
	
	/**下面是系统配置***/
	 
	
	  //添加数据字典
		Integer addDataDic(SessionFactory factory, DataDicDTO dataDicDTO);
		//生成数据字典key
		String createDataKey(SessionFactory factory, DataDicDTO dataDicDTO);
		//添加数据字典主表信息
		Integer addMainDataDic(SessionFactory factory, DataDicDTO dataDicDTO);
		//添加数据字典明细信息
		Integer addDetDataDic(SessionFactory factory, DataDicDTO dataDicDTO);
		
		//查询数据字典(查询明细信息键值)
		List<DataDicVO> queryDataDic(SessionFactory factory,DataDicDTO dataDicDTO);
		//查询数据字典(查询明细信息主)
		PageListVO<List<DataDicVO>> queryMainDataDic(SessionFactory factory,DataDicDTO dataDicDTO);
		
	    //修改数据字典(通过parent)
	    Integer updateDic(SessionFactory factory, DataDicDTO dataDicDTO);
	    
	    //删除数据字典(通过parent)
	    Integer deleteDicByParent(SessionFactory factory, DataDicDTO dataDicDTO);
	    
	    //通过字典主表ID修改数据字典明细接口(明细信息))
	    Integer updateDicDetById(SessionFactory factory, DataDicDTO dataDicDTO);
	    
	    //删除数据字典(通过Id)
	    Integer deleteDetDicByKey(SessionFactory factory, DataDicDTO dataDicDTO);
	    
	   //特征日
	     
	    //新建特征日
	    Integer addSpecialDate(SessionFactory factory,SpecialDayDTO specialDayDTO);
	    //查询某年某月特征日接口
	    List<SpecialDayDTO> querySpecialDate(SessionFactory factory,SpecialDayDTO specialDayDTO);
	    //根据日期查询某日特征日
	    List<SpecialDayDTO> querySpecialDateByDay(SessionFactory factory,SpecialDayDTO specialDayDTO);	    
	    //根据日期删除特征日
	    Integer deleteSpecialDate(SessionFactory factory,SpecialDayDTO specialDayDTO);
	    
	    //修改特征日操作
	    Integer updateSpecialDate(SessionFactory factory,SpecialDayDTO specialDayDTO);
	    
	    /** -----------树形组件------------------**/    
	    
	    //插入到部门表中和部门组织表中
	    Integer addTreeDept(SessionFactory factory,  OrgAndDeptDTO orgDeptDTO,int type,String foreignKey);
	    
	    //插入部门表
	    Integer deptAddTreeDept(SessionFactory factory,  OrgAndDeptDTO orgDeptDTO,int type,String foreignKey);
	    
	    //删除树结构部门的时候，判断该节点下的是否存在职员,存在的情况下不能删除
	    Integer judgeExistUser(SessionFactory factory, DeptAndUserDTO deptAndUserDTO,int type,String foreignkey, boolean force,int deleteType);
	    
	    //物理删除部门，部门表
	    Integer deleteTreeDept(SessionFactory factory,DeptAndUserDTO deptAndUserDTO);
	
	   //修改部门名称，通过部门ID
	    Integer updateTreeDept(SessionFactory factory,DeptDTO deptDTO);
	    
	    //根据部门Code查询部门职员
	    PageListVO<List<UserVO>> queryDeptUser(SessionFactory factory,DeptDTO deptDTO);
	    
	    
	    //生成菜单
	     Integer addMenu(SessionFactory factory,MenuTreeDTO menuTreeDTO);
	     
	     //加载角色菜单权限
	     List<RoleMenusVO> queryRoleMenuByRoleCode(SessionFactory factory,RoleDTO roleDTO);
	     
	     //根据角色Code修改菜单权限
	     Integer updateRoleMenuByRoleCode(SessionFactory factory,RoleMenuDTO roleMenuDTO);
	     
        //模糊查询部门接口
	     List<DeptVO> queryDept(SessionFactory factory,DeptDTO deptDTO);
	     
	     
	    //查询职位接口
	     List<PositionVO> queryPosition(SessionFactory factory,PositionDTO positionDTO);
		 
		 //通过模块菜单Code和角色加载该角色所有选中菜单以及可操作的权限。
		 List<RoleMenusVO> queryRoleMenuByRoleMenu(SessionFactory factory,RoleMenuDTO roleMenuDTO);	
		 
		 //根据moduleName 查询moduleCode
		 List<ModuleMenuVO> queryMenuOP(SessionFactory factory,ModuleMenuDTO moduleMenuDTO);
		 
		 //通过模块菜单Code和角色code查询该模块菜单操作权限
		 List<RoleMenusVO>  queryOPByCode(SessionFactory factory,RoleMenuDTO roleMenuDTO);
		 
		 //添加集成配置主表信息
		  Integer addIntegration(SessionFactory factory,IntegrationConfDTO integrationConfDTO);
		  
		 //添加表格映射主表信息
		  Integer addTableMapper(SessionFactory factory,TableMapperDTO tableMapperDTO);
		  
		  //添加表格字段映射
		  Integer addFieldMapper(SessionFactory factory,FieldMapperDTO fieldMapperDTO);
		  
		  //添加枚举值映射明细
		  Integer addEnumMapper(SessionFactory factory,EnumMapperDTO enumMapperDTO); 
		  
		  //查询集成配置列表信息接口
		  PageListVO<List<IntegrationConfVO>> queryIntegration(SessionFactory factory,IntegrationConfDTO integrationConfDTO);
		  
		  //根据code查询集成配置信息接口
		  List<IntegrationConfVO> queryIntegrationByCode(SessionFactory factory,IntegrationConfDTO integrationConfDTO);
		  		 
		  //查询表格映射列表明细
		  PageListVO<List<TableMapperVO>> queryTableMapper(SessionFactory factory,TableMapperDTO tableMapperDTO);
		  
		  //根据配置主表Code查询枚举值映射明细列表
		  PageListVO<List<EnumMapperVO>> queryEnumMapper(SessionFactory factory,EnumMapperDTO enumMapperDTO);
		  
		  //根据表格Code查询表格字段映射明细列表
		  PageListVO<List<FieldMapperVO>> queryFieldMapper(SessionFactory factory,FieldMapperDTO fieldMapperDTO);
		  
		  //修改集成配置信息接口
		  Integer updateConf(SessionFactory factory,IntegrationConfDTO integrationConfDTO);
		  
		  //根据code修改表格映射明细信息
		  Integer updateTableMapper(SessionFactory factory,TableMapperDTO tableMapperDTO);
		  
		  //根据id修改表格映射明细信息
		  Integer updateEnumMapper(SessionFactory factory,EnumMapperDTO enumMapperDTO);
		  
		  //根据Code修改表格字段映射明细
		  Integer updateFieldMapper(SessionFactory factory,FieldMapperDTO fieldMapperDTO);
		  
		  //根据Code删除表格映射
		  Integer deleteTableMapper(SessionFactory factory,TableMapperDTO tableMapperDTO);
		  
		  //根据Code删除表格字段映射
		  Integer deleteFieldMapper(SessionFactory factory,FieldMapperDTO fieldMapperDTO);
		  
		  //根据id删除枚举值映射明细
		  Integer deleteEnumMapper(SessionFactory factory,EnumMapperDTO enumMapperDTO);
		  
		 //添加excel时候导入用户数据 2020/04/22
		  ImportUserResVO addImportUserDataExcel(SessionFactory factory,List<UserExcelDTO> userList);
		  
		  //修改个人密码
		  Integer updateMyPassword(SessionFactory factory,UpdateWordDTO updateWordDTO);
		  
		  //上传头像2020/04/30
		  Integer uploadHeadPortrait(SessionFactory factory,MultipartFile file);
		  
		  //根据登录名查询头像信息
		  List<UploadFileNewVO> queryHeadPortrait(SessionFactory factory);
		  	 
		  
		 
}
