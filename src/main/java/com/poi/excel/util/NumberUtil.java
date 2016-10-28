package com.poi.excel.util;

import java.math.BigDecimal;

/**
 * Number数字工具类
 * 
 */
public class NumberUtil {

	/**
	 * 判断是否为不为空的Number
	 * 
	 * @param number
	 *            number
	 * @return boolean
	 */
	public static boolean isNotNullNumber(Object number) {
		try {
			if (!ExcelUtil.isBlank(number)) {
				new BigDecimal(number.toString().trim());
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
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
	 * 金币计算策略,默认取整数,不四舍五入
	 * 
	 * @param d
	 *            d
	 * @return long
	 */
	public static long calcGold(Double d) {
		return d.longValue();
	}

	/**
	 * 判断一个字符串是否为数值类型
	 * 
	 * @param str
	 *            str
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 如果小数点后全部为0，返回小数点前面的数字，否则返回本身
	 */
	public static String getNumber(String str) {
		if (str == null || !isNumeric(str)) {
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

}
