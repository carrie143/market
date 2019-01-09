package com.gop.cache;

public interface RedisCache {
	
	public boolean saveValue(final String phone, final String value);
	
	public String getValue(String phone);
	
	public void delKey(String phone);
}
