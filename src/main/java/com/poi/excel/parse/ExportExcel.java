package com.poi.excel.parse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.base.ExcelReflect;
import com.poi.excel.base.Type;

/**
 * 导出至Excel
 * 
 * @ClassName:OutExcel
 * @Description:
 * @author root
 * 
 * @param <T>
 */
public class ExportExcel<T> extends BaseExcel<T> {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * 以97版导出，最大限定65000
	 */
	private static final int MAX_ROW = 65000;

	// 设置全局变量
	private HSSFWorkbook wb = new HSSFWorkbook();

	public ExportExcel(List<T> datas, Class<T> clz) {
		super(clz);
		this.clz = clz;
		this.datas = datas;
	}

	public void saveFile(File file) {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
			writeStream(fout);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fout.close();
			} catch (IOException e) {
			}
		}
	}

	public void saveFile(String fileName) {
		saveFile(new File(fileName));
	}

	public byte[] getByte() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			writeStream(out);
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public void writeStream(OutputStream out) {
		try {
			Workbook wb = writeDatas();
			wb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initBorderCellStyle(CellStyle style) {
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置边框颜色
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setRightBorderColor(HSSFColor.BLACK.index);
	}

	private Workbook writeDatas() {
		CellStyle style = wb.createCellStyle();
		initBorderCellStyle(style);
		if (sheet == null) {
			writeSheet(datas, wb, style);
		} else {
			Map<Object, List<T>> map = new HashMap<Object, List<T>>();
			try {
				for (T t : datas) {
					Object sheetValue = sheet.getMethod().invoke(t);
					List<T> ts = map.get(sheetValue);
					if (ts == null) {
						ts = new ArrayList<T>();
						map.put(sheetValue, ts);
					}
					ts.add(t);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			for (Object key : map.keySet()) {
				writeSheet(map.get(key), wb, key.toString(), style);
			}
		}

		return wb;
	}

	private void writeSheet(List<T> list, HSSFWorkbook wb, CellStyle style) {
		writeSheet(list, wb, null, style);
	}

	private void writeSheet(List<T> list, HSSFWorkbook wb, String sheetName, CellStyle style) {
		int row = BASE_ROW;
		Sheet sheet = sheetName == null ? wb.createSheet() : wb.createSheet(sheetName);
		initBase(sheet, style);
		boolean tooLong = list.size() > MAX_ROW;
		List<T> temp = tooLong ? list.subList(0, MAX_ROW) : list;
		row++;
		for (T t : temp) {
			Row r = sheet.createRow(row++);
			writeRow(t, r, style);
		}
		if (tooLong)
			writeSheet(list.subList(MAX_ROW, list.size()), wb, sheetName, style);
	}

	private void initBase(Sheet sheet, CellStyle style) {
		sheet.setColumnWidth(0, 700);
		for (ExcelReflect reflect : reflects) {
			ExcelColumn excelColumn = reflect.getExcel();
			sheet.setColumnWidth(excelColumn.columnNum(), excelColumn.columnWeight() * 300);
		}
		writeFirstRow(sheet);
	}

	private void writeFirstRow(Sheet sheet) {
		CellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFont(font);
		initBorderCellStyle(style);
		Row row = sheet.createRow(BASE_ROW);
		row.setHeight((short) 400);
		try {
			for (ExcelReflect reflect : reflects) {
				ExcelColumn excelColumn = reflect.getExcel();
				Cell cell = row.createCell(excelColumn.columnNum() + BASE_COLUMN);
				writeCell(cell, style, excelColumn.columnName());
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private void writeRow(T t, Row row, CellStyle style) {
		try {
			for (ExcelReflect reflect : reflects) {
				Cell cell = row.createCell(reflect.getExcel().columnNum() + BASE_COLUMN);
				Object value = reflect.getMethod().invoke(t);
				if (value == null) {
					writeCell(cell, style, value);
				} else {
					// 日期特殊处理
					value = reflect.getExcel().columnType() == Type.Date ? sdf.format(value) : value;
					writeCell(cell, style, value);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeCell(Cell cell, CellStyle style, Object value) {
		if (value != null && value != "") {
			cell.setCellValue(value.toString());
		} else {
			cell.setCellValue("");
		}
		cell.setCellStyle(style);
	}

	@Override
	protected Method getMethod(Field field) throws NoSuchMethodException, SecurityException {
		return clz.getMethod("get".concat(super.getMethodName(field.getName())));
	}

}
