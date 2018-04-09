package poseidon.lib.core.tool.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UrlTool {
	private final static Logger log = LoggerFactory.getLogger(UrlTool.class);
	/**
	 * 获取Query参数
	 * 
	 * @param urlText
	 *            url地址
	 * @return query参数
	 */
	public static String getQuery(String urlText) {
		URL url;
		try {
			url = new URL(urlText);
			return url.getQuery();
		} catch (MalformedURLException e) {
			return "";
		}
	}

	
	/**
	 * 解析出url参数中的键值对
	 * 
	 * @param urlText
	 *            url地址
	 * @return url 请求参数部分
	 */
	
	public static HashMap<String, String> URLRequest(String urlText) {

		// 判断urlText 是否包含
		int pos = urlText.indexOf("prepath=http");
		if (pos > 0) {
			pos += "prepath=http".length();
			urlText = "http" + urlText.substring(pos);
		}

		HashMap<String, String> queryMap = new HashMap<String, String>();
		String[] arrSplit = null;
		String strUrlParam = getQuery(urlText);
		if (strUrlParam == null) {
			return queryMap;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				queryMap.put(arrSplitEqual[0], arrSplitEqual[1]);
			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					queryMap.put(arrSplitEqual[0], "");
				}
			}
		}
		return queryMap;
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 参考文章： http://developer.51cto.com/art/201111/305181.htm
	 *
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 *
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 *
	 * 用户真实IP为： 192.168.1.110
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		int seq = 0;
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			seq = 1;
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			seq = 2;
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			seq = 3;
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			seq = 4;
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			seq = 5;
		}
		log.info("=====================Request IP = " + ip + " seq = " + seq);
		return ip;
	}

	public static void main(String[] args) {

		HashMap<String, String> queryMap = UrlTool.URLRequest("");

		for (String strRequestKey : queryMap.keySet()) {
			String strRequestValue = queryMap.get(strRequestKey);
			System.out.println("[" + strRequestKey + "]" + strRequestValue + "");
		}

		// SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		// 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		// java.util.Date dt = new Date(1403752283180L );
		// String sDateTime = sdf.format(dt); //得到精确到秒的表示：08/31/2006 21:08:00
		// System.out.println(sDateTime);
	}

}
