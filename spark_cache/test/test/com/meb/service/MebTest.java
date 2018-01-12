package test.com.meb.service;


import io.rong.RongCloud;
import io.rong.models.TokenResult;
import junit.framework.TestCase;

import com.base.ds.DataSourceManager;
import com.base.utils.ParaMap;
import com.meb.service.MebService;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.Reader;
import java.security.GeneralSecurityException;
import java.util.*;
public class MebTest  extends TestCase{
	
	private MebService mebService =  new MebService();
	
	public MebTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		System.out.println("test begin...");
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		System.out.println("test end...");
	}
	
	/**
	 * http://127.0.0.1:8080/spark_cache/data?module=meb&service=Meb&method=regMeb&account=13628484078&password=123456&longitude=32.95&latitude=112.32&age=10&sex=1&height=180&want_sex=0&age_rang=18-20
	 * @throws Exception
	 * @author YXD
	 */
	public void testRegByTel() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("account", "11128484078");
		inMap.put("password", "123456");
		inMap.put("longitude", "32.95");
		inMap.put("latitude", "112.32");
		inMap.put("age", "10");
		inMap.put("sex", "1");
		inMap.put("height", "180");
		inMap.put("email", "36564@qq.com");
		inMap.put("want_sex", "0");
		inMap.put("age_rang", "18-20");
		ParaMap outMap = mebService.regMeb(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	/**
	 * http://127.0.0.1:8080/spark_cache/data?module=meb&service=Meb&method=loginByAccount&account=13628484078&password=123456&longitude=32.95&latitude=112.32
	 * 登录
	 * @throws Exception
	 * @author YXD
	 */
	public void testLoginByAccount() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("account", "13628484078");
		inMap.put("password", "123456");
		ParaMap outMap = mebService.loginByAccount(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}

	/**
	 * http://ec2-18-221-181-42.us-east-2.compute.amazonaws.com:8080/spark_cache/data?module=meb&service=Meb&method=updateMebInfo&uid=20171227055013435318271168050208&pets=190&height=190&body_type=sb&education=190&drinking=190&smoking=190
	 * @throws Exception
	 * @author YXD
	 */
	public void testUpdateMebInfo() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("uid", "20171227055013435318271168050208");
		inMap.put("height", 190);
		inMap.put("body_type", "sb");
		inMap.put("education", 190);
		inMap.put("drinking", 190);
		inMap.put("smoking", 190);
		inMap.put("pets", 190);
		inMap.put("photo", "http://www.bauhfd");
		ParaMap outMap = mebService.updateMebInfo(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	/**
	 * http://ec2-18-221-181-42.us-east-2.compute.amazonaws.com:8080/spark_cache/data?module=meb&service=Meb&method=getMebInfo&uid=20171227055013435318271168050208
	 * @throws Exception
	 * @author YXD
	 */
	public void testGetMebInfo() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("uid", "20171227055013435318271168050208");
		ParaMap outMap = mebService.getMebInfo(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testUpdatePwdNoConfirm() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("uid", "20171227055013435318271168050208");
		ParaMap outMap = mebService.updatePwdNoConfirm(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testGetMebListByLogin() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("page_size", "10");
		inMap.put("page_index", "1");
		ParaMap outMap = mebService.getMebListByLogin(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testGetMebListByReg() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("page_size", "10");
		inMap.put("page_index", "1");
		ParaMap outMap = mebService.getMebListByReg(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public void testGetMebListByNear() throws Exception{
		ParaMap inMap = new ParaMap();
		inMap.put("longitude", "10");
		inMap.put("latitude", "1");
		inMap.put("page_size", "10");
		inMap.put("page_index", "1");
		ParaMap outMap = mebService.getMebListByNear(inMap);
		DataSourceManager.commit();
		System.out.println(outMap);
	}
	
	public static void main(String[] args) throws GeneralSecurityException {/*
	
		// 收件人电子邮箱
	      String to = "pfjhetg@qq.com";
	 
	      // 发件人电子邮箱
	      String from = "659777399@qq.com";
	 
	      // 指定发送邮件的主机
	      String host = "smtp.qq.com" ; //QQ 邮件服务器";
	 
	      // 获取系统属性
	      Properties properties = System.getProperties();
	 
	      // 设置邮件服务器
	      properties.setProperty("mail.smtp.host", host);
	      properties.put("mail.smtp.auth", "true");
	      MailSSLSocketFactory sf = new MailSSLSocketFactory();
	      sf.setTrustAllHosts(true);
	      properties.put("mail.smtp.ssl.enable", "true");
	      properties.put("mail.smtp.ssl.socketFactory", sf);
	      // 获取默认session对象
	      Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("659777399@qq.com", "wprchrzvusyqbbcg"); //发件人邮件用户名、密码
            }
	      });

	      try{
	         // 创建默认的 MimeMessage 对象
	         MimeMessage message = new MimeMessage(session);
	 
	         // Set From: 头部头字段
	         message.setFrom(new InternetAddress(from));
	 
	         // Set To: 头部头字段
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));
	 
	         // Set Subject: 头部头字段
	         message.setSubject("邮件主题");
	 
	         // 设置消息体
	         message.setText("你大爷 来看乌龟子！！！！！！");
	 
	         // 发送消息
	         for (int i = 0; i < 10; i++) {
		         Transport.send(message);
			 }
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	*/
		
	
		String appKey = "sfci50a7s4vki";//替换成您的appkey
		String appSecret = "3IDH5NeitVblq8";//替换成匹配上面key的secret
		
		Reader reader = null ;
		RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
				
		
		System.out.println("************************User********************");
		// 获取 Token 方法 
		TokenResult userGetTokenResult = null;
		try {
			userGetTokenResult = rongCloud.user.getToken("20171225183609667747673223771123", "", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("getToken:  " + userGetTokenResult.toString());
		
		
	}
}
