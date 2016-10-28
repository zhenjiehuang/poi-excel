package com.poi.excel.format;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.exception.CellException;
import com.poi.excel.util.RegExpValidator;

public class NoChinaStringFormat extends DefaultStringFormat {

	@Override
	protected Object formatString(Object value, ExcelColumn excelColumn, int row) throws CellException {
		if (RegExpValidator.IsEngAndNumLengthAuto(value.toString(), excelColumn.length())) {
			return value.toString();
		} else {
			throw new CellException(
					"<" + excelColumn.columnName() + ">" + "长度不能超过" + excelColumn.length() + "或者数据中存在中文字符错误");
		}
	}

}
