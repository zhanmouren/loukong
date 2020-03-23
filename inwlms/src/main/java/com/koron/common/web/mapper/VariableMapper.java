package com.koron.common.web.mapper;

import org.apache.ibatis.annotations.*;

/**
 * 系统变量
 * 
 * @author fangzw
 * CREATE TABLE `tblvariable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(100) NOT NULL,
  `value` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_variable_key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='系统常量'
 */
public interface VariableMapper {
	@Update("CREATE TABLE IF NOT EXISTS `tblvariable` (\n" + 
			"  `id` int(11) NOT NULL AUTO_INCREMENT,\n" + 
			"  `key` varchar(100) NOT NULL COMMENT '键',\n" + 
			"  `value` varchar(200) NOT NULL COMMENT '值',\n" + 
			"  PRIMARY KEY (`id`),\n" + 
			"  UNIQUE KEY `idx_variable_key` (`key`)\n" + 
			") COMMENT='系统常量'")
	public Integer init();
	
	@Select("select `value` from `tblvariable` where `key` = #{variable} limit 1")
	public String getVariable(@Param("variable") String variable);
	@Update("update `tblvariable` set `value`= #{value} where `key` = #{variable}")
	public Integer setVariable(@Param("variable") String variable, @Param("value") String value);
	@Insert("insert `tblvariable` (`key`,`value`) values (#{variable},#{value})")
	public Integer addVariable(@Param("variable") String variable, @Param("value") String value);	
}