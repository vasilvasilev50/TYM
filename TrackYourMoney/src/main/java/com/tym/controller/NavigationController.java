package com.tym.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tym.model.Budget;
import com.tym.model.Expense;
import com.tym.model.Income;
import com.tym.model.Obligation;
import com.tym.model.User;

@Controller
public class NavigationController {
	
	@RequestMapping(value="/incomes", method = RequestMethod.GET)
	public String incomes(Model model, HttpServletRequest request) {
		try{
		if (request.getSession(false) == null){
			return "index";
		}
		
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute(new Income());
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		return "incomes";
	}
	
	@RequestMapping(value="/expenses", method = RequestMethod.GET)
	public String expenses(Model model, HttpServletRequest request) {
		try{
		if (request.getSession(false) == null){
			return "index";
		}
		
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute(new Expense());
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		return "expenses";
	}
	
	@RequestMapping(value="/obligations", method = RequestMethod.GET)
	public String obligations(Model model, HttpServletRequest request) {
		try{
		if (request.getSession(false) == null){
			return "index";
		}
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute(new Obligation());
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		return "obligations";
	}
	
	@RequestMapping(value="/budgets", method = RequestMethod.GET)
	public String budgets(Model model, HttpServletRequest request){
		try{
		if(request.getSession(false) == null){
			return "index";
		}
		
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
		model.addAttribute(new Budget());
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		
		return "budgets";
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		try{
		if (request.getSession(false) == null){
			return "index";
		}
		
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
		}
		catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "home";
	}
		
	
}
