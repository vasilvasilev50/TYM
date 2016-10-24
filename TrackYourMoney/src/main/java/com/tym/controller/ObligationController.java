package com.tym.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tym.model.Obligation;
import com.tym.model.User;
import com.tym.model.UserHasObligationsDAO;

@Controller
@ContextConfiguration(classes = UserHasObligationsDAO.class)
@Scope("session")
public class ObligationController {

	@Autowired
	private UserHasObligationsDAO userHasObligationsDAO;

	@RequestMapping(value = "/obligations", method = RequestMethod.POST)
	public String addObligation(Obligation obligation, Model model, HttpServletRequest request) {

		try {

			if (request.getSession(false) == null) {
				return "index";
			}

			User user = (User) request.getSession().getAttribute("user");

			model.addAttribute("obligation", userHasObligationsDAO.insertPayment(user.getUserId(), obligation));
			userHasObligationsDAO.selectAndAddAllPaymentsOfUser(user);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "redirect:/obligations";
	}

	@RequestMapping(value = "/deleteObligation", method = RequestMethod.POST)
	public String deleteObligation(HttpServletRequest req) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			String[] ids = req.getParameterValues("id");
			int id;
			if (ids != null) {
				for (int index = 0; index < ids.length; index++) {
					id = Integer.parseInt(ids[index]);
					try {
						if (userHasObligationsDAO.deletePayment(id)) {
							user.removeObligation(id);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return "error";
					}
				}
				userHasObligationsDAO.selectAndAddAllPaymentsOfUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "redirect:/obligations";
	}

}