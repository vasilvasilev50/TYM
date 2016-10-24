<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="header.jsp"></jsp:include>

	<div class="header_buttons">
		<a href="./"><button type="submit" class="h_butt"><spring:message code="home" /></button></a> <a
			href="./login"><button type="submit" class="h_butt"><spring:message code="login" /></button></a> <a
			href="http://www.dnes.bg/"><button type="submit" class="h_butt"><spring:message code="news" /></button></a>
	</div>

	</header>

	<div>
		<hr>
		<br/>
	</div>

	<section class="section">
	<div class="register">
	
		<c:if test="${not empty registerFail}"> 
			<p class="invalid_input"><c:out value="${registerFail}"></c:out><p>
		</c:if> 
	
		<h1><spring:message code="register" /></h1>
		
		<form:form commandName="user">
			<spring:message code="email" var="email1" />
			<p><form:input type="email" id="email" class="input" path="email" placeholder="${email1}" required="required" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"/></p>
			<spring:message code="username" var="username1" />
			<p><form:input id="username" class="input" path="username" placeholder="${username1}" minlength="4" maxlength="15" required="required"/></p>
			<spring:message code="password" var="password1" />
			<p><form:password id="password" class="input" path="password" placeholder="${password1}" minlength="4" maxlength="15" required="required"/></p>
			
			<p class="submit" style='margin-top: 20px;'><input type="submit" name="commit" value="<spring:message code="button.register" />"></p>
			
		</form:form>
		
	</div>
	</section>

	<jsp:include page="footer.jsp"></jsp:include>