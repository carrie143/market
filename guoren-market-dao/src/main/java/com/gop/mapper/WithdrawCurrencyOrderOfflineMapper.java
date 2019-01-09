package com.gop.mapper;

import com.gop.domain.WithdrawCurrencyOrderOffline;

public interface WithdrawCurrencyOrderOfflineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WithdrawCurrencyOrderOffline record);

    int insertSelective(WithdrawCurrencyOrderOffline record);

    WithdrawCurrencyOrderOffline selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WithdrawCurrencyOrderOffline record);

    int updateByPrimaryKey(WithdrawCurrencyOrderOffline record);
}