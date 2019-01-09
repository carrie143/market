package com.gop.mode.vo;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageModel<T> {
	private List<T> list;
	private Integer pageNum;
	private Integer pageNo;
	private Integer pageSize;
	private Long total;
}
