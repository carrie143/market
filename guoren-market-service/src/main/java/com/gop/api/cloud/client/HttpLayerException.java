package com.gop.api.cloud.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by zhangjinyang on 2018/6/20.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class HttpLayerException extends CommunicationException {

  private static final long serialVersionUID = 1L;


  public HttpLayerException(Errors error) {
    super(error);
  }

  public HttpLayerException(Errors error, String additionalMsg) {
    super(error, additionalMsg);
  }

  public HttpLayerException(Errors error, Exception cause) {
    super(error, cause);
  }
}
