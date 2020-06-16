package com.koron.permission.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.koron.ebs.mybatis.ADOSessionImpl;
import org.koron.ebs.mybatis.EnvSource;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.koron.permission.bean.DTO.TblDatabaseDTO;
import com.koron.permission.bean.DTO.TblOpDTO;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.TblOperationVO;
import com.koron.permission.bean.VO.TblRoleRangeValueVO;
import com.koron.permission.bean.VO.TblRoleVO;
import com.koron.permission.bean.VO.UserOperationVO;
import com.koron.permission.mapper.PermissionOuterMapper;
import com.koron.permission.service.PermissionOuterService;

public class PermissionOuterServiceImpl  implements PermissionOuterService{
	@Value("${app.filePath}")
	private String filePath;
	
	
	//静态变量存储
	private final Map<String,String> envMap=new HashMap<>();
	
	//根据用户名查询权限列表 2020-05-28
	@TaskAnnotation("getUserOPList")
	@Override
	public List<UserOperationVO> getUserOPList(SessionFactory factory,TblTenantDTO tblTenantDTO) {	
		String env=EnvSource.DEFAULT;
		try {
			ClassPathResource classPathResource = new ClassPathResource(filePath);
			InputStream inputStream =classPathResource.getInputStream();
		    JSONObject json = JSON.parseObject(IOUtils.toString(
		    		 inputStream,"utf-8"));
		    JSONArray jsonArray =json.getJSONArray(tblTenantDTO.get_tenantCode());
		    TblDatabaseDTO tblDatabaseDTO=new TblDatabaseDTO();
		    for(int i=0;i<jsonArray.size();i++) {
		    	tblDatabaseDTO.setDirver(jsonArray.getJSONObject(i).getString("driver")) ;
		    	tblDatabaseDTO.setPassword(jsonArray.getJSONObject(i).getString("password")) ;
		    	tblDatabaseDTO.setUrl(jsonArray.getJSONObject(i).getString("url")) ;
		    	tblDatabaseDTO.setUsername(jsonArray.getJSONObject(i).getString("username"));
		    }		    
		    String terant = tblTenantDTO.get_tenantCode();
			env = terant+EnvSource.DEFAULT;
			Properties prop = new Properties();
			prop.put(SessionFactory.PROPERTY_DRIVER,tblDatabaseDTO.getDirver());
			prop.put(SessionFactory.PROPERTY_URL,tblDatabaseDTO.getUrl());
			prop.put(SessionFactory.PROPERTY_USER,tblDatabaseDTO.getUsername());
			prop.put(SessionFactory.PROPERTY_PASSWORD,tblDatabaseDTO.getPassword());				
			if(envMap.get(terant)==null || "".equals(envMap.get(terant))) {
				new ADOSessionImpl().registeDBMap(env, prop);
				//设置到envMap里面
				envMap.put(terant,terant);
			}
		    
		 } catch (IOException e) {
			e.printStackTrace();
		}		
		PermissionOuterMapper mapper=factory.getMapper(PermissionOuterMapper.class,env);
		List<UserOperationVO> userOPList=mapper.getUserOPList(tblTenantDTO);
		return userOPList;
	}
	   //根据用户信息，获取用户所有角色
		@TaskAnnotation("getUserRoleList")
		@Override
		public List<TblRoleVO> getUserRoleList(SessionFactory factory,TblTenantDTO tenantDTO) {
				String env=EnvSource.DEFAULT;
				try {
					ClassPathResource classPathResource = new ClassPathResource(filePath);
					InputStream inputStream =classPathResource.getInputStream();
				    JSONObject json = JSON.parseObject(IOUtils.toString(
				    		 inputStream,"utf-8"));
				    JSONArray jsonArray =json.getJSONArray(tenantDTO.get_tenantCode());
				    TblDatabaseDTO tblDatabaseDTO=new TblDatabaseDTO();
				    for(int i=0;i<jsonArray.size();i++) {
				    	tblDatabaseDTO.setDirver(jsonArray.getJSONObject(i).getString("driver")) ;
				    	tblDatabaseDTO.setPassword(jsonArray.getJSONObject(i).getString("password")) ;
				    	tblDatabaseDTO.setUrl(jsonArray.getJSONObject(i).getString("url")) ;
				    	tblDatabaseDTO.setUsername(jsonArray.getJSONObject(i).getString("username"));
				    }		    
				    String terant = tenantDTO.get_tenantCode();
					env = terant+EnvSource.DEFAULT;
					
					Properties prop = new Properties();
					prop.put(SessionFactory.PROPERTY_DRIVER,tblDatabaseDTO.getDirver());
					prop.put(SessionFactory.PROPERTY_URL,tblDatabaseDTO.getUrl());
					prop.put(SessionFactory.PROPERTY_USER,tblDatabaseDTO.getUsername());
					prop.put(SessionFactory.PROPERTY_PASSWORD,tblDatabaseDTO.getPassword());				
					if(envMap.get(terant)==null || "".equals(envMap.get(terant))) {
						new ADOSessionImpl().registeDBMap(env, prop);
						//设置到envMap里面
						envMap.put(terant,terant);
					}
				    
				 } catch (IOException e) {
					e.printStackTrace();
				}
				PermissionOuterMapper mapper=factory.getMapper(PermissionOuterMapper.class,env);
				List<TblRoleVO> roleList=mapper.getUserRoleList(tenantDTO);
				return roleList;
			}

			//根据用户获取范围值
			@TaskAnnotation("getUserRangeValueList")
			@Override
			public List<TblRoleRangeValueVO> getUserRangeValueList(SessionFactory factory,TblTenantDTO tblTenantDTO) {	
				String env=EnvSource.DEFAULT;
				try {
					ClassPathResource classPathResource = new ClassPathResource(filePath);
					InputStream inputStream =classPathResource.getInputStream();
				    JSONObject json = JSON.parseObject(IOUtils.toString(
				    		 inputStream,"utf-8"));
				    JSONArray jsonArray =json.getJSONArray(tblTenantDTO.get_tenantCode());
				    TblDatabaseDTO tblDatabaseDTO=new TblDatabaseDTO();
				    for(int i=0;i<jsonArray.size();i++) {
				    	tblDatabaseDTO.setDirver(jsonArray.getJSONObject(i).getString("driver")) ;
				    	tblDatabaseDTO.setPassword(jsonArray.getJSONObject(i).getString("password")) ;
				    	tblDatabaseDTO.setUrl(jsonArray.getJSONObject(i).getString("url")) ;
				    	tblDatabaseDTO.setUsername(jsonArray.getJSONObject(i).getString("username"));
				    }		    
				    String terant = tblTenantDTO.get_tenantCode();
					env = terant+EnvSource.DEFAULT;
					
					Properties prop = new Properties();
					prop.put(SessionFactory.PROPERTY_DRIVER,tblDatabaseDTO.getDirver());
					prop.put(SessionFactory.PROPERTY_URL,tblDatabaseDTO.getUrl());
					prop.put(SessionFactory.PROPERTY_USER,tblDatabaseDTO.getUsername());
					prop.put(SessionFactory.PROPERTY_PASSWORD,tblDatabaseDTO.getPassword());				
					if(envMap.get(terant)==null || "".equals(envMap.get(terant))) {
						new ADOSessionImpl().registeDBMap(env, prop);
						//设置到envMap里面
						envMap.put(terant,terant);
					}
				    
				 } catch (IOException e) {
					e.printStackTrace();
				}
				PermissionOuterMapper mapper=factory.getMapper(PermissionOuterMapper.class,env);
				List<TblRoleRangeValueVO> rangeValueList=mapper.getUserRangeValueList(tblTenantDTO);
				return rangeValueList;
			}

			//根据用户获取是否存在操作权限
			@TaskAnnotation("getOPList")
			@Override
			public List<TblOperationVO> getOPList(SessionFactory factory, TblOpDTO tblOpDTO) {
				String env=EnvSource.DEFAULT;
				try {
					ClassPathResource classPathResource = new ClassPathResource(filePath);
					InputStream inputStream =classPathResource.getInputStream();
				    JSONObject json = JSON.parseObject(IOUtils.toString(
				    		 inputStream,"utf-8"));
				    JSONArray jsonArray =json.getJSONArray(tblOpDTO.get_tenantCode());
				    TblDatabaseDTO tblDatabaseDTO=new TblDatabaseDTO();
				    for(int i=0;i<jsonArray.size();i++) {
				    	tblDatabaseDTO.setDirver(jsonArray.getJSONObject(i).getString("driver")) ;
				    	tblDatabaseDTO.setPassword(jsonArray.getJSONObject(i).getString("password")) ;
				    	tblDatabaseDTO.setUrl(jsonArray.getJSONObject(i).getString("url")) ;
				    	tblDatabaseDTO.setUsername(jsonArray.getJSONObject(i).getString("username"));
				    }
				    
				    String terant = tblOpDTO.get_tenantCode();
					env = terant+EnvSource.DEFAULT;
					
					Properties prop = new Properties();
					prop.put(SessionFactory.PROPERTY_DRIVER,tblDatabaseDTO.getDirver());
					prop.put(SessionFactory.PROPERTY_URL,tblDatabaseDTO.getUrl());
					prop.put(SessionFactory.PROPERTY_USER,tblDatabaseDTO.getUsername());
					prop.put(SessionFactory.PROPERTY_PASSWORD,tblDatabaseDTO.getPassword());				
					if(envMap.get(terant)==null || "".equals(envMap.get(terant))) {
						new ADOSessionImpl().registeDBMap(env, prop);
						//设置到envMap里面
						envMap.put(terant,terant);
					}
				    
				 } catch (IOException e) {
					e.printStackTrace();
				}				
				PermissionOuterMapper mapper=factory.getMapper(PermissionOuterMapper.class,env);
				List<TblOperationVO> opList=mapper.getOPList(tblOpDTO);
				return opList;
			}
}
