package com.gop.user.dto;

import java.util.HashMap;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UnverifiedInfoDto {
	private HashMap<String, Integer> withdrawCoinMap = new HashMap<>();
	private Integer identificationInitNum;
	private Integer residenceInitNum;

}
