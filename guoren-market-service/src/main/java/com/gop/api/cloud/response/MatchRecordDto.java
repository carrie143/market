package com.gop.api.cloud.response;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * Created by Lxa on 2018/6/21.
 *
 * @author lixianan
 */
@Data
public class MatchRecordDto {

  private BigDecimal price;

  private BigDecimal num;

  private Date createTime;

}