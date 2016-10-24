package com.tym.model.exceptions;

public class IncomeException extends PaymentException {

	private static final long serialVersionUID = -8426293356184520008L;

	public IncomeException() {}

	public IncomeException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public IncomeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IncomeException(String arg0) {
		super(arg0);
	}

	public IncomeException(Throwable arg0) {
		super(arg0);
	}

	
}
