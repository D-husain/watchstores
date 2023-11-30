<%@page import="com.example.demo.paytm.pay.Config"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<% Map<String, Object> result = new HashMap<String, Object>();%>
<% result = Config.TransactionStatus();%>
<%
String outputHTML="";
for (Map.Entry<String, Object> entry : result.entrySet()) {
			outputHTML +="<tr><td>" + entry.getKey()  + "</td><td>" + entry.getValue() + "</td></tr>";
				 }
%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<title>Paytm Js Checkout - Java</title>
</head>
<body>
	<div class="container">
      	<div class="shadow p-3 mb-5 bg-white rounded">
      	<h4 class="text-center">Transaction Status</h4>
      	<table class="table table-bordered">
<%= outputHTML %>
</table>
</div>
</div>
</body>
</html>