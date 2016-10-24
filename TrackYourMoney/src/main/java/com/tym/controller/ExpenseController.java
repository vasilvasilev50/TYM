package com.tym.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tym.model.Expense;
import com.tym.model.Payment;
import com.tym.model.User;
import com.tym.model.UserHasExpensesDAO;
import com.tym.model.exceptions.UserException;

@Controller
@ContextConfiguration(classes = UserHasExpensesDAO.class)
@Scope("session")
public class ExpenseController {

	@Autowired
	private UserHasExpensesDAO userHasExpensesDAO;

	@RequestMapping(value = "/expenses", method = RequestMethod.POST)
	public String addExpense(Expense expense, Model model, HttpServletRequest request) {

		try {
			if (request.getSession(false) == null) {
				return "index";
			}

			User user = (User) request.getSession().getAttribute("user");
			userHasExpensesDAO.insertPayment(user.getUserId(), expense);
			userHasExpensesDAO.selectAndAddAllPaymentsOfUser(user);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "redirect:/expenses";
	}

	@RequestMapping(value = "/deleteExpense", method = RequestMethod.POST)
	public String deleteExpense(HttpServletRequest req) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			String[] ids = req.getParameterValues("id");
			int id;
			if (ids != null) {
				for (int index = 0; index < ids.length; index++) {
					id = Integer.parseInt(ids[index]);
					try {
						if (userHasExpensesDAO.deletePayment(id)) {
							user.removeExpense(id);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return "error";
					}
				}

				userHasExpensesDAO.selectAndAddAllPaymentsOfUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "redirect:/expenses";
	}

	@RequestMapping(value = "/getExpensesBy", method = RequestMethod.GET)
	public String getExpenses(Expense expense, Model model, HttpServletRequest request) {
		try {
			if (request.getSession(false) == null) {
				return "index";
			}

			User user = (User) request.getSession().getAttribute("user");
			String id = request.getParameter("categoryId");
			int categoryId = Integer.parseInt(id);
			String from = request.getParameter("from");
			String to = request.getParameter("to");

			List<Payment> expenses = new LinkedList<Payment>();
			try {
				expenses = user.getPaymentsBy(from, to, categoryId, user.getExpenses());
			} catch (UserException e) {
				e.printStackTrace();
				return "error";
			}
			double totalAmount = user.getTotalAmountFor(expenses);

			model.addAttribute("expenses", expenses);
			model.addAttribute("totalAmount", totalAmount);
			model.addAttribute("user", user);
			model.addAttribute(new Expense());
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "expenses";
	}

}
