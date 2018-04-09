package poseidon.lib.core.tool.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
import poseidon.lib.core.tool.string.StringTool;

public class URLNetTool {
	private final static Logger log = LoggerFactory.getLogger(URLNetTool.class);
	
	public static String getDataFromUrl(String url) throws IOException {
		StringBuffer sb = new StringBuffer();
		URL urlObject = new URL(url);
		HttpURLConnection httpConn = (HttpURLConnection) urlObject.openConnection();
		httpConn.setDoOutput(true);// 使用 URL 连接进行输出
		httpConn.setDoInput(true);// 使用 URL 连接进行输入
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod("POST");

		// 获得响应状态
		int responseCode = httpConn.getResponseCode();
		log.debug("---------responseCode-------" + responseCode);
		if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
			String str = null;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
			in.close();
		}
		return sb.toString();
	}
	
	public static String getCityNameByIp(String ip) throws IOException{
		log.debug("-----查找IP----" + ip);
		String s1 = getDataFromUrl("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
		log.debug("---------返回data-----" + s1);
		JSONObject o1 = JSONObject.fromObject(s1);
		String s2 = o1.getString("data");
		JSONObject o2 = JSONObject.fromObject(s2);
		String city = o2.getString("city");
		if(StringTool.isEmpty(city)){
			city = "长沙";
		}
		return city;
	}
	
	public static String getCityNameByPhone(String phone) throws IOException {
		String s1 = getDataFromUrl("http://api.k780.com:88/?app=phone.get&phone=" + phone + "&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json");
		log.debug("---------返回data-----" + s1);
		JSONObject o1 = JSONObject.fromObject(s1);
		if(!"1".equals(o1.getString("success"))){
			return "";
		}
		String s2 = o1.getString("result");
		JSONObject o2 = JSONObject.fromObject(s2);
		String[] strArr = o2.getString("att").split(",");
		return strArr[strArr.length-1];
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getCityNameByIp("113.240.224.230"));
		System.out.println(getCityNameByPhone("18711124265"));
	}
}
