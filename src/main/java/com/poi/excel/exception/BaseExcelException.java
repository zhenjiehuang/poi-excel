package com.poi.excel.exception;

public class BaseExcelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8594719767768298768L;

	protected String message;

	public BaseExcelException() {
	}

	public BaseExcelException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
