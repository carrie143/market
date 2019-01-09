/*
 * 
 */
package com.gop.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * fastjson 工具类
 * @author lipeng
 *
 */
public class JSONUtils {
	
	/**
	 * 增加默认的特性
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object){
		return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat,
												SerializerFeature.WriteMapNullValue,
												SerializerFeature.WriteNullNumberAsZero,
												SerializerFeature.WriteNullListAsEmpty,
												SerializerFeature.WriteNullStringAsEmpty,
												SerializerFeature.WriteNullBooleanAsFalse);
	}
	
	public static JSONObject parseObject(String text){
		return JSON.parseObject(text);
	}
	
	public static JSONObject parseObject(Object object){
		return parseObject(toJSONString(object));
	}
	
	public static JSONArray parseArray(List list){
		return JSON.parseArray(toJSONString(list));
	}
	
	
}
