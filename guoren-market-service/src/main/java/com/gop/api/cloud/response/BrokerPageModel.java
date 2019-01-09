package com.gop.api.cloud.response;

import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * Created by wuyanjie on 2018/6/21.
 */
@Data
@ToString
public class BrokerPageModel<T> {
    private List<T> list;
    private String brokerName; //企业名称
    private Integer assetCount; //币种个数
    private Integer pageNum;
    private Integer pageNo;
    private Integer pageSize;
}
