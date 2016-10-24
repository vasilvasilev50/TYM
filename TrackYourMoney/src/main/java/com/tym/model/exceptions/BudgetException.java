package com.tym.model.exceptions;

public class BudgetException extends PaymentException {

	private static final long serialVersionUID = 7302958163408395580L;

	public BudgetException() {}

	public BudgetException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BudgetException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BudgetException(String arg0) {
		super(arg0);
	}

	public BudgetException(Throwable arg0) {
		super(arg0);
	}

	
}
