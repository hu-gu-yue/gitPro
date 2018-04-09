package poseidon.lib.core.tool.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poseidon.lib.core.tool.cipher.Md5Tool;
import poseidon.lib.core.tool.string.StringTool;

public class SmsTool {
	private final static Logger log = LoggerFactory.getLogger(SmsTool.class);

	private static String zjUrlFormat = "http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=%s&pwd=%s&mobile=%s&content=%s";
	private static String zjContentFormat = "【自驾友网】欢迎加入自驾友！您的短信验证码是%s，请在30分钟内输入使用。超时请重新发送。";
	private static String zjAppId = "";
	private static String zjAppKey = "";
	

	static {
		try {
			zjAppId = URLEncoder.encode("SDK-WSS-010-06793", "utf-8");
			zjAppKey = Md5Tool.getMD5Str(zjAppId + "e-f7cb-[").toUpperCase();
		} catch (UnsupportedEncodingException e) {
			log.error("SmsTool error", e);
		}

	}

	public static void sendZJYOUSMS(String mobile, String code) throws Exception {
		if (StringTool.isEmpty(mobile) || StringTool.isEmpty(code)) {
			throw new Exception("手机号码或者校验码为空");
		}

		String content = String.format(zjContentFormat, code);
		send(zjUrlFormat, mobile, content, "");
		
	}

	public static void sendFindPwdSMS(String mobile, String code) throws Exception {
		if (StringTool.isEmpty(mobile) || StringTool.isEmpty(code)) {
			throw new Exception("手机号码或者校验码为空");
		}

		String zjContentFormat = "【自驾友网】您正在找回密码！您的短信验证码是%s，请在30分钟内输入使用。超时请重新发送。";

		String content = String.format(zjContentFormat, code);
		send(zjUrlFormat, mobile, content, "");

	}


	public static void sendZJYOUTICKETSMS(String mobile, String content) throws Exception {
		if (StringTool.isEmpty(mobile)) {
			throw new Exception("手机号码为空");
		}
		send(zjUrlFormat, mobile, content, "");
	}

	public static void send(String urlFormat, String mobile, String content, String rrid) throws Exception{
		GetMethod getMethod = null;
		Exception currExcption = null;
		try {
			// 构造HttpClient的实例
			HttpClient httpClient = new HttpClient();
			// 创建GET方法的实例
			mobile = URLEncoder.encode(mobile, "utf-8");
			content = URLEncoder.encode(content, "utf-8");
			rrid = URLEncoder.encode(rrid, "utf-8");
			String url = "";
			url = String.format(urlFormat, zjAppId, zjAppKey, mobile, content);
			log.debug(url);
			getMethod = new GetMethod(url);
			// 使用系统提供的默认的恢复策略
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

			getMethod.getParams().setParameter(HttpMethodParams.HTTP_URI_CHARSET, "utf-8");
			getMethod.getParams().setParameter(HttpMethodParams.HTTP_ELEMENT_CHARSET, "utf-8");
			getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
			System.out.println(new String(responseBody));
		} catch (Exception e) {
			currExcption = e;
		} finally {
			// 释放连接
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		
		if ( currExcption != null)
		{
			log.error("发送短信失败",currExcption);
			throw new Exception("发送短信给"+mobile+"失败");
		}
	}


	public static void main(String[] args) {

	
		try {
			sendZJYOUSMS("15387571204", "338767");
		} catch (Exception e) {
			log.error("发送短信失败",e);
		}
		
	}
}
