package com.gop.common.dto;

import com.qiniu.storage.model.DefaultPutRet;

import lombok.Data;

@Data
public class ImageSaveResultDto {
	private String name;

	public ImageSaveResultDto() {
		super();
	}
	public ImageSaveResultDto(String name) {
		super();
		this.name = name;
	}
	
}
