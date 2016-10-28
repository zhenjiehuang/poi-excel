package com.poi.excel.parse;

import java.io.File;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.poi.excel.exception.RowException;
import com.poi.excel.util.ExcelUtil;

/**
 * @ClassName:InExcelIterator
 * @Description:
 * @author root
 *
 * @param <T>
 */
public class ImportExcelIterator<T> extends ImportExcel<T> {

	private class RowData {
		/**
		 * 
		 */
		int sheetNum;
		/**
		 * 
		 */
		int rowNum;
		/**
		 * 
		 */
		RowData next;

		public RowData(int sheetNum, int rowNum) {
			this.sheetNum = sheetNum;
			this.rowNum = rowNum;
		}
	}

	/**
	 * 
	 */
	private Workbook workbook;
	/**
	 * 
	 */
	private RowData firstRow;

	/**
	 * 
	 */
	private Row currentRow;

	/**
	 * 
	 */
	private Sheet currentSheet;

	public ImportExcelIterator(File file, Class<T> clz) {
		super(file, clz);
	}

	public ImportExcelIterator(InputStream in, boolean needStreamClose, Class<T> clz) {
		super(in, needStreamClose, clz);
	}

	@Override
	protected void init() {
		try {
			workbook = ExcelUtil.createWorkbook(in);
			RowData row = null;
			for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
				// 得到sheet
				currentSheet = workbook.getSheetAt(s);
				// 得到数据行数
				int rowNum = currentSheet.getPhysicalNumberOfRows();
				// 为了firstRow firstRow是标题行，不参与数据解析
				if (rowNum >= BASE_ROW) {
					firstRow = new RowData(s, BASE_ROW);
					row = new RowData(s, BASE_ROW + 1);
					firstRow.next = row;
					for (int i = BASE_ROW + 2; i < rowNum; i++) {
						RowData r = new RowData(s, i);
						row.next = r;
						row = r;
					}
					break;
				}
			}

			if (row == null) {
				return;
			}

			for (int s = row.sheetNum + 1; s < workbook.getNumberOfSheets(); s++) {
				// 得到sheet
				Sheet sheet = workbook.getSheetAt(s);
				// 得到数据行数
				int rowNum = sheet.getPhysicalNumberOfRows();
				for (int i = BASE_ROW; i < rowNum; i++) {
					RowData r = new RowData(s, i);
					row.next = r;
					row = r;
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public boolean hasNext() {
		int currentSheetNo = firstRow.sheetNum;
		firstRow = firstRow.next;
		if (firstRow == null) {
			return false;
		}
		if (firstRow.sheetNum != currentSheetNo) {
			currentSheet = workbook.getSheetAt(firstRow.sheetNum);
		}
		return (currentRow = currentSheet.getRow(firstRow.rowNum)) == null ? hasNext() : true;
	}

	public T getNextRow() throws RowException {
		return super.getRow(currentSheet, currentRow);
	}

	public int getRowNu() {
		return firstRow.rowNum;
	}

	@Override
	@Deprecated
	protected T getRow(Sheet sheet, Row row) {
		throw new RuntimeException();
	}
}
