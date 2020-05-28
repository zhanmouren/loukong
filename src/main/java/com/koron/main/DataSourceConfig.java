package com.koron.main;

import org.koron.ebs.mybatis.ADOSessionImpl;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
//@MapperScan(basePackages="com.koron.inwlms.mapper.master",sqlSessionFactoryRef = "masterSqlSessionFactory")
public class DataSourceConfig {

	  @Primary
	  @Bean(name = "primaryDataSource")
	  @Qualifier("primaryDataSource")
	  @ConfigurationProperties(prefix="spring.datasource.default") public
	  DataSource primaryDataSource() { DataSource ds =
	  DataSourceBuilder.create().build(); ADOSessionImpl session = new
	  ADOSessionImpl(); //开启/关闭-自动转换驼峰标识
	  SessionFactory.setMapUnderscoreToCamelCase(true);
	  session.registeDataSourceMap("_default", ds); return ds; }
	  
	 
	 @Bean(name = "secodnaryDataSource")
	 @Qualifier("secodnaryDataSource")
	 @ConfigurationProperties(prefix="spring.datasource.secodnary") public
	 DataSource secodnaryDataSource() { DataSource ds =
	 DataSourceBuilder.create().build(); ADOSessionImpl session = new
	 ADOSessionImpl(); //开启/关闭-自动转换驼峰标识
	 SessionFactory.setMapUnderscoreToCamelCase(true);
	 session.registeDataSourceMap("SEC", ds); return ds; }

}