package com.common.util;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailUtil {
	private static final String MAIL_FROM = "659777399@qq.com";
	private static final String MAIL_HOST = "smtp.qq.com" ; //QQ 邮件服务器"
	private static final String MAIL_TOKEN = "wprchrzvusyqbbcg" ; //QQ 邮件服务器token"

	public static boolean sendMail(String toMail,String text){
	    // 获取系统属性
	    Properties properties = System.getProperties();
	    // 设置邮件服务器
	    properties.setProperty("mail.smtp.host", MAIL_HOST);
	    properties.put("mail.smtp.auth", "true");
	    MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    sf.setTrustAllHosts(true);
	    properties.put("mail.smtp.ssl.enable", "true");
	    properties.put("mail.smtp.ssl.socketFactory", sf);
	    // 获取默认session对象
	    Session session = Session.getDefaultInstance(properties,new Authenticator(){
	       public PasswordAuthentication getPasswordAuthentication()
	       {
	          return new PasswordAuthentication(MAIL_FROM, MAIL_TOKEN); //发件人邮件用户名、密码
	       }
	    });

	    try{
	       // 创建默认的 MimeMessage 对象
	       MimeMessage message = new MimeMessage(session);
	       // Set From: 头部头字段
	       message.setFrom(new InternetAddress(MAIL_FROM));
	       // Set To: 头部头字段
	       message.addRecipient(Message.RecipientType.TO,
	                                new InternetAddress(toMail));
	       // Set Subject: 头部头字段
	       message.setSubject("your new password");
	       // 设置消息体
	       message.setText(text);
	       // 发送消息
		   Transport.send(message);
	    }catch (MessagingException mex) {
	       mex.printStackTrace();
	    }
	    return  true ;
	}
	
	
}
