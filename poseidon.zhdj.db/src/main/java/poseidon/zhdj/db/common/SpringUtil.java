package poseidon.zhdj.db.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring IOC上下文工具类
 * 
 * @author zhouqi
 * 
 */
@Component
public class SpringUtil implements ApplicationContextAware {
	// 上下文
	private static ApplicationContext applicationContext;

	/**
	 * 设置当前上下文环境，此方法由spring自动装配
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;
	}

	/**
	 * 从当前IOC获得bean
	 * 
	 * @param id
	 * @return
	 */
	public static Object getBean(String id) {
		return applicationContext.getBean(id);
	}
}
