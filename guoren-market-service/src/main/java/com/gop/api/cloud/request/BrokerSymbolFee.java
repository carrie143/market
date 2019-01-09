package com.gop.api.cloud.request;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Lxa on 2018/6/14.
 *
 * @author lixianan
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BrokerSymbolFee {

  private Long id;
  private String brokerId;
  private String method;
  private String tradeAsset;
  private String priceAsset;
  private BigDecimal feeMin;
  private BigDecimal feeRatio;
  private Date createTime;
  private Date updateTime;

  public static BrokerSymbolFeeBuilder builder() {
    return new BrokerSymbolFeeBuilder();
  }

  public static class BrokerSymbolFeeBuilder {
    private Long id;
    private String brokerId;
    private Method method;
    private String tradeAsset;
    private String priceAsset;
    private BigDecimal feeMin;
    private BigDecimal feeRatio;
    private Date createTime;
    private Date updateTime;

    public BrokerSymbolFee build() {
      return new BrokerSymbolFee(id, brokerId, method == null ? null : method.name(), tradeAsset,
          priceAsset, feeMin, feeRatio, createTime, updateTime);
    }

    public BrokerSymbolFeeBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public BrokerSymbolFeeBuilder brokerId(String brokerId) {
      this.brokerId = brokerId;
      return this;
    }

    public BrokerSymbolFeeBuilder method(Method method) {
      this.method = method;
      return this;
    }

    public BrokerSymbolFeeBuilder tradeAsset(String tradeAsset) {
      this.tradeAsset = tradeAsset;
      return this;
    }

    public BrokerSymbolFeeBuilder priceAsset(String priceAsset) {
      this.priceAsset = priceAsset;
      return this;
    }

    public BrokerSymbolFeeBuilder feeMin(BigDecimal feeMin) {
      this.feeMin = feeMin;
      return this;
    }

    public BrokerSymbolFeeBuilder feeRatio(BigDecimal feeRatio) {
      this.feeRatio = feeRatio;
      return this;
    }

    public BrokerSymbolFeeBuilder createTime(Date createTime) {
      this.createTime = createTime;
      return this;
    }

    public BrokerSymbolFeeBuilder updateTime(Date updateTime) {
      this.updateTime = updateTime;
      return this;
    }
  }

  public enum Method {
    RATIO;
  }

}
