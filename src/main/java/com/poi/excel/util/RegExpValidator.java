package com.poi.excel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName:RegExpValidator
 * @Description:
 * @author root
 * 
 */
public final class RegExpValidator {

	/**
	 * 验证数据长度
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static boolean IsLengthAuto(String str, int length) {
		String regex = "^([\\S]{1," + length + "}$)";
		return match(regex, str.replaceAll(" ", ""));
	}

	/**
	 * 手机号码校验
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsMobile(String str) {
		String regex = "^$|(1[3|5|8]\\d{9})|(0\\d{2,3}-\\d{7,8})";
		return match(regex, str);
	}

	/**
	 * 英文，数字 验证数据长度
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static boolean IsEngAndNumLengthAuto(String str, int length) {
		String regex = "[a-zA-Z0-9.-]{1," + length + "}";
		return match(regex, str);
	}

	/**
	 * 校验IP地址
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsIP(String str) {
		String regex = "((25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1?\\d?\\d)";
		return match(regex, str);

	}

	/**
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static boolean IsNumLengthAuto(String str, int length) {
		String regex = "[\\d+(\\.\\d+)?]{1," + length + "}";
		return match(regex, str);
	}

	/**
	 * 数字最大为65535
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsNumMax65535(String str) {
		String regex = "^$|(65535|(\\d{0,4}|[0-6][0-5][0-5][0-3][0-5]))";
		return match(regex, str);
	}

	/**
	 * 验证0或者1
	 * 
	 * @param str
	 * @return
	 */
	public static boolean Is0or1(String str) {
		String regex = "0|1{0,1}";
		return match(regex, str);

	}

	/**
	 * 维度校验
	 * 
	 * @param str
	 * @return
	 */
	public static boolean regWD(String str) {
		String regex = "^$|(-|\\+)?(90|90\\.0{1,5}|(\\d|[1-8]\\d)\\.\\d{1,5}|(\\d|[1-8]\\d))";
		return match(regex, str);
	}

	/**
	 * 经度校验
	 * 
	 * @param str
	 * @return
	 */
	public static boolean regJD(String str) {
		String regex = "^$|(-|\\+)?(180|180\\.0{1,5}|(\\d{1,2}|1([0-7]\\d))\\.\\d{1,5}|(\\d{1,2}|1([0-7]\\d)))";
		return match(regex, str);
	}

	/**
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

}
