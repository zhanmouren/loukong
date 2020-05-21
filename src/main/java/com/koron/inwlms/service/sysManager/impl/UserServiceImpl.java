package com.koron.inwlms.service.sysManager.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.koron.ebs.mybatis.ADOConnection;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.mapper.TreeMapper;
import com.koron.common.web.service.TreeService;
import com.koron.inwlms.bean.DTO.common.FileConfigInfo;
import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.EnumMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.FieldMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.IntegrationConfDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuTreeDTO;
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
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.common.UploadFileVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.DeptAndUserVO;
import com.koron.inwlms.bean.VO.sysManager.DeptUserCodeVO;
import com.koron.inwlms.bean.VO.sysManager.DeptVO;
import com.koron.inwlms.bean.VO.sysManager.EnumMapperVO;
import com.koron.inwlms.bean.VO.sysManager.FieldMapperVO;
import com.koron.inwlms.bean.VO.sysManager.ImportUserResVO;
import com.koron.inwlms.bean.VO.sysManager.IntegrationConfVO;
import com.koron.inwlms.bean.VO.sysManager.ModuleMenuVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMenusVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleUserCodeVO;
import com.koron.inwlms.bean.VO.sysManager.RoleVO;
import com.koron.inwlms.bean.VO.sysManager.TableMapperVO;
import com.koron.inwlms.bean.VO.sysManager.UploadFileNewVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.mapper.sysManager.UserMapper;
import com.koron.inwlms.service.common.impl.FileServiceImpl;
import com.koron.inwlms.service.sysManager.UserService;
import com.koron.inwlms.util.EncryptionUtil;
import com.koron.inwlms.util.PageUtil;
import com.koron.inwlms.util.RandomCodeUtil;

@Service
public class UserServiceImpl implements UserService{
	
	private Integer mainDeptFlag=0;
	private Integer secDeptFlag=-1;
	
	@Autowired
    private FileConfigInfo fileConfigInfo;
	

	//管理员添加职员 2020/03/18	
	@TaskAnnotation("addUser")
	@Override
	public Integer addUser(SessionFactory factory,UserDTO userDTO) {
		// TODO Auto-generated method stub	
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		userDTO.setCreateBy("小詹");
		userDTO.setUpdateBy("小詹");
		//管理员设置默认密码yhsw123456
		userDTO.setPassword((new  EncryptionUtil().encodeBase64("yhsw123456")));
		//随机获取uuid,赋值给Code
		String userCode=new RandomCodeUtil().getUUID32();
		userDTO.setCode(userCode);	
		//添加用户的时候判断工号和登录名称是否存在
		QueryUserDTO queryUserDTO=new QueryUserDTO();
		queryUserDTO.setLoginName(userDTO.getLoginName());		
		List<UserVO> loginNameList=userMapper.queryUser(queryUserDTO);
		Integer addResult=null;
		if(loginNameList.size()>0) {
			addResult=-2;
			return addResult;
		}
		QueryUserDTO queryUserDTONew=new QueryUserDTO();
		queryUserDTONew.setWorkNo(userDTO.getWorkNo());
		List<UserVO> workNoList=userMapper.queryUser(queryUserDTONew);
		if(workNoList.size()>0) {
			addResult=-3;
			return addResult;
		}
	     addResult=userMapper.addUser(userDTO);
	    //获取usercode
	    if(addResult==1 && userDTO.getDepCode()!=null && !"".equals(userDTO.getDepCode())) {
	    	 //准备数据
		    DeptAndUserDTO deptAndUserDTO=new DeptAndUserDTO();
		    deptAndUserDTO.setDepCode(userDTO.getDepCode());
		    deptAndUserDTO.setUserCode(userCode);
		    deptAndUserDTO.setCreateBy("小詹");
		    deptAndUserDTO.setUpdateBy("小詹");
		    deptAndUserDTO.setMainDeptFlag(mainDeptFlag);
		    List<DeptAndUserDTO> deptUserDTOList=new ArrayList<DeptAndUserDTO>();
		    deptUserDTOList.add(deptAndUserDTO);
		    //添加用户和部门关系的操作
		    addResult=userMapper.addDeptUser(deptUserDTOList);
	    }    
		return addResult;
	}
	
	//查询职员 2020/03/19	
	@TaskAnnotation("queryUser")
	@Override
	public PageListVO<List<UserVO>>  queryUser(SessionFactory factory,QueryUserDTO userDTO) {		
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		//0代表在职
		userDTO.setWhetUse(0);
		List<UserVO> userList=userMapper.queryUser(userDTO);	
		int rowNumber = userMapper.getUserCount(userDTO);
		// 返回数据结果
		PageListVO<List<UserVO>> result = new PageListVO<>();
		result.setDataList(userList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(userDTO.getPage(), userDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}
	//修改职员 2020/03/20
	@TaskAnnotation("updateUser")
	@Override
	public Integer updateUser(SessionFactory factory, UserDTO userDTO) {
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		userDTO.setUpdateBy("小詹");
		Integer editResult=userMapper.updateUser(userDTO);
	    List<DeptAndUserDTO> deptUserDTOList=new ArrayList<DeptAndUserDTO>();
		
	  if(editResult==1 && userDTO.getDepCode()!=null && !"".equals(userDTO.getDepCode())) {
		 //准备数据
	    DeptAndUserDTO deptAndUserDTO=new DeptAndUserDTO();
	    deptAndUserDTO.setDepCode(userDTO.getDepCode());
	    deptAndUserDTO.setUserCode(userDTO.getCode());
	    deptAndUserDTO.setCreateBy("小詹");
	    deptAndUserDTO.setUpdateBy("小詹");
	    deptAndUserDTO.setMainDeptFlag(mainDeptFlag);  
	    deptUserDTOList.add(deptAndUserDTO);
	    //修改前先查询是否存在主部门，没有的话先插入	
		List<DeptAndUserVO> deptUserList=userMapper.queryMainDept(userDTO);
		if(deptUserList!=null && deptUserList.size()>0) {
			 //根据职员修改中间表MainDeptFlag为1的操作
		     UserDTO user=new UserDTO();
		     user.setCode(userDTO.getCode()); 
			 userMapper.updateUserDeptFlag(user);
			 //修改用户和部门关系的操作
			editResult=userMapper.editDeptUser(deptUserDTOList);
		}
		else {	 			  
		     //添加用户和部门关系的操作
	        editResult=userMapper.addDeptUser(deptUserDTOList);		   
		}
	  }
		return editResult;
	}
   
	//删除职员 2020/03/23
	@TaskAnnotation("deleteUser")
	@Override
	public Integer deleteUser(SessionFactory factory, UserDTO userDTO) {		
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		userDTO.setWhetUse(-1);
		Integer delResult=userMapper.deleteUser(userDTO);
		return delResult;
	}
	
	//添加新角色  2020/03/23
	@TaskAnnotation("addNewRole")
	@Override
	public Integer addNewRole(SessionFactory factory,RoleDTO roleDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		roleDTO.setCreateBy("xiaozhan");
		roleDTO.setUpdateBy("xiaozhan");
		//随机获取uuid,赋值给Code
		roleDTO.setRoleCode(new RandomCodeUtil().getUUID32());	
		//添加新角色的时候，判断名称是否重复
		RoleDTO roleDTONew=new RoleDTO();
		roleDTONew.setRoleName(roleDTO.getRoleName());
		List<RoleVO> roleVOList=userMapper.queryRoleByName(roleDTONew);
		Integer addResult=null;
		if(roleVOList.size()>0) {
			addResult=-2;
			return addResult;
		}
	    addResult=userMapper.addNewRole(roleDTO);
		return addResult;
	}
    
	//修改角色属性  2020/03/23
	@TaskAnnotation("updateRoleAttr")
	@Override
	public Integer updateRoleAttr(SessionFactory factory, RoleDTO roleDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);		
		roleDTO.setUpdateBy("xiaozhan");
		Integer editResult=userMapper.updateRoleAttr(roleDTO);
		return editResult;
	}
	
	//删除角色属性  2020/03/23(删除角色前先判断有没有绑定职员)
	@TaskAnnotation("deleteRoleAttr")
	@Override
	public RoleMsgVO deleteRoleAttr(SessionFactory factory, RoleDTO roleDTO) {		
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		Integer delResult;
		RoleMsgVO roleMsgVO=new RoleMsgVO();
		//根据角色查询是否该角色绑定职员
		for(String roleCode:roleDTO.getRoleCodeList()) {
	      RoleDTO roleDTONew=new RoleDTO();
	      roleDTONew.setRoleCode(roleCode);
		  List<RoleMsgVO> userList=userMapper.queryRoleUser(roleDTONew);
		  //说明这条角色RoleId存在用户
		  if(userList.size()>0) {
			  roleMsgVO.setResult(-1);
			  roleMsgVO.setMessage("角色为："+userList.get(0).getRoleName()+"下存在职员,请先删除关系后，继续操作");
			  return roleMsgVO;
		  }
		}
		//执行批量删除角色的操作		
		  delResult=userMapper.delRole(roleDTO.getRoleCodeList());
		  roleMsgVO.setMessage("批量删除成功");
		  roleMsgVO.setResult(delResult);
		  return roleMsgVO;
		  
	}

	//根据角色Code加载角色人员接口 2020/03/24 分页
	@TaskAnnotation("queryUserByRoleCode")
	@Override
	public PageListVO<List<UserVO>> queryUserByRoleCode(SessionFactory factory, RoleDTO roleDTO) {			
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<UserVO> userList=userMapper.queryUserByRoleCode(roleDTO);
		//查询总条数
		int rowNumber = userMapper.getRoleUserCount(roleDTO);
		// 返回数据结果
		PageListVO<List<UserVO>> result = new PageListVO<>();
		result.setDataList(userList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(roleDTO.getPage(), roleDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;		
	}

	//加载角色接口 2020/03/24
	@TaskAnnotation("queryAllRole")
	@Override
	public List<RoleVO> queryAllRole(SessionFactory factory) {		
		//先查询所有的角色
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<RoleVO> roleList=userMapper.queryAllRole();
		return roleList;
		
	}

	//遍历插入职员和角色的关系 2020/03/24
	@TaskAnnotation("addRoleUser")
	@Override
	public Integer addRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);		
		Integer addResult=null;
		//把数据封装成List<RoleAndUserDTO>,方便遍历插入数据
		List<RoleAndUserDTO> roleAndUserDTOList=new ArrayList<RoleAndUserDTO>();
		List<RoleUserCodeVO> codeList=new  ArrayList<>();
		//插入之前判断roleCode在SM_userRole表中是否存在
		List<RoleVO> roleList=userMapper.queryExistRole(roleUserDTO);
		if(roleList.size()<1) {
			addResult=-3;
			return addResult;
		}		
		for(int i=0;i<roleUserDTO.getUserCodeList().size();i++) {
			RoleAndUserDTO roleUserDTONew=new RoleAndUserDTO();
			roleUserDTONew.setRoleCode(roleUserDTO.getRoleCode());
			roleUserDTONew.setUserCode(roleUserDTO.getUserCodeList().get(i));		
			roleUserDTONew.setCreateBy("小詹");
			roleUserDTONew.setUpdateBy("小詹");
			roleAndUserDTOList.add(roleUserDTONew);
			//插入之前判断职员code是否在sm_user是否存在
			QueryUserDTO queryUserDTO=new QueryUserDTO();
			queryUserDTO.setCode(roleUserDTO.getUserCodeList().get(i));
			List<UserVO> userList=userMapper.queryUser(queryUserDTO);
			if(userList.size()<1) {
				addResult=-4;
				return addResult;
			}
			//插入之前先判断职员code和roleCode是否已经存在在用户角色关系表		
		    codeList=userMapper.judgeExistCode(roleUserDTONew);
		    if(codeList.size()>0) {
				addResult=-2;
				return addResult;				
			}
		}						
	    addResult=userMapper.addRoleUser(roleAndUserDTOList);
		return addResult;
	}

	//删除角色中职员(批量)接口 2020/03/24
	@TaskAnnotation("deleteRoleUser")
	@Override
	public Integer deleteRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO) {
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		//执行批量删除角色职员的操作
		Integer delResult=userMapper.deleteRoleUser(roleUserDTO.getRoleCode(),roleUserDTO.getUserCodeList());
		return delResult;
	}

	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员) 2020/03/24  分页
	@TaskAnnotation("queryExceptRoleUser")
	@Override
	public PageListVO<List<UserVO>> queryExceptRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO) {		
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		roleUserDTO.setWhetUse(0);
		List<UserVO> userList=userMapper.queryExceptRoleUser(roleUserDTO);
		//查询总条数
		int rowNumber = userMapper.getExceptRoleUserCount(roleUserDTO);
		// 返回数据结果
		PageListVO<List<UserVO>> result = new PageListVO<>();
		result.setDataList(userList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(roleUserDTO.getPage(), roleUserDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;		
		
	}
	
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25 分页
		@TaskAnnotation("queryExceptDeptUser")
		@Override
		public PageListVO<List<UserVO>> queryExceptDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO) {			
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			deptUserDTO.setWhetUse(0);
			List<UserVO> userList=userMapper.queryExceptDeptUser(deptUserDTO);
			//查询总条数
			int rowNumber = userMapper.getExceptDeptUserCount(deptUserDTO);
			// 返回数据结果
			PageListVO<List<UserVO>> result = new PageListVO<>();
			result.setDataList(userList);
			// 插入分页信息
			PageVO pageVO = PageUtil.getPageBean(deptUserDTO.getPage(), deptUserDTO.getPageCount(), rowNumber);
			result.setTotalPage(pageVO.getTotalPage());
			result.setRowNumber(pageVO.getRowNumber());
			result.setPageCount(pageVO.getPageCount());
			result.setPage(pageVO.getPage());
			return result;	
			
		}

		//添加用户(批量)和部门关系的操作 2020/03/25
		@TaskAnnotation("addDeptUser")
		@Override
		public Integer addDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO) {
			// TODO Auto-generated method stub
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			Integer addResult=null;
			//把数据封装成List<RoleAndUserDTO>,方便遍历插入数据
			List<DeptAndUserDTO> deptUserDTOList=new ArrayList<DeptAndUserDTO>();	
			List<DeptUserCodeVO> codeList=new  ArrayList<>();
			//添加之前先判断deptCode是否存在
			DeptDTO deptDTO=new DeptDTO();
			deptDTO.setDepCode(deptUserDTO.getDepCode());
			List<DeptVO> deptList=userMapper.queryExistDept(deptDTO);		
			if(deptList.size()<1) {
				addResult=-3;
				return addResult;
			}			
			for(int i=0;i<deptUserDTO.getUserCodeList().size();i++) {
				DeptAndUserDTO deptUserDTONew=new DeptAndUserDTO();
				deptUserDTONew.setDepCode(deptUserDTO.getDepCode());
				deptUserDTONew.setUserCode(deptUserDTO.getUserCodeList().get(i));				
				deptUserDTONew.setCreateBy("小詹");			
				deptUserDTONew.setUpdateBy("小詹");
				deptUserDTONew.setMainDeptFlag(secDeptFlag);
				deptUserDTOList.add(deptUserDTONew);
				//插入之前判断职员code是否在sm_user是否存在
				QueryUserDTO queryUserDTO=new QueryUserDTO();
				queryUserDTO.setCode(deptUserDTO.getUserCodeList().get(i));
				List<UserVO> userList=userMapper.queryUser(queryUserDTO);
				if(userList.size()<1) {
					addResult=-4;
					return addResult;
				}
				//插入之前先判断职员code和deptCode是否已经存在在用户角色关系表		
			    codeList=userMapper.judgeExistDeptUserCode(deptUserDTONew);
			    if(codeList.size()>0) {
					addResult=-2;
					return addResult;				
				}
			}
			addResult=userMapper.addDeptUser(deptUserDTOList);
			return addResult;
		}

		//删除部门中职员(批量)接口 2020/03/25
		@TaskAnnotation("deleteDeptUser")
		@Override
		public Integer deleteDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO) {			
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			//执行批量删除角色职员的操作
			Integer delResult=userMapper.deleteDeptUser(deptUserDTO.getDepCode(),deptUserDTO.getUserCodeList());
			return delResult;
		}
		
		
		//添加数据字典接口 2020/03/25
				@TaskAnnotation("addDataDic")
				@Override
				public Integer addDataDic(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					DataDicDTO dicDTO=new DataDicDTO();
					dicDTO.setDicParent(dataDicDTO.getDicParent());
					//添加之前先判断dicParent是否已经存在
					List<DataDicVO>  parentList=userMapper.queryParentDic(dicDTO);
					Integer addResult=null;
					if(parentList.size()>0) {
					  //说明已经存在
						addResult=-2;
						return addResult;
					}				
					List<DataDicDTO> dataDicDTOList=new ArrayList<DataDicDTO>();
					//插入i条
					for(int i=0;i<dataDicDTO.getDataDicDTOList().size();i++) {
						DataDicDTO dataDicDTONew=new DataDicDTO();
						dataDicDTONew.setDicCn(dataDicDTO.getDicCn());
						dataDicDTONew.setDicEn(dataDicDTO.getDicEn());
						dataDicDTONew.setDicTc(dataDicDTO.getDicTc());
						dataDicDTONew.setDicParent(dataDicDTO.getDicParent());
						dataDicDTONew.setDicRemark(dataDicDTO.getDicRemark());
						dataDicDTONew.setDicKey(dataDicDTO.getDataDicDTOList().get(i).getDicKey());
						dataDicDTONew.setDicValue(dataDicDTO.getDataDicDTOList().get(i).getDicValue());
						dataDicDTONew.setDicEnValue(dataDicDTO.getDataDicDTOList().get(i).getDicEnValue());
						dataDicDTONew.setDicTcValue(dataDicDTO.getDataDicDTOList().get(i).getDicTcValue());
						dataDicDTONew.setDicDetRemark(dataDicDTO.getDataDicDTOList().get(i).getDicDetRemark());
						dataDicDTONew.setDicType(1);
						if(dataDicDTO.getDataDicDTOList().get(i).getDicSeq()==null) {
							dataDicDTONew.setDicSeq(i);
						}else {
							dataDicDTONew.setDicSeq(dataDicDTO.getDataDicDTOList().get(i).getDicSeq());
						}
						dataDicDTONew.setCreateBy("小詹");					
						dataDicDTONew.setUpdateBy("小詹");						
						dataDicDTOList.add(dataDicDTONew);
					}
					
					 addResult=userMapper.addDataDic(dataDicDTOList);
					return addResult;			
				}

				//查询数据字典接口(查询明细信息键值) 2020/03/26
				@TaskAnnotation("queryDataDic")
				@Override
				public List<DataDicVO> queryDataDic(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<DataDicVO> dataDicVOList=userMapper.queryDataDic(dataDicDTO);
					//如果只是刚刚添加了数据字典主信息，就不返回了，返回个空的就行
					if(dataDicVOList.size()>0  && dataDicVOList.get(0).getDicKey()==null) {
						dataDicVOList=new  ArrayList<DataDicVO>();
					}
					return dataDicVOList;
				}
				//查询数据字典接口(查询明细信息主表) 2020/03/26 分页
				@TaskAnnotation("queryMainDataDic")
				@Override
				public PageListVO<List<DataDicVO>> queryMainDataDic(SessionFactory factory, DataDicDTO dataDicDTO) {					
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					//只需要返回第一条主的信息集合就行
					List<DataDicVO> dataDicVOList=userMapper.queryMainDataDic(dataDicDTO);
					// 查询总条数								
					int rowNumber = userMapper.getDataDicCount(dataDicDTO);
					// 返回数据结果
					PageListVO<List<DataDicVO>> result = new PageListVO<>();
					result.setDataList(dataDicVOList);
					// 插入分页信息
					PageVO pageVO = PageUtil.getPageBean(dataDicDTO.getPage(), dataDicDTO.getPageCount(), rowNumber);
					result.setTotalPage(pageVO.getTotalPage());
					result.setRowNumber(pageVO.getRowNumber());
					result.setPageCount(pageVO.getPageCount());
					result.setPage(pageVO.getPage());
					return result;
					
				}

			    //修改数据字典(通过parent,修改一条就要修改多条主的信息) 2020/03/27
				@TaskAnnotation("updateDic")
				@Override
				public Integer updateDic(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);					
					dataDicDTO.setUpdateBy("小詹");					
					Integer updateRes=userMapper.updateDic(dataDicDTO);
					return updateRes;
				}
				
				//删除数据字典(通过parent，删除一条就要修改多条主的信息，还要实现批量) 2020/03/27
				@TaskAnnotation("deleteDicByParent")
				@Override
				public Integer deleteDicByParent(SessionFactory factory, DataDicDTO dataDicDTO) {					
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<DataDicDTO> dataDicDTOList=new ArrayList<DataDicDTO>();
					for(int i=0;i<dataDicDTO.getDicParentList().size();i++) {
						DataDicDTO dataDicDTONew=new DataDicDTO();
						dataDicDTONew.setDicParent(dataDicDTO.getDicParentList().get(i));
						dataDicDTOList.add(dataDicDTONew);
					}
					Integer delRes=userMapper.deleteDicByParent(dataDicDTOList);
					return delRes;
				}
				
				//修改数据字典明细的操作  2020/03/27
				@TaskAnnotation("updateDicDetById")
				@Override
				public Integer updateDicDetById(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);					
					dataDicDTO.setUpdateBy("小詹");
					//修改时候判断key是否重复
					List<DataDicVO> keyList=userMapper.queryKey(dataDicDTO);
					if(keyList!=null && keyList.size()>0 && dataDicDTO.getDicId()!=keyList.get(0).getDicId()) {
						return -2;
					}
					Integer updateRes=userMapper.updateDicDetById(dataDicDTO);
					return updateRes;
				}
		      
				//删除数据字典(通过key) 2020/03/27
				@TaskAnnotation("deleteDetDicByKey")
				@Override
				public Integer deleteDetDicByKey(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
				    UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<DataDicDTO> dataDicDTOList=new ArrayList<DataDicDTO>();
					for(int i=0;i<dataDicDTO.getDicKeyList().size();i++) {
					    DataDicDTO dataDicDTONew=new DataDicDTO();
						dataDicDTONew.setDicKey(dataDicDTO.getDicKeyList().get(i));
						dataDicDTOList.add(dataDicDTONew);
					}
					Integer delRes=userMapper.deleteDetDicByKey(dataDicDTOList);
					 return delRes;
					}

				//新建特征日 2020/03/27
				@TaskAnnotation("addSpecialDate")
				@Override
				public Integer addSpecialDate(SessionFactory factory,SpecialDayDTO specialDayDTO) {
					// TODO Auto-generated method stub
				    UserMapper userMapper = factory.getMapper(UserMapper.class);
				    Integer insertRes=null;
				    //新建的时候判断在那个日期是否已经存在了
				    SpecialDayDTO specialDayDTONew=new SpecialDayDTO();
				    specialDayDTONew.setSpDate(specialDayDTO.getSpDate());
				    List<SpecialDayDTO> specialDayDTOList=userMapper.queryExistSp(specialDayDTONew);
				    if(specialDayDTOList.size()>0) {
				    	insertRes=-2;
				    	return insertRes;
				    }					
					specialDayDTO.setCreateBy("小詹");				
					specialDayDTO.setUpdateBy("小詹");				
				    insertRes=userMapper.addSpecialDate(specialDayDTO);
					return insertRes;					
				}

				//查询某年某月特征日 2020/03/27
				@TaskAnnotation("querySpecialDate")
				@Override
				public List<SpecialDayDTO> querySpecialDate(SessionFactory factory, SpecialDayDTO specialDayDTO) {					
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					//获取String年份(开始日期 年月,结束日期年月)
					 String selectYear="";
					if(specialDayDTO.getSelectYear()!=null) {
					   selectYear=specialDayDTO.getSelectYear();
					}
					String selectMonth="";
					if(specialDayDTO.getSelectMonth()!=null) {
					  selectMonth=specialDayDTO.getSelectMonth();	
					}				
			        String endYear="";
			        String endMonth="";
			        //如果选择的月份小于10 
			        if(Integer.valueOf(selectMonth)<10) {				        	
			        	if(Integer.valueOf(selectMonth)==9) {
			        		//当前选择月
			        		selectMonth="09";
			        		//当前选择年
			        		selectYear=selectYear;
			        		//下个月
			        		endMonth="10";			        	
			        		endYear=selectYear;
			        	}else {
			        		//当前选择月
			        		selectMonth="0"+selectMonth;
			        		//当前选择年	
			        		selectYear=selectYear;
			        		//下一个月份加1
			        		int selectMonthInt=Integer.parseInt(selectMonth)+1;
			        		//转化为String
			        		endMonth="0"+String.valueOf(selectMonthInt);
			        		endYear=selectYear;
			        	}		        	 
			        }else if(Integer.valueOf(selectMonth)==12){
			        	//当前选择月
		        		selectMonth="12";
		        		//当前选择年
		        		selectYear=selectYear;
		        		//下个月
		        		endMonth="01";
		        		//下一年
		        		int endYearInt=Integer.parseInt(selectYear)+1;
		        		//转化为String
		        		endYear=String.valueOf(endYearInt);
			        }else {
			        	//当前选择月
		        		selectMonth=selectMonth;
		        		//当前选择年
		        		selectYear=selectYear;
		        		//下一个月份加1
		        		int selectMonthInt=Integer.parseInt(selectMonth)+1;
		        		//转化为String
		        		endMonth=String.valueOf(selectMonthInt);		        	
		        		endYear=selectYear;
			        }
					//封装日期数据
					if(specialDayDTO.getSelectYear()!=null) {
						specialDayDTO.setSelectYear(selectYear);
						specialDayDTO.setEndYear(endYear);
					}if(specialDayDTO.getSelectMonth()!=null) {
						specialDayDTO.setSelectMonth(selectMonth);
						specialDayDTO.setEndMonth(endMonth);
					}
					String startDate= specialDayDTO.getSelectYear()+"-"+specialDayDTO.getSelectMonth();
					String endDate= specialDayDTO.getEndYear()+"-"+specialDayDTO.getEndMonth();
					specialDayDTO.setStartTime(startDate);
					specialDayDTO.setEndTime(endDate);
					List<SpecialDayDTO> spList=userMapper.querySpecialDate(specialDayDTO);
					return spList;
				}
				
				@TaskAnnotation("querySpecialDateByDay")
				@Override
				public List<SpecialDayDTO> querySpecialDateByDay(SessionFactory factory, SpecialDayDTO specialDayDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<SpecialDayDTO> specialDayDTOList=userMapper.querySpecialDateByDay(specialDayDTO);
					return specialDayDTOList;
				}		

				//根据日期删除特征日 2020/03/30
				@TaskAnnotation("deleteSpecialDate")
				@Override
				public Integer deleteSpecialDate(SessionFactory factory, SpecialDayDTO specialDayDTO) {					
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Integer deleteRes=userMapper.deleteSpecialDate(specialDayDTO);
					return deleteRes;
				}

				//根据日期修改特征日  2020/03/30
				@TaskAnnotation("updateSpecialDate")
				@Override
				public Integer updateSpecialDate(SessionFactory factory, SpecialDayDTO specialDayDTO) {					
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Integer updateRes=userMapper.updateSpecialDate(specialDayDTO);
					return updateRes;
				}

				//组织下添加部门2020/03/31
				@TaskAnnotation("addTreeDept")
				@Override
				public Integer addTreeDept(SessionFactory factory, OrgAndDeptDTO orgDeptDTO,int type,String foreignKey) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);						
					orgDeptDTO.setCreateBy("小詹");
					orgDeptDTO.setUpdateBy("小詹");
					//部门正常使用状态0,禁用状态-1
					Integer finalRes=null;
					orgDeptDTO.setDepStatus(0);
					
					RandomCodeUtil randomCodeUtil=new RandomCodeUtil();
					//随机获取uuid,赋值给Code	
					String deptCode=randomCodeUtil.getUUID32();
					orgDeptDTO.setDepCode(deptCode);		
					//先执行生成部门的操作(获取刚刚插入到部门表的Id)
					Integer depId=userMapper.addDeptNew(orgDeptDTO);
					//插入组织部门关系表
					OrgAndDeptDTO orgDeptDTONew=new OrgAndDeptDTO();
					orgDeptDTONew.setDepCode(deptCode);
					orgDeptDTONew.setOrgCode(orgDeptDTO.getOrgCode());
					orgDeptDTONew.setCreateBy("小詹");
					orgDeptDTONew.setUpdateBy("小詹");										
					Integer addRes=userMapper.addOrgDept(orgDeptDTONew);
					if(addRes==-1) {
						finalRes=-1;
						return finalRes;
					}
					else {
					  //组装child,主要两个参数，一个type，一个是foreignkey	
						  LongTreeBean child=new LongTreeBean();
						  child.setForeignkey(deptCode);
						  child.setType(0);
						  //根据treeParentId获取node
						  TreeService treeService  =new TreeService();
						  LongTreeBean parent= treeService.getNode(factory, type, foreignKey);
						    if(parent!=null) {
						      //生成根节点
						      LongTreeBean longTreeBean =treeService.add(factory, parent, child);	
							  if(longTreeBean!=null) {
							   	  finalRes=1;
							  }else {
								  finalRes=-1;
							  }
						    }
					  }
					return finalRes;
				}

				//部门下添加部门2020/03/31	
				@TaskAnnotation("deptAddTreeDept")
				@Override
				public Integer deptAddTreeDept(SessionFactory factory, OrgAndDeptDTO orgDeptDTO,int type,String foreignKey) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);	
					orgDeptDTO.setCreateBy("小詹");
					orgDeptDTO.setUpdateBy("小詹");
					Integer finalRes=null;
					//部门正常使用状态0,禁用状态-1
					orgDeptDTO.setDepStatus(0);
					RandomCodeUtil randomCodeUtil=new RandomCodeUtil();
					//随机获取uuid,赋值给Code	
					String deptCode=randomCodeUtil.getUUID32();
					orgDeptDTO.setDepCode(deptCode);
					Integer addRes=userMapper.deptAddTreeDept(orgDeptDTO);
					if(addRes==-1) {
						finalRes=-1;
						return finalRes;
					}
					else {
					  //组装child,主要两个参数，一个type，一个是foreignkey	
						  LongTreeBean child=new LongTreeBean();
						  child.setForeignkey(deptCode);
						  child.setType(0);
						  //根据treeParentId获取node
						  TreeService treeService  =new TreeService();
						  LongTreeBean parent= treeService.getNode(factory, type, foreignKey);
						    if(parent!=null) {
						      //生成根节点
						    	LongTreeBean longTreeBean =treeService.add(factory, parent, child);							  
							  if(longTreeBean!=null) {
							   	  finalRes=1;
							  }else {
								  finalRes=-1;
							  }
						    }
					  }
					return finalRes;
				}

			   //删除树结构部门的时候，判断该节点下的是否存在职员,存在的情况下不能删除根据外键code 2020/04/01
				@TaskAnnotation("judgeExistUser")
				@Override
				public Integer judgeExistUser(SessionFactory factory, DeptAndUserDTO deptAndUserDTO,int type,String foreignkey, boolean force,int deleteType) {					
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<UserVO> userList=userMapper.judgeExistUser(deptAndUserDTO);
					Integer userRes=0;
					if(userList.size()>0) {
						//删除部门存在职员,不同意删除
						userRes=-2;
						return userRes;
					}
					 if(userRes!=null && userRes==0) {
						  //判断下级数据是不是存在,就是不能强删除(下面这步才执行的是删除树结构节点的操作) 
						  TreeService treeService  =new TreeService();
						  Integer  delRes=treeService.forceDelNode(factory,type,foreignkey,force);					
						  if(delRes!=null && delRes!=-1) {
								//删除数结构部门成功，执行删除部门的操作(物理删除),根据外键Code
							   Integer delDeptRes = userMapper.deleteTreeDept(deptAndUserDTO);
							   if(delDeptRes!=null) {
								   if(delDeptRes==-1) {
									   //删除部门失败
									   userRes=-1;
								   }else {									   								   
									   if(deleteType==0) {	
										 //删除部门成功,删除组织部门关系
										   OrgAndDeptDTO orgAndDeptDTO=new OrgAndDeptDTO();
										  //TODO 测试使用 org001
										   orgAndDeptDTO.setDepCode(foreignkey);
										   orgAndDeptDTO.setOrgCode("org001");
										   userRes=userMapper.deleteOrgDept(orgAndDeptDTO);
										   if(userRes==1) {
											   userRes=0;  
										   }
									   }else {
									    userRes=0;
									   }
								   }
							   }else {
								   userRes=-1;
							   }
							  }else {
								 //存在下级单位
							     userRes=-3;
							  } 
					   }
					return userRes;
				}

				//物理删除部门，部门表 根据外键Code 2020/04/01
				@TaskAnnotation("deleteTreeDept")
				@Override
				public Integer deleteTreeDept(SessionFactory factory, DeptAndUserDTO deptAndUserDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Integer   delRes=userMapper.deleteTreeDept(deptAndUserDTO);
					return delRes;
				}

				//根据部门code更新部门名称 2020/04/01
				@TaskAnnotation("updateTreeDept")
				@Override
				public Integer updateTreeDept(SessionFactory factory, DeptDTO deptDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					deptDTO.setUpdateBy("小詹");				
					Integer updateRes=userMapper.updateTreeDept(deptDTO);
					return updateRes;
				}

				//根据部门Code查询部门职员 2020/04/07  分页
				@TaskAnnotation("queryDeptUser") 
				@Override
				public PageListVO<List<UserVO>> queryDeptUser(SessionFactory factory, DeptDTO deptDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<UserVO> userList=userMapper.queryDeptUser(deptDTO);
					// 查询总条数
					//0代表在职
					int rowNumber = userMapper.getDeptUserCount(deptDTO);
					// 返回数据结果
					PageListVO<List<UserVO>> result = new PageListVO<>();
					result.setDataList(userList);
					// 插入分页信息
					PageVO pageVO = PageUtil.getPageBean(deptDTO.getPage(), deptDTO.getPageCount(), rowNumber);
					result.setTotalPage(pageVO.getTotalPage());
					result.setRowNumber(pageVO.getRowNumber());
					result.setPageCount(pageVO.getPageCount());
					result.setPage(pageVO.getPage());
					return result;
					
				}
				
				//生成菜单(单条记录) 2020/04/08
				@TaskAnnotation("addMenu")
				public Integer addMenu(SessionFactory factory,MenuTreeDTO menuTreeDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					MenuDTO menuDTO=new MenuDTO();
					menuDTO.setLinkAddress(menuTreeDTO.getLinkAddress());
					RandomCodeUtil randomCodeUtil=new RandomCodeUtil();				
					//随机获取uuid,赋值给Code	
					String menuCode=randomCodeUtil.getUUID32();
					menuDTO.setMenuCode(menuCode);
					menuDTO.setModuleName(menuTreeDTO.getModuleName());
					menuDTO.setModuleNo(menuTreeDTO.getModuleNo());	
					menuDTO.setSequence(menuTreeDTO.getSequence());
					menuDTO.setCreateBy("小詹");
					menuDTO.setUpdateBy("小詹");
					//先生成菜单
					Integer addRes=userMapper.addMenu(menuDTO);
					if(addRes==-1) {
						addRes=-1;
						return addRes;
					}
					//树状关系中插入一条记录
					 TreeService treeService  =new TreeService();
					 //组装child,主要两个参数，一个type，一个是foreignkey	
					  LongTreeBean child=new LongTreeBean();
					  child.setForeignkey(menuCode);
					  child.setType(1);
					  int type=1;
					  LongTreeBean parent= treeService.getNode(factory, type, menuTreeDTO.getForeignKey());
					  if(parent==null) {
						  addRes=-1;
						  return addRes;
					  }else {
						  //生成根节点
					      LongTreeBean longTreeBean =treeService.add(factory, parent, child);
					      if(longTreeBean==null) {
					    	  addRes=-1;
					      }else {
					    	  addRes=1; 
					      }
					  }
					  return addRes;
				}

				//加载角色菜单权限 2020/04/08
				@TaskAnnotation("queryRoleMenuByRoleCode")
				@Override			
				public List<RoleMenusVO> queryRoleMenuByRoleCode(SessionFactory factory, RoleDTO roleDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<RoleMenusVO> roleMenusList=userMapper.queryRoleMenuByRoleCode(roleDTO);									
					return roleMenusList;
				}

				//根据角色Code修改菜单权限
				@TaskAnnotation("updateRoleMenuByRoleCode")
				@Override
				public Integer updateRoleMenuByRoleCode(SessionFactory factory, RoleMenuDTO roleMenuDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					//先执行批量删除SM_roleMenus的操作(根据roleCode)
					  List<RoleMenuDTO> roleMenuDTOList=new ArrayList<RoleMenuDTO>();
					for(int i=0;i<roleMenuDTO.getRoleMenuList().size();i++) {
						RoleMenuDTO roleMenuDTONew =new RoleMenuDTO();
					//	roleMenuDTONew.setModuleCode(roleMenuDTO.getRoleMenuList().get(i).getModuleCode());
						roleMenuDTONew.setRoleCode(roleMenuDTO.getRoleMenuList().get(i).getRoleCode());
						roleMenuDTOList.add(roleMenuDTONew);
					}
					Integer delRes=0;
					if(roleMenuDTOList.size()>0) {
						delRes=userMapper.deleteManyOP(roleMenuDTOList);
					}
					Integer updateRes=null;
					if(delRes==-1) {
						updateRes=-1;
						return updateRes;
					}					
					List<RoleMenuDTO> roleMenuList=new ArrayList<RoleMenuDTO>();					
					//删除完成后遍历插入SM_roleMenus数据
					for(int i=0;i<roleMenuDTO.getRoleMenuList().size();i++) {					
						for(int j=0;j<roleMenuDTO.getRoleMenuList().get(i).getOpList().size();j++) {
							RoleMenuDTO roleMenuNew =new RoleMenuDTO();
							roleMenuNew.setModuleCode(roleMenuDTO.getRoleMenuList().get(i).getModuleCode());
							roleMenuNew.setRoleCode(roleMenuDTO.getRoleMenuList().get(i).getRoleCode());
							roleMenuNew.setOp(Integer.valueOf(roleMenuDTO.getRoleMenuList().get(i).getOpList().get(j)));
							roleMenuNew.setCreateBy("小詹");
							roleMenuNew.setUpdateBy("小詹");							
							roleMenuList.add(roleMenuNew);
						}
					}
				    updateRes=userMapper.addManyRoleMenu(roleMenuList);					
					return updateRes;
				}

				//模糊查询部门接口 分页
				@TaskAnnotation("queryDept") 
				@Override
				public PageListVO<List<DeptVO>> queryDept(SessionFactory factory, DeptDTO deptDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<DeptVO> deptList=userMapper.queryDept(deptDTO);
					// 查询部门总条数
					int rowNumber = userMapper.getDeptCount(deptDTO);
					// 返回数据结果
					PageListVO<List<DeptVO>> result = new PageListVO<>();
					result.setDataList(deptList);
					// 插入分页信息
					PageVO pageVO = PageUtil.getPageBean(deptDTO.getPage(), deptDTO.getPageCount(), rowNumber);
					result.setTotalPage(pageVO.getTotalPage());
					result.setRowNumber(pageVO.getRowNumber());
					result.setPageCount(pageVO.getPageCount());
					result.setPage(pageVO.getPage());
					return result;
				}
				//通过模块菜单Code和角色加载该角色所有菜单以及可操作的权限
				@TaskAnnotation("queryRoleMenuByRoleMenu")
				@Override
				public List<RoleMenusVO>  queryRoleMenuByRoleMenu(SessionFactory factory,RoleMenuDTO roleMenuDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<RoleMenusVO> roleMenusList=userMapper.queryRoleMenuByRoleMenu(roleMenuDTO,roleMenuDTO.getModuleCodeList());					
					//String转为List									
					for(int  i=0;i<roleMenusList.size();i++) {					
						 String str = roleMenusList.get(i).getOp().replace(" ", "");						 
					     List<String> lis = Arrays.asList(str.split(","));						    
					     roleMenusList.get(i).setOpList(lis);
					}
					return roleMenusList;					
				}
				
				//添加数据字典主表信息  2020/04/13
				@TaskAnnotation("addMainDataDic") 
				@Override
				public Integer addMainDataDic(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					DataDicDTO dicDTO=new DataDicDTO();
					dicDTO.setDicParent(dataDicDTO.getDicParent());
					//添加之前先判断dicParent是否已经存在
					List<DataDicVO>  parentList=userMapper.queryParentDic(dicDTO);
					Integer addResult=null;
					if(parentList.size()>0) {
					  //说明已经存在
						addResult=-2;
						return addResult;
					}	
					dataDicDTO.setDicType(1);
					dataDicDTO.setUpdateBy("小詹");
					dataDicDTO.setCreateBy("小詹");				
					// 插入主表信息					
					 addResult=userMapper.addMainDataDic(dataDicDTO);
					return addResult;
				}

				//添加数据字典明细信息  2020/04/13
				@TaskAnnotation("addDetDataDic") 
				@Override
				public Integer addDetDataDic(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					//根据parent查询主表信息
					List<DataDicVO>  dataList=userMapper.queryDic(dataDicDTO);
					if(dataList!=null && dataList.size()>0) {
						dataDicDTO.setDicCn(dataList.get(0).getDicCn());
						dataDicDTO.setDicEn(dataList.get(0).getDicEn());
						dataDicDTO.setDicTc(dataList.get(0).getDicTc());
						dataDicDTO.setDicRemark(dataList.get(0).getDicRemark());
						dataDicDTO.setDicType(1);
					}
					//添加时候判断key是否重复
					List<DataDicVO> keyList=userMapper.queryKey(dataDicDTO);
					if(keyList.size()>0) {
						return -2;
					}
					//先执行删除主表信息的操作(键值为空的)
					Integer delRes=userMapper.deleteOneDic(dataDicDTO);
					//插入数据字典的操作(一条)	
					dataDicDTO.setDicType(1);
					dataDicDTO.setUpdateBy("小詹");
					dataDicDTO.setCreateBy("小詹");					
					if(dataDicDTO.getDicSeq()==null) {
						dataDicDTO.setDicSeq(1);
					}
					Integer addRes=userMapper.addOneDataDet(dataDicDTO);
					return addRes;
				}

				//根据moduleName 查询moduleCode 2020/04/14
				@TaskAnnotation("queryMenuOP") 
				@Override
				public List<ModuleMenuVO> queryMenuOP(SessionFactory factory, ModuleMenuDTO moduleMenuDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<ModuleMenuVO> moduleMenuList=userMapper.queryMenuOP(moduleMenuDTO);
					return moduleMenuList;
				}

				//通过模块菜单Code和角色code查询该模块菜单操作权限 2020/04/14
				@TaskAnnotation("queryOPByCode") 
				@Override
				public List<RoleMenusVO> queryOPByCode(SessionFactory factory, RoleMenuDTO roleMenuDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<RoleMenusVO> roleMenusList=userMapper.queryOPByCode(roleMenuDTO);
					return roleMenusList;
				}

				//管理员批量重置职员密码2020/04/15
				@TaskAnnotation("updateUserPassword") 
				@Override
				public Integer updateUserPassword(SessionFactory factory, UserDTO userDTO) {					
					UserMapper userMapper = factory.getMapper(UserMapper.class);					
					List<UserDTO> userList=new ArrayList<UserDTO>();
					for(int i=0;i<userDTO.getUserCodeList().size();i++) {
						UserDTO userDTONew=new UserDTO();
						userDTONew.setPassword((new  EncryptionUtil().encodeBase64("yhsw123456")));
						userDTONew.setCode(userDTO.getUserCodeList().get(i));
						userList.add(userDTONew);
					}
					Integer updateRes=userMapper.updateUserPassword(userList);
					return updateRes;
				}

				//添加集成配置主表信息 2020/04/16
				@TaskAnnotation("addIntegration") 
				@Override
				public Integer addIntegration(SessionFactory factory, IntegrationConfDTO integrationConfDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					integrationConfDTO.setCreateBy("小詹");
					integrationConfDTO.setUpdateBy("小詹");
					//随机获取uuid,赋值给Code
					String code=new RandomCodeUtil().getUUID32();
					integrationConfDTO.setInteConfCode(code);
					Integer addRes=userMapper.addIntegration(integrationConfDTO);
					return addRes;
				}

				//添加表格映射主表信息 2020/04/16
				@TaskAnnotation("addTableMapper") 
				@Override
				public Integer addTableMapper(SessionFactory factory, TableMapperDTO tableMapperDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					tableMapperDTO.setCreateBy("小詹");
					tableMapperDTO.setUpdateBy("小詹");
					String code=new RandomCodeUtil().getUUID32();
					tableMapperDTO.setTableMapperCode(code);
					Integer addRes=userMapper.addTableMapper(tableMapperDTO);
					return addRes;
				}

				//添加表格映射主表信息 2020/04/16
				@TaskAnnotation("addFieldMapper") 
				@Override
				public Integer addFieldMapper(SessionFactory factory, FieldMapperDTO fieldMapperDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					fieldMapperDTO.setCreateBy("小詹");
					fieldMapperDTO.setUpdateBy("小詹");
					String code=new RandomCodeUtil().getUUID32();
					fieldMapperDTO.setFieldMapperCode(code);
					Integer addRes=userMapper.addFieldMapper(fieldMapperDTO);
					return addRes;
				}

				//添加枚举值映射明细2020/04/16
				@TaskAnnotation("addEnumMapper") 
				@Override
				public Integer addEnumMapper(SessionFactory factory, EnumMapperDTO enumMapperDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					enumMapperDTO.setCreateBy("小詹");
					enumMapperDTO.setUpdateBy("小詹");				
					Integer addRes=userMapper.addEnumMapper(enumMapperDTO);
					return addRes;
				}

				//查询集成配置列表信息 2020/04/16 分页
				@TaskAnnotation("queryIntegration") 
				@Override
				public PageListVO<List<IntegrationConfVO>> queryIntegration(SessionFactory factory,
						IntegrationConfDTO integrationConfDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					// 查询集成配置列表
					List<IntegrationConfVO> IntegrationConfList=userMapper.queryIntegration(integrationConfDTO);
					//查询总条数
					int rowNumber = userMapper.getIntegrationCount(integrationConfDTO);
					// 返回数据结果
					PageListVO<List<IntegrationConfVO>> result = new PageListVO<>();
					result.setDataList(IntegrationConfList);
					// 插入分页信息
					PageVO pageVO = PageUtil.getPageBean(integrationConfDTO.getPage(), integrationConfDTO.getPageCount(), rowNumber);
					result.setTotalPage(pageVO.getTotalPage());
					result.setRowNumber(pageVO.getRowNumber());
					result.setPageCount(pageVO.getPageCount());
					result.setPage(pageVO.getPage());
					return result;							
				}

				//根据code查询集成配置信息
				@TaskAnnotation("queryIntegrationByCode") 
				@Override
				public List<IntegrationConfVO> queryIntegrationByCode(SessionFactory factory,
						IntegrationConfDTO integrationConfDTO) {				
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<IntegrationConfVO> integrationConfList=userMapper.queryIntegrationByCode(integrationConfDTO);
					return integrationConfList;
				}

				//根据配置主表Code查询表格映射明细列表 分页
				@TaskAnnotation("queryTableMapper") 
				@Override
				public PageListVO<List<TableMapperVO>> queryTableMapper(SessionFactory factory,TableMapperDTO tableMapperDTO) {											
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<TableMapperVO> tableMapperList=userMapper.queryTableMapper(tableMapperDTO);
					//查询总条数
					int rowNumber = userMapper.getTableMapperCount(tableMapperDTO);
					// 返回数据结果
					PageListVO<List<TableMapperVO>> result = new PageListVO<>();
					result.setDataList(tableMapperList);
					// 插入分页信息
					PageVO pageVO = PageUtil.getPageBean(tableMapperDTO.getPage(), tableMapperDTO.getPageCount(), rowNumber);
					result.setTotalPage(pageVO.getTotalPage());
					result.setRowNumber(pageVO.getRowNumber());
					result.setPageCount(pageVO.getPageCount());
					result.setPage(pageVO.getPage());
					return result;		
				}

				//根据配置主表Code查询枚举值映射明细列表 分页
				@TaskAnnotation("queryEnumMapper") 
				@Override
				public PageListVO<List<EnumMapperVO>> queryEnumMapper(SessionFactory factory,EnumMapperDTO enumMapperDTO) {						
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<EnumMapperVO> enumMapperList=userMapper.queryEnumMapper(enumMapperDTO);
					//查询总条数
					int rowNumber = userMapper.getEnumMapperCount(enumMapperDTO);
					// 返回数据结果
					PageListVO<List<EnumMapperVO>> result = new PageListVO<>();
					result.setDataList(enumMapperList);
					// 插入分页信息
					PageVO pageVO = PageUtil.getPageBean(enumMapperDTO.getPage(), enumMapperDTO.getPageCount(), rowNumber);
					result.setTotalPage(pageVO.getTotalPage());
					result.setRowNumber(pageVO.getRowNumber());
					result.setPageCount(pageVO.getPageCount());
					result.setPage(pageVO.getPage());
					return result;	
				}

				//根据表格Code查询表格字段映射明细列表 2020/04/17 分页
				@TaskAnnotation("queryFieldMapper") 
				@Override
				public PageListVO<List<FieldMapperVO>> queryFieldMapper(SessionFactory factory,
						FieldMapperDTO fieldMapperDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<FieldMapperVO>  FieldMapperList=userMapper.queryFieldMapper(fieldMapperDTO);
					//查询总条数
					int rowNumber = userMapper.getFieldMapperCount(fieldMapperDTO);
					// 返回数据结果
					PageListVO<List<FieldMapperVO>> result = new PageListVO<>();
					result.setDataList(FieldMapperList);
					// 插入分页信息
					PageVO pageVO = PageUtil.getPageBean(fieldMapperDTO.getPage(), fieldMapperDTO.getPageCount(), rowNumber);
					result.setTotalPage(pageVO.getTotalPage());
					result.setRowNumber(pageVO.getRowNumber());
					result.setPageCount(pageVO.getPageCount());
					result.setPage(pageVO.getPage());
					return result;				
				}
				//根据Code修改集成配置信息接口
				@TaskAnnotation("updateConf") 
				@Override
				public Integer updateConf(SessionFactory factory,IntegrationConfDTO integrationConfDTO) {						
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					integrationConfDTO.setUpdateBy("小詹");
					Integer updateRes=userMapper.updateConf(integrationConfDTO);
					return updateRes;				
				}
				
				//根据Code修改表格接口
				@TaskAnnotation("updateTableMapper") 
				@Override
				public Integer updateTableMapper(SessionFactory factory, TableMapperDTO tableMapperDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					tableMapperDTO.setUpdateBy("小詹");
					Integer updateRes=userMapper.updateTableMapper(tableMapperDTO);
					return updateRes;
				}

				//根据id修改表格枚举映射明细信息
				@TaskAnnotation("updateEnumMapper") 
				@Override
				public Integer updateEnumMapper(SessionFactory factory, EnumMapperDTO enumMapperDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					enumMapperDTO.setUpdateBy("小詹");
					Integer updateRes=userMapper.updateEnumMapper(enumMapperDTO);
					return updateRes;
				}
				//根据Code修改表格字段映射明细
				@TaskAnnotation("updateFieldMapper") 
				@Override
				public Integer updateFieldMapper(SessionFactory factory, FieldMapperDTO fieldMapperDTO) {				    
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					fieldMapperDTO.setUpdateBy("小詹");
					Integer updateRes=userMapper.updateFieldMapper(fieldMapperDTO);					
					return updateRes;
				}

				//根据Code删除表格映射
				@TaskAnnotation("deleteTableMapper") 
				@Override
				public Integer deleteTableMapper(SessionFactory factory, TableMapperDTO tableMapperDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					//根据主表code查询明细字段code
					List<FieldMapperVO> FieldMapperList=userMapper.queryFieldMapperCode(tableMapperDTO.getCodeList());
					List<String> fileCodeList=new ArrayList<>();
					for(int i=0;i<FieldMapperList.size();i++) {
						fileCodeList.add(FieldMapperList.get(i).getFieldMapperCode());
					}
					//先删除明细
					Integer delFieldRes=0;
					if(fileCodeList.size()>0) {
					  delFieldRes=userMapper.deleteFieldMapper(fileCodeList);
					}
					Integer delRes=null;
					if(delFieldRes!=-1) {
						delRes=userMapper.deleteTableMapper(tableMapperDTO.getCodeList());
					}else {
						delFieldRes=-1;
						return delFieldRes;
					}
					return delRes;
				}

				//根据Code删除表格字段映射
				@TaskAnnotation("deleteFieldMapper") 
				@Override
				public Integer deleteFieldMapper(SessionFactory factory, FieldMapperDTO fieldMapperDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Integer delRes=userMapper.deleteFieldMapper(fieldMapperDTO.getCodeList());
					return delRes;
				}

				//根据id删除枚举值映射明细
				@TaskAnnotation("deleteEnumMapper") 
				@Override
				public Integer deleteEnumMapper(SessionFactory factory, EnumMapperDTO enumMapperDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Integer delRes=userMapper.deleteEnumMapper(enumMapperDTO.getIdList());
					return delRes;
				}
				
				
				//添加excel时候导入用户数据 2020/04/22
				@TaskAnnotation("addImportUserDataExcel")
				@Override
				public ImportUserResVO addImportUserDataExcel(SessionFactory factory,List<UserExcelDTO> userList) {					
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					ImportUserResVO importUserResVO=new ImportUserResVO();
					//String转类型
					for(int j=0;j<userList.size();j++) {
					    if("男".equals(userList.get(j).getSexStr())) {
					    	userList.get(j).setSex(0);
					    }else if("女".equals(userList.get(j).getSexStr())) {
					    	userList.get(j).setSex(1);
					    }else {
					    	userList.get(j).setSex(-1);
					    }				    
					    userList.get(j).setPosition(1);
					}					
					List<UserExcelDTO> collect=userList.stream().filter(distinctByKey(UserExcelDTO::getLoginName)).collect(Collectors.toList());
					if(collect.size()<userList.size()) {
						importUserResVO.setResult(-4);
						importUserResVO.setLoginName(collect.get(0).getLoginName());
						return importUserResVO;
					}
					List<UserExcelDTO> collectWorkNo=userList.stream().filter(distinctByKey(UserExcelDTO::getWorkNo)).collect(Collectors.toList());
					if(collectWorkNo.size()<userList.size()) {
						importUserResVO.setResult(-5);
						importUserResVO.setLoginName(collect.get(0).getWorkNo());
						return importUserResVO;
					}
					
					for(int i=0;i<userList.size();i++) {
						userList.get(i).setCreateBy("小詹");
						userList.get(i).setUpdateBy("小詹");
						//管理员设置默认密码yhsw123456
						userList.get(i).setPassword((new  EncryptionUtil().encodeBase64("yhsw123456")));
						//随机获取uuid,赋值给Code
						String userCode=new RandomCodeUtil().getUUID32();
						userList.get(i).setCode(userCode);	
						//添加用户的时候判断工号和登录名称是否重复
						QueryUserDTO queryUserDTO=new QueryUserDTO();
						queryUserDTO.setLoginName(userList.get(i).getLoginName());		
						List<UserVO> loginNameList=userMapper.queryUser(queryUserDTO);
						Integer addResult=null;
						if(loginNameList.size()>0) {
							importUserResVO.setNum(i);
							importUserResVO.setLoginName(userList.get(i).getLoginName());
							importUserResVO.setResult(-2);
							return importUserResVO;
						}
						QueryUserDTO queryUserDTONew=new QueryUserDTO();
						queryUserDTONew.setWorkNo(userList.get(i).getWorkNo());
						List<UserVO> workNoList=userMapper.queryUser(queryUserDTONew);
						if(workNoList.size()>0) {
							importUserResVO.setNum(i);
							importUserResVO.setWorkNo(userList.get(i).getWorkNo());
							importUserResVO.setResult(-3);
							return importUserResVO;
						}				
				    }
				     Integer addResult=userMapper.addManyUser(userList);
				     if(userList.size()>0) {
				      importUserResVO.setSuccessRate(addResult.intValue()/userList.size());	
				     }
				     importUserResVO.setResult(0);
					 return importUserResVO;
				}
				
				
				/**
				  * 获取list中对象属性的重复值
				 */
				    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
				        Set<Object> seen = ConcurrentHashMap.newKeySet();
				        return t -> seen.add(keyExtractor.apply(t));
				    }

				//修改个人密码	
				@TaskAnnotation("updateMyPassword")
				@Override
				public Integer updateMyPassword(SessionFactory factory, UpdateWordDTO updateWordDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					//验证两次输入的密码是否一致
					EncryptionUtil encryptionUtil=new EncryptionUtil();
					if(!encryptionUtil.decodeBase64(updateWordDTO.getNewPassWord()).equals(encryptionUtil.decodeBase64(updateWordDTO.getSurePassWord()))) {						
						return -2;
					}
					//验证新老密码是否重复
					if(encryptionUtil.decodeBase64(updateWordDTO.getNewPassWord()).equals(encryptionUtil.decodeBase64(updateWordDTO.getOldPassWord()))) {						
						return -3;
					}
					// 正则 {8,}8位以上,包含大写小写字母，数字，特殊字符
					String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,16}";
		  
		            if(encryptionUtil.decodeBase64(updateWordDTO.getNewPassWord()).matches(PW_PATTERN)==false) { 
		        	  return -4; 
		        	}
		 
					//验证old密码是否正确   TODO 测试先使用loginName为测试11
					UserDTO user=new UserDTO();
					user.setLoginName("测试11");
					List<UserVO> userList=userMapper.queryPassWord(user);
					Integer updateRes=null;
					if(userList!=null && userList.size()>0) {						
						if(!encryptionUtil.decodeBase64(updateWordDTO.getOldPassWord()).equals(encryptionUtil.decodeBase64(userList.get(0).getPassword()))) {						
							return -5;
						}
						//执行修改密码的操作
						user.setPassword(updateWordDTO.getNewPassWord());
						user.setUpdateBy("小詹");
						updateRes=userMapper.updateMyPassword(user);
					}
					return updateRes;
				}

				//上传头像
				@TaskAnnotation("uploadHeadPortrait")
				@Override
				public Integer  uploadHeadPortrait(SessionFactory factory, MultipartFile file) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Integer addRes=null;
					//TODO 测试使用  获取登录的用户名为： 
					String userName="测试11";
					//根据登录的用户名查询Code
					QueryUserDTO queryUserDTO=new QueryUserDTO();
					queryUserDTO.setLoginName(userName);
					List<UserVO> userVOList=userMapper.queryUser(queryUserDTO);	
					
					//根据用户code删除在文件表中存在的头像(修改状态)
					if(userVOList!=null && userVOList.size()>0) {
						addRes=userMapper.deleteHeadPortrait(userVOList.get(0).getCode());
					}
					if(addRes==-1) {
					   return -2;	
					}
					String originalFilename = file.getOriginalFilename();
			   		// 获取后缀
			        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
			       	//校验文件类型是否为图片
			   		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$"; 
			   	    Matcher matcher=Pattern.compile(reg).matcher(originalFilename);
			   	    if(!matcher.find()) {
			   	    	return -3;
			   	    }			   		
			   	   
			   		// 获取当前月份，时间格式:201910
			   		SimpleDateFormat dataFormate = new SimpleDateFormat("yyyyMM");
			   		String time = dataFormate.format(new Date());
			   		// 保存目录
			   		String path = fileConfigInfo.getPath() + File.separator + "HeadPortrait" + File.separator + time ;
			   		// 生成保存文件
			   		File dirPath = new File(path);
			   		if (!dirPath.exists()) {
			   			dirPath.mkdirs();
			   		}
			   		
			   		//用时间戳作为文件名称存储
			   		String storeName = System.currentTimeMillis()+fileType;
			   		File uploadFile = new File(path+ File.separator + storeName);
			   		double size = file.getSize() / 1014;

			   		// 将上传文件保存到路径
			   		try {
			   			file.transferTo(uploadFile);
			   		} catch (IOException e) {
			   			e.printStackTrace();
			   		}

			   		UploadFileNewDTO uploadFileDTO = new UploadFileNewDTO();
			   		if(userVOList!=null && userVOList.size()>0) {
			   		 uploadFileDTO.setForeignkey(userVOList.get(0).getCode());
			   		}
			   		uploadFileDTO.setFileName(originalFilename);
			   		uploadFileDTO.setFilePath(path);
			   		uploadFileDTO.setFileSize(size);
			   		uploadFileDTO.setModuleType("HeadPortrait");
			   		uploadFileDTO.setStoreName(storeName);
			   		uploadFileDTO.setStorageTime(new java.util.Date());
			   		uploadFileDTO.setCreateBy("小詹");
			   		uploadFileDTO.setFileType(fileType);
			   		// 上传文件记录入库
			   		addRes=userMapper.insertFileDataNew(uploadFileDTO);
					return addRes;
				}

				//根据登录的用户名查询头像
				@TaskAnnotation("queryHeadPortrait")
				@Override
				public List<UploadFileNewVO> queryHeadPortrait(SessionFactory factory) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					//TODO 测试使用  获取登录的用户名为： 
					String userName="测试11";
					//根据登录的用户名查询Code
					QueryUserDTO queryUserDTO=new QueryUserDTO();
					queryUserDTO.setLoginName(userName);
					List<UserVO> userVOList=userMapper.queryUser(queryUserDTO);	
					String userCode="";
					if(userVOList!=null && userVOList.size()>0) {
						userCode=userVOList.get(0).getCode();
					}
					UploadFileNewDTO uploadFileNewDTO=new UploadFileNewDTO();
					uploadFileNewDTO.setForeignkey(userCode);
					uploadFileNewDTO.setStatus("1");
					List<UploadFileNewVO> fileList=userMapper.queryHeadPortrait(uploadFileNewDTO);
					return fileList;
				}

				//返回key
				@TaskAnnotation("createDataKey")
				@Override
				public String createDataKey(SessionFactory factory, DataDicDTO dataDicDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					//根据数据字典dicParent查询最大序列号
					List<DataDicVO> dataList=userMapper.queryMaxDataKey(dataDicDTO);
					String finalKey="L"+dataDicDTO.getDicParent()+"0001";
					boolean flag=false;
					int maxNum=0;
		            if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null) {
		            	//截取最后4位
		            	for(int i=0;i<dataList.size();i++) {
			            	if(dataList.get(i).getDicKey().length()>4) {
			            		flag=true;			            		
			            		if(Integer.parseInt(dataList.get(i).getDicKey().substring(dataList.get(i).getDicKey().length() - 4))>maxNum) {
			            		 maxNum=Integer.parseInt(dataList.get(i).getDicKey().substring(dataList.get(i).getDicKey().length() - 4));
			            		}
			            	}
		            	}
		            	if(flag) {
		            		if(1<=maxNum && maxNum<10) {
		            		 finalKey="L"+dataDicDTO.getDicParent()+"000"+String.valueOf(maxNum);
		                    }else if(10<=maxNum && maxNum<100) {
		                     finalKey="L"+dataDicDTO.getDicParent()+"00"+String.valueOf(maxNum);
		                    }else if(100<=maxNum && maxNum<1000){
		                     finalKey="L"+dataDicDTO.getDicParent()+"0"+String.valueOf(maxNum);
		                    }else {
		                     finalKey="L"+dataDicDTO.getDicParent()+String.valueOf(maxNum);	
		                    }
		            	}else {
		            		finalKey="L"+dataDicDTO.getDicParent()+"0001";	
		            	}
		            }else {
		            	finalKey="L"+dataDicDTO.getDicParent()+"0001";
		            }
					return finalKey;
				}
		
					
}