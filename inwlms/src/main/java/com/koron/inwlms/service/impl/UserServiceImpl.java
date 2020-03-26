package com.koron.inwlms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDetDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.RoleAndUserVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.mapper.master.UserMapper;
import com.koron.inwlms.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	//管理员添加职员 2020/03/18	
	@TaskAnnotation("addUser")
	@Override
	public Integer addUser(SessionFactory factory,UserDTO userDTO) {
		// TODO Auto-generated method stub	
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		userDTO.setCreateBy("xiaozhan");
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		userDTO.setCreateTime(timeNow);
		userDTO.setUpdateBy("xiaozhan");
		userDTO.setUpdateTime(timeNow);
		Integer addResult=userMapper.addUser(userDTO);
		return addResult;
	}
	
	//查询职员 2020/03/19	
	@TaskAnnotation("queryUser")
	@Override
	public List<UserVO> queryUser(SessionFactory factory,QueryUserDTO userDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<UserVO> userList=userMapper.queryUser(userDTO);
		return userList;
	}
	//修改职员 2020/03/20
	@TaskAnnotation("editUser")
	@Override
	public Integer editUser(SessionFactory factory, UserDTO userDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		userDTO.setUpdateBy("xiaozhan");
		userDTO.setUpdateTime(timeNow);
		Integer editResult=userMapper.editUser(userDTO);
		return editResult;
	}
   
	//删除职员 2020/03/23
	@TaskAnnotation("delUser")
	@Override
	public Integer delUser(SessionFactory factory, UserDTO userDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		userDTO.setWhetUse(-1);
		Integer delResult=userMapper.delUser(userDTO);
		return delResult;
	}
	
	//添加新角色  2020/03/23
	@TaskAnnotation("addNewRole")
	@Override
	public Integer addNewRole(SessionFactory factory,RoleDTO roleDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		roleDTO.setCreateBy("xiaozhan");
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		roleDTO.setCreateTime(timeNow);
		roleDTO.setUpdateBy("xiaozhan");
		roleDTO.setUpdateTime(timeNow);
		Integer addResult=userMapper.addNewRole(roleDTO);
		return addResult;
	}
    
	//修改角色属性  2020/03/23
	@TaskAnnotation("editRoleAttr")
	@Override
	public Integer editRoleAttr(SessionFactory factory, RoleDTO roleDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		roleDTO.setUpdateBy("xiaozhan");
		roleDTO.setUpdateTime(timeNow);
		Integer editResult=userMapper.editRoleAttr(roleDTO);
		return editResult;
	}
	
	//删除角色属性  2020/03/23(删除角色前先判断有没有绑定职员)
	@TaskAnnotation("delRoleAttr")
	@Override
	public RoleMsgVO delRoleAttr(SessionFactory factory, RoleDTO roleDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		Integer delResult;
		RoleMsgVO roleMsgVO=new RoleMsgVO();
		//根据角色查询是否该角色绑定职员
		for(Integer roleId:roleDTO.getRoleIdList()) {
	      RoleDTO roleDTONew=new RoleDTO();
	      roleDTONew.setRoleId(roleId);
		  List<RoleMsgVO> userList=userMapper.queryRoleUser(roleDTONew);
		  //说明这条角色RoleId存在用户
		  if(userList.size()>0) {
			  roleMsgVO.setResult(-1);
			  roleMsgVO.setMessage("角色为："+userList.get(0).getRoleName()+"下存在职员");
			  return roleMsgVO;
		  }
		}
		//执行批量删除角色的操作		
		  delResult=userMapper.delRole(roleDTO.getRoleIdList());
		  roleMsgVO.setMessage("批量删除成功");
		  roleMsgVO.setResult(delResult);
		  return roleMsgVO;
		  
	}

	//根据角色Id加载角色人员接口 2020/03/24
	@TaskAnnotation("queryUserByRoleId")
	@Override
	public List<UserVO> queryUserByRoleId(SessionFactory factory, RoleDTO roleDTO) {	
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<UserVO> userList=userMapper.queryUserByRoleId(roleDTO);
		return userList;
	}

	//根据角色Id加载角色人员接口 2020/03/24
	@TaskAnnotation("queryAllRoleUser")
	@Override
	public RoleAndUserVO queryAllRoleUser(SessionFactory factory, RoleDTO roleDTO) {
		// TODO Auto-generated method stub
		//先查询所有的角色
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<RoleVO> roleList=userMapper.queryAllRole();
		//查询第一个角色的角色人员接口
		if(roleDTO.getRoleId()==null) {
			//测试用，先默认为3
		   roleDTO.setRoleId(3);
		}
		List<UserVO> userList=userMapper.queryUserByRoleId(roleDTO);
		//拼接在一个List中
		RoleAndUserVO roleAndUser=new RoleAndUserVO();
		roleAndUser.setRoleList(roleList);
		roleAndUser.setUserList(userList);		
		return roleAndUser;
	}

	//遍历插入职员和角色的关系 2020/03/24
	@TaskAnnotation("addRoleUser")
	@Override
	public Integer addRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		//把数据封装成List<RoleAndUserDTO>,方便遍历插入数据
		List<RoleAndUserDTO> roleAndUserDTOList=new ArrayList<RoleAndUserDTO>();
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		for(int i=0;i<roleUserDTO.getUserList().size();i++) {
			RoleAndUserDTO roleUserDTONew=new RoleAndUserDTO();
			roleUserDTONew.setRoleId(roleUserDTO.getRoleId());
			roleUserDTONew.setUserId(roleUserDTO.getUserList().get(i));
			roleUserDTONew.setCreateTime(timeNow);
			roleUserDTONew.setUpdateTime(timeNow);
			roleUserDTONew.setCreateBy("小詹");
			roleUserDTONew.setUpdateBy("小詹");
			roleAndUserDTOList.add(roleUserDTONew);
		}
		Integer addResult=userMapper.addRoleUser(roleAndUserDTOList);
		return addResult;
	}

	//删除角色中职员(批量)接口 2020/03/24
	@TaskAnnotation("delRoleUser")
	@Override
	public Integer delRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		//执行批量删除角色职员的操作
		Integer delResult=userMapper.delRoleUser(roleUserDTO.getRoleId(),roleUserDTO.getUserList());
		return delResult;
	}

	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员) 2020/03/24
	@TaskAnnotation("queryExceptRoleUser")
	@Override
	public List<UserVO> queryExceptRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<UserVO> userList=userMapper.queryExceptRoleUser(roleUserDTO);
		return userList;
	}
	
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25
		@TaskAnnotation("queryExceptDeptUser")
		@Override
		public List<UserVO> queryExceptDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO) {
			// TODO Auto-generated method stub
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			List<UserVO> userList=userMapper.queryExceptDeptUser(deptUserDTO);
			return userList;
		}

		//添加用户(批量)和部门关系的操作 2020/03/25
		@TaskAnnotation("addDeptUser")
		@Override
		public Integer addDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO) {
			// TODO Auto-generated method stub
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			//把数据封装成List<RoleAndUserDTO>,方便遍历插入数据
			List<DeptAndUserDTO> deptUserDTOList=new ArrayList<DeptAndUserDTO>();
			Timestamp timeNow = new Timestamp(System.currentTimeMillis());
			for(int i=0;i<deptUserDTO.getUserList().size();i++) {
				DeptAndUserDTO deptUserDTONew=new DeptAndUserDTO();
				deptUserDTONew.setDepId(deptUserDTO.getDepId());
				deptUserDTONew.setUserId(deptUserDTO.getUserList().get(i));
				deptUserDTONew.setCreateTime(timeNow);
				deptUserDTONew.setCreateBy("小詹");
				deptUserDTOList.add(deptUserDTONew);
			}
			Integer addResult=userMapper.addDeptUser(deptUserDTOList);
			return addResult;
		}

		//删除部门中职员(批量)接口 2020/03/25
		@TaskAnnotation("delDeptUser")
		@Override
		public Integer delDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO) {
			// TODO Auto-generated method stub
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			//执行批量删除角色职员的操作
			Integer delResult=userMapper.delDeptUser(deptUserDTO.getDepId(),deptUserDTO.getUserList());
			return delResult;
		}

		//添加数据字典接口 2020/03/25
		@TaskAnnotation("addDataDic")
		@Override
		public Integer addDataDic(SessionFactory factory, DataDicDTO dataDicDTO) {
			// TODO Auto-generated method stub
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			Timestamp timeNow = new Timestamp(System.currentTimeMillis());
			dataDicDTO.setCreateBy("小詹");
			dataDicDTO.setCreateTime(timeNow);
			dataDicDTO.setUpdateBy("小詹");
			dataDicDTO.setUpdateTime(timeNow);
			//执行添加数据字典主表的操作(获取刚刚插入到数据字典主表的Id)
			Integer dictId=userMapper.addDataDic(dataDicDTO);
			Integer addDetCount;
			List<DataDicDetDTO> dataDicDetDTOList=new ArrayList<DataDicDetDTO>();
			//执行添加数据字典明细表的操作（多条记录）
			for(int i=0;i<dataDicDTO.getDictionaryDetList().size();i++) {
				DataDicDetDTO dataDicDetDTO=new DataDicDetDTO();
				dataDicDetDTO.setDictId(dictId);
				dataDicDetDTO.setDicDetName(dataDicDTO.getDictionaryDetList().get(i).getDicDetName());
				dataDicDetDTO.setDicDetValue(dataDicDTO.getDictionaryDetList().get(i).getDicDetValue());
				dataDicDetDTO.setCreateBy("小詹");
				dataDicDetDTO.setCreateTime(timeNow);
				dataDicDetDTO.setUpdateBy("小詹");
				dataDicDetDTO.setUpdateTime(timeNow);
				dataDicDetDTOList.add(dataDicDetDTO);
			}
			addDetCount=userMapper.addDataDetDic(dataDicDetDTOList);
			return addDetCount;
		}

		//查询数据字典接口(这个是列转行，展示所有的数据字典) 2020/03/25
		@TaskAnnotation("queryDataDic")
		@Override
		public List<DataDicVO> queryDataDic(SessionFactory factory, DataDicDTO dataDicDTO) {
			// TODO Auto-generated method stub
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			List<DataDicVO> dicList=userMapper.queryDataDic(dataDicDTO);
			return dicList;
		}

		//查询数据字典接口通过Id(通过Id查询详情) 2020/03/25
		@TaskAnnotation("queryDicById")
		@Override
		public List<DataDicVO> queryDicById(SessionFactory factory, DataDicDTO dataDicDTO) {
			// TODO Auto-generated method stub
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			List<DataDicVO> dicList=userMapper.queryDicById(dataDicDTO);
			return dicList;
		}
}