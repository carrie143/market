package com.gop.match.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@ToString
public class BatchMatchOrderResponse {

	private List<Response> responses = new ArrayList<>();

	@Builder
	@Getter
	public static class Response {
		private String orderNo;
		private String returnCode;
		private String returnMsg;
	}

}
