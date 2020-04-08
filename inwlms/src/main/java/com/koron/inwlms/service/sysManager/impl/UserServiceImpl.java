package com.koron.inwlms.service.sysManager.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.ADOConnection;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.service.TreeService;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuTreeDTO;
import com.koron.inwlms.bean.DTO.sysManager.OrgAndDeptDTO;
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
import com.koron.inwlms.mapper.sysManager.UserMapper;
import com.koron.inwlms.service.sysManager.UserService;
import com.koron.inwlms.util.RandomCodeUtil;
import com.koron.util.Constant;

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
		//随机获取uuid,赋值给Code
		userDTO.setCode(new RandomCodeUtil().getUUID32());	
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
		return addResult;
	}
	
	//查询职员 2020/03/19	
	@TaskAnnotation("queryUser")
	@Override
	public List<UserVO> queryUser(SessionFactory factory,QueryUserDTO userDTO) {		
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

	//根据角色Id加载角色人员接口 2020/03/24
	@TaskAnnotation("queryUserByRoleCode")
	@Override
	public List<UserVO> queryUserByRoleCode(SessionFactory factory, RoleDTO roleDTO) {			
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<UserVO> userList=userMapper.queryUserByRoleCode(roleDTO);
		return userList;
	}

	//根据角色Id加载角色接口 2020/03/24
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
		//把数据封装成List<RoleAndUserDTO>,方便遍历插入数据
		List<RoleAndUserDTO> roleAndUserDTOList=new ArrayList<RoleAndUserDTO>();
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		for(int i=0;i<roleUserDTO.getUserCodeList().size();i++) {
			RoleAndUserDTO roleUserDTONew=new RoleAndUserDTO();
			roleUserDTONew.setRoleCode(roleUserDTO.getRoleCode());
			roleUserDTONew.setUserCode(roleUserDTO.getUserCodeList().get(i));
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
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		//执行批量删除角色职员的操作
		Integer delResult=userMapper.deleteRoleUser(roleUserDTO.getRoleCode(),roleUserDTO.getUserCodeList());
		return delResult;
	}

	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员) 2020/03/24
	@TaskAnnotation("queryExceptRoleUser")
	@Override
	public List<UserVO> queryExceptRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO) {		
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<UserVO> userList=userMapper.queryExceptRoleUser(roleUserDTO);
		return userList;
	}
	
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25
		@TaskAnnotation("queryExceptDeptUser")
		@Override
		public List<UserVO> queryExceptDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO) {			
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
			for(int i=0;i<deptUserDTO.getUserCodeList().size();i++) {
				DeptAndUserDTO deptUserDTONew=new DeptAndUserDTO();
				deptUserDTONew.setDepCode(deptUserDTO.getDepCode());
				deptUserDTONew.setUserCode(deptUserDTO.getUserCodeList().get(i));
				deptUserDTONew.setCreateTime(timeNow);
				deptUserDTONew.setCreateBy("小詹");
				deptUserDTONew.setUpdateTime(timeNow);
				deptUserDTONew.setUpdateBy("小詹");
				deptUserDTOList.add(deptUserDTONew);
			}
			Integer addResult=userMapper.addDeptUser(deptUserDTOList);
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
					Timestamp timeNow = new Timestamp(System.currentTimeMillis());
					List<DataDicDTO> dataDicDTOList=new ArrayList<DataDicDTO>();
					//插入i条
					for(int i=0;i<dataDicDTO.getDataDicDTOList().size();i++) {
						DataDicDTO dataDicDTONew=new DataDicDTO();
						dataDicDTONew.setDicCn(dataDicDTO.getDicCn());
						dataDicDTONew.setDicEn(dataDicDTO.getDicEn());
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
					Timestamp timeNow = new Timestamp(System.currentTimeMillis());
					orgDeptDTO.setCreateTime(timeNow);
					orgDeptDTO.setUpdateTime(timeNow);
					orgDeptDTO.setCreateBy("小詹");
					orgDeptDTO.setUpdateBy("小詹");
					//部门正常使用状态0,禁用状态-1
					Integer finalRes=null;
					orgDeptDTO.setDepStatus(0);
					
					RandomCodeUtil randomCodeUtil=new RandomCodeUtil();
					//随机获取uuid,赋值给Code	
					String deptCode=randomCodeUtil.getUUID32();
					orgDeptDTO.setDepCode(deptCode);
					orgDeptDTO.setUpdateTime(timeNow);
					//先执行生成部门的操作(获取刚刚插入到部门表的Id)
					Integer depId=userMapper.addDeptNew(orgDeptDTO);
					//插入组织部门关系表
					OrgAndDeptDTO orgDeptDTONew=new OrgAndDeptDTO();
					orgDeptDTONew.setDepCode(deptCode);
					orgDeptDTONew.setOrgCode(orgDeptDTO.getOrgCode());
					orgDeptDTONew.setCreateTime(timeNow);
					orgDeptDTONew.setCreateBy("小詹");
					orgDeptDTONew.setUpdateBy("小詹");										
					orgDeptDTONew.setUpdateTime(timeNow);
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
					Timestamp timeNow = new Timestamp(System.currentTimeMillis());
					orgDeptDTO.setCreateTime(timeNow);
					orgDeptDTO.setCreateBy("小詹");
					orgDeptDTO.setUpdateBy("小詹");
					Integer finalRes=null;
					//部门正常使用状态0,禁用状态-1
					orgDeptDTO.setDepStatus(0);
					RandomCodeUtil randomCodeUtil=new RandomCodeUtil();
					//随机获取uuid,赋值给Code	
					String deptCode=randomCodeUtil.getUUID32();
					orgDeptDTO.setDepCode(deptCode);
					orgDeptDTO.setUpdateTime(timeNow);
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
				public Integer judgeExistUser(SessionFactory factory, DeptAndUserDTO deptAndUserDTO,int type,String foreignkey, boolean force) {					
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
							    UserServiceImpl userService=new UserServiceImpl();
								//删除数结构部门成功，执行删除部门的操作(物理删除),根据外键Code
							   Integer delDeptRes = userService.deleteTreeDept(factory, deptAndUserDTO);
							   if(delDeptRes!=null) {
								   if(delDeptRes==-1) {
									   //删除部门失败
									   userRes=-1;
								   }else {
									   //删除部门成功
									   userRes=0;
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

				//根据部门Id更新部门名称 2020/04/01
				@TaskAnnotation("updateTreeDept")
				@Override
				public Integer updateTreeDept(SessionFactory factory, DeptDTO deptDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					Timestamp timeNow = new Timestamp(System.currentTimeMillis());
					deptDTO.setUpdateBy("小詹");
					deptDTO.setUpdateTime(timeNow);
					Integer updateRes=userMapper.updateTreeDept(deptDTO);
					return updateRes;
				}

				//根据部门Code查询部门职员 2020/04/07
				@TaskAnnotation("queryDeptUser")
				@Override
				public List<UserVO> queryDeptUser(SessionFactory factory, DeptDTO deptDTO) {
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					List<UserVO> userList=userMapper.queryDeptUser(deptDTO);
					return userList;
				}
				
				//生成菜单(单条记录) 2020/04/08
				@TaskAnnotation("addMenu")
				public Integer addMenu(SessionFactory factory,MenuTreeDTO menuTreeDTO) {
					// TODO Auto-generated method stub
					UserMapper userMapper = factory.getMapper(UserMapper.class);
					MenuDTO menuDTO=new MenuDTO();
					menuDTO.setLinkAddress(menuTreeDTO.getLinkAddress());
					RandomCodeUtil randomCodeUtil=new RandomCodeUtil();
					Timestamp timeNow = new Timestamp(System.currentTimeMillis());
					//随机获取uuid,赋值给Code	
					String menuCode=randomCodeUtil.getUUID32();
					menuDTO.setMenuCode(menuCode);
					menuDTO.setModuleName(menuTreeDTO.getModuleName());
					menuDTO.setModuleNo(menuTreeDTO.getModuleNo());
					menuDTO.setCreateTime(timeNow);
					menuDTO.setCreateBy("小詹");
					menuDTO.setUpdateBy("小詹");
					menuDTO.setUpdateTime(timeNow);
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
}