package com.koron.inwlms.bean.DTO.leakageControl;

public class RecommendStrategy {
	/**
	 * 铸铁管总长
	 */
	private Double lenghC;
	/**
	 * 非铸铁管总长
	 */
	private Double lenghNC;
	/**
	 * 用户数
	 */
	private Integer pNum;
	
	/**
	 * 按管长加权的平均管龄
	 */
	private Double age;
	/**
	 * 最小夜间流量发生时对应压力平均值
	 */
	private Double press;
	
	private Double a1;
	
	private Double a2;
	
	private Double a3;
	
	private Double b;
	
	private Double c;

	public Double getLenghC() {
		return lenghC;
	}

	public void setLenghC(Double lenghC) {
		this.lenghC = lenghC;
	}

	public Double getLenghNC() {
		return lenghNC;
	}

	public void setLenghNC(Double lenghNC) {
		this.lenghNC = lenghNC;
	}

	public Integer getpNum() {
		return pNum;
	}

	public void setpNum(Integer pNum) {
		this.pNum = pNum;
	}

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	public Double getPress() {
		return press;
	}

	public void setPress(Double press) {
		this.press = press;
	}

	public Double getA1() {
		return a1;
	}

	public void setA1(Double a1) {
		this.a1 = a1;
	}

	public Double getA2() {
		return a2;
	}

	public void setA2(Double a2) {
		this.a2 = a2;
	}

	public Double getA3() {
		return a3;
	}

	public void setA3(Double a3) {
		this.a3 = a3;
	}

	public Double getB() {
		return b;
	}

	public void setB(Double b) {
		this.b = b;
	}

	public Double getC() {
		return c;
	}

	public void setC(Double c) {
		this.c = c;
	}
	
	

}
