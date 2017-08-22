package test;

import java.util.Date;

import com.poi.excel.base.ExcelColumn;
import com.poi.excel.base.Type;

//@Title
public class ExcelBean {


	@ExcelColumn(columnNum = 0, columnName = "测试String", columnType = Type.String)
	private String col1;

	@ExcelColumn(columnNum = 1, columnName = "测试Integer", columnType = Type.Num)
	private Integer col2;

	@ExcelColumn(columnNum = 2, columnName = "测试Long", columnType = Type.Num)
	private Long col3;

	@ExcelColumn(columnNum = 3, columnName = "测试Double", columnType = Type.Num)
	private Double col4;

	@ExcelColumn(columnNum = 4, columnName = "测试Date", columnType = Type.Date)
	private Date col5;

	@ExcelColumn(columnNum = 5, columnName = "测试NumString", columnType = Type.NumString)
	private String col6;

	@ExcelColumn(columnNum = 6, columnName = "测试NoChinaString", columnType = Type.NoChinaString)
	private String col7;

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public Integer getCol2() {
		return col2;
	}

	public void setCol2(Integer col2) {
		this.col2 = col2;
	}

	public Long getCol3() {
		return col3;
	}

	public void setCol3(Long col3) {
		this.col3 = col3;
	}

	public Double getCol4() {
		return col4;
	}

	public void setCol4(Double col4) {
		this.col4 = col4;
	}

	public Date getCol5() {
		return col5;
	}

	public void setCol5(Date col5) {
		this.col5 = col5;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

}
