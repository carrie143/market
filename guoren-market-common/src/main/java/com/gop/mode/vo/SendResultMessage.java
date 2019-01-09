
package com.gop.mode.vo;


import java.io.Serializable;

import lombok.Data;


/**
 * 转果仁，返回信息
 * 
 * @author zhengminghai
 * @version 2015年12月30日
 * @see SendResultMessage
 * @since
 */
@Data
public class SendResultMessage implements Serializable, Cloneable
{

    private static final long serialVersionUID = -236972438683717448L;

    private String id = "";

    private String code = "";

    private String status;

    private String message;


}
