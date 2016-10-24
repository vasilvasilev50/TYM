package com.tym.model.exceptions;

public class ExpenseException extends PaymentException {

	private static final long serialVersionUID = -1113180751974014705L;

	public ExpenseException() {}

	public ExpenseException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ExpenseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ExpenseException(String arg0) {
		super(arg0);
	}

	public ExpenseException(Throwable arg0) {
		super(arg0);
	}

	
	
}
