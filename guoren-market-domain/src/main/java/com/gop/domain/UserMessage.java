package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.UserMessageCategory;
import com.gop.domain.enums.UserMessageStatus;

import lombok.Data;

@Data
public class UserMessage {
    private Integer id;

    private Integer uid;

    private UserMessageCategory category;

    private Date createDate;

    private UserMessageStatus status;

    private String content;

}