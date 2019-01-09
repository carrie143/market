package com.gop.mode.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ImportExcelResultVo {

	@Setter
	@Getter
	private Integer successCount;
	@Setter
	@Getter
	private Integer failCount;

	@Setter
	@Getter
	private String erroMessage;

	@Getter
	private List<errorPair> errorPair = new ArrayList<>();

	public synchronized void addPair(Integer row, Integer column, String errorMsg) {
		errorPair.add(new errorPair(row, column, errorMsg));
	}

	@Data
	@AllArgsConstructor
	public class errorPair {
		private Integer row;
		private Integer column;
		private String errorMessage;
	}

}
