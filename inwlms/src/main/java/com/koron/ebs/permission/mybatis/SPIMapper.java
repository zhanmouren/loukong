package com.koron.ebs.permission.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.koron.ebs.permission.EntityID;
import com.koron.ebs.permission.ResourceLoader;

public interface SPIMapper {
//	/**
//	 * 添加主元素
//	 * @param id
//	 * @param type 类型  
//	 * @return
//	 * @see {@link ResourceLoader#ENTITY_ACCOUNT_INT}
//	 */
//	public Integer addEntity(@Param("id")EntityID id,@Param("type") int type);
//	/**
//	 * 移除元素
//	 * @param id
//	 * @param type
//	 * @return
//	 */
//	public Integer removeEntity(@Param("id")String id,@Param("type") int type);
//	/**
//	 * 添加关系
//	 * @param source
//	 * @param target
//	 * @param type
//	 * @return
//	 */
//	public Integer addRelation(@Param("source")String source,@Param("target")String target,@Param("type")int type);
//	/**
//	 * 移除关系
//	 * @param source
//	 * @param target
//	 * @param type
//	 * @return
//	 */
//	public Integer removeRelation(@Param("source")String source,@Param("target")String target,@Param("type")int type);
//	/**
//	 * 获取元素
//	 * @param id
//	 * @param type
//	 * @return
//	 */
//	@Select("select `key` as id,`name`,`type`,`param` from `tblspientity` where `key` = #{id} and `type` = #{type}")
//	public EntID getEntity(@Param("id")String id,@Param("type")int type);
//	/**
//	 * 获取关系列表
//	 * @param source
//	 * @param type
//	 * @return
//	 */
//	public List<String> list(@Param("id")String source,@Param("type")int type);
//	/**
//	 * 获取列表(target获取source)
//	 * @param target
//	 * @param type
//	 * @return
//	 */
//	public List<String> listSource(@Param("id")String target,@Param("type")int type);
//	
//	/**
//	 * 获取关系
//	 * @param source
//	 * @param target
//	 * @param type
//	 * @return
//	 */
////	public List<SPIrelationBean> getRelation(@Param("source")String source,@Param("target")String target,@Param("type")int type);
////	@Update("CREATE TABLE IF NOT EXISTS `tblspientity` (\r\n" + 
////			"  `key` varchar(20) NOT NULL,\r\n" + 
////			"  `name` varchar(255) NOT NULL COMMENT '名称',\r\n" + 
////			"  `type` int(255) NOT NULL COMMENT '类型',\r\n" + 
////			"  `param` text COMMENT '参数',\r\n" + 
////			"  PRIMARY KEY (`key`,`type`)\r\n" + 
////			") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体对象'")
////	public void createEntityTable();
////	@Update("CREATE TABLE IF NOT EXISTS `tblspirelation` (\r\n" + 
////			"  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n" + 
////			"  `source` varchar(200) NOT NULL COMMENT '源ID',\r\n" + 
////			"  `type` int(255) NOT NULL COMMENT '关系类型',\r\n" + 
////			"  `target` varchar(200) NOT NULL COMMENT '关联ID',\r\n" + 
////			"  PRIMARY KEY (`id`),\r\n" + 
////			"  UNIQUE KEY `index2` (`type`,`source`,`target`) USING BTREE\r\n" + 
////			") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体关系'")
////	public void createRelationTable();
}
