package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.PolicySchemeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicySettingDTO;
import com.koron.inwlms.bean.VO.leakageControl.Policy;
import com.koron.inwlms.bean.VO.leakageControl.PolicySchemeVO;

/**
 * 
 * @author 刘刚
 *
 */
@Service
public interface PolicyService {

	List<Policy> queryPolicySetting(SessionFactory factory, String code);

	List<PolicySchemeVO> queryPolicyScheme(SessionFactory factory, PolicySchemeDTO policySchemeDTO);

	Integer deletePolicyScheme(SessionFactory factory, String code);

	Integer deletePolicySetting(SessionFactory factory, String code);

	Integer updatePolicySetting(SessionFactory factory, PolicySettingDTO policyDTO);

	Integer addPolicyScheme(SessionFactory factory, PolicySchemeDTO policySchemeDTO);

	Integer addPolicySetting(SessionFactory factory, PolicySettingDTO policyDTO);

	Integer updatePolicyScheme(SessionFactory factory, PolicySchemeDTO policySchemeDTO);

}
