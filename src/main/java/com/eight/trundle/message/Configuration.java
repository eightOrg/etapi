package com.eight.trundle.message;

import java.io.IOException;
import java.util.Map;

/**
 * 配置文件读取配置类
 * @author weijl
 */
public abstract class Configuration {
	
	private static  Configuration configuration ;
	
	public abstract Map getMessages() throws IOException;
	
	/**
	 * 
	 * @return Configuration
	 */
	public static Configuration getDefaultConfiguration() {
        if(configuration == null){
            configuration = new PropertiesConfiguration();
        }
        return configuration;
    }

}
