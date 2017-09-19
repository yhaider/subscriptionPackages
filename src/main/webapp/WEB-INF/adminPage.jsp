<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="com.yasmeen.beltexam.models.User"%>
<%@page import="com.yasmeen.beltexam.models.Subscription"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
<style>
	table{
		border: 1px solid black;
	}
	td, th{
		padding: 10px;
		border: 1px solid black;
	}
</style>
</head>
<body>
    <h1>Welcome <c:out value="${currentUser.first_name}"></c:out>!</h1>
    <h3>Customers</h3>
    <table>
    		<tr>
    			<th>Name</th>
    			<th>Next Due Date</th>
    			<th>Amount Due</th>
    			<th>Subscription Type</th>
    		</tr>
    		<% List<User> all = (List<User>) request.getAttribute("all"); %>
    		<% for(int i = 0; i < all.size(); i++) { %>
    			<% User user = all.get(i); %>
    				<% if(user.getSubscription() != null) { %>
    				<tr>
    					<td><%= user.getFirst_name() %> <%= user.getLast_name() %></td>
    					<td><fmt:formatDate pattern = "EEEEEE, 'the' dd 'of' MMMM, yyyy"  value="<%= user.getDueDate() %>"/></td>
    					<td><%= user.getSubscription().getPrice() %></td>
    					<td><%= user.getSubscription().getName() %>
    					
    				</tr>
    				<% } %>
    		<% } %>
    </table><br>
    <h3>Subscriptions</h3>
    <table>
    		<tr>
    			<th>Name</th>
    			<th>Cost</th>
    			<th>Available</th>
    			<th>Users</th>
    			<th>Actions</th>
    		</tr>
    		<% List<Subscription> allsubs = (List<Subscription>) request.getAttribute("allsubs"); %>
    		<% for(int i = 0; i < allsubs.size(); i++) { %>
    			<% Subscription sub = allsubs.get(i); %>
    				<tr>
    					<td><%= sub.getName() %></td>
    					<td><%= sub.getPrice() %></td>
    					<% if(sub.isAvailability()) { %>
    						<td>Available</td>
    					<% } else { %>
    						<td>Not Available</td>
    					<% } %>
    					<td><%= sub.getUsers().size() %></td>
    					<% if(sub.isAvailability()) { %>
    						<% if(sub.getUsers().size() == 0) { %>
    							<td><a href="/sub/deactivate/<%= sub.getId()%>">Deactivate</a> || <a href="/sub/delete/<%= sub.getId()%>">Delete</a></td>
    						<% } else { %>
    							<td><a href="/sub/deactivate/<%= sub.getId()%>">Deactivate</a></td>
    						<% } %>
    					<% } else if(!sub.isAvailability()){ %>
    						<td><a href="/sub/activate/<%= sub.getId()%>">Activate</a></td>
    				<% } %>
    				</tr>
    		<% } %>
    </table><br>
    
    <h3>Make Subscription Package</h3>
    <form:form method="POST" modelAttribute="subscription" action="/createsub">
    		<form:label path="name">Package Name:
    			<form:input path="name" type="text"/>
    		</form:label><br>
    		<form:label path="name">Package Cost:
    			<form:input path="price" type="number"/>
    		</form:label><br>
    		<input type="submit"/>
    </form:form><br>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
</body>
</html>