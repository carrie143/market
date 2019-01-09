package com.gop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.gop.domain.enums.C2cTransOrderStatus;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class C2cTransOrder {
	private Integer id;

	private String transOrderId;

	private String advertId;

	private String orderId;

	private Integer buyUid;

	private Integer sellUid;

	private String assetCode;

	private String buyNickname;

	private String sellNickname;

	private String buyPayType;

	private BigDecimal number;

	private BigDecimal money;

	private String buyRequestNo;

	private String sellRequestNo;

	private BigDecimal lockNum;

	private BigDecimal fee;

	private C2cTransOrderStatus status;

	private BigDecimal tradePrice;
	private String remark;

	private Date createDate;

	private Date updateDate;

	private String flag;
	private String payCode;
}