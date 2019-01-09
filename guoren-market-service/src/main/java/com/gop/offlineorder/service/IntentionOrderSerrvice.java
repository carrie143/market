package com.gop.offlineorder.service;

import com.github.pagehelper.Page;
import com.gop.domain.TokenOrderIntention;
import com.gop.domain.enums.TokenOrderIntentionState;

public interface IntentionOrderSerrvice {

	public void createIntentionOrderSerrvice(TokenOrderIntention tokenOrderIntention);

	public Page<TokenOrderIntention> getIntentionOrderbyPage(String assetName, int pageNo, int pageSize);

	public void changeIntentionOrderState(int uid, int id, TokenOrderIntentionState tokenOrderIntentionState);

	public TokenOrderIntention getById(int id);

	public void updateUserComplete(Integer sellUid);

	public Page<TokenOrderIntention> getIntentionOrderbyPage(String assetName, TokenOrderIntentionState status,
			Integer uid, int pageNo, int pageSize);
}
