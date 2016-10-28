package com.poi.excel.format;

import org.apache.poi.ss.usermodel.Cell;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.exception.CellException;
import com.poi.excel.util.ExcelUtil;

abstract class DefaultFormat implements FormatValue {

	@Override
	public Object format(Cell cell, ExcelColumn excelColumn, int row, Class<?> resultType) throws CellException {
		return getValue(ExcelUtil.getCellValue(cell), excelColumn, row, resultType);
	}

	/**
	 * 
	 * @param value
	 * @param excelColumn
	 * @param row
	 * @param resultType
	 * @return
	 * @throws CellException
	 */
	protected abstract Object getValue(Object value, ExcelColumn excelColumn, int row, Class<?> resultType)
			throws CellException;

}
