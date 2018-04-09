package poseidon.zhdj.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import poseidon.zhdj.db.common.SpringUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * Redis工具类
 * 
 * @author HLF
 * 
 */
public final class RedisUtil {
	
	public static void set(String key,List list) {
		RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("jedisTemplate");
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		
		if(null != list) {
			del(key);
			int size = list.size();
			for(int i = 0; i < size ; i ++) {
			   listOperations.rightPush(key, list.get(i));
			}
		}
	}
	
	
	/**
	  * 获得缓存的list对象
	  * @param key 缓存的键值
	  * @return  缓存键值对应的数据
	  */
	public static List get(String key) {
		RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("jedisTemplate");
		List<Object> dataList = new ArrayList<Object>();
		ListOperations<String,Object> listOperation = redisTemplate.opsForList();
		
		Long size = listOperation.size(key);
		dataList = listOperation.range(key, 0, size);
//		for(int i = 0 ; i < size ; i ++) {
//			dataList.add(listOperation.rightPop(key));
//		}
		
		return dataList;
	 }
	
	 /**
     * @param key
     */
    public static long del(final String... keys) {
    	RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("jedisTemplate");
        return (long) redisTemplate.execute(new RedisCallback<Object>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });
    }
    
    /**
     * @param key
     * @return
     */
    public static boolean exists(final String key) {
    	RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("jedisTemplate");
        boolean flag1 = (boolean) redisTemplate.execute(new RedisCallback<Object>() {
            public Boolean doInRedis(final RedisConnection connection) throws DataAccessException {
            	boolean flag = connection.exists(key.getBytes());
                return flag;
            }
        });
        
        return flag1;
    }
    
    /**
     * @return
     */
    public static String flushDB() {
    	RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("jedisTemplate");
        return (String) redisTemplate.execute(new RedisCallback<Object>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }
}