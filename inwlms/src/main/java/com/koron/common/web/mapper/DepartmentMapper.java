package com.koron.common.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;

import com.koron.common.bean.StaffBean;
import com.koron.common.web.bean.DepartmentTreeBean;
//@EnvSource("_common")
public interface DepartmentMapper {
//	@Select("select tbltree.seq,tbltree.parentmask,tbldepartment.* from tbltree \n" + 
//			"inner join tbldepartment on tbltree.foreignkey=tbldepartment.id \n" + 
//			"inner join (\n" + 
//			"	select tbltree.* from tbltree \n" + 
//			"where type = 1 and seq =#{id}) a on (tbltree.`seq` & ~((1 << (62 - a.`parentMask`-a.`mask`))-1)) = a.`seq`")
//	public List<DepartmentTreeBean> getDescendantByParentId(@Param("id") Long id);
//	@Select("select tbltree.seq,tbltree.parentmask,tbldepartment.* from tbltree \n" + 
//			"inner join tbldepartment on tbltree.foreignkey=tbldepartment.id \n" + 
//			"where (#{seq} & ~((1 << (62 - parentmask-mask))-1)) = seq\n" + 
//			"order by `seq`\n")
//	public List<DepartmentTreeBean> getPathById(@Param("seq") Long seq);
//	@Select("select a.id,a.name,a.code,b.name as departmentname from tblstaff a left join tbldepartment b on a.departmentid = b.id where b.`code` = #{department}")
//	public List<StaffBean> getStaffOfDep(@Param("department")String code);
//	@Select("select concat(a.name,'(',b.name,')') as name,a.code from tblstaff a left join tbldepartment b on a.departmentid = b.id")
//	public List<StaffBean> getStaffInfo();
//	@Select("select code,name as department from tbldepartment")
//	public List<DepartmentTreeBean> getDepartmentInfo();
}