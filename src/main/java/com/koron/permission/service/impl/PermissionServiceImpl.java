package com.koron.permission.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.koron.ebs.mybatis.ADOConnection;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.mapper.TreeMapper;
import com.koron.common.web.service.TreeService;
import com.koron.inwlms.bean.DTO.sysManager.MenuTreeDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.mapper.sysManager.UserMapper;
import com.koron.inwlms.util.PageUtil;
import com.koron.permission.bean.DTO.TblAppCatalogueDTO;
import com.koron.permission.bean.DTO.TblAppDTO;
import com.koron.permission.bean.DTO.TblAppOPDTO;
import com.koron.permission.bean.DTO.TblOpCodeListDTO;
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
import com.koron.permission.bean.VO.TblOperateVO;
import com.koron.permission.bean.VO.TblOperationVO;
import com.koron.permission.bean.VO.TblRoleMenusVO;
import com.koron.permission.bean.VO.TblRoleVO;
import com.koron.permission.bean.VO.TblRoleZoneVO;
import com.koron.permission.mapper.PermissionMapper;
import com.koron.permission.service.PermissionService;
import com.koron.util.RandomCodeUtil;

@Service
public class PermissionServiceImpl implements PermissionService{	
	
		
	@Autowired
	private RandomCodeUtil randomCodeUtil;
	
	//添加应用信息 2020-06-01
	@TaskAnnotation("addApp")
	@Override
	public Integer addApp(SessionFactory factory,TblAppDTO tblAppDTO) {		
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
	    //应用code需要鉴别是否重复
		TblAppDTO tblApp=new TblAppDTO();
		tblApp.setAppCode(tblAppDTO.getAppCode());
		List<TblAppVO> appList=mapper.queryApp(tblApp);
		if(appList!=null && appList.size()>0) {
			return -2;
		}
		Integer addRes=mapper.addApp(tblAppDTO);
		return addRes;
	}
	//修改应用信息 2020-06-01
	@TaskAnnotation("updateApp")
	@Override
	public Integer updateApp(SessionFactory factory, TblAppDTO tblAppDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
		Integer updateRes=mapper.updateApp(tblAppDTO);
		return updateRes;
	}
	
    //删除应用信息  2020-06-01
	@TaskAnnotation("deleteApp")
	@Override
	public Integer deleteApp(SessionFactory factory, TblAppDTO tblAppDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
		Integer deleteRes=mapper.deleteApp(tblAppDTO);
		return deleteRes;
	}

	//查询应用信息  2020-06-01
	@TaskAnnotation("queryApp")
	@Override
	public List<TblAppVO> queryApp(SessionFactory factory, TblAppDTO tblAppDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);	 
		List<TblAppVO> appList=mapper.queryApp(tblAppDTO);
		return appList;
	}

	//生成操作节点
	@TaskAnnotation("addOperate")
	@Override
	public synchronized Integer addOperate(SessionFactory factory, TblOperationDTO tblOperationDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
		//生成code
		String opCode=randomCodeUtil.getUUID32();
		tblOperationDTO.setOpCode(opCode);
	    //先生成操作节点
		Integer addRes=mapper.addOperate(tblOperationDTO);
		if(addRes==-1) {
			addRes=-2;
			return addRes;
		}
		//树状关系中插入一条记录
		TreeService treeService  =new TreeService();
		 //组装child,主要两个参数，一个type，一个是foreignkey	
		  LongTreeBean child=new LongTreeBean();
		  child.setForeignkey(opCode);
		  child.setType(1);
		  int type=1;
		//  LongTreeBean parent= treeService.getNode(factory, type, tblOperationDTO.getForeignkey());
		  LongTreeBean parent= treeService.getNode(factory,1,tblOperationDTO.getForeignkey());
//		  LongTreeBean parent=ADOConnection.runTask(tblOperationDTO.getEnv(),treeService, "getNode", LongTreeBean.class,type, tblOperationDTO.getForeignkey());
		  if(parent==null) {
			  addRes=-3;
			  return addRes;
		  }else {
			  //生成子节点
		      LongTreeBean longTreeBean =treeService.add(factory, parent, child);
//		      LongTreeBean longTreeBean=ADOConnection.runTask(tblOperationDTO.getEnv(),treeService, "addNode", LongTreeBean.class,parent, child);		  	
		      if(longTreeBean==null) {
		    	  addRes=-4;
		      }else {
		    	  addRes=1; 
		      }
		  }
		  return addRes;
	}
	

	//批量删除操作节点
	@TaskAnnotation("deleteOperate")
	@Override
	public Integer deleteOperate(SessionFactory factory, TblOpCodeListDTO tblOpCodeListDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 		
		List<String> codeList=new ArrayList<>();
		//先批量删除操作节点
		Integer deleteRes=mapper.deleteOperate(tblOpCodeListDTO.getOpCodeList());		
		//批量删除树形结构的关系
		deleteRes=mapper.deleteOperateTree(tblOpCodeListDTO.getOpCodeList());
		return deleteRes;
	}

	//修改操作节点信息
	@TaskAnnotation("updateOperate")
	@Override
	public Integer updateOperate(SessionFactory factory, TblOperationDTO tblOperationDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 	
		Integer   updateRes =mapper.updateOperate(tblOperationDTO);
		return updateRes;
	}
	
    //添加应用-操作关系  2020-06-02
	@TaskAnnotation("addAppOP")
	@Override
	public Integer addAppOP(SessionFactory factory, TblAppOPDTO tblAppOPDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
		//添加的时候判断是否存在appCode(防止滥数据)
		TblAppDTO tblAppDTO=new TblAppDTO();
		tblAppDTO.setAppCode(tblAppOPDTO.getAppCode());
		List<TblAppVO> appList=mapper.queryApp(tblAppDTO);
		if(appList!=null && appList.size()>0) {
			
		}else {
			return -2;
		}
		//添加的时候判断是否存在opCode
		for(int i=0;i<tblAppOPDTO.getOpCodeList().size();i++) {
		 List<TblOpCodeVO> opCodeList=mapper.queryOpCode(tblAppOPDTO.getOpCodeList().get(i));
		 if(opCodeList!=null && opCodeList.size()>0) {
				
		 }else {
			return -3;
		 }
	   }    
		//添加的时候判断应用-操作关系表是否已经存在相关关系了
        List<TblAppOPVO> queryAppOpList=mapper.queryAppOp(tblAppOPDTO);
        if(queryAppOpList!=null && queryAppOpList.size()>0) {
        	return -4;
        }
        //添加应用-操作(批量)关系
        //制造数据
        List<TblAppOPDTO> appOpList=new ArrayList<>();
        for(int x=0;x<tblAppOPDTO.getOpCodeList().size();x++) {
        	TblAppOPDTO tblAppOP=new TblAppOPDTO();
        	tblAppOP.setAppCode(tblAppOPDTO.getAppCode());
        	tblAppOP.setOpCode(tblAppOPDTO.getOpCodeList().get(x));
        	appOpList.add(tblAppOP);
        }
		Integer  addRes =mapper.addAppOP(appOpList);
		return addRes;
	}

	//删除(一)应用-操作(多)关系  2020-06-02
	@TaskAnnotation("deleteAppOp")
	@Override
	public Integer deleteAppOp(SessionFactory factory, TblAppOPDTO tblAppOPDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
		Integer delRes=mapper.deleteAppOp(tblAppOPDTO.getAppCode(),tblAppOPDTO.getOpCodeList());
		return delRes;
	}

	//添加角色
	@TaskAnnotation("addRole")
	@Override
	public Integer addRole(SessionFactory factory, TblRoleDTO tblRoleDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);	 
		//添加的时候查询appCode是否存在
		TblAppDTO tblAppDTO=new TblAppDTO();
		tblAppDTO.setAppCode(tblRoleDTO.getApp());
		List<TblAppVO> appList=mapper.queryApp(tblAppDTO);
         if(appList!=null && appList.size()>0) {
			
		}else {
			return -2;
		}
		tblRoleDTO.setRoleCode(randomCodeUtil.getUUID32());
		Integer addRes=mapper.addRole(tblRoleDTO);
		return addRes;
	}

	//修改角色属性
	@TaskAnnotation("updateRole")
	@Override
	public Integer updateRole(SessionFactory factory, TblRoleDTO tblRoleDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
		Integer updateRes=mapper.updateRole(tblRoleDTO);
		return updateRes;
	}
	
    //删除角色
	@TaskAnnotation("deleteRole")
	@Override
	public Integer deleteRole(SessionFactory factory, TblRoleDTO tblRoleDTO) {
		 PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
		//删除角色-用户中关于该角色
	     Integer deleteRes=mapper.deleteRoleUser(tblRoleDTO.getRoleCode());		
		//删除角色-操作中关于该角色
	     deleteRes=mapper.deleteRoleOp(tblRoleDTO.getRoleCode());		
	    //删除角色-数据范围关于该角色
	     deleteRes=mapper.deleteRoleRange(tblRoleDTO.getRoleCode());			
		//删除角色-组织中关于该角色的
	     deleteRes=mapper.deleteRoleOrg(tblRoleDTO.getRoleCode()); 
	    //最终才删除角色
	     deleteRes=mapper.deleteRole(tblRoleDTO.getRoleCode()); 
		 return deleteRes;
	}

	//查询所有角色
	@TaskAnnotation("queryAllRole")
	@Override
	public List<TblRoleVO> queryAllRole(SessionFactory factory,TblTenantDTO tblTenantDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);	 
		List<TblRoleVO> roleList=mapper.queryAllRole(tblTenantDTO);
		return roleList;
	}
    //添加(一)角色-用户(多)关系
	@TaskAnnotation("addRoleUser")
	@Override
	public Integer addRoleUser(SessionFactory factory, TblRoleUserDTO tblRoleUserDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
		//封装数据
		List<TblRoleUserDTO> roleUserList=new ArrayList<>();
		for(int i=0;i<tblRoleUserDTO.getUserCodeList().size();i++) {
			TblRoleUserDTO tblRoleUser=new TblRoleUserDTO();
			tblRoleUser.setRoleCode(tblRoleUserDTO.getRoleCode());
			tblRoleUser.setUserCode(tblRoleUserDTO.getUserCodeList().get(i));
			roleUserList.add(tblRoleUser);
		}
		Integer addRes=mapper.addRoleUser(roleUserList);
		return addRes;
	}

	//添加(一)角色-操作(多)关系
	@TaskAnnotation("addRoleOP")
	@Override
	public Integer addRoleOP(SessionFactory factory, TblRoleOpDTO tblRoleOpDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);	
		//封装数据
		List<TblRoleOpDTO> roleOpList=new ArrayList<>();
		for(int i=0;i<tblRoleOpDTO.getOpCodeList().size();i++) {
			 TblRoleOpDTO tblRoleOp=new TblRoleOpDTO();
			 tblRoleOp.setRoleCode(tblRoleOpDTO.getRoleCode());
			 tblRoleOp.setOpCode(tblRoleOpDTO.getOpCodeList().get(i));
			 roleOpList.add(tblRoleOp);
		}	
		Integer addRes=mapper.addRoleOP(roleOpList);
		return addRes;
	}
	
    //删除(一)角色-操作(多)关系
	@TaskAnnotation("deleteRoleOP")
	@Override
	public Integer deleteRoleOP(SessionFactory factory, TblRoleOpDTO tblRoleOpDTO) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 	
		Integer delRes=mapper.deleteRoleOP(tblRoleOpDTO.getRoleCode(),tblRoleOpDTO.getOpCodeList());
		return delRes;
	}
	
	   //修改(一)角色-操作(多)关系
		@TaskAnnotation("updateRoleOP")
		@Override
		public Integer updateRoleOP(SessionFactory factory, TblRoleOpDTO tblRoleOpDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);			 
			//修改的时候先删除该角色之前的操作  (角色-操作表)
		    Integer deleteRes=mapper.deleteRoleOp(tblRoleOpDTO.getRoleCode());	
		    if(tblRoleOpDTO.getOpCodeList().size()<1) {
		    	return deleteRes;
		    }
			//封装数据
			List<TblRoleOpDTO> roleOpList=new ArrayList<>();
			for(int i=0;i<tblRoleOpDTO.getOpCodeList().size();i++) {
				 TblRoleOpDTO tblRoleOp=new TblRoleOpDTO();
				 tblRoleOp.setRoleCode(tblRoleOpDTO.getRoleCode());
				 tblRoleOp.setOpCode(tblRoleOpDTO.getOpCodeList().get(i));
				 roleOpList.add(tblRoleOp);
			}	
			Integer updateRes=mapper.addRoleOP(roleOpList);
			return updateRes;
		}
        //添加角色数据范围操作
		@TaskAnnotation("addRoleRangeValue")
		@Override
		public Integer addRoleRangeValue(SessionFactory factory, TblRoleRangeValueDTO tblRoleRangeValueDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			//封装数据
			List<TblRoleRangeValueDTO> tblRoleRangeValueList=new ArrayList<>();
			for(int i=0;i<tblRoleRangeValueDTO.getValueList().size();i++) {
				TblRoleRangeValueDTO tblRoleRangeValue=new TblRoleRangeValueDTO();
				tblRoleRangeValue.setRoleCode(tblRoleRangeValueDTO.getRoleCode());
				tblRoleRangeValue.setCatalogue(tblRoleRangeValueDTO.getCatalogue());
				tblRoleRangeValue.setValue(tblRoleRangeValueDTO.getValueList().get(i));
				tblRoleRangeValueList.add(tblRoleRangeValue);
			}
			Integer addRes=mapper.addRoleRangeValue(tblRoleRangeValueList);
			return addRes;
		}

		//添加域
		@TaskAnnotation("addAppCatalogue")
		@Override
		public Integer addAppCatalogue(SessionFactory factory, TblAppCatalogueDTO tblAppCatalogueDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			tblAppCatalogueDTO.setCode(randomCodeUtil.getUUID32());
			Integer addRes=mapper.addAppCatalogue(tblAppCatalogueDTO);
			return addRes;
		}

		//修改域
		@TaskAnnotation("updateAppCatalogue")
		@Override
		public Integer updateAppCatalogue(SessionFactory factory, TblAppCatalogueDTO tblAppCatalogueDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			Integer updateRes=mapper.updateAppCatalogue(tblAppCatalogueDTO);
			return updateRes;
		}

		//删除域
		@TaskAnnotation("deleteAppCatalogue")
		@Override
		public Integer deleteAppCatalogue(SessionFactory factory, TblAppCatalogueDTO tblAppCatalogueDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			Integer deleteRes=mapper.deleteAppCatalogue(tblAppCatalogueDTO);
			return deleteRes;
		}

		//修改角色数据范围操作
		@TaskAnnotation("updateRoleRangeValue")
		@Override
		public Integer updateRoleRangeValue(SessionFactory factory, TblRoleRangeValueDTO tblRoleRangeValueDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			//修改角色数据范围前，先删除 根据角色和目录
			Integer delRes=mapper.deleteRoleRangeValue(tblRoleRangeValueDTO);
			if(tblRoleRangeValueDTO.getValueList().size()<1) {
				return delRes;
			}
			//封装数据
			List<TblRoleRangeValueDTO> tblRoleRangeValueList=new ArrayList<>();
			for(int i=0;i<tblRoleRangeValueDTO.getValueList().size();i++) {
				TblRoleRangeValueDTO tblRoleRangeValue=new TblRoleRangeValueDTO();
				tblRoleRangeValue.setRoleCode(tblRoleRangeValueDTO.getRoleCode());
				tblRoleRangeValue.setCatalogue(tblRoleRangeValueDTO.getCatalogue());
				tblRoleRangeValue.setValue(tblRoleRangeValueDTO.getValueList().get(i));
				tblRoleRangeValueList.add(tblRoleRangeValue);
			}
			Integer updateRes=mapper.addRoleRangeValue(tblRoleRangeValueList);
			return updateRes;
		}
		
		//添加(一)组织角色(多)关系 
		@TaskAnnotation("addRoleOrg")
		@Override
		public Integer addRoleOrg(SessionFactory factory, TblOrgRoleDTO tblOrgRoleDTO) {
		      PermissionMapper mapper=factory.getMapper(PermissionMapper.class);	
					List<TblOrgRoleDTO> tblOrgRoleList=new ArrayList<>();
					for(int i=0;i<tblOrgRoleDTO.getRoleCodeList().size();i++) {
						TblOrgRoleDTO tblOrgRole=new TblOrgRoleDTO();
						tblOrgRole.setOrgCode(tblOrgRoleDTO.getOrgCode());
						tblOrgRole.setRoleCode(tblOrgRoleDTO.getRoleCodeList().get(i));
						tblOrgRoleList.add(tblOrgRole);
					}
					Integer addRes=mapper.addRoleOrg(tblOrgRoleList);	
					return addRes;
		}	
		
		//查询域
	   @TaskAnnotation("queryAppCatalogue")	
	   @Override
	   public List<TblAppCatalogueVO> queryAppCatalogue(SessionFactory factory,TblAppCatalogueDTO tblAppCatalogueDTO) {
			  PermissionMapper mapper=factory.getMapper(PermissionMapper.class);	
			  List<TblAppCatalogueVO> tblAppCatalogueList=mapper.queryAppCatalogue(tblAppCatalogueDTO);
			  return tblAppCatalogueList;
			
	   }
		
		//删除(一)组织角色(多)关系
		@TaskAnnotation("deleteManyRoleOrg")
		@Override
		public Integer deleteManyRoleOrg(SessionFactory factory, TblOrgRoleDTO tblOrgRoleDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);	
			List<TblOrgRoleDTO> tblOrgRoleList=new ArrayList<>();
			for(int i=0;i<tblOrgRoleDTO.getRoleCodeList().size();i++) {
				TblOrgRoleDTO tblOrgRole=new TblOrgRoleDTO();
				tblOrgRole.setOrgCode(tblOrgRoleDTO.getOrgCode());
				tblOrgRole.setRoleCode(tblOrgRoleDTO.getRoleCodeList().get(i));
				tblOrgRoleList.add(tblOrgRole);
			}
			Integer deleteRes=mapper.deleteManyRoleOrg(tblOrgRoleList);
		    return deleteRes;
					
	    }
				
		//删除(一)组织数据值(多)关系
		 @TaskAnnotation("deleteRoleRangeValue")
		 @Override
		 public Integer deleteRoleRangeValue(SessionFactory factory, TblRoleRangeValueDTO tblRoleRangeValueDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			Integer delRes=mapper.deleteRoleRangeValue(tblRoleRangeValueDTO);
			return delRes;
		}
		 
		/*******系统内接口*******/
		// 通过此接口根据角色查询人员
		@TaskAnnotation("queryUserByRole")
		@Override
		public PageListVO<List<UserVO>> queryUserByRole(SessionFactory factory, RoleDTO roleDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			List<UserVO> userList=mapper.queryUserByRoleCode(roleDTO);
			//查询总条数
			int rowNumber = mapper.getRoleUserCount(roleDTO);
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
		//查询角色菜单操作权限
		@TaskAnnotation("queryRoleMenuByRoleCode")
		@Override
		public List<TblRoleMenusVO> queryRoleMenuByRoleCode(SessionFactory factory, TblRoleAndOPDTO tblRoleAndOPDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			TreeMapper treeMapper=factory.getMapper(TreeMapper.class);
			List<TblRoleMenusVO> roleMenusList=mapper.queryRoleMenuByRoleCode(tblRoleAndOPDTO);	
			
			List<TblRoleMenusVO> finalMenuList=new ArrayList<>();
			 if(roleMenusList!=null && roleMenusList.size()>0) {
			    finalMenuList=roleMenusList.stream().filter(s->s.getParentMask()>0).collect(Collectors.toList());
			 }else {
				 return new ArrayList<TblRoleMenusVO>();
			 }
			 Map<Integer,List<TblRoleMenusVO>> treemap = new HashMap<>();	
		     Map<Integer,List<TblRoleMenusVO>> treeList=treeMenuSeq(finalMenuList,treemap);
		     
		     //新建一个最终的List去接收
		     List<TblRoleMenusVO> finalList=new ArrayList<>();
		     for (Map.Entry<Integer, List<TblRoleMenusVO>> entry : treeList.entrySet()) {			
					//System.out.println("Keyqwww = " + entry.getKey() + ", Value www= " + entry.getValue());
					if(entry.getKey()==1) {
						finalList.addAll(entry.getValue()); 
					}else {
						//位置
						int place=0;
						//获取当前value
						List<TblRoleMenusVO> finalListCur=entry.getValue();
						lxk:
						for(int i=0;i<finalList.size();i++) {						
							for(int j=0;j<finalListCur.size();j++) {
								if(finalList.get(i).getModuleCode().equals(finalListCur.get(j).getModuleCode())) {								
									place=i;
									break  lxk;
								}
							}
						}
						//删除i到i+finalList.size()的位置
							finalList.removeAll(finalListCur);
						//添加finalList1到指定位置		
						int fzVal=0;
						for(int j=place;j<place+finalListCur.size();j++) {
								  finalList.add(j,finalListCur.get(fzVal));
								  fzVal++;
						}
					}
					
			}
		    //拿到数据查找按钮权限,根据角色查询权限
		     if(finalList!=null && finalList.size()>0) {
				for(int i=0;i<finalList.size();i++) {					
					LongTreeBean longTreeBean=treeMapper.getBeanByForeignIdType(1, finalList.get(i).getForeignkey());
					if(longTreeBean!=null) {
						// 根据角色获取节点的直接下级节点(新菜单操作)
						List<TblRoleMenusVO> menuopList=treeMapper.getMenuAndOpChildren(longTreeBean,tblRoleAndOPDTO.getRoleCode());
						//查询该菜单所有的操作节点
						List<TblRoleMenusVO> allopList=treeMapper.getAllMenuAndOp(longTreeBean);
						if(menuopList!=null && menuopList.size()>0 && !"".equals(menuopList.get(0).getOpCodeName())) {
							String[] strArr = menuopList.get(0).getOpCodeName().split(";");
					
								List<String> finalOpList=new ArrayList<>();
								for(int p=0;p<strArr.length;p++) {
									finalOpList.add(strArr[p]);
								}
								finalList.get(i).setOpCodeNameList(finalOpList);
							
						}else {
							finalList.get(i).setOpCodeNameList(new ArrayList<>());
						}
						if(allopList!=null && allopList.size()>0 && !"".equals(allopList.get(0).getOpOwn())) {
							String[] strArrnew = allopList.get(0).getOpOwn().split(";");
							List<String> allOpList=new ArrayList<>();
							for(int f=0;f<strArrnew.length;f++) {
								allOpList.add(strArrnew[f]);
							}
							finalList.get(i).setOpOwnList(allOpList);
							
						}else {
							finalList.get(i).setOpOwnList(new  ArrayList<>());
						}
						
					}
				}
		     }
			 return finalList;
		}
		
		
		//同级菜单排序
		public static  Map<Integer,List<TblRoleMenusVO>>  treeMenuSeq(List<TblRoleMenusVO> finalMenuList,Map<Integer,List<TblRoleMenusVO>> treemap) {
			//	int firstnum=0;
				//取第一个数
				Integer firstParentmask=finalMenuList.get(0).getParentMask();
				TblRoleMenusVO lastTblRoleMenusVO=new TblRoleMenusVO();
				lastTblRoleMenusVO.setParentMask(firstParentmask);
				
				finalMenuList.add(lastTblRoleMenusVO);
				//循环数组
				//创建map集合
			    Map<Integer,List<TblRoleMenusVO>> map = new HashMap<>();	
			    //起始坐标
				int indexi=0;
				for(int q=1;q<finalMenuList.size();q++) {
					 if(finalMenuList.get(q).getParentMask()==firstParentmask) {
						List<TblRoleMenusVO> list=new ArrayList<TblRoleMenusVO>();
		                for(int j=0;j<q;j++) {
		                	if(j >= indexi) {                 	
		                   	 list.add(finalMenuList.get(j));
		                   	 map.put(q,list); 
		                	}                   	 
						 }	
		                //更改起始下标
		           	    indexi=q;
					}				
				}
				List<TblRoleMenusVO> finalSeqDeptList=new ArrayList<>();
				//装在List里面，从小到大排序
				List<Integer> sequenceList=new ArrayList<>();
				//先排序
				for (Map.Entry<Integer, List<TblRoleMenusVO>> entry : map.entrySet()) {
					//  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
					  sequenceList.add(entry.getValue().get(0).getSequence());
				}
				Collections.sort(sequenceList);
				
				 for(int z=0;z<sequenceList.size();z++) {
					for (Map.Entry<Integer, List<TblRoleMenusVO>> entry : map.entrySet()) {									 
						 if(entry.getValue().get(0).getSequence()==sequenceList.get(z)) {
							 finalSeqDeptList.addAll(entry.getValue());
							 break;
						 }
					}
				}
//				 System.out.println("输出结果为：");
//				 //输出经过该层级后的排序结果
//				for(int t=0;t<finalSeqDeptList.size();t++) {			
//					System.out.println(finalSeqDeptList.get(t));
//				}
				 int key=1;
		       
			    for (Map.Entry<Integer, List<TblRoleMenusVO>> entry : treemap.entrySet()) {   
			    	key=entry.getKey()+1;			
				}	    
			    treemap.put(key, finalSeqDeptList);		
				for (Map.Entry<Integer, List<TblRoleMenusVO>> entry : map.entrySet()) {
					List<TblRoleMenusVO> finalsonDeptList=new ArrayList<>();
					List<TblRoleMenusVO> menuList= entry.getValue();
					if(menuList!=null && menuList.size()>0) {
					  //去除第一个
						for(int j=1;j<menuList.size();j++) {
							finalsonDeptList.add(menuList.get(j));
						}
						
					}
					
					if(finalsonDeptList!=null &&  finalsonDeptList.size()>0) {				
						treeMenuSeq(finalsonDeptList,treemap);		 	   
					}

				}		
				return treemap;
				 				
			}
		
		//获取用户相关联操作(菜单按钮) 某个菜单下所有可以查看菜单
		@TaskAnnotation("getUserMenuOPList")
		@Override
		public List<TblRoleMenusVO> getUserMenuOPList(SessionFactory factory,TblRoleAndOPDTO tblRoleAndOPDTO) {
			
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			TreeMapper treeMapper=factory.getMapper(TreeMapper.class);
			List<TblRoleMenusVO> roleMenusList=mapper.queryRoleMenuByRoleCode(tblRoleAndOPDTO);	
			
			List<TblRoleMenusVO> finalMenuList=new ArrayList<>();
			 if(roleMenusList!=null && roleMenusList.size()>0) {
			    finalMenuList=roleMenusList.stream().filter(s->s.getParentMask()>0).collect(Collectors.toList());
			 }else {
				 return new ArrayList<TblRoleMenusVO>();
			 }
			 Map<Integer,List<TblRoleMenusVO>> treemap = new HashMap<>();	
		     Map<Integer,List<TblRoleMenusVO>> treeList=treeMenuSeq(finalMenuList,treemap);
		     
		     //新建一个最终的List去接收
		     List<TblRoleMenusVO> finalList=new ArrayList<>();
		     for (Map.Entry<Integer, List<TblRoleMenusVO>> entry : treeList.entrySet()) {			
					//System.out.println("Keyqwww = " + entry.getKey() + ", Value www= " + entry.getValue());
					if(entry.getKey()==1) {
						finalList.addAll(entry.getValue()); 
					}else {
						//位置
						int place=0;
						//获取当前value
						List<TblRoleMenusVO> finalListCur=entry.getValue();
						lxk:
						for(int i=0;i<finalList.size();i++) {						
							for(int j=0;j<finalListCur.size();j++) {
								if(finalList.get(i).getModuleCode().equals(finalListCur.get(j).getModuleCode())) {								
									place=i;
									break  lxk;
								}
							}
						}
						//删除i到i+finalList.size()的位置
							finalList.removeAll(finalListCur);
						//添加finalList1到指定位置		
						int fzVal=0;
						for(int j=place;j<place+finalListCur.size();j++) {
								  finalList.add(j,finalListCur.get(fzVal));
								  fzVal++;
						}
					}
					
			}
		    //根据登录的用户查询角色
		     List<TblRoleVO> roleList=mapper.getRoleByUser(tblRoleAndOPDTO.getUserCode());
		     List<String> roleCodeList=new ArrayList<>();
		     if(roleList!=null && roleList.size()>0) {
		    	 for(int p=0;p<roleList.size();p++) {
		    		 roleCodeList.add(roleList.get(p).getRoleCode());
		    	 }
		    	
		     }else {
				 return new ArrayList<TblRoleMenusVO>();
			 }
		    //拿到数据查找按钮权限
		     if(finalList!=null && finalList.size()>0) {
				for(int i=0;i<finalList.size();i++) {
					LongTreeBean longTreeBean=treeMapper.getBeanByForeignIdType(1, finalList.get(i).getForeignkey());
					if(longTreeBean!=null) {
						List<TblRoleMenusVO> menuopList=treeMapper.getMenuAndOpByUser(longTreeBean);
						if(menuopList!=null && menuopList.size()>0) {
							//过滤重复的
							Set<String> finalOpSet=new HashSet<>();							
							for(int n=0;n<menuopList.size();n++) {
								boolean containsRole=roleCodeList.contains(menuopList.get(n).getRoleCode());
								if(containsRole) {
									String[] strArr = menuopList.get(n).getOpCodeName().split(";");							
									for(int p=0;p<strArr.length;p++) {
										finalOpSet.add(strArr[p]);
									}
								}
							}
							
							List<String> finalOpList = new ArrayList<>(finalOpSet);
							finalList.get(i).setOpCodeNameList(finalOpList);
							
						}
						//查询该菜单所有的操作节点
						List<TblRoleMenusVO> allopList=treeMapper.getAllMenuAndOp(longTreeBean);
						if(allopList!=null && allopList.size()>0 && !"".equals(allopList.get(0).getOpOwn())) {
							String[] strArrnew = allopList.get(0).getOpOwn().split(";");
							List<String> allOpList=new ArrayList<>();
							for(int f=0;f<strArrnew.length;f++) {
								allOpList.add(strArrnew[f]);
							}
							finalList.get(i).setOpOwnList(allOpList);
							
						}else {
							finalList.get(i).setOpOwnList(new  ArrayList<>());
						}
					}
				}
		     }
		     finalList=finalList.stream().filter(s->s.getOpCodeNameList()!=null && s.getOpCodeNameList().size()>0).collect(Collectors.toList());
			 return finalList;
			
		}
		//删除(一)角色-用户(多)关系
		@TaskAnnotation("deleteUserByRole")
		@Override
		public Integer deleteUserByRole(SessionFactory factory, TblRoleUserDTO tblRoleUserDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			//执行批量删除角色职员的操作
			Integer delResult=mapper.deleteUserByRole(tblRoleUserDTO.getRoleCode(),tblRoleUserDTO.getUserCodeList());
			return  delResult; 
		}
		
		//加载分区树(打勾的权限)
		@TaskAnnotation("queryRoleZone")
		@Override
		public List<TblRoleZoneVO> queryRoleZone(SessionFactory factory, TblRoleZoneDTO tblRoleZoneDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			List<TblRoleZoneVO> roleZoneList=mapper.queryRoleZone(tblRoleZoneDTO);
			if(roleZoneList!=null && roleZoneList.size()>0) {
				for(int i=0;i<roleZoneList.size();i++) {
					if(roleZoneList.get(i).getValue()!=null && !"".equals(roleZoneList.get(i).getValue())) {
						roleZoneList.get(i).setBooltick(true);
					}
				}
			}		
			return roleZoneList;
		}
		
		//添加所有菜单节点
		@TaskAnnotation("addAllOperate")
		@Override
		public Integer addAllOperate(SessionFactory factory) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
			TreeMapper treeMapper = factory.getMapper(TreeMapper.class);
			//查询所有的菜单节点
			List<TblOperateVO> tblOperateVOList=mapper.queryAllOperation();
			if(tblOperateVOList!=null && tblOperateVOList.size()>0) {
				for(int i=0;i<tblOperateVOList.size();i++) {				
					LongTreeBean node=treeMapper.getBeanByForeignIdType(1,tblOperateVOList.get(i).getOpCode());
					if(node!=null) {
						//获取该节点下是否存在菜单
						 List<LongTreeBean> treeList=mapper.getNextMenuChildren(node);
						 
						 List<TblOperationDTO> tblOperationList=new ArrayList<>();
						//添加查询节点 
						 TblOperationDTO tblOperationq=new TblOperationDTO();
						 tblOperationq.setOpFlag(2);
						 tblOperationq.setOpStatus(0);
						 tblOperationq.setForeignkey(tblOperateVOList.get(i).getOpCode());
						 tblOperationq.setOpName("L102180004");
						 tblOperationq.setCreator("admin");
						 tblOperationq.setCrduFlag(4);
						 
						 tblOperationList.add(tblOperationq);
						 if(treeList!=null && treeList.size()>1) {
							 
						 }else {						
							 
							 //添加查询，添加，删除，修改接口
							 TblOperationDTO tblOperationa=new TblOperationDTO();
							 tblOperationa.setOpFlag(2);
							 tblOperationa.setOpStatus(0);
							 tblOperationa.setForeignkey(tblOperateVOList.get(i).getOpCode());
							 tblOperationa.setOpName("L102180001");
							 tblOperationa.setCreator("admin");
							 tblOperationa.setCrduFlag(1);
							 
							 TblOperationDTO tblOperatione=new TblOperationDTO();
							 tblOperatione.setOpFlag(2);
							 tblOperatione.setOpStatus(0);
							 tblOperatione.setForeignkey(tblOperateVOList.get(i).getOpCode());
							 tblOperatione.setOpName("L102180002");
							 tblOperatione.setCreator("admin"); 
							 tblOperatione.setCrduFlag(2);
							 
							 TblOperationDTO tblOperationd=new TblOperationDTO();
							 tblOperationd.setOpFlag(2);
							 tblOperationd.setOpStatus(0);
							 tblOperationd.setForeignkey(tblOperateVOList.get(i).getOpCode());
							 tblOperationd.setOpName("L102180003");
							 tblOperationd.setCreator("admin");
							 tblOperationd.setCrduFlag(3);
							 
							 tblOperationList.add(tblOperationa);
							 tblOperationList.add(tblOperatione);
							 tblOperationList.add(tblOperationd);
						 }	
						 for(int y=0;y<tblOperationList.size();y++) {
							 addOperateNew(factory,tblOperationList.get(y));
						 }
					}
				}
			}
			return 1;			
		}
		
		public static Integer addOperateNew(SessionFactory factory, TblOperationDTO tblOperationDTO) {
			PermissionMapper mapper=factory.getMapper(PermissionMapper.class);		 
			//生成code
			//String opCode=RandomCodeUtil.getUUID32();
			//tblOperationDTO.setOpCode(opCode);
			String opCode=tblOperationDTO.getForeignkey()+"004";
			if(tblOperationDTO.getCrduFlag()==4) {
				 opCode=tblOperationDTO.getForeignkey()+"004";
			}else if(tblOperationDTO.getCrduFlag()==1) {
				 opCode=tblOperationDTO.getForeignkey()+"001";
			}else if(tblOperationDTO.getCrduFlag()==2) {
				 opCode=tblOperationDTO.getForeignkey()+"002";
			}else{
				 opCode=tblOperationDTO.getForeignkey()+"003";
			}
			
		    //先生成操作节点
			Integer addRes=mapper.addOperate(tblOperationDTO);
			if(addRes==-1) {
				addRes=-2;
				return addRes;
			}
			//树状关系中插入一条记录
			TreeService treeService  =new TreeService();
			 //组装child,主要两个参数，一个type，一个是foreignkey	
			  LongTreeBean child=new LongTreeBean();
			  child.setForeignkey(opCode);
			  child.setType(1);
			  int type=1;
			  LongTreeBean parent= treeService.getNode(factory,1,tblOperationDTO.getForeignkey());
			  if(parent==null) {
				  addRes=-3;
				  return addRes;
			  }else {
				  //生成子节点
			      LongTreeBean longTreeBean =treeService.add(factory, parent, child);
			      if(longTreeBean==null) {
			    	  addRes=-4;
			      }else {
			    	  addRes=1; 
			      }
			  }
			  return addRes;
		}
		

}
