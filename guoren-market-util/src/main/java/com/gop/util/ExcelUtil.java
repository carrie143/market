package com.gop.util;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public static String getCellString(Cell cell) {

		if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return "";
		}
		if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
			return "";
		}

		String cellString = cell.getStringCellValue();
		return null == cellString ? "" : cellString.trim();

	}

	public static XSSFSheet getSheet(XSSFWorkbook workbook, int index) {
		if (workbook == null)
			throw new IllegalArgumentException("workbook is null");
		return workbook.getSheetAt(index);
	}

	public static XSSFSheet getSheet(XSSFWorkbook workbook, String sheetName) {
		XSSFSheet sheet = workbook.getSheet(sheetName);
		if (null == sheet)
			throw new IllegalArgumentException("Sheet " + sheetName + " is null ");
		return sheet;
	}

	public static List<List<String>> getSheetValues(XSSFWorkbook workbook, int index) {
		XSSFSheet sheet = getSheet(workbook, index);
		return getSheetValues(sheet);
	}

	public static List<String> getRowValue(XSSFRow row) {
		if (row == null)
			return null;
		List<String> list = new ArrayList<String>();
		int firstCell = row.getFirstCellNum();
		int lastCell = row.getLastCellNum();
		for (int i = firstCell; i <= lastCell; i++) {
			if (row.getCell(i) == null) {
				list.add("");
			} else {
				list.add(getCellString(row.getCell(i)));
			}

		}
		return list;
	}

	public static List<List<String>> getSheetValues(XSSFSheet sheet) {
		List<List<String>> list = new ArrayList<List<String>>();
		int maxRowNum = sheet.getLastRowNum();
		int minRowNum = sheet.getFirstRowNum();
		for (int i = minRowNum; i <= maxRowNum; i++) {
			XSSFRow row = sheet.getRow(i);
			List<String> rowValues = getRowValue(row);
			if (null != rowValues) {
				list.add(rowValues);
			}
		}
		return list;
	}

	public static XSSFWorkbook createWorkBook(InputStream is) throws IOException {
		return new XSSFWorkbook(is);
	}

}
