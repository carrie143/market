package com.gop.api.cloud.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Lxa on 2018/1/30.
 *
 * @author lixianan
 */
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class KlineQueryPages {

  private Pages<KlineRecord> pages;

  @EqualsAndHashCode
  @Data
  @NoArgsConstructor
  public static class KlineRecord {

    private Long time;

    private String klineType;

    private String symbol;

    private BigDecimal open;

    private BigDecimal close;

    private BigDecimal maxPrice;

    private BigDecimal lowPrice;

    private BigDecimal amount;

  }

}
