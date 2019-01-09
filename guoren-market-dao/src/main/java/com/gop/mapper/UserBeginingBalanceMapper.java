package com.gop.mapper;

import com.gop.domain.UserBeginingBalance;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

/**
 * Created by wuyanjie on 2018/5/17.
 */
public interface UserBeginingBalanceMapper {
    List<UserBeginingBalance> getUserBeginingBalance(@Param("uid") Integer uid,@Param("accountNo") String accountNo,@Param("assetCode") String assetCode, @Param("date") Date date);

    void insertBatch(List<UserBeginingBalance> userBeginingBalances);

    int countUserBeginBalance(@Param("date") Date date);

    int updateStatus(@Param("date") Date date);
}

