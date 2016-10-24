<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script>
	$(document).ready(function() {
		$("#datepicker").datepicker();
		$("#datepickerFrom").datepicker();
		$("#datepickerTo").datepicker();
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
window.onload = function () {
	var foodAndDrinks = ${user.getAmoutByPaymentCategoryId(1, user.expenses)};
	var transport = ${user.getAmoutByPaymentCategoryId(2, user.expenses)};
	var education = ${user.getAmoutByPaymentCategoryId(3, user.expenses)};
	var sport = ${user.getAmoutByPaymentCategoryId(4, user.expenses)};
	var bills = ${user.getAmoutByPaymentCategoryId(5, user.expenses)};
	var other = ${user.getAmoutByPaymentCategoryId(6, user.expenses)}; 
	var array = [];
	if (foodAndDrinks > 0){
		array.push({y: foodAndDrinks, indexLabel: "<spring:message code="Food_&_Drinks" /> #percent%", legendText: "<spring:message code="Food_&_Drinks" />" });
	}
	if (transport > 0){
	array.push({y: transport, indexLabel: "<spring:message code="Transport" /> #percent%", legendText: "<spring:message code="Transport" />"});
	}
	if (education > 0){
	array.push({y: education,  indexLabel: "<spring:message code="Education" /> #percent%", legendText: "<spring:message code="Food_&_Drinks" />" });
	}
	if (sport > 0){
	array.push({y: sport, indexLabel: "<spring:message code="Sport" /> #percent%", legendText: "<spring:message code="Sport" />" });
	}
	if (bills > 0){
	array.push({y: bills, indexLabel: "<spring:message code="Taxes" /> #percent%", legendText: "<spring:message code="Taxes" />" });
	}
	if (other > 0){
	array.push({y: other,  indexLabel: "<spring:message code="Other" /> #percent%", legendText: "<spring:message code="Other" />" });
	}
	var chart = new CanvasJS.Chart("chartContainer",
	{
		title:{
			text: "<spring:message code="expense.percentage" />"
		},
                animationEnabled: true,
		data: [
		{
			type: "doughnut",
			startAngle: 60,
			toolTipContent: "{legendText}: {y} - <strong>#percent% </strong>",
			showInLegend: true,
          explodeOnClick: true, 
			dataPoints: array
		}
		]
	});
	chart.render();
	}
	</script>
	<script src="../../canvasjs.min.js"></script>

<title><spring:message code="my.expenses" /></title>

</head>
<body>
	
	<jsp:include page="home.header.jsp"></jsp:include> 

	<section class="section_home">
	<c:choose>
		<c:when test="${not empty user.expenses}">
	<div id="chartContainer" style="height: 400px; width: 97%; margin-left: 20px;"></div>
	
	<div class="">

		<button id="myBtn"><spring:message code="add.expense" /></button>

		<button id="myBtn2"><spring:message code="get.expense" /></button>
		
		<div id="myModal2" class="modal">

			<div class="modal-content">
				<span class="close2"><spring:message code="close" /></span>

				<form class="Forms" action="./getExpensesBy" method="get">

					<p>
						<label for="categoryId"><spring:message code="category" />:</label> <select
							id="categoryId" class="input" name="categoryId">

							<option value="0"><spring:message code="all.categories" /></option>

							<option value="1"><spring:message code="Food_&_Drinks" /></option>

							<option value="2"><spring:message code="Transport" /></option>

							<option value="3"><spring:message code="Education" /></option>

							<option value="4"><spring:message code="Sport" /></option>

							<option value="5"><spring:message code="Taxes" /></option>

							<option value="6"><spring:message code="Other" /></option>

						</select>
					</p>

					<p>
						<label for="from"><spring:message code="from" />:</label> <input id="datepickerFrom" pattern="\d{1,2}/\d{1,2}/\d{4}"
							class="input" name="from" placeholder='<spring:message code="date" />' required="required" />
					</p>

					<p>
						<label for="to"><spring:message code="to" />:</label> <input id="datepickerTo" class="input"
							name="to" placeholder='<spring:message code="date" />' required="required" pattern="\d{1,2}/\d{1,2}/\d{4}"/>
					</p>

					<p class="submit">
						<input type="submit" name="commit" value="<spring:message code="find" />">
					</p>

				</form>

			</div>

		</div>
		
		<div class="Tables">
			<table class="table" name="expense_table" cellspacing="0"
						cellpadding="2" width="100%" border="1">		
				<thead>
					<tr style="height: 35px;">
						<th><c:if test="${empty expenses }"><input name="selectALL" type="checkbox" value=""
							id="main" />&nbsp;<spring:message code="select.all" /><br /></c:if></th>
						<th align="left"><spring:message code="category" /></th>
						<th align="right"><spring:message code="amount" /></th>
						<th><spring:message code="repeat" /></th>
						<th><spring:message code="date" /></th>
						<th align="left"><spring:message code="description" /></th>
					</tr>
				</thead>
				
				<c:choose>
					<c:when test="${empty expenses }">
						<tbody>
							<caption>
									<h2><spring:message code="all.expense" /></h2>
							</caption>
							<p>
								<form:form action="./deleteExpense">
									<c:forEach items="${user.expenses}" var="expense">
										<tr>
											<td align="center"><input type="checkbox" name="id"
												id="${expense.id}" value="${expense.id}" /></td>
											<td align="left"><spring:message code="${expense.category}" />
											</td>
											<td align="right"><c:out value="${expense.amount}"></c:out>&nbsp;$
											</td>
											<td align="center"><spring:message code="${expense.repeating}" /></td>
											<td align="center"><c:out value="${expense.date}"></c:out>
											</td>
											<td align="left">(<c:out value="${expense.description}"></c:out>)
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
								<td align="right" colspan="2" style="padding-top: 14px"><strong>
										<spring:message code="total.amount" />:</strong></td>
								<td align="right" style="padding-top: 14px"><strong>
								<c:out value="${user.getTotalAmountFor(user.expenses)}"></c:out> &nbsp;$ </strong> </td>
								<td colspan="4" ></td>
							</tr>
						</tfoot>
					</c:when>
					<c:otherwise>
						<tbody>
							<caption>
								<h2><spring:message code="result" /></h2>
							</caption>
							<c:forEach items="${expenses}" var="expense" varStatus="loop">
								<tr>
									<td align="center"><c:out value="${loop.index+1}"></c:out></td>
									<td align="left"><spring:message code="${expense.category}" />
									</td>
									<td align="right"><c:out value="${expense.amount}"></c:out>&nbsp;$
									</td>
									<td align="center"><spring:message code="${expense.repeating}" /></td>
									<td align="center"><c:out value="${expense.date}"></c:out></td>
									<td align="left">(<c:out value="${expense.description}"></c:out>)
									</td>
								</tr>			
							</c:forEach>
							<!-- <tr><th><input type="submit" id="delete" name="commit" value="Delete selected"><br/></th></tr> -->
						</tbody>
						<tfoot>
							<tr>
								<td align="right" colspan="2" style="padding-top: 14px"><strong><spring:message code="total.amount" />:</strong></td>
								<td align="right" style="padding-top: 14px"><strong>
								<c:out value="${totalAmount}"></c:out> &nbsp;$ </strong> </td>
								<td colspan="3" ></td>
							</tr>
						</tfoot>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>
	</c:when>
	<c:otherwise>
		<div id="welcome_text">
		<br>
		<h1><c:out value="${user.username}"></c:out>, <spring:message code="expense.welcome.message" /><br> </h1>
		<button id="myBtn" style="float:initial; margin-left:600px;"><spring:message code="add.expense" /></button>
		<button id="myBtn2" style="display:none;"><spring:message code="get.expense" /></button>
		</div>
	</c:otherwise>
	</c:choose>
	<div id="myModal" class="modal">

			<div class="modal-content">
				<span class="close"><spring:message code="close" /></span>

				<form:form commandName="expense" action="./expenses" method="POST">

					<p>
						<form:label path="categoryId"><spring:message code="category" />:</form:label>
						<form:select id="categoryId" class="input" path="categoryId">
							<form:option value="1"><spring:message code="Food_&_Drinks" /></form:option>

							<form:option value="2"><spring:message code="Transport" /></form:option>

							<form:option value="3"><spring:message code="Education" /></form:option>

							<form:option value="4"><spring:message code="Sport" /></form:option>

							<form:option value="5"><spring:message code="Taxes" /></form:option>

							<form:option value="6"><spring:message code="Other" /></form:option>

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
						<form:input id="datepicker" class="input" name="date" path="date" pattern="\d{1,2}/\d{1,2}/\d{4}"
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
		<script>
			// Get the modal
			var modal = document.getElementById('myModal');
			var modal2 = document.getElementById('myModal2');
			// Get the button that opens the modal
			var btn = document.getElementById("myBtn");
			var btn2 = document.getElementById("myBtn2");

			// Get the <span> element that closes the modal
			var span = document.getElementsByClassName("close")[0];
			var span2 = document.getElementsByClassName("close2")[0];

			// When the user clicks the button, open the modal
			btn.onclick = function() {
				modal.style.display = "block";
			}
			btn2.onclick = function() {
				modal2.style.display = "block";
			}

			// When the user clicks on <span> (x), close the modal
			span.onclick = function() {
				modal.style.display = "none";
			}
			span2.onclick = function() {
				modal2.style.display = "none";
			}

			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
				if (event.target == modal) {
					modal.style.display = "none";
				}
				if (event.target == modal2) {
					modal2.style.display = "none";
				}
			}
		</script>
	</section>

	<div>
		<br />
		<hr>
	</div>
		
	<footer>
	<div id="footer">
		<p><spring:message code="footer" />, IT Talents Training Camp,
			Java EE, 2016</p>
	</div>
	</footer>
</body>
</html>