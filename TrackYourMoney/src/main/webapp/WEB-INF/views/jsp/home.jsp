<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/homepage.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/canvasjs.min.js"></script>
<script type="text/javascript" src="js/jquery.canvasjs.min.js"></script>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script> 
<script type="text/javascript">
window.onload = function () {
	var incomes = ${user.getPaymentsForMonth(user.incomes)};
	var expenses = ${user.getPaymentsForMonth(user.expenses)};
	var balance = ${user.balanceForMonth};
	
	var chart = new CanvasJS.Chart("chartContainer2",
	{
		title:{
			text: "<spring:message code="monthly.overview" />"
		},
                animationEnabled: true,
		data: [
		{
			type: "doughnut",
			startAngle: 60,
			toolTipContent: "{legendText}: {y} - <strong>#percent% </strong>",
			showInLegend: true,
          explodeOnClick: true, 
			dataPoints: [
				{y: incomes, indexLabel: "<spring:message code="incomes" /> #percent%", legendText: "<spring:message code="incomes" />" },
				{y: expenses, indexLabel: "<spring:message code="expenses" /> #percent%", legendText: "<spring:message code="expenses" />" },
				{y: balance,  indexLabel: "<spring:message code="balance" /> #percent%", legendText: "<spring:message code="balance" />" },
			]
		}
		]
	});
	chart.render();
	}
	</script>
	<script src="../../canvasjs.min.js"></script>
<title><spring:message code="welcome.title" /></title>

</head>
<body>
	
	<jsp:include page="home.header.jsp"></jsp:include> 
	
	<c:choose>
		<c:when test="${user.getPaymentsForMonth(user.incomes) > 0 or user.getPaymentsForMonth(user.expenses) > 0}">
			<div id="chartContainer2" style="height: 400px; width: 97%; margin-left: 20px;"></div>
		
	
	<section class="section_home">	
	
	<div class="table" style="width:300px; margin-left: 537px;">
			
		<p class="total_money"><spring:message code="incomes" />:<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${user.getPaymentsForMonth(user.incomes)}" />&nbsp;$</p>
		<p class="total_money"><spring:message code="expenses"/>:<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${user.getPaymentsForMonth(user.expenses)}" />&nbsp;$</p>
		<p class="total_money"><spring:message code="balance" />:<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${user.balanceForMonth}" />&nbsp;$</p>

	</div>
	
	<div class="Tables">
		<table class="table" name="expense_table" cellspacing="0"
				cellpadding="2" width="100%" border="1">
			<thead>
				<tr style="height: 35px;">
					<th align="center" ><spring:message code="date" /></th>	
					<th align="center"><spring:message code="amount" /></th>
					<th align="center"><spring:message code="category" /></th>
					<th align="center"><spring:message code="description" /></th>
					<th align="center"><spring:message code="repeat" /></th>
				</tr>
					</thead>
					<tbody>
					<caption class="upcoming_payments">
						<h2><spring:message code="upcoming.payments" /></h2>
					</caption>
			<p>
			<c:forEach items="${user.getUpcomingPaymentsForMonth(user.expenses)}" var="payment">
				<tr>
					<td align="center" width="10%"><c:out value="${payment.date}"></c:out></td>
					<td align="center"><c:out value="${payment.amount}"></c:out>&nbsp;$</td>
					<td align="center"><spring:message code="${payment.category}" /></td>
					<td align="center">(<c:out value="${payment.description}"></c:out>)</td>		
					<td align="center"><spring:message code="${payment.repeating}" /></td>			
				</tr>
			</c:forEach>
			<p/>
			</tbody>
		</table>	
	</div>
	</section>
	</c:when>
		<c:otherwise>
			<div id="welcome_text">
			<br>
			<h1> <spring:message code="hello.message" /><c:out value="${user.username}"></c:out>, <spring:message code="monthly.info" />! <br> </h1>
			<h1><spring:message code="example.diagram" /></h1>
			<img src="img/HomePicture2.PNG" alt="Example picture" width="90%" style="margin-left: 90px" />
			</div>
		</c:otherwise>
	</c:choose>
	<jsp:include page="footer.jsp"></jsp:include>