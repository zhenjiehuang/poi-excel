package com.poi.excel.parse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.base.ExcelReflect;
import com.poi.excel.base.Sheet;

/**
 * @ClassName:BaseExcel
 * @Description:
 * @author root
 *
 * @param <T>
 */
public abstract class BaseExcel<T> {

	// 默认0开始
	protected static final int BASE_ROW = 0;

	// 默认0开始
	protected static final int BASE_COLUMN = 0;

	/**
	 * 
	 */
	protected Class<T> clz;
	/**
	 * 
	 */
	protected List<T> datas;
	/**
	 * 
	 */
	protected ExcelReflect[] reflects;
	/**
	 * 
	 */
	protected ExcelReflect sheet;

	public BaseExcel(Class<T> clz) {
		this.clz = clz;
		reflect();
	}

	/**
	 * 
	 */
	private void reflect() {
		try {
			Class<?> clzTemp = clz;
			List<ExcelReflect> fs = new ArrayList<ExcelReflect>();
			while (!clzTemp.equals(Object.class)) {
				Field[] fields = clzTemp.getDeclaredFields();
				for (Field field : fields) {
					ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
					if (excelColumn != null) {
						Method method = getMethod(field);
						fs.add(new ExcelReflect(field, method, excelColumn));

						Sheet s = field.getAnnotation(Sheet.class);
						if (sheet == null && s != null) {
							sheet = new ExcelReflect(field, method, excelColumn);
						}
					}
				}
				clzTemp = clzTemp.getSuperclass();
			}
			reflects = fs.toArray(new ExcelReflect[0]);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	protected abstract Method getMethod(Field field) throws NoSuchMethodException, SecurityException;

	protected final String getMethodName(String methodName) {
		return methodName.substring(0, 1).toUpperCase().concat(methodName.substring(1));
	}
}
