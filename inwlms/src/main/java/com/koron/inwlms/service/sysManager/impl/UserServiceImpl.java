package com.koron.inwlms.service.sysManager.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.SpecialDayDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.RoleAndUserVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.mapper.master.sysManager.UserMapper;
import com.koron.inwlms.service.sysManager.UserService;

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
	@TaskAnnotation("updateUser")
	@Override
	public Integer updateUser(SessionFactory factory, UserDTO userDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		userDTO.setUpdateBy("xiaozhan");
		userDTO.setUpdateTime(timeNow);
		Integer editResult=userMapper.updateUser(userDTO);
		return editResult;
	}
   
	//删除职员 2020/03/23
	@TaskAnnotation("deleteUser")
	@Override
	public Integer deleteUser(SessionFactory factory, UserDTO userDTO) {
		// TODO Auto-generated method stub
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
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		roleDTO.setCreateTime(timeNow);
		roleDTO.setUpdateBy("xiaozhan");
		roleDTO.setUpdateTime(timeNow);
		Integer addResult=userMapper.addNewRole(roleDTO);
		return addResult;
	}
    
	//修改角色属性  2020/03/23
	@TaskAnnotation("updateRoleAttr")
	@Override
	public Integer updateRoleAttr(SessionFactory factory, RoleDTO roleDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		roleDTO.setUpdateBy("xiaozhan");
		roleDTO.setUpdateTime(timeNow);
		Integer editResult=userMapper.updateRoleAttr(roleDTO);
		return editResult;
	}
	
	//删除角色属性  2020/03/23(删除角色前先判断有没有绑定职员)
	@TaskAnnotation("deleteRoleAttr")
	@Override
	public RoleMsgVO deleteRoleAttr(SessionFactory factory, RoleDTO roleDTO) {
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
			  roleMsgVO.setMessage("角色为："+userList.get(0).getRoleName()+"下存在职员,请先删除关系后，继续操作");
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
	@TaskAnnotation("deleteRoleUser")
	@Override
	public Integer deleteRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		//执行批量删除角色职员的操作
		Integer delResult=userMapper.deleteRoleUser(roleUserDTO.getRoleId(),roleUserDTO.getUserList());
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
		@TaskAnnotation("deleteDeptUser")
		@Override
		public Integer deleteDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO) {
			// TODO Auto-generated method stub
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			//执行批量删除角色职员的操作
			Integer delResult=userMapper.deleteDeptUser(deptUserDTO.getDepId(),deptUserDTO.getUserList());
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
					Timestamp timeNow = new Timestamp(System.currentTimeMillis());
					List<DataDicDTO> dataDicDTOList=new ArrayList<DataDicDTO>();
					//插入i条
					for(int i=0;i<dataDicDTO.getDataDicDTOList().size();i++) {
						DataDicDTO dataDicDTONew=new DataDicDTO();
						dataDicDTONew.setDicName(dataDicDTO.getDicName());
						dataDicDTONew.setDicParent(dataDicDTO.getDicParent());
						dataDicDTONew.setDicRemark(dataDicDTO.getDicRemark());
						dataDicDTONew.setDicKey(dataDicDTO.getDataDicDTOList().get(i).getDicKey());
						dataDicDTONew.setDicValue(dataDicDTO.getDataDicDTOList().get(i).getDicValue());
						if(dataDicDTO.getDataDicDTOList().get(i).getDicSeq()==null) {
							dataDicDTONew.setDicSeq(i);
						}else {
							dataDicDTONew.setDicSeq(dataDicDTO.getDataDicDTOList().get(i).getDicSeq());
						}
						dataDicDTONew.setCreateBy("小詹");
						dataDicDTONew.setCreateTime(timeNow);
						dataDicDTONew.setUpdateBy("小詹");
						dataDicDTONew.setUpdateTime(timeNow);
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
					return dataDicVOList;
				}
				//查询数据字典接口(查询明细信息主表) 2020/03/26
				@TaskAnnotation("queryMainDataDic")
				@Override
				public List<DataDicVO> queryMainDataDic(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					//只需要返回第一条主的信息就行
					List<DataDicVO> dataDicVOList=userMapper.queryMainDataDic(dataDicDTO);
					return dataDicVOList;
				}

			    //修改数据字典(通过parent,修改一条就要修改多条主的信息) 2020/03/27
				@TaskAnnotation("updateDicById")
				@Override
				public Integer updateDicById(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Timestamp timeNow = new Timestamp(System.currentTimeMillis());
					dataDicDTO.setUpdateBy("小詹");
					dataDicDTO.setUpdateTime(timeNow);
					Integer updateRes=userMapper.updateDicById(dataDicDTO);
					return updateRes;
				}
				
				//删除数据字典(通过parent，删除一条就要修改多条主的信息，还要实现批量) 2020/03/27
				@TaskAnnotation("deleteDicById")
				@Override
				public Integer deleteDicById(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<DataDicDTO> dataDicDTOList=new ArrayList<DataDicDTO>();
					for(int i=0;i<dataDicDTO.getDicParentList().size();i++) {
						DataDicDTO dataDicDTONew=new DataDicDTO();
						dataDicDTONew.setDicParent(dataDicDTO.getDicParentList().get(i));
						dataDicDTOList.add(dataDicDTONew);
					}
					Integer delRes=userMapper.deleteDicById(dataDicDTOList);
					return delRes;
				}
				
				//修改数据字典明细的操作  2020/03/27
				@TaskAnnotation("updateDicDetById")
				@Override
				public Integer updateDicDetById(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Timestamp timeNow = new Timestamp(System.currentTimeMillis());
					dataDicDTO.setUpdateBy("小詹");
					dataDicDTO.setUpdateTime(timeNow);
					Integer updateRes=userMapper.updateDicDetById(dataDicDTO);
					return updateRes;
				}
		      
				//删除数据字典(通过parent，删除一条就要修改多条主的信息，还要实现批量) 2020/03/27
				@TaskAnnotation("deleteDetDicById")
				@Override
				public Integer deleteDetDicById(SessionFactory factory, DataDicDTO dataDicDTO) {
					// TODO Auto-generated method stub
				    UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<DataDicDTO> dataDicDTOList=new ArrayList<DataDicDTO>();
					for(int i=0;i<dataDicDTO.getDicIdList().size();i++) {
					    DataDicDTO dataDicDTONew=new DataDicDTO();
						dataDicDTONew.setDicId(dataDicDTO.getDicIdList().get(i));
						dataDicDTOList.add(dataDicDTONew);
					}
					Integer delRes=userMapper.deleteDetDicById(dataDicDTOList);
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
					Timestamp timeNow = new Timestamp(System.currentTimeMillis());
					specialDayDTO.setCreateBy("小詹");
					specialDayDTO.setCreateTime(timeNow);
					specialDayDTO.setUpdateBy("小詹");
					specialDayDTO.setUpdateTime(timeNow);
				    insertRes=userMapper.addSpecialDate(specialDayDTO);
					return insertRes;					
				}

				//查询某年某月特征日 2020/03/27
				@TaskAnnotation("querySpecialDate")
				@Override
				public List<SpecialDayDTO> querySpecialDate(SessionFactory factory, SpecialDayDTO specialDayDTO) {
					// TODO Auto-generated method stub
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

				//根据日期删除特征日 2020/03/30
				@TaskAnnotation("deleteSpecialDate")
				@Override
				public Integer deleteSpecialDate(SessionFactory factory, SpecialDayDTO specialDayDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Integer deleteRes=userMapper.deleteSpecialDate(specialDayDTO);
					return deleteRes;
				}

				//根据日期修改特征日
				@TaskAnnotation("updateSpecialDate")
				@Override
				public Integer updateSpecialDate(SessionFactory factory, SpecialDayDTO specialDayDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Integer updateRes=userMapper.updateSpecialDate(specialDayDTO);
					return updateRes;
				}		

		
}