package com.poi.excel.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.exception.CellException;
import com.poi.excel.util.ExcelUtil;

public class DateFormat implements FormatValue {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	@Override
	public Object format(Cell cell, ExcelColumn excelColumn, int row, Class<?> resultType) throws CellException {
		try {
			Date value = null;
			try {
				value = cell.getDateCellValue();
			} catch (Exception e) {
				Object data = ExcelUtil.getCellValue(cell);
				value = sdf.parse(data.toString());
			}
			if (value == null) {
				if (excelColumn.none()) {
					return null;
				} else {
					throw new CellException("<" + excelColumn.columnName() + ">" + NOT_NONE);
				}
			} else {
				return value;
			}
		} catch (CellException e) {
			throw e;
		} catch (Exception e) {
			throw new CellException("数据单元格式不是日期或输入格式错误");
		}
	}

}
