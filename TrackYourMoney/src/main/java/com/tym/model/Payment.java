package com.tym.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.tym.model.exceptions.PaymentException;

public abstract class Payment implements Cloneable{

	private int categoryId;
	private String category;
	private String repeating;
	private int repeatingId;
	private double amount;
	private LocalDate date;
	private String description;
	private int id;
	
	public Payment(int categoryId, String category, String repeating, int reapeatingId, double amount, LocalDate date,
													String description, int id) throws PaymentException {
		setCategoryId(categoryId);
		setRepeatingId(reapeatingId);
		setAmount(amount);
		setDescription(description);
		if(id >= 0){
			this.id = id;
		}
		else{
			throw new PaymentException("Invalid id given!");
		}
		
		if(UserHasDAO.isValidString(category)){
			this.category = category;
		}
		else{
			throw new PaymentException("The category given is not valid!");
		}
		
		if(UserHasDAO.isValidString(repeating)){
			this.repeating = repeating;
		}
		else{
			throw new PaymentException("The repeating given is not valid!");
		}
		
		if(date != null){
			this.date = date;
		}
		else{
			throw new PaymentException("Invalid date given!");
		}
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setRepeatingId(int repeatingId) throws PaymentException {
		if (repeatingId <= 0){
			throw new PaymentException("Invalid repeating id given!");
		}
		this.repeatingId = repeatingId;
	}

	public void setAmount(double amount) throws PaymentException {
		if (amount > 0) {
			this.amount = amount;
		} else {
			throw new PaymentException("There is no such an amount!");
		}
	}

	public void setDate(String date) throws PaymentException {
		if (UserHasDAO.isValidString(date)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate parsedDate = LocalDate.parse(date, formatter);
			this.date = parsedDate;
		} else {
			throw new PaymentException("Not a valid date given!");
		}
	}
	
	public void setLocalDate(LocalDate date) {
		this.date = date;
	}

	public void setDescription(String description) throws PaymentException {
		if (UserHasDAO.isValidString(description)) {
			this.description = description;
		} else {
			throw new PaymentException("The description given is not valid!");
		}
	}

	public Payment() {

	}

	

	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getRepeating() {
		return repeating;
	}

	public int getRepeatingId() {
		return repeatingId;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Payment [category=" + category + ", repeating=" + repeating + ", amount=" + amount + ", date=" + date
				+ ", description=" + description + "]";
	}

	public Payment getCopy() throws CloneNotSupportedException {
		return (Payment) super.clone();
	}

}
