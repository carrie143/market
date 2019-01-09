package com.gop.match.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BatchMatchOrderDto {

	private List<MatchOrderDto> matchOrderDtos = new ArrayList<>();

}
