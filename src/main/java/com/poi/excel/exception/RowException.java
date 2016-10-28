package com.poi.excel.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:IllegalDataException
 * @Description:
 * @author root
 *
 */
public class RowException extends BaseExcelException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4985437481300043902L;

	private List<String> errors = new ArrayList<String>();

	public RowException(List<String> errors) {
		this.errors = errors;
	}

	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}

}
