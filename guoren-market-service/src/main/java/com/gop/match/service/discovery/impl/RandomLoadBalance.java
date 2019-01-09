package com.gop.match.service.discovery.impl;

import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.gop.code.consts.CommonCodeConst;
import com.gop.exception.AppException;
import com.gop.match.service.discovery.LoadBalance;

@Service
public class RandomLoadBalance implements LoadBalance {

	Random random = new Random();

	@Override
	public String loadBalance(Set<String> urls) {
		if (urls == null || urls.isEmpty()) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		int index = random.nextInt(100) % urls.size();

		return (String) urls.stream().toArray()[index];
	}


}
