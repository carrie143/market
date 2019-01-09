package com.gop.match.dto;

import java.math.BigDecimal;

import com.gop.domain.ConfigSymbol;
import com.gop.domain.enums.SymbolStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
// bid buy ask sell
public class SymbolDto {

	private String symbol;
	private String tradeAsset;
	private String priceAsset;
	private String description;
	private SymbolStatus status;
	private String title;
	private BigDecimal minAmount1;
	private BigDecimal maxAmount1;
	private BigDecimal minAmount2;
	private BigDecimal maxAmount2;
	private Integer minPrecision1;

	private Integer minPrecision2;

	public SymbolDto(ConfigSymbol configSymbol) {
		this.tradeAsset = configSymbol.getTradeAsset();
		this.priceAsset = configSymbol.getPriceAsset();
		this.description = configSymbol.getDescription();
		this.minAmount2 = configSymbol.getMinAmount2();
		this.maxAmount2 = configSymbol.getMaxAmount2();
		this.minAmount1 = configSymbol.getMinAmount1();
		this.maxAmount1 = configSymbol.getMaxAmount1();
		this.symbol = configSymbol.getSymbol();
		this.status = configSymbol.getStatus();
		this.title = configSymbol.getTitle();
		this.minPrecision1 = configSymbol.getMinPrecision1();
		this.minPrecision2 = configSymbol.getMinPrecision2();
	}

}
