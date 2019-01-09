package com.gop.fork.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.domain.ForkUserReceiveCandyDetail;
import com.gop.fork.ForkUserReceiveCandyDetailService;
import com.gop.mapper.ForkUserReceiveCandyDetailMapper;

@Service
public class ForkUserReceiveCandyDetailServiceImpl implements ForkUserReceiveCandyDetailService{
	
	@Autowired
	private ForkUserReceiveCandyDetailMapper forkUserReceiveCandyDetailMapper;

	@Override
	public void addForkUserReceiveCandyDetail(ForkUserReceiveCandyDetail forkUserReceiveCandyDetail) {
		forkUserReceiveCandyDetailMapper.addForkUserReceiveCandyDetail(forkUserReceiveCandyDetail);
	}
}
