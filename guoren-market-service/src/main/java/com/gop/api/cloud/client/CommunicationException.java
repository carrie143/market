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
public abstract class CommunicationException extends Exception {

  private static final long serialVersionUID = 1L;

  private int code;


  public CommunicationException(Errors error) {
    this(error, Constants.STRING_EMPTY);
  }

  public CommunicationException(Errors error, String additionalMsg) {
    super(error.getDescription() + additionalMsg);
    code = error.getCode();
  }

  public CommunicationException(Errors error, Exception cause) {
    super(error.getDescription(), cause);
    code = error.getCode();
  }
}
