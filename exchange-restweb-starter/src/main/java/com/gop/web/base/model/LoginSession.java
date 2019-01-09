package com.gop.web.base.model;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginSession implements Serializable {

	private static final long serialVersionUID = 610401627875700703L;
	private Integer userId;

}
