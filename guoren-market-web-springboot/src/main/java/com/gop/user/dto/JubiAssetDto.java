package com.gop.user.dto;

import java.util.List;
import org.hibernate.validator.constraints.NotBlank;
import com.gop.mode.vo.BaseDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JubiAssetDto extends BaseDto{
	
	@NotBlank
	private String randomkey;
	
	private List<JubiAuthDto> assetList;
	
}
