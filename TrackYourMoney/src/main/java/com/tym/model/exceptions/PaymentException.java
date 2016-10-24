package com.tym.model.exceptions;

public class PaymentException extends Exception {

	private static final long serialVersionUID = -5027401775046671742L;

	public PaymentException() {
		super();
	}

	public PaymentException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PaymentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PaymentException(String arg0) {
		super(arg0);
	}

	public PaymentException(Throwable arg0) {
		super(arg0);
	}

}
