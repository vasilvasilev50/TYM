package com.tym.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.tym.model.exceptions.ObligationException;
import com.tym.model.exceptions.PaymentException;

public class Obligation extends Payment {

	private static final int YEARLY_REP_ID = 5;
	private static final int MONTHLY_REP_ID = 4;
	private static final int WEEKLY_REP_ID = 3;
	private static final int DAILY_REP_ID = 2;
	private static final int ONCE_REP_ID = 1;
	private static final int PERIOD_ID_FOR_YEARS = 4;
	private static final int PERIOD_ID_FOR_MONTHS = 3;
	private static final int PERIOD_ID_FOR_WEEKS = 2;
	private static final int PERIOD_ID_FOR_DAYS = 1;
	private int periodId;
	private String period;
	private int periodQuantity;
	private double remainedAmount;

	public Obligation(int categoryId, String category, String repeating, int reapeatingId, double amount,
			LocalDate date, String description, int id, String period, int periodId, int periodQuantity)
			throws PaymentException {
		super(categoryId, category, repeating, reapeatingId, amount, date, description, id);
		if (UserHasDAO.isValidString(period)) {
			this.period = period;
		} else {
			throw new ObligationException("There is no such a period!");
		}
		setPeriodId(periodId);
		setPeriodQuantity(periodQuantity);
	}

	public Obligation() {
	}

	public int getPeriodId() {
		return periodId;
	}

	public String getPeriod() {
		return period;
	}

	public int getPeriodQuantity() {
		return periodQuantity;
	}

	public void setPeriodId(int periodId) throws ObligationException {
		if (periodId <= 0){
			throw new ObligationException("There is no such a period id!");
		}
		this.periodId = periodId;


	}

	public void setPeriodQuantity(int periodQuantity) throws ObligationException {
		if (periodQuantity > 0) {
			this.periodQuantity = periodQuantity;
		} else {
			throw new ObligationException("There is no such a period quantity!");
		}
	}

	@Override
	public String toString() {
		return super.toString() + ", Period=" + periodQuantity + " " + period;
	}

	/*
	 * get the paid amount for obligation, by calculating
	 * the payments from start date to date now and
	 * calculating the payment frequency
	 * */
	public double getPaidAmount(){
		LocalDate begin = super.getDate();
		LocalDate end = null;
		double payment = 0;
		double amount = 0;
		switch (this.periodId){
			case PERIOD_ID_FOR_DAYS:
				end = begin.plusDays(this.periodQuantity);
				break;
			case PERIOD_ID_FOR_WEEKS:
				end = begin.plusWeeks(this.periodQuantity);
				break;
			case PERIOD_ID_FOR_MONTHS:
				end = begin.plusMonths(this.periodQuantity);
				break;
			case PERIOD_ID_FOR_YEARS:
				end = begin.plusYears(this.periodQuantity);
				break;
			default:
				System.err.println("Invalid Input");
				break;
		}
		long days = ChronoUnit.DAYS.between(begin, end);
		long weeks = ChronoUnit.WEEKS.between(begin, end);
		long months = ChronoUnit.MONTHS.between(begin, end);
		long years = ChronoUnit.YEARS.between(begin, end);
		LocalDate now = LocalDate.now();
		switch (super.getRepeatingId()){
			case ONCE_REP_ID:
				payment = super.getAmount();
				if (end.isBefore(now)){
					amount = payment;
				}
				break;
			case DAILY_REP_ID:
				payment = super.getAmount()/days;
				if (end.isBefore(now)){
					amount = payment;
				} else {
					amount = payment*ChronoUnit.DAYS.between(begin, now);
				}
				break;
			case WEEKLY_REP_ID:
				payment = super.getAmount()/weeks;
				if (end.isBefore(now)){
					amount = payment;
				} else {
					amount = payment*ChronoUnit.WEEKS.between(begin, now);
				}
				break;
			case MONTHLY_REP_ID:
				payment = super.getAmount()/months;
				if (end.isBefore(now)){
					amount = payment;
				} else {
					amount = payment*ChronoUnit.MONTHS.between(begin, now);
				}
				break;
			case YEARLY_REP_ID:
				payment = super.getAmount()/years;
				if (end.isBefore(now)){
					amount = payment;
				} else {
					amount = payment*ChronoUnit.YEARS.between(begin, now);
				}
				break;
			default:
				System.err.println("Invalid obligation data");
				break;
		}
		return amount;	
	}
	
	public double getRemainedAmount() {
		this.remainedAmount = super.getAmount() - getPaidAmount();
		return remainedAmount;
	}

}
