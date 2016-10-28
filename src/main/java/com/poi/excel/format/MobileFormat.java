package com.poi.excel.format;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.exception.CellException;
import com.poi.excel.util.RegExpValidator;

public class MobileFormat extends DefaultStringFormat {

	@Override
	protected Object formatString(Object value, ExcelColumn excelColumn, int row) throws CellException {
		if (RegExpValidator.IsMobile(value.toString())) {
			return value;
		} else {
			throw new CellException("<" + excelColumn.columnName() + ">" + "不是正确的手机号码");
		}
	}
}
