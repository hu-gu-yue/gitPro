package poseidon.zhdj.db.common;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShiroPassword {
	
	private final static Logger log = LoggerFactory.getLogger(ShiroPassword.class);
	
	public static String genPassword(String password ){
		String text = new Md5Hash(password).toString();   
		log.debug("密码的长度是"+text.length());
		return text;
	}
}
