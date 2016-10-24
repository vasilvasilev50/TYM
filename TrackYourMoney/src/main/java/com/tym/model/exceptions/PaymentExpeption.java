package com.tym.model.exceptions;

public class PaymentExpeption extends Exception {

	private static final long serialVersionUID = -5027401775046671742L;

	public PaymentExpeption() {
		super();
	}

	public PaymentExpeption(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PaymentExpeption(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PaymentExpeption(String arg0) {
		super(arg0);
	}

	public PaymentExpeption(Throwable arg0) {
		super(arg0);
	}

}
