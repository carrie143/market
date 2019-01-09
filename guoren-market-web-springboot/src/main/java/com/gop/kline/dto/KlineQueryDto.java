package com.gop.kline.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by Lxa on 2018/1/30.
 *
 * @author lixianan
 */
@Builder
@Getter
public class KlineQueryDto {
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private int pageSize;
  private String symbol;
  private String kline;
}
