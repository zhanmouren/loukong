package com.koron.inwlms.bean.VO.indexData;

import java.util.List;

/**
 * @date:2020-01-01
 * @author 小詹
 *
 */
public class TaskMsgVO {
    //对象编码
	public String objectCode;
	//报警类型
	public String alarmType;
	//报警详情
	public String content;
	//任务状态
	public String state;
	//是否发起
	public String initiate;
	
	//坐标
	private List<Double> smgeometry;
	
	public List<Double> getSmgeometry() {
		return smgeometry;
	}
	public void setSmgeometry(List<Double> smgeometry) {
		this.smgeometry = smgeometry;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public String getContent() {
		return content;
	}
	public String getState() {
		return state;
	}
	
	
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getInitiate() {
		return initiate;
	}
	public void setInitiate(String initiate) {
		this.initiate = initiate;
	}
	
}
