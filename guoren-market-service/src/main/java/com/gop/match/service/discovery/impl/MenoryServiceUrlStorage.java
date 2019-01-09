package com.gop.match.service.discovery.impl;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import com.gop.match.service.discovery.ServiceUrlStorage;

@Service
public class MenoryServiceUrlStorage implements ServiceUrlStorage {

	private final ConcurrentHashMap<String, Set<String>> stroage = new ConcurrentHashMap<>();

	private Lock lock = new ReentrantLock();

	@Override
	public Set<String> getServiceUrl(String symbol) {

		if (null == stroage.get(symbol)) {
			Set<String> urls = null;
			lock.lock();
			urls = stroage.get(symbol);
			if (urls != null) {
				return urls;
			}
			try {
				urls = ConcurrentHashMap.newKeySet();
				stroage.put(symbol, urls);
			} catch (Exception e) {

			} finally {
				lock.unlock();
			}

		}
		return stroage.get(symbol);
	}

	@Override
	public void removeUrl(String symbol, String url) {
		Set<String> set = getServiceUrl(symbol);
		set.remove(url);

	}

	@Override
	public void addtUrl(String symbol, String url) {
		Set<String> set = getServiceUrl(symbol);
		set.add(url);

	}

	@Override
	public void addtUrlS(String symbol, Set<String> localUrls) {
		Set<String> set = getServiceUrl(symbol);
		set.addAll(localUrls);

	}

}
