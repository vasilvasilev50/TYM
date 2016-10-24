package com.tym.model;

import java.time.LocalDate;

import com.tym.model.exceptions.PaymentException;

public class Income extends Payment {

	public Income(int categoryId, String category, String repeating, int reapeatingId, double amount, LocalDate date,
			String description, int id) throws PaymentException {
		super(categoryId, category, repeating, reapeatingId, amount, date, description, id);
	}

	public Income(){}

}
