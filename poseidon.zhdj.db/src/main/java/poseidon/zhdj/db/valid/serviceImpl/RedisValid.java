package poseidon.zhdj.db.valid.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import poseidon.lib.core.tool.json.JsonTool;
import poseidon.lib.core.tool.system.OSTool;
import poseidon.zhdj.db.valid.vo.ValidVo;

import java.util.concurrent.TimeUnit;



@Service
public class RedisValid  {
	
	@Autowired
	StringRedisTemplate redis;
	

	/**
	 * Description: 是否存在该用户
	 * @param userName
	 * @return
	 */
	public  Boolean isExists(String type, String userName){
		String value = get(type,userName);
		if ( value == null )
		{
			return true;
		}
		return false;
	}

	/**
	 * 存入记录
	 * @param key
	 * @param value
	 * @param timeOut
     */
	public void putValue(String key, String value, long timeOut ){
		redis.delete(key);
		redis.opsForValue().set(key, value);
		redis.expire(key, timeOut, TimeUnit.SECONDS);
	}

	/**
	 * 根据key获取value
	 * @param key
	 * @return
     */
	public String getValue(String key){
		return redis.opsForValue().get(key);
	}

	/**
	 * Description: 存入记录
	 * @param userName
	 * @return
	 */
	public  String put(String type, String userName, long timeOut ){
		String code = ""+OSTool.genSixCode();
		String redisKey = type+userName;
		redis.delete(redisKey);
		redis.opsForValue().set(redisKey, code);
		redis.expire(redisKey, timeOut, TimeUnit.SECONDS);
		return code;
	}
	
	public  String putReg(String type, String userName, String nickName, String password, long timeOut ){
		String code = ""+OSTool.genSixCode();
		ValidVo validVo = new ValidVo();
		validVo.setCode(code);
		validVo.setPassword(password);
		validVo.setNickName(nickName);
		redis.opsForValue().set(type+userName, JsonTool.toJSON(validVo), timeOut, TimeUnit.SECONDS);
		return code;
	}
	
	
	/** 
	 * Description: 添加为Email
	 * @param userName
	 * @return
	 * @see poseidon.web.muses.db.user.service.ValidService#putForEmail(java.lang.String)
	 */
	public  String putForEmail(String type, String userName, long timeOut ){
		String emailCode = ""+OSTool.genUUID();
		redis.opsForValue().set(type+userName, emailCode, timeOut, TimeUnit.SECONDS);
		return emailCode;
	}
	
	/** 
	 * Description: 添加为Email的JSON格式
	 * @param userName
	 * @return
	 * @see poseidon.web.muses.db.user.service.ValidService#putForEmail(java.lang.String)
	 */
	public  String putRegForEmail(String type, String userName, String nickName,  String password, long timeOut ){
		String emailCode = ""+OSTool.genUUID();
		ValidVo validVo = new ValidVo();
		validVo.setCode(emailCode);
		validVo.setPassword(password);
		validVo.setNickName(nickName);
		
		redis.opsForValue().set(type+userName, JsonTool.toJSON(validVo), timeOut, TimeUnit.SECONDS);
		return emailCode;
	}
	
	
	
	/**
	 * Description: 获取记录
	 * @param userName
	 * @return
	 */
	public  String get(String type, String userName ){
		String value = redis.opsForValue().get(type+userName);
		return value;
	}
	
	/** 
	 * Description: 删除记录
	 * @param type
	 * @param userName
	 */
	public  void delete(String type, String userName ){
		redis.delete(type+userName);
	}
}
