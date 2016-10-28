package com.poi.excel.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.base.ExcelReflect;
import com.poi.excel.exception.CellException;
import com.poi.excel.exception.RowException;
import com.poi.excel.util.ExcelUtil;

/**
 * 导入Excel到内存
 * 
 * @ClassName:InExcel
 * @Description:
 * @author root
 * 
 * @param <T>
 */
public class ImportExcel<T> extends BaseExcel<T> {

	protected InputStream in;
	/**
	 * 
	 */
	protected List<String> errors = new ArrayList<String>();

	private long totalRow;

	public ImportExcel(File file, Class<T> clz) {
		super(clz);
		if (!file.exists()) {
			throw new RuntimeException("file not exist!");
		}

		try {
			this.in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.clz = clz;
		init();
		closeStream(true);
	}

	public ImportExcel(InputStream in, boolean needStreamClose, Class<T> clz) {
		super(clz);
		this.clz = clz;
		this.in = in;
		init();
		closeStream(needStreamClose);
	}

	private void closeStream(boolean needStreamClose) {
		try {
			if (needStreamClose)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	protected void init() {
		datas = new ArrayList<T>();

		try {
			Workbook workbook = ExcelUtil.createWorkbook(in);
			for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
				// 得到sheet
				Sheet sheet = workbook.getSheetAt(s);
				// 得到数据行数
				int rowNum = sheet.getPhysicalNumberOfRows();
				if (rowNum != 0) {
					// 遍历数据
					initRows(sheet, rowNum);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private void initRows(Sheet sheet, int rowNum) {
		for (int i = BASE_ROW + 1; i <= rowNum + 1; i++) {
			// 得到第i行
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			try {
				T t = getRow(sheet, row);
				datas.add(t);
			} catch (RowException e) {
				errors.addAll(e.getErrors());
			}
		}
	}

	protected T getRow(Sheet sheet, Row row) throws RowException {
		totalRow++;
		T t = null;
		try {
			t = clz.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Object cellValue = null;
		int rowNum = row.getRowNum();
		List<String> errors = new ArrayList<String>();
		for (ExcelReflect reflect : reflects) {
			ExcelColumn excelColumn = reflect.getExcel();
			try {
				cellValue = excelColumn.columnType().getFormat().format(
						row.getCell(excelColumn.columnNum() + BASE_COLUMN), excelColumn, rowNum,
						reflect.getField().getType());
				reflect.getMethod().invoke(t, cellValue);
			} catch (Exception e) {
				String error = sheet.getSheetName() + "，第" + (row.getRowNum() + BASE_ROW) + "行，第"
						+ (excelColumn.columnNum() + BASE_COLUMN) + "列，";
				if (e instanceof CellException) {
					errors.add(error + e.getMessage());
				} else {
					e.printStackTrace();
					errors.add(error + "<" + cellValue + ">" + "数据异常! 具体错误:" + e.toString());
				}
			}
		}
		if (errors.size() > 0) {
			throw new RowException(errors);
		}

		return t;
	}

	@Override
	protected Method getMethod(Field field) throws NoSuchMethodException, SecurityException {
		return clz.getMethod("set".concat(super.getMethodName(field.getName())), field.getType());
	}

	public List<T> getRowDatas() {
		return datas;
	}

	public Long getTotalRow() {
		return totalRow;
	}

	public List<String> getErrors() {
		return errors;
	}

}
