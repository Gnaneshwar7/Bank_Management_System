package com.bank.Exception;

public class CustomerDataInvalidException extends RuntimeException{
	private String except;
	public CustomerDataInvalidException() {}
	public CustomerDataInvalidException(String except) {
		super();
		this.except = except;
	}
	public String getExcept() {
		return except;
	}
	public void setExcept(String except) {
		this.except = except;
	}
	public String toString() {
		return getClass()+" : "+except;
	}
	
}
