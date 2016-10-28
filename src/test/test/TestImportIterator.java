package test;

import java.io.File;
import java.lang.reflect.Method;

import com.poi.excel.exception.RowException;
import com.poi.excel.parse.ImportExcelIterator;

public class TestImportIterator {

	static int index;

	private static void print(ExcelBean bean, Method[] methods) throws Exception {
		System.out.print(index++ + " ");
		for (Method method : methods) {
			System.out.print(method.getName().substring(3) + ":" + method.invoke(bean) + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		ImportExcelIterator<ExcelBean> importExcel = new ImportExcelIterator<ExcelBean>(new File("2.xls"),
				ExcelBean.class);

		Class<ExcelBean> clz = ExcelBean.class;
		Method[] allMethods = clz.getDeclaredMethods();
		Method[] methods = new Method[clz.getDeclaredFields().length];
		for (Method method : allMethods) {
			if (method.getName().startsWith("get"))
				methods[index++] = method;
		}

		index = 0;
		while (importExcel.hasNext()) {
			try {
				ExcelBean bean = importExcel.getNextRow();
				print(bean, methods);
			} catch (RowException e) {
				System.out.print(index++ + " ");
				for (String error : e.getErrors())
					System.out.print(error + " ");
				System.out.println();
			}
		}
	}
}
