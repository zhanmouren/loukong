package com.koron.inwlms.bean.DTO.leakageControl;

import java.util.List;

public class PolicyDTO {
	
	private PolicySchemeDTO policySchemeDTO;
	
	private List<PolicySettingDTO> policySettingDTOList;
	
	public PolicySchemeDTO getPolicySchemeDTO() {
		return policySchemeDTO;
	}
	public void setPolicySchemeDTO(PolicySchemeDTO policySchemeDTO) {
		this.policySchemeDTO = policySchemeDTO;
	}
	public List<PolicySettingDTO> getPolicySettingDTOList() {
		return policySettingDTOList;
	}
	public void setPolicySettingDTOList(List<PolicySettingDTO> policySettingDTOList) {
		this.policySettingDTOList = policySettingDTOList;
	}
	
	

}
