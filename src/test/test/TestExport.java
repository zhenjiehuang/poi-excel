package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.poi.excel.parse.ExportExcel;

public class TestExport {

	private static ExcelBean getBean() {
		ExcelBean bean = new ExcelBean();
		bean.setCol1("abc");
		bean.setCol2(1);
		bean.setCol3(2L);
		bean.setCol4(3D);
		bean.setCol5(new Date());
		bean.setCol6("1234567890");
		bean.setCol7("wfwegwegwegw");

		return bean;
	}

	public static void main(String[] args) {
		List<ExcelBean> beans = new ArrayList<ExcelBean>();
		for (int i = 0; i < 10; i++) {
			beans.add(getBean());
		}

		ExportExcel<ExcelBean> export = new ExportExcel<>(beans, ExcelBean.class);
		export.saveFile("1.xls");

	}
}
