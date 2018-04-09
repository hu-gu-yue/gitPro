package poseidon.lib.core.tool.cipher;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poseidon.lib.core.tool.verification.VerificationCodeTool;

public class Md5Tool {
	private final static Logger log = LoggerFactory.getLogger(Md5Tool.class);

	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException caught!", e);
			return "";
		} catch (UnsupportedEncodingException e) {
			return "";
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}

		return md5StrBuff.toString();
	}
	
	public static void main(String[] arg) {
		String[] didArr = new String[]{};
		System.out.println(didArr.length);
//		System.out.println(System.currentTimeMillis()/1000);
//		log.info(Md5Tool.getMD5Str("zjyouauth/recommend/P/indexpage/1/1059/10231/10231.jpg58204EB4"));
	}
	
}
