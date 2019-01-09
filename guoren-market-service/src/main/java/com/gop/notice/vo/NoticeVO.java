package com.gop.notice.vo;

import java.io.Serializable;
import java.util.Date;

import com.gop.domain.enums.Status;
import com.gop.domain.enums.TopStatus;

import lombok.Data;
@Data
public class NoticeVO implements Serializable{
    private Integer id;
    private Integer uid;
    private String nickname;

    private TopStatus topStatus;
    private Date topTime;
    private Date createTime;
    private String title;
    private String content;
    private Status status = Status.VALID;
}
