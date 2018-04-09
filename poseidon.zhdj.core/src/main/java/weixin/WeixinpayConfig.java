package weixin;

import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poseidon.lib.core.tool.cipher.Md5Tool;
import poseidon.lib.core.tool.system.OSTool;
import poseidon.lib.core.tool.xml.XmlUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class WeixinpayConfig {
	private final static Logger log = LoggerFactory.getLogger(WeixinpayConfig.class);
    
    /**随机字符串*/
    public static String nonceStr = OSTool.genUUID();
    
    /**
     * 微信扫码支付
     * @param params
     * @return 返回xml报文
     * @throws Exception
     */
    public static String getCodeUrl(List<NameValuePair> params) throws Exception{
    	StringBuffer sb = new StringBuffer();
		URL urlObject = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");  
		HttpURLConnection httpConn = (HttpURLConnection)urlObject.openConnection(); 
		httpConn.setDoOutput(true);// 使用 URL 连接进行输出   
		httpConn.setDoInput(true);// 使用 URL 连接进行输入   
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod("POST");
		
		// 设置请求属性   
		// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致   
		String xml = genXml(params);
		byte[] requestStringBytes = xml.getBytes("UTF-8");   
		httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);   
		httpConn.setRequestProperty("Content-Type", "text/xml");   
		httpConn.setRequestProperty("Charset", "UTF-8");
		
		// 建立输出流，并写入数据   
		OutputStream outputStream = httpConn.getOutputStream();   
		outputStream.write(requestStringBytes);
		outputStream.close();   
		
		// 获得响应状态  
		int responseCode = httpConn.getResponseCode();   
		if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功   
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8")); 
	        String str = null; 
	        while ((str = in.readLine()) != null) {  
	        	sb.append(str);  
	        } 
	        in.close();   
		}
		
		log.debug("------微信支付返回数据-----sb.toString()----------" + sb.toString());
		String retCode = XmlUtil.getElementValue(sb.toString(), "return_code");
		log.debug("------微信支付返回代码-----retCode----------" + retCode);
		if("SUCCESS".equals(retCode.toUpperCase())){
			return XmlUtil.getElementValue(sb.toString(), "code_url");
		}
        throw new Exception(XmlUtil.getElementValue(sb.toString(), "return_msg"));
    }
    
    /**
     * 统一下单
     * @param params
     * @return
     * @throws Exception
     */
    public static String placeOrder(List<NameValuePair> params) throws Exception {
    	StringBuffer sb = new StringBuffer();

		URL urlObject = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");  
		HttpURLConnection httpConn = (HttpURLConnection)urlObject.openConnection(); 
		httpConn.setDoOutput(true);// 使用 URL 连接进行输出   
		httpConn.setDoInput(true);// 使用 URL 连接进行输入   
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod("POST");
		
		// 设置请求属性   
		// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致   
		String xml = genXml(params);
		byte[] requestStringBytes = xml.getBytes("UTF-8");   
		httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);   
		httpConn.setRequestProperty("Content-Type", "text/xml");   
		httpConn.setRequestProperty("Charset", "UTF-8");
		
		// 建立输出流，并写入数据   
		OutputStream outputStream = httpConn.getOutputStream();   
		outputStream.write(requestStringBytes);
		outputStream.close();   
		
		// 获得响应状态  
		int responseCode = httpConn.getResponseCode();   
		if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功   
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8")); 
	        String str = null; 
	        while ((str = in.readLine()) != null) {  
	        	sb.append(str);  
	        } 
	        in.close();   
		}
		
		log.debug("------微信下单返回数据-----sb.toString()----------" + sb.toString());

		String retCode = XmlUtil.getElementValue(sb.toString(), "return_code");
		log.debug("------微信支付返回代码-----retCode----------" + retCode);
		if("SUCCESS".equals(retCode.toUpperCase())){
			return sb.toString();
		}

		throw new Exception(XmlUtil.getElementValue(sb.toString(), "return_msg"));
	}
    
    /**
     * 微信订单查询
     * @param params
     * @return 
     * @throws Exception
     */
    public static String searchOrder(List<NameValuePair> params) throws Exception {
    	StringBuffer sb = new StringBuffer();
		URL urlObject = new URL("https://api.mch.weixin.qq.com/pay/orderquery");  
		HttpURLConnection httpConn = (HttpURLConnection)urlObject.openConnection(); 
		httpConn.setDoOutput(true);// 使用 URL 连接进行输出   
		httpConn.setDoInput(true);// 使用 URL 连接进行输入   
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod("POST");
		
		// 设置请求属性   
		// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致   
		String xml = genXml(params);
		byte[] requestStringBytes = xml.getBytes("UTF-8");   
		httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);   
		httpConn.setRequestProperty("Content-Type", "text/xml");   
		httpConn.setRequestProperty("Charset", "UTF-8");
		
		// 建立输出流，并写入数据   
		OutputStream outputStream = httpConn.getOutputStream();   
		outputStream.write(requestStringBytes);
		outputStream.close();   
		
		// 获得响应状态  
		int responseCode = httpConn.getResponseCode();   
		if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功   
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8")); 
	        String str = null; 
	        while ((str = in.readLine()) != null) {  
	        	sb.append(str);  
	        } 
	        in.close();   
		}
		
		log.debug("------微信查询订单返回数据-----sb.toString()----------" + sb.toString());
		return sb.toString();
	}
    
    private static String genXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();  
        StringBuilder sb2 = new StringBuilder();  
        sb2.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><xml>");  
        for (int i = 0; i < params.size(); i++) {  
            // sb是用来计算签名的  
            sb.append(params.get(i).getName());  
            sb.append('=');  
            sb.append(params.get(i).getValue());  
            sb.append('&');  
            // sb2是用来做请求的xml参数  
            sb2.append("<" + params.get(i).getName() + ">");  
            sb2.append(params.get(i).getValue());  
            sb2.append("</" + params.get(i).getName() + ">");  
        }  
        sb.append("key=");  
        sb.append(WeixinpayConfig.getValue("key"));
        log.debug("--------签名字符串--sb.toString()----------" + sb.toString());
        String sign = Md5Tool.getMD5Str(sb.toString()).toUpperCase(); 
        sb2.append("<sign><![CDATA[" + sign + "]]></sign>");  
        sb2.append("</xml>");  
        log.debug("--------生成签名sign--------" + sign);
        return sb2.toString();  
    }
    
    public static String getSign(List<NameValuePair> params){
    	StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < params.size(); i++) {  
            // sb是用来计算签名的  
            sb.append(params.get(i).getName());  
            sb.append('=');  
            sb.append(params.get(i).getValue());  
            sb.append('&');  
        }  
        sb.append("key=");  
        sb.append(WeixinpayConfig.getValue("key"));
        log.debug("--------签名字符串--sb.toString()----------" + sb.toString());
        String sign = Md5Tool.getMD5Str(sb.toString()).toUpperCase(); 
        log.debug("-------------生成签名ssign---------" + sign);
        return sign;
    }

	/**
	 * JSAPI ：H5调起微信支付，返回的支付签名
	 * @param weixinpayModel
	 * @return
	 * @throws Exception
	 */
	public static Map getPaySign(WeiXinPayModel weixinpayModel) throws Exception {
		Map<String,Object> resultMap = new HashMap<>();

		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new NameValuePair("appid", WeixinpayConfig.getValue("appid")));
		packageParams.add(new NameValuePair("attach", weixinpayModel.getAttach()));
		packageParams.add(new NameValuePair("body", weixinpayModel.getBody()));
		packageParams.add(new NameValuePair("mch_id", WeixinpayConfig.getValue("mchid")));
		packageParams.add(new NameValuePair("nonce_str", WeixinpayConfig.nonceStr));
		packageParams.add(new NameValuePair("notify_url", weixinpayModel.getNotifyUrl()));
		packageParams.add(new NameValuePair("openid", weixinpayModel.getOpenId()));
		packageParams.add(new NameValuePair("out_trade_no", weixinpayModel.getOutTradeNo()));
		packageParams.add(new NameValuePair("spbill_create_ip", weixinpayModel.getRemoteIp()));
		packageParams.add(new NameValuePair("total_fee", weixinpayModel.getTotalFee()));
		packageParams.add(new NameValuePair("trade_type", "JSAPI"));

		String sb = null;
		try {
			sb = placeOrder(packageParams);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String prePayId = XmlUtil.getElementValue(sb.toString(), "prepay_id");
		resultMap.put("prePayId",prePayId);

		List<NameValuePair> packageParam = new LinkedList<NameValuePair>();
		packageParam.add(new NameValuePair("appId", WeixinpayConfig.getValue("appid")));
		packageParam.add(new NameValuePair("nonceStr", weixinpayModel.getNonceStr()));
		packageParam.add(new NameValuePair("package", "prepay_id=" + prePayId));
		packageParam.add(new NameValuePair("signType", "MD5"));
		packageParam.add(new NameValuePair("timeStamp", weixinpayModel.getTimeStamp()));

		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < packageParam.size(); i++) {
			sb1.append(packageParam.get(i).getName());
			sb1.append('=');
			sb1.append(packageParam.get(i).getValue());
			sb1.append('&');
		}
		sb1.append("key=");
		sb1.append(WeixinpayConfig.getValue("key"));
		log.debug("--------签名字符串--sb1----------" + sb1.toString());
		String sign = Md5Tool.getMD5Str(sb1.toString()).toUpperCase();
		resultMap.put("sign",sign);
		return resultMap;
	}

	/**
	 * 官网扫码微信支付，返回的支付URL，用于生成二维码
	 * @param weixinpayModel
	 * @return
	 * @throws Exception
	 */
	public static String getCodeUrl(WeiXinPayModel weixinpayModel) throws Exception {
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new NameValuePair("appid", WeixinpayConfig.getValue("appid")));
		packageParams.add(new NameValuePair("attach", weixinpayModel.getAttach()));
		packageParams.add(new NameValuePair("body",weixinpayModel.getBody()));
		packageParams.add(new NameValuePair("mch_id", WeixinpayConfig.getValue("mchid")));
		packageParams.add(new NameValuePair("nonce_str", WeixinpayConfig.nonceStr));
		packageParams.add(new NameValuePair("notify_url", weixinpayModel.getNotifyUrl()));
		packageParams.add(new NameValuePair("out_trade_no", weixinpayModel.getOutTradeNo()));
		packageParams.add(new NameValuePair("spbill_create_ip", weixinpayModel.getRemoteIp()));
		packageParams.add(new NameValuePair("total_fee", weixinpayModel.getTotalFee()));
		packageParams.add(new NameValuePair("trade_type", WeixinpayConfig.getValue("tradeType")));

		String codeUrl = getCodeUrl(packageParams);
		return codeUrl;
	}

	/**
	 *如果是微信浏览器，得到openId，要回调的url
	 * @return
	 * @throws Exception
	 */
	public static String getReturnUrl(String url) throws Exception {
		WeixinOauth oauth = new WeixinOauth();
		String returnUrl = null;
		try {
			returnUrl = oauth.getAuthUrl_snsapi_base( url );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		log.debug(" getUserOpenId returnUrl :" + returnUrl);

		return "redirect:"+returnUrl;
	}
	
	
	
    private static Properties props = new Properties(); 
    
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixinpayConfig.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key){
		return props.getProperty(key);
	}

    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    }

}
