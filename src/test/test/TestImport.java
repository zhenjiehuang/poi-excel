package test;

import java.io.File;
import java.lang.reflect.Method;

import com.poi.excel.parse.ImportExcel;

public class TestImport {

	static int index = 0;

	private static void print(ExcelBean bean, Method[] methods) throws Exception {
		System.out.print(index++ + " ");
		for (Method method : methods) {
			System.out.print(method.getName().substring(3) + ":" + method.invoke(bean) + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		ImportExcel<ExcelBean> importExcel = new ImportExcel<ExcelBean>(new File("1.xls"), ExcelBean.class);

		Class<ExcelBean> clz = ExcelBean.class;
		Method[] allMethods = clz.getDeclaredMethods();
		Method[] methods = new Method[clz.getDeclaredFields().length];
		for (Method method : allMethods) {
			if (method.getName().startsWith("get"))
				methods[index++] = method;
		}

		index = 0;
		for (ExcelBean bean : importExcel.getRowDatas()) {
			print(bean, methods);
		}
	}
}
