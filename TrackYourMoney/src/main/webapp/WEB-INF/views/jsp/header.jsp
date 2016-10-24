<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- <meta name="viewport" content="width=divice-width, initial-scale=1.0"> -->
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/homepage.css">
<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css"> -->
<!-- <link href="bootstrap.css" rel="stylesheet" media="screen"> -->
<!-- <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<title>TYM</title>

<script>
$(document).ready(function(){
    $("button").click(function(){
        $("p").hide(1000);
    });
});

$(document).ready(function(){
    $("button").click(function(){
        $("p").show(1000);
    });
});
</script>


<!-- <script>
$(document).ready(function() {
  $(function() {
    console.log('false');
    $( "#dialog" ).dialog({
        autoOpen: false,
        title: 'Test'
    });
  });

  $("button").click(function(){
    console.log("click");
        $(this).hide();
        $( "#dialog" ).dialog('open');
    });
}); 
</script> -->
</head>
<body>
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
		
			
	