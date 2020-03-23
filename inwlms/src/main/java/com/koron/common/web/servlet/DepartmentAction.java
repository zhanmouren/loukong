package com.koron.common.web.servlet;

import org.swan.bean.MessageBean;

import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koron.common.bean.StaffBean;
import com.koron.common.web.bean.DepartmentTreeBean;
import com.koron.common.web.mapper.DepartmentMapper;
import com.koron.common.web.mapper.TreeMapper;
import com.koron.util.Constant;

@Controller
public class DepartmentAction {
//	@RequestMapping("/dep/json.htm")
//	@ResponseBody
//	public String departmentJson(@RequestParam("id") Integer[] ids, @RequestParam(value = "type", required = false) Integer type) {
//		try (SessionFactory factory = new SessionFactory()) {
//			DepartmentMapper mapper = factory.getMapper(DepartmentMapper.class);
//			TreeMapper treeMapper = factory.getMapper(TreeMapper.class);
//			boolean dcKey = type != null && (type & 0x1000) == 0x1000;// 是否只查有维键的
//
//			if (type == null) {
//				type = -1;
//			}
//			/**
//			 * 比较器，用来进行排序，去重
//			 */
//			TreeSet<DepartmentTreeBean> selectedSet = new TreeSet<>((o1, o2) -> {
//				long dist = o1.getSeq() - o2.getSeq();
//				return dist > 0 ? 1 : dist == 0 ? 0 : -1;
//			});
//
//			for (Integer objMap : ids) {
//				Integer id = objMap;
//				long seq = -1;
//				if (id == -1)
//					seq = 1l << 61;
//				else
//					seq = treeMapper.getBeanByForeignIdType(1, String.valueOf(id)).getSeq();
//
//				List<DepartmentTreeBean> list = mapper.getDescendantByParentId(seq);
//				// 去除类型不匹配的
//				for (DepartmentTreeBean departmentTreeBean : list) {
//					if ((departmentTreeBean.getFlag() & type) != 0) {
//						if (!dcKey || !departmentTreeBean.getDatacenterkey().equals(""))// 要求有维键
//						{
//							selectedSet.add(departmentTreeBean);
//						}
//					}
//				}
//				// 加上父级路径
//				selectedSet.addAll(mapper.getPathById(seq));
//			}
//			// 定义一个临时的堆栈
//			Stack<DepartmentTreeBean> stack = new Stack<>();
//			for (DepartmentTreeBean departmentTreeBean : selectedSet) {
//				if (stack.isEmpty()) {
//					stack.push(departmentTreeBean);
//				} else {
//					DepartmentTreeBean top = stack.peek();
//					while (!stack.isEmpty() && departmentTreeBean.getParentmask().intValue() <= top.getParentmask().intValue()) {
//						stack.pop();
//						if (!stack.isEmpty())
//							top = stack.peek();
//					}
//					departmentTreeBean.setParentId(top.getId());
//					stack.push(departmentTreeBean);
//				}
//			}
//			try {
//				ObjectMapper objMapper = new ObjectMapper();
//				objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//				return objMapper.writeValueAsString(selectedSet);
//			} catch (JsonProcessingException ex) {
//				ex.printStackTrace();
//			}
//		}
//		return "";
//	}
//	/**
//	 * 获取
//	 * @param departmentCode
//	 * @return
//	 */
//	@RequestMapping("/dep/staff.htm")
//	@ResponseBody
//	public String staffOfDepartment(@RequestParam("code") String departmentCode) {
//		try (SessionFactory factory = new SessionFactory()) {
//			DepartmentMapper mapper = factory.getMapper(DepartmentMapper.class);
//			List<StaffBean> staff = mapper.getStaffOfDep(departmentCode);
//			try {
//				ObjectMapper objMapper = new ObjectMapper();
//				objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//				return objMapper.writeValueAsString(staff);
//			} catch (JsonProcessingException ex) {
//				ex.printStackTrace();
//			}
//		}
//		return "";
//	}
//	@RequestMapping("/staffquery.htm")
//	@ResponseBody
//	public String query(@RequestParam("q") String name,HttpServletResponse  response){
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		MessageBean<List<StaffBean>> ret = new MessageBean<>();
//		ret.setCode(0);
//		ret.setDescription("获取成功");
//		List<StaffBean> list = new ArrayList<>();
//		ret.setData(list);
//		int count = 0;
//		for (Entry<String,StaffBean> staff : Constant.STAFF.entrySet()) {
//			if(staff.getKey().indexOf(name) != -1)
//			{
//				list.add(staff.getValue());
//				count++;
//			}
//			if(count == 200)
//				break;
//		}
//		return ret.toJson();
//	}
//	@RequestMapping("/departmentquery.htm")
//	@ResponseBody
//	public String departmentQuery(@RequestParam("q") String name)
//	{
//		MessageBean<List<DepartmentTreeBean>> ret = new MessageBean<>();
//		ret.setCode(0);
//		ret.setDescription("获取成功");
//		List<DepartmentTreeBean> list = new ArrayList<>();
//		ret.setData(list);
//		int count = 0;
//		for (Entry<String,DepartmentTreeBean> staff : Constant.DEPARTMENT.entrySet()) {
//			if(staff.getKey().indexOf(name) != -1)
//			{
//				list.add(staff.getValue());
//				count++;
//			}
//			if(count == 200)
//				break;
//		}
//		return ret.toJson();
//	}
}
