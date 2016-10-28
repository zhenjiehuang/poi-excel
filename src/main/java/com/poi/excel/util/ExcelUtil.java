package com.poi.excel.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @ClassName:ExcelUtil
 * @Description:
 * @author root
 * 
 */
public abstract class ExcelUtil {

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isBlank(Object obj) {
		return obj == null || obj.toString().length() == 0;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	private static String removeEndZero(String str) {
		if (str.endsWith("0")) {
			str = str.substring(0, str.length() - 1);
		} else {
			return str;
		}
		return removeEndZero(str);
	}

	/**
	 * 如果小数点后全部为0，返回小数点前面的数字，否则返回本身
	 */
	public static String getNumber(String str) {
		if (str == null || !NumberUtil.isNumeric(str)) {
			return str;
		}
		String[] strs = str.split("\\.");
		if (strs.length != 2) {
			return str;
		}
		try {
			if (Long.valueOf(strs[1]) > 0) {// 不能转换 则说明小数点后的数据含有其他符号
				return removeEndZero(str);
			} else {
				return strs[0];
			}
		} catch (NumberFormatException e) {
			return str;
		}
	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public static String formatMaxPointString(Object number) {
		if (isNotNullNumber(number)) {
			java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("##.##");
			decimalFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
			return new BigDecimal(number.toString().trim()).toPlainString();

		}
		return null;
	}

	/**
	 * 判断是否为不为空的Number
	 * 
	 * @param number
	 *            number
	 * @return boolean
	 */
	public static boolean isNotNullNumber(Object number) {
		try {
			if (!isBlank(number)) {
				new BigDecimal(number.toString().trim());
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return String
	 */
	public static Object getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			String value = cell.getStringCellValue().trim();
			String cellValue = NumberUtil.getNumber(value);
			return cellValue;
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return NumberUtil.getNumber(NumberUtil.formatMaxPointString(cell.getNumericCellValue())); // 去除数字.后面所有的0
		} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
			return "";
		}
		return "";
	}

	/**
	 * 根据输入流得到Workbook
	 * 
	 * @param ins
	 *            ins
	 * @return Workbook
	 */
	public static Workbook createWorkbook(InputStream ins) {
		Workbook workbook = null;
		// 根据输入流创建Workbook对象
		try {
			workbook = WorkbookFactory.create(ins);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return workbook;
	}

}
