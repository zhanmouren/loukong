package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.PolicySchemeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicySettingDTO;
import com.koron.inwlms.bean.VO.leakageControl.Policy;
import com.koron.inwlms.bean.VO.leakageControl.PolicySchemeVO;

import com.koron.inwlms.mapper.leakageControl.PolicyMapper;

@Service
public  class PolicyServiceImpl implements PolicyService{
	
	/**
	 * 查询控漏损策略设置信息(通过code或者默认展示的启用方案的信息)
	 */
	@TaskAnnotation("queryPolicySetting")
	@Override
	public List<Policy> queryPolicySetting(SessionFactory factory,String code){
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		List<Policy> policyList = mapper.queryPolicySetting(code);
		return policyList;
	}
	
	@TaskAnnotation("queryPolicyScheme")
	@Override
	public List<PolicySchemeVO> queryPolicyScheme(SessionFactory factory, PolicySchemeDTO policySchemeDTO){
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		List<PolicySchemeVO> policySchemeList = mapper.queryPolicyScheme(policySchemeDTO);
		return policySchemeList;
	}

	@TaskAnnotation("deletePolicyScheme")
	@Override
	public Integer deletePolicyScheme(SessionFactory factory,String code) {
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		Integer num = mapper.deletePolicyScheme(code);
		return num;
	}
	
	@TaskAnnotation("deletePolicySetting")
	@Override
	public Integer deletePolicySetting(SessionFactory factory,String code) {
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		Integer num = mapper.deletePolicySetting(code);
		return num;
	}
	
	@TaskAnnotation("updatePolicyScheme")
	@Override
	public Integer updatePolicyScheme(SessionFactory factory,PolicySchemeDTO policySchemeDTO) {
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		Integer num = mapper.updatePolicyScheme(policySchemeDTO);
		return num;
	}
	
	@TaskAnnotation("updatePolicySetting")
	@Override
	public Integer updatePolicySetting(SessionFactory factory,List<PolicySettingDTO> policyDTOList) {
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		//先删除所有设置
		Integer num = 0;
		mapper.deletePolicySetting(policyDTOList.get(0).getPolicyCode());
		for(PolicySettingDTO policySettingDTO : policyDTOList) {
			//添加设置
			num = mapper.addPolicySetting(policySettingDTO);	
		}
		
		return num;
	}
	
	@TaskAnnotation("addPolicyScheme")
	@Override
	public Integer addPolicyScheme(SessionFactory factory,PolicySchemeDTO policySchemeDTO) {
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		Integer num = mapper.addPolicyScheme(policySchemeDTO);
		return num;
	}
	
	@TaskAnnotation("addPolicySetting")
	@Override
	public Integer addPolicySetting(SessionFactory factory,PolicySettingDTO policyDTO) {
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		Integer num = mapper.addPolicySetting(policyDTO);
		return num;
	}


	
	
}
