package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.leakageControl.PolicySettingDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicySchemeDTO;
import com.koron.inwlms.bean.VO.leakageControl.Policy;
import com.koron.inwlms.bean.VO.leakageControl.PolicySchemeVO;

/**
 * 
 * @author 刘刚
 *
 */
@Repository
@EnvSource("_default")
public interface PolicyMapper {

	List<Policy> queryPolicySetting(String policyCode);
	
	List<PolicySchemeVO> queryPolicyScheme(PolicySchemeDTO policySchemeDTO);
	
	Integer deletePolicyScheme(String code);
	
	Integer addPolicyScheme(PolicySchemeDTO policySchemeDTO);
	
	Integer addPolicySetting(PolicySettingDTO policyDTO);
	
	Integer updatePolicySetting(PolicySettingDTO policyDTO);
	
	Integer deletePolicySetting(String policyCode);
	
	
}
