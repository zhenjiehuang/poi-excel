package com.poi.excel.base;

import com.poi.excel.format.DateFormat;
import com.poi.excel.format.FormatValue;
import com.poi.excel.format.MobileFormat;
import com.poi.excel.format.NoChinaStringFormat;
import com.poi.excel.format.NumFormat;
import com.poi.excel.format.NumStringFormat;
import com.poi.excel.format.StringFormat;

/**
 * @ClassName:Type
 * @Description:
 * @author root
 * 
 */
public enum Type {
	/**
	 * 数字字符串
	 */
	NumString(new NumStringFormat()),
	/**
	 * 字符串
	 */
	String(new StringFormat()),
	/**
	 * 无中文字符
	 */
	NoChinaString(new NoChinaStringFormat()),

	/**
	 * 数字
	 */
	Num(new NumFormat()),
	/**
	 * 日期
	 */
	Date(new DateFormat()),
	/**
	 * 
	 */
	Mobile(new MobileFormat()),

	;

	private FormatValue format;

	private Type(FormatValue format) {
		this.format = format;
	}

	public FormatValue getFormat() {
		return format;
	}

}
