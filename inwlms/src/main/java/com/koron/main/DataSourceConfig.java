package com.koron.main;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.koron.ebs.mybatis.ADOSessionImpl;
import org.koron.ebs.mybatis.SessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
//@MapperScan(basePackages="com.koron.inwlms.mapper.master",sqlSessionFactoryRef = "masterSqlSessionFactory")
public class DataSourceConfig {
	
	  @Bean(name = "primaryDataSource")
	  
	  @Qualifier("primaryDataSource")
	  
	  @ConfigurationProperties(prefix="spring.datasource.default") public
	  DataSource primaryDataSource() { DataSource ds =
	  DataSourceBuilder.create().build(); ADOSessionImpl session = new
	  ADOSessionImpl(); //开启/关闭-自动转换驼峰标识
	  SessionFactory.setMapUnderscoreToCamelCase(true);
	  session.registeDataSourceMap("_default", ds); return ds; }
	  
	 
	  
	/*
	 * @Bean(name = "secodnaryDataSource")
	 * 
	 * @Qualifier("secodnaryDataSource")
	 * 
	 * @ConfigurationProperties(prefix="spring.datasource.secondary") public
	 * DataSource secodnaryDataSource() { DataSource ds =
	 * DataSourceBuilder.create().build(); ADOSessionImpl session = new
	 * ADOSessionImpl(); //开启/关闭-自动转换驼峰标识
	 * SessionFactory.setMapUnderscoreToCamelCase(true);
	 * session.registeDataSourceMap("SEC", ds); return ds; }
	 */

}