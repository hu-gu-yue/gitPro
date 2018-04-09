/*
 *                   _ooOoo_
 *                  o8888888o
 *                  88" . "88
 *                  (| -_- |)
 *                  O\  =  /O
 *               ____/`---'\____
 *             .'  \\|     |//  `.
 *            /  \\|||  :  |||//  \
 *           /  _||||| -:- |||||-  \
 *           |   | \\\  -  /// |   |
 *           | \_|  ''\---/''  |   |
 *           \  .-\__  `-`  ___/-. /
 *         ___`. .'  /--.--\  `. . __
 *      ."" '<  `.___\_<|>_/___.'  >'"".
 *     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *     \  \ `-.   \_ __\ /__ _/   .-` /  /
 *======`-.____`-.___\_____/___.-`____.-'======
 *                   `=---='
 *^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *         佛祖保佑       永无BUG
 */
package poseidon.zhdj.db.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static int typeLogin = 0;
	public final static int typeRegLogin = 1;
	public final static int typeQQLogin = 2;
	public final static int typeSinaLogin = 3;
	public final static int typeRenrenLogin = 4;
	public final static int typeWeixinLogin = 5;

	private int type;

	private String qqOpenId = "";
	private String sinaOpenId = "";
	private String renrenOpenId = "";
	private String weixinOpenId = "";
	
	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	public String getSinaOpenId() {
		return sinaOpenId;
	}

	public void setSinaOpenId(String sinaOpenId) {
		this.sinaOpenId = sinaOpenId;
	}

	public String getRenrenOpenId() {
		return renrenOpenId;
	}

	public void setRenrenOpenId(String renrenOpenId) {
		this.renrenOpenId = renrenOpenId;
	}

	public String getWeixinOpenId() {
		return weixinOpenId;
	}

	public void setWeixinOpenId(String weixinOpenId) {
		this.weixinOpenId = weixinOpenId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	/*
	 * 
	 */
	public ShiroToken(String userName, String passWord) {
		super(userName, passWord);
	}

	/*
	 * 
	 */
	public ShiroToken(String userName, String passWord, int type) {
		super(userName, passWord);
		this.type = type;
	}

	public ShiroToken(String userName, String passWord, int type, String openId) {
		super(userName, passWord);
		this.type = type;
		switch (type) {
		case typeQQLogin:
			qqOpenId = openId;
			break;
		case typeSinaLogin:
			sinaOpenId = openId;
			break;
		case typeRenrenLogin:
			renrenOpenId = openId;
			break;
		case typeWeixinLogin:
			weixinOpenId = openId;
			break;
		}
	}

}
