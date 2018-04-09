package poseidon.lib.core.tool.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poseidon.lib.core.tool.system.OSTool;

/**
 * 简单邮件（不带附件的邮件）发送器
 */
public class EmailTool {

	private static Logger log = LoggerFactory.getLogger(EmailTool.class);

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public boolean sendTextMail(EmailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		EmailAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new EmailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public static boolean sendHtmlMail(EmailSenderInfo mailInfo) {
		log.info("开始发送邮件");
		// 判断是否需要身份认证
		EmailAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new EmailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipient(Message.RecipientType.TO, to);

			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			log.info("发送邮件成功");
			return true;
		} catch (MessagingException ex) {
			log.error("发送邮件失败", ex);
		}
		
		return false;
	}

	public static void sendMail(String info) {
		String lineInfo = "";
		StackTraceElement stack[] = (new Throwable()).getStackTrace();

		StackTraceElement s = stack[1];
		lineInfo = String.format("%s - (%s:%s)", s.getMethodName(), s.getFileName(), s.getLineNumber());

		try {
			// 这个类主要是设置邮件
			EmailSenderInfo mailInfo = new EmailSenderInfo();

			/*
			 * mailInfo.setMailServerHost("smtp.163.com");
			 * mailInfo.setMailServerPort("25"); mailInfo.setValidate(true);
			 * mailInfo.setUserName("hiknowmonitor@163.com");
			 * mailInfo.setPassword("HK6195420");// 您的邮箱密码
			 * mailInfo.setFromAddress("hiknowmonitor@163.com");
			 * mailInfo.setToAddress("27447985@qq.com");
			 */

			/*
			 * mailInfo.setMailServerHost("smtp.mxhichina.com");
			 * mailInfo.setMailServerPort("25"); mailInfo.setValidate(true);
			 * mailInfo.setUserName("postmaster@sgchexun.com");
			 * mailInfo.setPassword("Zjyousmtp1216");// 您的邮箱密码
			 * mailInfo.setFromAddress("postmaster@sgchexun.com");
			 * mailInfo.setToAddress("27447985@qq.com");
			 */

			mailInfo.setMailServerHost("smtp.mxhichina.com");
			mailInfo.setMailServerPort("25");
			mailInfo.setValidate(true);
			mailInfo.setUserName("reg@zjyou.cn");
			mailInfo.setPassword("Zxcvbnm1104");// 您的邮箱密码
			mailInfo.setFromAddress("reg@zjyou.cn");
			mailInfo.setToAddress("419498504@qq.com");

			mailInfo.setSubject("自驾友注册邮件");
			mailInfo.setContent("<h2>这是三国发送的邮件</h2><br/><font color=\"black\">" + lineInfo + "</font><br/><font color=\"red\">" + info + "</font>");
			// 这个类主要来发送邮件
			EmailTool.sendHtmlMail(mailInfo);// 发送html格式

			log.info("自驾友的邮件发送成功");
		} catch (Exception e) {
			log.error("发送邮件失败", e);
		}

		/*
		 * try { // 这个类主要是设置邮件 EmailSenderInfo mailInfo = new EmailSenderInfo();
		 * mailInfo.setMailServerHost("smtp.exmail.qq.com");
		 * mailInfo.setMailServerPort("465"); mailInfo.setValidate(true);
		 * mailInfo.setUserName("zjyou@hj-invest.cn");
		 * mailInfo.setPassword("zxcvbnm11041");// 您的邮箱密码
		 * mailInfo.setFromAddress("zjyou@hj-invest.cn");
		 * mailInfo.setToAddress("27447985@qq.com");
		 * mailInfo.setSubject("这是一封测试邮件"); mailInfo.setContent(
		 * "<h2>HiknowMessageConsumer系统发生错误</h2><br/><font color=\"black\">" +
		 * lineInfo + "</font><br/><font color=\"red\">" + info + "</font>"); //
		 * 这个类主要来发送邮件 EmailTool.sendHtmlMail(mailInfo);// 发送html格式 } catch
		 * (Exception e) { log.error("发送邮件失败", e); }
		 */

	}

	/*
	 * 得到并返回邮箱的登陆地址
	 * 
	 * @email 邮箱的地址
	 */
	public static String GetEmailUrl(String email) {

		int startIndex = email.lastIndexOf("@") + 1;
		int length = email.indexOf(".");
		String flag = email.substring(startIndex, length).toLowerCase();

		String url = "";

		switch (flag) {
		case "sina":
			url = "http://mail.sina.com.cn/";
			break;
		case "163":
			url = "http://mail.163.com/";
			break;
		case "yeah":
			url = "http://yeah.net/";
			break;
		case "126":
			url = "http://www.126.com/";
			break;
		case "yahoo":
			url = "http://mail.cn.yahoo.com/";
			break;
		case "gmail":
			url = "https://www.google.com/accounts/ServiceLogin?service=mail&passive=true&rm=false&continue=http%3A%2F%2Fmail.google.com%2Fmail%2F%3Fhl%3Dzh-CN%26ui%3Dhtml%26zy%3Dl&bsv=zpwhtygjntrz&scc=1&ltmpl=default&ltmplcache=2&hl=zh-CN";
			break;
		case "21cn":
			url = "http://mail.21cn.com/";
			break;
		case "sohu":
			url = "http://mail.sohu.com/";
			break;
		case "tom":
			url = "http://mail.tom.com/";
			break;
		case "sogou":
			url = "http://mail.sogou.com/";
			break;
		case "139":
			url = "http://mail.139.com/";
			break;
		case "263":
			url = "http://www.263.net/";
			break;
		case "hotmail":
			url = "http://login.live.com/";
			break;
		case "live":
			url = "http://login.live.com/";
			break;
		case "msn":
			url = "http://login.live.com/";
			break;
		case "qq":
			url = "http://mail.qq.com/cgi-bin/loginpage";
			break;
		}
		return url;
	}

	public static void main(String[] args) {
		log.info(EmailTool.GetEmailUrl("27447985@qq.com"));
		// EmailTool.sendMail("你好，这是我的一封邮件");
		// OSTool.sleep(1);

	}

}