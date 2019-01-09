package com.gop.utils;

import java.util.ResourceBundle;

/**
 * 
 * @author lipeng
 *
 */
public class ResourceUtils {
	/**
	 *  根据baseName 和key，读取相应的值
	 * @param baseName
	 * @param key
	 * @return
	 */
	public static String get(String baseName, String key){
		ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);
        return resourceBundle.getString(key);
	}
}
