package com.poi.excel.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ClassName:ExcelColumn
 * @Description:
 * @author root
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

	/**
	 * 列号,0开始
	 */
	int columnNum();

	/**
	 * 列对应的字段名称
	 */
	String columnName();

	/**
	 * 列数据类型
	 */
	Type columnType() default Type.String;

	/**
	 * 是否可以空
	 */
	boolean none() default true;

	/**
	 * 字符长度
	 */
	int length() default 100;

	/**
	 * 导出列表宽，默认20个字符
	 */
	int columnWeight() default 20;

}
