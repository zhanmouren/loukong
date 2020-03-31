package com.koron.inwlms.service.sysManager;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.OrgAndDeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.SpecialDayDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.RoleAndUserVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;


public interface UserService {
	
	//管理员添加职员
	Integer addUser(SessionFactory factory, UserDTO userDTO);
	//查询职员（名称或部门）
	List<UserVO> queryUser(SessionFactory factory, QueryUserDTO userDTO);
	//修改职员
    Integer  updateUser(SessionFactory factory, UserDTO userDTO);
    //删除职员（不物理删除）
    Integer  deleteUser(SessionFactory factory, UserDTO userDTO);
    //新建新角色
	Integer addNewRole(SessionFactory factory, RoleDTO roleDTO);
	 //修改角色属性
	Integer updateRoleAttr(SessionFactory factory, RoleDTO roleDTO);
	 //修改角色属性(物理删除)
	RoleMsgVO deleteRoleAttr(SessionFactory factory, RoleDTO roleDTO);	
	//根据角色Id加载角色人员接口
	List<UserVO> queryUserByRoleId(SessionFactory factory, RoleDTO roleDTO);
	//查询所有角色接口以及相关职员(默认第一次进入角色的时候)
	RoleAndUserVO queryAllRoleUser(SessionFactory factory, RoleDTO roleDTO);
	//添加用户(批量)和角色关系的操作
	Integer addRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
	//删除角色中职员(批量)接口
	Integer deleteRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员)
	List<UserVO> queryExceptRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25			
    List<UserVO> queryExceptDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO);
	//添加用户(批量)和部门关系的操作
	Integer addDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO);
	//删除部门中职员(批量)接口
	Integer deleteDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO);
	
	
	/**下面是系统配置***/
	
	  //添加数据字典
		Integer addDataDic(SessionFactory factory, DataDicDTO dataDicDTO);
		//查询数据字典(查询明细信息键值)
		List<DataDicVO> queryDataDic(SessionFactory factory,DataDicDTO dataDicDTO);
		//查询数据字典(查询明细信息主)
	    List<DataDicVO> queryMainDataDic(SessionFactory factory,DataDicDTO dataDicDTO);
		
	    //修改数据字典(通过parent)
	    Integer updateDicById(SessionFactory factory, DataDicDTO dataDicDTO);
	    
	    //删除数据字典(通过parent)
	    Integer deleteDicById(SessionFactory factory, DataDicDTO dataDicDTO);
	    
	    //通过字典主表ID修改数据字典明细接口(明细信息))
	    Integer updateDicDetById(SessionFactory factory, DataDicDTO dataDicDTO);
	    
	    //删除数据字典(通过Id)
	    Integer deleteDetDicById(SessionFactory factory, DataDicDTO dataDicDTO);
	    
	   //特征日
	     
	    //新建特征日
	    Integer addSpecialDate(SessionFactory factory,SpecialDayDTO specialDayDTO);
	    //查询某年某月特征日接口
	    List<SpecialDayDTO> querySpecialDate(SessionFactory factory,SpecialDayDTO specialDayDTO);
	    //根据日期删除特征日
	    Integer deleteSpecialDate(SessionFactory factory,SpecialDayDTO specialDayDTO);
	    
	    //修改特征日操作
	    Integer updateSpecialDate(SessionFactory factory,SpecialDayDTO specialDayDTO);
	    
	    /** -----------树形组件------------------**/    
	    
	    //插入到部门表中和部门组织表中
	    String addTreeDept(SessionFactory factory,  OrgAndDeptDTO orgDeptDTO);
	    
	    //插入部门表
	    String deptAddTreeDept(SessionFactory factory,  OrgAndDeptDTO orgDeptDTO);
	
}
