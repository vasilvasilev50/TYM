package com.tym.model;

import java.time.LocalDate;

import com.tym.model.exceptions.PaymentException;

public class Expense extends Payment {

	public Expense(int categoryId, String category, String repeating, int reapeatingId, double amount, LocalDate date,
			String description, int id) throws PaymentException {
		super(categoryId, category, repeating, reapeatingId, amount, date, description, id);
	}

	public Expense() {
		
	}

}
