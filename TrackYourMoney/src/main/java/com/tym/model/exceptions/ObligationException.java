package com.tym.model.exceptions;

public class ObligationException extends PaymentException {
	
	private static final long serialVersionUID = -2552666114460858808L;

	public ObligationException() {}

	public ObligationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ObligationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ObligationException(String arg0) {
		super(arg0);
	}

	public ObligationException(Throwable arg0) {
		super(arg0);
	}

	
}
