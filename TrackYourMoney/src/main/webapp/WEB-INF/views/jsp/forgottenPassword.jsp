<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset Password</title>
</head>
<body>

<form:form method="post" action="/password/change" modelAttribute="changePasswordForm">
    <form:label path="password">
        New password:
    </form:label>
    <form:password path="password" />
    <input type="submit" value="Change password" />
</form:form>

</body>
</html>