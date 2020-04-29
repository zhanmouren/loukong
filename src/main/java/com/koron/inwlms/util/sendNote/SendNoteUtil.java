package com.koron.inwlms.util.sendNote;

public class SendNoteUtil {

	
	public static void sendNote(String phone,String content) throws Exception {
		
		if(phone == null || phone.length() != 11){
			throw new Exception("手机号码错误");
		}
		 if(content == null && content.trim().isEmpty()){
			 throw new Exception("短信发送内容为空");
		 }
		SmsServiceStub stub = new SmsServiceStub();
		SmsServiceStub.InsertDownSms insertDownSms = new SmsServiceStub.InsertDownSms();
		org.apache.axis2.databinding.types.soapencoding.String str = new org.apache.axis2.databinding.types.soapencoding.String();
		str.setString("[appkey]");
		insertDownSms.setUsername(str);
		str = new org.apache.axis2.databinding.types.soapencoding.String();
		str.setString("[appsecret]");
		insertDownSms.setPassword(str);
		str = new org.apache.axis2.databinding.types.soapencoding.String();
		str.setString("[应用自建分类]");
		insertDownSms.setBatch(str);
		str = new org.apache.axis2.databinding.types.soapencoding.String();
		str.setString("<sendbody><message><orgaddr></orgaddr><mobile>"+phone+"</mobile><content>"+content+"</content><sendtime></sendtime></message><publicContent></publicContent></sendbody>");
		insertDownSms.setSendbody(str);
		SmsServiceStub.InsertDownSmsResponse response = stub.insertDownSms(insertDownSms);
	}

}
