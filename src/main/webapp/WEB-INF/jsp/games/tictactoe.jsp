<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">
	<meta name="Content-Language" content="fr" />
	<meta name="Description" content="" />
	<meta name="Keywords" content="Jeu de morpion" />
	<meta name="Subject" content="" />
	<meta name="Content-Type" content="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<spring:url value="/css/tictactoe.css" var="tictactoeCss" />
	<link href="${tictactoeCss}" rel="stylesheet" />
	
	<spring:url value="/js/tictactoe.js" var="tictactoeJs" />
	<script src="${tictactoeJs}"></script>
	
	
</head>

	<%@include file="../include/header.jsp" %>
<body>

<h1>Page de tictactoe</h1>
	<%@include file="../include/navbar.jsp" %>
<div id="game">
	<table>
		<tbody>
			<tr class="plateau plateau1">
				<td><button onclick="onclick1(0)" id="0">Case0</button></td>
				<td><button onclick="onclick1(1)" id="1">Case1</button></td>
				<td><button onclick="onclick1(2)" id="2">Case2</button></td>
			</tr>
			<tr class="plateau">
				<td><button onclick="onclick1(3)" id="3">Case3</button></td>
				<td><button onclick="onclick1(4)" id="4">Case4</button></td>
				<td><button onclick="onclick1(5)" id="5">Case5</button></td>
			</tr>
			<tr class="plateau">
				<td><button onclick="onclick1(6)" id="6">Case6</button></td>
				<td><button onclick="onclick1(7)" id="7">Case7</button></td>
				<td><button onclick="onclick1(8)" id="8">Case8</button></td>
			</tr>
		</tbody>
	</table>
</div>	
<div id="result">
	<h1></h1>
</div>
	


	<%@include file="../include/footer.jsp" %>
</body>
</html>