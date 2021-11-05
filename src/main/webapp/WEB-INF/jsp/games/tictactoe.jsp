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
</head>

	<%@include file="../include/header.jsp" %>
<body>

<h1>Page de tictactoe</h1>
	<%@include file="../include/navbar.jsp" %>
<div id="game">
	<table>
		<tbody>
			<tr class="plateau plateau1">
				<td><button id="0" value="0">Case0</button></td>
				<td><button id="1" value="0">Case1</button></td>
				<td><button id="2" value="0">Case2</button></td>
			</tr>
			<tr class="plateau">
				<td><button id="3" value="0">Case3</button></td>
				<td><button id="4" value="0">Case4</button></td>
				<td><button id="5" value="0">Case5</button></td>
			</tr>
			<tr class="plateau">
				<td><button id="6" value="0">Case6</button></td>
				<td><button id="7" value="0">Case7</button></td>
				<td><button id="8" value="0">Case8</button></td>
			</tr>
		</tbody>
	</table>
</div>	
<script>

</script>
	
	
	

	<%@include file="../include/footer.jsp" %>
</body>
</html>