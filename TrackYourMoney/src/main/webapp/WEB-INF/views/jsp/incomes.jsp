<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="false"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript" src="js/jquery.canvasjs.min.js"></script>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#datepicker").datepicker();
	});

	$(document).ready(function() {

		$('#main').change(function() {

			if ($(this).is(':checked')) {
				$('input[name="id"]:checkbox').prop('checked', true);

			} else {

				$('input[name="id"]:checkbox').prop('checked', false);
			}
		});

		$('input[name="id"]:checkbox').change(function() {
			var chkLength = $('input[name="id"]:checkbox').length;
			var checkedLen = $('input[name="id"]:checkbox:checked').length;
			if (chkLength == checkedLen) {
				$('#main').prop('checked', true);
			} else {
				$('#main').prop('checked', false);
			}
		});
	});
</script>
<script type="text/javascript">
	window.onload = function() {
		var Salary = ${	user.getAmoutByPaymentCategoryId(6, user.incomes)};
		var Rent = ${ user.getAmoutByPaymentCategoryId(7, user.incomes)};
		var Grants = ${ user.getAmoutByPaymentCategoryId(8, user.incomes)};
		var Other = ${ user.getAmoutByPaymentCategoryId(9, user.incomes)};
		var array = [];
		if (Salary > 0){
			array.push({y: Salary, indexLabel: "<spring:message code="Salary" /> #percent%", legendText: "<spring:message code="Salary" />" });
		}
		if (Rent > 0){
		array.push({y: Rent, indexLabel: "<spring:message code="Rent" /> #percent%", legendText: "<spring:message code="Rent" />"});
		}
		if (Grants > 0){
		array.push({y: Grants,  indexLabel: "<spring:message code="Grants" /> #percent%", legendText: "<spring:message code="Grants" />" });
		}
		if (Other > 0){
		array.push({y: Other, indexLabel: "<spring:message code="Other" /> #percent%", legendText: "<spring:message code="Other" />" });
		}
		var chart = new CanvasJS.Chart("chartContainer",
				{
					title : {
						text : "<spring:message code="incomes.percentage" />"
					},
					animationEnabled : true,
					data : [ {
						type : "doughnut",
						startAngle : 60,
						toolTipContent : "{legendText}: {y} - <strong>#percent% </strong>",
						showInLegend : true,
						explodeOnClick : true,
						dataPoints : array
					} ]
				});
		chart.render();
	}
</script>
<script src="../../canvasjs.min.js"></script>

<title><spring:message code="my.incomes" /></title>

</head>
<body>

	<jsp:include page="home.header.jsp"></jsp:include>

	<section class="section_home">
	
	<c:choose>
		<c:when test="${not empty user.incomes }">
	<div id="chartContainer" style="height: 400px; width: 97%; margin-left: 20px;"></div>

	<div class="">
		<button id="myBtn"><spring:message code="add.income" /></button>

		<div class="Tables">
			<table class="table" name="expense_table" cellspacing="0"
				cellpadding="2" width="100%" border="1">
				<thead>
					<tr style="height: 35px;">
						<th><input name="selectALL" type="checkbox" value=""
							id="main" />&nbsp;<spring:message code="select.all" /><br /></th>
						<th align="left"><spring:message code="category" /></th>
						<th align="right"><spring:message code="amount" /></th>
						<th><spring:message code="repeat" /></th>
						<th><spring:message code="date" /></th>	
						<th align="left"><spring:message code="description" /></th>
					</tr>
				</thead>
				<tbody>
				<caption>
					<h2><spring:message code="all.incomes" /></h2>
				</caption>
				<p>
					<form:form action="./deleteIncome">

						<c:forEach items="${user.incomes}" var="income">
							<tr>
								<td align="center"><input type="checkbox" name="id"
									id="${income.id}" value="${income.id}" /></td>
								<td align="left"><spring:message code="${income.category}" /></td>
								<td align="right"><c:out value="${income.amount}"></c:out>&nbsp;$</td>
								<td align="center"><spring:message code="${income.repeating}" /></td>
								<td align="center"><c:out value="${income.date}"></c:out></td>
								<td align="left">(<c:out value="${income.description}"></c:out>)
								</td>
							</tr>
						</c:forEach>
						<input type="submit" id="delete" name="commit"
							value="<spring:message code="delete.selected" />">
					</form:form>
				</p>
				</tbody>
				<tfoot>
					<tr>
						<td align="right" colspan="2" style="padding-top: 14px"><strong><spring:message code="total.amount" />:</strong></td>
						<td align="right" style="padding-top: 14px"><strong>
								<c:out value="${user.getTotalAmountFor(user.incomes)}"></c:out> &nbsp;$
						</strong></td>
						<td colspan="4" ></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	</c:when>
		<c:otherwise>
			<div id="welcome_text">
			<br>
			<h1><c:out value="${user.username}"></c:out>, <spring:message code="incomes.welcome.message" /> <br> </h1>
			<button id="myBtn" style="float: inherit; margin-left: 600px;"><spring:message code="add.income" /></button>
			</div>
		</c:otherwise>
	</c:choose>
	
	
	
	<div id="myModal" class="modal">

			<div class="modal-content">
				<span class="close"><spring:message code="close" /></span>

				<form:form commandName="income">

					<p>
						<form:label path="categoryId"><spring:message code="category" /></form:label>
						<form:select id="categoryId" class="input" path="categoryId">
							<form:option value="6"><spring:message code="Salary" /></form:option>

							<form:option value="7"><spring:message code="Rent" /></form:option>

							<form:option value="8"><spring:message code="Grants" /></form:option>

							<form:option value="9"><spring:message code="Other" /></form:option>

						</form:select>
					</p>
					<p>
						<form:label path="amount"><spring:message code="amount" />:</form:label>
						<form:input type="number" min="0.01" step="0.01" value="1.00"
							max="1000000" id="amount" class="input" name="amount"
							path="amount" required="required" />
					</p>
					<p>
						<form:label path="repeatingId"><spring:message code="repeating" />:</form:label>
						<form:select id="repeatingId" class="input" name="repeatingId"
							path="repeatingId">

							<form:option value="1"><spring:message code="Once" /></form:option>
							<form:option value="2"><spring:message code="Daily" /></form:option>
							<form:option value="3"><spring:message code="Weekly" /></form:option>
							<form:option value="4"><spring:message code="Monthly" /></form:option>
							<form:option value="5"><spring:message code="Yearly" /></form:option>

						</form:select>

					</p>
					<p>
						<form:label path="date"><spring:message code="date" />:</form:label>
						<spring:message code="date" var="date1" />
						<form:input id="datepicker" class="input" name="date" path="date"
							placeholder="${date1}" required="required" />
					</p>
					<p>
						<form:label path="description"><spring:message code="description" />:</form:label>
						<spring:message code="description" var="description1" />
						<form:textarea id="description" class="input" name="description"
							path="description" placeholder="${description1}" required="required" />
					</p>

					<p class="submit">
						<input type="submit" name="commit" value="<spring:message code="add" />">
					</p>

				</form:form>

			</div>

		</div>
	<script type="text/javascript" src="<c:url value='js/modal.js'/>"></script>
	</section>

	<jsp:include page="footer.jsp"></jsp:include>