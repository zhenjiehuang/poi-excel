package com.poi.excel.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 一开始就反射完，减少每次实例化时的反射次数
 * 
 * @ClassName:ExcelReflect
 * @Description:
 * @author root
 *
 */
public class ExcelReflect {
	/**
	 * 
	 */
	private Field field;
	/**
	 * 
	 */
	private Method method;
	/**
	 * 
	 */
	private ExcelColumn excelColumn;

	public ExcelReflect() {
	}

	public ExcelReflect(Field field, Method method, ExcelColumn excelColumn) {
		this.field = field;
		this.method = method;
		this.excelColumn = excelColumn;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public ExcelColumn getExcel() {
		return excelColumn;
	}

	public void setExcel(ExcelColumn excelColumn) {
		this.excelColumn = excelColumn;
	}

}
