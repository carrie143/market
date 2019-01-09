package com.gop.financecheck.enums;

public enum BusinessSubject {
    DEPOSIT,               // 充值
    WITHDRAW,              // 提现
    WITHDRAW_RETURN,       // 提现退回
    MATCH_BUY,             // 交易买入
    MATCH_SELL,            // 交易卖出

    PUSH_BUY,              //push买入扣款
    PUSH_SELL,             //push卖出扣款
    PUSH_RETURN,
    LOCK,                  //冻结资产
    UNLOCK,                //冻结释放
    FEE,                   // 手续费
    FEE_RETURN,            // 手续费退回
    SYSTEM_TRANSFER,       // 系统账户转账
    BAD_DEBT,              // 坏帐
    ASSET_FIX,             // 资产修正
    INTEREST,              // 利息
    USER_TRANSFER,         // 普通账户转账

    C2C_SELL,	 		  // C2C卖出
    C2C_BUY,				  // C2C买入
    C2C_LOCK,			  // C2C冻结
    C2C_UNLOCK,             // C2C冻结

    RECHARGE,               // 购买充值
    RECHARGE_DEDUCT,       //备用金账户 用户充值时，扣减
    
    QUANT_FUND_TICKET_BUY,  //量化基金入场券购买
    QUANT_FUND_TICKET_RETURN, //量化基金入场券押金退还
    QUANT_FUND_INCOME_TRANSFER, //量化基金收益置换
    
    LOCK_POSITION_REWARD, //锁仓奖励
    LOCK_POSITION_REWARD_LESS, //锁仓账户给用户发放奖励扣除
    
    INVITER_REWARD, // 邀请人奖励
    INVITED_REWARD, // 被邀请奖励
    
    JUBI_TRANSFER_IN,  //聚币转入
   
    ACTIVITY_REWARD,  //活动奖励

    FORK_CANDY_IN     //分叉糖果
}
