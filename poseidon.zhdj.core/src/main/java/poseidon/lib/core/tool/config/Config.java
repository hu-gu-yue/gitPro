package poseidon.lib.core.tool.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poseidon.lib.core.tool.system.PathTool;

/**
 * 从配置文件中得到数据
 * 
 */
public class Config {
	private final static Logger log = LoggerFactory.getLogger(Config.class);
	protected Properties config;
	
	private static Properties configProps = new Properties(); 
	static{
		try {
			configProps.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key,String defaultValue){
		String props = configProps.getProperty(key);
		return props==null?defaultValue:props;
	}

    public static void updateProperties(String key,String value) {    
    	configProps.setProperty(key, value); 
    } 

	public Config(String fileName) throws Exception {
		InputStream is = null;
		config = new Properties();
		String currentDir = PathTool.getAppPath("");
		log.info(currentDir + fileName);
		File configFile = new File(currentDir + fileName);
		is = new FileInputStream(configFile);
		config.load(is);
		if (is != null) {
			is.close();
		}
	}

	/**
	 * 获取属性值
	 * 
	 * @param key
	 * @return String
	 */
	public String getStringProperty(String key) {
		return config.getProperty(key);
	}

	/**
	 * 获取属性值
	 * 
	 * @param key
	 * @return int
	 */
	public int getIntProperty(String key) {
		int value = 0;
		if (config.getProperty(key) != null) {
			value = Integer.parseInt(config.getProperty(key));
		}
		return value;
	}

	public boolean getBooleanProperty(String key) {
		boolean result = false;
		if (config.getProperty(key) != null) {
			result = Boolean.parseBoolean(config.getProperty(key));
		}
		return result;
	}

	/**
	 * 移除某个属性值
	 * 
	 * @param key
	 */
	public void removeProperty(String key) {
		if (key != null) {
			config.remove(key);
		}
	}

}
