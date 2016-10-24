package com.tym.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tym.model.Income;
import com.tym.model.User;
import com.tym.model.UserHasIncomesDAO;

@Controller
@ContextConfiguration(classes = UserHasIncomesDAO.class)
@Scope("session")
public class IncomeController {

	@Autowired
	private UserHasIncomesDAO userHasIncomesDAO;

	@RequestMapping(value = "/incomes", method = RequestMethod.POST)
	public String addIncome(Income income, Model model, HttpServletRequest request) {
		try {
			if (request.getSession(false) == null) {
				return "index";
			}

			User user = (User) request.getSession().getAttribute("user");

			model.addAttribute("income", userHasIncomesDAO.insertPayment(user.getUserId(), income));
			userHasIncomesDAO.selectAndAddAllPaymentsOfUser(user);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "redirect:/incomes";
	}

	@RequestMapping(value = "/deleteIncome", method = RequestMethod.POST)
	public String deleteIncome(HttpServletRequest req) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			String[] ids = req.getParameterValues("id");
			int id;
			if (ids != null) {
				for (int index = 0; index < ids.length; index++) {
					id = Integer.parseInt(ids[index]);
					try {
						if (userHasIncomesDAO.deletePayment(id)) {
							user.removeIncome(id);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return "error";
					}
				}

				userHasIncomesDAO.selectAndAddAllPaymentsOfUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "redirect:/incomes";
	}

}
