<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
    <h1>Welcome <c:out value="${currentUser.first_name}"></c:out>!</h1>
    <p><b>Current Subscription: </b> <c:out value="${currentUser.getSubscription().getName()}"></c:out></p>
    <p><b>Next Due Date: </b> <fmt:formatDate pattern = "EEEEEE, 'the' dd 'of' MMMM, yyyy"  value="${currentUser.getDueDate()}"/></p>
    <p><b>Amount Due ($): </b> <c:out value="${currentUser.getSubscription().getPrice()}0"></c:out></p>
    <p><b>Member Since: </b> <fmt:formatDate pattern = "EEEEEE, 'the' dd 'of' MMMM, yyyy"  value="${currentUser.createdAt}"/></p>
    
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
</body>
</html>