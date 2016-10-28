package com.poi.excel.format;

import org.apache.poi.ss.usermodel.Cell;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.exception.CellException;

public interface FormatValue {

	static final String NOT_NONE = "不能为空";

	/**
	 * 
	 * @param value
	 * @param excelColumn
	 * @param row
	 * @param resultType
	 * @return
	 * @throws CellException
	 */
	public Object format(Cell cell, ExcelColumn excelColumn, int row, Class<?> resultType) throws CellException;
}
