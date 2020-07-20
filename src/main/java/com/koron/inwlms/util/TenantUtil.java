package com.koron.inwlms.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.sysManager.DBInfoDTO;
import org.swan.bean.MessageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenantUtil {
	
	public static final Map<String,String> envMap=new HashMap<>();
	
	public String getTenantToken(String APPID,String tenantID,String cloudManagePlat,String APPVersion){
		String path = cloudManagePlat+"/port/tenant/token.htm?appCode="+APPID+"&tenantCode="+tenantID+"&version="+APPVersion;
		JsonObject ret =  InterfaceUtil.interfaceUtil(path);
		Gson gson = new Gson();
		MessageBean msg = gson.fromJson(ret, new TypeToken<MessageBean>(){}.getType());
		if(msg.getCode()!=0){
			return null;
		}
		try {
			Map data = (Map) msg.getData();
			return (String)data.get("token");
		}catch(Exception e){
			return null;
		}
	}
	
	public DBInfoDTO getDBInfo(String token,String cloudManagePlat,String privateKey){
		String path = cloudManagePlat+"/port/tenant/dbinfo.htm?token="+token;
		JsonObject ret =  InterfaceUtil.interfaceUtil(path);
		Gson gson = new Gson();
		MessageBean msg = gson.fromJson(ret, new TypeToken<MessageBean>(){}.getType());
		if(msg.getCode()!=0){
			return null;
		}
		try {
			//List<DBInfoDTO> data = gson.fromJson(msg.getData(), new TypeToken<List<DBInfoDTO>>(){}.getType());
			List<Map> data =(List<Map>)msg.getData();
			String  dbInfo = (String)data.get(0).get("dbinfo");
			String source = RSAUtil.decrypt(dbInfo, privateKey);
			DBInfoDTO dbd = gson.fromJson(source, new TypeToken<DBInfoDTO>(){}.getType());
			dbd.setMaxConnection(((Double)data.get(0).get("maxConnection")).intValue());

			return dbd;
		}catch(Exception e){
			return null;
		}
	}

}
