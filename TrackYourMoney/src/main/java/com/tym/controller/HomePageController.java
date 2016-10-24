package com.tym.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tym.model.Budget;
import com.tym.model.Expense;
import com.tym.model.Income;
import com.tym.model.Obligation;
import com.tym.model.User;
import com.tym.model.UserDAO;
import com.tym.model.UserHasBudgetsDAO;
import com.tym.model.UserHasExpensesDAO;
import com.tym.model.UserHasIncomesDAO;
import com.tym.model.UserHasObligationsDAO;
import com.tym.model.exceptions.UserException;

@Controller
@ContextConfiguration(classes = UserDAO.class)
public class HomePageController {

	private static final int REMEMBER_ME_COOKIE_TIME = 30 * 24 * 60 * 60;
	private static final int SESSION_TIME_IN_SECONDS = 60 * 60;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserHasIncomesDAO userHasIncomesDAO;
	@Autowired
	private UserHasExpensesDAO userHasExpensesDAO;
	@Autowired
	private UserHasObligationsDAO userHasObligationsDAO;
	@Autowired
	private UserHasBudgetsDAO userHasBudgetsDAO;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String homePage(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			Cookie[] cookies = request.getCookies();
			boolean foundCookie = false;
			String userId = null;
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if (c.getName().equals("userId")) {
						userId = c.getValue();
						foundCookie = true;
					}
				}
			}
			if (foundCookie) {
				int id = Integer.parseInt(userId);
				User loggedUser = new User(id, "usernam", "email@mail.com", "password");
				HttpSession session = request.getSession();
				userHasExpensesDAO.selectAndAddAllPaymentsOfUser(loggedUser);
				userHasIncomesDAO.selectAndAddAllPaymentsOfUser(loggedUser);
				userHasObligationsDAO.selectAndAddAllPaymentsOfUser(loggedUser);
				userHasBudgetsDAO.selectAndAddAllBudgetsOfUser(loggedUser);
				session.setAttribute("user", loggedUser);
				session.setMaxInactiveInterval(SESSION_TIME_IN_SECONDS);
				model.addAttribute("user", loggedUser);
				return "redirect:/home";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {

		try {
			if (!model.containsAttribute("user")) {
				model.addAttribute(new User());
			}
			model.addAttribute(new Expense());
			model.addAttribute(new Income());
			model.addAttribute(new Obligation());
			model.addAttribute(new Budget());
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute User user, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			User loggedUser = userDAO.loginUser(user);
			String rememberMe = request.getParameter("remember_me");
			if (rememberMe != null && rememberMe.equals("on")) {
				Cookie remember = new Cookie("userId", new Integer(loggedUser.getUserId()).toString());
				remember.setMaxAge(REMEMBER_ME_COOKIE_TIME);
				response.addCookie(remember);
			}
			userHasExpensesDAO.selectAndAddAllPaymentsOfUser(loggedUser);
			userHasIncomesDAO.selectAndAddAllPaymentsOfUser(loggedUser);
			userHasObligationsDAO.selectAndAddAllPaymentsOfUser(loggedUser);
			userHasBudgetsDAO.selectAndAddAllBudgetsOfUser(loggedUser);
			session.setAttribute("user", loggedUser);
			session.setMaxInactiveInterval(SESSION_TIME_IN_SECONDS);
			model.addAttribute("user", loggedUser);
		} catch (UserException e) {
			model.addAttribute("loginFail", "Invalid username or password");
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/home";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(Model model) {
		try {
			model.addAttribute(new User());
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute User user, Model model) {
		try {
			try {
				userDAO.registerUser(user);
			} catch (UserException e) {
				model.addAttribute("registerFail", "The username has been already chosen!");
				return "register";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession httpSession = request.getSession(false);
			if (httpSession != null) {
				httpSession.invalidate();
			}
			Cookie remember = new Cookie("userId", null);
			remember.setMaxAge(0);
			response.addCookie(remember);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/";
	}

}
