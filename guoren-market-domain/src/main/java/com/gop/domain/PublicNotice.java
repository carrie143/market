package com.gop.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gop.domain.enums.Status;
import com.gop.domain.enums.TopStatus;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Administrator on 2016/10/24.
 */
@Data
@ToString
public class PublicNotice implements Serializable {
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
