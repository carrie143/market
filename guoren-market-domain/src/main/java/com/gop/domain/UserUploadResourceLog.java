package com.gop.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class UserUploadResourceLog {
    private Integer id;

    private Integer uid;

    private String tag;

    private String datatype;

    private String soucre;

    private String storetype;

    private Date createtime;

    private Date updatetime;

   
}