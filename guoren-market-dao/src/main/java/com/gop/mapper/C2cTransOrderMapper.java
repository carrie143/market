package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cTransOrder;
import com.gop.domain.enums.C2cTransOrderStatus;

public interface C2cTransOrderMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(C2cTransOrder record);

	int insertSelective(C2cTransOrder record);

	C2cTransOrder selectByPrimaryKey(Integer id);


	int updateByPrimaryKeySelective(C2cTransOrder record);

	int updateByPrimaryKey(C2cTransOrder record);

	C2cTransOrder selectByTransOrderId(String transOrderId);

	int updateByPrimaryKeyAndStatus(@Param("id") Integer id, @Param("preStatus") C2cTransOrderStatus preStatus,
			@Param("endStatus") C2cTransOrderStatus endStatus,@Param("flag")String flag);

	List<C2cTransOrder> queryByCompletedStatus(Integer uid);

	List<C2cTransOrder> queryBuyerByUncompletedStatus(@Param("uid") Integer uid,
			@Param("status") C2cTransOrderStatus status);

	List<C2cTransOrder> querySellerByUncompletedStatus(@Param("uid") Integer uid,
			@Param("status") C2cTransOrderStatus status);

	List<C2cTransOrder> selectByStatus(C2cTransOrderStatus status);

	List<C2cTransOrder> queryByflagAndPayTypeAndStatus(@Param("seller") Integer seller, @Param("buyer") Integer buyer,
			@Param("completed") String completed, @Param("status") C2cTransOrderStatus status);

	List<C2cTransOrder> queryBySellUidOrBuyUidAndStatus(@Param("sellUid")Integer sellUid, @Param("buyUid")Integer buyUid,@Param("status")C2cTransOrderStatus status);


}