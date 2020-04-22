package com.koron.inwlms.bean.VO.sysManager;
/**
 * 批量导入的结果
 * @author xiaozhan
 * @date   2020/04/22
 *
 */
public class ImportUserResVO {
	//成功率
	private double successRate;
	//第几条数据(出现问题)
	private int  num;
	//登录名
	private String loginName;
	//工号
	private String workNo;
	//导入的条数
	private int importNum;
	//导入结果
	private int  Result;
	
	
	public String getWorkNo() {
		return workNo;
	}
	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}
	public int getResult() {
		return Result;
	}
	public void setResult(int result) {
		Result = result;
	}
	public double getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getImportNum() {
		return importNum;
	}
	public void setImportNum(int importNum) {
		this.importNum = importNum;
	}			

}
