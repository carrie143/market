package com.gop.notice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2016/10/24.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseMessage {
    public static final BaseMessage SERVER_ERROR_MESSAGE = new BaseMessage("服务器异常", "100103");
    public static final BaseMessage FIELD_ERROR_MESSAGE = new BaseMessage("传入字段校验错误", "100100");

    public static final BaseMessage SUCCESS_MESSAGE = new BaseMessage("成功", "100200");
    private String msg;
    private String code;
}
