package com.gop.certification.dto;

import org.springframework.web.multipart.MultipartFile;

import com.gop.mode.vo.BaseDto;

import lombok.Data;

@Data
public class PhotoDto extends BaseDto{
	
	MultipartFile file;
}
