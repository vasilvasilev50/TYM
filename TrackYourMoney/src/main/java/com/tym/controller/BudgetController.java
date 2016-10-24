package com.tym.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tym.model.Budget;
import com.tym.model.User;
import com.tym.model.UserHasBudgetsDAO;
import com.tym.model.exceptions.PaymentException;

@Controller
@ContextConfiguration(classes = UserHasBudgetsDAO.class)
@Scope("session")
public class BudgetController {

	@Autowired
	private UserHasBudgetsDAO userHasBudgetsDAO;

	@RequestMapping(value = "/budgets", method = RequestMethod.POST)
	public String addBudget(Budget budget, Model model, HttpServletRequest request) {

		try {

			if (request.getSession(false) == null) {
				return "index";
			}

			User user = (User) request.getSession().getAttribute("user");
			userHasBudgetsDAO.insertBudget(user.getUserId(), budget);
			userHasBudgetsDAO.selectAndAddAllBudgetsOfUser(user);
			model.addAttribute(new Budget());
		} catch (PaymentException e) {
			model.addAttribute("insertFail", "Already exist budget for this category");
			model.addAttribute(new Budget());
			return "redirect:/budgets";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/budgets";
	}

	@RequestMapping(value = "/deleteBudget", method = RequestMethod.POST)
	public String deleteBudget(HttpServletRequest req, Model model) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			String[] ids = req.getParameterValues("id");
			int expenseId;
			if (ids != null) {
				for (int index = 0; index < ids.length; index++) {
					expenseId = Integer.parseInt(ids[index]);
					try {
						if (userHasBudgetsDAO.deleteBudget(user.getUserId(), expenseId)) {
							user.removeBudget(expenseId);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return "error";
					}
				}
				userHasBudgetsDAO.selectAndAddAllBudgetsOfUser(user);
				model.addAttribute(new Budget());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/budgets";
	}

}
