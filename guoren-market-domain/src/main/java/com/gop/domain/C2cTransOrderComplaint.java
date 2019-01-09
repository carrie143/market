package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.C2cComplaintStatus;
import com.gop.domain.enums.C2cPayType;
import com.gop.domain.enums.C2cTransOrderStatus;
import com.gop.domain.enums.C2cTransType;

import lombok.Data;
@Data
public class C2cTransOrderComplaint {
    private Integer id;

    private String complainId;

    private String orderId;

    private String transOrderId;

    private Integer uid;

    private C2cTransType complainType;

    private String complainReason;

    private String buyPhone;

    private String sellPhone;

    private C2cPayType payType;

    private String payNo;

    private String capture;

    private String remark;

    private C2cComplaintStatus status;
    
    private C2cTransOrderStatus transOrderStatus;

    private Integer operUid;

    private Date createDate;

    private Date updateDate;

   
}