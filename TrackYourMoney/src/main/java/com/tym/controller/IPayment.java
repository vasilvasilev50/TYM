package com.tym.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.tym.model.Payment;

public interface IPayment {
	
	String addPayment(@ModelAttribute Payment payment, Model model, HttpServletRequest request);
	String deletePayment(HttpServletRequest request);
	
}
