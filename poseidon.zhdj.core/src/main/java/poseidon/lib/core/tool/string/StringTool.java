package poseidon.lib.core.tool.string;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringTool {
	private final static Logger log = LoggerFactory.getLogger(StringTool.class);
	private final static String XSTR = "XXXXXXXXXXXXXXXXXXXX";
	private final static String SSTR = "********************";
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("len can't %2.");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static boolean isEmpty(String text) {
		if (text == null || text.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String text) {
		return !isEmpty(text);
	}

	public static String gbkToUtf8(String gbkText) throws UnsupportedEncodingException {
		String isoText = new String(gbkText.getBytes("UTF-8"), "ISO-8859-1");
		String utf8Text = new String(isoText.getBytes("ISO-8859-1"), "UTF-8");
		return utf8Text;
	}

	// 判断字符串中是否有中文字符
	public static boolean containsChinese(String text) {
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) >= (char) 0x4E00 && text.charAt(i) <= (char) 0x9FA5) {
				return true;
			}
		}

		return false;
	}

	// 解码中文字符串
	public static String decodeChinese(String text) throws UnsupportedEncodingException {
		String decodeText = URLDecoder.decode(text, "utf-8");
		if (!StringTool.containsChinese(decodeText)) {
			decodeText = URLDecoder.decode(text, "GBK");
		}
		return decodeText;
	}

	public static String GetUrlParam(String url, String key) {
		String result = "";
		String temp = url;
		key = key + "=";
		int start = 0;
		int end = 0;
		start = temp.indexOf(key);
		if (start >= 0) {
			temp = temp.substring(start + key.length());
			end = temp.indexOf("&");

			if (end > 0) {
				result = temp.substring(0, end);
			} else {
				result = temp;
			}
		}
		return result;
	}

	/**
	 * 检验是否是合法的IP地址
	 * 
	 * @param ip
	 *            String IP地址
	 * @return boolean IP地址是否合法
	 */
	public static boolean isIpAddress(String ip) {
		String regex = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";

		Matcher matcher = Pattern.compile(regex).matcher(ip);

		if (matcher.find())
			return true;

		return false;
	}

	
	/**
	 * Description: 判断是不是手机号码
	 * @param mobile
	 * @return
	 */
	public static boolean isMobileNO(String mobile) {
		/*
		 * 根据实际开发于2009年9月7日最新统计：
		 * 中国电信发布中国3G号码段:中国联通185,186;中国移动188,187;中国电信189,180,181共7个号段。
		 * 3G业务专属的180-189号段已基本分配给各运营商使用,
		 * 其中180、189分配给中国电信,187、188归中国移动使用,185、186属于新联通。
		 * 中国移动拥有号码段：139、138、137、136、135
		 * 、134、159、158、157（3G）、152、151、150、188（3G）、187（3G）;14个号段
		 * 中国联通拥有号码段：130、131、132、155、156（3G）、186（3G）、185（3G）;6个号段
		 * 中国电信拥有号码段：133、153、189（3G）、180（3G）;4个号码段 移动:
		 * 2G号段(GSM网络)有139,138,137,136,135,134(0-8),159,158,152,151,150
		 * 3G号段(TD-SCDMA网络)有157,188,187 147是移动TD上网卡专用号段. 联通:
		 * 2G号段(GSM网络)有130,131,132,155,156 3G号段(WCDMA网络)有186,185 电信:
		 * 2G号段(CDMA网络)有133,153 3G号段(CDMA网络)有189,180
		 */
		if(mobile == null){
			return false;
		}

//		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		Pattern p = Pattern.compile("^[1][0-9]{10}$");
		Matcher m = p.matcher(mobile);
		log.info(m.matches() + "---");
		return m.matches();
	}

	/**
	 * Description: 判断是否是Email
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		log.info(m.matches() + "---");
		return m.matches();
	}
	
	/**
	 * Description: 获取马赛克手机号码
	 * @param mobile
	 * @return
	 */
	public static String getMosaicMobile(String mobile){
		int maxLength = mobile.length();
		String lastNumText = mobile.substring(maxLength-2, maxLength);
		int sLength = maxLength - 6;
		String mosaicMobile = mobile.substring(0, 4) + SSTR.substring(0,sLength) + lastNumText;
		return mosaicMobile;
	}
	
	/**
	 * Description: 获取马赛克银行号码
	 * @param cardNo
	 * @return
	 */
	public static String getMosaicBankCardNo( String cardNo){
		/*  银行卡的卡号长度及结构符合ISO7812-1有关规定,由13-19位数字表示,具体由以下几部分组成:
		 *     9  XXXXX              X......X           X
	     *  发卡银行标识代码         自定义位        校验位
	     *  发卡行标识代码指发卡行标识代码标识发卡机构,由6位数字表示,第一位固定为"9",后5位由BIN注册管理机构分配.
	     *  自定义位是指发卡行自定义位,由6-12位数字组成.
	     *  校验位是指卡号的最后一位数字,根据校验位前的数字计算得到.
	     *  BIN注册管理机构是指负责BIN注册管理的机构.
		 */
		if ( StringTool.isEmpty(cardNo))
		{
			cardNo ="XXXXXXXXXXXXX";
		}
		
		int maxLength = cardNo.length();
		String lastNumText = cardNo.substring(maxLength-4, maxLength);
		int xLength = maxLength - 11;
		
		
		String mosaicMobile = cardNo.substring(0, 7) + XSTR.substring(0,xLength) + lastNumText;
		return mosaicMobile;
	}
	
	/**
	 * 移除html标签
	 * @param str
	 * @return
	 */
	public static String removeHtml(String str, String replaceStr) {
		if (isEmpty(str)) {
			return "";
		}
		
		if (isEmpty(replaceStr)) {
			replaceStr = "";
		}
		Pattern patt = Pattern.compile("<[^>]+>");
		Matcher m = patt.matcher(str);
		str = m.replaceAll(replaceStr);
		str = str.trim();
		return str.length() >= 300 ? str.substring(0, 300) : str;
	}
	
	public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}

	/**
	 * 支付宝/微信 批次号生成
	 * 生成规则：当天日期[8位]+序列号[3至24位]，如：201008010000001
	 * @return
	 */
	public static String RandomBatchNum() {

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = dateFormat1.format(new Date());
		StringBuffer buffer =new StringBuffer();
		for(int i=0;i<18;i++) {
			Integer val = (int)(Math.random()*9+1);
			buffer.append(val.toString());
		}
		return format+buffer.toString();
	}

	public static boolean isIDCard(String idCard) {
		String regexID = "/(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/";
		return Pattern.matches(regexID, idCard);
	}

	public static void main(String[] args) {
		System.out.println("----------"+System.getProperty("user.dir"));
		System.out.println("----------"+ClassLoader.getSystemResource(""));
	}

	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException(
										"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();

	}


}
