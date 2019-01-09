package com.gop.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserPayPassword {
    private Integer id;

    private Integer uid;

    private Integer lockNum;

    private Date createDate;

}