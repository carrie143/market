package com.gop.notice.vo;
/**
 * Created by Administrator on 2016/10/24.
 */
public class CheckErrorMessage extends BaseMessage {
    public CheckErrorMessage(String errorMessage) {
        super(errorMessage, "405");
    }

}
