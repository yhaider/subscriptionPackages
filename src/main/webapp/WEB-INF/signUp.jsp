<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.yasmeen.beltexam.models.Subscription" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<% List<Subscription> subs = (List<Subscription>) request.getAttribute("allsub"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign Up</title>
</head>
<body>
    <h1>Welcome <c:out value="${currentUser.first_name}"></c:out>!</h1>
    <p>This must be your first time or you must be switching subscriptions, since we do not have a subscription registered under your information</p>
    <p>Please choose a subscription and a due date.
    
    <form id="setSubForm" method="POST" action="/signup">
        <label>Due Date:<br>
        	<input name="due" type="number" min="1" max="31"/>
        </label><br>
        <label>Subscription:<br>
        <select name="subid">
        		<% for(int i = 0; i < subs.size(); i++ ){ %>
        			<% Subscription sub = subs.get(i); %>
        			<% if(sub.isAvailability()) { %>
        				<option value="<%= sub.getId() %>"><%= sub.getName() %> (<%= sub.getPrice() %>)</option>
        			<% } %>
        		<% } %> 
        </select>
        </label><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit"/>
    </form>
</body>
</html>