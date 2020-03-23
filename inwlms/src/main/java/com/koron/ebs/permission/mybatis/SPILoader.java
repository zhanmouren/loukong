package com.koron.ebs.permission.mybatis;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.SqlTask;

import com.koron.ebs.permission.*;

public class SPILoader implements ResourceLoader {

	@Override
	public <T> boolean accept(String id, Class<T> t, int type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T get(String id, Class<T> t, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean acceptRelation(String id, int type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntityID> getRelation(String id, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends EntityID> getTypeClass(int type) {
		// TODO Auto-generated method stub
		return null;
	}
//	/**
//	 * 自动创建相关表
//	 */
//	static {
//		try(SessionFactory factory = new SessionFactory()){
//			try {
//				SPIMapper mapper = factory.getMapper(SPIMapper.class);
////				mapper.createEntityTable();
////				mapper.createRelationTable();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
//	}
//	
//	@Override
//	public <T> boolean accept(String id, Class<T> t, int type) {
//		if (t.isAssignableFrom(getTypeClass(type)))
//			return true;
//		else
//			return false;
//	}
//	/**<pre>
//	 * 根据id获取对应的实例.
//	 * 首先判断放会的类型是否和type对应。
//	 * 其次，实例化返回的对象。
//	 * 最后，从数据库获取tblspientity对应的数据，放入返回的对象并返回。
//	 * </pre>
//	 * @param id 实例的标识符
//	 * @param t 返回实例的对象
//	 * @param type 返回实例的类型
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T> T get(final String id, Class<T> t, final int type) {
//		if (!accept(id, t, type))
//			return null;
//		T ret = null;
//		try {
//			ret = (T) getTypeClass(type).newInstance();
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		try (SessionFactory factory = new SessionFactory();) {
//			
//			EntityID entity = factory.runTask(new SqlTask() {
//				@Override
//				public Object run(SessionFactory factory) {
//					SPIMapper mapper = factory.getMapper(SPIMapper.class);
//					EntID entId =  mapper.getEntity(id, type);
//					return entId;
//				}
//			}, EntityID.class);
//
//			if (entity == null)
//				return null;
//			// 复制属性到实际的对象中
//			try {
//				BeanUtils.copyProperties(ret, entity);
//			} catch (IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//			}
//			return ret;
//		}
//	}
//
//	@Override
//	public boolean acceptRelation(String id, int type) {
//		return true;
//	}
//
//	@Override
//	public List<EntityID> getRelation(String id, int type) {
//		try (SessionFactory factory = new SessionFactory();) {
//			List<String> list = factory.getMapper(SPIMapper.class).list(id, type);
//			List<EntityID> ret = new ArrayList<EntityID>();
//			for (String relationId : list) {
//				ret.add(get(relationId, getTypeClass(type & 0xFF), type & 0xFF));
//			}
//			return ret;
//		}
//	}
//	/**
//	 * 根据不同的值生成相对应的实例
//	 * @param type 类型
//	 * @see ResourceLoader#ENTITY_ACCOUNT_INT
//	 * @see ResourceLoader#ENTITY_GROUP_INT
//	 * @see ResourceLoader#ENTITY_ROLE_INT
//	 * @see ResourceLoader#ENTITY_OPERATION_INT
//	 * @see ResourceLoader#ENTITY_RULE_INT
//	 */
//	public Class<? extends EntityID> getTypeClass(int type) {
//		switch (type) {
//		case ResourceLoader.ENTITY_ACCOUNT_INT:
//			return StaffAccount.class;
//		case ResourceLoader.ENTITY_GROUP_INT:
//			return SPIGroup.class;
//		case ResourceLoader.ENTITY_ROLE_INT:
//			return SPIRole.class;
//		case ResourceLoader.ENTITY_OPERATION_INT:
//			return SPIOperation.class;
//		case ResourceLoader.ENTITY_RULE_INT:
//			return ContainRule.class;
//		default:
//			return null;
//		}
//	}
}
