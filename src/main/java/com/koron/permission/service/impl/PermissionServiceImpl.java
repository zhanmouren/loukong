package com.koron.permission.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.service.TreeService;
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
import com.koron.permission.bean.DTO.TblRoleDTO;
import com.koron.permission.bean.DTO.TblRoleOpDTO;
import com.koron.permission.bean.DTO.TblRoleRangeValueDTO;
import com.koron.permission.bean.DTO.TblRoleUserDTO;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.TblAppCatalogueVO;
import com.koron.permission.bean.VO.TblAppOPVO;
import com.koron.permission.bean.VO.TblAppVO;
import com.koron.permission.bean.VO.TblOpCodeVO;
import com.koron.permission.bean.VO.TblRoleVO;
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
	public Integer addOperate(SessionFactory factory, TblOperationDTO tblOperationDTO) {
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
		  child.setType(10);
		  int type=10;
		  LongTreeBean parent= treeService.getNode(factory, type, tblOperationDTO.getForeignkey());
		  if(parent==null) {
			  addRes=-3;
			  return addRes;
		  }else {
			  //生成根节点
		      LongTreeBean longTreeBean =treeService.add(factory, parent, child);
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
		    if(deleteRes==-1) {
		    	return -1;
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
			Integer addRes=mapper.addRoleRangeValue(tblRoleRangeValueDTO);
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
			Integer updateRes=mapper.updateRoleRangeValue(tblRoleRangeValueDTO);
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

}
