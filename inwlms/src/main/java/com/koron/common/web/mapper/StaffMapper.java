package com.koron.common.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;

import com.koron.common.bean.StaffBean;
import com.koron.common.bean.UserBean;
@EnvSource("_common")
public interface StaffMapper {
	@Select("select * from tblstaff where loginid = #{loginid}")
	public StaffBean getStaffByLoginId(@Param("loginid") String loginId);
	@Select("select * from tblstaff where code = #{code}")
	public StaffBean getStaffByCode(@Param("code") String code);
	/**
	 * 执行登录动作
	 */
	@Select("select tblstaff.*,tbluser.loginname from tbluser " + 
			"left join tblstaff on tbluser.loginname=tblstaff.loginid " + 
			"where tbluser.loginname=#{name} and tbluser.password=#{password} and tblstaff.`status`=1")
	public StaffBean login(@Param("name") String name, @Param("password") String password);
	@Select("select * from tbluser where id = #{id}")
	public UserBean getUserById(@Param("id") int id);
	@Select("select * from tbluser where loginname = #{name}")
	public UserBean getUserByName(@Param("name") String name);

}
