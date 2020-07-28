package com.koron.indicator.task;

import com.koron.inwlms.bean.DTO.sysManager.DBInfoDTO;
import com.koron.inwlms.util.TenantUtil;
import com.koron.inwlms.util.kafka.ZoneKafkaConsumer;
import org.koron.ebs.mybatis.ADOSessionImpl;
import org.koron.ebs.mybatis.EnvSource;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * 数据源初始化
 * @author 刘刚
 *
 */

@SpringBootApplication
public class DataSourceInitialization {
	
	@Value("${postgresql.driver.name}")
	private String postgresqlDriver;
	
	@Autowired
    private ZoneKafkaConsumer kafkacus;

	@Value("${cloud.management.platform.url}")
	private String cloudManagePlat;
	
	@Value("${cloud.management.platform.privateKey}")
	private String privateKey;

	@Value("${cloud.management.appid}")
	private String APPID;

	@Value("${cloud.management.appversion}")
	private String APPVersion;

	@PostConstruct
	public void kafka() {
		List<String> tokenlist = new ArrayList<>();
		TenantUtil tenantUtil = new TenantUtil();
		String token1 = tenantUtil.getTenantToken(this.APPID, "4a1e7e2df9134cd297d03bbbc26df7f4",this.cloudManagePlat,this.APPVersion);
		tokenlist.add(token1);
		String token2 = tenantUtil.getTenantToken(this.APPID, "565ee7bdd75a4c6e937ce9b406b3aa85",this.cloudManagePlat,this.APPVersion);
		tokenlist.add(token2);	
		for(String token : tokenlist) {
			String env = "";
			String tenantID = "";
			if(token.equals(token1)) {
				tenantID = "mz";
				env = tenantID+ EnvSource.DEFAULT;
			}else {
				tenantID = "cp";
				env = tenantID+ EnvSource.DEFAULT;
			}
			DBInfoDTO dbd = tenantUtil.getDBInfo(token,this.cloudManagePlat,this.privateKey);
			if (dbd != null) {
				Properties prop = new Properties();
				prop.put(SessionFactory.PROPERTY_DRIVER, this.postgresqlDriver);
				prop.put(SessionFactory.PROPERTY_URL, dbd.getUrl());
				prop.put(SessionFactory.PROPERTY_USER, dbd.getUser());
				prop.put(SessionFactory.PROPERTY_PASSWORD, dbd.getPassword());
				prop.put("commandTimeout", 120);
				new ADOSessionImpl().registeDBMap(env, prop);
				//设置到envMap里面
				TenantUtil.envMap.put(tenantID, tenantID);
			}
		}
		kafkacus.consume();
	}

}
