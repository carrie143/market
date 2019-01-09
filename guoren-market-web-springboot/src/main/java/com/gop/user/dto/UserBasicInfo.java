package com.gop.user.dto;

import com.gop.domain.enums.CardType;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserBasicInfo extends BaseDto {
	private Integer uid;

	private String account;

	private String authLevel;

	private String name;

	private boolean confirm;
	
	private String nickname;

	private String mobile;

	private UserInfoExtendDto userInfoExtend;
	
	private boolean isSetPayPassword;

	private CardType cardType;
}
