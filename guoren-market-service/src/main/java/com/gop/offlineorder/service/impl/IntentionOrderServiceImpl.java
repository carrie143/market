package com.gop.offlineorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gop.code.consts.OfflineOrderConst;
import com.gop.domain.TokenOrderIntention;
import com.gop.domain.enums.TokenOrderIntentionState;
import com.gop.exception.AppException;
import com.gop.mapper.TokenOrderIntentionMapper;
import com.gop.offlineorder.service.IntentionOrderSerrvice;

@Service
public class IntentionOrderServiceImpl implements IntentionOrderSerrvice {
	@Autowired
	private TokenOrderIntentionMapper tokenOrderIntentionMapper;

	@Override
	public void createIntentionOrderSerrvice(TokenOrderIntention tokenOrderIntention) {
		tokenOrderIntentionMapper.insertSelective(tokenOrderIntention);
	}

	@Override
	public Page<TokenOrderIntention> getIntentionOrderbyPage(String assetName, int pageNo, int pageSize) {
		PageHelper.orderBy("id desc");
		PageHelper.startPage(pageNo, pageSize);
		if (assetName == null) {
			return tokenOrderIntentionMapper.getTokenOrderIntentionByAssetName(null);
		} else {
			return tokenOrderIntentionMapper.getTokenOrderIntentionByAssetName(assetName + "%");
		}

	}

	@Override
	public void changeIntentionOrderState(int uid, int id, TokenOrderIntentionState tokenOrderIntentionState) {
		TokenOrderIntention tokenOrderIntention = tokenOrderIntentionMapper.selectByPrimaryKey(id);
		if (tokenOrderIntention == null) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_INTENTION_NOT_EXIST);
		}
		if (tokenOrderIntention.getUid() != uid) {
			throw new AppException(OfflineOrderConst.TOKEN_ORDER_INTENTION_NOT_EXIST);
		}
		tokenOrderIntention.setState(tokenOrderIntentionState);
		tokenOrderIntentionMapper.updateByPrimaryKeySelective(tokenOrderIntention);
	}

	@Override
	public TokenOrderIntention getById(int id) {

		return tokenOrderIntentionMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateUserComplete(Integer sellUid) {
		tokenOrderIntentionMapper.updateUserComplete(sellUid);

	}

	@Override
	public Page<TokenOrderIntention> getIntentionOrderbyPage(String assetName, TokenOrderIntentionState status,
			Integer uid, int pageNo, int pageSize) {
		PageHelper.orderBy("id desc");
		PageHelper.startPage(pageNo, pageSize);

		return tokenOrderIntentionMapper.getTokenOrderIntention(uid, status, assetName);

	}

}
