package com.koron.inwlms.bean.VO.indexData;
/**
 * @date:2020-01-01
 * @author 小詹
 *
 */
public class TaskMsgVO {
    //对象名称
	public String ObjectName;
	//报警类型
	public String alarmType;
	//报警详情
	public String content;
	//任务状态
	public String state;
	public String getObjectName() {
		return ObjectName;
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
	public void setObjectName(String objectName) {
		ObjectName = objectName;
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
	
}
