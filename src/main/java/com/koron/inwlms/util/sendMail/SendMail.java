package com.koron.inwlms.util.sendMail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	// 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
	// PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
//  对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
	public static String myEmailAccount = "xxxxxxx@126.com";
	public static String myEmailPassword = "xxxxxxxx";
	
	// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
	public static String myEmailSMTPHost = "smtp.126.com";
	
	// 收件人邮箱（替换为自己知道的有效邮箱）
//	public static String receiveMailAccount = "xxxxxx@qq.com";
	
	/**
	 * 
	 * @param name 收件人
	 * @param theme 邮件主题
	 * @param content 邮件正文
	 * @param receiveMailAccount 收件人邮箱
	 * @throws Exception
	 */
	public static void sendMail(String name,String theme,String content,String receiveMailAccount) throws Exception {
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置        
		Properties props = new Properties();                    // 参数配置        
		props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）        
		props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址        
		props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
		
		// 2. 根据配置创建会话对象, 用于和邮件服务器交互        
		Session session = Session.getInstance(props);        
		// 设置为debug模式, 可以查看详细的发送 log        
		session.setDebug(true);         
		// 3. 创建一封邮件        
		MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount,name,theme,content);         
		// 4. 根据 Session 获取邮件传输对象        
		Transport transport = session.getTransport();
		
		transport.connect(myEmailAccount, myEmailPassword);         
		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人        
		transport.sendMessage(message, message.getAllRecipients());         
		// 7. 关闭连接        
		transport.close();



	}
	
	/**
	 * 
	 * @param session
	 * @param sendMail 发件人邮箱
	 * @param receiveMail 收件人邮箱
	 * @param name 收件人
	 * @param theme 邮件主题
	 * @param content 邮件正文
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String name,String theme,String content) throws Exception {
		// 1. 创建一封邮件        
		MimeMessage message = new MimeMessage(session);         
		// 2. From: 发件人        
		message.setFrom(new InternetAddress(sendMail, "昵称", "UTF-8"));         
		// 3. To: 收件人（可以增加多个收件人、抄送、密送）       
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, name, "UTF-8"));         
		// 4. Subject: 邮件主题        
		message.setSubject(theme, "UTF-8");         
		// 5. Content: 邮件正文（可以使用html标签）        
		message.setContent(content, "text/html;charset=UTF-8");            
		// 6. 设置发件时间        
		message.setSentDate(new Date());         
		// 7. 保存设置        
		message.saveChanges();         
		return message;

	}
	
	
}
