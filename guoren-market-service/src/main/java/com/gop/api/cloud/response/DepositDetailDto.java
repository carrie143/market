package com.gop.api.cloud.response;

import com.gop.domain.enums.DepositCoinAssetStatus;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class DepositDetailDto {

	// @ApiModelProperty("订单创建时间")
	private Date createDate;

	// @ApiModelProperty("券商id")
	private String brokerId;

	// @ApiModelProperty("uid")
	private Long uid;

	// @ApiModelProperty("转入钱包地址")
	private String address;

	 //@ApiModelProperty("assetCode")
	 private String assetCode;

	// @ApiModelProperty("转入数量")
	private BigDecimal amount;

	// @ApiModelProperty("订单状态")
	private DepositCoinAssetStatus status;

	// @ApiModelProperty("订单附言")
	private String msg;

	// @ApiModelProperty("账号信息")
	private String account;

	private Date updateDate;


}
