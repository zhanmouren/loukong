package com.koron.inwlms.mapper.master.sysManager;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.EnvSource;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.OrgAndDeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.SpecialDayDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.OrgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;

/*
 * date:2020-03-18
 * author:xiaozhan
 */  	
@Repository
@EnvSource("_default")
public interface UserMapper {
   //添加职员
	public Integer addUser(UserDTO userDTO);
	
	//查询职员
	public List<UserVO> queryUser(QueryUserDTO userDTO);
	
	//修改职员
    public Integer updateUser(UserDTO userDTO);
    
    //删除职员
    public Integer deleteUser(UserDTO userDTO);
    
    //新建新角色
    public Integer addNewRole(RoleDTO roleDTO);
  	 //修改角色属性
    public Integer updateRoleAttr(RoleDTO roleDTO);
    
    //删除角色
    public Integer  delRole(List<Integer> roleList);
    
    //查询该角色是否存在职员
    public List<RoleMsgVO>  queryRoleUser(RoleDTO roleDTO);
    
   //根据角色Id加载角色人员接口
  	public List<UserVO> queryUserByRoleId(RoleDTO roleDTO);
  	
  	//加载所有角色
	public List<RoleVO> queryAllRole();
	
	//插入角色与职员(批量)的关系
	public Integer addRoleUser(List<RoleAndUserDTO> roleAndUserDTOList);
	
	//删除角色中职员(批量)
	public Integer  deleteRoleUser(@Param("roleId") Integer roleId,@Param("list") List<Integer> userList);
	
	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员)
	public List<UserVO> queryExceptRoleUser(RoleAndUserDTO roleUserDTO);
	
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25	
	public List<UserVO> queryExceptDeptUser(DeptAndUserDTO deptUserDTO);
	
	//添加用户(批量)和部门关系的操作
	public Integer addDeptUser(List<DeptAndUserDTO> deptUserDTOList);
	
	//删除部门中职员(批量)
	public Integer deleteDeptUser(@Param("depId") Integer depId,@Param("list") List<Integer> userList);
	
	
	/**下面是系统配置***/
	//添加数据字典主表
		public Integer addDataDic(List<DataDicDTO> dataDicDTOList);
		
		//查询数据字典(查询明细信息键值)
		public List<DataDicVO> queryDataDic(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询是否已经存在值域)
		public List<DataDicVO> queryParentDic(DataDicDTO dataDicDTO);
		
		//查询数据字典(查询主信息键值)
		public List<DataDicVO> queryMainDataDic(DataDicDTO dataDicDTO);
		
		//修改数据字典主表(批量修改主表信息)
		public Integer updateDicById(DataDicDTO dataDicDTO);
		
		//删除数据字典主表(删除主表信息)
		public Integer deleteDicById(List<DataDicDTO> dataDicDTOList);
		
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
		List <OrgVO> queryOrgByCode(@Param("orgCode") String orgCode);
		
		//根据部门Code查询部门人员
		List<UserVO> queryDeptUser(DeptDTO deptDTO);

}
