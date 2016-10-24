<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="false"%>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	HttpSession session = request.getSession();
	if (session.getAttribute("user") == null)
		response.sendRedirect("./login");
%>

<div class="language">
			<a href="?language=en" class="EN">
					EN
				</a>
				
				<a href="?language=de" class="DE">
					DE
				</a>
</div>
<header>
	<div class="logo">
		<img alt="logo" src="img/logo.jpg">
	</div>
	<div class="title-text">
	</div>
	<div class="header_buttons">
		<a href="./logout"><button type="submit" class="h_butt"><spring:message code="logout" /></button></a>
	</div>
	<br />
	<div class="navigation_buttons">
		<nav>
			<a href="./home" class="space"><button class="n_button" type="submit"><spring:message code="home" /></button></a>
			<a href="./incomes" class="space"><button class="n_button" type="submit"><spring:message code="incomes" /></button></a>
			<a href="./expenses" class="space"><button class="n_button" type="submit"><spring:message code="expenses" /></button></a>
			<a href="./obligations" class="space"><button class="n_button" type="submit"><spring:message code="obligations" /></button></a>
			<a href="./budgets" class="space"><button class="n_button" type="submit"><spring:message code="budgets" /></button></a>
		</nav>
	</div>
</header>

<div>
	<hr>
	<br />
</div>
