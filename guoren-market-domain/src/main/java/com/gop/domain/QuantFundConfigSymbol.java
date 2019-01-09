package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.QuantFundConfigSymbolStatus;

import lombok.Data;

@Data
public class QuantFundConfigSymbol {
	private Integer id;

	private String fundSymbol;

	private String tradeAssetCode;

	private String fundAssetCode;

	private QuantFundConfigSymbolStatus status;

	private Date createDate;

	private Date updateDate;

}