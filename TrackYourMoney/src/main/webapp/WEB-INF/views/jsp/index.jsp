<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:include page="header.jsp"></jsp:include>

<div class="header_buttons">
	
	<a href="./login"><button type="submit" class="h_butt"><spring:message code="login" /></button></a>
	<a href="./register"><button type="submit" class="h_butt"><spring:message code="register" /></button></a>
	<a href="http://www.dnes.bg/"><button type="submit" class="h_butt"><spring:message code="news" /></button></a>
</div>
</header>

<div>
	<hr>
	<br />
</div>

<section class="section">
<div class="front_page">
	<div class="main_heading">
		<h2 style="padding-top: 20px;"><spring:message code="welcome.message" /></h2>
	</div>

	<div class="sub_heading">
		<h3><spring:message code="welcome.message.second" /></h3>
	</div>

	<div class="center_buttons">


		<!-- <div id='content'>Hello World</div> -->
		<!-- <input type='button' id='hideshow' value='hide/show'> -->
		<!-- <script>
		
		jQuery(document).ready(function(){
		    jQuery('#hideshow').live('click', function(event) {        
		         jQuery('#content').toggle('show');
		    });
		});
		
		</script> -->
		<!-- <a href="#"> -->


		<ul>
			<li><spring:message code="possibility.first" /></li><br/>
			<li><spring:message code="possibility.second" /></li><br />
			<li><spring:message code="possibility.third" /></li><br/>
		</ul>


		<!-- <button type="submit" class="center_button" id="open">Enter</button>
		</a>
		<p>your incomes and expenses</p> -->
		<!-- <a href="#"><button type="submit" class="center_button" id="open">Track</button></a> 
		<a href="#"><button type="submit" class="center_button" id="open">Allocate</button></a> -->


	</div>
</div>
</section>
<jsp:include page="footer.jsp"></jsp:include>