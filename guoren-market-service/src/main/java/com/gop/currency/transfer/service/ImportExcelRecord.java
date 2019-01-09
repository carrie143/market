package com.gop.currency.transfer.service;

import java.io.File;
import java.io.InputStream;

import com.gop.mode.vo.ImportExcelResultVo;

public interface ImportExcelRecord {
	public ImportExcelResultVo importRecordExcel(File file, String fileName);

	public ImportExcelResultVo importRecordExcel(InputStream is, String fileName, Integer uid);
}
