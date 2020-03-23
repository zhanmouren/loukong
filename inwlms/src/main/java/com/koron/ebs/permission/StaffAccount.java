package com.koron.ebs.permission;

import com.koron.common.bean.StaffBean;
import com.koron.ebs.permission.mybatis.SPIAccount;

public class StaffAccount extends SPIAccount{
	private StaffBean staff;
	public StaffAccount()
	{
		
	}
	public StaffAccount(StaffBean staff)
	{
		this.setStaff(staff);
	}
	public StaffBean getStaff() {
		return staff;
	}
	public void setStaff(StaffBean staff) {
		this.staff = staff;
	}
}