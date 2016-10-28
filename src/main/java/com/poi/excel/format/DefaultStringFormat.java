package com.poi.excel.format;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.exception.CellException;
import com.poi.excel.util.ExcelUtil;

abstract class DefaultStringFormat extends DefaultFormat {

	@Override
	protected Object getValue(Object value, ExcelColumn excelColumn, int row, Class<?> resultType)
			throws CellException {
		if (ExcelUtil.isBlank(value)) {
			if (excelColumn.none()) {
				return value;
			} else {
				throw new CellException("<" + excelColumn.columnName() + ">" + NOT_NONE);
			}
		} else {
			return formatString(value, excelColumn, row);
		}
	}

	/**
	 * 
	 * @param value
	 * @param excelColumn
	 * @param row
	 * @return
	 * @throws CellException
	 */
	protected abstract Object formatString(Object value, ExcelColumn excelColumn, int row) throws CellException;

}
