package weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poseidon.lib.core.tool.cipher.Md5Tool;


public class WeixinOauth {
	private final static Logger log = LoggerFactory.getLogger(WeixinOauth.class);
	public String getAuthorizeUrl() throws UnsupportedEncodingException{
		String state = UUID.randomUUID().toString().replaceAll("-", "");
		//((HttpServletRequest) request).getSession().setAttribute("weixin_connect_state", state);//暂时不做用途
		String url = WeixinConfig.getValue("authorizeURL").trim() 
				+ "?appid=" + WeixinConfig.getValue("appid").trim()
				+ "&redirect_uri=" + URLEncoder.encode(WeixinConfig.getValue("redirect_uri").trim(), "utf-8")
				+ "&scope=" + WeixinConfig.getValue("scope").trim()
				+ "&response_type=code"
				+ "&state=" + state + "#wechat_redirect";
		return url;
	}

	public String getAuthUrl_snsapi_base(String redirect_uri) throws UnsupportedEncodingException{
		String state = UUID.randomUUID().toString().replaceAll("-", "");
		//((HttpServletRequest) request).getSession().setAttribute("weixin_connect_state", state);//暂时不做用途
		String url = WeixinConfig.getValue("authorizeURL").trim()
				+ "?appid=" + WeixinConfig.getValue("appid").trim()
				+ "&redirect_uri=" + URLEncoder.encode(redirect_uri.trim(), "utf-8")
				+ "&scope=snsapi_base"
				+ "&response_type=code"
				+ "&state=" + state + "#wechat_redirect";
		return url;
	}

	public String getAuthUrl_snsapi_userinfo(String redirect_uri) throws UnsupportedEncodingException{
		String state = UUID.randomUUID().toString().replaceAll("-", "");
		//((HttpServletRequest) request).getSession().setAttribute("weixin_connect_state", state);//暂时不做用途
		String url = WeixinConfig.getValue("authorizeURL").trim()
				+ "?appid=" + WeixinConfig.getValue("appid").trim()
				+ "&redirect_uri=" + URLEncoder.encode(redirect_uri.trim(), "utf-8")
				+ "&scope=snsapi_userinfo"
				+ "&response_type=code"
				+ "&state=" + state + "#wechat_redirect";
		return url;
	}

	public String getAccessTokenUrl(String code) {
		String url = WeixinConfig.getValue("accessTokenURL").trim() 
				+ "?appid=" + WeixinConfig.getValue("appid").trim()
				+ "&secret=" + WeixinConfig.getValue("secret").trim()
				+ "&code=" + code
				+ "&grant_type=authorization_code";
		return url;
	}

	public String getAccessTokenUrlForJSAPI() {
		log.debug("---appid---"+WeixinConfig.getValue("appid").trim()+"--secret--"+WeixinConfig.getValue("secret").trim());
		String url = WeixinConfig.getValue("accessTokenJSAPIURL").trim()
				+ "?appid=" + WeixinConfig.getValue("appid").trim()
				+ "&secret=" + WeixinConfig.getValue("secret").trim()
				+ "&grant_type=client_credential";
		return url;
	}
	
	/**
	 * 读weixinpayconfig
	 */
	public String getPayAccessTokenUrlForJSAPI() {
		log.debug("---appid---"+WeixinpayConfig.getValue("appid").trim()+"--secret--"+WeixinpayConfig.getValue("secret").trim());
		String url = WeixinpayConfig.getValue("accessTokenJSAPIURL").trim()
				+ "?appid=" + WeixinpayConfig.getValue("appid").trim()
				+ "&secret=" + WeixinpayConfig.getValue("secret").trim()
				+ "&grant_type=client_credential";
		return url;
	}

	public String getjsapi_ticket(String access_token){
		String url = WeixinConfig.getValue("jsapi_ticketURL").trim()
				+ "?access_token=" + access_token
				+ "&type=jsapi";
		return url;
	}


	public String getRefreshTokenUrl(String refreshToken) {
		String url = WeixinConfig.getValue("refreshTokenURL").trim() 
				+ "?appid=" + WeixinConfig.getValue("appid").trim()
				+ "&grant_type=refresh_token"
				+ "&refresh_token=" + refreshToken;
		return url;
	}
	
	public String getUserInfoUrl(String accessToken, String openId){
		String url = WeixinConfig.getValue("userInfoURL").trim() 
				+ "?access_token=" + accessToken
				+ "&openid=" + openId;
		return url;
	}
	
	public String getJsonDataByUrl(String url) throws IOException{
		StringBuilder sb = new StringBuilder(); 
		URL urlObject = new URL(url);  
		URLConnection uc = urlObject.openConnection(); 
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream())); 
        String str = null; 
        while ((str = in.readLine()) != null) {  
        	sb.append(str);  
        } 
        in.close();
        return sb.toString();
	}

	public String getBindWeixinUrl() throws UnsupportedEncodingException {
		String url = WeixinConfig.getValue("authorizeURL").trim() 
				+ "?appid=" + WeixinConfig.getValue("appid").trim()
				+ "&redirect_uri=" + URLEncoder.encode(WeixinConfig.getValue("bindWeixinURL").trim(), "utf-8")
				+ "&scope=snsapi_login"
				+ "&response_type=code"
				+ "#wechat_redirect";
		return url;
	}
	
	public String getSign(List<NameValuePair> params){
    	StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < params.size(); i++) {  
            // sb是用来计算签名的  
            sb.append(params.get(i).getName());  
            sb.append('=');  
            sb.append(params.get(i).getValue());  
            sb.append('&');  
        }
        log.debug("--------签名字符串--sb.toString()----------" + sb.toString());
        String sign = new String(DigestUtils.sha(sb.toString())); 
        log.debug("-------------生成签名ssign---------" + sign);
        return sign;
    }
	
	public String sha1(String text) {
		
		 try
	        {
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(text.getBytes("UTF-8"));
	            return byteToHex(crypt.digest());
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            e.printStackTrace();
	        }
		 return null;
	}
	
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

	public JSONObject getOpenId(String code) {
		log.debug("--------------getOpenId code----------------" + code);
		String accessTokenUrl = getAccessTokenUrl(code);
		String jsonStr = null;
		try {
			jsonStr = getJsonDataByUrl(accessTokenUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(jsonStr.toString());
		return jsonObject;
	}

	public JSONObject getUserInfo(String code) throws IOException {
		log.debug("--------------通过code获取accessToken----------------");
		// 通过code获取accessToken
		String accessTokenUrl =getAccessTokenUrl(code);
		String jsonStr = getJsonDataByUrl(accessTokenUrl);
		log.debug("------------获取accessToken结束------------返回值----" + jsonStr);
		JSONObject jsonObject = JSONObject.fromObject(jsonStr.toString());

		/*log.debug("----------------刷新accessToken--------------------------------------");
		// 刷新accessToken
		String refreshToken = jsonObject.getString("refresh_token");
		String refreshTokenUrl = getRefreshTokenUrl(refreshToken);
		jsonStr = getJsonDataByUrl(refreshTokenUrl);
		log.debug("----------------刷新accessToken结束--------返回值----" + jsonStr);*/

		log.debug("----------------获取微信用户信息-------------------------------------");
		// 获取微信用户信息
		String accessToken = jsonObject.getString("access_token");
		String openId = jsonObject.getString("openid");
		String userInfoUrl = getUserInfoUrl(accessToken, openId);
		jsonStr = getJsonDataByUrl(userInfoUrl);
		log.debug("----------------获取微信用户信息结束-----------返回值-----" + jsonStr);

		log.debug("----------------记录微信用户信息---------------------------");
		return JSONObject.fromObject(jsonStr.toString());
	}

}
