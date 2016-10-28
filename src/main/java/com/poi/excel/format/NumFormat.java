package com.poi.excel.format;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.exception.CellException;
import com.poi.excel.util.ExcelUtil;
import com.poi.excel.util.RegExpValidator;

public class NumFormat extends DefaultFormat {

	@Override
	protected Object getValue(Object value, ExcelColumn excelColumn, int row, Class<?> resultType)
			throws CellException {
		if (ExcelUtil.isBlank(value)) {
			if (excelColumn.none()) {
				return null;
			} else {
				throw new CellException("<" + excelColumn.columnName() + ">" + NOT_NONE);
			}
		} else {
			if (RegExpValidator.IsNumLengthAuto(value.toString(), excelColumn.length())) {
				try {
					Class<?> clz = Class.forName(
							NumFormat.class.getName() + "$" + resultType.getSimpleName().toUpperCase() + "Data");
					Constructor<?> con = clz.getDeclaredConstructors()[0];
					NumberData format = (NumberData) con.newInstance(this);
					return format.getValue(value.toString());
				} catch (Exception e) {
					throw new CellException(
							"<" + excelColumn.columnName() + ">" + "不支持的数据类型" + resultType.getSimpleName());
				}
			} else {
				throw new CellException("<" + excelColumn.columnName() + ">" + "必须是数字！");
			}
		}
	}

	interface NumberData {
		Number getValue(String value);
	}

	class BIGDECIMALData implements NumberData {
		@Override
		public Number getValue(String value) {
			return new BigDecimal(value);
		}
	}

	class DOUBLEData implements NumberData {

		@Override
		public Number getValue(String value) {
			return Double.parseDouble(value);
		}
	}

	class INTEGERData implements NumberData {
		@Override
		public Number getValue(String value) {
			return Integer.parseInt(value);
		}
	}

	class LONGData implements NumberData {
		@Override
		public Number getValue(String value) {
			return Long.parseLong(value);
		}
	}
}
